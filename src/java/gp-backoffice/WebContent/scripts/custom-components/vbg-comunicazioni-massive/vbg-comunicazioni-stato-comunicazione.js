import { copiaStiliDaDocument } from '../copia-stili-da-document.js';

class VbgComunicazioniStatoComunicazione extends HTMLElement {
	
	templateHtml = `
	<style>
		.elabora-riga {
		    display: inline-block;
		    background-color: gainsboro;
		    padding: var(--half-padding);
		    border-radius: var(--half-padding);
		    cursor: pointer;
		}
		.elabora-riga>div {
		    display: inline-block;
		}
		.elabora-riga.attivo{
			background-color: var(--color-warning);
		}

		.elabora-riga.attivo>i{
			animation: fa-spin 2s infinite linear;
		}
		.elabora-riga.conclusa{
			background-color: var(--color-success);
			color: var(--inverse-text-color);
			pointer-events: none;
		}
	</style>
	<div class="elabora-riga">
		<i class="fa fa-refresh"></i>
		<div></div>
	</div>
	`;
	
	static get observedAttributes() {
        return [
			'data-id',
			'data-id-comunicazione',
			'data-stato',
			'data-conclusa',
			'data-auth'
        ];
    }
    
    get idComunicazione() { return this._idComunicazione; }
    set idComunicazione(val) { this._idComunicazione = val; }
    
    get id() { return this._id; }
    set id(val) { this._id = val; }
    
    get stato() { return this._stato; }
    set stato(val) { this._stato = val; }
    
    get conclusa() { return this._conclusa; }
    set conclusa(val) {this._conclusa = val;}
    
    get auth() { return this._auth; }
    set auth(val) { this._auth = val; }
    
	constructor() {
	
		if(window.Prototype) {
			delete Array.prototype.toJSON;
		}
	
        super();

        const template = document.createElement('template');
        template.innerHTML = this.templateHtml;

        this.attachShadow({ mode: 'open' });

        this.shadowRoot.appendChild(template.content.cloneNode(true));

        copiaStiliDaDocument(this.shadowRoot);
    }
    
    inizializzaDettagliNellaLista(){
		
		let cmdDettagli = document.getElementsByName('cmdDettaglio');
		
		for(let i = 0;i < cmdDettagli.length; i++){
			
			let cmdDettaglio = cmdDettagli[i];
			
			let idRigaDettaglio = parseInt(cmdDettaglio.dataset.id);
						
			cmdDettaglio.addEventListener('click', async function(e) {
				
				e.preventDefault();
				try{
					vbg.mostraModalCaricamento();
					await visualizzaDettaglioRiga(idRigaDettaglio);
				}
				finally{
					vbg.nascondiModalCaricamento();
				}
			});
		}
	}
    
    attributeChangedCallback(attrName, oldValue, newValue) {

        if (newValue !== oldValue) {
			switch (attrName) {
				case 'data-id':
					this.id = newValue;
					break;
				case 'data-id-comunicazione':
					this.idComunicazione = newValue;
					break;				
				case 'data-stato':
					this.stato = newValue;
					break;
				case 'data-conclusa':
					this.conclusa = newValue;
					break;
				case 'data-auth':
					this.auth = newValue;
					break;
			}
        }
	}
	
	connectedCallback() {
		let divElabora = this.shadowRoot.querySelector('div.elabora-riga');

			divElabora.addEventListener('click', async (e) => {
				e.preventDefault();
				
				try
				{
					vbg.mostraModalCaricamento();
					divElabora.classList.add('attivo');
					
					const headers = { 'Authorization': this.auth };
					const url = '../services/rest-private/comunicazioni/' + this.idComunicazione + '/righe/' + this.id + '/elabora';
					
					let response = await fetch(url, {
						method: 'POST',
						headers
					});
					
					if (response.status !== 200) {
						const errore = await response.text();
	        			console.error(errore);
	        			throw errore;
	       			}
	       			
	       			let responseJson = await response.json();
	       			
	       			this._impostaStato(responseJson.comunicazione_destinatario.stato, responseJson.comunicazione_destinatario.conclusa);
				}
				finally{
					divElabora.classList.remove('attivo');
					vbg.nascondiModalCaricamento();
				}
			});
		this._impostaStato(this.stato, this.conclusa);
	}
	
	_impostaStato(stato, conclusa){
		
		let divElaboraRiga = this.shadowRoot.querySelector('div.elabora-riga');
		let icona = divElaboraRiga.querySelector('i');
		let divStato = divElaboraRiga.querySelector('div');
			divStato.textContent = stato;
			if( conclusa ){
				icona.classList.remove('fa-refresh');
				icona.classList.add('fa-check');
				divElaboraRiga.classList.add('conclusa');
			} else {
				icona.classList.remove('fa-check');
				icona.classList.add('fa-refresh');
				divElaboraRiga.classList.remove('conclusa');
			}

	}
	
	disconnectedCallback() {
		
    }
}

customElements.define('vbg-comunicazioni-stato-comunicazione', VbgComunicazioniStatoComunicazione);