<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum" %>
<%@page import="java.net.URLEncoder"%>
<html lang="it">
<head>
	<meta charset="UTF-8">
	<c:if test="${borsellino.id == null }">
		<title><fmt:message key="abbonamento.label.nuovo_abbonamento"/></title>
	</c:if>
	<c:if test="${borsellino.id != null }">
		<title><fmt:message key="abbonamento.label.abbonamento"/></title>
	</c:if>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	<style >
		.right {
			display: inline-block;
			float: right;
			padding: var(--default-padding);			
		}
		
		.noborder{
			border: 0 solid !important;		
		}
		
		.boldtext{
			font-weight: bolder;
		}
	
	</style>
	 <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${borsellino.id == null }">
			<fmt:message key="abbonamento.label.nuovo_abbonamento"/>
		</c:if>
		<c:if test="${borsellino.id != null }">
			<fmt:message key="abbonamento.label.abbonamento"/>
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../abbonamentoposteggi/list" />	
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="borsellino" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">	
			<jsp:param name="commandName" value="borsellino" />
		</jsp:include>
		<div class="vbg-form">
			<c:if test="${borsellino.id == null }">
			<fieldset>
				<legend><fmt:message key="abbonamento.label.nuovo_abbonamento"/></legend>
				<div class="form-group">
					<label><fmt:message key="label.anagrafe"/></label>
					<spring-form:input id="anagrafe_id" size="67" path="nominativo" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
					<init:autocompleter methodAjax="findAnagrafe.htm?tipoAnagrafe=F" minChars="3" idHidden="anagrafe_hidden" idInput="anagrafe_id" inputTitleKey="label.ricerca_nominativo"/>
					<spring-form:errors path="nominativo" cssClass="error" /> 
					<spring-form:hidden id="anagrafe_hidden" path="idAnagrafe" />				
				</div>				
				<div class="form-group">
					<label><fmt:message key="label.stato"/></label>
					<select id="stato" name="stato">
						<c:forEach items="${statiborsellino}" var="statoAbb">
							<option value="${statoAbb}" >${statoAbb}</option>
						</c:forEach>						
					</select>
				</div>			
			</fieldset>
			</c:if>
			<c:if test="${borsellino.id != null }">
			<fieldset>
				<legend>${borsellino.descrizione}</legend>
				<input type="hidden" id="id_hidden" value="${borsellino.id}"/>				
				<div class="form-group">
					<label><fmt:message key="abbonamento.label.creato_il"/></label>
					<input type="text" class="noborder" value="<fmt:formatDate value="${borsellino.dataCreazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>"/>
				</div>				
				<div class="form-group">
					<label><fmt:message key="abbonamento.label.credito_residuo"/></label>
					<b><input type="text" class="noborder boldtext" value="€ ${borsellino.creditoResiduo}"/></b>
					<fmt:message key="abbonamento.label.credito_residuo.help"/>
				</div>
				<div class="form-group">
					<label><fmt:message key="label.attivo"/></label>
					<c:set var="attivo"></c:set>
					<c:if test="${borsellino.stato == 'ATTIVO' }">
						<c:set var="attivo">checked="checked"</c:set>
					</c:if>						
					<input type="checkbox" id="modal-stato" ${attivo} onclick="aggiornaStato()" />					
				</div>
				</fieldset>	
				<fieldset class="collassabile">
					<legend><fmt:message key="label.autorizzazioni.abbonamento" /></legend>	
					<c:forEach  items="${borsellino.autorizzazioni}" var="aut">			
						<div class="form-group">
						
							<span class='readonly-form-control'>
							<c:choose>
							<c:when test="${not empty aut.numeroAttoCollegato}">
									<label><fmt:message key="label.numero" />: <b>${aut.numeroAttoCollegato}</b></label>
									<label><fmt:message key="label.data" />: <b>${aut.dataAttoCollegato}</b></label>
									(<label><fmt:message key="label.della_concessione_numero" />: <b>${aut.numero}</b></label>
									 <label><fmt:message key="label.del" />: <b>${aut.data}</b></label>
									 <label><fmt:message key="label.comune" />: <b>${aut.comune}</b></label>)
							</c:when>
							<c:otherwise>
								<label><fmt:message key="label.numero" />: <b>${aut.numero}</b></label>
								<label><fmt:message key="label.data" />: <b>${aut.data}</b></label>
								<label><fmt:message key="label.comune" />: <b>${aut.comune}</b></label>
							</c:otherwise>
							</c:choose>
								
								
							</span>
						</div>
					</c:forEach>
				</fieldset>
				
				
				<fieldset  class="collassabile">
					<c:if test="${not empty borsellino.movimenti }">
						<legend><fmt:message key="label.movimenti" /></legend>	
						<div class="form-group">
								<div>
									<div class="form-undergroup">
										<label><fmt:message key="label.ricerca"/></label>
										<input type="text" id="ricerca_id"  />
									</div>
								</div>
							<table class="vbg-table">
								<thead>
									<th style="width:15%"><fmt:message key="label.data_evento"/></th>
									<th style="width:75%"><fmt:message key="label.dettaglio_movimento"/></th>
									<th style="width:10%"><fmt:message key="label.credito"/></th>
								</thead>
								<tbody>
									<c:forEach items="${borsellino.movimenti}" var="movimenti">
									<tr class="righe-mov" data-id="${movimenti.id}">
									<vbg-fetch-ref id='data-by-id' method='get' response-format='json' request-format='form'
					                    url='../dettposizionedebitoria/ajaxDettaglioPosizione.htm'>
					                </vbg-fetch-ref>
										<td ><fmt:formatDate value="${movimenti.datamovimento}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>"/> </td>
										<td data-tipo="movimenti.dettaglio">${movimenti.dettaglio}</td>
										<td>${movimenti.importo}
											<c:if test="${movimenti.posizioneDebitoria != null }">
												<vbg-dettaglio-posizione-debitoria
													id-posizione="${movimenti.posizioneDebitoria}"
													fetch-ref='data-by-id'></vbg-dettaglio-posizione-debitoria>
											</c:if>															
										</td>
									</tr>
									</c:forEach> 
								</tbody>				
							</table>								
						</div>		
					</c:if>						
					
				</fieldset>
				
				<fieldset  class="collassabile">
				  <legend><fmt:message key="label.borsellino.storico.autorizzazioni" /></legend>
				   <div class="form-group">
				    <table id="sotricoaut_id" class="vbg-table">
				     <thead>
				      <th><fmt:message key="label.numero"/></th>
				      <th><fmt:message key="label.data_autorizzazione"/></th>
				      <th><fmt:message key="label.borsellino.autorizzazione.dataoperazione"/></th>
				      <th><fmt:message key="label.borsellino.autorizzazione.tipooperazione"/></th>
				     </thead>
				     <tbody>
				      <c:forEach items="${borsellino.autorizzazionihistory}" var="storico">
				       <tr>
				        <td>${storico.numero}</td>
				        <td>${storico.dataautStr}</td>
				        <td>${storico.dataoperazioneStr}</td>
				        <td>${storico.tipooperazione}</td>
				       </tr>
				      </c:forEach>
				     </tbody>
				    </table>
				    <style>
				    .datatable-sorter {
                       all: unset;
                       cursor: pointer;
                       display: block;
                       position: relative;
                       padding-right: 18px;
                     }

                     .datatable-sorter::after {
                       content: "\21C5";
                       color: #bbb;
                       margin-left: 3px;
                     }
                     
                     th.datatable-ascending .datatable-sorter::after {
                       content: "\2191";
                       color: #bbb;
                       margin-left: 3px;
                     }

                     th.datatable-descending .datatable-sorter::after {
                       content: "\2193";
                       color: #bbb;
                       margin-left: 3px;
                     }


                    .datatable-pagination-list {
                     list-style: none;
                     padding: 0;
                     margin: 10px 0;
                    }
                    .datatable-pagination-list li {
                     display: inline-block;
                     margin-right: 4px;
                    }
                    .datatable-pagination-list a, .datatable-pagination-list button {
                       padding: 4px 8px;
                       border: 1px solid #ccc;
                       background: #f5f5f5;
                       text-decoration: none;
                       cursor: pointer;
                     }

                    .datatable-active a, .datatable-active button {
                       background: #ddd;
                       font-weight: bold;
                    }
				    </style>
				    <script src="${pageContext.request.contextPath}/scripts/altri/simple-datatables@latest.js"></script>
                    <script>
                     document.addEventListener("DOMContentLoaded", () => {

                    	 new simpleDatatables.DataTable("#sotricoaut_id", {
                    		    searchable: false,
                    		    perPage: 10,
                    		    perPageSelect: [10,20,100],
                    		    columns: [
                    		    	{ select: 0, sortable: true } ,
                    		        { select: 1, sortable: false },
                    		        { select: 2, sortable: false },
                    		        { select: 3, sortable: false }
                    		    ],
                    		    labels: {
                    		        perPage: "Righe per pagina",
                    		        noRows: "Nessun record",
                    		        info: "Visualizzate {start}-{end} di {rows} righe"
                    		    }
                    		});

                    });
                    </script>
				   </div>
				</fieldset>
			
			</c:if>
		</div>
		</spring-form:form>
	</div>	
	

	<div class="form-button">
		<c:choose>
		<c:when test="${borsellino.id == null }">
			<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.save" /></a>
		</c:when>
		<c:otherwise>		    
			<a class="btn btn-primary" id="btn-ricarica" style="display:${borsellino.stato == 'ATTIVO' ? '' : 'none'}"><fmt:message key="button.abbonamenti.nuova.ricarica" /></a>
			<a class="btn btn-primary" id="btn-rimborso" style="display:${borsellino.stato == 'ATTIVO' ? '' : 'none'}"><fmt:message key="button.abbonamenti.nuovo.rimborso" /></a>
<c:if test="${IS_OPERATORE eq false }">			
			<a class="btn btn-primary" id="btn-autorizzazioni"><fmt:message key="button.abbonamenti.associa.autorizzazione" /></a>
</c:if>			
			<a class="btn btn-primary" id="btn-esporta" href="javascript:openExport();"><fmt:message key="button.esporta" /></a>
		</c:otherwise>	
		</c:choose>
		
		<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>		
	</div>	
	
		
	<vbg-modal id="modal-ricarica">
		<div slot='body' id='popup_ricarica'>
			<h1><fmt:message key="label.effettua_ricarica_abbonamento"/></h1>
			<div><fmt:message key="label.effettua_ricarica_abbonamento.help"/></div>
			<div id="risultato-operazione-ricarica" style="display:none"></div>
			<table width="100%" border="0">
				<tr id="elementIdBeforeCombo">
					<td width="250px;"></td>
					<td colspan="1"></td>
				</tr>
			 	<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="mostraTutti" value="false" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="ricarica_comune_id" />
					<jsp:param name="colspan" value="1" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>
				<tr>
					<td><fmt:message key="label.software" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
							<jsp:param name="idElemento" value="modulo_software_id" />		
							<jsp:param name="pathPropertyDescription" value="modulo_software_descrizione" />
							<jsp:param name="pathPropertyCode" value="modulo_software_codice" />
							<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=true" />							
							<jsp:param name="titleKey" value="label.ricerca_software" />							
						</jsp:include>
						<div style="font-size: 0.8em"><fmt:message key="abbonamento.label.software.help"/></div>
					</td>
				</tr>
				<tr id="elementIdBeforeCombo">
					<td><fmt:message key="label.importo"/></td>
					<td colspan="1"><input type="text" id="importo_id" onchange="checkNumberValue(this)" size="7"></td>
				</tr>
			</table>
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="saveButtonModalRicarica"><fmt:message key="button.save"/></div>
			<div class="btn btn-secondary" id="closeButtonModalRicarica"><fmt:message key="button.close"/></div>            
		</div>		
	</vbg-modal>
	
	<vbg-modal id="modal-rimborso">
		<div slot='body' id='popup_rimborso'>
			<h1><fmt:message key="label.effettua_rimborso_abbonamento"/></h1>
			<div id="risultato-operazione-rimborso" style="display:none"></div>
			<table width="100%" border="0">
				<tr id="elementIdBeforeCombo2">
					<td width="250px;"></td>
					<td colspan="1"></td>
				</tr>
			 	<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="mostraTutti" value="false" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="rimborso_comune_id" />
					<jsp:param name="colspan" value="1" />
					<jsp:param name="elementBeforeCombo2" value="elementIdBeforeCombo2" />
				</jsp:include>
				<tr>
					<td><fmt:message key="label.software" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
							<jsp:param name="idElemento" value="modulo_software_id2" />		
							<jsp:param name="pathPropertyDescription" value="modulo_software_descrizione2" />
							<jsp:param name="pathPropertyCode" value="modulo_software_codice2" />
							<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=true" />							
							<jsp:param name="titleKey" value="label.ricerca_software2" />							
						</jsp:include>
						<div style="font-size: 0.8em"><fmt:message key="abbonamento.label.software.help"/></div>
					</td>
				</tr>
				<tr id="elementIdBeforeCombo2">
					<td><fmt:message key="label.importo"/></td>
					<td colspan="1"><input type="text" id="importo_id2" onchange="checkNumberValue(this)" size="7"></td>
				</tr>
			</table>			
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="saveButtonModalRimborso"><fmt:message key="button.save"/></div>
			<div class="btn btn-secondary" id="closeButtonModalRimborso"><fmt:message key="button.close"/></div>            
		</div>		
	</vbg-modal>
	
	<vbg-modal id="modal-autorizzazioni">
		<div slot='body' id='popup_autorizzazioni'>
			<h1><fmt:message key="abbonamento.label.cerca_autorizzazioni_da_associare"/></h1>
			<div id="risultato-operazione-autorizzazioni" style="display:none"></div>
			<div style="width: 100%: margin: 10px">
			<table>
				<tr style="display:none;">
					<td><fmt:message key="label.software" /></td>
					<td colspan="7">						
						<jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
							<jsp:param name="idElemento" value="modulo_software_aut_id" />		
							<jsp:param name="pathPropertyDescription" value="modulo_software_aut_descrizione" />
							<jsp:param name="pathPropertyCode" value="modulo_software_aut_codice" />
							<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=true" />							
							<jsp:param name="titleKey" value="label.ricerca_software" />							
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.autorizzazioni" /></td>
					<td colspan="7">						
						<input id="autorizzazioneid" style="width:400px;" list="auts" placeholder="Scegli o scrivi un'autorizzazione">
					    <datalist id="auts">					    
					        <c:forEach var="entry" items="${autorizzazioniMap}">
					          <option value="${entry.value}" data-id="${entry.key}">    
                            </c:forEach>
						</datalist>

					</td>
				</tr>
			</table>	
			</div>
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="saveButtonModalAutorizzazioni"><fmt:message key="button.save"/></div>
			<div class="btn btn-secondary" id="closeButtonModalAutorizzazioni"><fmt:message key="button.close"/></div>            
		</div>		
	</vbg-modal>	

	<script type="text/javascript">
	
	
	 function filtraCelle() {

			let ricerca =  document.getElementById('ricerca_id').value;
		

			tr = document.getElementsByClassName('righe-mov');

			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td");		
				let trovato = false;
				for (let cell of td) {
					if (cell) {
						txtValue = cell.textContent || cell.innerText;
						if (txtValue.toUpperCase().indexOf(ricerca.toUpperCase()) > -1) {
							trovato = true;
							break;
						}
					}
				}
				if (trovato) {
					tr[i].style.display = "";
					trovato = false;
				} else {
					tr[i].style.display = "none";
				}
			}
			
	}
	
	
	let checkAttiva = document.querySelector('#modal-stato');
	let btnstorno = document.querySelector('#btn-storno');
	let modal = document.querySelector('#visualizza_dettagliMov');
	let modBoby = document.querySelector('#mod-body');
	
		async function aggiornaStato() {
		    
		    if(checkAttiva.checked){
				checkAttiva.checked = 'checked';
				document.querySelector('#btn-ricarica').style.display = '';
				document.querySelector('#btn-rimborso').style.display = '';
		    }else{
				checkAttiva.checked = '';
				document.querySelector('#btn-ricarica').style.display = 'none';
				document.querySelector('#btn-rimborso').style.display = 'none';
		    }
		    
		    let id = document.querySelector('#id_hidden').value;
		    let stato = checkAttiva.checked ? 1 : 0;
		    
		    window.vbg.mostraModalCaricamento();
		    
		    const url = "../abbonamentoposteggi/ajaxAggiornaStato.htm";
		    
		    const formData = new FormData();
		    formData.append('id',id);
		    formData.append('stato',stato);
		    
		    const result = await fetch(url, {
	                method: 'POST',
	                body: formData
	            });
		    
		    if(result.status == 200){
				window.vbg.nascondiModalCaricamento();
				
		    }
		}
		
		function popolaPopup(esito){

		    modBoby.innerText = "Esito" +esito.id;
		    
		    modal.open();
		    
		}
		
	
	
	vbg.ready(function(){
		
		function aggiungiEventoReload(){
			let det = document.querySelectorAll('vbg-dettaglio-posizione-debitoria');
			det.forEach((vbgDettaglioPosizione)=>{
				vbgDettaglioPosizione.addEventListener('stato-modificato',()=>{
					window.vbg.mostraModalCaricamento();
					document.location.reload();
				});
				
			});
			
		}
		
		aggiungiEventoReload();
		
		const risultatoAutorizzazioniDivId  = document.getElementById('risultato-operazione-autorizzazioni');
		const modalAutorizzazioni = document.getElementById('modal-autorizzazioni');
		if(document.getElementById('btn-autorizzazioni')){
			document.getElementById('btn-autorizzazioni').addEventListener('click', (e)=>{
				autorizzazioni();		
			});
		}
		document.getElementById('closeButtonModalAutorizzazioni').addEventListener('click', (e)=>{
			modalAutorizzazioni.close();		
		});
		
		document.getElementById('saveButtonModalAutorizzazioni').addEventListener('click', (e)=>{
			eseguiAutorizzazioni();	
		});
		
		function autorizzazioni(){
			risultatoAutorizzazioniDivId.innerHTML='';
			modalAutorizzazioni.open();			
		}
		
		function risultatoAutorizzazioni(messaggio, tipoMessaggio){
			risultatoAutorizzazioniDivId.className = '';
			if(tipoMessaggio === TipoMessaggi.None){
				risultatoAutorizzazioniDivId.style.display = 'none';
			}
			if(tipoMessaggio === TipoMessaggi.Success){
				risultatoAutorizzazioniDivId.style.display = '';
				risultatoAutorizzazioniDivId.classList.add("success_header");
			}
			if(tipoMessaggio === TipoMessaggi.Error){
				risultatoAutorizzazioniDivId.style.display = '';
				risultatoAutorizzazioniDivId.classList.add("error_header");
			}
			risultatoAutorizzazioniDivId.innerHTML = messaggio;
		}
		
		
		async function eseguiAutorizzazioni(){
			
			risultatoAutorizzazioni('', TipoMessaggi.None);
			
			let idBorsellino = ${borsellino.id};
			let idAutorizzazione = '';
			const value = document.getElementById("autorizzazioneid").value;
			const options = [...document.getElementById("auts").options];
			const option = options.find(opt => opt.value === value);
			if (option) {
				idAutorizzazione = option.dataset.id;
			}									
			
			if(idBorsellino=='' || idAutorizzazione ==''){
				risultatoAutorizzazioni('Dati non validi', TipoMessaggi.Error);
				return;
			}
			window.vbg.mostraModalCaricamento();
			
			const formData = new FormData();
			formData.append('idBorsellino', ${borsellino.id});
			formData.append('idAutorizzazione',idAutorizzazione);

			const response = await fetch('../abbonamentoposteggi/ajaxCollegaAutorizzazione.htm', {
		                method: 'POST',
		                body: formData
	            });
        		let jsResponse = await response.json();
        		if(jsResponse.esito===true){
        			risultatoAutorizzazioni('Autorizzazione collegata', TipoMessaggi.Success);	
        		}else{
        			risultatoAutorizzazioni(jsResponse.messaggio, TipoMessaggi.Error);	
        		}
        		document.location.reload();

			window.vbg.nascondiModalCaricamento();
		}
		
		///////////////////////////////////////////// RICARICA
		
			const modalRicarica = document.getElementById('modal-ricarica');
			
			document.getElementById('btn-ricarica').addEventListener('click', (e)=>{
				ricarica();		
			});
			document.getElementById('closeButtonModalRicarica').addEventListener('click', (e)=>{
				modalRicarica.close();		
			});
			
			document.getElementById('saveButtonModalRicarica').addEventListener('click', (e)=>{
				eseguiRicarica();	
			});
			function ricarica(){
				risultatoRicaricaDivId.innerHTML='';
				modalRicarica.open();			
			}
			
			const TipoMessaggi = {
					  None: 'none',
					  Success: 'success',
					  Error: 'error',
					}
			
			const risultatoRicaricaDivId  = document.getElementById('risultato-operazione-ricarica');
			
			function risultatoRicarica(messaggio, tipoMessaggio){
				risultatoRicaricaDivId.className = '';
				if(tipoMessaggio === TipoMessaggi.None){
					risultatoRicaricaDivId.style.display = 'none';
				}
				if(tipoMessaggio === TipoMessaggi.Success){
					risultatoRicaricaDivId.style.display = '';
					risultatoRicaricaDivId.classList.add("success_header");
				}
				if(tipoMessaggio === TipoMessaggi.Error){
					risultatoRicaricaDivId.style.display = '';
					risultatoRicaricaDivId.classList.add("error_header");
				}
				risultatoRicaricaDivId.innerHTML = messaggio;
			}
			
			
			async function eseguiRicarica(){
				
				risultatoRicarica('', TipoMessaggi.None);
				
				let codiceComune = document.getElementById('rimborso_comune_id').value;
				let importo = document.getElementById('importo_id').value;
				let codiceSoftware = document.getElementById('modulo_software_id_hidden').value;
				
				if(codiceComune=='' || parseFloat(importo) <= 0 ){
					console.log('Non puoi effettuare una ricarica negativa');
					risultatoRicarica('Dati non validi', TipoMessaggi.Error);
					return;
				}
				window.vbg.mostraModalCaricamento();
				
				const formData = new FormData();
				formData.append('idBorsellino', ${borsellino.id});
				formData.append('codiceComune',codiceComune);
				formData.append('codiceSoftware', codiceSoftware);
				formData.append('importo',importo);
				
				const response = await fetch('../abbonamentoposteggi/ajaxInsertRicarica.htm', {
			                method: 'POST',
			                body: formData
			            });

            	if( await response.status == 200){
            		let jsResponse = await response.json();
            		if(jsResponse.esito===true){
            			risultatoRicarica('Ricarica effettuata', TipoMessaggi.Success);	
            		}else{
            			risultatoRicarica(jsResponse.messaggio, TipoMessaggi.Error);
            			window.vbg.nascondiModalCaricamento();
            			await new Promise(resolve => setTimeout(resolve, 2000));
            		}
            		document.location.reload();
            	}
            	document.getElementById('importo_id').value = 0;
				window.vbg.nascondiModalCaricamento();
			}
			
			document.getElementById('ricerca_id').addEventListener('keyup', async function(e){
				e.preventDefault();
				filtraCelle();
			});
			
          ///////////////////////////////////////////// RIMBORSO
          
            const modalRimborso = document.getElementById('modal-rimborso');
			
			document.getElementById('btn-rimborso').addEventListener('click', (e)=>{
				rimborso();		
			});
			document.getElementById('closeButtonModalRimborso').addEventListener('click', (e)=>{
				modalRimborso.close();		
			});
			
			document.getElementById('saveButtonModalRimborso').addEventListener('click', (e)=>{
				eseguiRimborso();	
			});
			function rimborso(){
				risultatoRicaricaDivId.innerHTML='';
				modalRimborso.open();			
			}
			
            const risultatoRimborsoDivId  = document.getElementById('risultato-operazione-rimborso');
			
			function risultatoRimborso(messaggio, tipoMessaggio){
				risultatoRimborsoDivId.className = '';
				if(tipoMessaggio === TipoMessaggi.None){
					risultatoRimborsoDivId.style.display = 'none';
				}
				if(tipoMessaggio === TipoMessaggi.Success){
					risultatoRimborsoDivId.style.display = '';
					risultatoRimborsoDivId.classList.add("success_header");
				}
				if(tipoMessaggio === TipoMessaggi.Error){
					risultatoRimborsoDivId.style.display = '';
					risultatoRimborsoDivId.classList.add("error_header");
				}
				risultatoRimborsoDivId.innerHTML = messaggio;
			}
			
             async function eseguiRimborso(){
				
            	risultatoRimborso('', TipoMessaggi.None);
				
				let codiceComune = document.getElementById('rimborso_comune_id').value;
				let importo = document.getElementById('importo_id2').value;
				let codiceSoftware = document.getElementById('modulo_software_id2_hidden').value;
				
				if(codiceComune=='' || parseFloat(importo) <= 0 ){
					console.log('Non puoi effettuare un rimborso negativo');
					risultatoRimborso('Dati non validi', TipoMessaggi.Error);
					return;
				}
				window.vbg.mostraModalCaricamento();
				
				const formData = new FormData();
				formData.append('idBorsellino', ${borsellino.id});
				formData.append('codiceComune',codiceComune);
				formData.append('codiceSoftware', codiceSoftware);
				formData.append('importo',importo);
				
				const response = await fetch('../abbonamentoposteggi/ajaxInsertRimborso.htm', {
			                method: 'POST',
			                body: formData
			            });

            	if( await response.status == 200){
            		let jsResponse = await response.json();
            		if(jsResponse.esito===true){
            			risultatoRimborso('Rimborso effettuato', TipoMessaggi.Success);	
            		}else{
            			risultatoRimborso(jsResponse.messaggio, TipoMessaggi.Error);
            			window.vbg.nascondiModalCaricamento();
            			await new Promise(resolve => setTimeout(resolve, 2000));
            		}
            		document.location.reload();
            	}
            	document.getElementById('importo_id2').value = 0;
				window.vbg.nascondiModalCaricamento();
			}
			
		});				
	</script>
	
	<script type="text/javascript"
        src="${pageContext.request.contextPath}/scripts/jquery.statoposizionedebitoria/jquery.statoposizionedebitoria.js"></script>
    <link rel="stylesheet"
        href="${pageContext.request.contextPath}/scripts/jquery.statoposizionedebitoria/jquery.statoposizionedebitoria.css">
        
			<script type="text/javascript">
			
			
						vbg.ready(async function(){
						
						
						verificaElaborazioni();
						
					

					});

						function openExport(){		
							goToExportPentahoPanel();
						}

						function goToExportPentahoPanel(){
							
							var url  = URLDecode('${_urlback}');			
							ajaxHistorySet(url);			
							setTimeout("doHref('../abbonamentoposteggi/createExportModalitaPentaho.htm?codice=${borsellino.id}','')",10);
						}

						function ajaxHistorySet(url){
							
							var jhqr = jQuery.ajax({
								  url: '../history/ajaxSet.htm?ReturnTo='+url,
								  context: document.body,
								  cache: false,				
								  dataType: "html",
								  success: function(data) { 				   
									} 
								});	
						}
						
						
						
			</script>        
</body>
</html>