<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header">
  <ul>
    <li>
    	<c:if test="${sessionScope.user ne null}">
    		<a href="<%=response.encodeURL("CartServlet?action=cart")%>" >
    		<img style="margin-bottom:-3px;" src="images/cart.gif"> 购物车|</a>
    	</c:if>
    	<c:if test="${sessionScope.user eq null}">
    		<a href="<%=response.encodeURL("login.jsp")%>" >
    		<img style="margin-bottom:-3px;" src="images/cart.gif"> 购物车|</a>
    	</c:if>
    </li>
    <li><a href="#">帮助中心|</a></li>
    <li>
    	<c:if test="${sessionScope.user ne null}">
    		<a href="<%=response.encodeURL("UserServlet?action=info")%>">我的账户|</a>
    	</c:if>
    	<c:if test="${sessionScope.user eq null}">
    		<a href="<%=response.encodeURL("login.jsp")%>">我的账户|</a>
    	</c:if>
    </li>
    <li>
    	<c:if test="${sessionScope.user ne null}">
    		<a href="<%=response.encodeURL("LoginServlet?action=logout")%>">用户退出</a>
    	</c:if>
    	<c:if test="${sessionScope.user eq null}">
    		<a href="<%=response.encodeURL("register.jsp")%>">新用户注册</a>
    	</c:if>
    </li>
  </ul>
  <c:if test="${sessionScope.user ne null}">
  	 <span id="welcome-info">欢迎您：${sessionScope.user.username}</span>
  </c:if>
  <p>
  	<a href="<%=response.encodeURL("./")%>"><img src="images/logo.jpg" width="12%"></a>
  </p>
  <br/>
</div>