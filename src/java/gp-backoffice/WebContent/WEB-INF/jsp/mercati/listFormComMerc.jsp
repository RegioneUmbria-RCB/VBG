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
        Preselezione Mercati
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
                <div>Seleziona i mercati con i quali si desidera creare la comunicazione</div>
            </div>
        </div>
        <br class="clear" />
        <spring-form:form commandName="comunicazioniCommissioniCommand" name="inviodati"
            enctype="multipart/form-data">
            <jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName" value="comunicazioniCommissioniCommand" />
            </jsp:include>

            <div id="form" class="vbg-form">
                <fieldset>
                    <legend><fmt:message key="label.massive.sceltamercati" /></legend>
                    
                    <div class="form-group">
	                  <label for="tipoDestinatario">Destinatari:</label>
									<spring-form:select path="tipoDestinatario"
										id="tipoDestinatario">
										<spring-form:option value="tutti" label="Tutti" />
										<spring-form:option value="concessionari"
											label="Concessionari" />
										<spring-form:option value="spuntisti" label="Spuntisti" />
									</spring-form:select>
								</div> 
									
					  
											
						
                    
                    <div>
                     <label>Scegli manifestazione:</label><br> <label> <spring-form:radiobutton
													path="manifestazioneRadio" value="tutte"
													onchange="mostraNascondiMercati()" /> TUTTE
										</label><br> <label> <spring-form:radiobutton
														path="manifestazioneRadio" value="scegli"
														onchange="mostraNascondiMercati()" /> Scegli
													Manifestazione
											</label>
											
						</div>
					<br><br>
					<div class="form-group">
						<label style="width:100%">Solo chi ha partecipato in data</label> <br>
						<span><fmt:message
								key="label.data.inizio" /></span> <spring-form:input path="dallaDataM" id="dataInizio_id" size="10" maxlength="10"
                           onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio"
							idInput="dataInizio_id" textKey="label.calendar" />

						<span><fmt:message key="label.data.fine" /></span> <spring-form:input path="allaDataM" id="dataFine_id" size="10" maxlength="10"
                           onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafine"
							idInput="dataFine_id" textKey="label.calendar" />
						<p id="notatutti"><fmt:message key="label.massive.nota.crecomunicazionemercati.t" /></p>
						<p id="notaconcessionari" style="display:none;"><fmt:message key="label.massive.nota.crecomunicazionemercati.c" /></p>
						<p id="notaspuntisti" style="display:none;"><fmt:message key="label.massive.nota.crecomunicazionemercati.s" /></p>	
					</div>
				</fieldset>
                <fieldset id="sceglimanifestazionefield" style="display:none">
                    <legend>SCEGLI MANIFESTAZIONE</legend>
                    <jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
							<jsp:param name="idElemento" value="ricercamercati_id" />				
							<jsp:param name="autocompleterAjax" value="findMercati.htm" />
							<jsp:param name="afterUpdateElement" value="aggiungiMercatoAfterUpdate" />							
							<jsp:param name="titleKey" value="label.ricerca_mercati" />
	              </jsp:include>
	              <div>
                        <table class="vbg-table" id="tabella_mercati_scelti">
                            <thead>
                                <tr class="header">
                                    <th>
                                        Mercati
                                    </th>
                                    <th>
                                        <fmt:message key="label.azioni" />
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="mercati_scelti_body">
                            </tbody>
                            </table>
                            </div>
                </fieldset>
                <div style="margin-bottom: var(--default-padding);">
                    <a class="btn btn-primary" href="javascript:insertComunicazione('createComunicazioneMa.htm',document.inviodati)">
                        AVANTI
                    </a>
                    <a class='btn btn-secondary' href="listComunicazioniM.htm">
                        <fmt:message key="button.back" />
                    </a>
                </div>
            </div>
        </spring-form:form>
    </div>

    <script type="text/javascript">

    
        
        function mostraNascondiFieldSet(el, fieldsetid){
        	 const fieldsetidEl = document.getElementById(fieldsetid);
        	 if (el.checked) {
        		 fieldsetidEl.style.display = '';
        	    } else {
        	    	fieldsetidEl.style.display = 'none';
        	    }
        }
        function aggiungiMercatoAfterUpdate(inputField, listItem) {
			aggiungiMercato(listItem.id, listItem.textContent);
			
		}
        
        function aggiungiMercato(id,descrizione){
        	let tabellaMercati = document.getElementById("tabella_mercati_scelti");
            let rigaMercati = document.getElementById("mercati_scelti_body").insertRow();
            let cell0 = rigaMercati.insertCell(0);
            cell0.innerHTML = '<lable>' + descrizione + '</lable><input name="mercatiscelti" value="' + id + '" type="hidden"/>';
            let cell1 = rigaMercati.insertCell(1);
            cell1.innerHTML = '<td>'
                + '<a class="eliminaRiga" style="float: none;" onclick="eliminaMercato(event)" href="javascript:void(0)" title="<fmt:message key="label.elimina" />">'
                + '<label><fmt:message key="label.elimina.image" /></label>'
                + '</a>' + '</td>';
        }
        
        function eliminaMercato(event) {
        	event.target.closest("tr").remove();
        }
        
        function mostraNascondiMercati(){
        	const selezionato = document.querySelector('input[name="manifestazioneRadio"]:checked');
        	const fieldsetidEl = document.getElementById('sceglimanifestazionefield');
            if (selezionato && selezionato.value == 'scegli') {
            	fieldsetidEl.style.display = '';
            }else{
            	fieldsetidEl.style.display = 'none';
            }
        }
        
        function insertComunicazione(url, inviodati){
        	const selezionato = document.querySelector('input[name="manifestazioneRadio"]:checked');
        	if(selezionato && selezionato.value == 'scegli'){
        		let mercatiscelti = document.getElementsByName('mercatiscelti');
        		if(mercatiscelti.length == 0){
    				alert('Per via della scelta manuale è obbligatorio scegliere almeno un mercato');
        			return;
    			}
        	}
        	
        	doSubmit(url,'',inviodati);
        }
        
        function gestisciTipoDestinatarioMess() {
        	const select = document.getElementById("tipoDestinatario");
            const valore = select.value;
            const p1 = document.getElementById("notatutti");
            const p2 = document.getElementById("notaconcessionari");
            const p3 = document.getElementById("notaspuntisti");
            
            p1.style.display = 'none';
            p2.style.display = 'none';
            p3.style.display = 'none';
            
            switch (valore) {
                case "tutti":
                p1.style.display = '';
                break;
                
                case "concessionari":
                p2.style.display = '';
                break;
                
                case "spuntisti":
                p3.style.display = '';
                break;
                
                default:
                console.log('Nessun valore selezionato');
            }
        }
        

        document.getElementById("tipoDestinatario").addEventListener("change", gestisciTipoDestinatarioMess);
    </script>
</body>

</html>