// var url = "http://localhost:8080/dochub"
// var url = "http://www.the15373.com/dochub"

function createCollapseDiv(id, head, body){
	var a = document.createElement("a");
    a.setAttribute("data-toggle", "collapse");
    a.setAttribute("href", "#collapse-" + id); 
    a.setAttribute("style", "text-decoration: none;");
    a.appendChild(head);
    var h = document.createElement("h4");
    h.setAttribute("class", "panel-title");
    h.appendChild(a);
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

function createBody(data){
	var p1 = document.createElement("p");
	p1.innerHTML = "发布人：" + data.user.name;
	var group1Div = document.createElement("div");
	group1Div.setAttribute("class", "group1 group");
	group1Div.appendChild(p1);
	
	var p2 = document. createElement("p");
	p2.innerHTML = "时间：";
	var inputTime = document. createElement("input");
	inputTime.setAttribute("class", "start_time");
	inputTime.setAttribute("value", data.starttime);
	inputTime.setAttribute("readonly",true);
	var p3 = document. createElement("p");
	p3.innerHTML = "--";
	var inputTime1 = document. createElement("input");
	inputTime1.setAttribute("class", "end_time");
	inputTime1.setAttribute("value", data.deadline);
	inputTime1.setAttribute("readonly",true);
	var group2Div = document.createElement("div");
	group2Div.setAttribute("class", "group2 group");
	group2Div.appendChild(p2);
	group2Div.appendChild(inputTime);
	group2Div.appendChild(p3);
	group2Div.appendChild(inputTime1);
	
	var p4 = document. createElement("p");
	p4.innerHTML = "描述："
	var inputDescrib = document. createElement("input");
	inputDescrib.setAttribute("class", "describ");
	inputDescrib.setAttribute("value", data.description);
	inputDescrib.setAttribute("readonly",true);
	var group3Div = document.createElement("div");
	group3Div.setAttribute("class", "group3 group");
	group3Div.appendChild(p4);
	group3Div.appendChild(inputDescrib);

	var p5 = document.createElement("p");
	var a = document.createElement("a");
	if(data.mysubmt == null){
		a.innerHTML = "没有提交文件";
	}
	else{
		a.innerHTML = data.mysubmt.filename;
		a.setAttribute("onClick", "downloadfile('" + data.mysubmt.md5 + "')");
	}
	// getFileByNoticeAndUserid(data.noticeid, a);
	a.setAttribute("style", "text-decoration: none; color: #000000;");
	p5.innerHTML = "我的提交：";
	p5.appendChild(a);
	var inputFile = document.createElement("input");
	inputFile.setAttribute("class", "filedata");
	inputFile.setAttribute("type", "file");
	inputFile.setAttribute("id", data.noticeid);
    inputFile.setAttribute("userid", data.user.userid);
	var inputSave = document.createElement("input");
	inputSave.setAttribute("class", "save");
	inputSave.setAttribute("type", "button");
	inputSave.setAttribute("value", "提交");
	inputSave.setAttribute("onClick", "uploadFile(this, " + data.noticeid + ")");
	var group4Div = document.createElement("div");
	group4Div.setAttribute("class", "group4 group");
	group4Div.appendChild(p5);
	group4Div.appendChild(inputFile);
	group4Div.appendChild(inputSave);
	
    var div2 = document.createElement("div");
    div2.setAttribute("class", "panel-body");
    div2.appendChild(group1Div);
    div2.appendChild(group2Div);
    div2.appendChild(group3Div);
    div2.appendChild(group4Div);
    
    return div2;
}

function uploadFile(node, id){
	var file = node.previousElementSibling;
	console.log(file.value);
	if(file.value == ""){
		alert("请选择文件");
		file.focus();
		return ;
	}else{
        if(file.files[0].size > 20971520 * 5){
            alert("文件不能超过100MB");
            return ;
        }
        console.log(file.getAttribute("userid"));
        upfile('', file.getAttribute("id"), file.getAttribute("userid"), id, finalFunction);
	}
}
function finalFunction(fileName, md5, noticeid) {
    $.ajax({
	url:url + "/files/uploadFile",
	data:{"fileName" : fileName, "md5" : md5, "noticeid" : noticeid},
	type:"post",
	async:false,
	xhrFields: {  withCredentials: true  },
	success:function(data){
		console.log(data);
		if(data.status == 0){
			alert("清先登陆");
			window.location.href = "login.html";
		}
		if(data.status == 1){
			alert("上传成功");
			// document.getElementById("waitmodalcancel").click();

			window.location.reload();
		}
		else{
			alert("上传失败");
		}

	},
	error:function(data){
		console.log(data)
	}
});
}
function getNoticesFromFriends(){
	$.ajax({
		url:url + "/notices/getNoticesFromFriends",
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
			if(data.status == 1){
				var n = data.data.length;
				var footerDiv = document.getElementsByClassName("footer");
				if(n < 3){
					footerDiv[0].setAttribute("style", "margin-top: 300px;")
				}
				for(var i = 0; i < n; i++){
					document.getElementById("accordion").appendChild(createCollapseDiv(i + 1, document.createTextNode(data.data[i].notice), createBody(data.data[i])));
				}
				console.log(data);	
			}
			else{
				console.log(data);	
			}
			
		},
		error:function(data){
			console.log(data);
			alert("系统异常");
		}
	})
}


function getFileByNoticeAndUserid(id, a){
	$.ajax({
		url:url + "/files/getFileByNoticeidAndUserid",
		data:{"noticeid":id},
		type:"post",
		async:false,
		crossDomain:true,
		datatype:"text",
        xhrFields: {  withCredentials: true  },
        success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
        	if(data.data == null){
        		a.innerHTML = "没有提交文件";
        	}else{
        		a.innerHTML = data.data.filename;
				a.setAttribute("onClick", "downloadfile(" + data.data.fileid + ")");
        	}
        },
        error:function(data){
        	console.log(data);
        }
	});
}

function downloadfile(fileid){
	// alert(fileid);
	console.log("download " + fileid);
	var a = document.createElement('a');
	// a.href = url + "/files/downloadFile?fileid=" + fileid;
    a.href = fileServer + "/downloadFile?md5=" + fileid;
	$("body").append(a); 
	a.click();
	$(a).remove();
}
