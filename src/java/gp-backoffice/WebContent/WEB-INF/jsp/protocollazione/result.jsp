<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page
	import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.richiesta_protocollo" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message
	key="label.richiesta_protocollo" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
	<jsp:include page="../includes/history.jsp">
	   	<jsp:param name="path" value="../protocollazione/leggiProtocollo" />
	</jsp:include>
	<c:if test="${not empty protocolloCommand.entity.id.codice}">
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${protocolloCommand.entity.id.codice}</c:param>
	</c:import>
	</c:if>
<br class="clear" />
<div id="subcontent">
<spring-form:form commandName="protocolloCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="protocolloCommand" />
	</jsp:include>
	<c:if test="${protocolloCommand.mettiAllaFirma == null || protocolloCommand.mettiAllaFirma eq false }">
	<table width="100%">
		<tr>
			<td width="15%"><fmt:message key="label.numero_protocollo" /></td>
			<td><b>${protocolloCommand.datiProtocollo.numeroProtocollo}</b></td>
		</tr>
		<tr>
			<td width="15%"><fmt:message key="label.data_protocollo" /></td>
			<td><b>${protocolloCommand.datiProtocollo.dataProtocollo}</b></td>
		</tr>
		<c:if test="${not empty protocolloCommand.datiProtocollo.messaggio}">
			<tr>
				<td width="15%"><fmt:message key="label.messaggio" /></td>
				<td><b>${protocolloCommand.datiProtocollo.messaggio}</b></td>
			</tr>
		</c:if>		
	</table>
	</c:if>
	<c:if test="${protocolloCommand.mettiAllaFirma eq true }">
	<label><fmt:message key="label.movimento_messo_alla_firma"/></label>
	</c:if>
</spring-form:form>
</div>

<jsp:include page="../includes/dialogs.jsp"/>
<jsp:include page="../includes/pannelloSceltaSoftwarePec.jsp"/>	

<script type="text/javascript">

	function backToPecInbox(){
		doHref("${pageContext.request.contextPath}/pecinbox/indietro.htm?idPec=${istanzaPecCommand.pec.id.id}", "");
	}
	
	function creaIstanzaDaPec(idPec,idAccount){
		var url = "../pecinbox/istanzaDaPEC.htm?codicePec=" + idPec+'&idAccount='+idAccount;
		if(isSoftwareTT){
			pannelloSceltaSoftware(url,'historySet',true,'',idPec,idAccount);						
		}else{
			vaiAFunzione(url,'historySet',true,'',idPec);
		}	
	}
	
	function creaMovimentoDaPec(idPec,idAccount){
		var url = "../pecinbox/movimentoDaPEC.htm?codicePec=" + idPec+'&idAccount='+idAccount;
		if(isSoftwareTT){
			pannelloSceltaSoftware(url,'historySet',false,'',idPec,idAccount);						
		}else{
			vaiAFunzione(url,'historySet',false,'',idPec);
		}					
	}
</script>

<div id="functions">
   
	<ul>	     
	    <c:if test="${protocolloCommand.movimento.id.codice != null}">
	    	<c:choose>	
		    	<c:when test="${IS_DOCER_TIPOINVIO_PEC_MANUALE eq true}">
					<li><a href="javascript:doSubmit('invioPecDocer.htm','<fmt:message key="label.javascript.invio_pec_docer" />',document.inviodati)"><fmt:message key="button.invio_mail" /></a></li>
		   		</c:when>
		   		<c:otherwise>
		   		    <c:if test="${isViewInviaEmail}">
		   				<li><a href="javascript:doSubmit('createMail.htm','',document.inviodati)"><fmt:message key="button.invio_mail" /></a></li>
		   			</c:if>
		   		</c:otherwise>
	   		</c:choose>	
		</c:if>
		<c:if test="${protocolloCommand.provenienza ne 'P'}">
			<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
		</c:if>
		<c:if test="${protocolloCommand.provenienza eq 'P'}">
			<li><a href="javascript:backToPecInbox();"><fmt:message	key="button.back" /></a></li>
			<c:if test="${protocolloCommand.pec != null and protocolloCommand.pec.istanze == null and protocolloCommand.pec.movimenti == null}">
				<li><a href="javascript:creaIstanzaDaPec('${protocolloCommand.pec.id.id}','${protocolloCommand.pec.mailConfig.id.codice}');"><fmt:message	key="pecinbox.label.creaistanza" /></a></li>
				<li><a href="javascript:creaMovimentoDaPec('${protocolloCommand.pec.id.id}','${protocolloCommand.pec.mailConfig.id.codice}');"><fmt:message	key="pecinbox.label.creamovimento" /></a></li>
			</c:if>
		</c:if>
	</ul>
</div>
</body>
</html>