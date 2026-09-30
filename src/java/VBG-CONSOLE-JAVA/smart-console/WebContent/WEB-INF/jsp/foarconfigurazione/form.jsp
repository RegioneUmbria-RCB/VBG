<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${dispatch == 'create'}">
			<fmt:message key="form.foArconfigurazione.title.create" />
		</c:if> 
		<c:if test="${dispatch == 'view'}">
			<fmt:message key="form.foArconfigurazione.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${dispatch=='create'}">
	<fmt:message key="form.foArconfigurazione.title.create" />
</c:if> 
<c:if test="${dispatch=='view'}">
	<fmt:message key="form.foArconfigurazione.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../foarconfigurazione/view" />
	</jsp:include>
<div id="subcontent">

	<c:set var="VERTICALIZZAZIONE_AREA_RISERVATA_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA)%></c:set>
	<c:if test="${softwarePage.codice == 'TT'}">	
		<div class="parametriDiv">
			<div class="etichetta">
				<div>Modulo: </div>
			</div>
			<div class="parametro">
				<div>${softwarePage.descrizione} i FrontOffice</div>
			</div>
		</div>
		<br/>		
	</c:if>
	<c:if test="${softwarePage.codice != 'TT'}">	
		<div class="parametriDiv">
			<div class="etichetta">
				<div>Modulo: </div>
			</div>
			<div class="parametro">
				<div>${softwarePage.descrizione} </div>
			</div>
		</div>
		<br/>		
	</c:if>
	<c:if test="${sw.codice!= softwarePage.codice}">	
	<br/>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="form.foArconfigurazione.software.title" />: ${sw.descrizione} i frontoffice</div>
			</div>
		</div>
		<br/>		
	</c:if>
	<dir class="clear"></dir>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="foArconfigurazione" />
    </jsp:include>
	<spring-form:form commandName="foArconfigurazione" name="inviodati">
	<table>
	<c:if test="${VERTICALIZZAZIONE_AREA_RISERVATA_REQUEST eq true}">
		<tr>
			<td><fmt:message key="form.foArconfigurazione.intestazioneDettaglioVisura" /></td>
			<td><spring-form:textarea id="intestazioneDettaglioVisura_id" path="intestazioneDettaglioVisura" cols="70" rows="3"/>
			<init:help idHelp="intestazioneDettaglioVisura_id_help" textKey="form.foArconfigurazione.intestazioneDettaglioVisura.help"/>
			<spring-form:errors path="intestazioneDettaglioVisura" cssClass="error"/></td>
		</tr>
	</c:if>	
		<tr>
			<td><fmt:message key="form.foArconfigurazione.msgInvioFallito" /></td>
			<td><spring-form:textarea id="msgInvioFallito_id" path="msgInvioFallito" cols="70" rows="3" />
			<init:help idHelp="msgInvioFallito_id_help" textKey="form.foArconfigurazione.msgInvioFallito.help"/>
			<spring-form:errors path="msgInvioFallito" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.foArconfigurazione.msgRegistrazioneCompletata" /></td>
			<td><spring-form:textarea id="msgRegistrazioneCompletata_id" path="msgRegistrazioneCompletata" cols="70" rows="3" />
			<init:help idHelp="msgRegistrazioneCompletata_id_help" textKey="form.foArconfigurazione.msgRegistrazioneCompletata.help"/>
			<spring-form:errors path="msgRegistrazioneCompletata" cssClass="error"/></td>
		</tr>
		<c:if test="${VERTICALIZZAZIONE_AREA_RISERVATA_REQUEST eq true}">		
		<tr>
			<td><fmt:message key="form.foArconfigurazione.nomeParametroLoginUrl" /></td>
			<td>				
				<spring-form:select  id="nomeParametroLoginUrl_id" path="nomeParametroLoginUrl">
					<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:option value="AUTHENTICATION_GATEWAY_FO_URL">AUTHENTICATION_GATEWAY_FO_URL</spring-form:option>
					<spring-form:option value="AUTHENTICATION_GATEWAY_FO_URLCIE">AUTHENTICATION_GATEWAY_FO_URLCIE</spring-form:option>
				</spring-form:select>				
			<init:help idHelp="nomeParametroLoginUrl_id_help" textKey="form.foArconfigurazione.nomeParametroLoginUrl.help"/>
			<spring-form:errors path="nomeParametroLoginUrl" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.foArconfigurazione.codiceoggettofirma" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.codiceoggettoFirma.id.codice}" />
	   				<jsp:param name="idComuneOggetto" value="${foArconfigurazione.codiceoggettoFirma.id.idcomune}" />
	   				<jsp:param name="codiceOggettoId" value="codiceoggettoFirma_id_codice" />
	   				<jsp:param name="nomefileId" value="codiceoggettoFirma_nomefile" />
    			</jsp:include>
    			
    			<spring-form:hidden path="codiceoggettoFirma.id.codice" id="codiceoggettoFirma_id_codice"/>
    			<spring-form:hidden path="codiceoggettoFirma.nomefile" id="codiceoggettoFirma_nomefile"/>
    			<spring-form:errors path="codiceoggettoFirma" cssClass="error"/>
			</td>
			<td><init:help idHelp="codiceoggettofirma_id_help" textKey="form.foArconfigurazione.codiceoggettofirma.help"/></td>
		</tr>
		<c:if test="${AREA_RISERVATA_JAVA_ATTIVA eq false}">
		<tr>
			<td><fmt:message key="form.foArconfigurazione.codiceoggettosottoscriz" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodiceSottoscriz" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.codiceoggettoSottoscriz.id.codice}" />
	   				<jsp:param name="idComuneOggetto" value="${foArconfigurazione.codiceoggettoSottoscriz.id.idcomune}" />
	   				<jsp:param name="codiceOggettoId" value="codiceoggettoSottoscriz_id_codice" />
	   				<jsp:param name="nomefileId" value="codiceoggettoSottoscriz_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="codiceoggettoSottoscriz.id.codice" id="codiceoggettoSottoscriz_id_codice"/>
    			<spring-form:hidden path="codiceoggettoSottoscriz.nomefile" id="codiceoggettoSottoscriz_nomefile"/>
    			<spring-form:errors path="codiceoggettoSottoscriz" cssClass="error"/>
			</td>
			<td><init:help idHelp="codiceoggettoSottoscriz_id_help" textKey="form.foArconfigurazione.codiceoggettosottoscriz.help"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.foArconfigurazione.codiceoggettoworkflow" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodiceWorkflow" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.oggettoWorkflow.id.codice}" />
	   				<jsp:param name="idComuneOggetto" value="${foArconfigurazione.codiceoggettoSottoscriz.id.idcomune}" />
	   				<jsp:param name="codiceOggettoId" value="codiceoggettoWorkflow_id_codice" />
	   				<jsp:param name="nomefileId" value="codiceoggettoWorkflow_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggettoWorkflow.id.codice" id="codiceoggettoWorkflow_id_codice"/>
    			<spring-form:hidden path="oggettoWorkflow.nomefile" id="codiceoggettoWorkflow_nomefile"/>
    			<spring-form:errors path="oggettoWorkflow" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.foArconfigurazione.codiceoggettomenuxml" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdMenuxml" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.oggettoMenuxml.id.codice}" />
	   				<jsp:param name="idComuneOggetto" value="${foArconfigurazione.oggettoMenuxml.id.idcomune}" />
	   				<jsp:param name="codiceOggettoId" value="codiceoggettoMenuxml_id_codice" />
	   				<jsp:param name="nomefileId" value="codiceoggettoMenuxml_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggettoMenuxml.id.codice" id="codiceoggettoMenuxml_id_codice"/>
    			<spring-form:hidden path="oggettoMenuxml.nomefile" id="codiceoggettoMenuxml_nomefile"/>
    			<spring-form:errors path="oggettoMenuxml" cssClass="error"/>
			</td>
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="form.foArconfigurazione.nomeConfigurazioneContenuti" /></td>
			<td><spring-form:input id="nomeConfigurazioneContenuti_id" path="nomeConfigurazioneContenuti" size="55" />
			<init:help idHelp="nomeConfigurazioneContenuti_id_help" textKey="form.foArconfigurazione.nomeConfigurazioneContenuti.help"/>
			<spring-form:errors path="nomeConfigurazioneContenuti" cssClass="error"/></td>
		</tr>
		<c:if test="${foArconfigurazione.id.software ne 'TT'}">
		<tr>
			<td>
				<fmt:message key="foArconfigurazione.label.dyn2modelli_t" />
			</td>
			<td>
			<jsp:include page="../includes/autocompletergenericoTT.jsp" >
				<jsp:param name="idElemento" value="dyn2Modellit" />		
				<jsp:param name="propertyPath" value="dyn2Modellit" />				
				<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
				<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
				<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />	
				<jsp:param name="titleKey" value="label.ricerca_modelli" />
				<jsp:param name="id_help" value="help_famiglia" />
			</jsp:include>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.flag_scheda_richiede_firma" /></td>
			<td><spring-form:checkbox id="flgSchedaEcRichiedefirma_id" path="flgSchedaEcRichiedefirma"/>
			<init:help idHelp="help_flgSchedaEcRichiedefirma" textKey="foArconfigurazione.help.label.flag_scheda_richiede_firma"/>
			<spring-form:errors path="flgSchedaEcRichiedefirma" cssClass="error"/></td>
		</tr>
		</c:if>
		<c:if test="${AREA_RISERVATA_JAVA_ATTIVA eq true}">
		<tr>
			<td><fmt:message key="label.workflow_domanda_on-line" /></td>
			<td>
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="foarjstepstestata_id" />				
					<jsp:param name="propertyPath" value="foArjStepsTestata" />			
					<jsp:param name="pathPropertyDescription" value="foArjStepsTestata.descrizione" />
					<jsp:param name="pathPropertyCode" value="foArjStepsTestata.id.codice" />
					<jsp:param name="autocompleterAjax" value="findFoArjStepsTestata.htm" />
					<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
				</jsp:include>			
			</td>
		</tr>
								
		</c:if>
	</c:if>
	</table>
	<c:if test="${softwarePage.codice !='TT'}">	
	<script type='text/javascript'>
		$('statoInizialeIstanza_id').focus();
	</script>	
	</c:if>
	<c:if test="${softwarePage.codice =='TT'}">	
	<script type='text/javascript'>
		if($('intestazioneDettaglioVisura_id')){
			$('intestazioneDettaglioVisura_id').focus();
		}
	</script>
	</c:if>
</spring-form:form>
</div>
<script type="text/javascript">
	var goToUrl = "../messaggicfg/list.htm";
	goToUrl = escape(goToUrl);
</script>
<div id="functions">
<ul>
	<c:if test="${dispatch=='create'}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${dispatch=='view'}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<c:if test="${softwarePage.codice == sw.codice}">	
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
	</c:if>
	<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'')"><fmt:message key="button.messaggicfg" /></a></li>
	<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+escape('../verticalizzazionibase/listparametribase.htm?codice=AREA_RISERVATA'),'')"><fmt:message key="button.altri_parametri" /></a></li>
		<c:if test="${AREA_RISERVATA_JAVA_ATTIVA eq true}">
			<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+escape('../foarjstepstestata/list.htm'),'')"><fmt:message key="button.workflow_domanda_on-line" /></a></li>
		</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
