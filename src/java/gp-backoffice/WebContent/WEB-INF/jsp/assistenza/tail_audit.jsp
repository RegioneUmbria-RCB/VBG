<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.io.File"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>tail log file</title>
</head>
<body>
	<script type="text/javascript">
		function configLog4j(){
			var url="${pageContext.request.contextPath}/log4j/";
			var stile="width=750, height=600, status=no, menubar=no, toolbar=no, scrollbars=yes, resizable=yes";
		    window.open(url, "", stile);		
		}
	</script>
	<c:if test="${error!=null}">
		<label style="color: red;">${error}</label>
	</c:if>
	<form action="tail_audit.htm">
		<table>
		<tr><td>File</td><td><select name="tomcatLogFileSelected">
			<c:forEach items="${logs}" var="_log">
				<c:if test="${tomcatLogFileSelected eq _log}">
					<option value="${_log }" selected="selected">${_log }</option>
				</c:if>
				<c:if test="${tomcatLogFileSelected ne _log}">
					<option value="${_log }">${_log }</option>
				</c:if>
			</c:forEach>
		</select></td></tr>
		<tr><td>Righe</td><td><input name="rowCount" size="4" value="${rowCount }" style="text-align: right;"/></td></tr>
		</table>
		<input type="submit" value="Visualizza Log"/>
		<br />
		<c:if test="${tomcatLogFileSelected!=null}">
			<br />
			<label>Tail del file: <%=File.separator %>${tomcatLogFileSelected}</label>
			<br />
			<div style="border: 1px solid black; padding: 2px;">
			<pre style="font-size:1.5em">
				<c:out value="${contenutoFile }" escapeXml="true" />
			</pre>
			</div>
		</c:if>
	</form>
</body>
</html>