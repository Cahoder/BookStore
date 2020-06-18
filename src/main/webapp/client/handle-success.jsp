<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>操作成功</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/tips.css">
<script src="js/interval.js"></script>
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->
	
	<!-- 3.操作提示成功区域 start -->
	<div id="tips">
		<div class="info-area">
			<img src="images/IconTexto_WebDev_009.jpg">
			<div>
				<b>${requestScope.Msg }</b>
				<a href="${requestScope.redirectUrl }">
				<span id="interval">3</span>秒后自动为你跳转页面,如无反应请点击我直接跳转！</a>
			</div>
		</div>
	</div>
	<!-- 3.操作提示成功 end -->
	
	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>