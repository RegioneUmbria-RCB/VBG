<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.anagrafeimpresa-aree-pubbliche" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.anagrafeimpresa-aree-pubbliche" />
	</div>
	<div class="descrizione">
		<fieldset><legend><fmt:message key="label.filtro-anagrafeimpresa" /></legend>
			<c:if test="${not empty filter.codicefiscale}"><fmt:message key='label.codicefiscale' />: ${filter.codicefiscale}<br /></c:if>
			<c:if test="${not empty filter.cognome}"><fmt:message key='label.cognome' />: ${filter.cognome}<br /></c:if>
			<c:if test="${not empty filter.denominazione}"><fmt:message key='label.denominazione' />: ${filter.denominazione}<br /></c:if>
			<c:if test="${not empty filter.cfPi}"><fmt:message key='label.cf_pi' />: ${filter.cfPi}<br /></c:if>
			<c:if test="${not empty filter.numeroRegImprese}"><fmt:message key='label.numero-registro-imprese' />: ${filter.numeroRegImprese}<br /></c:if>
		</fieldset>
	</div>

	<form name="anagrafeimpresaForm" action="search.htm" method="post" id="anagrafeimpresaFormId">
		<jmesa:springTableModel id="anagrafeimpresaTableId" items="${anagrafeImpresaList}" var="bean"  stateAttr="restore">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
				    <jmesa:htmlColumn titleKey="label.nominativo" property="descrizionePersonaFisica" filterable="false" sortable="false" />
				    <jmesa:htmlColumn titleKey="label.in-qualita-di" property="inQualitaDi" filterable="false" sortable="false" />
				    <jmesa:htmlColumn titleKey="label.denominazione" property="denominazione" filterable="false" sortable="false"/>
					<jmesa:htmlColumn titleKey="label.sede-legale" property="descrizioneSedeLegale" filterable="false" sortable="false"/>
					<jmesa:htmlColumn titleKey="label.cf_pi" property="cfPi" filterable="false" sortable="false"/>
					<jmesa:htmlColumn titleKey="label.iscrizione-registro-imprese" property="iscrizioneRegImprese" filterable="false" sortable="false"/>
					<jmesa:htmlColumn titleKey="label.numero-registro-imprese" property="numeroRegImprese" filterable="false" sortable="false"/>
					<jmesa:htmlColumn filterable="false" sortable="false" width="20%">
						<a class="table_button" href="javascript:view('${bean.id.codice}','list')" title="<fmt:message key='label.visualizza' />"><fmt:message key='button.visualizza' /></a>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
		<input type="hidden" name="denominazione" value="${filter.denominazione }" />
		<input type="hidden" name="numeroRegImprese" value="${filter.numeroRegImprese }" />
		<input type="hidden" name=cfPi value="${filter.cfPi }" />
		<input type="hidden" name="cognome" value="${filter.cognome }" />
		<input type="hidden" name="codicefiscale" value="${filter.codicefiscale }" />
		
	</form>
	<br />
	<spring-security:authorize ifNotGranted="ROLE_READONLY">
		<a class="button" id="imprese-aree-pubbliche" href="#" onclick="view('','list')" title="<fmt:message key='label.nuova-impresa-areepubbliche' />"><fmt:message key='button.nuova-impresa-areepubbliche' /></a>
	</spring-security:authorize>

	<a class="button" id="chiudi" href="#" title="<fmt:message key='label.chiudi' />"><fmt:message key='button.chiudi' /></a>
	<script type="text/javascript">
		var _jmesaUrl='${pageContext.request.contextPath}/anagrafeimpresa/search.htm?';
		function onInvokeExportAction(id) {
			var parameterString = jQuery.jmesa.createParameterStringForLimit(id);
			var formUrl = _jmesaUrl + parameterString + "&denominazione=${filter.denominazione }&numeroRegImprese=${filter.numeroRegImprese }&cfPi=${filter.cfPi }&cognome=${filter.cognome }&codicefiscale=${filter.codicefiscale }";
			location.href = formUrl;
			
			
		}
		$(".table_button").button();
		function view(codice,returnto){
			location.href="${pageContext.request.contextPath}/anagrafeimpresa/view.htm?codice="+codice+"&returnto="+returnto;
		}
		$("#chiudi").click(function(){
			location.href="${pageContext.request.contextPath}/anagrafeimpresa/startSearch.htm";
		});
	</script>
</body>
</html>