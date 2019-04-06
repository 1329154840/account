var Auth = {
	vars: {
		login: document.querySelector('.login'),
		login_brand: document.querySelector('.login-brand'),
		login_wrapper: document.querySelector('.login-wrapper'),
		login_login: document.querySelector('.login-login'),
		login_wrapper_height: 0,
		login_back_link: document.querySelector('.login-back-link'),
		forgot_link: document.querySelector('.forgot-link'),
		login_link: document.querySelector('.login-link'),
		login_btn: document.querySelector('.login-btn'),
		register_link: document.querySelector('.register-link'),
		password_group: document.querySelector('.password-group'),
		password_group_height: 0,
		login_register: document.querySelector('.login-register'),
		login_footer: document.querySelector('.login-footer'),
		box: document.getElementsByClassName('login-box'),
		option: {}
	},
	register(e) {
		Auth.vars.login_login.className += ' login-animated';
		setTimeout(() => {
			Auth.vars.login_login.style.display = 'none';
		}, 500);
		Auth.vars.login_register.style.display = 'block';
		Auth.vars.login_register.className += ' login-animated-flip';

		Auth.setHeight(Auth.vars.login_register.offsetHeight + Auth.vars.login_footer.offsetHeight);

		e.preventDefault();
	},
	login(e) {
		Auth.vars.login_register.classList.remove('login-animated-flip');
		Auth.vars.login_register.className += ' login-animated-flipback';
		Auth.vars.login_login.style.display = 'block';
		Auth.vars.login_login.classList.remove('login-animated');
		Auth.vars.login_login.className += ' login-animatedback';
		setTimeout(() => {
			Auth.vars.login_register.style.display = 'none';
		}, 500);

		setTimeout(() => {
			Auth.vars.login_register.classList.remove('login-animated-flipback');
			Auth.vars.login_login.classList.remove('login-animatedback');
		},1000);

		Auth.setHeight(Auth.vars.login_login.offsetHeight + Auth.vars.login_footer.offsetHeight);

		e.preventDefault();
	},
	forgot(e) {
		Auth.vars.password_group.classList += ' login-animated';
		Auth.vars.login_back_link.style.display = 'block';

		setTimeout(() => {
			Auth.vars.login_back_link.style.opacity = 1;
			Auth.vars.password_group.style.height = 0;
			Auth.vars.password_group.style.margin = 0;
		}, 100);
		
		Auth.vars.login_btn.innerText = 'Forgot Password';

		Auth.setHeight(Auth.vars.login_wrapper_height - Auth.vars.password_group_height);
		Auth.vars.login_login.querySelector('form').setAttribute('action', Auth.vars.option.forgot_url);

		e.preventDefault();
	},
	loginback(e) {
		Auth.vars.password_group.classList.remove('login-animated');
		Auth.vars.password_group.classList += ' login-animated-back';
		Auth.vars.password_group.style.display = 'block';

		setTimeout(() => {
			Auth.vars.login_back_link.style.opacity = 0;
			Auth.vars.password_group.style.height = Auth.vars.password_group_height + 'px';
			Auth.vars.password_group.style.marginBottom = 30 + 'px';
		}, 100);

		setTimeout(() => {
			Auth.vars.login_back_link.style.display = 'none';
			Auth.vars.password_group.classList.remove('login-animated-back');
		}, 1000);

		Auth.vars.login_btn.innerText = 'Sign In';
		Auth.vars.login_login.querySelector('form').setAttribute('action', Auth.vars.option.login_url);

		Auth.setHeight(Auth.vars.login_wrapper_height);
		
		e.preventDefault();
	},
	setHeight(height) {
		Auth.vars.login_wrapper.style.minHeight = height + 'px';
	},
	brand() {
		Auth.vars.login_brand.classList += ' login-animated';
		setTimeout(() => {
			Auth.vars.login_brand.classList.remove('login-animated');
		}, 1000);
	},
	init(option) {
		Auth.setHeight(Auth.vars.box[0].offsetHeight + Auth.vars.login_footer.offsetHeight);

		Auth.vars.password_group.style.height = Auth.vars.password_group.offsetHeight + 'px';
		Auth.vars.password_group_height = Auth.vars.password_group.offsetHeight;
		Auth.vars.login_wrapper_height = Auth.vars.login_wrapper.offsetHeight;

		Auth.vars.option = option;
		Auth.vars.login_login.querySelector('form').setAttribute('action', option.login_url);

		var len = Auth.vars.box.length - 1;

		for(var i = 0; i <= len; i++) {
			if(i !== 0) {
				Auth.vars.box[i].className += ' login-flip';
			}
		}

		Auth.vars.forgot_link.addEventListener("click", (e) => {
			Auth.forgot(e);
		});

		Auth.vars.register_link.addEventListener("click", (e) => {
			Auth.brand();
			Auth.register(e);
		});

		Auth.vars.login_link.addEventListener("click", (e) => {
			Auth.brand();
			Auth.login(e);
		});

		Auth.vars.login_back_link.addEventListener("click", (e) => {
			Auth.loginback(e);
		});
	}
}

