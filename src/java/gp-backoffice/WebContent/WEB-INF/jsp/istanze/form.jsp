<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.IstanzeCommand"%>
<%@page import="org.springframework.security.userdetails.UserDetails"%>
<%@page import="org.springframework.security.Authentication"%>
<%@page
	import="org.springframework.security.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.context.SecurityContext"%>
<%@page
	import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@page
	import="it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento"%>
<%@page
	import="it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl"%>
<%@ page import="java.util.Date"%>
<%@ page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page
	import="it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.archivio_istanze" /></title>
<script type="module"
	src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>"
	defer></script>
<script type="module"
	src="${pageContext.request.contextPath}/scripts/custom-components/vbg-istanza/vbg-dettaglio-istanza.js?<%=vJS %>"
	defer></script>
<script type="text/javascript">
		vbg.ready(() => {
			if(${cartograficoAttivo} === true){
		        let buttonMostraInMappa = document.querySelector('.mostra-mappa');
		        if(buttonMostraInMappa) {
			        buttonMostraInMappa.addEventListener('click',async (e) => {
			            e.preventDefault();
			            window.vbg.mostraModalCaricamento();
			            try
			            {
			                await visualizzaMappa();
			            }
			            catch(error) {
			                console.log(error);
			                alert('Si sono verificati errori durante l\'apertura della mappa: ' + error);
			            }
			            window.vbg.nascondiModalCaricamento();
			        });
		        }
		    }
			
			async function visualizzaMappa(){
	            
	            const response = await fetch('../istanzestradariocartografico/jsonMostraSingolaAttivitaInMappa.htm?idAttivita=${istanzeCommand.entity.attivita.id.codice}&codiceIstanza=${istanzeCommand.entity.id.codice}', {
	                method: 'POST',
	                headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json',
	                }
	            });
	            
	            const jsResponse = await response.json();
	            
	            if(jsResponse.esito.esito == "KO"){
	                throw new Error(jsResponse.esito.exceptions.join(' - '));
	            }
	            
	            
	            if( jsResponse.method === "GET" ){
	                location.replace(jsResponse.url);
	                return;
	            }
	            if( jsResponse.method === "POST" ){
	                const formMappa = document.createElement("form");
	                formMappa.method = "POST";
	                formMappa.action = jsResponse.url;
	                console.log(jsResponse.body);
	                jsResponse.body.forEach(item => {
	                    const input = document.createElement("input");
	                    input.type = "hidden";
	                    input.name = item.chiave;
	                    input.value = item.valore;
	                    formMappa.appendChild(input);
	                });
	                
	                document.body.appendChild(formMappa);
	                formMappa.submit();
	                return;
	            }
	        }
		});
	</script>
</head>
<body>
	<%-- valore di default della position del qrcode, in alcuni casi può essere imposta a un valore differente 
    se sono attive particolari funzionalità.
    Es. se è presente la funzionalità di "assegnazione istruttore" (funz. anti corruzione) il valre verrà sovracsritto  --%>
	<c:set value="80px" var="posizion_qrcode" scope="page" />
	<c:if test="${isUtenteDeveAssegnareIstanza}">
		<c:set value="147px" var="posizion_qrcode" scope="page" />
	</c:if>
	<span class="titoloPagina"> <fmt:message
			key="label.archivio_istanze" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<c:choose>
		<c:when
			test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
			<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../istanze/view" />
				<jsp:param name="qs"
					value="codice%3D${istanzeCommand.entity.id.codice}%26software%3D${istanzeCommand.entity.software.codice}" />
			</jsp:include>
		</c:when>

		<c:otherwise>
			<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../istanze/create" />
			</jsp:include>
		</c:otherwise>
	</c:choose>




	<c:set var="VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI)%></c:set>

	<div id="subcontent">
		<c:if
			test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">

			<jsp:include page="../includes/istanze_funzioni.jsp">
				<jsp:param name="codiceIstanza"
					value="${istanzeCommand.entity.id.codice}" />
				<jsp:param name="isIstanzePage" value="true" />
			</jsp:include>

			<c:if test="${param.info_istanza eq 'on' }">
				<h1>${istanzeCommand.entity.uuid}</h1>
			</c:if>
		</c:if>

		<%
		    pageContext.setAttribute("CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI", request.getAttribute(WebConstants.CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI));
		%>
		<br class="clear" />
		<div id="div_bottoni_istanza_sopra">
			<c:if
				test="${CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI eq 'div_bottoni_istanza_sopra'}">
				<jsp:include page="../includes/bottoniIstanza.jsp">
					<jsp:param name="divId" value="div_bottoni_istanza_sopra" />
				</jsp:include>
			</c:if>
		</div>
		<c:set var="_INSERIMENTO_RAPIDO"
			value="<%=TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_RAPIDO.value()%>"></c:set>
		<spring-form:form commandName="istanzeCommand" name="inviodati"
			id="istanzeForm">

			<spring-form:hidden path="entity.id.codice" />
			<c:if
				test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
				<spring-form:hidden path="entity.operatoreInCarico.id.codice" />

			</c:if>

			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="istanzeCommand" />
			</jsp:include>

			<%-- PANNELLO EVENTI --%>
			<c:if
				test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
				<jsp:include page="../includes/pannelloEventi.jsp">
					<jsp:param name="codIstanza"
						value="${istanzeCommand.entity.id.codice}" />
				</jsp:include>



				<c:if test="${isVerticalizzazioneAUTORIZACCESSIAttiva}">
					<div id="div_operazioni_autorizzazioni"
						style="width: 98%; border: 1px solid black; display: none; padding: 10px;">

					</div>
					<script type="text/javascript">
					jQuery(function(){
						
						
								var url = '${pageContext.request.contextPath}/autorizzazioni/ajaxViewOperazione.htm?codiceIstanza=${istanzeCommand.entity.id.codice}' ;
								
								var ajaxOpts = {
										 
										context: this,
										type: 'POST',
										cache: false,
										success: function(data){
											enableFunctions();
											if(data!==''){
												jQuery("#div_operazioni_autorizzazioni").html(data);
												jQuery("#div_operazioni_autorizzazioni").show();
											}
										}
								}
							disableFunctions();			
							jQuery.ajax(url,ajaxOpts);  						
						});
					</script>

				</c:if>
			</c:if>



			<div style="position: relative">
				<c:if
					test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
					<c:set var="VERTICALIZZAZIONE_VERTICALIZZAZIONE_QRCODE_IN_REQUEST"><%=request.getAttribute(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE)%></c:set>
					<c:if
						test="${VERTICALIZZAZIONE_VERTICALIZZAZIONE_QRCODE_IN_REQUEST eq true}">

						<div
							style="position: absolute; top:${posizion_qrcode}; left: 775px;">
							<a href="javascript:void 0;"
								onclick="dijit.byId('rigeneraRiepilogo_id').show();"><img
								title="Clicca per rigenerare il riepilogo della domanda"
								align="top" id="qrcodeimg"
								src="${pageContext.request.contextPath}/istanze/qrcode.htm?uuid=${istanzeCommand.entity.uuid}" /></a>


							<div id="rigeneraRiepilogo_id" dojoType="dijit.Dialog"
								title="Rigenera riepilogo"
								style="display: none; background-color: #ffffff;">
								<label for="rigeneraRiepilogoIdChk_id"
									style="padding: 10px; font-weight: bold">Si desidera
									salvare il riepilogo tra i documenti della pratica? <input
									type="checkbox" name="rigeneraRiepilogoIdChk"
									id="rigeneraRiepilogoIdChk_id" />
								</label>

								<div id="functions">
									<ul>
										<li><a href="javascript:lanciaDownload();"><fmt:message
													key="button.ok" /></a></li>
										<li><a
											href="javascript:dijit.byId('rigeneraRiepilogo_id').hide();"><fmt:message
													key="button.annulla" /></a></li>
									</ul>
								</div>
								<script type="text/javascript">
						    	
						    		function lanciaDownload(){
						    			
						    			dijit.byId('rigeneraRiepilogo_id').hide();
						    			document.location.href='${pageContext.request.contextPath}/istanze/updateRigeneraRiepilogo.htm?codiceistanza=${istanzeCommand.entity.id.codice}&rigeneraRiepilogo='+jQuery('#rigeneraRiepilogoIdChk_id').prop('checked');
						    		}
						    	</script>
							</div>
						</div>
					</c:if>
				</c:if>


				<table width="100%" border="0">

					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:if test="${not empty pecId}">
							<tr>
								<td colspan="6" style="vertical-align: middle;">L'istanza è
									collegata con le seguenti mail/pec: <c:forEach items="${pecId}"
										var="pec">
										<div
											style="width: 100%; border: 1px dotted maroon; padding-left: 10px; padding-top: 4px; padding-bottom: 4px;">
											inviata da <b>${pec.pecFrom}</b><br /> ricevuta il giorno <b><fmt:formatDate
													value="${pec.pecDate}"
													pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /></b>
											<br />con oggetto <b>${pec.pecSubject}</b> <a
												title="Il movimento è collegato con la pec, cliccare per maggiori dettagli"
												class="vbg-btn btn-info"
												href="javascript:location.href='${pageContext.request.contextPath}/pecinbox/dettaglioPEC.htm?codicePec='+encodeURIComponent('${pec.id.id}')+'&provenienza=ISTANZE';">
											</a>
										</div>
									</c:forEach>
								</td>
							</tr>
						</c:if>

						<c:if
							test="${not empty istanzeCommand.entity.operatoreInCarico.id.codice}">
							<tr>
								<td colspan="6"
									style="width: 100%; border: 2px dotted maroon; padding-left: 10px; padding-top: 4px; padding-bottom: 4px;">
									L'istanza è contrassegnata come presa in carico da <b
									style="font-size: 1.2em">${istanzeCommand.entity.operatoreInCarico.responsabile}</b>
									<a class="eliminaRiga" style="float: none"
									href="javascript:rimuoviPresaInCarico()"
									title="Rimuovi l'assegnazione"> <label>Rimuovi
											l'assegnazione</label>
								</a>
								</td>
							</tr>
						</c:if>

						<c:if
							test="${not empty istanzeCommand.entity.amministrazioni.id.codice}">
							<tr>
								<td colspan="6"
									style="width: 100%; border: 2px dotted maroon; padding-left: 10px; padding-top: 4px; padding-bottom: 4px;">

									<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">

										<jsp:include page="../includes/autocompletergenerico.jsp">
											<jsp:param name="idElemento" value="amministrazioni" />
											<jsp:param name="propertyPath" value="entity.amministrazioni" />
											<jsp:param name="pathPropertyDescription"
												value="entity.amministrazioni.descrizioneEstesa" />
											<jsp:param name="pathPropertyCode"
												value="entity.amministrazioni.id.codice" />
											<jsp:param name="autocompleterAjax"
												value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />
											<jsp:param name="titleKey"
												value="label.ricerca_amministrazione" />
											<jsp:param name="autocompleterInputSize" value="100" />
										</jsp:include>

									</spring-security:authorize> <spring-security:authorize ifNotGranted="ROLE_ADMINISTRATOR">

										<fmt:message key="alberoproc.label.amministrazioni" />
										<b style="font-size: 1.2em">${istanzeCommand.entity.amministrazioni.amministrazione}</b>
										<spring-form:hidden path="entity.amministrazioni.id.codice" />

									</spring-security:authorize>


								</td>
							</tr>
						</c:if>
					</c:if>

					<%--
					SETTORI AVVISI
				 --%>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">




						<c:if test="${fn:length(avvisi)>0 }">
							<tr>
								<td></td>
								<td colspan="5"><c:forEach items="${avvisi}" var="avviso">
										<a
											href="javascript:historySet('${_urlback}','../istanzeattivita/list.htm?codiceIstanza=${istanzeCommand.entity.id.codice }')"
											title="${fn:replace(avviso.settore.settore,'\'','´')}"> <img
											src="../file/ajaxDownload.htm?fileId=${avviso.oggetto.id.codice}"
											border="0"
											title="${fn:replace(avviso.settore.settore,'\'','´')}" />
										</a>
										<br />
									</c:forEach></td>
							</tr>
						</c:if>

						<!--  Sezione assegnazione manuale istruttore da gruppo -->

						<c:if test="${isUtenteDeveAssegnareIstanza}">
							<tr>
								<td style="border: dashed; vertical-align: sub;" colspan="6"><b><fmt:message
											key="label.istanza_da_assegnare_all_istruttore" /></b>
									<div id="functions">
										<ul>
											<li><a
												href="javascript:assegnaIstruttore('ricercaIstruttoriDiv',${istanzeCommand.entity.id.codice},'false')">
													<fmt:message key="label.assegna" />
											</a></li>
										</ul>
									</div> <%-- 
									<a href="javascript:assegnaIstruttore('ricercaIstruttoriDiv',${istanzeCommand.entity.id.codice},'false')">
										<img src="${pageContext.request.contextPath }/images/add.png" />
									</a>
									--%></td>
							</tr>
							<tr>
								<td colspan="6">&nbsp;</td>
							</tr>
						</c:if>

						<div dojoType="dijit.Dialog" id="ricercaIstruttoriDiv"
							style="overflow: inherit;"
							title="<fmt:message key="label.istanza_da_assegnare_all_istruttore" />: ">
							<div dojoType="dijit.layout.ContentPane" class="generic_dialog"
								style="width: 700px; height: 500px;">
								<div id="ricercaIstruttori"></div>
							</div>
						</div>
						<%-- 
						<div dojoType="dijit.Dialog" id="ricercaIstruttoriInteraListaDiv"
							style="overflow: inherit;"
							title="<fmt:message key="label.istanza_da_assegnare_all_istruttore" />: ">
							<div dojoType="dijit.layout.ContentPane" class="generic_dialog"
								style="width: 600px; height: 500px;">
								<div id="ricercaIstruttoriDaInteraLista"></div>
							</div>
						</div>
						--%>


						<!-- END -->
						<c:if test="${isUtenteAssegnatoAdIstanza}">
							<tr>
								<td colspan="6"><b><fmt:message
											key="label.accettazione_ruolo_istruttore" /></b> <a
									class="addColumn"
									href="javascript:accettaIstruttore('accettazioneRuoloIstruttoriDiv',${istanzeCommand.entity.id.codice})">
										<label><fmt:message key="label.add.record.image" /></label>
								</a></td>
							</tr>

							<div dojoType="dijit.Dialog" id="accettazioneRuoloIstruttoriDiv"
								style="overflow: inherit;"
								title="<fmt:message key="label.dichiarazione_di_accettazione" />: ">
								<div dojoType="dijit.layout.ContentPane" class="generic_dialog"
									style="width: 600px; height: 200px;">
									<div id="accettazioneRuoloIstruttore"></div>
								</div>
							</div>
						</c:if>


						<!-- <div id="dialogAssOpe"><div id="dialogAssOpeText"></div></div> -->
						<vbg-modal id="dialogAssOpe">
						<div slot='body' id='dialogAssOpeText'></div>
						<div slot='footer'></div>
						</vbg-modal>
						<vbg-dettaglio-istanza id="vbg-dettaglio-istanza"></vbg-dettaglio-istanza>
						<div id="messaggioAssegnazioneNormale" style="display: none;">Attenzione!
							Confermate la scelta?</div>
						<div id="messaggioAssegnazioneModificata" style="display: none;">Attenzione!
							L'operatore scelto non e' quello calcolato dalla procedura.
							Proseguire?</div>
						<script type="text/javascript">	

document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById('dialogAssOpe').addEventListener('hide', function(e) {
		document.getElementById('dialogAssOpeText').innerHTML='';	
	});
    
});
					
function assegnaOperatore(tipoOperatore, codiceOperatore, check) {
    var messaggio = check
        ? jQuery("#messaggioAssegnazioneModificata").text()
        : jQuery("#messaggioAssegnazioneNormale").text();
    if (confirm(messaggio)) {
        disableFunctions();
        document.location.href =
            "${pageContext.request.contextPath}/istanze/updateOperatore.htm?codiceIstanza=${istanzeCommand.entity.id.codice}&tipo=" +
            tipoOperatore + "&respSorteggiato=" + codiceOperatore + "&check=" + check;
    }
}



  async function dettaglio(codiceResponsabile, idTestata, tipo){			
		
	  console.log(codiceResponsabile, idTestata, tipo);
		let url = "../istanze/ajaxDettaglioOperatori.htm?codiceResponsabile="+codiceResponsabile+"&idTestata="+idTestata+"&tipo="+tipo;
		const response = await fetch(url, {
	        method: "POST",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });
		
		let messaggio = await response.json();
		
		let modal = document.querySelector('#modal-elenco-istanze');
		let righe = modal.querySelector('#elenco_istanze tbody');
	 	messaggio.numeri_istanza.numero_istanza.forEach((el) => {
	 	 	const riga = document.createElement("tr");
	 	 	const cella = document.createElement("td");
	 		cella.innerHTML = `<a href=\"javascript:visualizzaDettaglioIstanza(`+ el.id +`)\">`+el.descrizione+`</a>`;
	 		riga.appendChild(cella);
	 		righe.appendChild(riga);
	 	 }); 		
		modal.open();
	} 
  async function chiusuraGruppo(idTestata){
		console.log("id testata:",idTestata);
		let url = "../istanze/ajaxChiusuraGruppo.htm?idTestata="+idTestata;
		const response = await fetch(url, {
	        method: "POST",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });
		let messaggio = await response.json()
		if (messaggio.result === "OK") {
			console.log("success chiusura per idTestata:",idTestata);
			
		} else {
			console.log("fail chiusura per idTestata:",idTestata);
		}
		document.getElementById('dialogAssOpe').close();

	}
	function chiudiModal(obj){
	
		obj.document.querySelector('#elenco_istanze tbody').innerHTML='';
		obj.document.querySelector('#modal-elenco-istanze').close();
	}
	
	async function visualizzaDettaglioIstanza(codiceIstanza){
		console.log(codiceIstanza);
		let dettaglioIstanza = document.querySelector('#vbg-dettaglio-istanza');
		let response = await fetch('../iattivita/ajaxViewDettagliIStanza.htm?codiceIstanza='+codiceIstanza);
		let messaggio = await response.json();					
		dettaglioIstanza.show(messaggio);
	}

