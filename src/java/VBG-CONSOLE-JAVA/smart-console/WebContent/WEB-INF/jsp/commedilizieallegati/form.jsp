<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizieallegati.id.codice==null}">
			<fmt:message key="label.nuovo_documento_commissione" />
		</c:if> 
		<c:if test="${commedilizieallegati.id.codice!=null}">
			<fmt:message key="label.modifica_documento_commissione" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizieallegati.id.codice==null}">
			<fmt:message key="label.nuovo_documento_commissione" />
		</c:if> 
		<c:if test="${commedilizieallegati.id.codice!=null}">
			<fmt:message key="label.modifica_documento_commissione" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.numero_commissione" />:</div>
			<div><fmt:message key="label.descrizione" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${commedilizieconvocazioni.commissioniedilizieT.numprotocollo}</div>
			<div>${commedilizieconvocazioni.commissioniedilizieT.descrizione}</div>
		</div>
	</div>
		<br class="clear" />
		<spring-form:form commandName="commedilizieallegati" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizieallegati" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="note" cols="40" readonly="4" />
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.doc_atto" /></td>
					<td><jsp:include page="../includes/oggetti.jsp">
					<jsp:param name="idElemento" value="oggettoIdCodice" />
					<jsp:param name="codiceOggetto" value="${commedilizieallegati.oggetti.id.codice}" />
					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
					<jsp:param name="nomefileId" value="oggetto_nomefile" />					
					</jsp:include>
					<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice" />
					<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile" />
					<spring-form:errors path="oggetti" cssClass="error"/>
					</td>
				</tr>
				
			</table>
			<script type='text/javascript'>
				$('descrizione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${commedilizieallegati.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commedilizieallegati.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceCommissione=${commedilizieallegati.commissioniedilizieT.id.codice }','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>