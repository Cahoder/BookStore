<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- addinfo start -->
<div class="modal fade" id="addInfo" tabindex="-1"
	role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">新建管理员</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/ruler/handle.jsp")%>" method="post">
					<input name="action" value="add" hidden="hidden">
				     	<div class="form-row">
			     			<div class="form-group col-md-6">
								<label for="username">用户名</label>
								<input type="text" class="form-control" name="username" id="username" required>
							</div>
							<div class="form-group col-md-6">
								<label for="password">密码</label>
								<input type="text" class="form-control" name="password" id="password" required>
							</div>
			     		</div>
			     		<div class="form-row">
				     		<div class="form-group col-md-6">
								<label for="telephone">用户电话</label>
								<input type="text" class="form-control" name="telephone" id="telephone" required>
							</div>
							<div class="form-group col-md-6">
								<label for="email">邮箱</label>
								<input type="email" class="form-control" name="email" id="email" required>
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