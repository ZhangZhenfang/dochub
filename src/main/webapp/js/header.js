var url = "http://www.the15373.com/"
// var url = "http://localhost:8080/dochub"

function getUserinfo(){
	$.ajax({
		url:url + "/users/getUserinfo",
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
			var user_name = document.getElementById("username");
			user_name.innerHTML = data.data.name;
			console.log(data);
		},
		error:function(data){
			console.log(data);
			alert("adsfa");
		}
	})
}

function lagout(){
	$.ajax(url + "/users/logout", {
		type:"post",
		async:false,
		// data:{},
		crossDomain:true,
		datatype: "json",
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

