 var url = 'http://localhost:8080/the15373v2';

function auth(account, password){
    var loginForm = document.getElementById("loginForm");
    var inputs = loginForm.getElementsByTagName("input");
    var data = {'account':inputs[0].value, 'password':inputs[1].value};
    ajax(url + "/Auth", data, function(data){
        var json =  eval('(' + data + ')'); 
        if(json.status == 3){
            alert("用户名和密码不匹配！");
            return ;
        }
        else if(json.status == 4){
            alert("没有该用户！");
            return ;
        }
        else if(json.status == 2){
            if(confirm("用户已经在线，强制下线?")){
                $.ajax(url + "/Logout", {
                    type:"POST",
                    async:false,
                    data:{ "userid" : json.userid },
                    crossDomain:true,
                    datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
                    xhrFields: {  withCredentials: true  },             
                    success:function(data,status,xhr){
                        var json =  eval('(' + data + ')'); 
                        if(json.status == '1'){
                            auth(account, password);
                        }
                        else{
                            alert("下线失败！");
                            // console.log('failed');
                        }
                 }});
            }
            return ;
        }
        else if(json.status == 5){
            alert(json.errors);
            return ;
        }
        document.getElementById("user").innerHTML = "";
        var a = document.createElement("a");
        a.setAttribute("id", "usercenter");
        a.innerHTML = json.username + ",注销";
        a.setAttribute("onclick", "logout(" + json.userid + ")");
        document.getElementById("user").appendChild(a);
        document.getElementById("loginFormCloseBtn").click();
    }, function(data){alert(data)});
}
function getNoticeFromMyFriend(){

    ajax(url + "/GetNoticeFromFriends", null, 
        function(data){
        var json =  eval('(' + data + ')'); 
        // console.log(json);
        if(json.status == 0){
            alert("请先登录！");
            return ;
        }
        if(json.status != 1){
            alert(data.errors);
            return ;
        }
        document.getElementById("upfile").getElementsByClassName("panel-group")[0].innerHTML = "";
        var notices = json.notices;
        var notice;
        for(var i = 0; i < notices.length; i++){
            notice = notices[i];
            var head = document.createTextNode(notice.notice);
            
            var body = document.createElement("div");
            body.appendChild(createNotice(notice, 0));
            var input = document.createElement("input");
            input.setAttribute("type", "file");
            input.setAttribute("name", notice.noticeid);
            input.setAttribute("id", "file" + notice.noticeid);
            var btn = document.createElement("button");
            btn.setAttribute("class", "uploadfilebtn");
            btn.setAttribute("onclick", "uploadfile(" + notice.noticeid + ")");

            if(notice.mysubmit == "未提交"){
                btn.innerHTML = "提交";
            }
            else{
                btn.innerHTML = "重新提交";
            }
            body.appendChild(input);
            body.appendChild(btn);

            body.setAttribute("id", notice.noticeid);
            addCollapseDiv(document.getElementById("upfile").getElementsByClassName("panel-group")[0], "upfilecollapse" + i, head, body);
        }
    }, function(data){alert(data);});
}
function getMyNotice(){
    ajax(url + "/GetNotice",
    null,
    function(data){
        var json =  eval('(' + data + ')'); 
        // console.log(json);
        if(json.status == 0){
            alert("请先登录！");
            return ;
        }
        if(json.status != 1){
            alert(data.errors);
            return ;
        }
        var notices = json.notices;
        document.getElementById("mynotice").getElementsByClassName("panel-group")[0].innerHTML = "";
        for(var i = 0; i < notices.length; i++){
            notice = notices[i];
            var head = document.createTextNode(notice.notice);
            var body = document.createElement("div");
            body.appendChild(notice2form(notice));
            var btn = document.createElement("button");
            btn.setAttribute("onclick", "editNotice(this)");
            btn.innerHTML = "编辑";
            body.appendChild(btn);
            
            var a = document.createElement("a");
            a.innerHTML = "下载";
            a.setAttribute("onclick","downloadNotice(" + notice.noticeid + ")");
            body.appendChild(a);
            var delbtn = document.createElement("button");
            delbtn.setAttribute("onclick", "deleteNotice(" + notice.noticeid + ")");
            delbtn.innerHTML = "删除";
            body.appendChild(delbtn);
            var submitedstudents = notice.submitedstudents;
            body.appendChild(json2ul(submitedstudents));
            addCollapseDiv(document.getElementById("mynotice").getElementsByClassName("panel-group")[0], "mynoticecollapse" + i, head, body, 1);
        }
    },      
    function(data){
        var json =  eval('(' + data + ')'); 
    }
    );
}

