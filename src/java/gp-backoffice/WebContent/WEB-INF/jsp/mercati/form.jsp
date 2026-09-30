<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
	<c:if test="${mercati.id.codice==null}">
		<fmt:message key="manifestazione.label.nuova_manifestazione.title" />
	</c:if>
	<c:if test="${mercati.id.codice!=null}">
		<fmt:message key="manifestazione.label.dettaglio_manifestazione.title" />
	</c:if>
	</title>
	<script type='text/javascript'>
		function openComunicazioniMassive(){		
			doHref('../mercati/listComunicazioni.htm?codicemercato=${mercati.id.codice}','');
		}

	</script>
</head>
<body>
<span class="titoloPagina">
<c:if test="${mercati.id.codice==null}">
	<fmt:message key="manifestazione.label.nuova_manifestazione.title" />
</c:if>
<c:if test="${mercati.id.codice!=null}">
	<fmt:message key="manifestazione.label.dettaglio_manifestazione.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../mercati/view" />
	<jsp:param name="qs" value="codice%3D${mercati.id.codice}" />
</jsp:include>
<div id="subcontent">
<div class="vbg-form">
<spring-form:form commandName="mercati" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="mercati" />
	</jsp:include>
	
	<c:set var="_URL_BACK" value="../mercati/view.htm?codice=${mercati.id.codice}" scope="page"/>
	<div class="form-group">
			<label><fmt:message key="label.descrizione" /></label>
			<spring-form:input id="descrizione_id" path="descrizione"
				size="70" /> <spring-form:errors path="descrizione"	cssClass="error" /></label>
		</div>
		<div class="form-group">
			<label><fmt:message key="label.tipo_manifestazione" /></label>
			
			
			
			
            <c:if test="${mercati.id.codice==null}">
            
	            <spring:bind path="manifestazione.codice">
	            	<select name="${status.expression}" id="tipoManifestazioneId" data-comportamento="" onchange="impostaTipoconteggio()">
					<c:set var="selected" value=""/>									
					<c:forEach var="ta" items="${listmanifestazioni}" varStatus="counter">
						<c:if test="${status.value[a.index] eq ta.codice}">
							<c:set var="selected" value="selected"/>
						</c:if>								 											            						 
						<option value="${ta.codice}" ${selected} data-comportamento="${ta.comportamentoPresenze }">${ta.descrizione}</option>
						<c:remove var="selected"/>							
					</c:forEach>            	
	            	</select>
	            </spring:bind>
			</c:if>
			<c:if test="${mercati.id.codice!=null}">
			
			 <spring:bind path="manifestazione.codice">
				<input type="hidden" id="tipoManifestazioneId" value="${status.value}" name="${status.expression}" data-comportamento="${mercati.manifestazione.comportamentoPresenze }" />
			 </spring:bind>	
			 
				<spring-form:input id="tipo_manifestazione_id" path="manifestazione.descrizione" 	size="15" readonly="true"/>
				
				</label>
			</c:if>
		</div>
		<div class="form-group" id="conteggiopresenze_id">
			<label><fmt:message key="label.tipoconteggio_presenze" /></label>
				
			<spring-form:select path="tipoconteggioPresenze">
				<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
				<spring-form:option value="1"><fmt:message key="label.tipoconteggio_presenze.valore_1" /></spring-form:option>
				<spring-form:option value="2"><fmt:message key="label.tipoconteggio_presenze.valore_2" /></spring-form:option>
			</spring-form:select> 
			<spring-form:errors	path="tipoconteggioPresenze" cssClass="error" />
			
		</div>
		
		<!-- §§§BEGIN§§§ -->
		
			<div class="form-group">
				<label><fmt:message key="manifestazione.label.flagContabilita" /></label>
				
					<spring-form:checkbox id="flagContabilita_id"
						path="flagContabilita" />
						<label style="width: 80%" for="flagContabilita_id" ><fmt:message key="manifestazione.help.flag_contabilita" /></label> 
						<spring-form:errors
						path="flagContabilita" cssClass="error" />
				
			</div>
		
		
		<!-- §§§END§§§ -->
		<c:if test="${NODO_PAGAMENTI eq true }">
		<div class="form-group">
				<label><fmt:message key="manifestazione.label.flagAttivanodoPagam" /></label>
				
				<spring-form:checkbox id="flagAttivanodoPagam_id"
						path="flagAttivanodoPagam" /> 
						<label style="width: 80%" for="flagAttivanodoPagam_id" ><fmt:message key="manifestazione.help.flagAttivanodoPagam" /></label>
						<spring-form:errors	path="flagAttivanodoPagam" cssClass="error" />
			</div>
		</c:if>
		
		
		<c:if test="${BORSELLINO_ATTIVO eq true }">
		<div class="form-group">
				<label><fmt:message key="manifestazione.label.activeWalletMarket" /></label>
				
				<spring-form:checkbox id="activeWalletMarket_id"
						path="activeWalletMarket" /> 
						<label style="width: 80%" for="activeWalletMarket_id" ><fmt:message key="manifestazione.help.activeWalletMarket" /></label>
						<spring-form:errors	path="activeWalletMarket" cssClass="error" />
			</div>
		</c:if>
		
		
		<div class="form-group">
			<label><fmt:message key="label.attivo" /></label>
			<spring-form:checkbox id="attivo_id" path="attivo" /> 
			<label style="width: 80%" for="attivo_id"><fmt:message key="manifestazione.help.attivo" /></label> 
			<spring-form:errors	path="attivo" cssClass="error" />
		</div>
		<div class="form-group">
			<label><fmt:message key="label.manifestazione_gestita_da_consorzi" /></label>
			<spring-form:checkbox id="flagConsorzio_id" path="flagConsorzio" /> 
			<spring-form:errors	path="flagConsorzio" cssClass="error" />
		</div>
		<div class="form-group">
			<label><fmt:message key="label.gestisci_posizione_posteggi" /></label>
			<spring-form:checkbox id="flagGestisciPosizioni_id" path="flagGestisciPosizioni" /> 
			<label style="width: 80%" for="flagGestisciPosizioni_id"><fmt:message key="manifestazione.help.gestisci_posizione" /></label> 
			<c:if test="${isMercatoMoreDay && mercati.flagGestisciPosizioni}">
				<b style="color: red;"><fmt:message key="label.gestisci_posizione_posteggi_no_multi_uso" /></b>
			</c:if>
			<spring-form:errors	path="flagGestisciPosizioni" cssClass="error" />
		</div>
		<div class="form-group">
			<label><fmt:message key="label.flag_temporaneo" /></label>
			<spring-form:checkbox id="flag_temporaneo_id" path="flgTemporaneo" /> 
			<label style="width: 80%" for="flag_temporaneo_id"><fmt:message key="manifestazione.help.flag_temporaneo" /></label>
			<spring-form:errors	path="flgTemporaneo" cssClass="error" />
		</div>
		<c:if test="${isAttivaGiornateNulle}">
			<div class="form-group">
				<label><fmt:message key="label.assenze_percentuale" /></label>
				<spring-form:input id="assenze_percentuale_id" path="assenzePercentuale" 	size="15" />
				<label style="width: 80%" for="assenze_percentuale_id"><fmt:message key="manifestazione.help.assenze_percentuale" /></label>
				<spring-form:errors	path="assenzePercentuale" cssClass="error" />
			</div>
			<div class="form-group">
				<label><fmt:message key="label.assenze_tipo_calcolo" /></label>
				<spring-form:select id="assenze_tipo_calcolo_id" path="assenzeTipoCalcolo">
					<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${assenzeTipoCalcoloList}" itemValue="valore" itemLabel="valore"></spring-form:options>
				</spring-form:select>	
				<label style="width: 80%" for="assenze_tipo_calcolo_id"><fmt:message key="manifestazione.help.assenze_tipo_calcolo" /></label>
				<spring-form:errors	path="assenzePercentuale" cssClass="error" />
			</div>
		
		</c:if>
		<c:if test="${mercati.id.codice==null}">
			<div class="form-group">
				<label><fmt:message key="manifestazione.label.numero_posteggi" /></label>
				<spring-form:input id="numeroPosteggi_id"
					path="numeroPosteggi" size="7" cssStyle="text-align:right;"
					onchange="javascript:checkNumberValue(this);javascript:isPositiveNumber(this)" />
					<label style="width: 80%" for="numeroPosteggi_id"><fmt:message key="manifestazione.help.numero_posteggi" /></label>
			</div>
		</c:if>
		<c:if test="${mercati.id.codice!=null}">
			<div class="form-group">
				<label><fmt:message key="manifestazione.label.immagine" /></label>
				<jsp:include page="../includes/oggetti.jsp">
					<jsp:param name="idElemento" value="oggettoIdCodice" />
					<jsp:param name="codiceOggetto"
						value="${mercati.oggetto.id.codice}" />
					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
					<jsp:param name="nomefileId" value="oggetto_nomefile" />
					<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg" />
				</jsp:include> 
				<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice" />
				<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile" />
				
			</div>
		</c:if>
		<div class="form-group">
			<label><fmt:message key="label.note" /></label>
			<spring-form:textarea id="note_id" path="note" cols="70"
				rows="7" />
		</div>
		<div class="form-group">
			<label><fmt:message key="label.circoscrizione" /></label>
			<spring-form:input id="descrizione_id" path="circoscrizione"
				size="50" /> <spring-form:errors path="circoscrizione"
				cssClass="error" />
		</div>
		
		<div class="form-group">
			<label><fmt:message key="label.mercaticategorie" /></label>
			
				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="mercatiCategorie_id" />
					<jsp:param name="propertyPath" value="mercatiCategorie" />
					<jsp:param name="pathPropertyDescription" value="mercatiCategorie.descrizione" />
					<jsp:param name="pathPropertyCode" value="mercatiCategorie.id.codice" />
					<jsp:param name="autocompleterAjax" value="findCategorieMercato.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_categorie_mercato" />
				</jsp:include>

			
		</div>
		<c:choose>
			<c:when test="${mercati.id.codice!=null}">
				<c:set var="isReadOnly" scope="page" value="true"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="isReadOnly" scope="page" value="false"></c:set>
			</c:otherwise>
		</c:choose>
		
		<jsp:include page="../includes/comboComuni.jsp">
			<jsp:param name="mostraTutti" value="true" />
			<jsp:param name="emptyLabelTutti" value="true" />
			<jsp:param name="readOnly" value="${isReadOnly}" />
			<jsp:param name="commandPropertyPath" value="comune" />
			<jsp:param name="renderFormGroup" value="true" />
			<jsp:param name="colspan" value="0" />
			<jsp:param name="comune"
				value="${mercati.comune.codicecomune}" />
			<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
		</jsp:include>
		

		<div class="form-group">
			<label><fmt:message key="label.codiceesterno" /></label>
			<spring-form:input id="codiceEsterno_id" path="codiceEsterno" size="8" maxlength="8"/>
			<spring-form:errors path="codiceEsterno"
				cssClass="error" />
		</div>
		</spring-form:form>
	</div>
