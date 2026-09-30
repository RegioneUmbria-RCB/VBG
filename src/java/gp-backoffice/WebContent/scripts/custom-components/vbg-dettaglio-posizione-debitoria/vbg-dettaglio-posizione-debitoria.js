import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { getDettaglioPosizione } from './get-dettaglio-posizione.js';
import { getStileIconaDaCodiceStato } from './get-stile-icona-da-codice-stato.js';
import { VbgDettaglioPosizioneDebitoriaPopup } from './vbg-dettaglio-posizione-debitoria-popup.js';
import { VbgFetchRef } from '../vbg-fetch-ref.js';

class VbgDettaglioPosizioneDebitoria extends HTMLElement {
    templateHtml = `
    
    <style>
        :host {
            box-sizing: border-box;
        }

        #vbg-stato-posizione-debitoria {
            display: inline-block;
            white-space: nowrap;
            border-radius: var(--half-padding);
            padding-top: var(--half-padding);
            padding-bottom: var(--half-padding);
            padding-right: var(--half-padding);
            cursor: pointer;
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

        .warning {
            background-color: var(--color-warning);
        }

        .critical {
            background-color: var(--color-critical);
            color: var(--inverse-text-color);
        }

        .success {
            background-color: var(--color-success);
            color: var(--inverse-text-color);
        }

        .default {
            background-color: var(--form-element-border-color);
            color: var(--text-color);
        }

        .critical #aggiorna-stato,
        .success #aggiorna-stato {
            border-left: 1px solid var(--inverse-text-color);
        }

        #caricamento-in-corso {
            display: inline-block;
            white-space: nowrap;
            border-radius: var(--half-padding);
            padding: var(--half-padding);
            background-color: var(--color-default);
        }

    </style>
    <div id='caricamento-in-corso'>
        <i class='fa fa-refresh fa-spin'></i>
    </div>

    <div id='vbg-stato-posizione-debitoria'>
        <div id='descrizione-stato'>
            <i class='' aria-hidden='true'></i>
            <div id='testo'></div>
        </div>
        
        <div id='aggiorna-stato'>
            <i class="fa fa-refresh"></i>
        </div>
    </div>
    
    <vbg-dettaglio-posizione-debitoria-popup></vbg-dettaglio-posizione-debitoria-popup>
    `;

    static get observedAttributes() {
        return [
            'id-posizione',
            'mostra-testo',
            'url-stato',
            'fetch-ref',
            'max-length'
        ];
    }

    // Id posizione debitoria
    get idPosizione() { return this._idPosizione; }
    set idPosizione(val) { this._idPosizione = val; }

    // Mostra testo
    get mostraTesto() { return this._mostraTesto == undefined ? true : this._mostraTesto; }
    set mostraTesto(val) { this._mostraTesto = val === 'true'; }

    // Url per la richiesta dello stato
    get urlStato() { return this._urlStato || ''; }
    set urlStato(val) { return this._urlStato = val; }
    
    // Lunghezza massimo campo note
    get maxLength(){return this._maxLength;}
	set maxLength(val){return this._maxLength = val;}    

    get fetchRef() { return this.fetchObj?.id || '' }
    set fetchRef(val) {

        this.fetchObj = null;

        if (typeof (val) !== 'string') {
            // fetchval è un oggetto
            // TODO: verificare che sia un oggetto del dom o sollevare errore
            this.fetchObj = val;
            console.log(val);
        } else {
            this.fetchObj = document.getElementById(val);
        }
        /*
        if (this.fetchObj) {
            this.aggiornaStatoBreve();
        }
        */
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

        console.log('attributeChangedCallback: attrName=', attrName, ', oldValue=', oldValue, ', newValue=', newValue);

        if (newValue !== oldValue) {
            switch (attrName) {
                case 'id-posizione':
                    this.idPosizione = newValue;
                    break;
                case 'mostra-testo':
                    this.mostraTesto = newValue;
                    break;
                case 'url-stato':
                    this.urlStato = newValue;
                    break;
                case 'fetch-ref':
                    this.fetchRef = newValue;
                    break;
                case 'max-length':
                	this.maxLength = newValue;
                	break;
            }
            // this.setAttribute(attrName, newValue);
        }
    }