function getMyExcelFiles(){
    ajax(url + "/GetMyExcelfiles", null,
        function(data){
            var json =  eval('(' + data + ')'); 
            var data = json.data;
            if(json.status == 0){
                alert("请先登录！");
                return ;
            }
            if(json.status != 1){
                alert(data.errors);
                return ;
            }
            document.getElementById("myexcel").getElementsByClassName("panel-group")[0].innerHTML = "";
            for(var i = 0; i < data.length; i++){
                var e = data[i];
                var head = document.createTextNode(e.title);
                var body = document.createElement("div");
                var a = document.createElement("a");
                var node = document.createTextNode(e.title);
                a.appendChild(node);
                a.setAttribute("onclick","downloadExcel(" + e.id + ")");
                body.appendChild(a);
                var btn = document.createElement("button");
                btn.setAttribute("onclick", "deleteExcelfile(" + e.id + ")");
                btn.innerHTML = "删除";
                body.appendChild(btn);
                addCollapseDiv(document.getElementById("myexcel").getElementsByClassName("panel-group")[0], "excelfilecollapse" + i, head, body);
            }
        },
        function(data){
            console.log(data);
        }
    );
}

function getExcelFromFriends(){
    ajax(url + "/GetExcelFilesFromFriends", null,
        function(data){
            var json = eval('(' + data + ')'); 
            if(json.status == 0){
                alert("请先登录！");
                return ;
            }
            else if(json.status == 1){
                document.getElementById("excelfilelist").getElementsByClassName("panel-group")[0].innerHTML = "";
                var data = json.data;
                
                for(var i = 0; i < data.length; i++){
                    
                    // console.log(data[i].excelfileid);
                    var head = document.createTextNode(data[i].title);
                    var body = getExcelHead(data[i].excelfileid);
                    // console.log(head);
                    // console.log(body);
                    addCollapseDiv(document.getElementById("excelfilelist").getElementsByClassName("panel-group")[0], "excelfilelistcollapse" + i, head, body);
                    // getrecord(id);
                }
            }
        },
        function(data){
            alert(data);
        }
    );
}

function newNotice(){
    var form = document.getElementById("newNoticeForm");
    var inputs = form.getElementsByTagName("input");
    for(var i = 0; i < inputs.length; i++){
        if(inputs[i].value.trim() == ""){
            inputs[i].focus();
            return ;
        }
    }
    var data = {
        'notice': inputs[0].value,
        'startdate': inputs[1].value,
        'starttime': inputs[2].value,
        'deadlinedate': inputs[3].value,
        'deadlinetime': inputs[4].value,
        'description': inputs[5].value,
    };

    ajax(url + "/NewNotice", data,
        function(data){
            console.log(data);
            var json =  eval('(' + data + ')'); 
            if(json.status == 1){
                alert("发布成功！");
            }
            else{
                alert("发布失败！");
            }
            document.getElementById("newNoticeFormCloseBtn").click();
        },
        function(data){alert(data);}
    );
}

