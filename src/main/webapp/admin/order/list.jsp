<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- navbar start -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand" href="">Filter</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<button class="btn btn-light my-2 my-sm-0" type="button" title="请先勾选需删除订单"
					onclick="deleteCheckedOrder('<%=response.encodeURL(request.getContextPath()
						+"/admin/OrderAdminServlet")%>')">批量删除</button>
			</li>
		</ul>
		<form class="form-inline" method="get"
			onsubmit="return $('input[name=receiverName]').val() != '' || $('input[name=id]').val()!='';"
			action="<%=response.encodeURL(request.getContextPath()+"/admin/OrderAdminServlet")%>">
			<input name="action" value="search" hidden>
			<div class="input-group col-md-5">
				<div class="input-group-prepend">
					<div class="input-group-text">收件人</div>
				</div>
				<input name="receiverName" class="form-control" type="search" placeholder="请输入收件人姓名">
			</div>
			<div class="input-group col-md-5">
				<div class="input-group-prepend">
					<div class="input-group-text">订单编号</div>
				</div>
				<input name="id" class="form-control" type="search" placeholder="请输入订单编号">
			</div>
			<button class="btn btn-info" type="submit">搜索</button>
		</form>
	</div>
</nav>
<!-- navbar end -->
<!-- table start -->
<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col"><input type="checkbox"
				onclick="$('input[name=deleteIds]').prop('checked',this.checked)" />
			</th>
			<th scope="col">订单编号</th>
			<th scope="col">收件人</th>
			<th scope="col">地址</th>
			<th scope="col">联系电话</th>
			<th scope="col">总价</th>
			<th scope="col">所属用户</th>
			<th scope="col">订单状态</th>
			<th scope="col">查看</th>
			<th scope="col">删除</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.OrderList eq null}">
			<jsp:include
				page="<%=response.encodeURL(\"/admin/OrderAdminServlet\")%>">
				<jsp:param value="show" name="action" />
			</jsp:include>
		</c:if>
		<c:forEach items="${OrderList}" var="order">
			<tr>
				<th><input type="checkbox" name="deleteIds"
					value="${order.id}" /></th>
				<th>${order.id}</th>
				<td>${order.receiverName}</td>
				<td>${order.receiverAddress}</td>
				<td>${order.receiverPhone}</td>
				<td>${order.money}</td>
				<td>${order.user.username}</td>
				<td>
					<c:if test="${order.payState eq 0}">未支付</c:if>
					<c:if test="${order.payState eq 1}">已支付</c:if>
				</td>
				<td><button type="button" class="btn btn-outline-info btn-sm"
						onclick="detailOrder('<%=response.encodeURL(request.getContextPath()
								+"/admin/OrderAdminServlet")%>','${order.id}')">订单详情</button></td>
				<td><button type="button" class="btn btn-danger btn-sm"
						onclick="deleteOrder('<%=response.encodeURL(request.getContextPath()
								+"/admin/OrderAdminServlet")%>','${order.id}')">删除</button></td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="17">
				<nav aria-label="">
					<ul id="pageLimit" class="pagination justify-content-center"></ul>
				</nav>
			</td>
		</tr>
	</tfoot>
</table>
<!-- table end -->
<!-- 静态引入订单详情表单 -->
	<%@ include file="detail.jsp" %>
<!-- 静态引入订单详情表单 -->
<!-- page script start -->
<script type="text/javascript">
	$('#pageLimit').twbsPagination({
		startPage: parseInt('${pageNo}'),
		totalPages: parseInt('${totalNo}'),
		visiblePages: 3,
		hideOnlyOnePage: false, href: false,
		first:'首页', prev: '上一页', next: '下一页', last: '尾页',
		onPageClick: function (event, page) {
			window.location.href= 
			"<%=response.encodeURL(request.getContextPath()+"/admin/login/home.jsp")%>"
			+"?item=order_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
</script>
<!-- page script end -->