var usertype={
	admi : 0,
	user : 1
};
function login_Form(form){
	var name = $("#login_name").val();
	var password = $("#login_password").val();
	var admicheck = document.getElementById("login_admi_check");
	var mytype =  usertype.user;
	if(admicheck.checked){
		mytype =  usertype.admi;
	}

	if(!name || name == ""){
		alert("请输入用户名");
		form.name.focus ();
		return false;
	}
	if(!password || password == ""){
		alert("请输入密码");
		form.password.focus ();
		return false;
	}
//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
	$.ajax({
		url : "/login",// 获取自己系统后台用户信息接口
		data :{"name":name,"password":password,"type":mytype},
		type : "POST",
		dataType: "json",
		success : function(data) {
			if (data){
				if (data.code == "0"){
					// alert(data.msg);
					if(mytype==usertype.user){ //普通用户登陆
						window.location.href = "/device_user_dashboard";
					}
					if(mytype==usertype.admi){//管理用户登陆
						window.location.href = "/device_admi_dashboard";
					}

					window.event.returnValue=false;
					return true;
				}else {
					alert(data.msg);//显示登录失败的原因
					return true;
				}
			}
		},
		error : function(data){

			alert(data.msg);
		}
	});

	return true;
}

function register_Form(form){
	var name = $("#register_name").val();
	var email = $("#register_email").val();
	var password = $("#register_password").val();
	var admicheck = document.getElementById("register_admi_check");
	var mytype =  1;
	if(admicheck.checked){
		mytype =  0;
	}
	// alert(name);
	// alert(password);
	if(!name || name == ""){
		alert("请输入用户名");
		form.name.focus ();
		return false;
	}
	if(!password || password == ""){
		alert("请输入密码");
		form.password.focus ();
		return false;
	}
//这里为用ajax获取用户信息并进行验证，如果账户密码不匹配则登录失败，如不需要验证用户信息，这段可不写
	$.ajax({
		url : "/register",// 获取自己系统后台用户信息接口
		data :{"name":name,"email":email,"password":password,"type":mytype},
		type : "POST",
		dataType: "json",
		success : function(data) {
			if (data){
				if (data.code == "0"){
					alert(data.msg);
					window.location.href = "/";
					window.event.returnValue=false;
					return true;
				}else {
					alert(data.msg);//显示登录失败的原因
					return true;
				}
			}
		},
		error : function(data){
			alert(data.msg);
		}
	});

	return true;
}
// function showMsg(msg){
// 	$("#CheckMsg").text(msg);
// }
/*
// ajax 对象
function ajaxObject() {
	var xmlHttp;
	try {
		// Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	}
	catch (e) {
		// Internet Explorer
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				alert("您的浏览器不支持AJAX！");
				return false;
			}
		}
	}
	return xmlHttp;
}

// ajax post请求：
function ajaxPost ( url , data , fnSucceed , fnFail , fnLoading ) {
	var ajax = ajaxObject();
	ajax.open( "post" , url , true );
	ajax.setRequestHeader( "Content-Type" , "application/x-www-form-urlencoded" );
	ajax.onreadystatechange = function () {
		if( ajax.readyState == 4 ) {
			if( ajax.status == 200 ) {
				fnSucceed( ajax.responseText );
			}
			else {
				fnFail( "HTTP请求错误！错误码："+ajax.status );
			}
		}
		else {
			fnLoading();
		}
	}
	ajax.send( data );

}*/