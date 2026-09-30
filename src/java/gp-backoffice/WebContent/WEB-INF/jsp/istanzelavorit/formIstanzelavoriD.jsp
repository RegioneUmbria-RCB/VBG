<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.causali_oneri_lavori" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.causali_oneri_lavori" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzelavorit/createIstanzelavoriD" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzelavorit.istanze.id.codice}</c:param>
	</c:import>
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="istanzelavorid" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzelavorid" />
		    </jsp:include>
			<table width="100%">
			<tr>
				<td ><fmt:message key="label.causale"/></td>			
				<td>					
					<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="tipicausalioneri" />
					<jsp:param name="propertyPath" value="tipicausalioneri" />										
					<jsp:param name="pathPropertyDescription" value="tipicausalioneri.coDescrizione" />
					<jsp:param name="pathPropertyCode" value="tipicausalioneri.id.codice" />
					<jsp:param name="autocompleterAjax" value="findTipicausalioneri.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_tipicausalioneri" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td ><fmt:message key="label.unita_misura"/></td>
				<td>						
					<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="tipiunitamisura" />
					<jsp:param name="propertyPath" value="tipiunitamisura" />										
					<jsp:param name="pathPropertyDescription" value="tipiunitamisura.umDescrbreve" />
					<jsp:param name="pathPropertyCode" value="tipiunitamisura.id.codice" />
					<jsp:param name="autocompleterAjax" value="findTipiunitamisura.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_unitamisura" />
					<jsp:param name="autocompleterInputSize" value="30" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.quantita"/></td>	
				<td><spring-form:input id="quantita_id" cssStyle="text-align: right;" size="8"  path="quantita" onchange="checkNumberValue(this)" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.costo_unitario"/></td>    
			    <td><spring-form:input id="costo_id" cssStyle="text-align: right;" size="8"  path="costoUnitarioUm" onchange="checkNumberValue(this)" /></td>
			</tr>
			</table>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertIstanzelavoriD.htm?codiceIstanzelavoriT=${istanzelavorit.id.codice}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanzelavorid.istanzelavoriT.istanze.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>