<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${ccicalcolotot.id.codice==null}">
			<fmt:message key="label.nuovo_ccicalcolotot.title" />
		</c:if> 
		<c:if test="${ccicalcolotot.id.codice!=null}">
			<fmt:message key="label.dettaglio_ccicalcolotot.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${ccicalcolotot.id.codice==null}">
			<fmt:message key="label.nuovo_ccicalcolotot.title" />
		</c:if> 
		<c:if test="${ccicalcolotot.id.codice!=null}">
			<fmt:message key="label.dettaglio_ccicalcolotot.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../ccicalcolotot/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="ccicalcolotot" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="ccicalcolotot" />
		    </jsp:include>
		    
		    <c:import url="/ajax/dettaglioIstanza.htm">
					<c:param name="codIstanza">${istanze.id.codice}</c:param>
			</c:import>
			<br clear="clear" />
			
			<table>
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${ccicalcolotot.id.codice==null}">
					<tr>
						<td>
							<fmt:message key="label.data" />
						</td>
						<td>
							<spring-form:input  id="data_id" path="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
							<init:calendar imagePath="/images/cal.gif" idImage="calDataIcalcolotot" idInput="data_id" textKey="label.calendar"/>
							<spring-form:errors path="data" cssClass="error"/>  
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="label.listino_coefficienti" />
						</td>
						<td>
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="ccValiditacoefficienti" />		
								<jsp:param name="propertyPath" value="ccValiditacoefficienti" />				
								<jsp:param name="pathPropertyDescription" value="ccValiditacoefficienti.descrizione" />
								<jsp:param name="pathPropertyCode" value="ccValiditacoefficienti.id.codice" />
								<jsp:param name="autocompleterAjax" value="findCcValiditaCoefficienti.htm" />	
								<jsp:param name="titleKey" value="label.ricerca_listino_coefficienti" />
								<jsp:param value="autocompleterInputSize" name="55"/>
							</jsp:include>	
						</td>	
					</tr>
				</c:if>
				<c:if test="${ccicalcolotot.id.codice!=null}">
					<tr>
						<td><fmt:message key="label.data" /></td>
						<td>	
							<spring-form:input  id="data_read_id" path="data" size="10" maxlength="10" disabled="true"/>
						</td>
					</tr>	 
				    <tr>
						<td><fmt:message key="label.listino_coefficienti" /></td>
						<td>
							<b>${ccicalcolotot.ccValiditacoefficienti.descrizione}</b>							
						</td>	
					</tr>
					<tr>
						<td><fmt:message key="label.totali_contributo" /></td>
						<td>
							<b><fmt:formatNumber minFractionDigits="2">${ccicalcolotot.quotacontribTotale}</fmt:formatNumber> &euro;</b>
						</td>	
					</tr>
				</c:if>
				
				
			</table>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${ccicalcolotot.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${ccicalcolotot.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:historySet('${_urlback }','../ccicalcoli/calcoloCostoCostruzione.htm?codiceI=${ccicalcolotot.id.codice}','')"><fmt:message key="button.dettaglio_calcolo" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanze.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>