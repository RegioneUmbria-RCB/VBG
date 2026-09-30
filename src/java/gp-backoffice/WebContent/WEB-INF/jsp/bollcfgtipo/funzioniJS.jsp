<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<script type="text/javascript">
	
	const _IMPLEMENTAZIONE_MERCATI = '<%= ImplementazioniEnum.MERCATI.getValore()%>';
			
	function mostraScadenzePeriodicheFisse() {
		  let dataScadenza = document.querySelector("#tipiscadenzaId").value;
		  let periodo = document.querySelector("#periodiId").value;
		  let tag = document.createElement("p");
		  const dd = document.createTextNode("gg"),
		    ddMM = document.createTextNode("gg/mm");

		  if (dataScadenza == 9) {
		    /* devo distinguere i casi */
		    document.querySelector(".scadenze-fisse").show();
		    if (
		      periodo == "Annuale" ||
		      periodo == "Biennale" ||
		      periodo == "Triennale"
		    ) {
		      /* mostro dd/MM */
		      document
		        .querySelector(".scadenze-fisse")
		        .removeChild(document.querySelector(".scadenze-fisse").lastChild);
		      tag.appendChild(ddMM);
		      document.querySelector(".scadenze-fisse").appendChild(tag);
		    } else {
		      /* mostro dd */
		      document
		        .querySelector(".scadenze-fisse")
		        .removeChild(document.querySelector(".scadenze-fisse").lastChild);
		      tag.appendChild(dd);
		      document.querySelector(".scadenze-fisse").appendChild(tag);
		    }
		  } else {
		    /* nascondo il campo scadenza periodi */
		    document.querySelector(".scadenze-fisse").hide();
		    document.querySelector("#scadenzePeriodi_id").value = "";
		  }
		}

		function validaPeriodo() {
		  let valore = document.querySelector("#scadenzePeriodi_id").value;
		  let periodo = document.querySelector("#periodiId").value;
		  let tag = document.createElement("p");
		  const dd = document.createTextNode("Valori non corretti!");
		  let errore = false;

		  if (periodo == "Annuale" || periodo == "Biennale" || periodo == "Triennale") {
		    console.log(valore);
		    const valori = valore.split(";");
		    valori.forEach(function (value, index) {
		      let d = value.split("/");
		      if (d[0] < 1 || (d[0] > 31 && d[1] < 1) || d[1] > 12) {
		        tag.appendChild(dd);
		        errore = true;
		        throw "Valori non corretti!";
		      }
		    });
		  } else {
		    if (valore < 1 || valore > 31) {
		      tag.appendChild(dd);
		      errore = true;
		      throw "Valori non corretti!";
		    }
		  }
		  if (errore) {
		    document
		      .querySelector(".scadenze-fisse")
		      .removeChild(document.querySelector(".scadenze-fisse").lastChild);
		  }
		}
				
		function viewFlag() {
		  let valore = document.querySelector("#implementazioniId").value;
		  if (valore == _IMPLEMENTAZIONE_MERCATI) {
		    document.querySelector(".rigamercati").show();
		  } else {
		    document.querySelector(".rigamercati").hide();
		  }
		}

		async function inizializzaListaSchede() {
		  const  valore = document.querySelector("#implementazioniId").value;
		  const schedaRuoli = document.querySelector("#schedaRuoli_id");
		  const schedaMercati = document.querySelector("#schedaMercati_id");
		  const schedaCausali = document.querySelector("#schedaCO_id");
		  const schedaConti = document.querySelector("#schedaConti_id");
		  const schedaAltriDati = document.querySelector("#schedaAltriDati_id");
		  const templateCaricamentoInCorso = jQuery("#templateCaricamentoInCorso");
		  const contenitoreDettaglio = jQuery("#dettaglioSchede");
		  
		  if (schedaRuoli != null && schedaMercati != null && schedaCausali != null && schedaConti!=null && schedaAltriDati!=null) {
		    if (valore == _IMPLEMENTAZIONE_MERCATI) {
		      schedaRuoli.show();
		      schedaMercati.show();
		      schedaConti.show();
		      schedaCausali.hide();
		      schedaAltriDati.show();
		    } else {
		      schedaRuoli.show();
		      schedaMercati.hide();
		      schedaConti.hide();
		      schedaCausali.show();
		      schedaAltriDati.show();
		    }
		  }

		  if (schedaRuoli) {
		    schedaRuoli.addEventListener("click", function (e) {
		      settaTabAttivo(schedaRuoli);

		      contenitoreDettaglio.html(templateCaricamentoInCorso.html());

		      visualizzaRuoli("");

		      e.preventDefault();
		    });
		  }

		  if (schedaMercati) {
		    schedaMercati.addEventListener("click", function (e) {
		      settaTabAttivo(schedaMercati);

		      contenitoreDettaglio.html(templateCaricamentoInCorso.html());

		      visualizzaMercato("");

		      e.preventDefault();
		    });
		  }

		  if (schedaCausali) {
		    schedaCausali.addEventListener("click", function (e) {
		      settaTabAttivo(schedaCausali);

		      contenitoreDettaglio.html(templateCaricamentoInCorso.html());

		      visualizzaTipiCO("");

		      e.preventDefault();
		    });
		  }
		  
		  if (schedaConti) {
			  schedaConti.addEventListener("click", function (e) {
			      settaTabAttivo(schedaConti);

			      contenitoreDettaglio.html(templateCaricamentoInCorso.html());

			      visualizzaConto("");

			      e.preventDefault();
			    });
			  }
		  
			if( schedaAltriDati ) {	
				
				//1. Verifico la presenza di metadati da configurare
				let response = await elencoMetadati();
				
				let metadati = response.dettaglio.metadati;
				if( metadati.length == 0 ){
					schedaAltriDati.style.display = 'none';
				}
				
				schedaAltriDati.addEventListener("click",async (e) =>{
					e.preventDefault();
					settaTabAttivo(schedaAltriDati);
					contenitoreDettaglio.html(templateCaricamentoInCorso.html());
				    await visualizzaAltriDati(metadati);
				});
				
			}
			
		  if (schedaRuoli) {
		    schedaRuoli.dispatchEvent(new Event("click"));
		  }
		  

	}

		function settaTabAttivo(scheda) {
		  document
		    .querySelector(".listaSchede > li > a")
		    .classList.remove("SchedaAttiva");
		  scheda.classList.add("SchedaAttiva");
		}
		
		function eliminaMessaggioErrore(){
		    
		    if(document.querySelector("#messaggioErrore") != null){
			 document.querySelector("#messaggioErrore").innerText = '';
			 document.querySelector("#messaggioErrore").hide();
		    }
		}
		
		//ALTRI DATI
		function setHiddenFieldMetadato(inputField, listItem) {
		  var a = listItem.id;
		  nuovoMetadato(a);
		}
		
		function nuovoMetadato(metadato) {
			  // elimino l'eventuale messaggio di errore se presente
			 eliminaMessaggioErrore();
				console.log("nuovoMetadato(metadato)");
			}
		
		const elencoMetadati = async function(){
			try {
				
				const response = await fetch('${pageContext.request.contextPath}/bollcfgtipo/jsonElencoMetadati.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}', {
	        		method: 'GET'
	        	});

				let responseJson = await response.json();
				return responseJson;
			}
			catch(error) {
				console.log(error);
				alert(error);
			}
			finally{

			}
		}
		
		const visualizzaAltriDati = async function(metadati){
			try {
				disableFunctions();
				let tBody = document.createElement('tbody');
				
				let legend = document.createElement('legend');
					legend.appendChild(document.createTextNode('<fmt:message key="label.nuovo" />'));
				
				let labelChiave = document.createElement('label');
					labelChiave.appendChild(document.createTextNode('<fmt:message key="label.parametro" />'));

				let spanHelp = document.createElement('span');
					spanHelp.classList.add('help-metadati');
					
				let listMetadati = document.createElement('select');
					listMetadati.setAttribute('name','listMetadati');
					listMetadati.appendChild(document.createElement('option'));
					listMetadati.addEventListener('change',async (e) =>{
						e.preventDefault();
						e.stopPropagation();
						
						try
						{
							await _helpMetadato(spanHelp, listMetadati.value);
						} 
						catch(error) {                                    
				            alert(error);
				        }
						finally{                                  
				            
				        }
				});
				
				spanHelp.appendChild(document.createTextNode('<fmt:message key="metadato.bollettazione.' + listMetadati.value + '.help" />'));
									
				let divNuovaChiave = document.createElement('div');
					divNuovaChiave.classList.add('form-group');
					divNuovaChiave.appendChild(labelChiave);
					divNuovaChiave.appendChild(listMetadati);
					divNuovaChiave.appendChild(spanHelp);

				let labelValore = document.createElement('label');
					labelValore.appendChild(document.createTextNode('<fmt:message key="label.valore" />'));
					
				let txNuovoValore = document.createElement('input');
					txNuovoValore.type = 'text';
				
				let divNuovoValore = document.createElement('div');
					divNuovoValore.classList.add('form-group');
					divNuovoValore.appendChild(labelValore);
					divNuovoValore.appendChild(txNuovoValore);
				
				let cmdAggiungi = document.createElement('a');
					cmdAggiungi.classList.add('btn');
					cmdAggiungi.classList.add('btn-primary');
					cmdAggiungi.appendChild(document.createTextNode('<fmt:message key="label.aggiungi" />'));
					cmdAggiungi.addEventListener('click',async (e) =>{
						e.preventDefault();
						e.stopPropagation();
						
						try
						{
							const option = listMetadati.options[listMetadati.selectedIndex];
							
							const codicecomune = option.dataset.codiceComune;
							const comune = option.dataset.comune;
							const idcfgtipo = ${bollcfgtipo.id.codice};
							const chiave = listMetadati.value;
							const valore = txNuovoValore.value;
							
							const postParams = { request: { codicecomune, idcfgtipo, chiave, valore } };
							
				        	const response = await fetch('./jsonInsertMetadato.htm', {
				        		method: 'POST',
				                headers: {
				                    'Accept': 'application/json',
				                    'Content-Type': 'application/json',
				                },
				                body: JSON.stringify(postParams)
				        	});
				        	
				        	let result = await response.json();
				        	if( result.esito.ok != true ){
				        		throw(result.esito.messaggio);
				        	}
				        	
				        	_creaTrMetadato(tBody, result.id, codicecomune, comune, listMetadati.value, txNuovoValore.value);
				        	
				        	listMetadati.options.remove(listMetadati.selectedIndex);
				        	txNuovoValore.value = '';
						} 
						catch(error) {                                    
				            alert(error);
				        }
						finally{                                  
				            
				        }
				});
					
				let divNuovoBottoni = document.createElement('div');
					divNuovoBottoni.classList.add('form-button');
					divNuovoBottoni.appendChild(cmdAggiungi);
					
				let fieldset = document.createElement('fieldset');
					fieldset.appendChild(legend);
					fieldset.appendChild(divNuovaChiave);
					fieldset.appendChild(divNuovoValore);
					fieldset.appendChild(divNuovoBottoni);
				
				let divErrore = document.createElement('div')
					divErrore.classList.add('error_header');
				
				let thMetadato = document.createElement('th');
					thMetadato.width = '40%';
					thMetadato.appendChild(document.createTextNode('<fmt:message key="label.parametro" />'));
					
				let thValore = document.createElement('th');
					thValore.width = '40%';
					thValore.appendChild(document.createTextNode('<fmt:message key="label.valore" />'));
					
				let thAzioni = document.createElement('th');
					thAzioni.width = '20%';
					thAzioni.appendChild(document.createTextNode('<fmt:message key="label.azioni" />'));
				
				let trHead = document.createElement('tr');
					trHead.appendChild(thMetadato);
					trHead.appendChild(thValore);
					trHead.appendChild(thAzioni);
					
				let tHead = document.createElement('thead');
					tHead.appendChild(trHead);
				
				let tabMetadati = document.createElement('table');
					tabMetadati.classList.add('vbg-table');
					tabMetadati.appendChild(tHead);
					tabMetadati.appendChild(tBody);
				
				let divContainer = document.createElement('div');
					divContainer.classList.add('vbg-form');
					divContainer.appendChild(fieldset);
					divContainer.appendChild(divErrore);
					divContainer.appendChild(tabMetadati);
					
				metadati.forEach(el => {
					if( el.id  ) {
						_creaTrMetadato(tBody, el.id, el.codicecomune, el.comune, el.chiave, el.valore);
					} else {
						let option = document.createElement('option');
						option.value = el.chiave;
						option.dataset.codiceComune = '';
						if(el.codicecomune){
							option.dataset.codiceComune = el.codicecomune;	
						}
						option.text = el.chiave;
						option.dataset.comune = '';
						if(el.comune){
							option.text += " (" + el.comune + ")";
							option.dataset.comune = el.comune;
						} else {
							option.text += " ( TUTTI GLI ENTI)";
						}
							
						listMetadati.appendChild(option);
					}
				});
				
				await _helpMetadato(spanHelp, listMetadati.value);
				
				document.querySelector("#dettaglioSchede").appendChild(divContainer);
			}
			catch(error) {
				console.log(error);
				alert(error);
			}
			finally{
				enableFunctions();
			}
		}
		
		async function _helpMetadato(helpContainer, metadato){
			
			try {
				helpContainer.textContent = '';
				let chiave = 'seleziona';
				if(metadato != ''){
					chiave = metadato;
				}
				
				chiave = 'metadato.bollettazione.' + chiave.toLowerCase() + '.help';

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
		
		function _creaTrMetadato(tbody, id, codicecomune, comune, chiave, valore){
			
			let tChiave = chiave;
			if(comune && comune != ''){
				tChiave += ' (' + comune + ')'
			} else {
				tChiave += ' ( TUTTI GLI ENTI )'
			}
			
			let spanHelp = document.querySelector('.help-metadati'); 
			
			let tdChiave = document.createElement('td');
				tdChiave.appendChild(document.createTextNode(tChiave));
		
			let txValore = document.createElement('input');
				txValore.type = 'textbox';
				txValore.value = valore;
				txValore.classList.add('readonly');
				txValore.setAttribute("name", "tx-valore");
				txValore.dataset.chiave = chiave;
			
			let tdValore = document.createElement('td');
				tdValore.appendChild(txValore);

				let iSalva = document.createElement('i');
				iSalva.classList.add('fa');
				iSalva.classList.add('fa-save');

			let liSalva = document.createElement('li');
				liSalva.classList.add('azione');
				liSalva.classList.add('cmd-salva');
				liSalva.style.display = 'none';
				liSalva.appendChild(iSalva);
				liSalva.appendChild(document.createTextNode(' <fmt:message key="label.salva" />'));
				liSalva.addEventListener('click',async (e) =>{
					e.preventDefault();
					e.stopPropagation();
					
					try
					{
						const idcfgtipo = ${bollcfgtipo.id.codice};
											
						const postParams = { request: { id, codicecomune, idcfgtipo, chiave, valore: txValore.value } };
						
			        	const response = await fetch('./jsonUpdateMetadato.htm', {
			        		method: 'POST',
			                headers: {
			                    'Accept': 'application/json',
			                    'Content-Type': 'application/json',
			                },
			                body: JSON.stringify(postParams)
			        	});
			        	
			        	let result = await response.json();
			        	if( result.response.esito.ok != true ){
			        		throw(result.response.esito.messaggio);
			        	}
						e.target.style.display = 'none';
						txValore.classList.add('readonly');
						liAnnulla.style.display = 'none';
						liModifica.style.display = '';
						liElimina.style.display = '';
						valore = txValore.value;
					} 
					catch(error) {                                    
			            alert(error);
			        }
					finally{                                  
			            
			        }
				});
				
			let iAnnulla = document.createElement('i');
				iAnnulla.classList.add('fa');
				iAnnulla.classList.add('fa-undo');

			let liAnnulla = document.createElement('li');
				liAnnulla.classList.add('azione');
				liAnnulla.classList.add('cmd-annulla');
				liAnnulla.style.display = 'none';
				liAnnulla.appendChild(iAnnulla);
				liAnnulla.appendChild(document.createTextNode(' <fmt:message key="label.annulla" />'));
				liAnnulla.addEventListener('click',async (e) =>{
					e.preventDefault();
					e.stopPropagation();
					
					e.target.style.display = 'none';
					txValore.value = valore;
					txValore.classList.add('readonly');
					liSalva.style.display = 'none';
					liModifica.style.display = '';
					liElimina.style.display = '';
				});
				
			let iElimina = document.createElement('i');
				iElimina.classList.add('fa');
				iElimina.classList.add('fa-trash-o');
				
			let liElimina = document.createElement('li');
				liElimina.classList.add('azione');
				liElimina.classList.add('cmd-elimina');
				liElimina.appendChild(iElimina);
				liElimina.appendChild(document.createTextNode(' <fmt:message key="label.elimina" />'));
				liElimina.addEventListener('click',async (e) =>{
					e.preventDefault();
					e.stopPropagation();
					
					try
					{
						const postParams = { request: { id } };
						
			        	const response = await fetch('./jsonDeleteMetadato.htm', {
			        		method: 'DELETE',
			                headers: {
			                    'Accept': 'application/json',
			                    'Content-Type': 'application/json',
			                },
			                body: JSON.stringify(postParams)
			        	});
			        	
			        	tbody.removeChild(trBody);
			        	
			        	let listMetadati = document.querySelector('[name="listMetadati"]');
			        	if(listMetadati){
							let option = document.createElement('option');
							
								option.value = chiave;
								option.dataset.codiceComune = '';
								if(codicecomune){
									option.dataset.codiceComune = codicecomune;	
								}
								option.text = chiave;							
								option.dataset.comune = '';
								if(comune != ''){
									option.text += " (" + comune + ")";
									option.dataset.comune = comune;
								} else {
									option.text += " ( TUTTI GLI ENTI)";
								}
								
							listMetadati.appendChild(option);
							_helpMetadato(spanHelp,'');
			        	}
					} 
					catch(error) {                                    
			            alert(error);
			        }
					finally{                                  
			            
			        }
				});
			let iModifica = document.createElement('i');
				iModifica.classList.add('fa');
				iModifica.classList.add('fa-pencil');
			
			let liModifica = document.createElement('li');
				liModifica.classList.add('azione');
				liModifica.classList.add('cmd-aggiungi');
				liModifica.appendChild(iModifica);
				liModifica.appendChild(document.createTextNode(' <fmt:message key="label.modifica" />'));
				liModifica.addEventListener('click',async (e) =>{
					e.preventDefault();
					e.stopPropagation();
					
					e.target.style.display = 'none';
					txValore.classList.remove('readonly');
					liSalva.style.display = '';
					liAnnulla.style.display = '';
					liElimina.style.display = 'none';
				});
				
			let ulAzioni = document.createElement('ul');
				ulAzioni.classList.add('funzioni-dettaglio');
				ulAzioni.appendChild(liModifica);
				ulAzioni.appendChild(liSalva);
				ulAzioni.appendChild(liAnnulla);
				ulAzioni.appendChild(liElimina);
				
			let tdAzioni = document.createElement('td');
				tdAzioni.appendChild(ulAzioni);
				
			let trBody = document.createElement('tr');
				trBody.appendChild(tdChiave);
				trBody.appendChild(tdValore);
				trBody.appendChild(tdAzioni);
			
			tbody.appendChild(trBody);
		}

		// RUOLI
		function setHiddenFieldRuolo(inputField, listItem) {
		  var a = listItem.id;
		  nuovoRuolo(a);
		}

		var visualizzaRuoli = function (codiceRuolo) {
		  disableFunctions();
		  var jhqrPr = jQuery.ajax({
		    url:
		      "${pageContext.request.contextPath}/bollcfgtipo/ajaxDettaglioRuoli.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceRuolo=" +
		      codiceRuolo,
		    context: document.body,
		    cache: false,
		    dataType: "html",
		    success: function (data, textStatus, jqXHR) {
		      if (data) {
		        jQuery("#dettaglioSchede").html(data);
		        jQuery("#dettaglioSchede").show();
		        enableFunctions();
		      }
		    },
		    error: gestisciErrore,
		  });
		};

		function nuovoRuolo(codiceRuolo) {
		  // elimino l'eventuale messaggio di errore se presente
		 eliminaMessaggioErrore();

		  if (codiceRuolo != "") {
		    disableFunctions();
		    var jhqrPr = jQuery.ajax({
		      url:
		        "${pageContext.request.contextPath}/bollcfgtipo/ajaxAssegnaRuoli.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceRuolo=" +
		        codiceRuolo,
		      context: document.body,
		      cache: false,
		      dataType: "html",
		      success: function (data, textStatus, jqXHR) {
		        verificaErroreoSuccesso(data, codiceRuolo, "ruolo");
		      },
		      error: gestisciErrore,
		    });
		  }
		}

		function eliminaRuoli(codiceRuolo) {
		  if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
		    // elimino l'eventuale messaggio di errore se presente
		    eliminaMessaggioErrore();
		    disableFunctions();
		    var jhqrPr = jQuery.ajax({
		      url:
		        "${pageContext.request.contextPath}/bollcfgtipo/ajaxEliminaRuoli.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceRuolo=" +
		        codiceRuolo,
		      context: document.body,
		      cache: false,
		      dataType: "html",
		      success: function (data, textStatus, jqXHR) {
		        verificaErroreoSuccesso(data, "", "ruolo");
		      },
		      error: gestisciErrore,
		    });
		  }
		}


		// MERCATI
		function setHiddenFieldMercato(inputField, listItem) {
		  var a = listItem.id;
		  nuovoMercato(a);
		}

		var visualizzaMercato = function (codiceMercati) {
		  disableFunctions();
		  var jhqrPr = jQuery.ajax({
		    url:
		      "${pageContext.request.contextPath}/bollcfgtipo/ajaxDettaglioMercati.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceMercati=" +
		      codiceMercati,
		    context: document.body,
		    cache: false,
		    dataType: "html",
		    success: function (data, textStatus, jqXHR) {
		      // settaTabAttivo('schedaRuoli_id');
		      if (data) {
		        jQuery("#dettaglioSchede").html(data);
		        jQuery("#dettaglioSchede").show();
		        enableFunctions();
		      }
		    },
		    error: gestisciErrore,
		  });
		};

		function nuovoMercato(codiceMercati) {
		  // elimino l'eventuale messaggio di errore se presente
		  eliminaMessaggioErrore();

		  if (codiceMercati != "") {
		    disableFunctions();
		    var jhqrPr = jQuery.ajax({
		      url:
		        "${pageContext.request.contextPath}/bollcfgtipo/ajaxAssegnaMercati.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceMercati=" +
		        codiceMercati,
		      context: document.body,
		      cache: false,
		      dataType: "html",
		      success: function (data, textStatus, jqXHR) {
		        verificaErroreoSuccesso(data, codiceMercati,"mercato");
		      },
		      error: gestisciErrore,
		    });
		  }
		}

		function eliminaMercati(codiceMercati) {
		  if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
		    // elimino l'eventuale messaggio di errore se presente
		    eliminaMessaggioErrore();
		    disableFunctions();
		    var jhqrPr = jQuery.ajax({
		      url:
		        "${pageContext.request.contextPath}/bollcfgtipo/ajaxEliminaMercato.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceMercati=" +
		        codiceMercati,
		      context: document.body,
		      cache: false,
		      dataType: "html",
		      success: function (data, textStatus, jqXHR) {
		        verificaErroreoSuccesso(data, "", "mercato");
		      },
		      error: gestisciErrore,
		    });
		  }
		}

		// TIPICAUSALIONERI
		function setHiddenFieldCausali(inputField, listItem) {
		  var a = listItem.id;
		  nuovoTipiCO(a);
		}
		
		var visualizzaTipiCO = function (codiceCO) {
		  disableFunctions();
		  var jhqrPr = jQuery.ajax({
		    url:
		      "${pageContext.request.contextPath}/bollcfgtipo/ajaxDettaglioTipiCO.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceCO=" +
		      codiceCO,
		    context: document.body,
		    cache: false,
		    dataType: "html",
		    success: function (data, textStatus, jqXHR) {
		      if (data) {
		        jQuery("#dettaglioSchede").html(data);
		        jQuery("#dettaglioSchede").show();
		        enableFunctions();
		      }
		    },
		    error: gestisciErrore,
		  });
		};

		function nuovoTipiCO(codiceCO) {
		  // elimino l'eventuale messaggio di errore se presente
		  eliminaMessaggioErrore();

		  if (codiceCO != "") {
		    disableFunctions();
		    var jhqrPr = jQuery.ajax({
		      url:
		        "${pageContext.request.contextPath}/bollcfgtipo/ajaxAssegnaTipiCO.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceCO=" +
		        codiceCO,
		      context: document.body,
		      cache: false,
		      dataType: "html",
		      success: function (data, textStatus, jqXHR) {
		        verificaErroreoSuccesso(data, codiceCO, "tipico");
		      },
		      error: gestisciErrore,
		    });
		  }
		}
		
		function gestisciErrore(jqXHR, textStatus, errorThrown) {
		  console.error([jqXHR, textStatus, errorThrown]);
		  jQuery("#dettaglioSchede").html(
		    "<div class='error_header'>Si è verificato un errore di sistema. Riprovare in un secondo momento</div>"
		  );
		  jQuery("#dettaglioSchede").show();
		  enableFunctions();
		}

		function eliminaTipiCO(codiceCO) {
		  if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
		    // elimino l'eventuale messaggio di errore se presente
		    eliminaMessaggioErrore();
		    disableFunctions();
		    var jhqrPr = jQuery.ajax({
		      url:
		        "${pageContext.request.contextPath}/bollcfgtipo/ajaxEliminaTipiCO.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceCO=" +
		        codiceCO,
		      context: document.body,
		      cache: false,
		      dataType: "html",
		      success: function (data, textStatus, jqXHR) {
		        verificaErroreoSuccesso(data, "","tipico");
		      },
		      error: gestisciErrore,
		    });
		  }
		}
		// CONTI
		function setHiddenFieldConti(inputField, listItem) {
		  var a = listItem.id;
		  nuovoConto(a);
		}
			var visualizzaConto = function (codiceConto) {
			  disableFunctions();
			  var jhqrPr = jQuery.ajax({
			    url:
			      "${pageContext.request.contextPath}/bollcfgtipo/ajaxDettaglioConto.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceConto=" +
			      codiceConto,
			    context: document.body,
			    cache: false,
			    dataType: "html",
			    success: function (data, textStatus, jqXHR) {
			      if (data) {
			        jQuery("#dettaglioSchede").html(data);
			        jQuery("#dettaglioSchede").show();
			        enableFunctions();
			      }
			    },
			    error: gestisciErrore,
			  });
			};

			function nuovoConto(codiceConto) {
			  // elimino l'eventuale messaggio di errore se presente
			  eliminaMessaggioErrore();

			  if (codiceConto != "") {
			    disableFunctions();
			    var jhqrPr = jQuery.ajax({
			      url:
			        "${pageContext.request.contextPath}/bollcfgtipo/ajaxAssegnaConto.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceConto=" +
			        codiceConto,
			      context: document.body,
			      cache: false,
			      dataType: "html",
			      success: function (data, textStatus, jqXHR) {
			        verificaErroreoSuccesso(data, codiceConto, "conti");
			      },
			      error: gestisciErrore,
			    });
			  }
			}
			

			function eliminaConto(codiceConto) {
			  if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
			    // elimino l'eventuale messaggio di errore se presente
			    eliminaMessaggioErrore();
			    disableFunctions();
			    var jhqrPr = jQuery.ajax({
			      url:
			        "${pageContext.request.contextPath}/bollcfgtipo/ajaxEliminaConto.htm?codiceBollcfgTipo=${bollcfgtipo.id.codice}&codiceConto=" +
			        codiceConto,
			      context: document.body,
			      cache: false,
			      dataType: "html",
			      success: function (data, textStatus, jqXHR) {
			        verificaErroreoSuccesso(data, "","conti");
			      },
			      error: gestisciErrore,
			    });
			  }
			}

			function verificaErroreoSuccesso(data, codice, identificativo) {
			    // verifica errori o altro e aggiorna
			    if (data.toLowerCase() == "ok") {
			        if (identificativo == "ruolo") {
			            visualizzaRuoli(codice);
			        } else if (identificativo == "mercato") {
			            visualizzaMercato(codice);
			        } else if (identificativo == "tipico") {
			            visualizzaTipiCO(codice);
			        }else if (identificativo == "conti") {
			            visualizzaConto(codice);
			        }
			    } else {
			        let divErrore = document.querySelector("#messaggioErrore");
			        let testo = document.createTextNode(data);
			        divErrore.appendChild(testo);
			        divErrore.show();
			        document.querySelector("#messaggioErrore").show();
			        if (identificativo == "ruolo") {
			            document.querySelector("#ruolo_id").value ="";
			            document.querySelector("#ruolo_id_hidden").value="";
			        } else if (identificativo == "mercato") {
			            document.querySelector("#mercato_id").value="";
			            document.querySelector("#mercato_id_hidden").value="";
			        } else if (identificativo == "tipico") {
			            document.querySelector("#tipiCO_id").value="";
			            document.querySelector("#tipiCO_id_hidden").value="";
			        } else if (identificativo == "conti") {
			            document.querySelector("#conto_id").value="";
			            document.querySelector("#conto_id_hidden").value="";
			        }
			        enableFunctions();
			    }
			}
		
		function aggiornaPopup() {
		    let codiceRateizzazione = document
		      .querySelector("#vbg-modal-gestisci-rata")
		      .querySelector("#descrizione_rata_id").value;
		  	visualizza(codiceRateizzazione);
		    console.log("codice:", codiceRateizzazione);
		  }

		vbg.ready(() => {
			
			if(document.querySelector('#tipiscadenzaId').value != 'Scadenze periodiche fisse' ){
				if(document.querySelector(".scadenze-fisse")){
					document.querySelector(".scadenze-fisse").hide();
				}
			}
		  
		  inizializzaListaSchede();

		  const popupDecodifica = document.querySelector("#vbg-modal-gestisci-rata");
		  const bottoneChiudi = popupDecodifica.querySelector(".btnChiudi");
		  const bottoneAggiungi = document.querySelector(".cmd-aggiungi");
		  const bottoneModifica = popupDecodifica.querySelector("#bottone_modifica");
		  const bottoneInserisci = popupDecodifica.querySelector("#bottone_inserisci");

		  if (bottoneChiudi) {
		    bottoneChiudi.addEventListener("click", (e) => {
		      popupDecodifica.close();
		    });
		  }
		  
		  document.querySelector("#vbg-modal-gestisci-rata").querySelector('#descrizione_rata_id').addEventListener('change', async (e) =>{
			  
			  let codiceRateizzazione =
			        popupDecodifica.querySelector("#descrizione_rata_id").value;
			      await visualizza(codiceRateizzazione);
			     console.log(codiceRateizzazione);
			  
		  });

		  if (bottoneAggiungi) {
		    bottoneAggiungi.addEventListener("click", async (e) => {
		      console.log("Aggiungi");

		      popolaPopup();
		      let codiceRateizzazione =
		        popupDecodifica.querySelector("#descrizione_rata_id").value;
		      await visualizza(codiceRateizzazione);
		    });
		  }

		  async function visualizza(codiceRateizzazione) {
		    let url =
		      "${pageContext.request.contextPath}/bollcfgtipo/ajaxPopolaCampiRata.htm?codiceRateizzazione=" +
		      codiceRateizzazione;
		    const response = await fetch(url, {
		      method: "GET",
		    });

		    if (response.status !== 200) {
		      const errore = await response.text();
		      console.error(errore);
		      throw errore;
		    } 

		    const responseJson = await response.json();
		    
		    popupDecodifica.querySelector("#rangeBasso_id").value = 
		    	responseJson.rangeBasso === 'null' ? '' : responseJson.rangeBasso;
		    popupDecodifica.querySelector("#rangeAlto_id").value =
		    	responseJson.rangeAlto === 'null' ? '' : responseJson.rangeAlto;
		    
		  }

		  function popolaPopup(item) {
			  
		    const isInserting = !item;

		    popupDecodifica.querySelector("h1").innerHTML = isInserting
		      ? "Nuovo tipo rate"
		      : "Modifica tipo rate";


		    if (!isInserting) {
		      bottoneModifica.style.display = "inline-block";
		      bottoneInserisci.style.display = "none";
		    } else {
		      bottoneModifica.style.display = "none";
		      bottoneInserisci.style.display = "inline-block";
		    }

		    popupDecodifica.open();
		  }

		  if (bottoneInserisci) {
		    bottoneInserisci.addEventListener("click", (e) => {
		      e.preventDefault();

		      let codiceRateizzazione = popupDecodifica.querySelector("#descrizione_rata_id").value;
		      	
		      console.log(codiceRateizzazione);
		      window.vbg.mostraModalCaricamento();
		      doHref('inserisciTipoRata.htm?codiceRateizzazione=' + codiceRateizzazione,'');
		    });
		  }

		  document.querySelectorAll(".cmd-elimina").forEach((x) => {
		    x.title = '<fmt:message key="label.elimina" />';

		    x.addEventListener("click", (e) => {
		      e.preventDefault();
		      let idRiga = x.parentElement.dataset.id;
		      window.vbg.mostraModalCaricamento();
		      doHref(
		        "deleteTipoRata.htm?codiceTipoRata=" + idRiga,
		        '<fmt:message key="javascript.confirm.delete" />'
		      );
		    });
		  });				  
		});
	</script>