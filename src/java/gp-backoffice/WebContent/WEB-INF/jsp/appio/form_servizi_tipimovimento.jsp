<<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<fmt:message key="appio.label.title" />
		</title>
		

	</head>
	<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/custom-components/marked.min.js"></script>
	<script type="text/javascript">
		
			vbg.ready(() => {
				document.getElementById('salva_configurazione').addEventListener('click',()=>{
					
					salvaConfigurazione();
				});
				
			});		
		
			function checkTipomovimento(inputField,listItem){
				
				var idElemento='tipoMovimentoInputId';
				var a = listItem.id;
				document.getElementById(idElemento+'_hidden').value = a;
				if($('id2_'+idElemento).style.display == 'inline'){
					document.getElementById(idElemento+'_id2').value = inputField.value;
					$(idElemento+'_id2'+'_choices').fade();
				}else{
					document.getElementById(idElemento+'_id1').value = inputField.value;
					$(idElemento+'_id1'+'_choices').fade();
				}
				
			}				

			
			function  salvaConfigurazione(){
				
				let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
				let servizio = document.getElementById('servizi_id').value;
				
			    if(tipoMovimento==='' || servizio===''){
			    	alert("Attenzione! non sono stati specificati i dati obbligatori");
			    	return;
			    }
			<c:choose>
				<c:when test="${create eq true}"> 
					doSubmit('insertservizitipimovimento.htm','',document.inviodati);
				</c:when>	
				<c:otherwise>
					doSubmit('updateservizitipimovimento.htm','',document.inviodati);
				</c:otherwise>
			</c:choose>
			    
				
			}
		
			
			async function elimina(	){
				
				let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
				let servizio = document.getElementById('servizi_id').value;
				
				if(confirm('<fmt:message key="javascript.confirm.delete"/>')){
						window.vbg.mostraModalCaricamento();
						const response = await fetch('../appioserviziconfig/jsonEliminaTipimovimentoServizio.htm?idservizio='+servizio+'&tipomovimento='+tipoMovimento, {
		            		method: 'POST',
		                    headers: {
		                        'Accept': 'application/json',
		                        'Content-Type': 'application/json',
		                    }
		                    
		            	});
						
						if( await response.status == 200){
							
							doHref('../appioserviziconfig/listservizitipimovimento.htm?idservizio='+servizio+"&status_msg=05");            		
		            	} else {
		            		_gestioneErrori(await response.json());
		            	}
						
						
						window.vbg.nascondiModalCaricamento();
				
				}
			}
			
			function _gestioneErrori(errJson){
	     		console.log(errJson.error);
	     		alert( errJson.error);
				}
			
			
		</script>
		<span class="titoloPagina">
		<fmt:message key="appio.label.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../appioserviziconfig/createservizitipimovimento" />
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
		 		<jsp:include page="../includes/displayGlobalMessages.jsp" >
					<jsp:param name="movimentiCommand" value="inviodati" />
				</jsp:include>
				
				<fieldset>
					<legend>
						<fmt:message key="appio.label.tipomovimento_config" />
					</legend>
					<spring-form:form commandName="tmovioservizi" name="inviodati">
						<c:choose>
							<c:when test="${create eq true}"> 
								<div class="form-group">
									<label><fmt:message key="label.tipomovimento" /></label>
									<jsp:include page="../includes/tipimovimentosearch.jsp" >
										<jsp:param name="idElemento" value="tipoMovimentoInputId" />
										<jsp:param name="pathTipomovimento" value="tipimovimento" />
										<jsp:param name="afterUpdateElement" value="checkTipomovimento" />
										<jsp:param name="readOnly" value="${readOnly}" />					
									</jsp:include>						
								</div>						
								
							</c:when>
							<c:otherwise>
							<div class="form-group">
									<label><fmt:message key="label.tipomovimento" /></label>
									${tmovioservizi.tipimovimento.descrizioneEstesa }
									<spring-form:hidden id="tipoMovimentoInputId_hidden" path="id.tipomovimento" />
							</div>			
		
							</c:otherwise>
						</c:choose>
						<div class="form-group">
									<label><fmt:message key="appio.label.servizio" /></label>
									${tmovioservizi.appIoServizi.descrizione} (${tmovioservizi.appIoServizi.id.identificativoServizio})
									<spring-form:hidden id="servizi_id"  path="id.identificativoServizio" />
						</div>							
						<div class="form-group">
							<label><fmt:message key="appio.label.ogg_msg" /></label>
							<spring-form:textarea id="templateOggetto_id"  path="templateOggetto" />
							<spring-form:errors path="templateOggetto"/>
						</div>
						<div class="form-group">
							<label><fmt:message key="appio.label.msg" /></label>
							<spring-form:textarea id="templateMessaggio_id" path="templateMessaggio" />
							<spring-form:errors path="templateMessaggio"/>
						</div>		
					</spring-form:form>
					<div class="form-button">
						<a id="salva_configurazione" class="btn btn-primary"><fmt:message key="button.save" /></a>
						<c:choose>
							<c:when test="${create ne true}"> 
								<a href="javascript:elimina()" class="btn btn-secondary">
									<fmt:message key="label.elimina" />
								</a>
							</c:when>	
						</c:choose>
						<a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')" class="btn btn-secondary">
							<fmt:message key="button.close" />
						</a>
					</div>
				</fieldset>
				<c:if test="${create ne true}">
				
				
										<div class="form-group">

											<h2>Stai ricercando le informazioni sul modulo
											<span style="color: red; font-size: 1.2em">&lt;<c:import url="/ajax/findCurrentSoftware.htm" />&gt;</span> 
											scegli un altro modulo per cambiare la ricerca
											</h2>
										<jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
											<jsp:param name="idElemento" value="modulo_id" />		
											<jsp:param name="pathPropertyDescription" value="software_descrizione" />
											<jsp:param name="pathPropertyCode" value="software_codice" />
											<jsp:param name="autocompleterAjax" value="findSoftwareAbilitatiResponsabile.htm?escludiNonOpzionali=false" />
											<jsp:param name="afterUpdateElement" value="impostaSoftware" />
											<jsp:param name="titleKey" value="label.ricerca_software" />
										</jsp:include>
					
												
																						

											
										</div>
				
				
				
				<fieldset  >
					<legend>
						<fmt:message key="label.interventi_selezionati" />
					</legend>
					<div style="font-size: 1.17em; margin-block-start: 1em;margin-block-end: 1em;margin-inline-start: 0px;margin-inline-end: 0px;font-weight: bold;"><fmt:message key="appio.label.interventi_selezionati.help" /></div>

					<div class="btn-foot" style="margin-bottom: 20px;">
					 	<a class="cmd-aggiungi-intervento" href="javascript:void(0)">
	                          <i class="fa fa-plus-circle"></i>
	                          <fmt:message key="label.ricerca_intervento" />
	                      </a>
					</div>
						
