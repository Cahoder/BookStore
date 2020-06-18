<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="editInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">公告详情</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/NoticeAdminServlet")%>" method="post">
					<input name="action" value="update" hidden>
					<input name="id" hidden>
					<div class="form-group row">
						<label for="addNoticeName" 
							class="col-sm-2 col-form-label col-form-label-sm">公告标题</label>
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
						<label for="addNoticeTimes"
							class="col-sm-2 col-form-label col-form-label-sm">创建时间</label>
						<div class="col-sm-10">
							<input type="datetime"
								name="create_time" class="form-control" id="addNoticeTimes" readonly>
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