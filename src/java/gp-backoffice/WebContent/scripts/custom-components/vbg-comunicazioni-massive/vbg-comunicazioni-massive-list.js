import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { vbgRisolviEtichette } from '../vbg-risolvi-etichette.js';

class VbgComunicazioniMassiveList extends HTMLElement {
	templateHtml = `
	<style>
		.parametriDiv {
			display: flex;
			clear: both;
		    margin-bottom: 20px;
		    margin-top: 20px;
		    min-width: 200px;
		}
		.etichetta {
		    float: left;
		    display: inline;
		    min-width: 100px;
		}
		.etichetta>div {
			display: block;
		    margin-right: 20px;
		    color: var(--color-label-header);
		    font-size: 1.0em;
		    font-weight: bold;
		}
		.parametro {
		    display: block;
		    min-width: 100px;
		    float: left;
		}
		.parametro>div {
		    color: var(--accent-color);
		    font-size: 1.0em;
		    font-weight: bold;
		}
		.avanzamento-stati {
		    margin: 0;
		    padding: 0;
		    list-style-type: none;
		}
		span {
			padding-left: 5px;
			text-transform: capitalize;
		}
		button {
		    width: 110px;
		    height: 25px;
		    border-radius: 0px;
		    border: 0;
		}
		.cmd-completo {
			text-align: right;
		    background-color: var(--color-success);
		    color: var(--inverse-text-color);
		    pointer-events: none;
		}
		.cmd-aggiorna {
		    background-color: var(--color-default);
		    cursor: pointer;
		}
		
		.cmd-aggiorna.attivo{
			background-color: var(--color-warning);
		}

		.cmd-aggiorna.attivo>i{
			animation: fa-spin 2s infinite linear;
		}
		
		table tbody tr td:first-of-type a {
			cursor: pointer;
		}
	</style>
	<div class="container">
		<div class="parametriDiv">
			<div class="etichetta">
		      	<div><label data-etichetta="label.descrizione" /></div>      	
		    </div>        
		    <div class="parametro">
				<div></div>	        
	        </div>
		</div>
		<div class="vbg-form">
			<div class="form-group">
				<table class="vbg-table">
					<thead>
						<tr class="header">
							<th><label data-etichetta="label.codice" /></th>
							<th><label data-etichetta="label.data" /></th>
							<th><label data-etichetta="label.descrizione" /></th>
	                        <th><label data-etichetta="label.ultimo_stato" /></th>
	                        <th><label data-etichetta="label.stato_avanzamento" /></th>
	                        <th><label data-etichetta="label.azioni" /></th>			
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div class="form-group">
				<div class='btn btn-secondary' id='chiudi' data-etichetta="button.back"></div>
			</div>
		</div>
	</div>
	`;
	
	static get observedAttributes() {
        return [
			'data-alias',
			'data-software',
            'data-titolo',
            'data-auth',
            'data-return-to'
        ];
    }
    
    get alias() { return this._alias; }
    set alias(val) { this._alias = val; }
    
    get software() { return this._software; }
    set software(val) { this._software = val; }
    	
    get titolo() { return this._titolo; }
    set titolo(val) { this._titolo = val; this._impostaTitolo(); }
    
    get auth() { return this._auth; }
    set auth(val) { this._auth = val; }
    
	get returnTo() { return this._returnTo; }
    set returnTo(val) { this._returnTo = val; }
    
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
    
     attributeChangedCallback(attrName, oldValue, newValue) {

        if (newValue !== oldValue) {
            switch (attrName) {
				case 'data-alias':
					this.alias = newValue;
					break;
				case 'data-software':
					this.software = newValue;
					break;
                case 'data-titolo':
                    this.titolo = newValue;
                    break;
                case 'data-etichetta-titolo':
                    this.etichettaTitolo = newValue;
                    break;
				case 'data-auth':
					this.auth = newValue;
					break;
                case 'data-return-to':
                    this.returnTo = newValue;
                    break;
            }

        }
    }
    
    connectedCallback() {
		vbgRisolviEtichette(this.shadowRoot, this.alias, this.software);
		
		this.shadowRoot.querySelector('#chiudi').addEventListener('click', (e) => {
			e.preventDefault();
			location.replace(this.returnTo);			
		});
	}
	
	disconnectedCallback() {
		
    }
    
