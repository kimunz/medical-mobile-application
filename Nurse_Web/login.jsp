<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HOPE - LOGIN</title>
<style>
	* {font-family:나눔고딕;}
	table, td {
		border-top: 1px solid #444444;
		border-bottom: 1px solid #444444;
		border-collapse: collapse;
		text-align: center;
		padding: 5px;
	}
	#title {padding:20px; font-size: 20pt; font-weight:bold; color:black; border:2px solid #000000;}
	.btn {
		border: 0px;
		background: #2b2b2b;
		background: -webkit-gradient(linear, left top, left bottom, from(#595959), to(#2b2b2b));
		background: -webkit-linear-gradient(top, #595959, #2b2b2b);
		background: -moz-linear-gradient(top, #595959, #2b2b2b);
		background: -ms-linear-gradient(top, #595959, #2b2b2b);
		background: -o-linear-gradient(top, #595959, #2b2b2b);
		padding: 5px 10px;
		-webkit-border-radius: 8px;
		-moz-border-radius: 8px;
		border-radius: 8px;
		-webkit-box-shadow: rgba(0,0,0,1) 0 1px 0;
		-moz-box-shadow: rgba(0,0,0,1) 0 1px 0;
		box-shadow: rgba(0,0,0,1) 0 1px 0;
		text-shadow: rgba(0,0,0,.4) 0 1px 0;
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
	input {border:none;}
</style>
<script>
function onEnterLogin(){
	var keyCode = window.event.keyCode;

	if (keyCode == 13) { //엔테키 이면
		loginCheck();
	}
}

function loginCheck() {
		var form = document.login_form;
		var id, pw;
		
		id = form.id.value;
		pw = form.pw.value;
	
		if(id=="") { alert("아이디를 입력해주세요."); return; }
		if(pw=="") { alert("비밀번호를 입력해주세요."); return; }
		form.action = "control.jsp?action=login";
		form.submit();
	}
</script>
</head>
<body onkeydown="javascript:onEnterLogin();">
<div align=center>
<div id="title">영남대학교병원 - 로그인</div>
<br><br><br>
	<form name="login_form" method="post" action="control.jsp">
		<table>
			<tr>
				<td><b>ID&nbsp;</b></td>
				<td><input id="id" type="text" name="id" value="9999"></td>
			</tr>
			<tr>
				<td><b>PW&nbsp;</b></td>
				<td><input id="pw" type="password" name="pw" value="1234"></td>
			</tr>
		</table>
		<br><input class="btn" type="button" value="로그인" onClick="loginCheck()">
	</form>
</div>
</body>
</html>