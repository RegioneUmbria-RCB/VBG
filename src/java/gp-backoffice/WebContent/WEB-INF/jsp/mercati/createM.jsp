<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

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
        <fmt:message key="label.comunicazione.commissione.title" />
    </title>
    <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-multi-upload.js?<%=vJS %>" defer></script>
</head>
<body>
    <span class="titoloPagina">
        <fmt:message key="label.comunicazione.commissione.title" />
    </span>
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="create" />
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
        <jsp:param name="path" value="../comunicazionicommissioni/view" />
    </jsp:include>
    <div id="subcontent">
        <div class="parametriDiv">
            <div class="etichetta">
                <div>
                    <fmt:message key="label.descrizione" />:
                </div>
            </div>
            <div class="parametro">
                <div>${comunicazioniCommissioniCommand.commissione.descrizione}</div>
            </div>
        </div>
        <br class="clear" />
        <spring-form:form commandName="comunicazioniCommissioniCommand" name="inviodati"
            enctype="multipart/form-data">
            <jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName" value="comunicazioniCommissioniCommand" />
            </jsp:include>

            <div id="form" class="vbg-form">
                <c:set var="ONLY_APPIO_SELECTED" value="${comunicazioniCommissioniCommand.scegliAppioChckN and !comunicazioniCommissioniCommand.scegliMailChckN and !comunicazioniCommissioniCommand.protocollaParametriCommand.protocolla}" />
                <c:set var="ANAGRAFE_SELECTED" value="${comunicazioniCommissioniCommand.tipoInvioMercato eq 'anagrafe'}" />                
                <fieldset>
                    <legend><fmt:message key="label.dati_principali" /></legend>
                    
                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.descrizione" />
                        </label>
                        <spring-form:input id="descrizione_id" path="descrizione" size="70" />
                    </div>
                    
                    <div class="form-group">
                      <label for="scegliMailChck"> Mail o PEC </label>
	                  <spring-form:checkbox id="scegliMailChck" path="scegliMailChckN" onchange="mostraNascondiFieldSet(this,'mailfield');mostraNascondiTemplate(this);mostraNascondiAllegatiFirmatari();"/><br>
                      
                      <c:choose>
	                      <c:when test="${comunicazioniCommissioniCommand.codiciComuneUguale  }">
	                      	  <c:if test="${not empty appioservizilist  }">
	                      	  	<label for="scegliAppioChck"> App.IO </label>
			                    <spring-form:checkbox id="scegliAppioChck" path="scegliAppioChckN" onchange="mostraNascondiFieldSet(this,'appiofield');mostraNascondiTemplate(this);mostraNascondiAllegatiFirmatari();"/><br>
	                      	  </c:if>
			                  
	                      </c:when>
	                      <c:otherwise>
	                          <c:if test="${not empty appioservizilist  }">
	                      		<label for="scegliAppioChck"> App.IO </label>
		                        <spring-form:checkbox id="scegliAppioChck" path="scegliAppioChckN"  disabled="${not comunicazioniCommissioniCommand.codiciComuneUguale  }"/>
		                       <i class="fa fa-exclamation-triangle warning" aria-hidden="true"><fmt:message key="label.comunicazione.commissione.appio_errori.codici_comuni" /></i><br/>
		                     </c:if>
	                      </c:otherwise>
                      </c:choose>
                      
                    
	                  
	                  <c:if test="${vert_prot_attivo == true}">
	                  <label>
                                <fmt:message key="label.comunicazione.commissione.prevedeprotocollazione" />
                            </label>
                            <spring-form:checkbox id="prevedeprotocollazione_id"
                                path="protocollaParametriCommand.protocolla" onchange="mostraNascondiFieldSet(this,'protofield');mostraNascondiAllegatiFirmatari();"/>
                      </c:if>          
                    </div>            
					<br><br>
					<label>Tipo invio:</label><br> <label>
								<spring-form:radiobutton path="tipoInvioMercato" value="anagrafe" />
									Per anagrafe 
						</label> <br> <label> <spring-form:radiobutton path="tipoInvioMercato" value="autorizzazione" />
										Per singola autorizzazione </label><br><br>
										
					<label>Tipo anagrafica:</label><br> <label>
								<spring-form:radiobutton path="tipoAnagrafe" value="titolare" />
									Titolare
						</label> <br> <label> <spring-form:radiobutton path="tipoAnagrafe" value="occupante" />
										Occupante </label>
				</fieldset>
                <fieldset id="allegati_fissi_id" style="${ONLY_APPIO_SELECTED ? 'display:none;' : ''}">
                	<legend><fmt:message key="label.comunicazione.commissione.allegatifissi" /></legend>
                	<vbg-multi-upload id="allegatifissiId" path-name="allegatiFissi" context-path='${pageContext.request.contextPath}'>
                		<c:forEach items="${comunicazioniCommissioniCommand.allegatiFissi}" var="allegato">
                			<input type="hidden" value="${allegato}"></input>
                		</c:forEach>
                	</vbg-multi-upload>
                </fieldset>

                <fieldset id="documentitipo_id" style="${ONLY_APPIO_SELECTED or ANAGRAFE_SELECTED ? 'display:none;' : ''}">
                    <legend><fmt:message key="label.comunicazione.commissione.documentitipo" /></legend>
					<jsp:include page="../includes/autocompletergenericoTT.jsp">
                          <jsp:param name="idElemento" value="letteratiposearchId" />
                          <jsp:param name="propertyPath" value="letteratiposearch" />
                          <jsp:param name="pathPropertyDescription" value="letteratiposearch.descrizione" />
                          <jsp:param name="pathPropertyCode" value="letteratiposearch.id.codice" />
                          <jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
                          <jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
                          <jsp:param name="id_help" value="help_doc_tipo_uno" />
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
                                <c:forEach items="${comunicazioniCommissioniCommand.allegaticompilabili}"
                                    var="allegato" varStatus="idx">

                                    <tr>
                                        <td><label>${allegato.descrizione }</label></td>
                                        <td><a class="eliminaRiga" style="float: none;"
                                                onclick="eliminaAllegatoCompilabile(event,${allegato.id.codice})"
                                                href="javascript:void(0)" title="<fmt:message key="label.elimina" />"><label>
                                                <fmt:message key="label.elimina.image" />
                                            </label></a></td>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
				</fieldset>
                <fieldset id="convertipdf_id" style="${ONLY_APPIO_SELECTED or ANAGRAFE_SELECTED ? 'display:none;' : ''}">
                    <legend><fmt:message key="label.allegati" /></legend>                    
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.commissione.convertiPDF" />
                        </label>
                        <spring-form:checkbox id="flgConvertiPDF_id" path="convertiPDF" />
                    </div>
                </fieldset>
                <fieldset id="metti_alla_firma_id" style="${ONLY_APPIO_SELECTED ? 'display:none;' : ''}">
                    <legend>
                        <fmt:message key="label.metti_alla_firma" />
                    </legend>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.commissione.firmatario" />
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
                                <c:forEach items="${comunicazioniCommissioniCommand.firmatari}" var="firmatario"
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
                    <fieldset id="protofield" style="${comunicazioniCommissioniCommand.protocollaParametriCommand.protocolla ? '' : 'display:none;'}">
                        <legend>
                            <fmt:message key="label.protocollazione" />
                        </legend>
                        <div class="form-group">
                            <label>
                                <fmt:message key="label.comunicazione.commissione.mailSoggProtocollo" />
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
                                <jsp:param name="id_help" value="help_mail_tipo_due" />
                            </jsp:include>
                        </div>
                        <div>
                            <table width="100%" class="vbg-table">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Classifica</th>
                                        <th>Tipo documento</th>
                                        <th>Amministrazione</th>
                                    </tr>
                                </thead>
                                <tbody id="tabella_parametri_protocollazione_body">
                                    <c:forEach
                                        items="${comunicazioniCommissioniCommand.protocollaParametriCommand.parametriPerEnte}"
                                        var="par" varStatus="idx">
                                        <tr>
                                            <td>
                                                <label>${par.comune.descrizione }</label>
                                                <input
                                                    name="protocollaParametriCommand.parametriPerEnte[${idx.index }].comune.descrizione"
                                                    value="${par.comune.descrizione }" type="hidden" /> <input
                                                    type="hidden"
                                                    name="protocollaParametriCommand.parametriPerEnte[${idx.index }].comune.id"
                                                    value="${par.comune.codice }" />
                                            </td>
                                            <td>
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
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${ !empty par.listaTipiDocumento }">
                                                        <select
                                                            name="protocollaParametriCommand.parametriPerEnte[${idx.index }].tipodocumento" class="prot_tp">
                                                            <option></option>
                                                            <c:forEach items="${ par.listaTipiDocumento}"
                                                                var="tipodocumento">
                                                                <option value="${ tipodocumento.codice}">
                                                                    ${tipodocumento.descrizione }</option>
                                                            </c:forEach>

                                                        </select>
                                                        <input class="codice_tipodocumeto_protocollo"  disabled="disabled" type="hidden" value="${par.tipodocumento }"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input
                                                            name="protocollaParametriCommand.parametriPerEnte[${idx.index }].tipodocumento" />
                                                    </c:otherwise>

                                                </c:choose>
                                            </td>
                                            <td>
                                                <select
                                                    name="protocollaParametriCommand.parametriPerEnte[${idx.index }].ammMittente.id" class="prot_amm">
                                                    <option></option>
                                                    <c:forEach items="${ par.listaAmministrazioni}"
                                                        var="amministrazione">
                                                        <option value="${ amministrazione.id}">
                                                            ${amministrazione.descrizione }</option>
                                                    </c:forEach>

                                                </select>
                                                 <input class="codice_amm_protocollo" disabled="disabled" type="hidden" value="${par.ammMittente.id }"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </fieldset>
                </c:if>
                <fieldset id="templatefield" style="${(comunicazioniCommissioniCommand.scegliMailChckN or comunicazioniCommissioniCommand.scegliAppioChckN) ? '' : 'display:none;'}">
                  <legend>
                        <fmt:message key="label.massive.messaggiodausare" />
                    </legend>
					<div class="form-group">
						<label> <fmt:message key="label.massive.oggetto" /></label>
						<spring-form:input id="oggettomail_id" path="oggettoEmail"
							cssStyle="width:480px;" />
						<span class="help_image" id="aprisegnapostooggetto"
							onclick="mostraNascondiTooltip()"><label>help</label></span>
						<div id="segnapostoguida"
							style="display: none; position: absolute; border: 1px solid #deddcc; background-color: #ffffcc;">
							<jsp:include page="../includes/segnapostocommassmerc.jsp" />

						</div>
						<span id="aprisegnapostooggetto_d">Cliccare qui per
							aggiungere segnaposti all'oggetto</span>
					</div>
					<div class="form-group">
						<label> <fmt:message key="label.massive.corpo" />
						</label>
						<spring-form:textarea id="bodymail_id" path="bodyEmail" cols="70"
							rows="5" cssClass="in-fieldset" />
						<span class="help_image" id="aprisegnapostobody"
							onclick="mostraNascondiTooltipBody()"><label>help</label></span>
						<div id="segnapostoguidabody"
							style="display: none; margin-top: 30px; position: absolute; border: 1px solid #deddcc; background-color: #ffffcc;"">
							<jsp:include page="../includes/segnapostocommassmerc.jsp" />

						</div>
						<span id="aprisegnapostobody_d">Cliccare qui per aggiungere
							segnaposti al corpo</span>
					</div>
				</fieldset>
                               
                <fieldset id="mailfield" style="${comunicazioniCommissioniCommand.scegliMailChckN ? '' : 'display:none;'}">
                    <legend>
                        <fmt:message key="label.email" />
                    </legend>
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.commissione.escludinomail" />
                        </label>
                        <spring-form:checkbox id="flgEscludiNoMail_id"
                            path="configuraParametriMailCommand.escludiNoMail" />
                    </div>  
                    <div class="form-group">
                        <label>
                            <fmt:message key="label.comunicazione.commissione.accountMail" />
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
                            <fmt:message key="label.comunicazione.commissione.scelta_mail_anagrafe"></fmt:message>
                        </label>
                        <select id="scelta_mailtipo_id" name="configuraParametriMailCommand.sceltaMailAnagrafe.codice">
                            <c:forEach items="${sceltaTipoMailAnagrafeList }" var="sceltaTipoMail">
                                <option value="${ sceltaTipoMail.codice}">${sceltaTipoMail.descrizione}</option>
                            </c:forEach>

                        </select>
                        <input id="scelta_mailtipo_val" type="hidden" disabled="disabled"
                            name="configuraParametriMailCommand.sceltaMailAnagrafe.codice" value="${comunicazioniCommissioniCommand.configuraParametriMailCommand.sceltaMailAnagrafe.codice}" />
                    </div>
                </fieldset>
                <c:if test="${not empty appioservizilist}">
                <fieldset id="appiofield" style="${comunicazioniCommissioniCommand.scegliAppioChckN ? '' : 'display:none;'}">
                  <legend>
                  APP.IO
                  </legend>
                  
                  <div class="form-group">
	                  <span>Servizio APP.IO</span> 
	                                <select id="servizioappioid" name="servizioappio">
	                                  <c:forEach items="${appioservizilist}" var="servizio">
                                        <option value="${servizio.id}">${servizio.descrizione}</option>
                                      </c:forEach>                                      
								    </select>
								    <c:forEach items="${appioservizilist}" var="servizio">
                                        <span style="color:red">${servizio.noservizidesc}</span>
                                    </c:forEach>
					  </div> 
                </fieldset>
                </c:if>
                <div style="margin-bottom: var(--default-padding);">
                    <a class="btn btn-primary" href="javascript:doSubmit('insertComunicazioneMa.htm','',document.inviodati)">
                        <fmt:message key="button.insert" />
                    </a>
                    <a class='btn btn-secondary' href="preselezionaMercatiMa.htm">
                        <fmt:message key="button.back" />
                    </a>
                </div>
            </div>
        </spring-form:form>
    </div>

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
        
        function getMailTipoScelta(inputField, listItem) {
            document.getElementById('template_mail_tipo_id_hidden').value = listItem.id
            aggiornaTemplate(listItem.id);
        }

        async function getCodiceLetteraAllegatoFissi(inputField, listItem) {
            document.getElementById('letteratiposearchId_hidden').value = listItem.id;
            const response = await fetch("../mercati/ajaxAggiungiAllegatiCompilabili.htm?codiceLetteretipo=" + listItem.id, {
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
                cell0.innerHTML = '<lable>' + listItem.textContent + '</lable>';
                let cell1 = rigaAllegatiCompilabili.insertCell(1);
                let idx = rigaAllegatiCompilabili.rowIndex;
                cell1.innerHTML = '<td>'
                    + '<a class="eliminaRiga" style="float: none;" onclick="eliminaAllegatoCompilabile(event,' + listItem.id + ')" href="javascript:void(0)" title="<fmt:message key="label.elimina" />">'
                    + '<label><fmt:message key="label.elimina.image" /></label>'
                    + '</a>' + '</td>';
                enableFunctions();
                document.getElementById(inputField.id).value = '';
               
            }
        }
        
        async function eliminaAllegatoCompilabile(event, codiceLetteretipo) {
            const response = await fetch("../mercati/ajaxRimuoviAllegatoCompilabile.htm?codiceLetteretipo=" + codiceLetteretipo, {
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
            const response = await fetch("../mercati/ajaxAggiungiFirmatario.htm?codiceFirmatario=" + firmatarioId, {
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

            const response = await fetch("../mercati/ajaxRimuoviFirmatario.htm?codiceFirmatario=" + firmatarioId, {
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
            const response = await fetch("../mercati/ajaxSetParametriProtocollazione.htm", {
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
        	let td = tipiDocumento[i].closest('td');
        	let selTipiDocumento = td.getElementsByClassName('prot_tp') ;
        	for (var j = 0; j < selTipiDocumento[0].options.length; j++) {
        			let opt = selTipiDocumento[0].options[j];
        			if (tipiDocumento[i].value === opt.value) {
    					selTipiDocumento[0].selectedIndex=j;
    				}
				
        		
				
			}
			
		}
        let amm = document.getElementsByClassName('codice_amm_protocollo');
        for (var i = 0; i < amm.length; i++) {
        	let td = amm[i].closest('td');
        	let selProtAmm = td.getElementsByClassName('prot_amm') ;
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
        
        function mostraNascondiFieldSet(el, fieldsetid){
        	 const fieldsetidEl = document.getElementById(fieldsetid);
        	 if (el.checked) {
        		 fieldsetidEl.style.display = '';
        	    } else {
        	    	fieldsetidEl.style.display = 'none';
        	    }
        }
        function mostraNascondiTemplate(el){
       	 const fieldsetidEl = document.getElementById('templatefield');
       	    if (el.checked) {
       		 fieldsetidEl.style.display = '';
       	    } else {
       	    	
       	    	if(el.id == 'scegliMailChck'){
       	    		if(document.getElementById('scegliAppioChck')?.checked){
       	    			fieldsetidEl.style.display = '';
       	    		}else{
       	    			fieldsetidEl.style.display = 'none';
       	    		}
       	    	}else if(el.id == 'scegliAppioChck'){
       	    		if(document.getElementById('scegliMailChck').checked){
       	    			fieldsetidEl.style.display = '';
       	    		}else{
       	    			fieldsetidEl.style.display = 'none';
       	    		}
       	    	}
       	    }
       }
        
        function aggiungiSegnapostoOggetto(a){
        	let oggetto = document.getElementById('oggettomail_id');
        	oggetto.value = oggetto.value + ' ' + a.innerText;
        }
        
        function aggiungiSegnapostoBody(a){
        	let body = document.getElementById('bodymail_id');
        	body.value = body.value + ' ' + a.innerText;
        }
        
        const aprisegnapostooggetto = document.getElementById('aprisegnapostooggetto');
        const segnaposto = document.getElementById('segnapostoguida');
        const aprisegnapostobody = document.getElementById('aprisegnapostobody');
        const segnapostobody = document.getElementById('segnapostoguidabody');
        
        function mostraNascondiTooltip(){
       	 if(segnaposto.style.display === 'inline'){
       		segnaposto.style.display = 'none';
       	 }else{
       		segnaposto.style.display = 'inline'; 
       	 }
        }
        function mostraNascondiTooltipBody(){
          	 if(segnapostobody.style.display === 'inline'){
          		segnapostobody.style.display = 'none';
          	 }else{
          		segnapostobody.style.display = 'inline'; 
          	 }
        }
        
        function onlyAppioSelected(){
        	return document.getElementById('scegliAppioChck')?.checked && !document.getElementById('scegliMailChck').checked && !document.getElementById('prevedeprotocollazione_id')?.checked;
        }
        function anagrafeSelected() {
            const selected = document.querySelector('input[name="tipoInvioMercato"]:checked');
            return selected && selected.value === 'anagrafe';
        }
        function mostraNascondiAllegatiFirmatari(){        	
        	document.querySelector('#documentitipo_id').style.display=(anagrafeSelected() || onlyAppioSelected()) ? 'none' : '';
	    	document.querySelector('#convertipdf_id').style.display=(anagrafeSelected() || onlyAppioSelected()) ? 'none' : '';
	    	document.querySelector('#allegati_fissi_id').style.display=onlyAppioSelected() ? 'none' : '';
	    	document.querySelector('#metti_alla_firma_id').style.display=onlyAppioSelected() ? 'none' : '';
        }
        
        document.addEventListener('click', function(event) {
        	  if (!aprisegnapostooggetto.contains(event.target) && !segnaposto.contains(event.target) ) {
        	    segnaposto.style.display = 'none';
        	  }
        	  
        	  if (!aprisegnapostobody.contains(event.target) && !segnapostobody.contains(event.target) ) {
        		  segnapostobody.style.display = 'none';
          	  }
        	});
     
        
        document.querySelectorAll('input[name="tipoInvioMercato"]').forEach(radio => {
      	  radio.addEventListener('change', () => {
      	    if (radio.checked) {
      	      if (radio.value === 'anagrafe') {
      	    	  document.querySelectorAll('.autsegnaposti').forEach(function(tabella) {
      	    		  tabella.style.display = 'none';
      	    	  });
      	    	  document.querySelectorAll('.autgruppisegnaposti').forEach(function(tabella) {
      	    		  tabella.style.display = '';
      	    	  });      	    	  
      	      } else if (radio.value === 'autorizzazione') {
      	    	  document.querySelectorAll('.autsegnaposti').forEach(function(tabella) {
      	    		  tabella.style.display = '';
      	    	  });
      	    	  document.querySelectorAll('.autgruppisegnaposti').forEach(function(tabella) {
      	    		  tabella.style.display = 'none';
      	    	  });      	    	  
      	      }
      	    }
      	    mostraNascondiAllegatiFirmatari();
      	  });
        });
        
        document.querySelectorAll('#segnapostoguida td').forEach(function(td) {
        	td.style.whiteSpace = 'normal';
            var text = td.textContent.trim();
            var match = text.match(/^\[(.+)\]$/);
            if (match) {
              var num = match[1];
              td.innerHTML = '<a href="javascript:void(0)" onclick="aggiungiSegnapostoOggetto(this)">[' + num + ']</a>';
            }
            
        }); 
        
        document.querySelectorAll('#segnapostoguidabody td').forEach(function(td) {
            td.style.whiteSpace = 'normal';
            var text = td.textContent.trim();
            var match = text.match(/^\[(.+)\]$/);
            if (match) {
              var num = match[1];
              td.innerHTML = '<a href="javascript:void(0)" onclick="aggiungiSegnapostoBody(this)">[' + num + ']</a>';
            }

        });    
        
    </script>
</body>

</html>