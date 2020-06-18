<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>用户登录</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/login.css">
	<script src="js/form.js"></script>
</head>

<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp" %>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp" %>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.网上书城登陆模块 start -->
	<div id="login">
		<br />
		<p id="location">
			<a href="<%=response.encodeURL(" ./")%>">首页 </a> &nbsp;> &nbsp; 个人用户登录
		</p>
		<div id="login-info">
			<div class="left">
				<div class="login-form">
					<p class="title"><img src="images/logintitle.gif"></p>
					<form id="login-form" 
						action="<%=response.encodeURL(request.getContextPath()+"/client/LoginServlet")%>" 
						method="post" onsubmit="return checkLogin();">
						<input name="action" value="login" hidden="hidden">
						<p><label>用户名：</label><input type="text" id="name" name="username" placeholder=" 字母数字下划线1到10位"></p>
						<p><label>密码：</label><input type="password" id="password" name="password" placeholder=" 6-16位字符"></p>
						<p>
							<input type="checkbox" name="options" id="remember-user">记住用户名&nbsp;&nbsp;
							<input type="checkbox" name="options" id="auto-login">自动登录
						</p>
						<p>
							<button type="submit" id="loginBtn"><img src="images/loginbutton.gif"></button>
						</p>
					</form>
				</div>
			</div>
			<div class="right">
				<h3>您还没有注册？</h3>
				<p>注册新用户，享受更优惠价格！</p>
				<p>千种图书，供你挑选！注册即享受丰富折扣，便宜有好货！超过万本图书任你选。</p>
				<p>超人气社区！精彩活动每一天，买卖更安心！支付宝交易超安全。</p>
				<a href="<%=response.encodeURL(" register.jsp")%>"><img src="images/signupbutton.gif"></a>
			</div>
		</div>
	</div>
	<!-- 3.网上书城登陆模块 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp" %>
	<!-- 4.网上书城下方显示 end -->
</body>

</html>