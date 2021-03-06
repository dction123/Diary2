<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 



<div class="data_list">
<c:choose>
	<c:when test="${diary.diaryId!=null }">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/diary_type_edit_icon.png"/>
		修改日记
	</div>
	</c:when>
	<c:otherwise>
			<div class="data_list_title">
			<img src="${pageContext.request.contextPath}/images/diary_add_icon.png"/>
		写日记
	</div>
	
	</c:otherwise>
</c:choose>

	<form action="diary?action=save" method="post" onsubmit="return checkForm()">
		<div class="diary_title">
			<input  type="text" id="title" name="title" placeholder="日志标题.." value="${diary.title }"/>
		</div>
		<div >
			<textarea class="ckeditor" id="content" name="content">${diary.content}</textarea>
		</div>
		<div class="diary_type">
			<select id="typeId" name="typeId">
					<option value="">请选择日志类别...</option>
				<c:forEach var="diaryTypeCount" items="${diaryTypeCountList }">
					<option value="${diaryTypeCount.diaryTypeId }" ${diaryTypeCount.diaryTypeId==diary.typeId ?'selected':'' } >${diaryTypeCount.typeName}</option>
					
				</c:forEach>
			</select>
		</div>
		<div>
				<input type="hidden" id="diaryId" name="diaryId" value="${diary.diaryId }"/>
				<input type="submit" class="btn btn-primary" value="保存" />
				<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
				<font id="error" color="red">${error }</font>  
		</div>
	</form>
	
</div>
<script type="text/javascript">

function checkForm(){
	var title=document.getElementById("title").value;
	var content=CKEDITOR.instances.content.getData();
	var typeId=document.getElementById("typeId").value;
	if(title==null||title==""){
		document.getElementById("error").innerHTML="标题不能为空！";
		return false;
	}
	if(content==null||content==""){
		document.getElementById("error").innerHTML="内容不能为空！";
		return false;
	}
	if(typeId==null||typeId==""){
		document.getElementById("error").innerHTML="请选择日志类别！";
		return false;
	}
	return true;
}
</script>