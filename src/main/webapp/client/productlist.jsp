<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>	
<meta charset="UTF-8">
<title>商品目录</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/productlist.css">
<script src="js/jquery.min.js"></script>
<script src="js/jquery.twbsPagination.min.js"></script>
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.图书商品列表 start -->
	<div id="products">
		<c:if test="${requestScope.ProductList ne null}">
			<table>
				<caption>商品目录</caption>
				<thead>
					<tr><td colspan="4">
					<c:if test="${fn:length(ProductList) > 0}">共${fn:length(ProductList)}种商品</c:if>
					<c:if test="${fn:length(ProductList) eq 0}">抱歉无商品</c:if>
					</td></tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${ProductList}" varStatus="status">
					<c:if test="${status.index%5 eq 0 }"><tr></c:if>
					<td>
						<div class="divbookpic">
						<p>
							<a href="<%=response.encodeURL("ProductServlet")%>?action=info&id=${product.id}">
							<img alt="" src="${product.imgurl}" width="115" height="129" border="0"
							onerror="this.src='images/losing.jpg;this.οnerrοr=null'"></a>
						</p>
						<div class="divbookinfo">
							<form action="<%=response.encodeURL("handle-product.jsp")%>" method="post">
								<ul>
								  <li><small>书名：</small><input readonly="readonly" type="text" name="name" value="${product.name}"></li>
								  <li><small>售价：</small><input readonly="readonly" type="number" name="price" value="${product.price}"></li>
								  <li><small>库存：</small><input readonly="readonly" type="number" name="pnum" value="${product.pnum}"></li>
								  <li>
								  	<input type="text" name="action" value="addCart" hidden="hidden">
								  	<input type="text" name="id" value="${product.id}" hidden="hidden">
								  	<button type="submit"></button>
								  </li>
								</ul>
							</form>
						</div>
						</div>
					</td>
					<c:if test="${status.count%5 eq 0 }"></tr></c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<nav style="text-align:center;">
			<ul id="pageLimit"></ul>
		</nav>
	</div>
	<!-- 3.图书商品列表 end -->

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
				"<%=response.encodeURL("./ProductServlet")%>"+"?action=list&pageNo=" + page;
		},initiateStartPageClick: false 
	});
	</script>
	<!-- page script end -->
</body>
</html>