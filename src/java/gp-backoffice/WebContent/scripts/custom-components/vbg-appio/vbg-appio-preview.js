import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import '../marked.min.js';


class VbgAppIoPreview extends HTMLElement {
	templateHtml = `
	<style>
		fieldset {
			border: 1px solid var(--form-element-border-color);
    		padding: var(--default-padding);
    		margin-bottom: var(--default-padding);
		}
		legend {
			border: 1px solid var(--form-element-border-color);
    		padding: 4px var(--default-padding);
    		text-transform: uppercase;
    		font-size: 1.2em;
		}
	
		.modifiche-non-salvate {
			font-weight: bold;
    		color: #b61218;
    		font-style: italic;
		}
		
		.modifiche-non-salvate:before{
			content: " NON SALVATO";
		}
		
		.style_msg{
			width:65%;
			height:10em;
			border:1px solid var(--form-element-border-color);
			display: inline-block;
			overflow-y: auto;
			padding: 5px;
			
		}
		.style_msg p{
			overflow-wrap: break-word;
			white-space:normal;
			
		}
		

    </style>
	<div id="preview" class="vbg-form">
		<h3></h3>
			<div class="form">
				<fieldset>
					<legend>Servizi<span id="non_salvato1"></span></legend>
					<div class="config_servizi" >
						<div class="form-group">
							<label>Ambito servizio</label>
							<input id="input_ambito" type="text" placeholder="ambito" readonly/>
						</div>
						<div class="form-group">
							<label>Massimo numero messaggi</label>
							<input id="input_num_max_msg" type="number" placeholder="numero massimo di messaggi al giorno" readonly/>
						</div>
						<div class="form-group">
							<label for="input_oggetto_msg">Oggetto messaggio</label>
							<div id="input_oggetto_msg" class="style_msg"></div>
						</div>
						<div class="form-group">
							<label for="input_msg">Corpo messaggio</label>
							<div id="input_msg" class="style_msg"></div>
						</div>
						<div class="form-group">
							<label>Attivo</label>
							<input id="input_attivo" type="checkbox" redaonly/>
						</div>						
					</div>				
				</fieldset>
				<fieldset>
					<legend>Parametri<span id="non_salvato"></span></legend>
					<div id="config_param"></div>			
				</fieldset>
			</div>		
	</div>
	`;

	static get observedAttributes() {
		return [
			'dataset'
		];
	}

	// Dataset JSON
	get dataset() { return this._dataset; }
	set dataset(val) { this._dataset = val; }

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

		this.preview = this.shadowRoot.querySelector('#preview');

		this.descComune = this.shadowRoot.querySelector('#preview h3');
		this.descComune.textContent = this.dataset.comune;

		this.configServizi = this.shadowRoot.querySelector('.config_servizi');
		this.configServizi.dataset.codice = this.dataset.codiceComune;

		this.inputAmbito = this.shadowRoot.querySelector('#input_ambito');
		if (this.dataset.ambito != undefined) {
			this.inputAmbito.value = this.dataset.ambito;
		}else{
			this.inputAmbito.value = document.querySelector('#ambito').value;
		}

		this.inputMaxMess = this.shadowRoot.querySelector('#input_num_max_msg');
		if(this.dataset.maxNumMessaggio != undefined){
			this.inputMaxMess.value = this.dataset.maxNumMessaggio;
		}else{
			this.inputMaxMess.value = document.querySelector('#num_max_msg').value;
		}
		

		this.inputOggMess = this.shadowRoot.querySelector('#input_oggetto_msg');
		if (this.dataset.oggettoMesaggio != undefined) {
			this.inputOggMess.innerHTML = marked.parse(this.dataset.oggettoMesaggio);
		}else{
			this.inputOggMess.innerHTML = marked.parse(document.querySelector('#oggetto_msg').innerHTML);
		}

		this.divMessaggio = this.shadowRoot.querySelector('#input_msg');
		if (this.dataset.messaggio != undefined) {
			this.divMessaggio.innerHTML = marked.parse(this.dataset.messaggio);
		}else{
			this.divMessaggio.innerHTML = marked.parse(document.querySelector('#msg').innerHTML);
		}

		this.chkAttivo = this.shadowRoot.querySelector('#input_attivo');
		this.chkAttivo.checked = this.dataset.attivo;
		
		

