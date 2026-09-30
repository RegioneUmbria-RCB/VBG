<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${typeform eq 'new'}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimentooneri.title" />
		</c:if> 
		<c:if test="${typeform eq 'view'}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimentooneri.title" />
		</c:if>
	</title>
</head>
<body>

	<span class="titoloPagina">
		<c:if test="${typeform eq'new'}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimentooneri.title" />
		</c:if> 
		<c:if test="${typeform eq'view'}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimentooneri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="tipimovimento.label.codice" />:</div>
				<div><fmt:message key="tipimovimento.label.movimento" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${tipimovimento.id.tipomovimento}" /></div>
	    	  <div><c:out value="${tipimovimento.movimento}" /></div>
			</div>
	</div>
	<br class="clear"/>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimentooneri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipimovimentooneri" />
		    </jsp:include>
			<table>
			   <tr>
					<td>
						<fmt:message key="tipimovimento.label.causale_onere" />
					</td>
					<td>
						<spring-form:input id="tipicausalioneri_id" path="tipicausalioneri.coDescrizione" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'tipicausalioneri_hidden')" size="40"/>
						<init:autocompleter methodAjax="findTipicausalioneri.htm" idHidden="tipicausalioneri_hidden" idInput="tipicausalioneri_id" inputTitleKey="label.ricerca_causale_onere"></init:autocompleter>
						<spring-form:errors path="tipicausalioneri" cssClass="error"/> 
						<spring-form:hidden id="tipicausalioneri_hidden" path="tipicausalioneri.id.codice"  />
					</td>
				</tr>
			    <tr>
					<td>
						<fmt:message key="tipimovimento.label.comportamento" />
					</td>
					<td>
						<spring-form:select id="selectComportamento" items="${listComportamenti}" itemLabel="comportamento" itemValue="codicecomportamento"  path="onericomportamento.codicecomportamento" onchange="comportamento(id);"></spring-form:select>
					</td>
				</tr>
				<tr id="sezione_scadenza_id">
					<td>
						<fmt:message key="tipimovimento.label.ggscadenza" />
					</td>
					<td>
						<spring-form:input  id="ggscadenza_id" path="ggscadenza" size="6"  cssStyle="text-align:right;" onchange="javascript:checkNumberValue(this);javascript:isPositiveNumber(this)"/>
						<spring-form:errors path="ggscadenza" cssClass="error"/>
					</td>
				</tr>	
			</table>
		</spring-form:form>
	</div>
	<script type='text/javascript'>
		function comportamento(id)
		{
			var comportamento_var = document.getElementById(id);
			if(comportamento_var.value!=<%=WebConstants.ONERI_COMPORTAMENTO_IMPOSTA_SCADENZA%>)
				{
					$("sezione_scadenza_id").fade();
				}
			else
				{
					$("sezione_scadenza_id").appear();
				}
			
		}
    </script>
	<div id="functions">
		<ul>
			<c:if test="${typeform eq 'new'}">
				<li><a href="javascript:doSubmit('insertOneri.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${typeform eq 'view'}">
				<li><a href="javascript:doSubmit('deleteOneri.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listoneri.htm?tipimovimento.codice=${tipimovimento.id.tipomovimento}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>