function newExcel(){
    var form = document.getElementById("newExcelForm");
    var inputs = form.getElementsByTagName("input");
    console.log(inputs);
    var formData = new FormData();
	formData.append("startdate", inputs[1].value);	
	formData.append("starttime", inputs[2].value);	
	formData.append("deadlinedate", inputs[3].value);	
	formData.append("deadlinetime", inputs[4].value);	
	formData.append("description", inputs[5].value);
	formData.append("0", inputs[0].files[0]);

	var path = inputs[0].value;
	
	if (path.trim() == "") { 
		alert("请先选择要上传的文件"); 
		return; 
    }
    
    $.ajax({
        url: url + "/NewExcelFile",
        type: "POST",
        data: formData,
        /**
        *必须false才会自动加上正确的Content-Type
        */
        contentType: false,
        /**
        * 必须false才会避开jQuery对 formdata 的默认处理
        * XMLHttpRequest会对 formdata 进行正确的处理
        */
        processData: false,
        xhrFields: {  withCredentials: true  },            
        success: function (data) {
            var json = eval('(' + data + ')'); 
            if (json.status == '1' || json.status == '2') {
                alert("上传成功！");
                document.getElementById("newExcelFormCloseBtn").click();
            }
            else{
                alert("上传失败！" + json.errors);
                for(var i = 0; i < btns.length; i++){
                    btns[i].disabled=false;
                }
            }
            $("#imgWait").hide();
            // console.log(data);
        },
        error: function () {
            alert("上传失败！");
            console.log("error!");
            $("#imgWait").hide();
        }
    });
}

function json2ul(j){

    var ul = document.createElement("ul");
    var li = document.createElement("li");
    li.innerHTML = "人数：" + j.length;
    ul.appendChild(li);
    for(var i = 0; i < j.length; i++){
        var li = document.createElement("li");
        li.innerHTML = j[i].name + j[i].studentnumber;
        ul.appendChild(li);
    }
    return ul;

}
function notice2form(notice){
    var form = document.createElement("form");
    form.setAttribute("id", "form" + notice.noticeid);
    var input1 = document.createElement("input");
    input1.setAttribute("disabled", "disabled");
    input1.setAttribute("type", "date");
    input1.setAttribute("value", notice.starttime.split(" ")[0]);
    var input2 = document.createElement("input");
    input2.setAttribute("disabled", "disabled");
    input2.setAttribute("type", "time");
    input2.setAttribute("value", notice.starttime.split(" ")[1].replace(".0", ""));

    var input3 = document.createElement("input");
    input3.setAttribute("disabled", "disabled");
    input3.setAttribute("type", "date");
    input3.setAttribute("value", notice.deadline.split(" ")[0]);
    var input4 = document.createElement("input");
    input4.setAttribute("disabled", "disabled");
    input4.setAttribute("type", "time");
    input4.setAttribute("value", notice.deadline.split(" ")[1].replace(".0", ""));
    var input5 = document.createElement("input");
    input5.setAttribute("type", "text");
    input5.setAttribute("disabled", "disabled");
    input5.setAttribute("value", notice.description);
    form.appendChild(document.createTextNode("开始时间："));
    form.appendChild(input1);
    form.appendChild(input2);
    form.appendChild(document.createElement("br"));
    form.appendChild(document.createTextNode("结束时间："));
    form.appendChild(input3);
    form.appendChild(input4);
    form.appendChild(document.createElement("br"));
    form.appendChild(document.createTextNode("描述："));
    form.appendChild(input5);

    return form;
}

function editNotice(btn){
    var form = btn.previousSibling;
    var inputs = form.getElementsByTagName("input");
    for(var i = 0; i < inputs.length; i++){
        inputs[i].removeAttribute("disabled");
    }
    btn.innerHTML = "保存";
    btn.setAttribute("onclick", "saveNotice(this)");
}
function saveNotice(btn){
    var form = btn.previousSibling;
    var inputs = form.getElementsByTagName("input");
    for(var i = 0; i < inputs.length; i++){
        inputs[i].setAttribute("disabled", "disabled");
    }
    btn.innerHTML = "编辑";
    btn.setAttribute("onclick", "editNotice(this)");
}
function onTr(){
    var hideTr = document.getElementsByClassName("hideTr");
    hideTr[0].setAttribute("class", "showTr");
}


