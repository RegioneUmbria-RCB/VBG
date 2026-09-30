<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo">
		<c:out value="${CURRENT_STEP.titolo }"></c:out>
	</div>
	<div class="descrizione">
		<c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out>
	</div>
	<%@ include file="../includes/alert.jsp"%>
	<c:if test="${fileTemporaneiEnabled eq true }">
	<div class="titolo_sottosezione">&nbsp;</div>
	<div class="sezione">
	<table class="sezione_table">
		<tr><td colspan="2" align="right">
			<input type="button" value="Scegli file dal server" onclick="displayFileTemporaneiSelect()" />
			<input type="button" value="Scegli file dal tuo computer" onclick="displayInputBox()" />
			</td>
		</tr>
	</table>
	</div>
	</c:if>	
		<c:choose>
		<c:when test="${not empty  nuovaIstanzaCommand.allegatiProcedimenti}">
			<div class="titolo_sottosezione"><fmt:message key="label.allegati-endo" /></div>
			<div id="sez_allegati_procedimenti" class="sezione">
			<table class="sezione_table">
				<c:forEach items="${nuovaIstanzaCommand.allegatiProcedimenti }" var="procH" varStatus="idx">
					<tr>
						<td width="40%"><label title="${procH.famigliaKey.descrizione } - ${procH.categoriaKey.descrizione }"><b>${procH.procedimento.descrizione }</b></label></td>
						<td width="60%">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2">
							<table class="sezione_table">
								<c:forEach items="${procH.documenti }" var="docH" varStatus="idxd">
									<tr>
										<td width="40%">
											${docH.doc.documento }<c:if test="${docH.obbligatorio eq true}"> *</c:if><c:if test="${docH.richiedeFirma eq true}"> (<fmt:message key="label.firma-obbligatoria" />)</c:if>
										</td>
										<td width="10%">
											<c:if test="${docH.codiceOggettoModello != null}"><a href="javascript: void 0" onclick="showDownloadModello('${docH.codiceOggettoModello}','${docH.doc.documento}','${docH.tipoDownload}','${fn:escapeXml(docH.noteFrontend)}','${docH.indirizzoWeb}')">MODELLO</a></c:if>
										</td>
										<c:choose>
											<c:when test="${not empty docH.doc.allegati.id }">
												<spring-form:form action="allegatiREMOVE.htm" method="post" commandName="nuovaIstanzaCommand"
													name="form_allegati_procedimento_remove_${idx.index}${idxd.index}">
													<td width="30%"><c:if test="${docH.richiedeFirma eq true and fn:indexOf(docH.doc.allegati.allegato,'.p7m') == -1}">
															<input type="checkbox" name="alla-firma" value="${docH.doc.allegati.id }" title="<fmt:message key='label.metti-alla-firma' />" />
														</c:if> <label id="${docH.doc.allegati.id }" style="font-weight: bold;">${docH.doc.allegati.allegato }</label> <spring:bind
															path="documentoCaricato.doc.id">
															<input type="hidden" name="${status.expression}" value="${docH.doc.id}" />
														</spring:bind> <spring:bind path="documentoCaricato.procedimentoId">
															<input type="hidden" name="${status.expression}" value="${docH.procedimentoId}" />
														</spring:bind></td>
													<td width="20%" align="right">
														<input type="button" value="<fmt:message key='button.info-file' />" onclick="infoAllegato('${docH.doc.allegati.id }')" />
														<input type="button" value="<fmt:message key='button.rimuovi' />" onclick="removeAllegatoProcedimento('${idx.index}${idxd.index}')" />
													</td>
												</spring-form:form>
											</c:when>
											<c:otherwise>
												<spring-form:form action="allegatiUPLOAD.htm" method="post" commandName="nuovaIstanzaCommand" enctype="multipart/form-data"
													name="form_allegati_procedimento_upload_${idx.index}${idxd.index}">
													<td width="40%">
														<input type="file" name="file" id="procedimento-file-${idx.index}${idxd.index}" size="40" class="file-box" />
														<spring:bind path="documentoCaricato.doc.id">
															<input type="hidden" name="${status.expression}" value="${docH.doc.id}" />
														</spring:bind>
														<spring:bind path="documentoCaricato.fileTempId">
															<input type="hidden" name="${status.expression}" value="${docH.fileTempId}" id="proc-fileTempId-${idx.index}${idxd.index}" />
														</spring:bind>
														<spring:bind path="documentoCaricato.doc.documento">
															<input type="hidden" name="${status.expression}" value="${docH.doc.documento}" />
														</spring:bind>
														<spring:bind path="documentoCaricato.procedimentoId">
															<input type="hidden" name="${status.expression}" value="${docH.procedimentoId}" />
														</spring:bind>
														<spring:bind path="documentoCaricato.obbligatorio">
															<input type="hidden" name="${status.expression}" value="${docH.obbligatorio}" />
														</spring:bind>
														<spring:bind path="documentoCaricato.richiedeFirma">
															<input type="hidden" name="${status.expression}" value="${docH.richiedeFirma}" />
														</spring:bind>
														<c:if test="${fileTemporaneiEnabled eq true }">
														<div class="file-temporanei-box" style="display: none;">
														<select class="file-temporanei" onchange="setFileTempId(this,'proc-fileTempId-${idx.index}${idxd.index}')">
															<option value="" selected="selected">Scegli file</option>
														</select>
														<img src="${pageContext.request.contextPath}/images/spinner.gif" alt="loading..." class="spinner" height="14px" />
														</div>
														</c:if>
													</td>
													<td width="20%" align="right"><input type="button" value="<fmt:message key='button.carica' />" onclick="uploadAllegatoProcedimento('${idx.index}${idxd.index}')" />
													</td>
												</spring-form:form>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
		</c:when>
		<c:otherwise></c:otherwise>
		</c:choose>
	
	<div class="titolo_sottosezione"></div>
	<div id="sez_allegati_intervento" class="sezione">
		<c:choose>
		<c:when test="${not empty nuovaIstanzaCommand.allegatiIntervento }">
		
			<table class="sezione_table">
				<c:forEach items="${nuovaIstanzaCommand.allegatiIntervento }" var="docIntH" varStatus="idx2">
					<tr>
						<td width="40%">
						${docIntH.doc.documento }<c:if test="${docIntH.obbligatorio eq true}"> *</c:if><c:if test="${docIntH.richiedeFirma eq true}"> (<fmt:message key="label.firma-obbligatoria" />)</c:if>
						</td>
						<td width="10%">
							<c:if test="${docIntH.codiceOggettoModello != null}"><a href="javascript: void 0" onclick="showDownloadModello('${docIntH.codiceOggettoModello}','${docIntH.doc.documento}','${docIntH.tipoDownload}','${fn:escapeXml(docIntH.noteFrontend)}','${docIntH.indirizzoWeb}')">MODELLO</a></c:if>
						</td>
						<c:choose>
							<c:when test="${not empty docIntH.doc.allegati.id }">
								<spring-form:form action="allegatiREMOVE.htm" method="post" commandName="nuovaIstanzaCommand" name="form_allegati_intervento_remove_${idx2.index}">
									<td width="30%">
										<c:if test="${docIntH.richiedeFirma eq true and fn:indexOf(docIntH.doc.allegati.allegato,'.p7m') == -1 }">
											<input type="checkbox" name="alla-firma" value="${docIntH.doc.allegati.id }" title="<fmt:message key='label.metti-alla-firma' />" />
										</c:if>
										<label id="${docIntH.doc.allegati.id }" style="font-weight: bold;">${docIntH.doc.allegati.allegato }</label>
										<spring:bind path="documentoCaricato.doc.id">
											<input type="hidden" name="${status.expression}" value="${docIntH.doc.id}" />
										</spring:bind>
										<spring:bind path="documentoCaricato.interventoId">
											<input type="hidden" name="${status.expression}" value="${docIntH.interventoId}" />
										</spring:bind>
									</td>
									<td width="20%" align="right">
										<input type="button" value="<fmt:message key='button.info-file' />" onclick="infoAllegato('${docIntH.doc.allegati.id }')" />
										<input type="button" value="<fmt:message key='button.rimuovi' />" onclick="removeAllegatoIntervento('${idx2.index}')" />
									</td>
								</spring-form:form>
							</c:when>
							<c:otherwise>
								<spring-form:form action="allegatiUPLOAD.htm" method="post" commandName="nuovaIstanzaCommand" enctype="multipart/form-data"
									name="form_allegati_intervento_upload_${idx2.index}">
									<td width="30%">
										<input type="file" name="file" id="intervento-file-${idx2.index}" size="40" class="file-box" />
										<spring:bind path="documentoCaricato.doc.id">
											<input type="hidden" name="${status.expression}" value="${docIntH.doc.id}" />
										</spring:bind>
										<spring:bind path="documentoCaricato.fileTempId">
											<input type="hidden" name="${status.expression}" value="${docIntH.fileTempId}" id="fileTempId-${idx2.index}" />
										</spring:bind>
										<spring:bind path="documentoCaricato.doc.documento">
											<input type="hidden" name="${status.expression}" value="${docIntH.doc.documento}" />
										</spring:bind>
										<spring:bind path="documentoCaricato.interventoId">
											<input type="hidden" name="${status.expression}" value="${docIntH.interventoId}" />
										</spring:bind>
										<spring:bind path="documentoCaricato.obbligatorio">
											<input type="hidden" name="${status.expression}" value="${docIntH.obbligatorio}" />
										</spring:bind>
										<spring:bind path="documentoCaricato.richiedeFirma">
											<input type="hidden" name="${status.expression}" value="${docIntH.richiedeFirma}" />
										</spring:bind>
										<c:if test="${fileTemporaneiEnabled eq true }">
										<div class="file-temporanei-box" style="display: none;">
										<select class="file-temporanei ui-state-default" onchange="setFileTempId(this,'fileTempId-${idx2.index}')">
											<option value="" selected="selected">Scegli file</option>
										</select>
										<img src="${pageContext.request.contextPath}/images/spinner.gif" alt="loading..." class="spinner" height="14px" />									
										</div>
										</c:if>
									</td>
									<td width="20%" align="right">	
										<input type="button" value="<fmt:message key='button.carica' />" onclick="uploadAllegatoIntervento('${idx2.index}')" />	
									</td>
								</spring-form:form>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise><fmt:message key="label.nessun-allegato" /></c:otherwise>
		</c:choose>
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
		function uploadAllegatoProcedimento(idx) {
			if ($('#procedimento-file-' + idx).val() == '' && $('#proc-fileTempId-' + idx).val() == '') {
				alert("<fmt:message key='alert.scegli-allegato' />");
				$('#procedimento-file-' + idx).css({'border' : '1px #cd0a0a solid'});
			} else {
				hideAppletContainer();
				$.blockUI();
				document.forms["form_allegati_procedimento_upload_" + idx].submit();
			}
		}
		function removeAllegatoProcedimento(idx) {
			if (confirm("<fmt:message key='alert.elimina-allegato' />")) {
				hideAppletContainer();
				$.blockUI();
				document.forms["form_allegati_procedimento_remove_" + idx].submit();
			}
		}
		
		function uploadAllegatoIntervento(idx) {
			if ($('#intervento-file-' + idx).val() == '' && $('#fileTempId-' + idx).val() == '') {
				alert("<fmt:message key='alert.scegli-allegato' />");
				$('#intervento-file-' + idx).css({'border' : '1px #cd0a0a solid'});
			} else {
				hideAppletContainer();
				$.blockUI();
				document.forms["form_allegati_intervento_upload_" + idx].submit();
			}
		}
		function removeAllegatoIntervento(idx) {
			if (confirm("<fmt:message key='alert.elimina-allegato' />")) {
				hideAppletContainer();
				$.blockUI();
				document.forms["form_allegati_intervento_remove_" + idx].submit();
			}
		}
		function infoAllegato(id) {
			var NWin = window.open('../nuovaistanza/infoAllegato.htm?idAllegato='+id, '69', 'width=650,height=350,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
		    if (window.focus){
		       	NWin.focus();
		    }
		    return false;
		}
		

		function showDownloadModello(id,desc,tipoDownolad,noteFrontend,indirizzoWeb){
			var titolo = "Download Modello";
			var dialogStart = $("<div class='dialog' title='" + titolo + "'>");
			var htmlDesc = $("<p class='descrizione'>"+desc+"</p>");
			var htmlNote = $("<p class='noteFrontend'>"+noteFrontend+"</p>");
			var htmlInd = $("<p class='indirizzoWeb'>"+indirizzoWeb+"</p>");
			var downloadButton = $("<p>Download</p>").click(function(){
				window.open('../file/ajaxDownload.htm?codiceOggetto='+id, '69', 'width=650,height=350,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
				dialogStart.dialog('close');
			}).button();
			dialogStart.append(htmlDesc);		
			dialogStart.append(htmlNote);
			dialogStart.append(htmlInd);
			dialogStart.append(downloadButton);
			var arrayTipiD = tipoDownolad.split(",");
			
			for (i=0;i<arrayTipiD.length-1;i++){
				var tipoD = arrayTipiD[i];
				var action = $("<p>"+tipoD+"</p>")
					.data('tipo',tipoD)
					.click(function(){
						//alert("Download: "+id+" "+$(this).data('tipo'));
						window.open('../file/ajaxConvertAndDownload.htm?codiceOggetto='+id+'&tipoDownolad='+$(this).data('tipo'), '69', 'width=650,height=350,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
						dialogStart.dialog('close');
					}).button();
			    dialogStart.append(action);
			}
			var dialogEnd = $("</div>");
			dialogStart.append(dialogEnd);
			createDialog(dialogStart);
			
		}
		
		function createDialog(text){
		    return text
		    .dialog({
		        resizable: true,
		        modal: true,
		        buttons: {
		            "<fmt:message key='button.chiudi' />": function() {
		                $( this ).dialog( "close" );
		            }
		        },
		        width: "50%"
		    });
		}

		<c:if test="${fileTemporaneiEnabled eq true }">
		$(function() {
			$.ajax({
		    type: 'GET',
		    url: '../ajax/sfogliaFileTemporanei.htm',
		    success: function(data) {		    	
		    	$.each($('.file-temporanei'),function(idx, item) {
		    		$(data).map(function () {	    	
		            	return $('<option>').val(this.value).text(this.label);
		        	}).appendTo(item);
		    	});
		    	$('.spinner').hide();
		    },
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		        alert("Errore durante il recupero dei documenti dal server");
		    },
		    dataType: "json"
			});
		});
		
		function setFileTempId(sel, elId){
			$("#"+elId).val(sel.value);
		}
		function displayFileTemporaneiSelect(){
			$('.file-box').hide();
			$('.file-temporanei-box').show();
		}
		function displayInputBox(){
			$('.file-temporanei-box').hide();
			$('.file-box').show();
		}
		</c:if>
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
			document.location.href = "../nuovaistanzaallegati/view.htm";
		}
		
		function pageAction() {
			//funzione presente nella jsp di firma
			hideAppletContainer();
		}
	</script>
</body>
</html>