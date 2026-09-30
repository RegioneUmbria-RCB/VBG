<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>

<%@page import="it.gruppoinit.pal.gp.core.domain.Tipimovimento"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.TipimovimentoCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimento.title" />
		</c:if> 
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimento.title" />
		</c:if>
	</title>
	<style>
	   .checkbox-label {
	       line-height: 2em;
	       vertical-align: top;
	   }
	</style>
	<script type="text/javascript">
        vbg.ready(() => {

        	document.getElementById('movimento_id').focus();
            
            // gestisce la visualizzazione iniziale dei campi che possono essere
            // nascosti
            // flag ggproroga
            if(document.getElementById('flagProroga_id').checked)
            {
            	document.getElementById('gg_proroga_id').style.display = '';
            }
            // flag tipologia esito
            if(document.getElementById('tipologiaesito_id').value==1 || document.getElementById('tipologiaesito_id').value==2)
            {
            	document.getElementById('campo_invio_mail_id').style.display = '';
            	document.getElementById('campo_tipologia_esito_id').style.display = '';
            }
            // flag tipologia registro
            if(document.getElementById('flagRegistro_id').checked)
            {
            	document.getElementById('campo_tipologia_registro_id').style.display = '';
            }
            
            const popup = document.getElementById('popup-soggetti-movimento');
            const tipisoggettoIdHidden = document.getElementById('tipisoggetto_id_hidden');
            const tipisoggettoDescrizione = document.getElementById('tipisoggetto_id_id');
            const cmdAggiungiSoggetto = document.getElementById('cmdAggiungiSoggetto');
            const tabellaTipiSoggetto = document.querySelector('#tabellaTipiSoggetto');
            const bodyTabellaTipiSoggetto = tabellaTipiSoggetto.querySelector('tbody');
            const idTipoMovimento = document.getElementById('tipomovimento_id').value;
            
            // Data una riga (TR) collega l'handler per l'eliminazione al bottone elimina.
            // Viene utilizzato alla prima apertura dell popup e quando viene aggiunta una nuova riga
            const collegaHandlerEliminazioneARiga = (tr) => {
                
                if(tr.dataset.handlerEliminazioneCollegato) {
                    return;
                }
                
                tr.querySelector('a').addEventListener('click', async (e) => {
                    const id = e.target.dataset.id;
                    e.preventDefault();
                    
                    try
                    {
                        const postParams = { elimina: { id } };
                            
                        const response = await post('../tipimovtipisoggetto/jsonElimina.htm', postParams);
                        
                        tr.parentElement.removeChild(tr);        
                        e.preventDefault();           
                    } catch(error) {                                    
                        alert(error);
                    } finally{                                  
                        aggiornaStatoVisualizzazioneTabella();
                    }
                });
                
                tr.dataset.handlerEliminazioneCollegato = true;
            };
            
            // Aggiorna la visualizzazione della tabella dei soggetti configurati.
            // Se non sono presenti righe la tabella viene nascosta, altrimenti viene visualizzata
            // Al termine della verifica genera l'evento "numero-righe-aggiornato"
            const aggiornaStatoVisualizzazioneTabella = () => {
                
                const numeroRighe = bodyTabellaTipiSoggetto.querySelectorAll('tr').length;
                
                if (numeroRighe === 0) {
                    tabellaTipiSoggetto.style.display = 'none';
                } else {
                    tabellaTipiSoggetto.style.display = 'table';
                }
                
                popup.dispatchEvent(new CustomEvent('numero-righe-aggiornato', { detail: {numeroRighe} }));
            };
            
            
            // Resetta lo stato dei campi di input dell'autocomplete
            const svuotaCampiForm = () => {
                tipisoggettoIdHidden.value = '';
                tipisoggettoDescrizione.value = '';
            };
            
            
            // Inizializzazione dei campi nel popup (da richiamare sempre all'apertura del popup)
            popup.addEventListener('shown', () => {
                svuotaCampiForm();
                
                aggiornaStatoVisualizzazioneTabella();  
                bodyTabellaTipiSoggetto.querySelectorAll('tr').forEach((tr) => collegaHandlerEliminazioneARiga(tr) );
            });
            
            
            // Effettua il post di una serie di dati in formato json verso la risorsa all'
            // url passato. Se lo stato della risposta ricevuto è diverso da 200
            // solleva un'eccezione
            const post = async (url, postParams) => {
                
                try
                {
                    vbg.mostraModalCaricamento();
                    
                    console.log(`Invio dei dati tramite POST all'indirizzo \${url}:`, postParams);
                    
                    const response = await fetch(url, {
                        method: 'POST',
                        headers: {'Content-Type': 'application/json'},
                        body: JSON.stringify(postParams)
                    });    
                    
                    if (response.status !== 200) {
                        const msg = await response.json();
                        throw msg.error;
                    }                           
                    
                    return await response.json();
                } finally {
                    vbg.nascondiModalCaricamento();
                }
            };
            
            // Handler del click sul bottone aggiungi soggetto
            // Crea un nuovo soggetto che può effettuare il movimento corrente
            cmdAggiungiSoggetto.addEventListener('click', async (e) => {
            
                e.preventDefault();
                
                const idTipoSoggetto = tipisoggettoIdHidden.value;
                const descrizione = tipisoggettoDescrizione.value;
                
                if (!idTipoSoggetto) {
                    return;
                }
                
                try
                {
                    const response = await post('../tipimovtipisoggetto/jsonAggiungi.htm', {
                                                    aggiungi: {
                                                        idTipoMovimento,
                                                        idTipoSoggetto                          
                                                    }
                                                });

                    const nuovoId = response.id;                
                    
                    let tr = document.createElement('tr');
                    tr.innerHTML = `
                          <td>\${descrizione}</td>
                          <td>
                            <a href='#' data-id='\${nuovoId}'>
                              <i class="fa fa-trash" aria-hidden="true"></i> 
                              Elimina
                            </a>
                          </td>`;
                          
                    collegaHandlerEliminazioneARiga(tr);
                    bodyTabellaTipiSoggetto.append(tr);
                    aggiornaStatoVisualizzazioneTabella();
                } catch(error) {
                    console.log(error);
                    alert(error);
                } finally {
                    svuotaCampiForm();
                }                           
            });
            
            // Aggiornamento del numero di righe configurate nel bottone "soggetti che possono effettuare il movimento"
            const cmdApriPopupTipiSoggetto = document.getElementById('cmdApriPopupTipiSoggetto');
            const testoOriginale = `<fmt:message key="tipimovimento.label.timov_tipisoggetto.title" />`;
            
            popup.addEventListener('numero-righe-aggiornato', (e) => {
                cmdApriPopupTipiSoggetto.innerText = `\${testoOriginale} (\${e.detail.numeroRighe})`;
            });
         
            const fosoggettiesterni_id = document.getElementById('fosoggettiesterni_id');
            if (fosoggettiesterni_id.value !=='1') {
                document.querySelectorAll('.sezione-soggetti-esterni').forEach((item) => {
                    item.style.display = 'none';                                    
                });
            }
        });
        
        // gestisce la visualizzazione dei campi invio mail ed esito proroga
        function viewHideEmailAndEsito(id){
            if(id.value==1 || id.value==2 ){
            	document.getElementById('campo_invio_mail_id').style.display = '';
            	document.getElementById('campo_tipologia_esito_id').style.display = '';
            }else{
            	document.getElementById('campo_invio_mail_id').style.display = 'none';
            	document.getElementById('campo_tipologia_esito_id').style.display = 'none';
            	document.getElementById('flagEnmail_id').checked=false;
            	document.getElementById('flagEnmostra_id').checked=false;
            }
        }
        
        // Gestisce la visualizzazione del campo ggProroga
        function viewHideGGproroga(id){
            if(id.checked){
            	document.getElementById('gg_proroga_id').style.display = '';
            }else{
            	document.getElementById('gg_proroga_id').style.display = 'none';
            	document.getElementById("ggproroga_id").value=0;
            }       
        }
        
        // Gestisce la visualizzazione del campo ggProroga
        function viewHideTipologiaregistri(id){
            if(id.checked){
            	document.getElementById('campo_tipologia_registro_id').style.display = '';
            }else{
            	document.getElementById('campo_tipologia_registro_id').style.display = 'none';
            	document.getElementById('tipologiaregistri_id').options[0].selected = true;
            }   
        }
        // verifica che se un movimento è di sospenzione
        // non potra essere di interruzione o proroga
        function Verificaintegrazione()
        {
            if( document.getElementById('flagProroga_id').checked || 
            	document.getElementById('flagInterruzione_id').checked || 
            	document.getElementById('flagFinesospinterr_id').checked ){

            	alert('<fmt:message key="tipimovimento.alert.no_sospenzione" />');
            	document.getElementById('flagRichiestaintegrazione_id').checked=false;
            }
        }
        // verifica che se un movimento interruzione
        // non potra essere di sospenzione o proroga
        function Verificainterruzione()
        {
            if(document.getElementById('flagProroga_id').checked || 
               document.getElementById('flagRichiestaintegrazione_id').checked  || 
               document.getElementById('flagFinesospinterr_id').checked){
                
            	alert('<fmt:message key="tipimovimento.alert.no_interruzione" />');
            	document.getElementById('flagInterruzione_id').checked=false;
            }
        }
        // verifica che se un movimento è di proroga
        // non potra essere di sospenzione o interruzione
        function Verificaproroga()
        {
            if(document.getElementById('flagInterruzione_id').checked || 
               document.getElementById('flagRichiestaintegrazione_id').checked  || 
               document.getElementById('flagFinesospinterr_id').checked){
                
            	alert('<fmt:message key="tipimovimento.alert.no_proroga" />');
            	document.getElementById('flagProroga_id').checked=false;
            }
        }
        // verifica che il tipo movimento non possa anche essere di interruzione/proroga/sospensione
        function Verificachiusuraevento()
        {
            if(document.getElementById('flagProroga_id').checked || 
               document.getElementById('flagInterruzione_id').checked || 
               document.getElementById('flagRichiestaintegrazione_id').checked){
                
            	alert('<fmt:message key="tipimovimento.alert.no_flagFinesospinterr" />');
            	document.getElementById('flagFinesospinterr_id').checked=false;
            }
        }
        // verifica che se un movimento è operante non potra essere
        // non operante
        function Verificaoperante()
        {
            if(document.getElementById('flagOperante_id').checked && document.getElementById('flagNonoperante_id').checked ){
                
            	alert('<fmt:message key="tipimovimento.alert.operante" />');
            	document.getElementById('flagOperante_id').checked=false;
            	document.getElementById('flagNonoperante_id').checked=false;
            }
        }
        function disableEl(elem,id){
            var valSel=elem[elem.selectedIndex].value;
            if(valSel){
            	document.getElementById(id).value = '';
            }
        }
    </script>
