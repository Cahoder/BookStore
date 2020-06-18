<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户收藏</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/register.css">
<script src="js/form.js"></script>
</head>
<style type="text/css">
.box {
	display: flex;
	flex-flow: row;
	align-items: top;
	align-content: top;
}

.box2 {
	padding: 20px 0 20px 0;
	flex: auto;
}
</style>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.用户个人订单详情 start -->
	<div id="register-body">
		<h3 id="register-title">
			<a href="<%=response.encodeURL("UserServlet?action=info")%>"
				class="secondary_link">个人信息详情</a> | <a
				href="<%=response.encodeURL("UserServlet?action=receipt")%>"
				class="secondary_link">个人地址详情</a> | <a
				href="<%=response.encodeURL("OrderServlet?action=order")%>"
				class="secondary_link">个人订单详情</a> | 个人收藏详情
		</h3>

		<div class="box">
			<div class="box2">
				<div style="margin-top: 15px;">
					<table cellspacing="0" c border="1"
						style="text-align: center; width: 100%;">
						<tr bgcolor="#A3D7E6" >
							<th width="30%">商品名称</th>
							<th width="15%">收藏时间</th>
							<th width="15%">售价</th>
							<th width="15%">剩余库存</th>
							<th width="15%">查看</th>
							<th width="10%">删除</th>
						</tr>

						<c:forEach items="${UserFavor}" var="o">
							<tr>
								<td>${o.product.name}</td>
								<td>${o.create_time}</td>
								<td>${o.product.price}</td>
								<td>${o.product.pnum}</td>
								<td>
									<a href="<%=response.encodeURL("ProductServlet")%>?action=info&id=${o.product.id}"> 
										<img alt="详情" src="images/icon3.png">
									</a>
								</td>
								<td>
									<a href="<%=response.encodeURL("UserServlet")%>?action=deleteFavor&id=${o.id}">
									<button style="color: #FF0000;" onclick='return confirm("您确认要删除此条收藏？");'>X</button>
									</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div style="width: 100%" align="right">
						<c:if test="${UserFavor==null||UserFavor.isEmpty()}">
							<div style="text-align: center; color: #ADADAD; padding-top: 10px;">
								😫你的收藏夹空空如也～
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- 3.用户个人订单详情 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>