function json2table(table, data){
    var item;
    var tbody = table.getElementsByTagName("tbody")[0];
    var childList = tbody.childNodes;
    for(var i = 1;i < childList.length; i++){
        tbody.removeChild(childList[i--]);
    }
    th = table.getElementsByTagName("th");
    // console.log(th.innerHTML);
    for(var i = 0; i < data.length; i++){
        var newTr = document.createElement("tr");
        newTr.setAttribute("id", "tr" + i + 1);
        newTr.setAttribute("onclick", "onTr(this)");
        // var newTd = newTr.insertCell();
        // newTd.innerHTML = i + 1;
        for(var j = 0; j < th.length; j++){
            var newTd = newTr.insertCell();
            newTd.innerHTML = data[i][th[j].getAttribute("name")];
        }
        tbody.appendChild(newTr);

        newTr = document.createElement("tr");
        newTr.setAttribute("class", "hideTr");
        
        var newTd = newTr.insertCell();
        newTd.innerHTML = "test";
        newTd.setAttribute("colspan", "4");
        tbody.appendChild(newTr);
    }
}

function createNoticeForm(notice){
    var form = document.createElement("form");
    form.setAttribute("id", "noticeform" + notice.noticeid);
    var input1 = document.createElement("input");
    input1.setAttribute("disabled", "disabled");
    input1.setAttribute("type", "text");
    input1.value = notice.noticeid;

    var input1 = document.createElement("input");
    input1.setAttribute("type", "text");
    input1.value = notice.noticeid;

}

function form2dl(form, dl){
    var inputs = form.getElementsByTagName("input");
    var dts = dl.getElementsByTagName("dt");
    var dds = dl.getElementsByTagName("dd");
    dds[1].innerHTML = inputs[0].value + inputs[1].value;
    dds[2].innerHTML = inputs[2].value + inputs[3].value;
    dds[3].innerHTML = inputs[4].value;
    console.log(inputs[4].value);
}

function dl2form(dl){
    console.log(dl);
    var form = document.createElement("form");
    form.appendChild(document.createTextNode("开始时间："));
    var input1 = document.createElement("input");
    input1.setAttribute("type", "date");
    input1.setAttribute("name", "startdate");
    form.appendChild(input1);
    var input2 = document.createElement("input");
    input2.setAttribute("type", "time");
    input2.setAttribute("name", "starttime");
    form.appendChild(input2);
    form.appendChild(document.createElement("br"));
    form.appendChild(document.createTextNode("结束时间："));
    var input3 = document.createElement("input");
    input3.setAttribute("type", "date");
    input3.setAttribute("name", "deadlinedate");
    form.appendChild(input3);
    var input4 = document.createElement("input");
    input4.setAttribute("type", "time");
    input4.setAttribute("name", "deadlinetime");
    form.appendChild(input4);
    form.appendChild(document.createElement("br"));
    form.appendChild(document.createTextNode("注意事项："));
    var input5 = document.createElement("input");
    input5.setAttribute("name", "description");
    input5.setAttribute("type", "text");
    input5.setAttribute("value", dl.childNodes[10].innerHTML);
    form.appendChild(input5);
    form.setAttribute("class", "hide");
    return form;
}

