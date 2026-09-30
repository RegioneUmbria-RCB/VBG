import { copiaStiliDaDocument } from '../../copia-stili-da-document.js';


export class BottoneAggiornaDataScadenza extends HTMLElement {


    _idDettPosizioneDebitoria;

    get dettPosizioneDebitoria() { return this._idDettPosizioneDebitoria; }
    set dettPosizioneDebitoria(value) { this._idDettPosizioneDebitoria = value; }

    // Url verso cui effettuare la chiamata per aggiornare la data di scadenza
    _urlAggiornamento;
    get urlAggiornamento() { return this._urlAggiornamento; }
    set urlAggiornamento(value) { this._urlAggiornamento = value; }

    // Legge o imposta la data nel formato dd/mm/yyyy
    get dataScadenza() {

        if (!this._dataScadenza.value) {
            return null;
        }

        const dateParts = this._dataScadenza.value.split('-');
        return `${dateParts[2]}/${dateParts[1]}/${dateParts[0]}`;
    }
    set dataScadenza(value) {

        if (!value) {
            this._dataScadenza.value = '';
            return;
        }

        const dateParts = value.split('/');
        this._dataScadenza.value = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`;
    }

    _template = `
    <div class='vbg-form'>
        <div class='form-group'>
            <label id='label'>Data scadenza</label>
            <input id='data-scadenza' type='date'>
            <span id='aggiorna-data-scadenza' class='btn btn-primary'>
                <i class='fa fa-pencil'></i>
                <i class='fa fa-refresh fa-spin hidden hide-on-data-loaded'></i>
                Aggiorna data scadenza
            </span>
        </div>
    </div>`;


    constructor() {
        super();

        const template = document.createElement('template');
        template.innerHTML = this._template;

        this.attachShadow({ mode: 'open' });
        this.shadowRoot.appendChild(template.content.cloneNode(true));

        copiaStiliDaDocument(this.shadowRoot);

        this._label = this.shadowRoot.querySelector('#label');
        this._dataScadenza = this.shadowRoot.querySelector('#data-scadenza');
        this._aggiorna = this.shadowRoot.querySelector('#aggiorna-data-scadenza');
    }

    connectedCallback() {
        this.dataScadenza = this.getAttribute('value');
        this.urlAggiornamento = this.getAttribute('url');
        this.dettPosizioneDebitoria = this.getAttribute('dett-posizione-debitoria');

        this._aggiornaDataScadenzaHandler = this._aggiornaDataScadenzaHandler.bind(this);

        this._aggiorna.addEventListener('click', this._aggiornaDataScadenzaHandler);
    }


    async _aggiornaDataScadenzaHandler(e) {

        e.preventDefault();
        e.stopPropagation();

        if (!this.dataScadenza) {
            return;
        }

        // Mostro lo spinner di caricamento
        const pencil = this.shadowRoot.querySelector('.fa-pencil');
        const refresh = this.shadowRoot.querySelector('.fa-refresh');

        pencil.classList.add('hidden');
        refresh.classList.remove('hidden');

        try {

            // Invio i dati
            const response = await fetch(this.urlAggiornamento, {
                method: 'POST',
                cache: 'no-cache',
                body: new URLSearchParams({
                    // 'idDettPosizioneDebitoria': this._dati.id,
                    'dataScadenza': this.dataScadenza
                })
            });

            if (response.status !== 200) {
                // Errore? Mostro l'alert
                const errore = await response.text();
                console.error(errore);
                alert(errore);
                return;
            }
            // processo la risposta
            // per ora non faccio nulla a meno che il servizio non ritorni il json 
            // della posizione debitoria


            // La data scadenza è stata aggiornata :)
            this.dispatchEvent(new CustomEvent('data-scadenza-modificata'));

        } finally {
            // nascondo lo spinner di caricamento
            pencil.classList.remove('hidden');
            refresh.classList.add('hidden');
        }
    }

    disconnectedCallback() {
        this._aggiorna.removeEventListener('click', this._aggiornaDataScadenzaHandler);
    }

}

customElements.define('bottone-aggiorna-posizione-debitoria', BottoneAggiornaDataScadenza);