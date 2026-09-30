import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import '../marked.min.js';


class VbgAbbonamentoPreview extends HTMLElement {
    templateHtml = `
	<style>
		fieldset {
			border: 1px solid var(--form-element-border-color);
    		padding: var(--default-padding);
    		margin-bottom: var(--default-padding);
		}
		legend {
			border: 1px solid var(--form-element-border-color);
    		padding: 4px var(--default-padding);
    		text-transform: uppercase;
    		font-size: 1.2em;
		}
		
		fieldset .fa-trash{
			background-color: transparent;
    		border: 0;
    		padding: 0;
    		margin: 0;
    		cursor: pointer;
    		color: #b61218;
    		float: right;
		}
		
		li.ricarica span {
			padding-right: 5px;
			cursor: pointer;
    		color: #b61218;
    		font-weight: bold;
		}
		
		.funzioni-dettaglio {
			margin: 0;
    		padding: 0;
    		list-style-type: none;
    		width: 100%;
    		float: left;
		}
		
		.funzioni-dettaglio>li {
			display: inline-block;
    		margin-right: 16px;
    		cursor: pointer;
    		color: #b61218;
		}
		
		.div-elimina {
			float: right;
		}
		
		.modifiche-non-salvate {
			font-weight: bold;
    		color: #b61218;
    		font-style: italic;
		}
		
		.modifiche-non-salvate:before{
			content: " NON SALVATO";
		}

    </style>
	<div id="preview" data-codice="">
		<h3></h3>
		<fieldset id="messaggio">
			<legend>Messaggio ricarica non disponibile<span></span></legend>
			<div></div>
		</fieldset>
		<div id="informative"></div>
		<div id="ricariche"></div>
	</div>
	`;

    static get observedAttributes() {
        return [
			'dataset'
        ];
    }

