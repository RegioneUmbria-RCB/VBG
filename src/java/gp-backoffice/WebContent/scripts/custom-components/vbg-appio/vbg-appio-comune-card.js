import { copiaStiliDaDocument } from '../copia-stili-da-document.js';

class VbgAppIoComuneCard extends HTMLElement {
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
		>
		<div class="desc-comune no-pointer-event"></div>
		<div class="container no-pointer-event">
		</div>
	</div>
	`;

    static get observedAttributes() {
        return [
            'codice',
            'comune',
            'active'
        ];
    }

    // Codice comune
    get codice() { return this._codice; }
    set codice(val) { this._codice = val; }

    // Descrizione comune
    get comune() { return this._comune; }
    set comune(val) { this._comune = val; }
    //active
	get active() { return this._active; }
    set active(val) { this._active = val; }
        
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
                case 'active':
                    this.active = newValue;
                    break;
            }

        }
    }
    
    _refreshCounters(){
		/*this.divMessaggi = this.shadowRoot.querySelector('.avviso.messaggi');
		this.divInformative = this.shadowRoot.querySelector('.avviso.informative');
		this.divRicariche = this.shadowRoot.querySelector('.avviso.ricariche');
		this.divMessaggi.style.visibility = this.numMessaggi > 0 ? 'visible' : 'hidden';
		this.divInformative.style.visibility = this.numInformative > 0 ? 'visible' : 'hidden';
		this.divRicariche.style.visibility = this.numRicariche > 0 ? 'visible' : 'hidden';*/
	}

    connectedCallback() {
		
		this.tablink = this.shadowRoot.querySelector('#dettaglio');
		this.tablink.dataset.codice = this.codice;
		this.tablink.dataset.comune = this.comune;
		if(this.active){
			this.tablink.classList.add('active');
		}
		
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

customElements.define('vbg-appio-comune-card', VbgAppIoComuneCard);