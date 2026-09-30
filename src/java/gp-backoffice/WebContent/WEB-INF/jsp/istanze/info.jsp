<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.informazioni_istanza" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.informazioni_istanza" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/infoView" />
    	<jsp:param name="qs" value="codice%3D${istanzeCommand.entity.id.codice }" />
	</jsp:include>	
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeCommand.entity.id.codice}</c:param>
	</c:import>
		
	<br class="clear" />
	<div id="subcontent">

		<spring-form:form commandName="istanzeCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeCommand" />
		    </jsp:include>
				<table width="100%" border="0">

					<tr class="titoloSezione">
					
						<td colspan="6"><fmt:message key="label.stati_istanza"/></td>
					</tr>
					<c:if test="${ not empty istanzeCommand.entity.attivita.id.codice}">
						<tr >
							<td width="20%"><fmt:message key="label.stato_dell_attivita" /></td>
							<td colspan="5" >								
									<b id="stato_attivita_id">
										<c:if test="${istanzeCommand.entity.attivita.attiva eq true}">
											<fmt:message key="label.attiva" />
										</c:if>
										<c:if test="${istanzeCommand.entity.attivita.attiva ne true}">
											<fmt:message key="label.non_attiva" />
										</c:if>
										-
										<c:if test="${istanzeCommand.entity.attivita.operante eq true}">
											<fmt:message key="label.operante" />
										</c:if>
										<c:if test="${istanzeCommand.entity.attivita.operante ne true}">
											<fmt:message key="label.non_operante" />
										</c:if>									
									</b>
							</td>
						</tr>
					</c:if>
					<tr>					
						<td width="20%" title="<fmt:message key="label.stato_attuale_dell_istanza.help" />"><fmt:message key="label.stato_attuale_dell_istanza" /></td>
						<td colspan="5" title="<fmt:message key="label.stato_attuale_dell_istanza.help" />">
							<spring-form:select id="statoistanza_id" path="entity.chiusura.id.codicestato">
								<spring-form:options items="${statiistanzaList}" itemValue="id.codicestato" itemLabel="stato"/>
							</spring-form:select>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.termine_stimato_del_procedimento" /></td>
						<td colspan="5">
							<c:if test="${empty istanzeCommand.entity.istanzeTempistica.stato}">
								<fmt:formatDate value="${istanzeCommand.entity.istanzeTempistica.datafine}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />&nbsp;${istanzeCommand.entity.istanzeTempistica.transientDurataStimataProcedimento}<fmt:message key="label.gg" />
								<c:if test="${istanzeCommand.entity.istanzeTempistica.ggaggiuntivi gt 0}">
									<span id="ggaggiuntivi_id" style="cursor: pointer;">[*]</span>
									<div id="ggaggiuntivi_id_tooltip" dojoType="dijit.Tooltip" connectId="ggaggiuntivi_id" position="after" style="display: none;">
									<fmt:message key="label.istanze_tempistica.conteggio_gg_aggiuntivi">
										<fmt:param value="${istanzeCommand.entity.istanzeTempistica.ggaggiuntivi}"></fmt:param>
									</fmt:message>
									</div>											
								</c:if>
								
							</c:if>							
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.termine_del_procedimento" /></td>
						<td colspan="5">
							<c:if test="${empty istanzeCommand.entity.istanzeTempistica.stato}">
								<c:if test="${not empty istanzeCommand.entity.istanzeTempistica.datafineeffettiva}">
									<fmt:formatDate value="${istanzeCommand.entity.istanzeTempistica.datafineeffettiva}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />&nbsp;${istanzeCommand.entity.istanzeTempistica.transientDurataProcedimento}<fmt:message key="label.gg" />
								</c:if>
								<c:if test="${istanzeCommand.entity.istanzeTempistica.ggaggiuntivi gt 0}">
									<span id="ggaggiuntivi_id2" style="cursor: pointer;">[*]</span>
									<div id="ggaggiuntivi_id2_tooltip" dojoType="dijit.Tooltip" connectId="ggaggiuntivi_id2" position="after" style="display: none;">
									<fmt:message key="label.istanze_tempistica.conteggio_gg_aggiuntivi">
										<fmt:param value="${istanzeCommand.entity.istanzeTempistica.ggaggiuntivi}"></fmt:param>
									</fmt:message>
									</div>
								</c:if>
							</c:if>							
						</td>
					</tr>
					<tr class="titoloSezione">
						<td colspan="6"><fmt:message key="label.parametri"/></td>
					</tr>
					<tr>
						<td><fmt:message key="label.azione" /></td>
						<td colspan="5">
							<spring-form:select id="azione_id" path="entity.azione">
								<spring-form:options items="${azioniList}" itemValue="azAzione" itemLabel="azDescrizione"/>
							</spring-form:select>
						</td>
					</tr>
					<tr class="titoloSezione">
						<td colspan="6"><fmt:message key="label.dati_del_provvedimento"/></td>
					</tr>	
					<tr>
						<td><fmt:message key="label.data_validita" /></td>
						<td colspan="5">
							<fmt:formatDate value="${istanzeCommand.entity.datavalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
						</td>
					</tr>				
				</table>
			</spring-form:form>
			<div class="dijitHidden">		
				<div dojoType="dijit.Tooltip" connectId="stato_attivita_id" position="after">				
					<fmt:message key="label.stato_dell_attivita.help" />
				</div>		
			</div>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('infoUpdate.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>