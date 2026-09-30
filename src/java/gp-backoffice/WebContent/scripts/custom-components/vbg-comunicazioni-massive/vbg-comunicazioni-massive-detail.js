import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { vbgRisolviEtichette } from '../vbg-risolvi-etichette.js';

import '../vbg-multi-upload.js';
import './vbg-comunicazioni-stato-comunicazione.js';
import './vbg-comunicazioni-dettaglio-riga.js';


class VbgComunicazioniMassiveDetail extends HTMLElement {
	
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
		.icona-azione {
			font-size: 1.5em;
			margin-left: var(--half-padding);
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
			<fieldset class="collassabile">
				<legend><label data-etichetta="label.dati_principali" /></legend>
				<div class="form-group">
					<label data-etichetta="label.data"></label>
					<span id="data" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.descrizione"></label>
					<span id="descrizione" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.escludinomail"></label>
					<span id="escludinomail" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.scelta_mail_anagrafe" /></label>
					<span id="scelta_mail_anagrafe" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.posizioni_non_pagate" /></label>
					<span id="posizioni_non_pagate" class='readonly-form-control'></span>
				</div>
			</fieldset>
			<fieldset class="collassabile">
				<legend><label data-etichetta="label.allegati" /></legend>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.allegatifissi" /></label>
					<vbg-multi-upload id="allegatiFissi" path-name="allegatiFissi" context-path='..' readonly='true'></vbg-multi-upload>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.documentitipo" /></label>
					<vbg-multi-upload id="allegatiDinamici" path-name="allegatiDinamici" context-path='..' readonly='true'></vbg-multi-upload>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.allegaavvpagamento"></label>
					<span id="allegaavvpagamento" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.convertiPDF" /></label>
					<span id="convertiPDF" class='readonly-form-control'></span>
				</div>
			</fieldset>
			<fieldset class="collassabile">
				<legend><label data-etichetta="label.firma_digitale" /></legend>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.firmatari"></label>
					<span id="firmatari" class='readonly-form-control'></span>
				</div>
			</fieldset>
			<fieldset class="collassabile">
				<legend><label data-etichetta="label.protocollazione" /></legend>
					<div class="form-group">
						<label data-etichetta="label.comunicazione.mailSoggProtocollo"></label>
						<span id="mailSoggProtocollo" class='readonly-form-control'></span>
					</div>
			</fieldset>
			<fieldset class="collassabile">
				<legend><label data-etichetta="label.email" /></legend>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.accountMail" /></label>
					<span id="accountMail" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.templateMailTipo" /></label>
					<span id="templateMailTipo" class='readonly-form-control'></span>
				</div>
			</fieldset>
			<div class="form-group">
				<div class='btn btn-primary' id='elabora'><span data-etichetta="button.elabora"></span></div>
				<div class='btn btn-primary' id='elimina'><span data-etichetta="button.delete"></span></div>
				<div class='btn btn-secondary' id='chiudi'><span data-etichetta="button.back"></span></div>
			</div>
			<fieldset class="collassabile">
				<legend><label data-etichetta="label.comunicazione.elenco.righe" /></legend>
				<table id='destinatari' class="vbg-table">
					<thead>
						<th><label data-etichetta="label.comunicazione.riga.destinatario"></label></th>
						<th><label data-etichetta="label.comunicazione.riga.stato"></label></th>
						<th><label data-etichetta="label.comunicazione.riga.errore"></label></th>
						<th><label data-etichetta="label.azioni"></label></th>
					</thead>
					<tbody></tbody>
				</table>
			</fieldset>
			<div id="popup-dettaglio-riga"></div>
		</div>
	</div>
	`;
	
	static get observedAttributes() {
        return [
			'data-alias',
			'data-software',
			'data-auth',
            'data-titolo',
            'data-return-to'
        ];
    }
    
    get alias() { return this._alias; }
    set alias(val) { this._alias = val; }
    
    get software() { return this._software; }
    set software(val) { this._software = val; }
    
    get auth() { return this._auth; }
    set auth(val) { this._auth = val; }
    
    get titolo() { return this._titolo; }
    set titolo(val) { this._titolo = val; this._impostaTitolo(); }
    
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
				case 'data-auth':
					this.auth = newValue;
					break;
                case 'data-titolo':
                    this.titolo = newValue;
                    break;
                case 'data-return-to':
                    this.returnTo = newValue;
                    break;
			}
        }
	}
	
	connectedCallback() {
		
		this.shadowRoot.querySelector('#chiudi').addEventListener('click', (e) => {
			e.preventDefault();
			location.replace(this.returnTo);
		});
		
		vbgRisolviEtichette(this.shadowRoot, this.alias, this.software);
		
		this._inizializzaFieldset();
	}
	
	disconnectedCallback() {
		
    }
    
    databind( dati ){
	
		if( dati == null ){
			return;
		}
		
		console.log(dati);
		
		this.shadowRoot.querySelector('#data').textContent = dati.data_string;
		this.shadowRoot.querySelector('#descrizione').textContent = dati.descrizione;
		this.shadowRoot.querySelector('#escludinomail').textContent = dati.escludi_destinatari_senza_mail ? "SI" : "NO";
		this.shadowRoot.querySelector('#scelta_mail_anagrafe').textContent = dati.inva_a_pec_o_mail;
		this.shadowRoot.querySelector('#posizioni_non_pagate').textContent = dati.ricerca_solo_pos_pagate ? "SI" : "NO";
		this.shadowRoot.querySelector('#allegaavvpagamento').textContent = dati.allega_avviso_pagamento ? "SI" : "NO";
		this.shadowRoot.querySelector('#convertiPDF').textContent = dati.converti_in_pdf ? "SI" : "NO";
		//Allegati fissi
		let allegatiFissi = this.shadowRoot.querySelector('#allegatiFissi');
		dati.allegati_fissi.forEach(allegato => {
			let input = document.createElement('input');
				input.type = 'hidden';
				input.value = allegato.codice_oggetto;
				input.setAttribute('var','allegato');
			allegatiFissi.appendChild(input);
		});
		//Allegati dinamici ( letttere tipo )
		let allegatiDinamici = this.shadowRoot.querySelector('#allegatiDinamici');
		dati.allegati_dinamici.forEach(allegato => {
			let input = document.createElement('input');
				input.type = 'hidden';
				input.value = allegato.codice_oggetto;
				input.setAttribute('var','allegato');
			allegatiDinamici.appendChild(input);
		});
		//Firmatari
		if( dati.firmatari ){
			let ul = document.createElement('ul');
			let firmatari = this.shadowRoot.querySelector('#firmatari');
				firmatari.appendChild(ul);
			
			dati.firmatari.forEach(firmatario => {
				
				let li = document.createElement('li');
					li.textContent = firmatario.nominativo;
				
				ul.appendChild(li);
			});
		}
		//Protocollazione
		if( dati.protocollazione ){
			this.shadowRoot.querySelector('#mailSoggProtocollo').textContent = dati.protocollazione.oggetto;			
		}
		//Account mail
		if(dati.account_mail){
			this.shadowRoot.querySelector('#accountMail').textContent = dati.account_mail.account;
			this.shadowRoot.querySelector('#templateMailTipo').textContent = dati.account_mail.template;
		}
		//Destinatari
		if(dati.destinatari){
			let tbody = this.shadowRoot.querySelector('#destinatari tbody');
			dati.destinatari.forEach( destinatario => {
				//1. Nominativo
				let divDestinatario = document.createElement('div');
					divDestinatario.textContent = destinatario.nominativo;
				let tdDestinatario = document.createElement('td');
					tdDestinatario.appendChild(divDestinatario);
				//2. Stato
				let stato = document.createElement('vbg-comunicazioni-stato-comunicazione');
					stato.idComunicazione = dati.id;
					stato.id = destinatario.id;
					stato.stato = destinatario.stato;
					stato.conclusa = destinatario.conclusa;
					stato.auth = this.auth;
					
				let tdStato = document.createElement('td');
					tdStato.appendChild(stato);
				//3. Errore
				let tdErrore = document.createElement('td');
					tdErrore.textContent = destinatario.errore ? destinatario.errore : "";
				//4. Azioni
				let azDettaglio = document.createElement('i');
					azDettaglio.classList.add('fa');
					azDettaglio.classList.add('fa-search-plus');
					azDettaglio.classList.add('icona-azione');
					azDettaglio.addEventListener('click', async function(e) {
						e.preventDefault();
						try{
							vbg.mostraModalCaricamento();
							
							let url = '../comunicazionibollettazione/ajaxGetRigaDettagliata.htm?idRiga=' + destinatario.id;
							const response = await fetch(url, {
			 					method: 'GET'
							});
		
							if (response.status !== 200) {
        					    const errore = await response.text();
        					    console.error(errore);
        					    throw errore;
       						 }
       						 let info = document.createElement('vbg-comunicazioni-dettaglio-riga');
       						 	 info.setAttribute('alias',dati.alias);
       						 	 info.setAttribute('software',dati.software);
       						 	 info.dataBind(destinatario);

       						 let popup = e.target.shadowRoot.querySelector('#popup-dettaglio-riga');
       						 	 popup.style.display = "flex";
       						 	 popup.appendChild(info);
							
							console.log(await response.json());
						}
						finally{
							vbg.nascondiModalCaricamento();
						}
					});
					
				let tdAzioni = document.createElement('td');
					tdAzioni.appendChild(azDettaglio);
				//Riga
				let tr = document.createElement('tr');
					tr.appendChild(tdDestinatario);
					tr.appendChild(tdStato);
					tr.appendChild(tdErrore);
					tr.appendChild(tdAzioni);
				tbody.appendChild(tr);
			});
		}
		
		//Bottone elabora
		this.shadowRoot.querySelector('#elabora').addEventListener('click', async (e) => {
			e.preventDefault();
			
			try {
				vbg.mostraModalCaricamento();
					
					const headers = { 'Authorization': this.auth };
					
					const url = '../services/rest-private/comunicazioni/' + dati.id + '/elabora';
					
					let response = await fetch(url, {
						method: 'POST',
						headers
					});
					
					if (response.status !== 200) {
						const errore = await response.text();
	        			console.error(errore);
	        			throw errore;
	       			}
	       			vbg.nascondiModalCaricamento();
	       			location.reload();

				}
				finally {
					vbg.nascondiModalCaricamento(); 
				}
				
		});
		
		//Bottone elimina
		this.shadowRoot.querySelector('#elimina').addEventListener('click', async (e) => {
			e.preventDefault();
			
			try {
			
				if(!confirm('Cancellare la comunicazione?')){
					return;
				}
			
				vbg.mostraModalCaricamento();
				
				const headers = { 'Authorization': this.auth };
				
				const url = '../services/rest-private/comunicazioni/' + dati.id;
				
				let response = await fetch(url, {
					method: 'DELETE',
					headers
				});
				
				if (response.status !== 200) {
					const errore = await response.text();
        			console.error(errore);
        			throw errore;
       			}
       			vbg.nascondiModalCaricamento();
       			location.replace(this.returnTo);

			}
			finally {
				vbg.nascondiModalCaricamento(); 
			}
				
		});
	}
	
	
	
	_impostaTitolo( ){
		this.divTitolo = this.shadowRoot.querySelector('div.parametriDiv div.parametro div');
		this.divTitolo.textContent = this.titolo;
	}	
	
	_inizializzaFieldset(){
        
        const espandi = (elemento) => {
        	elemento.classList.remove('collassato');		
        }
        
        const collassa = (elemento) => {
        	elemento.classList.add('collassato');
        }
        
        this.shadowRoot.querySelectorAll('fieldset.collassabile').forEach( elemento => {
        	
        	const legend = elemento.querySelector('fieldset>legend');
        	
        	const frecciaGiu = document.createElement('i');
        	frecciaGiu.className = 'fa fa-chevron-down';
        	
        	const frecciaSu = document.createElement('i');
        	frecciaSu.className = 'fa fa-chevron-up';
        	
        	legend.prepend(frecciaGiu);
        	legend.prepend(frecciaSu);
        	
        	legend.addEventListener('click', () => {
        		
        		if( elemento.classList.contains('collassato') ) {
        			espandi(elemento);
        		} else {
        			collassa(elemento);
        		}

        	});
        	
        	if( elemento.classList.contains('collassato') ){
        		collassa(elemento);
        	}
        	
        });
    }

}

customElements.define('vbg-comunicazioni-massive-detail', VbgComunicazioniMassiveDetail);