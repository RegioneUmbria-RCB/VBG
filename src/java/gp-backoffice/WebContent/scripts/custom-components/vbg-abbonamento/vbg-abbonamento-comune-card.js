import { copiaStiliDaDocument } from '../copia-stili-da-document.js';

class VbgAbbonamentoComuneCard extends HTMLElement {
    templateHtml = `
	<style>
		.no-pointer-event {
			pointer-events: none;
		}
		
    	div.tablink {
			display: inline-block;
			background-color: inherit;
			border: none;
			outline: none;
			cursor: pointer;
			padding: 14px 16px;
			transition: 0.3s;
			border-right: 1px solid #ccc;
			vertical-align: top;
		}
		
		div.tablink:hover {
			background-color: #ddd;
		}
		
		div.tablink.active {
			background-color: #ccc;
		}
		
								
		div.tablink div.container {
			text-align:center;
			width:100%;
		}
		
		div.tablink div.container .avviso {
			font-weight: bold;
			margin:1px;
			width:20%;
			text-align:center;
			display:inline-block;
		}
			
    </style>
	<div id="dettaglio" class="tablink" 
		data-codice="" 
		data-comune=""
		data-messaggi=""
		data-informative=""
		data-ricariche=""
		>
		<div class="desc-comune no-pointer-event"></div>
		<div class="container no-pointer-event">
			<div class="avviso no-pointer-event messaggi">
				&nbsp;M
			</div>
			<div class="avviso no-pointer-event informative">
				&nbsp;I
			</div>
			<div class="avviso no-pointer-event ricariche">
				&nbsp;R
			</div>
		</div>
	</div>
	`;

    static get observedAttributes() {
        return [
            'codice',
            'comune',
            'num-messaggi',
            'num-informative',
            'num-ricariche'
        ];
    }

    // Codice comune
    get codice() { return this._codice; }
    set codice(val) { this._codice = val; }

    // Descrizione comune
    get comune() { return this._comune; }
    set comune(val) { this._comune = val; }

    // Num-messaggi
    get numMessaggi() { return this._numMessaggi == undefined ? 0 : this._numMessaggi; }
    set numMessaggi(val) { this._numMessaggi = val; this._refreshCounters(); }    

    // Num-informative
    get numInformative() { return this._numInformative == undefined ? 0 : this._numInformative; }
    set numInformative(val) { this._numInformative = val; this._refreshCounters(); }    
    
    // Num-ricariche
    get numRicariche() { return this._numRicariche == undefined ? 0 : this._numRicariche; }
    set numRicariche(val) { this._numRicariche = val; this._refreshCounters(); }
        
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
                case 'codice':
                    this.codice = newValue;
                    break;
                case 'comune':
                    this.comune = newValue;
                    break;
                case 'num-messaggi':
                    this.numMessaggi = newValue;
                    break;
                case 'num-informative':
                    this.numInformative = newValue;
                    break;
                case 'num-ricariche':
                	this.numRicariche = newValue;
                	break;
            }

        }
    }
    
    _refreshCounters(){
		this.divMessaggi = this.shadowRoot.querySelector('.avviso.messaggi');
		this.divInformative = this.shadowRoot.querySelector('.avviso.informative');
		this.divRicariche = this.shadowRoot.querySelector('.avviso.ricariche');
		this.divMessaggi.style.visibility = this.numMessaggi > 0 ? 'visible' : 'hidden';
		this.divInformative.style.visibility = this.numInformative > 0 ? 'visible' : 'hidden';
		this.divRicariche.style.visibility = this.numRicariche > 0 ? 'visible' : 'hidden';
	}

    connectedCallback() {
		
		this.tablink = this.shadowRoot.querySelector('#dettaglio');
		this.tablink.dataset.codice = this.codice;
		this.tablink.dataset.comune = this.comune;
		this.tablink.dataset.messaggi = this.numMessaggi;
		this.tablink.dataset.informative = this.numInformative;
		this.tablink.dataset.ricariche = this.numRicariche;
		
		this.divComune = this.shadowRoot.querySelector('.tablink .desc-comune');
		this.divComune.appendChild( document.createTextNode(this.comune) );
				
		this._refreshCounters();
				
		this.addClickListener('#dettaglio', this._raiseClickComune);
		
    }
    
    disconnectedCallback() {

    }

	addClickListener(selector, callback) {
        const el = this.shadowRoot.querySelector(selector);
        el.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            callback.bind(this)(e);
        });
	}
    
    isSelezionato(){
		return this.tablink.classList.contains('active');
	}
    
    _raiseClickComune(e){
		
		this.tablink.classList.toggle('active');

        this.dispatchEvent(new CustomEvent('click', {
            detail: {
                codice: this.codice,
                comune: this.comune,
                selezionato: this.tablink.classList.contains('active')
            }
        }));
        
	}
}

customElements.define('vbg-abbonamento-comune-card', VbgAbbonamentoComuneCard);