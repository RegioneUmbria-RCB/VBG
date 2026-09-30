<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.upgr.core.FileUtils"%>
<%@ page import="it.gruppoinit.pal.gp.areariservata.web.util.FileUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<div class="titolo_sezione"></div>
	<div class="sezione">
	<table class="sezione_table" border="0" cellpadding="5">
		<thead>
			<tr class="sezione_table_h">
				<th width="5%">&nbsp;</th>
				<th width="30%"><fmt:message key='label.scheda' /></th>
				<th width="65%"><fmt:message key='label.documento' /></th>
			</tr>
		</thead>	
		<c:forEach items="${nuovaIstanzaCommand.listaPDFSchede }" var="pdfScheda" varStatus="count">	
		<c:set var="row" value="" />
		<c:choose>
			<c:when test="${count.index % 2 == 0 }"><c:set var="row" value="_odd" /></c:when>
			<c:otherwise><c:set var="row" value="" /></c:otherwise>
		</c:choose>
		<tr class="sezione_table_row${row }">
			<td width="5%">
				<c:if test="${pdfScheda.firmaObbligatoria }">
				firma obbligatoria
				</c:if>
			</td>
			<td class="sezione_table_label2" width="30%">${pdfScheda.scheda.nome }</td>
			<c:choose>
			<c:when test="${pdfScheda.bloccoMultiplo eq true }">
			<td colspan="3" width="65%">
				<table border="0" width="100%">
					<c:forEach items="${pdfScheda.listaPDFBlocchiSchedaDinamica }" var="pdfSchedaBlocco" varStatus="countBlocco">
					<c:set var="innerRow" value="" />
					<c:choose>
						<c:when test="${countBlocco.index % 2 == 0 }"><c:set var="innerRow" value="_odd" /></c:when>
						<c:otherwise><c:set var="innerRow" value="" /></c:otherwise>
					</c:choose>
					<tr class="sezione_table_row${innerRow }">
						<td class="sezione_table_label2" width="50%">
						<c:set var="idOggettoBlocco" value="${pdfSchedaBlocco.documento.allegati.id }"></c:set>
						<%
						String qs="id="+pageContext.getAttribute("idOggettoBlocco");
						qs=FileUtils.getLinkForFile(qs);
						%>
						<a href="../ajax/download.htm?<%=qs%>" target="_new">${pdfSchedaBlocco.documento.allegati.allegato }</a>
						</td>
						<td align="right" width="10%">
							<c:if test="${pdfSchedaBlocco.firmaObbligatoria && not pdfSchedaBlocco.allegatoDaUtente }">
							<input type="checkbox" name="alla-firma" id="${pdfSchedaBlocco.documento.allegati.id }" value="${pdfSchedaBlocco.documento.allegati.id }" title="<fmt:message key='label.metti-alla-firma' />" />
							</c:if>
						</td>
						<td class="sezione_table_buttons" width="40%">
							<c:if test="${pdfSchedaBlocco.firmaObbligatoria && not pdfSchedaBlocco.allegatoDaUtente }">				
							<spring-form:form action="allegatoFirmatoUPLOAD.htm" method="post" commandName="nuovaIstanzaCommand" enctype="multipart/form-data" name="form_allegato_firmato_upload_${count.index}_${countBlocco.index }">				
								<input type="file" name="file" id="file-${count.index}_${countBlocco.index }" size="40" />
								<input type="hidden" name="codiceScheda" value="${pdfScheda.scheda.codice }" />
								<input type="hidden" name="codiceBloccoScheda" value="${pdfSchedaBlocco.codice }" />
								<input type="button" value="<fmt:message key='button.carica' />" onclick="uploadAllegatoFirmato('${count.index}_${countBlocco.index }')" />
							</spring-form:form>
							</c:if>
							<c:if test="${pdfSchedaBlocco.allegatoDaUtente }">
							<spring-form:form action="allegatoFirmatoREMOVE.htm" method="post" commandName="nuovaIstanzaCommand" name="form_allegato_firmato_remove_${count.index}_${countBlocco.index }">				
								<input type="hidden" name="codiceScheda" value="${pdfScheda.scheda.codice }" />
								<input type="hidden" name="codiceBloccoScheda" value="${pdfSchedaBlocco.codice }" />
								<input type="button" value="<fmt:message key='button.elimina' />" onclick="removeAllegatoFirmato('${count.index}_${countBlocco.index }')" />
							</spring-form:form>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</td>
			</c:when>
			<c:otherwise>
			<td colspan="3" width="65%">
				<table border="0" width="100%">
				<tr>
					<td class="sezione_table_label2" width="50%">
						<c:set var="idOggetto" value="${pdfScheda.documento.allegati.id }"></c:set>
						<%
						String qs="id="+pageContext.getAttribute("idOggetto");
						qs=FileUtils.getLinkForFile(qs);
						%>
						<a href="../ajax/download.htm?<%=qs%>" target="_new">${pdfScheda.documento.allegati.allegato }</a>
					</td>
					<td align="right" width="10%">
						<c:if test="${pdfScheda.firmaObbligatoria && not pdfScheda.allegatoDaUtente }">
						<input type="checkbox" name="alla-firma" id="${pdfScheda.documento.allegati.id }" value="${pdfScheda.documento.allegati.id }" title="<fmt:message key='label.metti-alla-firma' />" />
						</c:if>
					</td>
					<td class="sezione_table_buttons" width="40%">
						<c:if test="${pdfScheda.firmaObbligatoria && not pdfScheda.allegatoDaUtente }">				
						<spring-form:form action="allegatoFirmatoUPLOAD.htm" method="post" commandName="nuovaIstanzaCommand" enctype="multipart/form-data" name="form_allegato_firmato_upload_${count.index}">				
							<input type="file" name="file" id="file-${count.index}" size="40" />
							<input type="hidden" name="codiceScheda" value="${pdfScheda.scheda.codice }" />
							<input type="button" value="<fmt:message key='button.carica' />" onclick="uploadAllegatoFirmato('${count.index}')" />
						</spring-form:form>
						</c:if>
						<c:if test="${pdfScheda.allegatoDaUtente }">
						<spring-form:form action="allegatoFirmatoREMOVE.htm" method="post" commandName="nuovaIstanzaCommand" name="form_allegato_firmato_remove_${count.index}">				
							<input type="hidden" name="codiceScheda" value="${pdfScheda.scheda.codice }" />
							<input type="button" value="<fmt:message key='button.elimina' />" onclick="removeAllegatoFirmato('${count.index}')" />
						</spring-form:form>
						</c:if>
					</td>
				</tr>
				</table>
			</td>
			</c:otherwise>
			</c:choose>
		</tr>
		</c:forEach>
	</table>	
	</div>
	<div class="titolo_sottosezione"><fmt:message key="label.firma-gli-allegati" /></div>
	<div class="sezione">
		<input type="button" id="button-firma" value="<fmt:message key='button.firma-online' />" onclick="mettiAllaFirma()" />
		<div id="applet-container" style="display: none">
			<%@ include file="../includes/firmadigitale2.jsp"%>
		</div>
	</div>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand" name="form_allegati_post">
		<jsp:include page="../includes/pager.jsp">
			<jsp:param name="formName" value="form_allegati_post" />
		</jsp:include>
	</spring-form:form>
	<script type="text/javascript">
		function uploadAllegatoFirmato(idx) {
			if ($('#file-' + idx).val() == '') {
				alert("<fmt:message key='alert.scegli-allegato' />");
				$('#file-' + idx).css({'border' : '1px #cd0a0a solid'});
			} else {
				hideAppletContainer();
				$.blockUI();
				document.forms["form_allegato_firmato_upload_" + idx].submit();
			}
		}
		function removeAllegatoFirmato(idx) {
			if (confirm("<fmt:message key='alert.elimina-allegato' />")) {
				hideAppletContainer();
				$.blockUI();
				document.forms["form_allegato_firmato_remove_" + idx].submit();
			}
		}
		
		function infoAllegato(id) {
			var NWin = window.open('../nuovaistanza/infoAllegato.htm?idAllegato='+id, '69', 'width=650,height=350,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
		    if (window.focus){
		       	NWin.focus();
		    }
		    return false;
		}
		
		//MEDODO DA IMPLEMENTARE PER LA FIRMA
		//torna un array con i codici oggetto (tabella oggetti) dei file selezionati
		function getFileDaFirmare(){
			var fileDaFirmare = $("input[name=alla-firma]:checked").map(function() {
				return this.value;
			}).get();
			return fileDaFirmare;
		}
		//MEDODO DA IMPLEMENTARE PER LA FIRMA
		//visualizza il risultato della firma per ogni file
		function afterSetEsitoFirma(codiceOggetto, fileName, esito, messaggio){
			$("#msg_"+codiceOggetto).remove();
			if ('OK' == esito) {
				$("#"+codiceOggetto).after("<b id='msg_"+codiceOggetto+"' style='color: green'> <fmt:message key='label.firma-ok' /> ("+fileName+")</b>");
			}else{
				$("#"+codiceOggetto).after("<b id='msg_"+codiceOggetto+"' style='color: red'> <fmt:message key='label.firma-ko' /> "+messaggio+"</b>");
			}
		}
		//MEDODO DA IMPLEMENTARE PER LA FIRMA
		//esegue un refresh della pagina se la firma è corretta
		function firmaCompletata(){
			document.location.href = "../nuovaistanzaallegatischede/view.htm";
		}

		function pageAction() {
			hideAppletContainer();
		}		
	</script>
</body>
</html>