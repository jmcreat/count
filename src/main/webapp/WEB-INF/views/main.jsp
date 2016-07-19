<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>



</head>
<body>
	<form role="form" name="insert" action="#" method="post">
		<div class="container">
			<table class="table">
				<thead>
					<tr>
						<td>상품번호</td>
						<td>상품이름</td>
						<td>상품가격</td>
						<td>상품갯수</td>
						<td>총합</td>
						<td>삭제</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="listBuy" items="${listBuy}" varStatus="status">
						<c:choose>
							<c:when test="${0 eq listBuy.no}">
							</c:when>
							<c:when test="${0 ne listBuy.no}">
								<tr>
									<td id="sendNo">${listBuy.no}</td>
									<td id="sendId">${listBuy.itemId}</td>
									<td id="sendPrice">${listBuy.itemPrice}</td>
									<td id="sendCount">${listBuy.count}</td>
									<td id="sendSum">${listBuy.totalPrice}</td>

									<td><input type="button" id="btn"
										onclick="sendDelete(${listBuy.no})" value="삭제"></td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	<div class="container">
		<form role="form" name="insert" method="POST" action="#">
			<table class="table">
				<tr>
					<td><input type="button" id="total"
						onclick="totalSum(${allTotal.allTotal})" value=결제></td>

					<td id="allTotal">${allTotal.allTotal}</td>
				</tr>
			</table>
		</form>
		<table class="table">
			<tr>
				<td><button id="inertItem" onclick="insertItem()">상품등록버튼</button></td>
			</tr>
		</table>
	</div>
	<div class="container">
		<form role="form" name="search" action="/itemSearch" method="POST">
			<input type="text" id="itemSearch" name="itemSearch" class="span2"> <input
				type="submit" id="searchBtn" class="btn" value="검색">
		</form>
	</div>
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<td width="100">상품번호</td>
					<td width="100">상품이름</td>
					<td width="100">상품가격</td>
					<td width="100">갯수</td>
					<td width="100">추가</td>
					<!-- 		<td width="100">삭제</td> -->
				</tr>
			</thead>


			<c:forEach var="itemList" items="${itemList}" varStatus="status">

				<c:choose>
					<c:when test="${0 eq itemList.no}">
					</c:when>
					<c:when test="${0 ne itemList.no}">
						<tbody>
							<tr>
								<td width="100" id="no">${itemList.no}</td>
								<td width="100" id="id">${itemList.itemId }</td>
								<td width="100" id="price">${itemList.itemPrice}</td>
								<td width="100"><input type="text" id="count${itemList.no}"></td>
								<td><input type="button" id="btn"
									onclick="sendCounter(${itemList.no})" value=${itemList.no}>
								</td>
							</tr>
						</tbody>
					</c:when>
				</c:choose>
			</c:forEach>


		</table>
	</div>

</body>
<script type="text/javascript">
	function insertItem() {
		window.open("/item", "상품등록", "width=500,height=400");
	}
	
	
	function totalSum(index){
		var total_conf = confirm("결제 하시겠습니까?");
		if(total_conf){
			var obj = new Object();
			obj.allTotal = index;
			var json_data = JSON.stringify(obj);
			alert(json_data);
			$.ajax({
				type:"POST",
				url:"/countEnd",
				dataType:"json",
				data:json_data,
					contentType : "application/json; charset=UTF-8",
					success : function(data, stat, xhr) {
						alert(index+"원이 결제 되었습니다");
						location.reload(true);
					}
			}); 
		}
	}
	
	
	
	function sendDelete(index) {
		var conf =confirm("삭제하시겠습니까?"); 
		if(conf){
		var obj = new Object();
		obj.no = index;
		var json_data = JSON.stringify(obj);
		$.ajax({
			type:"POST",
			url:"/deleteSend",
			dataType:"json",
			data:json_data,
				contentType : "application/json; charset=UTF-8",
				success : function(data, stat, xhr) {
					alert("상품이 삭제되었습니다");
					location.reload(true);
				}
		}); 
		}
	}

	function sendCounter(index) {
		var num_check = /^[0-9]*$/;
var form = document.insert;
 var obj = new Object();
	obj.no = index;
	obj.count = $("#count"+index).val();
alert(index);
	if(obj.count==""){
		alert("갯수를 입력하세요");
	} else if(num_check.test(obj.count)==false){
		alert("숫자를 입력하세요");
	}else{
 var json_data = JSON.stringify(obj);
	$.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : "POST",
		url : "/addItem",
		dataType : "json",
		data : json_data,
		contentType : "application/json; charset=UTF-8",
		success : function(data, stat, xhr) {
			alert(index+ "번" + obj.count+ " 개 추가.");
			location.reload(true);
		}
	});
	}
}
	
	$("#searchBtn").click(function(){
		itemId = $("#itemSearch").val();
		if(itemId==""){
			alert("검색어를 입력하세요");
			return false;
		}
		else{
			var obj = new Object();
			obj.itemId = $("#itemSearch").val();
			var json_data = JSON.stringify(obj);
			alert("notsearch");
			$.ajax({
				type:"POST",
				url:"/notSearch",
	 			dataType : "json",
				data : json_data,
				contentType : "application/json; charset=UTF-8",
				success : function(data) {
					console.log(data);
					alert("검색어가 존재하지 않습니다.");
					location.reload(true);
				},
				fail : function(data){
					alert("올바른 검색값")
				}
	 		});
		}
			}); 
</script>
</html>
