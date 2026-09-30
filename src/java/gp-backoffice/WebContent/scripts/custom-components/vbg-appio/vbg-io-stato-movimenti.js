import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import '../marked.min.js';

export class VbgIoStatoMovimenti extends HTMLElement {
	  templateHtml = `  <style>
	 :host {
            box-sizing: border-box;
        }
	 
    h1 {
        border-left: var(--half-padding) solid var(--color-default);
        padding-left: var(--half-padding);
        margin-top: 0;
    }

    .success {
        background-color: var(--color-success);
        color: var(--inverse-text-color);
    }

    .warning {
        background-color: var(--color-warning);
        color: var(--text-color);
    }
    .warning-titolo {
	  border-color: var(--color-warning);
	}
	.success-titolo {
	  border-color: var(--color-success);
	}
	.critical-titolo {
	  border-color: var(--color-critical);
	}

    .critical {
        background-color: var(--color-critical);
        color: var(--inverse-text-color);
    }
     .primary {
        background-color: #007bff;
        color: var(--inverse-text-color);
    }
    .primary-titolo {
	  border-color: #007bff;
	}
	 .info {
        background-color: #17a2b8;
        color: var(--inverse-text-color);
    }
    .info-titolo {
	  border-color: #17a2b8;
	}
	.secondary {
        background-color: #6c757d;
        color: var(--inverse-text-color);
    }
    .secondary-titolo {
	  border-color: #6c757d;
	}

    vbg-modal #noteAnnullamento {
        position: absolute;
        top: 100%;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: white;
        overflow-y: hidden;
        height: 0%;
        transition: height 0.3s, top 0.3s;
    }

    vbg-modal #comandiStandard {
        display: block;
    }

    vbg-modal #comandiAnnullamentoPosizione {
        display: none;
    }

    vbg-modal.modalita-annullamento-posizione #noteAnnullamento {
        height: 100%;
        top: 0%;
    }

    vbg-modal.modalita-annullamento-posizione #comandiAnnullamentoPosizione {
        display: block;
    }

    vbg-modal.modalita-annullamento-posizione #comandiStandard {
        display: none;
    }

    #formContacaratteri{
    	float:right;
    }
    
      #vbg-io-stato-movimento {
            display: inline-block;
            white-space: nowrap;
            border-radius: var(--half-padding);
            padding-top: var(--half-padding);
            padding-bottom: var(--half-padding);
            padding-right: var(--half-padding);
            cursor: pointer;
        }
        
     #caricamento-in-corso {
        display: inline-block;
        white-space: nowrap;
        border-radius: var(--half-padding);
        padding: var(--half-padding);
        background-color: var(--color-default);
    }
       #descrizione-stato, 
        #testo, 
        #aggiorna-stato {
            display: inline-block;
        }
       #descrizione-stato {
            /*padding-right: var(--half-padding);*/
            padding-left: var(--half-padding);
        }

        #aggiorna-stato {
            padding-left: var(--half-padding);
            /*padding-right: var(--half-padding);*/
            border-left: 1px solid var(--text-color);

        }
    </style>
    <div id='vbg-io-stato-movimento'>
        <div id='descrizione-stato'>
            <i class='' aria-hidden='true'></i>
            <div id='testo'></div>
        </div>
        
        <div id='aggiorna-stato'>
            <i class="fa fa-refresh"></i>
        </div>
    </div>
    <vbg-modal id='pop-up'>
        <div slot='body' style='position: relative'>
            <h1 id='titolo'>Dettaglio movimenti APPIO</h1>
            <div id='form' class='vbg-form'>
            	<div class="form-group">
            		<label>Movimento</label>
            		<input id="in_movimento" type="text"  placeholder="movimento" size="50" readonly/>
            	</div>
            	<div class="form-group">
            		<label>Ref protocolo</label>
            		<input id="in_ref_prot" type="text" size="50" placeholder="riferimento protocolo" readonly/>
            	</div>
            	<div class="form-group">
            		<label>Data</label>
            		<input id="in_data" type="text"  placeholder="data" readonly/>
            	</div>
            </div>
        </div>
        <div slot='footer' style='position: relative; margin-top: 8px'>
            <div>
                <div class='btn btn-primary' id='cmdAggiorna'><i class='fa fa-refresh fa-spin hidden hide-on-data-loaded'></i> Aggiorna</div>
                <div class='btn btn-primary' id='comandComunicazione'><i class='fa fa-refresh fa-spin hidden hide-on-data-loaded'></i>Dettaglio comunicazione</div>
            </div>
        </div>
    
    </vbg-modal>`;
    
   /* static get observedAttributes() {
        return [
			'data-json'
        ];
    }
    
    get dataJson() { return this._dataJson; }
    set dataJson(val) { this._dataJson = val; }*/
    
    
    constructor() {
        super();
        const template = document.createElement('template');
        template.innerHTML = this.templateHtml;
		console.log(template);
        this.attachShadow({ mode: 'open' });

        this.shadowRoot.appendChild(template.content.cloneNode(true));
        
        copiaStiliDaDocument(this.shadowRoot);
    }
    
    attributeChangedCallback(attrName, oldValue, newValue) {

        if (newValue !== oldValue) {
           if(attrName === 'data-json') {
                  this.dataJson = newValue;

            }
        }
    }
    
