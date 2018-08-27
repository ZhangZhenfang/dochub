// var url = "http://localhost:8080/dochub"
// var url = "http://www.the15373.com/dochub"

function createCollapseDiv(id, head, body, d){
	var a1 = document.createElement("a");
    a1.setAttribute("data-toggle", "collapse");
    a1.setAttribute("href", "#collapse-" + id); 
    a1.setAttribute("style", "text-decoration: none;")
    a1.appendChild(head);
    
    var a2 = document.createElement("a");
    var input2 = document.createElement("input");
    input2.setAttribute("class", "delete");
    input2.setAttribute("type", "button");
    input2.setAttribute("name", "delete");
	input2.setAttribute("id", "delete");
	// input2.setAttribute("onclick", "delete(" + ")");
    input2.setAttribute("value", "删除");
    a2.appendChild(input2);
    
    var a3 = document.createElement("a");
    a3.setAttribute("onClick", "downloadNotice(" + d.noticeid + ", " + d.user.userid + ", '" + d.notice + "')");
    var input3 = document.createElement("input");
    input3.setAttribute("class", "download");
    input3.setAttribute("type", "button");
    input3.setAttribute("name", "download");
    input3.setAttribute("value", "下载");
    a3.appendChild(input3);
    
    var h = document.createElement("h4");
    h.setAttribute("class", "panel-title");
    h.appendChild(a1);
    h.appendChild(a2);
    h.appendChild(a3);
    
    var div1 = document.createElement("div");
    div1.setAttribute("class", "panel-heading");
    div1.appendChild(h);

    var div3 = document.createElement("div");
    div3.setAttribute("class", "panel-collapse collapse in");
    div3.setAttribute("id", "collapse-" + id);
    div3.appendChild(body);

    var div4 = document.createElement("div");
    div4.setAttribute("class", "panel panel-default");
    div4.appendChild(div1);
    div4.appendChild(div3);

    return div4;
}

function downloadNotice(noticeid, userid, notice){
	console.log(noticeid, userid, notice)
	var a = document.createElement('a');
	// a.href = url + "/notices/downloadNotice?noticeid=" + noticeid;
    a.href = fileServer + "/downloadZip?path=noticedata/" + userid + "/" + noticeid + "&name=" + notice;
	$("body").append(a); 
	a.click();
	$(a).remove();
}

function createPanelBody(table, data){
	var div  = document.createElement("div");
	div.setAttribute("class", "panel-body");
	
	var p1 = document. createElement("p");
	p1.setAttribute("class", "time");
	p1.innerHTML = "时间";

	var noticeform = document.createElement("form");
	noticeform.setAttribute("id", "notice" + data.noticeid);

	var inputnotice = document. createElement("input");
	inputnotice.setAttribute("name", "notice");
	inputnotice.setAttribute("type", "text");
	inputnotice.setAttribute("value", data.notice);
	inputnotice.setAttribute("style", "display:none;");
	var inputtype = document. createElement("input");
	inputtype.setAttribute("name", "type");
	inputtype.setAttribute("type", "text");
	inputtype.setAttribute("value", data.type);
	inputtype.setAttribute("style", "display:none;");
	var inputnoticeid = document. createElement("input");
	inputnoticeid.setAttribute("type", "text");
	inputnoticeid.setAttribute("name", "noticeid");
	inputnoticeid.setAttribute("value", data.noticeid);
	inputnoticeid.setAttribute("style", "display:none;");

	var startDate = document. createElement("input");
	startDate.setAttribute("type", "date");
	startDate.setAttribute("name", "startdate");
	startDate.setAttribute("value", data.starttime.split(" ")[0]);
	startDate.setAttribute("class", "start_time");
	
	var startTime = document. createElement("input");
	startTime.setAttribute("type", "time");
	startTime.setAttribute("name", "starttime");
	startTime.setAttribute("value", data.starttime.split(" ")[1].substring(0, 5));
	startTime.setAttribute("class", "start_time");
	
	var deadlineDate = document. createElement("input");;
	deadlineDate.setAttribute("type", "date");
	deadlineDate.setAttribute("name", "deadlinedate");
	deadlineDate.setAttribute("value", data.deadline.split(" ")[0]);
	deadlineDate.setAttribute("class", "start_time");
	
	var deadlineTime = document. createElement("input");;
	deadlineTime.setAttribute("type", "time");
	deadlineTime.setAttribute("name", "deadlinetime");
	deadlineTime.setAttribute("value", data.deadline.split(" ")[1].substring(0, 5));
	deadlineTime.setAttribute("class", "start_time");

	var p2 = document. createElement("p");
	p2.setAttribute("class", "line");
	p2.innerHTML = "--";
	
	var group1Div = document.createElement("div");
	group1Div.setAttribute("class", "group1 group");
	group1Div.appendChild(inputtype);
	group1Div.appendChild(inputnotice);
	group1Div.appendChild(inputnoticeid);
	group1Div.appendChild(p1);
	group1Div.appendChild(startDate);
	group1Div.appendChild(startTime);
	group1Div.appendChild(p2);
	group1Div.appendChild(deadlineDate);
	group1Div.appendChild(deadlineTime);

	var p3 = document. createElement("p");
	p3.setAttribute("class", "des");
	p3.innerHTML = "描述"
	var inputDescrib = document. createElement("input");
	inputDescrib.setAttribute("class", "describ");
	inputDescrib.setAttribute("value", data.description);
	inputDescrib.setAttribute("name", "description");
	
	var group2Div = document.createElement("div");
	
	group2Div.setAttribute("class", "group2 group");
	group2Div.appendChild(p3);
	
	group2Div.appendChild(inputDescrib);
	
	var inputSave = document. createElement("input");

	inputSave.setAttribute("class", "save");
	inputSave.setAttribute("value", "保存");
	inputSave.setAttribute("style", "text-align: center;")
	inputSave.setAttribute("onclick", "updatenotice(" + data.noticeid + ")");
	var group3Div = document.createElement("div");
	group3Div.setAttribute("class", "group3 group");
	group3Div.appendChild(inputSave);
	
	var group4Div = document.createElement("div");
	group4Div.setAttribute("class", "person_num")
	group4Div.appendChild(table);
	
	noticeform.appendChild(group1Div);
	noticeform.appendChild(group2Div);
	noticeform.appendChild(group3Div);
	noticeform.appendChild(group4Div);

	div.appendChild(noticeform);
	return div;
}

