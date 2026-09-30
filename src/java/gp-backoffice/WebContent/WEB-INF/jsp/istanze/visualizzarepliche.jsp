<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_istanze_replicate" /></title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.lista_istanze_replicate" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/visualizzaRepliche" />
    	<jsp:param name="qs" value="codiceIstanza%3D${param.codiceIstanza}" />
	</jsp:include>

	<div id="subcontent">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${param.codiceIstanza}</c:param>
		</c:import>
 		<br class="clear" />
 	
 	
 	<fieldset>
		<legend><fmt:message key="label.istanza_di_origine" /></legend>
 		<div class="jmesa" >
			<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td><fmt:message key="label.codice_istanza" /></td>
						<td><fmt:message key="label.data" /></td>
						<td><fmt:message key="label.numero_protocollo" /></td>
						<td><fmt:message key="label.data_protocollo" /></td>
						<td><fmt:message key="label.richiedente" /></td>
						<td><fmt:message key="label.richiedente_storico" /></td>
						<td><fmt:message key="label.intervento" /></td>
					</tr>
				</thead>
				<tbody class="tbody" >
				<tr class="even">
					<td>
						<a href="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${istanzaPadre.id.codice}&software=${istanzaPadre.software.codice}','')">${istanzaPadre.numeroistanza}</a>
					</td>
					<td><fmt:formatDate value="${istanzaPadre.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${istanzaPadre.numeroprotocollo}</td>
					<td><fmt:formatDate value="${istanzaPadre.dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${istanzaPadre.transientRichiedenteQualitaAzienda}</td>
					<td>${istanzaPadre.transientRichiedenteQualitaAziendaStorico}</td>
					<td>${istanzaPadre.alberoproc.vwAlberoproc.scDescrizione }</td>
				</tr>
				</tbody>
			</table>
		</div>
 	</fieldset>
 	<br class="clear"/>
 		<!-- TABELLA DELLE ISTANZE REPLICATE -->
 		<!-- START -->
				<fieldset>
		 		<legend> <fmt:message key="label.istanze_replicate" /></legend>
		 		<div class="jmesa" >
				<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td><fmt:message key="label.codice_istanza" /></td>
							<td><fmt:message key="label.data" /></td>
							<td><fmt:message key="label.numero_protocollo" /></td>
							<td><fmt:message key="label.data_protocollo" /></td>
							<td><fmt:message key="label.richiedente" /></td>
							<td><fmt:message key="label.richiedente_storico" /></td>
							<td><fmt:message key="label.intervento" /></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<c:forEach items="${istanzeFiglies}" var="istanza_var">
					<tr class="even">
						<td>
							<a href="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${istanza_var.id.codice}&software=${istanza_var.software.codice}','')">${istanza_var.numeroistanza}</a>
						</td>
						<td><fmt:formatDate value="${istanza_var.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td>${istanza_var.numeroprotocollo}</td>
						<td><fmt:formatDate value="${istanza_var.dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td>${istanza_var.transientRichiedenteQualitaAzienda}</td>
						<td>${istanza_var.transientRichiedenteQualitaAziendaStorico}</td>
						<td>${istanza_var.alberoproc.vwAlberoproc.scDescrizione }</td>
					</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		 	</fieldset>		 			  
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	</body>
</html>