<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><fmt:message key="label.documenti_autorizzazioni.title"/></title>
	<style>
		.descrizione-file {
			font-weight: bold;
		}
		.allegati-tpl {
			margin-top: 7px;
			font-style: italic;
		}
	</style>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.documenti_autorizzazioni.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../documentiautorizzazione/list" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
		<jsp:param name="commandName" value="documentiistanza" />
	</jsp:include>
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.dati_autorizzazione"/></legend>
			<div class="form-group">
				<span class="header_dato_etichetta"><fmt:message key="label.documenti_autorizzazioni.numero_aut" /></span>
				<span class="header_dato_valore">${autorizzazione.autoriznumero}</span>
			</div>
			<div class="form-group">
				<span class="header_dato_etichetta"><fmt:message key="label.documenti_autorizzazioni.data_autorizzazione"/>:</span>			
				<span class="header_dato_valore"><fmt:formatDate value="${autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></span>
			</div>
			<div class="form-group">
				<span class="header_dato_etichetta"><fmt:message key="label.documenti_autorizzazioni.intestatario"/>:</span>
				<span class="header_dato_valore">${autorizzazione.anagrafe.descrizioneRichiedente}</span>
			</div>
		</fieldset>
		<form  name="inviodati" action="list.htm" id="myform" method="post">
			<fieldset>
				<legend><fmt:message key="label.lista_documenti_autorizzazioni"/></legend>
				<div id="dettaglioDocumentiAutorizzazioni" class="form-group">
					<img src='${pageContext.request.contextPath}/images/spinner.gif'/>
				</div>
				<div id="messaggioErroreDocumentiAutorizzazione" class="error_header" style="display: none;"></div>
				<a class="btn btn-primary" id="metti_alla_firma_btn" style="display: none;" href="javascript:historySet('${_urlback }','../documentiautorizzazione/listDocumentiMessiAllaFirma.htm?codiceautorizzazione=${autorizzazione.id.codice}','');"><fmt:message key="button.metti_alla_firma"/></a>
				<a class="btn btn-primary" id="btnCompleta" ><fmt:message key="button.documenti_autorizzazioni.completa" /></a>
				<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
			</fieldset>
			<%
				String displayDocumentiistanza = "display: none;";
				String styleDocist = "";
				// gestisce la visualizzazione della lista dei documenti dell'istanza
				if ("1".equals((String)request.getAttribute(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ISTANZA))){
				    displayDocumentiistanza = "";
				    styleDocist = "sezioneDatiMeno";
				} else {
				    displayDocumentiistanza = "display:none;";
				    styleDocist = "sezioneDatiPiu";
				}
			%>
			<fieldset>
				<legend>
					<a class="<%=styleDocist %>" id="id_link_docistanza" href="javascript:showHidePanel('dettaglioDocumentiIstanza', 'id_link_docistanza', '<%= WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ISTANZA %>', '${pageContext.request.contextPath}/images/', 'div');"	title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.documenti_istanza"/>">
						<label><fmt:message key="label.documenti_istanza"/></label>
					</a>
				</legend>
				<div id="dettaglioDocumentiIstanza" style="<%=displayDocumentiistanza%>;" class="form-group">
					<img src='${pageContext.request.contextPath}/images/spinner.gif'/>
				</div>
				<div id="messaggioErroreDocumentiIstanza" class="error_header" style="display: none;"></div>
			</fieldset>
			<%
				String displayDocmovimento = "display: none;";
				String styleDocmovimento = "";
				// gestisce la visualizzazione della lista dei movimenti
				if("1".equals((String)request.getAttribute(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_MOVIMENTO))){
				    displayDocmovimento = "";
				    styleDocmovimento = "sezioneDatiMeno";
				} else {
				    displayDocmovimento = "display: none;";
				    styleDocmovimento = "sezioneDatiPiu";
				}
			%>
			<fieldset>
				<legend>
					<a class="<%=styleDocmovimento %>" id="id_link_movallegati" href="javascript:showHidePanel('dettaglioMovimentiAllegati', 'id_link_movallegati', '<%= WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_MOVIMENTO %>', '${pageContext.request.contextPath}/images/','div');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.movimenti_allegati"/>">
						<label for="id_link_movallegati"><fmt:message key="label.movimenti_allegati"/></label>
					</a>
				</legend>
				<div class="form-group" id="dettaglioMovimentiAllegati" style="<%=displayDocmovimento%>;">
					<img src='${pageContext.request.contextPath}/images/spinner.gif'/>
				</div>
				<div id="messaggioErroreAllegatiMovimento" class="error_header" style="display: none;"></div>
			</fieldset>
			<%
				String displayIstanzeallegati = "display: none;";
				String styleIstanzeallegati = "";
				// gestisce la visualizzazione della lista degli allegati dell'istanza
				if ("1".equals((String)request.getAttribute(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_ALLEGATI_ISTANZA))){
				    displayIstanzeallegati = "";
					styleIstanzeallegati = "sezioneDatiMeno";
				} else {
				    displayIstanzeallegati = "display: none;";
					styleIstanzeallegati = "sezioneDatiPiu";
				}
			%>
			<fieldset>
				<legend>
					<a class="<%=styleIstanzeallegati %>" id="id_link_istallegati" href="javascript:showHidePanel('dettaglioIstanzeAllegati', 'id_link_istallegati', '<%= WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_ALLEGATI_ISTANZA %>', '${pageContext.request.contextPath}/images/','div');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.procedimento"/>">
						<label for="id_link_istallegati"><fmt:message key="label.procedimento"/></label>
					</a>
				</legend>
				<div class="form-group" id="dettaglioIstanzeAllegati" style="<%=displayIstanzeallegati %>;">
					<img src='${pageContext.request.contextPath}/images/spinner.gif'/>
				</div>
				<div id="messaggioErroreIstanzeAllegati" class="error_header" style="display: none;"></div>
			</fieldset>
			<%
				String displayProcure = "dsiplay: none;";
				String styleProcure = "";
				// gestisce la visualizzazione della lista dei documenti degli endoprocedimenti
				if ("1".equals((String)request.getAttribute(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_PROCURE))){
				    displayProcure = "";
				    styleProcure = "sezioneDatiMeno";
				} else {
				    displayProcure = "display: none;";
				    styleProcure = "sezioneDatiPiu";
				}
			%>
			<fieldset>
				<legend>
					<a class="<%=styleProcure %>" id="id_link_istprocure" href="javascript:showHidePanel('dettaglioIstanzeProcure', 'id_link_istprocure', '<%= WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_PROCURE %>', '${pageContext.request.contextPath}/images/','div');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.procure_dell_istanza"/>">
						<label for="id_link_istprocure"><fmt:message key="label.procure_dell_istanza"/></label>
					</a>
				</legend>
				<div class="form-group" id="dettaglioIstanzeProcure" style="<%=displayProcure%>;">
					<img src='${pageContext.request.contextPath}/images/spinner.gif'/>
				</div>
				<div id="messaggioErroreIstanzeProcure" class="error_header" style="display: none;"></div>
			</fieldset>
			<%
				String displayDocanagrafe = "display: none;";
				String styleDocanagrafe = "";
				// gestisce la visualizzazione della lista dei documenti relativi all'anagrafe
				if ("1".equals((String)request.getAttribute(WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ANAGRAFE))){
				    displayDocanagrafe = "";
					styleDocanagrafe = "sezioneDatiMeno";
				} else {
				    displayDocanagrafe = "display: none;";
					styleDocanagrafe = "sezioneDatiPiu";
				}
			%>
			<fieldset>
				<legend>
					<a class="<%=styleDocanagrafe %>" id="id_link_docanagrafe" href="javascript:showHidePanel('dettaglioDocumentiAnagrafe', 'id_link_docanagrafe', '<%= WebConstants.CONF_UTENTE_DOCAUTORIZZAZIONE_LIST_DOC_ANAGRAFE %>', '${pageContext.request.contextPath}/images/','div');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.documenti_anagrafe"/>">
						<label for="id_link_docanagrafe"><fmt:message key="label.documenti_anagrafe"/></label>
					</a>
				</legend>
				<div class="form-group" id="dettaglioDocumentiAnagrafe" style="<%=displayDocanagrafe%>;">
					<img src='${pageContext.request.contextPath}/images/spinner.gif'/>
				</div>
				<div id="messaggioErroreDocumentiAnagrafe" class="error_header" style="display: none;"></div>
			</fieldset>
		</form>
	</div>
	<script text="text/javascript">
	
	vbg.ready(() => {
	
		let btnCompleta = document.getElementById('btnCompleta');
		if( ${autorizzazione.modificaBloccata} == true ){
			btnCompleta.style.display = 'none';
		}
		if(btnCompleta) {
			btnCompleta.onclick = function(){
			
				if(confirm("L'atto verrà completato e non sarà modificabile. Continuare?"))
				{
					let idAutorizzazione = ${autorizzazione.id.codice};
					let completa = true;
					
					disableFunctions();
					
					let url = '${pageContext.request.contextPath}/autorizzazioni/ajaxCompletaAutorizzazione.htm?codiceautorizzazione=' + idAutorizzazione + '&completa=' + completa;
					
					fetch(url, {
						cache: 'no-cache'
					}).then( function(response) {
						return response.text();
					}).then( function(data) {
						if( data != 'OK' ){
							throw data;
						}
						enableFunctions();
						location.reload();
					}).catch( function(err) {
						enableFunctions();
						alert(err);
						console.log(err);
					});
				}
			}
		}
		
		let _jmesaUrl='list.htm?codiceautorizzazione='+${autorizzazione.id.codice};
		let _captionTab='<fmt:message key="label.documenti_autorizzazioni.title" />';
		
		/*
		GESTIONE DELLA VISUALIZZAZIONE DELLA TABELLA DEI DOCUMENTI DELL'AUTORIZZAZIONE
		*/
		function visualizzaDocAutorizzazione(codiceoggetto){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxDettaglioDocumentiAutorizzazione.htm?codiceautorizzazione=${autorizzazione.id.codice}&codiceoggettoinserito=' + codiceoggetto;
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				
				document.getElementById('dettaglioDocumentiAutorizzazioni').innerHTML = data;
				document.getElementById('dettaglioDocumentiAutorizzazioni').style.display = 'block';
				
				let bnElimina = document.querySelectorAll('#dettaglioDocumentiAutorizzazioni .togli-doc-autorizzazione');
				for( let i=0; i<bnElimina.length; i++ ){
					bnElimina[i].onclick = function(){
						eliminaRigaDocumentiAutorizzazione(bnElimina[i].dataset.codiceoggetto, bnElimina[i].dataset.idautorizzazione);
			        }
				}
				
				let chkPrincipale = document.querySelectorAll('#dettaglioDocumentiAutorizzazioni [name="principale"]');
				for( let i=0; i<chkPrincipale.length; i++ ){
					chkPrincipale[i].onclick = function(){
						
						let valore = chkPrincipale[i].checked ? chkPrincipale[i].value : '';
						
						impostaDocumentoPrincipale(chkPrincipale[i].dataset.idautorizzazione, valore);
			        }
				}
		    	document.querySelectorAll("#dettaglioDocumentiAutorizzazioni .allegati-tpl").forEach( el => {
		    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
				});
				
			}).catch( function(err) {
				console.log(err);			
			});
		}
		/*
		GESTIONE DELLA VISUALIZZAZIONE DELLA TABELLA DEI DOCUMENTI DELL'ISTANZA, TRAMITE CHIAMATE AJAX
		*/
		function visualizzaDettaglioDocumentiIstanza(codiceDocumento){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxDettaglioDocumento.htm?codice=${autorizzazione.istanza.id.codice}&codiceautorizzazione=${autorizzazione.id.codice}&codiceistanza=${autorizzazione.istanza.id.codice}&codicedocumentoInserito=' + codiceDocumento + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_DOCUMENTIISTANZA%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				
				document.getElementById('dettaglioDocumentiIstanza').innerHTML = data;
				document.getElementById('dettaglioDocumentiIstanza').style.display = 'block';

				let bnAggiungi = document.querySelectorAll('#dettaglioDocumentiIstanza .aggiungi-doc-istanza');
				for( let i=0; i<bnAggiungi.length; i++ ){
					bnAggiungi[i].onclick = function(){
						aggiungiDocumentoIstanza(bnAggiungi[i].dataset.codiceoggetto,bnAggiungi[i].dataset.idautorizzazione,bnAggiungi[i].dataset.iddocumentiistanza,bnAggiungi[i].codiceistanza);
			        }
				}
				
		    	document.querySelectorAll("#dettaglioDocumentiIstanza .allegati-tpl").forEach( el => {
		    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
				});
				
			}).catch( function(err) {
				console.log(err);			
			});			
		}
		/*
		GESTIONE DELLA VISUALIZZAZIONE DELLA TABELLA DEGLI ALLEGATI DEI MOVIMENTI, TRAMITE CHIAMATE AJAX
		*/
		function visualizzaDettaglioAllegatiMovimento(codiceMovimentiallegato){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxDettaglioDocumento.htm?codice=${autorizzazione.movimenti.id.codice}&codiceautorizzazione=${autorizzazione.id.codice}&codiceistanza=${autorizzazione.istanza.id.codice}&codicedocumentoInserito=' + codiceMovimentiallegato + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				
				document.getElementById('dettaglioMovimentiAllegati').innerHTML = data;
				document.getElementById('dettaglioMovimentiAllegati').style.display = 'block';
				
				let bnAggiungi = document.querySelectorAll('#dettaglioMovimentiAllegati .aggiungi-doc-movimenti');
				for( let i=0; i<bnAggiungi.length; i++ ){
					bnAggiungi[i].onclick = function(){
						aggiungiAllegatoMovimento(bnAggiungi[i].dataset.codiceoggetto,bnAggiungi[i].dataset.idautorizzazione,bnAggiungi[i].dataset.idmovimentiallegati,bnAggiungi[i].codiceistanza);
			        }
				}
				
		    	document.querySelectorAll("#dettaglioMovimentiAllegati .allegati-tpl").forEach( el => {
		    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
				});
			}).catch( function(err) {
				console.log(err);			
			});		
			
		}
		/*
		GESTIONE DELLA VISUALIZZAZIONE DELLA TABELLA DEGLI ALLEGATI ISTANZA (ENDOPROCEDIMENTI), TRAMITE CHIAMATE AJAX
		*/
		function visualizzaDettaglioDocumentiEndo(codiceistanzeallegati){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxDettaglioDocumento.htm?codice=' + codiceistanzeallegati + '&codiceautorizzazione=${autorizzazione.id.codice}&codiceistanza=${autorizzazione.istanza.id.codice}&codicedocumentoInserito=' + codiceistanzeallegati + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEALLEGATI%>';

			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				
				document.getElementById('dettaglioIstanzeAllegati').innerHTML = data;
				document.getElementById('dettaglioIstanzeAllegati').style.display = 'block';
				
				let bnAggiungi = document.querySelectorAll('#dettaglioIstanzeAllegati .aggiungi-doc-procedimenti');
				for( let i=0; i<bnAggiungi.length; i++ ){
					bnAggiungi[i].onclick = function(){
						aggiungiDocumentoEndo(bnAggiungi[i].dataset.codiceoggetto,bnAggiungi[i].dataset.idautorizzazione,bnAggiungi[i].dataset.idendoallegati,bnAggiungi[i].codiceistanza);
			        }
				}
				
		    	document.querySelectorAll("#dettaglioIstanzeAllegati .allegati-tpl").forEach( el => {
		    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
				});
			}).catch( function(err) {
				console.log(err);			
			});	
		}
		/*
		GESTIONE DELLA VISUALIZZAZIONE DELLA TABELLA PROCURE, TRAMITE CHIAMATE AJAX
		*/
		function visualizzaDettaglioProcure(codiceIstanzeprocure){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxDettaglioDocumento.htm?codice=' + codiceIstanzeprocure + '&codiceautorizzazione=${autorizzazione.id.codice}&codiceistanza=${autorizzazione.istanza.id.codice}&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEPROCURE%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				
				document.getElementById('dettaglioIstanzeProcure').innerHTML = data;
				document.getElementById('dettaglioIstanzeProcure').style.display = 'block';
				
				let bnAggiungi = document.querySelectorAll('#dettaglioIstanzeProcure .aggiungi-doc-procure');
				for( let i=0; i<bnAggiungi.length; i++ ){
					bnAggiungi[i].onclick = function(){
						aggiungiIstanzaProcura(bnAggiungi[i].dataset.codiceoggetto,bnAggiungi[i].dataset.idautorizzazione,bnAggiungi[i].dataset.idprocura,bnAggiungi[i].codiceistanza);
			        }
				}
				
		    	document.querySelectorAll("#dettaglioIstanzeProcure .allegati-tpl").forEach( el => {
		    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
				});
			}).catch( function(err) {
				console.log(err);			
			});	
		}
		/*
		GESTIONE DELLA VISUALIZZAZIONE DELLA TABELLA DEI DOCUMENTI ANAGRAFE, TRAMITE CHIAMATE AJAX
		*/
		function visualizzaDettaglioDocumentiAnagrafe(codicedocumentoanagrafe){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxDettaglioDocumento.htm?codice='+codicedocumentoanagrafe+'&codiceanagrafe=${autorizzazione.anagrafe.id.codice}&codiceautorizzazione=${autorizzazione.id.codice}&codiceistanza=${autorizzazione.istanza.id.codice}&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_ANAGRAFEDOCUMENTI%>';

			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				
				document.getElementById('dettaglioDocumentiAnagrafe').innerHTML = data;
				document.getElementById('dettaglioDocumentiAnagrafe').style.display = 'block';
				
				let bnAggiungi = document.querySelectorAll('#dettaglioDocumentiAnagrafe .aggiungi-doc-anagrafe');
				for( let i=0; i<bnAggiungi.length; i++ ){
					bnAggiungi[i].onclick = function(){
						aggiungiAnagrafeDocumento(bnAggiungi[i].dataset.codiceoggetto,bnAggiungi[i].dataset.idautorizzazione,bnAggiungi[i].dataset.iddocanagrafe,bnAggiungi[i].codiceistanza);
			        }
				}
				
		    	document.querySelectorAll("#dettaglioDocumentiAnagrafe .allegati-tpl").forEach( el => {
		    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
				});
			}).catch( function(err) {
				console.log(err);			
			});
		}
		
		
		function impostaDocumentoPrincipale(codiceAutorizzazione, codiceOggetto){
			
			disableFunctions();
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxImpostaDocumentoPrincipale.htm?codiceautorizzazione=' + codiceAutorizzazione + '&codiceoggetto=' + codiceOggetto;
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				visualizzaDocAutorizzazione('');
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});
		}
		
    	const getAllegatiTemplate = async (codiceOggetto, el) => {

    		el.innerHTML = await getSnippetOggetto(codiceOggetto,'allegato'+codiceOggetto);
    	}
		
		const getSnippetOggetto = async (codiceOggetto, idElemento) => {
			
			let mostralabel = true;
    		let mostraNomeFile = true;
    		let readonly = true;
    		let mostrastorico = true;
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
		
		function eliminaRigaDocumentiAutorizzazione(codiceOggetto, codiceAutorizzazione){
			disableFunctions();
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxEliminaDocumentoAutorizzazione.htm?codiceoggetto=' + codiceOggetto + '&codiceautorizzazione=' + codiceAutorizzazione;
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				verificaErroreOSuccessoDocAut(data, '');
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});
		}
				
		function verificaErroreOSuccessoDocAut(data, codiceOggetto){
			if(data == "OK"){
				visualizzaDocAutorizzazione(codiceOggetto);
				visualizzaDettaglioDocumentiIstanza('');
				visualizzaDettaglioAllegatiMovimento('');
				visualizzaDettaglioDocumentiEndo('');
				visualizzaDettaglioProcure('');
				visualizzaDettaglioDocumentiAnagrafe('');
				visualizzaMettiAllaFirmaBtn();
			} else {
				jQuery('#messaggioErroreDocumentiAutorizzazione').html(data);
				jQuery('#messaggioErroreDocumentiAutorizzazione').show();
			}
		}
		//-------- FINE DOCUMENTI DELL'AUTORIZZAZIONE
		

		
		//aggiunge il documento dell'istanza selezionato alla tabella documenti autorizzazione
		function aggiungiDocumentoIstanza(codiceOggetto, idautorizzazione, codiceDocumento, codiceistanza){

			disableFunctions();
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxAggiungiDocumento.htm?codiceoggetto=' + codiceOggetto + '&idautorizzazione=' + idautorizzazione + '&codice=' + codiceDocumento + '&tipocodice=<%=WebConstants.DOCAUTORIZZAZIONE_CODICE_DOCUMENTIISTANZA%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				verificaErroreoSuccessoDocumentiistanza(data, codiceOggetto, codiceDocumento);	
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});
		}
		
		function verificaErroreoSuccessoDocumentiistanza(data, codiceoggetto, codicedocumentoistanza){
			if (data == 'OK'){
				visualizzaDocAutorizzazione(codiceoggetto);
				visualizzaDettaglioDocumentiIstanza(codicedocumentoistanza);						
			} else {
				jQuery('#messaggioErroreDocumentiAutorizzazione').html(data);
				jQuery('#messaggioErroreDocumentiAutorizzazione').show();
			}
		}
	
		
		
		//aggiunge l'allegato del movimento selezionato alla tabella documentiautorizzazione
		function aggiungiAllegatoMovimento(codiceOggetto, idautorizzazione, codiceMovimento, codiceIstanza){

			disableFunctions();
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxAggiungiDocumento.htm?codiceoggetto='+codiceOggetto+'&idautorizzazione='+idautorizzazione+'&codice=' + codiceMovimento + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				verificaErroreoSuccessoMovimentiallegati(data, codiceOggetto, codiceMovimento);	
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});
		}
		
		function verificaErroreoSuccessoMovimentiallegati(data, codiceoggetto, codicemovimento){
			if (data == "OK"){
				visualizzaDocAutorizzazione(codiceoggetto);
				visualizzaDettaglioAllegatiMovimento(codicemovimento);
				visualizzaMettiAllaFirmaBtn();
			} else {
				jQuery('#messaggioErroreDocumentiAutorizzazione').html(data);
				jQuery('#messaggioErroreDocumentiAutorizzazione').show();
			}
		}
		

		
		//aggiunge il documento dell'endoprocedimento selezionato alla tabella documentiautorizzazione
		function aggiungiDocumentoEndo(codiceOggetto, codiceAutorizzazione, codiceIstanzaallegato, codiceistanza){
			
			disableFunctions();
		
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxAggiungiDocumento.htm?codiceoggetto=' + codiceOggetto + '&idautorizzazione=' + codiceAutorizzazione + '&codice=' + codiceIstanzaallegato + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEALLEGATI%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				verificaErroreoSuccessoIstanzeallegati(data, codiceOggetto, codiceIstanzaallegato);
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});

		}
		
		function verificaErroreoSuccessoIstanzeallegati(data, codiceoggetto, codiceistanzeallegati){
			if (data == 'OK'){
				visualizzaDocAutorizzazione(codiceoggetto);
				visualizzaDettaglioDocumentiEndo(codiceistanzeallegati);
			} else {
				jQuery('#messaggioErroreDocumentiAutorizzazione').html(data);
				jQuery('#messaggioErroreDocumentiAutorizzazione').show();
			}
		}
	

		
		//aggiunge il documento della procura selezionato alla tabella documentiautorizzazione
		function aggiungiIstanzaProcura(codiceOggetto, codiceAutorizzazione, codiceIstanzaProcure, codiceIstanza){
			
			disableFunctions();
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxAggiungiDocumento.htm?codiceoggetto=' + codiceOggetto + '&idautorizzazione=' + codiceAutorizzazione + '&codice=' + codiceIstanzaProcure + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEPROCURE%>';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				verificaErroreoSuccessoIstanzeProcure(data, codiceOggetto, codiceIstanzaProcure);
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});
		}
		
		function verificaErroreoSuccessoIstanzeProcure(data, codiceoggetto, codiceistanzaprocura){
			if (data == "OK"){
				visualizzaDocAutorizzazione(codiceoggetto);
				visualizzaDettaglioProcure(codiceistanzaprocura);
			} else {
				jQuery('#messaggioErroreDocumentiAutorizzazione').html(data);
				jQuery('#messaggioErroreDocumentiAutorizzazione').show();
			}
		}
		
		//aggiunge il documento dell'anagrafe selezionato alla tabella documentiautorizzazione
		function aggiungiAnagrafeDocumento(codiceOggetto, codiceAutorizzazione,codiceDocumentoAnagrafe, codiceAnagrafe, codiceIstanza){
			
			disableFunctions();
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxAggiungiDocumento.htm?codiceoggetto=' + codiceOggetto + '&idautorizzazione=' + codiceAutorizzazione + '&codice=' + codiceDocumentoAnagrafe + '&tipocodice=<%= WebConstants.DOCAUTORIZZAZIONE_CODICE_ANAGRAFEDOCUMENTI%>';

			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				enableFunctions();
				verificaErroreoAnagrafedocumenti(data, codiceOggetto, codiceDocumentoAnagrafe);
			}).catch( function(err) {
				enableFunctions();
				alert(err);
				console.log(err);			
			});
		}
		
		function verificaErroreoAnagrafedocumenti(data, codiceoggetto, codiceAnagrafeDocumenti) {
			if (data == "OK"){
				visualizzaDocAutorizzazione(codiceoggetto);
				visualizzaDettaglioDocumentiAnagrafe(codiceAnagrafeDocumenti);
			} else {
				jQuery('#messaggioErroreDocumentiAutorizzazione').html(data);
				jQuery('#messaggioErroreDocumentiAutorizzazione').show();
			}
		}
		//-------- FINE DOCUMENTI ANAGRAFE

		
		
		//Chiamata AJAX per permettere la visualizzazione del bottone METTI ALLA FIRMA, nel caso in cui sia presente almeno un 
		//documento dell'autorizzazione collegato ad un allegatomovimento.
		function visualizzaMettiAllaFirmaBtn(){
			
			let url = '${pageContext.request.contextPath}/documentiautorizzazione/ajaxShowMettiAllaFirmaBtn.htm?codiceautorizzazione=${autorizzazione.id.codice}';
			
			fetch(url, {
				cache: 'no-cache'
			}).then( function(response) {
				return response.text();
			}).then( function(data) {	
				showMettiAllaFirmaBtn(data);
			}).catch( function(err) {
				alert(err);
				console.log(err);			
			});
		}
		
		function showMettiAllaFirmaBtn(data){
			
			if (data == 'OK'){
				jQuery('#metti_alla_firma_btn').show();
			} else {
				jQuery('#metti_alla_firma_btn').hide();
			}
			return;
		}
		
		function displayDocuments(){
			visualizzaDocAutorizzazione('');
			visualizzaDettaglioDocumentiIstanza('');
			visualizzaDettaglioAllegatiMovimento('');
			visualizzaDettaglioDocumentiEndo('');
			visualizzaDettaglioProcure('');
			visualizzaDettaglioDocumentiAnagrafe('');
			visualizzaMettiAllaFirmaBtn();
		}
		
		displayDocuments();
	}); 

	async function tabStoricoOggetto(divId, id){
		
		window.vbg.mostraModalCaricamento();
		
		const data = new URLSearchParams();
		data.append('codiceOggetto',id);
		data.append('idElemento', '${param.idElemento}');

		const response = await fetch("${pageContext.request.contextPath}/file/ajaxListaOggettiStorico.htm", {
			method: "POST",
			cache: "no-cache",
			body: data                
		});		
		
		let messaggio = await response.text();
		
		vbg.nascondiModalCaricamento();
		
		if (response.status === 200) {

			let divModalId = 'storico-oggetto-div';
			let divModalIdBody = 'storico-oggetto-div-body';
			let vbgModalOggetti  =  document.getElementById(divModalId);			
			if(!vbgModalOggetti){	
				let modal = document.createElement('div');
				modal.innerHTML = `<vbg-modal id='\${divModalId}'> 
									<div slot='body' id='\${divModalIdBody}'></div>
									</vbg-modal>`;
				document.body.appendChild(modal);
			}
			vbgModalOggetti  =  document.getElementById(divModalId);
			impostaInnerHTMLConScript(document.getElementById(divModalIdBody), messaggio)
			vbgModalOggetti.open();
		}else{				
			alert( messaggio );
		}
	}

	</script>
</body>
</html>