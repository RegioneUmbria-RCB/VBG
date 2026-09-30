/**
 * 
 */
$(function() {
	$("#tabs").tabs();
	$( "#modificastato_dataProtocollo" ).datetimepicker();
});
function processaAttivazione() {
	$("#result").val("");
	var alias = $("#processaAttivazione_alias").val();
	var url = "processaAttivazione.xml?alias=" + alias;
	ajaxCall(url);

	return false;
};
/* GESTIONALE WS CLIENT */
function gestionaleWsRicercaIstanze() {
	$("#result").val("");
	var gestionaleUrl = $("#gestionalews_url").val();
	var aliasCert = $("#gestionalews_alias_certificato").val();
	var keyStore = $("#gestionalews_keystoreLocation").val();
	var trustStore = $("#gestionalews_truststoreLocation").val();
	var keyStorePwd = $("#gestionalews_keystorePwd").val();
	var trustStorePwd = $("#gestionalews_truststorePwd").val();
	var codiceComune = $("#gestionalews_ricercaistanze_codicecomune").val();
	var statoIstanza = $("#gestionalews_ricercaistanze_statoistanza").val();
	var url = "ricercaIstanze.xml?alias=" + aliasCert + "&url=" + gestionaleUrl + "&keystore=" + keyStore +
	"&truststore=" + trustStore + "&keystorePwd=" + keyStorePwd + "&truststorePwd=" + trustStorePwd + "&codiceComune=" + codiceComune + "&statoIstanza=" + statoIstanza;
	ajaxCall(url);

	return false;
};
function gestionaleWsScaricoXmlUnico() {
	$("#result").val("");
	var gestionaleUrl = $("#gestionalews_url").val();
	var aliasCert = $("#gestionalews_alias_certificato").val();
	var keyStore = $("#gestionalews_keystoreLocation").val();
	var trustStore = $("#gestionalews_truststoreLocation").val();
	var keyStorePwd = $("#gestionalews_keystorePwd").val();
	var trustStorePwd = $("#gestionalews_truststorePwd").val();
	var numeroIstanza = $("#scaricoXMLUnico_numeroIstanza").val();
	var url = "scaricoXML.xml?alias=" + aliasCert + "&url=" + gestionaleUrl + "&keystore=" + keyStore +
	"&truststore=" + trustStore + "&keystorePwd=" + keyStorePwd + "&truststorePwd=" + trustStorePwd + "&numeroIstanza=" + numeroIstanza;
	ajaxCall(url);

	return false;
};
function gestionaleWsModificaStatoIstanza() {
	$("#result").val("");
	var gestionaleUrl = $("#gestionalews_url").val();
	var aliasCert = $("#gestionalews_alias_certificato").val();
	var keyStore = $("#gestionalews_keystoreLocation").val();
	var trustStore = $("#gestionalews_truststoreLocation").val();
	var keyStorePwd = $("#gestionalews_keystorePwd").val();
	var trustStorePwd = $("#gestionalews_truststorePwd").val();
	var numeroIstanza = $("#modificastato_numeroIstanza").val();
	var numeroProtocollo = $("#modificastato_numeroProtocollo").val();
	var dataProtocollo = $("#modificastato_dataProtocollo").val();
	var nuovoStato = $("#modificastato_nuovoStato").val();
	var testo = $("#modificastato_testo").val();
	var url = "modificaStatoIstanza.xml?alias=" + aliasCert + "&url=" + gestionaleUrl + "&keystore=" + keyStore +
	"&truststore=" + trustStore + "&keystorePwd=" + keyStorePwd + "&truststorePwd=" + trustStorePwd + "&numeroIstanza=" + numeroIstanza + 
	"&nuovoStato=" + nuovoStato + "&testo=" + testo;
	if(numeroProtocollo !== ""){
		url += "&numeroProtocollo=" + numeroProtocollo;
	}
	if(dataProtocollo !== ""){
		url += "&dataProtocollo=" + dataProtocollo;
	}
	ajaxCall(url);

	return false;
};
/* DOCUMENTALE WS CLIENT */ 
function documentaleWsEstraiFileIstanza() {
	$("#result").val("");
	var documentaleUrl = $("#documentalews_url").val();
	var aliasCert = $("#documentalews_alias_certificato").val();
	var keyStore = $("#documentalews_keystoreLocation").val();
	var trustStore = $("#documentalews_truststoreLocation").val();
	var keyStorePwd = $("#documentalews_keystorePwd").val();
	var trustStorePwd = $("#documentalews_truststorePwd").val();
	var numeroIstanza = $("#estrai_file_numeroIstanza").val();
	var sbustato = $('[name="estrai_file_sbustato"]:checked').val();
	var url = "estraiFileIstanza.htm?alias=" + aliasCert + "&url=" + documentaleUrl + "&keystore=" + keyStore +
	"&truststore=" + trustStore + "&keystorePwd=" + keyStorePwd + "&truststorePwd=" + trustStorePwd + "&numeroIstanza=" + numeroIstanza + 
	"&sbustato=" + sbustato;
	window.open(url, "download");

	return false;
};
function documentaleWsEstraiAllegatoIstanza() {
	$("#result").val("");
	var documentaleUrl = $("#documentalews_url").val();
	var aliasCert = $("#documentalews_alias_certificato").val();
	var keyStore = $("#documentalews_keystoreLocation").val();
	var trustStore = $("#documentalews_truststoreLocation").val();
	var keyStorePwd = $("#documentalews_keystorePwd").val();
	var trustStorePwd = $("#documentalews_truststorePwd").val();
	var numeroIstanza = $("#estrai_allegato_numeroIstanza").val();
	var nomeAllegato = $("#estrai_allegato_nomeAllegato").val();
	var sbustato = $('[name="estrai_allegato_sbustato"]:checked').val();
	var url = "estraiAllegatoIstanza.htm?alias=" + aliasCert + "&url=" + documentaleUrl + "&keystore=" + keyStore +
	"&truststore=" + trustStore + "&keystorePwd=" + keyStorePwd + "&truststorePwd=" + trustStorePwd + "&numeroIstanza=" + numeroIstanza +
	"&nomeAllegato=" + nomeAllegato + "&sbustato=" + sbustato;
	window.open(url, "download");

	return false;
};
function ajaxCall(url) {
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'xml',
		complete : function(xhr, status) {
			$("#result").val(xhr.responseText);
			$('#result').format({
				method : 'xml'
			});
		}
	});
};