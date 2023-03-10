<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
* {
	font-family: ëëęł ë;
}

table, td, th {
	border-top: 1px solid #444444;
	border-bottom: 1px solid #444444;
	border-collapse: collapse;
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
	border-radius: 3px;
	border: 0px solid #000000;
	font-size: 15px;
	height: 30px;
	width: 180px;
}

.box2 {
	width: 70px;
	font-size: 15px;
	height: 30px;
}

.a {
	text-align: center;
}
</style>
<script>
function pa_insert() {
	var form = document.pa_insert_form;
	var id = form.sel_dList.options.value;
	var gender = form.gen.options.value;
	var y = form.sel_year.options.value;
	var d = form.sel_day.options.value;
	var m = form.sel_month.options.value;
	var pname = form.p_name.value;
	var proom = form.p_room.value;
	var birth = y+m+d;
	
	if(id=="" || y=="" || d=="" || m=="" || pname=="" || proom=="") {
		alert("ë´ěŠě ëŞ¨ë ěë Ľí´ěŁźě¸ě!!!");
		return;
	}
	
	form.sel_did.value = id;
	form.sel_gen.value = gender;
	form.sel_birth.value = birth;
	
	if(confirm("ëąëĄíěę˛ ěľëęš?") == true) {
		form.action = "control.jsp?action=pa_insert";
		form.submit();
	} else return;
}
</script>
</head>
<body>
<div align="center" id="left_div">
		<div id="title">
			<b>ěë¨ëíęľëłě - íě ę´ëŚŹ</b>

			<div align="right" id="link">
				<a class="menu" href="control.jsp?action=pa_list_req&value=down_1">ëěę°ę¸°</a>&nbsp;
				<a class="menu" href="control.jsp?action=login_req">ëĄęˇ¸ěě</a>
			</div>
		</div>

		<br><br>

		<form name="pa_insert_form" method="post" action="control.jsp">
			<input type="hidden" name="sel_did">
			<input type="hidden" name="sel_gen">
			<input type="hidden" name="sel_birth">
			<input type="hidden" name="result" value="${re}">
			<table>
				<tr>
					<td width="150" class="a"><b>íě ë˛í¸</b></td><td><input class="box" maxlength="8" name="p_id" type="text"></td>
				</tr>
				<tr>
					<td class="a"><b>ě´ëŚ</b></td><td><input class="box" name="p_name" type="text"></td>
				</tr>
				<tr>
					<td class="a"><b>ěąëł</b></td>
					<td align="left">
						<select name="gen" class="box2">
								<option value="">ěąëł</option>
								<option value="ěŹ">ěŹ</option>
								<option value="ë¨">ë¨</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="a"><b>ěëěěź</b></td>
					<td>
						<select name="sel_year" class="box2">
							<option value="">ëë</option>
							<c:forEach var="i" begin="1930" end="${str_year}" step="1">
								<option value="${i}">${i}</option>
							</c:forEach>
						</select>
						<select name="sel_month" class="box2">
							<option value="">ě</option>
							<c:forEach var="i" begin="1" end="12" step="1">
								<c:choose>
									<c:when test="${i < 10}">
										<option value="0${i}">0${i}</option>
									</c:when>
									<c:otherwise>
										<option value="${i}">${i}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
						<select name="sel_day" class="box2">
							<option value="">ěź</option>
							<c:forEach var="i" begin="1" end="31" step="1">
								<c:choose>
									<c:when test="${i < 10}">
										<option value="0${i}">0${i}</option>
									</c:when>
									<c:otherwise>
										<option value="${i}">${i}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="a"><b>ëłëŞ</b></td><td><input class="box" name="disease" type="text"></td>
				</tr>
				<tr>
					<td class="a"><b>ëłě¤</b></td><td><input class="box" name="p_room" type="text"><b>í¸</b></td>
				</tr>
				<tr>
					<td class="a"><b>ë´ëš ěěŹ</b></td>
					<td>
						<select name="sel_dList" class="box2">
							<option value="">ě í</option>
							<c:forEach var="d" items="${requestScope.d_list}">
								<option value="${d.d_id}">${d.d_name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="a"><b>ę¸ě§ěě</b></td><td><textarea cols="30" rows="3" name="forbid_food"></textarea></td>
				</tr>
			</table>
			<br>
			<input type="button" class="btn" value="ëąëĄ" onClick="pa_insert()">
		</form>
	</div>
	<div id="right_div">
		<iframe width="100%" height="700" src="control.jsp?action=med_req"
			frameborder="0"></iframe>
		<iframe width="1" height="1" src="control.jsp?action=emer_req"
			frameborder="0"></iframe>
	</div>
<script>
var result = document.pa_insert_form.result.value;
if(result=="true") alert("ëąëĄëěěľëë¤!");
</script>
</body>
</html>