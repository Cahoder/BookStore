// 检测注册表单信息
function checkRegister() {
	var form = document.getElementById("register-form");
	if (form == null) {
		alert("表单不存在!");
		return false;
	}
	var email = document.getElementById("email");
	if (email.value == "") {
		alert("邮箱不可以为空！");
		return false;
	} else if (!/^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
			.test(email.value)) {
		alert("邮箱输入格式有误！");
		return false;
	}
	var name = document.getElementById("name");
	if (name.value == "") {
		alert("用户名不可以为空！");
		return false;
	} else if (/^\d+/.test(name.value)) {
		alert("用户名不可以为数字开头！");
		return false;
	}
	var password = document.getElementById("password");
	if (password.value == "") {
		alert("密码不可以为空！");
		return false;
	} else if (password.value.length < 6 || password.value.length > 16) {
		alert("密码请设置6-16位字符！");
		return false;
	}
	var repassword = document.getElementById("repassword");
	if (repassword.value == "") {
		alert("重复密码不可以为空！");
		return false;
	} else if (repassword.value != password.value) {
		alert("两次输入的密码不一致！");
		return false;
	}
	var phone = document.getElementById("phone");
	if (phone.value == "") {
		alert("联系电话不可以为空！");
		return false;
	} else if (!/^1(3|4|5|7|8)\d{9}$/.test(phone.value)
			|| !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(phone.value)) {
		alert("联系电话输入格式有误！");
		return false;
	}

	return true;
}
//检测登陆表单信息
function checkLogin() {
	var form = document.getElementById("login-form");
	if (form == null) {
		alert("表单不存在!");
		return false;
	}
	var name = document.getElementById("name");
	if (name.value == "") {
		alert("用户名不可以为空！");
		return false;
	} else if (/^\d+/.test(name.value)) {
		alert("用户名不可以为数字开头！");
		return false;
	}
	var password = document.getElementById("password");
	if (password.value == "") {
		alert("密码不可以为空！");
		return false;
	} else if (password.value.length < 6 || password.value.length > 16) {
		alert("密码为6-16位字符！");
		return false;
	}
	
	return true;
}
//替换用户地址
function changeUserReceipt(e){
	var obj = $(e).find("option:selected");
	if(obj.attr("id")!= 0){
		$("#userReceiptForm").find("input[name=receiptID]").val(obj.attr("id"));
		$("#userReceiptForm").find("input[name=receiverPhone]").val(obj.attr("phone")).attr("readonly",true);
		$("#userReceiptForm").find("input[name=receiverName]").val(obj.attr("name")).attr("readonly",true);
		$("#userReceiptForm").find("input[name=receiverAddress]").val(obj.attr("address")).attr("readonly",true);
	}
	else {
		$("#userReceiptForm").find("input[name=receiptID]").val("");
		$("#userReceiptForm").find("input[name=receiverPhone]").val("").attr("readonly",false);
		$("#userReceiptForm").find("input[name=receiverName]").val("").attr("readonly",false);
		$("#userReceiptForm").find("input[name=receiverAddress]").val("").attr("readonly",false);
	}
}