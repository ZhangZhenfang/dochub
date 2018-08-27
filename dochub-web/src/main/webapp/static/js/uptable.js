
function createCollapseDiv(id, head, table, d){
	console.log(table);
	var a1 = document.createElement("a");
    a1.setAttribute("data-toggle", "collapse");
    a1.setAttribute("href", "#collapse-" + id); 
    a1.setAttribute("style", "text-decoration: none;")
    a1.appendChild(head);
    
    var a2 = document.createElement("a");
    var input1 = document.createElement("input");
    input1.setAttribute("class", "delete");
    input1.setAttribute("type", "button");
    input1.setAttribute("name", "delete");
    input1.setAttribute("id", "delete");
    input1.setAttribute("value", "删除");
    a2.appendChild(input1);
    
    var a3 = document.createElement("a");
    a3.setAttribute("onClick", "downloadExcel(" + d.excelid + ")");
    var input2 = document.createElement("input");
    input2.setAttribute("class", "download");
    input2.setAttribute("type", "button");
    input2.setAttribute("name", "download");
    input2.setAttribute("value", "下载");
    a3.appendChild(input2);
    
    var h = document.createElement("h4");
    h.setAttribute("class", "panel-title");
    h.appendChild(a1);
    h.appendChild(a2);
    h.appendChild(a3);
    var div1 = document.createElement("div");
    div1.setAttribute("class", "panel-heading");
    div1.appendChild(h);

    var div2 = document.createElement("div");
    div2.setAttribute("class", "panel-collapse collapse in");
    div2.setAttribute("id", "collapse-" + id);
    var div3 = document.createElement("div");
    div3.setAttribute("class", "panel-body");
    var div4 = document.createElement("div");
    div4.setAttribute("class", "person_num");
    div4.appendChild(table);
    div3.appendChild(div4);
    div2.appendChild(div3);

    var div5 = document.createElement("div");
    div5.setAttribute("class", "panel panel-default");
    div5.appendChild(div1);
    div5.appendChild(div2);

    return div5;
}

function downloadExcel(excelid){
	var a = document.createElement('a');
	a.href = url + "/excels/downloadExcel?excelid=" + excelid;
	$("body").append(a); 
	a.click();
	$(a).remove();
}

function createTable(excelid){
	var div = document.createElement("div");
	$.ajax({
		url:url + "/excels/excelToTable",
		type:"post",
		async:false,
		datatype:"text",
		data:{"excelId":excelid},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			console.log(data);
			if(data.status == 0){
				alert("请先登陆");
				window.location.href = "login.html";
			}
			div.innerHTML = data.data.split("::")[0];
		},
		error:function(data){
			console.log(data);
		}
	})
	return div;
}

function getMyExcels(){
	$.ajax({
		url:url + "/excels/getMyExcels", //getExcelFromFriends
		type:"post",
		async:false,
		datatype:"text",
		data:{},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			if(data.status == 1){
				for(var i = 0; i < data.data.length; i++){
					document.getElementById("accordion").appendChild(createCollapseDiv(i + 1, document.createTextNode(data.data[i].filename), createTable(data.data[i].excelid), data.data[i]));
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

function buildTable(){
	var form = document.getElementById("table_form");
	var inputs = form.getElementsByTagName("input");
	for(var i = 0; i < inputs.length; i++){
		if(inputs[i].value == ""){
			inputs[i].focus();
			return ;
		}
	}
	console.log($("#table_form").serialize());
	$.ajax({
		url:url + "/excels/uploadExcel",
		type:"post",
		async:false,
		data:new FormData(document.forms.namedItem("table_form")),
		
		contentType: false,
		processData: false,
		crossDomain:true,
		xhrFields: {  withCredentials: true  },
		success:function(data){
			console.log(data);
			if(data.status == 0){
				alert("请先登陆");
				window.location.href = "login.html";
			}
			if(data.status == 1){
				alert("创建成功");
				var cancel = document.getElementById("cancel_btn");
				cancel.click();
				window.location.reload();
			}
			else{
				alert("创建失败");
			}
		},
		error:function(data){console.log(data)}
	});
}
