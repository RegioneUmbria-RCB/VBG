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
				<fmt:message key="letteretipo.label.dettaglio_lettera.title" />:
			</div>
		</div>
		<div class="parametro">
			<div>
				<c:out value="${letteretipo.descrizione}" />
			</div>
		</div>
	</div>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../letteretipo/listaDipendenze" />
		<jsp:param name="qs" value="codice%3D${letteretipo.id.codice}%26software%3D${letteretipo.software.codice }" />	
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="letteretipo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="letteretipo" />
			</jsp:include>
			<br class="clear"/>
			<%-- 	model.addAttribute("tipidocumentos", docs); --%>
			<c:if test="${not empty tipidocumentos}">
				<fieldset>
					<legend><fmt:message key="tipidocumento.label.dettaglio_tipidocumento.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">						
						<td><fmt:message key="label.descrizione" /></td>
					</tr>
					<c:forEach items="${tipidocumentos}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipidocumento/view.htm?codice=${el_var.id.codice }','')">${el_var.documento} (${el_var.id.codice})</a>
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			<%--	model.addAttribute("tipiprocedures", procs); --%>
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
			<%--	model.addAttribute("tipimovimentodoctipos", tdocs); --%>
			<c:if test="${not empty tipimovimentodoctipos}">
				<fieldset>
					<legend><fmt:message key="tipimovimento.label.lista_tipimovimentodoctipo.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovimentodoctipos}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipimovimento/viewTipoDocumenti.htm?codiceMovimento=${el_var.id.tipomovimento }&codiceLettera=${el_var.id.codicelettera}&software=${el_var.tipomovimento.software.codice}','')">${el_var.tipomovimento.descrizioneEstesa} </a>
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
			
			<%--	model.addAttribute("tipimovimentos", tmovs); --%>
			<c:if test="${not empty tipimovimentos}">			
				<fieldset>
					<legend><fmt:message key="tipimovimento.label.lista_movimento.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="tipimovimento.label.dettaglio_tipimovimento.title" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovimentos}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipimovimento/view.htm?codice=${el_var.id.tipomovimento }&software=${el_var.software.codice}','')">${el_var.descrizioneEstesa} </a>
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
			<li><a
				href="javascript:doHref('view.htm?codice=${param.codice}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>

</body>
</html>
