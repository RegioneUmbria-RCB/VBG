<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.istanze.title.search" /></title>
	</head>
	<body>
		<span class="titoloPagina"></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		
		<div id="subcontent">	
		<form name="istanzeForm" action="reportResult.htm"><jmesa:springTableFacade
	id="istanza_id" items="${report}" var="istanza_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.codice" width="2%">
                 ${istanza_var.istanza.numeroistanza}
            </jmesa:htmlColumn>
			<jmesa:htmlColumn property="chiusa"	titleKey="label.esito">
				<c:choose>
				<c:when test="${istanza_var.chiusa eq true}">
					<fmt:message key="button.ok" />
				</c:when>
				<c:otherwise>
					${istanza_var.messaggioErrore}
				</c:otherwise>
				</c:choose>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

			<script type="text/javascript">
				var _jmesaUrl='reportResult.htm?';
				var _captionTab='<fmt:message key="label.istanze" />';
			</script>
			</div>
		
    	
    	<div id="functions">
			<ul>				
				<li><a href="javascript:chiudi();"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>