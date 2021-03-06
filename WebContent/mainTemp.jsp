<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
$('.collapse').collapse();
</script>
<title>个人日记本主页</title>

<link href="${pageContext.request.contextPath}/style/diary.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script
	src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}
</style>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top ">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand" href="main?all=true">一只明呀</a>
				<div class="nav-collapse collapse ">
					<ul class="nav">
						<li class="active"><a href="main?all=true"><i
								class="icon-home"></i>&nbsp;主页</a></li>
						<li class="active"><a href="diary?action=preSave"><i
								class="icon-pencil"></i>&nbsp;写日记</a></li>
						<li class="active"><a href="diaryType?action=list"><i
								class="icon-book"></i>&nbsp;日记分类管理</a></li>
						<li class="active"><a href="user?action=preSave"><i
								class="icon-user"></i>&nbsp;个人中心</a></li>
					</ul>
				</div>
				<form name="myForm" class="navbar-form pull-right" method="post"
					action="main?all=true">
					<input class="span2" id="s_title" name="s_title" type="text"
						style="margin-top: 5px; height: 30px;" placeholder="往事如烟...">
					<button type="submit" class="btn">
						<i class="icon-search"></i>&nbsp;搜索日志
					</button>
					<!-- Single button -->
					<div class="btn-group">
						<button type="button" class="btn btn-info dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							退出 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="./user?action=exit">退出</a></li>
						</ul>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row-fluid">
			<div class="span9">
				<jsp:include page="${mainPage}"></jsp:include>
			</div>
			<div class="span3">
				<div class="data_list">
					<div class="data_list_title">
						<img src="${pageContext.request.contextPath}/images/list_icon.png" />
						个人中心
					</div>
					<div class="user_image">
						<img alt=""
							src="${pageContext.request.contextPath}/userImages/${currentUser.imageName}">

					</div>

					<div class="nickName">${currentUser.nickName}</div>
					<div class="userSign">(${currentUser.mood })</div>
				</div>
				<div class="data_list">
					<div class="data_list_title">
						<img
							src="${pageContext.request.contextPath}/images/byType_icon.png" />
						按日志类别
					</div>
					<div class="datas">
						<ul>
							<c:forEach var="diaryType" items="${diaryTypeCountList}">
								<li><span><a
										href="main?s_typeId=${diaryType.diaryTypeId}">${diaryType.typeName}(${diaryType.diaryCount })</a></span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="data_list">
					<div class="data_list_title">
						<img
							src="${pageContext.request.contextPath}/images/byDate_icon.png" />
						按日志日期
					</div>
					<div class="datas">
						<ul>
							<c:forEach var="diaryCount" items="${diaryCountList}">
								<li><span><a
										href="main?s_releaseDatestr=${diaryCount.releaseDatestr }">${diaryCount.releaseDatestr}(${diaryCount.diaryCount })</a></span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
						<div class="row-fluid">
			<div class="span3">
				<!-- music -->
				<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width=330 height=450 src="//music.163.com/outchain/player?type=0&id=4986160015&auto=1&height=430"></iframe>
				<!-- music -->
			</div>
		</div>
			</div>

		</div>
	</div>

<jsp:include page="friendsLinks.jsp"></jsp:include>


</body>
</html>