</div>
	<script type='text/javascript'>
	
	function impostaTipoconteggio(){

		let elemento  = document.getElementById('tipoManifestazioneId');
		if(elemento.tagName === 'SELECT') {		
			let valoreComportamento = elemento.options[elemento.selectedIndex].dataset.comportamento
			elemento.dataset.comportamento = valoreComportamento;			
		}		
		checkTipoconteggio();
	}
	
	function checkTipoconteggio(){
		
		let valore = document.getElementById('tipoManifestazioneId').dataset.comportamento;
		if(valore!='0'){ // 0 == Fiere
			document.getElementById('conteggiopresenze_id').style.display='none';
		}else{
			document.getElementById('conteggiopresenze_id').style.display='';
		}				
	}
	
	jQuery( document ).ready(function() {
		impostaTipoconteggio();
		
	});
	
	$('descrizione_id').focus();
    </script>
    <%
	String urlStampeDoc = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_MERCATI(),"",(String)session.getAttribute(WebConstants.SOFTWARE),true);
	pageContext.setAttribute("dyn_url_stampe",urlStampeDoc);
	%>
</div>
<div id="functions">
<ul>
	<c:if test="${mercati.id.codice==null}">
		<li><a
			href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
			key="button.insert" /></a></li>
	</c:if>
	<c:if test="${mercati.id.codice!=null}">
		<li><a
			href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message
			key="button.update" /></a></li>
		<li><a
			href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
			key="button.delete" /></a></li>   
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listmercatistradario.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.localizzazione" /></a></li>
		<li><a href="javascript:doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid/list.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message
			key="button.posteggi" /></a></li>
		<li><a href="javascript:doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listmercatispunte.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message
			key="manifestazione.label.mercati_spunte" /></a></li>	
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listgiorni.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.giorni" /></a></li>
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid2cassegnaz/list.htm?codiceMercato=${mercati.id.codice}','')"><fmt:message
			key="button.criteri_assegnazione" /></a></li>
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listresponsabili.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.mercati_responsabili" /></a></li>	
		<c:if test="${mercati.flagContabilita==true}">
			<li><a
				href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticonti/list.htm?mercati.id.codice%3D${mercati.id.codice}','')"><fmt:message
				key="button.conti" /></a></li>
		</c:if>		
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fcalendariomercato/listusianni.htm%3Fmercati.id.codice%3D${mercati.id.codice}','')"><fmt:message
			key="button.calendario" /></a></li>
		<li><a
			href="javascript:historySet('${_urlback}','..%2Fmercatiformulecalcolo/list.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.formule_posteggi" /></a></li>
		<c:if test="${mercati.oggetto.id.codice!=null}">
			<li><a
				href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fgestionepresenze/graficoPosteggi.htm%3FcodiceMercato%3D${mercati.id.codice}','')"><fmt:message
				key="button.mappa" />
			</a></li>
		</c:if>
		<%-- SCHEDE --%>
		<%							
			pageContext.setAttribute("URL_MERCATODYN2_MODELLI", BackofficeNETConstants.getURL_MERCATI_DYN2_MODELLI());
		%>
		<c:set var="_URL_MERCATODYN2_MODELLI" value="${URL_MERCATODYN2_MODELLI}?CodiceMercato=${mercati.id.codice}"/>			
		<c:set var="_URL_MERCATODYN2_MODELLI" value="${inite:linkschedemercati(pageContext.request, _URL_BACK,mercati.software.codice, false,mercati.id.codice)}" />
		<li><a href="${_URL_MERCATODYN2_MODELLI}"><fmt:message key="button.schede" /></a></li>
		<li><a
			href="javascript:doHref('createAltridati.htm?codice=${mercati.id.codice}','')"><fmt:message
			key="button.altri_dati" /></a></li>
		<li><a href="javascript:void 0" onclick="window.open('${dyn_url_stampe}&CodiceMercato=${mercati.id.codice}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message
			key="button.stampa" /></a></li>
		<li><a
			href="javascript:openComunicazioniMassive()"><fmt:message
        	key="button.comunicazioni.massive" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message
		key="button.back" /></a></li>
