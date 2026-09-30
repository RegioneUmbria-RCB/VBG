<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovStcMapping.id.codice==null}">
			<fmt:message key="form.tipimovstcmapping.title.create" />
		</c:if> 
		<c:if test="${tipimovStcMapping.id.codice!=null}">
			<fmt:message key="form.tipimovstcmapping.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipimovStcMapping.id.codice==null}">
	<fmt:message key="form.tipimovstcmapping.title.create" />
</c:if> 
<c:if test="${tipimovStcMapping.id.codice!=null}">
	<fmt:message key="form.tipimovstcmapping.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipimovStcMapping" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipimovStcMapping" />
    </jsp:include>
    <span class="parametri">
        <fmt:message key="form.tipimovstcmapping.tipimovimento" /> : <label><c:out value="${tipimovStcMapping.tipimovimento.movimento }"/></label>
    </span>
	
	<%
		String displayflagAllegaDocumentiAndInviaAllegati="";
		String displayflagProtocolla="";
		String displayParametriProtocollo="";
		if((Boolean)request.getAttribute("viewFlagAllegatiEdoc").equals(true))
		{
		     displayflagAllegaDocumentiAndInviaAllegati="";
		     displayflagProtocolla="";
		    
		}else
		{
		     displayflagAllegaDocumentiAndInviaAllegati="display:none";
		     displayflagProtocolla="display:none";
			 displayParametriProtocollo="display:none";
		}
		if((Boolean)request.getAttribute("viewParametriProtocollo").equals(true))
		{
			 	displayParametriProtocollo="";
		}else
		{
				 displayParametriProtocollo="display:none";
		}
		
	
	%>
	
	
	<div style="width: 650px;min-height: 50px; border: thin dotted; padding: 5px;">
		<fmt:message key="label.help_configurazione_notifica_stc" />
	</div>
	
	<table>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.amministrazioni" /></td>
			<td><spring-form:select id="amministrazioni_id" path="amministrazioni.id.codice" >
				<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
			</spring-form:select>
			<spring-form:errors path="amministrazioni" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.codiceAttDest" /></td>
			<td><spring-form:input id="codiceAttDest_id" path="codiceAttDest" size="50" />
			<init:help idHelp="help_attivita" textKey="form.tipimovstcmapping.codiceAttDest.help"/>
			<spring-form:errors path="codiceAttDest" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.descrizioneAttDest" /></td>
			<td><spring-form:textarea id="descrizioneAttDest_id" path="descrizioneAttDest" cols="70" rows="2" />
			<init:help idHelp="help_desc_attivita" textKey="form.tipimovstcmapping.descrizioneAttDest.help"/>
			<spring-form:errors path="descrizioneAttDest" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.codiceprocedimento" /></td>
			<td><spring-form:input id="codiceprocedimento_id" path="codiceprocedimento" size="50" />
			<init:help idHelp="help_codiceprocedimento" textKey="form.tipimovstcmapping.codiceprocedimento.help"/>
			<spring-form:errors path="codiceprocedimento" cssClass="error"/></td>
		</tr>		
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.flagRifpratStorica" /></td>
			<td><spring-form:checkbox id="flagRifpratStorica_id" path="flagRifpratStorica"/>
			<init:help idHelp="help_flagRifpratStorica" textKey="form.tipimovstcmapping.flagRifpratStorica.help"/>
			<spring-form:errors path="flagRifpratStorica" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.flagNonInviareProcedimenti" /></td>
			<td><spring-form:checkbox id="flagNonInviareProcedimenti_id" path="nonInviareProcedimenti"/>
			<init:help idHelp="help_flagNonInviareProcedimenti" textKey="form.tipimovstcmapping.flagNonInviareProcedimenti.help"/>
			<spring-form:errors path="nonInviareProcedimenti" cssClass="error"/></td>
		</tr>
		
			<tr>
				<td><fmt:message key="label.flag_allega_documenti_endo" /></td>
				<td><spring-form:checkbox id="flagAllegaDocumentiEndo_id" path="flagAllegaDocumentiEndo"/>
				<init:help idHelp="help_flagAllegaDocumentiEndo" textKey="tipimovStcMapping.help.flag_allega_documenti_endo"/>
				<spring-form:errors path="flagAllegaDocumentiEndo" cssClass="error"/></td>
			</tr>
			<tr>
				<td><fmt:message key="label.flag_allega_documenti_istanza" /></td>
				<td><spring-form:checkbox id="flagAllegaDocumentiIstanza_id" path="flagAllegaDocumentiIstanza"/>
				<init:help idHelp="help_flagAllegaDocumentiIstanza" textKey="tipimovStcMapping.help.flag_allega_documenti_istanza"/>
				<spring-form:errors path="flagAllegaDocumentiIstanza" cssClass="error"/></td>
			</tr>
			<tr>
				<td><fmt:message key="label.flag_notifica_intera_pratica" /></td>
				<td><spring-form:checkbox id="flagNotificainterapratica_id" path="flagNotificainterapratica"/>
				<init:help idHelp="help_flagNotificainterapratica" textKey="tipimovStcMapping.help.flag_notifica_intera_pratica"/>
				<spring-form:errors path="flagNotificainterapratica" cssClass="error"/></td>
			</tr>
		<!-- §§§BEGIN§§§ -->
		<c:if test="${inite:isEnterprise()}">
			<tr>
				<td><fmt:message key="label.flag_notificaa_automatica" /></td>
				<td>
					<spring-form:select id="flagNotificaAutomatica_id" path="flagNotificaAutomatica" onclick="viewFlagDocAndAllegati()">
						<spring-form:option value="0"><fmt:message key="label.flag_notifica_automatica.notifica_non_automatica" /></spring-form:option>
						<spring-form:option value="1"><fmt:message key="label.flag_notifica_automatica.notifica_automatica" /></spring-form:option>
						<spring-form:option value="2"><fmt:message key="label.flag_notifica_automatica.notifica_automatica_inserimento" /></spring-form:option>
					</spring-form:select>					
					<init:help idHelp="help_flagNotificaAutomatica" textKey="help.flag_notifica_automatica"/>
					<spring-form:errors path="flagNotificaAutomatica" cssClass="error"/></td>
			</tr>
						
			
			<tr id="tr_flagCreainviaAllegati_id" style="<%=displayflagAllegaDocumentiAndInviaAllegati%>">
				<td><fmt:message key="label.flag_crea_invia_allegati" /></td>
				<td><spring-form:checkbox id="flagCreainviaAllegati_id" path="flagCreainviaAllegati"/>
				<init:help idHelp="help_flagCreainviaAllegati" textKey="tipimovStcMapping.help.flag_crea_invia_allegati"/>
				<spring-form:errors path="flagCreainviaAllegati" cssClass="error"/></td>
			</tr>
					
			
			<%-- 
			<tr id="tr_flagProtocolla_id" style="<%=displayflagProtocolla%>">
			--%>
			<tr id="tr_flagProtocolla_id">
				<td><fmt:message key="label.flagProtocolla" /></td>
				<td><spring-form:checkbox id="flagProtocolla_id" path="flagProtocolla" onclick="viewParametriProtocollazione()"/>
				<init:help idHelp="help_flagProtocolla" textKey="tipimovStcMapping.help.flag_protocolla"/>
				<spring-form:errors path="flagProtocolla" cssClass="error"/></td>
			</tr>
			
			<tr id="tr_flussso_id" style="<%=displayParametriProtocollo%>">
				<td><fmt:message key="label.flusso" /></td>
				<td>
					<spring-form:select id="flusso_id" path="protocolloFlusso.codice"> 
						<spring-form:option value="" ><fmt:message key='label.seleziona'/></spring-form:option>
						<spring-form:options items="${protocolloFlussos}" itemLabel="descrizione" itemValue="codice" />
					</spring-form:select>
					<spring-form:errors path="protocolloFlusso" cssClass="error"/>
					<init:help idHelp="help_flusso" textKey="tipimovStcMapping.help.flusso"/>
				</td>
			</tr>
			
			<tr id="tr_protocolloTipidocumento_id" style="<%=displayParametriProtocollo%>">
			    <td>
					<fmt:message key="label.tipo_documento" />
				</td>
				<td>
					<spring-form:input id="protocolloTipidocumentoCodice_id" path="protocolloTipidocumentoCodice" size="50" />
					<init:help idHelp="help_tipo_documento" textKey="tipimovStcMapping.help.tipo_protocollo_documento"/>					
				</td>
			</tr>			
			
			<tr id="tr_mailtipo_id" style="<%=displayParametriProtocollo%>">
			    <td>
					<fmt:message key="label.oggetto" />
				</td>
				<td>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="mailtipo" />		
						<jsp:param name="propertyPath" value="mailtipo" />				
						<jsp:param name="pathPropertyDescription" value="mailtipo.descrizione" />
						<jsp:param name="pathPropertyCode" value="mailtipo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />	
						<jsp:param name="titleKey" value="label.ricerca_testi_tipo" />
						<jsp:param value="autocompleterInputSize" name="55"/>
					</jsp:include>	
					<init:help idHelp="help_oggetto" textKey="tipimovStcMapping.help.tipi_documento"/>	
				</td>
			</tr>			
			<tr id="tr_amministrazioneMittente_id" style="<%=displayParametriProtocollo%>">
			    <td>
					<fmt:message key="label.amministrazione_mittente" />
				</td>
				<td>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="amministrazioneMittente" />		
						<jsp:param name="propertyPath" value="amministrazioneMittente" />				
						<jsp:param name="pathPropertyDescription" value="amministrazioneMittente.amministrazione" />
						<jsp:param name="pathPropertyCode" value="amministrazioneMittente.id.codice" />
						<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />	
						<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
						<jsp:param value="autocompleterInputSize" name="55"/>
					</jsp:include>
					<init:help idHelp="help_amministrazione_mittente" textKey="tipimovStcMapping.help.amministrazione_mittente"/>		
				</td>
			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="label.flag_invia_schede_istanza" /></td>
			<td><spring-form:checkbox id="flagInviaschedeistanza_id" path="flagInviaschedeistanza"/>
			<init:help idHelp="help_flagInviaschedeistanza" textKey="tipimovStcMapping.help.flag_invia_schede_istanza"/>
			<spring-form:errors path="flagInviaschedeistanza" cssClass="error"/></td>
		</tr>
		<!-- §§§END§§§ -->			
	</table>
