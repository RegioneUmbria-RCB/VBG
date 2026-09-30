<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.report.helper.TypeReport"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
		<fmt:message key="label.pannello_stampe" />
	
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.pannello_stampe" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="reportistanze" name="inviodati" target="_blank">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="reportistanze" />
		    </jsp:include>
			<table width="100%">
				<%--
				<tr>
					<td>
						<fmt:message key="label.seleziona_tipo_stampa" />
					</td>
					<td>
						<spring-form:select id="report_id" path="typeReport">
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.RICEVUTA_ISTANZA%>"><fmt:message key="label.ricevuta_istanza" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.REPORT_ISTANZA%>"><fmt:message key="label.istanza" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.ITER_ISTANZA%>"><fmt:message key="label.iter_istanza" /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="typeReport" cssClass="error"/>
					</td>
			 	</tr>
			 	 --%>	
				<tr  class="titoloSezione">
					<td  colspan="2">
						<fmt:message key="label.seleziona_tipo_stampa" />
					</td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.ricevuta_istanza" /></td>
				 	<td><spring-form:radiobutton id="ricevuta_id" path="typeReport"  value="<%=TypeReport.RICEVUTA_ISTANZA%>" /></td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.istanza" /></td>
				 	<td><spring-form:radiobutton id="istanza_id" path="typeReport" value="<%=TypeReport.REPORT_ISTANZA%>"/></td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.iter_istanza" /></td>
				 	<td><spring-form:radiobutton id="iter_id" path="typeReport" value="<%=TypeReport.ITER_ISTANZA%>"/></td>
				</tr>
			</table>
			<script type='text/javascript'>
			
			function printReport(){
				var url  = URLDecode('${_urlback}');			
				document.inviodati.action='printReportIstanze.htm';
				setTimeout("document.inviodati.submit()",10);
			}
			
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:printReport()"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:doHref('../istanze/view.htm?codice=${reportistanze.istanze.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>