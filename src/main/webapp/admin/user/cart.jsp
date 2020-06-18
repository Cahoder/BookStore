<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="cartDetailInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">用户购物车</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form>
					<input name="action" value="update" hidden="hidden">
					<div class="table-responsive-md">
						<table class="table" name="cartItems">
							<thead>
								<tr>
									<th scope="col">商品编号</th>
									<th scope="col">商品名称</th>
									<th scope="col">单价</th>
									<th scope="col">数量</th>
									<th scope="col">小计</th>
									<th scope="col">更新时间</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<div class="form-group row">
						<!-- <button type="button" class="btn btn-light col-sm-12">更新</button> -->
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<h1 class="display-8 text-success">总计：<span id="totalPrice">0</span>元</h1>
			</div>
		</div>
	</div>
</div>
<!-- editInfo end -->