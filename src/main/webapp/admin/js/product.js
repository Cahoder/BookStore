//ajax商品详情查看
function detailProduct(upd_url,upd_id){
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
		$('#editInfo').find("label[for=addProductImgurl]").html('Update file...');
		$('#editInfo').find("input[name=id]").val(datas.id);
		$('#editInfo').find("input[name=name]").val(datas.name);
		$('#editInfo').find("select[name=category]").find("option[value="+datas.category+"]").attr("selected",true);
		$('#editInfo').find("textarea[name=description]").val(datas.description);
		$('#editInfo').find("input[name=pnum]").val(datas.pnum);
		$('#editInfo').find("input[name=price]").val(datas.price);
		$('#editInfo').find("input[name=imgurl]").val(datas.imgurl).change();
	}, 1000);
	$('#editInfo').modal('show');
}
//ajax删除一条商品
function deleteProduct(delurl,id) {
	if(id==""||id==null) return;
	if(!confirm("确定要删除本条商品？")) return;
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
//ajax批量删除商品
function deleteCheckedProduct(delurl){
	var del_ids =[];	//定义一个数组    
	$("input[name='deleteIds']:checked").each(function () {
		del_ids.push($(this).val());
	});
	if(del_ids.length <=0) return;
	if(!confirm("确定要批量删除商品？")) return;
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