<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- navbar start -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand" href="">Filter</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item">
				<button class="btn btn-light my-2 my-sm-0" data-toggle="modal"
					data-target="#addInfo" type="button">新建轮播</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-light my-2 my-sm-0" type="button" title="请先勾选需删除订单"
					onclick="deleteCheckedSlider('<%=response.encodeURL(request.getContextPath()
						+"/admin/SliderAdminServlet")%>')">批量删除</button>
			</li>
		</ul>
	</div>
</nav>
<!-- navbar end -->
<!-- 静态引入添加表单 -->
	<%@ include file="add.jsp" %>
<!-- 静态引入添加表单 -->
<!-- table start -->
<table class="table table-striped" id="SliderList">
	<thead>
		<tr>
			<th width="5%" scope="col"><input type="checkbox"
				onclick="$('input[name=deleteIds]').prop('checked',this.checked)" />
			</th>
			<th width="10%" scope="col">编号</th>
			<th width="40%" scope="col">轮播图片</th>
			<th width="15%" scope="col">排序(越小越靠前)</th>
			<th width="10%" scope="col">前台展示</th>
			<th width="20%" scope="col">编辑</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${requestScope.SliderList eq null}">
			<jsp:include
				page="<%=response.encodeURL(\"/admin/SliderAdminServlet\")%>">
				<jsp:param value="display" name="action" />
			</jsp:include>
		</c:if>
		<c:forEach items="${SliderList}" var="slider" varStatus="status">
			<tr>
				<th><input type="checkbox" name="deleteIds"
					value="${slider.id}" /></th>
				<th>${status.count}</th>
				<td>
					<img src="${pageContext.request.contextPath}/client/${slider.str_name}" 
						class="img-fluid" alt="轮播图">
				</td>
				<td>${slider.str_order}</td>
				<td>
					<input name="is_show" type="checkbox" value="${slider.id}" 
					<c:if test="${slider.is_show eq 0}">checked</c:if> >
				</td>
				<td><button type="button" class="btn btn-outline-info btn-sm"
						onclick="detailSlider('<%=response.encodeURL(request.getContextPath()
								+"/admin/SliderAdminServlet")%>','${slider.id}')">编辑</button>
					<button type="button" class="btn btn-danger btn-sm"
						onclick="deleteSlider('<%=response.encodeURL(request.getContextPath()
								+"/admin/SliderAdminServlet")%>','${slider.id}')">删除</button></td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="17">
				<nav aria-label="">
					<ul id="pageLimit" class="pagination justify-content-center"></ul>
				</nav>
			</td>
		</tr>
	</tfoot>
</table>
<!-- table end -->
<!-- 静态引入轮播详情表单 -->
	<%@ include file="detail.jsp" %>
<!-- 静态引入轮播详情表单 -->
<!-- page script start -->
<script type="text/javascript">
	$('#pageLimit').twbsPagination({
		startPage: parseInt('${pageNo}'),
		totalPages: parseInt('${totalNo}'),
		visiblePages: 3,
		hideOnlyOnePage: false, href: false,
		first:'首页', prev: '上一页', next: '下一页', last: '尾页',
		onPageClick: function (event, page) {
			window.location.href= 
			"<%=response.encodeURL(request.getContextPath()+"/admin/login/home.jsp")%>"
			+"?item=slider_list&pageSize=${pageSize}&pageNo=" + page;
		},initiateStartPageClick: false 
	});
	$('#SliderList').find('[name="is_show"]').bootstrapSwitch({
       onText:"展示",
       offText:"屏蔽",
       onColor:"success",
       offColor:"light",
       size:"small",
       onSwitchChange:function(event,state){
    	   showSlider('<%=response.encodeURL(request.getContextPath()+"/admin/SliderAdminServlet")%>'
    			   ,event.currentTarget.value);
       }
    });
</script>
<!-- page script end -->