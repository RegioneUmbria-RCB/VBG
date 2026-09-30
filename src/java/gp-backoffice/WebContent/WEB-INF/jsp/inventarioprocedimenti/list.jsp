<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title><fmt:message key="inventarioprocedimenti.label.lista_inventarioprocedimenti.title" /></title>
</head>
<body>
	<span class="titoloPagina">
	<init:editLabel key="inventarioprocedimenti.label.lista_inventarioprocedimenti.title" role="ROLE_EDITLABEL" />
	<%-- <fmt:message key="inventarioprocedimenti.label.lista_inventarioprocedimenti.title" />--%>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../inventarioprocedimenti/list" />
	</jsp:include>
	<div id="subcontent">
			<form name="inviodati" action="list.htm">
				${htmltable}
			</form>
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="inventarioprocedimenti.label.lista_inventarioprocedimenti.title" />';
			</script>
	</div>
	<div id="functions">
		<ul>
		    <li><a href="javascript:historySet('${_urlback}','..%2Finventarioprocedimenti/create.htm','')"><fmt:message key="button.new" /></a></li>
		    <%if(!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)){ %>
		    	<c:if test="${!isFvgSolAttivaAndConsole}">
		    		<li><a href="javascript:historySet('${_urlback}','..%2Finventarioprocedimenti/listEndobase.htm','')"><fmt:message key="button.endo_di_base" /></a></li>
		    	</c:if>
			<%}%>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>