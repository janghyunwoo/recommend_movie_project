<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Movie Recommend</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/bootstrap/css/4-col-portfolio.css" rel="stylesheet">
    
    <!-- Bootstrap core JavaScript -->
    <script src="resources/bootstrap/vendor/jquery/jquery.min.js"></script>
    <script src="resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script> -->
	<link rel="shortcut icon" type="image⁄x-icon" href="resources/img/movie.png">

<style>

/*  body{

  font-family: "Helvetica Nene", Helvetica, Arial, 맑은 고딕;,"malgun gothic", sans-serif;

 } */

 </style>

<script type="text/javascript">
 function searcheMovieDo() {
	$("#searcheMovie").val();
	location.href="searcheMovie.do?searcheMovie="+$("#searcheMovie").val();

} 

$(document).ready(function() {
	//네비게이션 class 요소 전환
	if($("#top100").attr('id')== ${selectNV}){
		$("#top100").addClass("active");
	}else if($("#myRating").attr('id')== ${selectNV})
	{
		$("#myRating").addClass("active");
	}else if($("#userRecommend").attr('id')== ${selectNV})
	{
		$("#userRecommend").addClass("active");
	}else if($("#movieRecommend").attr('id')== ${selectNV})
	{
		$("#movieRecommend").addClass("active");
	}
	
});


function mahoutMovie() {
	$.ajax({
		url : "check.recommendmovie.count.do",
		type : "post",
		cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
		success : function(result) {
			if(result == "정상"){
				location.href="mahoutMovie.go?selectNV='movieRecommend'";
			}else{
				alert(result);
			}
		},
		error : function(result) {
			confirm("서버 오류3");
		}

	});

}

function mahoutUser() {
	$.ajax({
		url : "check.recommenduser.count.do",
		type : "post",
		cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
		success : function(result) {
			if(result == "정상"){
				location.href="mahoutUser.go?selectNV='userRecommend'";
			}else{
				alert(result);
			}
		},
		error : function(result) {
			confirm("서버 오류3");
		}

	});

}
</script>
  </head>

  <body>

    <!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="top100.go?selectNV='top100'">★Movie Recommend★</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li id="top100" class="nav-item" ><a class="nav-link" href="top100.go?selectNV='top100'">Top
							100 <span class="sr-only">(current)</span>
					</a></li>
					<li id="userRecommend" class="nav-item" ><a href="#" class="nav-link" onclick="mahoutUser()" >유저기반 추천</a>
<!-- 					<li id="userRecommend" class="nav-item" ><a class="nav-link" href="mahoutUser.go?selectNV='userRecommend'" >유저기반 추천</a> -->
					</li>
					<li id="movieRecommend" class="nav-item" ><a href="#" class="nav-link" onclick="mahoutMovie()">영화기반 추천</a>
<!-- 					<li id="movieRecommend" class="nav-item" ><a class="nav-link" href="mahoutMovie.go?selectNV='movieRecommend'">영화기반 추천</a> -->
					</li>
				</ul>
				<ul class="navbar-nav ml-auto">
					<c:if test="${sessionScope.loginMember != null }">
						<li id="myRating" class="nav-item"><a class="nav-link" href="myrating.go?selectNV='myRating'">나의 평가 · 리뷰</a></li>
						<li class="nav-item"><a class="nav-link" href="logout.do">로그 아웃</a></li>
					</c:if>
					<c:if test="${sessionScope.loginMember == null }">
						<li class="nav-item"><a class="nav-link" href="login.go">로그인/회원가입</a></li>
						
					</c:if>
				</ul>
				<!-- <div id="custom-search-input">
						<div class="input-group">
							<form action="http://www.naver.com" method="get" role="search">
								<input type="text" class="search-query form-control"
									placeholder="Search" />
							</form>
						</div>
					</div> -->
				<div class="input-group col-lg-3" >
					<input type="text" id="searcheMovie" class="form-control" onkeypress="if(event.keyCode==13) {searcheMovieDo(); return false;}" placeholder="Search" name="q">
				</div>
			</div>
		</div>
	</nav>
<%-- <button onclick="aaa()">${ContentPage} ss</button>ssdfsdfasfasfsdj --%>
	<!-- Page Content -->
    <div class="container">
		<jsp:include page="${ContentPage}"></jsp:include>
    </div>
    <!-- Footer -->
    <footer class="py-5 bg-dark" >
      <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2018</p>
      </div>
      <!-- /.container -->
    </footer>



  </body>

</html>