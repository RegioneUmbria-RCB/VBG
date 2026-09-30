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
		<fmt:message key="label.manifestazioni-aree-pubbliche" />
	</div>
	<div class="descrizione">
		<fieldset><legend><fmt:message key="label.filtro-manifestazione" /></legend>
		<c:if test="${not empty filter.denominazione}"><fmt:message key='label.denominazione' />: ${filter.denominazione}<br /></c:if>
		<c:if test="${not empty filter.tipologia}"><fmt:message key='label.tipologia' />: ${filter.tipologia}<br /></c:if>
	    <c:if test="${not empty filter.cadenza}"><fmt:message key='label.cadenza' />: ${filter.cadenza}<br /></c:if>
		<c:if test="${not empty filter.comune}"><fmt:message key='label.comune-svolgimento' />: ${filter.comune}<br /></c:if>
		</fieldset>
	</div>

	<form name="manifestazioniAreepubblicheForm" action="search.htm" method="post" id="manifestazioniAreepubblicheFormId">
		<jmesa:springTableModel id="manifestazioniAreepubblicheTableId" items="${manifAreePubblicheList}" var="bean" exportTypes="excel" stateAttr="restore">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.data-inserimento" property="dataInserimento" filterable="false" sortable="true" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
					<jmesa:htmlColumn titleKey="label.denominazione" property="denominazione" filterable="false" sortable="false">
						<a href="#" onclick="view('${bean.id.codice}','list')">${bean.denominazione }</a>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.tipologia" property="tipologia" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.cadenza" property="cadenza" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.periodo-svolgimento" property="periodoSvolgimento" filterable="false" sortable="false"/>
					<jmesa:htmlColumn titleKey="label.numero-posteggi-assegnati" property="numeroPosteggiAssegnati" filterable="false" sortable="false" width="5%" />
					<jmesa:htmlColumn titleKey="label.numero-posteggi-spuntisti" property="numeroPosteggiSpuntisti" filterable="false" sortable="false" width="5%"/>
					<jmesa:htmlColumn titleKey="label.comune-svolgimento" property="comuni.comune" filterable="false" sortable="false" width="5%"/>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
		<input type="hidden" name="denominazione" value="${filter.denominazione }" />
		<input type="hidden" name="tipologia" value="${filter.tipologia }" />
		<input type="hidden" name="cadenza" value="${filter.cadenza }" />
		<input type="hidden" name="codicecomune" value="${filter.codicecomune }" />
		
	</form>
	<br />
	<spring-security:authorize ifNotGranted="ROLE_READONLY">
		<a class="button" id="fiere-mostre" href="#" onclick="view('','list')" title="<fmt:message key='label.nuova-manifestazione-areepubbliche' />"><fmt:message key='button.nuova-manifestazione-areepubbliche' /></a>
	</spring-security:authorize>

	<a class="button" id="chiudi" href="#" title="<fmt:message key='label.chiudi' />"><fmt:message key='button.chiudi' /></a>
	<script type="text/javascript">
		var _jmesaUrl='${pageContext.request.contextPath}/manifareepubbliche/search.htm?';
		function onInvokeExportAction(id) {
			var parameterString = jQuery.jmesa.createParameterStringForLimit(id);
			var formUrl = _jmesaUrl + parameterString + "&denominazione=${filter.denominazione }&tipologia=${filter.tipologia }&codicecomune=${filter.codicecomune }&cadenza=${filter.cadenza }";
			location.href = formUrl;
			
			
		}
		function view(codice,returnto){
			location.href="${pageContext.request.contextPath}/manifareepubbliche/view.htm?codice="+codice+"&returnto="+returnto;
		}
		$("#chiudi").click(function(){
			location.href="${pageContext.request.contextPath}/manifareepubbliche/startSearch.htm";
		});
	</script>
</body>
</html>