<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="adminDetailInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">管理员信息详情</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="<%=response.encodeURL(request.getContextPath()+"/admin/ruler/handle.jsp")%>" method="post">
					<input name="id" hidden="hidden">
					<input name="action" value="update" hidden="hidden">
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
							<input type="text" class="form-control" name="telephone" id="telephone">
						</div>
						<div class="form-group col-md-6">
							<label for="email">邮箱</label>
							<input type="text" class="form-control" name="email" id="email">
						</div>
		     		</div>
		     		<div class="form-row">
						<label for="register_time">注册时间</label>
						<input type="text" class="form-control" id="register_time" readonly>
		     		</div>
					<div class="form-group row">
						<button type="submit" class="btn btn-light col-sm-12">更新</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- editInfo end -->