<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/resources/bootstrap-3.3.2-dist/js/bootstrap.min.js"
	type="text/javascript"></script>
<link href="/resources/bootstrap-3.3.2-dist/css/bootstrap.min.css"
	rel="stylesheet">
<scrpit src="/resources/jquery-2.2.3.min.js" type="text/javascrpit"></scrpit>
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<html>
<head>
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

	<div style="width: 1000; height: 300px; overflow: auto">
		<form role="form" name="insert" method="post" action="#">
			<table id="mytable" style="width: 600;">
				<tr>
					<td style="width: 100">상품번호</td>
					<td style: width="100">상품이름</td>
					<td width="100">상품가격</td>
					<td width="100">상품갯수</td>
					<td width="100">총합</td>

				</tr>
				<c:forEach var="addList" items="${addList}" varStatus="status">
					<tr>
						<td width="80"><input id="sendNo" value=${addList.no}></td>
						<td width="80"><input id="sendId" value=${addList.itemId}></td>
						<td width="80"><input id="sendPrice"
							value=${addList.itemPrice}></td>
						<td width="80"><input id="sendCount" value=${addList.count}></td>
						<td width="80"><input id="sendSum"></td>
					</tr>
				</c:forEach>

			</table>
		</form>
	</div>

	<table style="width: 600;">
		<tr>
			<button id="addItem" onclick="addItem()">상품등록버튼</button>
		</tr>
	</table>
	<div style="width: 800; height: 300px; overflow: auto">
		<form role="form" name="insert" method="post" action="#">
			<table style="width: 600;">
				<tr>
					<td width="100">상품번호</td>
					<td width="100">상품이름</td>
					<td width="100">상품가격</td>
					<td width="100">갯수</td>
					<td width="100">추가</td>
					<!-- <td width="100">삭제</td> -->
				</tr>
				<c:forEach var="itemList" items="${itemList}" varStatus="status">
					<tr>
						<td width="100"><input id="no" value=${itemList.no}></td>
						<td width="100"><input id="id" value=${itemList.itemId }></td>
						<td width="100"><input id="price"
							value=${itemList.itemPrice }></td>
						<td width="100"><input type="text" id="count"></td>
						<td width="100"><input type="button" id="btn"
							onclick="sendCounter(${itemList.no})" value=${itemList.no}>
							</button></td>
				<!-- 
						<td width="100"><input type="button" id="delete"
							onclick="deleteCounter()" value=${status.index}>
							</button></td>
							 -->
					</tr>

				</c:forEach>
			</table>
		</form>
	</div>


</body>
<script type="text/javascript">
	function addItem() {

		window.open("/item", "상품등록", "width=500,height=400");

	}

	function sendCounter(index) {
		/* alert(index);
		 $('#mytable > tbody:last')
				.append('<tr><td width="80"><input id="sendNo"></td><td width="80"><input id="sendId"></td><td width="80"><input id="sendPrice"></td><td width="80"><input id="sendCount"></td><td width="80"><input id="sendSum"></td></tr>');
	 
		no = $("#no").val();
		id = $("#id").val();
		price = $("#price").val();
		count = $("#count").val();

		
		
		$("#sendNo").val(no);
		$("#sendId").val(id);
		$("#sendPrice").val(price);
		$("#sendCount").val(count);
		$("#sendSum").val(price * count);
 */
 	var form = document.insert;
 var obj = new Object();
 obj.id = 1;
 obj.no = index;
 obj.count = $("#count").val();
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
			alert(obj.no + "번" + obj.count+ " 개 추가.");
		}
	});
	opener.location.reload(true);
}
</script>
</html>
