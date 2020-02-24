<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<script type="text/javascript">
	function checkForm(){
		var typeName=document.getElementById("diaryTypeName").value;
		if(typeName==null||typeName==""){
			document.getElementById("error").innerHTML="类别名称不能为空！";
			return false;
		}
		return true;
	}
</script>


<div class="data_list">
<c:choose>
	<c:when test="${selectdiaryType.diaryTypeId != null }">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/diary_type_edit_icon.png"/>
		修改日志类型
	</div>
	</c:when>
	<c:otherwise>
			<div class="data_list_title">
			<img src="${pageContext.request.contextPath}/images/diary_add_icon.png"/>
		添加日志类型
	</div>
	</c:otherwise>
</c:choose>

<div style="text-align: center;margin-top:20px;">
	<form action="diaryType?action=save" method="post" onsubmit="return checkForm()">
		<input type="hidden" name="diaryTypeId" id="diaryTypeId" value="${selectdiaryType.diaryTypeId }">
		<input type="text" name="diaryTypeName" id="diaryTypeName"  placeholder="日志标题.." value="${selectdiaryType.typeName }"/>
	<div>
		<button type="submit" class="btn btn-primary btn-xs">保存</button>
		<button type="button" class="btn btn-primary btn-xs" onclick="javascript:window.history.back()">返回</button>
		<font id="error" color="red">${error }</font>
	</div>
	
	</form>


</div>
	
</div>

