<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.lista_domandestc" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="label.lista_domandestc" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<div id="subcontent">

	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="domandestc" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		    <jsp:param name="path" value="../domandestc/list" />
	</jsp:include>


<form name="domandestcForm" action="list.htm">
	${htmltable}
</form>
	<script type="text/javascript">
		var _jmesaUrl='list.htm?';
		var _captionTab='<fmt:message key="label.lista_domandestc" />';
	</script>
</div>
<div id="functions">
<ul>
	<li><a
		href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>