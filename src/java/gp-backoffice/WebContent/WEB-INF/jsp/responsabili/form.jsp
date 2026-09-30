<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${responsabile.entity.id.codice==null}">
			<fmt:message key="responsabili.label.nuovo_responsabili.title" />
		</c:if>
 		<c:if test="${responsabile.entity.id.codice!=null}">
			<fmt:message key="responsabili.label.dettaglio_responsabili.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		<c:if test="${responsabile.entity.id.codice==null}">
			<fmt:message key="responsabili.label.nuovo_responsabili.title" />
		</c:if>
 		<c:if test="${responsabile.entity.id.codice!=null}">
			<fmt:message key="responsabili.label.dettaglio_responsabili.title" />
		</c:if>
	 </span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="responsabile" name="respForm">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="responsabile" />
			</jsp:include>
			<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../responsabili/view" />
	    	<jsp:param name="qs" value="codice%3D${responsabile.entity.id.codice}" />
		</jsp:include>	
			
			<table width="100%">
			    <tr>
					<td><fmt:message key="label.responsabile" /></td>
					<td class="inline-ui-cell" colspan="5"><spring-form:input tabindex="1" id="responsabile_id" path="entity.responsabile" size="67" maxlength="60" />
					<spring-form:errors	path="entity.responsabile" cssClass="error"/>

									<!-- §§§BEGIN§§§ -->
									
										<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">		
											<c:if test="${responsabile.entity.id.codice!=null}">									
												<spring-security:authorize ifNotGranted="ROLE_PREVIOUS_ADMINISTRATOR">&nbsp;
													<a href="javascript: switchUser('${responsabile.entity.userid}');" title="<fmt:message key="label.switch.user" />">
													<img src="../images/switch.gif" alt="<fmt:message key="label.switch.user" />" />
													</a>
		<script type="text/javascript">
			function switchUser(userid) {
				document._switch.j_username.value = userid;
				document._switch.submit();
			}
		</script>
		
												</spring-security:authorize>
											</c:if>												
										</spring-security:authorize>
									
									<!-- §§§END§§§ -->					
					
					</td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.tiporesponsabile" /></td>
					<td colspan="5"><spring-form:select tabindex="2" id="tiporesponsabile_id" path="entity.tiporesponsabile.id.codice" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${tipiresponsabiliList}" itemLabel="trDescrizione" itemValue="id.codice" />
					</spring-form:select>
					<spring-form:errors	path="entity.tiporesponsabile" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.matricola" /></td>
					<td colspan="5"><spring-form:input tabindex="3" id="matricola_id" path="entity.matricola" size="20" />
					<spring-form:errors	path="entity.matricola" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.codicefiscale" /></td>
					<td colspan="5"><spring-form:input tabindex="4" id="codicefiscale_id" path="entity.codicefiscale" size="20" maxlength="16"/>
					<spring-form:errors	path="entity.codicefiscale" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.titolo" /></td>
					<td colspan="5"><spring-form:input tabindex="5" id="titolo_id" path="entity.titolo" size="40" />
					<spring-form:errors	path="entity.titolo" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.qualifica" /></td>
					<td colspan="5"><spring-form:input tabindex="6" id="qualifica_id" path="entity.qualifica" size="20" />
					<spring-form:errors	path="entity.qualifica" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.datanascita" /></td>
					<td colspan="5"><spring-form:input tabindex="7" id="datanascita_id" path="entity.datanascita" size="20" />
					<spring-form:errors	path="entity.datanascita" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.indirizzo" /></td>
					<td colspan="5"><spring-form:input tabindex="8" id="indirizzo_id" path="entity.indirizzo" size="40" />
					<spring-form:errors	path="entity.indirizzo" cssClass="error" /></td>
				</tr>
				<tr>
					<td width="15">
						<fmt:message key="label.citta" />
					</td>
					<td width="25">
						<spring-form:input tabindex="9" id="citta_id" path="entity.citta" size="15" />
						<spring-form:errors	path="entity.citta" cssClass="error" />
					</td>
					<td width="15">
						<fmt:message key="label.cap" />
					</td>
					<td width="15">
						<spring-form:input tabindex="10" id="cap_id" path="entity.cap" size="5" maxlength="5"/>
						<spring-form:errors	path="entity.cap" cssClass="error" />
					</td>
					<td width="15">
						<fmt:message key="label.provincia" />
					</td>
					<td width="15">
						<spring-form:input tabindex="11" id="provincia_id" path="entity.provincia" size="2" maxlength="2"/>
						<spring-form:errors	path="entity.provincia" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.telefonolavoro" />
					</td>
					<td>
						<spring-form:input tabindex="12" id="telefonolavoro_id" path="entity.telefonolavoro" size="15" />
						<spring-form:errors path="entity.telefonolavoro" cssClass="error" />
					</td>
					<td>
						<fmt:message key="label.telefonocellulare" />
					</td>
					<td colspan="3">
						<spring-form:input tabindex="13" id="telefonocellulare_id" path="entity.telefonocellulare" size="15" />
						<spring-form:errors path="entity.telefonocellulare" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.telefonoabitazione" />
					</td>
					<td>
						<spring-form:input tabindex="14" id="telefonoabitazione_id" path="entity.telefonoabitazione" size="15" />
						<spring-form:errors path="entity.telefonoabitazione" cssClass="error" />
					</td>
					<td>
						<fmt:message key="label.fax" />
					</td>
					<td colspan="3">
						<spring-form:input tabindex="15" id="fax_id" path="entity.fax" size="15" />
						<spring-form:errors path="entity.fax" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.email" /></td>
					<td><spring-form:input tabindex="16" id="email_id" path="entity.email" size="40" />
					<spring-form:errors path="entity.email" cssClass="error" /></td>
				</tr>		
				<c:if test="${ isAssegnazioneOperatori eq true }">						
				<tr>
					<td><fmt:message key="label.peso_carico_lavoro" /></td>
					<td><spring-form:input tabindex="16" id="pesoCaricoLavoro_id" path="entity.pesoCaricoLavoro" size="4" maxlength="2" />
					<spring-form:errors path="entity.pesoCaricoLavoro" cssClass="error" /><fmt:message key="label.peso_carico_lavoro.help" /></td>
				</tr>					
				<tr>
					<td><fmt:message key="responsabili.label.flag_mod_responsabile_sorteggiato" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="34" id="flagModRespSort_id" path="entity.flagModRespSort" />
					<fmt:message key="responsabili.label.flag_mod_responsabile_sorteggiato.help" />
					<spring-form:errors path="entity.flagModRespSort" cssClass="error" /></td>
				</tr>			
				</c:if>		
				<tr class="titoloSezione">
					<td colspan="6"><spring-form:checkbox tabindex="17" id="amministratore_id" path="entity.amministratoreTransient" onclick="disable_enable();"/><b><fmt:message key="responsabili.label.checkbox_amministratore" /></b>
					<spring-form:errors path="entity.amministratoreTransient" cssClass="error" /></td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="6"><spring-form:checkbox id="amministratoresoftware_id" tabindex="18" path="entity.amministratoresoftwareTransient" /><b><fmt:message key="responsabili.label.checkbox_amministratoresoftware" /></b>
					<spring-form:errors path="entity.amministratoresoftwareTransient" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.userid" /></td>
					<td colspan="5">
						<spring-form:input tabindex="19" id="userid_id" path="entity.userid" size="33" maxlength="250" />
						<fmt:message key="responsabili.help.userid" />
						<spring-form:errors path="entity.userid" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password" /></td>
					<td colspan="5"><spring-form:input tabindex="20" id="password_clear_id" path="entity.passwordClear" size="33" maxlength="32"/>
					<spring-form:errors path="entity.passwordClear" cssClass="error" />
					<spring-form:errors path="entity.password" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.password_md5" /></td>
					<td colspan="5"><spring-form:input id="password_id" path="entity.password" size="33" disabled="true"/></td>
				</tr>
				<tr>
					<td id="cie_applet_id" colspan="6"></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.strong_userid" /></td>
					<td colspan="5">
						<spring-form:input id="stronguserid_id" path="entity.strongAuthId" size="100" maxlength="250" />
						<spring-form:errors path="entity.strongAuthId" cssClass="error" />
					</td>
				</tr>
				<%-- BOCCI 2012-02-21 ELIMINATO E GESTITO NELLA PAGINA CONFIGURAZIONE PARAMETRI SCADENZARIO BUGZILLA ID 435
				<tr>
					<td><fmt:message key="responsabili.label.scadenzario" /></td>
					<td><spring-form:checkbox tabindex="21" id="scadenzario_id" path="entity.scadenzario"  />
					<fmt:message key="responsabili.help.scadenzario" />
					<spring-form:errors path="entity.scadenzario" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.numggscadenz" /></td>
					<td>
					<spring-form:input tabindex="23" id="scadenzarioprec_id" path="entity.scadenzarioprec" size="2" maxlength="2"/>
					<spring-form:errors path="entity.scadenzarioprec" cssClass="error" />
					<spring-form:input tabindex="24" id="numggscadenz_id" path="entity.numggscadenz" size="5" maxlength="5"/>
					<spring-form:errors path="entity.numggscadenz" cssClass="error" />
					<fmt:message key="responsabili.help.numggscadenz" />
					</td>
				</tr>
				 --%>
				<tr>
					<td><fmt:message key="responsabili.label.updatehelp" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="25" id="updatehelp_id" path="entity.updatehelp" />
					<fmt:message key="responsabili.help.updatehelp" />
					<spring-form:errors path="entity.updatehelp" cssClass="error" /></td>
				</tr>	
				<c:if test="${isComuniAssociati}">
				<tr>
					<td>
						<fmt:message key="responsabili.label.responsabilicomunis" />
					</td>
					<td colspan="5">
						<spring:bind path="entity.responsabilicomunis">
							<input type="hidden" name="_${status.expression}" />
							<select tabindex="26" name="${status.expression}" multiple="multiple" style="width: 300px" size="${fn:length(responsabile.responsabilicomuniList)}">						
								<c:forEach var="respcom" items="${responsabile.responsabilicomuniList}" varStatus="counter">									 
									<c:forEach items="${responsabile.entity.responsabilicomunis}" var="currentRespCom">
										<c:if test="${currentRespCom.id.codicecomune == respcom.id.codicecomune}">
                      						 <c:set var="selected" value="true"/>
                  						 </c:if>
               						</c:forEach>               						 
									<option value="${respcom.comune.codicecomune}"
										<c:if test="${selected}">selected="selected"</c:if>>
											${respcom.comune.comune} (${respcom.comune.siglaprovincia})
									</option>
									<c:remove var="selected"/>								
								</c:forEach>
							</select>
							<span class="fieldError">${status.errorMessage}</span>							
						</spring:bind>
					</td>
				</tr>
				</c:if>
				<c:if test="${!isComuniAssociati}">
					<spring:bind path="entity.responsabilicomunis">
						
						<c:forEach items="${responsabile.entity.responsabilicomunis}" var="currentRespCom">
							<input type="hidden" name="${status.expression}" value="${currentRespCom.comune.codicecomune}"></input>
						</c:forEach>
					</spring:bind>
				</c:if>
				<tr>
				 	<td valign="middle">
				 		<fmt:message key="responsabili.label.softwareAbilitati" />
				 	</td>	
				 	<td colspan="5">
				 	<fieldset><legend><fmt:message key="responsabili.label.softwareAbilitati" /></legend>	 	
					 	<spring:bind path="entity.softwareAbilitati">
						 	<c:forEach var="responsabilisoftware" items="${responsabile.responsabilisoftwareList}" varStatus="counter">
						 		
						 		<c:set var="_disabled" value="" />
						 		<c:set var="_checked" value="" />					 		
								<input type="hidden" name="_${status.expression}" value="1"/>													
								<c:forEach var="d" items="${responsabile.entity.softwareAbilitati}">
									<c:if test="${d.software.codice eq responsabilisoftware.software.codice}">
										<c:set var="_checked" value="checked" />
									</c:if>							
								</c:forEach>
								<%
				    				pageContext.setAttribute("varTT",WebConstants.SOFTWARE_TT);
								%>
								<c:if test="${responsabilisoftware.software.codice eq varTT}">
						 			<c:set var="_disabled" value="disabled" />
						 			<c:set var="_checked" value="checked" />	
						 			<input type="hidden" id="${status.expression}_idsw" name="${status.expression}" value="${responsabilisoftware.software.codice}" checked="checked"/>			 			
						 		</c:if>				 		
								<input tabindex="27" type="checkbox" id="swabilitati_id${counter.index}" name="${status.expression}" value="${responsabilisoftware.software.codice}" ${_checked } ${_disabled } />
								<label for="swabilitati_id${counter.index}">${responsabilisoftware.software.descrizione}</label>
								<br />
							</c:forEach>					
						</spring:bind>	
					</fieldset>
					<spring-form:errors path="entity.softwareAbilitati" cssClass="error" />
					</td>
				</tr>										
				<tr>
					<td><fmt:message key="responsabili.label.flagGestioneOneri" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="28" id="flagGestioneOneri_id" path="entity.flagGestioneOneri" />
					<fmt:message key="responsabili.help.flagGestioneOneri" />
					<spring-form:errors path="entity.flagGestioneOneri" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.flagBloccaOneri" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="29" id="flagBloccaOneri_id" path="entity.flagBloccaOneri" />
					<fmt:message key="responsabili.help.flagBloccaOneri" />
					<spring-form:errors path="entity.flagBloccaOneri" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.readonly" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="30" id="readonly_id" path="entity.readonly" />
					<fmt:message key="responsabili.help.readonly" />
					<spring-form:errors path="entity.readonly" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.disabilitato" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="31" id="disabilitato_id" path="entity.disabilitato" />
					<fmt:message key="responsabili.help.disabilitato" />
					<spring-form:errors path="entity.disabilitato" cssClass="error" /></td>
				</tr>		
				<tr>
					<td><fmt:message key="responsabili.label.flagModificaNumist" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="32" id="flagModificaNumist_id" path="entity.flagModificaNumist" />
					<fmt:message key="responsabili.help.flagModificaNumist" />
					<spring-form:errors path="entity.flagModificaNumist" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.flagCancellaistanze" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="33" id="flagCancellaistanze_id" path="entity.flagCancellaistanze" />
					<fmt:message key="responsabili.help.flagCancellaistanze" />
					<spring-form:errors path="entity.flagCancellaistanze" cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="responsabili.label.flagCancelladocumentistc" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="34" id="flagCancelladocumentistc_id" path="entity.flagCancelladocumentistc" />
					<fmt:message key="responsabili.help.flagCancelladocumentistc" />
					<spring-form:errors path="entity.flagCancelladocumentistc" cssClass="error" /></td>
				</tr>
				<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
					<tr>
						<td><fmt:message key="responsabili.label.flagEditlabel" /></td>
						<td colspan="5"><spring-form:checkbox tabindex="34" id="flagEditlabel_id" path="entity.flagEditlabel" />
						<fmt:message key="responsabili.help.flagEditlabel" />
						<spring-form:errors path="entity.flagEditlabel" cssClass="error" /></td>
					</tr>				
				</spring-security:authorize>
				<tr>
					<td><fmt:message key="responsabili.label.flag_disabilita_modifiche_anagrafiche" /></td>
					<td colspan="5"><spring-form:checkbox tabindex="31" id="flagDisModanagrafe_id" path="entity.flagDisModanagrafe" />
					<fmt:message key="responsabili.label.flag_disabilita_modifiche_anagrafiche.help" />
					<spring-form:errors path="entity.flagDisModanagrafe" cssClass="error" /></td>
				</tr>
				<c:if test="${isDocErAttivo}">
				<tr>
					<td><fmt:message key="label.codice_utente_docer" /></td>
					<td colspan="5">
						<spring-form:input id="codUteDocer_id" path="entity.codUteDocer" size="20" maxlength="30" />
						<spring-form:errors path="entity.codUteDocer" cssClass="error" />
						
						<span id="docerStatus_id">
					
						</span>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password_utente_docer" /></td>
					<td colspan="5">
						<spring-form:password showPassword="true" id="passwordUteDocer_id" path="entity.passwordUteDocer" size="20" />
						<spring-form:errors path="entity.passwordUteDocer" cssClass="error" />
					</td>
				</tr>
				
				</c:if>
				
				
			</table>
		</spring-form:form>		
		<script type="text/javascript">
		jQuery("#responsabile_id").focus();
		disable_enable();
		function disable_enable(){
			if (jQuery("#amministratore_id").is(':checked')){
				jQuery("#amministratoresoftware_id").attr('disabled', true);
				jQuery("input[id^='swabilitati_id']").attr('disabled', true);
			}else{				
				jQuery("#amministratoresoftware_id").attr('disabled', false);
				jQuery("input[id^='swabilitati_id']").attr('disabled', false);
				jQuery("#swabilitati_id0").attr('disabled', true);
			}
		}
	

		
		
		
		function mostraOK(){
			jQuery('#docerStatus_id').html('<img title="L\'Informazione esiste in DOCER" src=\"${pageContext.request.contextPath}/images/success.png\" />');
		}
		
		function mostraKO(){
			jQuery('#docerStatus_id').html(htmlKO);
		}
		function mostraERRORE(errore){
			jQuery('#docerStatus_id').html(htmlKO+'&nbsp;<span class="error_header">'+errore+'</span>');
		}

		var htmlVerifica = "Verifica esistenza utente in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
		var htmlCreazione = "Creazione utente in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
		var htmlKO = '<a href="javascript:void(0)" onclick="creaUtente()"><img src=\"${pageContext.request.contextPath}/images/warning.gif\" /></a>';
		
		function verificaUtente(){
			
			var codiceGruppo = jQuery('#codUteDocer_id').val();
			if( codiceGruppo !=''){
				jQuery('#docerStatus_id').html( htmlVerifica);
			var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/controllaEsistenzaUtente.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceUtente="+jQuery('#codUteDocer_id').val(),
				  dataType: "text",
				  success: function(data) {
					  if(data){
							if(data=='true'){
								mostraOK();
							}else if (data=='false'){
								mostraKO();
							}else{
								mostraERRORE(data);
							}
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore: " + jqXHR.responseText);
				}
			});		
			}
		}
				
		function creaUtente(){
			var codiceGruppo = jQuery('#codUteDocer_id').val();
			if( codiceGruppo !=''){
				if(confirm('Attenzione l\'utente non sembra essere presente in DOCER. Procedendo verra\' creato.')){
				jQuery('#docerStatus_id').html( htmlCreazione );
				var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/creaUtente.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceUtente="+jQuery('#codUteDocer_id').val()+"&descrizioneUtente="+jQuery('#responsabile_id').val()+"&emailUtente="+jQuery('#email_id').val()+"&passwordUtente="+jQuery('#passwordUteDocer_id').val(),
				  dataType: "text",
				  success: function(data) {
					  if(data){
						  if(data=='true'){
								mostraOK();
							}else if (data=='false'){
								mostraKO();
							}else{
								mostraERRORE(data);
							}
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore: " + jqXHR.responseText);
				}
			});
			}
		}
		}
		
		<c:if test="${isDocErAttivo}">
			jQuery(document).ready(function(){
				
				verificaUtente();				
				
			});
		</c:if>
		</script>
	</div>
	<form name="_switch" action="<c:url value='../j_spring_security_switch_user'/>" method="post">
    			<input type="hidden" name='j_username'>
    </form>
	<%
	    String uriBack = "/responsabili/view.htm?codice=" + request.getParameter("codice");   
	    String urlaoo = BackofficeNETConstants.getURL_RESP_AOO_LISTA()+"?CodiceResponsabile="+request.getParameter("codice");
	    urlaoo = BackofficeNETConstants.getUrlTo(request,urlaoo,uriBack,null,false);
	    pageContext.setAttribute("urlaoo", urlaoo);
	%>
	<div id="functions">
	<ul>
		<c:if test="${responsabile.entity.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${responsabile.entity.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('createPermessi.htm?codice=${responsabile.entity.id.codice}&permessisoftware=TT','',document.inviodati)"><fmt:message key="responsabili.button.permessi_menu" /></a></li>
			<li><a href="javascript:doSubmit('createRuoli.htm?codice=${responsabile.entity.id.codice}','',document.inviodati)"><fmt:message key="responsabili.button.ruoli" /></a></li>
			<li><a href="${urlaoo}"><fmt:message key="responsabili.button.aoo_assegnazioni" /></a></li>
			<c:if test="${vert_prot_attivo == true}">
				<li><a href="javascript:doSubmit('createParametriprotocollo.htm?codice=${responsabile.entity.id.codice}','',document.inviodati)"><fmt:message key="responsabili.button.parametri_protocollo" /></a></li>
			</c:if>
			<li><a href="javascript:historySet('${_urlback}', '../responsabili/viewParametriscadenzario.htm?codice=${responsabile.entity.id.codice}', '')"><fmt:message key="button.parametri_scadenzario"/></a></li>
			<li><a title="<fmt:message key="help.resposabiliassenze.descrizione" />" href="javascript:historySet('${_urlback}', '../responsabiliassenze/list.htm?codice=${responsabile.entity.id.codice}', '')"> <fmt:message key="button.assenze"/></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('list.htm','')"><fmt:message	key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>