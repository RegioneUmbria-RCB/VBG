<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="anagrafe.label.dettaglio_email.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="dettaglio_email" />
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
           	 		<c:out value="${anagrafe.emailanagr.anagrafe.descrizioneRichiedente}"/>
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
						<fmt:message key="label.data" />
					</td>
					<td>
						<spring-form:input id="data_id" path="emailanagr.data" size="10" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.oggetto_email" />
					</td>
					<td>
						<spring-form:input id="oggetto_mail_id" path="emailanagr.oggetto" size="60" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.testo_email" />
					</td>
					<td>
						<spring-form:textarea id="testo_mail_id" path="emailanagr.testo" cols="70" rows="20" readonly="true" />
					</td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('listemail.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>