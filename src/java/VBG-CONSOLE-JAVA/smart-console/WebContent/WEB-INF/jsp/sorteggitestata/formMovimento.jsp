<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="sorteggitestata.label.inserisci_movimento.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="sorteggitestata.label.inserisci_movimento.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="movimento" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="movimento" />
    </jsp:include>       
	<table>
		<tr>
			<td>
				<fmt:message key="label.data" />
			</td>
			<td>
				<c:if test="${!view}">				
					<spring-form:input tabindex="1" id="data_id" path="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			   		<init:calendar idImage="caldata" idInput="data_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
			   		<spring-form:errors	path="data" cssClass="error" />				
				</c:if>			
				<c:if test="${view}">
					<spring-form:input tabindex="1" id="data_id" path="data" size="10" maxlength="10" disabled="true"/>
				</c:if>
			</td>
		</tr>	
		<tr>
			<td>
				<fmt:message key="label.tipomovimento" />
			</td>			
			<td colspan="3">	
				<c:if test="${!view}">						
				<jsp:include page="../includes/tipimovimentosearch.jsp">
					<jsp:param name="idElemento" value="tipoMovimentoInputId" />
					<jsp:param name="pathTipomovimento"	value="tipomovimento" />
				</jsp:include>
				</c:if>
				<c:if test="${view}">
					<spring-form:input tabindex="2" id="tipomovimento_id" path="tipomovimento.descrizioneEstesa" size="67%" disabled="true"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.amministrazione" />
			</td>			
			<td colspan="3">
				<c:if test="${!view}">
			    <jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="amministrazioni" />					
					<jsp:param name="propertyPath" value="amministrazioni" />	
					<jsp:param name="pathPropertyDescription" value="amministrazioni.amministrazione" />
					<jsp:param name="pathPropertyCode" value="amministrazioni.id.codice" />
					<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />
					<jsp:param name="titleKey" value="label.ricerca_amministrazione" />							
				</jsp:include>	
				</c:if>
				<c:if test="${view}">
					<spring-form:input tabindex="2" id="amministrazioni_id" path="amministrazioni.amministrazione" size="67%" disabled="true"/>
				</c:if>		    
			</td>		
		</tr>
	</table>
	</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${!view}">
		<li><a href="javascript:doSubmit('saveMovimento.htm?codice=${sorteggitestata.id.codice}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('view.htm?codice=${sorteggitestata.id.codice}&sorteggidettaglio_id_f_sorteggiata=Si','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
