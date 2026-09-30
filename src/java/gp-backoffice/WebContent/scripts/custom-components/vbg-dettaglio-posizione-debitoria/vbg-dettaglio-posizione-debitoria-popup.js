import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { getStileIconaDaCodiceStato } from './get-stile-icona-da-codice-stato.js';
import { scaricaDocumentoConRetry } from './utils/scarica-documento-con-retry.js';
import { BottoneAggiornaDataScadenza } from './bottoni/bottone-aggiorna-data-scadenza.js';
import { VbgContaCaratteriRimanenti } from './vbg-conta-caratteri-rimanenti.js';


export class VbgDettaglioPosizioneDebitoriaPopup extends HTMLElement {
    templateHtml = `
    <style>
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

    </style>
    <vbg-modal id='pop-up'>
        <div slot='body' style='position: relative'>
            <h1 id='titolo'>Dettaglio posizione debitoria</h1>
            <div id='form' class='vbg-form'></div>

            <div id='rate'></div>      
            
            <div id="noteAnnullamento">
                <h1>Note annullamento</h1>
                <div class="avvertimento-utente" style="margin-bottom: var(--default-padding);font-size: 1.1em;">
                    L'operazione che si sta per eseguire non potrà essere annullata.<br>
                    Specificare la ragione dell'annullamento e fare click su "Conferma" per proseguire
                </div>
                <div class="vbg-form">
                    <div class="form-group">
                            <label>Ragione annullamento</label>
                            <textarea class="form-control" id="txtNoteAnnullamento"></textarea>
                            
                    </div>
                    <div class="form-group" id="formContacaratteri">
                    	<label>numero dei caratteri</label>
                        <div id="areaContaCaratteri"></div>
                     </div
                </div>
            </div>
           

        </div>

        <div slot='footer' style='position: relative; margin-top: 8px'>
            <div id="comandiStandard">
                <div class='btn btn-primary' id='cmdAggiorna'><i class='fa fa-refresh fa-spin hidden hide-on-data-loaded'></i> Aggiorna</div>
                <div class='btn btn-primary' id='cmdPagaOffline'>Paga offline</div>
                <div class='btn btn-primary' id='cmdAvvisoDiPagamento'><i class='fa fa-refresh fa-spin hidden'></i> Avviso di pagamento</div>
                <div class='btn btn-primary' id='cmdFattura'><i class='fa fa-refresh fa-spin hidden'></i> Fattura</div>
                <div class='btn btn-primary' id='cmdRicevuta'><i class='fa fa-refresh fa-spin hidden'></i> Ricevuta</div>
                <div class='btn btn-primary' id='cmdAttivaSessione'><i class='fa fa-refresh fa-spin hidden'></i> Attiva sessione di pagamento</div>
                <div class='btn btn-primary' id='cmdIniziaAnnullamentoPosizione'><i class='fa fa-refresh fa-spin hidden'></i> Annulla posizione</div>
            </div>

            <div id='comandiAnnullamentoPosizione'>
                <div class='btn btn-primary' id='cmdConfermaAnnullamentoPosizione'><i class='fa fa-refresh fa-spin hidden hide-on-data-loaded'></i> Conferma</div>
                <div class='btn btn-secondary' id='cmdAnnullaAnnullamentoPosizione'>Torna indietro</div>
            </div>
        </div>
    
    </vbg-modal>`;

    // Lunghezza massimo campo note
    get maxLength(){return this._maxLength;}
	set maxLength(val){return this._maxLength = val;}  
	
    constructor() {
        super();

        const template = document.createElement('template');
        template.innerHTML = this.templateHtml;

        this.attachShadow({ mode: 'open' });
        this.shadowRoot.appendChild(template.content.cloneNode(true));
        this._dati = {};

        copiaStiliDaDocument(this.shadowRoot);
    }