<div id="selezione_intervento" style="display: none;">

	
		<div id="interventi-store">
			
			<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
				
				<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
				
				<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
				
				
			   	<script type="dojo/method" event="onClick" args="item">
		

    

		if(item.id != '0'){
			<%--
					//Determine the attributes we need to process.
		    		var attributes = alberoprocStore.getAttributes(item);
		    		if (attributes && attributes.length > 0) {
		      		var i;
		      			for (i = 0; i < attributes.length; i++) {
							alert(attributes[i]);
							var values = alberoprocStore.getValues(item, attributes[i]);
							alert(values);	
		      			}
		    		}
					*/
					--%>
			var itemId = alberoprocStore.getValue(item, "id");
			var descrizioneEstesa = alberoprocStore.getValue(item, "descrizioneEstesa");
			var padre = alberoprocStore.getValue(item, "padre");
			cercaProcedimentoAjax(itemId, descrizioneEstesa)
		}

   		</script>
			
			   <script type="dojo/method" event="getIconClass" args="item, opened">
		if(item.id != '0'){
			var dis = 'false';
			if(item){
				dis = alberoprocStore.getValue(item, "disabilitato");
			}
			if(dis == 'false'){
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
			}else{
				return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
			}
		}else{
			return "dijitFolderOpened"
		}
   		</script>   						
