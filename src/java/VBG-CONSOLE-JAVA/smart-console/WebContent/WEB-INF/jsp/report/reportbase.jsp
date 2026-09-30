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
		
		<fmt:message key="label.stampe_base" />
	
	</title>
</head>
<body>

	<%
    	String displayFiltriAnagrafe="display:none;";
	%>

	<span class="titoloPagina">
			<fmt:message key="label.report_base" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="reportbase" name="inviodati" target="_blank">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="reportbase" />
		    </jsp:include>
			<table width="100%">
				<%-- 
				<tr>
					<td>
						<fmt:message key="label.seleziona_tipo_stampa" />
					</td>
					<td>
						<spring-form:select id="tipo_report_id" path="typeReport" onchange="selectReport('tipo_report_id');">
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.OPERATORI%>"><fmt:message key="label.lista_operatori" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.FORME_GIURIDICHE%>"><fmt:message key="label.lista_forme_giuridiche" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.TEMPIFICAZIONI%>"><fmt:message key="label.lista_tempificazioni" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.AMMINISTRAZIONI%>"><fmt:message key="label.lista_amministazioni" /></spring-form:option>
						 	<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.AMMINISTRAZIONI%>"><fmt:message key="label.amministrazione" /></spring-form:option> 
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.RICHIEDENTI%>"><fmt:message key="label.lista_richiedenti_tecnici" /></spring-form:option>
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
				 	<td width="15%" ><fmt:message key="label.lista_operatori" /></td>
				 	<td><spring-form:radiobutton id="operatori_id" path="typeReport" value="<%=TypeReport.OPERATORI%>" onclick="selectReport('operatori_id');"/></td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.lista_forme_giuridiche" /></td>
				 	<td><spring-form:radiobutton id="forme_giuridiche_id" path="typeReport" value="<%=TypeReport.FORME_GIURIDICHE%>" onclick="selectReport('forme_giuridiche_id');"/></td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.lista_tempificazioni" /></td>
				 	<td><spring-form:radiobutton id="tempific_id" path="typeReport" value="<%=TypeReport.TEMPIFICAZIONI%>" onclick="selectReport('tempific_id');"/></td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.lista_amministazioni" /></td>
				 	<td><spring-form:radiobutton id="ammin_id" path="typeReport" value="<%=TypeReport.AMMINISTRAZIONI%>" onclick="selectReport('ammin_id');"/></td>
				</tr>
				<tr>
				 	<td width="15%" ><fmt:message key="label.lista_richiedenti_tecnici" /></td>
				 	<td><spring-form:radiobutton id="richiedenti_id" path="typeReport" value="<%=TypeReport.RICHIEDENTI%>" onclick="selectReport('richiedenti_id');"/></td>
				</tr>			
					<%-- 
					<td>
						<spring-form:select id="tipo_report_id" path="typeReport" onchange="selectReport('tipo_report_id');">
							<spring-form:option value="typeReport<fmt:message key="label.lista_operatori" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.FORME_GIURIDICHE%>"><fmt:message key="label.lista_forme_giuridiche" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.TEMPIFICAZIONI%>"><fmt:message key="label.lista_tempificazioni" /></spring-form:option>
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.AMMINISTRAZIONI%>"><fmt:message key="label.lista_amministazioni" /></spring-form:option>
						 	<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.AMMINISTRAZIONI%>"><fmt:message key="label.amministrazione" /></spring-form:option> 
							<spring-form:option value="<%=it.gruppoinit.pal.gp.backoffice.report.helper.TypeReport.RICHIEDENTI%>"><fmt:message key="label.lista_richiedenti_tecnici" /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="typeReport" cssClass="error"/>
					</td>
					
				</tr>
				--%>
				<tr id="filtri_anagrafe_nominativo" style="<%=displayFiltriAnagrafe%>">
				    <td><fmt:message key="label.nominativo" /></td>
				    <td><spring-form:input id="nominativo_id" path="anagrafeFilter.datiAnagrafe.nominativo" size="40"/></td>
				</tr>
				<tr id="filtri_anagrafe_soggetti" style="<%=displayFiltriAnagrafe%>">
				    <td><fmt:message key="label.tipo_soggetto" /></td>
				    <td>
				    	<spring-form:select id="tipo_report_id" path="anagrafeFilter.datiAnagrafe.tipologia" >
				    		<spring-form:option value=""><fmt:message key="label.tutti" /></spring-form:option>
							<spring-form:option value="0"><fmt:message key="label.richiedenti" /></spring-form:option>
							<spring-form:option value="-1"><fmt:message key="label.tecnico" /></spring-form:option>
						</spring-form:select>
				    </td>
				</tr>
				
			</table>
			<script type='text/javascript'>
			
			 function selectReport(id)
				{
				     
					if(document.getElementById(id).value=='<%=TypeReport.RICHIEDENTI%>')
					{	
						$('filtri_anagrafe_nominativo').style.display = '';
			    		$('filtri_anagrafe_soggetti').style.display = '';
					}else
					{
						$('filtri_anagrafe_nominativo').style.display = 'none';
			    		$('filtri_anagrafe_soggetti').style.display = 'none';
					}
		    		
				}
			 
			 
			 function printReport(){
					var url  = URLDecode('${_urlback}');			
					document.inviodati.action='printReportBase.htm';
					setTimeout("document.inviodati.submit()",10);
			 }
			
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:printReport()"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>