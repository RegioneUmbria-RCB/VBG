<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.notifica_copia_allegati_stc_attivo" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.notifica_copia_allegati_stc_attivo" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
    <br class="clear" />
	<div id="subcontent">
		  
		  <div class="warning_backup_stc"><img style="padding-right: 5px;" alt="" src="${pageContext.request.contextPath}/images/warning.gif"><fmt:message key="alert.copia_allegati_stc_attivo"/></div>
		
	</div>
	<div id="functions">
		<ul>
	    	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
		</ul>
	</div>
</body>
</html>