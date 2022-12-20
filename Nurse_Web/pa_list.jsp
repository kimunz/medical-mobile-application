<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>환자 조회</title>
<style>
* {
	font-family: 나눔고딕;
}

table, td {
	border-top: 1px solid #444444;
	border-bottom: 1px solid #444444;
	border-collapse: collapse;
	text-align: center;
	padding: 5px;
}

#title {
	padding: 20px;
	font-size: 20pt;
	color: black;
	border: 2px solid #000000;
}

.menu {
	font-size: 12pt;
	color: black;
	text-decoration: none;
}

.menu:hover {
	color: white;
	background-color: black;
}

#link {
	padding: 10px;
	margin-right: 10px;
}

.btn {
	font-size: 10pt;
	height: 25px;
	width: 20px;
	background-color: #FFFFFF;
	color: black;
	border: 0px;
}

.btn2 {
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

.btn2:hover {
	border-top-color: #595959;
	background: #595959;
	color: #ccc;
}

.btn2:active {
	border-top-color: #2b2b2b;
	background: #2b2b2b;
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
	function search() {
		var form = document.pa_list;
		var name, room;

		name = form.name.value;
		room = form.room.value;
		if (name == "" && room == "") {
			alert("내용을 입력해주세요!");
			return;
		}

		form.action = "control.jsp?action=pa_list_req&value=down_1";
		form.submit();
	}
	function popUp(p_name, p_id) {
		var form = document.pa_list;

		form.p_name.value = p_name;
		form.p_id.value = p_id;

		form.action = "control.jsp?action=pa_rec_req";
		form.submit();

	}
	function pa_admin(p_name, p_id) {
		var form = document.pa_list;

		form.p_name.value = p_name;
		form.p_id.value = p_id;

		form.action = "control.jsp?action=pa_admin_req";
		form.submit();

	}
</script>
</head>
<body>
	<div align="center" id="left_div">
		<div id="title">
			<b>영남대학교병원 - 환자 정보</b>
			<div align="right" id="link">
				<a class="menu" href="control.jsp?action=pa_insert_req">환자 추가</a>&nbsp;
				<a class="menu" href="control.jsp?action=med_admin_req">투약시간 관리</a>&nbsp;
				<a class="menu" href="control.jsp?action=login_req">로그아웃</a>
			</div>
		</div>
		<br>
		<form name="pa_list" method="post"
			action="control.jsp?action=pa_rec_req">
			<input type="hidden" name="p_name"> 
			<input type="hidden" name="p_id">
			<input type="hidden" name="re" value="true">
			<table>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name"></td>
					<td rowspan=2><input type="button" class="btn2" value="검색"
						onClick="search()"> <input type="button" class="btn2"
						value="전체 조회"
						onClick="location.href='control.jsp?action=pa_list_req&value=down_1'">
					</td>
				</tr>
				<tr>
					<td>병실</td>
					<td><input type="text" name="room"></td>
				</tr>
			</table>
			<br> <br>
			<table>
				<tr>
					<td width=130>번호 
						<input class="btn" type="button" value="▲" onClick="location.href='control.jsp?action=pa_list_req&value=up_1'">
						<input class="btn" type="button" value="▼" onClick="location.href='control.jsp?action=pa_list_req&value=down_1'">
					</td>
					<td width=130>이름 
						<input class="btn" type="button" value="▲" onClick="location.href='control.jsp?action=pa_list_req&value=up_2'">
						<input class="btn" type="button" value="▼" onClick="location.href='control.jsp?action=pa_list_req&value=down_2'">
					</td>
					<td width=130>성별 
						<input class="btn" type="button" value="▲" onClick="location.href='control.jsp?action=pa_list_req&value=up_3'">
						<input class="btn" type="button" value="▼" onClick="location.href='control.jsp?action=pa_list_req&value=down_3'">
					</td>
					<td width=130>병실 
						<input class="btn" type="button" value="▲" onClick="location.href='control.jsp?action=pa_list_req&value=up_4'">
						<input class="btn" type="button" value="▼" onClick="location.href='control.jsp?action=pa_list_req&value=down_4'">
					</td>
					<td width=130>병명 
						<input class="btn" type="button" value="▲" onClick="location.href='control.jsp?action=pa_list_req&value=up_5'">
						<input class="btn" type="button" value="▼" onClick="location.href='control.jsp?action=pa_list_req&value=down_5'">
					</td>
					<td width=130>나이 
						<input class="btn" type="button" value="▲" onClick="location.href='control.jsp?action=pa_list_req&value=up_6'">
						<input class="btn" type="button" value="▼" onClick="location.href='control.jsp?action=pa_list_req&value=down_6'">
					</td>
					<td width=130>담당의사</td>
					<td width=130>　</td>
				</tr>
				<c:forEach var="pa" items="${requestScope.list}">
					<tr>
						<td>${pa.p_id}</td>
						<td>${pa.p_name}</td>
						<td>${pa.gen}</td>
						<td>${pa.p_room}</td>
						<td>${pa.disease}</td>
						<td>${pa.age}</td>
						<td>${pa.d_name}</td>
						<td>
							<input type="button" class="btn2" value="관리" onClick="pa_admin('${pa.p_name}', '${pa.p_id}')">
							<input type="button" id="pa_rec" class="btn2" value="기록" onClick="popUp('${pa.p_name}', '${pa.p_id}')">
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>

	<div id="right_div" align="center">
		<iframe width="100%" height="700" src="control.jsp?action=med_req"
			frameborder="0"></iframe>
		<iframe width="1" height="1" src="control.jsp?action=emer_req"
			frameborder="0"></iframe>
	</div>
</body>
</html>