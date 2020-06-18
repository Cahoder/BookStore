<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- addinfo start -->
<div class="modal fade" id="addInfo" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">新建公告</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/NoticeAdminServlet")%>" method="post">
					<input name="action" value="add" hidden="hidden">
					<div class="form-group row form-control-sm">
						<label for="addNoticeName" class="col-sm-2 col-form-label">公告标题</label>
						<div class="col-sm-10">
							<input type="text" name="title" placeholder="请输入公告标题"
								class="form-control" id="addNoticeName" required>
						</div>
					</div>
					<div class="form-group row">
						<label for="addNoticeDetails"
							class="col-sm-2 col-form-label col-form-label-sm">公告内容</label>
						<div class="col-sm-10">
							<textarea name="details" class="form-control" rows="15"
								id=addNoticeDetails placeholder="请输入此公告的详细内容" required></textarea>
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