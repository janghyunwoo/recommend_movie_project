<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="resources/js/mypage.js"></script>
<script src="resources/js/selcetMovieProcessing.js"></script>
<!-- <script src="resources/js/top100PageProcessing.js"></script> -->
<link href="resources/css/top100.css" rel="stylesheet">

<style type="text/css">
.well {
	min-height: 20px;
	padding: 19px;
	margin-bottom: 20px;
	background-color: #f5f5f5;
	border: 1px solid #e3e3e3;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05)
}

.well blockquote {
	border-color: #ddd;
	border-color: rgba(0, 0, 0, .15)
}

.well-lg {
	padding: 24px;
	border-radius: 6px
}

.well-sm {
	padding: 9px;
	border-radius: 3px
}

.media, .media-body {
	overflow: hidden;
	zoom: 1
}

.media, .media .media {
	margin-top: 15px
}

.media:first-child {
	margin-top: 0
}

.media-object {
	display: block
}

.media-heading {
	margin: 0 0 5px
}

.media>.pull-left {
	margin-right: 10px
}

.media>.pull-right {
	margin-left: 10px
}

.media-list {
	padding-left: 0;
	list-style: none
}
</style>

<script type="text/javascript">
function movieRecommend(movieid) {
	$.ajax({
		url : "mahoutMovie.do",
		type : "post",
		dataType : "json",
		cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
		data : {
			"movieid" : movieid
		},
		success : function(result) {
			
			$("#movieBaseButton"+movieid).remove();
			
			$.each(result.Comments, function(index, Comments) { // 이치를 써서 모든 데이터들을 배열에 넣음
				var items = [];
				
				items.push("<p>" + Comments.title + " : 추천 지수: "+ Comments.recommendIndex +"</p>");
				$("<div></div>", {
					html : items
				}).appendTo("#mediabody"+movieid); // 그리고 그 tr을 테이블에 붙임
			
			}); 
			
		},
		error : function(result) {
			confirm("서버 오류3");
		}
	});
}
</script>

</head>
<div class="container">
    <h1 class="my-4">영화 기반 추천 결과</h1>
    <c:forEach var="d" items="${MovieContent }">
	<div class="well">
	      <div class="media">
	      	<a class="pull-left align-self-center" href="#" style="width: 100px; ">
	    		<img class="media-object card-img-top rounded float-left" src="${d.img }">
	  		</a>
	  		<div id="mediabody${d.movieid }" class="media-body card-body">
	    		<h4 class="media-heading">${d.title }</h4>
	          <p>${d.comment }</p>
	          <p>
	          <c:forEach var="a" begin="0" end="5" step="1">
					<c:if test="${ a < d.rating }">
						<span class="fa fa-star checked"></span>
					</c:if>
					<c:if test="${ a > d.rating }">
						<span class="fa fa-star"></span>
					</c:if>
			  </c:forEach>
			 </p>
			  
			    <button type="button" id="movieBaseButton${d.movieid}" class="btn btn-outline-secondary" onclick="movieRecommend(${d.movieid })" >보기</button>
			  	
	       </div>
	    </div>
	  </div>
	
    </c:forEach>
    
    <div id='moviePageNum' style="align-content: center;">
		<ul class="pagination justify-content-center" style="flex-wrap: wrap; overflow-y: auto;">
			<li class="page-item ${(0<pageNow && pageNow<11)?'disabled':''}">
				<fmt:parseNumber var="pages1" integerOnly="true" value="${(pageNow-1)/10}"/>
				<a class="page-link" href="mahoutMovie.go?selectNum=${pages1*10-9}" aria-label="Previous" >
					<span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
				</a>
			</li>
			<c:forEach var="a" begin="${pageBegin }" end="${pageEnd}" step="1">
				<li class="page-item ${pageNow == a? 'active': ''}" <%-- id="pagebutton${a}" --%> >
					<a class="page-link" href="mahoutMovie.go?selectNum=${a }"<%-- onclick="getTop100Page(${a})" --%>>${a}</a>
				</li>
			</c:forEach>
			<li class="page-item ${(pageEndBegin<pageNow && pageNow<=pageMax)?'disabled':''}">
				<fmt:parseNumber var="pages2" integerOnly="true" value="${(pageNow-1)/10 }"/>
				<a class="page-link" href="mahoutMovie.go?selectNum=${pages2*10+11}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
					<span class="sr-only">Next</span>
				</a>
			</li>
		</ul>
	</div>
	
	
</div>
</html>
