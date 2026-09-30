<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.istanze_discusse_commissione" />
	</title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	<style>
		.cella-ordine {
			text-align: right; 
			min-width: 6em !important; 
			max-width: 60px;
		}
		.cella-intervento { 
			font-weight: bold;
			margin-bottom: var(--half-padding);
		}
		.cella-lavori { 
			max-width: 250px;
		  	white-space: nowrap;
		  	text-overflow: ellipsis;
		  	overflow: hidden;
		  	margin-bottom: var(--half-padding);
		}
		.cella-documenti-selezionati {
			color: var(--accent-color);
		}
		.cella-movimento-rientro >a {
			margin-left: 0px !important;
		}
		.cella-movimento-origine {
			margin-bottom: var(--half-padding);
		}
		.azione {
			cursor: pointer;
		}
		.messaggio_status_errore {
			color: var(--error-color);
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
			width: 500px;
			display: inline-block;	
		}
		
		.messaggio_status_successo {
			color: var(--color-success);
			
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.istanze_discusse_commissione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../commissioniediliziet/listCommissioniedilizieR" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commissione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissione" />
		    </jsp:include>
		    <div class="vbg-form">
		    	<fieldset>
		    		<legend><fmt:message key="label.dettaglio_commissione_edilizia" /></legend>
					<div class="parametriDiv">
						<div class="etichetta">
							<div><fmt:message key="label.numero_commissione" />:</div>
							<div><fmt:message key="label.descrizione" />:</div>
							<div><fmt:message key="label.data_commissione" />:</div>
						</div>		
						<div class="parametro">       		 	
							<div>${commissione.numeroProtocollo}</div>
							<div>${commissione.descrizione}</div>
							<div><fmt:formatDate value="${commissione.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></div>
						</div>
					</div>
		    	</fieldset>
		    	<fieldset id="field_orario">
		    		<spring-form:hidden id="commissione_id" path="id" />
		    		<legend><fmt:message key="commissioniediliziet.label.orario" /></legend>
		    		
					<div class="form-group validate-required">
						<label><fmt:message key="label.ora_inizio" /></label>
						<spring-form:input id="orainizio_id" path="oraInizio"  size="8" maxlength="5" onblur="isValidOra(this,true);" cssClass="control-to-validate" />
						<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
					</div>
					<div class="form-group validate-required">
					   <label><fmt:message key="label.ora_fine" /></label>
					   	<spring-form:input id="orafine_id" path="oraFine" size="8" maxlength="5" onblur="isValidOra(this,true);" cssClass="control-to-validate" />
						<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						
					</div>
					<div class="form-group">
						<c:if test="${commissione.aperta eq true}">
							<a class="btn btn-primary bottone-salvataggio"><fmt:message key="button.update" /></a>											
						</c:if>	
						<div id="messaggio_status"></div>						
					</div>
				</fieldset>			
				<fieldset>
					<legend><fmt:message key="label.istanze_in_discussione" /></legend>
						<table width="100%">
							<tr>
								<td colspan="2">
									<form name="commissioniedilizietForm" action="listCommissioniedilizieR.htm">
									 <table class="vbg-table">
									 	<thead>
											<tr>
												<th><fmt:message key="label.ordine"/></th>
												<th><fmt:message key="label.istanza"/></th>
												<th><fmt:message key="label.lavori"/></th>
												<th><fmt:message key="label.movimento"/></th>
												<th><fmt:message key="label.tipologia_parere"/></th>
												<th><fmt:message key="label.azioni"/></th>
											</tr>	 	
									 	</thead>
									 	<tbody>
									 		<c:forEach items="${commissione.righe}" var="riga" varStatus="i">
									 			
									 			<tr data-id="${riga.id}">
									 				<td>
									 					<div class="form-group validate-required">
									 						<spring:bind path="righe[${i.index}].ordine">
									 							<input class="cella-ordine control-to-validate" type="text" value="${status.value}" name="${status.expression}" width="6"></input>
																<div class="error validation-feedback" style="padding-left: 0px;"><fmt:message key="label.campo_obbligatorio" /></div>
															</spring:bind>
														</div>
									 				</td>
									 				<td>
									 					<a href="javascript:historySet('${_urlback }','../istanze/view.htm?codice=${riga.codiceIstanza}','') ">
										 					<div>
										 					${riga.comune }
										 					</div>
										 					<div>
											 					Numero 
											 					${riga.numeroIstanza}
											 					 del 
											 					<fmt:formatDate value="${riga.dataPresentazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
										 					</div>
										 					<div>
										 					<c:if test="${empty riga.numeroProtocollo}">
																Istanza non protocollata
															</c:if>
										 					<c:if test="${not empty riga.numeroProtocollo}">
																Protocollo 
											 					${riga.numeroProtocollo }
											 					 del 
											 					<fmt:formatDate value="${riga.dataProtocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
											 				</c:if>
										 					</div>
										 					<div>
										 					${riga.richiedente }
										 					</div>
									 					</a>
									 				</td>
									 				<td>
									 					<div class="cella-intervento">
									 						${riga.intervento}
									 					</div>
									 					<div class="cella-lavori" title="${riga.lavori }">
									 						${riga.lavori }
									 					</div>
									 					<div class="cella-documenti-selezionati">
									 					    <a href="javascript:doHref('../commediliziedettagliodocumentipratica/list.htm?idCommissioneR=${riga.id}&codiceIstanza=${riga.codiceIstanza}','')">
									 					    	<i class="fa fa-exclamation-triangle"></i>&nbsp;${riga.messaggioNumeroDocumenti}
									 					    </a>
									 					</div>
									 				</td>
									 				<td>
									 					<div class="cella-movimento-origine">
									 						<i class="fa fa-arrow-right"></i>&nbsp;${riga.movimento}&nbsp;<fmt:formatDate value="${riga.dataRichiesta}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
									 					</div>
									 					<div class="cella-movimento-rientro">
										 					<c:if test="${riga.codiceMovimentoRientro!=null}">
																<a style="color: green;" href="javascript:historySet('${_urlback }','../movimenti/view.htm?codice=${riga.codiceMovimentoRientro}','')">
																	<i class="fa fa-arrow-left"></i>&nbsp;${riga.movimentoRientro}
																</a>
															</c:if>
									 					</div>
									 				</td>
									 				<td>
									 					<c:if test="${ empty riga.codiceMovimentoRientro}">
									                        <a style="color:red" href="javascript:doHref('createEsitoCommissioniedilizieR.htm?codiceCommissioneR=${riga.id}&ordine=${riga.ordine}&codiceCommissioneT=${commissione.id}','')"><fmt:message key="label.esito" /></a>
														</c:if>
														<c:if test="${not empty riga.codiceMovimentoRientro}">
															<a style="color: green;" href="javascript:doHref('createEsitoCommissioniedilizieR.htm?codiceCommissioneR=${riga.id}','')">${riga.tipologiaParere}</a>
														</c:if>	
									 				</td>
									 				<td>
									 						<c:if test="${commissione.aperta eq true}">
											 					<i class="fa fa-users fa-lg vbg-link aggiungi-soggetto azione"></i>
											 					<c:if test="${riga.codiceMovimentoRientro==null}">
																	 <i class="fa fa-times vbg-link fa-lg togli-pratica azione"></i>
																</c:if>
														 </c:if>
									 				</td>
									 			</tr>
									 		</c:forEach>
									 	</tbody>
									 </table>
									</form>
								</td>
							</tr>
							 <tr>
								<td colspan="2">
				 			    	<div id="filtro" dojoType="dijit.Dialog" title="<fmt:message key='label.filro_istanze_discutere'/>" style="display: none;">
				    					<div style="width: 500px; height: 200px;" id="filtroContent"></div>
									</div>
								</td>
							</tr>
						</table>
						<div class="vbg-group">
							<c:if test="${commissione.aperta eq true}">
								<a class="btn btn-primary" href="javascript:doSubmit('riordinaCommissioniedilizieR.htm','',document.inviodati)"><fmt:message key="button.update" /></a>	
							</c:if>
						</div>
					</div>
					
				</fieldset>
			</div>
			<c:forEach items="${cariche}" var="carica" >
				<input id="caricheId${carica.id.codice }" data-id="${carica.id.codice}" class="righe-carica" type="hidden" value="${carica.descrizione}"/>
			</c:forEach>
			<vbg-modal id="errore-eliminazione-soggetto">
				<div slot="body">
					<h1>
						<fmt:message key="label.aggiungi_soggetti_a_commissione" />
					</h1>
					<div class="vbg-form">
						<i class="fa fa-exclamation-triangle vbg-link"></i>
						<div id="errore-eliminazione-soggetto-messaggio" class="input-error"></div>
					</div>				
				</div>
		<h1>
			<fmt:message key="label.aggiungi_soggetti_a_commissione" />
		</h1>
			</vbg-modal>
			
			
			<script type='text/javascript'>
				vbg.ready(() => {
					
					
					// Registro i validatori della pagina
					const bottoni = document.querySelectorAll('.bottone-salvataggio');
					var campiDaValidare = Array.from(document.querySelectorAll('.validate-required')).map(
							(campo) =>
							new vbg.validators.RequiredFieldValidator(campo)
					);
					const validationGroup = new vbg.validators.ValidationGroup(campiDaValidare, bottoni);
					
					const bottoneSalva = document.querySelector('.bottone-salvataggio');
										
					if(bottoneSalva){
						bottoneSalva.addEventListener('click', async (e) => {
							
							if(!bottoneSalva.disabled){
								
								window.vbg.mostraModalCaricamento();
								
								let idCommissione = document.getElementById('commissione_id').value;
								let orarioInizio = document.getElementById('orainizio_id').value;
								let orarioFine = document.getElementById('orafine_id').value;
								let divMessaggio = document.querySelector('#messaggio_status');
								divMessaggio.style.display = "inline-block"; 
								
								let url = 'ajaxAggiornaOrario.htm?idCommissione='+idCommissione +'&orarioInizio='+orarioInizio+'&orarioFine='+orarioFine;
								
								const response = await fetch(url, {
									 method: 'POST'
								});
								
								let messaggio = await response.text();
								window.vbg.nascondiModalCaricamento();
								divMessaggio.innerHTML = messaggio;
								divMessaggio.setAttribute("title",messaggio );
								if(response.status === 200){
									divMessaggio.classList.remove("messaggio_status_errore");
									divMessaggio.classList.add("messaggio_status_successo");								
									setTimeout(()=>{ 
										divMessaggio.style.display="none"; 
										divMessaggio.innerHTML = "";
									}, 2000);
								}else {
									divMessaggio.classList.remove("messaggio_status_successo");
									divMessaggio.classList.add("messaggio_status_errore");	
								}
							}
						});
					}
					
					document.querySelectorAll('.togli-pratica').forEach(
						x => {
							
							x.title='<fmt:message key="label.elimina" />';
							
							x.addEventListener('click', (e) => {
								e.preventDefault();
								let idRiga = x.parentElement.parentElement.dataset.id;
								doHref('deleteCommissioniedilizieR.htm?codiceCommissioneR=' + idRiga,'<fmt:message key="javascript.confirm.delete" />');
							});
						}
					);
										
					const modal = document.getElementById('vbg-modal-aggiungi-soggetto');
					
					if( modal ) {
						
						let checkSelezionaTutti = document.getElementById('check_tutti');
						
						checkSelezionaTutti.addEventListener('click', async (e) => {							
							document.getElementsByName('chksoggetto').forEach(
								x => {
									x.checked = checkSelezionaTutti.checked;
								}
							);
							
						});

						let btnAggiungiSoggetto = document.querySelectorAll('.aggiungi-soggetto');
						
						btnAggiungiSoggetto.forEach(
							x => {
								x.title='<fmt:message key="label.convoca_soggetto" />';
								x.addEventListener('click', async (e) => {
									modal.dataset.id = x.parentElement.parentElement.dataset.id;
									e.preventDefault();									
									window.vbg.mostraModalCaricamento();
									const response = await fetch("../commissioniediliziet/ajaxElencoSoggettiIstanza.htm?idRiga=" + x.parentElement.parentElement.dataset.id, {
						                method: "POST",
						                cache: "no-cache",
						                headers: {
						                    'Content-Type': 'application/json'
						                }
									});
									
									let ris = await response.text();
								 	let risJson = JSON.parse(ris);													
								 	
									document.getElementById('riferimentiistanza').innerHTML = risJson.riferimentiistanza;
									document.getElementById('riferimentiprotocollo').innerHTML = risJson.riferimentiprotocollo;	

									let table = document.getElementById('lista-soggetti');
									table.removeChild(table.getElementsByTagName("tbody")[0]);
									table.createTBody();
									let tableBody = table.getElementsByTagName('tbody')[0];
									
									for(var i = 0; i < risJson.soggetti.length; i++) {
										
										let row = tableBody.insertRow();
										
										let cellCheck = row.insertCell();
										let cellSoggetto = row.insertCell();
										let cellQualifica = row.insertCell();
										let cellCarica = row.insertCell();
										
										row.setAttribute('class','riga-soggetti');
										
										var chk = document.createElement('input');
				                        chk.setAttribute('type', 'checkbox');
				                        chk.setAttribute('value', risJson.soggetti[i].codiceanagrafe);
				                        chk.setAttribute('name', 'chksoggetto');
				                        chk.checked = risJson.soggetti[i].associatoapratica;

				                        let sel = document.createElement('select');
				                        let opt1 = document.createElement('option');				                        
				                        opt1.text = "Seleziona";
				                        opt1.value = "";
				                        sel.appendChild(opt1);
				                        				                        
				                        document.querySelectorAll('.righe-carica').forEach(
				                        		(e) => {
				                        			
				                        			let option = document.createElement('option');				                        			
					                        		
				                        			option.value = e.dataset.id;
					                        		option.text = e.value;
					                        		
					                        		if(risJson.soggetti[i].idCarica === undefined){
					                        			opt1.selected = true;
					                        		}
					                        		
					                        		if(e.dataset.id === ""+risJson.soggetti[i].idCarica){
					                        			option.selected = true;
					                        			sel.remove(opt1);
					                        		}
					                        		
				                        		sel.appendChild(option);				                        		
				                        		}
				                        );
				                        
				                        cellCheck.appendChild(chk);
										cellSoggetto.appendChild(document.createTextNode(risJson.soggetti[i].soggetto));
										cellQualifica.appendChild(document.createTextNode(risJson.soggetti[i].qualifica));
										cellCarica.appendChild(sel);										
										
									}
									
									showModal(x.parentElement.parentElement.dataset.id);
									
									window.vbg.nascondiModalCaricamento();
								});
							}		
						);						
					}
									
										
					function showModal(idRigaCommissione){
						document.getElementById('check_tutti').checked = false;
						modal.open();
					}
				});		
				
				var _jmesaUrl='listCommissioniedilizieR.htm?codiceCommissione=${commissione.id}&';
				var _captionTab='<fmt:message key="label.istanze_in_discussione"/>';	
				
				
				function tabshowFiltro(){
					dijit.byId('filtro').show();
					showFiltro();		
				}
				
				function showFiltro() {
					var ts=(new Date()).getTime();
					new Ajax.Request(
							'${pageContext.request.contextPath}/commissioniediliziet/ajaxShowFiltro.htm?ts_='+ts+'&codiceCommissioneT='+${commissione.id},
							{
								method : 'post',
								onSuccess : function(transport) {							
									var response = transport.responseText;							
									$('filtroContent').innerHTML = parseAjaxResponse(response,true,false);
									applyStyle();
								},
								onFailure : function(transport) {
									var response = transport.responseText;
									alert(response);
								}
							});
				}
				
				// Javascrip per mostrare il calendario (Viene richiamto all'interno della jsp ajax/listInventarioprocedimenti.jsp)
				
				function setupCal(inputId, imageId){
					RANGE_CAL_1 = new Calendar({
							inputField: inputId,
							dateFormat: "%d/%m/%Y",
							trigger: imageId,
							bottomBar: false,
							onSelect: function() {
						var date = Calendar.intToDate(this.selection.get());
						this.hide();
					}
					})
				
				}
				
				function isDataPresent()
				{
					if(document.getElementById('data_id').value=='')
					{
						alert('Data Obbligatoria');
						return false;
					}
					return true;
				}
				
				function seachIstazeDaDiscutere()
				{
					doSubmit('searchIstanzeInCommissione.htm','',document.innnerForm);
				}
				
				
			</script>	
		</spring-form:form>
	</div>
	 
	<div>
		<c:if test="${commissione.aperta eq true}">
			<a class="btn btn-primary" href="javascript:tabshowFiltro();"><fmt:message key="button.new" /></a>	
			<%-- <a id="btnRiordina" class="btn btn-primary" href="javascript:doSubmit('riordinaCommissioniedilizieR.htm','',document.inviodati)"><fmt:message key="button.riordina" /></a> --%>
		</c:if>	
		<a class="btn btn-secondary" href="javascript:doHref('view.htm?codice=${commissione.id}','')"><fmt:message key="button.back" /></a>		
	</div>
	
	<jsp:include page="popupAggiungiSoggettoPratica.jsp"></jsp:include>
	
</body>
</html>