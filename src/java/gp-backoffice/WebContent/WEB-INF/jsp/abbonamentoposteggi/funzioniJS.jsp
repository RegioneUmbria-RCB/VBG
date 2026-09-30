<%@ include file="../includes/taglibs.jsp"%>

<script type="text/javascript">

		
		
		
		async function loadAutorizzazioni() {
			
			try{
					
					
					const url = "../abbonamentoposteggi/ajaxBorselliniPerAutorizzazioni.htm";
					
					let autorizzazione =  document.getElementById('autorizzazioni_id').value;
					
					const formData = new FormData();
					

					formData.append('autorizzazione', autorizzazione);
					
					const response = await fetch(url, {
						method: 'POST',
						body: formData
					});
					
					if( await response.status == 200){						
		        		return await response.json();
					}
					
			}finally{
					
			}
		}
		
		
		async function filtraCelle() {

			let stato = document.getElementById('stato').value;
			let anagrafe = document.getElementById('anagrafe_id').value;
			let autorizzazione =  document.getElementById('autorizzazioni_id').value;
			
			let input, filter, table, tr, td, i, txtValue;
			

			const trs = document.querySelectorAll('.riga-bors');
			
			table = document.getElementById("tabella-abbonamenti");
			tr = table.getElementsByClassName('riga-bors');
			
			
			if(autorizzazione.length < 3){ // minChars
				autorizzazione='';
			}
				
			try{
				
				window.vbg.mostraModalCaricamento();
				
				if(anagrafe =='' && stato=='0' && autorizzazione==''){
					
					for (i = 0; i < tr.length; i++) {
						td = tr[i].style.display='';
					}
					
					return;
				}

				let filtriImpostati = 0;
				if(anagrafe!='') {
					filtriImpostati +=1; 
				}
				if(stato!='0'){
					filtriImpostati +=1; 
				}
				var trovaIdPerAut = []; 
				if(autorizzazione != ''){
					filtriImpostati += 1;
					trovaIdPerAut = await trovaIdBorsellinoPerAutorizzazioni(autorizzazione);
				}
				
				let nrighe = 0;	
				for (i = 0; i < tr.length; i++) {
					td = tr[i].getElementsByTagName("td");		
					let trovato = false;
					let filtriTrovati = 0;
					for (let cell of td) {
						if (cell) {
							txtValue = cell.textContent || cell.innerText;
							if(anagrafe.length >=2 ) {
								if(cell.className == 'riga-anagr'){
									if (txtValue.toUpperCase().indexOf(anagrafe.toUpperCase()) > -1) {
										filtriTrovati  += 1;
										trovato = true;
									}
								}
							}
							if(stato!='0'){
								if(cell.className == 'riga-stato'){
									if ( txtValue.toUpperCase() == stato ) {
										filtriTrovati  += 1;
										trovato = true;
									}
								}
							}
							if(autorizzazione!=''){
								if(cell.className == 'riga-dett'){
									let idBorsellino = tr[i].dataset.id;
									if(trovaIdPerAut.includes(idBorsellino)){
										filtriTrovati  += 1;
										trovato = true;
									}
								}									
							}
						}
					}
					if (trovato && filtriTrovati == filtriImpostati) {
						nrighe++;
						tr[i].style.display = "";
						trovato = false;
					} else {
						tr[i].style.display = "none";
					}
				}
				
			} finally {
				window.vbg.nascondiModalCaricamento();
			}
			
		}

		async function trovaIdBorsellinoPerAutorizzazioni(autorizzazione) {
			
			let ret = [];
			const idBorsellino = await loadAutorizzazioni();
			
			if(idBorsellino && idBorsellino.dati && idBorsellino.dati.length>0){
				
				idBorsellino.dati.forEach((elem) => {
					ret.push(''+elem.value);
					  
				});
			}

			return ret;
		}
		

		async function popolaTabella() {
			
			try{	
				let borsellino = await getDatiBorsellino();
				
				window.vbg.mostraModalCaricamento();
				
				
				if(borsellino && borsellino.borsellini){
				
				let tBody = document.querySelector('#tabella-abbonamenti tbody');
					tBody.innerHTML = '';
		
					let dett = `<a href="javascript:void(0)" id="dettaglio" class="dettaglio">
								<i class="fa fa-edit"></i>
								<fmt:message key="label.dettaglio" />
								</a>`;
		
					borsellino.borsellini.forEach(el => {
		
						let tdNominativo = document.createElement('td');
						tdNominativo.addClassName('riga-anagr');
						tdNominativo.textContent = el.nominativo;
		
						let tdStato = document.createElement('td');
						tdStato.addClassName('riga-stato');
						tdStato.textContent = el.stato;
		
						let tdDataCreazione = document.createElement('td');
						let data = new Date(el.data_creazione);
						tdDataCreazione.textContent = data.toLocaleDateString();
		
						let tdCredito = document.createElement('td');
						tdCredito.addClassName('riga-dett');
						tdCredito.setAttribute('data-idanagrafica', el.idAnagrafica);
						tdCredito.innerHTML = el.credito_residuo.toLocaleString('it-IT', { style: 'currency', currency: 'EUR' }) + dett;
		
						let tr = document.createElement('tr');
						tr.addClassName('riga-bors visibile');
						tr.setAttribute('data-id', el.id);
						tr.appendChild(tdNominativo);
						tr.appendChild(tdStato);
						tr.appendChild(tdDataCreazione);
						tr.appendChild(tdCredito);
		
						tBody.appendChild(tr);
						tdCredito.querySelector(".dettaglio").addEventListener("click", () => {
							vaiADettaglio(el.idAnagrafica);
						});
		
						
					});
		
				
				}else{
					// non ci sono dati da mostrare
				}
			}finally{
				window.vbg.nascondiModalCaricamento();
			}
			
		}

		function vaiADettaglio(codiceanagrafe) {

		 	doHref('view.htm?codiceanagrafe=' + codiceanagrafe, '');
		}
		
		async function getDatiBorsellino() {
			
			try{
					window.vbg.mostraModalCaricamento();
					
					const url = "../abbonamentoposteggi/ajaxGetBorsellino.htm";
					let stato = document.getElementById('stato').value;
					let anagrafe = ''; //document.getElementById('idAnagrafe').value;
					let id_autorizzazione =  document.getElementById('autorizzazioni_id').value;
					const formData = new FormData();
					
					formData.append('stato', stato);
					formData.append('anagrafe', anagrafe);
					formData.append('id_autorizzazione', id_autorizzazione);
					
					const response = await fetch(url, {
						method: 'POST',
						body: formData
					});
					
					if( await response.status == 200){						
		        		return await response.json();
					}
			}finally{
					window.vbg.nascondiModalCaricamento();
			}
		}
		

	vbg.ready(() => {	
		
		popolaTabella();
	
		document.getElementById('anagrafe_id').addEventListener('keyup', async function(e){
			e.preventDefault();
			let el = e.target;
			if(el.value.length==0 || el.value.length>=2){ // solo se digita almeno due caratteri 
											  // anche se la stringa è 0 caratteri che deve reimpostare 
				filtraCelle();
			}
		});
		
		document.getElementById('autorizzazioni_id').addEventListener('keyup', async function(e){
			e.preventDefault();
			let el = e.target;
			if(el.value.length==0 || el.value.length>=2){ // solo se digita almeno due caratteri 
											  // anche se la stringa è 0 caratteri che deve reimpostare 
				filtraCelle();
			}
		});
		
		document.getElementById('stato').addEventListener('change', async function(e){
			e.preventDefault();
			filtraCelle();
			
		});
		

		document.querySelectorAll('.riga-dett').forEach(d => {

			d.querySelector('#dettaglio').addEventListener('click', async (e) => {

				let codiceanagrafe = d.dataset.idanagrafica;
				doHref('view.htm?codiceanagrafe=' + codiceanagrafe, '');

			});

		});



});


	</script>