</head>
<body>
<%
String  GGproroga="display:none;";
String  invioMaailAndMostraEsito="display:none;";
String  tipologiaregistri="display:none;";
%>
	<span class="titoloPagina">
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimento.title" />
		</c:if> 
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimento.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../tipimovimento/view" />
    </jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipimovimento" />
		    </jsp:include>
			<c:choose>
				<c:when test="${tipimovimento.entity.flagDisabilitato eq true}">
					<br class="clear" />					
					<div id="disabilitato_status_msg" class="alertLine">
			    		<b><fmt:message key="label.record_disabilitato"/></b>
			    	</div>
			    	<br class="clear" />
				</c:when>
			</c:choose>
			<div id="form" class="vbg-form">
				<fieldset class="collassabile" data-collassato="false">
	                <legend><fmt:message key="tipimovimento.label.dati_generali" /></legend>
	                <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
		                <div class="form-group">
		                    <label><fmt:message key="tipimovimento.label.codice" /></label>
	                        <input type="text" name="_entity.id.tipomovimento" value="${software}" readonly="readonly" size="3"/>
	                        <spring-form:input id="tipomovimento_id" path="entity.id.tipomovimento" size="8" maxlength="6" />
	                        <spring-form:errors path="entity.id.tipomovimento" cssClass="error"/>
		                </div>
	                </c:if>
	                <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
	                    <div class="form-group">
	                        <label><fmt:message key="tipimovimento.label.codice" /></label>
	                        <spring-form:input id="tipomovimento_id" path="entity.id.tipomovimento" size="10" readonly="true" />
	                    </div>
	                </c:if>
	                <div class="form-group">
	                    <label><fmt:message key="tipimovimento.label.movimento"/></label>
	                    <spring-form:input id="movimento_id" path="entity.movimento" size="70"/>
	                    <spring-form:errors path="entity.movimento" cssClass="error"/>
	                </div>
	                <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_richiesta_integrazione" />">
	                    <label><fmt:message key="tipimovimento.label.flag_richiesta_integrazione"/></label>
	                    <spring-form:checkbox id="flagRichiestaintegrazione_id" path="entity.flagRichiestaintegrazione" onclick="Verificaintegrazione();"/>
	                    <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_richiesta_integrazione" /></span>
	                    <spring-form:errors path="entity.flagRichiestaintegrazione" cssClass="error"/>
	                </div>
	                <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_interruzione" />">
	                    <label><fmt:message key="tipimovimento.label.flag_interruzione"/></label>
	                    <spring-form:checkbox id="flagInterruzione_id" path="entity.flagInterruzione" onclick="Verificainterruzione();"/>
	                    <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_interruzione" /></span>
	                    <spring-form:errors path="entity.flagInterruzione" cssClass="error"/>
	                </div>
	                <div class="form-group">
	                    <label><fmt:message key="tipimovimento.label.flag_proroga"/></label>
	                    <spring-form:checkbox id="flagProroga_id" path="entity.flagProroga"  onclick="Verificaproroga();viewHideGGproroga(this);"/>
	                    <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_proroga"/></span>
	                    <spring-form:errors path="entity.flagProroga" cssClass="error"/>
	                </div>
	                <div class="form-group" id="gg_proroga_id" style="<%=GGproroga%>">
	                    <label><fmt:message key="tipimovimento.label.descrizione_gg_proroga"/></label>
	                    <spring-form:input id="ggproroga_id" path="entity.ggproroga" size="5" onchange="checkNumberValue(this);javascript:isPositiveNumber(this)"/>
	                    <spring-form:errors path="entity.ggproroga" cssClass="error"/>
	                </div>
	                <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_fine_sospensione_interruzione" />">
	                    <label><fmt:message key="tipimovimento.label.flag_fine_sospensione_interruzione"/></label>
                        <spring-form:checkbox id="flagFinesospinterr_id" path="entity.flagFinesospinterr" onclick="Verificachiusuraevento();"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_fine_sospensione_interruzione" /></span>
                        <spring-form:errors path="entity.flagFinesospinterr" cssClass="error"/>
	                </div>
	                <div class="form-group">
	                    <label><fmt:message key="tipimovimento.label.statoistanza"/></label>
                        <spring-form:select id="statoistanza_id"  path="entity.statoistanza.id.codicestato">
                            <spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
                            <spring-form:options items="${statiistanzaList}" itemLabel="stato" itemValue="id.codicestato" />
                        </spring-form:select>
                        <init:help idHelp="help_statoistanza_id" textKey="tipimovimento.label.statoistanza.help" />
                        <spring-form:errors path="entity.statoistanza" cssClass="error"/>
	                </div>
	                <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_operante" />">
	                    <label><fmt:message key="tipimovimento.label.flag_operante"/></label>
                        <spring-form:checkbox id="flagOperante_id" path="entity.flagOperante" onclick="Verificaoperante();"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_operante" /></span>
                        <spring-form:errors path="entity.flagOperante" cssClass="error"/>
	                </div>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_nonoperante" />">
                        <label><fmt:message key="tipimovimento.label.flag_nonoperante"/></label>
                        <spring-form:checkbox id="flagNonoperante_id" path="entity.flagNonoperante" onclick="Verificaoperante();"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_nonoperante" /></span>
                        <spring-form:errors path="entity.flagNonoperante" cssClass="error"/>
                    </div>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_conferenza_servizi" />">
                        <label><fmt:message key="tipimovimento.label.flag_conferanza_servizi"/></label>
                        <spring-form:checkbox id="flagCds_id" path="entity.flagCds"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_conferanza_servizi" /></span>
                        <spring-form:errors path="entity.flagCds" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.tipologiaesito" /></label>
                        <spring-form:select  id="tipologiaesito_id"  path="entity.tipologiaesito" onchange="viewHideEmailAndEsito(this);"  >
                            <spring-form:option value="0" ><fmt:message key="label.non_previsto" /></spring-form:option>
                            <spring-form:option value="2" ><fmt:message key="label.positivo" /></spring-form:option>
                             <spring-form:option value="1"><fmt:message key="label.negativo" /></spring-form:option>
                        </spring-form:select>               
                        <spring-form:errors path="entity.tipologiaesito" cssClass="error"/>
                    </div>
                    <div class="form-group" id="campo_invio_mail_id" style="<%=invioMaailAndMostraEsito%>">
                        <label>&nbsp;</label>
                        <spring-form:checkbox id="flagEnmail_id" path="entity.flagEnmail" />
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_invio_mail" /></span>
                        <spring-form:errors path="entity.flagEnmail" cssClass="error"/>
                    </div>
                    <div class="form-group" id="campo_tipologia_esito_id" style="<%=invioMaailAndMostraEsito%>" title="<fmt:message key="tipimovimento.label.spiegazione_flag_mostra_esito" />">
                        <label>&nbsp;</label>
                        <spring-form:checkbox id="flagEnmostra_id" path="entity.flagEnmostra" />
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_mostra_esito" /></span>
                        <spring-form:errors path="entity.flagEnmostra" cssClass="error"/>
                    </div>
                    <c:if test="${entity.tipimovimento.software.codice !='TT'}">
	                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_registro" />">
	                        <label><fmt:message key="tipimovimento.label.flag_registro"/></label>
                            <spring-form:checkbox id="flagRegistro_id" path="entity.flagRegistro" onclick="viewHideTipologiaregistri(this);" />
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_registro" /></span>
                            <spring-form:errors path="entity.flagRegistro" cssClass="error"/>
	                    </div>
	                    <div class="form-group" id="campo_tipologia_registro_id" style="<%=tipologiaregistri%>">
	                        <label>&nbsp;</label>
                            <spring-form:select  id="tipologiaregistri_id"  path="entity.tipologiaregistri.id.codice"   >
                                <spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
                                <spring-form:options items="${tipologiaregistriList}" itemLabel="trDescrizione" itemValue="id.codice"></spring-form:options>
                            </spring-form:select>   
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_tipologiaregistri" /></span>         
                            <spring-form:errors path="entity.tipologiaregistri" cssClass="error"/>
	                    </div>
	                </c:if>
	                <c:if test="${isAmministrazioniInterneEsistono eq true}">
	                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_no_ammi_interna" />">
	                        <label><fmt:message key="tipimovimento.label.flag_no_amm_interna"/></label>
                            <spring-form:checkbox id="flagNoamminterna_id" path="entity.flagNoamminterna"/>
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_no_amm_interna" /></span>
                            <spring-form:errors path="entity.flagNoamminterna" cssClass="error"/>
	                    </div>
	                </c:if>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_usa_dal_protocollo" />">
                        <label><fmt:message key="tipimovimento.label.flag_usa_dal_protocollo"/></label>
                        <spring-form:checkbox id="flagUsadalprotocollo_id" path="entity.flagUsadalprotocollo"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_usa_dal_protocollo" /></span>
                        <spring-form:errors path="entity.flagUsadalprotocollo" cssClass="error"/>
                    </div>
                    <c:if test="${isVerticalizzazioneSTCAttiva eq true}">
	                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_stc" />">
	                        <label><fmt:message key="tipimovimento.label.flag_stc"/></label>
                            <spring-form:checkbox id="flagStc_id" path="entity.flagStc"/>
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_stc" /></span>
                            <spring-form:errors path="entity.flagStc" cssClass="error"/>
	                    </div>
	                    <div class="form-group">
	                        <label><fmt:message key="tipimovimento.label.flag_disdavisionare"/></label>
	                        <spring-form:checkbox id="flagDisdavisionare_id" path="entity.flagDisdavisionare"/>
	                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.flag_disdavisionare.help" /></span>
	                        <spring-form:errors path="entity.flagDisdavisionare" cssClass="error"/>
	                    </div>
	                </c:if>
	                <c:if test="${isVerticalizzazioneINFOCAMERAAttiva eq true}">
	                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_camcom" />">
	                        <label><fmt:message key="tipimovimento.label.flag_camcom"/></label>
                            <spring-form:checkbox id="flagCamcom_id" path="entity.flagCamcom"/>
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_camcom" /></span>
                            <spring-form:errors path="entity.flagCamcom" cssClass="error"/>
	                    </div>
	                </c:if>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_riporta_dati_prot_istanza"/></label>
                        <spring-form:checkbox id="flagRiportaProtIstanza_id" path="entity.flagRiportaProtIstanza"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.flag_riporta_dati_prot_istanza.help" /></span>
                        <spring-form:errors path="entity.flagRiportaProtIstanza" cssClass="error"/>
                    </div>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_ric_tel" />">
                        <label><fmt:message key="tipimovimento.label.flag_ric_tel"/></label>
                        <spring-form:select id="flagRicTel_id" path="entity.mailtipoByFkTipimovricTelMailtipo.id.codice" onchange="disableEl(this,'flagComTel_id')">
                            <spring-form:option value="">...</spring-form:option>
                            <spring-form:options items="${listaMailtipo }" itemLabel="descrizione" itemValue="id.codice" />
                        </spring-form:select>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_ric_tel" /></span>
                        <spring-form:errors path="entity.mailtipoByFkTipimovricTelMailtipo.id.codice" cssClass="error"/>
                    </div>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_com_tel" />">
                        <label><fmt:message key="tipimovimento.label.flag_com_tel"/></label>
                        <spring-form:select id="flagComTel_id" path="entity.mailtipoByFkTipimovcomTelMailtipo.id.codice" onchange="disableEl(this,'flagRicTel_id')">
                            <spring-form:option value="">...</spring-form:option>
                            <spring-form:options items="${listaMailtipo }" itemLabel="descrizione" itemValue="id.codice" />
                        </spring-form:select>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_com_tel" /></span>
                        <spring-form:errors path="entity.mailtipoByFkTipimovcomTelMailtipo.id.codice" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.lettera_per_com_tel_o_ric_tel"/></label>
                        <%
                          String  swSettato1="display:none;";
                          String  swTT1="display:inline;";
                        %>
                        <script type="text/javascript">
                           function tuttiSw(){
                               if(document.getElementById('id_flag1').checked){           
                            	   document.getElementById('letteretipo_id1').style.display="inline";
                            	   document.getElementById('letteretipo_id2').style.display="none";
                                }else{
                                	document.getElementById('letteretipo_id1').style.display="none";
                                	document.getElementById('letteretipo_id2').style.display="inline";
                                }
                           }
                        </script>
                        <div id="letteretipo_id1" style="<%=swSettato1%>">
                            <spring-form:input id="lettere_tipo_id1" path="entity.letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
                            <init:autocompleter methodAjax='findLettereTipo.htm?codicesoftware=TT'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id1" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter>
                        </div>
                        <div id="letteretipo_id2" style="<%=swTT1%>">
                            <spring-form:input id="lettere_tipo_id2" path="entity.letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
                            <init:autocompleter methodAjax='findLettereTipo.htm'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id2" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter>
                        </div>
                        <spring-form:errors path="entity.letteretipo" cssClass="error"/> 
                        <spring-form:hidden id="lettere_tipo_hidden" path="entity.letteretipo.id.codice"/>
                        <input type="checkbox" id="id_flag1" onclick="tuttiSw();"/>
                        <init:help idHelp="help1" textKey="help.letteretipo_archivi_base"/> 
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_lettera_per_com_tel_o_ric_tel" /></span> 
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_allegati_link"/></label>
                        <spring-form:checkbox id="flgInvialinkallmail_id" path="entity.flgInvialinkallmail" />
                        <fmt:message key="help.flag_allegati_link"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_allegati_link_protocollo"/></label>
                        <spring-form:checkbox id="flgProtocollalinkall_id" path="entity.flgProtocollalinkall" />
                        <fmt:message key="help.flag_allegati_link_protocollo"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.letteratipo_linkdoc" /></label>
	                    <jsp:include page="../includes/autocompletergenericoTT.jsp" >
	                        <jsp:param name="idElemento" value="letteraTipoAllegati" />     
	                        <jsp:param name="propertyPath" value="entity.letteraTipoAllegati" />                
	                        <jsp:param name="pathPropertyDescription" value="entity.letteraTipoAllegati.descrizione" />
	                        <jsp:param name="pathPropertyCode" value="entity.letteraTipoAllegati.id.codice" />
	                        <jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />  
	                        <jsp:param name="titleKey" value="label.ricerca_tipo_lettera" />
	                        <jsp:param name="id_help" value="help_letteraTipoAllegati" />
	                        <jsp:param name="help" value="help.search_archivi_base" />
	                    </jsp:include>
	                    <fmt:message key="help.letteratipo_linkdoc"/>
                    </div>
                    <c:if test="${isVerticalizzazionePROTOCOLLOAttiva}">
	                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_mailtipoOggProt" />">
	                        <label><fmt:message key="tipimovimento.label.mailtipoOggProt"/></label>
                            <spring-form:select id="mailtipoOggProt_id" path="entity.mailtipoOggProt.id.codice" >
                                <spring-form:option value="">...</spring-form:option>
                                <spring-form:options items="${listaMailtipoOggProt}" itemLabel="descrizione" itemValue="id.codice" />
                            </spring-form:select>
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_mailtipoOggProt" /></span>
                            <spring-form:errors path="entity.mailtipoOggProt.id.codice" cssClass="error"/>
	                    </div>
	                </c:if>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_accedi_schede" /></label>
                        <spring-form:checkbox id="flagAccediSchede_id" path="entity.flagAccediSchede"/>
                        <span class="checkbox-label"><fmt:message key="help.flag_accedi_schede" /></span>
                    </div>
				</fieldset>
				<c:if test="${isVerticalizzazioneAUTORIZACCESSIAttiva}">
					<fieldset class="collassabile">
					    <legend><fmt:message key="label.proroga_preavviso_rinnovo" /></legend>
	                    <div class="form-group">
	                        <label><fmt:message key="tipimovimento.label.flag_Autoriz_Proroga"/></label>
                            <spring-form:checkbox id="flagAutorizProroga_id" path="entity.flagAutorizProroga" />                            
                            <span class="checkbox-label"><fmt:message key="help.flag_autoriz_proroga" /></span>
	                    </div>
	                    <div class="form-group">
	                        <label><fmt:message key="tipimovimento.label.flag_Autoriz_Preavv"/></label>
                            <spring-form:checkbox id="flagAutorizPreavv_id" path="entity.flagAutorizPreavv" />                          
                            <span class="checkbox-label"><fmt:message key="help.flag_autoriz_preavv" /></span>
	                    </div>
	                    <div class="form-group">
	                        <label><fmt:message key="tipimovimento.label.flag_Autoriz_Rinnovo"/></label>
                            <spring-form:checkbox id="flagAutorizRinnovo_id" path="entity.flagAutorizRinnovo" />                        
                            <span class="checkbox-label"><fmt:message key="help.flag_autoriz_rinnovo" /></span>
	                    </div>
	                    <div class="form-group">
	                        <label><fmt:message key="tipimovimento.label.flag_Autoriz_Modifica"/></label>
                            <spring-form:checkbox id="flagAutorizModifica_id" path="entity.flagAutorizModifica" />                          
                            <span class="checkbox-label"><fmt:message key="help.flag_autoriz_modifica" /></span>
	                    </div>
					</fieldset>
                </c:if>
				<fieldset class="collassabile">
				    <legend><fmt:message key="label.dati_frontoffice" /></legend>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_pubblica_movimento" />">
                        <label><fmt:message key="tipimovimento.label.flag_pubblica_movimento"/></label>
                        <spring-form:checkbox id="flagPubblicamovimento_id" path="entity.flagPubblicamovimento"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_pubblica_movimento" /></span>
                        <spring-form:errors path="entity.flagPubblicamovimento" cssClass="error"/>
                    </div>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_flag_pubblica_parere" />">
                        <label><fmt:message key="tipimovimento.label.flag_pubblica_parere"/></label>
                        <spring-form:checkbox id="flagPubblicaparere_id" path="entity.flagPubblicaparere"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_pubblica_parere" /></span>
                        <spring-form:errors path="entity.flagPubblicaparere" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_pubblica_allegati"/></label>
                        <spring-form:checkbox id="flagPubblicaallegati_id" path="entity.flagPubblicaallegati"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_flag_pubblica_allegati" /></span>
                        <spring-form:errors path="entity.flagPubblicaallegati" cssClass="error"/>
                    </div>
                    <div class="form-group" title="<fmt:message key="tipimovimento.label.spiegazione_fo_soggettiesterni" />">
                        <label><fmt:message key="tipimovimento.label.fo_soggettiesterni" /></label>
                        <spring-form:select  id="fosoggettiesterni_id"  path="entity.foSoggettiesterni.codice" cssStyle="margin-right: 10px;" >
                            <spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
                            <spring-form:options items="${soggettiesterniList}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
                        </spring-form:select> 
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.descrizione_fo_soggettiesterni" /></span>
                        <spring-form:errors path="entity.foSoggettiesterni" cssClass="error" />
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_sostituzione_documentale"/></label>
                        <spring-form:select id="flagSostDocumentale_id" path="entity.flagSostDocumentale" cssStyle="margin-right: 10px;">
                            <spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
                            <spring-form:option value="0">Non permette sost.doc</spring-form:option>
                            <spring-form:option value="1">Permette sost.doc per documenti non validi</spring-form:option>
                            <spring-form:option value="2">Permette sost.doc per documenti non validi o da verificare</spring-form:option>
                        </spring-form:select>                       
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.flag_sostituzione_documentale.help" /></span>
                        <spring-form:errors path="entity.flagSostDocumentale" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_integr_check_firma"/></label>
                        <spring-form:checkbox id="flagIntegrCheckFirma_id" path="entity.flagIntegrCheckFirma"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.flag_integr_check_firma.help" /></span>
                        <spring-form:errors path="entity.flagIntegrCheckFirma" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.flag_pubblica_schede"/></label>
                        <spring-form:checkbox id="flagPubblSchede_id" path="entity.flagPubblSchede"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.flag_pubblica_schede.help" /></span>
                        <spring-form:errors path="entity.flagPubblSchede" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.aggiorna_riepilogo_domanda" /></label>
                        <spring-form:checkbox id="flagAggiornaRiepilogo_id" path="entity.flagAggiornaRiepilogo"/>
                        <span class="checkbox-label"><fmt:message key="tipimovimento.label.aggiorna_riepilogo_domanda.help" /></span>                 
                        <spring-form:errors path="entity.flagAggiornaRiepilogo" cssClass="error"/>
                    </div>
                    <c:if  test="${isVerticalizzazioneSitAttiva eq true }">
                        <div class="form-group">
                            <label><fmt:message key="tipimovimento.label.flag_fo_richiama_sit" /></label>
                            <spring-form:checkbox id="flagFoRichiamaSit_id" path="entity.flagFoRichiamaSit"/>
                            <span class="checkbox-label"><fmt:message key="tipimovimento.label.flag_fo_richiama_sit.help" /></span>
                            <spring-form:errors path="entity.flagFoRichiamaSit" cssClass="error"/>
                        </div>
                    </c:if> 
				</fieldset>
				<fieldset class="collassabile sezione-soggetti-esterni">
				    <legend><fmt:message key="tipimovimento.label.timov_tipisoggetto.title" /></legend>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.chi_puo_effettuare_movimento" /></label>
                        <spring-form:select  id="flag_soggetti_movimento"  path="entity.flagSoggettiMovimento" >
                            <spring-form:option value="false" ><fmt:message key="tipimovimento.chi_puo_effettuare_movimento.option.richiedente_tecnico_soggetti" /></spring-form:option>
                            <spring-form:option value="true" ><fmt:message key="tipimovimento.chi_puo_effettuare_movimento.option.solo_soggetti" /></spring-form:option>
                        </spring-form:select> 
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="tipimovimento.label.altri_soggetti_movimento" /></label>
                        <!-- Inizio della gestione del popup per la gestione dei soggetti che possono eseguire il movimento  -->
                        <a class="btn btn-secondary" href="#" id="cmdApriPopupTipiSoggetto" data-role="open-vbg-modal" data-target-modal-id="popup-soggetti-movimento">
                            <fmt:message key="tipimovimento.label.timov_tipisoggetto.button.lista_soggetti" />
                            (${fn:length(tipimovimento.soggettiChePossonoEffettuareIlMovimento)})
                        </a>
                        <div id="popup-soggetti-movimento" class='vbg-modal'>
                            <div class='vbg-modal-body'>
                                <h1>
                                    <fmt:message key="tipimovimento.label.timov_tipisoggetto.button.lista_soggetti" />
                                    <!--Soggetti che possono effettuare il movimento-->
                                </h1>
                            
                                <div class="vbg-form">
                                    <div class="form-group">
                                        <label><fmt:message key="tipimovimento.label.timov_tipisoggetto.label_cerca" /></label>
                                        <jsp:include page="../includes/autocompletergenerico.jsp" >
                                            <jsp:param name="idElemento" value="tipisoggetto_id" />
                                            <jsp:param name="propertyPath" value="tipoSoggettoAutocomplete" />
                                            <jsp:param name="pathPropertyCode" value="tipoSoggettoAutocomplete.id" />
                                            <jsp:param name="pathPropertyDescription" value="tipoSoggettoAutocomplete.descrizione" />
                                            <jsp:param name="autocompleterAjax" value="findTipisoggetto.htm" />                         
                                            <jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
                                        </jsp:include>
                                    </div>
                                    <div id="cmdAggiungiSoggetto" class="btn btn-primary">
                                        <fmt:message key="tipimovimento.label.timov_tipisoggetto.aggiungi" />
                                    </div>
                                    
                                    <table id="tabellaTipiSoggetto" class="vbg-table">
                                        <thead>
                                            <tr>
                                                <th><fmt:message key="tipimovimento.label.timov_tipisoggetto.label_cerca" /></th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
    
                                        <tbody>
                                            <c:forEach items="${tipimovimento.soggettiChePossonoEffettuareIlMovimento}" var="soggetto">
                                                <tr>
                                                    <td>${soggetto.descrizione}</td>
                                                    <td>
                                                        <a href='#' data-id='${soggetto.id}'>
                                                          <i class="fa fa-trash" aria-hidden="true"></i> 
                                                          Elimina
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    
                                    </table>
                                </div>
                            
                                <div class="vbg-modal-footer">
                                    <a href="#" data-role='toggle-popup' class="btn btn-secondary">
                                        <fmt:message key="button.back" />
                                    </a>
                                </div>
                            </div>                        
    
                        </div>
                        <!-- Fine della gestione del popup per la gestione dei soggetti che possono eseguire il movimento  -->
                    </div>
                    <div class="form-group">
                        <label></label>
                    </div>
				</fieldset>
				<!-- TABELLA DEI CONTROMOVIMENTI  -->
                <!-- START -->
                <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
                    <fieldset>
					    <legend><fmt:message key="tipimovimento.label.contro_movimenti_associati.tilte" /></legend>
						<div class="form-group">
	                        <table class="vbg-table">
	                            <thead>
                                    <tr>
		                                <th><fmt:message key="tipimovimento.label.codice" /></th>
							            <th><fmt:message key="label.obbligatorio" /></th>
							            <th><fmt:message key="label.comportamento" /></th>
							            <th><fmt:message key="label.procedura" /></th>
							            <th><fmt:message key="tipimovimento.label.amministrazione_effettua_mov" /></th>
							            <th><fmt:message key="tipimovimento.label.amministrazione_effettua_contromov" /></th>
							            <th><fmt:message key="label.tempi_attesa" /></th>
                                    </tr>
						        </thead>
	                            <tbody>
	                                <c:forEach items="${listaTipiContromovimenti}" var="contro_mov_var">
	                                    <tr>
	                                        <td>
	                                            <a href="javascript:doHref('viewContromovimento.htm?codicecontromovimento=${contro_mov_var.id.codice}','')">${contro_mov_var.tipocontromovimento.id.tipomovimento}</a>-
	                                            <a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/view.htm?codice=${contro_mov_var.tipocontromovimento.id.tipomovimento}','')">${contro_mov_var.tipocontromovimento.movimento}</a>
	                                        </td>
							                <td>
	                                            <c:if test="${contro_mov_var.flagbase==true}">
	                                                <fmt:message key="label.si" />
	                                            </c:if>
	                                            <c:if test="${contro_mov_var.flagbase==false}">
	                                                <fmt:message key="label.no" />
	                                            </c:if>
							                </td>
							                <td>
	                                            <fmt:message key="label.effettua" />:<br />
	                                            <c:if test="${contro_mov_var.soloseesitonegativo==0 || contro_mov_var.soloseesitonegativo==null}">
	                                                <fmt:message key="label.sempre" />
	                                            </c:if>
	                                            <c:if test="${contro_mov_var.soloseesitonegativo==2}">
	                                                <fmt:message key="tipimovimento.label.se_positivo" />
	                                            </c:if>
	                                            <c:if test="${contro_mov_var.soloseesitonegativo==1}">
	                                                <fmt:message key="tipimovimento.label.se_negativo" />
	                                            </c:if>
							                </td>
							                <td>
	                                            <c:if test="${contro_mov_var.tipiprocedure.id.codice != null}">
	                                                ${contro_mov_var.tipiprocedure.procedura}
	                                            </c:if>
	                                            <c:if test="${contro_mov_var.tipiprocedure.id.codice == null}">
	                                                <fmt:message key="label.tutte_procedure" />
	                                            </c:if>
							                </td>
							                <td>
	                                            ${contro_mov_var.amministrazioniTipiMovimento.amministrazione}
							                </td>
							                <td>
	                                            ${contro_mov_var.amministrazioniTipiContromovimento.amministrazione}
							                </td>
							                <td>
							                  <a class="btn btn-secomdary" title="<fmt:message key="label.visualizza"/>" onclick="javascript:creaTempiDiRisposta(${contro_mov_var.id.codice})" href="javascript:void(0)">
							                      <fmt:message key="button.visualizza" />
							                  </a>
							                </td>
	                                    </tr>
	                                </c:forEach>
	                            </tbody>
						   </table>
						</div>
				        <jsp:include page="../includes/pannelloSceltaSoftware.jsp"/>
				        <script type='text/javascript'>
				        <%
				        String software=ORMHelper.getSoftware();
				        pageContext.setAttribute("software", software);
				        %>
				        function creaTempiDiRisposta(codice)
				        {
				              var url = "../tipimovimento/createTempirispostaContromovimento.htm?codicecontromovimento=" + codice;
				              if('${software}'=='<%=WebConstants.SOFTWARE_TT%>')
				              {
				                  pannelloSceltaSoftware(url,'doHref',true,'');
				              }else
				              {
				                 doHref('createTempirispostaContromovimento.htm?codicecontromovimento='+ codice,'');
				              }
				        }
				        </script>
				        <div class="form-button">
                            <a class="btn btn-primary" href="javascript:doHref('createContromovimento.htm?codicemovimento=${tipimovimento.entity.id.tipomovimento}','')">
                                <fmt:message key="button.new" />
                            </a>
				        </div>
                    </fieldset>
                </c:if>
                <!-- END -->
                <!-- TABELLA DEI MOVIMETI DI CUI E' CONTROMOVIMENTO  -->
                <!-- START -->
                <c:if test="${fn:length(listaTipiContromovimenti1)>0}">
                    <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
	                    <fieldset>
	                        <legend><fmt:message key="tipimovimento.label.movimenti_contro_movimenti.title" /></legend>
	                        <div class="form-group">
                                <table class="vbg-table">
                                    <thead>
                                        <tr>
                                            <th><fmt:message key="tipimovimento.label.movimento" /></th>
                                            <th><fmt:message key="label.procedura" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listaTipiContromovimenti1}" var="mov_var">
	                                        <tr>
	                                            <td>
                                                    <a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/view.htm?codice=${mov_var.tipomovimento.id.tipomovimento}','')">${mov_var.tipomovimento.id.tipomovimento} </a>&nbsp;-&nbsp;${mov_var.tipomovimento.movimento}
	                                            </td>
	                                            <td>
	                                                <c:if test="${mov_var.tipiprocedure.id.codice != null}">
	                                                    ${mov_var.tipiprocedure.procedura}
                                                    </c:if>
                                                    <c:if test="${mov_var.tipiprocedure.id.codice == null}">
                                                        <fmt:message key="label.tutte_procedure" />
                                                    </c:if>                                                            
	                                            </td>
	                                        </tr>
                                        </c:forEach>
                                    </tbody>
	                            </table>
	                        </div>
	                    </fieldset>
                    </c:if>
                </c:if>
                <!-- END -->
			</div>
			<div class="form-button">
			    <%  
			    String codiceTipoMovimento = "";
			    TipimovimentoCommand command = (TipimovimentoCommand)request.getAttribute("tipimovimento");
			    if(null!=command){
			        Tipimovimento entity = command.getEntity();
			        codiceTipoMovimento = entity.getId().getTipomovimento();
			    }
			    String uriBack = "../tipimovimento/view.htm?codice=" + codiceTipoMovimento;
			    String uriModelli = BackofficeNETConstants.getURL_DYN2_TIPIMOV_MODELLI() + "?tipomovimento=" +codiceTipoMovimento;
			    String urlModelli = BackofficeNETConstants.getUrlTo(request,uriModelli,uriBack,(String)session.getAttribute(WebConstants.SOFTWARE),false);
			    pageContext.setAttribute("dyn_url_modelli",urlModelli);
			    %>
			    <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
                    <a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			    </c:if>
			    <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
					<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
					<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
					<a class="btn btn-primary" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Ftipimovimento%2Flistdocumentitipo.htm?tipimovimento.codice=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.documenti_tipo" /></a>
					<a class="btn btn-primary" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Ftipimovimento%2Flistoneri.htm?tipimovimento.codice=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.gestioni_oneri" /></a>
					<!-- Lasciare in dot net -->
					<a class="btn btn-primary" href="javascript:historySet('${_urlback}','..%2Ftipimovimento/listmodelli.htm?codicemovimento=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.modelli" /></a>
					<a class="btn btn-primary" href="javascript:historySet('${_urlback}','..%2Ftipimovimentocomunicazioni/list.htm?codiceTipomov=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.configura_comunicazioni" /></a>
                    <c:if test="${isVerticalizzazioneSTCAttiva eq true && tipimovimento.entity.flagStc eq true }">
                        <a class="btn btn-primary" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fparametristc/list.htm?tipimovimento.idtipomovimento=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.configura_notifiche_stc" /></a>
                    </c:if>
                    <c:choose>
                        <c:when test="${tipimovimento.entity.flagDisabilitato eq true}">
                            <a class="btn btn-primary" href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.abilita"><fmt:param value="Tipi movimento"/></fmt:message>',document.inviodati)"><fmt:message key="button.abilita" /></a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-primary" href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.disabilita"><fmt:param value="Tipi movimento" /></fmt:message>',document.inviodati)"><fmt:message key="button.disabilita" /></a>
                        </c:otherwise>
                    </c:choose>
	                <c:if test="${isVerticalizzazioneRABBITAttiva }">
                        <a class="btn btn-primary" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Ftipimovimentorabbit/list.htm?tipomovimento=${ tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.configura_rabbit"/></a>
	                </c:if>
			    </c:if>
			    <a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
			</div>
        </spring-form:form>
	</div>
  </body>
</html>