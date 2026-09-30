<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Download documenti</title>
	</head>
	<body>
		<h2 align="center">Download documenti</h2>
		
		<c:if test="${not empty pageContext.exception}">
			<p align='center'><font color='red'><c:out escapeXml="true" value="${pageContext.exception.message}" default="L'applicazione ha generato un errore inatteso."/></font></p>
		</c:if>
		
		<form name='f' action='<%=request.getContextPath() %>/main/viewZipLogico.htm' method="post">
			<input type='hidden' value='<%=request.getParameter("a") %>' name='a' />
			<input type='hidden' value='<%=request.getParameter("u") %>' name='u' />
			<input type='hidden' value='<%=request.getParameter("m") %>' name='m' />
			<table align="center">
			    <tr>
			    	<td>
			    	<fmt:message key="html.pin" /> <input type='text' name='pin' />
			    	<input name="submit" type="submit" value="Scarica il documento" />
			    	</td>
			    </tr>
		 	</table>
		</form>
	</body>
</html>