function ajax(url, data, success, error){
    $.ajax(url, {
		type:"POST",
		async:false,
		data:data,
		crossDomain:true,
		datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
        success: function(data,status,xhr){
            if(data == "0"){
                alert("Please login.");
                return ;
            }
            success(data);
        },
        error: function(){
            error(data);
        }
	 });
}
function ajaxwithfile(url, data, success, error){
    $.ajax(url, {
		type:"POST",
		async:true,
        data:data,
        processData: false,
        contentType: false,
		crossDomain:true,
		datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
        success: function(data,status,xhr){
            if(data == "0"){
                alert("Please login.");
                return ;
            }
            success(data);
        },
        error: function(){
            error(data);
        }
	 });
}
function createNotice(notice, flag){

    var dl = document.createElement("dl");
    var dt1 = document.createElement("dt");
    dt1.innerHTML = "发布人：";
    var dd1 = document.createElement("dd");
    dd1.innerHTML = notice.username;

    var dt2 = document.createElement("dt");
    dt2.innerHTML = "开始时间：";
    var dd2 = document.createElement("dd");
    dd2.innerHTML = notice.starttime;

    var dt3 = document.createElement("dt");
    dt3.innerHTML = "结束时间：";
    var dd3 = document.createElement("dd");
    dd3.innerHTML = notice.deadline;

    var dt4 = document.createElement("dt");
    dt4.innerHTML = "注意事项：";
    var dd4 = document.createElement("dd");
    dd4.innerHTML = notice.description;
    dl.appendChild(dt1);
    dl.appendChild(dd1);
    dl.appendChild(document.createElement("br"));
    dl.appendChild(dt2);
    dl.appendChild(dd2);
    dl.appendChild(document.createElement("br"));
    dl.appendChild(dt3);
    dl.appendChild(dd3);
    dl.appendChild(document.createElement("br"));
    dl.appendChild(dt4);
    dl.appendChild(dd4);
    dl.appendChild(document.createElement("br"));
    if(flag == 0){
        var dt5 = document.createElement("dt");
        dt5.innerHTML = "我的提交：";
        var dd5 = document.createElement("dd");
        if(notice.mysubmit == "未提交"){
            dd5.innerHTML = "未提交";
        }
        else{
            var a = document.createElement("a");
            a.appendChild(document.createTextNode(notice.mysubmit));
            a.setAttribute("onclick", "downloadfile(" + notice.mysubmitid + ")");
            dd5.appendChild(a);
        }
        dl.appendChild(dt5);
        dl.appendChild(dd5);
    }
    return dl;
}
function addCollapseDiv(div, id, head, body){
    var a = document.createElement("a");
    a.setAttribute("data-toggle", "collapse");
    a.setAttribute("href", "#" + id); 
    a.appendChild(head);
    
    var h = document.createElement("h4");
    h.setAttribute("class", "panel-title");
    h.appendChild(a);
    
    var div1 = document.createElement("div");
    div1.setAttribute("class", "panel-heading");
    div1.appendChild(h);

    var div2 = document.createElement("div");
    div2.setAttribute("class", "panel-body");
    div2.appendChild(body);

    var div3 = document.createElement("div");
    div3.setAttribute("class", "panel-collapse collapse");
    div3.setAttribute("id", id);
    div3.appendChild(div2);

    var div4 = document.createElement("div");
    div4.setAttribute("class", "panel panel-success");
    div4.appendChild(div1);
    div4.appendChild(div3);

    div.appendChild(div4); 
}
function downloadfile(fileid){
	// alert(fileid);
	var a = document.createElement('a');
	a.href = url + '/DownloadFile?fileid=' + fileid;
	$("body").append(a); 
	a.click();
	$(a).remove();
}
function uploadfile(noticeid){
	var btns = document.getElementsByClassName("uploadfilebtn");
	for(var i = 0; i < btns.length; i++){
		btns[i].disabled=true;
	}
	var path = document.getElementById("file" + noticeid).value;
	if ($.trim(path) == "") { 
		alert("请先选择要上传的文件"); 
		return; 
	}
	$("#imgWait").show();

	var formData = new FormData();

    formData.append(noticeid, document.getElementById("file" + noticeid).files[0]); 
    // ajax("http://www.the15373.com/Upload", formData, function(){}, function(){});  
    $.ajax({
	        url: url + "/Upload",
	        type: "POST",
	        data: formData,
	        /**
	        *必须false才会自动加上正确的Content-Type
	        */
	        contentType: false,
	        /**
	        * 必须false才会避开jQuery对 formdata 的默认处理
	        * XMLHttpRequest会对 formdata 进行正确的处理
	        */
            processData: false,
            xhrFields: {  withCredentials: true  },            
	        success: function (data) {
	        	var json = eval('(' + data + ')'); 
	            if (json.status == '1' || json.status == '2') {
                    alert("上传成功！");
                    getNoticeFromMyFriend();
	            }
	            else{
					alert("上传失败！" + json.errors);
					for(var i = 0; i < btns.length; i++){
						btns[i].disabled=false;
					}
	            }
	            $("#imgWait").hide();
	            // console.log(data);
	        },
	        error: function () {
	            alert("上传失败！");
				$("#imgWait").hide();
				for(var i = 0; i < btns.length; i++){
					btns[i].disabled=false;
				}
	        }
	    });
}
function switchdiv(index){
    var divs = document.getElementsByClassName("switchablediv");
    for(var i = 0; i < divs.length; i++){
        divs[i].setAttribute("class", "switchablediv hide");
    }
    divs[index].setAttribute("class", "switchablediv show");
}
function downloadNotice(noticeid){
    // alert(noticeid);
    var a = document.createElement('a');
    a.href = url + '/DownLoadNotice?noticeid=' + noticeid;
    $("body").append(a); 
    a.click();
    $(a).remove();
}
function downloadfile(fileid){
	// alert(fileid);
	var a = document.createElement('a');
	
	a.href = url + '/DownloadFile?fileid=' + fileid;
	$("body").append(a); 
	a.click();
	$(a).remove();
}
function downloadExcel(id){
	
    var a = document.createElement('a');

    a.href = url + '/DownloadExcelFile?excelfileid=' + id;
    $("body").append(a); 
    a.click();
    $(a).remove();
}
function deleteExcelfile(id){
    var data = {
        'excelfileid':id
    };
    console.log(id);
    console.log(data);
    ajax(url + "/DeleteExcelFile", data,
        function(data){
            console.log(data);
            getMyExcelFiles();
        },
        function(data){alert(data);}
    );
}
function deleteNotice(id){
    var data = {
        'noticeid':id
    };
    console.log(id);
    console.log(data);
    ajax(url + "/DeleteNotice", data,
        function(data){
            console.log(data);
            getMyNotice();
        },
        function(data){alert(data);}
    );
}
function onlogin(){
    var btn = document.getElementById("loginbtn");
    btn.setAttribute("data-toggle", "modal");
    btn.setAttribute("data-target", "#loginModal");
}
function showModel(modal, btn){
    console.log(modal);
    btn.setAttribute("data-toggle", "modal");
    btn.setAttribute("data-target", "#" + modal);
    
    // var btn = document.getElementById("newnoticebtn");
    // btn.setAttribute("data-toggle", "modal");
    // btn.setAttribute("data-target", "#newNoticeModal");
}
function resetForm(){
    var forms = document.getElementsByTagName("form");
    for(var i = 0; i < forms.length; i++){
        forms[i].reset();
    }
}

