<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>

<jsp:useBean id="newUser" class="model.User" scope="page" />
<jsp:setProperty property="*" name="newUser" />
<% request.setAttribute("NewUser", newUser); %>
<jsp:forward  page="<%=response.encodeURL(\"/client/UserServlet\")%>"></jsp:forward>