<%@ page language="java" pageEncoding="UTF-8"%>
<div id="menu_search">
  <ul>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=文学" >文学</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=生活">生活</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=计算机">计算机</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=外语">外语</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=经管">经管</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=励志">励志</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=社科">社科</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=学术">学术</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=少儿">少儿</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=艺术">艺术</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=原版">原版</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=科技">科技</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=考试">考试</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list&category=生活百科">生活百科</a></li>
    <li><a href="<%=response.encodeURL("ProductServlet")%>?action=list" style="color:yellowgreen;">全部商品目录</a></li>
  </ul>
  <div>
    <form action="<%=response.encodeURL("MenuSearchServlet")%>" method="get" 
    	  onsubmit="return document.getElementById('searchInp').value.trim() != ''">
	   <span id="book_search">
	     Search：<input type="text" id="searchInp" name="textfield" placeholder="请输入书名">
	     &nbsp;<button type="submit"><img src="images/serchbutton.gif"></button>
	   </span>
    </form>
  </div>
</div>