function chiudiAssegnazione() {
	document.querySelector('#dialogAssOpe').close();
	
}

async function assegnazioneOperatori(codiceIstanza, tipoOperatore) {
    let url =
        "${pageContext.request.contextPath}/istanze/ajaxAssegnaOperatori.htm?codiceIstanza=" +
        codiceIstanza + "&tipo=" + tipoOperatore;
    const response = await fetch(url,{
    	method: "POST",
        cache: "no-cache"  
    });
    
    let messaggio = await response.text();
    
    if(response.status === 200){
    	
    	document.querySelector('#dialogAssOpeText').innerHTML = messaggio;
    	document.querySelector('#dialogAssOpe').open();
    }else{
    	
    	console.log(messaggio);
    	document.querySelector('#dialogAssOpeText').innerHTML = messaggio;
    	document.querySelector('#dialogAssOpe').open();
    }
    
               
   /*  var ajaxOpts = {
        context: this,
        type: "POST",
        cache: false,
        success: function (data) {
            enableFunctions();
            document.querySelector('#dialogAssOpeText').open();
            jQuery("#dialogAssOpeText").html(data);
            jQuery("#dialogAssOpe").dialog({
                resizable: false,
                modal: true,
                width: "60%",
                title: "Assegnazione operatori", 
            });
        },
        error: function (data) {
            enableFunctions();
            jQuery("#dialogAssOpeText").html(data.responseText);
            jQuery("#dialogAssOpe").dialog({
                resizable: false,
                modal: true,
                width: "60%",
                title: "Errore",
            });
        },
    };
    disableFunctions();
    jQuery.ajax(url, ajaxOpts); */
}

function assegnaIstruttore(divId, codIstanza, isModifica) {
    dijit.byId(divId).show();
    assegnaIstruttoreTab(codIstanza, isModifica);
}

function assegnaIstruttoreTab(codIstanza, isModifica) {
    var mod = false;
    if (isModifica != "" && isModifica == "true") {
        mod = true;
    }

    new Ajax.Request(
        "${pageContext.request.contextPath}/gruppiistruttori/ajaxCreateRicercaIstruttore.htm?codiceIstanza=" +
        codIstanza +
        "&isModifica=" +
        mod,
        {
            method: "post",
            onSuccess: function (transport) {
                var response = transport.responseText;
                $("ricercaIstruttori").innerHTML = parseAjaxResponse(
                    response,
                    false,
                    true
                );
                parseAjaxResponse(response, true, false);
                applyStyle();
            },
            onFailure: function (transport) {
                var response = transport.responseText;
                alert(response);
            },
        }
    );
}
function assegna(codIstanza) {
    if (document.getElementById("istruttore_id")) {
        var resp = document.getElementById("istruttore_id").value;
        doHref(
            "../gruppiistruttori/assegnaIstruttoreTempAdIstanza.htm?codiceIstanza=" +
            codIstanza +
            "&codiceIstruttore=" +
            resp,
            ""
        );
    }
}

function modifica(codIstanza) {
    if (document.getElementById("istruttore_id")) {
        var resp = document.getElementById("istruttore_id").value;
        doHref(
            "../gruppiistruttori/modificaIstruttoreAdIstanza.htm?codiceIstanza=" +
            codIstanza +
            "&codiceIstruttore=" +
            resp,
            ""
        );
    }
}

function modificaDaInteraLista(codIstanza, id) {
    if (document.getElementById(id)) {
        var resp = document.getElementById(id).value;
        doHref(
            "../gruppiistruttori/modificaIstruttoreAdIstanza.htm?codiceIstanza=" +
            codIstanza +
            "&codiceIstruttore=" +
            resp,
            ""
        );
    }
}

function accettaIstruttore(divId, codIstanza) {
    dijit.byId(divId).show();
    accettaIstruttoreTab(codIstanza);
}

function accetto(id) {
    document.getElementById(id).checked = false;
}

function rigetto(id) {
    document.getElementById(id).checked = false;
}

function accettaOrRigetta(
    codiceIstanza,
    id_radio_button_accetta,
    id_radio_button_rigetta
) {
    var accetta = document.getElementById("radiobox_accetto").checked;
    var rigetta = document.getElementById("radiobox_non_accetto").checked;
    doHref(
        "../gruppiistruttori/accettaOrRigettaAssegnazione.htm?codiceIstanza=" +
        codiceIstanza +
        "&accetta=" +
        accetta +
        "&rigetta=" +
        rigetta,
        ""
    );
}

function accettaIstruttoreTab(codIstanza) {
    new Ajax.Request(
        "${pageContext.request.contextPath}/gruppiistruttori/ajaxAccettazioneruoloIstruttore.htm?codiceIstanza=" +
        codIstanza,
        {
            method: "post",
            onSuccess: function (transport) {
                var response = transport.responseText;
                $("accettazioneRuoloIstruttore").innerHTML = parseAjaxResponse(
                    response,
                    false,
                    true
                );
                parseAjaxResponse(response, true, false);
                applyStyle();
            },
            onFailure: function (transport) {
                var response = transport.responseText;
                alert(response);
            },
        }
    );
}
function assegna(codIstanza) {
    var resp = document.getElementById("istruttore_id").value;
    doHref(
        "../gruppiistruttori/assegnaIstruttoreTempAdIstanza.htm?codiceIstanza=" +
        codIstanza +
        "&codiceIstruttore=" +
        resp,
        ""
    );
}