    connectedCallback() {
        this.popup = this.shadowRoot.querySelector('#pop-up');
        this.form = this.shadowRoot.querySelector('#form');
        this.titolo = this.shadowRoot.querySelector('#titolo');

        // Collegamento degli handler dei bottoni
        this.cmdAggiorna = this.addClickListener('#cmdAggiorna', this._aggiornaListener);
        this.cmdPagaOffline = this.addClickListener('#cmdPagaOffline', this._pagaOfflineListener);
        this.cmdAvvisoDiPagamento = this.addClickListener('#cmdAvvisoDiPagamento', this._avvisoDiPagamentoListener);
        this.cmdFattura = this.addClickListener('#cmdFattura', this._fatturaListener);
        this.cmdRicevuta = this.addClickListener('#cmdRicevuta', this._ricevutaListener);
        this.cmdAttivaSessione = this.addClickListener('#cmdAttivaSessione', this._attivaSessioneDiPagamentoListener);

        // Annullamento posizione debitoria
        this.cmdIniziaAnnullamentoPosizione = this.addClickListener('#cmdIniziaAnnullamentoPosizione', this._iniziaAnnullamentoPosizione);
        this.cmdAnnullaAnnullamentoPosizione = this.addClickListener('#cmdAnnullaAnnullamentoPosizione', this._annullaAnnullamentoPosizione);
        this.cmdConfermaAnnullamentoPosizione = this.addClickListener('#cmdConfermaAnnullamentoPosizione', this._confermaAnnullamentoPosizioneListener);
        this.txtNoteAnnullamento = this.shadowRoot.querySelector('#txtNoteAnnullamento');

        this._dataLoadedListener = this._dataLoadedListener.bind(this);
        this.getRootNode().host.addEventListener('data-loaded', this._dataLoadedListener);
    }

    addClickListener(selector, callback) {
        const el = this.shadowRoot.querySelector(selector);
        el.addEventListener('click', (e) => {
            e.preventDefault();
            e.stopPropagation();
            callback.bind(this)(e);
        });

        return el;
    }

    disconnectedCallback() {
        this.cmdAggiorna.removeEventListener('click', this._aggiornaListener);
        this.cmdPagaOffline.removeEventListener('click', this._pagaOfflineListener);
        this.cmdAvvisoDiPagamento.removeEventListener('click', this._avvisoDiPagamentoListener);
        this.cmdFattura.removeEventListener('click', this._fatturaListener);
        this.cmdRicevuta.removeEventListener('click', this._ricevutaListener);
        this.cmdAttivaSessione.removeEventListener('click', this._attivaSessioneDiPagamentoListener);
        this.cmdIniziaAnnullamentoPosizione.removeEventListener('click', this._iniziaAnnullamentoPosizione);
        this.cmdAnnullaAnnullamentoPosizione.removeEventListener('click', this._annullaAnnullamentoPosizione);
        this.cmdConfermaAnnullamentoPosizione.removeEventListener('click', this._confermaAnnullamentoPosizioneListener);

        this.getRootNode().host.removeEventListener('data-loaded', this._dataLoadedListener);
    }

    _annullaAnnullamentoPosizione(e) {
        this.popup.classList.remove('modalita-annullamento-posizione');
        this.txtNoteAnnullamento.value = '';
    }

    _iniziaAnnullamentoPosizione(e) {
        this.popup.classList.add('modalita-annullamento-posizione');
        this.txtNoteAnnullamento.value = '';
        let contenitoreContaCaratteri = this.shadowRoot.querySelector('#areaContaCaratteri');
        let vgbContaCaratteriRimanenti = new VbgContaCaratteriRimanenti(this.txtNoteAnnullamento,contenitoreContaCaratteri,this.maxLength);
        vgbContaCaratteriRimanenti.verificaLunghezza();
    }

    async _confermaAnnullamentoPosizioneListener(e) {

        if (!this.txtNoteAnnullamento.value || this.txtNoteAnnullamento.value.trim() === '') {
            alert('Specificare la ragione dell\'annullamento');
            return;
        }

        if (!confirm(`L'operazione che si sta per effettuare non è reversibile. Annullare la posizione debitoria corrente?`)) {
            return;
        }

        if (!this._dati.operazioniConsentite.annullaPosizioneDebitoria) {
            const errMsg = 'è stato richiesto l\'annullamento di una posizione debitoria ma il connettore attuale non lo consente';
            console.error(errMsg);
            alert(errMsg);
            return;
        }

        const i = this.cmdConfermaAnnullamentoPosizione.querySelector('i');

        try {
            i.classList.remove('hidden'); // spinner nascosto nel _dataLoadedListener

            let formData = new FormData();
            formData.append('idDettPosizioneDebitoria', this._dati.id);
            formData.append('noteAnnullamento', this.txtNoteAnnullamento.value.trim());

            const response = await fetch(this._dati.operazioniConsentite.annullaPosizioneDebitoria, {
                method: 'POST',
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams(formData)
            });
            if (response.status !== 200) {
                const errore = await response.text();
                console.error(errore);
                alert(errore);
                return;
            }

            this._annullaAnnullamentoPosizione(e);
            this._aggiornaListener(e);
        } finally {
            i.classList.add('hidden');
        }
    }

