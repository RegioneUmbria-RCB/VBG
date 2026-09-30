<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${areedettagli.id.codice==null}">
			<fmt:message key="form.areedettagli.title.create" />
		</c:if> 
		<c:if test="${areedettagli.id.codice!=null}">
			<fmt:message key="form.areedettagli.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${areedettagli.id.codice==null}">
	<fmt:message key="form.areedettagli.title.create" />
</c:if> 
<c:if test="${areedettagli.id.codice!=null}">
	<fmt:message key="form.areedettagli.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>	

<c:if test="${not empty param.codiceArea}">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.area" />:</div>
		</div>
		<div class="parametro">
			<div>${areedettagli.aree.denominazione}</div>
		</div>
	</div>
</c:if>
<c:if test="${not empty param.codiceStradario}">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.stradario" />:</div>
		</div>
		<div class="parametro">
			<div>${areedettagli.stradario.prefisso} ${areedettagli.stradario.descrizione}</div>
		</div>
	</div>
</c:if>
<br />
<div id="subcontent">
<spring-form:form commandName="areedettagli" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">	
		<jsp:param name="commandName" value="areedettagli" />
	</jsp:include>
	<table>
		<c:if test="${empty param.codiceStradario}">
			<tr>
				<td><fmt:message key="label.stradario" /></td>
				<td>				
				<spring-form:input id="stradario_id" path="stradario.descrizioneCompleta" cssClass="searchbox" onchange="checkValue(this,'stradario_hidden')" size="50" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findStradario.htm?codiceComune=${areedettagli.aree.comune.codicecomune}&searchDisabilitati=false" idHidden="stradario_hidden" idInput="stradario_id" inputTitleKey="label.ricerca_stradario"/>						
				<spring-form:errors path="stradario" cssClass="error" /> 
				<spring-form:hidden	id="stradario_hidden" path="stradario.id.codice" /></td>
			</tr>
		</c:if>		
		<c:if test="${not empty param.codiceStradario}">
			<tr>
				<td><fmt:message key="label.stradario" /></td>
				<td><input id="stradario_id" name="_stradario_descrizione" class="searchbox" disabled="disabled" size="50" value="${areedettagli.stradario.prefisso} ${areedettagli.stradario.descrizione}" />				
				<spring-form:hidden	id="stradario_hidden" path="stradario.id.codice" /></td>
			</tr>
		</c:if>		
		<tr>
			<td><fmt:message key="form.areedettagli.paridispari" /></td>
			<td>
				<spring-form:select path="paridispari" >
					<spring-form:option value=""><fmt:message key="form.areedettagli.paridispari.tutti" /></spring-form:option>
					<spring-form:option value="true"><fmt:message key="form.areedettagli.paridispari.pari" /></spring-form:option>
					<spring-form:option value="false"><fmt:message key="form.areedettagli.paridispari.dispari" /></spring-form:option>					
				</spring-form:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.areedettagli.civicoDa" /></td>
			<td><spring-form:input id="civicoDa_id" path="civicoDa"	size="5" tabindex="0" /> <spring-form:errors path="civicoDa" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="form.areedettagli.civicoA" /></td>
			<td><spring-form:input id="civicoA_id" path="civicoA" size="5"	tabindex="0" /> <spring-form:errors path="civicoA" cssClass="error" /></td>
		</tr>
		<c:if test="${empty param.codiceArea}">
			<tr>
				<td><fmt:message key="form.areedettagli.aree" /></td>
				<td><spring-form:input id="aree_id" path="aree.denominazione"	cssClass="searchbox" onchange="checkValue(this,'aree_hidden')" size="50" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findAree.htm?codiceComune=${areedettagli.stradario.comune.codicecomune}" idHidden="aree_hidden" idInput="aree_id" inputTitleKey="label.ricerca_area"/>
				<spring-form:errors path="aree" cssClass="error" /> <spring-form:hidden	id="aree_hidden" path="aree.id.codice" /></td>
			</tr>
		</c:if>
		<c:if test="${not empty param.codiceArea}">
			<tr>
				<td><fmt:message key="form.areedettagli.aree" /></td>
				<td><input id="aree_id" name="_aree_denominazione" class="searchbox" disabled="disabled" value="${areedettagli.aree.denominazione}" size="50"/>
					<spring-form:hidden	id="aree_hidden" path="aree.id.codice" /></td>
			</tr>
		</c:if>
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${areedettagli.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>		
	</c:if>
	<c:if test="${areedettagli.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}','',document.inviodati)"><fmt:message	key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}','')"><fmt:message key="button.back" /></a></li></ul>
</div>
</body>
</html>