</spring-form:form>
		     </div>

	<script type='text/javascript'>
			function viewFlagDocAndAllegati()
			{
				if(document.getElementById("flagNotificaAutomatica_id").value==0)
		    	{	if($('tr_flagCreainviaAllegati_id')){
		    			$('tr_flagCreainviaAllegati_id').style.display = 'none';
		    		}
		    		if($('tr_flagAllegaDocumentiIstanza_id')){
		    			$('tr_flagAllegaDocumentiIstanza_id').style.display = 'none';
		    		}
		    		if($('tr_flussso_id')){
		    			$('tr_flussso_id').style.display = 'none';
		    		}
		    		if($('tr_protocolloTipidocumento_id')){
		    			$('tr_protocolloTipidocumento_id').style.display = 'none';
		    		}
		    		if($('tr_mailtipo_id')){
		    			$('tr_mailtipo_id').style.display = 'none';
		    		}
		    		if($('tr_amministrazioneMittente_id')){
		    			$('tr_amministrazioneMittente_id').style.display = 'none';
		    		}
		    	}else
		    	{
		    		if($('tr_flagCreainviaAllegati_id')){	
		    			$('tr_flagCreainviaAllegati_id').style.display = '';
		    		}
		    		if($('tr_flagAllegaDocumentiIstanza_id')){
		    			$('tr_flagAllegaDocumentiIstanza_id').style.display = '';
		    		}
		    		if(document.getElementById("flagProtocolla_id").checked==true)
		    		{
		    			if($('tr_flussso_id')){
		    				$('tr_flussso_id').style.display = '';
		    			}
		    			if($('tr_protocolloTipidocumento_id')){
			    			$('tr_protocolloTipidocumento_id').style.display = '';
		    			}
		    			if($('tr_mailtipo_id')){
			    			$('tr_mailtipo_id').style.display = '';
		    			}
		    			if($('tr_amministrazioneMittente_id')){
			    			$('tr_amministrazioneMittente_id').style.display = '';
		    			}
		    		}
		    			
		    	}
			}
			
			function viewParametriProtocollazione()
			{
				if(document.getElementById("flagProtocolla_id").checked == false)
		    	{
		    		$('tr_flussso_id').style.display = 'none';
		    		$('tr_protocolloTipidocumento_id').style.display = 'none';
		    		$('tr_mailtipo_id').style.display = 'none';
		    		$('tr_amministrazioneMittente_id').style.display = 'none';
		    	}else
		    	{
		    		if($('flagNotificaAutomatica_id').value==0){
			    		$('tr_flussso_id').style.display = '';
			    		$('tr_protocolloTipidocumento_id').style.display = '';
			    		$('tr_mailtipo_id').style.display = '';
			    		$('tr_amministrazioneMittente_id').style.display = '';
		    		}else{
			    		$('tr_flussso_id').style.display = 'none';
			    		$('tr_protocolloTipidocumento_id').style.display = 'none';
			    		$('tr_mailtipo_id').style.display = 'none';
			    		$('tr_amministrazioneMittente_id').style.display = 'none';
		    		}
		    	}
			}
	</script>	
<div id="functions">
<ul>
	<c:if test="${tipimovStcMapping.id.codice==null}">
		<li><a href="javascript:doSubmit('insertMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipimovStcMapping.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:void 0;" onclick="historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
