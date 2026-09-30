<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeT"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="form.gestionepresenze.title" /></title>
</head>
<body>

<style>

.layer_nodo_pagamenti{ 
		background-color: lightgrey; 
		padding:5px; 
		background-image: linear-gradient(45deg, #f0f0f0 5.56%, #ffffff 5.56%, #ffffff 50%, #f0f0f0 50%, #f0f0f0 55.56%, #ffffff 55.56%, #ffffff 100%);
		background-size: 12.73px 12.73px; 
		min-width: 250px; 
		border: 2px solid maroon; 
		font-size: 1.1em; 
		font-weight: bold	
	}
	
#panelSpuntistiManifestazionePosteggio{
	max-height: 400;
	overflow: scroll;
}
#panelSpuntistiManifestazione, #panelSpuntisiManifestazione{
	max-height: 400;
	overflow: scroll;
}
	

.fasespuntaattiva {
	-moz-box-shadow: 0px 0px 0px 2px #d9fbbe;
	-webkit-box-shadow: 0px 0px 0px 2px #d9fbbe;
	box-shadow: 0px 0px 0px 2px #d9fbbe;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #b8e356), color-stop(1, #a5cc52));
	background:-moz-linear-gradient(top, #b8e356 5%, #a5cc52 100%);
	background:-webkit-linear-gradient(top, #b8e356 5%, #a5cc52 100%);
	background:-o-linear-gradient(top, #b8e356 5%, #a5cc52 100%);
	background:-ms-linear-gradient(top, #b8e356 5%, #a5cc52 100%);
	background:linear-gradient(to bottom, #b8e356 5%, #a5cc52 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#b8e356', endColorstr='#a5cc52',GradientType=0);
	background-color:#b8e356;
	-moz-border-radius:10px;
	-webkit-border-radius:10px;
	border-radius:10px;
	border:1px solid #83c41a;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:19px;
	padding:12px 37px;
	text-decoration:none;
	text-shadow:0px 1px 0px #86ae47;
}
.fasespuntaattiva:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #a5cc52), color-stop(1, #b8e356));
	background:-moz-linear-gradient(top, #a5cc52 5%, #b8e356 100%);
	background:-webkit-linear-gradient(top, #a5cc52 5%, #b8e356 100%);
	background:-o-linear-gradient(top, #a5cc52 5%, #b8e356 100%);
	background:-ms-linear-gradient(top, #a5cc52 5%, #b8e356 100%);
	background:linear-gradient(to bottom, #a5cc52 5%, #b8e356 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#a5cc52', endColorstr='#b8e356',GradientType=0);
	background-color:#a5cc52;
}
.fasespuntaattiva:active {
	position:relative;
	top:1px;
}

	
	
.fasespuntadisattiva {
	-moz-box-shadow: 0px 0px 0px 2px #ffffff;
	-webkit-box-shadow: 0px 0px 0px 2px #ffffff;
	box-shadow: 0px 0px 0px 2px #ffffff;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ffffff), color-stop(1, #f6f6f6));
	background:-moz-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
	background:-webkit-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
	background:-o-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
	background:-ms-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
	background:linear-gradient(to bottom, #ffffff 5%, #f6f6f6 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#f6f6f6',GradientType=0);
	background-color:#ffffff;
	-moz-border-radius:10px;
	-webkit-border-radius:10px;
	border-radius:10px;
	border:1px solid #dcdcdc;
	display:inline-block;
	cursor:pointer;
	color:#666666;
	font-family:Arial;
	font-size:19px;
	padding:12px 37px;
	text-decoration:none;
	text-shadow:0px 1px 0px #ffffff;
}
.fasespuntadisattiva:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #f6f6f6), color-stop(1, #ffffff));
	background:-moz-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
	background:-webkit-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
	background:-o-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
	background:-ms-linear-gradient(top, #f6f6f6 5%, #ffffff 100%);
	background:linear-gradient(to bottom, #f6f6f6 5%, #ffffff 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#f6f6f6', endColorstr='#ffffff',GradientType=0);
	background-color:#f6f6f6;
}
.fasespuntadisattiva:active {
	position:relative;
	top:1px;
}

.stilePagato{
	color: #669900; 
	font-size:1.0em;
}
.stileNonPagato{
	color: #ff0000;
    font-size: 1.2em;
}

</style>
	<script type="text/javascript">
		function segnaPresenzaConcessionario(codiceMercato,usoMercato,giornoMercato,idPosteggio,id,codiceAnagrafe,idAut,anchor){	
			var url = "segnaPresenzaConcessionario.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&idPosteggio="+idPosteggio+"&codice="+id+"&codiceAnagrafe="+codiceAnagrafe+"&idAut="+idAut+'#'+anchor;
			doHref(url);
		}
		function segnaPresenzaSpuntista(codiceMercato,usoMercato,giornoMercato,idPosteggio,codiceAnagrafe,idAut,catMerc,anchor){
			if(idAut){
				var url = "segnaPresenzaSpuntista.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&idPosteggio="+idPosteggio+"&codiceAnagrafe="+codiceAnagrafe+"&idAut="+idAut+"&catMerc="+catMerc+'#'+anchor;
				doHref(url);
			}else{
				return ricercaAutorizzazione(codiceMercato,usoMercato,giornoMercato,idPosteggio,'',codiceAnagrafe,'spuntista');
			}
		}
		function segnaPresenzaSpuntistaNoPosteggio(codiceMercato,usoMercato,giornoMercato,codiceAnagrafe,idAut,catMerc,anchor){
			if(idAut){
				var url = "segnaPresenzaSpuntistaNoPosteggio.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&codiceAnagrafe="+codiceAnagrafe+"&idAut="+idAut+"&catMerc="+catMerc+'#'+anchor;
				doHref(url);
			}else{
				return ricercaAutorizzazione(codiceMercato,usoMercato,giornoMercato,'','',codiceAnagrafe,'spuntnop');
			}
		}
		function ricercaAutorizzazione(codiceMercato,usoMercato,giornoMercato,idPosteggio,id,codiceAnagrafe,occupante){
			var url = "${pageContext.request.contextPath}/autorizzazioni/listDaGestionePresenze.htm?codiceAnagrafe="+codiceAnagrafe+"&codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&idPosteggio="+idPosteggio+"&codice="+id+"&occupante="+occupante;
			doHref(url);
		}
		function mostraMappaMercato(){
		
			
			disableFunctions();
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxGraficoPosteggi.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'codiceMercato=${mercato.id.codice}&codiceUso=${uso.id.codice}',
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {	     			
	     			jQuery('#mappaMercatoPanelContent').html(data);
	     			enableFunctions();
	     			jQuery('#mappaMercatoPanel').show();
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     			enableFunctions();   			
	     			jQuery('#mappaMercatoPanelContent').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
	     			jQuery('#mappaMercatoPanel').show();
	     		}
	     	});		

		
		
		}
	</script>
	<span class="titoloPagina"><fmt:message key="form.gestionepresenze.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../gestionepresenze/list" />
	</jsp:include>
	<%-- Inserito per usare i flash messages. Come command name è stata messa la stringa dummy
	     perchè il parametro è obbligatorio, ma non necessario per questa funzionalità --%>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		 <jsp:param name="commandName" value="dummy" /> 
	</jsp:include>
	<div id="subcontent">
	<%
		String flagAssenza="display:none;";
		String flagAssenza1="display:inline;";
	%>
	<%-- 
		CODICE CHE PERMETTE DI VISUALIZZARE I MESSAGGI DI ERRORE O AVVENUTA CHIUSURA DEL GIORNO DI MERCATO 
     	IL CODICE é STATO PRESO DALLA JSP DISPAYGLOBALMESSAGE 
     --%>
    <c:if test="${param.chiusura=='ko'}">
    <div id="error_msg" class="error_header" >
        <fmt:message key="label.storicizzazionegiorno.noneseguita"/>
    </div>
    <script type="text/javascript">
        $('error_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>  
    </c:if>
    <c:if test="${param.chiusura=='ok'}">
    <div id="status_msg" class="success_header" >
    	<fmt:message key="label.storicizzazionegiorno.eseguita"/>
    </div>
    <script type="text/javascript" >
        $('status_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>
    </c:if>
    <c:if test="${param.apertura=='ko'}">
    <div id="error_msg" class="error_header" >
        <fmt:message key="label.gestionepresenze.apertura.noneseguita"/>
    </div>
    <script type="text/javascript">
        $('error_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>  
    </c:if>
    <c:if test="${param.apertura=='ok'}">
    <div id="status_msg" class="success_header" >
        <fmt:message key="label.gestionepresenze.apertura.eseguita"/>
    </div>
    <script type="text/javascript" >
        $('status_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>
    </c:if>
	<%-- END CODICE --%>	
	<c:if test="${giornoMercato.flagPresenze eq true}">
		<span class="parametri"><label><fmt:message key="label.storicizzazionegiorno.giornochiuso"/></label></span><br/>
	</c:if>
	
	
	
    <span class="parametri"><fmt:message key="form.gestionepresenze.mercato" />:<label> ${mercato.descrizione}</label>
    <c:if test="${not empty mercato.oggetto.id.codice}">
    	<a class="vbg-btn btn-gis" href="javascript:mostraMappaMercato()"></a></span>
    </c:if>
    
    <c:if test="${not empty appSpuntaURL}">
    	&nbsp;<a href="${ appSpuntaURL }" target="_new" title="Passa a APP Spunta digitale">==&gt; APP SPUNTA &lt;==</a>
    </c:if>	
    	
	<span class="parametri"><fmt:message key="form.gestionepresenze.mercatiUso" />:<label> ${uso.descrizione}</label></span>
	<span class="parametri"><fmt:message key="form.gestionepresenze.data" />:<label> ${data}</label></span><br />
	
	<span class="parametri">TIPO GIORNATA:<label> 

		<select name="conc_uso" id="concessioni_uso_id" onchange="modificaUsoGiornata()">
			<option value=""></option>
			<c:forEach items="${concessioniUsos }" var="concessioniUso">
			
				<c:set var="selected" />
				<c:if test="${concessioniUso.id.codice eq concessioniUsoSelezionato }">
					<c:set var="selected" value=" selected='selected' "/>
				</c:if>
				
				<option value="${concessioniUso.id.codice}" ${ selected }>${concessioniUso.descrizione}</option>
				
				
			</c:forEach>
		</select>
	</label></span><br />
	
	<c:if test="${isAttivaGiornateNulle }">
	
		<span class="parametri"><fmt:message key="form.gestionepresenze.flg_gg_nulla" />:<label>
			<c:choose>
				<c:when test="${giornoMercato.flagGiornataNulla eq 1}">
					<input type="checkbox" id="flag_gg_nulla_id" name="Flag giornata nulla" checked="checked" disabled="disabled"/
				</c:when>
				<c:otherwise>
					<input type="checkbox" id="flag_gg_nulla_id" name="Flag giornata nulla" disabled="disabled"/
				</c:otherwise>			
			</c:choose>
			
		</label></span><br />
			<div style="clear: both">
				<div>
					<fmt:message key="form.gestionepresenze.annotazioni_gg_nulla" />
				</div>
				<div>
					<textarea id="flag_gg_nulla_id" readonly="readonly" cols=150 rows=6>${giornoMercato.annotazioniGiornataNulla }</textarea>
				</div>
			</div>
			<c:if test="${giornoMercato.flagGiornataNulla eq 1}">
				<div id="functions" style="margin: 4px;">
					<ul>
						<li><a id="canc_gg_nulla_btn"
							href="javascript:ajaxCancellaAnnullamentoGiornata()"><fmt:message
									key="giornataNulla.button.canc" /></a></li>
					</ul>
				</div>
			</c:if>
		</c:if>
	
	
	
	
	<div id="mappaMercatoPanel" style="display: none; margin-bottom: 20px; width: 100%; border: 1px solid black;">
		
		<div id="mappaMercatoPanelContent" style="padding: 10px;">
		</div>
		
		<div id="functions" style="margin:4px;">
			<ul>
				<li><a href="javascript:jQuery('#mappaMercatoPanel').hide()"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>	
		<br class="clear">&nbsp;</br>
		
	</div>
	
	<c:choose>
		<c:when test="${giornoMercato.flagPresenze eq true}">
			
				

					
						<c:forEach items="${mercatiSpuntes }" var="ms">
							
							
								<c:set var="stileSpunta" value=""></c:set>
								<c:set var="attivacls" value="fasespuntadisattiva"></c:set>
							<c:if test="${ms.id.codice eq spuntaAttiva}">
									<c:set var="attivacls" value="fasespuntaattiva"></c:set>
								
								<c:set var="stileSpunta" value="font-size:2em;"></c:set>
							</c:if>
								
							<span class="${attivacls}">${ms.descrizione }</span>

							
						</c:forEach>
					
				
					


			
		</c:when>
		<c:otherwise>
			<c:if test="${not empty mercatiSpuntes}">	
				<div id="lista_spunte_panel">
					
						<c:forEach items="${mercatiSpuntes }" var="ms">
							<%-- ${ms.id.codice} eq ${spuntaAttiva} --%>
							<c:set var="attivacls" value="fasespuntadisattiva"></c:set>
							<c:if test="${ms.id.codice eq spuntaAttiva}">
								<c:set var="attivacls" value="fasespuntaattiva"></c:set>
							</c:if>
							<a id="raggruppatoScheda" class="${attivacls} fase_spunta" data-id="${ms.id.codice }">${ms.descrizione }</a>
							
						</c:forEach>
					
				</div>
			</c:if>
		</c:otherwise>
		</c:choose>


<c:choose>
		<c:when test="${giornoMercato.flagPresenze eq false}">
				<c:set var="titoloAppello" scope="page" value="Termina appello"></c:set>
				<c:set var="fnAppello" scope="page" value="chiudiAppello()"></c:set>
			<c:if test="${giornoMercato.flagChiusuraAppello eq true}">			
				<c:set var="titoloAppello" scope="page" value="APPELLO TERMINATO - Riapri appello"></c:set>
				<c:set var="fnAppello" scope="page" value="apriAppello()"></c:set>				
			</c:if>
			<div id="functions">
				<ul>
					<li><a id="appello_btn" href="javascript:${ fnAppello }">${ titoloAppello }</a></li>
				</ul>
			</div>
			<script type="text/javascript">
			async function ajaxCancellaAnnullamentoGiornata(){
				  const response = await fetch('../gestionepresenze/ajaxCancellaAnnullamentoGiornata.htm?id-giornata='+${giornoMercato.id.codice}, {
			  		method: 'POST',
			          headers: {
			              'Accept': 'application/json',
			              'Content-Type': 'application/json',
			          }	         
			  	});
					
				  const status = await response.status;
					
					if(status === 200){
						location.reload();
					
					} else {
						throw new Error('Si è verificato un errore');
					}
					
				} 
			
			function chiudiAppello(){
				if(confirm('Confermate di voler terminare l\'appello?')){
					disableFunctions();
					jQuery.ajax({
			     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxChiudiAppello.htm', 
			     		dataType: 'html',
			     		type: 'POST',
			     		data: 'idGiornata=${giornoMercato.id.codice}',
			     		cache: false,	
			     		success: function (data, textStatus, jqXHR) {
			     			  	
			     			enableFunctions();
			     			 jQuery('#genericDialogContainer').dialog({ 
			     				 	autoOpen: false, 
			     				 	modal : true,
			     				 	width: 500,
			     					height: 300,
			     					resizable: true,
			     					title: 'Termina appello'
			     			 	}).html(data);
			     			 jQuery('#appello_btn').text('APPELLO TERMINATO - Riapri appello');
			     			 jQuery("#appello_btn")[0].onclick = null;
			     			 jQuery('#appello_btn').click(function() { apriAppello() } );
							 jQuery('#genericDialogContainer').dialog("open");
							 
			     		},
			     		error:function (jqXHR, textStatus, errorThrown) {
			     			enableFunctions();   			
			     			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
			     		}
			     	});	
				}
			}
			function apriAppello(){
				if(confirm('Confermate di voler riaprire l\'appello?')){
					disableFunctions();
					jQuery.ajax({
			     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxApriAppello.htm', 
			     		dataType: 'html',
			     		type: 'POST',
			     		data: 'idGiornata=${giornoMercato.id.codice}',
			     		cache: false,	
			     		success: function (data, textStatus, jqXHR) {
			     			  	
			     			enableFunctions();
			     			 jQuery('#genericDialogContainer').dialog({ 
			     				 	autoOpen: false, 
			     				 	modal : true,
			     				 	width: 500,
			     					height: 300,
			     					resizable: true,
			     					title: 'Apri appello'
			     			 	}).html(data);
			     			 jQuery('#appello_btn').text('Termina appello');
			     			 jQuery("#appello_btn")[0].onclick = null;
			     			 jQuery('#appello_btn').click(function() { chiudiAppello() });
							 jQuery('#genericDialogContainer').dialog("open");							
			     		},
			     		error:function (jqXHR, textStatus, errorThrown) {
			     			enableFunctions();   			
			     			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
			     		}
			     	});	
				}
			}
			</script>
		
		
		<div style="clear:both">
			<div><fmt:message key="label.note" /></div>
			<div>
				<textarea name="note" id="note_id" cols="150" rows="6">${giornoMercato.note}</textarea>
				<a id="mod_note_id"  class="vbg-btn btn-salva"
					href="javascript:aggiornaNote()"
					 title="<fmt:message key="label.salva" />">
				</a>
			</div>
			
			<script type="text/javascript">
			function aggiornaNote(){
				if(confirm('Confermate il salvataggio delle informazioni?')){
					disableFunctions();
					jQuery.ajax({
			     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxUpdateNoteGiornata.htm', 
			     		dataType: 'html',
			     		type: 'POST',
			     		data: 'idGiornata=${giornoMercato.id.codice}&note=' + jQuery('#note_id').val(),
			     		cache: false,	
			     		success: function (data, textStatus, jqXHR) {
			     			  	
			     			enableFunctions();
			     			 jQuery('#genericDialogContainer').dialog({ 
			     				 	autoOpen: false, 
			     				 	modal : true,
			     				 	width: 500,
			     					height: 300,
			     					resizable: true,
			     					title: '<fmt:message key="label.note" />'
			     			 	}).html(data);
							 jQuery('#genericDialogContainer').dialog("open");
							 
			     		},
			     		error: function (jqXHR, textStatus, errorThrown) {
			     			enableFunctions();   			
			     			jQuery('#genericDialogContainer').dialog({ 
		     				 	autoOpen: false, 
		     				 	modal : true,
		     				 	width: 500,
		     					height: 300,
		     					resizable: true,
		     					title: '<fmt:message key="label.note" />'
		     			 	});
			     			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel salvataggio dei dati </div>");
			     			jQuery('#genericDialogContainer').dialog("open");
			     		}
			     	});	
				}
			}
			</script>
		</div>	
			
		</c:when>
		<c:otherwise>
			<div>
				<div><fmt:message key="label.note" /></div>
				<div style="border: 1px solid black; padding: 4px; margin-bottom: 10px;">
					<c:out value="${giornoMercato.note}" escapeXml="true"/>
				</div>
			</div>	
		</c:otherwise>
</c:choose>

	<c:set var="visColonnaConcessionario" value="true" />
	
	<c:if test="${not empty listaPosteggi}">
	
	<c:set var="visColonnaConcessionario" value="true" />
	
	<form name="posteggiForm" action="list.htm">
    <div class="">
	<table id="posteggiTableId" class="display compact cell-border" style="width:100%; border: 1px">
	<thead>
		<tr>
			<th>
				<fmt:message key="form.gestionepresenze.posteggio" />
				<div>
					<c:if test="${not empty visPosteggiLiberi && visPosteggiLiberi eq true}">
						<c:set var="visColonnaConcessionario" value="false" />
						<a class="vbg-btn btn-check-enabled" href="javascript:doHref('list.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&visPosteggiLiberi=false','')" title="<fmt:message key="form.gestionepresenze.vissololiberi" />" ></a>
					</c:if>
					<c:if test="${not empty visPosteggiLiberi && visPosteggiLiberi ne true}">
						<a class="vbg-btn btn-check-disabled" href="javascript:doHref('list.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&visPosteggiLiberi=true','')" title="<fmt:message key="form.gestionepresenze.vissololiberi" />" ></a>						
					</c:if>
				</div>					
			</th>
			<th>
				<fmt:message key="label.settore" />
			</th>			
			<c:if test="${ visColonnaConcessionario eq true }">
				<th><fmt:message key="form.gestionepresenze.concessionario" /></th>			
            	<th><fmt:message key="form.gestionepresenze.assenzagiustificata" /></th>
            </c:if>
			<th><fmt:message key="form.gestionepresenze.spuntista" /></th>
			
	<c:if test="${GESTISCI_PROPRIETARIO eq true}">		
				<th><fmt:message key="form.gestionepresenze.proprietario" /></th>
	</c:if>		
			
			
		</tr>
	</thead>
	<tbody>
	<%int i=0;%>
	<fmt:message key="label.ricerca_spuntista" var="ricerca_spuntista_title" />				
	<c:forEach items="${listaPosteggi}" var="var_presenza" varStatus="varIndex">
		<c:if test="${not empty var_presenza.posteggio.id.codice}">
		<c:if test="${visPosteggiLiberi ne true or (empty var_presenza.occupante.id.codice)}">
		
		<%-- se è presente il gerente AUTORIZZAZIONI_CSI mostra il gerente, altrimenti il titolare 
			 per tutti i mercati in cui la tabella AUTORIZZAZIONI_CSI non è popolata continua a funzionare
			 come prima 
		--%>
		<c:choose>
				<c:when test="${var_presenza.gerentecon!=null && var_presenza.gerentecon.id.codice !=null }">
					<c:set value="${var_presenza.gerentecon.descrizioneRichiedente}" var="concessionario_gerente_con"></c:set>
				</c:when>
				<c:otherwise>
					<c:set value="${var_presenza.concessionario.descrizioneRichiedente}" var="concessionario_gerente_con"></c:set>
				</c:otherwise>
		</c:choose>
		<tr id="anchor${varIndex.index}" class="<%=(i%2)==0?"odd":"even"%>">
			<%--colonna numero posteggio e dettaglio --%>
			<td>
			<c:if test="${empty var_presenza.occupante.id.codice}">
				<label style="font-size: large; cursor: pointer;" onclick="dettaglioPosteggio(this,${var_presenza.posteggio.id.codice}, ${var_presenza.id.codice})" for="codiceposteggio${varIndex.index}"  title="<fmt:message key="form.gestionepresenze.posteggio.libero" />">${var_presenza.posteggio.codiceposteggio}</label>
			</c:if>
			<c:if test="${not empty var_presenza.occupante.id.codice}">
				<label style="font-size: large; color: red; cursor: pointer;" for="codiceposteggio${varIndex.index}" onclick="dettaglioPosteggio(this,${var_presenza.posteggio.id.codice} , ${var_presenza.id.codice})"  title="<fmt:message key="form.gestionepresenze.posteggio.occupato" />">${var_presenza.posteggio.codiceposteggio}</label>
			</c:if>
			<c:if test="${var_presenza.posteggio.superficie gt 0}">
				<div>[${var_presenza.posteggio.superficie } mq.]</div>
			</c:if>
			</td>
			<td>
				<div class="categorie-merceologiche" data-idposteggio="${var_presenza.posteggio.id.codice}">
					<img src="${pageContext.request.contextPath}/images/spinner.gif" />
				</div>
			
			</td>
<c:if test="${ visColonnaConcessionario eq true }">		

			<%--colonna concessionario --%>
			<td>
			<%-- colonna concessionario caso1: posteggio libero e concessionario esistente --%>
			<c:if test="${empty var_presenza.occupante.id.codice and (not empty var_presenza.concessionario.id.codice)}">
			<a href="javascript:dettaglioAnagrafe(${var_presenza.concessionario.id.codice})" class="vbg-btn btn-dettaglio"></a>
			<label id="label_occupante${varIndex.index}" class="occupante">
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						<%-- ${var_presenza.concessionario.descrizioneRichiedente} --%>
						${concessionario_gerente_con}
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						<%--  ${var_presenza.concessionario.descrizioneRichiedente} --%>
						${concessionario_gerente_con}
					</c:when>
					<c:otherwise>
						<a href="javascript: segnaPresenzaConcessionario('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.posteggio.id.codice}','${var_presenza.id.codice}','${var_presenza.concessionario.id.codice }','${var_presenza.transientAutDaSchedaDyn.id.codice}','anchor${varIndex.index}')" title="Segna presenza concessionario">
						<%-- ${var_presenza.concessionario.descrizioneRichiedente} --%>
						${concessionario_gerente_con}
						</a>
					</c:otherwise>
				</c:choose>
				
			</label>
			
							
			<c:if test="${var_presenza.flagAssenzaGiust eq true}">
				<label><div>[${var_presenza.autorizzazioneConcessionarioAssente.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></label>
				
				<c:if test="${var_presenza.concessionario.id.codice ne var_presenza.autorizzazioneConcessionarioAssente.codicetitolareaut}">
					<i><fmt:message key="label.concessione_titolare"/>: ${var_presenza.autorizzazioneConcessionarioAssente.descrizioneTitolare}</i>
				</c:if>
				
			</c:if>
			</c:if>
			<%-- colonna concessionario caso2: posteggio occupato dal concessionario--%>
			<c:if test="${not empty var_presenza.occupante.id.codice and (not empty var_presenza.concessionario.id.codice) and (var_presenza.spuntista ne true)}">
			<a href="javascript:dettaglioAnagrafe(${var_presenza.concessionario.id.codice})" class="vbg-btn btn-dettaglio"></a>
			<i class="vbg-btn btn-utente" style="cursor: default;" title="<fmt:message key="form.gestionepresenze.presente" />" ></i>
			<label id="label_occupante${varIndex.index}" class="occupante">	
				
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						<%--	${var_presenza.concessionario.descrizioneRichiedente} --%>
						${concessionario_gerente_con}
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						<%-- ${var_presenza.concessionario.descrizioneRichiedente} --%>
						 ${concessionario_gerente_con}
					</c:when>
					<c:otherwise>
						<a href="javascript:doHref('eliminaPresenza.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codPresenza=${var_presenza.id.codice}#anchor${varIndex.index}','');" title="Elimina presenza concessionario">  
						 ${concessionario_gerente_con}
						</a>
					</c:otherwise>
				</c:choose>
			</label>
			
				
			<label><div>[${var_presenza.autorizzazioni.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></label>
			
			<c:if test="${var_presenza.concessionario.id.codice ne var_presenza.autorizzazioneConcessionarioAssente.codicetitolareaut}">
				<i><fmt:message key="label.concessione_titolare"/>: ${var_presenza.autorizzazioneConcessionarioAssente.descrizioneTitolare}</i>
			</c:if>
			
			</c:if>
			<c:if test="${not empty autorizzazioniConcessioni }">
				<c:set value="false" var="autcoll_spuntista_spuntista" />
				<c:forEach items="${autorizzazioniConcessioni }" var="autConc">
					<c:if test="${autConc.autorizzazioniByFkAutconcAutatt.id.codice eq var_presenza.autorizzazioneConcessionarioAssente.id.codice && autcoll_spuntista_spuntista eq 'false' }">
						<c:set value="true" var="autcoll_spuntista_spuntista" />
						<b><i class="fa fa-lg fa-link"><fmt:message key="label.autorizzazione_collegata" /></i>:&nbsp;</b>
						${autConc.autorizzazioniByFkAutconcAutcoll.transientEstremiAut }
					</c:if>
				</c:forEach>
			</c:if>
			<%-- colonna concessionario caso3: concessionario esistente posteggio occupato dallo spuntista--%>
			<c:if test="${not empty var_presenza.occupante.id.codice and (not empty var_presenza.concessionario.id.codice) and (var_presenza.spuntista eq true)}">
			<a href="javascript:dettaglioAnagrafe(${var_presenza.concessionario.id.codice})" class="vbg-btn btn-dettaglio"></a>
			<label id="label_occupante${varIndex.index}" class="occupante">
			<%-- 	${var_presenza.concessionario.descrizioneRichiedente} --%>
				 ${concessionario_gerente_con}
			</label>
			<c:if test="${var_presenza.flagAssenzaGiust eq true}">
			<label><div>[${var_presenza.autorizzazioneConcessionarioAssente.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></label>
				<c:if test="${var_presenza.concessionario.id.codice ne var_presenza.autorizzazioneConcessionarioAssente.codicetitolareaut}">
					<i><fmt:message key="label.concessione_titolare"/>: ${var_presenza.autorizzazioneConcessionarioAssente.descrizioneTitolare}</i>
				</c:if>
			</c:if>
			</c:if>
			</td>
            <%--colonna assenza giustificata --%>
            <td>
                <%-- test se mercato chiuso o no, se è aperto visualizza questa parte di codice in modalità editabile  --%>
                <c:if test="${giornoMercato.flagPresenze ne true}">          
	                <c:if test="${(var_presenza.occupante.id.codice==null or var_presenza.spuntista eq true)&& var_presenza.concessionario.id.codice!=null}">
	 				<c:if test="${var_presenza.flagAssenzaGiust eq true}">	          
	                	<input type="checkbox" id="flagAssenza${varIndex.index}" checked="checked" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', true);"/> 
				    	<textarea  rows="4" cols="20" id="txtaAssenza${varIndex.index}" >${var_presenza.motivazione}</textarea>
	                    <a class="vbg-btn btn-salva" title="<fmt:message key="label.salva" />"
	                     href="javascript:assegnaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','txtaAssenza${varIndex.index}','anchor${varIndex.index}');">
				  		</a>
	                    <c:if test="${not (var_presenza.motivazione == '' or var_presenza.motivazione == null)}">
		                    <a class="vgb-btn btn-elimina" title="<fmt:message key="label.elimina" />"
		                    href="javascript:cancellaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','anchor${varIndex.index}')">
					  		</a>
	                    </c:if>
					</c:if>
					<c:if test="${var_presenza.flagAssenzaGiust ne true}">
		            	<input type="checkbox" id="flagAssenza${varIndex.index}" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', false);" />
	                </c:if> 
	                </c:if>
                </c:if>
                <%-- test se mercato chiuso o no, se è chiuso visualizza questa parte di codice in modalità readonly --%>
                
                <c:if test="${ giornoMercato.flagPresenze eq true and var_presenza.flagAssenzaGiust eq true}">
                	
                     <c:if test="${var_presenza.flagAssenzaGiust eq true}">	          
	                	<input type="checkbox" id="flagAssenza${varIndex.index}" checked="checked" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', true);"/> 
				    	<textarea  rows="4" cols="20" id="txtaAssenza${varIndex.index}" >${var_presenza.motivazione}</textarea>
	                    <a class="vbg-btn btn-salva" title="<fmt:message key="label.salva" />"
	                     href="javascript:assegnaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','txtaAssenza${varIndex.index}','anchor${varIndex.index}');">
				  		</a>
	                    <c:if test="${not (var_presenza.motivazione == '' or var_presenza.motivazione == null)}">
		                    <a class="vgb-btn btn-elimina" title="<fmt:message key="label.elimina" />"
		                    href="javascript:cancellaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','anchor${varIndex.index}')">
					  		</a>
	                    </c:if>
					</c:if>
					<c:if test="${var_presenza.flagAssenzaGiust ne true}">
		            	<input type="checkbox" id="flagAssenza${varIndex.index}" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', false);" />
	                </c:if>         
			    </c:if>
			     <c:if test="${ giornoMercato.flagPresenze eq true and (var_presenza.flagAssenzaGiust eq false or empty var_presenza.flagAssenzaGiust) and (var_presenza.concessionario.id.codice!=null and var_presenza.spuntista eq true)}">
			    	
			    	<c:if test="${var_presenza.flagAssenzaGiust eq true}">	          
	                	<input type="checkbox" id="flagAssenza${varIndex.index}" checked="checked" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', true);"/> 
				    	<textarea  rows="4" cols="20" id="txtaAssenza${varIndex.index}" >${var_presenza.motivazione}</textarea>
	                    <a class="vbg-btn btn-salva" title="<fmt:message key="label.salva" />"
	                     href="javascript:assegnaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','txtaAssenza${varIndex.index}','anchor${varIndex.index}');">
				  		</a>
	                    <c:if test="${not (var_presenza.motivazione == '' or var_presenza.motivazione == null)}">
		                    <a class="vgb-btn btn-elimina" title="<fmt:message key="label.elimina" />"
		                    href="javascript:cancellaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','anchor${varIndex.index}')">
					  		</a>
	                    </c:if>
					</c:if>
					<c:if test="${var_presenza.flagAssenzaGiust ne true}">
		            	<input type="checkbox" id="flagAssenza${varIndex.index}" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', false);" />
	                </c:if> 
			    </c:if>
            </td>
            <%--colonna spuntista --%>
			</c:if>
			
			
			<c:set value="" var="backgroudstyle_spuntista" />
			<c:set  var="label_presenza_non_maturata_spuntista_con_posteggio" value=""/>
			
			<c:if test="${var_presenza.numeropresenze==0 && var_presenza.occupante.id.codice !=null}">
				<c:set  var="backgroudstyle_spuntista" value="#FFF164;"/>
				<c:set  var="label_presenza_non_maturata_spuntista_con_posteggio" value="Attenzione: Presenza non maturata"/>
				
			</c:if>
			
            <td style="background-color: ${backgroudstyle_spuntista}">
			<c:if test="${empty var_presenza.occupante.id.codice or (not empty var_presenza.occupante.id.codice and (var_presenza.spuntista eq true))}">
			
			<c:choose>
				<c:when test="${not empty var_presenza.occupante.id.codice and (var_presenza.spuntista eq true)}">
					<a href="javascript:dettaglioAnagrafe(${var_presenza.occupante.id.codice})" class="vbg-btn btn-dettaglio"></a>
					<label id="label_spuntista${varIndex.index}" class="spuntista">
					<i title="<fmt:message key="form.gestionepresenze.presente" />" class="vbg-btn btn-utente" style="cursor: default;"></i>
					
					<%-- se è presente il gerente AUTORIZZAZIONI_CSI mostra il gerente, altrimenti il titolare 
					 per tutti i mercati in cui la tabella AUTORIZZAZIONI_CSI non è popolata continua a funzionare
					 come prima 
					--%>
					<c:choose>
							<c:when test="${var_presenza.gerente!=null && var_presenza.gerente.id.codice !=null }">
								<c:set value="${var_presenza.gerente.descrizioneRichiedente}" var="spuntista_spuntistaGerente"></c:set>
							</c:when>
							<c:otherwise>
								<c:set value="${var_presenza.occupante.descrizioneRichiedente}" var="spuntista_spuntistaGerente"></c:set>
							</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${giornoMercato.flagRegfatte eq true}">
						${spuntista_spuntistaGerente}
						</c:when>
						<c:when test="${giornoMercato.flagPresenze eq true}">
							${spuntista_spuntistaGerente}
						</c:when>
						<c:otherwise>
							<a href="javascript:doHref('eliminaPresenza.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codPresenza=${var_presenza.id.codice}','')" title="Elimina presenza spuntista">
							${spuntista_spuntistaGerente}
							</a>
						</c:otherwise>
					</c:choose>	
					
					</label>
					<div>
						<label>
							<c:if test="${not empty var_presenza.autorizzazioni.id.codice}"><div>[${var_presenza.autorizzazioni.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></c:if>
							
							<c:if test="${var_presenza.autorizzazioni.codiceoccupaut ne var_presenza.autorizzazioni.codicetitolareaut}">
								<i><fmt:message key="label.concessione_titolare"/>: ${var_presenza.autorizzazioni.descrizioneTitolare}</i>
							</c:if>
							
						</label>
						<c:if test="${not empty var_presenza.faseSpunta}"> - <b>${var_presenza.faseSpunta}</b></c:if>
					</div>	
					<div id=funzioni>					
						<c:if test="${flagMercatoContabilita eq true}">
							<div style="display: inline" class="warning-posteggi-spuntisti" data-idpresenzad="${var_presenza.id.codice}"></div>
									
						<c:choose>
								<c:when test="${NODO_PAGAMENTI eq true}">
								
									<span class="costo-posteggio-spuntista-nodo" data-idposteggio="${var_presenza.posteggio.id.codice}" data-idpresenzad="${var_presenza.id.codice}"></span>																
								</c:when>
								<c:otherwise>				
									<span class="costo-posteggio-spuntista" data-idposteggio="${var_presenza.posteggio.id.codice}" data-idpresenzad="${var_presenza.id.codice}"></span>
									<c:set var="stilePagato" value="stilePagato" ></c:set>
									<c:choose>
										<c:when test="${var_presenza.flagPagato eq false}">
											<c:set var="stilePagato" value="stileNonPagato" ></c:set>
										</c:when>
									</c:choose>
									
									<c:choose>
										<c:when test="${giornoMercato.flagPresenze eq true}">
										
											<a class="vbg-btn btn-euro btn-euro-aggiungi registra-pagamento ${stilePagato}" 
													data-idmercatipresenza="${var_presenza.id.codice}" 
													href="javascript: void 0" id="pagamento-id-${var_presenza.id.codice}"></a>							
									
										</c:when>
										<c:otherwise>
										
												<a class="vbg-btn btn-euro btn-euro-aggiungi registra-pagamento ${stilePagato}" 
													data-idmercatipresenza="${var_presenza.id.codice}" 
													href="javascript: void 0" onclick="registraPagamento(this,'pagamento-id-${var_presenza.id.codice}');" id="pagamento-id-${var_presenza.id.codice}"></a>							
									
										</c:otherwise>
									</c:choose>
							</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<div>
					    <br />
						<b>${label_presenza_non_maturata_spuntista_con_posteggio}</b>
					</div>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						<%--visualizzo il vuoto--%>
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						<%--visualizzo il vuoto--%>
					</c:when>
					<c:otherwise>
					
					<div id="ricercaSpuntista${varIndex.index}" style="display: none">
					<span id="preferenze-posteggio-spuntista${var_presenza.id.codice}" style="display:none"></span>
					<div id="functions">			
					<ul>	
						<li><a href="javascript:ricercaTardizionale('ricercaSpuntistaTradizionale${varIndex.index}')">Anagrafe</a></li>
						<li><a href="javascript:ricercaSpuntistiGGPresenza(${var_presenza.id.codice});">Spuntisti</a></li>
					</ul>
					</div>			
							<div id="ricercaSpuntistaTradizionale${varIndex.index}" style="display: none">
								<span id="div_spuntista_search${varIndex.index}">
								<input id="anagrafe_id${varIndex.index}" type="text" class="searchbox" onkeydown="javascript:return searchAll(this,event,1)" size="40" title="${ricerca_spuntista_title}" onchange="verifica${varIndex.index}()"/>
								</span>
								<script type='text/javascript'>
								
									function verifica${varIndex.index}(){
										var valore = jQuery('#anagrafe_id${varIndex.index}_hidden').val();
										if(valore!=''){
											if(nuovaAnagrafe${varIndex.index}Win){
												nuovaAnagrafe${varIndex.index}Win.close();
											}
											var idAut='';
											var catMerc='';
											segnaPresenzaSpuntista('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.posteggio.id.codice}',valore,idAut,catMerc);
										}
									}
									function setHiddenField${varIndex.index}(inputField,listItem){
										// GIANPAOLO
										var codAnagrafe = listItem.id;	
										var idAut = listItem.lang;
										var catMerc = listItem.title;
										segnaPresenzaSpuntista('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.posteggio.id.codice}',codAnagrafe,idAut,catMerc);
									}
									var nuovaAnagrafe${varIndex.index}Win;
									function nuovaAnagrafe${varIndex.index}(objId){
										jQuery('#anagrafe_id${varIndex.index}_hidden').val('');
										jQuery('#anagrafe_id${varIndex.index}').val('');
										var caller = 'anagrafe_id${varIndex.index}';	
											if('${param.tiposoggetto}'!= '')
											{
												
												var tipo='${param.tiposoggetto}';
												nuovaAnagrafe${varIndex.index}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?visualizzaTipoAnagrafe="+tipo+"&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");								
											}else
											{
												nuovaAnagrafe${varIndex.index}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?tiposoggetto=F&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");			
											}
									}
									
									function filterspuntisti${varIndex.index}(element, entry) { 
										
										var cercaTutti = false;
										if(jQuery('#chkRicercaTutteId${varIndex.index}').attr('checked')==='checked'){
											cercaTutti = true;
										}
										return entry + "&cercaTutti=" + cercaTutti;
									}
		
								</script>
								<init:autocompleter minChars="1" callBack="filterspuntisti${varIndex.index}" afterUpdateElement="setHiddenField${varIndex.index}" methodAjax="findAnagrafeSpuntisti.htm?mercati.id.codice=${mercato.id.codice}&mercatiUso.id.codice=${uso.id.codice}"  idHidden="anagrafe_id${varIndex.index}_hidden" idInput="anagrafe_id${varIndex.index}" inputTitleKey="label.ricerca_richiedente"/>
								<input type="checkbox"  name="chkRicercaTutte${varIndex.index}" id="chkRicercaTutteId${varIndex.index}" value="on" />
								<init:help idHelp="hlpRicercaspuntisti${varIndex.index}" text="Se il checkbox non e' selezionato la ricerca verra' eseguita su tutte le anagrafiche censite altrimenti solo in quelle con autorizzazione" /> 
								</div>
						</div>
						<div id=funzioni>
							<input id="anagrafe_id${varIndex.index}_hidden" type="hidden"  />	
								<a class="vbg-btn btn-cerca" href="javascript:ricercaSpuntistaPanel('ricercaSpuntista${varIndex.index}', ${var_presenza.id.codice})"   title="cerca spuntista">
						    </a>
						    <a class="vbg-btn btn-aggiungi" href="javascript:nuovaAnagrafe${varIndex.index}('${varIndex.index}_hidden');" title="<fmt:message key="label.inserisci_anagrafe" />">
						    </a>	
							<c:if test="${flagMercatoContabilita eq true}">
								<span class="costo-posteggio-spuntista" data-idposteggio="${var_presenza.posteggio.id.codice}" data-idpresenzad="${var_presenza.id.codice}"></span>
							</c:if>
						</div>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
			</c:choose>
			</c:if>
			</td>
<c:if test="${GESTISCI_PROPRIETARIO eq true}">		
			
				
			<%-- colonna flag proprietario --%>
			<td>
				<c:if test="${not empty var_presenza.occupante.id.codice}" >
					<c:if test="${var_presenza.proprietario ne 1 && giornoMercato.flagPresenze ne true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" onclick="aggiornaProprietario('flagProprietario${varIndex.index}','${var_presenza.id.codice}');"/>
					</c:if>
					<c:if test="${var_presenza.proprietario eq 1 && giornoMercato.flagPresenze ne true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" checked="checked" onclick="aggiornaProprietario('flagProprietario${varIndex.index}','${var_presenza.id.codice}');"/>
					</c:if>
					<c:if test="${var_presenza.proprietario ne 1 && giornoMercato.flagPresenze eq true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" disabled="disabled" />
					</c:if>
					<c:if test="${var_presenza.proprietario eq 1 && giornoMercato.flagPresenze eq true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" checked="checked" disabled="disabled"/>
					</c:if>
				</c:if>
				<a name="anchor${varIndex.index}">&nbsp;</a>
			</td>
			
</c:if>		
		</tr>
		</c:if>
		</c:if>
	<%i++; %>
	</c:forEach>
	</tbody>
	</table>
	</div>
    </form>
	
	<style>
	
	
	.aut_presente{
		background-color: #90ee90;
	
	}
	
.legendSpuntisti{

	font-weight: bolder;
	font-size: 1.4em;
	padding: 10px;
	text-transform: uppercase;
}
.rTable {
  	display: table;
  	width: 100%;
}
.rTableRow {
  	display: table-row;
}
.rTableHeading {
  	display: table-header-group;
  	background-color: #DEDDCC;
}
.rTableCell, .rTableHead {
  	display: table-cell;
  	padding: 3px 10px;
  	border: 1px solid #999999;
}
.rTableHeading {
  	display: table-header-group;
  	background-color: #DEDDCC;
  	font-weight: bold;
}
.rTableFoot {
  	display: table-footer-group;
  	font-weight: bold;
  	background-color: #DEDDCC;
}
.rTableBody {
  	display: table-row-group;
}

		</style>
	
	<br class="clear" />
	<div class="titoloSezione"><fmt:message key="form.gestionepresenze.spuntistinoposteggio.list" /></div>
	
<fieldset style="display:none;" id="pannelloGraduatoriaSpuntisti">
	<legend class="legendSpuntisti" >Graduatoria Spuntisti <a href="${pageContext.request.contextPath}/gestionepresenze/ajaxEsportaGraduatoriaSpuntistiManifestazione.htm?idGiornoMercato=${giornoMercato.id.codice}"><img src="${pageContext.request.contextPath}/images/table/excel.gif" title="Estrazione in formato XLS" alt="esporta in excel">
			</a></legend>
			
		<div id="graduatoriaSpuntistiContainer" style="max-height: 400px; overflow: auto;"></div>
		
</fieldset>		
		<br >&nbsp;</br>
		<c:if test="${giornoMercato.flagPresenze ne true}">
		<label><fmt:message key="form.gestionepresenze.spuntistinoposteggio.search" /></label>
		<span><input id="anagrafe_spuntista_id" type="text" class="searchbox" onkeydown="javascript:return searchAll(this,event,1)" size="50"/></span>
		<script type='text/javascript'>
			function setHiddenFieldSpuntista(inputField,listItem){
				
				var codiceAnagrafe = listItem.id;
				var idAut = listItem.lang;
				var catMerc = listItem.title;		
				segnaPresenzaSpuntistaNoPosteggio('${mercato.id.codice}','${uso.id.codice}','${data}',codiceAnagrafe,idAut,catMerc);
			}				
			
			
			function filterspuntisti(element, entry) { 
				
				var cercaTutti = false;
				if(jQuery('#chkRicercaTutteId').attr('checked')==='checked'){
					cercaTutti = true;
				}
				return entry + "&cercaTutti=" + cercaTutti;
			}
		</script>
		
		
		<init:autocompleter minChars="1" callBack="filterspuntisti" afterUpdateElement="setHiddenFieldSpuntista" 
			methodAjax="findAnagrafeSpuntisti.htm?mercati.id.codice=${mercato.id.codice}&mercatiUso.id.codice=${uso.id.codice}" 
			idHidden="anagrafe_spuntista_id_hidden" idInput="anagrafe_spuntista_id" inputTitleKey="label.ricerca_richiedente"/>
		
		<input id="anagrafe_spuntista_id_hidden" type="hidden"  />
		
		<input type="checkbox"  name="chkRicercaTutte" id="chkRicercaTutteId" value="on" />
		<init:help idHelp="hlpRicercaspuntisti" text="Se il checkbox non e' selezionato la ricerca verra' eseguita su tutte le anagrafiche censite altrimenti solo in quelle con autorizzazione" /> 
		
		<a class="vbg-btn btn-aggiungi" href="javascript:nuovaAnagrafeSpuntisti();" title="<fmt:message key="label.inserisci_anagrafe" />"></a>
						    
								
		</c:if>
		
		<fieldset>		
				<legend class="legendSpuntisti">Spuntisti registrati 
					<a href="${pageContext.request.contextPath}/gestionepresenze/ajaxEsportaSpuntistiNoPosteggio.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}"><img src="${pageContext.request.contextPath}/images/table/excel.gif" title="Estrazione in formato XLS" alt="esporta in excel">
					</a>
					</legend>
				
		         
				<div id="spuntistiNoPosteggioContainer">
				
				</div>
		
		</fieldset>		

		
		</c:if>
</div>


<script type="text/javascript">

			var _jmesaUrl='list.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&';
			var _captionTab='<fmt:message key="form.gestionepresenze.spuntistinoposteggio.list" />';

			//Sovrascrivo le due funzioni javascript presenti in gruppoinit.js
			//è neccessario sovrascriverle perchè nella pagina sono presenti due tabelle jmesa

			// funzione per determinare le proprietà del table facade (JMesa)
			function getProperties(id) {
				var properties = new Array();
				properties[0]='occupante.descrizioneRichiedente';
				return properties;
			}

			// determina a partire dall'id del table facade della tabella di JMesa
			// il nome delle colonne
			function getColumnArray(id) {
				key = new Array();
				key[0] = '<fmt:message key="form.anagrafe.nome" />';
				return key;
			}
	
		var nuovaAnagrafeSpuntistiWin;
		function nuovaAnagrafeSpuntisti(objId){
			jQuery('#anagrafe_spuntista_id_hidden').val('');
			jQuery('#anagrafe_spuntista_id').val('');
			var caller = 'anagrafe_spuntista_id';	
				if('${param.tiposoggetto}'!= '')
				{
					var tipo='${param.tiposoggetto}';
					nuovaAnagrafeSpuntistiWin = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?visualizzaTipoAnagrafe="+tipo+"&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");								
				}else
				{
					nuovaAnagrafeSpuntistiWin = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?tiposoggetto=F&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");			
				}
		}
			
			
			
	var goToUrl = "../registrazionimercato/registrazionipresenze.htm?mercati.id.codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&giornoMercato=${data}";
	goToUrl = escape(goToUrl);
	function getParametriAssenza(idTextAreaAssenza){
		var testo=$(idTextAreaAssenza).value;
		var parametri='&testo='+escape(testo);
		return parametri;
	}
	function assegnaMotivazioneGiustificazione(codiceMercato,usoMercato,giornoMercato,codice,idTextAreaAssenza){
		var parametriAssenza = getParametriAssenza(idTextAreaAssenza);
		var url="assegnaMotivazioneGiustificazione.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&codice="+codice+parametriAssenza;
		doHref(url);
	}
	function cancellaMotivazioneGiustificazione(codiceMercato,usoMercato,giornoMercato,codice){
		var url = "cancellaMotivazioneGiustificazione.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&codice="+codice;
		doHref(url);
	}
	function displayGiustificazione(idflagAssenza,codice, nomeAncora,isChecked){
		
		var flag='';
	   	if($(idflagAssenza).checked){
			flag='true';
		} else{
		    flag='false';
	   	}
	   	if(isChecked){
	   		doHref('aggiornaFlagGiustificazione.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codice='+codice+'&flag='+flag+"&dataFine=#"+nomeAncora,'');
	   	}else{
	   		$('nomeAncora_id').value=nomeAncora;
		   	$('codice_id').value=codice;
			$('flag_id').value=flag;
			dijit.byId('pannelloSceltaSoftwareDiv').show();
	   	}
	}
	function chiudiPannelloGiustificazione(idflagAssenza,codice, nomeAncora,isChecked){
		dijit.byId('pannelloSceltaSoftwareDiv').hide();
	}
	
	function aggiornaProprietario(idFlagProprietario,codice){
		var check=''; 	  
	   	if($(idFlagProprietario).checked){
			check='true';
		}else{
		    check='false';
	   	}
	   	doHref('aggiornaFlagProprietario.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codice='+codice+'&flag='+check,'');
	}
	
	function setValuesAndGo(){
		var codice = $('codice_id').value;
		var flag = $('flag_id').value;
		var dataFine = $('dataFine_id').value;
		var nomeAncora =$('nomeAncora_id').value;
		var motivazione =$('motivazione_id').value;
		doHref('aggiornaFlagGiustificazione.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codice='+codice+'&flag='+flag+'&dataFine='+dataFine+'&motivazione='+motivazione+'#'+nomeAncora,'');		
	}
	
	
	function eliminaPresenzaSpuntista(presenzaId, autorizzazione){
						
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxEliminaPresenzaSpuntista.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'codPresenza=' + presenzaId,
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     			smarcaSpuntistaDellaGraduatoria(autorizzazione);
	     			pannelloSpuntistiNoPosteggio();
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     						     			
	     		}
	     	});	
	}

	function aggiungiSpuntista(spuntistaAutIdCodice, spuntistaAnagrafeCodice,idRigaAnagrafe){
			disableFunctions();
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxAggiungiSpuntista.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&autId=' + spuntistaAutIdCodice + '&codiceAnagrafe=' + spuntistaAnagrafeCodice,
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     			jQuery('#errorMessageSpuntistiNoPosteggioContainer').html(data);
	     			if(data!='')	
	     			{
	     			pannelloSpuntistiNoPosteggio(idRigaAnagrafe,data); // KO non ho potuto aggiungerlo perchè già presente inun posteggio per la giornata
	     		    }else
	     		    {pannelloSpuntistiNoPosteggio(idRigaAnagrafe,'');}
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     			enableFunctions();   			
	     		}
	     	});		
		
	}
	
	function pannelloSpuntistiNoPosteggio(idRigaAnagrafe,error){
		
			
		jQuery.ajax({
     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxSpuntistiNoPosteggio.htm', 
     		dataType: 'html',
     		type: 'POST',
     		data: 'codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}',
     		cache: false,	
     		success: function (data, textStatus, jqXHR) {
     			jQuery('#spuntistiNoPosteggioContainer').html(data);
     			evidenziaAggiuntiDellaGraduatoria(idRigaAnagrafe,error);
     		},
     		error:function (jqXHR, textStatus, errorThrown) {
     					     			
     			jQuery('#spuntistiNoPosteggioContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
     		}
     	});		
		
		
	}
	
	function smarcaSpuntistaDellaGraduatoria(idautorizzazione){
		
			jQuery(".graduatoriaSpuntistiCls").each(function () {
				var idautgrad = jQuery(this).data('idautorizzazione');
				if(idautorizzazione===idautgrad){
					jQuery(this).removeClass('aut_presente');
					return;
				}
			});

	}
	
	function evidenziaAggiuntiDellaGraduatoria(idRigaAnagrafe,error){
		
		enableFunctions();
		if(jQuery(".spuntistiNoPosteggioCls").length > 0){
			jQuery(".spuntistiNoPosteggioCls").each(function () {
				var idaut = jQuery(this).data('idautorizzazione');
					jQuery(".graduatoriaSpuntistiCls").each(function () {
						var idautgrad = jQuery(this).data('idautorizzazione');
						if(idautgrad===idaut){
							jQuery(this).addClass('aut_presente');
						}
					});
			});
		}else{
			jQuery(".graduatoriaSpuntistiCls").removeClass('aut_presente');
			
		}
		if(error!='')
		{
			jQuery('#'+idRigaAnagrafe).css('background-color', '#ba0000');
			jQuery('#'+idRigaAnagrafe).css('color', '#FFFFFF');
			jQuery('#'+idRigaAnagrafe).append(" - ").append(error);
		}
	}
	
	
	
	var graduatoriaFatta = false;
	function visualizzaGraduatoriaSpuntisti(){
		jQuery('#pannelloGraduatoriaSpuntisti').toggle();
		if(jQuery('#pannelloGraduatoriaSpuntisti:visible').length > 0){
			if(!window.graduatoriaFatta){
				pannelloGraduatoriaSpuntisti();
			}
		}
	}

	
	function pannelloGraduatoriaSpuntisti(){
		disableFunctions();
		jQuery.ajax({
     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxGraduatoriaSpuntistiManifestazione.htm', 
     		dataType: 'html',
     		type: 'POST',
     		data: 'idGiornoMercato=${giornoMercato.id.codice}',
     		cache: false,	
     		success: function (data, textStatus, jqXHR) {
     			enableFunctions();
     			window.graduatoriaFatta=true;
     			jQuery('#graduatoriaSpuntistiContainer').html(data);
     			evidenziaAggiuntiDellaGraduatoria();
     		},
     		error:function (jqXHR, textStatus, errorThrown) {
     			enableFunctions();	     			
     			jQuery('#graduatoriaSpuntistiContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
     		}
     	});		
		
		
	}
	
	function aggiornaCostoPosteggiSpuntisti(){
			<c:choose>
			<c:when test="${NODO_PAGAMENTI eq true}">

				jQuery(".costo-posteggio-spuntista-nodo").each(function () {
					var elemento = jQuery(this);
					var idPresenza = jQuery(this).data('idpresenzad');	
			 		jQuery.ajax({
			     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxRiepilogoPagamentiNodo.htm', 
			     		dataType: 'html',
			     		type: 'POST',
			     		data: 'idPresenza='+idPresenza,
			     		cache: false,	
			     		success: function (data, textStatus, jqXHR) {
			     			
			     			jQuery( elemento ).html(data);
			     				     			
			     		},
			     		error:function (jqXHR, textStatus, errorThrown) {
			     			
			     			
			     		}
			     	});	
				});
			</c:when>
			<c:otherwise>
				jQuery(".costo-posteggio-spuntista").each(function () {
					var elemento = jQuery(this);
					var idPosteggio = jQuery(this).data('idposteggio');		
					var idPresenza = jQuery(this).data('idpresenzad');	
				
					var anno = '<%=((String)request.getAttribute("data")).substring(6,10) %>';
					var contesto = '<%= WebConstants.MERCATO_CONTESTO_SPUNTISTI%>';
			 		jQuery.ajax({
			     		url: '${pageContext.request.contextPath}/mercaticonti/ajaxCalcolaConto.htm', 
			     		dataType: 'html',
			     		type: 'POST',
			     		data: 'idPosteggio='+ idPosteggio +'&anno='+anno+'&contesto='+contesto+'&idPresenza='+idPresenza+"&idMercatiUso=${uso.id.codice}",
			     		cache: false,	
			     		success: function (data, textStatus, jqXHR) {
			     			jQuery( elemento ).text(data);
			     		},
			     		error:function (jqXHR, textStatus, errorThrown) {
			     		}
			     	});	
				});
			</c:otherwise>
		</c:choose>	
	}
	
	jQuery(function() {
		
		pannelloSpuntistiNoPosteggio();
		
		aggiornaCostoPosteggiSpuntisti();
		
			
			

		jQuery(".warning-posteggi-spuntisti").each(function () {
			var elemento = jQuery(this);
			var idpresenzad = jQuery(this).data('idpresenzad');
					
			var jhqrPr = jQuery.ajax({
				  url: '${pageContext.request.contextPath}/gestionepresenze/ajaxWarningSpuntisti.htm?idpresenzad='+idpresenzad,
				  method: "POST",
				  context: document.body,			  
				  cache: false,					  
				  dataType: "html",
				  success: function(data, textStatus, jqXHR){
					  if(data!='OK'){
						  jQuery(elemento).data('avvisi',data);
						  jQuery(elemento).html('<span class="vbg-btn btn-avvisi" title="Avvisi"></span>').css("cursor", "pointer");
						  jQuery(elemento).click(function() {
							  mostraAvviso(elemento);
						  });
					  }else{
						  jQuery(elemento).data('avvisi','');
						  jQuery(elemento).hide();
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
				  }
					  
			});
			
		});
		
		
		
		jQuery(".registra-pagamento").each(function () {
			jQuery(this).attr('title','Verifica pagamenti');
		});
		
		
		
		jQuery('#posteggiTableId').DataTable( {
	        responsive: true,
<c:if test="${ visColonnaConcessionario eq true }">
	<c:if test="${GESTISCI_PROPRIETARIO eq true}">		
	        "aoColumnDefs": [{ "searchable": true, "aTargets": [5] }],
	    	"paging": false,
	    	"columns": [
	    	            { "orderable": false, "width": "2%" },
	    	            { "orderable": false, "width": "2%" },
	    	            { "orderable": false, "width": "60%" },
	    	            { "orderable": false, "width": "2%" },
	    	            { "orderable": false, "width": "30%" },
	    	            { "orderable": false, "width": "2%" }
	    	          ],
	</c:if>
	<c:if test="${GESTISCI_PROPRIETARIO eq false}">
    "aoColumnDefs": [{ "searchable": true, "aTargets": [3] }],
	"paging": false,
	"columns": [
	            { "orderable": false, "width": "2%" },
	            { "orderable": false, "width": "2%" },
	            { "orderable": false, "width": "60%" },
	            { "orderable": false, "width": "2%" },
	            { "orderable": false, "width": "30%" }
	          ],
	</c:if>
</c:if>	 
<c:if test="${ visColonnaConcessionario eq false }">
		<c:if test="${GESTISCI_PROPRIETARIO eq true}">
			"aoColumnDefs": [{ "searchable": true, "aTargets": [3] }],
			"paging": false,
			"columns": [
	            { "orderable": false, "width": "2%" },	
	            { "orderable": false, "width": "2%" },
	            { "orderable": false, "width": "30%" },
	            { "orderable": false, "width": "2%" }
	          ],
		 </c:if>
			
	          <c:if test="${GESTISCI_PROPRIETARIO eq false}">	          
		          "aoColumnDefs": [{ "searchable": true, "aTargets": [2] }],
					"paging": false,
					"columns": [
			            { "orderable": false, "width": "2%" },	
			            { "orderable": false, "width": "2%" },
			            { "orderable": false, "width": "30%" }
			          ],
	          </c:if>
</c:if>
	    	          
	    	          "language":{
	    	        	    "decimal":        "",
	    	        	    "emptyTable":     "Nessun dato",
	    	        	    "info":           "Visualizzati da _START_ a _END_ di _TOTAL_ record",
	    	        	    "infoEmpty":      "Visualizzati da 0 a 0 di 0 record",
	    	        	    "infoFiltered":   "(filtered from _MAX_ total entries)",
	    	        	    "infoPostFix":    "",
	    	        	    "thousands":      ",",
	    	        	    "lengthMenu":     "Visualizza _MENU_ record",
	    	        	    "loadingRecords": "Caricamento dati...",
	    	        	    "processing":     "In elaborazione...",
	    	        	    "search":         "Ricerca:",
	    	        	    "zeroRecords":    "Nessun record trovato",
	    	        	    "paginate": {
	    	        	        "first":      "Primo",
	    	        	        "last":       "Ultimo",
	    	        	        "next":       "Prossimo",
	    	        	        "previous":   "Precedente"
	    	        	    },
	    	        	    "aria": {
	    	        	        "sortAscending":  ": Attivare per ordinare in ordine crescente le colonne",
	    	        	        "sortDescending": ": Attivare per ordinare in ordine decrescente le colonne"
	    	        	    }
	    	        	},
	        initComplete: function () {
	            this.api().columns('.select-filter').every( function () {
	                var column = this;
	                
	                var select = jQuery('<select><option value=""></option></select>')
	                    .appendTo( jQuery(column.footer()).empty() )
	                    .on( 'change', function () {
	                        var val = jQuery.fn.dataTable.util.escapeRegex(
	                        		jQuery(this).val()
	                        );
	                       
	                        column
	                            .search( val ? '^'+val+'$' : '', true, false )
	                            .draw();
	                    } );
	                column.data().unique().sort().each( function ( d, j ) {
	                    select.append( '<option value="'+d+'">'+d+'</option>' );
	                } );
	            } );
	        }
	    } );
		
		
		<c:if test="${spuntaAttivaSelezionata eq false}">
			
			jQuery('#genericDialogContainer').dialog({ autoOpen: true, modal : true }).html(jQuery("#SPUNTA_WARNING_MESSAGE").html());
			
		</c:if>
		
		jQuery(".fase_spunta").each(function () {
			
			jQuery(this).css("cursor", "pointer");
			jQuery(this).click(function() {
			  
				var id = jQuery(this).data("id");
				
				disableFunctions();
				var jhqrPr = jQuery.ajax({
					  url: '${pageContext.request.contextPath}/gestionepresenze/ajaxImpostaFaseSpunta.htm',
					  method: "POST",
					  context: document.body,			  
					  cache: false,	
					  data: "giornoMercato=${giornoMercato.id.codice}&mercatiSpuntaId="+id,
					  dataType: "html",
					  success: function(data, textStatus, jqXHR){
						  enableFunctions();
						  if(data==='OK'){
						  	impostaFaseAttiva(id);
						  }else{
							  
						  }						  
					  },
					  error: function(jqXHR, textStatus, errorThrown){
					  }
						  
				});
				
			});		
		});
		
		jQuery(".categorie-merceologiche").each(function () {
			var elemento = jQuery(this);
			var idPosteggio = jQuery(this).data('idposteggio');
					
			var jhqrPr = jQuery.ajax({
				  url: '${pageContext.request.contextPath}/gestionepresenze/ajaxMerceologiePosteggio.htm',
				  method: "POST",
				  context: document.body,			  
				  cache: false,
				  data: 'idPosteggio='+idPosteggio,
				  dataType: "html",
				  success: function(data, textStatus, jqXHR){
						  jQuery(elemento).html(data);
				  },
				  error: function(jqXHR, textStatus, errorThrown){
				  }
					  
			});
			
		});
		
	});
	
	function dettaglioAnagrafe(codiceAnagrafe){
		
		
		disableFunctions();
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/ajax/dettaglioAnagrafe.htm',
			  method: "POST",
			  context: document.body,			  
			  cache: false,	
			  data: "codiceAnagrafe="+codiceAnagrafe,
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				 
				  var wWidth = jQuery(window).width();
     		        var dWidth = wWidth * 0.6;
     		        var wHeight = jQuery(window).height();
     		        var dHeight = wHeight * 0.6;
     			
     			 jQuery('#genericDialogContainer').dialog({ 
     				 	autoOpen: false, 
     				 	modal : true, 
     				 	width: dWidth,
     					height: dHeight,
     					resizable: true,
     					title: 'Dettaglio anagrafe'
     			 	}).html(data);
     			 enableFunctions();
				 jQuery('#genericDialogContainer').dialog("open");				  
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  enableFunctions();
			  }
				  
		});
	}
	
	function impostaFaseAttiva(idFaseAttiva){
		
		jQuery(".fase_spunta").each(function () {
			var id = jQuery(this).data("id");
			if(id === idFaseAttiva){
				jQuery(this).removeClass("fasespuntadisattiva");
				jQuery(this).addClass("fasespuntaattiva");
			}else{
				jQuery(this).removeClass("fasespuntaattiva");
				jQuery(this).addClass("fasespuntadisattiva");
			}
		});
		
	}
	
	 function mostraAvviso(elemento){
		 
		 jQuery('#genericDialogContainer').dialog({ autoOpen: false, modal : true }).html(jQuery(elemento).data('avvisi'));
		 jQuery('#genericDialogContainer').dialog("open");
		 
	 }
	
	function dettaglioPosteggio(elemento, idPosteggio,idPresenza){
		disableFunctions();
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/ajax/dettaglioPosteggio.htm?idMercatipresenze='+idPresenza+'&codicePosteggio='+idPosteggio+"&codiceUso=${uso.id.codice}&showInfo=true",
			  method: "POST",
			  context: document.body,			  
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  jQuery('#dialogPosteggio').dialog({ autoOpen: false, modal : true, width: 400, height: 400 }).html(data);
				 
				  jQuery('#dialogPosteggio').dialog("open"); 
				  enableFunctions();
			  },
			  error: function(jqXHR, textStatus, errorThrown){
				  enableFunctions();
			  }
				  
		});
	 
	 }
	
	function chiudiFormPagamento(idElemento, elementChecked){
	
		if(jQuery(elementChecked).prop('checked')){
			jQuery('#'+idElemento).removeClass('stileNonPagato');
			jQuery('#'+idElemento).addClass('stilePagato');			
		}else{
			jQuery('#'+idElemento).removeClass('stilePagato');
			jQuery('#'+idElemento).addClass('stileNonPagato');
		}
		jQuery('#dialogRegistraPagamento').dialog("close");
		aggiornaCostoPosteggiSpuntisti();
	}
	
	function registraPagamento(elemento, idElemento){
		
		jQuery('#pagamentiFormIdResult').hide();
		jQuery('#pagamentiFormIdResult').html('');
		
		var idpresenza = jQuery(elemento).data('idmercatipresenza');
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/gestionepresenze/ajaxFormDettaglioPagamento.htm',
			  method: "POST",
			  context: document.body,			  
			  cache: false,		
			  data: 'idmercatipresenza='+idpresenza+ '&idelemento='+idElemento,
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  
				  jQuery('#dialogRegistraPagamento').dialog({ autoOpen: false, modal : true, width: 600, height: 400 }).html(data);
				  jQuery('#dialogRegistraPagamento').dialog("open");
			  },
			  error: function(jqXHR, textStatus, errorThrown){
			  }
				  
		});
		
	}
	function salvaPagamentoPresenza(formId){
		
			jQuery('#pagamentiFormIdResult').hide();
			jQuery('#pagamentiFormIdResult').html('');
			
		
		        jQuery.ajax({
	  			  url: '${pageContext.request.contextPath}/gestionepresenze/ajaxSalvaPagamento.htm',
	  			  method: "POST",
	  			  context: document.body,			  
	  			  cache: false,
	  			  data : jQuery("#"+formId).serialize(),
	  			  dataType: "html",
	  			  success: function(data, textStatus, jqXHR){
	  				  
	  					jQuery('#pagamentiFormIdResult').addClass('success_header');
	  					jQuery('#pagamentiFormIdResult').html(data);
	  					jQuery('#pagamentiFormIdResult').show();
	  			  },
	  			  error: function(jqXHR, textStatus, errorThrown){
	  				  
	  			  }
	  			});
		}
	
		function ricercaSpuntistaPanel(divId, idpresenza){
			
			jQuery('#'+divId).toggle();
			if(jQuery('#'+divId+':visible').length > 0){
				cercaPreferenze(idpresenza);
			}			
		}
		
		function cercaPreferenze(idpresenza){
			var divId = '#preferenze-posteggio-spuntista'+idpresenza;
			
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxContaPreferenze.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'idpresenzad=' + idpresenza,
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     			if(data!='0'){
	     			  	jQuery(divId).html('<a class="vbg-btn btn-info" style="cursor: pointer" title="visualizza le preferenze" onclick="visualizzaPreferenze('+idpresenza+')" ></a>');
	     			  	jQuery(divId).show();
	     			}
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     					     			
	     			
	     		}
	     	});		
			
		}
		
		
		function visualizzaPreferenze(idpresenza){
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxVisualizzaPreferenze.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'idpresenzad=' + idpresenza,
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     			if(data!='0'){
	     				
	     				var wWidth = jQuery(window).width();
	     		        var dWidth = wWidth * 0.8;
	     		        var wHeight = jQuery(window).height();
	     		        var dHeight = wHeight * 0.8;
	     			
	     			 jQuery('#genericDialogContainer').dialog({ 
	     				 	autoOpen: false, 
	     				 	modal : true, 
	     				 	width: dWidth,
	     					height: dHeight,
	     					resizable: true,
	     					title: 'Preferenze richieste'
	     			 	}).html(data);
					 jQuery('#genericDialogContainer').dialog("open");
	     			}
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     					     			
	     			
	     		}
	     	});		
			
		}
		
		function ricercaTardizionale(divId){
			jQuery('#'+divId).toggle();
			
		}
		
		
		function ricercaSpuntistiGGPresenza(varPresenzaCodice){
			disableFunctions();
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxListaSpuntistiPosteggio.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'presenzaCodice=' + varPresenzaCodice,
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     			  	var wWidth = jQuery(window).width();
	     		        var dWidth = wWidth * 0.8;
	     		        var wHeight = jQuery(window).height();
	     		        var dHeight = wHeight * 0.8;
	     				enableFunctions();
	     			 jQuery('#graduatoriaSpuntistiPosteggiContainer').dialog({ 
	     				 	autoOpen: false, 
	     				 	modal : true, 
	     				 	width: dWidth,
	     					height: dHeight,
	     					resizable: true
	     			 	}).html(data);
					 jQuery('#graduatoriaSpuntistiPosteggiContainer').dialog("open");
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     			enableFunctions();   			
	     			jQuery('#graduatoriaSpuntistiPosteggiContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
	     		}
	     	});		
			
		}
		
		function assegnaPresenzaSpuntistaNelGiorno(idMercatiPresenzeDDEst, idMercatiPresenzeDSrc){
			
			location.href="${pageContext.request.contextPath}/gestionepresenze/segnaPresenzaSpuntistaDaLista.htm?idPresenzaDDest="+idMercatiPresenzeDDEst+"&idMercatiPresenzeDSrc="+idMercatiPresenzeDSrc+"&a=2#";

			
		}
		
		
	function segnaRinunciaSpuntistaNoPosteggio(mercatiPresenzeDid, idselect, rinuncia){
		
		var idposteggio = jQuery('#'+idselect).val();
		jQuery("#posteggioRinunciaIdMsgs").removeClass();
		
		if(idposteggio=='' && rinuncia==true){
			jQuery("#posteggioRinunciaIdMsgs").addClass('error_header alert alert-danger');
			jQuery('#posteggioRinunciaIdMsgs').html('per proseguire selezionare il posteggio');
			return;
		}
		disableFunctions();
		jQuery.ajax({
     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxUpdateSegnaRinunciaPosteggio.htm', 
     		dataType: 'html',
     		type: 'POST',
     		data: 'presenzaCodice=' + mercatiPresenzeDid+'&idposteggio='+idposteggio+'&rinuncia='+rinuncia,
     		cache: false,	
     		success: function (data, textStatus, jqXHR) {
     			 if(data==='OK'){
				 	jQuery('#genericDialogContainer').dialog("close");
				 	enableFunctions();
				 	pannelloSpuntistiNoPosteggio();
     			 }
     		},
     		error:function (jqXHR, textStatus, errorThrown) {
     			enableFunctions();   		
     			jQuery("#posteggioRinunciaIdMsgs").removeClass();
 				jQuery("#posteggioRinunciaIdMsgs").addClass('error_header alert alert-danger');
     			alert("Si è verificato un errore nel caricamento dei dati");
     		}
     	});		
		
	}
		
	function segnaRinunciaAlPosteggio(mercatiPresenzeDid){
		
		disableFunctions();
		jQuery.ajax({
     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxSegnaRinunciaPosteggio.htm', 
     		dataType: 'html',
     		type: 'POST',
     		data: 'presenzaCodice=' + mercatiPresenzeDid,
     		cache: false,	
     		success: function (data, textStatus, jqXHR) {
     			  	
     				enableFunctions();
     			 jQuery('#genericDialogContainer').dialog({ 
     				 	autoOpen: false, 
     				 	modal : true,
     				 	width: 500,
     					height: 300,
     					resizable: true,
     					title: 'Rinuncia al posteggio'
     			 	}).html(data);
				 jQuery('#genericDialogContainer').dialog("open");
     		},
     		error:function (jqXHR, textStatus, errorThrown) {
     			enableFunctions();   			
     			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
     		}
     	});		
		
	}
		
		
	function assegnaPosteggiospuntistaNelGiorno(mercatiPresenzeDid){

		jQuery('#presenzaDIdNoPosteggioID').val(mercatiPresenzeDid);
		var wWidth = jQuery(window).width();
        var dWidth = wWidth * 0.8;
        var wHeight = jQuery(window).height();
        var dHeight = wHeight * 0.8;
		jQuery('#presenzaSpuntistaPosteggioContainer').dialog({ 
			 	autoOpen: false, 
			 	modal : true, 
			 	width: dWidth,
				height: dHeight,
				resizable: true
		 	});
	 jQuery('#presenzaSpuntistaPosteggioContainer').dialog("open");
		
	}
	function presenzaSpuntistaNoPosteggio(){
		
		
		var idPosteggio = jQuery('#posteggiLiberiId').val();
		var mercpresenzeDId = jQuery('#presenzaDIdNoPosteggioID').val();
			
		doHref('updatePresenzaSpuntistaNoPosteggio.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&idPosteggio='+idPosteggio+'&presenzaDId='+mercpresenzeDId,'');
		
	}
	
	
	
	function modificaUsoGiornata(){
		if(confirm('Attenzione! Si intende procedere alla modifica della configurazione?')){
			if( jQuery('#concessioni_uso_id').val() == ''){
				alert("E' necessario selezionare un valore");
				return;
			}
			disableFunctions();
			jQuery.ajax({
	     		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxModificaUsoGiornata.htm', 
	     		dataType: 'html',
	     		type: 'POST',
	     		data: 'giornoMercatoIdCodice=${giornoMercato.id.codice}&concessioneusoid='+ jQuery('#concessioni_uso_id').val(),
	     		cache: false,	
	     		success: function (data, textStatus, jqXHR) {
	     				enableFunctions();
	     				aggiornaCostoPosteggiSpuntisti();
	     		},
	     		error:function (jqXHR, textStatus, errorThrown) {
	     			enableFunctions();   			
	     			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
	     		}
	     	});		
		}
	}
	
	
	</script>
	<%
		MercatipresenzeT giornoMM = (MercatipresenzeT)request.getAttribute("giornoMercato");
		String urlStampeDoc = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_LETTERE_TIPO()+"?idgiorno="+giornoMM.getId().getCodice()+"&fkcodicemercato="+giornoMM.getMercato().getId().getCodice()+"&fkidmercatiuso="+giornoMM.getMercatoUso().getId().getCodice(),"",(String)session.getAttribute(WebConstants.SOFTWARE),true);
		pageContext.setAttribute("dyn_url_stampe",urlStampeDoc);
	%>
		
	<div dojoType="dijit.Dialog" id="pannelloSceltaSoftwareDiv" title="<fmt:message key="label.gestione_presenze.gestisci_assenza" />"  style="display: none; height: auto;">
			<div style="width:400px; height: 200px;">


		<div>
			<fmt:message key="label.gestione_presenze.gestisci_assenza.help" />			
		</div>	
		<input type="hidden" name="codice" id="codice_id" />	
		<input type="hidden" name="flag" id="flag_id" />
		<input type="hidden" name="nomeAncora" id="nomeAncora_id" />
		<input type="hidden" name="proprietario" id="propiretario_id" />
		<table>
		
		    <tr>
				<td><fmt:message key="label.data_fine" /></td>
				<td><input type="text" size="8" name="dataFine" id="dataFine_id" onblur="isValidDate(this,true);" value="${data}"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.motivazione" /></td>
				<td><textarea rows="6" cols="30" name="" id="motivazione_id" ></textarea></td>
		    <tr>
		</table>		
			<div id="functions">
				<ul>
					<li id="OkId"><a href="javascript:setValuesAndGo()"><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void 0" onclick="dijit.byId('pannelloSceltaSoftwareDiv').hide();"><fmt:message key="button.annulla" /></a></li>
				</ul>
			</div>
			<br class="clear" />	
			</div>
		</div>
	
	
	<div id="functions">
	
	<ul>
		<c:if test="${giornoMercato.flagPresenze ne true}">
			<c:if test="${nascondiBottoneConcPres ne true}">
				<li><a href="javascript:doHref('segnaPresentiTuttiConcessionari.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&useHistoryBack=${useHistoryBack}','')"><fmt:message key="button.insert.tuttiConcessionari" /></a></li>
			</c:if>

			<li><a href="javascript:doHref('chiudiGiornoMercato.htm?mercato.id.codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&data=${data}','<fmt:message key="button.storicizzazionegiorno.alert" />')"><fmt:message key="button.storicizzazionegiorno.closemarket" /></a></li>
		</c:if>
		<c:if test="${giornoMercato.flagPresenze eq true and giornoMercato.flagPresenzeArchivio eq false}">
			<li><a href="javascript:doHref('apriGiornoMercato.htm?mercato.id.codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&data=${data}','<fmt:message key="button.aperturagiorno.alert" />')"><fmt:message key="button.aperturagiorno" /></a></li>
		</c:if>
	
		
			<li><a href="javascript: void 0;" onclick="window.open('${dyn_url_stampe}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message	key="button.print" /></a></li>
		
		
			<% String idTemp=String.valueOf(System.currentTimeMillis()); %>	      
			<c:if test="${anagrafeParixAttivo eq true }">			    	 	
		    	<li><a href="javascript:visuraParixFN<%=idTemp%>()"><fmt:message key="button.visura_infocamere" /></a></li>
		    </c:if>		

			<li><a href="javascript:visualizzaGraduatoriaSpuntisti()"><fmt:message key="label.mostra_graduatoria_spuntisti" /></a></li>
			
			<li><a href="javascript:openExport();"><fmt:message key="button.esporta" /></a></li>
			
						
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	
		
		
		
		
	</ul>
	
	<c:if test="${anagrafeParixAttivo eq true }">
		<jsp:include page="../includes/ricercaimpreseparix.jsp">
			<jsp:param name="identificativoTemporaneoParix" value="<%= idTemp%>" />
		</jsp:include>
	</c:if>	
	<div id="dialogPosteggio" style="display: none;"></div>
	<div id="dialogRegistraPagamento" style="display: none;"></div>
	<div id="dialogRicercaSpuntistaDiv" style="display: none;"></div>
	<div id="graduatoriaSpuntistiPosteggiContainer" style="display: none;"></div>
	
	<div id="SPUNTA_WARNING_MESSAGE" style="display: none;">
		<div id="status_msg" class="error_header alert alert-danger" >
	    	Attenzione!! il mercato prevede la gestione delle fasi di spunta!<br>Selezionare la fase.</br>	
	    </div>
	</div>
	
	<div id="genericDialogContainer" style="display:hidden"></div>
	<br class="clear" />	
	<div id="presenzaSpuntistaPosteggioContainer" style="display:none">
	
	Seleziona il posteggio da assegnare:
	<input type="hidden" id="presenzaDIdNoPosteggioID" value="" />
	<select id="posteggiLiberiId">
	
	<c:forEach items="${listaPosteggi}" var="var_presenza" varStatus="varIndex">
		<c:if test="${not empty var_presenza.posteggio.id.codice}">
			<c:if test="${visPosteggiLiberi ne true or (empty var_presenza.occupante.id.codice)}">		
				<c:if test="${empty var_presenza.occupante.id.codice}">
					<option value="${var_presenza.posteggio.id.codice}">${var_presenza.posteggio.codiceposteggio} </option>
				</c:if>			
			</c:if>
		</c:if>
	</c:forEach>		
	</select>	
	<div id="functions">	
		<ul>
			<li><a href="javascript:presenzaSpuntistaNoPosteggio()"><fmt:message key="label.assegna" /></a></li>					
		</ul>			
	</div>
	
