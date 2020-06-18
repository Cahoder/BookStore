<%@page import="java.util.Calendar"%>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- navbar start -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand" href="">Filter</a>

	<div class="collapse navbar-collapse">
		<div class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto"></ul>
			<form class="form-inline my-2 my-lg-0"
				action="<%=response.encodeURL(request.getContextPath()+"/admin/SaleAdminServlet")%>" >
				<input name="action" hidden>
				<div class="input-group">
					<div class="input-group-prepend">
						<div class="input-group-text">请输入年份</div>
					</div>
					<input type="number" class="form-control" name="year" 
						max="<%=Calendar.getInstance().get(Calendar.YEAR)%>"
						pattern="[0-9]{4}" min="1970" placeholder="--YYYY--">
				</div>
				<div class="input-group">
					<div class="input-group-prepend">
						<div class="input-group-text">请输入月份</div>
					</div>
					<select class="custom-select" name="month">
						<option selected value="0">--MM--</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
					</select>
				</div>
				<div class="col-auto">
					<button type="submit" class="btn btn-success" 
						onclick="if($('select[name=month]').val() == 0&&$('input[name=year]').val()=='') return false;
							$('input[name=action]').val('filter');">搜索</button>
					<button onclick="$('input[name=action]').val('download');" 
						type="submit" class="btn btn-danger">下载</button>
				</div>
			</form>
		</div>
	</div>
</nav>
<!-- navbar end -->
<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col">商品编号</th>
			<th scope="col">商品名称</th>
			<th scope="col">商品类型</th>
			<th scope="col">商品总销量</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.ProductList eq null}">
			<jsp:include page="<%=response.encodeURL(\"/admin/SaleAdminServlet\")%>">
				<jsp:param value="show" name="action"/>
			</jsp:include>
		</c:if>
		<c:forEach items="${requestScope.ProductList}" var="product">
			<tr>
				<th>${product.product_id}</th>
				<td>${product.product_name}</td>
				<td>${product.category}</td>
				<td>${product.buynum}</td>
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
			+"?item=sale_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
</script>
<!-- page script end -->