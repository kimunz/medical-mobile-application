<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>환자 기록</title>
<style>
* {
	font-family: 나눔고딕;
}

#title {
	padding: 20px;
	font-size: 20pt;
	color: black;
	border: 2px solid #000000;
}

#link {
	padding: 10px;
	margin-right: 10px;
}

.menu {
	font-size: 12pt;
	color: black;
	text-decoration: none;
}

.menu:hover {
	background-color: black;
	color: white;
}

table, td, th {
	border-top: 1px solid #444444;
	border-bottom: 1px solid #444444;
	border-collapse: collapse;
	text-align: center;
	padding: 5px;
}

.btn {
	border: 0px;
	background: #2b2b2b;
	background: -webkit-gradient(linear, left top, left bottom, from(#595959),
		to(#2b2b2b));
	background: -webkit-linear-gradient(top, #595959, #2b2b2b);
	background: -moz-linear-gradient(top, #595959, #2b2b2b);
	background: -ms-linear-gradient(top, #595959, #2b2b2b);
	background: -o-linear-gradient(top, #595959, #2b2b2b);
	padding: 5px 10px;
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
	-webkit-box-shadow: rgba(0, 0, 0, 1) 0 1px 0;
	-moz-box-shadow: rgba(0, 0, 0, 1) 0 1px 0;
	box-shadow: rgba(0, 0, 0, 1) 0 1px 0;
	text-shadow: rgba(0, 0, 0, .4) 0 1px 0;
	color: white;
	font-size: 14px;
	text-decoration: none;
	vertical-align: middle;
}

.btn:hover {
	border-top-color: #595959;
	background: #595959;
	color: #ccc;
}

.btn:active {
	border-top-color: #2b2b2b;
	background: #2b2b2b;
}

#alert {
	border: none;
}

#combo1, #combo2 {
	height: 25px;
	width: 200px;
	font-size: 14px;
}

#left_div {
	float: left;
	width: 80%;
}

#right_div {
	float: right;
	width: 19%;
	height: 100%;
	border: 2px solid #000000;
}
</style>
<script>
	function sel_carte() {
		var form = document.p_condition;
		var carte = form.carte.options.value;
		form.sel.value = carte;

		form.action = "control.jsp?action=pa_rec_req";
		form.submit();
	}
	function back() {
		var form = document.p_condition;
		form.action = "control.jsp?action=pa_list_req&value=down_1";
		form.submit();
	}
	function sel_date() {
		var form = document.p_condition;
		var date = form.reg_date.options.value;
		form.p_date.value = date;

		form.action = "control.jsp?action=pa_rec_req";
		form.submit();
	}
</script>
</head>
<body>
	<div align=center id="left_div">
		<div id="title">
			<b>영남대학교병원 - ${p_name}님의 상태기록</b>

			<div align="right" id="link">
				<a class="menu" href="control.jsp?action=pa_list_req&value=down_1">돌아가기</a>&nbsp;
				<a class="menu" href="control.jsp?action=login_req">로그아웃</a>
			</div>
		</div>

		<br>

		<form name="p_condition" method="post" action="control.jsp">
			<input type="hidden" name="p_id" value="${p_id}"> <input
				type="hidden" name="p_name" value="${p_name}"> <input
				type="hidden" name="sel" value="${sel}"> <input
				type="hidden" name="p_date">

			<table>
				<tr>
					<td><select name="carte" id="combo1">
							<option>소변량</option>
							<option>배설량</option>
							<option>수분섭취량</option>
					</select></td>
					<td width=100><input type="button" class="btn" value="분류 조회"
						onClick="sel_carte()"></td>
				</tr>
				<tr>
					<td><select name="reg_date" id="combo2">
							<c:forEach var="date" items="${requestScope.date_list}">
								<option>${date.reg_date}</option>
							</c:forEach>
					</select></td>
					<td><input type="button" class="btn" value="날짜 조회"
						onClick="sel_date()"></td>
				</tr>
			</table>

			<br> <br>

			<table>
				<tr>
					<th width=100>날짜</th>
					<th width=100>시간</th>
					<th width=100>분류</th>
					<th width=100>단위(${unit})</th>
				</tr>
				<c:forEach var="con" items="${requestScope.pa_con_list}">
					<tr>
						<td>${con.reg_date}</td>
						<td>${con.reg_time}</td>
						<td>${con.carte}</td>
						<td>${con.volume}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	<div id="right_div">
		<iframe width="100%" align="middle" height="700" src="control.jsp?action=med_req"
			frameborder="0"></iframe>
		<iframe width="1" height="1" src="control.jsp?action=emer_req"
			frameborder="0"></iframe>
	</div>
	<script>
		var form = document.p_condition;
		var sel_combo1 = form.sel.value;

		if (sel_combo1 == "소변량")
			form.carte.options[0].selected = true;
		if (sel_combo1 == "배설량")
			form.carte.options[1].selected = true;
		if (sel_combo1 == "수분섭취량")
			form.carte.options[2].selected = true;
	</script>
</body>
</html>