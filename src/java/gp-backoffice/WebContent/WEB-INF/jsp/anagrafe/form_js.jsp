<script type="text/javascript">
         <%-- 
         	variabile stringa che contien tutti gli id dei campi che hanno subito una 
         	modifica e questa è stata accettata/rifiutata separati dal carattere graticcio 
         	(es. campo1_id#campo2_id.....campoN) 
         --%>
var elementiVisitati = '';
function mostraNascondiEls(obj,tipo){	
	var a = document.getElementById(obj);
	if(tipo == '')
		{
			doHref('../anagrafe/${anagrafe.prefixPopup}create.htm?tiposoggetto='+a.value+"&popupCaller=${anagrafe.popupCaller}",'');	
		}else
	   {
			doHref('../anagrafe/${anagrafe.prefixPopup}create.htm?tiposoggetto='+a.value+'&visualizzaTipoAnagrafe='+tipo+'&popupCaller=${anagrafe.popupCaller}','');
	   }
	}
<%-- La sezione albo deve essere mostrata solo se il flag tecnico è selezionato --%>
function mostraNascondiSezioneAlbo()
{
	if($('albo_hidden')){
		if(!($('albo_hidden').value!='' || $('numero_id').value!=''
				|| $('provincia_provinciaelencopro_id').value!='' )){
		 showHideElements("sezione_albo_id","tr");
		}
	}else{
		  showHideElements("sezione_albo_id","tr");
	}				
}
<%-- FUNZIONE CHE GESTISCE L'APERTURA DELLA FINESTRA DI DIALOG
     NEL CASO UN CAMPO SIA STATO AGGIORNATO --%>
function gda(elementId){
	if(elementiVisitati.indexOf(elementId, 0)<0){
		if(document.getElementById(elementId).style.display=='none'){
			dijit.byId(elementId).show();
		}else{
			dijit.byId(elementId).hide();
		}					
	}
}
<%-- FUNZIONI CHE GESTISCO LE AZIONI CHE POSSONO ESSERE SVOLTE SU UN CAMPO MODIFICATO
     1- RIPRISTINO
     2- ACCETTAZIONE CAMBIAMENTI 
     3- RIPRISTINO CAMPO DI CHECKBOX PER IL CAMPO TIPOLOGIA
     4- ACCETTAZIONE CAMBIAMENTI CAMPO DI CHECKBOX PER IL CAMPO TIPOLOGIA
     5- RIPRISTINO CAMPO DI CHECKBOX 
     6- ACCETTAZIONE CAMBIAMENTI CAMPO DI CHECKBOX 
--%>			
function ripristina(fieldId,oldfieldId,elementId){
	if(document.getElementById(fieldId)){
		document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
	}
	var a = document.getElementById(oldfieldId);
	$(fieldId).value=a.value;	
	gda(elementId);
	elementiVisitati+=elementId+'#';
}
function accetta(fieldId,elementId){
	if(document.getElementById(fieldId)){
	document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
	}
	gda(elementId);
	elementiVisitati+=elementId+'#';
}
function ripristinaCheckboxTipologia(fieldId,oldfieldId,elementId){
	document.getElementById(fieldId).style.outlineColor='#FFFFFF';
	document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
	if(document.getElementById(oldfieldId).value==-1){
		$(fieldId).checked='checked';
	}else
	{
		$(fieldId).checked='';
	}
	gda(elementId);
	elementiVisitati+=elementId+'#';
}
function accettaCheckboxTipologia(fieldId,elementId){
	if(document.getElementById(fieldId).checked==false){
		$(fieldId).checked='';
	}else
	{
		$(fieldId).checked='checked';
	}
	document.getElementById(fieldId).style.outlineColor='#FFFFFF';
	document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
	gda(elementId);
	elementiVisitati+=elementId+'#';
}
function riprChkbox(fieldId,oldfieldId,elementId){
    document.getElementById(fieldId).style.outlineColor='#FFFFFF';
	document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
	if(document.getElementById(oldfieldId).value=='true'){
		$(fieldId).checked='checked';
	}else
	{
		$(fieldId).checked='';
	}
	gda(elementId);
	elementiVisitati+=elementId+'#';
}
function accChkbox(fieldId,elementId){
	if(document.getElementById(fieldId).checked==false){
		$(fieldId).checked='';
	}else
	{
		$(fieldId).checked='checked';
	}
	document.getElementById(fieldId).style.outlineColor='#FFFFFF';
	document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
	gda(elementId);
	elementiVisitati+=elementId+'#';
}
function nuovaPassword(){
	new Ajax.Request('<%=request.getContextPath()%>/anagrafe/ajaxGeneraPassword.htm', {
		  method: 'post',
		  parameters: {},
		  onSuccess: function(transport){
			  var response = transport.responseText;		
			  $('password_clear_id').value = response;						  	  
		    },
		  onFailure: function(transport){ 
			var response = transport.responseText;
				alert(response);
			 }						    		 
	});
}
function stampaRicevuta(url){
	var pwd = $('password_clear_id').value;
	if(pwd == ''){
		alert("Per la stampa è necessario generare una password.\nAttenzione! Dopo la stampa salvare la scheda per rendere effettiva la nuova password.");
	}else{
		url = url.replace('SEGNAPOSTO',pwd);
		window.open(url,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
	}
}
function calcolaCodicefiscale(){
	var sesso=$('sesso_id').value;
	var nominativo=$('nominativo_id').value;
	var nome=$('nome_id').value;
	var datanascita=$('data_nascita_id').value;
	var codicecomune=$('comune_nascita_hidden').value;
	if(sesso==''){
		alert('<fmt:message key="label.sesso"/> <fmt:message key="alert.required"/>');
		return;
	}
	if(nominativo==''){
		alert('<fmt:message key="label.cognome"/> <fmt:message key="alert.required"/>');
		return;
	}
	if(nome==''){
		alert('<fmt:message key="label.nome"/> <fmt:message key="alert.required"/>');
		return;
	}
	if(datanascita==''){
		alert('<fmt:message key="label.data_nascita"/> <fmt:message key="alert.required"/>');
		return;
	}
	if(codicecomune==''){
		alert('<fmt:message key="label.comune_nascita"/> <fmt:message key="alert.required"/>');
		return;
	}
	new Ajax.Request('<%=request.getContextPath()%>/anagrafe/ajaxGeneraCodicefiscale.htm', {
		  method: 'post',
		  parameters: {nominativo: nominativo, nome: nome, sesso:sesso, datanascita:datanascita, codicecomune:codicecomune},
		  onSuccess: function(transport){
			  var response = transport.responseText;		
			  $('codice_fiscale_id').value=response;						  	  
		    },
		  onFailure: function(transport){ 
			var response = transport.responseText;
				alert(response);
		  }						    		 
	});
}
</script>