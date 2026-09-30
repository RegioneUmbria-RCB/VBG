import { copiaStiliDaDocument } from '../copia-stili-da-document.js';

class VbgDettaglioIstanza extends HTMLElement {

    templateHtml = `
    <style>
        #stato_mod {
            float: right;
        }

        .elenco_richiedenti{
            margin-left: var(--double-padding);
        }

        .tab-istanza{
            display: inline-block;                   
        }

        span > label {
            width: 150px !important;
        }

        .elenco_richiedenti > label{
            padding : 8px;
        }
        
        .divEditabile {
	 		width: 700px;
			height: fit-content;			
			resize: vertical;
			overflow: auto;
			display: inline-block;
			white-space: normal;
		}
        
       
    </style>
        <vbg-modal id='modal-dettaglio-istanza'>   
            <div id='info' slot='body' class='vbg-form'>
                <h1>Dettaglio istanza</h1>
                <fieldset>
                <legend>Dati istanza</legend>
                <div id='istanza' class='form-group'>
                    <span>
                        <label style="padding-right:63px;">Numero istanza:</label>
                        <p id='numero-istanza' class="tab-istanza"></p>    
                    </span>
                    <span id='stato_mod' >
                        <label>Stato:</label>
                        <p id='stato' class="tab-istanza"></p>          
                    </span>
                </div>
                <div id='protocollo' class='form-group'>
                    <label>Numero protocollo:</label>
                    <span id='numero-protocollo'></span>                    
                </div>
                <div id='intervento_mod' class='form-group'>
                    <label>Intervento:</label>
                    <div class='divEditabile' id='intervento' contenteditable="false"></div>                   
                </div>
                <div id='oggetto_mod' class='form-group'>
                    <label>Oggetto:</label>
                    <div class='divEditabile' id='oggetto' contenteditable="false"></div>                   
                </div>
                <div id='note_mod' class='form-group'>
                    <label>Note:</label>
                    <div class='divEditabile' id='note' contenteditable="false"></div>                   
                </div>
                <div id='richiedente_mod' class='form-group'>
                    <label>Richiedenti:</label>
                    <div class="elenco_richiedenti">
                        <label>Richiedente: </label><span id='richiedente' ></span> 
                        <br/>
                        <label>Intermediario: </label><span id='intermediario'></span>
                    </div>
                </div>
                <div id='stradario_mod' class='form-group'>
                    <label>Stradario:</label>
                    <span id='stradario'></span>                   
                </div>
                </fieldset>
            </div>
            <div id='footer' slot='footer' class='vbg-form'>
            </div>
        </vbg-modal>
    `;

    constructor() {
        super();

        const template = document.createElement('template');
        template.innerHTML = this.templateHtml;

        this.attachShadow({ mode: 'open' });
        this.shadowRoot.appendChild(template.content.cloneNode(true));

        copiaStiliDaDocument(this.shadowRoot);
    }

    connectedCallback() {
        this.modal = this.shadowRoot.querySelector('#modal-dettaglio-istanza');
    }

    show(json) {
        this._json = json;
        this._mostraDettaglioIstanza();
        this.modal.open();
    }

    _mostraDettaglioIstanza() {

        let istanza = this._json;
        //
        let data_istanza = new Date(istanza.data_istanza);
        let _data_istanza = data_istanza.toLocaleDateString('it-IT');
        let dati_istanza = `<b>` + istanza.numero_istanza + `</b> del <b>` + _data_istanza + `</b>`;
        this.modal.querySelector('#numero-istanza').innerHTML = dati_istanza;
        this.modal.querySelector('#stato').innerHTML = `<b>` + istanza.stato + `</b>`;
        //
        let dati_protocollo = '';
        let _data_protocollo = '';
        if (istanza.data_protocollo != null) {
            let data_protocollo = new Date(istanza.data_protocollo);
            _data_protocollo = data_protocollo.toLocaleDateString('it-IT');
        }
        if (istanza.numero_protocollo != null) {
            dati_protocollo = `<b>` + istanza.numero_protocollo + `</b> del <b>` + _data_protocollo + `</b>`;
        }
        this.modal.querySelector('#numero-protocollo').innerHTML = dati_protocollo == '' ? `<b> - </b>`  : dati_protocollo;
        //
        this.modal.querySelector('#intervento').innerHTML = `<b>` + istanza.intervento + `</b>`;
        let oggetto = istanza.oggetto == null ? '-' : istanza.oggetto;
        this.modal.querySelector('#oggetto').innerHTML = `<b>` + oggetto + `</b>` ;
        let note = istanza.note == null ? '-' : istanza.note;
        this.modal.querySelector('#note').innerHTML = `<b>` + note + `</b>`;
        this.modal.querySelector('#richiedente').innerHTML = `<b>` + istanza.richiedente + `</b>`;
        if (istanza.intermediario != null) {
            this.modal.querySelector('#intermediario').innerHTML = istanza.intermediario;
        }
        let vie = '';
        if (istanza.stradario.length > 0) {
            vie += `<ul>`;
            istanza.stradario.forEach(el => {
                vie += `<li>` + el.via + `</li>`;
            });
            vie += `</ul>`;
        }
        this.modal.querySelector('#stradario').innerHTML = vie;


    }
}

customElements.define('vbg-dettaglio-istanza', VbgDettaglioIstanza);