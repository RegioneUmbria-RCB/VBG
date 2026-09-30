<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--

Per utilizzare la firma digitale è necessario includere questa jsp nella pagina dove sono presenti i file da firmare.
La pagina utilizza i codici oggetto della tabella oggetti per il recupero dei file da firmare e per la loro sostituzione con il corrispondente firmato.
La firma è realizzata dalla webapp firma.
La jsp per poter funzionare correttamente deve trovare nella pagina chiamante i seguenti metodi javascript implementati:

function getFileDaFirmare()
il metodo deve restituire un array con i codici oggetto dei file da firmare

function afterSetEsitoFirma(codiceOggetto, esito, messaggio)
il metodo è invocato per ogni file firmato e può essere utilizzato per visualizzare l'esito della firma per ogni singolo file


function firmaCompletata()
metodo invocato solo se tutti i file sono stati firmati correttamente (da utilizzare per refresh della pagina)

Nella pagina del chiamante deve essere presente il seguente codice html per includere la pagina di firma

<input type="button" id="button-firma" value="Metti alla firma" onclick="mettiAllaFirma()" />
<div id="applet-container" style="display: none">
	<%@ include file="../includes/firmadigitale2.jsp"%>
</div>

--%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/deployJava.js"></script>
<script type="text/javascript">
//nome del contesto dell'applicazione di firma
var contextPathFirmaWebApp = '<%=WebConstants.getFirmaContextPath()%>';
//id della sessione di firma
var sessionIdVar;
//stringa dei fileId dei file messi alla firma separati da virgola
var listaFileId;
//array dei file messi alla firma
var listaFileMessiAllaFirma;
var totDaFirmare;
var totFirmati;

//recupera il valore dell'id della sessione di firma
function getFirmaSessionId(){
	return sessionIdVar;
}

//valorizza l'id della sessione di firma
function setFirmaSessionId(id){
	return sessionIdVar = id;
}

//reset delle variabili
function resetFirma(){
	sessionIdVar = "";
	listaFileId = "";
	listaFileMessiAllaFirma = "";
	totDaFirmare = 0;
	totFirmati = 0;
	$('#button-firma').removeAttr("disabled");
}

//metodo principale da invocare dalla pagina chiamante per avviare il processo di firma
function mettiAllaFirma(){
	resetFirma();
	//metodo implementato nella pagina chiamante
	var lista = getFileDaFirmare();
	totDaFirmare = lista.length;
	if(totDaFirmare == 0){
		alert("Nessun file selezionato per la firma");
		return;
	}
	lista = lista.join(",");
	$('#button-firma').attr("disabled", "disabled");
	$.ajax({
		  url: "../firmadigitale2/ajaxMettiAllaFirma.htm",
		  data:{listaCodiciOggetto:lista},
		  dataType: "json",
		  cache: false
	}).done(function( data ) {
		listaFileMessiAllaFirma = data.files;
		$.each(data.files, function (index, file) { 
			setFirmaSessionId(file.sessionId);
			listaFileId += file.fileId + ",";
           });
		listaFileId.slice(0,-1);
		showAppletContainer();
		downloadSignApplet();
	}).fail(function(jqXHR, textStatus) {
		alert( "Errore durante la messa alla firma dei file selezionati: (" + textStatus + ")" );
		resetFirma();
	});
}

//torna una stringa con i fileId dei file messi alla firma
function getListaCodiciOggetto() {
	return listaFileId;
}

//scarica il codice javascript dall'applicazione di firma per il caricamento dell'applet
function downloadSignApplet(){
	if(!$('#signAppletElement').is(":visible")){
		$.ajax({
			  url: contextPathFirmaWebApp + "/downloadSignApplet",		
			  dataType: "script",
			  cache: false
		}).fail(function(jqXHR, textStatus) {
			alert( "Errore durante lo scaricamento dell'applet di firma: (" + textStatus +")");
		});
	}
}

//aggiunge l'applet al tag applet-container
function docWriteWrapper(func) {
	var writeTo = document.createElement('div'),oldwrite = document.write,content = '';
	writeTo.id = "signAppletElement";
	writeTo.className = "signAppletClass";
	document.write = function(text) {content += text;};
	func();
	writeTo.innerHTML += content;
	document.write = oldwrite;
	//document.body.appendChild(writeTo);
	document.getElementById("applet-container").appendChild(writeTo);
}

//esegue la chiamata ajax al metodo per il recupero del file firmato, poi chiama il metodo afterSetEsitoFirma() ed al termine della firma
//di tutti i file chiama firmaCompletata()
function setEsitoFirma(fileId, esito, messaggio) {
	var codiceOggetto = "";
	var fileName = "";
	$.each(listaFileMessiAllaFirma, function (index, file) { 
		if(file.fileId == fileId){
			codiceOggetto = file.codiceOggetto;
			return;
		}
          });
	if ('OK' == esito) {
		$.ajax({
			  url: "../firmadigitale2/ajaxScaricaFileFirmato.htm",
			  data:{sessionId:getFirmaSessionId(),fileId:fileId,codiceOggetto:codiceOggetto},
			  dataType: "json",
			  async: false,
			  cache: false
		}).done(function( data ) {
			totFirmati += 1;
			fileName = data.fileName;
		}).fail(function(jqXHR, textStatus) {
			esito = 'KO';
			messaggio = "Errore durante il recupero del file firmato: ("+textStatus+")";
			alert(messaggio);
		});	
	}
	//metodo implementato nella pagina chiamante
	afterSetEsitoFirma(codiceOggetto, fileName, esito, messaggio);
	if (totFirmati == totDaFirmare) {
		//metodo implementato nella pagina chiamante
		firmaCompletata();
	}
}

function hideAppletContainer() {
	$('#applet-container').hide();
}

function showAppletContainer() {
	$('#applet-container').show();
}
</script>
