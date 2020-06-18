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
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<button class="btn btn-light my-2 my-sm-0" data-toggle="modal"
					data-target="#addInfo" type="button">新建管理员</button>
			</li>
		</ul>
		<form class="form-inline" method="get" onsubmit="return $('input[name=username]').val() != '';"
			action="<%=response.encodeURL(request.getContextPath()+"/admin/AdministratorAdmin")%>">
			<input name="action" value="search" hidden="hidden">
			<div class="input-group">
				<div class="input-group-prepend">
					<div class="input-group-text">管理员</div>
				</div>
				<input name="username" class="form-control" type="search" placeholder="请输入管理员用户名">
			</div>
			<div class="col-auto">
				<button class="btn btn-info" type="submit">搜索</button>
			</div>
		</form>
	</div>
</nav>
<!-- navbar end -->
<!-- 静态引入添加表单 -->
	<%@ include file="add.jsp" %>
<!-- 静态引入添加表单 -->
<!-- table start -->
<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col">管理员ID</th>
			<th scope="col">管理员名</th>
			<th scope="col">管理员邮箱</th>
			<th scope="col">管理员电话</th>
			<th scope="col">创建时间</th>
			<th scope="col">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.AdminList eq null}">
			<jsp:include page="<%=response.encodeURL(\"/admin/AdministratorAdmin\")%>">
				<jsp:param value="show" name="action"/>
			</jsp:include>
		</c:if>
		<c:forEach items="${requestScope.AdminList}" var="admin">
			<tr>
				<th>${admin.id}</th>
				<td>${admin.username}</td>
				<td>${admin.email}</td>
				<td>${admin.telephone}</td>
				<td>${admin.register_time}</td>
				<td>
					<button type="button" class="btn btn-outline-info btn-sm"
						onclick="detailAdminInfo('<%=response.encodeURL(request.getContextPath()
								+"/admin/AdministratorAdmin")%>','${admin.id}')">详细信息</button>
					<button type="button" class="btn btn-danger btn-sm"
						onclick="detailAdminInfo('<%=response.encodeURL(request.getContextPath()
								+"/admin/AdministratorAdmin")%>','${admin.id}')">删除</button>
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
<!-- 静态引入管理员信息详情表单 -->
	<%@ include file="detail.jsp" %>
<!-- 静态引入管理员信息详情表单 -->
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
			+"?item=admin_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
</script>
<!-- page script end -->