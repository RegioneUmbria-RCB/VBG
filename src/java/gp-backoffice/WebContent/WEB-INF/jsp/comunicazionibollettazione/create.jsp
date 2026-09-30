<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>
        <fmt:message key="label.comunicazione.bollettazione.title" />
    </title>
    <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-multi-upload.js?<%=vJS %>" defer></script>
	<style>
		.valore-metadato{
			width: 600px;
		}
	</style>
</head>
<body>
    <span class="titoloPagina">
        <fmt:message key="label.comunicazione.bollettazione.title" />
    </span>
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="create" />
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
        <jsp:param name="path" value="../comunicazionibollettazione/view" />
    </jsp:include>
    <div id="subcontent">
        <div class="parametriDiv">
            <div class="etichetta">
                <div>
                    <fmt:message key="label.descrizione" />:
                </div>
            </div>
            <div class="parametro">
                <div>${comunicazioniBollettazioneCommand.gestTestata.descrizione}</div>
            </div>
        </div>
        <br class="clear" />
        <spring-form:form commandName="comunicazioniBollettazioneCommand" name="inviodati"
            enctype="multipart/form-data">
            <jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName" value="comunicazioniBollettazioneCommand" />
            </jsp:include>

            <div id="form" class="vbg-form">
                <fieldset>
                    <legend><fmt:message key="label.dati_principali" /></legend>
                    
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.descrizione" />
                        </label>
                        <spring-form:input id="descrizione_id" path="descrizione" size="70" />
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.escludinomail" />
                        </label>
                        <spring-form:checkbox id="flgEscludiNoMail_id"
                            path="configuraParametriMailCommand.escludiNoMail" />
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.posizioni_non_pagate" />
                        </label>
                        <spring-form:checkbox id="posizioni_non_pagate_id" path="soloPosizioniDebitorieNonPagate" />
                    </div>
                </fieldset>
                <fieldset>
                	<legend><fmt:message key="label.comunicazione.bollettazione.allegatifissi" /></legend>
                	<vbg-multi-upload id="allegatifissiId" path-name="allegatiFissi" context-path='${pageContext.request.contextPath}'>
                		<c:forEach items="${comunicazioniBollettazioneCommand.allegatiFissi}" var="allegato">
                			<input type="hidden" value="${allegato}"></input>
                		</c:forEach>
                	</vbg-multi-upload>
                </fieldset>

                <fieldset>
                    <legend><fmt:message key="label.comunicazione.bollettazione.documentitipo" /></legend>
					<jsp:include page="../includes/autocompletergenericoTT.jsp">
                          <jsp:param name="idElemento" value="letteratiposearchId" />
                          <jsp:param name="propertyPath" value="letteratiposearch" />
                          <jsp:param name="pathPropertyDescription" value="letteratiposearch.descrizione" />
                          <jsp:param name="pathPropertyCode" value="letteratiposearch.id.codice" />
                          <jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
                          <jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
                          <jsp:param name="id_help" value="help_doc_tipo" />
                          <jsp:param name="onchangeCallback" value="onChangeCallLT(this,'letteretipo_hidden')" />
                          <jsp:param name="afterUpdateElement" value="getCodiceLetteraAllegatoFissi" />
					</jsp:include>
					<div>
                        <table class="vbg-table" id="tabella_allegati_compilabili">
                            <thead>
                                <tr class="header">
                                    <th>
                                        <fmt:message key="label.documento_tipo" />
                                    </th>
                                    <th>
                                        <fmt:message key="label.azioni" />
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="allegati_compilabili_body">
                                <c:forEach items="${comunicazioniBollettazioneCommand.allegaticompilabili}"
                                    var="allegato" varStatus="idx">

                                    <tr>
                                        <td><label>${allegato.descrizione }</label> <input
                                                id="allegati_compilabili_id_${idx.index}"
                                                path="allegaticompilabili[${idx.index }].id.codice"
                                                value="${allegato.id.codice }" type="hidden" /></td>
                                        <td>
                                           <br />
                                        </td>                                      
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
				</fieldset>
                <fieldset>
                    <legend><fmt:message key="label.allegati" /></legend>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.allegaavvpagamento" />
                        </label>
                        <spring-form:checkbox id="flgAllegaavvpagamento_id" path="allegaAvvisiPagamento" />
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.convertiPDF" />
                        </label>
                        <spring-form:checkbox id="flgConvertiPDF_id" path="convertiPDF" />
                    </div>
                </fieldset>
                <fieldset>
                    <legend>
                        <fmt:message key="label.metti_alla_firma" />
                    </legend>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.firmatario" />
                        </label>
                        <jsp:include page="../includes/autocompletergenerico.jsp">
                            <jsp:param name="idElemento" value="responsabile" />
                            <jsp:param name="propertyPath" value="firmatario" />
                            <jsp:param name="pathPropertyDescription" value="firmatario.responsabile" />
                            <jsp:param name="pathPropertyCode" value="firmatario.id.codice" />
                            <jsp:param name="autocompleterAjax" value="findResponsabili.htm" />
                            <jsp:param name="titleKey" value="label.ricerca_responsabile" />
                            <jsp:param name="autocompleterInputSize" value="65" />
                            <jsp:param name="afterUpdateElement" value="updateFirmatari" />
                        </jsp:include>
                        <i id="spinner" class="fa fa-circle-o-notch fa-spin" style="display:none"></i>
                    </div>
                    <div>
                        <table class="vbg-table" id="tabella_riga_firmatari">
                            <thead>
                                <tr class="header">
                                    <th>
                                        <fmt:message key="label.codice" />
                                    </th>
                                    <th>
                                        <fmt:message key="label.firmatari" />
                                    </th>
                                    <th>
                                        <fmt:message key="label.azioni" />
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="righe_firmatari_body">
                                <c:forEach items="${comunicazioniBollettazioneCommand.firmatari}" var="firmatario"
                                    varStatus="idx">

                                    <tr>
                                        <td>
                                            <label>${firmatario.id.codice }</label>
                                            <input id="firmatario_${idx.index}"
                                                path="firmatari[${idx.index }].id.codice"
                                                value="${firmatario.id.codice }" type="hidden" />
                                        </td>
                                        <td><label>${firmatario.responsabile }</label>
                                            <input id="firmatario_responsabile_${idx.index}"
                                                path="firmatari[${idx.index }].responsabile"
                                                value="${firmatario.responsabile }" type="hidden" />
                                        </td>
                                        <td><a class="eliminaRiga" style="float: none;"
                                                onclick="eliminaFirmatario(event,${firmatario.id.codice })"
                                                href="javascript:void(0)" title="<fmt:message key="
                                                label.elimina" />"><label>
                                                <fmt:message key="label.elimina.image" />
                                            </label></a></td>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </fieldset>
                <c:if test="${vert_prot_attivo == true}">
                    <fieldset class="collassabile" data-collassato="false">
                        <legend>
                            <fmt:message key="label.protocollazione" />
                        </legend>
                        <div class="form-group">
                            <label>
                                <fmt:message key="label.comunicazione.bollettazione.prevedeprotocollazione" />
                            </label>
                            <spring-form:checkbox id="prevedeprotocollazione_id"
                                path="protocollaParametriCommand.protocolla" />
                        </div>
                        <div class="form-group">
                            <label>
                                <fmt:message key="label.comunicazione.bollettazione.mailSoggProtocollo" />
                            </label>
                            <jsp:include page="../includes/autocompletergenericoTT.jsp">
                                <jsp:param name="idElemento" value="mailtipoId" />
                                <jsp:param name="propertyPath" value="protocollaParametriCommand.mailtipo" />
                                <jsp:param name="pathPropertyDescription"
                                    value="protocollaParametriCommand.mailtipo.descrizione" />
                                <jsp:param name="pathPropertyCode"
                                    value="protocollaParametriCommand.mailtipo.id.codice" />
                                <jsp:param name="autocompleterAjax" value="findMailtipo.htm?ambito=P" />
                                <jsp:param name="titleKey" value="label.ricerca_mail_tipo" />
                                <jsp:param name="id_help" value="help_mailtipo" />
                            </jsp:include>
                        </div>
                        <div class="form-group">
							<c:forEach
                            	items="${comunicazioniBollettazioneCommand.protocollaParametriCommand.parametriPerEnte}"
                                var="par" varStatus="idx">
								<fieldset class="collassabile" data-collassato="false">
	                				<legend>${par.comune.descrizione}</legend>
	                				<div class="form-group">
	                					<label><fmt:message key="label.classifica" /></label>
										<c:choose>
											<c:when test="${ !empty par.listaClassifiche }">
												<select
													name="protocollaParametriCommand.parametriPerEnte[${idx.index }].classifica" class="prot_cls">
													<option></option>
	                                                <c:forEach
	                                                	items="${ par.listaClassifiche}"
	                                                    var="classifica">
	                                                    <option value="${ classifica.codice}">${classifica.descrizione }</option>
													</c:forEach>
												</select>
	                                            <input class="codice_classifica_protocollo"  disabled="disabled" type="hidden" value="${par.classifica }"/>
											</c:when>
	                                        <c:otherwise>
												<input
													name="protocollaParametriCommand.parametriPerEnte[${idx.index }].classifica"
	                                                value="${par.classifica }" />
											</c:otherwise>
										</c:choose>
	                				</div>
	                				<div class="form-group">
	                					<label>
	                						<fmt:message key="label.comunicazione.bollettazione.tipodocumento" />
	                					</label>
										<c:choose>
											<c:when test="${ !empty par.listaTipiDocumento }">
	                                        	<select
	                                            	name="protocollaParametriCommand.parametriPerEnte[${idx.index }].tipodocumento" class="prot_tp">
	                                                <option></option>
	                                                <c:forEach items="${ par.listaTipiDocumento}" var="tipodocumento">
	                                                	<option value="${ tipodocumento.codice}">${tipodocumento.descrizione }</option>
													</c:forEach>
												</select>
	                                            <input class="codice_tipodocumeto_protocollo"  disabled="disabled" type="hidden" value="${par.tipodocumento }"/>
											</c:when>
	                                        <c:otherwise>
	                                        	<input name="protocollaParametriCommand.parametriPerEnte[${idx.index }].tipodocumento" />
	                                        </c:otherwise>
										</c:choose>
	                				</div>
	                				<div class="form-group">
	                					<label><fmt:message key="label.amministrazione" /></label>
	               						<select
											name="protocollaParametriCommand.parametriPerEnte[${idx.index }].ammMittente.id" class="prot_amm">
	                                           <option></option>
	                                           <c:forEach items="${ par.listaAmministrazioni}" var="amministrazione">
												<option value="${ amministrazione.id}">${amministrazione.descrizione }</option>
											</c:forEach>
										</select>
										<input class="codice_amm_protocollo" disabled="disabled" type="hidden" value="${par.ammMittente.id }"/>
	                				</div>
									<c:forEach items="${par.metadati}" var="metadato" varStatus="idxMetadato">
											<div class="form-group">
                                				<label><fmt:message key="metadato.bollettazione.${fn:toLowerCase(metadato.chiave)}" /></label>
                                				<spring:bind path="protocollaParametriCommand.parametriPerEnte[${idx.index }].metadati[${idxMetadato.index}].chiave">
                                					<input type="hidden" name="${status.expression}" value="${status.value}" />
                                				</spring:bind>
                                				<spring:bind  path="protocollaParametriCommand.parametriPerEnte[${idx.index }].metadati[${idxMetadato.index}].valore">
                                					<input type="text" name="${status.expression}" value="${status.value}" class="valore-metadato" />
                                				</spring:bind>
                                			</div>

                            		</c:forEach>
	                			</fieldset>
	                		</c:forEach>
                        </div>
                    </fieldset>
                </c:if>
                <fieldset>
                    <legend>
                        <fmt:message key="label.email" />
                    </legend>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.accountMail" />
                        </label>
                        <spring-form:select id="select_mittente_email_id"
                            path="configuraParametriMailCommand.senderAccount.id.codice">
                            <c:forEach items="${listMailConfig}" var="current">
                                <spring-form:option value="${current.id.codice}">${current.descrizioneLunga}
                                </spring-form:option>
                            </c:forEach>
                        </spring-form:select>
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.templateMailTipo" />
                        </label>
                        <jsp:include page="../includes/autocompletergenericoTT.jsp">
                            <jsp:param name="idElemento" value="template_mail_tipo_id" />
                            <jsp:param name="propertyPath" value="configuraParametriMailCommand.mailtipo" />
                            <jsp:param name="pathPropertyDescription"
                                value="configuraParametriMailCommand.mailtipo.descrizione" />
                            <jsp:param name="pathPropertyCode"
                                value="configuraParametriMailCommand.mailtipo.id.codice" />
                            <jsp:param name="autocompleterAjax" value="findMailtipo.htm?ambito=M" />
                            <jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
                            <jsp:param name="id_help" value="help_doc_tipo" />
                            <jsp:param name="afterUpdateElement" value="getMailTipoScelta" />
                            <jsp:param name="autocompleterInputSize" value="50" />
                        </jsp:include>
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.bollettazione.scelta_mail_anagrafe"></fmt:message>
                        </label>
                        <select id="scelta_mailtipo_id" name="configuraParametriMailCommand.sceltaMailAnagrafe.codice">
                            <option></option>
                            <c:forEach items="${sceltaTipoMailAnagrafeList }" var="sceltaTipoMail">
                                <option value="${ sceltaTipoMail.codice}">${sceltaTipoMail.descrizione}</option>
                            </c:forEach>

                        </select>
                        <input id="scelta_mailtipo_val" type="hidden" disabled="disabled"
                            name="configuraParametriMailCommand.sceltaMailAnagrafe.codice" value="${comunicazioniBollettazioneCommand.configuraParametriMailCommand.sceltaMailAnagrafe.codice}" />
                    </div>
                </fieldset>
                <div style="margin-bottom: var(--default-padding);">
                
                    <a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)">
                        <fmt:message key="button.insert" />
                    </a>
                    <a class='btn btn-secondary' href="javascript:historyBack('')">
                        <fmt:message key="button.back" />
                    </a>
                </div>
            </div>
        </spring-form:form>
    </div>

		
    <vbg-modal id="vbg-modal-gestisci-lettera" >
		<div slot='body' class='vbg-modal-body'>
			<h1><fmt:message key="letteretipo.label.dettaglio_lettera.title" /></h1>
			<div class="vbg-form">
			</div>			
		</div>
		<div slot="footer" class="vbg-modal-footer">             
             <a href="#" data-role='toggle-popup' class="btn btn-secondary btnChiudi" >
                 <fmt:message key="button.back" />
             </a>
		</div>
	</vbg-modal>  

    <script type="text/javascript">

    /////////////////////////////////////////
    vbg.ready(() => {
    	
	    function inizializzaBottoneScegliFile(){
			
			document.querySelectorAll(".multi-upload>.btn-primary").forEach( cmd => {
				cmd.addEventListener('click', function(e) {
					alert('cliccato');
				});
			});
		}
	    
	    
	    inizializzaBottoneScegliFile();
    });
    /////////////////////////////////////////
        function editDocsfileIdCodice(id){
			location.href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId="+id;
		}
    
        function getMailTipoScelta(inputField, listItem) {
            document.getElementById('template_mail_tipo_id_hidden').value = listItem.id
        }
        
		async function _etichettaMetadato(helpContainer, metadato){
			
			try {
				helpContainer.textContent = '';
				
				chiave = 'metadato.bollettazione.' + metadato.toLowerCase();

				const postParams = { request: { alias: '<%= ORMHelper.getIdcomuneAlias()%>', software: '<%= ORMHelper.getSoftware()%>', etichette: chiave } };
				
				const response = await fetch('../services/rest/layout/testi', {
					method: 'POST',
				    headers: {
				    	'Accept': 'application/json',
				        'Content-Type': 'application/json',
					},
				    body: JSON.stringify(postParams)
				});
				
				if( response.status == 200){
					let etichette = await response.json();
					helpContainer.appendChild(document.createTextNode(etichette[0].response.testo));
				}
				
				
			} 
			catch(error){
				alert(error);	
			}
		}

        async function getCodiceLetteraAllegatoFissi(inputField, listItem) {
            document.getElementById('letteratiposearchId_hidden').value = listItem.id;
            const response = await fetch("../comunicazionibollettazione/ajaxAggiungiAllegatiCompilabili.htm?codiceLetteretipo=" + listItem.id, {
                method: "POST",
                cache: "no-cache",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            disableFunctions();
            let ris = await response.text();
            if (ris != "OK") {
                enableFunctions();
                document.getElementById(inputField.id).value = '';
            } else {
                let bodyTabella = document.getElementById('allegati_compilabili_body');
                let rigaAllegatiCompilabili = bodyTabella.insertRow();
                let cell0 = rigaAllegatiCompilabili.insertCell(0);
                cell0.innerHTML = '<label>' + listItem.textContent + '</label><input name="allegaticompilabili" value="' + listItem.id + '" type="hidden"/>';
                let cell1 = rigaAllegatiCompilabili.insertCell(1);
                let idx = rigaAllegatiCompilabili.rowIndex;
                cell1.innerHTML = '<td>'
                    + '<a style="float: none;" onclick="eliminaAllegatoCompilabile(event,' + listItem.id + ')" href="javascript:void(0)" title="<fmt:message key="label.elimina" />">'
                    + '<i class="fa fa-trash-o"></i>'
                    + '</a>'
                    + '<a style="float:none;" onclick="modificaAllegatoCompilabile(event,' + listItem.id + ')" href="javascript:void(0)" title="<fmt:message key="label.modifica" /> ">'
                    + '<i class="fa fa-edit"></i></a>'
                    + '</td>';
                enableFunctions();
                document.getElementById(inputField.id).value = '';
               
            }
        }
        
        async function modificaAllegatoCompilabile(event, codiceLetteraTipo){
        	
        	console.log(codiceLetteraTipo);
        	let body = document.querySelector('.vbg-modal-body');
        	window.vbg.mostraModalCaricamento();
        	 const response = await fetch("../comunicazionibollettazione/popolaPopupLettere.htm?codiceLetteretipo=" + codiceLetteraTipo, {
                 method: "GET",
                 cache: "no-cache",
                 headers: {
                     'Content-Type': 'application/text'
                 }
             });
             disableFunctions();
             let ris = await response.text();
             console.log(ris);
             if(ris!=''){
            	document.querySelector('.vbg-modal-body .vbg-form').innerHTML = ris; 
             	// Apro popup per la modifica del file
             	window.vbg.nascondiModalCaricamento();             	
             	document.querySelector('#vbg-modal-gestisci-lettera').open();
             }
             enableFunctions();			
        }
        
        document.querySelector('#vbg-modal-gestisci-lettera').querySelector('.btnChiudi').addEventListener('click',()=>{
        	document.querySelector('#vbg-modal-gestisci-lettera').close();
       	})
        
        async function eliminaAllegatoCompilabile(event, codiceLetteretipo) {
            const response = await fetch("../comunicazionibollettazione/ajaxRimuoviAllegatoCompilabile.htm?codiceLetteretipo=" + codiceLetteretipo, {
                method: "POST",
                cache: "no-cache",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            disableFunctions();
            let ris = await response.text();
            if (ris != "OK") {
                enableFunctions();
                

            }
            event.target.closest("tr").remove();
            enableFunctions();
        }
        
        async function updateFirmatari(inputField, listItem) {
            let firmatarioId = listItem.id;
            const response = await fetch("../comunicazionibollettazione/ajaxAggiungiFirmatario.htm?codiceFirmatario=" + firmatarioId, {
                method: "POST",
                cache: "no-cache",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            disableFunctions();
            let ris = await response.text();
            if (ris != "OK") {
                enableFunctions();
                document.getElementById(inputField.id).value = '';

            } else {

                let tabellaFirmatari = document.getElementById("tabella_riga_firmatari");
                let rigaFirmatari = document.getElementById("righe_firmatari_body").insertRow();
                let cell0 = rigaFirmatari.insertCell(0);
                cell0.innerHTML = '<lable>' + firmatarioId + '</lable><input name="firmatari.id.codice" value="' + firmatarioId + '" type="hidden"/>';
                let cell1 = rigaFirmatari.insertCell(1);
                cell1.innerHTML = '<lable>' + listItem.textContent + '</lable><input name="firmatari.responsabile" value="' + listItem.textContent + '" type="hidden"/>';
                let cell2 = rigaFirmatari.insertCell(2);
                let idx = rigaFirmatari.rowIndex;
                cell2.innerHTML = '<td>'
                    + '<a class="eliminaRiga" style="float: none;" onclick="eliminaFirmatario(event,' + firmatarioId + ')" href="javascript:void(0)" title="<fmt:message key="label.elimina" />">'
                    + '<label><fmt:message key="label.elimina.image" /></label>'
                    + '</a>' + '</td>';
                enableFunctions();
                document.getElementById(inputField.id).value = '';
                
            }

        }
        
        async function eliminaFirmatario(event, firmatarioId) {
            let tabellaFirmatari = document.getElementById("tabella_riga_firmatari");
            console.log(event.target.parentNode.parentNode.nodeName);

            const response = await fetch("../comunicazionibollettazione/ajaxRimuoviFirmatario.htm?codiceFirmatario=" + firmatarioId, {
                method: "POST",
                cache: "no-cache",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            disableFunctions();
            let ris = await response.text();
            if (ris != "OK") {
                enableFunctions();

            }
            event.target.closest("tr").remove();
            enableFunctions();
        }

        async function processaDatiProtocollazioneDaSalvare() {

            var array = [];

            let table = document.getElementById("tabella_parametri_protocollazione_body");
            for (let i = 0; i < table.rows.length; i++) {
                let row = table.rows[i]
                let obj = new Object();
                for (let j in row.cells) {
                    let col = row.cells[j];
                    let e1 = document.getElementById("protocollazione_classifica_id_" + i);
                    if (e1.nodeName === 'SELECT') {
                        obj.classifica = e1.options[e1.selectedIndex].value;
                    } else {
                        obj.classifica = e1.value;
                    }

                    obj.ammMittente = {};
                    let sel = document.getElementById("amministrazione_id_" + i)
                    obj.ammMittente.id = sel.value;
                    obj.ammMittente.descrizione = sel.options[sel.selectedIndex].text;
                    let e2 = document.getElementById("protocollazione_tipodocumento_id_" + i);
                    if (e2.nodeName === 'SELECT') {
                        obj.tipodocumento = e2.options[e2.selectedIndex].value;
                    } else {
                        obj.tipodocumento.descrizione = e2.value;
                    }

                    obj.comune = {};
                    obj.comune.codice = document.getElementById("comune_id_" + i).dataset.codice;
                    obj.comune.descrizione = document.getElementById("comune_id_" + i).textContent;
                }
                array.push(obj);
            }
            if (window.Prototype) {
                delete Object.prototype.toJSON;
                delete Array.prototype.toJSON;
                delete Hash.prototype.toJSON;
                delete String.prototype.toJSON;
            }
            var jObject = { "listParamprotoPerEnte": array };
            console.log(jObject);
            var res = JSON.stringify(jObject);
            console.log(res);
            const response = await fetch("../comunicazionibollettazione/ajaxSetParametriProtocollazione.htm", {
                method: "POST",
                cache: "no-cache",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: res
            });
            let ris = await response.text();
            if (ris === 'OK') {
                document.getElementById("popup").hide();

            }

        }

        let mailtipo = document.getElementById('scelta_mailtipo_id');
        mailtipo.addEventListener('change', (e) => {
            let mailtipodesc = document.getElementById('scelta_mailtipo_val');
            console.log(mailtipo);
            mailtipodesc.value = mailtipo[mailtipo.selectedIndex].text;
        });
        
        
        let sel = document.getElementById('scelta_mailtipo_id');
        let codiceMailScelta  = document.getElementById('scelta_mailtipo_val').value;
        for (var i = 0; i < sel.options.length; i++) {
        	let opt = sel.options[i];
        	if (opt.value === codiceMailScelta) {   
        		sel.selectedIndex=i;
			}
        }
        let tipiDocumento = document.getElementsByClassName('codice_tipodocumeto_protocollo');
        for (var i = 0; i < tipiDocumento.length; i++) {
        	let parent = tipiDocumento[i].parentElement;
        	let selTipiDocumento = parent.getElementsByClassName('prot_tp');
        	for (var j = 0; j < selTipiDocumento[0].options.length; j++) {
        			let opt = selTipiDocumento[0].options[j];
        			if (tipiDocumento[i].value === opt.value) {
    					selTipiDocumento[0].selectedIndex=j;
    				}
			}
		}
        
        let amm = document.getElementsByClassName('codice_amm_protocollo');
        for (var i = 0; i < amm.length; i++) {
        	let parent = amm[i].parentElement;
        	let selProtAmm = parent.getElementsByClassName('prot_amm') ;
        	for (var j = 0; j < selProtAmm[0].options.length; j++) {
        			let opt = selProtAmm[0].options[j];
        			if (amm[i].value === opt.value) {
        				selProtAmm[0].selectedIndex=j;
    				}
			}
			
		}
        	
        let cls = document.getElementsByClassName('codice_classifica_protocollo');
        for (var i = 0; i < cls.length; i++) {
        	let td = cls[i].closest('td');
        	let selProtAmm = td.getElementsByClassName('prot_cls') ;
        	for (var j = 0; j < selProtAmm[0].options.length; j++) {
        			let opt = selProtAmm[0].options[j];
        			if (cls[i].value === opt.value) {
        				selProtAmm[0].selectedIndex=j;
    				}
				
        		
				
			}
			
		}
				
    </script>
</body>

</html>