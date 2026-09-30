<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${alberoprocdocumenticat.id.codice==null}">
			<fmt:message key="form.alberoprocdocumenticat.title.create" />
		</c:if> 
		<c:if test="${alberoprocdocumenticat.id.codice!=null}">
			<fmt:message key="form.alberoprocdocumenticat.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${alberoprocdocumenticat.id.codice==null}">
	<fmt:message key="form.alberoprocdocumenticat.title.create" />
</c:if> 
<c:if test="${alberoprocdocumenticat.id.codice!=null}">
	<fmt:message key="form.alberoprocdocumenticat.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="alberoprocdocumenticat" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="alberoprocdocumenticat" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.alberoprocdocumenticat.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.alberoprocdocumenticat.oggetto" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${alberoprocdocumenticat.oggetto.id.codice}" />
	   				<jsp:param name="idComuneOggetto" value="${alberoprocdocumenticat.oggetto.id.idcomune}" />
	   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
	   				<jsp:param name="nomefileId" value="oggetto_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice"/>
    			<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile"/>
    			<spring-form:errors path="oggetto" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.alberoprocdocumenticat.foRichiedefirma" /></td>
			<td><spring-form:checkbox id="foRichiedefirma_id" path="foRichiedefirma" />
			<spring-form:errors path="foRichiedefirma" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.alberoprocdocumenticat.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="3" maxlength="3" cssStyle="text-align: right;" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
	</table>

	<script type='text/javascript'>
		$('descrizione_id').focus();


		
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${alberoprocdocumenticat.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${alberoprocdocumenticat.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
