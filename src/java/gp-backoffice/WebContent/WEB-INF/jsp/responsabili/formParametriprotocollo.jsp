<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="responsabili.label.parametri_protocollo.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="responsabili.label.parametri_protocollo.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
	            <div>
	             	<fmt:message key="label.responsabile" />:
	        	</div>
           	</div>
           	<div class="parametro">
       	   		<div>
		        	 <c:out value="${responsabile.responsabile}"/>
		   		</div>
		 	</div>
        </div>   
        <br />
		<spring-form:form commandName="responsabile" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="responsabile" />
		    </jsp:include>
			<table>				
				<tr id="protocolloFlussos_id" >
					<td valign="middle" width="100">
				 		<fmt:message key="label.registro" />
				 	</td>
				 	<td>
				 		<spring-form:select path="protocolloFlussos" multiple="multiple" size="${fn:length(protocolloFlussoList)}">
				 			<spring-form:options items="${protocolloFlussoList}" itemValue="codice" itemLabel="descrizione"/>
				 		</spring-form:select>
				 		<spring-form:errors path="protocolloFlussos" cssClass="error" />
				 	</td>
				</tr>									
			</table>			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('saveParametriprotocollo.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${responsabile.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>