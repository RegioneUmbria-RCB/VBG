<%@page import="it.gruppoinit.pal.gp.core.domain.MercatiD"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ConcessioniCommand" %>
<%@ include file="../includes/taglibs.jsp" %>
<script type="text/javascript">
vbg.ready(() => {
	
	viewDataAffitto();
	
	if(!isEditmode){
		if (document.getElementById("mercati_hidden")!=null && document.getElementById("mercati_hidden").value != '') {
			assegnaPosteggio();			
		}
	}		
	
	gestFlagAttiva();	
	
	
	async function mercatiUsoDisplay(){
		removeOptionSelected("selectMercatiUso");
		let idMercato=document.getElementById("mercati_hidden").value;
		const response = await fetch("${pageContext.request.contextPath}/ajax/findMercatiUso.htm?code="+idMercato+"&limit=12",{
			method: 'post'
		});
		if(response.status=200){
			let resp = await response.text();
			  $("mercatiUso").appear();
			  var opts=response.split(",");
			  for(var i=0;i<((opts.length)-1); i++ ){
				  var j=i+1;
				  $('selectMercatiUso').add(new Option(opts[j],opts[i]),null);
				  i++;
			  }
		}else{
			
		}				  
	}	
	
	

	showTipologia(document.getElementById('selectTipoConcessione'));
	
	showHideDiv('ricercaAutorizzazione');
	showHideDiv('datiAutorizzazione');
	mostraModificaPosteggio('selectPosteggio');
	
	
	/* chiusura del popup */
	if(document.getElementById('closeButtonModal')){
		document.getElementById('closeButtonModal').addEventListener('click', (e)=>{
			document.querySelector('#popup-selectPosteggio').close();
		});
	}
	
	if(document.querySelector('#bottone_chiudi')){
		document.querySelector('#bottone_chiudi').addEventListener('click', (e) =>{
			window.close();
		});
	}
	
});

function aggiorna(){
	if(convalida()){
		doSubmit('updateConcessione.htm?codiceIstanza=${param.codiceIstanza}','',document.inviodati);
	}
}

function nuovoPosteggio(){
	if (document.getElementById("mercati_hidden").value != '') {
		var codiceMercato = document.getElementById("mercati_hidden").value;
		doSubmit('addPosteggio.htm?codiceMercato='+codiceMercato,'',document.inviodati);
	}
}

function visualizzaAutorizzazione(){

	showHideDiv('ricercaAutorizzazione');
	showHideDiv('datiAutorizzazione');
}

function inserisci(isPopup){
	if(convalida()){
		if(isPopup){
			doSubmit('insertConcessione.htm?codiceIstanza=${param.codiceIstanza}&decorator=popup', '', document.inviodati);
		}else{
			doSubmit('insertConcessione.htm?codiceIstanza=${param.codiceIstanza}','',document.inviodati);
		}
		
	}
}

function convalidaAut(){
	if(document.getElementById('selectChiusuraIstanza')){
		if(document.getElementById('selectChiusuraIstanza').value==''){
			alert('<fmt:message key="label.tipo_chiusura" /> <fmt:message key="field.required" />');
			document.getElementById('selectChiusuraIstanza').focus();				
			return false;
		}
	}
	return true;	
}

function convalida(){
	if(document.getElementById('selectPosteggio')){
		if(document.getElementById('selectPosteggio').value==''){
			alert('<fmt:message key="label.mercaD.obbligatorio" />');
			document.getElementById('selectPosteggio').focus();
			return false;
		}
	}
	if(document.getElementById('flag_attiva_id')){
		if(document.getElementById('flag_attiva_id').checked==false){
			if(document.getElementById('selectCausaleCessazione').value==''){
				alert('<fmt:message key="field.required" />');
				document.getElementById('selectCausaleCessazione').focus();
				return false;
			}
			if(document.getElementById('cessazione_id').value==''){
				alert('<fmt:message key="field.required" />');
				document.getElementById('cessazione_id').focus();
				return false;
			}
		}
	}			
	return true;
}	

function gestFlagAttiva(){
	let id_div_cessazione = document.querySelector('#dati_cessazione_id');
	let chkObj = document.getElementById('flag_attiva_id');
	if(chkObj){
		if(chkObj.checked){
			id_div_cessazione.style.display = 'none';
		}else{
			id_div_cessazione.style.display = 'inline-block';
		}
	}
}

function mostraModificaPosteggio(selectPosteggio){
	if (document.getElementById('selectPosteggio')) {
		if (document.getElementById('selectPosteggio').value != '') {
			showDiv('posteggioDettaglio_id');
		}else{
			hideDiv('posteggioDettaglio_id');
		}	        	
	}
}

