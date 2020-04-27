<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ page import="com.java1234.model.Diary" %>
<%@ page import=" com.java1234.dao.DiaryDao" %>
<%@ page import=" com.java1234.util.DbUtil" %>
<%@ page import=" java.sql.Connection" %>
<script type="text/javascript">
	function diaryDelete(diaryId){
		if(confirm("您确定要删除这个日志吗?")){
			window.location="diary?action=delete&diaryId="+diaryId;
		}
	}

</script>
<%! 
	synchronized void countPeople(int diaryId,int viewCount){ //串行化计数函数
			ServletContext application=((HttpServlet)(this)).getServletContext();
			
			
			if(diaryId==0){ //如果是第1个访问本站
				
				application.setAttribute("Count"+diaryId+"", ++viewCount);
			}
			else
			{
				application.setAttribute("Count"+diaryId+"", ++viewCount);
				
			}
	}
		%>
		<%
		
		Diary diary =(Diary)request.getAttribute("diary");
		countPeople(diary.getDiaryId(),diary.getViewCount());
		//保存次数
		DbUtil dbUtil = new DbUtil();
		DiaryDao diaryDao = new DiaryDao();
		Connection con = dbUtil.getCon();
		int diaryId = diary.getDiaryId();
		int count = (int)application.getAttribute("Count"+diaryId+"");
		diaryDao.saveViewCount(con, diaryId, count);
		dbUtil.closeCon(con);
		
		%>
<div class="data_list">
	<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/list_icon.png"/>
		日记信息
	</div>
	<div class="diary_title"><h2>${diary.title}</h2></div>
	<div class="bshare-custom" style="text-align: center; margin: 20px;"><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
	<div class="diary_info">
		发布时间:【<fmt:formatDate value="${diary.releaseDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>】&nbsp;&nbsp;日志类别:${diary.typeName }&nbsp;&nbsp;阅读 (&nbsp;${diary.viewCount}&nbsp;)
	</div>
	<div class="diary_content">
		${diary.content }
	</div>
	<div class="diary_action">
		<button class="btn btn-primary" type="button" onclick="javascript:window.location='diary?action=preSave&diaryId=${diary.diaryId}'">修改日志</button>
		<button class="btn btn-primary" type="button" onclick="javascript:history.back()"> 返回</button>
		<button class="btn btn-danger" type="button" onclick="diaryDelete(${diary.diaryId})"> 删除</button>
	</div>
</div>
