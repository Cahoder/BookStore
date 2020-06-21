//ajax用户信息详情查看
function detailUserInfo(upd_url,upd_id){
	var datas = {};
	$.ajax({
		url:upd_url,
		type:"POST",
		dataType:"json",
		success:function(data,textStatus){
			datas = data;
		},
		data:{
			action:"detailInfo",
			id: upd_id
		}
	});
	setTimeout(function(){
		$('#userDetailInfo').find("input[name=id]").val(datas.id);
		$('#userDetailInfo').find("input[name=username]").val(datas.username);
		$('#userDetailInfo').find("input[name=gender]").val(datas.gender);
		$('#userDetailInfo').find("p[id=introduce]").html(datas.introduce);
		$('#userDetailInfo').find("input[name=register_time]")
				.val(new Date(datas.register_time).toLocaleDateString().replace(/\//g, "-") 
				+ " " + new Date(datas.register_time).toTimeString().substr(0, 8));
		$('#userDetailInfo').find("input[name=telephone]").val(datas.telephone);
		$('#userDetailInfo').find("input[name=email]").val(datas.email);
		$('#userDetailInfo').find("input[name=avatar]").val(datas.avatar).change();
		
		$('#userDetailInfo').find("table[name=userReceipt] tbody").html("");
		jQuery.each(datas.user_receipt, function(i, val) {
			var tr = "<tr><th scope='row'>"+(i+1)+"</th>" +
					"<td>"+val.name+"</td>" +
					"<td>"+val.address+"</td>" +
					"<td>"+val.phone+"</td>" +
					"</tr>";
			$('#userDetailInfo').find("table[name=userReceipt] tbody").append(tr);
		});
		
	}, 1000);
	$('#userDetailInfo').modal('show');
}

//ajax用户订单详情查看
function detailUserOrder(upd_url,upd_id){
	var datas = [];
	$.ajax({
		url:upd_url,
		type:"POST",
		dataType:"json",
		success:function(data,textStatus){
			datas = data;
		},
		data:{
			action:"detailOrder",
			id: upd_id
		}
	});
	setTimeout(function(){
		if($('#detailPageLimit').data("twbs-pagination")){
            $('#detailPageLimit').twbsPagination('destroy');
        }
		$('#detailPageLimit').twbsPagination({
			startPage: 1,
			totalPages: datas.length?datas.length:1,
			visiblePages: 3,
			hideOnlyOnePage: false, href: false,
			first:'首条', prev: '上一条', next: '下一条', last: '尾条',
			onPageClick: function (event, page) {
				if(!datas || typeof(datas)=="undefined" || datas==0 || datas.length==0) {
					$('#orderDetailInfo').find("input[name=id]").val('');
					$('#orderDetailInfo').find("input[name=receiverAddress]").val('');
					$('#orderDetailInfo').find("input[name=receiverPhone]").val('');
					$('#orderDetailInfo').find("input[name=receiverName]").val('');
					$('#orderDetailInfo').find("input[name=username]").val('');
					$('#orderDetailInfo').find("input[name=money]").val('');
					$('#orderDetailInfo').find("select[name=payState]").find("option[value=未支付]").prop("selected", true);
					$('#orderDetailInfo').find("input[name=orderTime]").val('');
					$('#orderDetailInfo').find("table[name=orderItems] tbody").html("");
					alert("该用户无订单记录！");
					return;
				}
				if(page>datas.length) return;
				$('#orderDetailInfo').find("input[name=id]").val(datas[page-1].id);
				$('#orderDetailInfo').find("input[name=receiverAddress]").val(datas[page-1].receiverAddress);
				$('#orderDetailInfo').find("input[name=receiverPhone]").val(datas[page-1].receiverPhone);
				$('#orderDetailInfo').find("input[name=receiverName]").val(datas[page-1].receiverName);
				$('#orderDetailInfo').find("input[name=username]").val(datas[page-1].user.username);
				$('#orderDetailInfo').find("input[name=money]").val(datas[page-1].money);
				$('#orderDetailInfo').find("select[name=payState]").find("option[value="+datas[page-1].payState+"]").prop("selected", true);
				$('#orderDetailInfo').find("input[name=orderTime]")
					.val(new Date(datas[page-1].orderTime).toLocaleDateString().replace(/\//g, "-") 
					+ " " + new Date(datas[page-1].orderTime).toTimeString().substr(0, 8));
				$('#orderDetailInfo').find("table[name=orderItems] tbody").html("");
				jQuery.each(datas[page-1].orderItems, function(i, val) {
					var tr = "<tr><th scope='row'>"+(i+1)+"</th>" +
							"<td>"+val.product.id+"</td>" +
							"<td>"+val.product.name+"</td>" +
							"<td>"+val.product.price+"</td>" +
							"<td>"+val.product.pnum+"</td>" +
							"<td>"+val.product.category+"</td>" +
							"<td>"+val.product.description+"</td>" +
							"</tr>";
					$('#orderDetailInfo').find("table[name=orderItems] tbody").append(tr);
				});
			},initiateStartPageClick: true 
		});
	}, 1000);
	$('#orderDetailInfo').modal('show');
}

//ajax用户购物车详情查看
function detailUserCart(upd_url,upd_id){
	var datas = [];
	$.ajax({
		url:upd_url,
		type:"POST",
		dataType:"json",
		success:function(data,textStatus){
			datas = data;
		},
		error:function(data,textStatus){},
		data:{
			action:"detailCart",
			id: upd_id
		}
	});
	setTimeout(function(){
		$('#cartDetailInfo').find("table[name=cartItems] tbody").html("");
		if(!datas || datas.length < 1){
			var tr = "<tr><td colspan='6' class='text-center text-secondary'>---该用户购物车为空---</td></tr>";
			$('#cartDetailInfo').find("table[name=cartItems] tbody").append(tr);
			$('#cartDetailInfo').find("#totalPrice").html(0);
			return;
		}
		var totalPrice = 0;
		jQuery.each(datas, function(i, val) {
			var subTotal = val.product.price * val.addnum;
			var updateTime = new Date(val.update_time?val.update_time:val.create_time).toLocaleDateString().replace(/\//g, "-") 
			+ " " + new Date(val.update_time?val.update_time:val.create_time).toTimeString().substr(0, 8);
			var tr = "<tr>" +
					"<th scope='row'>"+val.product.id+"</th>" +
					"<td>"+val.product.name+"</td>" +
					"<td>"+val.product.price+"</td>" +
					"<td>"+val.addnum+"</td>" +
					"<td>"+subTotal+"</td>" +
					"<td>"+updateTime+"</td>" +
					"</tr>";
			$('#cartDetailInfo').find("table[name=cartItems] tbody").append(tr);
			totalPrice+=subTotal;
		});
		$('#cartDetailInfo').find("#totalPrice").html(totalPrice);
	}, 1000);
	$('#cartDetailInfo').modal('show');
}

//ajax用户收藏夹详情查看
function detailUserFavor(upd_url,upd_id){
	var datas = [];
	$.ajax({
		url:upd_url,
		type:"POST",
		dataType:"json",
		success:function(data,textStatus){
			datas = data;
		},
		data:{
			action:"detailFavor",
			id: upd_id
		}
	});
	setTimeout(function(){
		$('#favorDetailInfo').find("table[name=favorItems] tbody").html("");
		if(!datas || datas.length < 1){
			var tr = "<tr><td colspan='5' class='text-center text-secondary'>---该用户收藏夹为空---</td></tr>";
			$('#favorDetailInfo').find("table[name=favorItems] tbody").append(tr);
		}
		
		jQuery.each(datas, function(i, val) {
			var createTime = new Date(val.create_time).toLocaleDateString().replace(/\//g, "-") 
			+ " " + new Date(val.create_time).toTimeString().substr(0, 8);
			var tr = "<tr><th scope='row'>"+val.product.id+"</th>" +
					"<td>"+val.product.name+"</td>" +
					"<td>"+val.product.category+"</td>" +
					"<td>"+val.product.price+"</td>" +
					"<td>"+createTime+"</td>" +
					"</tr>";
			$('#favorDetailInfo').find("table[name=favorItems] tbody").append(tr);
		});
	}, 1000);
	$('#favorDetailInfo').modal('show');
}


//ajax管理员信息详情查看
function detailAdminInfo(upd_url,upd_id){
	var datas = {};
	$.ajax({
		url:upd_url,
		type:"POST",
		dataType:"json",
		success:function(data,textStatus){
			datas = data;
		},
		data:{
			action:"detail",
			id: upd_id
		}
	});
	setTimeout(function(){
		$('#adminDetailInfo').find("input[name=id]").val(datas.id);
		$('#adminDetailInfo').find("input[name=username]").val(datas.username);
		$('#adminDetailInfo').find("input[name=password]").val(datas.password);
		$('#adminDetailInfo').find("input[id=register_time]")
				.val(new Date(datas.register_time).toLocaleDateString().replace(/\//g, "-") 
				+ " " + new Date(datas.register_time).toTimeString().substr(0, 8));
		$('#adminDetailInfo').find("input[name=telephone]").val(datas.telephone);
		$('#adminDetailInfo').find("input[name=email]").val(datas.email);
	}, 1000);
	$('#adminDetailInfo').modal('show');
}

//ajax管理员删除
function deleteAdmin(delurl,id) {
	if(id==""||id==null) return;
	if(!confirm("确定要删除此管理员？")) return;
	var del_ids =[];
	del_ids.push(id);
	$.ajax({
		url:delurl,
		dataType:"json",
		type:"POST",
		traditional:true,
		success:function(data,textStatus){
			if(data.code==200) location.reload();
		},
		data:{
			action:"delete",
			ids: del_ids
		}
	});
}