    // Dataset JSON
    get dataset() { return this._dataset; }
    set dataset(val) { this._dataset = val; }
    
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
                case 'dataset':
                    this.dataset = newValue;
                    break;
            }

        }
    }

    connectedCallback() {
				
		this.preview = this.shadowRoot.querySelector('#preview');
		this.divMessaggio = this.shadowRoot.querySelector('#messaggio div');
		this.divInformative = this.shadowRoot.querySelector('#informative');
		this.divRicariche = this.shadowRoot.querySelector('#ricariche');
		
		this.preview.dataset.codice = this.dataset.codice;
		
		this.descComune = this.shadowRoot.querySelector('#preview h3');
		this.descComune.textContent = this.dataset.comune;
		
		
		if( this.dataset.messaggio != undefined ){
			this.divMessaggio.innerHTML = marked.parse(this.dataset.messaggio);
		}
				
		this.dataset.informative.forEach( informativa => {
			this._aggiungiInformativa('informativa', informativa.id, informativa.informativa, informativa.scadenza, true);
		});
		
		let span = document.createElement('span');
		
		let legend = document.createElement('legend');
			legend.textContent = 'Modalità di ricarica';
			legend.appendChild(span);
			
		let ol = document.createElement('ol');
			ol.setAttribute('name','tiporicarica_ol');
			ol.classList.add('fa-ul');
			
		let div = document.createElement('div');
			div.appendChild(ol);
			div.classList.add('ricarica');
		
		let fieldset = document.createElement('fieldset');
			fieldset.setAttribute('name','ricarica');
			fieldset.appendChild(legend);
			fieldset.appendChild(div);
		
		this.divRicariche.appendChild(fieldset);

		
		this.dataset.ricariche.forEach( ricarica => {
			this._aggiungiRicarica('', ricarica.id, ricarica.tipologia.descrizione, ricarica.etichetta, ricarica.importo);
		});
		
    }
    
    disconnectedCallback() {

    }
    
    scriviMessaggio(testo){
		
		this.shadowRoot.querySelector('#messaggio legend span').classList.add('modifiche-non-salvate');
	
		this.divMessaggio.innerHTML = marked.parse(testo);
	}
	
	_testoRicarica(descrizione, etichetta, importo){
		return descrizione + ' ( ' + etichetta + ' ' + importo + ' \u20AC )';
	}
	
	_aggiungiRicarica(name, idRicarica, descrizione, etichetta, importo){
		let ol = this.shadowRoot.querySelector('fieldset[name="ricarica"] div ol');
		
		let span = document.createElement('span');
			span.textContent = 'X';
			span.dataset.id = idRicarica;
			span.addEventListener('click', async function(e){
				e.preventDefault();
				e.stopPropagation();
				
				if(!confirm('Procedere con l\'eliminazione della metodologia di ricarica?')){
					return;
				}
				
				try
				{
					const postParams = { request: { codice: e.target.dataset.id } };
	
	                const response = await fetch('../abbonamenticonfig/jsonDelRicarica.htm', {
	                	method: 'POST',
	                    headers: {
	                    	'Accept': 'application/json',
	                        'Content-Type': 'application/json',
						},
	                    body: JSON.stringify(postParams)
					});
	                	
	                if( await response.status == 200){
						e.target._rimuoviRicarica(postParams.request.codice);
						const event = new CustomEvent("ricaricaCancellata",{ detail: e.target._conteggi()});
	                	e.target.dispatchEvent(event);
	                }
				} 
				catch(error) {                                    
	            	alert(error);
				}
				finally{}
			});
				
		let li = document.createElement('li');
			li.classList.add('ricarica');
			li.setAttribute('name',name);
			li.dataset.id = idRicarica;
			li.appendChild(span);
			li.appendChild(document.createTextNode(this._testoRicarica(descrizione, etichetta, importo)));
			
		ol.appendChild(li);
		
		return li;
	}
	
	nuovaRicarica(descrizione, etichetta, importo){
		let liRicarica = this.shadowRoot.querySelector('fieldset[name="ricarica"] div.ricarica ol li[name="ricarica_new"]');
		if(liRicarica != null && etichetta == '' ){
			this.shadowRoot.querySelector('fieldset[name="ricarica"] div.informativa ol').removeChild(liRicarica);
			return;
		}
		
		if( etichetta == '' ){
			return;
		}
		
		let span = document.createElement('span');
			span.textContent = 'X';
			
		
		if( liRicarica == null ){
			liRicarica = this._aggiungiRicarica('ricarica_new', null, descrizione, etichetta, importo)	;
		}
		
		this.shadowRoot.querySelector('#ricariche fieldset legend span').classList.add('modifiche-non-salvate');
		
		liRicarica.textContent = '';
		liRicarica.appendChild(document.createTextNode(this._testoRicarica(descrizione, etichetta, importo)));
	}
	
	_rimuoviInformativa(id){
		
		this.divInformative.removeChild(this.divInformative.querySelector('fieldset[data-id="'+id+'"]'));
	}
	
	_rimuoviRicarica(id){
		
		this.divRicariche.querySelector('fieldset div ol').removeChild(this.divRicariche.querySelector('fieldset div ol li[data-id="'+id+'"]'));
	}
	
	_conteggi() {
		let detail =
		{
			messaggi: this.divMessaggio.textContent != '' ? 1 : 0,
			informative: this.divInformative.querySelectorAll('fieldset').length,
			ricariche: this.divRicariche.querySelectorAll('fieldset div ol li').length
		};
		
		return detail;
	}
	
	_testoLegendInformativa(scadenza){
		return 'Informativa ( scadenza ' + new Date(scadenza).toLocaleDateString('it-it', { year:"numeric", month:"2-digit", day:"2-digit"}) + ' )';
	}
	
	_aggiungiInformativa(name, id, informativa, scadenza, showElimina){		
		let legend = document.createElement('legend');
			legend.appendChild(document.createTextNode(this._testoLegendInformativa(scadenza)));
			
		
		let liElimina = document.createElement('li');
			liElimina.classList.add('azione');
			liElimina.classList.add('cmd-elimina');
			liElimina.dataset.id = id;
			liElimina.appendChild(document.createTextNode('Elimina'));
			liElimina.addEventListener('click', async function(e){
				e.preventDefault();
				e.stopPropagation();
				
				if(!confirm('Procedere con l\'eliminazione dell\'informativa?')){
					return;
				}
					
				try
				{
					const postParams = { request: { codice: e.target.dataset.id } };
	
	                const response = await fetch('../abbonamenticonfig/jsonDelInformativa.htm', {
	                	method: 'POST',
	                    headers: {
	                    	'Accept': 'application/json',
	                        'Content-Type': 'application/json',
						},
	                    body: JSON.stringify(postParams)
					});
	                	
	                if( await response.status == 200){
						e.target._rimuoviInformativa(postParams.request.codice);
						const event = new CustomEvent("informativaCancellata",{ detail: e.target._conteggi()});
	                	e.target.dispatchEvent(event);
	                }
				} 
				catch(error) {                                    
	            	alert(error);
				}
				finally{}
			});
			

		let ulElimina = document.createElement('ul');
			ulElimina.classList.add('funzioni-dettaglio');
			ulElimina.appendChild(liElimina);
		
		let divElimina = document.createElement('div');
			divElimina.appendChild(ulElimina);
			divElimina.classList.add('div-elimina');
		
		let div = document.createElement('div');
			div.innerHTML = marked.parse(informativa);
			div.classList.add('informativa');
			
		let fieldset = document.createElement('fieldset');
			fieldset.dataset.id = id;
			fieldset.setAttribute('name',name);
			fieldset.appendChild(legend);
			if( showElimina ){
				fieldset.appendChild(divElimina);				
			}
			fieldset.appendChild(div);
		
		this.divInformative.appendChild(fieldset);
		
		
		return fieldset;
	}
	
	nuovaInformativa(informativa, scadenza){
		
		let fieldSet = this.shadowRoot.querySelector('fieldset[name="informativa_new"]');
		
		if( fieldSet != null &&  informativa == '' ){
			this.shadowRoot.querySelector('#informative').removeChild(fieldSet);
		}
				
		if( informativa == '' || scadenza == '' ){
			return;
		}
		
		if( fieldSet == null ){
			fieldSet = this._aggiungiInformativa('informativa_new', null, informativa, scadenza, false);	
		}
		fieldSet.querySelector('legend').textContent = '';
		fieldSet.querySelector('legend').appendChild(document.createTextNode(this._testoLegendInformativa(scadenza)));
		let span = document.createElement('span');
			span.classList.add('modifiche-non-salvate');
		fieldSet.querySelector('legend').appendChild(span);
		fieldSet.querySelector('div.informativa').innerHTML = marked.parse(informativa);
	}
    
}

customElements.define('vbg-abbonamento-preview', VbgAbbonamentoPreview);