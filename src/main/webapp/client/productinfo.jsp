<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户地址</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/register.css">
<script src="js/form.js"></script>
<style type="text/css">
.box {
	display: flex;
	flex-flow: row;
	align-items: center;
	align-content: center;
}

.box2 {
	padding: 20px 0 20px 0;
	flex: auto;
	position: relative;
}

.btn2 {
	padding-left: 15px;
	padding-right: 15px;
	height: 40px;
	color: white;
	background-color: #055577;
	border-style: none;
	border-radius: 20px;
	font-size: 16px;
	font-weight: bold;
}

.btn2:hover {
	background-color: #750505;
}
</style>

</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.用户商品详情 start -->
	<div id="register-body" style="min-height: 600px;">
		<br>
		<br>
		<form action="<%=response.encodeURL("handle-product.jsp")%>" method="post">
			<input type="text" name="action" value="addCart" hidden="hidden">
			<input type="text" name="id" value="${requestScope.Product.id}" hidden="hidden">
			<input readonly="readonly" type="text" name="name" hidden="hidden" value="${requestScope.Product.name}">
			<input readonly="readonly" type="number" name="price" hidden="hidden" value="${requestScope.Product.price}">
			<input readonly="readonly" type="number" name="pnum" hidden="hidden" value="${requestScope.Product.pnum}">
			<div class="box">
				<div class="box2">
					<img alt="${requestScope.Product.name}" src="${requestScope.Product.imgurl}" 
						height="280" style="position: absolute; left: 10%; top: 15%;"
						onerror="this.src='images/losing.jpg;this.οnerrοr=null'">
					<div style="padding: 30px 0px 0px 500px;">
						<table style="font-size: 20px; line-height: 50px;">
							<tr>
								<td>书名： <b>${requestScope.Product.name}</b></td>
								<td colspan="1">&nbsp;</td>
							</tr>
							<tr>
								<td>类别： <b>${requestScope.Product.category}</b></td>
								<td colspan="1">&nbsp;</td>
							</tr>
							<tr>
								<td>单价： <b style="color:red;">¥${requestScope.Product.price}</b></td>
								<td colspan="1">&nbsp;</td>
							</tr>
							<tr>
								<td>简介： <b>${requestScope.Product.description}</b></td>
								<td colspan="1">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">
									<c:if test="${isFavor==null}">
										<a href="<%=response.encodeURL("UserServlet")%>?action=addFavor&id=${requestScope.Product.id}">
										<button type="button" class="btn2">收藏</button></a>
									</c:if>
									<c:if test="${isFavor!=null}">
										<a href="<%=response.encodeURL("UserServlet")%>?action=deleteFavor&id=${requestScope.isFavor.id}">
										<button type="button" class="btn2">取消收藏</button></a>
									</c:if>
									<button type="submit" class="btn2">加入购物车</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- 3.用户商品详情 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>