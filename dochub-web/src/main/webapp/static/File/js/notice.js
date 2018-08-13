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
    input2.setAttribute("value", "删除");
    a2.appendChild(input2);
    
    var a3 = document.createElement("a");
    a3.setAttribute("onClick", "downloadNotice(" + d.noticeid + ")");
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

function downloadNotice(noticeid){
	var a = document.createElement('a');
	a.href = url + "/notices/downloadNotice?noticeid=" + noticeid;
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
	var inputTime = document. createElement("input");
	inputTime.setAttribute("class", "start_time");
	inputTime.setAttribute("value", data.starttime);
	var p2 = document. createElement("p");
	p2.setAttribute("class", "line");
	p2.innerHTML = "--";
	var inputTime1 = document. createElement("input");
	inputTime1.setAttribute("class", "end_time");
	inputTime1.setAttribute("value", data.deadline);
	var group1Div = document.createElement("div");
	group1Div.setAttribute("class", "group1 group");
	group1Div.appendChild(p1);
	group1Div.appendChild(inputTime);
	group1Div.appendChild(p2);
	group1Div.appendChild(inputTime1);
	div.appendChild(group1Div);
	
	
	var p3 = document. createElement("p");
	p3.setAttribute("class", "des");
	p3.innerHTML = "描述"
	var inputDescrib = document. createElement("input");
	inputDescrib.setAttribute("class", "describ");
	inputDescrib.setAttribute("value", data.description);
	var group2Div = document.createElement("div");
	group2Div.setAttribute("class", "group2 group");
	group2Div.appendChild(p3);
	group2Div.appendChild(inputDescrib);
	div.appendChild(group2Div);
	
	var inputSave = document. createElement("input");
	inputSave.setAttribute("class", "save");
	inputSave.setAttribute("value", "保存");
	inputSave.setAttribute("style", "text-align: center;")
	var group3Div = document.createElement("div");
	group3Div.setAttribute("class", "group3 group");
	group3Div.appendChild(inputSave);
	div.appendChild(group3Div);
	
	var group4Div = document.createElement("div");
	group4Div.setAttribute("class", "person_num")
	group4Div.appendChild(table);
	
	div.appendChild(group4Div);
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
	$.ajax({
		url:url + "/files/getFilesByNoticeid",
		type:"post",
		async:false,
		datatype:"text",
		data:{"noticeid":d.noticeid},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			//console.log(data);
			var p = document.createElement("p");
			p.setAttribute("class", "tip");
			p.innerHTML = "提交人数 " + data.data.length + "人";
			th.appendChild(tn);
			th.appendChild(p);
			
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
			for(var i = 0; i < data.data.length; i++){
				var tr;
				if(i % 3 == 0){
					tr = table.insertRow();
					c = 0;
				}
				var td = tr.insertCell(c++);
				td.innerHTML = i + 1;
				td.setAttribute("class", "num");
				td = tr.insertCell(c++);
				td.innerHTML = data.data[i].user.name;
				td = tr.insertCell(c++);
				td.innerHTML = data.data[i].user.studentnumber;
			}
		},
		error:function(data){
			//console.log(data);
		}
	})
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
				alert("清先登陆");
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

