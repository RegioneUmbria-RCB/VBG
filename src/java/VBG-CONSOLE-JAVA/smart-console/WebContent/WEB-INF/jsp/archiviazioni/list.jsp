<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_archiviazioni" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.lista_archiviazioni" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">
			<div id="error_msg" class="error_header">
			<c:if test="${not empty param.status_msg }">    
	    		<fmt:message key="${param.status_msg }"/><br />
	    		<c:out value="${param.error_msg }" />   	
	    	</c:if>
			</div>
			<form name="inviodati" action="list.htm">
				${htmlTable}
			</form>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:archivia()"><fmt:message key="button.esegui_archiviazione" /></a></li>
			</ul>
		</div>
		<script type="text/javascript">
		function archivia(){
			doHref('../archiviazioni/archivia.htm','<fmt:message key="alert.conferma_archiviazione"/>');
			
		}
		$('error_msg').pulsate({ pulses: 2, duration: 1.0 });
		</script>
	</body>
</html>