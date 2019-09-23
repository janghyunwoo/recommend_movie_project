<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!--  -->

<!--  -->
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

</script>

</head>
<div class="container">

    <h1 class="my-4">나의 평가 · 리뷰</h1>
    <c:forEach var="d" items="${MyRating }">
	<div class="well">
	      <div class="media">
	      	<a class="pull-left align-self-center" href="#" style="width: 100px; " onclick="resetVar(${d.movieid })" data-toggle="modal" data-target=".bd-example-modal-lg${d.movieid }">
	    		<img class="media-object card-img-top rounded float-left" src="${d.img }">
	  		</a>
	  		<div class="media-body card-body">
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
			  
			    <button type="button" class="btn btn-outline-secondary" data-toggle="modal" data-target=".bd-example-modal-lg${d.movieid }">수정</button>
			    <button type="button" class="btn btn-outline-danger" onclick="deleteMyRating(${d.movieid})">삭제</button>
			  
	       </div>
	    </div>
	  </div>
    </c:forEach>
    
    <div id='moviePageNum' style="align-content: center;">
		<ul class="pagination justify-content-center" style="flex-wrap: wrap; overflow-y: auto;">
			<li class="page-item ${(0<pageNow && pageNow<11)?'disabled':''}">
				<fmt:parseNumber var="pages1" integerOnly="true" value="${(pageNow-1)/10}"/>
				<a class="page-link" href="myrating.go?selectNum=${pages1*10-9}" aria-label="Previous" >
					<span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
				</a>
			</li>
			<c:forEach var="a" begin="${pageBegin }" end="${pageEnd}" step="1">
				<li class="page-item ${pageNow == a? 'active': ''}" <%-- id="pagebutton${a}" --%> >
					<a class="page-link" href="myrating.go?selectNum=${a }"<%-- onclick="getTop100Page(${a})" --%>>${a}</a>
				</li>
			</c:forEach>
			<li class="page-item ${(pageEndBegin<pageNow && pageNow<=pageMax)?'disabled':''}">
				<fmt:parseNumber var="pages2" integerOnly="true" value="${(pageNow-1)/10 }"/>
				<a class="page-link" href="myrating.go?selectNum=${pages2*10+11}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
					<span class="sr-only">Next</span>
				</a>
			</li>
		</ul>
	</div>
	<c:forEach var="d" items="${MyRating }">
		<div class="modal fade bd-example-modal-lg${d.movieid }" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title h4" id="myLargeModalLabel">수정 - ${d.title }</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
							<table class="table table-borderless">
								<tbody>
									<tr>
										<th rowspan="2" style="width: 65%">
											<div>
												<label for="comment">◆평가하기</label>
											</div>
											<div>
												<textarea class="form-control" rows="5"
													id="comment${d.movieid }"></textarea>
											</div> <!-- </span> -->
										</th>
										<th rowspan="2" style="width: 35%">

											<table class="table table-borderless">
												<tbody>
													<tr>
														<td class="h3"><span class="fa fa-star"
															id="star1_${d.movieid }"
															onclick="add(this,1,${d.movieid })"></span> <span
															class="fa fa-star" id="star2_${d.movieid }"
															onclick="add(this,2,${d.movieid })"></span> <span
															class="fa fa-star" id="star3_${d.movieid }"
															onclick="add(this,3,${d.movieid })"></span> <span
															class="fa fa-star" id="star4_${d.movieid }"
															onclick="add(this,4,${d.movieid })"></span> <span
															class="fa fa-star" id="star5_${d.movieid }"
															onclick="add(this,5,${d.movieid })"></span></td>
													</tr>
													<tr>
														<td>
															<button type="button" class="btn btn-outline-secondary" onclick="updateRating(${d.movieid })"
																type="button">수정</button>
														</td>
													</tr>
												</tbody>
											</table>
										</th>
									</tr>
								</tbody>
							</table>
					
					

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	</c:forEach>
	

	
</div>
</html>
