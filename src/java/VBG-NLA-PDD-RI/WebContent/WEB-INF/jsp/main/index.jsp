<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NODO NLA PDD RI</title>
</head>
<body>
	<h1>NODO NLA PDD Registro Imprese</h1>
	<c:if test="${not empty requestScope.MSG }">
		<h3>${requestScope.MSG }</h3>
	</c:if>
	<a href="reloadConfiguration.htm?ts_=<%=System.currentTimeMillis()%>">Ricarica
		configurazioni</a>
	<a href="test.htm?ts_=<%=System.currentTimeMillis()%>">test</a>
</body>
</html>