</div>
</div>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.18/r-2.2.2/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.18/r-2.2.2/datatables.min.js"></script>
	
<script type="text/javascript">



	

function openExport(){		
	goToExportPentahoPanel();
}

function goToExportPentahoPanel(){
	
	var url  = URLDecode('${_urlback}');			
	ajaxHistorySet(url);			
	setTimeout("doHref('../gestionepresenze/createExportModalitaPentaho.htm?giornoMercato=${giornoMercato.id.codice}','')",10);
}

function ajaxHistorySet(url){
	
	var jhqr = jQuery.ajax({
		  url: '../history/ajaxSet.htm?ReturnTo='+url,
		  context: document.body,
		  cache: false,				
		  dataType: "html",
		  success: function(data) { 				   
			} 
		});	
}
  
function deletePosizioneDebitoria(idPresenza){
	if(confirm('Attenzione! Confermate di voler annullare il pagamento inserito?\nL\'operazione sarà salvata nei logs')){
		
		disableFunctions();
		jQuery.ajax({
	 		url: '${pageContext.request.contextPath}/gestionepresenze/ajaxAnnullaPosizioneDebitoria.htm', 
	 		dataType: 'html',
	 		type: 'POST',
	 		data: 'idPresenza=' + idPresenza,
	 		cache: false,	
	 		success: function (data, textStatus, jqXHR) {
	 			if(data!='OK'){
	 				alert('Si è verificato un errore: ' + data);
	 			}
	 			enableFunctions();
	 			aggiornaCostoPosteggiSpuntisti();
	 		},
	 		error:function (jqXHR, textStatus, errorThrown) {
	 			enableFunctions();   			
	 			aggiornaCostoPosteggiSpuntisti();
	 			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
	 		}
	 	});		
	}
}
  

</script>	
</body>
</html>