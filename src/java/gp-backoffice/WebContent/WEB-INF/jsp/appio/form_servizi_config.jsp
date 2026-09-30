<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	<title>
		<fmt:message key="appio.label.title" />
	</title>
	
	<script type="text/javascript">
	
	vbg.ready(() => {
	    if (window.Prototype) {
	        delete Array.prototype.toJSON;
	    }

	    salvaServizio();

	    let buttons = document.querySelector('.tabmenu .buttons');
	    let left = document.querySelector('.tabmenu .left');
	    let right = document.querySelector('.tabmenu .right');
	    let filtroTestuale = document.getElementById('input_ricerca');
	    let comuneCards = document.querySelectorAll('vbg-appio-comune-card');
	    let up = document.getElementById('update_configurazione');
	    let movimenti = document.querySelector('#open_movimenti');
	    let idServizio = document.getElementById('id_servizio').value;
	    if (idServizio == "") {
	        idServizio = document.getElementById('input_identificativo_servizio').value;
	        document.getElementById('id_servizio').value = idServizio;
	    }

	    left.addEventListener('click', function (e) {
	        let lastElement = [...document.querySelectorAll('vbg-appio-comune-card')]
	            .map((card, index) => {
	                let delta = parseInt(card.offsetLeft - buttons.offsetLeft - buttons.scrollLeft);
	                return delta;
	            })
	            .filter(delta => {

	                return delta > 0;
	            })
	            .first();

	        buttons.scrollLeft += lastElement;
	    });


	    right.addEventListener('click', function (e) {
	        let lastElement = [...document.querySelectorAll('vbg-appio-comune-card')]
	            .map((card, index) => {
	                let delta = parseInt(card.offsetLeft + card.offsetWidth - buttons.offsetLeft - buttons.scrollLeft - buttons.offsetWidth);
	                return delta;
	            })
	            .filter(delta => {

	                return delta < 0;
	            })
	            .last();

	        buttons.scrollLeft += lastElement;
	    });

	    filtroTestuale.addEventListener('keyup', function (e) {
	        _filtraTestoCelle();
	    });

	    comuneCards.forEach(card => {
	        card.addEventListener('click', _selezionaComune)
	    });

	    document.getElementById('salva_servizio').addEventListener('click', () => {
	    	if(validaForm()){
	    		salvaConfigServizi();
	    	}
	        
	    });

	    salvaParam();

	    preSetServiziConfig();

	    //setParam();
	    
	    if(movimenti){
	    	movimenti.addEventListener('click', () =>{
	    		let idservizio = document.querySelector('#input_identificativo_servizio').value;
	    		console.log("idservizio",idservizio);
	    		javascript:historySet('../appioserviziconfig/serviziente.htm?idservizio='+idservizio,'../appioserviziconfig/listservizitipimovimento.htm?idservizio='+idservizio,'');   			
	    			
	    	});
	    }

	    if (up != undefined) {
	        up.addEventListener('click', () => {
	            updateServizio();
	        });
	    }
		
		function validaForm(){
		let valido = true;
		const l = document.querySelector('#msg').value.length;
		if(l<80){
			valido =false;
			document.querySelector('#msg').style.border ='solid 1px var(--error-color)'; 
			alert("La lunghezza del messaggio deve essere compresa tra 80 e 1000 caratteri! Attuale : " + l);
		}
		return valido;
	}
	
	function salvaServizio() {

	    let salvaConfigurazione = document.getElementById("salva_configurazione");
	    if (salvaConfigurazione === null) {
	        return;
	    }
	    salvaConfigurazione.addEventListener('click', async () => {

	        vbg.mostraModalCaricamento();
	        let descrizioneServizio = document.getElementById("input_servizio").value;
	        let identificativoServizio = document.getElementById("input_identificativo_servizio").value;
	        const postParams = { request: { descrizioneServizio, identificativoServizio } };
	        const response = await fetch('../appioserviziconfig/jsonSalvaServizio.htm', {
	            method: 'POST',
	            headers: {
	                'Accept': 'application/json',
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify(postParams)
	        });

	        if (response.status == 200) {
	            document.getElementById('id_servizio').value = identificativoServizio;
	            document.querySelector('#error_ins').style.display='none';
	           
	        }else if(response.status == 400){
	        	const err = document.querySelector('#error_ins');
	        	err.innerHTML +='Errore inserimneto: esiste già un servizio con quello identificativo';
	        	err.style.display='block';
	        } 
	        else {
	            _gestioneErrori(response.json());
	        }
	        vbg.nascondiModalCaricamento();
	    });
	}

	function _filtraTestoCelle() {

	    let input, filter, table, tr, td, i, txtValue, filtro;

	    input = document.getElementById("input_ricerca");
	    filter = input.value.toUpperCase();

	    let elements = document.querySelectorAll('vbg-appio-comune-card');

	    for (let el of elements) {
	        let display = '';

	        if (filter != '' && !el.comune.toUpperCase().startsWith(filter)) {
	            display = 'none';
	        }
	        el.style.display = display;
	    }
	}

	async function _mostraDettaglio(container, codiceComune, comune) {

	    let dettaglio = await _recuperaDettaglio(codiceComune, comune);
	    if (dettaglio.codiceComune == undefined) {
	        dettaglio = { codiceComune: codiceComune, comune: comune };
	    }
	    
	    let vbgPreview = document.createElement('vbg-appio-preview');
	    vbgPreview.id = 'preview_' + codiceComune;
	    vbgPreview.setAttribute('codice', codiceComune);
	    vbgPreview.dataset = dettaglio;

	    container.appendChild(vbgPreview);
	    
	    let prevs = document.getElementsByTagName('vbg-appio-preview');
        for (let elm of prevs) {
            elm.shadowRoot.querySelector('#non_salvato').classList.remove('modifiche-non-salvate');
            //elm.shadowRoot.querySelector('#non_salvato1').classList.remove('modifiche-non-salvate');
        }
	}

	function _rimuoviDettaglio(container, codiceComune) {
	    container.removeChild(document.getElementById('preview_' + codiceComune));
	}

	async function _selezionaComune(e) {

	    e.preventDefault();

	    vbg.mostraModalCaricamento();

	    let codice = e.detail.codice;
	    let comune = e.detail.comune;
	    let selezionato = e.detail.selezionato;

	    if (selezionato) {
	        await _mostraDettaglio(preview, codice, comune);
	    } else {
	        _rimuoviDettaglio(document.getElementById('preview'), codice);
	    }

	   vbg.nascondiModalCaricamento();
	}

	async function salvaConfigServizi() {
	    let config_servizi = document.getElementsByTagName('vbg-appio-preview');
	    let arr = [];
	    for (let prev of config_servizi) {
	        let arr2 = [];
	        let ente = prev.shadowRoot.querySelector('.config_servizi');
	        let codiceComune = ente.dataset.codice;
	        let identificativoServizio = document.getElementById('id_servizio').value;
	        if (identificativoServizio == "") {
	            identificativoServizio = document.getElementById('input_identificativo_servizio').value;
	            document.getElementById('id_servizio').value = identificativoServizio;
	        }
	        let ambito = ente.querySelector('#input_ambito').value;
	        let maxNumMessaggio = ente.querySelector('#input_num_max_msg').value;
	        let oggettoMesaggio = document.getElementById('oggetto_msg').value;
	        let messaggio = document.getElementById('msg').value;
	        let attivo = ente.querySelector('#input_attivo').checked;
	        let params = prev.shadowRoot.querySelector('#config_param');
	        if (params.innerHTML.trim() != "") {
	            params.querySelectorAll('.params').forEach(element => {
	                let p = element.querySelector('#parametro').value;
	                let v = element.querySelector('#valore').value;
	                let param = { parametro: p, descrizione: v }
	                arr2.push(param);
	            });
	        }

	        arr.push({ codiceComune, identificativoServizio, ambito, maxNumMessaggio, oggettoMesaggio, messaggio, attivo, parametri: arr2 });

	    }
	    let postParams = { request: { entiServizi: arr } };
	    vbg.mostraModalCaricamento();
	    const response = await fetch('../appioserviziconfig/jsonSalvaServizioConfig.htm', {
	        method: 'POST',
	        headers: {
	            'Accept': 'application/json',
	            'Content-Type': 'application/json',
	        },
	        body: JSON.stringify(postParams)
	    });

	    if (response.status == 200) {
	        let prevs = document.getElementsByTagName('vbg-appio-preview');
	        for (let elm of prevs) {
	            elm.shadowRoot.querySelector('#non_salvato').classList.remove('modifiche-non-salvate');
	            elm.shadowRoot.querySelector('#non_salvato1').classList.remove('modifiche-non-salvate');
	        }
	    } else {
	        _gestioneErrori(response.json());
	    }
	   vbg.nascondiModalCaricamento();
	}

	function salvaParam() {

	    document.getElementById('aggiungi_param').addEventListener('click', async () => {
	        let param = document.getElementById('input_parametro').value;
	        let val = document.getElementById('input_parametro_val').value;
	        let identificativoServizio = document.getElementById('id_servizio').value;
	        if (identificativoServizio === '' ) {
	        	identificativoServizio = document.getElementById('input_identificativo_servizio').value;
			}
	        let postParams = { param: { parametro: param, descrizione: val, identificativoServizio: identificativoServizio } };
		    vbg.mostraModalCaricamento();
	        const response = await fetch('../appioserviziconfig/jsonSalvaServizioParams.htm', {
	            method: 'POST',
	            headers: {
	                'Accept': 'application/json',
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify(postParams)
	        });
	        if (response.status == 200) {
	           vbg.nascondiModalCaricamento();

	            let par = document.getElementById('input_parametro').value;
	            let v = document.getElementById('input_parametro_val').value;
	            document.querySelectorAll('vbg-appio-preview').forEach(element => {
	                element.aggiungiParam(par, v);
	                document.getElementById('input_parametro').value = "";
	                document.getElementById('input_parametro_val').value = "";
	            });
	        } else {
	            _gestioneErrori(response.json());
	        }
	    });
	}

	function updateParam() {

	    document.getElementById('input_parametro').addEventListener('keyup', (e) => {
	        let config_servizi = document.getElementsByTagName('vbg-appio-preview');
	        for (let prev of config_servizi) {
	            let ente = prev.shadowRoot.querySelector('#config_param');
	            ente.querySelector('#in_parametro').value = e.target.value;
	        }
	    });

	    document.getElementById('input_parametro_val').addEventListener('keyup', (e) => {
	        let config_servizi = document.getElementsByTagName('vbg-appio-preview');
	        for (let prev of config_servizi) {
	            let ente = prev.shadowRoot.querySelector('#config_param');
	            ente.querySelector('#in_parametro_val').value = e.target.value;
	        }
	    });
	}

	async function preSetServiziConfig() {
	    let container = document.getElementById('preview');
	    document.querySelectorAll('vbg-appio-comune-card').forEach(comune => {
	        let cc = comune.shadowRoot.querySelector('#dettaglio');
	        let com = cc.dataset.comune;
	        let co = cc.dataset.codice;
	        let det = { codice: co, comune: com };
	        if (cc.classList.contains('active')) {
	            _mostraDettaglio(container, co, com);
	            document.getElementById('ambito').dispatchEvent(new Event('click'));
	            document.getElementById('num_max_msg').dispatchEvent(new Event('input'));
	            document.getElementById('oggetto_msg').dispatchEvent(new Event('keyup'));
	            document.getElementById('msg').dispatchEvent(new Event('keyup'));
	            document.getElementById('attivo').dispatchEvent(new Event('change'));
	            document.querySelectorAll('vbg-appio-preview').forEach(prev => {
	                prev.shadowRoot.querySelector('#non_salvato1').classList.remove('modifiche-non-salvate');
	            });
	        }
	    });
	}

	async function setParam() {
	   
	    let idServizio = document.getElementById('id_servizio').value;
	    if (idServizio == "") {
	        idServizio = document.getElementById('input_identificativo_servizio').value;
	        document.getElementById('id_servizio').value = idServizio;
	    }
	    vbg.mostraModalCaricamento();
	    const response = await fetch('../appioserviziconfig/jsongetServizioParams.htm?idServizio=' + idServizio, {
	        method: 'POST',
	        headers: {
	            'Accept': 'application/json',
	            'Content-Type': 'application/json',
	        },

	    });

	    if (response.status == 200) {
	       vbg.nascondiModalCaricamento();
	        let params = await response.json();
	        document.querySelectorAll('vbg-appio-preview').forEach(element => {
	            for (let param of params) {
	                if (param.codiceComune == element.dataset.codice) {
	                    element.aggiungiParam(param.parametro, param.valore);
	                    element.shadowRoot.querySelector('#non_salvato').classList.remove('modifiche-non-salvate');
	                }
	            }
	        });
	    } else {
	        _gestioneErrori(await response.json());
	    }
	}

	async function updateServizio() {
		if (confirm('ATTENZIONE!!! Stai per modificare il servizio cio porterà la perdita del precedente e modifiche per tutti i software.')) {
			 vbg.mostraModalCaricamento();
				
			    let precIdServizio = document.getElementById('id_servizio').value;
			    let desc = document.getElementById('input_servizio').value;
			    let idServizio = document.getElementById('input_identificativo_servizio').value;
			    let postParams = { request: { descrizioneServizio: desc, identificativoServizio: idServizio, precIdentificativoServizio: precIdServizio } }
			    const response = await fetch('../appioserviziconfig/jsonModificaServizio.htm', {
			        method: 'POST',
			        headers: {
			            'Accept': 'application/json',
			            'Content-Type': 'application/json',
			        },
			        body: JSON.stringify(postParams)
			    });

			    if (response.status == 200) {
			       vbg.nascondiModalCaricamento();
			        document.getElementById('id_servizio').value = idServizio;
			    } else {
			        _gestioneErrori(response.json());
			    }
		}
	   
	}

	function _gestioneErrori(errJson) {
	    vbg.nascondiModalCaricamento();
	    console.log(errJson.error);
	    // alert(errJson.error);
	}

	async function _recuperaDettaglio(codiceComune, comune) {

	    let precIdServizio = document.getElementById('input_identificativo_servizio').value;
	    try {
	        const postParams = { request: { comune: codiceComune, idServizio: precIdServizio } };
	        disableFunctions();
	        const response = await fetch('../appioserviziconfig/jsonFindConfigurazioneComune.htm', {
	            method: 'POST',
	            headers: {
	                'Accept': 'application/json',
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify(postParams)
	        });

	        const jsResponse = await response.json();
	        enableFunctions();

	        return jsResponse.dettaglio;
	    }
	    catch (error) {
	        alert(error);
	    }
	    finally {
	    }
	}		
	});
	

	
	
	</script>
	
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-appio/vbg-appio-comune-card.js?<%=vJS %>" defer></script>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-appio/vbg-appio-preview.js?<%=vJS %>" defer></script>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-appio/vbg-io-comunicazione.js?<%=vJS %>" defer></script>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-appio/vbg-io-movimenti.js?<%=vJS %>" defer></script>
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
			  width: 45%;
			}
			.tab .tabcontent .preview {
			  float: left;
			  padding: 6px 12px;
			  border-top: none;
			  width: 45%;
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
				height: 13em !important;
				width:65%;
				
			}
			#error_ins{
				display: none;
				background:var(--accent-color);
				padding: 2px !important;
				border-radius: 0 !important;
				box-shadow: 1px 1px 1px #aaaaaa;
				margin-top: 10px;
				width: max-content;
				color:var(--inverse-text-color) ;
				
			}
			#error_ins i{
					color: #f2a600;
					padding: 1px;
				}

		</style>
	</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="appio.label.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../appio/serviziente" />
		</jsp:include>
	<div id="subcontent">
		
	 	<div class="vbg-form">
	 		<jsp:include page="../includes/displayGlobalMessages.jsp" >
				<jsp:param name="commandName" value="appioservizi" />
			</jsp:include>
			<input id="id_servizio" type="hidden"/>
			<spring-form:form commandName="appioservizi" name="inviodati">
				<fieldset>
					<legend><fmt:message key="appio.label.servizio" /></legend>
					<div class="form-group">
				    	<label><fmt:message key="appio.label.servizio" /></label>
				    	<c:choose>
				    		<c:when test="${not empty appioservizi.id.identificativoServizio  }">
				    			<input id="input_servizio" type="text" placeholder="servizio" size="70" value="${appioservizi.descrizione}"/>
				    		
				    		</c:when>
				    		<c:otherwise>
				    			<input id="input_servizio" type="text" placeholder="servizio" size="70" value=""/>
				    		</c:otherwise>
				    	
				    	</c:choose>
				    </div>
				    <div class="form-group">	
				    	<label><fmt:message key="appio.label.id_servizio" /></label>
				    	<c:choose>
				    		<c:when test="${not empty appioservizi.id.identificativoServizio  }">
				    		
				    			<input id="input_identificativo_servizio" type="text" placeholder="identificativo servizio" size="70" value="${appioservizi.id.identificativoServizio }"/>
				    		</c:when>
				    		<c:otherwise>
				    			<input id="input_identificativo_servizio" type="text" placeholder="identificativo servizio" size="70" value=""/>
				    		</c:otherwise>
				    	
				    	</c:choose>
				    	<label id="error_ins"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i></label>
					</div>
					<div class="form-button">
						<c:if test="${empty appioservizi.id.identificativoServizio  }">
							<a id="salva_configurazione" class="btn btn-primary"><fmt:message key="button.save" />
							</a>
						</c:if>
						<c:if test="${not empty appioservizi.id.identificativoServizio  }">
							<a id="update_configurazione" class="btn btn-primary"><fmt:message key="button.modify" />
							</a>
							
							</a>
						</c:if>
						<a id="open_movimenti" class="btn btn-primary"><fmt:message key="label.movimenti" />
							
						<a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')" class="btn btn-secondary">
							<fmt:message key="button.close" />
						</a>
					</div>
				</fieldset>
				</spring-form:form>
				<fieldset>
					<legend><fmt:message key="appio.label.config.ente" /></legend>
					<div class="form-group">
						<div class="input-icons">
							<i class="fa fa-search icon"></i>
							<input id="input_ricerca" type="text" placeholder="Cerca"/>
						</div>		
					</div>
					<div class="form-group">
		    			<div class="tab">
		    				<div class="tabmenu">
			    				<div class="left">
			    					<i class="fas fa-caret-left fa-2x"></i>
			    				</div>
			    				<div class="buttons">
			    				<c:set var = "trovato" scope = "session" value = "false"/>
			    				<c:choose>		 
	    							<c:when test="${ not empty appIoServiziConfig}">
	    								<c:forEach items="${comuni}" var="comune">
	    									<c:forEach items="${appIoServiziConfig }" var="appServConf">
	    									
	    										
													<c:if test="${appServConf.id.codiceComune == comune.comune.codicecomune }">
															<vbg-appio-comune-card 
																codice="${comune.comune.codicecomune}"
																comune="${comune.comune.comune}"
																active=true
															></vbg-appio-comune-card>
															<c:set var="trovato" value="true" />  
													 </c:if>		
	    									
	    									</c:forEach>
	    									<c:if test="${not trovato }">
	    										<vbg-appio-comune-card 
														codice="${comune.comune.codicecomune}"
														comune="${comune.comune.comune}"
												></vbg-appio-comune-card>
	    									</c:if>
	    									
											<c:set var="trovato" value="false" />
	    								
	    								</c:forEach>
	    							
	    							</c:when>
	    							<c:otherwise>
	    						<c:forEach items="${comuni}" var="comune">
	    								<vbg-appio-comune-card 
													codice="${comune.comune.codicecomune}"
													comune="${comune.comune.comune}"
										></vbg-appio-comune-card>
	    							
	    							</c:forEach>
	    								
	    							
	    							</c:otherwise>
			    					
			    				</c:choose>
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
				    			<legend id="legend_msgnodopagnondisp_comune_id"><fmt:message key="appio.label.config.servizio" /></legend>
				    			<c:choose>
										<c:when test="${not empty appIoServiziConfig and not empty appioservizi.id.identificativoServizio }">
											<div class="form-group">
												<label><fmt:message key="appio.label.ambito" /></label>
												
												<select id="ambito" value="${appIoServiziConfig[0].ambito }">
													<option>seleziona</option>
													<c:forEach items="${ambiti}" var="ambito">
													<c:choose>
														<c:when test="${appIoServiziConfig[0].ambito ==ambito.value }">
															<option selected>${ambito.value }</option>
														
														</c:when>
														<c:otherwise>
															<option>${ambito.value }</option>
														</c:otherwise>
													
													</c:choose>
														
													</c:forEach>
												</select>												
											</div>
											<div class="form-group">
												<label><fmt:message key="appio.label.max_msg" /></label>
												<input id="num_max_msg" type="number" value="${appIoServiziConfig[0].maxMessaggiGiorno }" placeholder="numero massimo di messaggi al giorno"/>
											</div>
											<div class="form-group">
												<label><fmt:message key="appio.label.ogg_msg" /></label>
												<textarea id="oggetto_msg" class="in-fieldset" >${appIoServiziConfig[0].templateOggetto }</textarea>
											</div>
											<div class="form-group">
												<label><fmt:message key="appio.label.msg" /></label>
												<textarea id="msg" class="in-fieldset">${appIoServiziConfig[0].templateMessaggio }</textarea>
											</div>
											<div class="form-group">
												<label><fmt:message key="appio.label.attivo" /></label>
												<input id="attivo" type="checkbox" checked="${appIoServiziConfig[0].attivo }"/>
											</div>
										
										
										
										</c:when>
										<c:otherwise>
											<div class="form-group">
									<label><fmt:message key="appio.label.ambito" /></label>
									
									<select id="ambito">
										<option>seleziona</option>
										<c:forEach items="${ambiti}" var="ambito">
											<option>${ambito.value }</option>
										</c:forEach>
									</select>
									<!-- <textarea id="msgnodopagnondisp_comune_id" cols="70" rows="5" class="in-fieldset"></textarea> -->
								</div>
								<div class="form-group">
									<label><fmt:message key="appio.label.max_msg" /></label>
									<input id="num_max_msg" type="number" placeholder="numero massimo di messaggi al giorno"/>
								</div>
								<div class="form-group">
									<label><fmt:message key="appio.label.ogg_msg" /></label>
									<textarea id="oggetto_msg" class="in-fieldset"></textarea>
								</div>
								<div class="form-group">
									<label><fmt:message key="appio.label.msg" /></label>
									<textarea id="msg" class="in-fieldset"></textarea>
								</div>
								<div class="form-group">
									<label><fmt:message key="appio.label.attivo" /></label>
									<input id="attivo" type="checkbox"/>
								</div>
										
										</c:otherwise>
									</c:choose>
								
								
								<div class="form-button">
									<a id="salva_servizio" class="btn btn-primary" ><fmt:message key="button.save" /></a>
								</div>
							</fieldset>
							<fieldset>
								<legend><fmt:message key="appio.label.config.paramteri" /></legend>
								<div class="form-group">
							    	<label><fmt:message key="appio.label.parametro" /></label>
							    	<input id="input_parametro" type="text" placeholder="parametro"/>
							    </div>
							    <div class="form-group">
							    	<label><fmt:message key="appio.label.val_parametro" /></label>
							    	<input id="input_parametro_val" type="text" placeholder="valore parametro"/>
							    </div>
							    <div class="form-button">
									<a id="aggiungi_param" class="btn btn-primary" ><fmt:message key="button.aggiungi" /></a>
								</div>
							</fieldset>
						</div>
						<div id="preview" class="preview">
							<!--<c:forEach items="${appIoServiziConfig }" var="appserconf">
								<vbg-appio-preview id="preview_${appserconf.id.codiceComune }" data-codice="${appserconf.id.codiceComune }" data-comune="${appserconf.id.codiceComune }"></vbg-appio-preview>
							</c:forEach>-->
						</div>
					</div>
							</div>
					</div>
					
				
				
				</fieldset>
	 	
	 	</div>
	 </div>
</body>
</html>