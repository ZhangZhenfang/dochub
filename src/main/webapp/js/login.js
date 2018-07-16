// var url = "http://localhost:8080/dochub"
// var url = "http://www.the15373.com/dochub"

function login(){
	var id = document.getElementById("user");
	var pas = document.getElementById("pass");
	if(id.value == ""){
		id.focus();
		return ;
	}
	if(pas.value == ""){
		pas.focus();
		return ;
	}
	auth(id.value, pas.value);
}

function auth(account, password){
	$.ajax({
		url:url + "/users/auth",
		type:"post",
		async:false,
		datatype:"text",
		data:{"account":account, "password":password },
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			console.log(data);
//			data = eval('(' + data + ')'); 
			if(data.status == 1 || data.status == 2){
				window.location.href = "upfile.html";
			}
			else{
				alert("用户名或密码错误");
			}
		},
		error:function(data){
		
		console.log(data);
		alert("adsfa");
		
		}
	})
}

function regist(){
	var user = document.getElementById("user_re");
	var name = document.getElementById("name");
	var pass1 = document.getElementById("pass_re1");
	var pass2 = document.getElementById("pass_re2");
	var regist = document.getElementById("regist");
	
	if(user.value == ""){
		user.focus();
		return;
	}
	if(name.value == ""){
		name.focus();
		return;
	}
	if(pass1.value == ""){
		pass1.focus();
		return;
	}
	if(pass2.value == ""){
		pass2.focus();
		return;
	}
	
	regist.onclick = function(){
		if(pass1.value != pass2.value){
			alert("密码不匹配");
		}else{
			regist1(user.value, name.value, pass1.value);
		}
	}
}

function regist1(account, name, pass){
	$.ajax({
		url:url + "/users/regist",
		type:"post",
		async:false,
		datatype:"text",
		data:{"account":account, "password":pass, "studentnumber":account, "name":name, "phonenumber":"123", "email":"123", "school":"123", "institute":"123", "major":"123", "sex":"f"},
		crossDomain:true,
		xhrFields: {  withCredentials: true  }, 
		success:function(data){
			console.log(data);
			if(data.status == 1){
				alert("注册成功，请登录");
				window.location.href = "login.html";
			}else{
				alert(data.errors);
			}
		},
		error:function(data){
			console.log(data);
			alert("登录异常");
		}
	})
}
