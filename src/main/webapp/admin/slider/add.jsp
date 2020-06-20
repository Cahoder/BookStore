<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- addinfo start -->
<div class="modal fade" id="addInfo" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">新建轮播</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/slider/handle.jsp")%>" method="post">
					<input name="action" value="add" hidden="hidden">
					<div class="form-group row">
						<label for="sliderPicture" 
							class="col-sm-2 col-form-label col-form-label-sm">轮播图片</label>
						<div class="col-sm-10">
							<figure class="figure">
								<img src="" id="newSliderPicture"
									class="figure-img img-fluid rounded">
								<div class="custom-file">
									<input onchange="sliderImgurlUpload(this)" type="file"
										accept="image/*" size="5120" class="custom-file-input">
								  	<label class="custom-file-label" for="uploadSlider" 
								  		data-browse="上传">Upload slider...</label>
									<input type="text" name="str_name" id="<%=request.getContextPath()+"/client/"%>"
										onchange="$('#newSliderPicture').attr('src',this.id + $(this).val());" 
										hidden="hidden">
								</div>
							</figure>
						</div>
					</div>
					<div class="form-group row">
						<label for="addSliderOrder"
							class="col-sm-2 col-form-label col-form-label-sm">轮播图排序</label>
						<div class="col-sm-10">
							<input name="str_order" type="number" class="form-control" 
								id="addSliderOrder" placeholder="数值越小越靠前" min="0"/>
						</div>
					</div>
					<div class="form-group row">
						<label for="addSliderLink"
							class="col-sm-2 col-form-label col-form-label-sm">轮播图超链接</label>
						<div class="col-sm-10">
							<input name="str_value" type="text" class="form-control" 
								id="addSliderLink" placeholder="默认为空,请以http://或https://开头" autocomplete="off"/>
						</div>
					</div>
					<div class="form-group row">
						<label for="addSliderShow"
							class="col-sm-2 col-form-label col-form-label-sm">是否前台展示</label>
						<div class="col-sm-10">
							<input name="is_show" hidden="hidden" value="0">
							<input id="is_show" type="checkbox" checked
								onchange="$('#editInfo').find('input[name=is_show]').val(this.checked?0:1);">
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