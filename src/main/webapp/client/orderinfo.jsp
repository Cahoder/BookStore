<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户地址</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/register.css">
<script src="js/form.js"></script>
<style type="text/css">
	.box{
		display: flex;
		flex-flow: row;
		align-items: top;
		align-content: top;
	}
	.box2{
 		/* background-color: #66ccff; */
		padding:20px 0 20px 0; 
 		flex: auto; 
	}

	input {
		bOrder-style: none;
		bOrder: gray solid thin;
	}
	.item{
		background-color: #FFF8E1;
		text-align: center;
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

	<!-- 3.用户订单详情 start -->
	<div id="register-body">
		<h3 id="register-title">
			<a href="<%=response.encodeURL("UserServlet?action=info")%>"
				class="secondary_link">个人信息详情</a> | <a
				href="<%=response.encodeURL("UserServlet?action=receipt")%>"
				class="secondary_link">个人地址详情</a> | 个人订单详情
		  | <a href="<%=response.encodeURL("UserServlet?action=favor")%>" class="secondary_link">个人收藏详情</a>
			<input type="button" style="margin-left: 50%;" onclick="history.go(-1)" value="返回上一页" />
		</h3>

		<div class="box">
			<div class="box2">
				<div>
					<table cellspacing="0" style="line-height: 30px; width: 100%;">
						<tr>
							<td class="item" width="15%">订单编号：</td>
							<td width="50%">${Order.id}</td>
							<td class="item" width="15%">账户：</td>
							<td width="20%">${Order.user.username}</td>
						</tr>
						<tr>
							<td class="item" width="15%">收件人：</td>
							<td width="50%">${Order.receiverName}</td>
							<td class="item" width="15%">联系电话：</td>
							<td width="20%">${Order.receiverPhone}</td>
						</tr>
						<tr>
							<td class="item" width="15%">送货地址：</td>
							<td width="50%">${Order.receiverAddress}</td>
							<td class="item" width="15%">下单时间：</td>
							<td width="35%">${Order.orderTime}</td>
						</tr>
						<tr>
							<td class="item" width="15%">合计：</td>
							<td width="20%" style="color: red;">¥：${Order.money}</td>

						</tr>
						<tr>
							<td class="item" width="15%">商品列表：</td>
							<td colspan="3">
								<table cellspacing="0" bOrder="1"
									style="text-align: center; width: 100%;">
									<tr bgcolor="#E8F5E9">
										<th>序号</th>
										<th>商品名称</th>
										<th>商品描述</th>
										<th>商品价格</th>
										<th>购买数量</th>
										<th>价格小计</th>
									</tr>
									<c:forEach items="${Order.orderItems}" var="oi"
										varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${oi.product.name}</td>
											<td>${oi.product.description}</td>
											<td>${oi.product.price}</td>
											<td>${oi.buynum}</td>
											<td>${oi.product.price*oi.buynum}</td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: right; padding-right: 10px; padding-top: 10px;">
								<c:if test="${Order.payState==0}">
									<a href="<%=response.encodeURL("OrderServlet")%>?action=payOrder&id=${Order.id}">
										<img src="images/gif53_030.gif" width="115" height="35">
									</a>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 3.用户订单详情 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>