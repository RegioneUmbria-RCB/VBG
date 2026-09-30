<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<?xml version="1.0" encoding="UTF-8" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.welcome.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.welcome.title" />
</span>
<div id="subcontent">
	<div><label><fmt:message key="label.welcome.description" /></label></div>
	
	
	<br />
	<c:if test="${fn:length(dizionaridaElaborare) > 0}">
		<div class="error_header">Ci sono dizionari da elaborare
		
		<br/>
		<ul id="functions">
			<li><a href="../cart/view.htm?software=SS">Clicca per elaborare</a></li>
		</ul>
		</div>
	</c:if>
	
</div>
<div id="functions"></div>
</body>
</html>