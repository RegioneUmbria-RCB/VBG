<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<fmt:message key="abbonamento.configurazione.title" />
		</title>
		<style>
			.tab {
			  border: 1px solid #ccc;
			}
			.tab .tabmenu {
  				background-color: #f1f1f1;
  				overflow: auto;
				display: flex;
				align-items: center;
			}
			.tab .tabmenu .left {
  				padding-right: 5px;
  				cursor: pointer;
  				box-shadow: 4px 1px 12px -4px rgba(0,0,0,0.75);
    			margin-left: var(--half-padding);
			}
			.tab .tabmenu .buttons {
  			  overflow: auto;
  			  white-space: nowrap;
			  width: 90vw;
			  overflow: hidden;
			  flex-grow: 1;
			}
			.tab .tabmenu .right {
  				padding-left: 5px;
  				cursor: pointer;
  				box-shadow: -4px 1px 12px -4px rgba(0,0,0,0.75);
    			margin-right: var(--half-padding);
			}
			
			.tab .tabcontent .form {
			  float: left;
			  padding: 6px 12px;
			  border-right: 1px solid #ccc;
			  
			  width: 40%;
			}
			.tab .tabcontent .preview {
			  float: left;
			  padding: 6px 12px;
			  border-top: none;
			  width: 50%;
			}

			.label_checkbox {
				font-weight: bold;
			}
			
			.input-icons input[type=checkbox] {
				margin-left: 5px !important;
				vertical-align: middle;
			}
			
			.input-icons input[type=checkbox]:hover {
				
    			box-shadow:0px 0px 10px #016C10;
			}
			
			textarea.in-fieldset {
				min-width: 0em !important;
				width: 100%;
			}			
			
            .form-control{
               text-align: right;
            }

		</style>
		<script type="text/javascript">
			function _mostraComuniSelezionati(){
				let comuni = '';
				document.querySelectorAll('vbg-abbonamento-comune-card').forEach( comune => {
					if(comune.isSelezionato()){
						comuni += comune.comune + ', ';	
					}
				})
				if(comuni != ''){
					comuni = comuni.slice(0,-2)
				}
				document.getElementById("etichetta_comune").innerText = comuni;
				
				_abilitaSalvataggioDati();
			}
			
			function _comuniSelezionati(){
				return Array
						.from(document.querySelectorAll('vbg-abbonamento-comune-card'))
						.filter( x => x.isSelezionato() )
						.map( comune => {
							return { comune: comune.codice };
						});
			}
			
			function _abilitaSalvataggioDati(){
				
				let disabilita = _comuniSelezionati().length == 0;
				let salvaMsg = document.getElementById('salva_nodo_pag');
				let salvaInfo = document.getElementById('salva_informativa');
				let salvaRicarica = document.getElementById('salva_ricarica');
				
				if( disabilita ){
					salvaMsg.setAttribute('disabled','disabled');
					salvaInfo.setAttribute('disabled','disabled');
					salvaRicarica.setAttribute('disabled','disabled');
				} else {
					salvaMsg.removeAttribute('disabled');
					salvaInfo.removeAttribute('disabled');
					salvaRicarica.removeAttribute('disabled');
				}
			}
			
			async function _recuperaDettaglio(codiceComune){
				try
				{
					const postParams = { request: { comune: codiceComune } };
					
                	const response = await fetch('../abbonamenticonfig/jsonFindConfigurazioneComune.htm', {
                		method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(postParams)
                	});
                	
                	const jsResponse = await response.json();

					return jsResponse.dettaglio;
				} 
				catch(error) {                                    
                    alert(error);
                }
				finally{                                  
                    
                }
			}
			
			async function _mostraDettaglio(container, codiceComune){
				
				let dettaglio = await _recuperaDettaglio(codiceComune);
				
				let vbgPreview = document.createElement('vbg-abbonamento-preview');
				vbgPreview.id = 'preview_' + codiceComune;
				vbgPreview.dataset = dettaglio;
				vbgPreview.addEventListener('informativaCancellata', async (e) => {
					
					window.vbg.mostraModalCaricamento();
					
					let dettaglio = await e.detail;
					let card = document.querySelector('vbg-abbonamento-comune-card[codice="' + codiceComune + '"]');
					card.numMessaggi = dettaglio.messaggi;
					card.numInformative = dettaglio.informative;
					card.numRicariche = dettaglio.ricariche;
					
					window.vbg.nascondiModalCaricamento();
				});
				vbgPreview.addEventListener('ricaricaCancellata', async (e) => {
					
					console.log('cancellata');
					
					window.vbg.mostraModalCaricamento();
					
					let dettaglio = await e.detail;
					let card = document.querySelector('vbg-abbonamento-comune-card[codice="' + codiceComune + '"]');
					card.numMessaggi = dettaglio.messaggi;
					card.numInformative = dettaglio.informative;
					card.numRicariche = dettaglio.ricariche;
					
					window.vbg.nascondiModalCaricamento();
				});
				
				
					
				container.appendChild(vbgPreview);
			}
		
			function _filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue, filtro;
				
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				
				elements = document.querySelectorAll('vbg-abbonamento-comune-card');
				
				for (let el of elements) {
					let display = '';
					
					if( document.getElementById('ricerca_messaggi').checked ) {
						if( Number(el.numMessaggi) == 0 ){
							display = 'none';
						}
					}
					
					if( document.getElementById('ricerca_informative').checked ) {
						if( Number(el.numInformative) == 0 ){
							display = 'none';
						}
					}
					
					if( document.getElementById('ricerca_ricariche').checked ) {
						if( Number(el.numRicariche) == 0 ){
							display = 'none';
						}
					}
					
					if(filter != '' && !el.comune.toUpperCase().startsWith(filter)){
						display = 'none';
					}
					
					el.style.display = display;
				}
			}
			
			async function _aggiornaDettaglio(codiceComune){
				const preview = document.getElementById('preview');
				
				_rimuoviDettaglio(preview, codiceComune);
				await _mostraDettaglio(preview, codiceComune);
			}
			
			function _rimuoviDettaglio(container, codiceComune){
				container.removeChild( document.getElementById('preview_' + codiceComune));
			}
			
			function _gestioneErrori(errJson){
        		console.log(errJson.error);
        		alert( errJson.error);
			}
			
			async function _selezionaComune(e) {
				
				e.preventDefault();
		        
				window.vbg.mostraModalCaricamento();
				
				let codice = e.detail.codice;
				let comune = e.detail.comune;
				let selezionato = e.detail.selezionato;
				
				_mostraComuniSelezionati();
				
				if( selezionato ){
					await _mostraDettaglio(preview, codice);
				} else {
					_rimuoviDettaglio(document.getElementById('preview'), codice);
				}
				
				window.vbg.nascondiModalCaricamento();

				
		    }
						
			vbg.ready(() => {
				
				const id = '${abbonamentiConfigModel.id}';
				const cards = document.querySelectorAll('vbg-abbonamento-comune-card');
				let cardIndex = 0;
				
				if(window.Prototype) {
				    delete Array.prototype.toJSON;
				}
				
				const preview = document.getElementById('preview');
				
				Array.prototype.slice.call(document.getElementsByClassName('tablinks')).forEach( button => {
					button.addEventListener('click', async function(e){
						e.preventDefault();
						
						window.vbg.mostraModalCaricamento();
						
						let comune = e.target.dataset.comune;
						let codice = e.target.dataset.codice;
						
						let mostraDettaglio = document.querySelector("div.tablinks[data-codice=" + codice + "]").classList.contains('active');
						
						if(!mostraDettaglio){
							 _rimuoviDettaglio(preview, codice);
						} else {
							await _mostraDettaglio(preview, codice);
						}
						
						window.vbg.nascondiModalCaricamento();
					});
				});
				
				let tipoRicarica = document.getElementById('tiporicarica_id');
				tipoRicarica.addEventListener('change', function(e){
					var testo = event.target.options[event.target.selectedIndex].dataset.etichetta;
					document.getElementById('etichetta_importo').title = testo;
					document.getElementById('etichetta_importo').innerHTML = testo;
				});
				
				let msgNodoPagamentiComune = document.getElementById('msgnodopagnondisp_comune_id');
				msgNodoPagamentiComune.addEventListener('keyup', function(e){
					document.querySelectorAll('vbg-abbonamento-preview').forEach( element => {
						element.scriviMessaggio(msgNodoPagamentiComune.value);
					});
				});
				
				let informativa = document.getElementById('informativa_id');
				let dataScadenzaInformativa = document.getElementById('data_fine_id');
				
				informativa.addEventListener('keyup', function(e){
					document.querySelectorAll('vbg-abbonamento-preview').forEach( element => {
						element.nuovaInformativa( informativa.value, dataScadenzaInformativa.value );
					});
				});
				
				dataScadenzaInformativa.addEventListener('change', function(e){
					document.querySelectorAll('vbg-abbonamento-preview').forEach( element => {
						element.nuovaInformativa( informativa.value, dataScadenzaInformativa.value );
					});
				});
				
				let tiporicarica = document.getElementById('tiporicarica_id');
				let importo = document.getElementById('importo_id');
				
				tiporicarica.addEventListener('change', function(e){
					document.querySelectorAll('vbg-abbonamento-preview').forEach( element => {
						element.nuovaRicarica( 
							tiporicarica.options[tiporicarica.selectedIndex].text,
							tiporicarica.options[tiporicarica.selectedIndex].dataset.etichetta,
							importo.value
						);
					});
				});
				
				
				importo.addEventListener('change', function(e){
					document.querySelectorAll('vbg-abbonamento-preview').forEach( element => {
						element.nuovaRicarica( 
							tiporicarica.options[tiporicarica.selectedIndex].text,
							tiporicarica.options[tiporicarica.selectedIndex].dataset.etichetta,
							importo.value
						);
					});
				});
				
				let salvaConfigurazione = document.querySelector('#salva_configurazione');
			
				salvaConfigurazione.addEventListener('click', async function(e){
					e.preventDefault();
					window.vbg.mostraModalCaricamento();
					
					try
					{
						let messaggio = document.getElementById('msgnodopagnondisp_id').value;
						let attivofo = document.getElementById('attivofo_id').checked;
						let tipo = document.getElementById('tipoinstallazione_id').options[document.getElementById('tipoinstallazione_id').selectedIndex].value;
						let destinatari = document.getElementById('selectdestinatari_id').options[document.getElementById('selectdestinatari_id').selectedIndex].value;
						let importomassimo = document.getElementById('importomassimoStr').value;
						
						if(tipo === ''){
							throw new Error('La tipologia è obbligatoria, selezionarne una per proseguire');
						}
						if(importomassimo && importomassimo.trim() != ''){
							var pattern = /^\d+(\.\d{1,2})?$/;
							if(!pattern.test(importomassimo)){
								throw new Error('Formato importo massimo non valido');
							}
						}else{
							importomassimo = null;
						}
						
						const postParams = { request: { messaggio, attivofo, tipo, destinatari, importomassimo } };

                    	const response = await fetch('../abbonamenticonfig/jsonUpdateConfig.htm', {
                    		method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(postParams)
                    	});
                    	
                    	if( await response.status == 200){
                    		location.replace('view.htm?software=TT');
                    	} else {
                    		_gestioneErrori(await response.json());
                    	}
					} 
					catch(error) { 
	                    alert(error);
	                }
					finally{

	                }
					
					
					window.vbg.nascondiModalCaricamento();
				});
				
				let salvaNodoPag = document.getElementById('salva_nodo_pag');
				salvaNodoPag.addEventListener('click', async function(e){
					e.preventDefault();
					
					if(!confirm('Aggiornare il messaggio per gli enti selezionati?')){
						return;
					}
					
					window.vbg.mostraModalCaricamento();
					try
					{
						const postParams = { request: { comuni: _comuniSelezionati(), messaggio: msgNodoPagamentiComune.value } };

                    	const response = await fetch('../abbonamenticonfig/jsonUpdateMsgNodoPag.htm', {
                    		method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(postParams)
                    	});
                    	
                    	if( await response.status == 200){
                    		_comuniSelezionati().forEach(async (comune) => {
                    			await _aggiornaDettaglio(comune.comune);
                    			let card = document.querySelector('vbg-abbonamento-comune-card[codice="' + comune.comune + '"]');
                    			card.numMessaggi = msgNodoPagamentiComune.value != '' ? 1 : 0;
                    		});
                    	}
					} 
					catch(error) {                                    
	                    alert(error);
	                }
					finally{

	                }
					window.vbg.nascondiModalCaricamento();
				});
				
				let salvaInformativa = document.getElementById('salva_informativa');
				let dataScadenza = document.getElementById('data_fine_id');
				let informativaTxt = document.getElementById('informativa_id');
				
				salvaInformativa.addEventListener('click', async function(e){
					e.preventDefault();
					if(!confirm('Aggiungere l\'informativa per gli enti selezionati?')){
						return;
					}
					
					window.vbg.mostraModalCaricamento();
					
					try
					{
						let comuni = _comuniSelezionati();
						let scadenza = dataScadenza.value === '' ? '' : dataScadenza.value.substring(8,10) + '/' + dataScadenza.value.substring(5,7) + '/' + dataScadenza.value.substring(0,4);
						let informativa = informativaTxt.value;
						
						const postParams = { request: { comuni, scadenza, informativa } };

                    	const response = await fetch('../abbonamenticonfig/jsonAddInformativa.htm', {
                    		method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(postParams)
                    	});
                    	
                    	if( await response.status == 200){
							_comuniSelezionati().forEach(async (comune) => {
                        		await _aggiornaDettaglio(comune.comune);
    							let card = document.querySelector('vbg-abbonamento-comune-card[codice="' + comune.comune + '"]');
    							card.numInformative += 1;
                        	});
                    	}
					} 
					catch(error) { 
	                    alert(error);
	                }
					finally{

	                }
					
					window.vbg.nascondiModalCaricamento();
				});
				
				let salvaRicarica = document.getElementById('salva_ricarica');
				salvaRicarica.addEventListener('click', async function(e){
					e.preventDefault();
					if(!confirm('Aggiungere la metodologia di ricarica per gli enti selezionati?')){
						return;
					}
					
					window.vbg.mostraModalCaricamento();
					
					try
					{
						let importoRicarica = document.getElementById('importo_id').value.replace(',','.');
						
						const postParams = { request: { comuni: _comuniSelezionati(), tipo: tipoRicarica.options[tiporicarica.selectedIndex].value , importo: importoRicarica  } };

                    	const response = await fetch('../abbonamenticonfig/jsonAddRicarica.htm', {
                    		method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(postParams)
                    	});
                    	
                    	if( await response.status == 200){
                    		
                    		const jsResponse = await response.json();
                    		
							_comuniSelezionati().forEach(async (comune) => {
								
	                        		await _aggiornaDettaglio(comune.comune);
	    							let card = document.querySelector('vbg-abbonamento-comune-card[codice="' + comune.comune + '"]');
	    							card.numRicariche += 1;
                        	});
                    	}
					} 
					catch(error) {                                    
	                    alert(error);
	                }
					finally{

	                }
					
					window.vbg.nascondiModalCaricamento();
				});
				
				let filtroTestuale = document.getElementById('input_ricerca');
				filtroTestuale.addEventListener('keyup',  function(e) {
					_filtraTestoCelle();
				});
				
				let filtroMessaggi = document.getElementById('ricerca_messaggi');
				filtroMessaggi.addEventListener('click',  function(e) {
					_filtraTestoCelle();
				});
				
				let filtroInformative = document.getElementById('ricerca_informative');
				filtroInformative.addEventListener('click',  function(e) {
					_filtraTestoCelle();
				});
				
				let filtroRicariche = document.getElementById('ricerca_ricariche');
				filtroRicariche.addEventListener('click',  function(e) {
					_filtraTestoCelle();
				});
				
				let comuneCards = document.querySelectorAll('vbg-abbonamento-comune-card');
				comuneCards.forEach( card => {
					card.addEventListener('click', this._selezionaComune)
				});
				
				let buttons = document.querySelector('.tabmenu .buttons');
				
				let left = document.querySelector('.tabmenu .left');
				let right = document.querySelector('.tabmenu .right');
				
				left.addEventListener('click',  function(e) {
					let lastElement = [...document.querySelectorAll('vbg-abbonamento-comune-card')]
					.map( (card,index) => {
						let delta = parseInt(card.offsetLeft - buttons.offsetLeft - buttons.scrollLeft);
						return delta;
					} )	
					.filter( delta => {
							
							return delta > 0;
						})
					.first();
									
					buttons.scrollLeft += lastElement;
				});
				
				
				right.addEventListener('click',  function(e) {
					let lastElement = [...document.querySelectorAll('vbg-abbonamento-comune-card')]
					.map( (card,index) => {
						let delta = parseInt(card.offsetLeft + card.offsetWidth - buttons.offsetLeft - buttons.scrollLeft - buttons.offsetWidth);	
						return delta;
					} )	
					.filter( delta => {
							
							return delta < 0;
						})
					.last();
									
					buttons.scrollLeft += lastElement;
				});
				
				_abilitaSalvataggioDati();
				
				document.querySelector('#config_enti').style.display = ( id != '' ) ? '' : 'none';
				
				
				<%-- limito input a numeri --%>
				  const    intRx = /\d/;
				  const  integerChange = (event) => {
				      console.log(event.key, )
				      if (
				        (event.key.length > 1) ||
				        event.ctrlKey ||
				        ( (event.key === "-") && (!event.currentTarget.value.length) ) ||
				        intRx.test(event.key)
				      ) return;
				      event.preventDefault();
				    };
				  
				  for (let input of document.querySelectorAll('input[type="number"][step="1"]')) {
					  input.addEventListener("keydown", integerChange);
				  }
				  <%-- limito input a numeri --%>
			});
		</script>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-abbonamento/vbg-abbonamento-comune-card.js?<%=vJS %>" defer></script>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-abbonamento/vbg-abbonamento-preview.js?<%=vJS %>" defer></script>
	</head>
	<body>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../abbonamenticonfig/view" />
		</jsp:include>
		<span class="titoloPagina">
			<fmt:message key="abbonamento.configurazione.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
					<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        		<jsp:param name="commandName" value="abbonamentiConfigModel" />
		    		</jsp:include>
		    		<spring-form:form commandName="abbonamentiConfigModel" name="inviodati">
		    			<fieldset>
			    			<legend><fmt:message key="abbonamento.configurazione.form.generale" /></legend>
			    			<div class="form-group">
			    				<label><fmt:message key="label.destinatari" /></label>
			    				<spring-form:select id="selectdestinatari_id" path="destinatari">
									<spring-form:options items="${destinatariList}" itemValue="valore" itemLabel="valore"></spring-form:options>
								</spring-form:select>	
			    			</div>
			    			<div class="form-group">
			    				<label><fmt:message key="abbonamento.configurazione.form.tipoinstallazione" /></label>
			    				<spring-form:select id="tipoinstallazione_id" path="tipoInstallazione">
									<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
									<spring-form:options items="${tipiInstallazioneList}" itemValue="valore" itemLabel="valore"></spring-form:options>
								</spring-form:select>	
			    			</div>
			    			<div class="form-group">
			    				<label><fmt:message key="abbonamento.configurazione.form.msgnodopagnondisp" /></label>
			    				<spring-form:textarea id="msgnodopagnondisp_id" path="msgNodoPagNonDisp" cols="70" rows="5" />
			    			</div>
			    			<div class="form-group">                               

                                      <label>Importo massimo</label>
                                           <input type="text"
                                               id="importomassimoStr"
                                               name="importomassimoStr"
                                               class="form-control"
                                               value="${abbonamentiConfigModel.importomassimoStr}"
                                               placeholder="es. 200 o 200.9 o 200.99"
                                               oninput="this.value=this.value.replace(/[^0-9.]/g,'')">

                                      <span style="font-weight:bold;">€</span>

                            </div>
			    			<div class="form-group">
			    				<label><fmt:message key="abbonamento.configurazione.form.attivofo" /></label>
								<spring-form:checkbox id="attivofo_id" path="attivoFo"  />
			    			</div>
							<div class="form-button">
								<a id="salva_configurazione" class="btn btn-primary">
									<fmt:message key="button.save" />
								</a>
								<a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')" class="btn btn-secondary">
									<fmt:message key="button.close" />
								</a>
							</div>
			    		</fieldset>
		    		</spring-form:form>
					<fieldset id="config_enti">
		    			<legend><fmt:message key="abbonamento.configurazione.form.comune" /></legend>
		    			<div class="form-group">
							<div class="input-icons">
								<i class="fa fa-search icon"></i>
								<input id="input_ricerca" type="text" placeholder="Cerca"/>
								<input id="ricerca_messaggi" type="checkbox" value="M"><label class="label_checkbox" for="ricerca_messaggi">Messaggi</label></input>
								<input id="ricerca_informative" type="checkbox" value="I"><label class="label_checkbox" for="ricerca_informative">Informative</label></input>
								<input id="ricerca_ricariche" type="checkbox" value="R"><label class="label_checkbox" for="ricerca_ricariche">Modalità di ricarica</label></input>
							</div>		
						</div>
		    			<div class="form-group">
			    			<div class="tab">
			    				<div class="tabmenu">
				    				<div class="left">
				    					<i class="fas fa-caret-left fa-2x"></i>
				    				</div>
				    				<div class="buttons">
										<c:forEach items="${abbonamentiConfigModel.comuni}" var="comune">
											<vbg-abbonamento-comune-card 
												codice="${comune.codiceComune}"
												comune="${comune.comune}"
												num-messaggi="${comune.messaggi}"
												num-informative="${comune.informative}"
												num-ricariche="${comune.ricariche}"
											></vbg-abbonamento-comune-card>
										</c:forEach>
									</div>
									<div class="right">
										<i class="fas fa-caret-right fa-2x"></i>
									</div>
								</div>
								<div class="tabcontent">
									<div class="form">
										<div>
											<h3 style='white-space:normal !important;'><label id="etichetta_comune"></label></h3>
										</div>
										<fieldset>
							    			<legend id="legend_msgnodopagnondisp_comune_id"><fmt:message key="abbonamento.configurazione.form.comune.msgnodopagnondisp" /></legend>
											<div class="form-group">
												<textarea id="msgnodopagnondisp_comune_id" cols="70" rows="5" class="in-fieldset"></textarea>
											</div>
											<div class="form-button">
												<a id="salva_nodo_pag" class="btn btn-primary" ><fmt:message key="button.save" /></a>
											</div>
										</fieldset>
										<fieldset>
							    			<legend id="legend_informativa_id"><fmt:message key="abbonamento.configurazione.form.comune.informative.title" /></legend>
											<div class="form-group">
							    				<label><fmt:message key="abbonamento.configurazione.form.comune.informative.datafinevalidita" /></label>
												<input id="data_fine_id" type="date" pattern="\d{4}-\d{2}-\d{2}"/>
											</div>
											<div class="form-group">
												<textarea id="informativa_id" cols="70" rows="5" class="in-fieldset"></textarea>
											</div>
											<div class="form-button">
												<a id="salva_informativa" class="btn btn-primary" ><fmt:message key="button.aggiungi" /></a>
											</div>
										</fieldset>
										<fieldset>
							    			<legend id="legend_tiporicarica_id"><fmt:message key="abbonamento.configurazione.form.comune.ricariche.title" /></legend>
											<div class="form-group">
												<label><fmt:message key="abbonamento.configurazione.form.ricariche.tipo" /></label>
												<select id="tiporicarica_id">
													<option value="" data-etichetta=""><fmt:message key="label.select.default"/></option>
													<c:forEach items="${tipiRicaricheList}" var="tipoRicarica">
														<option value="${tipoRicarica}" data-etichetta="${tipoRicarica.etichetta}">${tipoRicarica.valore}</option>
													</c:forEach>
												</select>
											</div>
											<div class="form-group">
												<label id="etichetta_importo"><fmt:message key="abbonamento.configurazione.form.ricariche.importo_massimo" /></label>
												<input id="importo_id" size="10" maxlength="10" type="number" step="1" />
											</div>
											<div class="form-button">
												<a id="salva_ricarica" class="btn btn-primary" ><fmt:message key="button.aggiungi" /></a>
											</div>
										</fieldset>										
									</div>
									<div id="preview" class="preview"></div>
								</div>
							</div>
		    			</div>
		    		</fieldset>
			</div>
		</div>
	</body>	
</html>