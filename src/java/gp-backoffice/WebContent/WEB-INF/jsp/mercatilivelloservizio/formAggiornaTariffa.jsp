<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.mercatidlivelloservizio_aggiorna.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.mercatidlivelloservizio_aggiorna.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidlivelloservizio/createUpdateTariffaServizio" />
	</jsp:include>
	
	
	<div id="subcontent">
		<spring-form:form commandName="mercatilivelloservizio" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatilivelloservizio" />
		    </jsp:include>
		    
		     <fieldset>
		        <legend><fmt:message key="label.procedura_aggiornamento.title" /></legend>
		        <br/>
		       	<fmt:message key="label.procedura_aggiornamento" /><br/>
		       	<br/><fmt:message key="label.livello_servizio_da_aggiornare_regole" /><br/>
				<fmt:message key="label.livello_servizio_da_aggiornare_operazioni" />
    		 </fieldset>
    		
		    <br/>
		    
		    
		    <table border="0" width="100%">
		    <tr >
		    	<td width="50%">
		    		<table border="0" width="100%">
		    		<%-- servizio da aggiornare --%>
		    		<tr  class="titoloSezione">
		    			<td  colspan="2">
							<fmt:message key="label.livello_servizio_da_aggiornare" />
						</td>
		    		</tr>	
		    		<tr>
		                <td><fmt:message key="label.giorno" /></td>
						<td><spring-form:input id="descrizione_id" path="entityupdate.mercatiUso.descrizione" size="70" readonly="true" /></td>
					</tr>
					<tr>
						<td><fmt:message key="label.descrizione" /></td>
						<td><spring-form:input id="descrizione_id" path="entityupdate.descrizione" size="70" readonly="true"/></td>
					</tr>
					<tr>
						<td><fmt:message key="label.tipologia_livello_servizio" /></td>
						<td><spring-form:input id="descrizione_id" path="entityupdate.livelloServizio.descrizione" size="60" readonly="true"/></td>
					</tr>
					<tr>
						<td><fmt:message key="label.attivo" /></td>
						<td><spring-form:checkbox id="attivo_id" path="entityupdate.attivo" disabled="true" /></td>
					</tr>
					<tr>
						<td><fmt:message key="label.tariffa" /></td>
						<td><spring-form:input id="tariffa_id" path="entityupdate.tariffa" size="6" onblur="checkNumberValue(this);" readonly="true" /></td>
					</tr>
					<tr>
						<td><fmt:message key="label.data_inizio_validita" /></td>
						<td>
							<spring-form:input id="data_id" path="entityupdate.dataInizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="true"/>
	
							<fmt:message key="label.data_fine_validita" />
							<spring-form:input id="datafine_id" path="entityupdate.dataFineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="true"/>
							<c:if test="${!isDataInizioCalcolata}"><fmt:message key="help.mercatilivelloservizio.data_fine_validita_aggiorna" /></c:if>
							<%-- 
							<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="datafine_id" textKey="label.calendar"/>
							<spring-form:errors path="entity.dataFineValidita" cssClass="error"/>
							
							--%>
						</td>
					</tr>
		    		</table>
		    	</td>
		    	<td valign="top" width="50%">
		    	
		    		<table border="0" width="100%">
		    			
		    			<tr class="titoloSezione">
		    			<td valign="top" colspan="2">
							<fmt:message key="label.livello_servizio_aggiornamento" />
						</td>
						<tr>
		                <td><fmt:message key="label.giorno" /></td>
						<td><spring-form:input id="descrizione_id" path="entity.mercatiUso.descrizione" size="70" readonly="true" /></td>
						</tr>
						<tr>
							<td><fmt:message key="label.descrizione" />*</td>
							<td><spring-form:input id="descrizione_id" path="entity.descrizione" size="60" />
							    <spring-form:errors path="entity.descrizione" cssClass="error"/>
								<fmt:message key="help.mercatilivelloservizio.descrizione_aggiornamento" />
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.tipologia_livello_servizio" /></td>
							<td><spring-form:input id="descrizione_id" path="entity.livelloServizio.descrizione" size="70" readonly="true"/></td>
						</tr>
						<tr>
							<td><fmt:message key="label.attivo" /></td>
							<td><spring-form:checkbox id="attivo_id" path="entity.attivo" disabled="true" /></td>
						</tr>
						<tr>
							<td><fmt:message key="label.tariffa" />*</td>
							<td>
								<spring-form:input id="tariffa_id" path="entity.tariffa" size="6" onblur="checkNumberValue(this);"  />
							    <spring-form:errors path="entity.tariffa" cssClass="error"/>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.data_inizio_validita" />*</td>
							<td>
								<spring-form:input id="data_update_id" path="entity.dataInizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${isDataInizioCalcolata}"/>
		                        <c:if test="${!isDataInizioCalcolata}">
		                        	<init:calendar imagePath="/images/cal.gif" idImage="data_updateinizio" idInput="data_update_id" textKey="label.calendar"/>
		                        </c:if>
								<spring-form:errors path="entity.dataInizioValidita" cssClass="error"/>
								<fmt:message key="label.data_fine_validita" />
								<spring-form:input id="datafine_update_id" path="entity.dataFineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
								<init:calendar imagePath="/images/cal.gif" idImage="data_updatefine" idInput="datafine_update_id" textKey="label.calendar"/>
								<spring-form:errors path="entity.dataFineValidita" cssClass="error"/>
							</td>
						</tr>
		    		</table>
		    		
		    	</td>
		    	
		    	
		    	
		    </tr>
		    
		    </table>
		    
		    
		    
			
			
		</spring-form:form>
	</div>
	
	<div id="functions">
		<ul>
			<li>
				<a href="javascript:doSubmit('aggiornaTariffa.htm?codiceservizio=${mercatilivelloservizio.entity.id.codice}','<fmt:message key="help.mercatilivelloservizio.aggiorna_tariffa_servizio" />',document.inviodati)"><fmt:message key="button.aggiorna" /></a>
			</li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<br />
	
		
	
	
	
	    
	    
	   
	
</body>
</html>