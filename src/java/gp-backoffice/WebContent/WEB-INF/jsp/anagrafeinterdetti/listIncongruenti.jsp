<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.riassunto_importazione_interdetti" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.riassunto_importazione_interdetti" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
				<div class="etichetta">
					<div><fmt:message key="label.totale_soggetti_importare" />:</div>
					<div><fmt:message key="label.numero_soggetti_importati" />:</div>
					<div><fmt:message key="label.numero_soggetti_prensenti" />:</div>
					<div><fmt:message key="label.numero_soggetti_scartati" />:</div>
				</div>		
				<div class="parametro">       		 	
					<div>${anagrafeInterdettiHelper.totaleRecord}</div>
					<div>${anagrafeInterdettiHelper.recordImportati}</div>
					<div>${anagrafeInterdettiHelper.recordNonAggiornati}</div>
					<div>${anagrafeInterdettiHelper.recordScartati}</div>
				</div>
			</div>
			<br class="clear" />
	    <div class="titoloSezione">
	    	<fmt:message key="label.lista_soggetti_scartati" />
	    </div>
   		<form name="interdettiForm" action="listIncongruenti.htm">
		${htmltable}
	</form>
	
	<script type="text/javascript">
			var _jmesaUrl='listIncongruenti.htm?';
			var _captionTab='<fmt:message key="label.lista_soggetti_scartati"/>';
	</script>
		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>