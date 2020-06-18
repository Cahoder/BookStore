<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- addinfo start -->
<div class="modal fade" id="addInfo" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">新建商品</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/products/add-handle.jsp")%>" method="post">
					<input name="action" value="add" hidden>
					<div class="form-group row form-control-sm">
						<label for="addProductName" class="col-sm-2 col-form-label">商品名</label>
						<div class="col-sm-10">
							<input type="text" name="name" placeholder="请输入商品名称"
								class="form-control" id="addProductName" required>
						</div>
					</div>
					<div class="form-group row form-control-sm">
						<label for="addProductRealPrice" class="col-sm-2 col-form-label">商品价格</label>
						<div class="col-sm-10">
							<input name="price" type="number" placeholder="请输入商品价格" min="0"
								class="form-control" id="addProductRealPrice" required>
						</div>
					</div>
					<div class="form-group row form-control-sm">
						<label for="addProductCategory" class="col-sm-2 col-form-label">商品类型</label>
						<div class="col-sm-10">
							<select name="category" class="custom-select"
								id="addProductCategory" required>
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
					</div>
					<div class="form-group row">
						<label for="addProductDesc"
							class="col-sm-2 col-form-label col-form-label-sm">商品描述</label>
						<div class="col-sm-10">
							<textarea name="description" class="form-control"
								id="addProductDesc" placeholder="请输入此商品的详细描述" required></textarea>
						</div>
					</div>
					<div class="form-group row form-control-sm">
						<label for="addProductNum" class="col-sm-2 col-form-label">商品库存</label>
						<div class="col-sm-10">
							<input type="number" name="pnum" placeholder="请输入商品库存" min="0"
								class="form-control" id="addProductNum" required>
						</div>
					</div>
					<div class="form-group row form-control-sm">
						<label for="addProductImgurl" class="col-sm-2 col-form-label">商品图片连接</label>
						<div class="col-sm-10 custom-file">
							<input onchange="productImgurlUpload(this)" type="file" accept="image/*" size="5120"
								class="custom-file-input" id="addProductImgurl" >
							<label class="custom-file-label" data-browse="上传" for="addProductImgurl">Choose	file...</label>
							<input type="text" name="imgurl" hidden>
						</div>
					</div>
					<div class="form-group row">
						<button type="reset" class="btn btn-default col-sm-6">重置</button>
						<button type="submit" class="btn btn-primary col-sm-6">提交</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- addinfo end -->