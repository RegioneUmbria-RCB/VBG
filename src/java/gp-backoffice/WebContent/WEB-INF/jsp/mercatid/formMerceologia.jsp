<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="mercatid.label.nuova_merceologia.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="mercatid.label.nuova_merceologia.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercati.descrizione}" />											
		</jsp:include>
	     <div class="parametriDiv">
	    	<div class="etichetta">
				
				<c:if test="${mercatid.codiceposteggio!=''}">
				<div><fmt:message key="label.codiceposteggio" />:</div>
				</c:if>
			</div>
			<div class="parametro">
				
				<c:if test="${mercatid.codiceposteggio!=''}">
				<div><c:out value="${mercatid.codiceposteggio}" /></div>
				</c:if >
			</div>
	    </div>
	    <div class="clear"></div>	 	
	   <jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatidattivitaistat" />
		</jsp:include>
		<spring-form:form commandName="mercatidattivitaistat" name="inviodati">
	
			<table>
				<tr>
					<td>
						<fmt:message key="label.attivita" />
					</td>
					<td>
						<spring-form:input id="attivita_id" path="attivita.istat" cssClass="searchbox" onchange="checkValue(this,'attivita_hidden')" onkeydown="javascript:return searchAll(this,event)" size="60"/>
						<init:autocompleter methodAjax="findAttivita.htm?codicesettore=" idHidden="attivita_hidden" idInput="attivita_id" inputTitleKey="label.ricerca_attivita"></init:autocompleter>
						<spring-form:errors path="attivita" cssClass="error"/> 
						<spring-form:hidden id="attivita_hidden" path="attivita.id.codiceistat"  />
					</td>
				</tr>
				 <tr>
			       <td><fmt:message key="label.consentito" /></td>
			       <td><spring-form:checkbox id="flagConsentito_id" path="flagConsentito"/>
			       <init:help idHelp="help1" textKey="mercatid.help.consentito"/>
			       <spring-form:errors path="flagConsentito" cssClass="error"/></td>
		        </tr>
			</table>
		
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
		
			<li><a href="javascript:doSubmit('insertMerceologia.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>