<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
			<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
		</c:if> 
		<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
		<c:if test="${error ne '03'}">
			<fmt:message key="statiistanze.label.dettaglio_statiistanze.title" />
			</c:if>
			<c:if test="${error eq '03'}">
			<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
			</c:if>
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${statiistanza.id.codicestato =='' || statiistanza.id.codicestato==null}">
	<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
</c:if> 
<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
	<c:if test="${error ne '03'}">
	<fmt:message key="statiistanze.label.dettaglio_statiistanze.title" />
	</c:if>
	<c:if test="${error eq '03'}">
	<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
	</c:if>
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="statiistanza" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="statiistanza" />
    </jsp:include>
	<table>
		<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
			<c:if test="${error ne '03'}">
			<tr>
				<td><fmt:message key="statiistanze.label.codicestato" /></td>
				<td><spring-form:input id="codicestato_id" path="id.codicestato" size="2" disabled="true" maxlength="2"/>
				<spring-form:errors path="id.codicestato" cssClass="error"/></td>
			</tr>
			</c:if>
			<c:if test="${error eq '03'}">
			<tr>
				<td><fmt:message key="statiistanze.label.codicestato" /></td>
				<td><spring-form:input id="codicestato_id" path="id.codicestato" size="2" maxlength="2"/>
				<spring-form:errors path="id.codicestato" cssClass="error"/></td>
			</tr>
			</c:if>
		</c:if>
		<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
			<tr>
				<td><fmt:message key="statiistanze.label.codicestato" /></td>
				<td><spring-form:input id="codicestato_id" path="id.codicestato" size="2" />
				<spring-form:errors path="id.codicestato" cssClass="error"/></td>
			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="statiistanze.label.stato" /></td>
			<td><spring-form:input id="stato_id" path="stato" size="70" />
			<spring-form:errors path="stato" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="statiistanze.label.modificaistanza" /></td>
			<td><spring-form:checkbox path="modificaistanza" />
			<fmt:message key="statiistanze.label.modificaistanza.help" />
			<spring-form:errors path="modificaistanza" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="statiistanze.label.staticomportamento" /></td>
			<td><spring-form:select path="staticomportamento.codcomportamento" itemLabel="comportamento" itemValue="codcomportamento" items="${staticomportamenti}"></spring-form:select>
			<spring-form:errors path="staticomportamento.codcomportamento" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="statiistanze.label.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="2" maxlength="2" onchange="changeValue(this)"/>
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
	</table> 
	<script type='text/javascript'>
		function changeValue(obj){
			var importo=obj.value;
			if(isNaN(importo)){
				alert('<fmt:message key="alert.field.numeric" />');
				obj.value = '';
				return;
			}
		}
	</script>
	<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
	<script type='text/javascript'>
		$('codicestato_id').focus();
	</script>	
	</c:if>
	<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
	<c:if test="${error ne '03'}">
	<script type='text/javascript'>
		$('stato_id').focus();
	</script>	
	</c:if>
	<c:if test="${error eq '03'}">
	<script type='text/javascript'>
		$('codicestato_id').focus();
	</script>
	</c:if>
	</c:if>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${statiistanza.id.codicestato!='' &&  statiistanza.id.codicestato!=null}">
	<c:if test="${error ne '03'}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<c:if test="${error eq '03'}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
