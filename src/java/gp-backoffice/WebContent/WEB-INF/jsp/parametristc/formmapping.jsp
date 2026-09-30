<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${tipimovStcMapping.id.codice==null}">
				<fmt:message key="form.tipimovstcmapping.title.create" />
			</c:if> 
			<c:if test="${tipimovStcMapping.id.codice!=null}">
				<fmt:message key="form.tipimovstcmapping.title.view" />
			</c:if>
		</title>
		<style type="text/css">
			hr{
			   	display: block;
				height: 1px;
				border: 0;
				border-top: 1px solid #ccc;
				margin: 1em 0;
				padding: 0;			
			}
			
			.cod_mov{
				white-space: normal;
			}
		</style>
	</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${tipimovStcMapping.id.codice==null}">
				<fmt:message key="form.tipimovstcmapping.title.create" />
			</c:if> 
			<c:if test="${tipimovStcMapping.id.codice!=null}">
				<fmt:message key="form.tipimovstcmapping.title.view" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
				<spring-form:form commandName="tipimovStcMapping" name="inviodati">
					<jsp:include page="../includes/displayGlobalMessages.jsp" >
				        <jsp:param name="commandName" value="tipimovStcMapping" />
				    </jsp:include>
             
                    <fieldset>
                        <legend><fmt:message key="label.tipomovimento" /></legend>
                        <div class="etichetta">
                            <div><fmt:message key="label.codice" />:</div>
                            <div><fmt:message key="label.tipomovimento" />:</div>
                        </div>
                        <div class="parametro">
                          <div><c:out value="${tipimovStcMapping.tipimovimento.id.tipomovimento}" /></div>
                          <div><c:out value="${tipimovStcMapping.tipimovimento.movimento }" /></div>
                        </div>
                    </fieldset>


			    	<%
						String displayflagAllegaDocumentiAndInviaAllegati="";
						String displayflagProtocolla="";
						String displayParametriProtocollo="";
						if((Boolean)request.getAttribute("viewFlagAllegatiEdoc").equals(true))
						{
						     displayflagAllegaDocumentiAndInviaAllegati="";
						     displayflagProtocolla="";
						    
						}else
						{
						     displayflagAllegaDocumentiAndInviaAllegati="display:none";
						     displayflagProtocolla="display:none";
							 displayParametriProtocollo="display:none";
						}
						if((Boolean)request.getAttribute("viewParametriProtocollo").equals(true))
						{
							 	displayParametriProtocollo="";
						}else
						{
								 displayParametriProtocollo="display:none";
						}					
					%>
					<div style="width: 650px;min-height: 50px; border: thin dotted; padding: 5px;">
						<fmt:message key="label.help_configurazione_notifica_stc" />
					</div>
					<br/>
					<fieldset>
						<legend><fmt:message key="label.parametri_ente"/></legend>
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.amministrazioni" /></label>
							<spring-form:select id="amministrazioni_id" path="amministrazioni.id.codice" >
								<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
							</spring-form:select>
							<spring-form:errors path="amministrazioni" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.codiceAttDest" /></label>
							<spring-form:input id="codiceAttDest_id" path="codiceAttDest" size="50" />
							<init:help idHelp="help_attivita" textKey="form.tipimovstcmapping.codiceAttDest.help"/>
							<spring-form:errors path="codiceAttDest" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.descrizioneAttDest" /></label>
							<spring-form:textarea id="descrizioneAttDest_id" path="descrizioneAttDest" cols="70" rows="2" />
							<init:help idHelp="help_desc_attivita" textKey="form.tipimovstcmapping.descrizioneAttDest.help"/>
							<spring-form:errors path="descrizioneAttDest" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.codiceprocedimento" /></label>
							<spring-form:input id="codiceprocedimento_id" path="codiceprocedimento" size="50" />
							<init:help idHelp="help_codiceprocedimento" textKey="form.tipimovstcmapping.codiceprocedimento.help"/>
							<spring-form:errors path="codiceprocedimento" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.flagRifpratStorica" /></label>
							<spring-form:checkbox id="flagRifpratStorica_id" path="flagRifpratStorica"/>
							<init:help idHelp="help_flagRifpratStorica" textKey="form.tipimovstcmapping.flagRifpratStorica.help"/>
							<spring-form:errors path="flagRifpratStorica" cssClass="error"/>
						</div>					
						<hr />					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.flagNonInviareProcedimenti" /></label>
							<spring-form:checkbox id="flagNonInviareProcedimenti_id" path="nonInviareProcedimenti"/>
							<init:help idHelp="help_flagNonInviareProcedimenti" textKey="form.tipimovstcmapping.flagNonInviareProcedimenti.help"/>
							<spring-form:errors path="nonInviareProcedimenti" cssClass="error"/>
						</div>					
						<div class="form-group verifica_notifica">
							<label><fmt:message key="label.flag_notifica_intera_pratica" /></label>
							<spring-form:checkbox id="flagNotificainterapratica_id" path="flagNotificainterapratica"/>
							<init:help idHelp="help_flagNotificainterapratica" textKey="tipimovStcMapping.help.flag_notifica_intera_pratica"/>
							<spring-form:errors path="flagNotificainterapratica" cssClass="error"/>
						</div>					
						<div class="form-group verifica_notifica">
							<label><fmt:message key="label.flag_notifica_sub_endo" /></label>
							<spring-form:checkbox id="flagNotificaSubEndo_id" path="flagNotificaSubEndo"/>
							<init:help idHelp="help_flagNotificaSubEndo" textKey="label.flag_notifica_sub_endo.help"/>
							<spring-form:errors path="flagNotificaSubEndo" cssClass="error"/>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.flag_endo_obbligatorio"/></label>
							<spring-form:checkbox id="flagEndoObbligatorio_id" path="flagEndoObbligatorio"/>
							<init:help idHelp="help_flagEndoObbligatorio" textKey="label.flag_endo_obbligatorio.help"/>
							<spring-form:errors path="flagEndoObbligatorio" cssClass="error"/>					
						</div>
						<hr />					
						<div class="form-group">
							<label><fmt:message key="label.flag_allega_documenti_endo" /></label>
							<spring-form:checkbox id="flagAllegaDocumentiEndo_id" path="flagAllegaDocumentiEndo"/>
							<init:help idHelp="help_flagAllegaDocumentiEndo" textKey="tipimovStcMapping.help.flag_allega_documenti_endo"/>
							<spring-form:errors path="flagAllegaDocumentiEndo" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="label.flag_allega_documenti_istanza" /></label>
							<spring-form:checkbox id="flagAllegaDocumentiIstanza_id" path="flagAllegaDocumentiIstanza"/>
							<init:help idHelp="help_flagAllegaDocumentiIstanza" textKey="tipimovStcMapping.help.flag_allega_documenti_istanza"/>
							<spring-form:errors path="flagAllegaDocumentiIstanza" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="label.flag_allega_documenti_della_pratica" /></label>
							<spring-form:checkbox id="flagAllegaDocPratica_id" path="flagAllegaDocPratica"/>
							<init:help idHelp="help_flagAllegaDocPratica" textKey="tipimovStcMapping.help.flag_allega_doc_pratica"/>
							<spring-form:errors path="flagAllegaDocPratica" cssClass="error"/>
						</div>
						<hr />					
						<div class="form-group">
							<label><fmt:message key="label.flag_notificaa_automatica" /></label>
							<spring-form:select id="flagNotificaAutomatica_id" path="flagNotificaAutomatica" onclick="viewFlagDocAndAllegati()">
								<spring-form:option value="0"><fmt:message key="label.flag_notifica_automatica.notifica_non_automatica" /></spring-form:option>
								<spring-form:option value="1"><fmt:message key="label.flag_notifica_automatica.notifica_automatica" /></spring-form:option>
								<spring-form:option value="2"><fmt:message key="label.flag_notifica_automatica.notifica_automatica_inserimento" /></spring-form:option>
							</spring-form:select>					
							<init:help idHelp="help_flagNotificaAutomatica" textKey="help.flag_notifica_automatica"/>
							<spring-form:errors path="flagNotificaAutomatica" cssClass="error"/>
						</div>						
						<div id="tr_flgCreaZipLogico_id" class="form-group">
							<label><fmt:message key="label.flgCreaZipLogico" /></label>
							<spring-form:checkbox id="flgCreaZipLogico_id" path="flgCreaZipLogico"/>
							<init:help idHelp="help_flgCreaZipLogico" textKey="tipimovStcMapping.help.flgCreaZipLogico"/>
							<spring-form:errors path="flgCreaZipLogico" cssClass="error"/>
						</div>		
						<div id="tr_flagConvertipdf_id" class="form-group">
							<label><fmt:message key="label.flag_convertipdf" /></label>
							<spring-form:checkbox id="flagConvertipdf_id" path="flagConvertipdf"/>
							<init:help idHelp="help_flagConvertipdf" textKey="tipimovStcMapping.help.flag_convertipdf"/>
							<spring-form:errors path="flagConvertipdf" cssClass="error"/>
						</div>					
						<div id="tr_flagCreainviaAllegati_id" style="<%=displayflagAllegaDocumentiAndInviaAllegati%>" class="form-group">
							<label><fmt:message key="label.flag_crea_invia_allegati" /></label>
							<spring-form:checkbox id="flagCreainviaAllegati_id" path="flagCreainviaAllegati"/>
							<init:help idHelp="help_flagCreainviaAllegati" textKey="tipimovStcMapping.help.flag_crea_invia_allegati"/>
							<spring-form:errors path="flagCreainviaAllegati" cssClass="error"/>
						</div>					
						<div  id="tr_flagProtocolla_id" class="form-group">
							<label><fmt:message key="label.flagProtocolla" /></label>
							<spring-form:checkbox id="flagProtocolla_id" path="flagProtocolla" onclick="viewParametriProtocollazione()"/>
							<init:help idHelp="help_flagProtocolla" textKey="tipimovStcMapping.help.flag_protocolla"/>
							<spring-form:errors path="flagProtocolla" cssClass="error"/>
						</div>						
						<div  id="tr_flgProtocollaDocumenti_id" class="form-group">
							<label><fmt:message key="label.flgProtocollaDocumenti" /></label>
							<spring-form:checkbox id="flgProtocollaDocumenti_id" path="flgProtocollaDocumenti" />
							<init:help idHelp="help_flgProtocollaDocumenti" textKey="tipimovStcMapping.help.flgProtocollaDocumenti"/>
							<spring-form:errors path="flgProtocollaDocumenti" cssClass="error"/>
						</div>
						<div id="tr_flussso_id" style="<%=displayParametriProtocollo%>" class="form-group">
							<label><fmt:message key="label.flusso" /></label>
							<spring-form:select id="flusso_id" path="protocolloFlusso.codice"> 
							    <spring-form:option value="" ><fmt:message key='label.seleziona'/></spring-form:option>
								<spring-form:options items="${protocolloFlussos}" itemLabel="descrizione" itemValue="codice" />
							</spring-form:select>
							<spring-form:errors path="protocolloFlusso" cssClass="error"/>
							<init:help idHelp="help_flusso" textKey="tipimovStcMapping.help.flusso"/>
						</div>					
						<div id="tr_protocolloTipidocumento_id" style="<%=displayParametriProtocollo%>" class="form-group">
							<label><fmt:message key="label.tipo_documento" /></label>
							<spring-form:input id="protocolloTipidocumentoCodice_id" path="protocolloTipidocumentoCodice" size="50" />
							<init:help idHelp="help_tipo_documento" textKey="tipimovStcMapping.help.tipo_protocollo_documento"/>
							<spring-form:errors path="protocolloTipidocumentoCodice" cssClass="error"/>	
						</div>					
						<div id="tr_mailtipo_id" style="<%=displayParametriProtocollo%>" class="form-group">
							<label><fmt:message key="label.oggetto" /></label>
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="mailtipo" />		
								<jsp:param name="propertyPath" value="mailtipo" />				
								<jsp:param name="pathPropertyDescription" value="mailtipo.descrizione" />
								<jsp:param name="pathPropertyCode" value="mailtipo.id.codice" />
								<jsp:param name="autocompleterAjax" value="findMailtipo.htm" />	
								<jsp:param name="titleKey" value="label.ricerca_testi_tipo" />
								<jsp:param value="autocompleterInputSize" name="55"/>
							</jsp:include>	
							<init:help idHelp="help_oggetto" textKey="tipimovStcMapping.help.tipi_documento"/>						
						</div>						
						<div id="tr_amministrazioneMittente_id" style="<%=displayParametriProtocollo%>" class="form-group">
							<label><fmt:message key="label.amministrazione_mittente" /></label>
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="amministrazioneMittente" />		
								<jsp:param name="propertyPath" value="amministrazioneMittente" />				
								<jsp:param name="pathPropertyDescription" value="amministrazioneMittente.amministrazione" />
								<jsp:param name="pathPropertyCode" value="amministrazioneMittente.id.codice" />
								<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />	
								<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
								<jsp:param value="autocompleterInputSize" name="55"/>
							</jsp:include>
							<init:help idHelp="help_amministrazione_mittente" textKey="tipimovStcMapping.help.amministrazione_mittente"/>
						</div>

						<div id="tr_sovrascrivi_amm_dest_id" style="<%=displayParametriProtocollo%>" class="form-group" >
							<label><fmt:message key="label.sovrascrivi_amm_dest"/></label>
							<spring-form:checkbox id="flgSovrascriviAmmDest_id" path="flgSovrascriviAmmDest"/>
							<init:help idHelp="help_flgSovrascriviAmmDest" textKey="tipimovStcMapping.help.sovrascrivi_amm_dest"/>
							<spring-form:errors path="flgSovrascriviAmmDest" cssClass="error"/>
						</div>
										
						<div class="form-group">
							<label><fmt:message key="label.flag_invia_schede_istanza" /></label>
							<spring-form:checkbox id="flagInviaschedeistanza_id" path="flagInviaschedeistanza"/>
							<init:help idHelp="help_flagInviaschedeistanza" textKey="tipimovStcMapping.help.flag_invia_schede_istanza"/>
							<spring-form:errors path="flagInviaschedeistanza" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.codice_mov_documento" /></label>
							<spring-form:input id="codiceMovRecuperoAllegato_id" path="codiceMovRecuperoAllegato" size="50" />
							<spring-form:errors path="codiceMovRecuperoAllegato" cssClass="error"/>
							<div class="cod_mov">
								<fmt:message  key="form.tipimovstcmapping.codiceMovRecuperoAllegato.help"/>
							</div>
						</div>		
					</fieldset>			
				</spring-form:form>
   			</div>			    
		</div>

		<script type='text/javascript'>
					
			function viewFlagDocAndAllegati() {
				if (document.querySelector("#flagNotificaAutomatica_id ").value == 0) {
					if (document.querySelector("#tr_flagCreainviaAllegati_id")) {
						document.querySelector("#tr_flagCreainviaAllegati_id").style.display = "none";
					}
					if (document.querySelector("#tr_flagAllegaDocumentiIstanza_id")) {
						document.querySelector("#tr_flagAllegaDocumentiIstanza_id").style.display = "none";
					}
					if (document.querySelector("#tr_flussso_id")) {
						document.querySelector("#tr_flussso_id").style.display = "none";
					}
					if (document.querySelector("#tr_protocolloTipidocumento_id")) {
						document.querySelector("#tr_protocolloTipidocumento_id").style.display = "none";
					}
					if (document.querySelector("#tr_mailtipo_id")) {
						document.querySelector("#tr_mailtipo_id").style.display = "none";
					}
					if (document.querySelector("#tr_amministrazioneMittente_id")) {
						document.querySelector("#tr_amministrazioneMittente_id").style.display = "none";
					}
					if (document.querySelector("#tr_flgCreaZipLogico_id")) {
						document.querySelector("#tr_flgCreaZipLogico_id").style.display = "none";
						document.querySelector("#flgCreaZipLogico_id").checked = false;
					}
				} else if(document.querySelector("#flagNotificaAutomatica_id ").value == 1){
					
					if (document.querySelector("#tr_flgCreaZipLogico_id")) {
						document.querySelector("#tr_flgCreaZipLogico_id").style.display = "";
					}
					viewFlag();
					
				}else if(document.querySelector("#flagNotificaAutomatica_id ").value == 2){
					
					if (document.querySelector("#tr_flgCreaZipLogico_id")) {
						document.querySelector("#tr_flgCreaZipLogico_id").style.display = "none";
						document.querySelector("#flgCreaZipLogico_id").checked = false;
					}
					viewFlag();
				}
			}
			
			function viewFlag(){
				
				if (document.querySelector("#tr_flagCreainviaAllegati_id")) {
					document.querySelector("#tr_flagCreainviaAllegati_id").style.display = "";
				}
				if (document.querySelector("#tr_flagAllegaDocumentiIstanza_id")) {
					document.querySelector("#tr_flagAllegaDocumentiIstanza_id").style.display = "";
				}
				if (document.querySelector("#flagProtocolla_id").checked == true) {
					if (document.querySelector("#tr_flussso_id")) {
						document.querySelector("#tr_flussso_id").style.display = "";
					}
					if (document.querySelector("#tr_protocolloTipidocumento_id")) {
						document.querySelector("#tr_protocolloTipidocumento_id").style.display = "";
					}
					if (document.querySelector("#tr_mailtipo_id")) {
						document.querySelector("#tr_mailtipo_id").style.display = "";
					}
					if (document.querySelector("#tr_amministrazioneMittente_id")) {
						document.querySelector("#tr_amministrazioneMittente_id").style.display = "";
					}
				}
			
			}

			function viewParametriProtocollazione() {
				if (document.querySelector("#flagProtocolla_id").checked == false) {
					document.querySelector("#tr_flussso_id").style.display = "none";
					document.querySelector("#tr_protocolloTipidocumento_id").style.display = "none";
					document.querySelector("#tr_mailtipo_id").style.display = "none";
					document.querySelector("#tr_amministrazioneMittente_id").style.display = "none";
					document.querySelector("#tr_sovrascrivi_amm_dest_id").style.display = "none";
				} else {
					if (document.querySelector("#flagNotificaAutomatica_id").value != 0) {
						document.querySelector("#tr_flussso_id").style.display = "";
						document.querySelector("#tr_protocolloTipidocumento_id").style.display = "";
						document.querySelector("#tr_mailtipo_id").style.display = "";
						document.querySelector("#tr_amministrazioneMittente_id").style.display = "";
						document.querySelector("#tr_sovrascrivi_amm_dest_id").style.display = "";
					} else {
						document.querySelector("#tr_flussso_id").style.display = "none";
						document.querySelector("#tr_protocolloTipidocumento_id").style.display = "none";
						document.querySelector("#tr_mailtipo_id").style.display = "none";
						document.querySelector("#tr_amministrazioneMittente_id").style.display = "none";
						document.querySelector("#tr_sovrascrivi_amm_dest_id").style.display = "none";
					}
				}
			}

			document.querySelector("#flagNotificainterapratica_id").addEventListener("click", function() {
						verificaInteraPraticaSubendo();
					});

			document.querySelector("#flagNotificaSubEndo_id").addEventListener("click", function() {
						verificaInteraPraticaSubendo();
					});

			function verificaInteraPraticaSubendo() {
			    if (
			        document.querySelector("#flagNotificaSubEndo_id").checked &&
			        document.querySelector("#flagNotificainterapratica_id").checked
			    ) {
			        console.log("Attenzione entrambi selezionati");
			        document.querySelector("#flagNotificainterapratica_id").checked = false;
			        document.querySelector("#flagNotificaSubEndo_id").checked = false;
			        document.querySelector("#dialog_avviso_notifica").open();
			    }
			}
			
			vbg.ready(() => {
				if(document.querySelector('#closeButtonModal')){
					document.querySelector('#closeButtonModal').addEventListener('click', function() {
						document.querySelector("#dialog_avviso_notifica").close();
					});
				}
			})
		</script>	
	
		<vbg-modal id="dialog_avviso_notifica">			
			<div slot='body'>
				<h1>Non è possibile selezionare entrambe le modalità.</h1>
				<ul>
					<li><b><fmt:message key="label.flag_notifica_intera_pratica" /></b><br/>
						Prevale sul comportamento di <b><fmt:message key="label.flag_notifica_sub_endo" /></b>.<br/>
						
						<fmt:message key="tipimovStcMapping.help.flag_notifica_intera_pratica" />
					</li>
					<li><b><fmt:message key="label.flag_notifica_sub_endo" /></b><br />
					<fmt:message key="label.flag_notifica_sub_endo.help" />
					</li>
				</ul>			
				In entrambe i casi se viene specificato <b><fmt:message key="form.tipimovstcmapping.flagNonInviareProcedimenti" /></b>
				nella pratica creata non vengono inviati endorocedimenti (neanche quello del movimento)
			</div>
			<div slot="footer">
				<div class="btn btn-primary" id="closeButtonModal"><fmt:message key="button.close"/></div>
			</div>
		</vbg-modal>
	
		<div class="form-button">			
			<c:if test="${tipimovStcMapping.id.codice==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insertMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${tipimovStcMapping.id.codice!=null}">
				<a class="btn btn-primary" href="javascript:doSubmit('updateMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('deleteMapping.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm?tipimovimento.idtipomovimento=${idtipomovimento}&viewTab=TAB_MAPPING');" ><fmt:message key="button.back" /></a>
		</div>
	</body>
</html>
