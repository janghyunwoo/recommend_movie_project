// 페이지가 로딩된 후 실행해야 되는 코드를 추가한다.
//window.onload(getContent(1));
	
/*function getTop100Page(selectNum){
	
	$("#pagebutton"+selectNum).addClass("active");
	
}*/


function deleteMyRating(movieid) {
	if(confirm("정말 삭제 하시겠습니까?")){
		location.href="deleteRating.do?movieid="+movieid;
	}
}

function updateRating(movieid) {
	$.ajax({
		url : "updateRating.do",
		type : "post",
		cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
		data : {
			"movieid" : movieid,
			"rating" : rating,
			"comment" :$("#comment"+movieid).val()
		},
		success : function(result) {
			alert(result);
			//ratingSaveResult(result);
			location.href="myrating.go";
		},
		error : function(result) {
			confirm("서버 오류3");
		}

	});
}