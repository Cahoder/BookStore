<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<jsp:useBean id="newSlider" class="model.Str" scope="page" />
<jsp:setProperty property="*" name="newSlider" />
<% request.setAttribute("NewSlider", newSlider); %>
<jsp:forward  page="<%=response.encodeURL(\"/admin/SliderAdminServlet\")%>"></jsp:forward>