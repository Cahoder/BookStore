<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="editInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">轮播图详情</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/slider/handle.jsp")%>" method="post">
					<input name="action" value="update" hidden="hidden">
					<input name="id" hidden="hidden">
					<div class="form-group row">
						<label for="sliderPicture" 
							class="col-sm-2 col-form-label col-form-label-sm">轮播图片</label>
						<div class="col-sm-10">
							<figure class="figure">
								<img src="" id="sliderPicture"
									class="figure-img img-fluid rounded">
								<div class="custom-file">
									<input onchange="sliderImgurlUpload(this)" type="file"
										accept="image/*" size="5120" class="custom-file-input" >
								  	<label class="custom-file-label" for="uploadSlider" 
								  		data-browse="上传">Update file...</label>
									<input type="text" name="str_name" id="<%=request.getContextPath()+"/client/"%>"
										onchange="$('#sliderPicture').attr('src',this.id + $(this).val());" hidden="hidden">
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
							<input name="is_show" hidden="hidden">
							<input id="is_show" type="checkbox" 
								onchange="$('#editInfo').find('input[name=is_show]').val(this.checked?0:1);">
						</div>
					</div>
					<div class="form-group row">
						<label for="addTimes"
							class="col-sm-2 col-form-label col-form-label-sm">创建时间</label>
						<div class="col-sm-10">
							<input type="datetime"
								id="create_time" class="form-control" id="addTimes" readonly>
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