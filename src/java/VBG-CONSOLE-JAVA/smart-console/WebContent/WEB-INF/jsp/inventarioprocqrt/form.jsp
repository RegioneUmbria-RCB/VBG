<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
			<fmt:message key="label.inventarioprocqrt" />
		
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.inventarioprocqrt" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="inventarioprocQrt" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="inventarioprocqrt" />
    </jsp:include>
    
   
    
    
    <div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocQrt.inventarioprocedimento.procedimento}" /></div>
			</div>
			</div>
			<br class="clear" />
    
	<table>
	
		<tr>
			<td  width="20%"><fmt:message key="label.codice" /></td>
			<td><spring-form:input id="codice_id" path="codice" size="10" />
			<spring-form:errors path="codice" cssClass="error"/></td>
		</tr>
		<tr>
			<td  width="20%"><fmt:message key="label.titolo" /></td>
			<td><spring-form:input id="titolo_id" path="titolo" size="120" />
			<spring-form:errors path="titolo" cssClass="error"/></td>
		</tr>
		<tr>
			<td  width="20%"><fmt:message key="label.help" /></td>
			<td><spring-form:input id="help_id" path="help" size="60" />
			<spring-form:errors path="help" cssClass="error"/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.pubblica" />
			</td>
			<td>
				<spring-form:checkbox path="flagPubblica"/>
				<spring-form:errors path="flagPubblica" cssClass="error"/> 
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="label.documento" /></td>
			<td><jsp:include page="../includes/oggetti.jsp">
				<jsp:param name="idElemento" value="oggettoIdCodice" />
				<jsp:param name="codiceOggetto" value="${inventarioprocQrt.oggetti.id.codice}" />
				<jsp:param name="idComuneOggetto" value="${inventarioprocQrt.oggetti.id.idcomune}" />
				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
				<jsp:param name="nomefileId" value="oggetto_nomefile" />
				<jsp:param name="overrideExtensionsAllowed" value="xml" />
			</jsp:include> 
			<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice" />
			<spring-form:hidden path="oggetti.nomefile"	id="oggetto_nomefile" />
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="label.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="3" maxlength="3" cssStyle="text-align: right;" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
	</table>

	<script type='text/javascript'>
		$('titolo_id').focus();


		
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${inventarioprocQrt.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${inventarioprocQrt.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm?inventarioproc.id.codice=${inventarioprocQrt.inventarioprocedimento.id.codice }','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?inventarioproc.id.codice=${inventarioprocQrt.inventarioprocedimento.id.codice }','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