function createTable(d){
	var table = document.createElement("table");
	table.setAttribute("class", "table");
	var tr = table.insertRow(0);
	var th = document.createElement("th");
	tr.appendChild(th);
	th.setAttribute("colspan", "9");
	var tn = document.createTextNode(d.notice);
	var p = document.createElement("p");
	p.setAttribute("class", "tip");
	p.innerHTML = "提交人数 " + d.files.length + "人";
	th.appendChild(tn);
	th.appendChild(p);
	if(d.files == null){

	}
	else{
		tr = table.insertRow();
		var c = 0;
		td = tr.insertCell(c++);
		td.innerHTML = "编号";
		td.setAttribute("class", "num");
		td = tr.insertCell(c++);
		td.innerHTML = "学号";
		td = tr.insertCell(c++);
		td.innerHTML = "姓名";
		td = tr.insertCell(c++);
		td.innerHTML = "编号";
		td.setAttribute("class", "num");
		td = tr.insertCell(c++);
		td.innerHTML = "学号";
		td = tr.insertCell(c++);
		td.innerHTML = "姓名";
		td = tr.insertCell(c++);
		td.innerHTML = "编号";
		td.setAttribute("class", "num");
		td = tr.insertCell(c++);
		td.innerHTML = "学号";
		td = tr.insertCell(c++);
		td.innerHTML = "姓名";
		c = 0;
		for(var i = 0; i < d.files.length; i++){
			var tr;
			if(i % 3 == 0){
				tr = table.insertRow();
				c = 0;
			}
			var td = tr.insertCell(c++);
			td.innerHTML = i + 1;
			td.setAttribute("class", "num");
			td = tr.insertCell(c++);
			td.innerHTML = d.files[i].user.name;
			td = tr.insertCell(c++);
			td.innerHTML = d.files[i].user.studentnumber;
		}
		
	}
	return table;
}

function getMyNotice(){
	$.ajax({
		url:url + "/notices/getMyNotice",
		type:"post",
		async:false,
		datatype:"text",
		data:{},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			if(data.status == 0){
				alert("请先登陆");
				window.location.href = "login.html";
			}
			console.log(data);
			if(data.status == 1){
				for(var i = 0; i < data.data.length; i++){
					document.getElementById("accordion").appendChild(createCollapseDiv(i + 1, document.createTextNode(data.data[i].notice), createPanelBody(createTable(data.data[i]), data.data[i]), data.data[i]));
				}
				//console.log(data);	
			}
			else{
				//console.log(data);	
			}
			
		},
		error:function(data){
			//console.log(data);
			alert("系统异常");
		}
	})
}

function buildNotice(){
	var form = document.getElementById("notice_form");
	var inputs = form.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++){
		if(inputs[i].value == ""){
			inputs[i].focus();
			return ;
		}
	}
	//console.log($("#notice_form").serialize());
	$.ajax({
		url:url + "/notices/newNotice",
		type:"post",
		async:false,
		data:$("#notice_form").serialize(),
		crossDomain:true,
		xhrFields: {  withCredentials: true  },
		success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			//console.log(data);
			if(data.status == 1){
				alert("发布成功");
				document.getElementById("cancel").click();
			}
			else{
				alert("发布失败");
			}
		},
		error:function(data){
			//console.log(data);
		}
	});
}

function updatenotice(noticeid){
	var form = document.getElementById("notice" + noticeid);
	var inputs = form.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++){
		if(inputs[i].value == ""){
			inputs[i].focus();
			return ;
		}
	}
	$.ajax({
		url:url + "/notices/updateNotice",
		type:"post",
		async:false,
		data:$("#notice" + noticeid).serialize(),
		crossDomain:true,
		xhrFields: {  withCredentials: true  },
		success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			//console.log(data);
			if(data.status == 1){
				alert("更新成功");
				document.getElementById("cancel").click();
			}
			else{
				alert("更新失败");
			}
		},
		error:function(data){
			//console.log(data);
		}
	});
}