<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户订单</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/register.css">
<script src="js/form.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.twbsPagination.min.js"></script>
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
				class="secondary_link">个人地址详情</a> | 个人订单详情
		  | <a href="<%=response.encodeURL("UserServlet?action=favor")%>" class="secondary_link">个人收藏详情</a>
		</h3>

		<div class="box">
			<div class="box2">
				<div style="margin-top: 15px;">
					<table cellspacing="0" border="1"
						style="text-align: center; width: 100%;">
						<tr bgcolor="#A3E6DF">
							<th width="30%">订单编号</th>
							<th width="10%">收件人</th>
							<th width="15%">联系电话</th>
							<th width="15%">时间</th>
							<th width="10%">订单状态</th>
							<th width="7%">查看</th>
							<th width="7%">删除</th>
						</tr>

						<c:forEach items="${UserOrder}" var="o">
							<tr>
								<td><div style="padding: 8px 16px;overflow: hidden;
								text-overflow: ellipsis;word-break: break-all;">${o.id}</div></td>
								<td>${o.receiverName}</td>
								<td>${o.receiverPhone}</td>
								<td>${o.orderTime}</td>
								<td><b>${o.payState==1?"已支付":"未支付"}</b></td>
								<td>
									<a href="<%=response.encodeURL("OrderServlet")%>?action=infoOrder&id=${o.id}"> 
										<img alt="详情" src="images/icon2.png">
									</a>
								</td>
								<td>
									<c:if test="${o.payState==0}">
									<a href="<%=response.encodeURL("OrderServlet")%>?action=delOrder&id=${o.id}">
									<button style="color: #FF0000;" onclick='return confirm("您确认要删除本条订单？");'>X</button>
									</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
					<div style="width: 100%" align="right">
						<c:if test="${UserOrder==null||UserOrder.isEmpty()}">
							<div style="text-align: center; color: #ADADAD; padding-top: 10px;">
								😫你的订单空空如也～
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<nav style="text-align:center;">
			<ul id="pageLimit"></ul>
		</nav>
	</div>
	<!-- 3.用户个人订单详情 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
	<!-- page script start -->
	<script type="text/javascript">
	$("#pageLimit").twbsPagination({
		startPage: parseInt('${pageNo}'),
		totalPages: parseInt('${totalNo}'),
		visiblePages: 3,
		hideOnlyOnePage: false, href: false,
		first:'首页', prev: '上一页', next: '下一页', last: '尾页',
		onPageClick: function (event, page) {
			window.location.href= 
				"<%=response.encodeURL("./OrderServlet")%>"+"?action=order&pageNo=" + page;
		},initiateStartPageClick: false 
	});
	</script>
	<!-- page script end -->
</body>
</html>