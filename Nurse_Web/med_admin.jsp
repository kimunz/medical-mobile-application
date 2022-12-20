<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>투약정보 관리</title>
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

.combo {
	height: 25px;
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
</style>
<script>
	function med_insert() {
		var form = document.med_form;

		var id = form.patient.options.value;
		var hour = form.hour.options.value;
		var min = form.min.options.value;

		form.sel_id.value = id;
		form.sel_hour.value = hour;
		form.sel_min.value = min;
		
		form.sel_text.value = form.patient.options[form.patient.selectedIndex].text;
		
		var con = confirm("등록하시겠습니까?");
		
		if(con) {
			form.action = "control.jsp?action=med_insert";
			form.submit();
		}
	}
	function med_search() {
		var form = document.med_form;
		var id = form.patient.options.value;
		form.sel_id.value = id;

		form.sel_text.value = form.patient.options[form.patient.selectedIndex].text;
		
		form.action = "control.jsp?action=med_admin_req";
		form.submit();
	}
	function med_del(sel_pid, sel_ptime) {
		var form = document.med_form;
		form.sel_id.value = sel_pid;
		form.sel_time.value = sel_ptime;
		
		var con = confirm("삭제하시겠습니까?");
		
		if(con) {
			form.action = "control.jsp?action=med_del";
			form.submit();
		}
	}
</script>
</head>
<body>
	<div align="center" id="left_div">
		<div id="title">
			<b>영남대학교병원 - 투약정보 관리</b>

			<div align="right" id="link">
				<a class="menu" href="control.jsp?action=pa_list_req&value=down_1">돌아가기</a>&nbsp;
				<a class="menu" href="control.jsp?action=login_req">로그아웃</a>
			</div>
		</div>

		<br>

		<form name="med_form" method="post" action="control.jsp">
			<input type="hidden" name="sel_id"> 
			<input type="hidden" name="sel_hour"> 
			<input type="hidden" name="sel_min">
			<input type="hidden" name="sel_text">
			<input type="hidden" name="sel_time">

			<table border=1>
				<tr>
					<td><select id="sel_box" class="combo" name="patient">
							<c:forEach var="pa" items="${requestScope.pa_list}">
								<option value="${pa.p_id}">${pa.p_name}(${pa.p_room}호)</option>
							</c:forEach>
					</select>&nbsp; <input type="button" class="btn" value="조회"
						onClick="med_search()">&nbsp; <select class="combo"
						name="hour">
							<%
								for (int i = 0; i < 24; i++) {
							%>
							<option><%=i%></option>
							<%
								}
							%>
					</select> 시 <select class="combo" name="min">
							<%
								int j = 0;
								for (int i = 0; i < 4; i++) {
							%>
							<option><%=j%></option>
							<%
								j += 15;
								}
							%>
					</select> 분&nbsp; <input type="button" class="btn" value="등록"
						onClick="med_insert()"></td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<th width="500" height="40" colspan=2>${sel_text} 투약 시간</th>
				</tr>
				<c:forEach var="pa" items="${requestScope.pa_med_list}">
					<tr>
						<td width="400">${pa.med_time}</td>
						<td width="100"><input type="button" class="btn" value="삭제"
							onClick="med_del('${pa.p_id}','${pa.med_time}')"></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	<div id="right_div">
		<iframe width="100%" height="700" src="control.jsp?action=med_req"
			frameborder="0"></iframe>
		<iframe width="1" height="1" src="control.jsp?action=emer_req"
			frameborder="0"></iframe>
	</div>
</body>
</html>