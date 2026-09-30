<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="manifestazione.label.altre_info.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="manifestazione.label.altre_info.title" />
	</span>

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
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
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercati" />
   		</jsp:include>
   <spring-form:form commandName="mercati" name="inviodati">
		   
	 <div class="titoloSezione">
	 	<fmt:message key="manifestazione.label.altridati" />
	 </div>
		<table >
		    	<tr>
				<td  width="30%">
					<fmt:message key="manifestazione.label.detDirigenziale" />
				</td>
				<td width="5%">
					<fmt:message key="label.numero" />
				</td>
				<td width="20%">
					<spring-form:input id="detDirigenzialeNumero_id" path="detDirigenzialeNumero" size="16" />
					<spring-form:errors path="detDirigenzialeNumero" cssClass="error"/>
				</td>
				<td width="5%">
					<fmt:message key="label.data" />
				</td>
				<td >
					<spring-form:input id="detDirigenzialeData_id" path="detDirigenzialeData" size="8" />
					<init:calendar imagePath="/images/cal.gif" idImage="caldetermina" textKey="label.calendar" idInput="detDirigenzialeData_id"/>
					<spring-form:errors path="detDirigenzialeData" cssClass="error"/>
				</td>
			</tr>
			<tr >
				<td width="30%">
					<fmt:message key="label.oraIngresso" />
				</td>
				<td width="5%">
					<fmt:message key="label.da" />
				</td>
				<td width="20%">
					<spring-form:input id="oraIngressoInizio_id" path="oraIngressoInizio" size="8" />
					<spring-form:errors path="oraIngressoInizio" cssClass="error"/>
				</td> 
				<td width="5%">
					<fmt:message key="label.a" />
				</td>
				<td>
					<spring-form:input id="oraIngressoFine_id" path="oraIngressoFine" size="8" />
					<spring-form:errors path="oraIngressoFine" cssClass="error"/>
				</td>
			</tr>
			<tr >
				<td width="30%">
					<fmt:message key="label.oraVendita" />
				</td>
				<td width="5%">
					<fmt:message key="label.da" />
				</td>
				<td width="20%">
					<spring-form:input id="oraVenditaInizio_id" path="oraVenditaInizio" size="8" />
					<spring-form:errors path="oraVenditaInizio" cssClass="error"/>
				</td>
				<td width="5%">
					<fmt:message key="label.a" />
				</td>
				<td>
					<spring-form:input id="oraVenditaFine_id" path="oraVenditaFine" size="8" />
					<spring-form:errors path="oraVenditaFine" cssClass="error"/>
				</td>
			</tr>
			<tr >
				<td width="30%">
					<fmt:message key="label.oraSgombero" />
				</td>
				<td width="5%">
					<fmt:message key="label.da" />
				</td>
				<td width="20%">
					<spring-form:input id="oraSgomberoInizio_id" path="oraSgomberoInizio" size="8" />
					<spring-form:errors path="oraSgomberoInizio" cssClass="error"/>
				</td>
				<td width="5%">
					<fmt:message key="label.a" />
				</td>
				<td>
					<spring-form:input id="oraSgomberoFine_id" path="oraSgomberoFine" size="8" />
					<spring-form:errors path="oraSgomberoFine" cssClass="error"/>
				</td>
			</tr>
		
		</table>
			<script type='text/javascript'>
				$('detDirigenzialeNumero_id').focus();
			</script>	
		</spring-form:form>
	</div>

	<div id="functions">
		<ul>
			
			<li><a href="javascript:doSubmit('insertAltridati.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${mercati.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>