<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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
		<ul class="navbar-nav mr-auto"></ul>
		<form class="form-inline" method="get" onsubmit="return $('input[name=username]').val() != '';"
			action="<%=response.encodeURL(request.getContextPath()+"/admin/UserAdminServlet")%>">
			<input name="action" value="search" hidden="hidden">
			<div class="input-group">
				<div class="input-group-prepend">
					<div class="input-group-text">用户名</div>
				</div>
				<input name="username" class="form-control" type="search" placeholder="请输入用户名">
			</div>
			<div class="col-auto">
				<button class="btn btn-info" type="submit">搜索</button>
			</div>
		</form>
	</div>
</nav>
<!-- navbar end -->
<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col">用户ID</th>
			<th scope="col">用户名</th>
			<th scope="col">用户性别</th>
			<th scope="col">用户邮箱</th>
			<th scope="col">用户电话</th>
			<th scope="col">注册时间</th>
			<th scope="col">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.UserList eq null}">
			<jsp:include page="<%=response.encodeURL(\"/admin/UserAdminServlet\")%>">
				<jsp:param value="show" name="action"/>
			</jsp:include>
		</c:if>
		<c:forEach items="${requestScope.UserList}" var="user">
			<tr>
				<th>${user.id}</th>
				<td>${user.username}</td>
				<td>${user.gender}</td>
				<td>${user.email}</td>
				<td>${user.telephone}</td>
				<td>${user.register_time}</td>
				<td>
					<button type="button" class="btn btn-outline-info btn-sm"
						onclick="detailUserInfo('<%=response.encodeURL(request.getContextPath()
								+"/admin/UserAdminServlet")%>','${user.id}')">详细信息</button>
					<button type="button" class="btn btn-outline-success btn-sm"
						onclick="detailUserOrder('<%=response.encodeURL(request.getContextPath()
								+"/admin/UserAdminServlet")%>','${user.id}')">购物记录</button>
					<button type="button" class="btn btn-outline-danger btn-sm"
						onclick="detailUserFavor('<%=response.encodeURL(request.getContextPath()
								+"/admin/UserAdminServlet")%>','${user.id}')">收藏夹</button>
					<button type="button" class="btn btn-outline-warning btn-sm"
						onclick="detailUserCart('<%=response.encodeURL(request.getContextPath()
								+"/admin/UserAdminServlet")%>','${user.id}')">购物车</button>
				</td>
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
<!-- 静态引入用户信息详情表单 -->
	<%@ include file="detail.jsp" %>
<!-- 静态引入用户信息详情表单 -->
<!-- 静态引入用户订单详情表单 -->
	<%@ include file="order.jsp" %>
<!-- 静态引入用户订单详情表单 -->
<!-- 静态引入用户收藏夹详情表单 -->
	<%@ include file="favor.jsp" %>
<!-- 静态引入用户收藏夹详情表单 -->
<!-- 静态引入用户购物车详情表单 -->
	<%@ include file="cart.jsp" %>
<!-- 静态引入用户购物车详情表单 -->
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
			+"?item=user_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
</script>
<!-- page script end -->