    databind( dati ){
	
		if( dati == null ){
			return;
		}
		
		let tBody = this.shadowRoot.querySelector('table.vbg-table tbody');
		
		dati.forEach(el => {
							
			let href = document.createElement('a');
				href.dataset.id = el.id;
				href.textContent = el.id;
				href.addEventListener('click', function(e){
					e.preventDefault();
					e.target.dispatchEvent(new CustomEvent("detailclick",{ 
						bubbles: true,
  						composed: true,
						detail: {id: el.id }}));
			})
				
							
			let tdCodice = document.createElement('td');
				tdCodice.appendChild(href);
							
			let tdData = document.createElement('td');
				tdData.textContent = el.data_string;
								
			let tdDescrizione = document.createElement('td');
				tdDescrizione.textContent = el.descrizione;
					
			let tdUltimoStato = document.createElement('td');
				tdUltimoStato.setAttribute('name','ultimo-stato');
				tdUltimoStato.appendChild(this._riepilogoStatiComunicazione(el.operazioni));
			
			let tdAvanzamento = document.createElement('td');
				tdAvanzamento.setAttribute('name','avanzamento');
				tdAvanzamento.appendChild(this._riepilogoStatoAvanzamentoComplessivo(el));
						
			let tdAzioni = document.createElement('td');
				tdAzioni.append(this._buttonAzione(el));
			
			let tr = document.createElement('tr');
				tr.dataset.id = el.id;
				tr.appendChild(tdCodice);
				tr.appendChild(tdData);
				tr.appendChild(tdDescrizione);
				tr.appendChild(tdUltimoStato);
				tr.appendChild(tdAvanzamento);
				tr.appendChild(tdAzioni);
			
			tBody.appendChild(tr);
		});
	}
	
	_riepilogoStatiComunicazione(operazioni){
		let ulStati = document.createElement('ul');
			ulStati.classList.add('avanzamento-stati');
				
		operazioni.forEach( op => {
			let liStato = document.createElement('li');
				liStato.textContent = op.titolo + ': ' + op.totale_operazioni_eseguite;
			ulStati.appendChild(liStato);

		});
		
		return ulStati;
	}
	
	_riepilogoStatoAvanzamentoComplessivo(el){
		
		let progress = document.createElement('progress');
			progress.setAttribute('max',el.totale_operazioni_richieste);
			progress.value = el.totale_operazioni_completate;

		let spanAvanzamento = document.createElement('span');
			spanAvanzamento.appendChild(document.createTextNode(el.totale_operazioni_completate + ' su ' + el.totale_operazioni_richieste));
		
		let div = document.createElement('div');
			div.appendChild(progress);
			div.appendChild(spanAvanzamento);
			
		return div;
	}
	
	_buttonAzione(comunicazione){
			
		let iAzione = document.createElement('i');
			iAzione.classList.add('fa');
		
		let label = document.createElement('span');
			
		let button = document.createElement('button');
			button.appendChild(iAzione);
			button.appendChild(label);
		
		if( comunicazione.completa ){
			iAzione.classList.add('fa-check');
			button.classList.add('cmd-completo');
			label.setAttribute('data-etichetta','label.completato');
		} else {
			iAzione.classList.add('fa-refresh');
			button.classList.add('cmd-aggiorna');
			label.setAttribute('data-etichetta','label.aggiorna');
			button.addEventListener('click', async (e) => {
				e.preventDefault();
				
				try {
					vbg.mostraModalCaricamento();
					
					button.classList.add('attivo');
					
					const headers = { 'Authorization': this.auth };
					
					const url = '../services/rest-private/comunicazioni/' + comunicazione.id + '/elabora';
					
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
	       			
	       			
	       			let tdUltimoStato = this.shadowRoot.querySelector('tr[data-id="' + comunicazione.id + '"] td[name="ultimo-stato"]');
	       				tdUltimoStato.innerHTML = '';
	       				tdUltimoStato.appendChild(this._riepilogoStatiComunicazione(new Array(responseJson.comunicazione.operazioni)));
	       				
					let tdAvanzamento = this.shadowRoot.querySelector('tr[data-id="' + comunicazione.id + '"] td[name="avanzamento"]');
	       				tdAvanzamento.innerHTML = '';
	       				tdAvanzamento.appendChild(this._riepilogoStatoAvanzamentoComplessivo(responseJson.comunicazione));

				}
				finally {
					button.classList.remove('attivo');
					vbg.nascondiModalCaricamento(); 
				}
			});
		}
		
		return button;
	}
    
    _impostaTitolo( ){
		this.shadowRoot.querySelector('div.parametriDiv div.parametro div').textContent = this.titolo;
	}
}

customElements.define('vbg-comunicazioni-massive-list', VbgComunicazioniMassiveList);