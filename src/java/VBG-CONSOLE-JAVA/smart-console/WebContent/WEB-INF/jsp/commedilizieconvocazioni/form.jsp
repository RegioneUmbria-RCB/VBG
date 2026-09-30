<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizieconvocazioni.id.codice==null}">
			<fmt:message key="label.nuova_convocazione" />
		</c:if> 
		<c:if test="${commedilizieconvocazioni.id.codice!=null}">
			<fmt:message key="label.modifica_convocazione" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizieconvocazioni.id.codice==null}">
			<fmt:message key="label.nuova_convocazione" />
		</c:if> 
		<c:if test="${commedilizieconvocazioni.id.codice!=null}">
			<fmt:message key="label.modifica_convocazione" />
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
		<spring-form:form commandName="commedilizieconvocazioni" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizieconvocazioni" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.data_convocazione" />
					</td>
					<td>
						<spring-form:input id="dataconvocazione_id" path="dataconvocazione" size="8" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldataconvocazione" idInput="dataconvocazione_id" textKey="label.calendar"/>
						<spring-form:errors path="dataconvocazione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.ora_convocazione" />
					</td>
				<td>
					<spring-form:input path="oraconvocazione" size="8" maxlength="5" onblur="isValidOra(this,true);" />
					<spring-form:errors path="oraconvocazione" cssClass="error"/>
				</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('dataconvocazione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${commedilizieconvocazioni.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commedilizieconvocazioni.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../commissioniediliziet/view.htm?codice=${commedilizieconvocazioni.commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>