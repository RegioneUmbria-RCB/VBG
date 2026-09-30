<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.consorzi" />
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.consorzi" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${mercati.descrizione}" /></div>
			</div>
		</div>
	<br class="clear" />
	<div>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="mercaticonsorzi" />
	</jsp:include>
  	</div>
	<spring-form:form commandName="mercaticonsorzi" name="inviodati">
	<table>
		<tr>
			<td><fmt:message key="label.consorzio" />
			</td>
			<td>
				<jsp:include page="../includes/anagraficasearch.jsp">
					<jsp:param name="idElemento" value="consorzioIdCodice" />
					<jsp:param name="pathAnagrafica" value="consorzio" />
					<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=G" />
					<jsp:param name="tiposoggetto"  value="G"/>
				</jsp:include>				
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.data_inizio_esercizio" /></td>
			<td>
				<spring-form:input id="dataInizioEsercizio_id" path="dataInizioEsercizio" size="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="caldataInizioEsercizio" idInput="dataInizioEsercizio_id" textKey="label.calendar" /> 			  	
				<spring-form:errors path="dataInizioEsercizio" cssClass="error" delimiter="," />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.data_fine_esercizio" /></td>
			<td>
				<spring-form:input id="dataFineEsercizio_id" path="dataFineEsercizio" size="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="caldataFineEsercizio" idInput="dataFineEsercizio_id" textKey="label.calendar" /> 			  	
				<spring-form:errors path="dataFineEsercizio" cssClass="error" delimiter="," />
			</td>
		</tr>
	</table>

	</spring-form:form>
	</div>
	<div id="functions">
	<ul>
		<c:if test="${mercaticonsorzi.id.codice==null}">
			<li><a
				href="javascript:doSubmit('insertConsorzio.htm','',document.inviodati)"><fmt:message
				key="button.insert" /></a></li>
		</c:if>
		<c:if test="${mercaticonsorzi.id.codice!=null}">
			<li><a
				href="javascript:doSubmit('updateConsorzio.htm?codicemercato=${mercati.id.codice}&codice=${mercaticonsorzi.id.codice}','',document.inviodati)"><fmt:message
				key="button.update" /></a></li>			
		</c:if>
		<li><a
			href="javascript:doHref('view.htm?codice=${mercati.id.codice}','')"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>