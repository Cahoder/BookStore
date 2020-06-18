<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<jsp:useBean id="newAdmin" class="com.model.User" scope="page" />
<jsp:setProperty property="*" name="newAdmin" />
<% request.setAttribute("NewAdmin", newAdmin); %>
<jsp:forward  page="<%=response.encodeURL(\"/admin/AdministratorAdmin\")%>"></jsp:forward>
    