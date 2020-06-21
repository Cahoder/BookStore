<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

<jsp:useBean id="Product" class="model.Product" scope="page" />
<jsp:setProperty property="*" name="Product" />
<% request.setAttribute("Product", Product); %>
<jsp:forward  page="<%=response.encodeURL(\"/client/CartServlet\")%>"></jsp:forward>