</div>						
			</div>
									
									<div id="mostra_intervento_scelto_id" style="font-weight: bolder; font-size: 2em; color: maroon; padding: 20px;">
									
									</div>
									<a name="ancorainterventi" style="display: none;">..</a>
									<input type="hidden" id="intervento_scelto_id" >   		
									<div class="form-group">
										<label><fmt:message key="appio.label.ogg_msg" /></label>
									
										<textarea id="templateOggetto_intervento_id" cols="50" rows="10" name="templateOggetto_intervento_id"  ></textarea>
										
									</div>
									<div class="form-group">
										<label><fmt:message key="appio.label.msg" /></label>
										
										<textarea id="templateMessaggio_intervento_id"  cols="50" rows="10" name="templateMessaggio_intervento_id" ></textarea>
																			
									</div>							
									
									
					
				
				<div>
					<div class="btn btn-primary" id="saveButtonModalSelezioneIntervento"><fmt:message key="button.save"/></div>			
					<div class="btn btn-secondary" id="closeButtonModalSelezioneIntervento"><fmt:message key="button.close"/></div>            
				</div>		
		
</div> 	
					

					
						
					
	<div id="tabella_interventi">
	</div>
					
					
				</fieldset>
				 

<fieldset >
					<legend>
						<fmt:message key="alberoproc.label.endoprocedimenti" />
					</legend>
					
					
					
					<vbg-modal id="selezione_endo">
						<div slot='body' id='selezione_endo_id_content'>
							
						

					
										<div class="form-group">
											<label>
												<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
											</label>
											
											<jsp:include page="../includes/autocompletergenerico-no-bind-TT.jsp" >
												<jsp:param name="idElemento" value="famigliaendo" />																		
												<jsp:param name="pathPropertyDescription" value="inventarioprocedimento.tipoendo.tipifamiglieendo.tipo" />
												<jsp:param name="pathPropertyCode" value="inventarioprocedimento.tipoendo.tipifamiglieendo.id.codice" />
												<jsp:param name="autocompleterAjax" value="findTipifamiglieendo.htm?codicesoftware=" />	
												<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
												<jsp:param name="id_help" value="help_famiglia" />
												<jsp:param name="help" value="help.search_famiglie_e_categorie_endo_archivi_base" />
											</jsp:include>
											<fmt:message key="help.ricerca_per_software_TT" />
											
										</div>
										<div class="form-group">
											<label>
												<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
											</label>
											
												<script type="text/javascript">
													function filtertipiendo(element, entry) { 
														return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
													}
												</script>
												
												<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
												<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendo.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
												<input name="inventarioprocedimento.tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
											
										</div>
										<div class="form-group">
											<label>
												<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
											</label>
											
												<script type="text/javascript">
													function inventarioCallBack(inputField,listItem){
														var a = listItem.id;
														document.getElementById('inventarioprocedimento_id').value = inputField.value;
														document.getElementById('inventarioprocedimento_hidden').value = a;
														document.getElementById('inventarioprocedimento_id_hidden').value = a;
														$('inventarioprocedimento_id_choices').fade();	
													}
													function filterinventario(element, entry) { 
														return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
													}
												</script>
												<input type="text" id="inventarioprocedimento_id" path="inventarioprocedimento.procedimento" 
													class="searchbox" 
													onchange="checkValue(this,'inventarioprocedimento_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
												<init:autocompleter methodAjax="findInventarioprocByFamigliaAndCategoriaEndoAndSoftware.htm" idHidden="inventarioprocedimento_hidden"
													 idInput="inventarioprocedimento_id" callBack="filterinventario" afterUpdateElement="inventarioCallBack" inputTitleKey="label.ricerca_inventarioprocedimento"/>
												<input type="hidden" id="inventarioprocedimento_hidden" name="inventarioprocedimento.id.codice"  />
												<input type="hidden" id="inventarioprocedimento_id_hidden" name="id.codiceinventario"  />												
											
										</div>

									
										<div class="form-group">
											<label><fmt:message key="appio.label.ogg_msg" /></label>
										
											<textarea id="templateOggetto_endo_id" cols="50" rows="10" name="templateOggetto_endo_id"  ></textarea>
											
										</div>
										<div class="form-group">
											<label><fmt:message key="appio.label.msg" /></label>
											
											<textarea id="templateMessaggio_endo_id"  cols="50" rows="10" name="templateMessaggio_endo_id" ></textarea>
																				
										</div>
									 	
										
										
								</div>
								<div slot='footer'>
									<div class="btn btn-primary" id="saveButtonModalSelezioneEndo"><fmt:message key="button.save"/></div>			
									<div class="btn btn-secondary" id="closeButtonModalSelezioneEndo"><fmt:message key="button.close"/></div>            
								</div>		
					</vbg-modal>					
										<div class="btn-foot">
										 	<a class="cmd-aggiungi-endo" href="javascript:void(0)">
					                           <i class="fa fa-plus-circle"></i>
					                           <fmt:message key="label.ricerca_inventarioprocedimento" />
					                       </a>
										</div>
										<div id="tabella_endo">
					
										</div>
					
					
					
				</fieldset>							
				
				
				
				</c:if>
				



	
	
	 
