<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
			<fmt:message key="amministrazioni.label.dettaglio_mail.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="amministrazioni.label.dettaglio_mail.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">

   <div class="parametriDiv">
		<div class="etichetta">
        <div>
             <fmt:message key="label.amministrazione" />:
       	</div>
        </div>
    <div class="parametro">
       	<div>
           	  <c:out value="${email.amministrazioni.amministrazione}"/>
   	   </div>
    </div>
    </div> 
    <br class="clear" /> 
	<spring-form:form commandName="email" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="email" />
    </jsp:include>
	 
    <table>
        <tr>
			<td><fmt:message key="label.data_email" /></td>
			<td><spring-form:input id="data_id" path="data" size="11" readonly="true"/></td>
		</tr>
		<tr>
			<td><fmt:message key="label.oggetto_email" /></td>
			<td><spring-form:input id="oggetto_id" path="oggetto" size="70" readonly="true"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.testo_email" /></td>
			<td><spring-form:textarea id="testo_id" path="testo" cols="70" rows="15" readonly="true"/></td>
		</tr>
		
	</table>
	
	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('listemail.htm?codice=${email.amministrazioni.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