</ul>

<c:if test="${mercati.id.codice!=null}">
	<c:if test="${mercati.flagConsorzio eq true}">
		<br class="clear" />
		<fieldset><legend><fmt:message key="label.consorzi" /></legend>
		<table style="border: 1px; border-style: dotted;  border-collapse:collapse;" cellpadding="4">
		<tr>
			<td style="font-weight:bold; border: 1px; border-style: dotted;"><fmt:message key="label.denominazione_consorzio" /></td>
			<td style="font-weight:bold; border: 1px; border-style: dotted;"><fmt:message key="label.data_inizio_esercizio" /></td>
			<td style="font-weight:bold; border: 1px; border-style: dotted;"><fmt:message key="label.data_fine_esercizio" /></td>
			<td style="font-weight:bold; border: 1px; border-style: dotted;"><fmt:message key="label.azioni" /></td>
		</tr>
		<c:forEach items="${consorzis}" var="mc" varStatus="idx">
			<tr>
				<td style="border: 1px; border-style: dotted;">${mc.consorzio.descrizioneRichiedente}</td>
				<td style="border: 1px; border-style: dotted;"><fmt:formatDate value="${mc.dataInizioEsercizio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				<td style="border: 1px; border-style: dotted;"><fmt:formatDate value="${mc.dataFineEsercizio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				<td style="border: 1px; border-style: dotted;">
					<a class="dettaglioColumn" href="viewConsorzio.htm?codicemercato=${mercati.id.codice}&codice=${mc.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${mc.id.codice}">
						<label><fmt:message key="label.edit.record.image" /></label>
					</a>
					<a border="0" class="eliminaRiga" href="javascript:doSubmit('deleteMercatiConsorzioList.htm?codicemercato=${mercati.id.codice}&codice=${mc.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" />&nbsp;${mc.id.codice}">
						<label><fmt:message key="label.elimina" /></label>
					</a>				
				</td>
			</tr>
		</c:forEach>
		</table>
		<div id="functions">
		<ul>
			<li><a	href="javascript:doHref('createConsorzio.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message key="button.new" /></a>
			</li>
		</ul>	
		</fieldset>
	</c:if>	
</c:if>


</div>
</body>
</html>