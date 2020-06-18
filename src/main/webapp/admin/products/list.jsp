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
					data-target="#addInfo" type="button">新建商品</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-light my-2 my-sm-0" type="button" title="请先勾选需删除商品"
					onclick="deleteCheckedProduct('<%=response.encodeURL(request.getContextPath()
						+"/admin/ProductAdminServlet")%>')">批量删除</button>
			</li>
		</ul>
	</div>
	<!-- 静态引入添加表单 -->
		<%@ include file="add.jsp" %>
	<!-- 静态引入添加表单 -->
</nav>
<div class="card">
	<form class="form-inline my-2 my-lg-1" 
		onsubmit="if(($('input[name=min]').val() ==''||$('input[name=max]').val()=='')
				&&$('input[name=category]').val() ==''&&$('input[name=id]').val() ==''
				&&$('input[name=pname]').val() =='') return false;"
		action="<%=response.encodeURL(request.getContextPath()+"/admin/ProductAdminServlet")%>" method="get">
		<input name="action" value="filter" hidden>
		<div class="input-group">
			<div class="input-group-prepend">
				<div class="input-group-text">商品类别</div>
			</div>
			<input class="form-control" name="category" type="search" placeholder="请输入商品类别">
		</div>
		<div class="input-group">
			<div class="input-group-prepend">
				<div class="input-group-text">商品编号</div>
			</div>
			<input class="form-control" name="id" type="search" placeholder="请输入商品编号">
		</div>
		<div class="input-group">
			<div class="input-group-prepend">
				<div class="input-group-text">商品名称</div>
			</div>
			<input class="form-control" name="pname" type="search" placeholder="请输入商品名称">
		</div>
		<div class="input-group">
			<div class="input-group-prepend">
				<div class="input-group-text">价格区间</div>
			</div>
			<input type="number" name="min" min="0" class="form-control" placeholder="最小值">
			<div class="input-group-append">
				<span class="input-group-text">.00</span>
			</div>
		</div>
		-<input type="number" name="max" min="0" class="form-control" placeholder="最大值">
		<div class="input-group-append">
			<span class="input-group-text">.00</span>
		</div>
		<button class="btn btn-info" type="submit">搜索</button>
	</form>
</div>
<!-- navbar end -->
<!-- table start -->
<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col">
				<input type="checkbox" onclick="$('input[name=deleteIds]').prop('checked',this.checked)"/>
			</th>
			<th scope="col">商品编号</th>
			<th scope="col">商品名称</th>
			<th scope="col">商品价格</th>
			<th scope="col">商品数量</th>
			<th scope="col">商品类别</th>
			<th scope="col">编辑</th>
			<th scope="col">删除</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.ProductList eq null}">
			<jsp:include page="<%=response.encodeURL(\"/admin/ProductAdminServlet\")%>">
				<jsp:param value="show" name="action"/>
			</jsp:include>
		</c:if>
		<c:forEach items="${ProductList}" var="product">
			<tr>
				<th><input type="checkbox" name="deleteIds" value="${product.id}" /></th>
				<th>${product.id}</th>
				<td>${product.name}</td>
				<td>${product.price}</td>
				<td>${product.pnum}</td>
				<td>${product.category}</td>
				<td><button type="button" class="btn btn-outline-info btn-sm"
						onclick="detailProduct('<%=response.encodeURL(request.getContextPath()
								+"/admin/ProductAdminServlet")%>','${product.id}')">编辑</button></td>
				<td><button type="button" class="btn btn-danger btn-sm"
						onclick="deleteProduct('<%=response.encodeURL(request.getContextPath()
								+"/admin/ProductAdminServlet")%>','${product.id}')">删除</button></td>
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
<!-- 静态引入商品详情表单 -->
	<%@ include file="detail.jsp" %>
<!-- 静态引入商品详情表单 -->
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
			+"?item=product_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
</script>
<!-- page script end -->