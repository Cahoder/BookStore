<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="editInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">商品详情</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/products/add-handle.jsp")%>" method="post">
					<input name="action" value="update" hidden>
					<div class="form-group row">
				     	<div class="form-group col-md-6">
				     		<div class="form-row">
								<label for="productId">商品编号</label>
								<input type="text" class="form-control" name="id" id="productId" readonly>
				     		</div>
				     		<div class="form-row">
								<label for="productName">商品名称</label>
								<input type="text" class="form-control" name="name" id="productName">
				     		</div>
				     		<div class="form-row">
								<label for="productCategory">商品分类</label>
								<select name="category" class="custom-select" id="productCategory" required>
									<option disabled>--请选择商品类型--</option>
									<option value="计算机">计算机</option>
									<option value="文学">文学</option>
									<option value="生活">生活</option>
									<option value="外语">外语</option>
									<option value="经管">经管</option>
									<option value="励志">励志</option>
									<option value="社科">社科</option>
									<option value="学术">学术</option>
									<option value="少儿">少儿</option>
									<option value="艺术">艺术</option>
									<option value="原版">原版</option>
									<option value="科技">科技</option>
									<option value="考试">考试</option>
									<option value="生活百科">生活百科</option>
								</select>
				     		</div>
				     		<div class="form-row">
				     			<div class="col-md-6 mb-3">
				     				<label for="productPrice">商品价格</label>
				     				<input type="number" class="form-control" id="productPrice" name="price">
    							</div>
    							<div class="col-md-6 mb-3">
				     				<label for="productPnum">商品库存</label>
				     				<input type="number" class="form-control" name="pnum" id="productPnum">
    							</div>
							</div>
						</div>
						<div class="form-group col-md-6">
							<div class="form-row">
								<label for="inputImg">商品封面</label>
				     		</div>
							<figure class="figure">
								<img src="" id="imgurl"
									class="figure-img img-fluid rounded">
								<div class="custom-file">
									<input onchange="productImgurlUpload(this)" type="file"
										accept="image/*" size="5120" class="custom-file-input"
										id="addProductImgurl">
								  	<label class="custom-file-label" for="addProductImgurl" 
								  		data-browse="上传">Update file...</label>
									<input type="text" name="imgurl" id="<%=request.getContextPath()+"/client/"%>"
										onchange="$('#imgurl').attr('src',this.id + $(this).val());" hidden>
								</div>
							</figure>
							<div class="form-row">
								<label for="productDescription">商品描述</label>
								<textarea name="description" class="form-control" rows="5"
									id="productDescription" placeholder="请输入此商品的详细介绍" >
								</textarea>
				     		</div>
						</div>
				    </div>
					<div class="form-group row">
						<button type="submit" class="btn btn-primary col-sm-12">更新</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- editInfo end -->