import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { vbgRisolviEtichette } from '../vbg-risolvi-etichette.js';

import '../vbg-multi-upload.js';
import './vbg-comunicazioni-stato-comunicazione-gen.js';
import './vbg-comunicazioni-dettaglio-riga-gen.js';


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
		.fa {
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
					<label data-etichetta="label.massive.tipocomunicazione"></label>
					<span id="tipocomunicazioneid" class='readonly-form-control'></span>
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
			<fieldset class="collassabile" id="templatefieldid">
				<legend><label data-etichetta="label.massive.messaggiodausare" /></legend>
				<div class="form-group">
					<label data-etichetta="label.massive.oggetto" /></label>
					<span id="templateOMailTipo" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.massive.corpo" /></label>
					<span id="templateBMailTipo" class='readonly-form-control' style="word-wrap: break-word; white-space: pre-line;max-width:600px;"></span>
				</div>
			</fieldset>
			<fieldset class="collassabile" id="mailcomunicazionefieldid">
				<legend><label data-etichetta="label.email" /></legend>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.escludinomail"></label>
					<span id="escludinomail" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.scelta_mail_anagrafe" /></label>
					<span id="scelta_mail_anagrafe" class='readonly-form-control'></span>
				</div>
				<div class="form-group">
					<label data-etichetta="label.comunicazione.accountMail" /></label>
					<span id="accountMail" class='readonly-form-control'></span>
				</div>
				
			</fieldset>
			<fieldset class="collassabile" id="appiocomunicazionefieldid">
				<legend><label data-etichetta="label.massive.appiocomunicazione" /></legend>
				<div class="form-group">
					<label data-etichetta="label.massive.appioservizio"></label>
					<span id="appioservizioid" class='readonly-form-control'></span>
				</div>
				
			</fieldset>
			<div class="form-group">
				<div class='btn btn-primary' id='elabora'><span data-etichetta="button.elabora"></span></div>
				<div class='btn btn-primary' id='elaborazioneincorso' style="display:none;opacity:0.5;pointer-events: none;"><span>ELABORAZIONE IN CORSO</span></div>
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
	<div id="popup-modifica-mail" class='vbg-modal' data-auto-open='false'>
	<div class='vbg-modal-body'>
		<h1>
			<span id="titolare-mail-pec"></span>
		</h1>
		<div class="vbg-form">
			<div id="div-modifica-pec" class="form-group">
				<label data-etichetta="label.comunicazione.bollettazione.riga.pec" ></label>
				<input type="text" value="" id="modifica-pec" width="250px">
			</div>
		</div>
		<div class="vbg-form">
			<div id="div-modifica-mail" class="form-group">
				<label data-etichetta="label.comunicazione.bollettazione.riga.mail"></label>
				<input type="text" value="" id="modifica-mail" width="250px">
			</div>
		</div>	
		<div class="vbg-modal-footer">
			<div class='btn btn-primary' id='cmdSalvaMail' data-id="" data-codiceanagrafe="">
				<label data-etichetta="button.save"></label>
			</div>
			<a href="#" data-role='toggle-popup' class="btn btn-secondary">
				<label data-etichetta="button.back"></label>
			</a>
		</div>
	</div>
</div>
	`;
	
	static get observedAttributes() {
        return [
			'data-alias',
			'data-software',
			'data-auth',
            'data-titolo',
            'data-return-to',
            'data-det'
            
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
		this._inizializzaModificaMailNellaLista();
		this.inizializzaModificaMailNelPopup();
		this.shadowRoot.querySelector('*[data-role=toggle-popup]').addEventListener('click', (e) => {
			this.shadowRoot.querySelector('.vbg-modal').hide();
            e.preventDefault();
           	return false;
        });	
		
		
	}
	
	disconnectedCallback() {
		
    }
    
    databind( dati ){
	
		if( dati == null ){
			return;
		}
		
		let contesto = dati.contesto + '';
		
		console.log(dati);
		
		this.shadowRoot.querySelector('#data').textContent = dati.data_string;
		this.shadowRoot.querySelector('#descrizione').textContent = dati.descrizione;
		this.shadowRoot.querySelector('#tipocomunicazioneid').textContent = dati.tipo_comunicazione;
		this.shadowRoot.querySelector('#escludinomail').textContent = dati.escludi_destinatari_senza_mail ? "SI" : "NO";
		this.shadowRoot.querySelector('#scelta_mail_anagrafe').textContent = dati.inva_a_pec_o_mail;
		this.shadowRoot.querySelector('#posizioni_non_pagate').textContent = dati.ricerca_solo_pos_pagate ? "SI" : "NO";
		this.shadowRoot.querySelector('#allegaavvpagamento').textContent = dati.allega_avviso_pagamento ? "SI" : "NO";
		this.shadowRoot.querySelector('#convertiPDF').textContent = dati.converti_in_pdf ? "SI" : "NO";
		this.shadowRoot.querySelector('#appioservizioid').textContent = dati.servizio_appio;
		
		this.shadowRoot.querySelector('#templateOMailTipo').textContent = dati.oggetto_comunicazione;
		this.shadowRoot.querySelector('#templateBMailTipo').textContent = dati.body_comunicazione;
		
		if(!dati.inva_a_pec_o_mail){
			this.shadowRoot.querySelector('#mailcomunicazionefieldid').style.display = 'none';
		}
		
		if(!dati.servizio_appio){
			this.shadowRoot.querySelector('#appiocomunicazionefieldid').style.display = 'none';
		}
		
		if(!dati.inva_a_pec_o_mail && !dati.servizio_appio){
			this.shadowRoot.querySelector('#templatefieldid').style.display = 'none';
		}
		
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
				let modificaMail = `<div name="cmdModificaMail" data-id=${destinatario.id} 
									data-codiceanagrafe=${destinatario.codice_anagrafe}
									data-email="${destinatario.email || ''}"
									data-pec="${destinatario.pec || ''}" 
									data-titolare="${destinatario.nominativo}"
									data-sceltamail=${dati.inva_a_pec_o_mail}
									data-modificamail=${destinatario.modificamail}>
								<i id="iconaModificaMail${destinatario.id}" class="fa fa-envelope" style="font-size: 1.5em; margin-left: var(--half-padding);"></i>
							</div>`;
					let spanModificaMail = document.createElement('template');
						spanModificaMail.innerHTML= modificaMail
					divDestinatario.appendChild(spanModificaMail.content.firstChild);
					
				//2. Stato
				let stato = document.createElement('vbg-comunicazioni-stato-comunicazione-gen');
					stato.idComunicazione = dati.id;
					stato.id = destinatario.id;
					stato.stato = destinatario.stato;
					stato.conclusa = destinatario.conclusa;
					stato.auth = this.auth;
					stato.tipowarning = destinatario.tipowarning;
					
				let tdStato = document.createElement('td');
					tdStato.appendChild(stato);
				//3. Errore
				let tdErrore = document.createElement('td');
//				    if(destinatario.tipowarning){
//					  tdErrore.textContent = "";
//				    }else{
//					  tdErrore.textContent = destinatario.errore ? destinatario.errore : "";
//				    }
                    tdErrore.textContent = ""; //lo lasceremo vuoto, in Dettaglio è più facile da vedere				
				//4. Azioni
				let azDettaglio = document.createElement('i');
					azDettaglio.classList.add('fa');
					azDettaglio.classList.add('fa-search-plus');
					azDettaglio.classList.add('icona-azione');
					azDettaglio.addEventListener('click', async function(e) {
						e.preventDefault();
						try{
							vbg.mostraModalCaricamento();
							
							let url = '../'+contesto+'/ajaxGetRigaDettagliata.htm?idRiga=' + destinatario.id;
							const response = await fetch(url, {
			 					method: 'GET'
							});
		
							if (response.status !== 200) {
        					    const errore = await response.text();
        					    console.error(errore);
        					    alert('Si è verificato un errore durante la lettura del dettaglio: ' + errore);
        					    return;
       						 }
       						 let info = document.createElement('vbg-comunicazioni-dettaglio-riga-gen');
       						 	 info.setAttribute('alias',dati.alias);
       						 	 info.setAttribute('software',dati.software);
       						 	 
       						 let rispostaJson = await response.json();	 
       						 	 console.log(rispostaJson);
       						 	 info.dataBind(rispostaJson);

       						 let popup = e.target.shadowRoot.querySelector('#popup-dettaglio-riga');
       						 	 popup.style.display = "flex";
       						 	 popup.appendChild(info);
							
							
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
		/*this.shadowRoot.querySelector('#elabora').addEventListener('click', async (e) => {
			e.preventDefault();
			
			try {
				
					
					const headers = { 'Authorization': this.auth };
					
					const url = '../services/rest-private/comunicazioni/' + dati.id + '/elabora';
					
					let response = await fetch(url, {
						method: 'POST',
						headers
					});
					
					if (response.status !== 200) {
						const errore = await response.text();
	        			console.error(errore);
	        			alert('Si è verificato un errore durante l\'elaborazine massiva: ' + errore);
	        			location.reload();
        			    return;
	       			}
	       		
	       			location.reload();

				}
				finally {
					
				}
				
		});*/
		 this.shadowRoot.querySelector('#elabora').addEventListener('click', async (e) => {
		   
		    let stati=null;
		    
		    try {
		    	vbg.mostraModalCaricamento();
		        stati = this.shadowRoot.querySelectorAll('vbg-comunicazioni-stato-comunicazione-gen');
		        this.shadowRoot.querySelector('#elabora').style.display = 'none';
		        this.shadowRoot.querySelector('#elaborazioneincorso').style.display = '';
		        for (const stato of stati) {
			       let divElaboraRiga = stato.shadowRoot.querySelector('.elabora-riga');
			       divElaboraRiga.classList.add('massivaincorso');
			    }
			    
			    vbg.nascondiModalCaricamento();
		
				for (const stato of stati) {
					let divElaboraRiga = stato.shadowRoot.querySelector('.elabora-riga');
					
					divElaboraRiga.classList.remove('massivaincorso');
					const style = window.getComputedStyle(divElaboraRiga);
					if (style.pointerEvents == "none") {
						continue;
					}
					divElaboraRiga.classList.add('massivaincorso');
					
					divElaboraRiga.click();
					await this._waitUntil(divElaboraRiga);
				}
				//alert('Elaborazione conclusa');
		    }
		    finally {
				if (stati) {
					for (const stato of stati) {
						let divElaboraRiga = stato.shadowRoot.querySelector('.elabora-riga');
						divElaboraRiga.classList.remove('massivaincorso');
					}
				}
				
				this.shadowRoot.querySelector('#elabora').style.display = '';
		        this.shadowRoot.querySelector('#elaborazioneincorso').style.display = 'none';
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
        			alert('Si è verificato un errore durante la cancellazione: ' + errore);
        		    return;
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
   
      inizializzaModificaMailNelPopup(){
		let cmdSalvaMail = this.shadowRoot.querySelector('#cmdSalvaMail');
		const self = this;
		cmdSalvaMail.addEventListener('click', async function(e) {
			
			e.preventDefault();
			try{
				vbg.mostraModalCaricamento();
				let idRigaDettaglio = parseInt(cmdSalvaMail.dataset.id);
				let codiceAnagrafe = parseInt(cmdSalvaMail.dataset.codiceanagrafe);
				console.log(cmdSalvaMail.dataset);
				await self.salvaMail(idRigaDettaglio,codiceAnagrafe,cmdSalvaMail.dataset.sceltamail);
			}
			finally{
				vbg.nascondiModalCaricamento();
			}
			
			self.shadowRoot.querySelector('#popup-modifica-mail').style.display = "none";
			window.location.reload();
		});
	}
	 _inizializzaModificaMailNellaLista(){
		
		//let cmdModificaMail = document.getElementsByName('cmdModificaMail');
		let cmdModificaMail = this.shadowRoot.querySelectorAll('[name="cmdModificaMail"]');	

		for(let i = 0;i < cmdModificaMail.length; i++){
			
			let cmd = cmdModificaMail[i];
			
			let idRigaDettaglio = parseInt(cmd.dataset.id);
			let codiceAnagrafe = cmd.dataset.codiceanagrafe;
			let codiceAmministrazione = cmd.dataset.codiceamministrazione;
			let codiceResponsabile = cmd.dataset.codiceresponsabile;
			let mail = cmd.dataset.email;
			let pec = cmd.dataset.pec;
			let titolare = cmd.dataset.titolare;
			let modificamail = cmd.dataset.modificamail;
			let sceltamail = cmd.dataset.sceltamail;

			
			if( modificamail === "false" ) {
				cmd.style.display = 'none';
				continue;
			}
			
			
			cmd.style.display = 'inline-block';
			 const self = this;
			cmd.addEventListener('click', function(e) {
				
				e.preventDefault();
				try{
					vbg.mostraModalCaricamento();
					self._visualizzaModificaMail(idRigaDettaglio, codiceAnagrafe, codiceAmministrazione, codiceResponsabile,titolare, mail, pec,sceltamail);
					
					
					
				}
				finally{
					vbg.nascondiModalCaricamento();
				}
			});
		}
	}
	
	 _visualizzaModificaMail(idRigaDettaglio, codiceAnagrafe, codiceAmministrazione, codiceResponsabile, titolare, mail, pec,sceltamail){
		
		
		let tipoIndirizzo = '${comunicazioneBollettazioneDetail.sceltaMailAnagrafeCodice}';
		
		this.shadowRoot.querySelector('#titolare-mail-pec').innerHTML = titolare;
		
		this.shadowRoot.querySelector('#cmdSalvaMail').setAttribute('data-id' , idRigaDettaglio);
		this.shadowRoot.querySelector('#cmdSalvaMail').setAttribute('data-codiceanagrafe' , codiceAnagrafe);
		this.shadowRoot.querySelector('#cmdSalvaMail').setAttribute('data-codiceamministrazione' , codiceAmministrazione);
		this.shadowRoot.querySelector('#cmdSalvaMail').setAttribute('data-codiceresponsabile' , codiceResponsabile);
		this.shadowRoot.querySelector('#cmdSalvaMail').setAttribute('data-sceltamail' , sceltamail);
		
		
		this.shadowRoot.querySelector('#div-modifica-mail').style.display = 'inline-block';
		this.shadowRoot.querySelector('#div-modifica-pec').style.display = 'inline-block';
		
		
		if(codiceResponsabile!=''){
			this.shadowRoot.querySelector('#div-modifica-pec').style.display = 'inline-block';;	
		}
		
		this.shadowRoot.querySelector('#modifica-mail').value = mail;
		this.shadowRoot.querySelector('#modifica-pec').value = pec;
		
		this.shadowRoot.querySelector('#popup-modifica-mail').style.display = "flex";
	}
	
	async  salvaMail(idMassiveDett ,codiceAnagrafe,sceltamail) {
		
		let email = this.shadowRoot.querySelector('#modifica-mail').value;
		let pec = this.shadowRoot.querySelector('#modifica-pec').value;
		
		
		 const formData = new FormData();
       

         formData.append('codiceAnagrafe', codiceAnagrafe);
         formData.append('email', email);
         formData.append('pec', pec);
         formData.append('sceltaMailAnagrafe',sceltamail);
         formData.append('idMassiveDettaglio',idMassiveDett);
		
		let url = '../mercati/ajaxAggiornaMailOPec.htm';
	
		const response = await fetch(url, {
			 method: 'POST',
			 cache: "no-cache",
		     body: formData         
		});
		
		if (response.status !== 200) {
            const errore = await response.text();
            console.error(errore);
            throw errore;
        }
		
		const responseJson = await response.json();
		
		if (responseJson.aggiorna_mail.codice != 'OK') {
			const errore = responseJson.aggiorna_mail.descrizione;
			console.error(errore);
            throw errore;
        }
		

	}
	
	_waitUntil(divElabora) {
		return new Promise(resolve => {
			const interval = setInterval(() => {
				if (!divElabora.classList.contains("attivo")) {
					clearInterval(interval);
					resolve();
				}
			}, 500);
		});
	}

}

customElements.define('vbg-comunicazioni-massive-detail-gen', VbgComunicazioniMassiveDetail);