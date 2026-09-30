<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="movimentimail.label.inviomail.title" />
</title>
<script type="module"
	src="${pageContext.request.contextPath}/scripts/custom-components/vbg-email-autocomplete/vbg-email-autocomplete.js?<%=vJS %>"
	defer></script>
<style>
.indirizzi-precompilati {
	position: relative;
}

.indirizzi-precompilati .disabled {
	color: rgb(177, 177, 177);
	cursor: no-drop;
}

.indirizzi-precompilati .messaggio-errore-no-email {
	background-color: var(- -form-element-border-color);
	padding: var(- -half-padding) var(- -default-padding);
	display: inline-block;
	position: absolute;
	bottom: -32px;
	z-index: 100;
}

.indirizzi-precompilati .messaggio-errore-no-email.hidden {
	display: none;
}

#mittente_id {
	width: 500px;
}

#etichetta_id_account {
	min-width: 500px;
}
</style>
</head>
<body>
	<%--VARIABILI PER IL CONTROLLO DELLA VISUALIZZAZIONE --%>
	<c:set var="VERTICALIZZAZIONE_ALLEGATI_PEC_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)%></c:set>

	<span class="titoloPagina">
		${movimentimail.entity.movimento.movimento}
		[${movimentimail.entity.movimento.tipomovimento.id.tipomovimento}] - <fmt:message
			key="movimentimail.label.inviomail.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../movimentimail/createMail" />
		</jsp:include>
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${movimentimail.entity.movimento.istanza.id.codice}</c:param>
		</c:import>

		<br class="clear" />
		<spring-form:form commandName="movimentimail" name="inviodati">
			<input type="hidden" name="entity.id.codice" value="${movimentimail.entity.id.codice}" />
			<div class="vbg-form">
				<c:if test="${param.invio eq 'KO'}">
					<div id="invio_msg" class="error_header">
						<fmt:message key="error.inviomail" />
					</div>
					<script type="text/javascript">
        		    	$('invio_msg').pulsate({ pulses: 2, duration: 1.0 });
        		    </script>
				</c:if>

				<c:if test="${param.invio eq 'OK'}">
					<div id="invio_msg" class="success_header">
						<fmt:message key="label.mailinviata" />
					</div>
					<script type="text/javascript">
        		    	$('invio_msg').pulsate({ pulses: 2, duration: 1.0 });
        		    </script>
				</c:if>
				
				<c:if test="${salvabozza eq 'OK'}">
					<div id="salva_bozza" class="success_header">
						<fmt:message key="label.salvabozza" />
					</div> 
					<script type="text/javascript">
        		    			$('salva_bozza').pulsate({ pulses: 4, duration: 1.0 });
        		    </script>
				</c:if>

				<c:if test="${salvabozza eq 'KO'}">
					<div id="salva_bozza" class="error_header">
						<fmt:message key="error.salvabozza" />
					</div> 
					<script type="text/javascript">
        		    			$('salva_bozza').pulsate({ pulses: 4, duration: 1.0 });
        		    </script>
				</c:if>
				
				<jsp:include page="../includes/displayGlobalMessages.jsp">
					<jsp:param name="commandName" value="movimentimail" />
				</jsp:include>


				<fieldset>
					<legend>
						<fmt:message key="movimentimail.label.mittente.titolo" />
					</legend>
					<div class="form-group">
						<label><fmt:message key="label.account_mail_cfg" /></label>
						<div id="etichetta_id_account" class="readonly-form-control">
							<b>${movimentimail.mailConfig.descrizione}</b>
						</div>
						</td>

						<div class="form-group">
							<label><fmt:message key="movimentimail.label.mittente" /></label>

							<div style="display: inline-block;">
								<spring-form:input id="mittente_id" path="entity.mittente"
									readonly="true" />
								<spring-form:errors path="entity.mittente" cssClass="error" />
								<a href="#"
									onclick="javascript:openSearch('ricerca_mail_mitt');return false;">
									<i class="fas fa-address-book fa-lg" style="cursor: pointer;"
									alt='<fmt:message key="movimentimail.label.rubrica_help" />'></i>
								</a> <br /> <span id="ricerca_mail_mitt" style="display: none;">
									<fmt:message key="movimentimail.label.seleziona_account_email" />
									<br /> <spring-form:select id="select_mittente_email_id"
										path="mailConfig.id.codice"
										onchange="javascript:setMittente()">
										<%-- <spring-form:options items="${listMailConfig}" itemLabel="descrizioneLunga" itemValue="id.codice" />--%>
										<c:forEach items="${listMailConfig}" var="current">
											<spring-form:option value="${current.id.codice}">${current.descrizioneLunga}</spring-form:option>
										</c:forEach>


									</spring-form:select> <script type="text/javascript">
    
                                function setMittente()
                                {
                                    
                                    var codiceMailCfg=jQuery('#select_mittente_email_id').val();
                                    jQuery.ajax({
                                          url: '../mailconfig/ajaxSendmail.htm',
                                          context: document.body,
                                          cache: false,
                                          data: "codice="+codiceMailCfg,
                                          dataType: "html",
                                          success: function(data) {
                                              var res = data.split("#@#");
                                              jQuery('#mittente_id').val(res[1]);
                                              jQuery('#etichetta_id_account').html("<b>"+res[0]+"</b>");
                                              jQuery('#ricerca_mail_mitt').hide();
                                             
                                          },
                                          error: function(jqXHR, textStatus, errorThrown){
                                              alert(jqXHR.responseText);
                                        }
                                    }); 
                                }
                                </script>
								</span>
							</div>
						</div>
				</fieldset>


				<fieldset>
					<legend>
						<fmt:message key="movimentimail.label.destinatario" />
					</legend>
					<div class="form-group">
						<label><fmt:message key="movimentimail.label.destinatari" /></label>
						<div style="display: inline-block;">
							<vbg-email-autocomplete id='email-destinatari'
								autocomplete-url='../rubricajson/ricerca.htm'
								autocomplete-parameter='partial'> <spring-form:hidden
								id="destinatario_id" path="entity.destinatario" /> </vbg-email-autocomplete>

							<div class='indirizzi-precompilati'>
								<a href='#' class='richiedente'><fmt:message
										key="movimentimail.label.recupero_email_richiedente" /></a> <a
									href='#' class='azienda'><fmt:message
										key="movimentimail.label.recupero_email_azienda" /></a> <a
									href='#' class='tecnico'><fmt:message
										key="movimentimail.label.recupero_email_tecnico" /></a> <a
									href='#' class='soggetti-collegati'><fmt:message
										key="movimentimail.label.recupero_email_soggetticollegati" /></a>
								<a href='#' class='amministrazione'><fmt:message
										key="movimentimail.label.recupero_email_amministrazioni" /></a> <a
									href='#' class='domicilio-elettronico'><fmt:message
										key="movimentimail.label.recupero_domicilio_elettronico" /></a> <a
									href='#' class='ente'><fmt:message
										key="movimentimail.label.recupero_ente_mail" /></a> <a href='#'
									onclick="javascript:openSearch('ricerca_mail_dest');return false;"
									title=" <fmt:message key="movimentimail.label.rubrica_help" />"><i
									class="fas fa-address-book fa-lg"></i></a>
								<div class="messaggio-errore-no-email hidden">
									<fmt:message key="movimentimail.label.noemail" />
								</div>
							</div>

							<script type="text/javascript">
        
                            function saveMailInTextAreaDest(inputField,listItem)
                            {
                                var a = listItem.id;
                                document.getElementById('amministrazioni_dest_id').value = inputField.value;
                                document.getElementById('amministrazioni_dest_hidden').value = a;                 
        
                                document.getElementById('email-destinatari').aggiungiIndirizzi(a);
                                
                                document.getElementById('amministrazioni_dest_id').value = '';
                                document.getElementById('amministrazioni_dest_hidden').value = '';
                                document.getElementById('ricerca_mail_dest').style.display='none';
        
                            }
                            </script>
							<spring-form:errors path="entity.destinatario" cssClass="error" />
							<br /> <span id="ricerca_mail_dest" style="display: none;">
								<fmt:message
									key="movimentimail.label.seleziona_email_amministrazione" /> <br />
								<jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="amministrazioni_dest" />
									<jsp:param name="propertyPath" value="entity.amministrazioni" />
									<jsp:param name="pathPropertyDescription"
										value="entity.amministrazioni.amministrazione" />
									<jsp:param name="pathPropertyCode"
										value="entity.amministrazioni.emailOrPec" />
									<jsp:param name="autocompleterAjax"
										value="findEmailByAmministrazioneDescrizione.htm" />
									<jsp:param name="afterUpdateElement"
										value="saveMailInTextAreaDest" />
									<jsp:param name="autocompleterInputSize" value="90" />
									<jsp:param name="titleKey"
										value="label.ricerca_amministrazione" />
								</jsp:include>
							</span>
						</div>
					</div>

				</fieldset>


				<fieldset>
					<legend>
						<fmt:message key="movimentimail.label.destinatariocc" />
					</legend>
					<div class="form-group">
						<label><fmt:message key="movimentimail.label.destinatari" /></label>

						<div style="display: inline-block">
							<vbg-email-autocomplete id='email-destinatari-cc'
								autocomplete-url='../rubricajson/ricerca.htm'
								autocomplete-parameter='partial'> <spring-form:hidden
								id="destinatariocc_id" path="entity.destinatariocc" /> </vbg-email-autocomplete>

							<div class='indirizzi-precompilati'>
								<a href='#' class='richiedente'><fmt:message
										key="movimentimail.label.recupero_email_richiedente" /></a> <a
									href='#' class='azienda'><fmt:message
										key="movimentimail.label.recupero_email_azienda" /></a> <a
									href='#' class='tecnico'><fmt:message
										key="movimentimail.label.recupero_email_tecnico" /></a> <a
									href='#' class='soggetti-collegati'><fmt:message
										key="movimentimail.label.recupero_email_soggetticollegati" /></a>
								<a href='#' class='amministrazione'><fmt:message
										key="movimentimail.label.recupero_email_amministrazioni" /></a> <a
									href='#' class='domicilio-elettronico'><fmt:message
										key="movimentimail.label.recupero_domicilio_elettronico" /></a> <a
									href='#' class='ente'><fmt:message
										key="movimentimail.label.recupero_ente_mail" /></a> <a href='#'
									onclick="javascript:openSearch('ricerca_mail_cc');return false;"
									title=" <fmt:message key="movimentimail.label.rubrica_help" />"><i
									class="fas fa-address-book fa-lg"></i></a>
								<div class="messaggio-errore-no-email hidden">
									<fmt:message key="movimentimail.label.noemail" />
								</div>
							</div>

							<script type="text/javascript">
                            
    
                            function saveMailInTextAreaCC(inputField,listItem)
                            {
                                var a = listItem.id;
                                document.getElementById('amministrazioni_cc_id').value = inputField.value;
                                document.getElementById('amministrazioni_cc_hidden').value = a;                 
                                document.getElementById('email-destinatari-cc').aggiungiIndirizzi(a);                           
                                document.getElementById('amministrazioni_cc_id').value = '';
                                document.getElementById('amministrazioni_cc_hidden').value = '';
                                document.getElementById('ricerca_mail_cc').style.display='none';
                            }
                            </script>

							<spring-form:errors path="entity.destinatariocc" cssClass="error" />
							<br /> <span id="ricerca_mail_cc" style="display: none;">
								<fmt:message
									key="movimentimail.label.seleziona_email_amministrazione" /> <br />
								<jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="amministrazioni_cc" />
									<jsp:param name="propertyPath" value="entity.amministrazioni" />
									<jsp:param name="pathPropertyDescription"
										value="entity.amministrazioni.amministrazione" />
									<jsp:param name="pathPropertyCode"
										value="entity.amministrazioni.emailOrPec" />
									<jsp:param name="autocompleterAjax"
										value="findEmailByAmministrazioneDescrizione.htm" />
									<jsp:param name="afterUpdateElement"
										value="saveMailInTextAreaCC" />
									<jsp:param name="autocompleterInputSize" value="90" />
									<jsp:param name="titleKey"
										value="label.ricerca_amministrazione" />
								</jsp:include>
							</span>
						</div>
					</div>



				</fieldset>

				<fieldset>
					<legend>
						<fmt:message key="movimentimail.label.destinatariobcc" />
					</legend>
					<div class="form-group">
						<label><fmt:message key="movimentimail.label.destinatari" /></label>

						<div style="display: inline-block;">
							<vbg-email-autocomplete id='email-destinatari-bcc'
								autocomplete-url='../rubricajson/ricerca.htm'
								autocomplete-parameter='partial'> <spring-form:hidden
								id="destinatariobcc_id" path="entity.destinatariobcc" /> </vbg-email-autocomplete>

							<div class='indirizzi-precompilati'>
								<a href='#' class='richiedente'><fmt:message
										key="movimentimail.label.recupero_email_richiedente" /></a> <a
									href='#' class='azienda'><fmt:message
										key="movimentimail.label.recupero_email_azienda" /></a> <a
									href='#' class='tecnico'><fmt:message
										key="movimentimail.label.recupero_email_tecnico" /></a> <a
									href='#' class='soggetti-collegati'><fmt:message
										key="movimentimail.label.recupero_email_soggetticollegati" /></a>
								<a href='#' class='amministrazione'><fmt:message
										key="movimentimail.label.recupero_email_amministrazioni" /></a> <a
									href='#' class='domicilio-elettronico'><fmt:message
										key="movimentimail.label.recupero_domicilio_elettronico" /></a> <a
									href='#' class='ente'><fmt:message
										key="movimentimail.label.recupero_ente_mail" /></a> <a href='#'
									onclick="javascript:openSearch('ricerca_mail_bcc');return false;"
									title=" <fmt:message key="movimentimail.label.rubrica_help" />"><i
									class="fas fa-address-book fa-lg"></i></a>
								<div class="messaggio-errore-no-email hidden">
									<fmt:message key="movimentimail.label.noemail" />
								</div>
							</div>

							<script type="text/javascript">
    
                            function saveMailInTextAreaBcc(inputField,listItem)
                            {
                                var a = listItem.id;
                                document.getElementById('amministrazioni_bcc_id').value = inputField.value;
                                document.getElementById('amministrazioni_bcc_hidden').value = a;                 
                                document.getElementById('email-destinatari-bcc').aggiungiIndirizzi(a);                           
                                document.getElementById('amministrazioni_bcc_id').value = '';
                                document.getElementById('amministrazioni_bcc_hidden').value = '';
                                document.getElementById('ricerca_mail_bcc').style.display='none';
                            }
                            
                            
                            </script>

							<spring-form:errors path="entity.destinatariobcc"
								cssClass="error" />
							<br /> <span id="ricerca_mail_bcc" style="display: none;">
								<fmt:message
									key="movimentimail.label.seleziona_email_amministrazione" /> <br />
								<jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="amministrazioni_bcc" />
									<jsp:param name="propertyPath" value="entity.amministrazioni" />
									<jsp:param name="pathPropertyDescription"
										value="entity.amministrazioni.amministrazione" />
									<jsp:param name="pathPropertyCode"
										value="entity.amministrazioni.emailOrPec" />
									<jsp:param name="autocompleterAjax"
										value="findEmailByAmministrazioneDescrizione.htm" />
									<jsp:param name="afterUpdateElement"
										value="saveMailInTextAreaBcc" />
									<jsp:param name="autocompleterInputSize" value="90" />
									<jsp:param name="titleKey"
										value="label.ricerca_amministrazione" />
								</jsp:include>
							</span>
						</div>
					</div>

				</fieldset>

				<fieldset>
					<legend>
						<fmt:message key="movimentimail.label.mailtipo.titolo" />
					</legend>

					<div class="form-group">
						<label><fmt:message key="movimentimail.label.mailtipo" /></label>
						<select id="mailtipo_id" onchange="recuperaOggettoCorpo(this);">
							<option></option>
							<c:forEach items="${mailtipi}" var="mailtipi_var">
								<option label="${mailtipi_var.descrizione}"
									value="${mailtipi_var.id.codice}">${mailtipi_var.descrizione}</option>
							</c:forEach>
						</select>
					</div>

					<div class="form-group">
						<label><fmt:message key="movimentimail.label.oggetto" /></label>
						<div style="display: inline-block;">
							<spring-form:input id="oggetto_id" path="entity.oggetto"
								size="100" />
							<spring-form:errors path="entity.oggetto" cssClass="error" />
						</div>
					</div>

					<div class="form-group" style="display: inline-block">
						<label><fmt:message key="movimentimail.label.corpo" /></label>
						<div style="display: inline-block;">
							<spring-form:textarea id="corpo_id" path="entity.corpo"
								cols="100" rows="10" />
							<spring-form:errors path="entity.corpo" cssClass="error" />
						</div>
						<div id="functions">
							<ul>
								<li><a
									href="javascript:doSubmit('../movimentimail/salvaBozzaMovimenti.htm','',document.inviodati)"><fmt:message
											key="button.salvabozza" /></a></li>
								<li><a
									href="javascript:doSubmit('../movimentimail/cancellaBozzaMovimenti.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
											key="button.cancellabozza" /></a></li>
							</ul>
						</div>
					</div>
				</fieldset>

				<c:if test="${VERTICALIZZAZIONE_ALLEGATI_PEC_REQUEST}">
					<fieldset>
						<legend>
							<fmt:message
								key="movimentimail.label.comportamento-allegati.titolo" />
						</legend>
						<div class="form-group">
							<label><fmt:message
									key="tipimovimento.label.flag_allegati_link" /></label>
							<div style="display: inline-block;">
								<spring-form:checkbox id="flgInvialinkallmail_id"
									path="flgInvialinkallmail"
									onclick="showZipLogicoSection(false);" />
								<fmt:message key="help.flag_allegati_link" />
							</div>
						</div>

						<div class="form-group">
							<label><fmt:message
									key="tipimovimento.label.letteratipo_linkdoc" /></label>

							<div style="display: inline-block;">
								<jsp:include page="../includes/autocompletergenericoTT.jsp">
									<jsp:param name="idElemento" value="letteraTipoAllegati" />
									<jsp:param name="propertyPath" value="letteraTipoAllegati" />
									<jsp:param name="pathPropertyDescription"
										value="letteraTipoAllegati.descrizione" />
									<jsp:param name="pathPropertyCode"
										value="letteraTipoAllegati.id.codice" />
									<jsp:param name="autocompleterAjax"
										value="findLettereTipo.htm?codicesoftware=" />
									<jsp:param name="titleKey" value="label.ricerca_tipo_lettera" />
									<jsp:param name="id_help" value="help_letteraTipoAllegati" />
									<jsp:param name="help" value="help.search_archivi_base" />
								</jsp:include>
							</div>
						</div>
					</fieldset>
				</c:if>

			</div>

			<br />

			<script type="text/javascript">
			
			
			function selezionaAndDeselezionaTutti(){

				var checkIt = jQuery('#a_check_allegati').prop('checked');
				jQuery("input[id^='di_']").prop('checked', checkIt);
				jQuery("input[id^='ma_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='altrima_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='ia_']").prop('checked', checkIt);
				jQuery("input[id^='docanagr_']").prop('checked', checkIt);
				jQuery("input[id^='dp_']").prop('checked', checkIt);
				jQuery("input[id^='cds_']").prop('checked', checkIt);
				//jQuery('.selezionabile').prop('checked', checkIt);
			}
			
			function showZipLogicoSection(onReady) {
				//debugger;
				var checklinkall = jQuery('#flgInvialinkallmail_id').prop("checked");
				var flgZipLogicoChecked = jQuery('#flg_zip_logico_id').prop('checked');
				
				if (checklinkall == true) {
					
					<c:if test="${ ifZipLogicoExist eq true }">
					
						jQuery('#zip_logico_row').show();
						
						if (onReady) {
							
							hideOtherDocumentSectionsIfFlgZipLogicoChecked(false);
						}
						
					</c:if>
					
				} else {
					
					jQuery('#zip_logico_row').hide();
					
					if (flgZipLogicoChecked == true) {
						
						jQuery('#flg_zip_logico_id').prop('checked', false);
						
						// show other document sections
						showOtherDocumentSections();
					}
					
				}
			}
			
			</script>

			<div class="vbg-form">
				-
				<fieldset>
					<legend>
						<fmt:message key="label.sezione_documenti" />
					</legend>

					<div>
						<div style="float: right;">
							<input id="a_check_allegati" type="checkbox"
								onclick="selezionaAndDeselezionaTutti()"></input> <label
								id="message_label" for="a_check_allegati"><fmt:message
									key="label.seleziona_deseleziona_tutti" /></label>
						</div>
					</div>

					<div class="jmesa">
						<table border="0" cellpadding="2" cellspacing="0" class="table">
							<c:if
								test="${not empty movimentimail.documentiHelper.documentiMovimentoList || ifZipLogicoExist}">
								<%int i=1;%>
								<tr class="header">
									<td colspan="5"><fmt:message
											key="label.documenti_movimento" /></td>
								</tr>


								<jsp:include page="../includes/ziplogicosection.jsp">
									<jsp:param name="movimento"
										value="${movimentimail.entity.movimento.movimento}" />
									<jsp:param name="codicemovimento"
										value="${movimentimail.entity.movimento.id.codice }" />
									<jsp:param name="isZipLogico" value="${ifZipLogicoExist }" />
									<jsp:param name="displayNone" value="<%=true %>" />
									<jsp:param name="labelForFlgZipLogicoChbx"
										value="label.movimenti_zip_logico.invio_mail" />
									<jsp:param name="commandPathProperty"
										value="flgInvioMailZipLogico" />
									<jsp:param name="help"
										value="label.movimenti_zip_logico.help_invio_mail" />
									<jsp:param name="hideDocAltrimov"
										value=".hide_doc_altri_movimenti" />
									<jsp:param name="inputMaChbx" value="input[id^='ma_']" />
									<jsp:param name="hideDocist" value=".hide_doc_istanza" />
									<jsp:param name="inputIstChbx" value="input[id^='di_']" />
									<jsp:param name="hideDocproc" value=".hide_doc_procure" />
									<jsp:param name="inputProcChbx" value="input[id^='dp_']" />
									<jsp:param name="hideDocendo" value=".hide_doc_endo" />
									<jsp:param name="inputEndoChbx" value="input[id^='ia_']" />
									<jsp:param name="hideDocanag" value=".hide_doc_anagrafe" />
									<jsp:param name="inputAnagChbx" value="input[id^='docanagr_']" />
									<jsp:param name="hideDoccds" value=".hide_doc_cds" />
									<jsp:param name="inputCdsChbx" value="input[id^='cds_']" />
									<jsp:param name="isRadioBtn" value="<%=false %>" />
									<jsp:param name="gestisciDocPrincipale" value="false" />
								</jsp:include>



								<!-- SEZIONE DOCUMENTI DEL MOVIMENTO -->
								<c:forEach
									items="${movimentimail.documentiHelper.documentiMovimentoList}"
									var="current" varStatus="a">
									<tr>
										<td width="25%" style="vertical-align: top;">${current.chiave}</td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>

												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(i%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceOggetto }">
														<td width="50%">${var.descrizione}</td>
														<td width="35%">${var.nomeFile}</td>
														<td width="10%">${var.note}</td>
														<c:if test="${var.codiceOggetto!=null}">
															<td width="13%"><jsp:include
																	page="../includes/visualizzaOggetto.jsp">
																	<jsp:param name="idElemento"
																		value="docist${var.id.codice}" />
																	<jsp:param name="fileId" value="${var.codiceOggetto}" />
																	<jsp:param name="mostralabel" value="true" />
																</jsp:include></td>
															<td><jsp:include
																	page="../includes/dettaglioCheckOggetto.jsp">
																	<jsp:param name="controllook"
																		value="${var.controllook}" />
																</jsp:include></td>
															<td class="colonna-allegati-selezionabili"
																data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
																	path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																	<input type="hidden"
																		name="_<c:out value="${status.expression}"/>"
																		value="visible" />
																	<input id="ma_${a.index}_${b.index}" type="checkbox"
																		name="<c:out value="${status.expression}"/>"
																		value="true"
																		<c:if test="${status.value}">checked="checked"</c:if> />
																</spring:bind></td>
														</c:if>
														<c:if
															test="${var.codiceOggetto==null && not empty var.messageId}">
															<!-- se esiste un record con oggetto null, ma messageid popolato carica automaticamente il codice per scaricare 
    							    l'allegato eml dal server di posta configurato -->
															<td colspan="2"><jsp:include
																	page="../ajax/downloadEml.jsp">
																	<jsp:param name="codiceallegato"
																		value="${var.id.codice}" />
																	<jsp:param name="idElemento"
																		value="docist${var.id.codice }" />
																	<jsp:param name="fileId" value="${var.codiceOggetto}" />
																	<jsp:param name="mostralabel" value="true" />
																	<jsp:param name="id_ckh"
																		value="ma_${a.index}_${b.index}" />
																</jsp:include></td>
															<td><spring:bind
																	path="documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																	<input type="hidden"
																		name="_<c:out value="${status.expression}"/>"
																		value="visible" />
																	<input id="ma_${a.index}_${b.index}" type="checkbox"
																		name="<c:out value="${status.expression}"/>"
																		title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />"
																		disabled="disabled" value="true"
																		<c:if test="${status.value}">checked="checked"</c:if> />
																</spring:bind></td>
														</c:if>
													</tr>
													<%i++;%>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<!-- SEZIONE DOCUMENTI DEL ALTRI MOVIMENT -->
							<c:if
								test="${not empty movimentimail.documentiHelper.documentiAltriMovimentiList}">
								<%int j=1;%>
								<tr class="header hide_doc_altri_movimenti">
									<td colspan="5"><fmt:message
											key="label.documenti_altri_movimenti" /></td>
								</tr>
								<c:forEach
									items="${movimentimail.documentiHelper.documentiAltriMovimentiList}"
									var="current" varStatus="a">
									<tr class="hide_doc_altri_movimenti">
										<td width="25%" style="vertical-align: top;">${current.chiave}</td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>
												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(j%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceOggetto }">
														<td width="50%">${var.descrizione}</td>
														<td width="35%">${var.nomeFile}</td>
														<td width="10%">${var.note}</td>
														<c:if test="${var.codiceOggetto!=null}">
															<td width="13%"><jsp:include
																	page="../includes/visualizzaOggetto.jsp">
																	<jsp:param name="idElemento"
																		value="docist${var.id.codice }" />
																	<jsp:param name="fileId" value="${var.codiceOggetto}" />
																	<jsp:param name="mostralabel" value="true" />
																</jsp:include></td>
															<td><jsp:include
																	page="../includes/dettaglioCheckOggetto.jsp">
																	<jsp:param name="controllook"
																		value="${var.controllook}" />
																</jsp:include></td>
															<td class="colonna-allegati-selezionabili"
																data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
																	path="documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																	<input type="hidden"
																		name="_<c:out value="${status.expression}"/>"
																		value="visible" />
																	<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																	<input id="altrima_${a.index}_${b.index}"
																		type="checkbox"
																		name="<c:out value="${status.expression}"/>"
																		value="true"
																		<c:if test="${status.value}">checked="checked"</c:if> />
																</spring:bind></td>
														</c:if>

														<c:if
															test="${var.codiceOggetto==null && not empty var.messageId}">
															<!-- se esiste un record con oggetto null, ma messageid popolato carica automaticamente il codice per scaricare 
    							    l'allegato eml dal server di posta configurato -->
															<td colspan="2"><jsp:include
																	page="../ajax/downloadEml.jsp">
																	<jsp:param name="codiceallegato"
																		value="${var.id.codice}" />
																	<jsp:param name="idElemento"
																		value="docist${var.id.codice }" />
																	<jsp:param name="fileId" value="${var.codiceOggetto}" />
																	<jsp:param name="mostralabel" value="true" />
																	<jsp:param name="id_ckh"
																		value="altrima_${a.index}_${b.index}" />
																	<jsp:param name="id_radio_button" value="no" />
																</jsp:include></td>

															<td><spring:bind
																	path="documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																	<input type="hidden"
																		name="_<c:out value="${status.expression}"/>"
																		value="visible" />
																	<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																	<input id="altrima_${a.index}_${b.index}"
																		type="checkbox"
																		name="<c:out value="${status.expression}"/>"
																		title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />"
																		disabled="disabled" value="true"
																		<c:if test="${status.value}">checked="checked"</c:if> />
																</spring:bind></td>
														</c:if>
														<%j++; %>
													
												</c:forEach>

											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
							<c:if
								test="${not empty movimentimail.documentiHelper.documentiIstanzaList}">
								<%int k=1;%>
								<tr class="header hide_doc_istanza">
									<td colspan="5"><fmt:message key="label.allegati_istanza" />
									</td>
								</tr>
								<c:forEach
									items="${movimentimail.documentiHelper.documentiIstanzaList}"
									var="current" varStatus="a">
									<tr class="hide_doc_istanza">
										<td width="25%" style="vertical-align: top;"><b>${current.chiave}</b></td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>

												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(k%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceOggetto }">
														<td width="50%">${var.documento}</td>
														<td width="35%">${var.nomeFile}</td>
														<td width="10%">${var.note}</td>
														<td width="13%"><jsp:include
																page="../includes/visualizzaOggetto.jsp">
																<jsp:param name="idElemento"
																	value="docist${var.id.codice }" />
																<jsp:param name="fileId" value="${var.codiceOggetto}" />
																<jsp:param name="mostralabel" value="true" />
															</jsp:include></td>
														<td><jsp:include
																page="../includes/dettaglioCheckOggetto.jsp">
																<jsp:param name="controllook" value="${var.controllook}" />
															</jsp:include></td>
														<td class="colonna-allegati-selezionabili"
															data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
																path="documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																<input type="hidden"
																	name="_<c:out value="${status.expression}"/>"
																	value="visible" />
																<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="di_${b.index}" type="checkbox"
																	name="<c:out value="${status.expression}"/>"
																	value="true"
																	<c:if test="${status.value}">checked="checked"</c:if> />
															</spring:bind></td>

													</tr>
													<%k++; %>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<!-- GESTIONE DELLA  VISUALIZZAZIONE DELLE PROCURE  -->
							<c:if
								test="${not empty movimentimail.documentiHelper.istanzeprocureList}">
								<%int k=1;%>
								<tr class="header hide_doc_procure">
									<td colspan="5"><fmt:message key="label.documenti_procure" />
									</td>
								</tr>
								<c:forEach
									items="${movimentimail.documentiHelper.istanzeprocureList}"
									var="current" varStatus="a">
									<tr class="hide_doc_procure">
										<td width="25%" style="vertical-align: top;"><b>${current.chiave}</b></td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>

												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(k%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceOggetto }">
														<td width="50%">Documento della procura di
															${var.anagrafeProcuratore.descrizioneRichiedente}</td>
														<td width="35%">${var.nomeFile} <c:if
																test="${not empty var.codiceOggettoDocId }">
																<p />
    											${var.nomeFileDocId}
    										</c:if>
														</td>
														<td colspan="2" width="13%"><jsp:include
																page="../includes/visualizzaOggetto.jsp">
																<jsp:param name="idElemento"
																	value="docProc${var.id.codice }" />
																<jsp:param name="fileId" value="${var.codiceOggetto}" />
																<jsp:param name="mostralabel" value="true" />
															</jsp:include> <c:if test="${not empty var.codiceOggettoDocId }">
																<p />
																<jsp:include page="../includes/visualizzaOggetto.jsp">
																	<jsp:param name="idElemento"
																		value="docProcDocId${var.id.codice }" />
																	<jsp:param name="fileId"
																		value="${var.codiceOggettoDocId}" />
																	<jsp:param name="mostralabel" value="true" />
																</jsp:include>
															</c:if></td>
														<td><jsp:include
																page="../includes/dettaglioCheckOggetto.jsp">
																<jsp:param name="controllook" value="${var.controllook}" />
															</jsp:include></td>
														<td class="colonna-allegati-selezionabili"
															data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
																path="documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																<input type="hidden"
																	name="_<c:out value="${status.expression}"/>"
																	value="visible" />
																<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="dp_${b.index}" type="checkbox"
																	name="<c:out value="${status.expression}"/>"
																	value="true"
																	<c:if test="${status.value}">checked="checked"</c:if> />
															</spring:bind></td>

													</tr>
													<%k++; %>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if
								test="${not empty movimentimail.documentiHelper.documentiEndoprocedimentiList}">
								<%int b=1;%>
								<tr class="header hide_doc_endo">
									<td colspan="5"><fmt:message
											key="label.allegati_endoprocedimenti" /></td>
								</tr>
								<c:forEach
									items="${movimentimail.documentiHelper.documentiEndoprocedimentiList}"
									var="current" varStatus="a">
									<tr class="hide_doc_endo">
										<td width="25%" style="vertical-align: top;">${current.chiave}</td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>
												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(b%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceOggetto }">
														<td width="50%">${var.allegatoextra}</td>
														<td width="35%">${var.nomeFile}</td>
														<td width="10%">${var.note}</td>
														<td width="13%"><jsp:include
																page="../includes/visualizzaOggetto.jsp">
																<jsp:param name="idElemento"
																	value="docist${var.id.codice }" />
																<jsp:param name="fileId" value="${var.codiceOggetto}" />
																<jsp:param name="mostralabel" value="true" />
															</jsp:include></td>
														<td><jsp:include
																page="../includes/dettaglioCheckOggetto.jsp">
																<jsp:param name="controllook" value="${var.controllook}" />
															</jsp:include></td>
														<td class="colonna-allegati-selezionabili"
															data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
																path="documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																<input type="hidden"
																	name="_<c:out value="${status.expression}"/>"
																	value="visible" />
																<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="ia_${b.index}" type="checkbox"
																	name="<c:out value="${status.expression}"/>"
																	value="true"
																	<c:if test="${status.value}">checked="checked"</c:if> />
															</spring:bind></td>
													</tr>
													<%b++; %>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE -->
							<c:if
								test="${not empty movimentimail.documentiHelper.documentiAnagrafeList}">
								<%int p=1;%>
								<tr class="header hide_doc_anagrafe">
									<td colspan="5"><fmt:message
											key="label.allegati_anagrafiche" /></td>
								</tr>
								<c:forEach
									items="${movimentimail.documentiHelper.documentiAnagrafeList}"
									var="current" varStatus="a">
									<tr class="hide_doc_anagrafe">
										<td width="25%" style="vertical-align: top;">${current.chiave}</td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>
												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(p%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceOggetto }">
														<td width="50%">${var.documento}</td>
														<td width="35%">${var.nomeFile}</td>
														<td width="10%">&nbsp;</td>
														<td width="13%"><jsp:include
																page="../includes/visualizzaOggetto.jsp">
																<jsp:param name="idElemento"
																	value="docist${var.id.codice }" />
																<jsp:param name="fileId" value="${var.codiceOggetto}" />
																<jsp:param name="mostralabel" value="true" />
															</jsp:include></td>
														<td>&nbsp;</td>
														<%-- 
    						    <td>
      							    <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
    		       						<jsp:param name="controllook" value="${var.controllook}" />
    		   						</jsp:include>
       							</td>
       							--%>
														<td class="colonna-allegati-selezionabili"
															data-codiceoggetto="${ var.codiceOggetto }"><spring:bind
																path="documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																<input type="hidden"
																	name="_<c:out value="${status.expression}"/>"
																	value="visible" />
																<input id="docanagr_${b.index}" type="checkbox"
																	name="<c:out value="${status.expression}"/>"
																	value="true"
																	<c:if test="${status.value}">checked="true"</c:if>" class="selezionabile" />
															</spring:bind></td>
													</tr>
													<%p++; %>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>

							<!-- GESTIONE DELLA  VISUALIZZAZIONE DELLE CDS  -->
							<c:if
								test="${not empty movimentimail.documentiHelper.cdsattiList}">
								<%int k=1;%>
								<tr class="header hide_doc_cds">
									<td colspan="5"><fmt:message key="label.verbale_cds" /></td>
								</tr>
								<c:forEach items="${movimentimail.documentiHelper.cdsattiList}"
									var="current" varStatus="a">
									<tr class="hide_doc_cds">
										<td width="25%" style="vertical-align: top;"><b>CDS</b></td>
										<td width="75%" colspan="4">
											<table width="100%">
												<tr class="header">
													<td width="98%" colspan="5"><fmt:message
															key="movimentimail.label.documento" /></td>
													<td width="2%"><fmt:message key="label.seleziona" /></td>

												</tr>
												<c:forEach items="${current.valore}" var="var" varStatus="b">
													<tr
														class="<%=(k%2)==0?"odd":"even"%> riga-allegati-selezionabili"
														data-codiceoggetto="${ var.codiceoggetto }">
														<td colspan="2" width="85%">${var.nomefile}</td>
														<td colspan="3" width="13%"><jsp:include
																page="../includes/visualizzaOggetto.jsp">
																<jsp:param name="idElemento"
																	value="docProc${var.id.codice }" />
																<jsp:param name="fileId" value="${var.codiceoggetto}" />
																<jsp:param name="mostralabel" value="true" />
															</jsp:include></td>
														<td class="colonna-allegati-selezionabili"
															data-codiceoggetto="${ var.codiceoggetto }"><spring:bind
																path="documentiHelper.cdsattiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
																<input type="hidden"
																	name="_<c:out value="${status.expression}"/>"
																	value="visible" />
																<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
																<input id="cds_${b.index}" type="checkbox"
																	name="<c:out value="${status.expression}"/>"
																	value="true"
																	<c:if test="${status.value}">checked="checked"</c:if> />
															</spring:bind></td>
													</tr>
													<%k++; %>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:if>

						</table>
				</fieldset>
			</div>




			<spring-form:hidden id="movimento_id"
				path="entity.movimento.id.codice" />

			<script type='text/javascript'>
			
    			vbg.ready(async () => {
    
    			    const arr = [
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .richiedente'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaMailRichiedente.htm'
    			        },
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .azienda'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaMailAzienda.htm'
    			        },
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .tecnico'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaMailTecnico.htm'
    			        },
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .soggetti-collegati'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaMailSoggettiCollegati.htm'
    			        },
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .amministrazione'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaMailAmministrazioni.htm'
    			        },
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .domicilio-elettronico'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaIndirizzoElettronico.htm'
    			        },
    			        {
    			            controlli: document.querySelectorAll('.indirizzi-precompilati .ente'),
    			            url: '${pageContext.request.contextPath}/ajax/recuperaEnteSoftware.htm'
    			        }
    			    ];
    			    
    			    const inizializzaStatoControllo = async (x) => {
    
                        const url = x.url + '?codiceistanza=${istanza.id.codice}&codicemovimento=${movimentimail.entity.movimento.id.codice}&ts_=' + new Date().getTime();
                        var response = await fetch(url);
    
                        if (response.status != 200) {
                            x.controlli.forEach(controllo => controllo.style.display = 'none');
                            return;
                        }
    
                        var email = await response.text();
    
                        x.controlli.forEach(controllo => {
    
                            const td = controllo.closest('.form-group');
                            const emailInput = td.querySelector('vbg-email-autocomplete');
                            const messaggioNoEmail = td.querySelector('.messaggio-errore-no-email');
    
                            controllo.dataset.email = email;
    
                            if (email.length > 0) {
                                controllo.title = email;
                                controllo.addEventListener('click', (e) => {
                                    e.preventDefault();
                                    emailInput.aggiungiIndirizzi(email);
                                });
                            } else {
                                controllo.addEventListener('click', (e) => e.preventDefault());
                                controllo.disabled = true;
                                controllo.classList.add('disabled');
    
                                controllo.addEventListener('mouseenter', (e) => {
                                    messaggioNoEmail.style.left = controllo.offsetLeft + 'px';
                                    messaggioNoEmail.classList.remove('hidden');
                                });
                                controllo.addEventListener('mouseleave', (e) => {
                                    messaggioNoEmail.classList.add('hidden');
                                });
                            }
                        });
                    };
    			    
    			    
    			    vbg.mostraModalCaricamento();
    			    
    			    // Utilizzo una promise invece di await per parallelizzare
    			    // la ricerca.
    			    // allSettled invoca la callback quando tutte le chiamate 
    			    // asincrone sono terminate indipendentemente da loro esito
    			    // (success, reject o error)
    			    Promise.allSettled(arr.map(x => inizializzaStatoControllo(x)))
    			           .then(() => vbg.nascondiModalCaricamento());
    			    /*
    			    for await (const x of arr){
    			    	await inizializzaStatoControllo(x);
    			    }
    			    */
    			    
    			});

			
			
			
			
			
			
				tinyMCE.init({
					
		  			/*mode: "exact",*/ 
		  			/*selector:"textarea",*/ 
		  			theme: "advanced",
		  			body_id : "my_id",
		  			elements : "corpo_id",
		  			theme_advanced_toolbar_location: "top",
		  			theme_advanced_toolbar_align: "left",
		  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
		  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
		  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
		  			theme_advanced_buttons3: "link,unlink,anchor,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,advhr",
		  			forced_root_block : false,
		  	        force_br_newlines : true,
		  	        force_p_newlines : false
				});
				
				
				function viewTitle(id){
					$(id).appear();
					}
				function closeTitle(id){
					$(id).fade();
					}
			
				var isespandi=false;
				$('destinatario_id').focus();
				
				
				
				function recuperaOggettoCorpo(elem){
					var code=elem[elem.selectedIndex].value;
					if(code!=''){
					var ts = new Date().getTime();
				 	new Ajax.Request('${pageContext.request.contextPath}/jsonmail/recuperaOggettoCorpo.htm?codiceistanza=${istanza.id.codice}&codicemovimento=${movimentimail.entity.movimento.id.codice}&ts_='+ts, { 
					 	method:'post',
					 	parameters:{chiave_ricerca : code}, 
			  			onSuccess: function(transport){
			  			 	var json = transport.responseText.evalJSON();
			      			var mailtipo = json.mailtipo;
			      			$('oggetto_id').value=mailtipo.oggetto;
			      			tinyMCE.get('corpo_id').setContent(mailtipo.corpo);
			    		},
			    		onFailure: function(transport){
			  				printResult(transport, "Errore durante il recupero dell'oggetto e del corpo dell'e-mail");
			    		}
					});
					}else{
						$('oggetto_id').value='';
		      			//$('corpo_id').value='';
						tinyMCE.get('corpo_id').setContent('');
						}
				}
				
				jQuery(document).ready(function(){
					var setmailtipo = '${setmailtipo}';
					if(setmailtipo=='true'){
						$('mailtipo_id').options[1].selected=true;
						recuperaOggettoCorpo($('mailtipo_id'));
					}
				});
				
				function openSearch(id)
				{
					document.getElementById(id).value='';
					if(document.getElementById(id).style.display=='none')
					{
						document.getElementById(id).style.display='';
					}else
					{
						document.getElementById(id).style.display='none';
					}
				}
				function populateCampoEmail(emails,a)
				{
					if(emails.substring(emails.length-1,emails.length)==';' || emails=='' )
					{
						emails = emails+a+";";
					}else
					{
						emails = emails+";"+a+";";
					}
					return emails;
				}
				
				tinyMCE.init({
		  			mode: "exact", 
		  			id:"id_corpo",
					elements: "corpo_id", 
		  			theme: "advanced",
		  			theme_advanced_toolbar_location: "top",
		  			theme_advanced_toolbar_align: "left",
		  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
		  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
		  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
		  			theme_advanced_buttons3: "link,unlink,anchor,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,advhr",
		  			forced_root_block : false,
		  	        force_br_newlines : true,
		  	        force_p_newlines : false
				});
				
			</script>
		</spring-form:form>
	</div>

	<div id="functions">
		<ul>
			<li><a
				href="javascript:doSubmit('../movimentimail/inviaMail.htm','',document.inviodati)"><fmt:message
						key="button.inviamail" /></a></li>
			<li><a id="back_btn_id" href="javascript:closePopup();"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		function closePopup(){
			if (window.opener && !window.opener.closed) {
					window.close();
				}else{
					doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','');
				
					}			
		}
		
		jQuery(document).ready(function () {
			
			showZipLogicoSection(true);
			
		});
		
		
	</script>
</body>
</html>