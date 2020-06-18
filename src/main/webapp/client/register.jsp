<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>网上书城注册</title>
  <link rel="stylesheet" href="css/common.css">
  <link rel="stylesheet" href="css/register.css">
  <script src="js/form.js"></script>
</head>

<body>
  <!-- 1.网上书城顶部 start -->
  <%@ include file="include/head.jsp" %>
  <!-- 1.网上书城顶部 end -->
  <!-- 2.网上书城菜单列表 start -->
  <%@ include file="include/menu_search.jsp" %>
  <!-- 2.网上书城菜单列表 end -->
  <div id="register-body">
    <h3 id="register-title">新用户注册</h3>
    <form action="<%=response.encodeURL("handle-user.jsp")%>" method="post" id="register-form" 
    	  onsubmit="return checkRegister();">
    	  <input name="action" value="register" hidden="hidden">
      <p><label>邮箱：</label><input type="email" name="email" id="email"><span>请输入有效的邮箱地址</span></p>
      <p><label>用户：</label><input type="text" name="username" id="name"><span>字母数字下划线1到10位，不能是数字开头</span></p>
      <p><label>密码：</label><input type="password" name="password" id="password"><span>密码请设置6-16位字符</span></p>
      <p><label>重复密码：</label><input type="password" name="repassword" id="repassword"></p>
      <p><label>性别：</label><input type="radio" name="gender" id="male" value="男" checked> 男
        <input type="radio" name="gender" value="女" id="female"> 女</p>
      <p><label>联系电话：</label><input type="number" name="telephone" id="phone"></p>
      <p><label>个人简介：</label><textarea name="introduce" id="introduction" cols="30" rows="5"></textarea></p>
      <p><button type="submit" id="registerBtn"><img src="images/signup.gif"></button></p>
    </form>
  </div>
  <!-- 4.网上书城下方显示 start -->
  <%@ include file="include/foot.jsp" %>
  <!-- 4.网上书城下方显示 end -->
</body>

</html>