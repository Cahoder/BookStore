<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="登录页面">
	<meta name="author" content="cahoder">
	<title>网上书城后台管理</title>
	<!-- Favicons -->
	
	<!-- Core CSS & JS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/bootstrap.min.css">
	<script src="${pageContext.request.contextPath}/admin/js/jquery.min.js"></script>
	<script	src="${pageContext.request.contextPath}/admin/js/bootstrap.bundle.min.js"></script>
	<style>
		.bd-placeholder-img {
			font-size: 1.125rem;
			text-anchor: middle;
			-webkit-user-select: none;
			-moz-user-select: none;
			-ms-user-select: none;
			user-select: none;
		}
		
		@media ( min-width : 768px) {
			.bd-placeholder-img-lg {
				font-size: 3.5rem;
			}
		}
		
		html, body {
			height: 100%;
		}
		
		body {
			display: -ms-flexbox;
			display: flex;
			-ms-flex-align: center;
			align-items: center;
			padding-top: 40px;
			padding-bottom: 40px;
			background-color: #f5f5f5;
		}
		
		.form-signin {
			width: 100%;
			max-width: 330px;
			padding: 15px;
			margin: auto;
		}
		
		.form-signin .checkbox {
			font-weight: 400;
		}
		
		.form-signin .form-control {
			position: relative;
			box-sizing: border-box;
			height: auto;
			padding: 10px;
			font-size: 16px;
		}
		
		.form-signin .form-control:focus {
			z-index: 2;
		}
		
		.form-signin input[type="text"] {
			margin-bottom: -1px;
			border-bottom-right-radius: 0;
			border-bottom-left-radius: 0;
		}
		
		.form-signin input[type="password"] {
			margin-bottom: 10px;
			border-top-left-radius: 0;
			border-top-right-radius: 0;
		}
	</style>
</head>
<body class="text-center">
	<form class="form-signin" method="post" 
		action="<%=response.encodeURL(request.getContextPath()+"/admin/LoginAdminServlet")%>">
		<img class="mb-4" src="/favicon.ico" alt=""	width="72" height="72">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		<input name="action" value="login" hidden>
		<label for="inputUser" class="sr-only">Username</label>
		<input type="text" name="username" id="inputUser" 
			class="form-control" autocomplete="off" placeholder="Username" required autofocus>
		<label for="inputPassword" class="sr-only">Password</label>
		<input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
		<div class="form-group">
			<label for="inputCode" class="sr-only">Validatecode</label>
			<input type="text" name="code" id="inputCode" class="form-control" autocomplete="off" placeholder="Validatecode" required >
			<img src="<%=response.encodeURL(request.getContextPath()+"/admin/LoginAdminServlet")%>?action=code"> 
		</div>
		<div class="checkbox mb-3">
			<label><input type="checkbox" value="remember-me"> Remember me</label>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign	in</button>
		<p class="mt-5 mb-3 text-muted">&copy; 2019-2020</p>
	</form>
</body>
</html>