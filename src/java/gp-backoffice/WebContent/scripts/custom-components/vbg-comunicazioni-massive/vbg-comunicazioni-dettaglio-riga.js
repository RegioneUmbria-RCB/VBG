import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { vbgRisolviEtichette } from '../vbg-risolvi-etichette.js';


class VbgComunicazioniDettaglioRiga extends HTMLElement {
	templateHtml = `
		<style>
			a {	
				text-decoration: none;
    			margin-left: 4px;
    			margin-right: 2px;
			}
			ul {
				line-height: 1.67em;
			}
			#riga-firmatari ul li i {
				font-size: 1.5em;
				margin-left: var(--half-padding);
			}
			#riga-firmatari ul li i.firma-completa {
				color:green;
			}
			#riga-firmatari ul li i.firma-incompleta {
				color:red;
			}

			#riga-mail-inviate ul li {
				display: flex;
			}
			
			#riga-mail-inviate ul li div:first-of-type i {
				padding-right: 2px;
				margin-top: 5px;
			}
			#riga-errore {
				max-width: 500px;
			}
			
		</style>
		<div class="vbg-modal" data-auto-open="false" style="display: flex;">
			<div class="vbg-modal-body">
				<h1 id='riga-titolo'></h1>
				<div class="vbg-form">
					<div class="form-group">
						<label data-etichetta="label.comunicazione.riga.destinatario"></label>
						<span id="riga-destinatario" class='readonly-form-control'></span>
					</div>
				</div>
				<div class="vbg-form">
					<div class="form-group">
						<label data-etichetta="label.comunicazione.riga.protocollo"></label>
						<span id="riga-protocollo" class='readonly-form-control'></span>
					</div>
				</div>
				<div class="vbg-form">
					<div class="form-group">
						<label data-etichetta="label.comunicazione.riga.allegati"></label>
						<span id="riga-allegati" class='readonly-form-control'></span>
					</div>
				</div>
				<div class="vbg-form">
					<div class="form-group">
						<label data-etichetta="label.comunicazione.firmatari"></label>
						<span id="riga-firmatari" class='readonly-form-control'></span>
					</div>
				</div>
				<div class="vbg-form">
					<div class="form-group">
						<label data-etichetta="label.comunicazione.mail_inviate"></label>
						<span id="riga-mail-inviate" class='readonly-form-control' style="white-space: nowrap;"></span>
					</div>
				</div>		
				<div class="vbg-form">
					<div class="form-group">
						<label data-etichetta="label.comunicazione.riga.errore"></label>
						<span id="riga-errore" class='readonly-form-control'></span>
					</div>
				</div>		
				<div class="vbg-modal-footer">
					<div class='btn btn-primary' id='riga-cmd-elabora-riga' data-id="">
						<label data-etichetta="button.elabora"></label>
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
			'alias',
			'software'
        ];
    }
    
    get alias() { return this._alias; }
    set alias(val) { this._alias = val; }
    
    get software() { return this._software; }
    set software(val) { this._software = val; }
    
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
    
    connectedCallback() {
		vbgRisolviEtichette(this.shadowRoot, this.alias, this.software);

		
		this.shadowRoot.querySelector('#riga-cmd-elabora-riga').addEventListener('click', function(e){
			console.log('elabora');
		});
		
		this.shadowRoot.querySelector('*[data-role=toggle-popup]').addEventListener('click', (e) => {
			this.shadowRoot.querySelector('.vbg-modal').hide();
            e.preventDefault();
           	return false;
        });
		
	}
	
	disconnectedCallback() {
		
    }
    
    dataBind(dati){
		console.log(dati);
		
		this.shadowRoot.querySelector('#riga-titolo').textContent = dati.stato;
		this.shadowRoot.querySelector('#riga-destinatario').textContent = dati.nominativo;
		this.shadowRoot.querySelector('#riga-protocollo').textContent = dati.riferimenti_protocollo;
		this.shadowRoot.querySelector('#riga-errore').textContent = dati.errore;
		//Allegati
		if( dati.allegati ){
			this.shadowRoot.querySelector('#riga-allegati').appendChild(this._templateAllegati(dati.allegati));
		}
		//Firmatari
		if( dati.firmatari ){
			this.shadowRoot.querySelector('#riga-firmatari').appendChild(this._templateFirmatari(dati.firmatari));
		}
		//Mail
		if( dati.mail){
			this.shadowRoot.querySelector('#riga-mail-inviate').appendChild(this._templateMail(dati.mail));
		}
	}
	
	_templateAllegati(allegati){
		let ul = document.createElement('ul');
			
		allegati.forEach(async allegato => {
			let li = document.createElement('li');
				li.innerHTML = await this.getSnippetOggetto(allegato.codice_oggetto);
				ul.appendChild(li);
		});
		
		return ul;
	}
	
	_templateFirmatari(firmatari){
		
		let ul = document.createElement('ul');
			
		firmatari.forEach(firmatario => {
			let iFirmato = this._generaIconaFirmatario(firmatario);
				
			let li = document.createElement('li');
				li.appendChild( document.createTextNode(firmatario.nominativo) );
				li.appendChild(iFirmato);
			ul.appendChild(li);
		});
		
		return ul;
	}
	_generaIconaFirmatario(firmatario) {
		let iFirmato = document.createElement('i');
			iFirmato.classList.add('fa');
		if( firmatario.firma_completata ){
			iFirmato.classList.add('fa-toggle-on');
			iFirmato.classList.add('firma-completa');
			iFirmato.title = 'Il documento è stato firmato da ' + firmatario.nominativo;
		} else {
			iFirmato.classList.add('fa-toggle-off');
			iFirmato.classList.add('firma-incompleta');
			iFirmato.title = 'Il documento non è stato firmato da ' + firmatario.nominativo;
		}
			
		return iFirmato;
	}
	
	
	
	_templateMail(mail_inviate){
		
		let ul = document.createElement('ul');
		
		mail_inviate.forEach(mail => {
			
			let iMailInviata = document.createElement('i');
				iMailInviata.classList.add('fas');
				iMailInviata.classList.add('fa-envelope');
			
			let divMail = document.createElement('div');
				divMail.appendChild(iMailInviata);
				
			let destinatario = document.createElement('strong');
				destinatario.appendChild(document.createTextNode(mail.destinatario));
			
			let oggetto = document.createElement('strong');
				oggetto.appendChild(document.createTextNode(mail.oggetto));

			let divTesto = document.createElement('div');
				divTesto.appendChild(document.createTextNode('destinatario: '));
				divTesto.appendChild(destinatario);
				divTesto.appendChild(document.createElement('br'));
				divTesto.appendChild(document.createTextNode('oggetto: '));
				divTesto.appendChild(oggetto);
				divTesto.appendChild(document.createElement('br'));
						
			let li = document.createElement('li');
				li.appendChild(divMail);
				li.appendChild(divTesto);
			
			if( mail_inviate.ricevute ){
				li.appendChild(this._templateMail(mail_inviate.ricevute));
			}
			
			ul.appendChild(li);
		});
		
		return ul;
	}
    
    async getSnippetOggetto(codiceOggetto) {
		
		let mostralabel = true;
		let mostraNomeFile = true;
		let readonly = true;
		let mostrastorico = false;
		let jsFx = 'viewOggetto_' + codiceOggetto + '_fx'; 
		let styleHref = '';
		let url = '../file/ajaxViewOggettoList.htm?fileId=' + codiceOggetto + '&mostralabel=' + mostralabel + '&mostraNomeFile=' + mostraNomeFile + '&mostrastorico=' + mostrastorico + '&readonly=' + readonly + '&jsFx=' + jsFx + '&styleHref=' + styleHref;
		
		const response = await fetch(url, {
			method: 'GET',
			context: document.body,
			dataType: "html",
		});
		
		if (response.status !== 200) {
           	const errore = await response.text();
           	console.error(errore.innerText);
           	throw errore.innerText;
       	}
		
		return await response.text();
	}
    
    attributeChangedCallback(attrName, oldValue, newValue) {

        if (newValue !== oldValue) {
	         switch (attrName) {
				case 'alias':
					this.alias = newValue;
					break;
				case 'software':
					this.software = newValue;
					break;
			}
        }
	}
}

customElements.define('vbg-comunicazioni-dettaglio-riga', VbgComunicazioniDettaglioRiga);