<vbg-modal id="selezione_aggiorna_endo">

		<div slot='body' id='selezione_aggiorna_id_content'>
						<input type="hidden" id="codiceinventario_update_id" />
						<div class="form-group">
							<label><fmt:message key="appio.label.ogg_msg" /></label>
							<textarea id="templateOggetto_endo_updateendo_id" cols="50" rows="10" name="templateOggetto_endo_updateendo_id"  ></textarea>
						</div>
						<div class="form-group">
							<label><fmt:message key="appio.label.msg" /></label>
							<textarea id="templateMessaggio_update_endo_id"  cols="50" rows="10" name="templateMessaggio_update_endo_id" ></textarea>
						</div>
				</div>
				<div slot='footer'>
					<div class="btn btn-primary" id="saveButtonModalAggiornaEndo"><fmt:message key="button.save"/></div>			
					<div class="btn btn-secondary" id="closeButtonModalAggiornaEndo"><fmt:message key="button.close"/></div>            
				</div>		
</vbg-modal>	

<vbg-modal id="selezione_aggiorna_intervento">

		<div slot='body' id='selezione_aggiorna_int_id_content'>
						<input type="hidden" id="codiceintervento_update_id" />
						<div class="form-group">
							<label><fmt:message key="appio.label.ogg_msg" /></label>
							<textarea id="templateOggetto_intervento_updateintervento_id" cols="50" rows="10" name="templateOggetto_intervento_updateintervento_id"  ></textarea>
						</div>
						<div class="form-group">
							<label><fmt:message key="appio.label.msg" /></label>
							<textarea id="templateMessaggio_update_intervento_id"  cols="50" rows="10" name="templateMessaggio_update_intervento_id" ></textarea>
						</div>
				</div>
				<div slot='footer'>
					<div class="btn btn-primary" id="saveButtonModalAggiornaIntervento"><fmt:message key="button.save"/></div>			
					<div class="btn btn-secondary" id="closeButtonModalAggiornaIntervento"><fmt:message key="button.close"/></div>            
				</div>		
</vbg-modal>	
	
	
<vbg-modal id="feedback_id">
	<div slot='body' id='feedback_id_content'>
		
	</div>
	<div slot='footer'>			
		<div class="btn btn-primary" id="closeButtonModalFeedback"><fmt:message key="button.close"/></div>            
	</div>		
</vbg-modal>	
	
	
	
</div>				
				
			</div>
			
			
	




		
