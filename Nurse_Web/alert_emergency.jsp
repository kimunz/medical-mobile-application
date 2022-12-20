<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Refresh" Content="3; URL=control.jsp?action=emer_req">
<title>Insert title here</title>
<script>
</script>
</head>
<body>
<form name="emer_form" method="post" action="control.jsp">
	<input type="hidden" name="list" value="${emer_list}">
</form>
<script>
var form = document.emer_form;
var val = form.list.value;
if(val != "") {
	val = "\t<긴 급 호 출>\n\n" + val;
	alert(val);
}
</script>
</body>
</html>