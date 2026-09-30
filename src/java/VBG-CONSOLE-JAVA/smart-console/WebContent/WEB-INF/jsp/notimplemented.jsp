<%@ include file="includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Error</title>
</head>
<body>
<div id="subcontent">

<div id="status_msg" class="error_header"><b>
	<fmt:message key="alert.notimplemented" />
</b></div>

</div>
<div id="functions">
<ul>
	<li>
		<a	href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
			key="button.back" /></a>
	</li>
</ul>
</div>
</body>
</html>