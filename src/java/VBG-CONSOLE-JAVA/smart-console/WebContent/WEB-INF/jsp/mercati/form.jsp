<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<div id="subcontent"><spring-form:form commandName="mercati" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="mercati" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione"
				size="70" /> <spring-form:errors path="descrizione"
				cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.tipo_manifestazione" /></td>
            <c:if test="${mercati.id.codice==null}">
			<td><spring-form:select path="manifestazione.codice" id="tipoManifestazioneId"
				items="${listmanifestazioni}" itemLabel="descrizione"
				itemValue="codice" onchange="checkTipoconteggio()"></spring-form:select> <spring-form:errors
				path="manifestazione" cssClass="error" /></td>
			</c:if>
			<c:if test="${mercati.id.codice!=null}">
			<td>
				<spring-form:hidden id="tipoManifestazioneId" path="manifestazione.codice" 
				/>
				<spring-form:input id="tipo_manifestazione_id" path="manifestazione.descrizione" 
				size="15" readonly="true"/>
				
				</td>
			</c:if>
		</tr>
		<tr id="conteggiopresenze_id">
			<td><fmt:message key="label.tipoconteggio_presenze" /></td>
			<td>			
			<spring-form:select path="tipoconteggioPresenze">
				<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
				<spring-form:option value="1"><fmt:message key="label.tipoconteggio_presenze.valore_1" /></spring-form:option>
				<spring-form:option value="2"><fmt:message key="label.tipoconteggio_presenze.valore_2" /></spring-form:option>
			</spring-form:select> 
			<spring-form:errors	path="tipoconteggioPresenze" cssClass="error" />
			</td>
		</tr>
		
		<!-- §§§BEGIN§§§ -->
		<c:if test="${inite:isEnterprise()}">
			<tr>
				<td><fmt:message key="manifestazione.label.flagContabilita" /></td>
				<c:if test="${fn:length(mercati.mercatipresenzeTs)==0}">
					<td><spring-form:checkbox id="flagContabilita_id"
						path="flagContabilita" /> <init:help idHelp="help1"
						textKey="manifestazione.help.flag_contabilita" /> <spring-form:errors
						path="flagContabilita" cssClass="error" /></td>
				</c:if>
				<c:if test="${fn:length(mercati.mercatipresenzeTs)>0}">
					<td><spring-form:checkbox id="flagContabilita_id"
						path="flagContabilita" disabled="true" /> <init:help
						idHelp="help1" textKey="manifestazione.help.flag_contabilita" /></td>
				</c:if>
			</tr>
		</c:if>
		<!-- §§§END§§§ -->
		<tr>
			<td><fmt:message key="label.attivo" /></td>
			<td><spring-form:checkbox id="attivo_id" path="attivo" /> <init:help
				idHelp="help2" textKey="manifestazione.help.attivo" /> <spring-form:errors
				path="attivo" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.manifestazione_gestita_da_consorzi" /></td>
			<td><spring-form:checkbox id="flagConsorzio_id" path="flagConsorzio" /> 
			<spring-form:errors	path="flagConsorzio" cssClass="error" /></td>
		</tr>
		<c:if test="${mercati.id.codice==null}">
			<tr>
				<td><fmt:message key="manifestazione.label.numero_posteggi" /></td>
				<td><spring-form:input id="numeroPosteggi_id"
					path="numeroPosteggi" size="7" cssStyle="text-align:right;"
					onchange="javascript:checkNumberValue(this);javascript:isPositiveNumber(this)" /> <init:help
					idHelp="help3" textKey="manifestazione.help.numero_posteggi" /></td>
			</tr>
		</c:if>
		<c:if test="${mercati.id.codice!=null}">
			<tr>
				<td><fmt:message key="manifestazione.label.immagine" /></td>
				<td><jsp:include page="../includes/oggetti.jsp">
					<jsp:param name="idElemento" value="oggettoIdCodice" />
					<jsp:param name="codiceOggetto"
						value="${mercati.oggetto.id.codice}" />
					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
					<jsp:param name="nomefileId" value="oggetto_nomefile" />
					<jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|jpg" />
				</jsp:include> 
				<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice" />
				<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile" />
				</td>
			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td><spring-form:textarea id="note_id" path="note" cols="70"
				rows="7" /></td>
		</tr>


	</table>
	<script type='text/javascript'>
	
	function checkTipoconteggio(){
		var valore = jQuery('#tipoManifestazioneId').val();
		if(valore!='0'){ // 0 == Fiere
			jQuery('#conteggiopresenze_id').hide();
		}else{
			jQuery('#conteggiopresenze_id').show();
		}				
	}
	
	jQuery( document ).ready(function() {
		checkTipoconteggio();
	});
	
	$('descrizione_id').focus();
    </script>
    <%
	String urlStampeDoc = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_MERCATI(),"",(String)session.getAttribute(WebConstants.SOFTWARE),true);
	pageContext.setAttribute("dyn_url_stampe",urlStampeDoc);
	%>
</spring-form:form></div>
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
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listgiorni.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.giorni" /></a></li>
		<li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid2cassegnaz/list.htm?codiceMercato=${mercati.id.codice}','')"><fmt:message
			key="button.criteri_assegnazione" /></a></li>
		<!-- §§§BEGIN§§§ -->
		<c:if test="${inite:isEnterprise()}">
			<c:if test="${mercati.flagContabilita==true}">
				<li><a
					href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticonti/list.htm?mercati.id.codice%3D${mercati.id.codice}','')"><fmt:message
					key="button.conti" /></a></li>
			</c:if>
		</c:if>
		<!-- §§§END§§§ -->
		<!-- §§§BEGIN§§§ -->
		<c:if test="${inite:isEnterprise()}">
			<li><a
				href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fcalendariomercato/listusianni.htm%3Fmercati.id.codice%3D${mercati.id.codice}','')"><fmt:message
				key="button.calendario" /></a></li>
		</c:if>	
		<!-- §§§END§§§ -->
		<!-- §§§BEGIN§§§ -->
		<c:if test="${inite:isEnterprise()}">
			<c:if test="${mercati.oggetto.id.codice!=null}">
				<li><a
					href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fgestionepresenze/graficoPosteggi.htm%3FcodiceMercato%3D${mercati.id.codice}','')"><fmt:message
					key="button.mappa" /></a></li>
		</c:if>
		</c:if>
		<!-- §§§END§§§ -->
		<li><a
			href="javascript:doHref('createAltridati.htm?codice=${mercati.id.codice}','')"><fmt:message
			key="button.altri_dati" /></a></li>
        <!-- §§§BEGIN§§§ -->
        <c:if test="${inite:isEnterprise()}">
			<li><a href="javascript:void 0" onclick="window.open('${dyn_url_stampe}&CodiceMercato=${mercati.id.codice}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message
				key="button.stampa" /></a></li>
		</c:if>	
		<!-- §§§END§§§ -->	
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