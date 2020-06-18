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
				<button class="btn btn-light my-2 my-sm-0" data-toggle="modal"
					data-target="#addInfo" type="button">新建公告</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-light my-2 my-sm-0" type="button" title="请先勾选需删除订单"
					onclick="deleteCheckedNotice('<%=response.encodeURL(request.getContextPath()
						+"/admin/NoticeAdminServlet")%>')">批量删除</button>
			</li>
		</ul>
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
			<th scope="col"><input type="checkbox"
				onclick="$('input[name=deleteIds]').prop('checked',this.checked)" />
			</th>
			<th scope="col">公告编号</th>
			<th scope="col">公告标题</th>
			<th scope="col">公告内容</th>
			<th scope="col">时间</th>
			<th scope="col">编辑</th>
			<th scope="col">删除</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.NoticeList eq null}">
			<jsp:include
				page="<%=response.encodeURL(\"/admin/NoticeAdminServlet\")%>">
				<jsp:param value="show" name="action" />
			</jsp:include>
		</c:if>
		<c:forEach items="${NoticeList}" var="notice">
			<tr>
				<th><input type="checkbox" name="deleteIds"
					value="${notice.id}" /></th>
				<th>${notice.id}</th>
				<td>${notice.title}</td>
				<td>${notice.details}</td>
				<td>${notice.create_time}</td>
				<td><button type="button" class="btn btn-outline-info btn-sm"
						onclick="detailNotice('<%=response.encodeURL(request.getContextPath()
								+"/admin/NoticeAdminServlet")%>','${notice.id}')">编辑</button></td>
				<td><button type="button" class="btn btn-danger btn-sm"
						onclick="deleteNotice('<%=response.encodeURL(request.getContextPath()
								+"/admin/NoticeAdminServlet")%>','${notice.id}')">删除</button></td>
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
<!-- 静态引入公告详情表单 -->
	<%@ include file="detail.jsp" %>
<!-- 静态引入公告详情表单 -->
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
			+"?item=notice_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
</script>
<!-- page script end -->