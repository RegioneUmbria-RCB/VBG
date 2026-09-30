<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.configurazione_mailtipo.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		
			<fmt:message key="label.configurazione_mailtipo.title" />
		
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="configurazione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="configurazione" />
		    </jsp:include>
			<table width="100%" cellspacing="6" cellpadding="6" >
			<tr  class="titoloSezione">
				<td colspan="3"><fmt:message key="label.invio_automatico_mail" /></td>
			</tr>
			<tr>
			    <td><fmt:message key="label.notifica_registrazione_mov_sportello" /></td>
			    <td>
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="mailtipoAmministrazioneEndo" />			
					<jsp:param name="propertyPath" value="configurazione.mailtipoAmministrazioneEndo" />			
					<jsp:param name="pathPropertyDescription" value="configurazione.mailtipoAmministrazioneEndo.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="configurazione.mailtipoAmministrazioneEndo.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />
					<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
					<jsp:param name="autocompleterInputSize" value="50"/>
				</jsp:include>
				</td>
				<td><fmt:message key="label.descrizione_mailtipoAmministrazioneEndo" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.notifica_registrazione_mov_richidente" /></td>
			    <td >
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="mailtipoMovimentoRichiedente" />			
					<jsp:param name="propertyPath" value="configurazione.mailtipoMovimentoRichiedente" />			
					<jsp:param name="pathPropertyDescription" value="configurazione.mailtipoMovimentoRichiedente.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="configurazione.mailtipoMovimentoRichiedente.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />
					<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
					<jsp:param name="autocompleterInputSize" value="50"/>
				</jsp:include>
				
				</td>
				<td><fmt:message key="label.descrizione_mailtipoMovimentoRichiedente" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.notifica_registrazione_mov_amministrazione" /></td>
			    <td>
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="mailtipoMovimentoAmministrazione" />			
					<jsp:param name="propertyPath" value="configurazione.mailtipoMovimentoAmministrazione" />			
					<jsp:param name="pathPropertyDescription" value="configurazione.mailtipoMovimentoAmministrazione.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="configurazione.mailtipoMovimentoAmministrazione.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />
					<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
					<jsp:param name="autocompleterInputSize" value="50"/>
				</jsp:include>
				
				</td>
				<td><fmt:message key="label.descrizione_mailtipoMovimentoAmministrazione" /></td>	
			</tr>
			<tr>
				<td><fmt:message key="label.notifica_registrazione_mov_negativo_amministrazione" /></td>
				<td align="top">	
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="mailtipoMovimentoNegativo" />			
					<jsp:param name="propertyPath" value="configurazione.mailtipoMovimentoNegativo" />			
					<jsp:param name="pathPropertyDescription" value="configurazione.mailtipoMovimentoNegativo.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="configurazione.mailtipoMovimentoNegativo.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />
					<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
					<jsp:param name="autocompleterInputSize" value="50"/>
				</jsp:include>
				
				</td>
			    <td><fmt:message key="label.descrizione_mailtipoMovimentoNegativo" /></td>
			</tr>								
			<tr class="titoloSezione">
				<td colspan="3"><fmt:message key="label.oggetto_protocollo" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.protocollo_istanza" /></td>
				<td>	
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="mailtipoByFkIstanza" />			
					<jsp:param name="propertyPath" value="protocolloConfigurazione.mailtipoByFkIstanza" />			
					<jsp:param name="pathPropertyDescription" value="protocolloConfigurazione.mailtipoByFkIstanza.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="protocolloConfigurazione.mailtipoByFkIstanza.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipoMultiambito.htm?ambito=P&ambito=T" />
					<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
					<jsp:param name="autocompleterInputSize" value="50"/>
				</jsp:include>
				
				</td>
				<td><fmt:message key="label.descrizione_protocollo_istanza" /></td>
			</tr>	
			<tr>
				<td><fmt:message key="label.protocollo_movimento" /></td>
				<td>	
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="mailtipoByFkMovimento" />			
					<jsp:param name="propertyPath" value="protocolloConfigurazione.mailtipoByFkMovimento" />			
					<jsp:param name="pathPropertyDescription" value="protocolloConfigurazione.mailtipoByFkMovimento.descrizioneEstesa" />
					<jsp:param name="pathPropertyCode" value="protocolloConfigurazione.mailtipoByFkMovimento.id.codice" />
					<jsp:param name="autocompleterAjax" value="findMailtipoMultiambito.htm?ambito=P&ambito=T" />
					<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
					<jsp:param name="autocompleterInputSize" value="50"/>
				</jsp:include>
				
				</td>
				<td><fmt:message key="label.descrizione_protocollo_movimento" /></td>
			</tr>										
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${formMailAndTestiTipo.id.codice==null}">
				<li><a href="javascript:doSubmit('insertOrUpdateConfigurazioneMailAntTestiTpo.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<%-- 
			<c:if test="${formMailAndTestiTipo.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			--%>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>