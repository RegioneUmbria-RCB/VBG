<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.elenco_procedimenti" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="label.elenco_procedimenti" /></span>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../istanzeprocedimenti/riepilogo" />
	<jsp:param name="qs" value="codiceIstanza%3D${istanze.id.codice}" />
</jsp:include>
<div id="subcontent"><c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${istanze.id.codice}</c:param>
</c:import> <br class="clear" />


		<span id="infoAggiuntive">
		    <fieldset><legend><a title="<fmt:message key="button.gestione_allegati" />" href="javascript:historySet('${_urlback}','../documentiistanza/list.htm?codiceIstanza=${istanze.id.codice}','')"><fmt:message key="label.situazione_allegati_endo_and_istanza"/></a></legend>
			<div class="jmesa">
				<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td><fmt:message key="label.endoprocedimenti_numero_allegati_richiesti"/>: <font class="red">${allegatiRichiesti}</font></td>
							<td><fmt:message key="label.endoprocedimenti_numero_allegati_presentati"/>: <font class="red">${allegatiPresentati}</font></td>
							<td><fmt:message key="label.endoprocedimenti_numero_allegati_non_validi"/>: <font class="red">${allegatiNonValidi}</font></td>
							<td><fmt:message key="label.endoprocedimenti_numero_allegati_validi"/>: <font class="red">${allegatiValidi}</font></td>
						</tr>
					</thead>	
				</table>
			</div>
			</fieldset>
		</span>


<form name="istanzeprocedimentiForm" action="riepilogo.htm"><jmesa:springTableFacade
	id="procedimenti_istanze_id" items="${istanzeprocedimentiList}"
	var="istanzeprocedimenti_var" stateAttr="restore">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn
				property="istanzeprocedimenti.inventarioprocedimenti.tipoendo.tipo"
				titleKey="label.categoria_endo" />
			<jmesa:htmlColumn width="15%"
				property="istanzeprocedimenti.inventarioprocedimenti.procedimento"
				titleKey="label.procedimento" />
			<jmesa:htmlColumn width="8%"
				property="istanzeprocedimenti.inventarioprocedimenti.amministrazioni.amministrazione"
				titleKey="label.amministrazione" />
			<jmesa:htmlColumn
				property="istanzeprocedimenti.inventarioprocedimenti.naturaendo.natura"
				titleKey="label.natura" />
			<jmesa:htmlColumn property="istanzeprocedimenti.dataattivazione"
				titleKey="label.data" sortable="false" filterable="false"
				cellEditor="org.jmesa.view.editor.DateCellEditor"
				pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
			<jmesa:htmlColumn property="istanzeprocedimenti.perprovvedimento"
				titleKey="label.avvia" sortable="false" filterable="false" headerEditor="org.jmesa.custom.AvviaHeaderEditor">
				<c:set var="autorizImageName" value="error.png" />
				<c:if test="${istanzeprocedimenti_var.istanzeprocedimenti.perprovvedimento eq true}">
					<c:set var="autorizImageName" value="success.png" />
				</c:if>
				<img title="<fmt:message key="label.avvia" />"
					src="${pageContext.request.contextPath}/images/${autorizImageName}" />
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="istanzeprocedimenti.acquisito"
				titleKey="label.acquis" sortable="false" filterable="false" headerEditor="org.jmesa.custom.AcquisitoHeaderEditor">
				<c:set var="acquisImageName" value="error.png" />
				<c:if test="${istanzeprocedimenti_var.istanzeprocedimenti.acquisito eq true}">
					<c:set var="acquisImageName" value="success.png" />
				</c:if>
				<img title="<fmt:message key="label.acquis" />"
					src="${pageContext.request.contextPath}/images/${acquisImageName}" />
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="movimentoTrasmissione.data"
				titleKey="label.trasmissione" sortable="false" filterable="false">
				<c:set var="trasmisImageName" value="error.png" />
				<c:set var="title" value="" />
				<c:if test="${istanzeprocedimenti_var.istanzeprocedimenti.perprovvedimento eq true}">
				<c:if test="${not empty istanzeprocedimenti_var.movimentoTrasmissione.id.codice}">
					<c:set var="trasmisImageName" value="success.png" />
					<c:set var="title" >${istanzeprocedimenti_var.movimentoTrasmissione.movimento}&nbsp;[${istanzeprocedimenti_var.movimentoTrasmissione.tipomovimento.id.tipomovimento}]&#13;&#10;<fmt:message key="label.del" />&nbsp;<fmt:formatDate value="${istanzeprocedimenti_var.movimentoTrasmissione.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></c:set>
				</c:if>
				<img title="<fmt:message key="label.trasmissione" />:&#13;&#10;${title}"
					src="${pageContext.request.contextPath}/images/${trasmisImageName}" />
				</c:if>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="movimentoRitorno.data"
				titleKey="label.ritorno" sortable="false" filterable="false">
				<c:set var="ritImageName" value="error.png" />
				<c:set var="title" value="" />
				<c:if test="${(istanzeprocedimenti_var.istanzeprocedimenti.perprovvedimento eq true) 
									&& (istanzeprocedimenti_var.istanzeprocedimenti.perprovvedimento eq true && istanzeprocedimenti_var.istanzeprocedimenti.acquisito eq false ) }">
				<c:if test="${not empty istanzeprocedimenti_var.movimentoRitorno.id.codice }">
					
					<c:set var="title" >${istanzeprocedimenti_var.movimentoRitorno.movimento}&nbsp;[${istanzeprocedimenti_var.movimentoRitorno.tipomovimento.id.tipomovimento}]&#13;&#10;<fmt:message key="label.del" />&nbsp;<fmt:formatDate value="${istanzeprocedimenti_var.movimentoRitorno.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></c:set>
					<c:set var="ritImageName" value="frecciarossa.gif" />
					<c:if test="${istanzeprocedimenti_var.movimentoRitorno.esito eq true}">
						<c:set var="ritImageName" value="frecciaverde.gif" />
					</c:if>
					
				</c:if>
				<img title="<fmt:message key="label.ritorno" />:&#13;&#10;${title}"
					src="${pageContext.request.contextPath}/images/${ritImageName}" />
				</c:if>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade> <input type="hidden" value="${istanze.id.codice}" name="codiceIstanza" />
</form>
<!-- END --> <script type="text/javascript">
	var _jmesaUrl = 'riepilogo.htm?codiceIstanza=${istanze.id.codice}&';
	var _captionTab = '<fmt:message key="label.elenco_procedimenti" />';
</script></div>
<div id="functions">
<ul>
	<li><a href="javascript:historyBack();"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>