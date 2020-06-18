<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="userDetailInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">用户信息详情</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="" method="post">
					<input name="action" value="update" hidden="hidden">
					<input name="id" hidden="hidden">
					<div class="form-group row">
				     	<div class="form-group col-md-6">
				     		<div class="form-row">
								<label for="username">用户名</label>
								<input type="text" class="form-control" name="username" id="username" readonly>
				     		</div>
				     		<div class="form-row">
					     		<div class="form-group col-md-6">
									<label for="gender">性别</label>
									<input type="text" class="form-control" name="gender" id="gender" readonly>
								</div>
								<div class="form-group col-md-6">
									<label for="email">邮箱</label>
									<input type="text" class="form-control" name="email" id="email" readonly>
								</div>
				     		</div>
				     		<div class="form-row">
								<label for="telephone">用户电话</label>
								<input type="text" class="form-control" name="telephone" id="telephone" readonly>
				     		</div>
				     		<div class="form-row">
								<label for="register_time">注册时间</label>
								<input type="text" class="form-control" name="register_time" id="register_time" readonly>
				     		</div>
						</div>
						<div class="form-group col-md-6">
							<div class="form-row">
								<label for="inputImg">用户头像</label>
				     		</div>
				     		<div class="card-group">
								<div class="card">
								  <input name="avatar" hidden="hidden" id="<%=request.getContextPath() + "/client/"%>"
								  	onchange="$('#avatar').attr('src',this.id + $(this).val());">
								  <img id="avatar" class="card-img-top"
								  	onerror="this.src='<%=request.getContextPath() + "/admin/images/losing.jpg"%>;this.οnerrοr=null'" 
								  	alt="User Avatar...">
								  <div class="card-body">
								    <p class="card-text" id="introduce">Introduce...</p>
								  </div>
								</div>
							</div>
						</div>
				    </div>
					<div class="form-group row">
						<input class="form-control" type="text" placeholder="用户快递收件信息" readonly>
						<table class="table table-sm table-hover" name="userReceipt">
							<thead>
								<tr>
									<th scope="col">编号</th>
									<th scope="col">收件人</th>
									<th scope="col">收件地址</th>
									<th scope="col">收件电话</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<div class="form-group row">
						<button type="button" class="btn btn-light col-sm-12">更新</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- editInfo end -->