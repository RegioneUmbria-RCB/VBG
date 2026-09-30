<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><fmt:message key="label.record_disabilitato_lista_delle_dipendenze" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.record_disabilitato_lista_delle_dipendenze" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<div class="parametriDiv">
		<div class="etichetta">
			<div>
				<fmt:message key="tipimovimento.label.movimento"/>:
			</div>
		</div>
		<div class="parametro">
			<div>
				${tipimovimento.descrizioneEstesa}
			</div>
		</div>
	</div>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipimovimento/listaDipendenze" />
		<jsp:param name="qs" value="codice%3D${tipimovimento.id.tipomovimento}%26software%3D${tipimovimento.software.codice}" />	
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="tipimovimento" />
			</jsp:include>
			<br class="clear"/>
			
			
			<%-- model.addAttribute"inventarioprocedimentis",inventarioprocedimentis); --%>
			<c:if test="${not empty inventarioprocedimentis}">
				<fieldset>
					<legend><fmt:message key="label.endoprocedimenti" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.endoprocedimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${inventarioprocedimentis}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${el_var.id.codice}&software=${el_var.software.codice}','')">${el_var.procedimento} </a>
							</td>
							<td>
								${el_var.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			<%-- model.addAttribute("inventarioprocedimentisoftwares", inventarioprocedimentisoftwares); --%>
			<c:if test="${not empty inventarioprocedimentisoftwares}">
				<fieldset>
					<legend><fmt:message key="label.endoprocedimenti" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.endoprocedimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${inventarioprocedimentisoftwares}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/listmodalita.htm?codiceendo=${el_var.inventarioprocedimento.id.codice}&software=TT','')">${el_var.inventarioprocedimento.procedimento} </a>
							</td>
							<td>
								${el_var.inventarioprocedimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			<%-- model.addAttribute("protocolloRegistris", protocolloRegistris); --%>
			<c:if test="${not empty protocolloRegistris}">
				<fieldset>
					<legend><fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.codice" /></td>
					</tr>
					<c:forEach items="${protocolloRegistris}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipologiaregistri/createProtocolloRegistri.htm?codice=${el_var.id.codice}','')">${el_var.id.codice} </a>
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			

			<%-- model.addAttribute("tipiprocedures", tipiprocedures); --%>
			<c:if test="${not empty tipiprocedures}">
				<fieldset>
					<legend><fmt:message key="tipiprocedure.label.dettaglio_tipiprocedure.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">						
						<td width="60%"><fmt:message key="label.descrizione" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipiprocedures}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipiprocedure/view.htm?codice=${el_var.id.codice }&software=${el_var.software.codice}','')">${el_var.procedura} (${el_var.id.codice})</a>
							</td>
							<td>
								${el_var.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			<%-- model.addAttribute("tipiprocedureavvios", tipiprocedureavvios); --%>
			<c:if test="${not empty tipiprocedureavvios}">
				<fieldset>
					<legend><fmt:message key="tipiprocedure.label.dettaglio_tipiprocedure.title" /> - <fmt:message key="label.movimenti_avvio"/></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">						
						<td width="60%"><fmt:message key="label.descrizione" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipiprocedureavvios}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipiprocedure/view.htm?codice=${el_var.tipoProcedura.id.codice }&software=${el_var.tipoProcedura.software.codice}','')">${el_var.tipoProcedura.procedura} (${el_var.tipoProcedura.id.codice})</a>
							</td>
							<td>
								${el_var.tipoProcedura.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			<%-- model.addAttribute"commedilizieTipologiedetts",commedilizieTipologiedetts); --%>
			<c:if test="${not empty commedilizieTipologiedetts}">
				<fieldset>
					<legend><fmt:message key="commedilizietipologie.label.dettaglio_commedilizietipologie.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.descrizione" /></td>
					</tr>
					<c:forEach items="${commedilizieTipologiedetts}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../commedilizietipologie/view.htm?codice=${el_var.id.codcommtipologia}','')">${el_var.commedilizieTipologie.descrizione} </a>
							</td>

						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			<%-- model.addAttribute("commedilizieTipopareris", commedilizieTipopareris); --%>
			<c:if test="${not empty commedilizieTipopareris}">
				<fieldset>
					<legend><fmt:message key="commedilizietipopareri.label.dettaglio_commedilizietipopareri.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.descrizione" /></td>
					</tr>
					<c:forEach items="${commedilizieTipopareris}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../commedilizietipopareri/view.htm?codice=${el_var.id.codice}','')">${el_var.descrizione} </a>
							</td>

						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
		
			<%-- model.addAttribute("tipicontromovimentomovs", tipicontromovimentomovs); --%>
			<c:if test="${not empty tipicontromovimentomovs}">
				<fieldset>
					<legend><fmt:message key="tipimovimento.label.contro_movimenti_associati.tilte" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipicontromovimentomovs}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipimovimento/view.htm?codice=${el_var.tipocontromovimento.id.tipomovimento}&software=${el_var.tipocontromovimento.software.codice}','')">${el_var.tipocontromovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipocontromovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			<%-- model.addAttribute("tipicontromovimentocontros", tipicontromovimentocontros); --%>
			<c:if test="${not empty tipicontromovimentocontros}">
				<fieldset>
					<legend><fmt:message key="tipimovimento.label.movimenti_contro_movimenti.tilte" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipicontromovimentocontros}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipimovimento/view.htm?codice=${el_var.tipomovimento.id.tipomovimento}&software=${el_var.tipomovimento.software.codice}','')">${el_var.tipomovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipomovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>

			<%-- model.addAttribute("oneritipirateizzaziones", oneritipirateizzaziones); --%>			
			<c:if test="${not empty oneritipirateizzaziones}">
				<fieldset>
					<legend><fmt:message key="label.tipirateizzazione" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.descrizione" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<%pageContext.setAttribute("URL_ONERITIPIRATEIZZAZIONI", BackofficeNETConstants.getURL_ONERITIPIRATEIZZAZIONI());%>
					<c:forEach items="${oneritipirateizzaziones}" var="el_var">
						<c:set var="_URL_ONERIRATEIZZAZIONI" value="${URL_ONERITIPIRATEIZZAZIONI}?CodiceIstanza=${movimentiCommand.entity.istanza.id.codice}"/>								
						<c:set var="_URL_ONERIRATEIZZAZIONI" value="${inite:geturlto(pageContext.request, _URL_ONERIRATEIZZAZIONI,_urlback, el_var.software.codice, false)}" />
						<tr>
							<td>
								<a href="${_URL_ONERIRATEIZZAZIONI}">${el_var.descrizione} </a>
							</td>
							<td>
								${el_var.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
		</spring-form:form>
	</div>
	<br class="clear"/>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('view.htm?codice=${param.codice}','')"><fmt:message	key="button.back" /></a></li>
		</ul>
	</div>

</body>
</html>
