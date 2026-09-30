<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${amministrazionireferenti.id.codice==null}">
			<fmt:message key="amministrazioni.label.nuova_amministrazionereferente.title" />
		</c:if> 
		<c:if test="${amministrazionireferenti.id.codice!=null}">
			<fmt:message key="amministrazioni.label.modifica_amministrazionereferente.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${amministrazionireferenti.id.codice==null}">
	<fmt:message key="amministrazioni.label.nuova_amministrazionereferente.title" />
</c:if> 
<c:if test="${amministrazionireferenti.id.codice!=null}">
	<fmt:message key="amministrazioni.label.modifica_amministrazionereferente.title" />
</c:if>
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
           	 <c:out value="${amministrazionireferenti.amministrazioni.amministrazione}"/>
       	</div>
		 </div>
    </div>  
    <br class="clear"/>
	<spring-form:form commandName="amministrazionireferenti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="amministrazionireferenti" />
    </jsp:include>

    
	<table>
		<tr>
			<td><fmt:message key="label.ufficio" /></td>
			<td><spring-form:input id="ufficio_id" path="ufficio" size="50" />
			<spring-form:errors path="ufficio" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.referente" /></td>
			<td><spring-form:input id="referente_id" path="referente" size="50" />
			<spring-form:errors path="referente" cssClass="error"/></td>
		</tr>
         <tr>
			<td><fmt:message key="label.indirizzo" /></td>
			<td><spring-form:input id="indirizzo_id" path="indirizzo" size="50" />
			<spring-form:errors path="indirizzo" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.citta" /></td>
			<td><spring-form:input id="citta_id" path="citta" size="50" />
			<spring-form:errors path="citta" cssClass="error"/></td>
		</tr>
		<tr>
        	<td><fmt:message key="label.email" /></td>
			<td><spring-form:input id="email_id" path="email" size="50" />
			<spring-form:errors path="email" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.cap" /></td>
			<td><spring-form:input id="cap_id" path="cap" size="5" />
			<spring-form:errors path="cap" cssClass="error"/></td>

            <td><fmt:message key="label.provincia" /></td>
			<td><spring-form:input id="provincia_id" path="provincia" size="2" />
			<spring-form:errors path="provincia" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.telefono1" /></td>
			<td><spring-form:input id="telefono1_id" path="telefono1" size="15" />
			<spring-form:errors path="telefono1" cssClass="error"/></td>

            <td><fmt:message key="amministrazioni.label.telefono2" /></td>
			<td><spring-form:input id="telefono2_id" path="telefono2" size="15" />
			<spring-form:errors path="telefono2" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="label.fax" /></td>
			<td><spring-form:input id="fax_id" path="fax" size="15" />
			<spring-form:errors path="fax" cssClass="error"/></td>
        </tr>
        
		
	</table>
	
	<script type='text/javascript'>
		$('ufficio_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${amministrazionireferenti.id.codice==null}">
		<li><a href="javascript:doSubmit('insertAmministrazionireferenti.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${amministrazionireferenti.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateAmministrazionireferenti.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteAmministrazionireferenti.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('listamministrazionireferenti.htm?codice=${amministrazionireferenti.amministrazioni.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
