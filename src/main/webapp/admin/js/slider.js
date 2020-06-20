//ajax轮播图详情查看
function detailSlider(upd_url,upd_id){
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
		$('#editInfo').find("label[for=uploadSlider]").html('Update file...');
		$('#editInfo').find("input[name=id]").val(datas.id);
		$('#editInfo').find("input[name=str_name]").val(datas.str_name?datas.str_name:'').change();
		$('#editInfo').find("input[name=str_value]").val(datas.str_value?datas.str_value:'');
		$('#editInfo').find("input[name=str_order]").val(datas.str_order?datas.str_order:'');
		$('#editInfo').find("input[name=is_show]").val(datas.is_show);
		$('#editInfo').find("input[id=is_show]").prop('checked',datas.is_show==0?true:false);
		$('#editInfo').find("input[id=create_time]")
		.val(new Date(datas.create_time).toLocaleDateString().replace(/\//g, "-") 
		+ " " + new Date(datas.create_time).toTimeString().substr(0, 8));
	}, 1000);
	$('#editInfo').modal('show');
}
//ajax删除一条轮播图
function deleteSlider(delurl,id) {
	if(id==""||id==null) return;
	if(!confirm("确定要删除本条轮播图？")) return;
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
//ajax批量删除轮播图
function deleteCheckedSlider(delurl){
	var del_ids =[];	//定义一个数组    
	$("input[name='deleteIds']:checked").each(function () {
		del_ids.push($(this).val());
	});
	if(del_ids.length <=0) return;
	if(!confirm("确定要批量删除轮播图？")) return;
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
//ajax修改一条轮播图展示状态
function showSlider(surl,id) {
	if(id==""||id==null) return;
	$.ajax({
		url:surl,
		dataType:"json",
		type:"POST",
		traditional:true,
		success:function(data,textStatus){},
		error:function(data,textStatus){alert('更新失败');},
		data:{
			action:"show",
			id: id
		}
	});
}