<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${anagrafe.scadenze.id.codice==null}">
			<fmt:message key="label.nuovo_notifica_dei_soggetti" />
		</c:if> 
		<c:if test="${anagrafe.scadenze.id.codice!=null}">
			<fmt:message key="label.modifica_notifica_dei_soggetti" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${anagrafe.scadenze.id.codice==null}">
			<fmt:message key="label.nuovo_notifica_dei_soggetti" />
		</c:if> 
		<c:if test="${anagrafe.scadenze.id.codice!=null}">
			<fmt:message key="label.modifica_notifica_dei_soggetti" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	    <div class="parametriDiv">
			<div class="etichetta">
   				<div>
             		<fmt:message key="label.soggetto" />:
       			</div>
        	</div>
        	<div class="parametro">
       			<div>
           	 		<c:out value="${anagrafe.scadenze.anagrafe.descrizioneRichiedente}"/>
       			</div>
		 	</div>
    	</div>  
    <br class="clear"/>
		<spring-form:form commandName="anagrafe" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="anagrafe" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.categoria" />
					</td>
					<td>
						<spring-form:select id="categoria_id" path="scadenze.categoria">
						   <spring-form:options items="${listScadenzabase}" itemValue="codice" itemLabel="descrizione"  />
						</spring-form:select>
						<spring-form:errors path="scadenze.categoria" cssClass="error"/>
					</td>
				</tr>
			    <tr>
					<td>
						<fmt:message key="label.non_mostrare" />
					</td>
					<td>
						<spring-form:checkbox id="flagNascondi_id" path="scadenze.flagNascondi"/>
						<spring-form:errors path="scadenze.flagNascondi" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.dataregistrazione" />
	       			</td>
	       			<td>
						<spring-form:input id="dataregistrazione_id" path="scadenze.dataregistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>
					    <spring-form:errors path="scadenze.dataregistrazione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.datascadenza" />
					</td>
	       			<td>
						<spring-form:input id="datascadenza_id" path="scadenze.datascadenza" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatascadenza" idInput="datascadenza_id" textKey="label.calendar"/>
					    <spring-form:errors path="scadenze.datascadenza" cssClass="error"/>
					</td>
				</tr>
	
				<tr>
					<td>
						<fmt:message key="label.scadenza" />
					</td>
					<td>
						<spring-form:textarea id="flagNascondi_id" path="scadenze.scadenza" rows="2" cols="40"/>
						<spring-form:errors path="scadenze.scadenza" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="scadenze.note" rows="2" cols="40"/>
						<spring-form:errors path="scadenze.note" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('categoria_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${anagrafe.scadenze.id.codice==null}">
				<li><a href="javascript:doSubmit('insertScadenze.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${anagrafe.scadenze.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateScadenze.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteScadenze.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listscadenze.htm?codiceanagrafe=${anagrafe.scadenze.anagrafe.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>