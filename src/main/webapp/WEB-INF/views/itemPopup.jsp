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
}
</style>
<style type="text/css"></style>

</head>
<body>
	<form role="form" name="insert" method="post" action="#">
		<div class="container">
			<h2>상품등록</h2>
			<div class="form-group">
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
		obj.itemId = $("#itemId").val();
		obj.itemPrice = $("#itemPrice").val();
		var json_data = JSON.stringify(obj);

		if (obj.itemId == "" || obj.itemPrice == "") {
			alert("상품명이나 가격을 입력해 주세요")
		}
		console.log(json_data);
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "/insertItem",
			dataType : "json",
			data : json_data,
			contentType : "application/json; charset=UTF-8",
			success : function(data, stat, xhr) {
				alert(obj.itemId + "이" + obj.itemPrice + " 원에 등록되었습니다.");
			}
		});
		
		opener.location.reload(true);
	}
</script>

</html>