function getExcelHead(id){
    var div = document.createElement("div");
    var inputs = new Array();
    var excelrecordid = getrecord(id, inputs);
    
    $.ajax(url + "/GetExcelHead", {
        type:"POST",
        async:false,
        data:{ 'id': id},
        crossDomain:true,
        datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
        xhrFields: {  withCredentials: true  },             
        success:function(data,status,xhr){
            var json =  eval('(' + data + ')'); 
            if(json.status == '0'){
                alert("请先登录！");
                // login();
                return ;
            }
            // console.log(json);
            if(json.status == '1'){
                
                // alert(json.data);
                
                div.innerHTML = (json.data);
                var table = div.getElementsByTagName("table")[0];
                var col = json.colnumber;
                var newTR = table.insertRow(table.rows.length);
                for(var i = 0; i < col; i++){
                    var input = document.createElement("input");
                    input.setAttribute("class", "excelrecord" + id);
                    input.setAttribute("type", "text");
                    input.setAttribute("name", "" + i);
                    inputs.push(input);
                    newTR.insertCell(i).appendChild(input);
                    // newTR.insertCell(i).innerHTML = "<input class='excelrecord" + excelrecordid + "' type=text name=" + i + " />";
                    // console.log("<input class='excelrecord" + excelrecordid + "' type=text name=" + i + " />");
                }	
                getrecord(id, inputs);
                table.appendChild(newTR);	
            }
    }});
    
    var btn = document.createElement("button");
    btn.setAttribute("onclick", "saveorupdateexcelrecord(" + id + ", " + excelrecordid + ")");
    btn.setAttribute("id", "subrecordbtn" + id);
    btn.appendChild(document.createTextNode("提交"));
    
    div.appendChild(btn);
    return div;
}

