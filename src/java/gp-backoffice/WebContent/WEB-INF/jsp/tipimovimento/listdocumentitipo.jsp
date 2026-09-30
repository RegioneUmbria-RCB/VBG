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
	<span class="titoloPagina">
		<fmt:message key="tipimovimento.label.lista_tipimovimentodoctipo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipimovimento/listdocumentitipo" />
		<jsp:param name="qs" value="tipimovimento.codice%3D${tipomovimentoinfo.id.tipomovimento}%26software%3D${tipomovimentoinfo.software.codice }" />			
	</jsp:include>
	
	<div id="subcontent">
		<form name="tipimovimentodoctipoForm" action="listdocumentitipo.htm">
			<div class="vbg-form">
				<fieldset>
					<legend>
						<fmt:message key="label.dettaglio" />
					</legend>
					<div class="parametriDiv">
						<div class="etichetta">
							<div><fmt:message key="tipimovimento.label.movimento" />:</div>
				    	</div>
				    	<div class="parametro">
				    	  <div><c:out value="${tipomovimentoinfo.id.tipomovimento}" /> - <c:out value="${tipomovimentoinfo.movimento}" /></div>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend>
						<fmt:message key="tipimovimento.label.lista_tipimovimentodoctipo.title" />
					</legend>		
					<table class="vbg-table" id="lista_tipimovimentodoctipo">
						<thead>
							<th><fmt:message key="label.documento_tipo" /></th>
							<th><fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.flg_generaaut" /></th>
							<th><fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.fase_esecuzione" /></th>
							<th><fmt:message key="label.edit.record" /></th>
						</thead>
						<tbody>
							<c:forEach items="${tipimovimentodoctipoList}" var="docTipo">
								<tr>
									<td>
										<a href="javascript:historySet('${_urlback }','../tipimovimento/viewTipoDocumenti.htm?codiceMovimento=${docTipo.id.tipomovimento}&codiceLettera=${docTipo.id.codicelettera}&software=${docTipo.tipomovimento.software.codice}','')">
											${docTipo.letteretipo.descrizione}
										</a>
									</td>
									<td>
										<c:if test="${docTipo.flgGeneraAut}">
											<fmt:message key="label.si" />
										</c:if>
										<c:if test="${not docTipo.flgGeneraAut}">
											<fmt:message key="label.no" />
										</c:if>
									</td>
									<td>
										${docTipo.faseEsecuzioneDescr}
									</td>
									<td>
										<a class="elimina" href="javascript:doHref('deleteDocumentoTipoFromList.htm?codicedoctipo=${docTipo.id.codicelettera}&codicemovimento=${docTipo.id.tipomovimento}','<fmt:message key="javascript.confirm.delete" />')" >
											<i class="fa fa-times" aria-hidden="true"></i> <fmt:message key="label.elimina" />
										</a>
									</td>
								</tr>				
							</c:forEach>
						</tbody>		
					</table>
				</fieldset>	
			</div>
			<input type="hidden" value="${tipomovimentoinfo.id.tipomovimento}" name="tipimovimento.codice" />
			<div>
				<a class="btn btn-primary" href="javascript:doHref('createTipiDocumento.htm?codicetipomovimento=${tipomovimentoinfo.id.tipomovimento}','');"><fmt:message key="button.new" /></a>
				<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
			</div>
		</form>
	</div>
</body>
</html>