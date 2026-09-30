<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${inventarioprocedimenti.documenti.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_documento.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.documenti.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_documento.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${inventarioprocedimenti.documenti.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_documento.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.documenti.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_documento.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
			<table>
				
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.documento" />
					</td>
					<td>
						<spring-form:input id="documento_id" path="documenti.documento" size="50"/>
						<spring-form:errors path="documenti.documento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.indirizzoweb" />
					</td>
					<td>
						<spring-form:input id="indirizzoweb_id" path="documenti.indirizzoweb" size="50"/>
						<spring-form:errors path="documenti.indirizzoweb" cssClass="error"/>
					</td>
				</tr>
				
				<tr>
						<td>
							<fmt:message key="label.documento" />
						</td>
						<td><jsp:include page="../includes/oggetti.jsp">
							<jsp:param name="idElemento" value="oggettoIdCodice" />
							<jsp:param name="codiceOggetto" value="${inventarioprocedimenti.documenti.oggetti.id.codice}" />
							<jsp:param name="idComuneOggetto" value="${inventarioprocedimenti.documenti.oggetti.id.idcomune}" />
							<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
							<jsp:param name="nomefileId" value="oggetto_nomefile" />
							<jsp:param name="overrideExtensionsAllowed" value="pdf|rtf|doc|odt"/>
							</jsp:include> 
							<spring-form:hidden path="documenti.oggetti.id.codice" id="oggetto_id_codice" />
							<spring-form:hidden path="documenti.oggetti.nomefile" id="oggetto_nomefile" />
						</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.documenti.id.codice==null}">
				<li><a href="javascript:doSubmit('insertDocumento.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.documenti.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateDocumento.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doHref('../inventarioprocsoggfirmatari/list.htm?codicedocumento=${inventarioprocedimenti.documenti.id.codice}','')"><fmt:message key="button.soggetti" /></a></li>				
				<li><a href="javascript:doSubmit('deleteDocumento.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listdocumenti.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>