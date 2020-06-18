//ajax订单详情查看
function detailOrder(upd_url,upd_id){
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
		$('#editInfo').find("input[name=id]").val(datas.id);
		$('#editInfo').find("input[name=receiverAddress]").val(datas.receiverAddress);
		$('#editInfo').find("input[name=receiverPhone]").val(datas.receiverPhone);
		$('#editInfo').find("input[name=receiverName]").val(datas.receiverName);
		$('#editInfo').find("input[name=username]").val(datas.user.username);
		$('#editInfo').find("input[name=money]").val(datas.money);
		$('#editInfo').find("select[name=payState]").find("option[value="+datas.payState+"]").prop("selected", true);
		$('#editInfo').find("input[name=orderTime]")
			.val(new Date(datas.orderTime).toLocaleDateString().replace(/\//g, "-") 
			+ " " + new Date(datas.orderTime).toTimeString().substr(0, 8));
		$('#editInfo').find("table[name=orderItems] tbody").html("");
		jQuery.each(datas.orderItems, function(i, val) {
			var tr = "<tr><th scope='row'>"+(i+1)+"</th>" +
					"<td>"+val.product.id+"</td>" +
					"<td>"+val.product.name+"</td>" +
					"<td>"+val.product.price+"</td>" +
					"<td>"+val.product.pnum+"</td>" +
					"<td>"+val.product.category+"</td>" +
					"<td>"+val.product.description+"</td>" +
					"</tr>";
			$('#editInfo').find("table[name=orderItems] tbody").append(tr);
		});
	}, 1000);
	$('#editInfo').modal('show');
}
//ajax删除一条订单
function deleteOrder(delurl,id) {
	if(id==""||id==null) return;
	if(!confirm("确定要删除本条订单？")) return;
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
//批量删除订单
function deleteCheckedOrder(delurl){
	var del_ids =[];	//定义一个数组    
	$("input[name='deleteIds']:checked").each(function () {
		del_ids.push($(this).val());
	});
	if(del_ids.length <=0) return;
	if(!confirm("确定要批量删除订单？")) return;
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