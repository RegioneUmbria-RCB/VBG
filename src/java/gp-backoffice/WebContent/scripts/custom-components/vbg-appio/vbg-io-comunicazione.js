import { copiaStiliDaDocument } from '../copia-stili-da-document.js';


export class VbgIoComunicazione extends HTMLElement {
	
	 templateHtml = `  <style>
    h1 {
        border-left: var(--half-padding) solid var(--color-default);
        padding-left: var(--half-padding);
        margin-top: 0;
    }

    h1.success {
        border-color: var(--color-success);
    }

    h1.warning {
        border-color: var(--color-warning);
    }

    h1.critical {
        border-color: var(--color-critical);
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
    .textarea{
		  min-width:40em;
		  min-height:8em;
		  border: 1px solid var(--form-element-border-color);
		  display:inline-block;
		  padding: var(--half-padding) var(--default-padding);
		  box-sizing: border-box;
		  font-size: 1em;
	      resize: auto;
	      cursor: text;
	      white-space-collapse: preserve;
	      overflow: scroll;
    	  max-height: 16em;
	}

    </style>
    <vbg-modal id='pop-up-com'>
        <div slot='body' style='position: relative'>
            <h1 id='titolo'>Dettaglio comunicazione APPIO</h1>
            <div id='form' class='vbg-form'>
            	<div class="form-group">
            		<label>Stato messaggio</label>
            		<textarea id="stato-messaggio"></textarea>
            	</div>
            	<div class="form-group">
            		<label>Ultimo stato</label>
            		<input id="in_ultimostato" type="text"  placeholder="ultimo stato" size="50" readonly/>
            	</div>
            	<div class="form-group">
            		<label>Data Ultimo stato</label>
            		<input id="in_dataultimostato" type="text"  placeholder="ultimo data stato" size="50" readonly/>
            	</div>
            	<div class="form-group">
            		<label>Oggetto</label>
            		<div class="textarea" id="oggetto-messaggio"></div>
            	</div>
            	<div class="form-group">
            		<label>Messaggio</label>
            		<div class="textarea" id="messaggio"></div>
            	</div>
            	<div class="form-group">
            		<label>Codice fiscale</label>
            		<input id="in_codicefiscale" type="text"  placeholder="movimento" size="50" readonly/>
            	</div>
            	<div class="form-group">
            		<label>Identificativo servizio</label>
            		<input id="in_idservizio" type="text"  placeholder="movimento" size="50" readonly/>
            	</div>
            
            </div>
        </div>
        <div slot='footer' style='position: relative; margin-top: 8px'>
        </div>
    
    </vbg-modal>`;
	 
	 static get observedAttributes() {
        return [
			'dataset'
        ];
    }
    
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
	
		this.popup = this.shadowRoot.querySelector('#pop-up-com');
        this.form = this.shadowRoot.querySelector('#form');
        this.titolo = this.shadowRoot.querySelector('#titolo');
     
	}
    
    disconnectedCallback() {
	}
	open(){
		this.shadowRoot.querySelector('#pop-up-com').open();
	}
    
	
}
customElements.define('vbg-io-comunicazione', VbgIoComunicazione);