<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:useBean id="newProduct" class="model.Product" scope="page" />
<jsp:setProperty property="*" name="newProduct" />
<% request.setAttribute("NewProduct", newProduct); %>
<jsp:forward  page="<%=response.encodeURL(\"/admin/ProductAdminServlet\")%>"></jsp:forward>