    _aggiornaListener(e) {

        const i = this.cmdAggiorna.querySelector('i');
        i.classList.remove('hidden'); // spinner nascosto nel _dataLoadedListener

        this.dispatchEvent(new CustomEvent('reload-data', {
            detail: {
                url: this._dati.operazioniConsentite.verificaStato
            }
        }));

    }

    _pagaOfflineListener(e) {
        // ...
        document.location.replace(this._dati.operazioniConsentite.pagamentiOffline);
    }

    async _avvisoDiPagamentoListener(e) {

        const i = this.cmdAvvisoDiPagamento.querySelector('i');
        i.classList.remove('hidden');

        try {
            const ret = await scaricaDocumentoConRetry(this._dati.operazioniConsentite.generazioneAvviso, 3);

            if (!ret) {
                alert(`Richiesta di generazione avviso inoltrata correttamente ma l'operazione potrebbe richiedere alcuni minuti.\n` +
                    `Per scaricare il documento sarà necessario riprovare più tardi`);
            }
        } catch (e) {
            alert(e);
        } finally {
            i.classList.add('hidden');
        }
    }

    async _fatturaListener(e) {

        const i = this.cmdFattura.querySelector('i');
        i.classList.remove('hidden');

        try {
            const ret = await scaricaDocumentoConRetry(this._dati.operazioniConsentite.generazioneFattura, 3);

            if (!ret) {
                alert(`Richiesta di generazione fattura inoltrata correttamente ma l'operazione potrebbe richiedere alcuni minuti.\n` +
                    `Per scaricare il documento sarà necessario riprovare più tardi`);
            }
        } catch (e) {
            alert(e);
        } finally {
            i.classList.add('hidden');
        }
    }

    async _ricevutaListener(e) {

        const i = this.cmdRicevuta.querySelector('i');
        i.classList.remove('hidden');

        try {
            const ret = await scaricaDocumentoConRetry(this._dati.operazioniConsentite.generazioneRicevuta, 3);

            if (!ret) {
                alert(`Richiesta di generazione ricevuta inoltrata correttamente ma l'operazione potrebbe richiedere alcuni minuti.\n` +
                    `Per scaricare il documento sarà necessario riprovare più tardi`);
            }
        } catch (e) {
            alert(e);
        } finally {
            i.classList.add('hidden');
        }
    }

    async _attivaSessioneDiPagamentoListener(e) {

        const i = this.cmdAttivaSessione.querySelector('i');
        i.classList.remove('hidden');
        try {
            const response = await fetch(this._dati.operazioniConsentite.attivazioneSessionePagamento);
            if (response.status !== 200) {
                const errore = await response.text();
                console.error(errore);
                alert(errore);
                return;
            }
            const data = await response.json();
            console.log(data);
            const form = document.createElement('form');

            let url = data.payUrl.split('?');
            let qs = '';

            data.formParams = data.formParams || [];

            if (url.length == 2) {
                data.payUrl = url[0];
                qs = url[1].split('&');
                for (let param of qs) {
                    let nameValue = param.split('=');
                    data.formParams.push({ paramName: nameValue[0], value: nameValue[1] });
                }
            }

            form.action = data.payUrl;
            form.method = data.httpMethod;
            form.target = '_blank';

            for (let param of data.formParams) {
                const input = document.createElement('input');
                input.type = 'hidden';
                input.id = input.name = param.paramName;
                input.value = param.value;
                form.appendChild(input);
            }

            document.body.appendChild(form);
            form.submit();
            form.remove();

        } finally {
            i.classList.add('hidden');
        }

    }

