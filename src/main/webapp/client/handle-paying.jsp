<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>支付页面</title>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript">
	window.onload = function() {
		$('#payingModal').modal('show');
	};
</script>
</head>
<body>
	<!-- Modal -->
	<div class="modal fade" id="payingModal" data-backdrop="static"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<form action="<%=response.encodeURL("OrderServlet")%>" method="post" >
				<input name="action" value="payOrder" hidden="hidden">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">请选择支付方式</h5>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="exampleInputEmail1">订单号</label>
							<input type="text" class="form-control" name="id" value="${param.id }" 
								readonly="readonly" required="required">
						</div>
						<div class="form-check form-check-inline">
						  <img src="images/payImg/alipay.png" class="img-fluid" alt="Responsive image">
							<input class="form-check-input" type="radio"
								name="paytype" id="inlineRadio1" value="alipay" required="required">
							<label class="form-check-label" for="inlineRadio1">支付宝</label>
						</div>
						<div class="form-check form-check-inline">
							<img src="images/payImg/unionpay.png" class="img-fluid" alt="Responsive image">
							<input class="form-check-input" type="radio"
								name="paytype" id="inlineRadio2" value="unionpay"  required="required">
							<label class="form-check-label" for="inlineRadio2">银联支付</label>
						</div>
						<div class="form-check form-check-inline">
							<img src="images/payImg/wechatpay.png" class="img-fluid" alt="Responsive image">
							<input class="form-check-input" type="radio"
								name="paytype" id="inlineRadio3" value="wechatpay" required="required">
							<label class="form-check-label"	for="inlineRadio3">微信支付</label>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" onclick="history.go(-1);">取消支付</button>
						<button type="submit" class="btn btn-primary">确认支付</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- Modal -->
</body>
</html>