async function showTipologia(obj){
	let pos = document.getElementById('selectTipoConcessione').selectedIndex;
	let valore_nascosto = '';
	if(document.getElementById('hidden_tipoconcessione')){
		valore_nascosto = document.getElementById('hidden_tipoconcessione').value;
	}		
	if (pos>-1 || valore_nascosto!='') {
		let itemSelected = null;
		if(valore_nascosto == ''){
			itemSelected = document.getElementById('selectTipoConcessione').options[pos].value;
		}else{
			itemSelected = valore_nascosto;
		}
		if(itemSelected==''){
			hideDiv('tipologiaStagionaleDiv');
			return;
		}
		
		const response =await fetch("${pageContext.request.contextPath}/concessionitipi/ajaxIsConcessioneStagionale.htm?tipoconcessione=" + itemSelected,
		{
			method : 'POST'
		});
		
		let flag = await response.text();
		if(response.status === 200){				
			if (flag == "true") {
				document.getElementById("tipologiaStagionaleDiv").style.display = "";
			} else {
				document.getElementById("tipologiaStagionaleDiv").style.display = "none";
			}
		}else{
			alert(flag);
		}
		
	}
}

async function assegnaPosteggio(){		
	removeOptionSelected("selectPosteggio");
	let idMercato = document.querySelector("#mercati_hidden").value;
	let idGiorno = document.querySelector("#mercatiUso_hidden").value;
	
	const response = await fetch("${pageContext.request.contextPath}/ajax/findPosteggiNonAssegnati.htm?codiceMercato="+idMercato+"&codiceMercatiUso="+idGiorno+"&limit=12",{
		method: 'POST'			
	});
	if(response.status ===200){
		let resp = await response.text();
		$("mercatiPosteggio").appear();
		let opts = resp.split("-SEP-");
		let itemSelected = false;
		$('selectPosteggio').options[$('selectPosteggio').options.length] = new Option('<fmt:message key="label.select.default" />','');
		for(var i=0;i<((opts.length)-1); i++ ){
		  if(posteggioSelezionato == opts[i]){
			  itemSelected = true; 
		  }else{
			  itemSelected = false;
		  }
	      $('selectPosteggio').options[$('selectPosteggio').options.length] = new Option(opts[i+1],opts[i],false,itemSelected);
		  i++;
	  }
	}else{
	  printResult(transport, "Errore durante la ricerca di posteggi");
  }		
}

function removeOptionSelected(opt){
	let elSel = document.getElementById(opt);
	let i;
	for (i = elSel.length - 1; i>=0; i--) {
		if (elSel.options[i]) {
		elSel.remove(i);
		}
	}
}

function viewDataAffitto(){	
	
	var causale = document.getElementById('selectCausaleAcquisizione').value;
	dataFineAffittoDisplay(causale);
}

async function dataFineAffittoDisplay(codice) {
	if(!codice){
	 	document.querySelector('#dataAffitto').hide();			 	
     	return;
	}

	const response = await fetch("${pageContext.request.contextPath}/autorizzazionisubentri/isDataFineAffitto.htm?codiceConcCausale="+codice, {
        method: "GET",
        cache: "no-cache"
        
	});
	if(response.status === 200){
		let flag = await response.text();
        if (flag == 'true') {
        	document.querySelector('#dataAffitto').show();
            document.querySelector('#dataAffitto').style.display = 'inline-block';           
        }
        else {
        	document.querySelector('#dataAffitto').hide();	                
            document.getElementById('dataFineAffitto_id').innerHTML = '';
        }         
	}else{
		alert(flag);
	}		   
}

function composizioneMercato(codiceMercato, codiceUso){
	if(codiceMercato!='' && codiceUso!=''){
		historySet('${_urlback}', '../mercatid/list.htm?codicemercato='+codiceMercato+"&codiceuso="+codiceUso,'');
	}			
}

var visualizzaDettaglioAutorizSpuntista = async function(){
	window.vbg.mostraModalCaricamento();
	let url =  '${pageContext.request.contextPath}/spuntistimercati/ajaxMercatiSpuntisti.htm?idautorizzazione=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.id.codice}';
	
	const response = await fetch(url, {
		method : 'GET',
		context: document.body,
		cache: 'no-cache'				
	});
	
	if(response.status === 200){
		
		const text = await response.text();
		window.vbg.nascondiModalCaricamento();
		document.querySelector('#vbg-modal-dettaglio-mercati-spuntisti').innerHTML = text;
		document.querySelector('#vbg-modal-dettaglio-mercati-spuntisti').open();
		
	}else{
		window.vbg.nascondiModalCaricamento();
		console.log('Errore:',await response.text());
	}
}

function apriModificaPosteggio(divId){
	
	document.querySelector('#popup-selectPosteggio').open();	
}


function assegnaAltroPosteggio(codiceConcessione,codiceIstanza, idAutorizzazione, divId){
	
	let idPosteggio=document.querySelector('#selectPosteggio_'+codiceConcessione).value;
	doHref('updateAssegnaPosteggioLibero.htm?codiceConcessione='+codiceConcessione+'&idPosteggio='+idPosteggio+'&idAutorizzazione='+idAutorizzazione+'&codiceIstanza='+codiceIstanza,'Attenzione!! Si intende assegnare il nuovo posteggio alla concessione? \nL\'operazione sarà riportata sui log applicativi');
}	

function modificaPosteggio(idPosteggio, codiceIstanza, idAutorizzazione){
	
	doHref('updatePosteggio.htm?idPosteggio='+idPosteggio+'&idAutorizzazione='+idAutorizzazione+'&codiceIstanza='+codiceIstanza,'');
		
}
</script>