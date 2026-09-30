import { copiaStiliDaDocument } from '../copia-stili-da-document.js';


class VbgOverrideParametro extends HTMLElement {
	
	templateHtml = `
		<vbg-modal id='modal-override-parametro'>
			<div id='info' slot='body' class='vbg-form'>
				<table class="vbg-table">
					<thead>
						<tr>
							<th>Albero degli interventi</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<div>Commercio</div>
								<div>
									<a href="">Rete Territoriale Enti - Nodo Locale Applicativo - Istanze da definire</a>
								</div>
								<div>WS_ATTI.CODICE_DIRIGENTE: <b>3_CON_AUT_ST</b></div>
							</td>
						</tr>
						<tr>
							<td>
								<div>Commercio</div>
								<div>
									<a href="">Rete Territoriale Enti - Nodo Locale Applicativo</a>
								</div>
								<div>WS_ATTI.CODICE_DIRIGENTE: <b>2DIR_PATRIM</b></div>
							</td>
						</tr>
					</tbody>
				</table>
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
		this.modal = this.shadowRoot.querySelector('#modal-override-parametro');
		this.divInfo = this.shadowRoot.querySelector('#info');
	}
	
	show( url, auth, returnTo  ){
		this._url= url;
		this._auth = auth;
		this._returnTo = returnTo;
		this.divInfo.innerHTML = '';
		this._recuperaOverrideParametro();
		this.modal.open();
	}
	
	hide(){
		this.modal.close();
	}
	
	async _recuperaOverrideParametro(){

		const response = await fetch(this._url, {
        	method: "GET",
            cache: "no-cache",
            headers: new Headers({
            	'Authorization' : this._auth
            }),
		});
		
		if (response.status !== 200) {
			const errore = await response.text();
			console.log(errore);
			return;
		}
		const descrizione = await response.json();
		console.log(descrizione);
		this._aggiornaInfo(descrizione);
	}
	
	_aggiornaInfo(overrideJson){
		let table;
		let body;
		let intestazione = '';
		
		overrideJson.forEach((item) => {
			
			if(item.contesto != intestazione){
				let th = document.createElement('th');
					th.innerHTML = item.contesto;
				
				let tr = document.createElement('tr');
					tr.appendChild(th);
				
				let head = document.createElement('thead');
					head.appendChild(tr);
				
				body = document.createElement('tbody');
				
				table = document.createElement('table');
				table.classList.add('vbg-table');
				table.appendChild(head);
				table.appendChild(body);
				
				this.divInfo.appendChild( table );
			}
			
			intestazione = item.contesto;
			
			let divSoftware = document.createElement('div');
				divSoftware.innerHTML = item.software;
			
			let divDescrizione = document.createElement('div');
			
			if( item.link != '' ){
				
				let returnTo = escape(this._returnTo);
				let goTo = escape(escape(item.link));
				
				let a = document.createElement('a');
					
					a.href = "javascript:doHref('../history/set.htm?ReturnTo=" + returnTo + "&GoTo=" + goTo + "','')";
					a.innerText = item.descrizione;
				
				divDescrizione.appendChild(a);
			} else {
				divDescrizione.innerText = item.descrizione;
			}
			
			let divChiaveValore = document.createElement('div');
				divChiaveValore.innerHTML = item.chiave + ': <b>' + item.valore + '</b>';
				
			let td = document.createElement('td');
				td.appendChild(divSoftware);
				td.appendChild(divDescrizione);
				td.appendChild(divChiaveValore);
			let tr = document.createElement('tr');
				tr.appendChild(td);
			
			body.appendChild(tr);
		});
	}
}

customElements.define('vbg-override-parametro', VbgOverrideParametro);