// var url = "http://localhost:8080/dochub";
// var url = "http://www.the15373.com/dochub";
function getUserInfo(){
	var name = document.getElementById("name");
	var college = document.getElementById("college");
	var email = document.getElementById("email");
	var school = document.getElementById("school");
	var major = document.getElementById("major");
	var phone = document.getElementById("phone");
	
	$.ajax({
		type:"get",
		url:url+"/users/getUserinfo",
		data:{},
		async:true,
		crossDomain:true,
		datatype:"text",
        xhrFields: {  withCredentials: true  },
		success:function(data){
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			if(data!=null){
			console.log(data);
			name.setAttribute("value",data.data.name);
			college.setAttribute("value",data.data.name);
			email.setAttribute("value",data.data.email);
			school.setAttribute("value",data.data.school);
			major.setAttribute("value",data.data.major);
			phone.setAttribute("value",data.data.phonenumber);
			}
			else{
				alert("请重新登录")
			}
		},
		error:function(data){
			alert("操作异常");
		}
	});	
}

function updateUserInfo(){
	var name = document.getElementById("name");
	var college = document.getElementById("college");
	var email = document.getElementById("email");
	var school = document.getElementById("school");
	var major = document.getElementById("major");
	var phone = document.getElementById("phone");
	console.log("name:"+name.value);
	$.ajax({
		type:"post",
		url:url+"/users/updateUserinfo",
		async:true,
		datatype:"text",
		data:{
			"name":name.value,
			"institute":college.value,
			"email":email.value,
			"school":school.value,
			"major":major.value,
			"phonenumber":phone.value
		},
		crossDomain:true,
		xhrFields:{ withCredentials:true },
		success:function(data){
			console.log(data);
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			if(data.status==1){
				console.log(data);
				alert("修改成功");
				window.location.reload();
			}
			else if(data.status==2){
				alert("修改失败");
			}
		},
		error:function(data){
			console.log(data);
			alert("操作异常");
		}
	});
	
}

function updatePwd(){
	var oldpwd = document.getElementById("oldpwd");
	var newpwd = document.getElementById("newpwd");
	var surepwd = document.getElementById("surepwd");
	var warn = document.getElementById("warn");
	if(surepwd.value==newpwd.value){
		$.ajax({
			type:"post",
			url:url+"/users/updatePassword",
			async:true,
			datatype:"text",
			data:{
				"oldpassword":oldpwd.value,
				"newpassword":newpwd.value
			},
			crossDomain:true,
			xhrFields:{ withCredentials:true },
			success:function(data){
				console.log(data);
				if(data.status == 0){
					alert("清先登陆");
					window.location.href = "login.html";
				}
				if(data.status==1){
					console.log(data);
					alert("修改成功");
					// window.location.reload();
				}
				else if(data.status==2){
					alert("修改失败");
				}
			},
			error:function(data){
				console.log(data);
				alert("操作异常");
			}
		});
	}
	else{
		warn.innerHTML = "两次密码不正确"
	}
}
function creatTable(){
	
	var tableVar = document.getElementById("table_result");
	
	$.ajax({
		type:"get",
		url:url+"/users/getFriends",
		async:true,
		datatype:"text",
		crossDomain:true,
		xhrFields:{ withCredentials:true },
		success:function(data){
			console.log(data);
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
			if(data.status==1){
				for(var i=0;i<data.data.length;i++){
					var tr = tableVar.insertRow();
					var c = 0;
					var td = tr.insertCell(c++);
					td.innerHTML = data.data[i].account;
					td = tr.insertCell(c++);
					td.innerHTML = data.data[i].name;
					td = tr.insertCell(c++);
					if(data.data[i].sex=="m"){
						td.innerHTML = "男";
					}
					else{
						td.innerHTML = "女";
					}
					td = tr.insertCell(c++);
					td.innerHTML = data.data[i].institute;
					td = tr.insertCell(c++);
					td.innerHTML = data.data[i].major;
	
					td = tr.insertCell(c++);
					var a = document.createElement("a");
					a.setAttribute("class", "a");
					a.setAttribute("onclick", "deleteRelationship("+data.data[i].account+")");
					a.innerHTML = "删除";
					td.appendChild(a);
					tableVar.appendChild(tr);
				}
			}
			else if(data.status==2){
				alert("修改失败");
			}
		},
		error:function(data){
			console.log(data);
			alert("操作异常");
		}
	});
}
function addAttention(){
	var attention = document.getElementById("attention");
	console.log(attention.value);
	if(attention.value != null){
		$.ajax({
			url:url + "/relations/addRelationship",
			data:{"account":attention.value},
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
        		if(data.status==1){
        			console.log(data);
					alert("关注成功");
					window.location.reload();
        		}
        		else if(data.status==2){
        			console.log(data);
        			alert(data.errors);
        		}
        		else if(data.status==3){
        			console.log(data);
        			alert("操作异常");
        		}
        	},
        	error:function(data){
        		console.log(data);
        	}
		});
	}
	
}

function deleteRelationship(account){
	$.ajax({
		type:"post",
		url:url + "/relations/deleRelationship",
		async:true,
		data:{"account":account},
		crossDomain:true,
		datatype:"text",
        xhrFields: {  withCredentials: true  },
        success:function(data){
			console.log(data);
			if(data.status == 0){
				alert("清先登陆");
				window.location.href = "login.html";
			}
        	if(data.status==1){
				alert("删除成功");
				window.location.reload();
        	}
        	else if(data.status==3){
        		alert(data.errors)
        	}
        },
        error:function(data){
        	alert("操作异常");
        }
	});
}

function logout(){
	$.ajax(url + "/users/logout", {
		type:"post",
		async:false,
		// data:{},
		crossDomain:true,
		datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
		xhrFields: {  withCredentials: true  },             
		success:function(data,status,xhr){
			if(data.status == '1'){
				window.location.href = "login.html";	
				return ;
			}else{
				return ;
			}
	 	}
	});
}