    _dataLoadedListener(event) {

        this._dati = event.detail;

        // Visualizzazione dei bottoni
        const operazioni = this._dati.operazioniConsentite;
        this.cmdAggiorna.style.display = operazioni?.verificaStato ? 'inline-block' : 'none';
        this.cmdPagaOffline.style.display = operazioni?.pagamentiOffline ? 'inline-block' : 'none';
        this.cmdAvvisoDiPagamento.style.display = operazioni?.generazioneAvviso ? 'inline-block' : 'none';
        this.cmdFattura.style.display = operazioni?.generazioneFattura ? 'inline-block' : 'none';
        this.cmdRicevuta.style.display = operazioni?.generazioneRicevuta ? 'inline-block' : 'none';
        this.cmdAttivaSessione.style.display = operazioni?.attivazioneSessionePagamento ? 'inline-block' : 'none';
        this.cmdIniziaAnnullamentoPosizione.style.display = operazioni?.annullaPosizioneDebitoria ? 'inline-block' : 'none';

        // Titolo
        const stile = getStileIconaDaCodiceStato(this._dati.codiceStato);
        this.titolo.className = '';
        this.titolo.classList.add(stile.className);
        this.titolo.innerHTML = `Posizione debitoria ${this._dati.idPosizioneDebitoria}<br/><small>${this._dati.stato} al ${this._dati.dataUltimoStato}</small>`

        // Dati del form
        this.form.innerHTML = '';
		this.form.innerHTML += this.createHtmlFormElement('Nominativo', this._dati.nominativo);
		this.form.innerHTML += this.createHtmlFormElement('Importo €', this.padNumber(this._dati.importoIvato));
        this.form.innerHTML += this.createHtmlFormElement('IUV', this._dati.iuv);
        this.form.innerHTML += this.createHtmlFormElement('Codice avviso', this._dati.codiceAvviso);
        this.form.innerHTML += this.createHtmlFormElement('Descrizione', this._dati.descrizione);
        this.form.innerHTML += this.createHtmlFormElement('Stato nodo', this._dati.descCodiceStatoNodo);

        if (operazioni?.modificaDataScadenza) {
            this.form.innerHTML += `
            <bottone-aggiorna-posizione-debitoria
                dett-posizione-debitoria='${this._dati.id}'
                url='${operazioni.modificaDataScadenza}'
                value='${this._dati.dataScadenza}'
            >
            </bottone-aggiorna-posizione-debitoria>`
        } else {
            this.form.innerHTML += this.createHtmlFormElement('Data scadenza', this._dati.dataScadenza);
        }

        // righe di dettaglio delle rate
        this._dati.importi.rate.forEach(rata => {
            const div = document.createElement('div');
            div.innerHTML = VbgDettaglioPosizioneDebitoriaPopup._templateRata;

            const h2 = div.querySelector('h2');
            h2.innerText = `Rata ${rata.numero}`;//<!-- (Scad. ${rata.dataScadenza})-->`;

            const tbody = div.querySelector('tbody');

            rata.dettagli.forEach(dettaglioRata => {
                tbody.innerHTML += `
                <tr>
                    <td>${dettaglioRata.raggruppamento || ''}</td>
                    <td>${dettaglioRata.causale}</td>
                    <td style='text-align:right'>${this.padNumber(dettaglioRata.importo)}</td>
                </tr>`;
            });

            this.form.appendChild(div);
        });

        this.shadowRoot.querySelectorAll('i.hide-on-data-loaded').forEach(el => {
            el.classList.add('hidden');
        });
    }

    padNumber(importo) {
        const parts = importo.toString().split('.');

        if (parts.length == 1) {
            return importo.toString() + ',00';
        }

        return parts[0] + ',' + parts[1].padEnd(2, '0');
    }


    createHtmlFormElement(label, value) {
        if ((value || '') === '') {
            return '';
        }

        return `
            <div class= 'form-group'>
                <label>${label}</label>
                <span class='readonly-form-control'>${value}</span>
            </div>`;
    }

    open() {
        this.popup.open();
    }
}

VbgDettaglioPosizioneDebitoriaPopup._templateRata = `
<h2></h2>
<table id='tabella' class='vbg-table'>
<thead>
    <tr>
        <th>Raggruppamento</th>
        <th>Causale</th>
        <th  style='text-align:right'>Importo (€)</th>
    </tr>
</thead>
<tbody>
    
</tbody>
</table>`;


customElements.define('vbg-dettaglio-posizione-debitoria-popup', VbgDettaglioPosizioneDebitoriaPopup);