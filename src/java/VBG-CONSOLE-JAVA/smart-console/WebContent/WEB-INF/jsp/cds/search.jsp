<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.cds" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.cds" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="cdsFilter" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
	        <jsp:param name="commandName" value="cdsFilter" />
	    </jsp:include>
	    <table>
	    		<tr id="elementIdBeforeCombo">
					<td width="15%"></td>
					<td colspan="3"></td>
				</tr>
				<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="mostraTutti" value="true" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="comune" />
					<jsp:param name="colspan" value="4" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>	    	
				<tr>
					<td><fmt:message key="label.numeroistanza" /></td>
					<td colspan="3">
						<spring-form:input id="numeroistanza_id" path="numeroistanza" size="10"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.periodo" />&nbsp;<fmt:message key="label.da" />
					</td>
					<td>						
						<spring-form:input id="dallaData_id" path="dallaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="dallaData" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.a" />
						<spring-form:input id="allaData_id" path="allaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData_id" textKey="label.calendar" /> 
						<spring-form:errors path="allaData" cssClass="error" delimiter="," />
					</td>
				</tr>
			<tr>
				<td>
		    		<fmt:message key="label.anagrafe" />
		    	</td>
				<td>
					<spring-form:input id="anagrafe_id" path="nominativo" size="67"/>
				</td>
			</tr>
		</table>
	</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('list.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>