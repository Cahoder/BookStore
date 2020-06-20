<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>网上书城</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/index.css">
	<link rel="stylesheet" href="css/autoplay.css">
	<script src="js/autoplay.js"></script>
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp" %>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp" %>
	<!-- 2.网上书城菜单列表 end -->
	<!-- 3.图书商场首页轮播图 start -->
	<div id="box_autoplay">
		<div class="list">
			<ul>
				<c:if test="${requestScope.Notice eq null}">
					<jsp:include page="<%=response.encodeURL(\"./NoticeServlet\")%>"></jsp:include>
				</c:if>
				<c:forEach items="${requestScope.Slider}" var="slider" varStatus="status">
					<li><a <c:if test="${slider.str_value ne NULL}">href="${slider.str_value }"</c:if> target="_blank">
						<img src="${slider.str_name }" width="900" height="335" 
							onerror="this.src='images/ad/losing.jpg;this.οnerrοr=null'">
					</a></li>
				</c:forEach>
				<c:if test="${requestScope.Slider eq null || fn:length(requestScope.Slider) eq 0}">
					<li><img src="images/ad/index_ad1.jpg" width="900" height="335"></li>
					<li><img src="images/ad/index_ad2.jpg" width="900" height="335"></li>
					<li><img src="images/ad/index_ad3.jpg" width="900" height="335"></li>
				</c:if>
			</ul>
		</div>
	</div>
	<!-- 3.图书商场首页轮播图 end -->
	<!-- 4.图书商场信息栏 start -->
	<div id="information">
		<table>
			<tr>
				<td width="50%"><img src="images/billboard.gif"></td>
				<td width="50%"><img src="images/hottitle.gif"></td>
			</tr>
			<tr>
				<td class="billboard_info">
					<c:if test="${requestScope.Notice eq null || fn:length(requestScope.Notice) eq 0}">
						<b>近期无公告！</b>
					</c:if>
					<c:if test="${requestScope.Notice ne null && fn:length(requestScope.Notice) gt 0}">
						<p style="text-align: right;">
							<small><a href="#" 
								onclick="document.getElementById('Notices').removeAttribute('hidden');
								document.getElementById('Notice').setAttribute('hidden',true);">更多>></a>
							</small>
						</p>
						<div id="Notice">
							<h4>尊敬的网上书城用户：</h4>
							<p><b>标题：</b>${Notice[requestScope.ni].title}</p>
							<p><b>内容：</b>${Notice[requestScope.ni].details}</p>
							<p><b>日期：</b>${Notice[requestScope.ni].create_time}</p>
						</div>
						<ul type="disc" id="Notices" hidden="hidden">
							<c:forEach items="${requestScope.Notice}" var="notice" varStatus="status">
								<li>
									<a href="<%=response.encodeURL("./")%>?ni=${status.index}">
									<fmt:formatDate type="date" value="${notice.create_time}"/> --- ${notice.title}
									</a>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</td>
				<td class="hottitle_info">
					<ul type="disc">
					<c:if test="${requestScope.Sale ne null}">
						<c:forEach items="${requestScope.Sale}" var="sale">
							<li><a href="<%=response.encodeURL("ProductServlet")%>?action=info&id=${sale.product_id}">
							《${sale.product_name }》</a></li>
						</c:forEach>
					</c:if>
					<c:if test="${requestScope.Sale eq null}">
						<li><a href="#">《微观经济学》</a></li>
						<li><a href="#">《操作系统》</a></li>
						<li><a href="#">《数据结构与算法》</a></li>
						<li><a href="#">《计算机组成原理》</a></li>
						<li><a href="#">《计算机网络》</a></li>
						<li><a href="#">《图解TCP/IP》</a></li>
					</c:if>
					</ul>
				</td>
			</tr>
		</table>
	</div>
	<!-- 4.图书商场信息栏 end -->
	<!-- 5.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp" %>
	<!-- 5.网上书城下方显示 end -->
</body>
</html>