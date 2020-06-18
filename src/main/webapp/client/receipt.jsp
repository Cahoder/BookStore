<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户地址</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/register.css">
<link rel="stylesheet" href="css/receipt.css">
<script src="js/form.js"></script>
<script src="js/jquery.min.js"></script>
</head>
<body>
	<!-- 1.网上书城顶部 start -->
	<%@ include file="include/head.jsp"%>
	<!-- 1.网上书城顶部 end -->
	<!-- 2.网上书城菜单列表 start -->
	<%@ include file="include/menu_search.jsp"%>
	<!-- 2.网上书城菜单列表 end -->

	<!-- 3.用户个人地址详情 start -->
	<div id="register-body">
		<h3 id="register-title">
			<a href="<%=response.encodeURL("UserServlet?action=info")%>"
				class="secondary_link">个人信息详情</a> | 个人地址详情 | <a
				href="<%=response.encodeURL("OrderServlet?action=order")%>"
				class="secondary_link">个人订单详情</a>
				| <a href="<%=response.encodeURL("UserServlet?action=favor")%>" class="secondary_link">个人收藏详情</a>
		</h3>
		<form role="grid" action="<%=response.encodeURL("UserServlet")%>?action=updateReceipt" method="post"
			id="myForm" class="next-form next-medium">
			<input name="id" value="${requestScope.DefaultUserReceipt.id}" hidden="hidden" required="required">
			<input name="action" value="updateReceipt" hidden="hidden">
			<div dir="ltr" role="row"
				class="next-row next-form-item next-left has-success next-medium">
				<div dir="ltr" role="gridcell" class="next-col next-form-item-label">
					<label for="fullName" >收货人名字:</label>
				</div>
				<div dir="ltr" role="gridcell"
					class="next-col next-col-19 next-form-item-control">
					<span data-meta="Field" class="next-input next-medium">
						<input id="fullName" placeholder="请输入收件人联系方式"  name="name" type="text" 
						height="100%" autocomplete="off" value="${requestScope.DefaultUserReceipt.name }">
					</span>
				</div>
			</div>
			<div dir="ltr" role="row"
				class="next-row next-form-item next-left next-medium form-item-mobile">
				<div dir="ltr" role="gridcell" class="next-col next-form-item-label">
					<label for="fullPhone" >收货人电话:</label>
				</div>
				<div dir="ltr" role="gridcell"
					class="next-col next-col-19 next-form-item-control">
					<span data-meta="Field" class="next-input next-medium">
						<input id="fullPhone" placeholder="请输入收件人联系方式"  name="phone" type="number" 
						height="100%" autocomplete="off" value="${requestScope.DefaultUserReceipt.phone }">
					</span>
				</div>
			</div>
			<div dir="ltr" role="row"
				class="next-row next-form-item next-left next-medium form-item-mobile">
				<div dir="ltr" role="gridcell" class="next-col next-form-item-label">
					<label for="fullAddress" >详细地址:</label>
				</div>
				<div dir="ltr" role="gridcell"
					class="next-col next-col-19 next-form-item-control">
					<span data-meta="Field" class="next-input next-medium">
						<textarea class="cndzk-entrance-associate-area-textarea" name="address" id="fullAddress" rows="5"  
						 cols="40" placeholder="请输入详细地址信息">${requestScope.DefaultUserReceipt.address}</textarea>
					</span>
				</div>
			</div>
			<div dir="ltr" role="row"
				class="next-row next-form-item next-left next-medium">
				<div dir="ltr" role="gridcell"
					class="next-col next-form-item-label">
					<label> </label>
				</div>
				<div dir="ltr" role="gridcell"
					class="next-col next-col-19 next-form-item-control">
					<button type="submit" onclick="$('#myForm').find('input[name=id]').remove();"
						class="next-btn next-medium next-btn-primary">
						<span class="next-btn-helper">新增</span>
					</button>
					<button type="submit" class="next-btn next-medium next-btn-primary">
						<span class="next-btn-helper">保存</span>
					</button>
				</div>
			</div>
		</form>
		<div class="next-table next-table-medium">
			
			<table role="table">
				<colgroup>
					<col style="width: 70px;">
					<col style="width: 200px;">
					<col style="width: 140px;">
					<col style="width: 90px;">
				</colgroup>
				<thead class="next-table-header">
					<tr>
						<th rowspan="1" class="next-table-cell next-table-header-node"
							role="gridcell">
							<div class="next-table-cell-wrapper" data-next-table-col="0">收货人</div>
						</th>
						<th rowspan="1" class="next-table-cell next-table-header-node"
							role="gridcell"><div class="next-table-cell-wrapper"
								data-next-table-col="2">详细地址</div></th>
						<th rowspan="1" class="next-table-cell next-table-header-node"
							role="gridcell"><div class="next-table-cell-wrapper"
								data-next-table-col="4">电话/手机</div></th>
						<th rowspan="1" class="next-table-cell next-table-header-node"
							role="gridcell"><div class="next-table-cell-wrapper"
								data-next-table-col="5">操作</div></th>
					</tr>
				</thead>
				<tbody class="next-table-body">
					<c:forEach var="receipt" items="${requestScope.UserReceipt}">
					<tr class="next-table-row first" role="row">
						<td data-next-table-col="0" data-next-table-row="0"
							class="next-table-cell first" role="gridcell"><div
								class="next-table-cell-wrapper" data-next-table-row="0">${receipt.name }</div></td>
						<td data-next-table-col="2" data-next-table-row="0"
							class="next-table-cell" role="gridcell"><div
								class="next-table-cell-wrapper" data-next-table-row="0">${receipt.address }</div></td>
						<td data-next-table-col="4" data-next-table-row="0"
							class="next-table-cell" role="gridcell"><div
								class="next-table-cell-wrapper" data-next-table-row="0">
								<span>${receipt.phone }<br></span>
							</div></td>
						<td data-next-table-col="5" data-next-table-row="0"
							class="next-table-cell" role="gridcell"><div
								class="next-table-cell-wrapper" data-next-table-row="0">
								<div class="tAction">
									<a class="t-change"	style="color: red;" target="_self" 
									href="<%=response.encodeURL("UserServlet")%>?action=receipt&id=${receipt.id}" >修改</a>
										<span class="t-line">|</span>
									<a class="t-delete"	target="_self" onclick=" return confirm('确定删除此条地址？')" 
									href="<%=response.encodeURL("UserServlet")%>?action=deleteReceipt&id=${receipt.id}">删除</a>
								</div>
							</div></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>
	<!-- 3.用户个人地址详情 end -->

	<!-- 4.网上书城下方显示 start -->
	<%@ include file="include/foot.jsp"%>
	<!-- 4.网上书城下方显示 end -->
</body>
</html>