    connectedCallback() {

        this.iconaStato = this.shadowRoot.querySelector('#descrizione-stato>i');
        this.descrizioneStato = this.shadowRoot.querySelector('#descrizione-stato>#testo');
        this.statusContainer = this.shadowRoot.querySelector('#vbg-stato-posizione-debitoria');
        this.aggiornaStato = this.shadowRoot.querySelector('#aggiorna-stato');
        this.popup = this.shadowRoot.querySelector('vbg-dettaglio-posizione-debitoria-popup');
        this.popup.maxLength = this.maxLength;
        this.caricamentoInCorso = this.shadowRoot.querySelector('#caricamento-in-corso');

        if (!this.mostraTesto) {
            this.descrizioneStato.style.display = 'none';
        }
        // TODO: leggere lo stato della posizione debitoria...
        this.caricaStatoAttuale();

        this._apriPopupListener = this._apriPopupListener.bind(this);
        this._aggiornaStatoListener = this._aggiornaStatoListener.bind(this);

        // Al click sul componente deve aprire il popup di dettaglio
        this.addEventListener('click', this._apriPopupListener);

        // Al click sul bottone aggiorna deve forzare un aggiornamento della posizione debitoria
        this.aggiornaStato.addEventListener('click', this._aggiornaStatoListener);
        this.popup.addEventListener('reload-data', this._aggiornaStatoListener);
    }

    _apriPopupListener(e) {
        e.preventDefault();
        e.stopPropagation();

        this.popup.open();
    }

    _aggiornaStatoListener(e) {
        e.preventDefault();
        e.stopPropagation();
        this.forzaAggiornamentoStato();
    }

    disconnectedCallback() {
        this.aggiornaStato.removeEventListener('click', this._aggiornaStatoListener);
        this.removeEventListener('click', this._apriPopupListener);
    }

    async forzaAggiornamentoStato() {

       	const i = this.aggiornaStato.querySelector('i');
       	i.classList.add('fa-spin');

		const data = await getDettaglioPosizione(this.dettaglioPosizione.operazioniConsentite.verificaStato, this.idPosizione)

        this.dataBind(data);

        i.classList.remove('fa-spin');
        
    }

    inizioCaricamento() {
        this.caricamentoInCorso.style.display = 'inline-block';
        this.statusContainer.style.display = 'none';
    }

    fineCaricamento() {
        this.caricamentoInCorso.style.display = 'none';
        this.statusContainer.style.display = 'inline-block';
    }

    dataBind(posizione) {
		
    	let statoPrecedente = posizione.stato;
    	if(this.dettaglioPosizione){
    		statoPrecedente = this.dettaglioPosizione.stato;
    	}
		
        this.dettaglioPosizione = posizione;
        const stileIcona = getStileIconaDaCodiceStato(this.dettaglioPosizione.codiceStato);

        this.statusContainer.className = ""; // svuoto la classe css
        this.statusContainer.classList.add(stileIcona.className);

        this.iconaStato.className = stileIcona.icon;
        this.descrizioneStato.innerHTML = stileIcona.descStato;// this.dettaglioPosizione.stato;
        this.title = stileIcona.descStato;

        if (!this.dettaglioPosizione.operazioniConsentite.verificaStato) {
            this.aggiornaStato.style.display = 'none';
        }

        this.dispatchEvent(new CustomEvent('data-loaded', { detail: this.dettaglioPosizione }));
        
        if(statoPrecedente !== this.dettaglioPosizione.stato){
        	this.dispatchEvent(new CustomEvent('stato-modificato', {
	            detail: {
	                idPosizione: this._idPosizione
	            }
       		 }));
        }
        
    }

    async caricaStatoAttuale() {

        try {

            this.inizioCaricamento();

            if (this.urlStato === '' && this.fetchObj === null) {
                throw 'Errore di configurazione, non è stato trovato un url per la lettura dello stato e non è stato configurato un fetch-ref';
            }

            let posizione = (this.fetchObj == null) ?
                (await getDettaglioPosizione(this.urlStato, this.idPosizione)) :
                (await this.fetchObj.read({
                    data: {
                        idDettPosizioneDebitoria: this.idPosizione
                    }
                }));

            if (posizione == null) {
                this.style.display = 'none';
				return;
            }

            this.dataBind(posizione);
        }
        catch (e) {
            console.error(e);
            this.style.display = 'none';
        }
        finally {
            this.fineCaricamento();
        }
    }


}

customElements.define('vbg-dettaglio-posizione-debitoria', VbgDettaglioPosizioneDebitoria);