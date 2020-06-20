<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>网上书城后台管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/bootstrap-switch.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/admin/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/jquery.twbsPagination.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/bootstrap-switch.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/upload.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/order.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/product.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/notice.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/user.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/slider.js"></script>
</head>
<body>
	<!-- 1.网上书城后台管理顶部 start -->
	<%@ include file="top.jsp"%>
	<!-- 1.网上书城后台管理顶部 end -->
	<!-- 2.网上书城后台管理左边菜单栏 start -->
	<jsp:include page="left.jsp"></jsp:include>
	<!-- 2.网上书城后台管理左边菜单栏 end -->

	<c:choose>
		<c:when test="${param.item eq 'product_list'}">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Products</h1>
			</div>
			<!-- 3.网上书城后台管理商品列表页面 start -->
			<jsp:include page="../products/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理商品列表页面 end -->
		</c:when>
		<c:when test="${param.item eq 'sale_list'}">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Sales</h1>
			</div>
			<!-- 3.网上书城后台管理销售榜单页面 start -->
			<jsp:include page="../sale/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理销售榜单页面 end -->
		</c:when>
		<c:when test="${param.item eq 'order_list'}">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Orders</h1>
			</div>
			<!-- 3.网上书城后台管理订单管理页面 start -->
			<jsp:include page="../order/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理订单管理页面 end -->
		</c:when>
		<c:when test="${param.item eq 'notice_list'}">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Notices</h1>
			</div>
			<!-- 3.网上书城后台管理公告管理页面 start -->
			<jsp:include page="../notice/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理公告管理页面 end -->
		</c:when>
		<c:when test="${param.item eq 'slider_list'}">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Sliders</h1>
			</div>
			<!-- 3.网上书城后台管理轮播图管理页面 start -->
			<jsp:include page="../slider/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理轮播图管理页面 end -->
		</c:when>
		<c:when test="${param.item eq 'user_list'}">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Customers</h1>
			</div>
			<!-- 3.网上书城后台管理用户管理页面 start -->
			<jsp:include page="../user/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理用户管理页面 end -->
		</c:when>
		<c:when test="${param.item eq 'admin_list' && sessionScope.admin.role eq '超级管理员' }">
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Admins</h1>
			</div>
			<!-- 3.网上书城后台管理管理员管理页面 start -->
			<jsp:include page="../ruler/list.jsp"></jsp:include>
			<!-- 3.网上书城后台管理管理员管理页面 end -->
		</c:when>
		<c:otherwise>
			<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
				<h1 class="h2">Home</h1>
			</div>
			<!-- 3.网上书城后台管理首页页面 start -->
			<jsp:include page="welcome.jsp"></jsp:include>
			<!-- 3.网上书城后台管理首页页面 end -->
		</c:otherwise>
	</c:choose>

	<!-- 4.网上书城后台管理底部 start -->
	<%@ include file="bottom.jsp"%>
	<!-- 4.网上书城后台管理底部 end -->
</body>
</html>