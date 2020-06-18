<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>用户信息</title>
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/register.css">
	<script src="js/jquery.min.js"></script>
	<script src="js/form.js"></script>
	<script type="text/javascript">
	//头像上传
	function avatarImgurlUpload(e) {
	  var img = $(e)[0].files[0];
	  if (img == null) return;
	  var formData = new FormData();
	  formData.append("file",img);
	  formData.append("operator","userAvatar");
	  $.ajax({
	    type: 'post',
	    url: '/BookStore/UpLoadFileServlet',
	    data: formData,
	    cache: false,
	    traditional:true,
	    dataType:'json',
	    processData: false,
	    contentType: false,  //一定要设置成false
	  }).success(function (data) {
	    //设置返回的图片地址,并触发onchange事件
	    $("#avatar").attr("src",data.success).change();
	    $("input[name=avatar]").val(data.success);
	  }).error(function (err){
	    alert("图片上传失败！");
	  });
	}
	</script>
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->
	
	<!-- 3.用户个人信息详情 start -->
	<div id="register-body">
    <h3 id="register-title">个人信息详情 | <a href="<%=response.encodeURL("UserServlet?action=receipt")%>" class="secondary_link">个人地址详情</a>
     | <a href="<%=response.encodeURL("OrderServlet?action=order")%>" class="secondary_link">个人订单详情</a>
     | <a href="<%=response.encodeURL("UserServlet?action=favor")%>" class="secondary_link">个人收藏详情</a>
     </h3>
    <form action="<%=response.encodeURL("handle-user.jsp")%>" method="post" id="register-form" 
    	  onsubmit="return checkRegister();">
    	  <img height="170" alt="User Avatar" id="avatar" src="${User.avatar}" 
    	  	style="position:absolute;right:5%;">
    	  <input onchange="avatarImgurlUpload(this)" type="file" accept="image/*" size="5120"
    	  	style="position:absolute;right: -2%;top: 40%;">
    	  <input name="avatar" value="${User.avatar}" hidden="hidden">
    	  <input name="action" value="updateInfo" hidden="hidden" readonly="readonly">
    	  <input name="id" value="${User.id}" hidden="hidden" readonly="readonly">
      <p><label>邮箱：</label><input type="email" value="${User.email}" name="email" id="email"><span>请输入有效的邮箱地址</span></p>
      <p><label>用户：</label><input type="text" value="${User.username}" name="username" id="name"><span>字母数字下划线1到10位，不能是数字开头</span></p>
      <p><label>密码：</label><input type="password" value="${User.password}" name="password" id="password"><span>密码请设置6-16位字符</span></p>
      <p><label>重复密码：</label><input type="password" value="${User.password}" name="repassword" id="repassword"></p>
      <p><label>性别：</label><input type="radio" name="gender" id="male" value="男" ${User.gender eq "男" ?"checked":""}> 男
        <input type="radio" name="gender" value="女" id="female" ${User.gender eq "女"?"checked":""}> 女</p>
      <p><label>联系电话：</label><input type="number" value="${User.telephone}" name="telephone" id="phone"></p>
      <p><label>个人简介：</label><textarea name="introduce" id="introduction" cols="30" rows="5">${User.introduce}</textarea></p>
      <p><button type="submit" id="registerBtn"><img src="images/signup.gif"></button></p>
    </form>
  </div>
	<!-- 3.用户个人信息详情 end -->
	
	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>