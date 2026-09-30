<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.manifestazioni" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.manifestazioni" />
	</div>
	<div class="descrizione">
		<fieldset><legend><fmt:message key="label.filtro-manifestazione" /></legend>
		<c:if test="${not empty filter.denominazione }"><fmt:message key='label.denominazione' />: ${filter.denominazione }<br /></c:if>
		<c:if test="${not empty filter.dal }"><fmt:message key='label.dal' />: <fmt:formatDate value="${filter.dal }" pattern="dd/MM/yyyy" /><br /></c:if>
		<c:if test="${not empty filter.al }"><fmt:message key='label.al' />: <fmt:formatDate value="${filter.al }" pattern="dd/MM/yyyy" /><br /></c:if>
		<c:if test="${not empty filter.tipologia }"><fmt:message key='label.tipologia' />: ${filter.tipologia }<br /></c:if>
		<c:if test="${not empty filter.luogoSvolgimento }"><fmt:message key='label.luogo-svolgimento' />: ${filter.luogoSvolgimento }<br /></c:if>
		<c:if test="${not empty filter.organizzatore }"><fmt:message key='label.organizzatore' />: ${filter.organizzatore }<br /></c:if>
		<c:if test="${not empty filter.comune }"><fmt:message key='label.comune-svolgimento' />: ${filter.comune }<br /></c:if>
		</fieldset>
	</div>
	<c:if test="${filter.tipoManifestazione eq 'FM' }">
	<div class="titolo_sottosezione">
		<fmt:message key="label.fiere-mostre" />
	</div>
	<form name="fiereMostreForm" action="search.htm" method="post" id="fiereMostreFormId">
		<jmesa:springTableModel id="fiereMostreTableId" items="${fiereMostreList}" var="bean" exportTypes="excel" stateAttr="restore">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.data-inserimento" property="dataInserimento" filterable="false" sortable="true" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
					<jmesa:htmlColumn titleKey="label.periodi" property="periodi" filterable="false" sortable="false">
						<c:forEach items="${bean.fiereMostrePeriodisTransient }" var="periodo">
							<fmt:formatDate value="${periodo.dal }" pattern="dd/MM/yyyy" /> - <fmt:formatDate value="${periodo.al }" pattern="dd/MM/yyyy" /><br />
						</c:forEach>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.denominazione" property="denominazione" filterable="false" sortable="true">
						<a href="#" onclick="view('FM','${bean.id.codice}','list')">${bean.denominazione }</a>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.comune-svolgimento" property="comuneSvolgimento.comune" filterable="false" sortable="true" />
					<jmesa:htmlColumn titleKey="label.luogo-svolgimento" property="luogoSvolgimento" filterable="false" sortable="true" />
					
					<jmesa:htmlColumn titleKey="label.merceologie" property="merceologie" filterable="false" sortable="false">
						<c:forEach items="${bean.fiereMostreMerceologiesTransient }" var="merceologia">
							<c:out value="${merceologia.merceologia }" /><br />
						</c:forEach>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.organizzatore" property="organizzatore" filterable="false" sortable="true" />
					<jmesa:htmlColumn titleKey="label.classificazione" property="classificazione" filterable="false" sortable="true" />
					<%-- <jmesa:htmlColumn titleKey="label.qualifica" property="qualifica" filterable="false" sortable="true" /> --%>
					<jmesa:htmlColumn titleKey="label.tipologia" property="tipologia" filterable="false" sortable="true" />
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
		<input type="hidden" name="tipoManifestazione" value="${filter.tipoManifestazione }" />
		<input type="hidden" name="denominazione" value="${filter.denominazione }" />
		<input type="hidden" name="dal" value="<fmt:formatDate value="${filter.dal }" pattern="dd/MM/yyyy" />" />
		<input type="hidden" name="al" value="<fmt:formatDate value="${filter.al }" pattern="dd/MM/yyyy" />" />
		<input type="hidden" name="luogoSvolgimento" value="${filter.luogoSvolgimento }" />
		<input type="hidden" name="organizzatore" value="${filter.organizzatore }" />
		<input type="hidden" name="codicecomune" value="${filter.codicecomune }" />
	</form>
	<br />
	<spring-security:authorize ifNotGranted="ROLE_READONLY">
	<a class="button" id="fiere-mostre" href="#" onclick="view('FM','','list')" title="<fmt:message key='label.nuovo-fiere-mostre' />"><fmt:message key='button.nuovo-fiere-mostre' /></a>
	</spring-security:authorize>
	</c:if>
	<c:if test="${filter.tipoManifestazione eq 'FS' }">
	<div class="titolo_sottosezione">
		<fmt:message key="label.feste-sagre" />
	</div>
	<form name="festeSagreForm" action="search.htm" method="post" id="festeSagreFormId">
		<jmesa:springTableModel id="festeSagreTableId" items="${festeSagreList}" var="bean" exportTypes="excel" stateAttr="restore">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.data-inserimento"  property="dataInserimento" filterable="false" sortable="true" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
					<jmesa:htmlColumn titleKey="label.dal" property="dal" filterable="false" sortable="true" cellEditor="org.jmesa.view.editor.DateCellEditor" pattern="dd/MM/yyyy" />
					<jmesa:htmlColumn titleKey="label.al" property="al" filterable="false" sortable="true" cellEditor="org.jmesa.view.editor.DateCellEditor" pattern="dd/MM/yyyy" />
					<jmesa:htmlColumn titleKey="label.denominazione" property="denominazione" filterable="false" sortable="true">
						<a href="#" onclick="view('FS','${bean.id.codice}','list')">${bean.denominazione }</a>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.tipologia" property="tipologia" filterable="false" sortable="true" />
					<jmesa:htmlColumn titleKey="label.comune-svolgimento" property="comuneSvolgimento.comune" filterable="false" sortable="true" />
					<jmesa:htmlColumn titleKey="label.luogo-svolgimento" property="luogoSvolgimento" filterable="false" sortable="true" />
					<jmesa:htmlColumn titleKey="label.organizzatore" property="organizzatore" filterable="false" sortable="true" />	
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
		<input type="hidden" name="tipoManifestazione" value="${filter.tipoManifestazione }" />
		<input type="hidden" name="denominazione" value="${filter.denominazione }" />
		<input type="hidden" name="dal" value="<fmt:formatDate value="${filter.dal }" pattern="dd/MM/yyyy" />" />
		<input type="hidden" name="al" value="<fmt:formatDate value="${filter.al }" pattern="dd/MM/yyyy" />" />
		<input type="hidden" name="tipologia" value="${filter.tipologia }" />
		<input type="hidden" name="luogoSvolgimento" value="${filter.luogoSvolgimento }" />
		<input type="hidden" name="organizzatore" value="${filter.organizzatore }" />
		<input type="hidden" name="codicecomune" value="${filter.codicecomune }" />
	</form>
	<br />
	<spring-security:authorize ifNotGranted="ROLE_READONLY">
	<a class="button" id="feste-sagre" href="#" onclick="view('FS','','list')" title="<fmt:message key='label.nuovo-feste-sagre' />"><fmt:message key='button.nuovo-feste-sagre' /></a>
	</spring-security:authorize>
	</c:if>
	<a class="button" id="chiudi" href="#" title="<fmt:message key='label.chiudi' />"><fmt:message key='button.chiudi' /></a>
	<script type="text/javascript">
		var _jmesaUrl='${pageContext.request.contextPath}/manifestazioni/search.htm?';
		function onInvokeExportAction(id) {
			var parameterString = jQuery.jmesa.createParameterStringForLimit(id);
			var dal = '<fmt:formatDate value="${filter.dal }" pattern="dd/MM/yyyy" />';
			var al = '<fmt:formatDate value="${filter.al }" pattern="dd/MM/yyyy" />';
			var formUrl = _jmesaUrl + parameterString + "&tipoManifestazione=${filter.tipoManifestazione}&denominazione=${filter.denominazione }&dal="+dal+"&al="+al+
			"&tipologia=${filter.tipologia }&luogoSvolgimento=${filter.luogoSvolgimento }&organizzatore=${filter.organizzatore }&codicecomune=${filter.codicecomune }";
			location.href = formUrl;
		}
		function view(tipo,codice,returnto){
			location.href="${pageContext.request.contextPath}/manifestazioni/view.htm?tipo="+tipo+"&codice="+codice+"&returnto="+returnto;
		}
		$("#chiudi").click(function(){
			location.href="${pageContext.request.contextPath}/manifestazioni/startSearch.htm?tipo=${filter.tipoManifestazione}";
		});
	</script>
</body>
</html>