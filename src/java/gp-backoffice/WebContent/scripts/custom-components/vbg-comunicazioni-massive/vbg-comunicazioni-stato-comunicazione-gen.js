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

		.elabora-riga.conclusaw{
			background-color: var(--color-warning);
			pointer-events: none;
		}

		.elabora-riga.appio-schedulata{
			background-color: var(--color-warning);
		}
		.elabora-riga.appio-errore{
			background-color: var(--error-color);
			color: var(--inverse-text-color);
			pointer-events: none;
		}
		.elabora-riga.massivaincorso{
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
			'data-auth',
			'data-tipowarning'
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
    
    get tipowarning() { return this._tipowarning; }
    set tipowarning(val) { this._tipowarning = val; }
    
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
				case 'data-tipowarning':
					this.tipowarning = newValue;
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
					/*vbg.mostraModalCaricamento();*/
					divElabora.classList.add('attivo');
					
					const headers = { 'Authorization': this.auth };
					const url = '../services/rest-private/comunicazioni/' + this.idComunicazione + '/righe/' + this.id + '/elabora';
					
					let response = await fetch(url, {
						method: 'POST',
						headers
					});
					
					if (response.status !== 200) {
						console.log('Mi trovo qui');
						const errore = await response.text();
	        			console.error(errore);
	        			alert('Si è verificato un errore durante l\'elaborazione: ' + errore );
	        			location.reload();
	        			return;
	       			}
	       			
	       			let responseJson = await response.json();
	       			this._impostaStato(responseJson.comunicazione_destinatario.stato, responseJson.comunicazione_destinatario.conclusa, responseJson.comunicazione_destinatario.tipowarning);
				}
				finally{
					divElabora.classList.remove('attivo');
					/*vbg.nascondiModalCaricamento();*/
				}
			});
		this._impostaStato(this.stato, this.conclusa, this.tipowarning);
		if(this.stato.includes('APPIO_SCHEDULATA')){
			  if (performance.getEntriesByType("navigation")[0].type === "reload") {
			    console.log("Page was refreshed!");
			    this.doElabora(this.idComunicazione,this.auth,this.id,divElabora);
			    this._impostaStato(this.stato, this.conclusa, this.tipowarning)
			  }
				
			}
		}
	
	_impostaStato(stato, conclusa, tipowarning){
		
		let divElaboraRiga = this.shadowRoot.querySelector('div.elabora-riga');
		let icona = divElaboraRiga.querySelector('i');
		let divStato = divElaboraRiga.querySelector('div');
			divStato.textContent = stato;
			if( conclusa ){
				icona.classList.remove('fa-refresh');
				icona.classList.add('fa-check');
				divElaboraRiga.classList.remove('appio-schedulata');
				divElaboraRiga.classList.add('conclusa');
			} 
			
			else if(stato.includes('APPIO_SCHEDULATA') || stato.includes('CONCLUSA_CON_ERRORE')){
				icona.classList.remove('fa-check');
				icona.classList.add('fa-refresh');
				divElaboraRiga.classList.remove('conclusa');
				divElaboraRiga.classList.add('appio-schedulata');
			}
			else if(stato.includes('CONCLUSA_CON_APPIO_ERRORE')){
				icona.classList.remove('fa-check');
				icona.classList.remove('fa-refresh');
				icona.classList.add('fa-exclamation-triangle');
				divElaboraRiga.classList.remove('appio-schedulata');
				divElaboraRiga.classList.remove('conclusa');
				divElaboraRiga.classList.add('conclusaw');
			}
			else {
				icona.classList.remove('fa-check');
				icona.classList.add('fa-refresh');
				divElaboraRiga.classList.remove('conclusa');
			}

	}
	
	disconnectedCallback() {
		
    }
    async doElabora(id ,aut,idR,divElabora) {
		        
	
		try
				{
					
					divElabora.classList.add('attivo');
					
					const headers = { 'Authorization': aut };
					const url = '../services/rest-private/comunicazioni/' + id + '/righe/' + idR + '/elabora';
					
					let response = await fetch(url, {
						method: 'POST',
						headers
					});
					
					if (response.status !== 200) {
						console.log('Mi trovo qui');
						const errore = await response.text();
	        			console.error(errore);
	        			alert('Si è verificato un errore durante l\'elaborazione: ' + errore );
	        			location.reload();
	        			return;
	       			}
	       			
	       			let responseJson = await response.json();
	       			this._impostaStato(responseJson.comunicazione_destinatario.stato, responseJson.comunicazione_destinatario.conclusa, responseJson.comunicazione_destinatario.tipowarning);
				}
				finally{
					divElabora.classList.remove('attivo');
					
				}
	
	    
	}
}

customElements.define('vbg-comunicazioni-stato-comunicazione-gen', VbgComunicazioniStatoComunicazione);