function getrecord(id, inputs){
    
    var excelrecordid = -1;
    $.ajax(url + "/GetExcelrecord", {
        type:"POST",
        async:false,
        data:{ 'excelfileid': id},
        crossDomain:true,
        datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
        xhrFields: {  withCredentials: true  },             
        success:function(data,status,xhr){
            var json =  eval('(' + data + ')'); 
            if(json.status == '0'){
                alert("请先登录！");
                return ;
            }
            console.log(json);
            if(json.status == '1'){
                json = json.data;
                // var div = document.getElementById("fillexcel");
                console.log("excelrecord" + excelrecordid);
                // var inputs = document.getElementsByClassName("excelrecord" + excelrecordid);
                for(var i = 0; i < inputs.length; i++){
                    // console.log(json.record[i]);
                    inputs[i].setAttribute("value", json.record[i]);
                }
                excelrecordid = parseInt(json.id);
            }
        }});
        return excelrecordid;
}

function saveorupdateexcelrecord(excelfileid, excelrecordid){
        
    var inputs = document.getElementsByClassName("excelrecord" + excelfileid);
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
    text = '{' + text + '}';
    text = '"excelfileid":"' + excelfileid + '","excelrecordid":"' + excelrecordid + '","record":' + text;
    text = '{' + text + '}';
    text = JSON.parse(text);

    // console.log(text);
    $.ajax(url + "/SaveOrUpdateExcelrecord", {
        type:"POST",
        async:false,
        data:JSON.stringify(text),
        crossDomain:true,
        datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
        xhrFields: {  withCredentials: true  },             
        success:function(data,status,xhr){
            console.log(data);
            var json =  eval('(' + data + ')'); 
            if(json.status == '0'){
                alert("请先登录！");
                // login();
                return ;
            }
            console.log(json);
            if(json.status == '1'){
                var recordid = getrecord(excelfileid, inputs);
                document.getElementById("subrecordbtn" + excelfileid).setAttribute("onclick", "saveorupdateexcelrecord(" + excelfileid + ", " + recordid + ")") ;
                alert("提交成功！");

            }
            else{
                alert("提交失败！");
            }
            // excelfilelist();
        }});

}

function onregist(){
	
	var inputs = document.getElementsByClassName("registinput");
	var studentnumber = inputs[0].value;
	var username = inputs[1].value;
	var password = inputs[2].value;
	var repassword = inputs[3].value;
	var email = inputs[4].value;
	var tel = inputs[5].value;
    var data = { 'studentnumber': studentnumber, 'password': password ,'email': email, 'tel' : tel, 'username' : username}
    console.log(data);
	$.ajax(url + "/Regist", {
		type:"POST",
		async:false,
		data:data,
		crossDomain:true,
		datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')'); 
			if(json.status == '1'){
				alert("注册成功");
				auth(studentnumber, password);
			}
			else{
				alert("failed");
			}
	 }});
}

function senduserinfo(){
    // var form = document.getElementById("updateinfoform");
    // var inputs = form.getElementsByTagName("input");
	var inputs = document.getElementsByClassName("updateuserinfoinput");
	for(var i = 0; i < inputs.length; i++){
		console.log(inputs[i].value);
	}
	$.ajax(url + "/UpdateUserinfo", {
		type:"POST",
		async:false,
		data:{ 'name': inputs[0].value, 'school': inputs[1].value, 'institute': inputs[2].value, 'major': inputs[3].value, 'email': inputs[4].value, 'tel': inputs[5].value },
		crossDomain:true,
		datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')'); 
			if(json.status == "0"){
				alert("请先登录");
				login();
				return ;
			}
			if(json.status == '1'){
				alert("更新成功！");
			}
			else if(json.status == '5'){
				alert("更新失败！");
			}
	 }});
}

