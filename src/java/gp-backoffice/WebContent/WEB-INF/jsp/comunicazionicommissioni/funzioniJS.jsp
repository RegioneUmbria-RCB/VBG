<%@ include file="../includes/taglibs.jsp" %>
<script type="text/javascript">
vbg.ready(() => {
	
	function inizializzaComunicazione(){
		
		inizializzacmdEliminaComunicazione();
		inizializzaChiudiDettaglio();
		inizializzaElaboraComunicazione();
		inizializzaDettagliNellaLista();
		inizializzaModificaMailNellaLista();
		inizializzaModificaMailNelPopup();
		inizializzaElaboraRigaNellaLista();
		inizializzaElaboraRigaNelPopup();
		aggiungiTitleALabel();
	}
	
	
	function inizializzaDettagliNellaLista(){
		
		let cmdDettagli = document.getElementsByName('cmdDettaglio');
		
		for(let i = 0;i < cmdDettagli.length; i++){
			
			let cmdDettaglio = cmdDettagli[i];
			
			let idRigaDettaglio = parseInt(cmdDettaglio.dataset.id);
						
			cmdDettaglio.addEventListener('click', async function(e) {
				
				e.preventDefault();
				try{
					vbg.mostraModalCaricamento();
					await visualizzaDettaglioRiga(idRigaDettaglio);
				}
				finally{
					vbg.nascondiModalCaricamento();
				}
			});
		}
	}
	
	function inizializzaModificaMailNellaLista(){
		
		let cmdModificaMail = document.getElementsByName('cmdModificaMail');	

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

			
			if( modificamail === "false" ) {
				cmd.style.display = 'none';
				continue;
			}
			
			cmd.style.display = 'inline-block';
			cmd.addEventListener('click', function(e) {
				
				e.preventDefault();
				try{
					vbg.mostraModalCaricamento();
					visualizzaModificaMail(idRigaDettaglio, codiceAnagrafe, codiceAmministrazione, codiceResponsabile,titolare, mail, pec);
				}
				finally{
					vbg.nascondiModalCaricamento();
				}
			});
		}
	}
	
	 function inizializzaModificaMailNelPopup(){
		let cmdSalvaMail = document.getElementById('cmdSalvaMail');
		
		cmdSalvaMail.addEventListener('click', async function(e) {
			
			e.preventDefault();
			try{
				vbg.mostraModalCaricamento();
				let idRigaDettaglio = parseInt(cmdSalvaMail.dataset.id);
				let codiceAnagrafe = cmdSalvaMail.dataset.codiceanagrafe;
				let codiceAmministrazione = cmdSalvaMail.dataset.codiceamministrazione;
				let codiceResponsabile = cmdSalvaMail.dataset.codiceresponsabile;
				await salvaMail(codiceAnagrafe, codiceAmministrazione, codiceResponsabile);
				await aggiornaStato(idRigaDettaglio);
			}
			finally{
				vbg.nascondiModalCaricamento();
			}
			
			document.getElementById('popup-modifica-mail').style.display = "none";
			// elaboraComunicazione();
		});
	}
	
	function inizializzaElaboraComunicazione(){
		let cmdElaboraComunicazione =  document.getElementById('cmdElaboraComunicazione');
		
		cmdElaboraComunicazione.addEventListener('click', function(e) {
			let dettaglio = elaboraComunicazione();
			e.preventDefault();
		});
		
	}
	
	function inizializzaChiudiDettaglio(){
		let cmdChiudiDettaglio =  document.getElementById('cmdChiudiDettaglio');
		
		let url = cmdChiudiDettaglio.dataset.url;
		
		cmdChiudiDettaglio.addEventListener('click', function(e) {
			doHref(url,'');
			e.preventDefault();
		});
	}
	
	
	function inizializzacmdEliminaComunicazione(){
		let cmdEliminaComunicazione =  document.getElementById('cmdEliminaComunicazione');
		
		let url = cmdEliminaComunicazione.dataset.url;
		
		cmdEliminaComunicazione.addEventListener('click', function(e) {
			doHref(url,'<fmt:message key="javascript.confirm.delete" />');
			e.preventDefault();
		});
	}
	
	function inizializzaElaboraRigaNelPopup(){
		
		let cmdElaboraRiga = document.getElementById('riga-cmd-elabora-riga');
		
		cmdElaboraRiga.addEventListener('click', async function(e) {
			
			e.preventDefault();
			
			try {
				vbg.mostraModalCaricamento();
				let idRigaDettaglio = parseInt(cmdElaboraRiga.dataset.id);
				let dettaglio = await elaboraRiga(idRigaDettaglio);
				await setDettaglioRiga(dettaglio);				
			}
			finally {
				vbg.nascondiModalCaricamento();
			}
		});
	}
	
	
	const inizializzaElaboraRigaNellaLista = () => {
		let cmdElaboraRiga = document.getElementsByName('cmdElaboraRiga');
		
		for(let i = 0;i < cmdElaboraRiga.length; i++){
						
			let elaboraRiga = cmdElaboraRiga[i];
			
			let idRigaDettaglio = parseInt(elaboraRiga.dataset.id);
						
			elaboraRiga.addEventListener('click', async function(e) {
				
				e.preventDefault();
				
				try{
					vbg.mostraModalCaricamento();
					elaboraRiga.classList.add('attivo');
					await aggiornaStato(idRigaDettaglio);
					elaboraRiga.classList.remove('attivo');
				}
				finally {
					vbg.nascondiModalCaricamento();
				}
				
			});
		}
	}
	
	async function aggiornaStato(idRigaDettaglio){
		
		let dettaglio = await elaboraRiga(idRigaDettaglio);
				
		document.getElementById('destinatario'+idRigaDettaglio).innerHTML = dettaglio.destinatario;
		document.getElementById('descrizioneStatoRiga'+idRigaDettaglio).innerHTML = dettaglio.descrizionestato;
		document.getElementById('errore'+idRigaDettaglio).innerHTML = '';
		if( dettaglio.errore ) {
			document.getElementById('errore'+idRigaDettaglio).innerHTML = dettaglio.errore;
		}
		let cmd = document.querySelector('div[name="cmdModificaMail"][data-id="' + idRigaDettaglio + '"]');
				
		cmd.style.display = dettaglio.modificamail === false ? 'none' : 'inline-block';

	}
	
	async function visualizzaDettaglioRiga(idRigaDettaglio){
						
		let dettaglio = await getDettagliRiga(idRigaDettaglio);
				
		await setDettaglioRiga(dettaglio);
		
		document.getElementById('popup-dettaglio-riga').style.display = "flex";
	}
	
	async function getDettagliRiga(idRigaDettaglio){		
		
		let url = '${pageContext.request.contextPath}/comunicazionicommissioni/ajaxGetRigaDettagliata.htm?idRiga=' + idRigaDettaglio;
		const response = await fetch(url, {
			 method: 'GET'
		});
		
		if (response.status !== 200) {
            const errore = await response.text();
            console.error(errore);
            throw errore;
        }
		
		return await response.json();
	}
	
	const elaboraRiga = async(idRigaDettaglio) => {
			
		let url = '${pageContext.request.contextPath}/comunicazionicommissioni/ajaxElaboraRiga.htm?idRiga=' + idRigaDettaglio;

		const response = await fetch(url, {
			 method: 'GET'
		});
		
		if (response.status !== 200) {
            const errore = await response.text();
            console.error(errore);
            throw errore;
        }
		
		let risposta = await response.json(); 
		
		return risposta; 
	}
	
	const salvaMail = async ( codiceAnagrafe , codiceAmministrazione, codiceResponsabile ) => {
		
		let email = document.getElementById('modifica-mail').value;
		let pec = document.getElementById('modifica-pec').value;
		
		 const formData = new FormData();
       

         formData.append('codiceAnagrafe', codiceAnagrafe);
         formData.append('codiceResponsabile', codiceResponsabile);
         formData.append('codiceAmministrazione', codiceAmministrazione);
         formData.append('email', email);
         formData.append('pec', pec);
		
		let url = '${pageContext.request.contextPath}/comunicazionicommissioni/ajaxAggiornaMailOPec.htm';
	
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
	
	 const elaboraComunicazione = async (idComunicazione) => {
			
		let cmdElaboraRiga = document.getElementsByName('cmdElaboraRiga');
		
		for(let i = 0;i < cmdElaboraRiga.length; i++){
			let elaboraRiga = cmdElaboraRiga[i];
			
			elaboraRiga.classList.add('attivo');
			await aggiornaStato(parseInt(elaboraRiga.dataset.id));
			elaboraRiga.classList.remove('attivo');
		}
	}
	
	const setDettaglioRiga = async (dettaglio) => {
		
		clearDettaglioRiga();
		
		document.getElementById('riga-titolo').innerHTML = dettaglio.descrizionestato;
		document.getElementById('riga-destinatario').innerHTML = dettaglio.destinatario;
		
		if( dettaglio.estremiprotocollo ){
			document.getElementById('riga-protocollo').innerHTML = dettaglio.estremiprotocollo;
		}
		if( dettaglio.errore ){
			document.getElementById('riga-errore').innerHTML = dettaglio.errore;
		}

		if(dettaglio.codicioggettoallegati){
			document.getElementById('riga-allegati').innerHTML = await getAllegatiTemplate(dettaglio.codicioggettoallegati);
		}

		if(dettaglio.firmatari){
			document.getElementById('riga-firmatari').innerHTML = getFirmatariTemplate(dettaglio.firmatari);
		}

		document.getElementById('riga-cmd-elabora-riga').setAttribute('data-id' , dettaglio.id);
	}
	
	const clearDettaglioRiga = () => {
		document.getElementById('riga-titolo').innerHTML = '';
		document.getElementById('riga-destinatario').innerHTML = '';
		document.getElementById('riga-protocollo').innerHTML = '';
		document.getElementById('riga-errore').innerHTML =  '';
		document.getElementById('riga-allegati').innerHTML = '';
		document.getElementById('riga-firmatari').innerHTML = '';
		document.getElementById('riga-cmd-elabora-riga').setAttribute('data-id' , '');
	}
	
	const visualizzaModificaMail = (idRigaDettaglio, codiceAnagrafe, codiceAmministrazione, codiceResponsabile, titolare, mail, pec) => {
		
		
		let tipoIndirizzo = '${comunicazioneBollettazioneDetail.sceltaMailAnagrafeCodice}';
		
		document.getElementById('titolare-mail-pec').innerHTML = titolare;
		
		document.getElementById('cmdSalvaMail').setAttribute('data-id' , idRigaDettaglio);
		document.getElementById('cmdSalvaMail').setAttribute('data-codiceanagrafe' , codiceAnagrafe);
		document.getElementById('cmdSalvaMail').setAttribute('data-codiceamministrazione' , codiceAmministrazione);
		document.getElementById('cmdSalvaMail').setAttribute('data-codiceresponsabile' , codiceResponsabile);
		
		
		document.getElementById('div-modifica-mail').style.display = tipoIndirizzo === 'SOLO_PEC' ? 'none' : 'inline-block';
		document.getElementById('div-modifica-pec').style.display = tipoIndirizzo === 'SOLO_MAIL' ? 'none' : 'inline-block';
		
		if(codiceResponsabile!=''){
			document.getElementById('div-modifica-pec').style.display = 'none';	
		}
		
		document.getElementById('modifica-mail').value = mail;
		document.getElementById('modifica-pec').value = pec;
		
		document.getElementById('popup-modifica-mail').style.display = "flex";
	};
		
	const getAllegatiTemplate = async (codiciOggettoAllegati) => {

		let template = '<ul>';
		for( let i=0; i < codiciOggettoAllegati.length; i++ ){
			template += '<li>' + await getSnippetOggetto(codiciOggettoAllegati[i],'allegato'+codiciOggettoAllegati[i]) + '</li>';
		}
		template += '</ul>';
				
		return template;
	}
	
	const getFirmatariTemplate = (firmatari) => {
		let template  = '<ul>';
		for( let i=0; i<firmatari.length; i++ ){
			template += '<li>' + firmatari[i].responsabile;
			if( firmatari[i].firmato === true  ){
				template +='<i  title="Il documento è stato firmato da '+firmatari[i].responsabile+'" class="fa fa-toggle-on" style="color:green; font-size: 1.5em; margin-left: var(--half-padding);"></i>'; 
			}else{
				template +='<i title="Il documento non è stato firmato '+firmatari[i].responsabile+'" class="fa fa-toggle-off" style="color:red; font-size: 1.5em; margin-left: var(--half-padding);"></i>'
			}
			template +='</li>'
		}
		template += '</ul>';
		
		return template;
	}
	
	const getSnippetOggetto = async (codiceOggetto, idElemento) => {
		
		let mostralabel = true;
		let mostraNomeFile = true;
		let readonly = true;
		let mostrastorico = false;
		let jsFx = 'viewOggetto_' + idElemento + '_fx'; 
		let styleHref = '';
		let url = `../file/ajaxViewOggettoList.htm?fileId=\${codiceOggetto}&mostralabel=\${mostralabel}&mostraNomeFile=\${mostraNomeFile}&mostrastorico=\${mostrastorico}&readonly=\${readonly}&jsFx=\${jsFx}&styleHref=\${styleHref}`;
			
		const response = await fetch(url, {
			 method: 'GET',
			 context: document.body,
			 //cache: false,				
			 dataType: "html",
		});
		
		if (response.status !== 200) {
            const errore = await response.text();
            document.getElementById('id_'+ idElemento).innerHTML = errore.innerText;
            
            console.error(errore.innerText);
            throw errore.innerText;
        }
		
		return await response.text();
	}
	

	inizializzaComunicazione();

	
});
</script>