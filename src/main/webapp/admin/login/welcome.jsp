<%@ page language="java" pageEncoding="UTF-8"%>
<form
	action="<%=response.encodeURL(request.getContextPath()+"/admin/ruler/handle.jsp")%>"
	method="post">
	<input name="action" value="updateSelf" hidden="hidden"
		readonly="readonly" required="required"> <input name="id"
		hidden="hidden" value="${sessionScope.admin.id }" readonly="readonly"
		required="required">
	<div class="form-group row">
		<div class="form-group col-md-6">
			<div class="form-row">
				<label for="username">用户名</label> <input type="text" autocomplete="off"
					class="form-control" value="${sessionScope.admin.username }"
					name="username" id="username" readonly required="required">
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="gender">性别</label> <input type="text" autocomplete="off"
						class="form-control" value="${sessionScope.admin.gender }"
						name="gender" id="gender">
				</div>
				<div class="form-group col-md-6">
					<label for="email">邮箱</label> <input type="email" autocomplete="off"
						class="form-control" value="${sessionScope.admin.email }"
						name="email" id="email">
				</div>
			</div>
			<div class="form-row">
				<label for="telephone">用户电话</label> <input type="text" autocomplete="off"
					class="form-control" value="${sessionScope.admin.telephone }"
					name="telephone" id="telephone">
			</div>
			<div class="form-row">
				<label for="register_time">注册时间</label> <input type="datetime" autocomplete="off"
					class="form-control" value="${sessionScope.admin.register_time }"
					id="register_time" readonly>
			</div>
		</div>
		<div class="form-group col-md-6">
			<label for="introduce">个人简介</label>
			<div>
				<textarea name="introduce" cols="50" class="form-control" autocomplete="off"
					id="introduce" placeholder="个人简介">${sessionScope.admin.introduce }</textarea>
			</div>
			<label for="role">角色</label>
			<div>
				<input type="text" class="form-control" autocomplete="off"
					value="${sessionScope.admin.role }" id="role" disabled="disabled">
			</div>
			<label for="password">密码</label>
			<div>
				<input type="text" class="form-control" name="password"
					value="${sessionScope.admin.password }" id="password"
					 autocomplete="off" required="required">
			</div>
		</div>
	</div>
	<div class="form-group row">
		<button type="submit" class="btn btn-light col-sm-12">更新</button>
	</div>
</form>