<script type="text/javascript">



			const modalFeedback = document.getElementById('feedback_id');
			const modalFeedbackContent = document.getElementById('feedback_id_content');
			const tabella_interventi_div = document.getElementById('tabella_interventi');
			const tabella_endo_div = document.getElementById('tabella_endo');
			const bottoneAggiungiEndo = document.querySelector(".cmd-aggiungi-endo");
			const bottoneAggiungiIntervento = document.querySelector(".cmd-aggiungi-intervento");
			

			// selezione endo
			const modalTabellaSelezioneEndo = document.getElementById('selezione_endo');
			const saveSelezioneEndo = document.getElementById('saveButtonModalSelezioneEndo');
			const closeSelezioneEndo = document.getElementById('closeButtonModalSelezioneEndo');
			
			// aggiornamento Endo
			const modalTabellaAggiornaEndo  = document.getElementById('selezione_aggiorna_endo');
			const saveAggiornaEndo = document.getElementById('saveButtonModalAggiornaEndo');
			const closeAggiornaEndo = document.getElementById('closeButtonModalAggiornaEndo');
			
			
			// intervento
			const modalTabellaSelezioneIntervento = document.getElementById('selezione_intervento');
			const saveSelezioneIntervento = document.getElementById('saveButtonModalSelezioneIntervento');
			const closeSelezioneIntervento = document.getElementById('closeButtonModalSelezioneIntervento');


			const campoInterventoScelto = document.getElementById('intervento_scelto_id');	
			const mostraInterventoScelto = document.getElementById('mostra_intervento_scelto_id');
			
			
			const modalTabellaAggiornaIntervento  = document.getElementById('selezione_aggiorna_intervento');
			const saveAggiornaIntervento = document.getElementById('saveButtonModalAggiornaIntervento');
			const closeAggiornaIntervento = document.getElementById('closeButtonModalAggiornaIntervento');
		
			const interventiStore = document.getElementById('interventi-store');
			
			
			vbg.ready(() => {
				
				closeAggiornaEndo.addEventListener('click', (e)=>{
					
					modalTabellaAggiornaEndo.close();		
				}); 
				
				closeSelezioneEndo.addEventListener('click', (e)=>{
					
					modalTabellaSelezioneEndo.close();		
				});
				
				
				closeSelezioneIntervento.addEventListener('click', (e)=>{
					
					modalTabellaSelezioneIntervento.style.display='none';		
				});
				
				closeAggiornaIntervento.addEventListener('click', (e)=>{
					
					modalTabellaAggiornaIntervento.close();		
				}); 
				document.getElementById('closeButtonModalFeedback').addEventListener('click', (e)=>{
					modalFeedback.close();		
				});
				
				if (bottoneAggiungiEndo) {
					bottoneAggiungiEndo.addEventListener("click", async (e) => {
				      console.log("Aggiungi Endo");
				      modalTabellaSelezioneEndo.open();
				      
				    });
				}
				if (bottoneAggiungiIntervento) {
					bottoneAggiungiIntervento.addEventListener("click", async (e) => {
					  let displayVar= modalTabellaSelezioneIntervento.style.display=='none'?'':'none';
				      console.log("Aggiungi Intervento");
					  campoInterventoScelto.value='';	
					  mostraInterventoScelto.innerHTML='';
					  interventiStore.style.display=displayVar;
					  modalTabellaSelezioneIntervento.style.display=displayVar;	
				      
				    });
				}
				
				
				if (saveSelezioneEndo) {
					
					saveSelezioneEndo.addEventListener("click", async (e) => {
				      console.log("Aggiungi Endo Insert");
				     
				      	aggiungiEndo();
				      
				    });
				  }
				
				saveAggiornaEndo.addEventListener("click", async (e) => {
				      console.log("Aggiorna Endo Update");
					     
				      modificaEndo();
				      
				});

				
				if(saveSelezioneIntervento){
					saveSelezioneIntervento.addEventListener("click", async (e) => {
					     console.log("Aggiungi Intervento Insert");
					     
					     salvaProcedimentoAjax();
					      
					    });					
				}
				
				if(saveAggiornaIntervento){
					saveAggiornaIntervento.addEventListener("click", async (e) => {
					      console.log("Aggiorna Intervento Update");
						     
					      modificaIntervento();
					      
					});
				}
				async function caricaEndo(){
					
					if(tabella_endo_div){
						
							let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
							let servizio = document.getElementById('servizi_id').value;
							
						 	const formData = new FormData();
						    formData.append('id.identificativoServizio',servizio);
						    formData.append('id.tipomovimento',tipoMovimento);
						    
						    window.vbg.mostraModalCaricamento();
						    
						    const response = await fetch('ajaxCaricaTabellaEndo.htm', {
					                method: 'POST',
					                body: formData
					            });
						    
						    if( await response.status == 200){
						    						    	
						    	tabella_endo_div.innerHTML = await response.text();
						    	
						    	let azioni = document.querySelectorAll('.azioni-elimina-endo');
						    	for (var i = 0, len = azioni.length; i < len; i++) {
						    		
						    		azioni[i].addEventListener('click', (e) => {
						    			  console.log("Elimina Endo");
									      	eliminaEndo(e.target);
						    		  });
						    	}

						    	let azioniUpdate = document.querySelectorAll('.azioni-aggiorna-endo');
						    	for (var i = 0, len = azioniUpdate.length; i < len; i++) {
						    		azioniUpdate[i].addEventListener('click', (e) => {
						    			  console.log("Elimina Endo");
									      mostraAggiornaEndo(e.target);
						    		  });
						    	}
						    	
						    	let anteprimaMarkDownEls = document.querySelectorAll('.anteprima_markdown');
						    	for (var i = 0, len = anteprimaMarkDownEls.length; i < len; i++) {
						    		let messaggio = anteprimaMarkDownEls[i].dataset.messaggio;
						    		anteprimaMarkDownEls[i].innerHTML = marked.parse(messaggio);
						    	}
						    	
						    }
						    
						    window.vbg.nascondiModalCaricamento();
						
						
					}
					
				}
				
				function mostraAggiornaEndo(el){
					console.log("Aggiungi Endo");
					document.getElementById('templateOggetto_endo_updateendo_id').value = el.dataset.templateoggetto;
					document.getElementById('templateMessaggio_update_endo_id').value = el.dataset.templatemessaggio;
					document.getElementById('codiceinventario_update_id').value = el.dataset.codiceinventario;
					
					modalTabellaAggiornaEndo.open();
					
				}

				async function aggiungiEndo(){
					
					let codiceInventarioEl =   document.getElementById('inventarioprocedimento_id_hidden');
					let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
					let servizio = document.getElementById('servizi_id').value;
					
					if(codiceInventarioEl && codiceInventarioEl.value!=''){
						
						window.vbg.mostraModalCaricamento();
						const formData = new FormData();
					    formData.append('id.identificativoServizio',servizio);
					    formData.append('id.tipomovimento',tipoMovimento);
					    formData.append('codiceInventario',codiceInventarioEl.value);
					    formData.append('templateOggetto',document.getElementById('templateOggetto_endo_id').value);
					    formData.append('templateMessaggio',document.getElementById('templateMessaggio_endo_id').value);
					    
					    const response = await fetch('ajaxInserisciEndo.htm', {
				                method: 'POST',
				                body: formData
				            });
					    
					    let messaggio = '';
					    messaggio = await response.text();
					    modalFeedbackContent.innerHTML = messaggio;
					    modalFeedback.open();			
				    	await caricaEndo();
					    window.vbg.nascondiModalCaricamento();
						
					}
				}
				
				
				async function modificaEndo(){
					
					let codiceInventarioEl = document.getElementById('codiceinventario_update_id');
					let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
					let servizio = document.getElementById('servizi_id').value;
					
					if(codiceInventarioEl && codiceInventarioEl.value!=''){
						
							window.vbg.mostraModalCaricamento();
							const formData = new FormData();
						    formData.append('id.identificativoServizio',servizio);
						    formData.append('id.tipomovimento',tipoMovimento);
						    formData.append('codiceInventario',codiceInventarioEl.value);
						    formData.append('templateOggetto',document.getElementById('templateOggetto_endo_updateendo_id').value);
						    formData.append('templateMessaggio',document.getElementById('templateMessaggio_update_endo_id').value);
						    
						    const response = await fetch('ajaxModificaEndo.htm', {
					                method: 'POST',
					                body: formData
					            });
						    
						    let messaggio = '';
						    messaggio = await response.text();
						    modalFeedbackContent.innerHTML = messaggio;
							window.vbg.nascondiModalCaricamento();
						    modalFeedback.open();
					    	await caricaEndo();
						    
					}
				}
				
			async function eliminaEndo(el){
					
					let codiceInventario = el.dataset.codiceinventario;
					let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
					let servizio = document.getElementById('servizi_id').value;
					
					if(codiceInventario && codiceInventario.value!=''){
						if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
							window.vbg.mostraModalCaricamento();
							const formData = new FormData();
						    formData.append('id.identificativoServizio',servizio);
						    formData.append('id.tipomovimento',tipoMovimento);
						    formData.append('codiceInventario',codiceInventario);
	
						    const response = await fetch('ajaxEliminaEndo.htm', {
					                method: 'POST',
					                body: formData
					            });
						    
						    let messaggio = '';
						    messaggio = await response.text();
						    modalFeedbackContent.innerHTML = messaggio;
						    modalFeedback.open();			
					    	await caricaEndo();
						    window.vbg.nascondiModalCaricamento();
						}
					}
				}
				

			<%--
			----------------------------------------------------------------------------------------
			--------------------------------inizio INTERVENTI-----------------------------------------
			----------------------------------------------------------------------------------------				
			 --%>
				
				async function  caricaInterventi(){
					
					if(tabella_interventi_div){
						
							let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
							let servizio = document.getElementById('servizi_id').value;
							
						 	const formData = new FormData();
						    formData.append('id.identificativoServizio',servizio);
						    formData.append('id.tipomovimento',tipoMovimento);
						    
						    window.vbg.mostraModalCaricamento();
						    
						    const response = await fetch('ajaxCaricaTabellaIntervento.htm', {
					                method: 'POST',
					                body: formData
					            });
						    
						    if( await response.status == 200){
						    						    	
						    	tabella_interventi_div.innerHTML = await response.text();
						    	
						    	let azioni = document.querySelectorAll('.azioni-elimina-intervento');
						    	for (var i = 0, len = azioni.length; i < len; i++) {
						    		
						    		console.log(azioni[i]);
						    		azioni[i].addEventListener('click', (e) => {
						    			  console.log("Elimina Intervento");
									      	eliminaIntervento(e.target);
						    		  });
						    	}
	
						    	let azioniUpdate = document.querySelectorAll('.azioni-aggiorna-intervento');
						    	for (var i = 0, len = azioniUpdate.length; i < len; i++) {
						    		
						    		console.log(azioniUpdate[i]);
						    		azioniUpdate[i].addEventListener('click', (e) => {
						    			  console.log("Mostra aggiorna Intervento");
									      mostraAggiornaIntervento(e.target);
						    		  });
						    	}
						    	let anteprimaMarkDownEls = document.querySelectorAll('.anteprima_markdown');
						    	for (var i = 0, len = anteprimaMarkDownEls.length; i < len; i++) {
						    		let messaggio = anteprimaMarkDownEls[i].dataset.messaggio;
						    		anteprimaMarkDownEls[i].innerHTML = marked.parse(messaggio);
						    	}
						    	
						    }
						    
						    window.vbg.nascondiModalCaricamento();
					}
				}
				
				
				async function modificaIntervento(){
					
					let codiceInterventoEl = document.getElementById('codiceintervento_update_id');
					let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
					let servizio = document.getElementById('servizi_id').value;
					
					if(codiceInterventoEl && codiceInterventoEl.value!=''){
						
							window.vbg.mostraModalCaricamento();
							const formData = new FormData();
						    formData.append('id.identificativoServizio',servizio);
						    formData.append('id.tipomovimento',tipoMovimento);
						    formData.append('codiceIntervento',codiceInterventoEl.value);
						    formData.append('templateOggetto',document.getElementById('templateOggetto_intervento_updateintervento_id').value);
						    formData.append('templateMessaggio',document.getElementById('templateMessaggio_update_intervento_id').value);
						    
						    const response = await fetch('ajaxModificaIntervento.htm', {
					                method: 'POST',
					                body: formData
					            });
						    
						    let messaggio = '';
						    messaggio = await response.text();
						    modalFeedbackContent.innerHTML = messaggio;
							window.vbg.nascondiModalCaricamento();
						    modalFeedback.open();
					    	await caricaInterventi();
						    
					}
				}
				
			async function eliminaIntervento(el){
					
					let codiceIntervento = el.dataset.codiceintervento;
					let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
					let servizio = document.getElementById('servizi_id').value;
					
					if(codiceIntervento && codiceIntervento.value!=''){
						if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
							window.vbg.mostraModalCaricamento();
							const formData = new FormData();
						    formData.append('id.identificativoServizio',servizio);
						    formData.append('id.tipomovimento',tipoMovimento);
						    formData.append('codiceIntervento',codiceIntervento);
	
						    const response = await fetch('ajaxEliminaIntervento.htm', {
					                method: 'POST',
					                body: formData
					            });
						    
						    let messaggio = '';
						    messaggio = await response.text();
						    modalFeedbackContent.innerHTML = messaggio;
						    modalFeedback.open();			
					    	await caricaInterventi();
						    window.vbg.nascondiModalCaricamento();
						}
					}
				}
				

				
				function mostraAggiornaIntervento(el){
					
					console.log("Aggiungi Intervento");
					document.getElementById('templateOggetto_intervento_updateintervento_id').value = el.dataset.templateoggetto;
					document.getElementById('templateMessaggio_update_intervento_id').value = el.dataset.templatemessaggio;
					document.getElementById('codiceintervento_update_id').value = el.dataset.codiceintervento;
					
					modalTabellaAggiornaIntervento.open();
					
				}
				
				async function salvaProcedimentoAjax(){
					
					let tipoMovimento = document.getElementById('tipoMovimentoInputId_hidden').value;
					let servizio = document.getElementById('servizi_id').value;
									
					window.vbg.mostraModalCaricamento();
					const formData = new FormData();
				    formData.append('id.identificativoServizio',servizio);
				    formData.append('id.tipomovimento',tipoMovimento);
				    formData.append('codiceIntervento',campoInterventoScelto.value);
				    formData.append('templateOggetto', document.getElementById('templateOggetto_intervento_id').value);
				    formData.append('templateMessaggio', document.getElementById('templateMessaggio_intervento_id').value);
				    
				    const response = await fetch('ajaxInserisciIntervento.htm', {
			                method: 'POST',
			                body: formData
			            });
				    
				    let messaggio = '';
				    messaggio = await response.text();
				    modalFeedbackContent.innerHTML = messaggio;
				    modalFeedback.open();			
			    	await caricaInterventi();
				    window.vbg.nascondiModalCaricamento();
					
				}

				

				
				
				
				
				function feedbackConMessaggio(messaggio){
					
					modalFeedbackContent.innerHTML = messaggio;
				    modalFeedback.open();
				}
				
				<%--
				----------------------------------------------------------------------------------------
				--------------------------------FINE INTERVENTI-----------------------------------------
				----------------------------------------------------------------------------------------				
				 --%>
				
				
				async function inizializzaTabelle(){
					
					await caricaEndo();
					await caricaInterventi();
					
				}
				
				inizializzaTabelle();
				
			});
			
			function impostaSoftware(inputField ,listItem) {
				
				var a = listItem.id;
				let locationTo = document.location.href;
				console.log(locationTo);
				locationTo = locationTo.replace(/&software=[A-Z]{2}/gi, '');
				console.log(locationTo);
				document.location.href = locationTo + '&software=' + a;
				
			}
			
			function cercaProcedimentoAjax(codiceAlberoproc, descrizioneAlberoproc) {

				campoInterventoScelto.value      = codiceAlberoproc;
				let messaggio  = descrizioneAlberoproc + '('+codiceAlberoproc+')'
				mostraInterventoScelto.innerHTML = messaggio  ;
				interventiStore.style.display = 'none';
				feedbackConMessaggio('Intervento scelto '+messaggio);
			}
			
			function feedbackConMessaggio(messaggio){
				
				modalFeedbackContent.innerHTML = messaggio;
			    modalFeedback.open();
			}
</script>
		</div>
	
	</body>
</html>
