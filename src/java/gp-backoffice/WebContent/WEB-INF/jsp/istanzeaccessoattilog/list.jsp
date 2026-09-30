<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.istanzeAccessoAtti.title" />
	</title>
</head>
<body>	
	<span class="titoloPagina">
		<fmt:message key="label.istanzeAccessoAtti.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeaccessoattilog/list" />
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<fieldset><legend><fmt:message key="label.filtri" /></legend>			
			<c:if test="${not empty istanzeAccessoAttiFilter.anagrafe.descrizioneRichiedente}">
				<span class="parametri">
	            	<fmt:message key="label.nominativo" /> : 
	            	<label><c:out value="${istanzeAccessoAttiFilter.anagrafe.descrizioneRichiedente}" /></label>
	        	</span>
        	</c:if>
			<c:if test="${not empty istanzeAccessoAttiFilter.istanzeFilter.numeroistanza}">
				<span class="parametri">
	            	<fmt:message key="label.numero" /> : 
	            	<label><c:out value="${istanzeAccessoAttiFilter.istanzeFilter.numeroistanza}" /></label>
	        	</span>
        	</c:if>			
			<c:if test="${not empty istanzeAccessoAttiFilter.dallaData }">
				<span class="parametri">
					<fmt:message key="label.da" /> :
	              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${istanzeAccessoAttiFilter.dallaData}"/></label>
              	</span>
			</c:if>
			<c:if test="${not empty istanzeAccessoAttiFilter.allaData }">
				<span class="parametri">
					<fmt:message key="label.a" /> :
	              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${istanzeAccessoAttiFilter.allaData}"/></label>
              	</span>
			</c:if>		
			</fieldset>
		</div>
	<form name="inviodati" action="list.htm">
		${htmlTable}
	</form>
	<script type="text/javascript">
	var _jmesaUrl='list.htm?';
	var _captionTab='<fmt:message key="label.istanzeaccessoatti" />';
	
	</script>
	</div>
	<div id="functions">
		<ul>			
			<li><a href="javascript:doHref('createSearch.htm?resetAttrs=false','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>