function newfriend(iput){
	
	var par = document.getElementById("friendaccount").value;
	$.ajax(url + "/NewRelationship", {
		type:"POST",
		async:false,
		data:{ "friendaccount" : par },
		crossDomain:true,
		datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')'); 
			if(json.status == '1'){
				alert("添加成功！");
				document.getElementById("friendaccount").value = "";
				addfriend();
			}
			else{
				alert(json.errors);
			}
	 }});
}

function addfriend(){
	// switchfun('addfriend');
	$.ajax(url + "/GetFriends", {
		type:"POST",
		async:false,
		crossDomain:true,
		
		datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')'); 
			if(json.status == "0"){
				alert("请先登录！");
				// login();
				return ;
			}
			
			if(json.status == '1'){
				json = json.friends;
				var ul = document.getElementById("friendslist");
				ul.innerHTML = "";
				for(var i = 0; i < json.length; i++){
					var li = document.createElement("li");
					var a = document.createElement("a");
					li.appendChild(document.createTextNode(json[i].friendname));
					a.appendChild(document.createTextNode("删除"));
					a.setAttribute("onclick", "deletefriend(" + json[i].id + ")");
					li.appendChild(a);
					ul.appendChild(li);
				}
				document.getElementById("addfriend").appendChild(ul);
			}
			else{
				alert(json.errors);
			}
	 }});
	
	
}

function deletefriend(id){
	$.ajax(url + "/DeleteRelationship", {
		type:"POST",
		async:false,
		crossDomain:true,
		data:{'id':id},
		datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')'); 
			if(json.status == "0"){
				alert("请先登录！");
				login();
				return ;
			}
			
			if(json.status == '1'){
				alert("删除成功！");
				addfriend();
			}
			else{
				alert(json.errors);
			}
	 }});


}

function logout(userid){
	$.ajax(url + "/Logout", {
		type:"POST",
		async:false,
		data:{ 'id' : userid },
		crossDomain:true,
		datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){

			var json = eval('(' + data + ')'); 
			console.log(json);
			if(json.status == '1'){
				window.location.href = url;	
				return ;
			}
			else{
				
				return ;
			}
	 }});
}

function updateinfo(){
    // switchfun('updateinfo');
    
	var inputs = document.getElementsByClassName("updateuserinfoinput");

	$.ajax(url + "/GetUserinfo", {
		type:"POST",
		async:false,
		data:{ },
		crossDomain:true,
		datatype: "html",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')'); 
			if(json.status == "0"){
				alert("请先登录");
				login();
				return ;
			}
			if(json.status == '1'){
				inputs[0].value = json.username;
				inputs[1].value = json.school;
				inputs[2].value = json.institute;
				inputs[3].value = json.major;
				inputs[4].value = json.email;
				inputs[5].value = json.tel;
			}
	 }});

}


function onload(){
	$.ajax(url + "/GetUserinfo", {
		type:"POST",
		async:false,
		crossDomain:true,
		datatype: "text",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			var json =  eval('(' + data + ')');

			if(json.status == "0"){
				return ;
			}
			if(json.status == '1'){
                console.log(json);
                document.getElementById("user").innerHTML = "";
                var a = document.createElement("a");
                a.setAttribute("id", "usercenter");
                a.innerHTML = json.username + ",注销";
                a.setAttribute("onclick", "logout(" + json.userid + ")");
                document.getElementById("user").appendChild(a);
			}
			else{
				console.log('failed');
			}
	 }});
}

function rePassword(){
    var inputs = document.getElementsByClassName("updatepasswordinput");
    var data = {
        'oldpassword':inputs[0].value,
        'newpassword':inputs[1].value
    };
    ajax(url + "/UpdatePassword", data, 
        function(data){
            var data = eval('(' + data + ')');
            if(data.status == 1){   
                alert("修改成功，请牢记新密码！");
            }
            else{
                alert(data.errors);
            }
        },
        function(data){alert(data)}
    );
}