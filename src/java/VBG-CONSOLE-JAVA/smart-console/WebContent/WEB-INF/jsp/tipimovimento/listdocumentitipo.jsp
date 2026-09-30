<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipimovimento.label.lista_tipimovimentodoctipo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipimovimento.label.lista_tipimovimentodoctipo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipimovimento/listdocumentitipo" />
		<jsp:param name="qs" value="tipimovimento.codice%3D${tipomovimentoinfo.id.tipomovimento}%26software%3D${tipomovimentoinfo.software.codice }" />			
	</jsp:include>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="tipimovimento.label.codice" />:</div>
				<div><fmt:message key="tipimovimento.label.movimento" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${tipomovimentoinfo.id.tipomovimento}" /></div>
	    	  <div><c:out value="${tipomovimentoinfo.movimento}" /></div>
			</div>
		</div>
		<div id="subcontent">
			<form name="tipimovimentodoctipoForm" action="listdocumentitipo.htm">
			<jmesa:springTableFacade
				id="tipimovimentodoctipo_id" 
				items="${tipimovimentodoctipoList}" 
				var="tipimovimentodoctipo_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
					<jmesa:htmlColumn property="letteretipo.descrizione" titleKey="label.documento_tipo" >
					<a href="javascript:historySet('${_urlback }','../tipimovimento/viewTipoDocumenti.htm?codiceMovimento=${tipimovimentodoctipo_var.id.tipomovimento}&codiceLettera=${tipimovimentodoctipo_var.id.codicelettera}&software=${tipimovimentodoctipo_var.tipomovimento.software.codice}','')">
					${tipimovimentodoctipo_var.letteretipo.descrizione}</a>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
					<a class="eliminaRiga" href="javascript:doHref('deleteDocumentoTipoFromList.htm?codicedoctipo=${tipimovimentodoctipo_var.id.codicelettera}&codicemovimento=${tipimovimentodoctipo_var.id.tipomovimento}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />&nbsp;${tipimovimentodoctipo_var.letteretipo.descrizione}">
								<label><fmt:message key="label.elimina" /></label>
					</a>
					</jmesa:htmlColumn>
				    </jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${tipomovimentoinfo.id.tipomovimento}" name="tipimovimento.codice" />
			</form>
		<script type="text/javascript">
			var _jmesaUrl='listdocumentitipo.htm?tipimovimento.codice=${tipomovimentoinfo.id.tipomovimento}&';
			var _captionTab='<fmt:message key="tipimovimento.label.lista_tipimovimentodoctipo.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createTipiDocumento.htm?codicetipomovimento=${tipomovimentoinfo.id.tipomovimento}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>