<%@ page language="java" pageEncoding="UTF-8"%>
<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
  <a class="navbar-brand col-md-3 col-lg-2 mr-0 px-3" 
  	href="<%=response.encodeURL(request.getContextPath()+"/admin/login/home.jsp")%>">BookStore Admin
  	<!-- <span id="timeInner"></span> -->
  </a>
  <ul class="navbar-nav px-3">
    <li class="nav-item">
      <a class="nav-link text-danger" 
      	href="<%=response.encodeURL(request.getContextPath()+"/admin/LoginAdminServlet")%>?action=logout">Sign out</a>
    </li>
  </ul>
</nav>