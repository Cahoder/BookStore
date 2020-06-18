//商品图片上传
function productImgurlUpload(e) {
  var img = $(e)[0].files[0];
  if (img == null) return;
  var formData = new FormData();
  formData.append("file",img);
  formData.append("operator","productImg");
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
    $(e).next().next().val(data.success).change();
  }).error(function (err){
    alert("图片上传失败！");
  });

  //选择的文件名显示
  $(e).next().html(img.name + "<b>---上传完成</b>");
  
}