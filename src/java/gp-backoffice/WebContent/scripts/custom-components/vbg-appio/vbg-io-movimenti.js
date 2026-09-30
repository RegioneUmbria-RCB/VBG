import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import '../marked.min.js';
import { VbgIoStatoMovimenti } from './vbg-io-stato-movimenti.js';



class VbgIoMovimenti extends HTMLElement {
	templateHtml = `
	<style>
		#container-stati-coda{
			display:inline-block;
		}
	</style>
	<div id="container-stati-coda"></div>`;
	
	
	 
	 static get observedAttributes() {
        return [
			'id-movimento',
			'descrizione',
			'data',
			'ref-protocolo'
        ];
    }
    
    get idMovimento() { return this._idMovimento; }
    set idMovimento(val) { this._idMovimento = val; }
    
    get descrizione() { return this._descrizione; }
    set descrizione(val) { this._descrizione = val; }
    
    get data() { return this._data; }
    set data(val) { this._data = val; }
    
    get refProtocolo() { return this._refProtocolo; }
    set refProtocolo(val) { this._refProtocolo = val; }
    
    constructor() {
        super();
        const template = document.createElement('template');
        template.innerHTML = this.templateHtml;
        this.attachShadow({ mode: 'open' });

        this.shadowRoot.appendChild(template.content.cloneNode(true));
        
        copiaStiliDaDocument(this.shadowRoot);
    }
    
     attributeChangedCallback(attrName, oldValue, newValue) {

        if (newValue !== oldValue) {
            switch (attrName) {
                case 'id-movimento':
                    this.idMovimento = newValue;
                    break;
                case 'descrizione':
                    this.descrizione = newValue;
                    break;
                case 'data':
                    this.data = newValue;
                    break;
                case 'ref-protocolo':
                    this.refProtocolo = newValue;
                    break;
            }

        }
    }
    
    
    connectedCallback() {
		
		window.vbg.initializeAttenderePrego();
		this._getStatoNotificaIo();
		
	}
    
    disconnectedCallback() {}
    
     async _getStatoNotificaIo(){
			
			window.vbg.mostraModalCaricamento();
			const response = await fetch('../appioserviziconfig/jsongetStatoComunicazione.htm?codicemovimento='+this.idMovimento, {
            		method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                    }
            	});
				
				if( await response.status == 200){
					const json = await response.json();
					let containerStati = this.shadowRoot.querySelector('#container-stati-coda');
					json.forEach((obj) => { 
						const snippet = new VbgIoStatoMovimenti();
						snippet.data = obj;
						snippet.idMovimento = this.idMovimento;
						snippet.descrizione = this.descrizione;
						snippet.date = this.data;
						snippet.refProtocolo =this.refProtocolo;
				   		containerStati.appendChild(snippet);

					 });
					 window.vbg.nascondiModalCaricamento();
					
		}
	}
	
	_gestioneErrori(errJson){
     		console.log(errJson.error);
     		alert( errJson.error);
			}
			
	
	
}
customElements.define('vbg-io-movimenti', VbgIoMovimenti);