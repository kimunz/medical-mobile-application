<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
* {
	font-family: 나눔고딕;
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

.box {
	border: 0px solid #000000;
	font-size: 15px;
	height: 30px;
}

.box2 {
	width: 150px;
	font-size: 15px;
	height: 30px;
}

</style>
<script>
function pa_update(){
	var form = document.pa_admin_form;
	var id = form.sel_dList.options.value;
	
	form.sel_did.value = id;
	
	form.action = "control.jsp?action=pa_update";
	form.submit();
}
function pa_del() {
	var form = document.pa_admin_form;
	
	if (confirm("삭제하시겠습니까?") == true) {
		form.action = "control.jsp?action=pa_del";
		form.submit();
	} else return;
	
}
</script>
</head>
<body>
<div align="center" id="left_div">
		<div id="title">
			<b>영남대학교병원 - 환자 관리</b>

			<div align="right" id="link">
				<a class="menu" href="control.jsp?action=pa_list_req&value=down_1">돌아가기</a>&nbsp;
				<a class="menu" href="control.jsp?action=login_req">로그아웃</a>
			</div>
		</div>

		<br><br>

		<form name="pa_admin_form" method="post" action="control.jsp">
			<input type="hidden" name="sel_id">
			<input type="hidden" name="sel_did">
			<input type="hidden" name="result" value="${re}">
			<table>
				<c:forEach var="pa" items="${requestScope.pa_list}">
					<tr>
						<td><b>이름</b></td><td><input class="box" name="p_name" type="text" value="${pa.p_name}" readonly></td>
					</tr>
					<tr>
						<td><b>병실</b></td><td><input class="box" name="p_room" type="text" value="${pa.p_room}"></td>
					</tr>
					<tr>
						<td><b>담당의사 번호</b></td>
						<td>
							<select name="sel_dList" class="box2">
								<option value="">선택</option>
								<c:forEach var="d" items="${requestScope.d_list}">
									<option value="${d.d_id}">${d.d_name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><b>금지음식</b></td><td><textarea rows="3" name="forbid_food">${pa.forbid_food}</textarea>
						<input type="hidden" name="p_id" value="${pa.p_id}">
						<input type="hidden" name="del_d_id" value="${pa.d_id}"></td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<input type="button" class="btn" value="수정" onClick="pa_update()">&nbsp;
			<input type="button" class="btn" value="삭제" onClick="pa_del()">
		</form>
	</div>
	<div id="right_div">
		<iframe width="100%" height="700" src="control.jsp?action=med_req"
			frameborder="0"></iframe>
		<iframe width="1" height="1" src="control.jsp?action=emer_req"
			frameborder="0"></iframe>
	</div>
<script>
var result = document.pa_admin_form.result.value;
if(result=="fail") alert("의사 번호가 존재하지 않습니다!");
</script>
</body>
</html>