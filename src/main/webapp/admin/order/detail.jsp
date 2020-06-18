<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- editInfo start -->
<div class="modal fade" id="editInfo" data-backdrop="static"
	tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">订单详情</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="#" method="post">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputId">订单编号</label> <input type="text"
								class="form-control" id="inputId" name="id" readonly>
						</div>
						<div class="form-group col-md-6">
							<label for="inputUser">所属用户</label> <input type="text"
								class="form-control" name="username" id="inputUser" readonly>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputPeople">收件人</label> <input type="text"
								class="form-control" name="receiverName" id="inputPeople"
								readonly>
						</div>
						<div class="form-group col-md-6">
							<label for="inputPhone">联系电话</label> <input type="text"
								class="form-control" id="inputPhone" name="receiverPhone"
								readonly>
						</div>
					</div>
					<div class="form-group">
						<label for="inputAddress">送货地址</label> <input type="text"
							class="form-control" id="inputAddress" name="receiverAddress"
							readonly>
					</div>
					<div class="form-row">
						<div class="form-group col-md-4">
							<label for="inputCity">订单总价</label> <input type="number"
								name="money" class="form-control" id="inputCity" readonly>
						</div>
						<div class="form-group col-md-4">
							<label for="inputState">支付状态</label> <select id="inputState"
								name="payState" class="form-control">
								<option value="0">未支付</option>
								<option value="1">已支付</option>
							</select>
						</div>
						<div class="form-group col-md-4">
							<label for="inputZip">下单时间</label> <input type="datetime"
								name="orderTime" class="form-control" id="inputZip" readonly>
						</div>
					</div>
					<div class="form-group">
						<label for="inputAddress">订单详情</label>
						<table class="table table-sm table-hover" name="orderItems">
							<thead>
								<tr>
									<th scope="col">序号</th>
									<th scope="col">商品编号</th>
									<th scope="col">商品名称</th>
									<th scope="col">商品价格</th>
									<th scope="col">购买数量</th>
									<th scope="col">商品类型</th>
									<th scope="col">商品描述</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
					<button type="button" class="btn btn-primary">更新</button>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- editInfo end -->