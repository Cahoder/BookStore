<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
	pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人购物车</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/cart.css">
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.图书购物车 start -->
	<p id="location">
		<a href="<%=response.encodeURL("./")%>">首页</a> &nbsp; > &nbsp; 购物车
	</p>
	<div id="cart">
		<table>
			<thead>
				<tr>
					<th>序号</th>
					<th>商品名称</th>
					<th>价格</th>
					<th>库存</th>
					<th>数量</th>
					<th>小计</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
				<c:set value="0.0" var="totalPrice"/>
				<c:if test="${sessionScope.UserCarts eq null || fn:length(sessionScope.UserCarts) < 1}">
					<tr>
						<td colspan='7' style='color:grey;'>购物车空空如也,快去书城商品看看吧！</td>
					</tr>
				</c:if>
				<c:forEach items="${sessionScope.UserCarts}" var="cart" varStatus="status">
					<c:set value="${totalPrice + cart.product.price * cart.addnum}" var="totalPrice"/>
					<tr>
						<td>${status.count }</td>
						<td>${cart.product.name}</td>
						<td>${cart.product.price}</td>
						<td>${cart.product.pnum - cart.addnum}</td>
						<td>
							<c:if test="${cart.addnum > 1}">
							<a href="<%=response.encodeURL("CartServlet")%>?action=subNum&id=${cart.product.id}">-</a>
							</c:if>
							 ${cart.addnum} 
							<c:if test="${cart.addnum < cart.product.pnum}">
							<a href="<%=response.encodeURL("CartServlet")%>?action=addNum&id=${cart.product.id}">+</a>
							</c:if>
						</td>
						<td>${cart.product.price * cart.addnum}</td>
						<td>
							<a href='<%=response.encodeURL("CartServlet")%>?action=delCart&id=${cart.product.id}'>
								<img width='10px' width='10px' src='images/error.jpg'>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th colspan="5">合计：${totalPrice}元</th>
					<td align="center">
						<a href="<%=response.encodeURL("ProductServlet")%>?action=list">
							<img src="images/gwc_jx.gif" width="110" height="30"></a>
					</td>
					<td align="center">
						<c:if test="${fn:length(sessionScope.UserCarts) > 0}">
							<a href="<%=response.encodeURL("CartServlet")%>?action=preOrder">
							<img src="images/finalbutton.gif" width="115" height="35"></a>
						</c:if>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<!-- 3.图书购物车 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>