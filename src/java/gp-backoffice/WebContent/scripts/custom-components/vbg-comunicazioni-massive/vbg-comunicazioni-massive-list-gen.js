import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { vbgRisolviEtichette } from '../vbg-risolvi-etichette.js';

class VbgComunicazioniMassiveList extends HTMLElement {
	
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
		.avanzamento-stati {
		    margin: 0;
		    padding: 0;
		    list-style-type: none;
		}
		span {
			padding-left: 5px;
			text-transform: capitalize;
		}
		button {
		    width: 110px;
		    height: 25px;
		    border-radius: 0px;
		    border: 0;
		}
		.cmd-completo {
			text-align: right;
		    background-color: var(--color-success);
		    color: var(--inverse-text-color);
		    pointer-events: none;
		}
		.cmd-aggiorna {
		    background-color: var(--color-default);
		    cursor: pointer;
		}
		
		.cmd-aggiorna.attivo{
			background-color: var(--color-warning);
		}

		.cmd-aggiorna.attivo>i{
			animation: fa-spin 2s infinite linear;
		}
		
		table tbody tr td:first-of-type a {
			cursor: pointer;
		}
		
		.btnhddn{
			display: none !important;
		}
  
  .pagination {
    margin-top: 10px;
    display: flex;
    align-items: center;
    gap: 6px;
  }
  .pagination button {
    padding: 4px 8px;
    cursor: pointer;
    width: auto;
  }
  .pagination button[disabled] {
    opacity: 0.5;
    cursor: not-allowed;
    width: auto;
  }
  .pagination-info {
    margin-left: auto;
    font-size: 0.9em;
    color: #444;
  }
  .nascondiRigaFiltroCelle{
	display: none !important;
  }
  .nascondiDataFiltroCelle{
	display: none !important;
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
		    <legend><label>RICERCA TESTUALE</label></legend>
		    <div class="form-group">
					<div class="input-icons">
						<i class="fa fa-search icon"></i> <input id="input_ricerca"
							type="text" placeholder="Cerca" />
					</div>
					<div class="input-help">
						<fmt:message data-etichetta="label.messaggio_ricerca_tabella" />
					</div>
			</div>
		</fieldset>
		<fieldset class="collassabile">
		   <legend><label>RICERCA PER DATA</label></legend>	
			<div class="form-group">
					<span data-etichetta="label.data.inizio" style="padding-right:3px;"></span><input type="text" id="dataInizio_id" name="dallaData" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
	                <a id="caldatainizio" href="javascript:void(0)" title="Calendario" class="calendario" data-cal-refid="dataInizio_id"> <img src="/backend/images/cal.gif" alt="Calendario"></a>
			
					<span data-etichetta="label.data.fine" style="padding-right:3px;"></span><input type="text" id="dataFine_id" name="allaData" size="10" maxlength="10" onblur="isValidDate(this,true);">
	                <a id="caldatafine" href="javascript:void(0)" title="Calendario" class="calendario" data-cal-refid="dataFine_id"> <img src="/backend/images/cal.gif" alt="Calendario"></a>
			</div>
			<div class="form-group">				
				<div id="filtraperdataid" class='btn btn-primary'>FILTRA</div>
				<div id="pulisciperdataid" class='btn btn-secondary'>PULISCI</div>
			</div>
			<div class="form-group">
			<div class="input-help">
				Effettua la ricerca nella lista sottostante per intervallo di date
		    </div>
		    </div>
		</fieldset>
		<div style="height: 30px;">
		</div>	
			<div class="form-group">
				<table id="pTable"class="vbg-table">
					<thead>
						<tr class="header">
							<th><label data-etichetta="label.codice" /></th>
							<th><label data-etichetta="label.data" /></th>
							<th><label data-etichetta="label.descrizione" /></th>
	                        <th><label data-etichetta="label.ultimo_stato" /></th>
	                        <th><label data-etichetta="label.stato_avanzamento" /></th>
	                        <th><label data-etichetta="label.azioni" /></th>			
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<div id="pagination" class="pagination"></div>
			</div>
			<div class="form-group">
				<div class='btn btn-secondary' id='chiudi' data-etichetta="button.back"></div>
				<div class='btn btn-primary' id='nuovacm' data-etichetta="button.new_comunicazione"></div>
			</div>
		</div>
	</div>
	`;
	
	static get observedAttributes() {
        return [
			'data-alias',
			'data-software',
            'data-titolo',
            'data-auth',
            'data-return-to'
        ];
    }
    
    get alias() { return this._alias; }
    set alias(val) { this._alias = val; }
    
    get software() { return this._software; }
    set software(val) { this._software = val; }
    	
    get titolo() { return this._titolo; }
    set titolo(val) { this._titolo = val; this._impostaTitolo(); }
    
    get auth() { return this._auth; }
    set auth(val) { this._auth = val; }
    
	get returnTo() { return this._returnTo; }
    set returnTo(val) { this._returnTo = val; }
    
    constructor() {
	
		if(window.Prototype) {
			delete Array.prototype.toJSON;
		}
	
        super();

        this.currentPage = 1;

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
                case 'data-titolo':
                    this.titolo = newValue;
                    break;
                case 'data-etichetta-titolo':
                    this.etichettaTitolo = newValue;
                    break;
				case 'data-auth':
					this.auth = newValue;
					break;
                case 'data-return-to':
                    this.returnTo = newValue;
                    break;
            }

        }
    }
    
    connectedCallback() {
		vbgRisolviEtichette(this.shadowRoot, this.alias, this.software);
		
		this.shadowRoot.querySelector('#chiudi').addEventListener('click', (e) => {
			e.preventDefault();
			location.replace(this.returnTo);			
		});
	}
	
	disconnectedCallback() {
		
    }
    
	nuovacomunicazione(url) {
		if (url === '') {
			this.shadowRoot.querySelector('#nuovacm').classList.add('btnhddn');
		} else {
			this.shadowRoot.querySelector('#nuovacm').addEventListener('click', (e) => {
				e.preventDefault();
				location.replace(url);
			});
		}
	}
    
    databind( dati ){
	
		if( dati == null ){
			return;
		}
		
		let tBody = this.shadowRoot.querySelector('table.vbg-table tbody');
		
		dati.forEach(el => {
							
			let href = document.createElement('a');
				href.dataset.id = el.id;
				href.textContent = el.id;
				href.addEventListener('click', function(e){
					e.preventDefault();
					e.target.dispatchEvent(new CustomEvent("detailclick",{ 
						bubbles: true,
  						composed: true,
						detail: {id: el.id }}));
			})
				
							
			let tdCodice = document.createElement('td');
				tdCodice.appendChild(href);
							
			let tdData = document.createElement('td');
				tdData.textContent = el.data_string;
								
			let tdDescrizione = document.createElement('td');
				tdDescrizione.textContent = el.descrizione;
					
			let tdUltimoStato = document.createElement('td');
				tdUltimoStato.setAttribute('name','ultimo-stato');
				tdUltimoStato.appendChild(this._riepilogoStatiComunicazione(el.operazioni));
			
			let tdAvanzamento = document.createElement('td');
				tdAvanzamento.setAttribute('name','avanzamento');
				tdAvanzamento.appendChild(this._riepilogoStatoAvanzamentoComplessivo(el));
						
			let tdAzioni = document.createElement('td');
				tdAzioni.append(this._buttonAzione(el));
			
			let tr = document.createElement('tr');
				tr.dataset.id = el.id;
				tr.appendChild(tdCodice);
				tr.appendChild(tdData);
				tr.appendChild(tdDescrizione);
				tr.appendChild(tdUltimoStato);
				tr.appendChild(tdAvanzamento);
				tr.appendChild(tdAzioni);
			
			tBody.appendChild(tr);
		});
		
		let inputRicerca =  this.shadowRoot.querySelector("#input_ricerca");
		inputRicerca.addEventListener('keyup', () => {
			console.log('filtra testo celle');
			this.filtraTestoCelle();
		});
		
		const input = this.shadowRoot.getElementById("dataInizio_id");
		const trigger = this.shadowRoot.getElementById("caldatainizio");

		this.cal = new Calendar({
			inputField: input,
			dateFormat: "%d/%m/%Y",
			trigger: trigger,
			bottomBar: false,
			onSelect: () => {
				Calendar.intToDate(this.cal.selection.get());
				this.cal.hide();
			}
		});
		
		const input2 = this.shadowRoot.getElementById("dataFine_id");
		const trigger2 = this.shadowRoot.getElementById("caldatafine");

		this.cal2 = new Calendar({
			inputField: input2,
			dateFormat: "%d/%m/%Y",
			trigger: trigger2,
			bottomBar: false,
			onSelect: () => {
				Calendar.intToDate(this.cal2.selection.get());
				this.cal2.hide();
			}
		});
		
		let filtraperdataid =  this.shadowRoot.querySelector("#filtraperdataid");
		filtraperdataid.addEventListener('click', () => {
			console.log('filtra data celle');
			this.filtraDataCelle();
		});
		
		let pulisciperdataid =  this.shadowRoot.querySelector("#pulisciperdataid");
		pulisciperdataid.addEventListener('click', () => {
			console.log('pulisci data celle');
			input.value = '';
			input2.value = '';
			this.filtraDataCelle();
		});
		
		this.paginateTable();
	}
	
	_riepilogoStatiComunicazione(operazioni){
		let ulStati = document.createElement('ul');
			ulStati.classList.add('avanzamento-stati');
				
		operazioni.forEach( op => {
			let liStato = document.createElement('li');
				liStato.textContent = op.titolo + ': ' + op.totale_operazioni_eseguite;
			ulStati.appendChild(liStato);

		});
		
		return ulStati;
	}
	
	_riepilogoStatoAvanzamentoComplessivo(el){
		
		let progress = document.createElement('progress');
			progress.setAttribute('max',el.totale_operazioni_richieste);
			progress.value = el.totale_operazioni_completate;

		let spanAvanzamento = document.createElement('span');
			spanAvanzamento.appendChild(document.createTextNode(el.totale_operazioni_completate + ' su ' + el.totale_operazioni_richieste));
		
		let div = document.createElement('div');
			div.appendChild(progress);
			div.appendChild(spanAvanzamento);
			
		return div;
	}
	

	paginateTable() {

		const rowsPerPage = 20;


		const table = this.shadowRoot.querySelector("#pTable");
		const tbody = table.querySelector("tbody");
		const rows = Array.from(tbody.querySelectorAll("tr:not(.nascondiRigaFiltroCelle):not(.nascondiDataFiltroCelle)"));
		const totalRows = rows.length;
		
		const pagination = this.shadowRoot.querySelector("#pagination");
		pagination.innerHTML = "";
		
		if(rowsPerPage > totalRows){
			rows.forEach(row => row.style.display = "");
		    return;
		}
		
		const totalPages = Math.ceil(totalRows / rowsPerPage);


		rows.forEach(row => row.style.display = "none");


		const start = (this.currentPage - 1) * rowsPerPage;
		const end = Math.min(start + rowsPerPage, totalRows);
		rows.slice(start, end).forEach(row => row.style.display = "");


		const prevBtn = document.createElement("button");
		prevBtn.textContent = "Previous";
		prevBtn.disabled = this.currentPage === 1;
		prevBtn.onclick = () => {
			this.currentPage--;
			this.paginateTable();
		};
		pagination.appendChild(prevBtn);

		const maxButtonsToShow = 10;
		let startPage = Math.max(1, this.currentPage - Math.floor(maxButtonsToShow / 2));
		let endPage = startPage + maxButtonsToShow - 1;

		if (endPage > totalPages) {
			endPage = totalPages;
			startPage = Math.max(1, endPage - maxButtonsToShow + 1);
		}
		for (let i = startPage; i <= endPage; i++) {
			const btn = document.createElement("button");
			btn.textContent = i;
			btn.disabled = i === this.currentPage;
			btn.onclick = () => {
				this.currentPage = i;
				this.paginateTable();
			};
			pagination.appendChild(btn);
		}


		const nextBtn = document.createElement("button");
		nextBtn.textContent = "Next";
		nextBtn.disabled = this.currentPage === totalPages;
		nextBtn.onclick = () => {
			this.currentPage++;
			this.paginateTable();
		};
		pagination.appendChild(nextBtn);


		const info = document.createElement("div");
		info.className = "pagination-info";
		info.textContent = `Mostrati da ${start + 1} a ${end} di ${totalRows} risultati`;
		pagination.appendChild(info);

	}
	
	 filtraTestoCelle() {
				console.log('sto filtrando');
				let input, filter, table, tr, td, i, txtValue;
				input = this.shadowRoot.querySelector("#input_ricerca");
				filter = input.value.toUpperCase();
				table = this.shadowRoot.querySelector("#pTable");
				tr = table.querySelectorAll("tr");
				let trovato = false;
				for (i = 1; i < tr.length; i++) {
				  td = tr[i].querySelectorAll("td");
				  for (let cell of td) {
				    if (cell) {
				      txtValue = cell.textContent || cell.innerText;			       
				      if (txtValue.toUpperCase().indexOf(filter) > -1) {			        
				      	trovato = true;
				      }			        
				    }
				  }
				  if(trovato){
				  	tr[i].classList.remove('nascondiRigaFiltroCelle');
				  	trovato = false;
				  } else{
				  	tr[i].classList.add('nascondiRigaFiltroCelle');				  	
				  }
				}
				this.currentPage = 1;
				this.paginateTable();
		}
		
	    filtraDataCelle() {
				console.log('sto filtrando filtraDataoCelle');
				
				let inputText1 = this.shadowRoot.querySelector("#dataInizio_id").value;
				let inputText2 = this.shadowRoot.querySelector("#dataFine_id").value;
				
				if(!inputText1 || inputText1.trim() === ''){
					inputText1 = '';
				}
				if(!inputText2 || inputText2.trim() === ''){
					inputText2 = '';
				}
				
				if(!inputText1 && !inputText2){
					console.log('l intervallo di date è vuoto');
					let tr = this.shadowRoot.querySelector("#pTable").querySelectorAll("tr");
					tr.forEach(trsingle => trsingle.classList.remove('nascondiDataFiltroCelle'));
					this.currentPage = 1;
					this.paginateTable();
					return;
				}
				
				let input1,input2, table, tr, td, i;
				
				if(inputText1){
					
					try{
						const parts = inputText1.split("/");
						if(parts.length !== 3){
							input1 = null;
						}
						input1 = new Date(parts[2], parts[1] - 1, parts[0]);
					}catch(e){
						log.error(e);
						input1 = null;
					}
					
				}else{
					input1 = null;
				}
				
				if(inputText2){
					
					try{
						const parts = inputText2.split("/");
						if(parts.length !== 3){
							input2 = null;
						}
						input2 = new Date(parts[2], parts[1] - 1, parts[0]);
					}catch(e){
						log.error(e);
						input2 = null;
					}
					
				}else{
					input2 = null;
				}
				
				
				table = this.shadowRoot.querySelector("#pTable");
				tr = table.querySelectorAll("tr");
				for (i = 1; i < tr.length; i++) {
				  td = tr[i].querySelectorAll("td");
				  if(td.length > 1){
					let txtValue = td[1].textContent || td[1].innerText;
					if(!txtValue || txtValue.trim()===''){
						tr[i].classList.add('nascondiDataFiltroCelle');
						continue;
					}else{
						
					    try{
						   const parts = txtValue.split("/");
						   if(parts.length !== 3){
							 tr[i].classList.add('nascondiDataFiltroCelle');
						     continue;
						   }else{
							
							let tdData = new Date(parts[2], parts[1] - 1, parts[0]);
							if( input1 && tdData < input1 ){
								tr[i].classList.add('nascondiDataFiltroCelle');
						        continue;
							}
							if( input2 && tdData > input2 ){
								tr[i].classList.add('nascondiDataFiltroCelle');
						        continue;
							}
							
							tr[i].classList.remove('nascondiDataFiltroCelle');
						   }
						   
						   
					    }catch(e){
						   console.error(e);
						   tr[i].classList.add('nascondiDataFiltroCelle');
					    }
						
						
					}
					
					
					
				  }
				  
				}
				this.currentPage = 1;
				this.paginateTable();
		}	
	
	_buttonAzione(comunicazione){
			
		let iAzione = document.createElement('i');
			iAzione.classList.add('fa');
		
		let label = document.createElement('span');
			
		let button = document.createElement('button');
			button.appendChild(iAzione);
			button.appendChild(label);
		
		if( comunicazione.completa ){
			iAzione.classList.add('fa-check');
			button.classList.add('cmd-completo');
			label.setAttribute('data-etichetta','label.completato');
		} else {
			iAzione.classList.add('fa-refresh');
			button.classList.add('cmd-aggiorna');
			label.setAttribute('data-etichetta','label.aggiorna');
			button.addEventListener('click', async (e) => {
				e.preventDefault();
				
				try {
					vbg.mostraModalCaricamento();
					
					button.classList.add('attivo');
					
					const headers = { 'Authorization': this.auth };
					
					const url = '../services/rest-private/comunicazioni/' + comunicazione.id + '/elabora';
					
					let response = await fetch(url, {
						method: 'POST',
						headers
					});
					
					if (response.status !== 200) {
						const errore = await response.text();
	        			console.error(errore);
	        			throw errore;
	       			}
	       			
	       			let responseJson = await response.json();
	       			
	       			
	       			let tdUltimoStato = this.shadowRoot.querySelector('tr[data-id="' + comunicazione.id + '"] td[name="ultimo-stato"]');
	       				tdUltimoStato.innerHTML = '';
					tdUltimoStato.appendChild(this._riepilogoStatiComunicazione(Array.isArray(responseJson.comunicazione.operazioni)
						? responseJson.comunicazione.operazioni
						: [responseJson.comunicazione.operazioni]));
	       				
					let tdAvanzamento = this.shadowRoot.querySelector('tr[data-id="' + comunicazione.id + '"] td[name="avanzamento"]');
	       				tdAvanzamento.innerHTML = '';
	       				tdAvanzamento.appendChild(this._riepilogoStatoAvanzamentoComplessivo(responseJson.comunicazione));

				}
				finally {
					button.classList.remove('attivo');
					vbg.nascondiModalCaricamento(); 
				}
			});
		}
		
		return button;
	}
    
    _impostaTitolo( ){
		this.shadowRoot.querySelector('div.parametriDiv div.parametro div').textContent = this.titolo;
	}
	
	
}

customElements.define('vbg-comunicazioni-massive-list-gen', VbgComunicazioniMassiveList);