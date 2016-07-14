<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
<script src="/resources/bootstrap-3.3.2-dist/js/bootstrap.min.js"
	type="text/javascript"></script>
<link href="/resources/bootstrap-3.3.2-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<style>
table {
	border-collapse: collapse;
}

th {
	background-color: #4CAF50;
	color: white;
}

table, td {
	border: 1px solid black;

</style>
<style type="text/css"></style>

</head>
<body>
	<form role="form" name="insert" method="POST" action="#">
		<div class="container">
			<h2>상품등록</h2>
			<div class="form-group">
				<label class="control-label col-sm-1" for="itemNo">상품번호:</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="itemNo">
				</div>
				<label class="control-label col-sm-1" for="itemId">상품이름:</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="itemId">
				</div>

				<label class="control-label col-sm-1" for="itemPrice">상품가격:</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="itemPrice">

				</div>
			</div>
		</div>
		<div class="container">
			<button type="button" class="btn btn-default" onclick="insertItem()">등록</button>
		</div>
		<div class="container"></div>
	</form>
</body>
<script>
	function insertItem() {
		var form = document.insert;
		var obj = new Object();
		var num_check = /^[0-9]*$/;
		obj.no = $("#itemNo").val();
		obj.itemId = $("#itemId").val();
		obj.itemPrice = $("#itemPrice").val();
		if (obj.no == "" || obj.itemId == "" || obj.itemPrice == "") {
			alert("상품 번호나 상품명이나 가격을 입력해 주세요");
		}else if(num_check.test(obj.no)==false){
			alert("상품번호는 숫자만 입력해야합니다.");
			$("#itemNo").val("");
			$("#itemNo").focus();
		}else if(num_check.test(obj.itemPrice)==false){
			alert("상품가격은 숫자만 입력해야합니다.");
			$("#itemId").val("");
			$("#itemId").focus();
		}
		else {
		

		var json_data = JSON.stringify(obj);
			console.log(json_data);
			$.ajax({
				url : "/insertItem",
				type : "POST",
				dataType : "json",
				data : json_data,
				contentType : "application/json; charset=UTF-8",
				success : function(data, stat, xhr) {
					alert(obj.no + "번" + obj.itemId + "이" + obj.itemPrice
							+ " 원에 등록되었습니다.");
				},
				error : function(jqXhr, textStatus, errorThrown) {
					alert("상품번호,상품 이름중 중복이 있습니다.");
					$("#itemNo").val("");
					$("#itemId").val("");
					$("#itemNo").focus();
				}
			});

			opener.location.reload(true);
		}
	}
</script>
</html>
