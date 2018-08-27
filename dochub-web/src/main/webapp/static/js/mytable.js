// var url = "http://localhost:8080/dochub"


function createCollapseDiv(id, head, table){
	var a1 = document.createElement("a");
    a1.setAttribute("data-toggle", "collapse");
    a1.setAttribute("href", "#collapse-" + id); 
    a1.setAttribute("style", "text-decoration: none;")
    a1.appendChild(head);
    
    var h = document.createElement("h4");
    h.setAttribute("class", "panel-title");
    h.appendChild(a1);
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
    var inputDiv = document.createElement("input");
    inputDiv.setAttribute("class", "submit");
    inputDiv.setAttribute("name", "submit2");
    inputDiv.setAttribute("type", "button");
	inputDiv.setAttribute("value", "提交");
	inputDiv.setAttribute("onClick", "subrecord(" + id + ")");
    div4.appendChild(table);
    div4.appendChild(inputDiv);
    div3.appendChild(div4);
    div2.appendChild(div3);

    var div5 = document.createElement("div");
    div5.setAttribute("class", "panel panel-default");
    div5.appendChild(div1);
    div5.appendChild(div2);

    return div5;
}

function createTable(){
	
	var data = [{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	},
	{
		"name": "ad",
		"studentnumber": "asdfasd"
	}
];
	
	var table = document.createElement("table");
	table.setAttribute("class", "table");
	var tr = table.insertRow();
	var c = 0;
	var td = tr.insertCell(c++);
	td.innerHTML = "编号";
	td = tr.insertCell(c++);
	td.innerHTML = "姓名";
	td = tr.insertCell(c++);
	td.innerHTML = "学号";
	
	for(var i = 0; i < data.length; i++){
		var tr = table.insertRow();
		var c = 0;
		var td = tr.insertCell(c++);
		td.innerHTML = i + 1;
		td = tr.insertCell(c++);
		td.innerHTML = data[i].name;
		td = tr.insertCell(c++);
		td.innerHTML = data[i].studentnumber;
	}
	
	return table;
}

function getExcelFromFriends(){
	$.ajax({
		url:url + "/excels/getExcelFromFriends", 
		type:"post",
		async:false,
		datatype:"text",
		data:{},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			console.log(data);
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			if(data.status == 1){
				for(var i = 0; i < data.data.length; i++){
					var div = document.createElement("div");
					getExcelHead(data.data[i].head, div, data.data[i].excelid);

					if(data.data[i].mysubmit == null){
						
					}
					else{
						var array = div.getElementsByTagName("input");
						// console.log(t.getElementsByTagName("input")[0]);
						// console.log(array);
						var record = JSON.parse(data.data[i].mysubmit.record);
						for(var j = 0; j < array.length; j++){
							array[j].setAttribute("value", record[j]);
							// console.log(record[i]);
						}
					}
					// alert("adsfasdf");
					var div1 = document.getElementById("accordion");
					var collapseDiv = createCollapseDiv(data.data[i].excelid, document.createTextNode(data.data[i].filename), div);
					div1.appendChild(collapseDiv);
					
				}
				// console.log(data);	
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

function getExcelHead(head, d, id){
	var tablestr = head.split("::")[0];
	d.innerHTML = tablestr;
	var table = d.getElementsByTagName("table")[0];
	table.setAttribute("id", "table" + id);
	var n = head.split("::")[2];
	var row = table.insertRow();
	for(var i = 0; i < n; i++){
		var cell = row.insertCell(i);
		var input = document.createElement("input");
		input.setAttribute("type", "text");
		input.setAttribute("class", "table-input" + id);
		cell.appendChild(input);
	}
}

function fillRecord(excelid, d){
	
	$.ajax({
		url:url + "/excelrecords/getExcelrecordByUseridAndExcelId",
		type:"post",
		async:false,
		data:{"excelId":excelid},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			if(data.status == 1){
				var record = JSON.parse(data.data.record);
				// console.log(record);
				var array = d.getElementsByTagName("input");
				// console.log(t.getElementsByTagName("input")[0]);
				// console.log(array);
				for(var i = 0; i < array.length; i++){
					array[i].setAttribute("value", record[i]);
					// console.log(record[i]);
				}
			}
		},
		error:function(data){console.log(data);return data;}
	});
}

function subrecord(id){
	alert(id);
	var table = document.getElementById("table" + id);
	var inputs = table.getElementsByTagName("input");
	var str = "";
	// console.log(i);
	var text = '';
    var flag = 0;
    for(var i = 0; i < inputs.length; i++){
        text += '"' + i + '":'
        text += '"' + inputs[i].value +  '"';
        if(inputs[i].value.trim() != ""){
            flag += 1;
        }
        if(i <= inputs.length - 2){
            text += ',';
        }
    }
    if(flag == 0){
        inputs[0].focus();
        return ;
	}
	var dat = '{' + text + '}';
	text = '{' + text + '}';
    text = '"excelId":"' + id + '","record":' + text;
    text = '{' + text + '}';
	console.log(JSON.stringify(text));
	$.ajax({
		url:url + "/excelrecords/insertOrUpdateExcelrecord",
		type:"post",
		data:{"excelId":id, "record" : dat},
		async:false,
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			if(data.status == 1){
				alert("提交成功");
			} 
			else{
				alert("提交失败");
			}
		},
		error:function(data){alert(data)}

	});
}