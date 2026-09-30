<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI 

--%>
<c:set var="codiceOggetto" value="" />
<c:if test="${not empty param.codiceOggetto}">
	<c:set var="codiceOggetto" value="${param.codiceOggetto}" />
</c:if>
<c:set var="identificativo" value="" />
<c:if test="${not empty param.identificativo}">
	<c:set var="identificativo" value="${param.identificativo}" />
</c:if>
<c:set var="nomeFile" value="" />
<c:if test="${not empty param.nomeFile}">
	<c:set var="nomeFile" value="${param.nomeFile}" />
</c:if>
<c:set var="pagamentoOnline" value="" />
<c:if test="${not empty param.pagamentoOnline}">
	<c:set var="pagamentoOnline" value="${param.pagamentoOnline}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI 
<pre>
--
	Identificativo: ${identificativo}
	codiceOggetto: ${codiceOggetto}
	nomeFile: ${nomeFile}
--
</pre>
--%>
<c:choose>
	<c:when test="${not empty codiceOggetto}">
		<c:if test="${pagamentoOnline eq false}">
			<form action="allegationeriREMOVE.htm" method="post" 
				id="form_allegati_oneri_remove_${identificativo}_id" 
				name="form_allegati_oneri_remove_${identificativo}">
				<input type="hidden" name="identificativo" id="identificativo_${identificativo}" value="${identificativo}" />
				<label id="${identificativo}_lbl_id" style="font-weight: bold;">${nomeFile}</label>
				<input type="button" value="<fmt:message key='button.rimuovi' />" onclick="$('#form_allegati_oneri_remove_${identificativo}_id').submit();"/> 
			</form>
		</c:if>				
	</c:when>
	<c:otherwise>
		<c:set var="hideIfOnline"></c:set>
		<c:if test="${pagamentoOnline eq true}">
			<c:set var="hideIfOnline">display: none;</c:set>
		</c:if>
		<div id="scelta_azione_${identificativo}_id" style="${hideIfOnline}">
			<input type="button" value="<fmt:message key='button.carica-ricevuta' />" onclick="$('#azione_upload_${identificativo}_id').show();$('#scelta_azione_${identificativo}_id').hide();"/>
			<c:if test="${(fn:length(nuovaIstanzaCommand.domandaOneriHelper.oneriIntervento) + fn:length(nuovaIstanzaCommand.domandaOneriHelper.oneriProcedimenti)) gt 1}">
				<input type="button" value="<fmt:message key='button.usa-ricevuta' />" onclick="$('#dialog_${identificativo}_id').dialog({ resizable: true, width:800, modal: true});"/>
			</c:if>
		</div>
		<div id="azione_upload_${identificativo}_id" style="display: none">
			<form action="allegationeriUPLOAD.htm" method="post" enctype="multipart/form-data" 
				id="form_allegati_oneri_upload_${identificativo}_id" 
				name="form_allegati_oneri_upload_${identificativo}"> 		
				<input type="hidden" name="identificativo" id="identificativo_${identificativo}" value="${identificativo}" />		
				<input type="file" name="file" id="onere_file_${identificativo}" size="40" />			
				<input type="button" value="<fmt:message key='button.carica' />" onclick="$('#form_allegati_oneri_upload_${identificativo}_id').submit();"/> 
				<input type="button" value="<fmt:message key='button.annulla' />" onclick="$('#azione_upload_${identificativo}_id').hide();$('#scelta_azione_${identificativo}_id').show();"/>
			</form>
		</div>
		<div id="azione_usa_${identificativo}_id" style="display: none">
			<div id="dialog_${identificativo}_id" title="<fmt:message key='button.usa-ricevuta' />" class="dialog">
				<form action="allegationeriUSARICEVUTA.htm" method="post" 
						id="form_allegati_oneri_usa_${identificativo}_id" 
						name="form_allegati_oneri_usa_${identificativo}">
					<input type="hidden" name="identificativo" id="identificativo_${identificativo}" value="${identificativo}" />
					<input type="hidden" name="ricevutaId" id="ricevuta_id_${identificativo}" value="" />
					<table border="0" cellpadding="2" cellspacing="0">
					<c:forEach items="${nuovaIstanzaCommand.domandaOneriHelper.oneriIntervento}" var="oi2_var" varStatus="oi2_status">
						<c:if test="${not empty oi2_var.oggettoPdf.id.codice}">
							<tr class="${oi2_status.index % 2 == 0 ? 'even' : 'odd'}">
								<td>			
								${intervento.vwAlberoproc.scDescrizione}
								-${oi2_var.tipicausalioneri.coDescrizione}
								-<fmt:formatNumber value="${oi2_var.importo}" minFractionDigits="2" /> &euro;
								-<b>${oi2_var.oggettoPdf.nomefile}</b>
								</td>
								<td>
								<input type="button" value="<fmt:message key='button.usa-ricevuta' />" onclick="$('#ricevuta_id_${identificativo}').val(${oi2_var.oggettoPdf.id.codice});if(confirm('<fmt:message key='alert.conferma-usa-ricevuta' />')){$('#form_allegati_oneri_usa_${identificativo}_id').submit();}"/>
								</td>								
							</tr>
						</c:if>
					</c:forEach>				
					<c:forEach items="${nuovaIstanzaCommand.domandaOneriHelper.oneriProcedimenti}" var="oi2_var" varStatus="oi2_status">
						<c:if test="${not empty oi2_var.oggettoPdf.id.codice}">
							<tr class="${oi2_status.index % 2 == 0 ? 'even' : 'odd'}">
								<td>	
								${oi2_var.inventarioprocedimenti.procedimento}
								-${oi2_var.tipicausalioneri.coDescrizione}
								-<fmt:formatNumber value="${oi2_var.importo}" minFractionDigits="2" /> &euro;
								-<b>${oi2_var.oggettoPdf.nomefile}</b>
								</td>
								<td>
								<input type="button" value="<fmt:message key='button.usa-ricevuta' />" onclick="$('#ricevuta_id_${identificativo}').val(${oi2_var.oggettoPdf.id.codice});if(confirm('<fmt:message key='alert.conferma-usa-ricevuta' />')){$('#form_allegati_oneri_usa_${identificativo}_id').submit();}" />
								</td>								
							</tr>
						</c:if>
					</c:forEach>
					</table>
				</form>
			</div>
		</div>	
	</c:otherwise>
</c:choose>