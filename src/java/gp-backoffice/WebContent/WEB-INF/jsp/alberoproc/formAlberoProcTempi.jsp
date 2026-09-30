<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<script type="text/javascript">
	vbg.ready(() => {
		
		let codIntervento = '${param.codiceIntervento}';
		
		let fieldsetTestata = document.querySelector('fieldset[name="alberoproctempi_testata"]');
		let fieldsetDettaglio = document.querySelector('fieldset[name="alberoproctempi_dettaglio"]');
		
		let btnSalva = fieldsetDettaglio.querySelector('.form-button>.btn-save');
		btnSalva.addEventListener('click',async (e) => {
			e.preventDefault();
			let tempisalvati = await salvaTempi(codIntervento);
			if(tempisalvati){
				let trTestata = fieldsetTestata.querySelector('div.vbg-form>table>tbody>tr');
				trTestata.dataset.idTempo = tempisalvati.idtestata;
				trTestata.dataset.idIntervento = tempisalvati.codiceintervento;
				
				alert("Salvataggio avvenuto correttamente");
				databind(codIntervento);
			}
		});
		
		let btnAggiungiDettaglio = fieldsetDettaglio.querySelector('.form-button>.btn-add');
		btnAggiungiDettaglio.addEventListener('click',async (e) => {
			
			e.preventDefault();
			let table = fieldsetDettaglio.querySelector('div.vbg-form>table>tbody');
			aggiungiDettaglio(table);
		});
		
		
		databind(codIntervento);
		
		return;
		/*
		
		*/
		
		async function databind(codiceIntervento){
			try
			{
				const postParams = { request: { codiceIntervento } };
				
				const response = await fetch('../alberoproc/jsonFindAlberoProcTempi.htm', {
	        		method: 'POST',
	                headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json',
	                },
	                body: JSON.stringify(postParams)
	        	});
				
	        	const jsResponse = await response.json();

				let tableTestata = fieldsetTestata.querySelector('div.testata>table>tbody');
				tableTestata.innerHTML = '';
				
	        	if( jsResponse.response.tempi ){
	    			for (var i=0; i<jsResponse.response.tempi.length; i++) {
	    				let tempo = jsResponse.response.tempi[i];
	    				
	    				aggiungiTestata(tableTestata, codiceIntervento, tempo);
	    				mostraNascondiAzioniTestata(tableTestata);
	    				popolaPanelDettaglio(tempo.id, tempo.dettaglio);
	    			}	
	        	} else {
	        		nuovaTestata(codiceIntervento);
	        	}
			}
			catch(error) {
				console.log(error);
	            alert(error);
	        }
		}
		
		function nuovaTestata(codiceIntervento){
			//1. Aggiungo una nuova testata
			let tbodyTestata = fieldsetTestata.querySelector('div.testata>table>tbody');
			aggiungiTestata(tbodyTestata, codiceIntervento, null);
			mostraNascondiAzioniTestata(tbodyTestata);
			//2. Aggiungo un nuovo dettaglio
			let tbodyDettaglio = fieldsetDettaglio.querySelector('div.vbg-form>table>tbody');
			tbodyDettaglio.innerHTML = '';
			aggiungiDettaglio(tbodyDettaglio);
			fieldsetDettaglio.style.display='none';
		}
		
		

		
		function aggiungiTestata(tbodyTestata, codiceIntervento, testata){
			
			let txTempo = document.createElement('input');
			txTempo.setAttribute('name','txDescrizione');
			txTempo.type = 'text';
			txTempo.style.width="100%";
			if(testata){
				txTempo.value = testata.titolo;
			}
			
			let tdTempo = document.createElement('td');
			tdTempo.appendChild(txTempo);
			
			let iAggiungi = document.createElement('i');
			iAggiungi.classList.add('far');
			iAggiungi.classList.add('fa-lg');
			iAggiungi.classList.add('fa-plus-square');
			iAggiungi.classList.add('vbg-link');
			iAggiungi.addEventListener('click',(e) => { 
				let tr = tbodyTestata.querySelector('tr');
				let btnAggiungi = tr.querySelector('i.fa-plus-square');
				let btnModifica = tr.querySelector('i.fa-pencil');
				let btnElimina = tr.querySelector('i.fa-trash');
				let txDescrizione = tr.querySelector('input[name="txDescrizione"]');
				
				btnAggiungi.style.display = 'none';
				txDescrizione.style.display = '';
				btnModifica.style.display = '';
				btnElimina.style.display = '';
				
				fieldsetDettaglio.style.display='';
			});
			
			let iDettaglio = document.createElement('i');
			iDettaglio.classList.add('fa');
			iDettaglio.classList.add('fa-lg');
			iDettaglio.classList.add('fa-pencil');
			iDettaglio.classList.add('vbg-link');
			iDettaglio.addEventListener('click', (e)=>{
				e.preventDefault();
				
				if (fieldsetDettaglio.style.display === "none") {
					fieldsetDettaglio.style.display = '';
				} else {
					fieldsetDettaglio.style.display = 'none';
				}
			});
			
			
			let iElimina = document.createElement('i');
			iElimina.classList.add('fa');
			iElimina.classList.add('fa-lg');
			iElimina.classList.add('fa-trash');
			iElimina.classList.add('vbg-link');
			iElimina.addEventListener('click', async (e)=>{
				e.preventDefault();
				
				let tr = e.target.parentElement.parentElement;
				if(tr.dataset.idTempo){
					await eliminaTempoAlbero(codiceIntervento,tr.dataset.idTempo);
					tr.dataset.idTempo = '';
				}
				
				fieldsetDettaglio.querySelector('div.vbg-form>table>tbody').innerHTML = '';
				
				fieldsetDettaglio.style.display='none';
				mostraNascondiAzioniTestata(tbodyTestata);
			});
			
			let tdAzioni = document.createElement('td');
			tdAzioni.style.display = 'flex';
			tdAzioni.style.justifyContent = 'space-around';
			tdAzioni.appendChild(iAggiungi);
			tdAzioni.appendChild(iDettaglio);
			tdAzioni.appendChild(iElimina);
			
			let tr = document.createElement('tr');
			tr.dataset.idIntervento = codiceIntervento;
			tr.appendChild(tdTempo);
			tr.appendChild(tdAzioni);
			
			tbodyTestata.appendChild(tr);
			
			if(testata){
				tr.dataset.idTempo = testata.id;	
			}
		}
		
		function mostraNascondiAzioniTestata(tbodyTestata){
			
			let tr = tbodyTestata.querySelector('tr');
			let btnAggiungi = tr.querySelector('i.fa-plus-square');
			let btnModifica = tr.querySelector('i.fa-pencil');
			let btnElimina = tr.querySelector('i.fa-trash');
			let txDescrizione = tr.querySelector('input[name="txDescrizione"]');
			
			if(tr.dataset.idTempo && tr.dataset.idTempo != ''){
				btnAggiungi.style.display = 'none';
				txDescrizione.style.display = '';
				btnModifica.style.display = '';
				btnElimina.style.display = '';
			} else {
				btnAggiungi.style.display = '';
				txDescrizione.style.display = 'none';
				btnModifica.style.display = 'none';
				btnElimina.style.display = 'none';
			}
		}
		
		async function eliminaTempoAlbero(codiceIntervento, codiceTempoFo){
			
			const postParams = { request: { codiceIntervento, codiceTempoFo } };
			
			const response = await fetch('../alberoproc/jsonEliminaAlberoProcTempi.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
	    	if( await response.status != 200){
	    		_gestioneErrori(await response.json());
	    	} else {
				alert('Cancellazione avvenuta con successo');
	    	}
		}
		
		async function salvaTempi(codiceintervento){
			
			let tr = fieldsetTestata.querySelector('div.testata>table>tbody>tr');
			
			let idtempot = tr.dataset.idTempo ?? null;
			let desctempot = tr.querySelector('td>input[name="txDescrizione"]').value;
			let dettaglio = [];
			
			fieldsetDettaglio.querySelectorAll('div.vbg-form>table>tbody>tr').forEach((el,idx) => {
				let tempo = {};
				tempo.id = el.dataset.id;
				tempo.titolo = el.querySelector('input[name="titolo"]').value;
				tempo.descrizione = el.querySelector('input[name="descrizione"]').value;
				tempo.tipo = el.querySelector('select[name="tipo"]').value;
				tempo.giorni = el.querySelector('input[name="giorni"]').value;
				tempo.scadenza = el.querySelector('input[name="scadenza"]').value;
				tempo.ordine = idx;
				dettaglio.push(tempo);
			});
			
			const postParams = 
			{
			  request: {
				codiceintervento,
				idtempot,
				desctempot,
				dettaglio
			  }
			};
						
			const response = await fetch('../alberoproc/jsonSalvatempi.htm', {
	    		method: 'POST',
	            headers: {
	                'Accept': 'application/json',
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify(postParams)
	    	});
			
	    	if( await response.status != 200){
	    		_gestioneErrori(await response.json());
	    		return null;
	    	} else {
	    		let jsonResponse = await response.json();
	    		return jsonResponse.response;
	    	}
		}
		
		function aggiungiDettaglio(container, dettaglio){
			
			let txTitolo = document.createElement('input');
			txTitolo.setAttribute('name','titolo');
			txTitolo.type = 'text';
			txTitolo.style.width="100%";
			if(dettaglio && dettaglio.titolo){
				txTitolo.value = dettaglio.titolo;
			}		
			let tdTitolo = document.createElement('td');
			tdTitolo.appendChild(txTitolo);
			
			let txDescrizione = document.createElement('input');
			txDescrizione.setAttribute('name','descrizione');
			txDescrizione.type = 'text';
			txDescrizione.style.width="100%";
			if(dettaglio && dettaglio.descrizione){
				txDescrizione.value = dettaglio.descrizione;
			}
			let tdDescrizione = document.createElement('td');
			tdDescrizione.appendChild(txDescrizione);

			let optAssoluta = document.createElement('option');
			optAssoluta.value = 'A';
			optAssoluta.text = 'ASSOLUTA';
			
			let optRelativa = document.createElement('option');
			optRelativa.value = 'R';
			optRelativa.text = 'RELATIVA';
			
			let cbTipi = document.createElement("select");
			cbTipi.setAttribute('name','tipo');
			cbTipi.appendChild(document.createElement('option'));
			cbTipi.appendChild(optAssoluta);
			cbTipi.appendChild(optRelativa);
			if(dettaglio && dettaglio.tipo){
				cbTipi.value = dettaglio.tipo;
			}
			cbTipi.addEventListener('change',async function(e){
				e.preventDefault();
				ImpostaScadenza(e.target);			
			});
			
			let tdTipo = document.createElement('td');
			tdTipo.appendChild(cbTipi);
			
			
			let txGiorni = document.createElement('input');
			txGiorni.setAttribute('name','giorni');
			txGiorni.size = 4;
			txGiorni.type = 'text';
			txGiorni.style.textAlign = 'right';
			if(dettaglio && dettaglio.giorni){
				txGiorni.value = dettaglio.giorni;
			}
			let tdGiorni = document.createElement('td');
			tdGiorni.appendChild(txGiorni);

			
			let timeStamp = Date.now();
			let txScadenza = document.createElement('input');
			txScadenza.setAttribute('id','scadenza_' + timeStamp + '_id');
			txScadenza.setAttribute('name','scadenza');
			txScadenza.setAttribute('size','10');
			txScadenza.addEventListener('blur',(e) => {
				isValidDate(e.target,true);
			});
			txScadenza.type = 'text';
			
			if(dettaglio && dettaglio.scadenza){
				txScadenza.value = dettaglio.scadenza;
			}
			
			let iCalendar = document.createElement('i');
			iCalendar.classList.add('far');
			iCalendar.classList.add('fa-lg');
			iCalendar.classList.add('fa-calendar-alt');
			iCalendar.classList.add('vbg-link');
			
			let aScadenza = document.createElement('a');
			aScadenza.setAttribute('id','calScadenza_' + timeStamp);
			aScadenza.setAttribute('title','Calendario');
			aScadenza.dataset.calRefid = 'scadenza_id';
			aScadenza.classList.add('calendario');
			aScadenza.appendChild(iCalendar);
			
			let aScript = document.createElement('script');
			aScript.setAttribute('type','text/javascript');
			aScript.innerHTML = 'RANGE_CAL_1 = new Calendar({ inputField: "scadenza_' + timeStamp + '_id", dateFormat: "%d/%m/%Y", trigger: "calScadenza_' + timeStamp + '", bottomBar: false, onSelect: function() { var date = Calendar.intToDate(this.selection.get()); this.hide();}})'
			
			let divScadenza = document.createElement('div');
			divScadenza.setAttribute('name','divScadenza');
			divScadenza.appendChild(txScadenza);
			divScadenza.appendChild(aScadenza);
			divScadenza.appendChild(aScript);
			
			let tdScadenza = document.createElement('td');
			tdScadenza.appendChild(divScadenza);

			
			let iUp = document.createElement('i');
			iUp.classList.add('far');
			iUp.classList.add('fa-lg');
			iUp.classList.add('fa-caret-square-up');
			iUp.classList.add('vbg-link');
			iUp.addEventListener('click',async function(e){
				e.preventDefault();
				MoveUp(e.target);
			});
			
			let iDown = document.createElement('i');
			iDown.classList.add('far');
			iDown.classList.add('fa-lg');
			iDown.classList.add('fa-caret-square-down');
			iDown.classList.add('vbg-link');
			iDown.addEventListener('click',async function(e){
				e.preventDefault();
				MoveDown(e.target);
			});
			
			let iElimina = document.createElement('i');
			iElimina.classList.add('fa');
			iElimina.classList.add('fa-lg');
			iElimina.classList.add('fa-trash');
			iElimina.classList.add('vbg-link');
			iElimina.addEventListener('click',async function(e){
				e.preventDefault();
				container.removeChild(e.target.parentElement.parentElement);
			});
			
			let tdAzioni = document.createElement('td');
			tdAzioni.style.display = 'flex';
			tdAzioni.style.justifyContent = 'space-around';
			tdAzioni.appendChild(iUp);
			tdAzioni.appendChild(iDown);
			tdAzioni.appendChild(iElimina);
			
			let tr = document.createElement('tr');
			if(dettaglio){
				if(dettaglio.id){
					tr.dataset.id = dettaglio.id;	
				}
				
					tr.dataset.ordine = dettaglio.ordine;
				
			}
			tr.appendChild(tdTitolo);
			tr.appendChild(tdDescrizione);
			tr.appendChild(tdTipo);
			tr.appendChild(tdGiorni);
			tr.appendChild(tdScadenza);
			tr.appendChild(tdAzioni);
			
			ImpostaScadenza(cbTipi);
			
			container.appendChild(tr);
		}
		
		function popolaPanelDettaglio(idTempo, dettaglio){
			
			let table = fieldsetDettaglio.querySelector('div.vbg-form>table');
			table.dataset.id = idTempo;
			
			let tbody = table.querySelector('tbody');
			tbody.innerHTML = '';
			
			for (var d=0; d<dettaglio.length; d++) {
				let dett = dettaglio[d];
				aggiungiDettaglio(tbody, dett);
			}
		}
		
		function _gestioneErrori(errJson){
			console.log(errJson.error);
			alert( errJson.error);
		}
		
		function ImpostaScadenza(cbTipo){
			let divScadenza = cbTipo.parentElement.parentElement.querySelector('div[name="divScadenza"]');
			let giorni = cbTipo.parentElement.parentElement.querySelector('input[name="giorni"]');
			let scadenza = cbTipo.parentElement.parentElement.querySelector('input[name="scadenza"]');
			
			switch(cbTipo.value){
				case 'A':{
					giorni.style.display = 'none';
					giorni.value = '';
					divScadenza.style.display = '';
					break;
				}
				case 'R':{
					giorni.style.display = '';
					divScadenza.style.display = 'none';
					scadenza.value = '';
					break;
				}
				default:{
					giorni.style.display = 'none';
					giorni.value = '';
					divScadenza.style.display = 'none';
					scadenza.value = '';
					break;
				}
			}
		}
		
		function MoveUp(el)
	    {
	        var table,
	            row = el.parentNode;
	        
	        while ( row != null ) {
	            if ( row.nodeName == 'TR' ) {
	                break;
	            }
	            row = row.parentNode;
	        }
	        table = row.parentNode;
	        let dest = get_previoussibling( row );
	        table.insertBefore( row, dest );
	    }
		
	    function MoveDown(el)
	    {
	        var table,
	            row = el.parentNode;
	        
	        while ( row != null ) {
	            if ( row.nodeName == 'TR' ) {
	                break;
	            }
	            row = row.parentNode;
	        }
	        table = row.parentNode;
	        let dest = get_nextsibling( row );
	        table.insertBefore( dest, row );
	    }
	    function get_previoussibling(n)
	    {
	        x=n.previousSibling;
	        if(x == null){
	        	return n;
	        }
	        while (x.nodeType!=1)
	        {
	          x=x.previousSibling;
	        }
	        return x;
	    } 

	    function get_nextsibling(n)
	    {
	        x=n.nextSibling;
	        if(x == null){
	        	return n;
	        }
	        while (x.nodeType!=1)
	        {
	          x=x.nextSibling;
	        }
	        return x;
	    } 
	})
</script>
<fieldset name="alberoproctempi_testata">
	<legend><b><fmt:message key="alberoproc.label.alberoproctempi" /></b></legend>
	<div class="vbg-form testata">
		<table class="vbg-table">
			<thead>
				<tr>
					<th width="95%"><fmt:message key="label.descrizione"/></th>
					<th width="5%"><fmt:message key="label.azioni"/></th>
				<tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div class="vbg-form dettaglio">
		<fieldset name="alberoproctempi_dettaglio" style="display: none;">
			<legend><fmt:message key="label.prossimipassi" /></legend>
			<div class="vbg-form">
				<table class="vbg-table">
					<thead>
						<tr>
							<th width="30%"><fmt:message key="alberoproc.label.tempi.titolo"/></th>
							<th width="35%"><fmt:message key="alberoproc.label.tempi.descrizione"/></th>
							<th width="10%"><fmt:message key="alberoproc.label.tempi.tipo"/></th>
							<th width="5%"><fmt:message key="alberoproc.label.tempi.giorni"/></th>
							<th width="15%"><fmt:message key="alberoproc.label.tempi.scadenza"/></th>
							<th width="5%"><fmt:message key="label.azioni"/></th>
						</tr>
					</thead>
					<tbody></tbody>
					<tfoot></tfoot>
				</table>
			</div>
			<div class="form-button" style="padding-top: 10px;">
				<a class="btn btn-primary btn-save" href=""><fmt:message key="button.save" /></a>
				<a class="btn btn-primary btn-add" href="" ><fmt:message key="button.aggiungi" /></a>
			</div>
		</fieldset>		
	</div>
</fieldset>

