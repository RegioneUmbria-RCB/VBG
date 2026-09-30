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
	<c:if test="${softwarePage.codice !='TT'}">	
		<tr>
			<td><fmt:message key="form.foArconfigurazione.statoInizialeIstanza" /></td>
			<td>
			<spring-form:select id="statoInizialeIstanza_id" path="statoInizialeIstanza.id.codicestato"  >
				<spring-form:option value="" ></spring-form:option>
				<spring-form:options items="${statiistanzaList}" itemLabel="stato" itemValue="id.codicestato"/>
			</spring-form:select>
			<init:help idHelp="statoInizialeIstanza_id_help" textKey="form.foArconfigurazione.statoInizialeIstanza.help"/>
			<spring-form:errors path="statoInizialeIstanza" cssClass="error"/></td>
		</tr>
	</c:if>
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
			<spring-form:errors path="nomeParametroLoginUrl" cssClass="error"/>
			<c:if test="${not empty urlAuthOv }">
				<div class="error_header">
					Attenzione! E' stata configurata la regola AREA_RISERVATA.URL_AUTHENTICATION_OVERRIDE che sovrascrive la configurazione del seguente parametro.
					Il valore del parametro è [ ${urlAuthOv} ]
				</div> 
			</c:if>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.foArconfigurazione.codiceoggettofirma" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.codiceoggettoFirma.id.codice}" />
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
			<td><fmt:message key="form.foArconfigurazione.oggettoRiepilogoSchede" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="RiepilogoSchede" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.oggettoRiepilogoSchede.id.codice}" />
	   				<jsp:param name="codiceOggettoId" value="oggettoRiepilogoSchede_id_codice" />
	   				<jsp:param name="nomefileId" value="oggettoRiepilogoSchede_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="oggettoRiepilogoSchede.id.codice" id="oggettoRiepilogoSchede_id_codice"/>
    			<spring-form:hidden path="oggettoRiepilogoSchede.nomefile" id="oggettoRiepilogoSchede_nomefile"/>
    			<spring-form:errors path="oggettoRiepilogoSchede" cssClass="error"/>
			</td>
			<td><init:help idHelp="oggettoRiepilogoSchede_id_help" textKey="form.foArconfigurazione.oggettoRiepilogoSchede.help"/></td>
		</tr>
		
		<tr>
			<td><fmt:message key="form.foArconfigurazione.codiceoggettoworkflow" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="oggettoIdCodiceWorkflow" />
	   				<jsp:param name="codiceOggetto" value="${foArconfigurazione.oggettoWorkflow.id.codice}" />
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
		
		<tr>
		    <td>
				<fmt:message key="label.dimensione_massima" />
	        </td>	
			<td>
				<spring-form:input id="dimensioneMassima_id" path="dimensioneMassima" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberInt(this);"/> Kb
				<spring-form:errors path="dimensioneMassima" cssClass="error"/>
			</td>
		</tr>
		
		<tr>
		    <td>
				<fmt:message key="label.estensioni_ammesse" />
			</td>
			<td>
				<spring-form:input path="estensioniAmmesse" size="40" />
				<init:help idHelp="help_foEstensioniAmmesse" textKey="help.fo_estensioni_ammesse"/>
				<spring-form:errors path="estensioniAmmesse" cssClass="error"/>
		    </td>
		</tr>	
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
	
	function togglecomuniesclusi(e) {
		
		disableFunctions();	
		jQuery('#messaggioErrore').hide();
		var codicecomune = e.value;
		var checkato = e.checked;			
		var jhqrPr = jQuery.ajax({
			url: '${pageContext.request.contextPath}/foarconfigurazione/ajaxToggleComuniEsclusi.htm?codicecomune='+codicecomune+'&checkato='+checkato,
			context: document.body,
			cache: false,
			dataType: "html",
			success: function(data, textStatus, jqXHR){						
				  if(textStatus=='success'){
					  console.log('Comune: '+codicecomune);					  	
					  enableFunctions();
				  }						  
			},
			error: gestisciErrore					
		});
	}
	
	function gestisciErrore(jqXHR, textStatus, errorThrown){
 		 console.error([jqXHR, textStatus, errorThrown ]);
 		 jQuery('#messaggioErrore').html("<div class='error_header'>Si è verificato un errore di sistema. Riprovare in un secondo momento</div>");
		 jQuery('#messaggioErrore').show();		
		 enableFunctions();
 	}
	
	
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
		<li><a href="javascript:historySet('${_urlback}','../foformatidocumenti/list.htm','');"><fmt:message key="button.formati_documenti" /></a></li>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</br>
<c:if test="${softwarePage.codice !='TT'}">  
	<div id=messaggioErrore></div>
	<div id="tabellaComunidaescludere" class="divTable" >	
		<div class="divTableHeading"> 
			<fmt:message key="label.comuniassociatiesclusioni.title" />&nbsp
			<init:help idHelp="help_comuniesclusioni" textKey="help.comuniesclusioni"/>
		</div>
		
		<div class="divTableBody">
		 	<c:forEach var="comunidaescludere" items="${comuniAssociati}" varStatus="counter">
		 		<div id="riga_dettaglio" class="divTableRow">
			 		<div class="divTableCell">
			 			<label for="comunidaescludere_id${counter.index}">${comunidaescludere.comune.transientDescrizioneComune}</label>
			 		</div>		 		
			 		<c:set var="_checked" value="" />			 			 		
					<input type="hidden" value="${comunidaescludere.comune.codicecomune}"/>													
					<c:forEach var="d" items="${comuniAssociatiEsclusioni}">				
						<c:if test="${d.comuniassociati.comune.codicecomune eq comunidaescludere.comune.codicecomune}">
							<c:set var="_checked" value="checked" />
						</c:if>							
					</c:forEach> 
						
			 		<div class="divTableCell">			 		
						<input type="checkbox" id="comunidaescludere_id_${counter.index}" name="comunedaescludere_${comunidaescludere.comune.codicecomune}" value="${comunidaescludere.comune.codicecomune}" ${_checked} onclick="togglecomuniesclusi(this)" />
					</div>
				</div> 				
			</c:forEach>			
		</div>
	</div>
</c:if>
	
	<style>	
		.divTable{
			display: table;
			width: 20%;
			margin-left: 4px;
   			margin-top: 16px;
		}
		.divTableRow {
			display: table-row;
		}
	
		.divTableCell, .divTableHead {			
			display: table-cell;
			padding: 3px 10px;
		}
		.divTableHeading {
			background-color: #EEE;
			display: table-caption;
			font-weight: bold;
			font-size: 13px; 
		}
		.divTableFoot {
			background-color: #EEE;
			display: table-footer-group;
			font-weight: bold;
		}
		.divTableBody {
			display: table-row-group;
		}
	
	</style>


</body>
</html>
