<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<script>
	function checkForm(){
		var nickName = document.getElementById("nickName").value;
		if(nickName == null || nickName == ""){
			document.getElementById("error").innerHTML = "昵称不能为空！";
			return false;
		}
		return true;
	}


</script>
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/user_edit_icon.png"/>
		个人信息设置
	</div>
<div class="row-fluid" style="padding-top: 20px;">
	 <div class="span4">
	 	<img alt="" src="Diary2/${currentUser.imageName }">
	 </div>
	 <div class="span8">
	 	<form action="user?action=save" onsubmit="return checkForm()" enctype="multipart/form-data" method="post">
	 		<table width="100%">
	 			<tr >
	 				<td>头像路径:</td>
	 				<td>
	 				<input type="file" id="imagePath" name="imagePath"/>	
	 				</td>
	 			</tr>
	 			<tr>
	 				<td>个人昵称:</td>
	 				<td>
	 				<input type="text" value="${currentUser.nickName }" id="nickName" name="nickName" />	
	 				</td>
	 			</tr>
	 			<tr>
	 				<td>个人心情:</td>
	 				<td>
	 				<textarea rows="10" style="width:100%" id="mood" name="mood">${currentUser.mood }</textarea>	
	 				</td>
	 			</tr>
	 			<tr >
	 				<td><input class="btn btn-primary" type="submit" value="保存"/></td>
	 				<td><input class="btn btn-primary" type="submit" value="返回" onclick="javascript:history.back()"/>&nbsp;&nbsp;<font id="error" color="red">${error }</font></td>
	 			
	 			</tr>	 				 		
	 		</table>
	 	
	 	
	 	</form>
	 </div>
</div>
