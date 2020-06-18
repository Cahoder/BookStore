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
<script src="js/jquery.min.js"></script>
<script src="js/form.js"></script>
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.网上书城订单确认 start -->
	<p id="location">
		<a href="<%=response.encodeURL("./")%>">首页</a> &nbsp; > &nbsp; 
		<a href="<%=response.encodeURL("CartServlet?action=cart")%>">购物车</a> &nbsp; > &nbsp; 订单确认
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
				</tr>
			</thead>
			<tbody>
				<c:set value="0.0" var="totalPrice"/>
				<c:if test="${sessionScope.UserCarts eq null || fn:length(sessionScope.UserCarts) < 1}">
					<tr>
						<td colspan='6' style='color:grey;'>购物车空空如也,快去书城商品看看吧！</td>
					</tr>
				</c:if>
				<c:forEach items="${sessionScope.UserCarts}" var="cart" varStatus="status">
					<c:set value="${totalPrice + cart.product.price * cart.addnum}" var="totalPrice"/>
					<tr>
						<td>${status.count }</td>
						<td>${cart.product.name}</td>
						<td>${cart.product.price}</td>
						<td>${cart.product.pnum - cart.addnum}</td>
						<td>${cart.addnum}</td>
						<td>${cart.product.price * cart.addnum}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th colspan="5"></th>
					<th colspan="1">合计：${totalPrice}元</th>
				</tr>
			</tfoot>
		</table>
		<div class="address" style="background-color: #FFF8E1;padding: 10px; box-shadow:0px 0px 20px 16px #FFFFF3 inset;">
			<form action="<%=response.encodeURL("OrderServlet")%>" method="post" id="userReceiptForm"
				onsubmit="return $('input[name=receiverName]').val()!=''&&$('input[name=receiverPhone]').val()!=''&&$('input[name=receiverAddress]').val()!='';">
				<div>
						<input name="action" hidden="hidden" value="newOrder" readonly="readonly" required="required">
						<input name="receiptID" hidden="hidden" readonly="readonly" required="required">
						收货人：<input name="receiverName" type="text" required="required"
						style="width:20%;border-style: none;border: gray thin solid; margin-right: 5%;" />
						联系方式：<input type="text" name="receiverPhone" required="required"
						style="width:30%;border-style: none;border: gray thin solid;" />
					<br />
						收货地址：<input name="receiverAddress" type="text" required="required"
						style="width:61%; border-style: none;border: gray thin solid;" />
					<br />	
				<c:if test="${requestScope.UserReceipt ne null && fn:length(requestScope.UserReceipt) > 0 }">
					使用原有地址：
					<select onchange="changeUserReceipt(this)">
						<option id="0">------不使用------</option>
						<c:forEach items="${requestScope.UserReceipt}" var="receipt" varStatus="status">
							<option id="${receipt.id }" address="${receipt.address }" phone="${receipt.phone }" name="${receipt.name }">${receipt.address }</option>
						</c:forEach>
					</select>
				</c:if>
				</div>
				<div style="text-align: right;margin-top: 10px;">
					<a href="<%=response.encodeURL("ProductServlet")%>?action=list">
						<img src="images/gwc_jx.gif" width="110" height="30">
					</a>&nbsp;&nbsp;
					<button type="submit"><img src="images/gif53_029.gif" width="110" height="31"></button>
				</div>
			</form>
		</div>
	</div>
	<!-- 3.网上书城订单确认 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>