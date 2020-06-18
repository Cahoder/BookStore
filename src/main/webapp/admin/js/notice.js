//ajax公告详情查看
function detailNotice(upd_url,upd_id){
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
		$('#editInfo').find("input[name=title]").val(datas.title);
		$('#editInfo').find("textarea[name=details]").val(datas.details);
		$('#editInfo').find("input[name=create_time]")
		.val(new Date(datas.create_time).toLocaleDateString().replace(/\//g, "-") 
		+ " " + new Date(datas.create_time).toTimeString().substr(0, 8));
	}, 1000);
	$('#editInfo').modal('show');
}
//ajax删除一条公告
function deleteNotice(delurl,id) {
	if(id==""||id==null) return;
	if(!confirm("确定要删除本条公告？")) return;
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
//ajax批量删除公告
function deleteCheckedNotice(delurl){
	var del_ids =[];	//定义一个数组    
	$("input[name='deleteIds']:checked").each(function () {
		del_ids.push($(this).val());
	});
	if(del_ids.length <=0) return;
	if(!confirm("确定要批量删除公告？")) return;
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