		if (this.dataset.parametri != undefined) {
			this.dataset.parametri.forEach(parametro => {
				this.aggiungiParam(parametro.parametro, parametro.descrizione);
			});
		}
		
		this._setEventoPerCampi();

	}

	disconnectedCallback() {

	}


	_setEventoPerCampi() {
		let n = this.shadowRoot.querySelector('#non_salvato1');
		let ambito = document.getElementById('ambito');
		ambito.addEventListener('click', (e) => {
			let val = e.target.value;
			let in_ambito = this.shadowRoot.querySelector('#input_ambito');
			in_ambito.value = val;
			n.classList.add('modifiche-non-salvate');
		});

		let max_msg = document.getElementById('num_max_msg');
		max_msg.addEventListener('input', (e) => {
			let val = e.target.value;
			let in_max_msg = this.shadowRoot.querySelector('#input_num_max_msg');
			in_max_msg.value = val;
			n.classList.add('modifiche-non-salvate');
		});

		let ogg_msg = document.getElementById('oggetto_msg');
		ogg_msg.addEventListener('keyup', (e) => {
			let val = e.target.value;
			let in_ogg_msg = this.shadowRoot.querySelector('#input_oggetto_msg');
			in_ogg_msg.innerHTML = marked.parse(val);
			n.classList.add('modifiche-non-salvate');
		});

		let msg = document.getElementById('msg');
		msg.addEventListener('keyup', (e) => {
			document.querySelector('#msg').style.border ='1px solid var(--form-element-border-color)';
			let val = e.target.value;
			let in_msg = this.shadowRoot.querySelector('#input_msg');
			in_msg.innerHTML = marked.parse(val);
			n.classList.add('modifiche-non-salvate');
		});

		let att = document.getElementById('attivo');
		att.addEventListener('change', (e) => {
			var val = e.target.checked;
			var in_attivo = this.shadowRoot.querySelector('#input_attivo');
			in_attivo.checked = val;
			n.classList.add('modifiche-non-salvate');
		});
	}

	aggiungiParam(param, val) {
		let con = document.createElement('div');
		con.classList.add('params');
		let container = document.createElement('div');
		container.classList.add('form-group');
		let lab = document.createElement('label');
		lab.innerHTML = "Parametro";
		container.appendChild(lab);
		let inp1 = document.createElement('input');
		inp1.setAttribute("type", "text");
		inp1.setAttribute("value", param);
		inp1.setAttribute("id", "parametro");
		container.appendChild(inp1);
		let container1 = document.createElement('div');
		container1.classList.add('form-group');
		let lab1 = document.createElement('label');
		lab1.innerHTML = "Valore";
		container1.appendChild(lab1);
		let inp2 = document.createElement('input');
		inp2.setAttribute("type", "text");
		inp2.setAttribute("value", val);
		inp2.setAttribute("id", "valore");
		container1.appendChild(inp2);
		let container2 = document.createElement('div');
		container2.classList.add('form-group');
		let elm = document.createElement('a');
		elm.classList.add('btn');
		elm.classList.add('btn-primary');
		elm.innerHTML = "Elimina";

		elm.addEventListener('click', async () => {
			let idServizio = document.getElementById('id_servizio').value;
			if (idServizio == "") {
				idServizio = document.getElementById('input_identificativo_servizio').value;
				document.getElementById('id_servizio').value = idServizio;
			}
			let postParams = { request: { codiceComune: this.dataset.codiceComune, parametro: param, identificativoServizio: idServizio } };
			const response = await fetch('../appioserviziconfig/jsonEliminaServizioParams.htm', {
				method: 'POST',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				body: JSON.stringify(postParams)
			});

			if ( response.status == 200) {
				con.remove();
				if (this.params.innerHTML.trim() == "") {
					this.salva.classList.remove('modifiche-non-salvate');
				}
				window.vbg.nascondiModalCaricamento();

			} else {
				_gestioneErrori(await response.json());
			}


		});
		container2.appendChild(elm);
		this.params = this.shadowRoot.querySelector('#config_param');
		con.appendChild(container);
		con.appendChild(container1);
		con.appendChild(container2);
		this.params.appendChild(con);
		this.salva = this.shadowRoot.querySelector('#non_salvato');
		this.salva.classList.add('modifiche-non-salvate');

	}

}

customElements.define('vbg-appio-preview', VbgAppIoPreview);