    connectedCallback() {
		
		window.vbg.initializeAttenderePrego();
		this._setDatiStatoCoda(false);
		this.descStato = this.shadowRoot.querySelector('#descrizione-stato');
		this.popup = this.shadowRoot.querySelector('#pop-up');
        this.form = this.shadowRoot.querySelector('#form');
        this.titolo = this.shadowRoot.querySelector('#titolo');
        this.popup.querySelector('#in_movimento').value = this.descrizione;
        const date = new Date(this.date);
        this.popup.querySelector('#in_data').value = date.toLocaleDateString();
        this.popup.querySelector('#in_ref_prot').value = this.refProtocolo;
        this.aggiornastato = this.shadowRoot.querySelector('#aggiorna-stato');
        
        
        this.cmdAggiorna = this.shadowRoot.querySelector('#cmdAggiorna');
           this.descStato.addEventListener('click',()=>{
			this._setDatiStatoCoda(true);
			this.popup.open();
			
		
		});
		this.aggiornastato.addEventListener('click',()=>{
			this._setDatiStatoCoda(true);
			
		});
		this.cmdAggiorna.addEventListener('click',()=>{
			this._setDatiStatoCoda(true);
			
		});
		
	}
	
	disconnectedCallback() {}
	
	async _setDatiStatoCoda(isAggiorna){
					let json; 
					if(!isAggiorna){
							json = await this.data;
					}else{
						
							window.vbg.mostraModalCaricamento();
							const response = await fetch('../appioserviziconfig/jsongetSingoloStatoComunicazione.htm?guid='+this.data.guid, {
				            		method: 'POST',
				                    headers: {
				                        'Accept': 'application/json',
				                        'Content-Type': 'application/json',
				                    }
				            	});
								
								if( await response.status == 200){
									 json = await response.json();
									 window.vbg.nascondiModalCaricamento();
									
								}
					}
					
					let stato_style = "";
					this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList ="";
					this.shadowRoot.querySelector('#descrizione-stato>i').classList = "";
					this.popup.querySelector('#titolo').classList = "";
					
					switch (json.ultimo_stato) {
		                case 'DA_PROCESSARE':
		                	this.shadowRoot.querySelector('#testo').innerHTML = "Da processare";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('warning');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-clock');
		                    this.popup.querySelector('#titolo').classList.add('warning-titolo');
		                    stato_style = "warning";
		                    break;
		                case 'INVIATA_A_GATEWAY':
		                	this.shadowRoot.querySelector('#testo').innerHTML = "Inviata gateway";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('primary');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-paper-plane-o');
		                    this.popup.querySelector('#titolo').classList.add('primary-titolo');
		                    stato_style = "primary";
		                    break;
		                 case 'NOTIFICATA_APPIO':
		                	this.shadowRoot.querySelector('#testo').innerHTML = "Notificata AppIO";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('info');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-bell');
		                    this.popup.querySelector('#titolo').classList.add('info-titolo');
		                    stato_style = "info";
		                    break;
		                  case 'IN_LAVORAZIONE':
		                	this.shadowRoot.querySelector('#testo').innerHTML = "In lavorazione";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('secondary');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-spinner');
		                    this.popup.querySelector('#titolo').classList.add('secondary-titolo');
		                    stato_style = "secondary";
		                    break;
		                  case 'NOTIFICATA_UTENTE':
		                	this.shadowRoot.querySelector('#testo').innerHTML = "Notificata utente";
		                	this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList ="";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('success');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-check-circle-o');
		                    this.popup.querySelector('#titolo').classList.add('success-titolo');
		                    stato_style = "success";
		                    break;
	                      /*case 'ERRORE':
		                	this.shadowRoot.querySelector('#testo').innerHTML = "Errore";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('critical');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-times-circle-o');
		                    this.popup.querySelector('#titolo').classList.add('critical-titolo');
		                    stato_style = "critical";
	                        break;*/
	                      default:
	                      	this.shadowRoot.querySelector('#testo').innerHTML = "Errore";
		                    this.shadowRoot.querySelector('#vbg-io-stato-movimento').classList.add('critical');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa');
		                    this.shadowRoot.querySelector('#descrizione-stato>i').classList.add('fa-times-circle-o');
		                    this.popup.querySelector('#titolo').classList.add('critical-titolo');
		                    stato_style = "critical";
		                       
                   }
                  
                  
                  
                   this.dettagliocomunicazione = this.cmdAggiorna = this.shadowRoot.querySelector('#comandComunicazione');
                   this.dettagliocomunicazione.addEventListener('click',()=>{
					
					 if( this.shadowRoot.querySelector('vbg-io-comunicazione') == null){
						const html = `<vbg-io-comunicazione></vbg-io-comunicazione>`;
				   		this.popup.insertAdjacentHTML("afterend", html); 
					}
				   console.log(this.shadowRoot.querySelector('vbg-io-comunicazione') );
				   const com = this.shadowRoot.querySelector('vbg-io-comunicazione').shadowRoot;
				   com.querySelector('#titolo').classList = "";
				   com.querySelector('#titolo').classList.add(stato_style);
				   com.querySelector('#in_codicefiscale').value = json.codice_fiscale;
				   com.querySelector('#stato-messaggio').value = json.stato_messaggio;
				   com.querySelector('#oggetto-messaggio').innerHTML= marked.parse(json.oggetto);
				    com.querySelector('#oggetto-messaggio').classList.remove('form-group');
				   com.querySelector('#messaggio').innerHTML = marked.parse(json.messaggio);
				   com.querySelector('#messaggio').classList.remove('form-group');
				   com.querySelector('#in_idservizio').value = json.id_servizio;
				   const date = new Date(json.ultimo_datastato);
				   com.querySelector('#in_dataultimostato').value = date.toLocaleDateString();
				   com.querySelector('#in_ultimostato').value = json.ultimo_stato;
				   this.shadowRoot.querySelector('vbg-io-comunicazione').open();
				   
				});
				window.vbg.nascondiModalCaricamento();	
            		
	}
	
	_gestioneErrori(errJson){
     		console.log(errJson.error);
     		alert( errJson.error);
	}
			
	
}
customElements.define('vbg-io-stato-movimenti', VbgIoStatoMovimenti);



