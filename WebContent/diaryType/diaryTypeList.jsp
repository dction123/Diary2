<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<script type="text/javascript">
	function deleteType(diaryTypeId){
		if(confirm("您确定要删除这个类别吗?")){
			window.location="diaryType?action=delete&diaryTypeId="+diaryTypeId;
		}
	}

</script>
<div class="data_list">
		<div class="data_list_title">
			<img src="${pageContext.request.contextPath}/images/list_icon.png"/>
			日记类别列表
			<span style="float: right; margin-right:20px;">
			<button type="button" class="btn  btn-success btn-xs" onclick="javascript:window.location='diaryType?action=preSave'">添加日志类别</button>
			</span>
		</div>
		
		<div>
			<table class="table table-hover">
				<tr>
					<th>编号</th>
					<th>日志类别</th>
					<th>操作</th>
				</tr>
					
					<c:forEach var="diaryType" items="${diaryTypeList}">
						<tr>	
							<td>${diaryType.diaryTypeId}</td>
							<td>${diaryType.typeName}</td>
							<td>
							<button type="button" class="btn btn-info btn-xs" onclick="javascript:window.location='diaryType?action=preSave&diaryTypeId=${diaryType.diaryTypeId}'">修改</button>&nbsp;&nbsp;
							<button type="button" class="btn btn-danger btn-xs" onclick="deleteType(${diaryType.diaryTypeId})">删除</button>
							
							</td>
						</tr>
					</c:forEach>			
					
			</table>
		
		</div>
		
</div>
