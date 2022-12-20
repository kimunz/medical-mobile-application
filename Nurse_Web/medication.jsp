<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Refresh" Content="10; URL=control.jsp?action=med_req">
<title>Insert title here</title>
<style>
	* {font-family:나눔고딕;}
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
</style>
<script>
	
</script>
</head>
<body>
<div align="center">
	<form name="med_time" method="post" action="control.jsp">
		<br>
		<b>투약 시간 - ${med_time}</b>
		<br><br>
		<table>
			<tr>
				<td width="130">이름</td><td width="70">병명</td>
			</tr>
			<c:forEach var="list" items="${requestScope.med_list}">
				<tr>
					<td>${list.p_name} (${list.p_room}호)</td><td>${list.disease}</td>
				</tr>
			</c:forEach>
		</table>
	</form>	
</div>
</body>
</html>