</script>


					</c:if>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:set var="isReadOnly" scope="page" value="true"></c:set>
					</c:if>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
						<c:set var="isReadOnly" scope="page" value="false"></c:set>
					</c:if>

					<jsp:include page="../includes/comboComuni.jsp">
						<jsp:param name="mostraTutti" value="true" />
						<jsp:param name="emptyLabelTutti" value="true" />
						<jsp:param name="readOnly" value="${isReadOnly}" />
						<jsp:param name="commandPropertyPath" value="entity.comune" />
						<jsp:param name="colspan" value="5" />
						<jsp:param name="comune"
							value="${istanzeCommand.entity.comune.codicecomune}" />
						<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
					</jsp:include>

					<%--
						ATTIVITA'
					 --%>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:set var="VERTICALIZZAZIONE_SIEDER_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIEDER)%></c:set>
						<c:if test="${VERTICALIZZAZIONE_SIEDER_IN_REQUEST eq true}">
							<c:if
								test="${urlStatiPresente eq true or urlStatoPresente eq true}">
								<tr class="titoloSezione">
									<td colspan="6"><label>Funzionalità SIEDER</label></td>
								</tr>
								<tr>

									<td colspan="6">

										<div id="functions">
											<ul>
												<li><c:if test="${urlStatoPresente eq true}">
														<a href="javascript:verificaStatoSieder()" title="">Verifica
															lo stato istanza</a>
													</c:if> <c:if test="${urlStatiPresente eq true}">
														<a href="javascript:statiAmmissibiliSieder()" title="">Verifica
															gli stati ammissibili</a>
													</c:if> <c:if test="${urlModStatiPresente eq true}">
														<a href="javascript:cambioStatoSieder()" title="">Comunica
															la ricezione</a>
													</c:if></li>
											</ul>
										</div> <script type="text/javascript">
							
							function chiudiDivSieder(){
								siederDLG.hide();
							}
							var siederDLG = new dijit.Dialog({
					            title: "Funzioni SIEDER" ,
					            style: "overflow:auto; width: 700px; height:180px;"
					        });
							
							function verificaStatoSieder(){
								disableFunctions();
								jQuery.ajax({
									  url: "<%=request.getContextPath()%>/istanze/ajaxStatoIstanzaSieder.htm",
									  data: { codiceIstanza: ${istanzeCommand.entity.id.codice}},
									  cache: false,
									  dataType: "html"
									}).done(function( html ) {
										enableFunctions();
										siederDLG.attr("content", html);
										siederDLG.show();	
									});											
							}
							function cambioStatoSieder(){
								disableFunctions();
								jQuery.ajax({
									  url: "<%=request.getContextPath()%>/istanze/ajaxCambioStatoSieder.htm",
									  data: { codiceIstanza: ${istanzeCommand.entity.id.codice} },
									  cache: false,
									  dataType: "html"
									}).done(function( html ) {
										enableFunctions();
										siederDLG.attr("content", html);
										siederDLG.show();	
									});											
							}
							function statiAmmissibiliSieder(){
								disableFunctions();
								jQuery.ajax({
									  url: "<%=request.getContextPath()%>/istanze/ajaxStatiIstanzaSieder.htm",
									  data: { codiceIstanza: ${istanzeCommand.entity.id.codice}},
									  cache: false,
									  dataType: "html"
									}).done(function( html ) {
										enableFunctions();
										siederDLG.attr("content", html);
										siederDLG.show();	
									});	
							}
							</script>
									</td>
								</tr>
							</c:if>
						</c:if>


						<c:set var="VERTICALIZZAZIONE_I_ATTIVITA_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA)%></c:set>
						<!-- §§§BEGIN§§§ -->

						<c:if test="${VERTICALIZZAZIONE_I_ATTIVITA_IN_REQUEST eq true}">
							<tr class="titoloSezione">
								<td colspan="6"><init:editLabel key="label.i_attivita"
										role="ROLE_EDITLABEL" /></td>
							</tr>
							<tr>
								<td><init:editLabel key="label.denominazione_attivita"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell" colspan="5"><c:if
										test="${empty istanzeCommand.entity.attivita.id.codice}">
										<c:if test="${istanzeCommand.entity.azione eq '+'}">
											<c:set var="title_crea_attivita">
												<fmt:message key="label.crea_attivita_da_istanza" />
											</c:set>
											<c:set var="label_crea_attivita">
												<fmt:message key="label.crea" />
											</c:set>
											<%--
													<c:if test="${not empty forzaCreazioneAttivita}">
														<c:set var="title_crea_attivita"><fmt:message key="label.forza_crea_attivita_da_istanza"/></c:set>
														<c:set var="label_crea_attivita"><fmt:message key="label.forza_creazione"/></c:set>
													</c:if>
													 --%>
											<c:if test="${isModificaIstanza eq true}">
												<a href="javascript:nuovaAttivita(false)"
													title="${title_crea_attivita}">${label_crea_attivita}&#x00BB;</a>
											</c:if>
										</c:if>
										<a href="javascript:cercaAttivita()"
											title="<fmt:message key="label.cerca_attivita_da_collegare"/>"><fmt:message
												key="label.cerca" />&#x00BB;</a>
									</c:if> <spring-form:hidden path="entity.attivita.id.codice" /> <a
									href="javascript:mostraAttivita();" id="attivitaHrefId"
									title="<fmt:message key="label.mostra_attivita" />">${istanzeCommand.entity.attivita.denominazione}</a>


									<c:if test="${cartograficoAttivo eq true}">
										<a style="float: none;" href=""
											title="<fmt:message key="label.cartografico.aprimappa" />">
											<i class="fa fa-map-marked-alt fa-lg mostra-mappa"></i>
										</a>
									</c:if>
									<div class="dijitHidden">
										<div dojoType="dijit.Tooltip" connectId="attivitaHrefId"
											position="after">
											<b> <c:if
													test="${istanzeCommand.entity.attivita.attiva eq true}">
													<fmt:message key="label.attiva" />
												</c:if> <c:if
													test="${istanzeCommand.entity.attivita.attiva ne true}">
													<fmt:message key="label.non_attiva" />
												</c:if> - <c:if
													test="${istanzeCommand.entity.attivita.operante eq true}">
													<fmt:message key="label.operante" />
												</c:if> <c:if
													test="${istanzeCommand.entity.attivita.operante ne true}">
													<fmt:message key="label.non_operante" />
												</c:if>
											</b>
										</div>
									</div> <c:if
										test="${not empty istanzeCommand.entity.attivita.id.codice}">
										<a style="float: none" href="javascript:scollegaAttivita()"
											title="<fmt:message key="label.scollega_istanza" />"> <i
											class="far fa-times-circle fa-lg"
											style="color: var(- -accent-color)"></i>
										</a>
									</c:if> <c:if test="${not empty forzaCreazioneAttivita}">
										<div dojoType="dijit.Dialog" id="altreAttivitaDialogDiv"
											title="<fmt:message key="label.altre_attivita" />">
											<div dojoType="dijit.layout.ContentPane"
												class="generic_dialog">
												<div class="error_header" style="width: 650px;">${ DescrizioneEccezioneCreazioneAttivita }
												</div>
												<div id="functions">
													<ul>
														<li><a href="javascript:nuovaAttivita(true)"
															title="<fmt:message key="label.forza_crea_attivita_da_istanza"/>"><fmt:message
																	key="label.forza_creazione" />&#x00BB;</a></li>
													</ul>
												</div>

												<div style="clear: both;" id="listaAltreAttivitaDialogDiv"></div>
											</div>
										</div>
									</c:if> <script type="text/javascript">
											
											
											<c:if test="${not empty forzaCreazioneAttivita}">
												
												jQuery(document).ready(function () {
													disableFunctions();
													pannelloIattivitaEsistente(${istanzeCommand.entity.id.codice},'${TipoEccezioneCreazioneAttivita}');												
												});
											
											</c:if>
											
											
											function pannelloIattivitaEsistente(codiceistanza,tipoEccezione){
												
												var jqxhr = jQuery.ajax({
													  url: "ajaxVisualizzaAttivitaEsistenti.htm",
													  context: document.body,
													  cache: false,				
													  dataType: "html",
													  data: "codiceistanza="+codiceistanza+"&tipoEccezione="+tipoEccezione,
													  success: function(dataResult) {
														  enableFunctions();
													  		jQuery('#listaAltreAttivitaDialogDiv').html(dataResult);
													  		dijit.byId('altreAttivitaDialogDiv').show();
			 										  },
													  error: function(dataError){		
														  enableFunctions();
														  jQuery('#listaAltreAttivitaDialogDiv').html(dataError);	
														  dijit.byId('altreAttivitaDialogDiv').show();
													  }	
													});		
											}
											
											function mostraAttivita()
											{
												historySet('${_urlback}','../iattivita/view.htm?codice=${istanzeCommand.entity.attivita.id.codice}','');
											}
											function cercaAttivita()
											{									
												historySet('${_urlback}','../iattivita/search.htm?codiceIstanza=${istanzeCommand.entity.id.codice}&pSoftware=${istanzeCommand.entity.software.codice}','');
											}
											function scollegaAttivita()
											{
												if(confirm('<fmt:message key="javascript.confirm.scollegare_attivita" />')){
													doSubmit('scollegaAttivita.htm','', document.inviodati);
												}
											}
											function nuovaAttivita(forzaCreazioneAttivita)
											{
												if(confirm('<fmt:message key="javascript.confirm.creare_attivita" />')){
													doSubmit('creaAttivita.htm?skipCheckEsistenzaAttivita='+((forzaCreazioneAttivita==true)?'true':''),'', document.inviodati);
												}
											}
											</script></td>
							</tr>
						</c:if>

						<!-- §§§END§§§ -->
					</c:if>
					<tr class="titoloSezione">
						<td colspan="6"><init:editLabel role="ROLE_EDITLABEL"
								key="label.dati_istanza" /> <%-- Start Inserimento rapido mostra tutti --%>
							&nbsp; <c:if
								test="${istanzeCommand.tipoInserimento eq _INSERIMENTO_RAPIDO and istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
								<c:set var="ir_mostratutti" value="0" />
								<c:set var="checked_ir" value="" />
								<c:if test="${ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI eq '1'}">
									<c:set var="ir_mostratutti" value="1" />
									<c:set var="checked_ir" value=" checked='checked' " />
								</c:if>
								<input type="checkbox"
									id="ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI_id"
									onclick="salvaPreferenza('<%=WebConstants.ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI%>',this)"
									${checked_ir} />
								<label style="font-size: 13px; font-weight: normal;"
									for="ISTANZE_INSERIMENTO_RAPIDO_MOSTRATUTTI_id"><fmt:message
										key="label.mostra_tutti" /></label>
								<init:help idHelp="helpMostraTutti" textKey="help.mostra_tutti" />
							</c:if> <script type="text/javascript">			
								function salvaPreferenza(nomeparametro, objchk){
									var valore = "0";	
									if(objchk.checked==true){
										valore="1";	
									}
									saveUserPreference(nomeparametro, valore);
									setTimeout('document.location.reload()',300);
								}
							</script> <%-- End Inserimento rapido mostra tutti --%></td>
					</tr>
					<tr>
						<td style="width: 200px;"><label class="required">*</label> <init:editLabel
								role="ROLE_EDITLABEL" key="label.numeroistanza" /></td>
						<td colspan="3" class="inline-ui-cell"><c:if
								test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
								<c:if
									test="${configurazione.flagNoNumeroistanza eq false or empty configurazione.flagNoNumeroistanza}">
									<spring-form:input cssClass="inputRed"
										id="entity_numeroistanza_id" path="entity.numeroistanza"
										size="40" maxlength="60" />
								</c:if>
								<c:if test="${configurazione.flagNoNumeroistanza eq true }">
									<spring-form:input cssClass="inputRed"
										id="entity_numeroistanza_id" path="entity.numeroistanza"
										size="40" readonly="true" />
								</c:if>
							</c:if> <c:if
								test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
								<span id="label_numeroistanza"><input type="text"
									class="inputRed" id="_entity_numeroistanza_id"
									readonly="readonly" name="_entity.numeroistanza" size="40"
									value="${istanzeCommand.entity.numeroistanza}" /></span>
								<span id="input_numeroistanza" style="display: none;"><spring-form:input
										cssClass="inputRed" id="entity_numeroistanza_id"
										path="entity.numeroistanza" size="40" /></span>
							</c:if> <spring-form:errors path="entity.numeroistanza" cssClass="error" />
							<c:if
								test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
								<c:if test="${responsabileInRequest.flagModificaNumist eq true}">
									<c:if test="${isModificaIstanza eq true}">
										&nbsp;&nbsp;
										<a id="link_modifica_id"
											href="javascript:modificaNumeroIstanza();"
											title="<fmt:message key="label.modifica_numero_istanza"/>">
											<fmt:message key="label.modifica" />&#x00BB;
										</a>
										&nbsp;
										<a id="mod_num_ist_id" style="display: none;"
											class="vbg-btn btn-salva"
											href="javascript:salvaNumeroIstanza()"
											title="<fmt:message key="label.salva_numero_istanza" />">
										</a>

										<script type="text/javascript">
											var oldNumeroIstanza = '';
											function modificaNumeroIstanza(){
												var visibile = showHideElement(document.getElementById('mod_num_ist_id'));
												if(visibile){
													oldNumeroIstanza = document.getElementById("entity_numeroistanza_id").value;
													showHideElement(document.getElementById('link_modifica_id'));
													$("label_numeroistanza").style.display='none';
													$("input_numeroistanza").style.display='';
													$("entity_numeroistanza_id").focus();
												}else{
													showHideElement(document.getElementById('link_modifica_id'));
													$("label_numeroistanza").style.display='';
													$("input_numeroistanza").style.display='none';
													// document.getElementById("entity_numeroistanza_id").disabled = true;
												}
											}
											function salvaNumeroIstanza(){
												// chiamata ajax
												var valore = document.getElementById("entity_numeroistanza_id").value;
												new Ajax.Request('ajaxUpdateProprieta.htm', {
													  method: 'post',
													  parameters: {codiceIstanza: ${istanzeCommand.entity.id.codice}, valore: valore, campoDaModificare: 'numeroistanza'},
													  onSuccess: function(transport){
														  var response = transport.responseText;
														  if(response!=''){
														   	alert(response);
														   	document.getElementById("entity_numeroistanza_id").value=oldNumeroIstanza;
														  }else{
														  	alert("operazione avvenuta correttamente");
														  	$('_entity_numeroistanza_id').value=$("entity_numeroistanza_id").value;
														  	modificaNumeroIstanza();
														  	
														  }
													  },
													  onFailure: function(transport){ 
														var response = transport.responseText;
													    alert(response); }						    		 
												} );											
											}
											 
										</script>

									</c:if>

								</c:if>
							</c:if></td>
						<td colspan="2">&nbsp;</td>
					</tr>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:if test="${not empty istanzeCommand.codiceIstanzaOnline }">
							<tr>
								<td><init:editLabel key="label.codice_domanda_online"
										role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"
									style="display: inline-flex;"><input type="text"
									name="codDomOnline" size="50"
									value="${istanzeCommand.codiceIstanzaOnline}"
									disabled="disabled" /> <c:set
										var="VERTICALIZZAZIONE_STC_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_STC)%></c:set>
									<c:if test="${VERTICALIZZAZIONE_STC_IN_REQUEST eq true}">
										<c:if test="${istanzeCommand.entity.creatoDaStc eq true }">
											<c:choose>
												<c:when test="${enableSTCPraticaCollegataLink eq true }">
													<jsp:include page="../includes/funzioni_stc.jsp">
														<jsp:param name="codiceIstanza"
															value="${istanzeCommand.entity.id.codice}" />
														<jsp:param name="funzioneRichiesta"
															value="richiestaPraticaIstanza" />
														<jsp:param name="returnTo" value="${_urlback}" />
													</jsp:include>
												</c:when>
												<c:otherwise>
													<span class="vbg-btn btn-stc-nolink" align="middle"
														title="<fmt:message key="label.pratica_creata_da_stc"/>: <fmt:message key="label.pratica_creata_da_stc_no_praticacollegata"/>"></span>
												</c:otherwise>
											</c:choose>
											<a class="visualizzaDocColumn" target="_blank"
												href="ajaxDownloadDomandaStc.htm?codiceIstanza=${istanzeCommand.entity.id.codice}"
												title="<fmt:message key="label.scarica_domanda_stc" />">
											</a>
											<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
												<a class="vbg-btn btn-rielabora" href="#"
													title="rielabora mappature"
													onclick="if(confirm('Verranno rielaborate le informazioni della pratica pervenuta. L\'operazione verrà riportata nei log. Procedere?')){document.location.href='rielaboraMappature.htm?codice=${istanzeCommand.entity.id.codice}'}">
												</a>
											</spring-security:authorize>
										</c:if>
									</c:if></td>
							</tr>
						</c:if>
						<c:if test="${isGISLinkActive eq true}">
							<tr>
								<td>&nbsp;</td>
								<td colspan="5" class="inline-ui-cell"><jsp:include
										page="../includes/funzione_call_link.jsp">
										<jsp:param name="link" value="${urlPraticaGis}" />
										<jsp:param name="title" value="richiestaPraticaIstanza" />
										<jsp:param name="view" value="${isGISLinkActive}" />
									</jsp:include> <c:if test="${mostraGestioneAreeLDP eq true}">
										<a class="vbg-btn btn-altreopzioni"
											href="javascript:historySet('${_urlback}','../istanze/visualizzaAreeLDP.htm?codice=${istanzeCommand.entity.id.codice }')"
											title="Gestisci Aree"></a>
									</c:if></td>
							</tr>
						</c:if>
					</c:if>
					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldCodicePraticaTelematica')}">
						<tr>
							<td><init:editLabel key="label.codice_pratica_telematica"
									role="ROLE_EDITLABEL" /></td>
							<td class="inline-ui-cell" colspan="5"><c:set
									var="VERTICALIZZAZIONE_DBINFORMATICA_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_DBINFORMATICA)%></c:set>
								<c:set var="readonlyPOD">false</c:set> <c:choose>
									<c:when
										test="${VERTICALIZZAZIONE_DBINFORMATICA_IN_REQUEST eq true}">
										<c:if
											test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
											<c:if
												test="${not empty istanzeCommand.entity.codicepraticatel}">
												<c:set var="readonlyPOD">true</c:set>
											</c:if>
										</c:if>
									</c:when>
								</c:choose> <c:choose>
									<c:when test="${readonlyPOD eq true }">
										<div style="width: 250px; padding: 4px; border: 1px solid">${ istanzeCommand.entity.codicepraticatel }</div>
										<spring-form:hidden id="entity_codice_pratica_tel_id"
											path="entity.codicepraticatel" />
									</c:when>
									<c:otherwise>
										<spring-form:input id="entity_codice_pratica_tel_id"
											path="entity.codicepraticatel" size="40" />
									</c:otherwise>
								</c:choose> <spring-form:errors path="entity.codicepraticatel"
									cssClass="error" /> <c:choose>
									<c:when
										test="${VERTICALIZZAZIONE_DBINFORMATICA_IN_REQUEST eq true}">
										<%--
										<c:choose>
											<c:when test="${readonlyPOD ne true }">
										--%>
										<a class="vbg-btn btn-dettaglio"
											href="javascript:validaPOD();" title="Valida dati POD"
											id="imgDettaglioPOD"> </a>
										<%--		
											</c:when>											
										</c:choose>
										--%>
										<script type="text/javascript">
									var podDLG = new dijit.Dialog({
							            title: "Validazione dati POD" ,
							            style: "overflow:auto; width: 700px;height:290px;"
							        });
									
									function inserisciDatiPOD(val){
										
										if(val){
											jQuery('#richiedenteIdCodice_hidden').val( jQuery('#anagrafe_pod_id_hidden').val() );
											jQuery('#richiedenteIdCodice').prop('disabled', true);
											jQuery('#entity_codice_pratica_tel_id').prop('readonly', true);
											jQuery('#imgAggiungirichiedenteIdCodice').hide();
											
											jQuery('#richiedenteIdCodice').val( jQuery('#anagrafe_pod_desc_hidden').val() );
											jQuery('#richiedenteIdCodiceLabel').text( jQuery('#anagrafe_pod_desc_hidden').val() );

											if(jQuery('#distributore_pod_id_hidden')){
												if(jQuery('#distributore_pod_id_hidden').val()!=''){
													jQuery('#aziendaIdCodice_hidden').val( jQuery('#distributore_pod_id_hidden').val() );
													jQuery('#aziendaIdCodice').prop('disabled', true);
													jQuery('#imgAggiungiaziendaIdCodice').hide();
													jQuery('#aziendaIdCodice').val( jQuery('#distributore_pod_desc').val() );
													jQuery('#aziendaIdCodiceLabel').text( jQuery('#distributore_pod_desc').val() );
												}
											}
											
										}else{
											//alert("dati non corretti");
										}
										podDLG.hide();
									}
									function validaPOD(){										
										
										var isView = false;
										<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
											<c:if test="${not empty istanzeCommand.entity.codicepraticatel}">
												// isView = true;
											</c:if>
										</c:if>
										
										var codicePraticaTel = jQuery('#entity_codice_pratica_tel_id').val();
										if(codicePraticaTel && codicePraticaTel !='' ){				
											disableFunctions();
											
											jQuery.ajax({
												  url: "<%=request.getContextPath()%>/anagrafe/ajaxValidaDatiPOD.htm",
												  data: { codicePraticaTel: codicePraticaTel, isView : isView},
												  cache: false,
												  dataType: "html"
												}).done(function( html ) {
													enableFunctions();
													podDLG.attr("content", html);
													podDLG.show();	
												});											
											}										
									}
									
									</script>
									</c:when>
								</c:choose></td>
						</tr>
					</c:if>
					<c:if
						test="${istanzeCommand.tipoInserimento ne _INSERIMENTO_RAPIDO or ir_mostratutti eq '1'}">
						<c:if test="${isArchiviopraticheVisible eq true }">
							<tr>
								<td><init:editLabel key="label.archivio_pratiche"
										role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><jsp:include
										page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="archiviopratiche_id" />
										<jsp:param name="propertyPath"
											value="entity.tipiarchivioistanza" />
										<jsp:param name="pathPropertyDescription"
											value="entity.tipiarchivioistanza.archivio" />
										<jsp:param name="pathPropertyCode"
											value="entity.tipiarchivioistanza.id.codice" />
										<jsp:param name="autocompleterAjax"
											value="findTipiarchivioistanze.htm" />
										<jsp:param name="titleKey"
											value="label.ricerca_tipiarchivioistanze" />
									</jsp:include></td>
							</tr>
						</c:if>
					</c:if>
					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldPosizioneArchivio')}">
						<tr>
							<td><init:editLabel role="ROLE_EDITLABEL"
									key="label.posizione_in_archivio" /></td>
							<td colspan="5" class="inline-ui-cell"><spring-form:input
									id="posizionearchivio_id" path="entity.posizionearchivio"
									size="40" /> <spring-form:errors
									path="entity.posizionearchivio" cssClass="error" /></td>
						</tr>
					</c:if>
					<c:if
						test="${istanzeCommand.tipoInserimento ne _INSERIMENTO_RAPIDO  or ir_mostratutti eq '1'}">
						<c:if test="${isTipologiaistanzaVisible eq true }">
							<c:if
								test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldTipologiaIstanza')}">
								<tr>
									<td><init:editLabel key="label.tipologia_istanza"
											role="ROLE_EDITLABEL" /></td>
									<td colspan="5" class="inline-ui-cell"><jsp:include
											page="../includes/autocompletergenerico.jsp">
											<jsp:param name="idElemento" value="tipologia_istanze_id" />
											<jsp:param name="propertyPath"
												value="entity.tipologiaistanza" />
											<jsp:param name="pathPropertyDescription"
												value="entity.tipologiaistanza.tiDescrizione" />
											<jsp:param name="pathPropertyCode"
												value="entity.tipologiaistanza.id.codice" />
											<jsp:param name="autocompleterAjax"
												value="findTipologiaistanze.htm" />
											<jsp:param name="titleKey"
												value="label.ricerca_tipologiaistanze" />
										</jsp:include></td>
								</tr>
							</c:if>
						</c:if>
					</c:if>
					<c:if
						test="${istanzeCommand.tipoInserimento ne _INSERIMENTO_RAPIDO  or ir_mostratutti eq '1'}">
						<tr>
							<td><label class="required">*</label> <init:editLabel
									key="label.operatore" role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><jsp:include
									page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="responsabile_id" />
									<jsp:param name="propertyPath" value="entity.responsabile" />
									<jsp:param name="pathPropertyDescription"
										value="entity.responsabile.responsabile" />
									<jsp:param name="pathPropertyCode"
										value="entity.responsabile.id.codice" />
									<jsp:param name="autocompleterAjax"
										value="findResponsabili.htm" />
									<jsp:param name="titleKey" value="label.ricerca_responsabile" />
								</jsp:include></td>
						</tr>
					</c:if>

					<tr>
						<td style="vertical-align: top;"><label class="required">*</label>
							<init:editLabel key="label.richiedente" role="ROLE_EDITLABEL" /></td>
						<td colspan="5" class="inline-ui-cell"><c:if
								test="${istanzeCommand.displayMode ne istanzeCommand.displayConstants.VIEW}">
								<jsp:include page="../includes/anagraficasearch.jsp">
									<jsp:param name="idElemento" value="richiedenteIdCodice" />
									<jsp:param name="pathAnagrafica" value="entity.richiedente" />
									<jsp:param name="anagrafeAutocompleterAjax"
										value="findAnagrafe.htm?tipoAnagrafe=${configurazioneRichiedentepf}" />
									<jsp:param name="tiposoggetto"
										value="${configurazioneRichiedentepf}" />
									<jsp:param name="codAnagrafeStorico"
										value="${istanzeCommand.entity.richiedentestorico.id.codice}" />
									<jsp:param name="descrizioneAnagrafeStorico"
										value="${istanzeCommand.entity.richiedentestorico.descrizioneRichiedente}" />
									<jsp:param name="dataAnagrafeStorico"
										value="${istanzeCommand.entity.richiedentestorico.datafinevalidita}" />
									<jsp:param name="codiceIstanza"
										value="${istanzeCommand.entity.id.codice}" />
									<jsp:param name="isInUpdate"
										value="${istanzeCommand.entity.id.codice}" />
									<jsp:param name="listaElementiQsAggiuntiva"
										value="entity_codice_pratica_tel_id" />
								</jsp:include>

							</c:if> <c:if
								test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
								<c:choose>
									<c:when
										test="${VERTICALIZZAZIONE_DBINFORMATICA_IN_REQUEST eq true}">
										<input type="hidden" name="entity.richiedente.id.codice"
											id="richiedenteIdCodice_hidden"
											value="${istanzeCommand.entity.richiedente.id.codice}" />
										<div style="width: 550px; padding: 4px; border: 1px solid"
											id="richiedenteIdCodiceLabel">${istanzeCommand.entity.richiedente.descrizioneRichiedente}</div>
									</c:when>
									<c:otherwise>

										<jsp:include page="../includes/anagraficasearch.jsp">
											<jsp:param name="idElemento" value="richiedenteIdCodice" />
											<jsp:param name="pathAnagrafica" value="entity.richiedente" />
											<jsp:param name="anagrafeAutocompleterAjax"
												value="findAnagrafe.htm?tipoAnagrafe=${configurazioneRichiedentepf}" />
											<jsp:param name="tiposoggetto"
												value="${configurazioneRichiedentepf}" />
											<jsp:param name="codAnagrafeStorico"
												value="${istanzeCommand.entity.richiedentestorico.id.codice}" />
											<jsp:param name="descrizioneAnagrafeStorico"
												value="${istanzeCommand.entity.richiedentestorico.descrizioneRichiedente}" />
											<jsp:param name="dataAnagrafeStorico"
												value="${istanzeCommand.entity.richiedentestorico.datafinevalidita}" />
											<jsp:param name="codiceIstanza"
												value="${istanzeCommand.entity.id.codice}" />
											<jsp:param name="isInUpdate"
												value="${istanzeCommand.entity.id.codice}" />
											<jsp:param name="listaElementiQsAggiuntiva"
												value="entity_codice_pratica_tel_id" />
										</jsp:include>

									</c:otherwise>
								</c:choose>

							</c:if></td>
					</tr>
					<c:choose>
						<c:when test="${configurazione.flagAziendarappresentata eq true }">
							<c:if
								test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeQualitaDi')}">
								<tr>
									<td><init:editLabel key="label.in_qualita_di"
											role="ROLE_EDITLABEL" /></td>
									<td colspan="5" class="inline-ui-cell"><script
											type="text/javascript">
											function setFieldTipisoggetto(inputField, listItem){
													var a = listItem.id;
													//array che contiene l'id dei campi separati da '#'
													//il primo valore è l'id della tabella tipisogegtto e il secondo se deve mostrare o meno la descrizione del soggetto
													var arrayValori=a.split('#');
													document.getElementById('tipisoggetto_id_id').value = inputField.value;
													document.getElementById('tipisoggetto_id_hidden').value = arrayValori[0];
													if(arrayValori[1]=='true'){
														$('TR_DESCRSOGGETTO').appear();
													}else{
														$('TR_DESCRSOGGETTO').fade();
													}
											}
										</script> <jsp:include page="../includes/autocompletergenerico.jsp">
											<jsp:param name="idElemento" value="tipisoggetto_id" />
											<jsp:param name="propertyPath" value="entity.tipisoggetto" />
											<jsp:param name="pathPropertyDescription"
												value="entity.tipisoggetto.tiposoggetto" />
											<jsp:param name="pathPropertyCode"
												value="entity.tipisoggetto.id.codice" />
											<jsp:param name="autocompleterAjax"
												value="findTipisoggettoAndSpecificadescrizione.htm?flagQualita=true" />
											<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
											<jsp:param name="afterUpdateElement"
												value="setFieldTipisoggetto" />
										</jsp:include></td>
								</tr>
								<c:set var="displayDescrSoggetto" value="display: none;" />

								<c:if
									test="${istanzeCommand.entity.tipisoggetto.flgSpecificadescrizione eq true}">
									<c:set var="displayDescrSoggetto" value="" />
								</c:if>

								<tr id="TR_DESCRSOGGETTO" style="${displayDescrSoggetto}">
									<td>&nbsp;</td>
									<td colspan="5" class="inline-ui-cell"><spring-form:input
											path="entity.descrsoggetto" size="70" /> <spring-form:errors
											path="entity.descrsoggetto" cssClass="error" /> &nbsp;<fmt:message
											key="label.specificare_la_tipologia_di_soggetto" /></td>
								</tr>
							</c:if>
							<tr>
								<td style="vertical-align: top;"><init:editLabel
										key="label.ragione_sociale" role="ROLE_EDITLABEL" /></td>
								<td colspan="5"><c:if
										test="${istanzeCommand.displayMode ne istanzeCommand.displayConstants.VIEW}">
										<jsp:include page="../includes/anagraficasearch.jsp">
											<jsp:param name="idElemento" value="aziendaIdCodice" />
											<jsp:param name="pathAnagrafica"
												value="entity.titolarelegale" />
											<jsp:param name="anagrafeAutocompleterAjax"
												value="findAnagrafe.htm?tipoAnagrafe=G" />
											<jsp:param name="tiposoggetto" value="G" />
											<jsp:param name="codAnagrafeStorico"
												value="${istanzeCommand.entity.titolarelegalestorico.id.codice}" />
											<jsp:param name="descrizioneAnagrafeStorico"
												value="${istanzeCommand.entity.titolarelegalestorico.descrizioneRichiedente}" />
											<jsp:param name="dataAnagrafeStorico"
												value="${istanzeCommand.entity.titolarelegalestorico.datafinevalidita}" />
											<jsp:param name="codiceIstanza"
												value="${istanzeCommand.entity.id.codice}" />
											<jsp:param name="isInUpdate"
												value="${istanzeCommand.entity.id.codice}" />
										</jsp:include>
									</c:if> <c:if
										test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
										<c:choose>
											<c:when
												test="${VERTICALIZZAZIONE_DBINFORMATICA_IN_REQUEST eq true}">
												<input type="hidden" name="entity.titolarelegale.id.codice"
													id="aziendaIdCodice_hidden"
													value="${istanzeCommand.entity.titolarelegale.id.codice}" />
												<div style="width: 550px; padding: 4px; border: 1px solid"
													id="aziendaIdCodiceLabel">${istanzeCommand.entity.titolarelegale.descrizioneRichiedente}</div>
											</c:when>
											<c:otherwise>
												<jsp:include page="../includes/anagraficasearch.jsp">
													<jsp:param name="idElemento" value="aziendaIdCodice" />
													<jsp:param name="pathAnagrafica"
														value="entity.titolarelegale" />
													<jsp:param name="anagrafeAutocompleterAjax"
														value="findAnagrafe.htm?tipoAnagrafe=G" />
													<jsp:param name="tiposoggetto" value="G" />
													<jsp:param name="codAnagrafeStorico"
														value="${istanzeCommand.entity.titolarelegalestorico.id.codice}" />
													<jsp:param name="descrizioneAnagrafeStorico"
														value="${istanzeCommand.entity.titolarelegalestorico.descrizioneRichiedente}" />
													<jsp:param name="dataAnagrafeStorico"
														value="${istanzeCommand.entity.titolarelegalestorico.datafinevalidita}" />
													<jsp:param name="codiceIstanza"
														value="${istanzeCommand.entity.id.codice}" />
													<jsp:param name="isInUpdate"
														value="${istanzeCommand.entity.id.codice}" />
												</jsp:include>
											</c:otherwise>
										</c:choose>
									</c:if></td>
							</tr>

						</c:when>
						<c:otherwise>
							<spring-form:hidden path="entity.tipisoggetto.id.codice" />
							<spring-form:hidden path="entity.descrsoggetto" />
							<spring-form:hidden path="entity.titolarelegale.id.codice" />
						</c:otherwise>
					</c:choose>
					<c:if
						test="${istanzeCommand.tipoInserimento ne _INSERIMENTO_RAPIDO  or ir_mostratutti eq '1'}">
						<c:if
							test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeTecnico')}">
							<tr>
								<td style="vertical-align: top;"><init:editLabel
										role="ROLE_EDITLABEL" key="label.tecnico" /></td>
								<td colspan="5" class="inline-ui-cell"><jsp:include
										page="../includes/anagraficasearchtecnico.jsp">
										<jsp:param name="idElemento" value="tecnicoIdCodice" />
										<jsp:param name="pathAnagrafica" value="entity.professionista" />
										<jsp:param name="statoAnagrafe" value="ACTIVE" />
										<jsp:param name="codAnagrafeStorico"
											value="${istanzeCommand.entity.professionistastorico.id.codice}" />
										<jsp:param name="descrizioneAnagrafeStorico"
											value="${istanzeCommand.entity.professionistastorico.descrizioneRichiedente}" />
										<jsp:param name="dataAnagrafeStorico"
											value="${istanzeCommand.entity.professionistastorico.datafinevalidita}" />
										<jsp:param name="codiceIstanza"
											value="${istanzeCommand.entity.id.codice}" />
										<jsp:param name="isInUpdate"
											value="${istanzeCommand.entity.id.codice}" />
									</jsp:include></td>
							</tr>
						</c:if>
						<c:if test="${isTipiSoggMostraDettIstanza}">
							<%
							    String displaySoggColl = "display:none;";
														String styleSoggColl = "";
														//gestisce la visualizzazione della tabella normative
														if (((String) request.getAttribute(WebConstants.CONF_UTENTE_MOSTRA_SOGG_COLL_ISTANZA)).equals("1")) {
														    displaySoggColl = "";
														    styleSoggColl="sezioneDatiMeno";
														} else {
														    displaySoggColl = "display:none;";
														    styleSoggColl="sezioneDatiPiu";
														}
							%>
							<tr class="titoloSezione">
								<td colspan="6"><a class="<%=styleSoggColl%>"
									id="id_sogg_coll"
									href="javascript:showHidePanelBase('id_sogg_coll_table', 'id_sogg_coll', '<%=WebConstants.CONF_UTENTE_MOSTRA_SOGG_COLL_ISTANZA %>', '${pageContext.request.contextPath}/images/','tr','true');"
									title="<fmt:message key="label.mostra_nasconde_sezione" /> ">
										<label for="id_link_parametri_prot"><fmt:message
												key="label.soggetti_collegati" /></label>

								</a></td>
							</tr>
							<tr id="id_sogg_coll_table" style="<%=displaySoggColl%>">
								<td colspan="6">
									<div class="jmesa" id="id_">
										<table border="0" cellpadding="2" cellspacing="0"
											class="table" width="100%">
											<thead>
												<tr class="header">
													<td style="text-align: center;"><init:editLabel
															key="label.soggetto_anagrafica" role="ROLE_EDITLABEL" /></td>
													<td style="text-align: center;"><init:editLabel
															key="label.tipo_soggetto" role="ROLE_EDITLABEL" /></td>
													<td style="text-align: center;"><init:editLabel
															key="label.ragione_sociale" role="ROLE_EDITLABEL" /></td>
													<td style="text-align: center;"><init:editLabel
															key="label.procuratore" role="ROLE_EDITLABEL" /></td>
													<td style="text-align: center;"><init:editLabel
															key="label.azioni" role="ROLE_EDITLABEL" /></td>
												</tr>
											</thead>
											<tbody>
												<%
												    int j=1;
												%>
												<c:forEach items="${istanzerichiedentis}"
													var="istanzerichiedenti">
													<tr colspan="6" class="<%=(j%2)==0?"odd":"even"%>">
														<td>${istanzerichiedenti.richiedente.descrizioneRichiedente}</td>
														<td>${istanzerichiedenti.tiposoggetto.tiposoggetto}<c:if
																test="${not empty istanzerichiedenti.descrsoggetto}">
													( ${istanzerichiedenti.descrsoggetto} )
												    </c:if>
														</td>
														<td>${istanzerichiedenti.anagrafeCollegata.descrizioneRichiedente}</td>
														<td>${istanzerichiedenti.procuratore.descrizioneRichiedente}</td>
														<td><a class="dettaglioColumn"
															href="javascript:historySet('${_urlback}','../istanzerichiedenti/view.htm?codice=${istanzerichiedenti.id.codice}','')"
															title="<fmt:message key="label.azioni" /> "> <label><fmt:message
																		key="label.azioni" /></label>
														</a></td>
													</tr>
													<%
													    j++;
													%>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</td>
							</tr>
							<tr id="id_sogg_coll_table" style="<%=displaySoggColl%>">
								<td colspan="6"><a class="addColumn" style="float: left;!"
									href="javascript:historySet('${_urlback}','../istanzerichiedenti/create.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','')"
									title="<fmt:message key="label.aggiungi" /> "> <label><fmt:message
												key="label.aggiungi" /></label>
								</a></td>
							</tr>
							<tr style="padding-bottom: 6px;!" class="titoloSezione">
								<td colspan="6"></td>
							</tr>
						</c:if>
						<c:if
							test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeDomicilioElettronico')}">
							<tr>
								<td><init:editLabel key="label.domicilio_elettronico"
										role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><c:set
										var="domicilioElettronicoStyle" value="" /> <c:if
										test="${not empty istanzeCommand.entity.domicilioElettronico}">
										<c:set var="domicilioElettronicoStyle"
											value="background-color: #E6FAE8;" />
									</c:if> <spring-form:input id="domicilioElettronico_id"
										path="entity.domicilioElettronico" size="70"
										cssStyle="font-weight: bolder;${domicilioElettronicoStyle}" />
									<spring-form:errors path="entity.domicilioElettronico"
										cssClass="error" /></td>
							</tr>
						</c:if>
					</c:if>
					<tr>
						<td><init:editLabel key="label.data_presentazione"
								role="ROLE_EDITLABEL" /></td>
						<td class="inline-ui-cell"><spring-form:input
								cssClass="inputRed" id="data_id" path="entity.data" size="10"
								onblur="isValidDate(this,true);" /> <init:calendar
								imagePath="/images/cal.gif" idImage="calData" idInput="data_id"
								textKey="label.calendar" /> <spring-form:errors
								path="entity.data" cssClass="error" delimiter="," /> &nbsp; <input
							type="text" class="inputRed time-input"
							name="entity.oraInserimento"
							value="${istanzeCommand.entity.oraInserimento}" size="6"
							placeholder="HH:MM" onblur="checkTime(this,true)" /></td>
						<c:set var="campoProtocolloObbligatorio"></c:set>
						<c:if test="${protocolloObbligatorio eq true}">
							<c:set var="campoProtocolloObbligatorio">
								<label class="required">*</label>
							</c:set>
						</c:if>

						<c:set var="protocolloreadonly" value="true" />
						<c:if
							test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
							<c:if test="${configurazione.flagProtgenmodif eq true }">
								<c:set var="protocolloreadonly" value="false" />
							</c:if>
						</c:if>
						<c:if
							test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
							<c:if test="${configurazione.flagProtgeninser eq true }">
								<c:set var="protocolloreadonly" value="false" />
							</c:if>
						</c:if>
						<c:set var="VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IN_REQUEST"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)%></c:set>
						<c:choose>
							<c:when
								test="${VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IN_REQUEST eq true}">
								<td class="inline-ui-cell">${campoProtocolloObbligatorio}<init:editLabel
										role="ROLE_EDITLABEL" key="label.numero_protocollo" />
								</td>
								<td class="inline-ui-cell"><spring-form:input
										readonly="${protocolloreadonly}" cssClass="inputRed"
										id="numeroprotocollo_id" path="entity.numeroprotocollo"
										size="10" /> <spring-form:errors
										path="entity.numeroprotocollo" cssClass="error" /></td>
								<td class="inline-ui-cell">${campoProtocolloObbligatorio}<init:editLabel
										role="ROLE_EDITLABEL" key="label.data_protocollo" />
								</td>
								<td class="inline-ui-cell"><spring-form:input
										readonly="${protocolloreadonly}" cssClass="inputRed"
										id="dataprotocollo_id" path="entity.dataprotocollo" size="10"
										onblur="isValidDate(this,true);" /> <init:calendar
										imagePath="/images/cal.gif" idImage="calDataprotocollo"
										idInput="dataprotocollo_id" textKey="label.calendar" /> <spring-form:errors
										path="entity.dataprotocollo" cssClass="error" delimiter="," />
								</td>
							</c:when>
							<c:otherwise>
								<td class="inline-ui-cell">${campoProtocolloObbligatorio}<init:editLabel
										key="label.numero_protocollo" role="ROLE_EDITLABEL" />
								</td>
								<td class="inline-ui-cell"><spring-form:input
										readonly="${protocolloreadonly}" cssClass="inputRed"
										id="numeroprotocollo_id" path="entity.numeroprotocollo"
										size="10" /> <spring-form:errors
										path="entity.numeroprotocollo" cssClass="error" /></td>
								<td class="inline-ui-cell">${campoProtocolloObbligatorio}<init:editLabel
										key="label.data_protocollo" role="ROLE_EDITLABEL" />
								</td>
								<td class="inline-ui-cell"><spring-form:input
										readonly="${protocolloreadonly}" cssClass="inputRed"
										id="dataprotocollo_id" path="entity.dataprotocollo" size="10"
										onblur="isValidDate(this,true);" /> <init:calendar
										imagePath="/images/cal.gif" idImage="calDataprotocollo"
										idInput="dataprotocollo_id" textKey="label.calendar" /> <spring-form:errors
										path="entity.dataprotocollo" cssClass="error" delimiter="," />
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:if
							test="${VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IN_REQUEST eq true}">
							<tr>
								<td></td>
								<td class="inline-ui-cell" style="min-width: 234px;"></td>
								<td colspan="4" class="inline-ui-cell">
									<div id="functions">
										<ul>
											<c:if test="${empty istanzeCommand.entity.numeroprotocollo}">
												<li><a
													href="javascript:historySet('${_urlback }','../protocollazione/create.htm?provenienza=I&codiceIstanza=${istanzeCommand.entity.id.codice}','');"><fmt:message
															key="label.protocollo" /></a></li>
											</c:if>



											<c:if
												test="${not empty istanzeCommand.entity.numeroprotocollo}">
												<%-- ANNULLA PROTOCOLLO --%>
												<%--  ANNULLA PROTOCOLLO VENIVA USATO SOLO CON PROTOCOLLO SIGEPRO E IRIDE MA IRIDE NON LO USA
									<li id="link_annulla_protocollo" style="display: none;">
										<a href="javascript:historySet('${_urlback }','../protocollazione/annullaProtocolloView.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','');"><fmt:message key="label.annulla_protocollo"/></a>
									</li>		
									--%>
												<li id="link_protocollo_annullato" style="display: none;">
													<a class="vbg-btn btn-avvisi" href="javascript:void(0);"
													style="cursor: pointer;"
													onclick="dijit.byId('infoProtocollo').show();"
													title="<fmt:message key="label.messaggi_annulla_protocollo"/>">
												</a>
												</li>
												<div id="infoProtocollo" dojoType="dijit.Dialog"
													title="<fmt:message key="label.messaggi_annulla_protocollo"/>"
													style="display: none;">
													<div id="infoProtocollo_content"></div>
												</div>
												<script type="text/javascript">
										function checkProtocolloAnnullato(){											
											var jhqrPr = jQuery.ajax({
												  url: '../protocollazione/ajaxCheckProtocolloAnnullato.htm',
												  context: document.body,
												  cache: false,
												  data: "codiceIstanza=${istanzeCommand.entity.id.codice}",
												  dataType: "html",
												  success: function(data) {													  
													  if(data!=''){
														$('infoProtocollo_content').innerHTML=data;
														$('link_protocollo_annullato').style.display='';
														applyStyle();
													  }else{
														 // $('link_annulla_protocollo').style.display='';
													  }
													},
													error: function(jqXHR, textStatus, errorThrown){
														console.error("Errore nella richiesta del protocollo annullato: "+jqXHR.responseText);														
													}
												});		
										}	
										jQuery(document).ready(function () {
											checkProtocolloAnnullato();
										});	
									</script>
												<%-- LEGGI PROTOCOLLO --%>
												<c:set var="_VISUALIZZABOTTONELEGGI"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI)%></c:set>
												<c:if test="${_VISUALIZZABOTTONELEGGI eq '1' }">
													<li><a
														href="javascript:historySet('${_urlback }','../protocollazione/leggiProtocollo.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','');"
														title="<fmt:message key="label.leggi_protocollo"/>"><fmt:message
																key="label.leggi" /></a></li>
												</c:if>
												<%-- ACCETTA PROTOCOLLO --%>
												<c:set var="_GESTIONEACCETTAZIONE"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_ACCETTAZIONE)%></c:set>
												<c:if test="${_GESTIONEACCETTAZIONE eq '1' }">

													<c:if test="${mostraBottoneAccettaProtocollo eq '1' }">
														<li id="accettaprotocollobutton_id"><a
															href="javascript:accettaProtocollo('${istanzeCommand.entity.id.codice}')"><fmt:message
																	key="label.accetta_protocollo" /></a></li>
													</c:if>
													<c:if test="${mostraBottoneAccettaProtocollo eq 'KO' }">
														<li id="link_is_esitato"><a
															class="vbg-btn btn-avvisi" href="javascript:void(0);"
															style="cursor: pointer;"
															onclick="dijit.byId('infoIsEsitato').show();"
															title="Errore sul check esito protocollo"> </a></li>
														<div id="infoIsEsitato" dojoType="dijit.Dialog"
															title="Errore sul check esito protocollo"
															style="display: none;">
															<div id="infoIsEsitato_content">
																<c:out value="${isEsitatoErroreMessaggio}"
																	escapeXml="true" />
															</div>
														</div>
													</c:if>

												</c:if>
												<%-- FASCICOLA  --%>
												<c:if test="${gestisciFascicolo eq true }">

													<li id="link_cambia_fascicolo" style="display: none;">
														<a
														href="javascript:historySet('${_urlback }','../protocollazione/cambiaFascicoloView.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','');"
														title="<fmt:message key="label.modifica_fascicolazione"/>"><fmt:message
																key="label.modifica_fascicolazione" /></a>
													</li>
													<li id="link_crea_fascicolo" style="display: none;"><a
														href="javascript:historySet('${_urlback }','../protocollazione/creaFascicoloView.htm?codiceIstanza=${istanzeCommand.entity.id.codice}&provenienza=<%=ProtocollazioneCommand.PROVENIENZA_ISTANZA %>','');"
														title="<fmt:message key="label.crea_fascicolazione"/>"><fmt:message
																key="label.crea_fascicolazione" /></a></li>
													<li id="errori_fascicolazione" style="display: none;">
														<a style="cursor: pointer;" class="vbg-btn btn-avvisi"
														onclick="javascript:dijit.byId('infoFascicolo').show();"
														title="<fmt:message key="label.messaggi_fascicolazione"/>">
													</a>
													</li>
													<div id="infoFascicolo" dojoType="dijit.Dialog"
														title="<fmt:message key="label.messaggi_fascicolazione"/>"
														style="display: none;">
														<div id="info_fascicolo_content"></div>
													</div>

													<script type="text/javascript">
													function checkProtocolloFascicolato(){											
														var jhqrPr = jQuery.ajax({
															  url: '../protocollazione/ajaxCheckFascicolato.htm',
															  context: document.body,
															  cache: false,
															  data: "codiceIstanza=${istanzeCommand.entity.id.codice}",
															  dataType: "html",
															  success: function(data) {
																  if(data!=''){
																	  if(data=='si'){
																		$('link_cambia_fascicolo').style.display='';
																	  }	else if(data=='no'){
																		$('link_crea_fascicolo').style.display='';
																	  }else{
																		$('info_fascicolo_content').innerHTML=data;
																		$('errori_fascicolazione').style.display='';
																		applyStyle();
																	  }														
																  }
																},
																error: function(jqXHR, textStatus, errorThrown){
																	$('info_fascicolo_content').innerHTML=jqXHR.responseText;
																	$('errori_fascicolazione').style.display='';									
																}
															});		
													}	
													jQuery(document).ready(function () {
														checkProtocolloFascicolato();
													});
												</script>
												</c:if>
												<%-- FASCICOLA --%>
												<c:set var="_VISUALIZZABOTTONESTAMPA"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA)%></c:set>
												<c:if test="${_VISUALIZZABOTTONESTAMPA eq '1' }">
													<li><a
														href="javascript:historySet('${_urlback }','../protocollazione/stampaView.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','');"
														title="<fmt:message key="label.stampa"/>"><fmt:message
																key="label.stampa" /></a></li>
												</c:if>
											</c:if>
											<c:if test="${isDocEr eq true}">
												<li><a
													href="javascript:historySet('${_urlback }','../protocollazione/createUnitaDocumentale.htm?provenienza=I&codiceIstanza=${istanzeCommand.entity.id.codice}','');"><fmt:message
															key="label.riversa_documenti_docer" /></a></li>
												<li><a
													href="javascript:historySet('${_urlback }','../protocollazione/create.htm?provenienza=I&codiceIstanza=${istanzeCommand.entity.id.codice}&registrazioneDocer=true','');"><fmt:message
															key="label.registrazione_docer" /></a></li>
											</c:if>
										</ul>
									</div>
								</td>
							</tr>
						</c:if>
					</c:if>
					<c:if
						test="${istanzeCommand.tipoInserimento eq _INSERIMENTO_RAPIDO}">
						<tr>
							<td></td>
							<td class="inline-ui-cell" style="min-width: 234px;"></td>
							<td colspan="4" class="inline-ui-cell"><init:editLabel
									key="label.numero_e_data_protocollo_assegnati_automaticamente"
									role="ROLE_EDITLABEL" /></td>
						</tr>
					</c:if>
					<tr class="titoloSezione">
						<td colspan="6"><init:editLabel key="label.dati_progetto"
								role="ROLE_EDITLABEL" /></td>
					</tr>

					<c:choose>
						<c:when
							test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">

							<tr id="id_progetto_table">
								<td valign="top"><label class="required">*</label> <init:editLabel
										key="label.alberoproc" role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><spring-form:input
										id="alberoproc_hidden" path="entity.alberoproc.id.codice"
										onchange="cercaProcedimento()" size="9"
										cssStyle="text-align: right;" /> <%-- ALBEROPROC DOJO TREE --%>
									<a class="vbg-btn btn-cerca"
									href="javascript:cercaProcedimento();"
									title="Cerca procedimento" style="vertical-align: bottom;"
									id="alberoimg_id"> </a> <spring-form:input
										id="alberoproc_descrestesa_hidden"
										path="entity.alberoproc.vwAlberoproc.scDescrizione" size="100"
										readonly="true" /> <spring-form:errors
										path="entity.alberoproc" cssClass="error" /></td>
							</tr>
							<tr id="id_progetto_table">
								<td colspan="">&nbsp;</td>
								<td colspan="5">

									<div dojoType="dojo.data.ItemFileReadStore"
										jsId="alberoprocStore"
										url="${pageContext.request.contextPath}/json/getAlberoproc.htm?_timestamp=<%=String.valueOf(System.currentTimeMillis()) %>">
									</div>
									<div dojoType="dijit.tree.ForestStoreModel"
										jsId="alberoprocModel" store="alberoprocStore"
										query="{root:'1'}"
										rootId="<%=WebConstants.ATECO_CODICE_ROOT%>"
										rootLabel="<fmt:message key="label.albero_dei_procedimenti" />"
										childrenAttrs="children"></div> <br />
									<div id="treeOne"></div>
									<div id="mostraEndoDiv" style="border: 1px;">&nbsp;</div> <script
										type="text/javascript">
								var treeControl = null;
								var treeInitialized = false;
								
								function cercaProcedimento(){									
									var codProc = $('alberoproc_hidden').value;
									rimuoviValori2();
									if(codProc){
										cercaProcedimentoAjax(codProc);	
									}else{
										apriAlbero();
									}
									$('alberoproc_hidden').focus();
								}
								
								function cercaProcedimentoAjax(codiceAlberoproc){
									if(isNaN(codiceAlberoproc)){
										alert("Ricerca per codice. Inserire un valore numerico");
										return;
									}
									new Ajax.Request('../json/getAlberoprocHelper.htm?hideDisabled=true', {
										  method: 'post',
										  parameters: {id: codiceAlberoproc},
										  onSuccess: function(transport){ 
											var response = transport.responseText;
											//alert(response);
											var json = response.evalJSON();
											if(json.id){
												if(json.padre == 'true'){
													alert("Procedimento non selezionabile.");
												}else{
													assegnaValori2(json);
													$('treeOne').style.display="none";
												}						
											}else{
												alert("Procedimento non trovato o disattivato.");
										    }
										  },
										  onFailure: function(transport){ 
											var response = transport.responseText; 
										    alert("Errore nella ricerca del procedimento!");
										  }						    		 
									} );
								}
								
								jQuery($('alberoproc_hidden')).keypress(function(e) {
							  	  	var code = e.keyCode ? e.keyCode : e.which;
									if(code.toString() == 13) {
										cercaProcedimento(); 
									}
							    });
								
								function apriAlbero() {
							        if(!treeControl){
								        treeControl = new dijit.Tree({
								            model: alberoprocModel,
								            showRoot: true,							            
								            onClick: function(item, node){
								            	if(item.id !='0' ){	
									        		var itemId = alberoprocStore.getValue(item, "id");
									        		if(itemId>0){
														cercaProcedimentoAjax(itemId);
														$('alberoproc_hidden').focus();
									        		}
								            	}
								            },
								            getIconClass: function(item,opened){							            	
								            	treeInitialized = true;
								            	if(item.id!='0'){
									        		var dis = 'false';
									        		if(item){
									        			dis = alberoprocStore.getValue(item, "disabilitato");
									        		}
									        		if(dis == 'false'){
									        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf";
									        		}else{
									        			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled";
									        		}
								            	}else{
								            		return "dijitFolderOpened"
								            	}	
								            }
								        },
								        "treeOne");
							        }else{
							        	document.getElementById('treeOne').style.display="";
							        }
							    }
								
								<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
									var forzaNumeroPratica = false; 
								</c:if>
								<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
									var forzaNumeroPratica = true; 
								</c:if>
								
								var endoSplashDiv=null;
								
								jQuery(document).ready(function(){
									 endoSplashDiv = new dijit.Dialog({
							            title: "<fmt:message key='label.endoprocedimenti' />" ,
							            style: "width: 500px"
							        });
								});
								
								function mostraDivEndo(codiceAlberoproc){
									
									new Ajax.Request('../istanze/ajaxListaEndo.htm', {
										  method: 'post',
										  parameters: {codiceAlberoProc: codiceAlberoproc},
										  onSuccess: function(transport){ 
											  endoSplashDiv.attr("content", transport.responseText);
											  endoSplashDiv.show();
										  },
										  onFailure: function(transport){ 
											var response = transport.responseText;
											console.error("Errore nella ricerca degli endo procedimenti:" + response);										    
										  }						    		 
									} );			
									
								}
								
								var progressivoIstanza='';
								
								function trovaProgressivoIstanza(codiceAlberoproc){
									var ts = new Date().getTime();
									new Ajax.Request('../alberoproc/ajaxFindProgressivo.htm?ts='+ts, {
										  method: 'post',
										  parameters: {codiceAlberoProc: codiceAlberoproc},
										  onSuccess: function(transport){											  
												var nuovoNumeroIstanza = transport.responseText;
												if(nuovoNumeroIstanza){
													if(nuovoNumeroIstanza!=''){
														if ($('entity_numeroistanza_id').value=="" ){											
															$('entity_numeroistanza_id').value=nuovoNumeroIstanza;
														} else if ( $('entity_numeroistanza_id').value != nuovoNumeroIstanza && forzaNumeroPratica == true && nuovoNumeroIstanza ){
															if ('${configurazione.flagNoNumeroistanza }' == 'false'){
																if( confirm("Attenzione, la pratica ha già un numero che non corrisponde con quello dell'intervento selezionato.\nSostituire il numero " + 
																			$('entity_numeroistanza_id').value + " con il nuovo numero "+nuovoNumeroIstanza+" ?" ) ){
																	$('entity_numeroistanza_id').value = nuovoNumeroIstanza;
																}
															}else{
																$('entity_numeroistanza_id').value=nuovoNumeroIstanza;												
															}
														}
													}
												}
										  },										  
										  onFailure: function(transport){ 
											  var response = transport.responseText;
											  console.error("Errore nella ricerca del numero istanza:" + response);			
										  }										  
									} );									
								}
								
								function chiudiEndoDiv(){
									endoSplashDiv.hide();
								}
								
								function assegnaValori2(map){
									$('alberoproc_hidden').value = map.id;
									$('alberoproc_descrestesa_hidden').value = map.desc;
									if(map.resp_proc_id){
										if($('responsabileProcedimento_id_id')){
											$('responsabileProcedimento_id_id').value = map.resp_proc_desc;
											$('responsabileProcedimento_id_hidden').value = map.resp_proc_id;
										}
									}
									if(map.resp_istr_id){
										if($('istruttore_id_id')){
											$('istruttore_id_id').value = map.resp_istr_desc;
											$('istruttore_id_hidden').value = map.resp_istr_id;
										}
									}
									if(map.procedura_id){
										$('tipiprocedure_id_id').value = map.procedura_desc;
										$('tipiprocedure_id_hidden').value = map.procedura_id;
									}
									if(map.mov_avv_id){
										$('tipoMovimentoAvvio_id_id').value = map.mov_avv_desc;
										$('tipoMovimentoAvvio_id_hidden').value = map.mov_avv_id;
									}
									if(map.endo_presenti == "true"){
										mostraDivEndo(map.id);
									}else{
										resetEndoInSession();
									}	
									trovaProgressivoIstanza(map.id);	
								}
								
							function resetEndoInSession(){
								jQuery.ajax({
									url: '${pageContext.request.contextPath}/istanze/ajaxResetEndo.htm', 
									dataType: 'text',													
									cache: false													
								});
							}
								
								
								function rimuoviValori2(){
									$('alberoproc_hidden').value = '';
									$('alberoproc_descrestesa_hidden').value = '';
									if($('responsabileProcedimento_id_id')){
										$('responsabileProcedimento_id_id').value = '';
										$('responsabileProcedimento_id_hidden').value = '';
									}
									if($('istruttore_id_id')){
										$('istruttore_id_id').value = '';
										$('istruttore_id_hidden').value = '';
									}
									
									$('tipiprocedure_id_id').value = '';
									$('tipiprocedure_id_hidden').value = '';
									
									$('tipoMovimentoAvvio_id_id').value = '';
									$('tipoMovimentoAvvio_id_hidden').value = '';
								}
								
								function endoInSession(chkobjid, codiceinventario) {			
									var _ts = new Date().getTime();
									var url = '${pageContext.request.contextPath}/istanze/ajaxEndoInSession.htm?_ts='+_ts;
											url+='&codiceinventario='+codiceinventario+'&selezionato='+$(chkobjid).checked
																		
									changeCheckboxValue(chkobjid,url);									
								}
								
								</script>

								</td>
							</tr>

						</c:when>
						<c:otherwise>
							<tr id="id_progetto_table" title="${whyCanNotModifyIntervento}">
								<td style="vertical-align: text-top;"><init:editLabel
										key="label.alberoproc" role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><b>${istanzeCommand.entity.alberoproc.vwAlberoproc.scDescrizione }</b>
									<c:if test="${isModificaIntervento eq true}">
										<div id="functions">
											<ul>
												<li><a href="javascript:void(0);"
													onclick="historySet('${_urlback}','../istanze/modificaIntervento.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','');"><fmt:message
															key="label.modifica_intervento" /></a></li>
											</ul>
										</div>
									</c:if></td>
							</tr>

						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${isModificaIntervento eq true}">

							<%--
							<c:if test="${isImpiantiVisibile eq true }">
							<tr>
								<td><fmt:message key="label.tipo_impianto" /></td>
								<td colspan="3">						
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="tipoimpianto_id" />
										<jsp:param name="propertyPath" value="entity.impianto" />
										<jsp:param name="pathPropertyDescription" value="entity.impianto.impianto" />
										<jsp:param name="pathPropertyCode" value="entity.impianto.id.codice" />
										<jsp:param name="autocompleterAjax" value="findImpianti.htm" />							
										<jsp:param name="titleKey" value="label.ricerca_tipiimpianto" />
									</jsp:include>
								</td>
							</tr>
							</c:if>
						 --%>

							<tr id="id_progetto_table">
								<td><label class="required">*</label> <init:editLabel
										key="label.tipiprocedure.procedura" role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><script
										type="text/javascript">
									function proceduraCallBack(inputField,listItem){
										var a = listItem.id;
										document.getElementById('tipiprocedure_id_id').value = inputField.value;
										document.getElementById('tipiprocedure_id_hidden').value = a;
										checkMovAvvio(a, 'tipoMovimentoAvvio_id');
									}
									function checkMovAvvio(codiceProcedura, movAvvioId){
										if(codiceProcedura){
											if(codiceProcedura!=''){
												if($(movAvvioId + '_id')){
													var ts = new Date().getTime();
													new Ajax.Request('<%=request.getContextPath()%>/ajax/jsonFindMovimentoAvvioProcedura.htm?ts='+ts, {
														  method: 'post',
														  parameters: {codice: codiceProcedura},
														  onSuccess: function(transport){	
															  	var json = transport.responseText.evalJSON();		 	
															  	var tipomov = json.tipoMovimento;
																if(tipomov.id.tipomovimento && tipomov.id.tipomovimento!=''){
																	$(movAvvioId + '_hidden').value=tipomov.id.tipomovimento;
																	$(movAvvioId + '_id').value=tipomov.movimento+' ('+tipomov.id.tipomovimento+')';
																}else
																{
																	$(movAvvioId + '_hidden').value='';
																	$(movAvvioId + '_id').value='';
																}
														    },
														  onFailure: function(transport){ 
															var response = transport.responseText;
															console.error('checkMovAvvio: '+response);
														  } 
													});
												}
											}
										}
									}
								</script> <jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="tipiprocedure_id" />
										<jsp:param name="propertyPath" value="entity.procedura" />
										<jsp:param name="pathPropertyDescription"
											value="entity.procedura.procedura" />
										<jsp:param name="pathPropertyCode"
											value="entity.procedura.id.codice" />
										<jsp:param name="autocompleterAjax"
											value="findTipiprocedure.htm?soloConMovimentoAvvio=true" />
										<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
										<jsp:param name="afterUpdateElement" value="proceduraCallBack" />
									</jsp:include></td>
							</tr>
						</c:when>
						<c:otherwise>
							<%--
						<c:if test="${isImpiantiVisibile eq true }">
							<tr id="id_progetto_table" title="${whyCanNotModifyIntervento}">
								<td><fmt:message key="label.tipo_impianto" /></td>
								<td colspan="5">
									<b>${istanzeCommand.entity.impianto.impianto }</b>
								</td>
							</tr>
						</c:if>
						 --%>
							<tr id="id_progetto_table" title="${whyCanNotModifyIntervento}">
								<td><init:editLabel key="label.tipiprocedure.procedura"
										role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><b>${istanzeCommand.entity.procedura.procedura }</b>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>

					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeRespProc')}">


						<tr>
							<td><init:editLabel key="label.responsabile_procedimento"
									role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><c:choose>
									<c:when
										test="${VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_IN_REQUEST eq false}">
										<jsp:include page="../includes/autocompletergenerico.jsp">
											<jsp:param name="idElemento"
												value="responsabileProcedimento_id" />
											<jsp:param name="propertyPath"
												value="entity.responsabileProcedimento" />
											<jsp:param name="pathPropertyDescription"
												value="entity.responsabileProcedimento.responsabile" />
											<jsp:param name="pathPropertyCode"
												value="entity.responsabileProcedimento.id.codice" />
											<jsp:param name="autocompleterAjax"
												value="findResponsabiliProcedimento.htm" />
											<jsp:param name="titleKey" value="label.ricerca_responsabile" />
										</jsp:include>
									</c:when>
									<c:otherwise>
										<spring-form:input id="responsabileProcedimento_id_id"
											path="entity.responsabileProcedimento.responsabile" size="70"
											readonly="true" />
										<spring-form:errors path="entity.responsabileProcedimento"
											cssClass="error" />
										<spring-form:hidden id="responsabileProcedimento_id_hidden"
											path="entity.responsabileProcedimento.id.codice" />
										<c:if test="${canChangeResponsabileAssegnato eq true}">
											<a class="vbg-btn btn-utente" href="javascript: void 0"
												onclick="assegnazioneOperatori(${istanzeCommand.entity.id.codice},'responsabile')"
												title="Assegnazione operatori"> &nbsp; </a>
										</c:if>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:if>
					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeIstruttore')}">
						<tr>
							<td><init:editLabel key="label.responsabile_istruttoria"
									role="ROLE_EDITLABEL" /></td>


							<c:choose>
								<c:when
									test="${VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_IN_REQUEST eq false}">
									<c:if
										test="${istanzeCommand.entity.istruttoreTemp != null && istanzeCommand.entity.istruttoreTemp.id.codice ==null}">
										<td colspan="5" class="inline-ui-cell"><jsp:include
												page="../includes/autocompletergenerico.jsp">
												<jsp:param name="idElemento" value="istruttore_id" />
												<jsp:param name="propertyPath" value="entity.istruttore" />
												<jsp:param name="pathPropertyDescription"
													value="entity.istruttore.responsabile" />
												<jsp:param name="pathPropertyCode"
													value="entity.istruttore.id.codice" />
												<jsp:param name="autocompleterAjax"
													value="findResponsabiliIstruttoria.htm" />
												<jsp:param name="titleKey"
													value="label.ricerca_responsabile" />
											</jsp:include></td>
									</c:if>

									<c:if
										test="${istanzeCommand.entity.istruttoreTemp != null && istanzeCommand.entity.istruttoreTemp.id.codice !=null}">
										<td class="inline-ui-cell"><spring-form:input
												path="entity.istruttore.responsabile" readonly="true"
												size="70" /> <spring-form:hidden
												path="entity.istruttore.id.codice" /> <a
											class="vbg-btn btn-salva"
											href="javascript:assegnaIstruttore('ricercaIstruttoriDiv',${istanzeCommand.entity.id.codice},'true')"
											id="istrut_temp"
											title="<fmt:message key="label.modifica_istruttore" />">
										</a> <init:help idHelp="help_istr_temp"
												textKey="help.istruttore_assegnato_anticorruzione" /></td>
									</c:if>
						</tr>

						</c:when>
						<c:otherwise>
							<td colspan="5" class="inline-ui-cell"><spring-form:input
									id="istruttore_id_id" path="entity.istruttore.responsabile"
									size="70" readonly="true" /> <spring-form:errors
									path="entity.istruttore.responsabile" cssClass="error" /> <spring-form:hidden
									id="istruttore_id_hidden" path="entity.istruttore.id.codice" />
								<c:if test="${canChangeResponsabileAssegnato eq true}">
									<a class="vbg-btn btn-utente" href="javascript: void 0"
										onclick="assegnazioneOperatori(${istanzeCommand.entity.id.codice},'istruttore')"
										title="Assegnazione operatori"> &nbsp; </a>
								</c:if></td>
						</c:otherwise>
						</c:choose>



					</c:if>
					<%-- 
				   <tr>		
						
							<td>Gruppo:<spring-form:input path="entity.gruppiIstruttori.descrizione"  readonly="true"/></td>
							<td>Assegnazione Aut:<spring-form:input path="entity.grpFlagAssegnazioneAut"  readonly="true"/></td>
							<td>Accettazione:<spring-form:input path="entity.grpFlagAccettazione"  readonly="true"/></td>
							<td>Data Accetazione:<spring-form:input path="entity.gprDataAccettazione"  readonly="true"/></td>
							<td>Istruttore temp:<spring-form:input path="entity.istruttoreTemp.responsabile"  readonly="true"/></td>
						
					</tr>
					--%>

					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'sezIstanzeLocalizzazione')}">

						<tr class="titoloSezione">
							<td colspan="6"><init:editLabel
									key="label.dati_localizzazione" role="ROLE_EDITLABEL" /></td>
						</tr>

						<!-- BEGIN SEZIONE LOCALIZZAZIONE -->

						<c:set var="VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO)%></c:set>

						<c:set var="VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT)%></c:set>
						<c:if
							test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">




							<c:if test="${isAreeVisibile eq true}">
								<tr id="id_localizzazione_table">
									<td><init:editLabel key="label.area" role="ROLE_EDITLABEL" /></td>

									<c:if
										test="${fn:length(istanzeCommand.entity.istanzearees)==0}">
										<td class="inline-ui-cell">
									</c:if>
									<c:if
										test="${fn:length(istanzeCommand.entity.istanzearees)!=0}">
										<td class="inline-ui-cell">
									</c:if>
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="area_id" />
										<jsp:param name="propertyPath" value="istanzearee" />
										<jsp:param name="pathPropertyDescription"
											value="istanzearee.area.denominazione" />
										<jsp:param name="pathPropertyCode"
											value="istanzearee.id.codicearea" />
										<jsp:param name="autocompleterAjax" value="findAree.htm" />
										<jsp:param name="titleKey" value="label.ricerca_aree" />
									</jsp:include>
									</td>
									<td colspan="4" class="inline-ui-cell"><a class="letteraA"
										href="javascript:dialogoListAree('listaAreeDialogDiv',${istanzeCommand.entity.id.codice});"
										title="<fmt:message key="label.lista_altre_aree" />"> </a> <c:if
											test="${fn:length(istanzeCommand.entity.istanzearees)>0}">
											<a class="addColumn" style="float: none;"
												href="javascript:historySet('${_urlback}','..%2Fistanzearee%2Fcreate.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','')"
												title="<fmt:message key="label.nuova" /> <fmt:message key="label.area" />">
												<!-- <label><fmt:message key="label.add.record.image" /></label>  -->
											</a>

											<div dojoType="dijit.Dialog" id="listaAreeDialogDiv"
												title="<fmt:message key="label.lista_altre_aree" />: ">
												<div dojoType="dijit.layout.ContentPane"
													class="generic_dialog" style="width: 500px">
													<div id="listaAree"></div>
												</div>
											</div>

										</c:if></td>
								</tr>
							</c:if>
							<c:if test="${isAree2Visibile eq true}">
								<tr id="id_localizzazione_table">

									<td><init:editLabel key="label.area2"
											role="ROLE_EDITLABEL" /></td>
									<td colspan="5" class="inline-ui-cell"><jsp:include
											page="../includes/autocompletergenerico.jsp">
											<jsp:param name="idElemento" value="area2_id" />
											<jsp:param name="propertyPath" value="entity.aree2" />
											<jsp:param name="pathPropertyDescription"
												value="entity.aree2.denominazione" />
											<jsp:param name="pathPropertyCode"
												value="entity.aree2.id.codice" />
											<jsp:param name="autocompleterAjax" value="findAree2.htm" />
											<jsp:param name="titleKey" value="label.ricerca_aree2" />
										</jsp:include></td>
								</tr>
							</c:if>

							<tr>
								<td colspan="6"><c:if
										test="${isVerticalizzazioneGOOGLE_MAPSAttiva}">
										<c:if test="${SHOW_BTN_PERCORSO}">

											<div id="functions">
												<ul>
													<li><a href="javascript:mostraPercorso();">Mostra
															percorso</a></li>

												</ul>
											</div>

											<script type="text/javascript">
										function mostraPercorso(){
											var params  = 'width='+screen.width;
											 params += ', height='+screen.height;
											 params += ', top=0, left=0'
											 params += ', fullscreen=yes';
											var url = '${pageContext.request.contextPath}/istanze/popupViewPercorsoMaps.htm?codiceIstanza=${istanzeCommand.entity.id.codice}' ;
										  	var win_<%=System.currentTimeMillis()%>_fx = window.open(url,'_blank',params);				
										};
									</script>
										</c:if>
									</c:if> <script type="text/javascript">
							<%-- 
								FIX IE8 MODALITA' NON COMPATIBILE - RENDERIZZAZIONE TABELLA
							--%>
							jQuery(document).ready(function(){
								setTimeout(function(){

									$('localizzazione_table_jmesa_id').style.display = '';

								}, 20);	
							});
							</script>
									<div class="jmesa">
										<table border="1" id="localizzazione_table_jmesa_id"
											width="100%" cellpadding="2" cellspacing="0" class="table"
											style="display: none;">
											<thead>
												<tr class="header">
													<td colspan="2" width="30%"><fmt:message
															key="label.indirizzo" /></td>
													<td
														title="<fmt:message key="label.civico" />/<fmt:message key="label.km" />"
														width="2%"><fmt:message key="label.C" /></td>
													<c:if
														test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocEsponente')}">
														<td title="<fmt:message key="label.esponente" />"
															width="2%"><fmt:message key="label.E" /></td>
													</c:if>
													<c:if test="${isStradariocoloreVisible eq true }">
														<td title="<fmt:message key="label.colore" />" width="2%"><fmt:message
																key="label.CO" /></td>
													</c:if>
													<c:if
														test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldScalaInternoEspInterno')}">
														<td title="<fmt:message key="label.scala" />" width="2%"><fmt:message
																key="label.S" /></td>
														<td title="<fmt:message key="label.piano" />" width="2%"><fmt:message
																key="label.P" /></td>
														<td title="<fmt:message key="label.interno" />" width="2%"><fmt:message
																key="label.I" /></td>
														<td title="<fmt:message key="label.esponente_interno" />"
															width="2%"><fmt:message key="label.EI" /></td>
													</c:if>
													<td title="<fmt:message key="label.fabbricato" />"
														width="2%"><fmt:message key="label.F" /></td>
													<td><fmt:message key="label.cap" /></td>
													<td width="10%"><fmt:message key="label.note" /></td>
													<c:if
														test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocFrazioneCircoscrizione')}">
														<td title="<fmt:message key="label.frazione" />"
															width="2%"><fmt:message key="label.FR" /></td>
														<td title="<fmt:message key="label.quartiere" />"
															width="2%"><fmt:message key="label.Q" /></td>
														<td title="<fmt:message key="label.circoscrizione" />"
															width="2%"><fmt:message key="label.CI" /></td>
													</c:if>
													<td width="20%"><fmt:message
															key="label.dati_catastali" /></td>
													<td width="10%"><fmt:message key="label.azioni" /></td>
												</tr>
											</thead>
											<tbody class="tbody">
												<c:forEach items="${istanzestradarios}" var="istStradario"
													varStatus="istStradStatus">
													<c:set var="primarioTitle" value="" />
													<c:if
														test="${not empty istStradario.primario && istStradario.primario eq true}">
														<c:set var="primarioTitle">
															<fmt:message key="label.primario" />
														</c:set>
													</c:if>
													<c:set var="trStyle" value="odd" />
													<c:if test="${(istStradStatus.index mod 2) eq 0}">
														<c:set var="trStyle">even</c:set>
													</c:if>
													<tr class="${trStyle}" title="${primarioTitle}">
														<td width="5%"><c:if
																test="${not empty istStradario.primario && istStradario.primario eq true}">
																<fmt:message key="label.p" />
															</c:if>&nbsp; <c:if
																test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
																<c:if
																	test="${istStradario.valido eq false or empty istStradario.valido}">
																	<span class="vbg-btn btn-error"
																		style="vertical-align: middle;"
																		title="<fmt:message key="label.stradario_non_validato_da_sit" />">
																	</span>
																</c:if>
																<c:if
																	test="${VERTICALIZZAZIONE_SIT_ATTIVO_TIPOSIT_IN_REQUEST eq 'SIT_QUAESTIOFLORENZIA' }">
																	<c:if test="${empty istStradario.idPuntoSit}">
																		<span class="vbg-btn btn-avvisi"
																			style="vertical-align: middle;"
																			title="<fmt:message key="label.stradario_non_geolocalizzato_da_sit" />" />
																	</c:if>
																</c:if>
															</c:if></td>
														<td class="inline-ui-cell">
															${istStradario.stradario.descrizioneCompleta}&nbsp; <%-- <c:if test="${istStradario.stradario.datavalidita !=null}"> --%>
															<%
															  pageContext.setAttribute("today", new Date());
															%> <c:if
																test="${istStradario.stradario.datavalidita <= today}">
																<a class="blockColumn" style="float: none;"
																	href="javascript:void(0)"
																	title="<fmt:message key="label.stradario_disabilitato" />&nbsp;<fmt:formatDate value="${istStradario.stradario.datavalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />">
																	<label><fmt:message
																			key="label.stradario_disabilitato" /></label>
																</a>
															</c:if>
														</td>
														<td style="white-space: nowrap;"><c:if
																test="${not empty istStradario.km and not empty istStradario.civico}">
																<fmt:message key="label.civico" />: </c:if>
															${istStradario.civico}<c:if
																test="${not empty istStradario.km}">&nbsp;<fmt:message
																	key="label.km" />: ${istStradario.km}</c:if></td>

														<c:if
															test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocEsponente')}">
															<td>${istStradario.esponente}&nbsp;</td>
														</c:if>
														<c:if test="${isStradariocoloreVisible eq true }">
															<td>${istStradario.stradariocolore.colore}&nbsp;</td>
														</c:if>
														<c:if
															test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldScalaInternoEspInterno')}">
															<td>${istStradario.scala}&nbsp;</td>
															<td>${istStradario.piano}&nbsp;</td>
															<td>${istStradario.interno}&nbsp;</td>
															<td>${istStradario.esponenteinterno}&nbsp;</td>
														</c:if>
														<td>${istStradario.fabbricato}&nbsp;</td>
														<td width="5%">${istStradario.cap}&nbsp;</td>
														<td width="5%">${istStradario.note}&nbsp;</td>
														<c:if
															test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocFrazioneCircoscrizione')}">
															<td>${istStradario.frazione}&nbsp;</td>
															<td>${istStradario.quartiere}&nbsp;</td>
															<td>${istStradario.circoscrizione}&nbsp;</td>
														</c:if>
														<td><c:forEach
																items="${istStradario.istanzemappalis}" var="istMappale"
																varStatus="istMappStatus">
															${istMappale.descrizioneEstesa}<br />
															</c:forEach> &nbsp;</td>
														<td width="8%"><input type="hidden"
															id="stradario_id_hidden${istStradario.id.codice}"
															value="${istStradario.stradario.id.codice}" /> <input
															type="hidden" id="civico_id${istStradario.id.codice}"
															value="${istStradario.civico}" /> <input type="hidden"
															id="esponente_id${istStradario.id.codice}"
															value="${istStradario.esponente}" /> <input
															type="hidden" id="colore_id${istStradario.id.codice}"
															value="${istStradario.stradariocolore.id.codicecolore}" />
															<a class="dettaglioColumn"
															href="../istanzestradario/view.htm?codice=${istStradario.id.codice}"
															title="<fmt:message key="label.edit.record" /> ${istStradario.descrizioneEstesaTransient}">
																<!-- <label><fmt:message key="label.edit.record.image" /></label>  -->
														</a> <a class="letteraA"
															href="javascript:visualizzaAltreIstanze('altreIstanzeDialogDiv${istStradario.id.codice}',${istStradario.id.codice});"
															title="<fmt:message key="label.altre_istanze_con_la_stessa_localizzazione" />">
																<!-- <label><fmt:message key="label.altri_indirizzi.image" /></label>  -->
														</a>
															<div dojoType="dijit.Dialog"
																id="altreIstanzeDialogDiv${istStradario.id.codice}"
																title="<fmt:message key="label.altre_istanze_con_la_stessa_localizzazione" />: ${istStradario.descrizioneEstesaTransient}">
																<div dojoType="dijit.layout.ContentPane"
																	class="generic_dialog">
																	<table>
																		<tr>
																			<td><label
																				for="_civico${istStradario.id.codice}"><fmt:message
																						key="label.civico" /></label> <input type="checkbox"
																				id="_civico${istStradario.id.codice}"
																				name="_civico${istStradario.id.codice}"
																				checked="checked"
																				onclick="visualizzaListaIstanze(${istStradario.id.codice});" /></td>
																			<td><label
																				for="_esponente${istStradario.id.codice}"><fmt:message
																						key="label.esponente" /></label> <input type="checkbox"
																				id="_esponente${istStradario.id.codice}"
																				name="_esponente${istStradario.id.codice}"
																				checked="checked"
																				onclick="visualizzaListaIstanze(${istStradario.id.codice});" /></td>
																			<td><label
																				for="_colore${istStradario.id.codice}"><fmt:message
																						key="label.colore" /></label> <input type="checkbox"
																				id="_colore${istStradario.id.codice}"
																				name="_colore${istStradario.id.codice}"
																				checked="checked"
																				onclick="visualizzaListaIstanze(${istStradario.id.codice});" /></td>
																		</tr>
																	</table>
																	<div
																		id="listaIstanzeAltriIndirizziDiv${istStradario.id.codice}"></div>
																</div>
															</div> <!-- §§§END§§§ --> <!-- §§§BEGIN§§§ --> <%--
														
															<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
																<a class="gisLink" href="../istanzestradario/view.htm?codice=${istStradario.id.codice}" 
																	title="<fmt:message key="label.visualizza_gis" />">	
																<label><fmt:message key="label.gis.image" /></label></a>
																																		
															</c:if>	
														
														 --%> <!-- §§§END§§§ --> <!-- §§§BEGIN§§§ --> <c:if
																test="${isNotificheASLPresenti eq true}">
																<a class="notificheASLColumn"
																	href="javascript:historySet('${_urlback }','../notificheausl/list.htm?filterIndirizzo=${istStradario.stradario.descrizione}&filterRagSoc=','') "
																	title="<fmt:message key="label.notifiche_asl" />"><label><fmt:message
																			key="label.notifiche_asl.image" /></label></a>
															</c:if> <!-- §§§END§§§ --></td>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr class="odd">
													<td><a class="addColumn"
														href="../istanzestradario/create.htm?codiceIstanza=${istanzeCommand.entity.id.codice}"
														title="<fmt:message key="label.nuovo" /> <fmt:message key="label.indirizzo" />">
															<label><fmt:message key="label.add.record.image" /></label>
													</a></td>
													<td colspan="7" width="80%">&nbsp;</td>
												</tr>
											</tfoot>
										</table>
									</div></td>
							</tr>
						</c:if>
						<c:if
							test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
							<c:if
								test="${istanzeCommand.tipoInserimento ne _INSERIMENTO_RAPIDO  or ir_mostratutti eq '1'}">
								<c:if test="${isAreeVisibile eq true}">
									<tr id="id_localizzazione_table">
										<td><init:editLabel key="label.area"
												role="ROLE_EDITLABEL" /></td>
										<td colspan="5" class="inline-ui-cell"
											style="min-width: 800px;"><jsp:include
												page="../includes/autocompletergenerico.jsp">
												<jsp:param name="idElemento" value="area_id" />
												<jsp:param name="propertyPath"
													value="istanzeFilter.istanzearee" />
												<jsp:param name="pathPropertyDescription"
													value="istanzeFilter.istanzearee.area.denominazione" />
												<jsp:param name="pathPropertyCode"
													value="istanzeFilter.istanzearee.id.codicearea" />
												<jsp:param name="autocompleterAjax" value="findAree.htm" />
												<jsp:param name="titleKey" value="label.ricerca_aree" />
											</jsp:include></td>
									</tr>
								</c:if>
								<c:if test="${isAree2Visibile eq true}">
									<tr id="id_localizzazione_table">

										<td><init:editLabel key="label.area2"
												role="ROLE_EDITLABEL" /></td>
										<td colspan="5" class="inline-ui-cell"><jsp:include
												page="../includes/autocompletergenerico.jsp">
												<jsp:param name="idElemento" value="area2_id" />
												<jsp:param name="propertyPath" value="entity.aree2" />
												<jsp:param name="pathPropertyDescription"
													value="entity.aree2.denominazione" />
												<jsp:param name="pathPropertyCode"
													value="entity.aree2.id.codice" />
												<jsp:param name="autocompleterAjax" value="findAree2.htm" />
												<jsp:param name="titleKey" value="label.ricerca_aree2" />
											</jsp:include></td>
									</tr>
								</c:if>
							</c:if>
							<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
								<tr id="id_localizzazione_table">
									<td colspan="6">
										<div id="stradario_validato_id" class="alertLine"
											style="display: none;">
											<init:editLabel key="label.stradario_non_validato_da_sit"
												role="ROLE_EDITLABEL" />
										</div>
									</td>
								</tr>
							</c:if>

							<c:choose>
								<c:when
									test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true && inite:contains(campiGestiti, 'codcivico_id')}">
									<tr id="id_localizzazione_table">
										<td>&nbsp;</td>
										<td colspan="5" class="inline-ui-cell"><label
											for="codcivico_id" style="display: none;"> <fmt:message
													key="label.codice_civico" />
										</label> <c:set var="codCivicoTitle" scope="page">
												<fmt:message key="label.codice_civico.help" />
											</c:set> <spring-form:input id="codcivico_id"
												path="istanzeFilter.istanzestradario.codicecivico"
												readonly="true" title="${codCivicoTitle}" /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<label for="codcivico_id" style="display: none;"> <init:editLabel
											key="label.codice_civico" role="ROLE_EDITLABEL" /></label>
									<spring-form:hidden id="codcivico_id"
										path="istanzeFilter.istanzestradario.codicecivico" />
								</c:otherwise>
							</c:choose>


							<c:if test="${not empty tipiLocalizzazionis}">

								<tr id="id_localizzazione_table">
									<td><init:editLabel key="label.tipo_localizzazione"
											role="ROLE_EDITLABEL" /></td>
									<td colspan="5" class="inline-ui-cell"><spring-form:select
											id="tipo_localizzazione_id"
											path="istanzeFilter.istanzestradario.tipiLocalizzazioni.id.codice">
											<spring-form:option value="">
												<fmt:message key="label.select.default" />
											</spring-form:option>
											<spring-form:options items="${tipiLocalizzazionis}"
												itemValue="id.codice" itemLabel="descrizione" />
										</spring-form:select></td>
								</tr>
							</c:if>


							<tr id="id_localizzazione_table">

								<td><init:editLabel key="label.indirizzo"
										role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell">
									<%-- INCLUDE SNIPPET FUNZIONI JAVASCRIPT --%> <jsp:include
										page="../includes/javascriptSIT.jsp">
										<jsp:param name="PAGINA_PROVENIENZA" value="ISTANZE" />
										<jsp:param name="CODICEISTANZA"
											value="${istanzeCommand.entity.id.codice}" />
									</jsp:include> <spring-form:hidden id="valido_id"
										path="istanzeFilter.istanzestradario.valido" /> <label
									for="codviario_id" style="display: none;"><init:editLabel
											key="label.codice_viario" role="ROLE_EDITLABEL" /></label> <spring-form:hidden
										id="codviario_id"
										path="istanzeFilter.istanzestradario.stradario.codviario" />

									<jsp:include page="../includes/searchstradario.jsp">
										<jsp:param name="idElemento" value="stradario_id" />
										<jsp:param name="pathStradario"
											value="istanzeFilter.istanzestradario.stradario" />
										<jsp:param name="stradarioHideFunctions"
											value="${isAddStradario}" />
										<jsp:param name="stradarioAutocompleterAjax"
											value="findStradario.htm?searchDisabilitati=false" />
										<jsp:param name="ajaxCallBack" value="filterCodiceComune" />
										<jsp:param name="afterUpdateElement"
											value="ricercaStradarioAfterUpdate" />
									</jsp:include> <a class="letteraA" style="float: none;"
									href="javascript:visualizzaAltreIstanze('altreIstanzeDialogDiv','');"
									title="<fmt:message key="label.altre_istanze_con_la_stessa_localizzazione" />">
										<!-- <label><fmt:message key="label.altri_indirizzi.image" /></label>  -->
								</a>
									<div dojoType="dijit.Dialog" id="altreIstanzeDialogDiv"
										title="<fmt:message key="label.altre_istanze_con_la_stessa_localizzazione" />">
										<div dojoType="dijit.layout.ContentPane"
											class="generic_dialog">
											<table>
												<tr>
													<td><label for="_civico"><fmt:message
																key="label.civico" /></label> <input type="checkbox"
														id="_civico" name="_civico" checked="checked"
														onclick="visualizzaListaIstanze('');" /></td>
													<td><label for="_esponente"><fmt:message
																key="label.esponente" /></label> <input type="checkbox"
														id="_esponente" name="_esponente" checked="checked"
														onclick="visualizzaListaIstanze('');" /></td>
													<td><label for="_colore"><fmt:message
																key="label.colore" /></label> <input type="checkbox"
														id="_colore" name="_colore" checked="checked"
														onclick="visualizzaListaIstanze('');" /></td>
												</tr>
											</table>
											<div id="listaIstanzeAltriIndirizziDiv"></div>
										</div>
									</div> <!-- §§§END§§§ -->
								</td>
							</tr>
							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.civico"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzestradario.civico" />
										<jsp:param name="idCampo" value="civico_id" />
										<jsp:param name="size" value="5" />
										<jsp:param name="cssStyle" value="max-width: 100px;" />
										<jsp:param name="resettaCivico" value="true" />
									</jsp:include></td>
								<td class="inline-ui-cell"><c:if
										test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocEsponente')}">
										<label for="esponente_id"><init:editLabel
												key="label.esponente" role="ROLE_EDITLABEL" /> </label>
									</c:if></td>
								<td class="inline-ui-cell"><c:if
										test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocEsponente')}">
										<jsp:include page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.esponente" />
											<jsp:param name="idCampo" value="esponente_id" />
											<jsp:param name="size" value="5" />
											<jsp:param name="cssStyle" value="max-width: 100px;" />
											<jsp:param name="resettaCivico" value="true" />
										</jsp:include>

									</c:if></td>
								<td class="inline-ui-cell"><c:if
										test="${isStradariocoloreVisible eq true }">
										<label for="colore_id"> <init:editLabel
												key="label.colore" role="ROLE_EDITLABEL" />
										</label>
									</c:if></td>
								<td class="inline-ui-cell"><c:if
										test="${isStradariocoloreVisible eq true }">
										<c:set var="fnColoreSit"></c:set>
										<c:if test="${inite:contains(campiGestiti, 'colore_id')}">
											<c:set var="fnColoreSit">resettaCodiceCivicoID();validaSIT($('colore_id'));</c:set>
										</c:if>

										<spring-form:select id="colore_id"
											path="istanzeFilter.istanzestradario.stradariocolore.id.codicecolore"
											onchange="${fnColoreSit}">
											<spring-form:option value="">
												<fmt:message key="label.select.default" />
											</spring-form:option>
											<spring-form:options items="${stradariocoloreList }"
												itemValue="id.codicecolore" itemLabel="colore" />
										</spring-form:select>
										<span class="vbg-btn btn-attesa" id="spinner-colore_id"
											style="display: none;" title="richiesta in corso...">
											richiesta in corso... </span>
										&nbsp;
										<c:if test="${inite:contains(campiGestiti, 'colore_id')}">
											<jsp:include page="../includes/funzioni_sit.jsp">
												<jsp:param name="idCampo" value="colore_id" />
											</jsp:include>
										</c:if>
									</c:if></td>
							</tr>
							<c:if
								test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldScalaInternoEspInterno')}">
								<tr id="id_localizzazione_table">
									<td><init:editLabel key="label.scala"
											role="ROLE_EDITLABEL" /></td>
									<td class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.scala" />
											<jsp:param name="idCampo" value="scala_id" />
											<jsp:param name="size" value="5" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include> &nbsp; <%-- AL MOMENTO NON SONO GESTITI DAL SIT <label for="piano_id">--%>
										<init:editLabel key="label.piano" role="ROLE_EDITLABEL" /> <%-- AL MOMENTO NON SONO GESTITI DAL SIT</label>--%>
										<jsp:include page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.piano" />
											<jsp:param name="idCampo" value="piano_id" />
											<jsp:param name="size" value="5" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
									<td class="inline-ui-cell"><init:editLabel
											key="label.interno" role="ROLE_EDITLABEL" /></td>
									<td class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.interno" />
											<jsp:param name="idCampo" value="interno_id" />
											<jsp:param name="size" value="5" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
									<td class="inline-ui-cell"><label
										for="esponenteinterno_id"> <init:editLabel
												key="label.esponente_interno" role="ROLE_EDITLABEL" /></label></td>
									<td class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.esponenteinterno" />
											<jsp:param name="idCampo" value="esponenteinterno_id" />
											<jsp:param name="size" value="5" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
								</tr>
							</c:if>

							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.fabbricato"
										role="ROLE_EDITLABEL" /></td>
								<td colspan="5" class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzestradario.fabbricato" />
										<jsp:param name="idCampo" value="fabbricato_id" />
										<jsp:param name="size" value="30" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
							</tr>

							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.km" role="ROLE_EDITLABEL" />
								</td>
								<td colspan="5" class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzestradario.km" />
										<jsp:param name="idCampo" value="km_id" />
										<jsp:param name="size" value="5" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
							</tr>
							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.catasto"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><input type="hidden"
									name="daValidare" id="daValidare" value="0" /> <c:set
										var="fnCatastoSit" value="" /> <c:if
										test="${inite:contains(campiGestiti, 'tipocatasto_id')}">
										<c:set var="fnCatastoSit">validaSIT($('tipocatasto_id'));</c:set>
									</c:if> <spring-form:select id="tipocatasto_id"
										path="istanzeFilter.istanzemappali.catasto.codice"
										onchange="${fnCatastoSit}">
										<spring-form:option value="">
											<fmt:message key="label.select.default" />
										</spring-form:option>
										<spring-form:options items="${catastoList}" itemValue="codice"
											itemLabel="descrizione" />
									</spring-form:select> <span id="spinner-tipocatasto_id" style="display: none;"
									title="richiesta in corso..." class="vbg-btn btn-attesa">
										richiesta in corso... </span> <c:if
										test="${inite:contains(campiGestiti, 'tipocatasto_id')}">
										<jsp:include page="../includes/funzioni_sit.jsp">
											<jsp:param name="idCampo" value="tipocatasto_id" />
										</jsp:include>
									</c:if></td>
								<td class="inline-ui-cell"><init:editLabel
										key="label.sezione" role="ROLE_EDITLABEL" /></td>
								<td colspan="3" class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzemappali.sezione" />
										<jsp:param name="idCampo" value="sezione_id" />
										<jsp:param name="size" value="10" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
							</tr>
							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.foglio"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzemappali.foglio" />
										<jsp:param name="idCampo" value="foglio_id" />
										<jsp:param name="size" value="5" />
										<jsp:param name="cssStyle" value="max-width: 100px;" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
								<td class="inline-ui-cell"><init:editLabel
										key="label.particella" role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzemappali.particella" />
										<jsp:param name="idCampo" value="particella_id" />
										<jsp:param name="size" value="5" />
										<jsp:param name="cssStyle" value="max-width: 100px;" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
								<td class="inline-ui-cell"><init:editLabel key="label.sub"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzemappali.sub" />
										<jsp:param name="idCampo" value="sub_id" />
										<jsp:param name="size" value="5" />
										<jsp:param name="cssStyle" value="max-width: 100px;" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
							</tr>
							<c:if
								test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldUnitaImmobiliare')}">
								<tr id="id_localizzazione_table">
									<td><init:editLabel key="label.unita_immobiliare"
											role="ROLE_EDITLABEL" /></td>
									<td colspan="5" class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzemappali.unitaimmob" />
											<jsp:param name="idCampo" value="unitaimmob_id" />
											<jsp:param name="size" value="30" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
								</tr>
							</c:if>
							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.note" role="ROLE_EDITLABEL" /></td>
								<td colspan="3" class="inline-ui-cell"><spring-form:input
										id="notestradario_id"
										path="istanzeFilter.istanzestradario.note" size="70" /></td>
								<td class="inline-ui-cell"><init:editLabel key="label.cap"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><jsp:include
										page="../includes/campo_sit.jsp">
										<jsp:param name="entityPath"
											value="istanzeFilter.istanzestradario.cap" />
										<jsp:param name="idCampo" value="cap_id" />
										<jsp:param name="size" value="5" />
										<jsp:param name="resettaCivico" value="false" />
									</jsp:include></td>
							</tr>
							<c:if
								test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldLocFrazioneCircoscrizione')}">
								<tr id="id_localizzazione_table">
									<td><init:editLabel key="label.frazione"
											role="ROLE_EDITLABEL" /></td>
									<td class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.frazione" />
											<jsp:param name="idCampo" value="frazione_id" />
											<jsp:param name="size" value="15" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
									<td class="inline-ui-cell"><init:editLabel
											key="label.quartiere" role="ROLE_EDITLABEL" /></td>
									<td class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.quartiere" />
											<jsp:param name="idCampo" value="quartiere_id" />
											<jsp:param name="size" value="15" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
									<td class="inline-ui-cell"><init:editLabel
											key="label.circoscrizione" role="ROLE_EDITLABEL" /></td>
									<td class="inline-ui-cell"><jsp:include
											page="../includes/campo_sit.jsp">
											<jsp:param name="entityPath"
												value="istanzeFilter.istanzestradario.circoscrizione" />
											<jsp:param name="idCampo" value="circoscrizione_id" />
											<jsp:param name="size" value="20" />
											<jsp:param name="resettaCivico" value="false" />
										</jsp:include></td>
								</tr>
							</c:if>


							<tr id="id_localizzazione_table">
								<td><init:editLabel key="label.longitudine"
										role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell"><spring-form:input
										id="longitudine_id"
										path="istanzeFilter.istanzestradario.longitudine" size="50" />
									<spring-form:errors
										path="istanzeFilter.istanzestradario.longitudine"
										cssClass="error" /></td>
								<td class="inline-ui-cell"><init:editLabel
										key="label.latitudine" role="ROLE_EDITLABEL" /></td>
								<td class="inline-ui-cell" colspan="3"><spring-form:input
										id="latitudine_id"
										path="istanzeFilter.istanzestradario.latitudine" size="50" />
									<spring-form:errors
										path="istanzeFilter.istanzestradario.latitudine"
										cssClass="error" /></td>
							</tr>
						</c:if>
						<%-- END SEZIONE LOCALIZZAZIONE --%>
					</c:if>
					<tr class="titoloSezione">
						<%-- <td colspan="6"><fmt:message key="label.altri_dati_progetto"/></td> --%>
						<td colspan="6"><init:editLabel
								key="label.altri_dati_progetto" role="ROLE_EDITLABEL" /></td>
					</tr>

					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldDescrizioneLavori')}">
						<tr>
							<td><init:editLabel key="label.lavori" role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><spring-form:textarea
									id="lavori_id" path="entity.lavori" cols="73" rows="3" /></td>
						</tr>

					</c:if>

					<c:if
						test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeNomeAttivita')}">
						<tr>
							<td><init:editLabel key="label.denominazione_attivita"
									role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><spring-form:input
									id="nomeattivita_id" path="entity.nomeattivita" size="70" /></td>
						</tr>
					</c:if>
					<c:if
						test="${istanzeCommand.tipoInserimento ne _INSERIMENTO_RAPIDO  or ir_mostratutti eq '1'}">
						<%--
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldNote')}">
					 --%>
						<tr>
							<td><init:editLabel key="label.lavoriestesa"
									role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><spring-form:textarea
									id="lavoriestesa_id" path="entity.lavoriestesa" cols="73"
									rows="6" /> <c:if
									test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
									<%-- <c:if test="${isModificaIstanza eq true}">--%>
										&nbsp;<a id="mod_note_ist_id" class="vbg-btn btn-salva"
										href="javascript:salvaNoteIstanza()"
										title="<fmt:message key="label.salva_note_istanza" />"> </a>
									<script type="text/javascript">
										function salvaNoteIstanza(){
													// chiamata ajax
													var valore = document.getElementById("lavoriestesa_id").value;
													new Ajax.Request('ajaxUpdateProprieta.htm', {
														  method: 'post',
														  parameters: {codiceIstanza: ${istanzeCommand.entity.id.codice}, valore: valore, campoDaModificare: 'lavoriestesa'},
														  onSuccess: function(transport){
															  var response = transport.responseText;
															  if(response!=''){
															   	alert(response);
															  }else{
															  	alert("operazione avvenuta correttamente");
															  }
														  },
														  onFailure: function(transport){ 
															var response = transport.responseText;
															console.error("Errore nella ricerca salvaNoteIstanza:" + responseTexts);		
														    }						    		 
													} );										
													
												}
										</script>
									<%--</c:if>	--%>
								</c:if></td>
						</tr>
						<%--
					</c:if>
					--%>
					</c:if>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:if test="${isSettoriVisible eq true }">
							<c:if
								test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'sezIstanzeAltreinformazioni')}">
								<tr>
									<td><c:if test="${not empty LABEL_CONTA_MQ_SETTORI}">${LABEL_CONTA_MQ_SETTORI}</c:if></td>
									<td colspan="5" class="inline-ui-cell"><c:if
											test="${not empty LABEL_CONTA_MQ_SETTORI}">
											<spring-form:input id="metriquadrati_id"
												path="entity.metriquadrati" size="10"
												cssStyle="text-align:right" readonly="readolny" />
								&nbsp;${LABEL_CONTA_MQ_UNITA_MISURA}
								<spring-form:errors path="entity.metriquadrati" cssClass="error" />
										</c:if>
										<div style="float: right; margin-right: 50px;">
											<div id="functions">
												<ul>
													<li><a
														href="javascript:historySet('${_urlback}','../istanzeattivita/list.htm?codiceIstanza=${istanzeCommand.entity.id.codice }')"><fmt:message
																key="button.altre_informazioni" /></a></li>
												</ul>
											</div>
										</div></td>
								</tr>
							</c:if>
						</c:if>
					</c:if>
					<c:if
						test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
						<c:if
							test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstanzeSelezDisattiva')}">
							<tr>
								<td><label for="disattiva_istanza_id"><init:editLabel
											key="label.seleziona_per_disattivare_istanza"
											role="ROLE_EDITLABEL" /></label></td>
								<td colspan="5" class="inline-ui-cell"><spring-form:checkbox
										id="disattiva_istanza_id" path="entity.attiva" /></td>
							</tr>
						</c:if>
					</c:if>
					<c:if test="${isModificaIntervento eq true}">
						<tr>
							<td><label class="required">*</label> <init:editLabel
									key="label.movimento_avvio" role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><script
									type="text/javascript">
									function filtertipoprocedura(element, entry) {
										if(document.getElementById("tipiprocedure_id_hidden")){
											if(document.getElementById("tipiprocedure_id_hidden").value==''){
												// alert('Attenzione è necessario specificare una procedura');
												return entry + "&codiceProcedura=-1";
											}
											return entry + "&codiceProcedura=" + document.getElementById("tipiprocedure_id_hidden").value;
										}else{
											// alert('Attenzione è necessario specificare una procedura');
											return entry + "&codiceProcedura=-1";
										}
									}
								</script> <jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="tipoMovimentoAvvio_id" />
									<jsp:param name="propertyPath"
										value="entity.tipoMovimentoAvvio" />
									<jsp:param name="pathPropertyDescription"
										value="entity.tipoMovimentoAvvio.movimento" />
									<jsp:param name="pathPropertyCode"
										value="entity.tipoMovimentoAvvio.id.tipomovimento" />
									<jsp:param name="autocompleterAjax"
										value="findMovimentiAvvioProcedura.htm" />
									<jsp:param name="titleKey" value="label.ricerca_tipimovimento" />
									<jsp:param name="ajaxCallBack" value="filtertipoprocedura" />
								</jsp:include></td>
						</tr>
					</c:if>
					<c:if test="${isModificaIntervento eq false}">
						<tr title="${whyCanNotModifyIntervento}">
							<td><init:editLabel key="label.movimento_avvio"
									role="ROLE_EDITLABEL" /></td>
							<td colspan="5" class="inline-ui-cell"><b>${istanzeCommand.entity.tipoMovimentoAvvio.movimento}</b>
							</td>
						</tr>
					</c:if>

				</table>
			</div>

		</spring-form:form>

		<div id="div_bottoni_istanza_sotto">
			<c:if
				test="${CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI eq 'div_bottoni_istanza_sotto'}">
				<jsp:include page="../includes/bottoniIstanza.jsp">
					<jsp:param name="divId" value="div_bottoni_istanza_sotto" />
				</jsp:include>
			</c:if>
		</div>

	</div>

	<script type="text/javascript">
	
	
	
	function prendiInCarico(){
			doHref('../istanze/updatePrendiIncarico.htm','Attenzione!! Si vuole contrassegnare la pratica come presa in carico?');		
	}
	function rimuoviPresaInCarico(){
		doHref('../istanze/updateRimuoviPresaIncarico.htm','Attenzione!! Si vuole rimuovere la pratica come presa in carico?');		
	}
	
	var START_OPACITY = 0.4;
	
	jQuery(function(){
		
		if(jQuery('#notificaEventinonletti').html()=="")
			return;

		jQuery('#notificaEventinonletti').fadeTo('slow',START_OPACITY,function(){ jQuery(this).css('display','block'); })
			   .hover(
					function() {
						jQuery(this).stop().animate({"opacity": "1"}, "slow");
					},
					function() {
						jQuery(this).stop().animate({"opacity": START_OPACITY}, "slow");
					});
		
		jQuery(function() {
			 var jhqrPr = jQuery.ajax({
		 			url: '../json/getTabFunzioniIstanzaPopolati.htm',
		 			context: document.body,
		 			cache: false,					  
		 			dataType: "html",
		 			method: 'GET',
		 			data: {
		 				codiceIstanza: '${istanzeCommand.entity.id.codice}'
		 			},
		 			success: function(transport) {
		 				var response =transport;
		 				//alert(response.evalJSON().endo);
		 				if(response.evalJSON().endo== 'true')
		 				{jQuery(".SchedaEndo").toggleClass("SchedaDati");}  
		 				if(response.evalJSON().schede== 'true')
		 				{jQuery(".SchedaModelliT").toggleClass("SchedaDati");}
		 				if(response.evalJSON().docIstanze== 'true')
		 				{jQuery(".SchedaDoc").toggleClass("SchedaDati");}
		 				if(response.evalJSON().oneri== 'true')
		 				{jQuery(".SchedaOneri").toggleClass("SchedaDati");}
		 				if(response.evalJSON().operazioniColl=='true')
		 				{jQuery(".SchedaAutAccesso").toggleClass("SchedaDati");}
		 				if(response.evalJSON().istanzeColl== 'true')
		 				{jQuery(".SchedaIstColl").toggleClass("SchedaDati");}
		 				if(response.evalJSON().soggColl== 'true')
		 				{jQuery(".SchedaSoggColl").toggleClass("SchedaDati");}
		 				if(response.evalJSON().autConc== 'true')
		 				{jQuery(".SchedaAut").toggleClass("SchedaDati");}
		 				},
		 		   onFailure: function(transport){ 
							var response = transport.responseText; 
						    alert("Errore nella ricerca del procedimento!");
				   }	
		 			
		 		});
			   
			
	});
		
	});
	
	function ajaxHistorySet(url){
		new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
			  method: 'get',
			  parameters: {ReturnTo: url, limit: 12},
			  onSuccess: function(transport){},
			  onFailure: function(){}			  
		});
	}
	
	function visualizzaAltreIstanze(divId, codiceIstanzestradario){
		dijit.byId(divId).show();
		visualizzaListaIstanze(codiceIstanzestradario);		
	}

	function visualizzaListaIstanze(codiceIstanzestradario){
		var _ts=new Date().getTime();
		var isCivico = true;
		var isEsponente = true;
		var isColore = true;
		var contentDiv = $('listaIstanzeAltriIndirizziDiv');
		
		var codiceStradario = '';
		var civico='';
		var esponente = '';
		var colore = '';
			
		isCivico = $('_civico'+codiceIstanzestradario).checked;
		isEsponente = $('_esponente'+codiceIstanzestradario).checked;
		isColore = $('_colore'+codiceIstanzestradario).checked;
		contentDiv = $('listaIstanzeAltriIndirizziDiv'+codiceIstanzestradario);
		
		var civico = $('civico_id'+codiceIstanzestradario).value;
		if($('esponente_id'+codiceIstanzestradario)){
		 	esponente=$('esponente_id'+codiceIstanzestradario).value;
		}
		if($('colore_id'+codiceIstanzestradario)){
			colore = $('colore_id'+codiceIstanzestradario).value;
		}
		if($('stradario_id_hidden'+codiceIstanzestradario)){
			codiceStradario = $('stradario_id_hidden'+codiceIstanzestradario).value;
		}
		var mostraLinkIstanze = false;	
		<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
			mostraLinkIstanze  = true;
		</c:if>
		
			if(codiceStradario != ''){
				new Ajax.Request('<%=request.getContextPath()%>/ajax/listaAltreIstanzeStradario.htm', {
					  method: 'post',
					  parameters: {
						  codiceStradario: codiceStradario,  
						  	isCivico:isCivico, 
						  	isEsponente:isEsponente, 
						  	isColore:isColore,
						  	civico: civico,
						  	esponente: esponente,
						  	colore: colore,
						  	_ts: _ts,
						  	codiceIstanza: '${istanzeCommand.entity.id.codice}',
						  	urlBack: escape('../istanze/view.htm?codice=${istanzeCommand.entity.id.codice}&software=${istanzeCommand.entity.software.codice}'),
						  	showLinkIstanze: mostraLinkIstanze
						  	},
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  contentDiv.innerHTML = response;			  							 
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
						console.error("Errore nella ricerca visualizzaListaIstanze:" + response);
					    contentDiv.innerHTML=response;  
					  }						  
					  });
			}

			
	}
	
	  new Draggable('draggable_menu', {
	    revert: false,
	    ghosting: true,
	    scroll: window ,
	    onStart: function(){
	    	styleDroppables('highligthDroppable');
	    },
	    onEnd: function(){
	    	styleDroppables('unHighligthDroppable');
	    }
	  });
	  
	  Droppables.add('div_bottoni_istanza_sotto', { 
		    accept: 'draggable',    
		    onDrop: function(dragged, dropped, event) {
		    	saveUserPreference('<%=WebConstants.CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI%>', 'div_bottoni_istanza_sotto');
		    	$('div_bottoni_istanza_sotto').highlight();
		    }
		  });
		  
		Droppables.add('div_bottoni_istanza_sopra', { 
		    accept: 'draggable',	   
		    onDrop: function() { 	    	
		    	saveUserPreference('<%=WebConstants.CONF_UTENTE_ISTANZE_POSIZIONE_BOTTONI%>', 'div_bottoni_istanza_sopra');
		    	$('div_bottoni_istanza_sopra').highlight();
		    }
		  });
	
	  
	  function styleDroppables(className){
		 document.getElementById('div_bottoni_istanza_sotto').className=className;
		 document.getElementById('div_bottoni_istanza_sopra').className=className;		 
	  }
	  
	  
	  function dialogoListAree(divId,istanza){
			dijit.byId(divId).show();
			listAree(istanza);		
		}
		
		
	  function listAree(istanza) {
			
			new Ajax.Request(
						'${pageContext.request.contextPath}/istanzearee/ajaxlistAree.htm?codiceIstanza='+ istanza,
						{
							method : 'post',
							onSuccess : function(transport) {							
								var response = transport.responseText;							
								$("listaAree").innerHTML =response;
								applyStyle();
							},
							onFailure : function(transport) {
								var response = transport.responseText;
								console.error("Errore nella ricerca listAree:" + response);								
							}
						});
			}

	  jQuery(document).ready(function(){
		  if($('alberoproc_descrestesa_hidden')){
			if('${fn:replace(istanzeCommand.entity.alberoproc.vwAlberoproc.scDescrizione,'\'','%27')}' != ''){									
				$('alberoproc_descrestesa_hidden').innerText='${fn:replace(istanzeCommand.entity.alberoproc.vwAlberoproc.scDescrizione,'\'','\\\'')}';
			}
		  }
		});
	  
	  async function accettaProtocollo(codiceistanza){
		  
		    var dialogWorking = new dijit.Dialog({
		       title: "Operazione in corso..." ,
		       style: "overflow:auto; width: 250px;height: 70px;",
		       content: "<img src='../images/spinner.gif'/>"
		    });		
		    dialogWorking.show();
		  
		    try{
		    	let url = "../protocollorest/ajaxAccettaProtocollo.htm?codiceIstanza="+codiceistanza;
				const response = await fetch(url, {
			        method: "POST",
			        cache: "no-cache",
			        headers: {
			            'Content-Type': 'application/json'
			        }
			    });
				//let data = await response.json();
				//console.log(data.status);
				if(response.ok){
					let accettaprotocollobuttonId = document.getElementById('accettaprotocollobutton_id');
					if(accettaprotocollobuttonId){
						console.log('Sto nascondendo il bottone');
						accettaprotocollobuttonId.style.display = 'none'; //Preferisco nasconderlo, se proprio necessario rimuoviamo l'element
						console.log('Ho nascosto il bottone');
					}
					alert("Accettazione andata a buon fine");
				}else{
					alert('Errore durante l accettazione');
				}
		    }catch(err){
		    	console.log('Errore generico su accettazione : ' + err);
		    	alert('Errore durante l accettazione');
		    }finally{
		    	dialogWorking.hide();
		    }
		    
	}
	</script>
	<c:if test="${not empty nuovoNumeroIstanza}">
		<script type="text/javascript">
			jQuery(document).ready(function(){
					if( confirm("Attenzione, Il numero di pratica risulta utilizzato.\nSostituire il numero " + 
							document.getElementById('entity_numeroistanza_id').value + 
							" con il nuovo numero ${nuovoNumeroIstanza}?" ) ){
					document.getElementById('entity_numeroistanza_id').value = '${nuovoNumeroIstanza}';				
					}
				});
		</script>
	</c:if>




</body>
</html>