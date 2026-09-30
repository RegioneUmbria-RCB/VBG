
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipiresponsabili.id.codice==null}">
			<fmt:message key="tipiresponsabili.label.nuovo_tipiresponsabili.title" />
		</c:if> 
		<c:if test="${tipiresponsabili.id.codice!=null}">
			<fmt:message key="tipiresponsabili.label.dettaglio_tipiresponsabili.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipiresponsabili.id.codice==null}">
	<fmt:message key="tipiresponsabili.label.nuovo_tipiresponsabili.title" />
</c:if> 
<c:if test="${tipiresponsabili.id.codice!=null}">
	<fmt:message key="tipiresponsabili.label.dettaglio_tipiresponsabili.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipiresponsabili" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipiresponsabili" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="trDescrizione_id" path="trDescrizione" size="70" />
			<spring-form:errors path="trDescrizione" cssClass="error"/></td>
		</tr>
        <tr  valign="middle" >
			<td><fmt:message key="label.responsabile_procedimento" /></td>
			<td><spring-form:checkbox path="trFlagresponsabile" value="1"  /><init:help idHelp="trFlagresponsabile_id" textKey="tipiresponsabili.help.trFlagresponsabile"/> </td>
	    </tr>
		<tr>
			<td><fmt:message key="label.responsabile_istruttoria" /></td>
			<td valign="middle"><spring-form:checkbox path="trFlagistruttore" value="1"  /><init:help idHelp="trFlagistruttore_id" textKey="tipiresponsabili.help.trFlagistruttore"/> </td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('trDescrizione_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipiresponsabili.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipiresponsabili.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
       <li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
    </ul>
</div>
</body>
</html>
