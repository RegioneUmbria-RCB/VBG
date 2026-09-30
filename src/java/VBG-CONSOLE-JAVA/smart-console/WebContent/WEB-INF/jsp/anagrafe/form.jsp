<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.Anagrafe"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${anagrafe.entity.id.codice==null}">
			<fmt:message key="anagrafe.label.nuovo_anagrafe.title" />
		</c:if> 
		<c:if test="${anagrafe.entity.id.codice!=null}">
			<fmt:message key="anagrafe.label.dettaglio_anagrafe.title" />
		</c:if>
	</title>
</head>
<body>
  
    <c:set var="personaGiuridicaval" value="<%= WebConstants.PERSONA_GIURIDICA %>" scope="page"/>
    <c:set var="personaFisicaval" value="<%= WebConstants.PERSONA_FISICA %>" scope="page"/>
    <% 
      	// SETTA LO STYLE INIZIALE 
    	// 1- Se l'anagrafe scelto è tecnico,mostra la sezione albo
    	// 2- Se non è tecnico la tiene nascosta

    	String displaySezioneAlbo="";
    	if(!request.getAttribute("isTecnico").toString().equals("-1"))
    	{
			 displaySezioneAlbo = "display:none;";
    	}
    	AnagrafeCommand ac = (AnagrafeCommand)request.getAttribute("anagrafe");
    	Anagrafe a = ac.getEntity();
    	if(StringUtils.isNotBlank(a.getNumeroelencopro()) || 
    			StringUtils.isNotBlank(a.getProvinciaelencopro()) ||
    			( a.getElenchiprofessionalibase()!=null && a.getElenchiprofessionalibase().getId()!=null )){
    		displaySezioneAlbo = "";
    	}
	%> 
	<span class="titoloPagina">
		<c:if test="${anagrafe.entity.id.codice==null}">
			<fmt:message key="anagrafe.label.nuovo_anagrafe.title" />
		</c:if> 
		<c:if test="${anagrafe.entity.id.codice!=null}">
			<fmt:message key="anagrafe.label.dettaglio_anagrafe.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafe/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="anagrafe" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="anagrafe" />
		    </jsp:include>
			<script type="text/javascript">
            <%-- 
            	variabile stringa che contien tutti gli id dei campi che hanno subito una 
            	modifica e questa è stata accettata/rifiutata separati dal carattere graticcio 
            	(es. campo1_id#campo2_id.....campoN) 
            --%>
			var elementiVisitati = '';
			
			function mostraNascondiEls(obj,tipo){	
				var a = document.getElementById(obj);
				if(tipo == '')
					{
						doHref('../anagrafe/${anagrafe.prefixPopup}create.htm?tiposoggetto='+a.value+"&popupCaller=${anagrafe.popupCaller}",'');	
					}else
				   {
						doHref('../anagrafe/${anagrafe.prefixPopup}create.htm?tiposoggetto='+a.value+'&visualizzaTipoAnagrafe='+tipo+'&popupCaller=${anagrafe.popupCaller}','');
						
				   }
				}
			<%-- La sezione albo deve essere mostrata solo se il flag tecnico è selezionato --%>
			function mostraNascondiSezioneAlbo()
			{
				if($('albo_hidden')){
					if(!($('albo_hidden').value!='' || $('numero_id').value!=''
							|| $('provincia_provinciaelencopro_id').value!='' )){
					
					 showHideElements("sezione_albo_id","tr");
					}
				}else{
					  showHideElements("sezione_albo_id","tr");
				}
				
				/*
				if(a==false)
				{
					$('albo_id').value='';
					$('albo_hidden').value='';
					$('numero_id').value='';
					$('provincia_albo_id').value='';
					$('provincia_albo_hidden').value='';
				}
				*/
			}
			<%-- FUNZIONE CHE GESTISCE L'APERTURA DELLA FINESTRA DI DIALOG
			     NEL CASO UN CAMPO SIA STATO AGGIORNATO --%>
			function gestDialogAnagrafe(elementId){
				if(elementiVisitati.indexOf(elementId, 0)<0){
					if(document.getElementById(elementId).style.display=='none'){
						dijit.byId(elementId).show();
					}else{
						dijit.byId(elementId).hide();
					}					
				}
			}
			<%-- FUNZIONI CHE GESTISCO LE AZIONI CHE POSSONO ESSERE SVOLTE SU UN CAMPO MODIFICATO
			     1- RIPRISTINO
			     2- ACCETTAZIONE CAMBIAMENTI 
			     3- RIPRISTINO CAMPO DI CHECKBOX PER IL CAMPO TIPOLOGIA
			     4- ACCETTAZIONE CAMBIAMENTI CAMPO DI CHECKBOX PER IL CAMPO TIPOLOGIA
			     5- RIPRISTINO CAMPO DI CHECKBOX 
			     6- ACCETTAZIONE CAMBIAMENTI CAMPO DI CHECKBOX 
			--%>			
			function ripristina(fieldId,oldfieldId,elementId){
				
				if(document.getElementById(fieldId)){
					document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
				}
				var a = document.getElementById(oldfieldId);
				$(fieldId).value=a.value;	
				gestDialogAnagrafe(elementId);
				elementiVisitati+=elementId+'#';
			
			}
			function accetta(fieldId,elementId){
				if(document.getElementById(fieldId)){
				document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
				}
				gestDialogAnagrafe(elementId);
				elementiVisitati+=elementId+'#';
		
			}
			
			function ripristinaCheckboxTipologia(fieldId,oldfieldId,elementId){
				document.getElementById(fieldId).style.outlineColor='#FFFFFF';
				document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
				if(document.getElementById(oldfieldId).value==-1){
					$(fieldId).checked='checked';
				}else
				{
					$(fieldId).checked='';
				}
				gestDialogAnagrafe(elementId);
				elementiVisitati+=elementId+'#';
			
			}
			function accettaCheckboxTipologia(fieldId,elementId){
				if(document.getElementById(fieldId).checked==false){
					$(fieldId).checked='';
				}else
				{
					$(fieldId).checked='checked';
				}
				document.getElementById(fieldId).style.outlineColor='#FFFFFF';
				document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
				gestDialogAnagrafe(elementId);
				elementiVisitati+=elementId+'#';
		
			}
			
            function ripristinaCheckbox(fieldId,oldfieldId,elementId){
            	document.getElementById(fieldId).style.outlineColor='#FFFFFF';
				document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
				if(document.getElementById(oldfieldId).value=='true'){
					$(fieldId).checked='checked';
				}else
				{
					$(fieldId).checked='';
				}
				gestDialogAnagrafe(elementId);
				elementiVisitati+=elementId+'#';
			
			}
			function accettaCheckbox(fieldId,elementId){
				if(document.getElementById(fieldId).checked==false){
					$(fieldId).checked='';
				}else
				{
					$(fieldId).checked='checked';
				}
				document.getElementById(fieldId).style.outlineColor='#FFFFFF';
				document.getElementById(fieldId).style.backgroundColor='#FFFFFF';
				gestDialogAnagrafe(elementId);
				elementiVisitati+=elementId+'#';
		
			}
			function nuovaPassword(){
				
				new Ajax.Request('<%=request.getContextPath()%>/anagrafe/ajaxGeneraPassword.htm', {
					  method: 'post',
					  parameters: {},
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  $('password_clear_id').value = response;						  	  
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
							alert(response);
						 }						    		 
				});
			}
			
			function stampaRicevuta(url){
				var pwd = $('password_clear_id').value;
				if(pwd == ''){
					alert("Per la stampa è necessario generare una password.\nAttenzione! Dopo la stampa salvare la scheda per rendere effettiva la nuova password.");
				}else{
					url = url.replace('SEGNAPOSTO',pwd);
					// alert(url);
					window.open(url,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
				}
					   
			}
			
			function calcolaCodicefiscale(){
				
				var sesso=$('sesso_id').value;
				var nominativo=$('nominativo_id').value;
				var nome=$('nome_id').value;
				var datanascita=$('data_nascita_id').value;
				var codicecomune=$('comune_nascita_hidden').value;
				if(sesso==''){
					alert('<fmt:message key="label.sesso" /> <fmt:message key="alert.required" />');
					return;
				}
				if(nominativo==''){
					alert('<fmt:message key="label.cognome" /> <fmt:message key="alert.required" />');
					return;
				}
				if(nome==''){
					alert('<fmt:message key="label.nome" /> <fmt:message key="alert.required" />');
					return;
				}
				if(datanascita==''){
					alert('<fmt:message key="label.data_nascita" /> <fmt:message key="alert.required" />');
					return;
				}
				if(codicecomune==''){
					alert('<fmt:message key="label.comune_nascita" /> <fmt:message key="alert.required" />');
					return;
				}
				new Ajax.Request('<%=request.getContextPath()%>/anagrafe/ajaxGeneraCodicefiscale.htm', {
					  method: 'post',
					  parameters: {nominativo: nominativo, nome: nome, sesso:sesso, datanascita:datanascita, codicecomune:codicecomune},
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  $('codice_fiscale_id').value=response;						  	  
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
							alert(response);
					  }						    		 
				});
			}
			</script>
            <%-- DATI GENERALI 
			 START 
		     --%>
			
			<c:set var="VERTICALIZZAZIONE_WSANAGRAFE_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE)%></c:set>
			<c:set var="ESCLUDI_RICERCA_PER_PF_REQUEST"><%=request.getAttribute(WebConstants.ESCLUDI_RICERCA_PER_PF)%></c:set>			
			<table width="100%">
			<c:if test="${anagrafe.entity.id.codice == null}">	
				<!-- §§§BEGIN§§§ -->
				<c:if test="${inite:isEnterprise()}">
						<c:if test="${VERTICALIZZAZIONE_WSANAGRAFE_REQUEST eq true}">
							<%-- GESTIONE FUNZIONALITà RICERCA ANAGRAFICA DA STSIMA ESTERNO PER PERS. FISICA --%>
							<%--Per la persona fisica eiste in verticalizzazione un ulteriorte parametro che deve essere attivato
							    affinchè la funzionalità di ricerca da un sistema inteno sia attiva --%>
							<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval && ESCLUDI_RICERCA_PER_PF_REQUEST eq 1 }">
							<tr class="titoloSezione">
								<td colspan="6">
									<fmt:message key="anagrafe.label.ricerca_dal_ws" />
								</td>
							</tr>		
							<tr>
								<td width="30%">
									<fmt:message key="label.codice_fiscale" />
								</td>
								<td colspan="5">
									<spring-form:input id="cfPivaRicercaWs_id" path="cfPivaRicercaWs" size="20" maxlength="16" cssStyle="float: left;"/>
									<span id="functions">
										<ul style="margin-top: -2px;">											
											<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}popolaDatiDaWs.htm','',document.inviodati)"><fmt:message key="button.richiedi_dati" /></a></li>
										</ul>
									</span>		
								</td>
							</tr>		
							</c:if>
							<%-- GESTIONE FUNZIONALITà RICERCA ANAGRAFICA DA STSIMA ESTERNO PER PERS. GIURIDICA --%>
							<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
							<tr class="titoloSezione">
								<td colspan="6">
									<fmt:message key="anagrafe.label.ricerca_dal_ws" />
								</td>
							</tr>
							<tr>	
								<td width="30%">
									<fmt:message key="label.partita_iva" />/<fmt:message key="label.codice_fiscale" />
								</td>
								<td colspan="5">
									<spring-form:input id="cfPivaRicercaWs_id" path="cfPivaRicercaWs" size="20" maxlength="16" cssStyle="float: left;"/>
									<span id="functions">
										<ul style="margin-top: -2px;">											
											<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}popolaDatiDaWs.htm','',document.inviodati)"><fmt:message key="button.richiedi_dati" /></a></li>
										</ul>
									</span>		
								</td>
							</c:if>	
							</tr>
						</c:if>	
				</c:if>
				<!-- §§§END§§§ -->
				
			</c:if>
			<tr class="titoloSezione">
					<td colspan="6">
						<fmt:message key="anagrafe.label.dati_generali" />
					</td>
			</tr>		
				<tr>
					<td width="30%">
						<fmt:message key="anagrafe.label.tipo_anagrafe" />
					</td>
					<c:if test="${anagrafe.entity.id.codice==null}">
					<td colspan="5" >
					    <c:if test="${anagrafe.popupCaller != ''}">
					    	
					    	<c:if test="${anagrafe.visualizzaTipoAnagrafe eq '' || anagrafe.visualizzaTipoAnagrafe eq personaFisicaval}">
						    	<spring-form:radiobutton id="id_fisica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_FISICA%>" onclick="mostraNascondiEls(id,'${anagrafe.visualizzaTipoAnagrafe}')"  />
						    	<label for="id_fisica"><fmt:message key="label.persona_fisica"/></label>
					    	</c:if>
					    	<c:if test="${anagrafe.visualizzaTipoAnagrafe eq '' || anagrafe.visualizzaTipoAnagrafe eq personaGiuridicaval}">
						    	<spring-form:radiobutton id="id_giuridica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_GIURIDICA%>" onclick="mostraNascondiEls(id,'${anagrafe.visualizzaTipoAnagrafe}')"  />
						    	<label for="id_giuridica"><fmt:message key="label.persona_giuridica" /></label>	 
					    	</c:if>   
							<spring-form:errors path="entity.tipoanagrafe" cssClass="error"/>
						</c:if>
						<c:if test="${anagrafe.popupCaller == ''}">
							<spring-form:radiobutton id="id_fisica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_FISICA%>" onclick="mostraNascondiEls(id,'')"  />
					    	<label for="id_fisica"><fmt:message key="label.persona_fisica"/></label>
					    	<spring-form:radiobutton id="id_giuridica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_GIURIDICA%>" onclick="mostraNascondiEls(id,'')"  />
					    	<label for="id_giuridica"><fmt:message key="label.persona_giuridica" /></label>	    
							<spring-form:errors path="entity.tipoanagrafe" cssClass="error"/>
						</c:if>
					</td>
					</c:if>
					<c:if test="${anagrafe.entity.id.codice!=null}">
					<td colspan="5">
					   <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
					     <input type="text" value="<fmt:message key="label.persona_fisica" />" readonly="readonly" size="30" />
					     <c:if test="${isConversioneAnagrafica eq true }">
					     	<label><a class="linkConversioneFisicaGiuridica" href="javascript:doHref('../anagrafe/covertTipoAnagrafe.htm?codice=${anagrafe.entity.id.codice}','')"><fmt:message key="label.persona_fisica_to_giuridica" /></a></label>
					     </c:if>
					   </c:if>
					   <c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
					     <input type="text" value="<fmt:message key="label.persona_giuridica" />" readonly="readonly" size="30" />
					     <c:if test="${isConversioneAnagrafica eq true }">
					     	<label ><a class="linkConversioneFisicaGiuridica" href="javascript:doHref('../anagrafe/covertTipoAnagrafe.htm?codice=${anagrafe.entity.id.codice}','')"><fmt:message key="label.persona_giuridica_to_fisica" /></a></label>
					     </c:if>
					   </c:if>
					</td>
					</c:if>
				</tr>				
				<%-- VISUALIZZATA SE SCELTO PERSONAFISICA 
				 START --%>
			    <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval }">
				<tr>
					<td>
						<fmt:message key="label.cognome" />
					</td>
					<td>
						<c:if test="${anagrafe.entity.nominativo eq anagrafe.oldAnagrafe.nominativo || anagrafe.entity.nominativo==null}">
							<spring-form:input id="nominativo_id" path="entity.nominativo" size="30" />
						</c:if>
						<c:if test="${anagrafe.entity.nominativo ne anagrafe.oldAnagrafe.nominativo && anagrafe.entity.nominativo!=null }">
							<spring-form:input cssStyle="background:red;" id="nominativo_id" path="entity.nominativo" size="30" onclick="gestDialogAnagrafe('nominativo_modificatoOverlay_id')"/>
							<input id="id_nuovo_nominativo" type="hidden" value="${anagrafe.oldAnagrafe.nominativo}" name="oldAnagrafe.nominativo"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.nominativo}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.nominativo}" />
							   <jsp:param value="nominativo_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="nominativo_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="nominativo_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_nominativo" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.nominativo" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.nome" />
					</td>
					<td colspan="3">
						<c:if test="${anagrafe.entity.nome eq anagrafe.oldAnagrafe.nome || anagrafe.entity.nome==null}">
							<spring-form:input id="nome_id" path="entity.nome" size="30" />
						</c:if>
						<c:if test="${anagrafe.entity.nome ne anagrafe.oldAnagrafe.nome && anagrafe.entity.nome!=null }">
							<spring-form:input cssStyle="background:red;" id="nome_id" path="entity.nome" size="30" onclick="gestDialogAnagrafe('nome_modificatoOverlay_id')"/>
							<input id="id_nuovo_nome" type="hidden" value="${anagrafe.oldAnagrafe.nome}" name="oldAnagrafe.nome"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.nome}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.nome}" />
							   <jsp:param value="nome_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="nome_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="nome_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_nome" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.nome" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<%-- END -->
				
				START --%>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO FLAG NO PROFIT SARà UGUALE A true  DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO FLAG NO PROFIT SARà UGUALE A false DOVRà MOSTRALO NON SELEZIONATO
				    String showCheckedNoProfit="";
					if(((Boolean) request.getAttribute("flagNoProfit")!=null))
					{
						if ((Boolean) request.getAttribute("flagNoProfit")==true) {
						    showCheckedNoProfit = "checked='checked'";
		    			} else {
		    				showCheckedNoProfit = "";
						}
					}
				%>
				
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr >
					<td>
						<fmt:message key="label.ragione_sociale" />
					</td>
					<td>						
						<c:if test="${anagrafe.entity.nominativo eq anagrafe.oldAnagrafe.nominativo || anagrafe.entity.nominativo==null}">
							<spring-form:input id="ragione_sociale_id" path="entity.nominativo" size="60" />
						</c:if>
						<c:if test="${anagrafe.entity.nominativo ne anagrafe.oldAnagrafe.nominativo && anagrafe.entity.nominativo!=null }">
							<spring-form:input cssStyle="background:red;" id="ragione_sociale_id" path="entity.nominativo" size="60" onclick="gestDialogAnagrafe('ragione_sociale_modificatoOverlay_id')"/>
							<input id="id_nuovo_ragione_sociale" type="hidden" value="${anagrafe.oldAnagrafe.nominativo}" name="oldAnagrafe.nominativo"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.nominativo}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.nominativo}" />
							   <jsp:param value="ragione_sociale_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="ragione_sociale_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="ragione_sociale_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_ragione_sociale" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.nominativo" cssClass="error"/>
					</td>
					<td colspan="4">
						<c:if test="${anagrafe.entity.flagNoprofit == anagrafe.oldAnagrafe.flagNoprofit}">
							<spring-form:checkbox id="flag_no_profit_id" path="entity.flagNoprofit"  />
						</c:if>
						<c:if test="${anagrafe.entity.flagNoprofit != anagrafe.oldAnagrafe.flagNoprofit}">
					    	<input type="checkbox" id="flag_no_profit_id" <%=showCheckedNoProfit%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.flagNoprofit"  value="true" onmouseover="javascript:gestDialogAnagrafe('flag_no_profit_dialog');" />
					    	<input type="hidden" id="id_nuovo_flag_no_profit"  name=""  value="${anagrafe.oldAnagrafe.flagNoprofit}"/>
							<div id="flag_no_profit_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="flag_no_profit_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
									    <c:if test="${anagrafe.entity.flagNoprofit==true}">
								    		<td  class="parametri"><fmt:message key="label.si" /></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.flagNoprofit==false}">
								    		<td  class="parametri"><fmt:message key="label.no" /></td>
								    	</c:if>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.flagNoprofit==true}">
									       <td  class="parametri"><fmt:message key="label.si" /></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.flagNoprofit==false}">
									       <td  class="parametri"><fmt:message key="label.no" /></td>
									    </c:if>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="ripristinaCheckbox('flag_no_profit_id','id_nuovo_flag_no_profit','flag_no_profit_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accettaCheckbox('flag_no_profit_id','flag_no_profit_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>	
						<spring-form:errors path="entity.flagNoprofit" cssClass="error"/>
						<fmt:message key="label.flag_no_profit" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.forma_giuridica" />
					</td>
					<td colspan="5" >
					<c:if test="${anagrafe.entity.formagiuridica.id.codice == anagrafe.oldAnagrafe.formagiuridica.id.codice || anagrafe.entity.formagiuridica.id.codice==null }">
						<spring-form:input id="formagiuridica_id" path="entity.formagiuridica.formagiuridica" cssClass="searchbox" onchange="checkValue(this,'formagiuridica_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findFormegiuridiche.htm" idHidden="formagiuridica_hidden" idInput="formagiuridica_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
						<spring-form:errors path="entity.formagiuridica" cssClass="error"/> 
						<spring-form:hidden id="formagiuridica_hidden" path="entity.formagiuridica.id.codice"  />
					</c:if>
					<c:if test="${anagrafe.entity.formagiuridica.id.codice != anagrafe.oldAnagrafe.formagiuridica.id.codice && anagrafe.entity.formagiuridica.id.codice!=null }">
						<spring-form:input id="formagiuridica_id" path="entity.formagiuridica.formagiuridica" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'formagiuridica_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('formagiuridica_modificatoOverlay_id')"  size="40"/>
						<init:autocompleter methodAjax="findFormegiuridiche.htm" idHidden="formagiuridica_hidden" idInput="titolo_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
						<spring-form:errors path="entity.formagiuridica" cssClass="error"/> 
						<spring-form:hidden id="formagiuridica_hidden" path="entity.formagiuridica.id.codice"  />
						<input type="hidden" id="id_nuovo_formagiuridica" name="oldAnagrafe.formagiuridica.id.codice"  value="${anagrafe.oldAnagrafe.formagiuridica.id.codice}"  />
						<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								<jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.formagiuridica.formagiuridica}" />
								<jsp:param name="nuovocampo" value="${anagrafe.entity.formagiuridica.formagiuridica}" />
								<jsp:param value="formagiuridica_modificatoOverlay_id" name="id_div_overlay"/>
								<jsp:param value="formagiuridica_modificatoInner_id" name="id_div_inner"/>
								<jsp:param value="formagiuridica_id" name="fieldId"/>
								<jsp:param value="id_nuovo_formagiuridica" name="oldfieldId"/>
						</jsp:include>	
					</c:if>
					</td>
				</tr>
				</c:if>
				<%-- END
				VISUALIZZATA SE SCELTO PERSONAGIURIDICA --%>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
				<tr>
					<td>
						<fmt:message key="label.titolo" />
					</td>
					<td colspan="5" >
					<c:if test="${anagrafe.entity.titolo.id.codice == anagrafe.oldAnagrafe.titolo.id.codice || anagrafe.entity.titolo.id.codice==null }">
						<spring-form:input id="titolo_id" path="entity.titolo.titolo" cssClass="searchbox" onchange="checkValue(this,'titolo_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findTitoli.htm" idHidden="titolo_hidden" idInput="titolo_id" inputTitleKey="label.ricerca_titoli"></init:autocompleter>
						<spring-form:errors path="entity.titolo" cssClass="error"/> 
						<spring-form:hidden id="titolo_hidden" path="entity.titolo.id.codice"  />
					</c:if>
					<c:if test="${anagrafe.entity.titolo.id.codice != anagrafe.oldAnagrafe.titolo.id.codice && anagrafe.entity.titolo.id.codice!=null }">
						<spring-form:input id="titolo_id" path="entity.titolo.titolo" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'titolo_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('titolo_modificatoOverlay_id')"  size="40"/>
						<init:autocompleter methodAjax="findTitoli.htm" idHidden="titolo_hidden" idInput="titolo_id" inputTitleKey="label.ricerca_titoli"></init:autocompleter>
						<spring-form:errors path="entity.titolo" cssClass="error"/> 
						<spring-form:hidden id="titolo_hidden" path="entity.titolo.id.codice"  />
						<input type="hidden" id="id_nuovo_titolo" name="oldAnagrafe.titolo.id.codice"  value="${anagrafe.oldAnagrafe.titolo.id.codice}"  />
						<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								<jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.titolo.titolo}" />
								<jsp:param name="nuovocampo" value="${anagrafe.entity.titolo.titolo}" />
								<jsp:param value="titolo_modificatoOverlay_id" name="id_div_overlay"/>
								<jsp:param value="titolo_modificatoInner_id" name="id_div_inner"/>
								<jsp:param value="titolo_id" name="fieldId"/>
								<jsp:param value="id_nuovo_titolo" name="oldfieldId"/>
						</jsp:include>	
					</c:if>	
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval }">
				<tr>
					<td>
						<fmt:message key="label.sesso" />
					</td>
					<td colspan="5">					
						<c:if test="${anagrafe.entity.sesso eq anagrafe.oldAnagrafe.sesso || anagrafe.entity.sesso==null}">
							<spring-form:select id="sesso_id" path="entity.sesso">
								<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
								<spring-form:option value="<%=WebConstants.MASCHIO%>"><fmt:message key="label.maschio" /></spring-form:option>
								<spring-form:option value="<%=WebConstants.FEMMINA%>"><fmt:message key="label.femmina" /></spring-form:option>
							</spring-form:select>							
						</c:if>
						<c:if test="${anagrafe.entity.sesso ne anagrafe.oldAnagrafe.sesso && anagrafe.entity.sesso!=null }">							
							<spring-form:select id="sesso_id" path="entity.sesso" cssStyle="background:red;" onclick="gestDialogAnagrafe('sesso_modificatoOverlay_id')" >
								<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
								<spring-form:option value="<%=WebConstants.MASCHIO%>"><fmt:message key="label.maschio" /></spring-form:option>
								<spring-form:option value="<%=WebConstants.FEMMINA%>"><fmt:message key="label.femmina" /></spring-form:option>
							</spring-form:select>																				
							<input id="id_nuovo_sesso" type="hidden" value="${anagrafe.oldAnagrafe.sesso}" name="oldAnagrafe.sesso"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.sesso}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.sesso}" />
							   <jsp:param value="sesso_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="sesso_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="sesso_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_sesso" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.sesso" cssClass="error"/>
					</td>
				</tr>
			    </c:if>
			    <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
			    <tr>
					<td>
						<fmt:message key="label.cittadinanza" />
					</td>
					<td colspan="5" >
					<c:if test="${anagrafe.entity.cittadinanza.codice == anagrafe.oldAnagrafe.cittadinanza.codice || anagrafe.entity.cittadinanza.codice==null }">
						<spring-form:input id="cittadinanza_id" path="entity.cittadinanza.cittadinanza" cssClass="searchbox" onchange="checkValue(this,'cittadinanza_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="22"/>
						<init:autocompleter methodAjax="findcittadinanze.htm" idHidden="cittadinanza_hidden" idInput="cittadinanza_id" inputTitleKey="label.ricerca_cittadinanza"></init:autocompleter>
						<spring-form:errors path="entity.cittadinanza" cssClass="error"/> 
						<spring-form:hidden id="cittadinanza_hidden" path="entity.cittadinanza.codice"  />
					</c:if>
					<c:if test="${anagrafe.entity.cittadinanza.codice != anagrafe.oldAnagrafe.cittadinanza.codice && anagrafe.entity.cittadinanza.codice!=null }">
						<spring-form:input id="cittadinanza_id" path="entity.cittadinanza.cittadinanza" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'cittadinanza_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('cittadinanza_modificatoOverlay_id')"  size="22"/>
							<init:autocompleter methodAjax="findcittadinanze.htm" idHidden="cittadinanza_hidden" idInput="cittadinanza_id" inputTitleKey="label.ricerca_cittadinanza"></init:autocompleter>
							<spring-form:errors path="entity.cittadinanza" cssClass="error"/> 
							<spring-form:hidden id="cittadinanza_hidden" path="entity.cittadinanza.codice"/>
							<input type="hidden" id="id_nuovo_cittadinanza" name="oldAnagrafe.cittadinanza.cittadinanza"  value="${anagrafe.oldAnagrafe.cittadinanza.cittadinanza}"  />
						
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.cittadinanza.cittadinanza}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.cittadinanza.cittadinanza}" />
							   <jsp:param value="cittadinanza_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="cittadinanza_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="cittadinanza_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_cittadinanza" name="oldfieldId"/>
							</jsp:include>	
					
					</c:if>
					</td>
				</tr>
				</c:if>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO TIPOLOGIA SARà UGUALE A -1 DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO TIPOLOGIA SARà UGUALE A 0 DOVRà MOSTRALO NON SELSZIONATO
				    String showChecked1="";
					if(((Integer) request.getAttribute("tipologia")!=null))
					{
						if ((Integer) request.getAttribute("tipologia")==-1) {
		   					showChecked1 = "checked='checked'";
		    			} else {
							showChecked1 = "";
						}
					}
				%>
				<tr>
					<td>
						<fmt:message key="label.tipo_soggetto" />
					</td>
					<td colspan="5" >
					<c:if test="${anagrafe.entity.tipologia == anagrafe.oldAnagrafe.tipologia}">
						<spring-form:checkbox id="tipologia_id" path="entity.tipologia" value="-1" onclick="mostraNascondiSezioneAlbo()" />
					</c:if>	
					<c:if test="${anagrafe.entity.tipologia != anagrafe.oldAnagrafe.tipologia}">
					    <input type="checkbox" id="tipologia_id" <%=showChecked1%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.tipologia"  value="-1" onmouseover="javascript:gestDialogAnagrafe('tipologia_dialog');" onclick="mostraNascondiSezioneAlbo()" />
					    <input type="hidden" id="id_nuovo_tipologia"  name=""  value="${anagrafe.oldAnagrafe.tipologia}"/> 
						<div id="tipologia_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="tipologia_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
									    <c:if test="${anagrafe.entity.tipologia==-1}">
								    		<td  class="parametri"><fmt:message key="label.tecnico" /></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.tipologia==0}">
								    		<td  class="parametri"><fmt:message key="label.non_tecnico" /></td>
								    	</c:if>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.tipologia==-1}">
									       <td  class="parametri"><fmt:message key="label.tecnico" /></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.tipologia==0}">
									       <td  class="parametri"><fmt:message key="label.non_tecnico" /></td>
									    </c:if>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="ripristinaCheckboxTipologia('tipologia_id','id_nuovo_tipologia','tipologia_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accettaCheckboxTipologia('tipologia_id','tipologia_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
					</c:if>	
						<spring-form:errors path="entity.tipologia" cssClass="error"/>
						<fmt:message key="tipimovimento.label.descrizione_tipo_soggetto"/>
					</td>
				</tr>
	
				<tr>
					<td><fmt:message key="label.password" /></td>
					<td colspan="6">
					<spring-form:input id="password_clear_id" path="entity.passwordClear" size="8" cssStyle="float: left;"/>
					<spring-form:errors path="entity.passwordClear" cssClass="error" />
						<div id="functions" style="display: inline;">
							<ul style="margin-top: -2px;">
							    <li><a href="javascript:nuovaPassword();"><fmt:message key="button.genera_password" /></a></li>
							    <!-- §§§BEGIN§§§ -->
							    <c:if test="${inite:isEnterprise()}">
								    <c:if test="${anagrafe.entity.id.codice!=null}">
								    <%-- STAMPA RICEVUTA --%>
									<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_RICHIEDENTI_DOCUMENTI_TIPO());%>
									<%-- la password è settata dalla funzione stampaRicevuta a runtime recuperandola dal form quindi utilizzo un segnaposto per la sostituzione--%>
									<c:set var="_URL_STAMPA_RICEVUTA" value="${URL_STAMPA}?CodiceAnagrafe=${anagrafe.entity.id.codice}&Doc_base=SCHEDAANAGRAFE.RTF&Password=SEGNAPOSTO" />
									<c:set var="_URL_STAMPA_RICEVUTA" value="${inite:geturlto(pageContext.request, _URL_STAMPA_RICEVUTA, _urlback, null, true)}" />
								    <%
								    try{
								    	// devo eseguire l'encoding perchè quando passo l'url alla funzione js stampaRicevuta questa esegue il decode!
										String encS = java.net.URLEncoder.encode((String)pageContext.getAttribute("_URL_STAMPA_RICEVUTA"),"UTF-8");
								    	pageContext.setAttribute("_URL_STAMPA_RICEVUTA", encS);
								    }catch(Exception e){}
								    %>
								    <li><a href="javascript:stampaRicevuta('${_URL_STAMPA_RICEVUTA}');"><fmt:message key="button.stampa" /></a></li>	  
								    </c:if>
							    </c:if>
							    <!-- §§§END§§§ -->
							    <c:if test="${isPasswordSet=='1'}">
							    <li><a href="javascript:doHref('resetPassword.htm?codice=${anagrafe.entity.id.codice}','',document.inviodati)"><fmt:message key="button.reset_password" /></a></li>
								</c:if>
							</ul>
					    </div>
					    <fmt:message key="label.descrizione_password_anagrafica" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password_md5" /></td>
					<td>
						<spring-form:input id="password_id" path="entity.password" size="50" disabled="true"/>
					</td>
					<%--
					<c:if test="${isPasswordSet=='1'}">
					<td colspan="5">
						<div id="functions">
							<ul>
							    <li><a href="javascript:doHref('resetPassword.htm?codice=${anagrafe.entity.id.codice}','',document.inviodati)"><fmt:message key="button.reset_password" /></a></li>
							</ul>
					    </div>
					</td>
					</c:if>
					--%>
				</tr>
				<%-- DATI RESIDENZA
				START --%>
				<%
					String displayResidenza_sedeLegale= "display:none;";
					String styleResidenza_sedeLegale = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE)).equals("1")) {
					    displayResidenza_sedeLegale = "";
					    styleResidenza_sedeLegale="sezioneDatiMeno";
					} else {
					    displayResidenza_sedeLegale = "display:none;";
					    styleResidenza_sedeLegale="sezioneDatiPiu";
					}
				%>
				
	            <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleResidenza_sedeLegale%>" id="id_link_residenza_sede_legale" href="javascript:showHidePanel('id_residenza_sede_legale_table', 'id_link_residenza_sede_legale', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.residenza"/>">
							<label for="id_link_residenza_sede_legale"><fmt:message key="label.residenza"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleResidenza_sedeLegale%>" id="id_link_residenza_sede_legale" href="javascript:showHidePanel('id_residenza_sede_legale_table', 'id_link_residenza_sede_legale', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.sede_legale"/>">
							<label for="id_link_residenza_sede_legale"><fmt:message key="label.sede_legale"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<tr id="id_residenza_sede_legale_table" style="<%=displayResidenza_sedeLegale%>;">
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td colspan="5">
						<c:choose>
						   <c:when test="${anagrafe.entity.indirizzo eq anagrafe.oldAnagrafe.indirizzo || (empty anagrafe.entity.indirizzo and empty  anagrafe.oldAnagrafe.indirizzo)}">
								<spring-form:input id="indirizzo_id" path="entity.indirizzo" size="60" />
							</c:when>
							<c:otherwise>
								<spring-form:input cssStyle="background:red;" id="indirizzo_id" path="entity.indirizzo" size="60" onclick="gestDialogAnagrafe('indirizzo_modificatoOverlay_id')"/>
								<input id="id_nuovo_indirizzo" type="hidden" value="${anagrafe.oldAnagrafe.indirizzo}" name="oldAnagrafe.indirizzo"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.indirizzo}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.indirizzo}" />
								   <jsp:param value="indirizzo_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="indirizzo_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="indirizzo_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_indirizzo" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>
						<spring-form:errors path="entity.indirizzo" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_residenza_sede_legale_table" style="<%=displayResidenza_sedeLegale%>;">
					<td>
						<fmt:message key="label.localita" />
					</td>
					<td >
					  <c:choose>
					  	<c:when test="${anagrafe.entity.citta eq anagrafe.oldAnagrafe.citta || (empty anagrafe.entity.citta and empty  anagrafe.oldAnagrafe.citta)}">
					  		<spring-form:input id="citta_id" path="entity.citta" size="30" />
					    </c:when>
					  	<c:otherwise>
						  	<spring-form:input cssStyle="background:red;" id="citta_id" path="entity.citta" size="30" onclick="gestDialogAnagrafe('citta_modificatoOverlay_id')"/>
						    <input id="id_nuovo_citta" type="hidden" value="${anagrafe.oldAnagrafe.citta}" name="oldAnagrafe.citta"></input>
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.citta}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.citta}" />
								   <jsp:param value="citta_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="citta_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="citta_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_citta" name="oldfieldId"/>
							</jsp:include>	
					  	</c:otherwise>
					  </c:choose>
						<spring-form:errors path="entity.citta" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.cap" />
					</td>
					<td colspan="3">
						<c:choose>
						   <c:when test="${anagrafe.entity.cap eq anagrafe.oldAnagrafe.cap || (empty anagrafe.entity.cap and empty  anagrafe.oldAnagrafe.cap)}">
								<spring-form:input id="cap_id" path="entity.cap" size="6" />
						   </c:when>
						   <c:otherwise>							   
							    	<spring-form:input id="cap_id" cssStyle="background:red;" path="entity.cap" size="6" onclick="gestDialogAnagrafe('cap_modificatoOverlay_id')" />
							    	<input id="id_nuovo_cap" type="hidden" value="${anagrafe.oldAnagrafe.cap}" name="oldAnagrafe.cap"></input>
							        <jsp:include page="../anagrafe/formModificaCampi.jsp" >
									   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.cap}" />
									   <jsp:param name="nuovocampo" value="${anagrafe.entity.cap}" />
									   <jsp:param value="cap_modificatoOverlay_id" name="id_div_overlay"/>
									   <jsp:param value="cap_modificatoInner_id" name="id_div_inner"/>
									   <jsp:param value="cap_id" name="fieldId"/>
									   <jsp:param value="id_nuovo_cap" name="oldfieldId"/>
									</jsp:include>								   
						   </c:otherwise>
					   </c:choose>
							<spring-form:errors path="entity.cap" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_residenza_sede_legale_table" style="<%=displayResidenza_sedeLegale%>;">
					<td>
						<fmt:message key="label.comune" />
					</td>
					<td>
						<script type="text/javascript">
					   function ricercaComuneresidenzaAfterUpdate(inputField,listItem){
						  
						   		var a = listItem.id;
								document.getElementById('comune_id').value = inputField.value;
								document.getElementById('comune_hidden').value = a;								
								altreInformazioniComuneresidenza(a);
						}
												
						function altreInformazioniComuneresidenza(codiceComune){
								var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getProvincia.htm?codiceComune=' + codiceComune, {
									  method: 'post',	
									  onSuccess: function(transport){ 
										var json = transport.responseText.evalJSON();											
										var comune = json.comune;
										var provElem = $('provincia_id');
										if(comune.siglaprovincia!=null){
											provElem.value		= comune.siglaprovincia;
										}
										var capElem = $('cap_id');
										if(comune.cap!=null){
											capElem.value		= comune.cap;
										}
							  			},
									  onFailure: function(transport){ 
							  			var responseTexts = transport.responseText;
							  			alert(responseTexts);
								  	 }						    		 
								});								
							}

						</script>
					   <c:if test="${anagrafe.entity.comuneResidenza.codicecomune eq anagrafe.oldAnagrafe.comuneResidenza.codicecomune || anagrafe.entity.comuneResidenza.codicecomune==null }">
							<spring-form:input id="comune_id" path="entity.comuneResidenza.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="40"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_hidden" idInput="comune_id" afterUpdateElement="ricercaComuneresidenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneResidenza" cssClass="error"/> 
							<spring-form:hidden id="comune_hidden" path="entity.comuneResidenza.codicecomune"  />
						</c:if>
						<c:if test="${(anagrafe.entity.comuneResidenza.codicecomune ne anagrafe.oldAnagrafe.comuneResidenza.codicecomune) && anagrafe.entity.comuneResidenza.codicecomune!=null}">
							<spring-form:input id="comune_id" path="entity.comuneResidenza.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comune_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('comune_modificatoOverlay_id')"  size="40"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_hidden" idInput="comune_id" afterUpdateElement="ricercaComuneresidenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneResidenza" cssClass="error"/> 
							<spring-form:hidden id="comune_hidden" path="entity.comuneResidenza.codicecomune"  />
							<input type="hidden" id="id_nuovo_comune" name="oldAnagrafe.comuneResidenza.comune"  value="${anagrafe.oldAnagrafe.comuneResidenza.comune}"  />
						
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comuneResidenza.comune}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comuneResidenza.comune}" />
							   <jsp:param value="comune_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comune_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comune" name="oldfieldId"/>
							</jsp:include>	
						
						</c:if>
					</td>
					<td>
						<fmt:message key="label.sigla_provincia" />
					</td>
					<td colspan="3">
					    <c:choose>
						   <c:when test="${anagrafe.entity.provincia eq anagrafe.oldAnagrafe.provincia || (empty anagrafe.entity.provincia and empty  anagrafe.oldAnagrafe.provincia)}">
								<spring-form:input id="provincia_id" path="entity.provincia" size="2" />
							</c:when>
						    <c:otherwise>
						    	<spring-form:input id="provincia_id" cssStyle="background:red;" path="entity.provincia" size="2" onclick="gestDialogAnagrafe('provincia_modificatoOverlay_id')" />
						    	<input id="id_nuovo_provincia" type="hidden" value="${anagrafe.oldAnagrafe.provincia}" name="oldAnagrafe.provincia"></input>
						        <jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provincia}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provincia}" />
								   <jsp:param value="provincia_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provincia_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provincia_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provincia" name="oldfieldId"/>
								</jsp:include>	
					 		</c:otherwise>
					 	</c:choose>	
						<spring-form:errors path="entity.provincia" cssClass="error"/>
					</td>
				</tr>
				<%-- GESTIONE DELL'INDIRIZZO DI CORRISPONDENZA
				START
				 --%>	
				<%
					String displayIndirizzoCorrispondenza= "display:none;";
					String styleIndirizzoCorrispondenza = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA)).equals("1")) {
					    displayIndirizzoCorrispondenza = "";
					    styleIndirizzoCorrispondenza="sezioneDatiMeno";
					} else {
					    displayIndirizzoCorrispondenza = "display:none;";
					    styleIndirizzoCorrispondenza="sezioneDatiPiu";
					}
				%>
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleIndirizzoCorrispondenza%>" id="id_link_indirizzo_corrispondenza" href="javascript:showHidePanel('id_indirizzo_corrispondenza_table', 'id_link_indirizzo_corrispondenza', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.corrispondenza"/>">
							<label for="id_link_indirizzo_corrispondenza"><fmt:message key="label.corrispondenza"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_indirizzo_corrispondenza_table" style="<%=displayIndirizzoCorrispondenza%>;">
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td colspan="5">
					    <c:choose>
						    <c:when test="${anagrafe.entity.indirizzocorrispondenza eq anagrafe.oldAnagrafe.indirizzocorrispondenza || (empty anagrafe.entity.indirizzocorrispondenza and empty  anagrafe.oldAnagrafe.indirizzocorrispondenza)}">
								<spring-form:input id="indirizzo_corrispondenza_id" path="entity.indirizzocorrispondenza" size="60" />
						    </c:when>
							<c:otherwise>
								<spring-form:input id="indirizzo_corrispondenza_id"  cssStyle="background:red;" path="entity.indirizzocorrispondenza" size="60" onclick="gestDialogAnagrafe('indirizzo_corrispondenza_modificatoOverlay_id')"/>
							    <input id="id_nuovo_indirizzo_corrispondenza" type="hidden" value="${anagrafe.oldAnagrafe.indirizzocorrispondenza}" name="oldAnagrafe.indirizzocorrispondenza"></input>
							    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.indirizzocorrispondenza}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.indirizzocorrispondenza}" />
								   <jsp:param value="indirizzo_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="indirizzo_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="indirizzo_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_indirizzo_corrispondenza" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
							<spring-form:errors path="entity.indirizzocorrispondenza" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_indirizzo_corrispondenza_table" style="<%=displayIndirizzoCorrispondenza%>;">
					<td>
						<fmt:message key="label.localita" />
					</td>
					<td>
					    <c:choose>
						    <c:when test="${anagrafe.entity.cittacorrispondenza eq anagrafe.oldAnagrafe.cittacorrispondenza || (empty anagrafe.entity.cittacorrispondenza and empty  anagrafe.oldAnagrafe.cittacorrispondenza)}">
								<spring-form:input id="citta_corrispondenza_id" path="entity.cittacorrispondenza" size="20" />
						    </c:when>
							<c:otherwise>
								<spring-form:input cssStyle="background:red;" id="citta_corrispondenza_id" path="entity.cittacorrispondenza" size="20" onclick="gestDialogAnagrafe('citta_corrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_citta_corrispondenza" type="hidden" value="${anagrafe.oldAnagrafe.cittacorrispondenza}" name="oldAnagrafe.cittacorrispondenza"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.cittacorrispondenza}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.cittacorrispondenza}" />
								   <jsp:param value="citta_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="citta_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="citta_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_citta_corrispondenza" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>
						<spring-form:errors path="entity.cittacorrispondenza" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.cap" />
					</td>
					<td colspan="3">
						<c:choose>
						    <c:when test="${anagrafe.entity.capcorrispondenza eq anagrafe.oldAnagrafe.capcorrispondenza || (empty anagrafe.entity.capcorrispondenza and empty  anagrafe.oldAnagrafe.capcorrispondenza)}">
								<spring-form:input id="cap_corrispondenza_id" path="entity.capcorrispondenza" size="8" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input  cssStyle="background:red;" id="cap_corrispondenza_id" path="entity.capcorrispondenza" size="8" onclick="gestDialogAnagrafe('cap_corrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_cap_corrispondenza" type="hidden" value="${anagrafe.oldAnagrafe.capcorrispondenza}" name="oldAnagrafe.capcorrispondenza"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.capcorrispondenza}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.capcorrispondenza}" />
								   <jsp:param value="cap_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="cap_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="cap_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_cap_corrispondenza" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.capcorrispondenza" cssClass="error"/>
						
					</td>
				</tr>
				<tr id="id_indirizzo_corrispondenza_table" style="<%=displayIndirizzoCorrispondenza%>;">
					<td>
						<fmt:message key="label.comune" />
					</td>
					<td>
					<script type="text/javascript">
					 function ricercaComunecorrispondenzaAfterUpdate(inputField,listItem){
						   		var a = listItem.id;
								document.getElementById('comunecorrispondenza_id').value = inputField.value;
								document.getElementById('comunecorrispondenza_hidden').value = a;
								altreInformazioniComunecorrispondenza(a);
						}
							
					function altreInformazioniComunecorrispondenza(codiceComune){
								var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getProvincia.htm?codiceComune=' + codiceComune, {
									  method: 'post',	
									  onSuccess: function(transport){ 
										var json = transport.responseText.evalJSON();											
										var comune = json.comune;
										var provElem = $('provincia_corrispondenza_id');
										if(comune.siglaprovincia!=null){
											provElem.value		= comune.siglaprovincia;
										}
										var capElem = $('cap_corrispondenza_id');
										if(comune.cap!=null){
											capElem.value		= comune.cap;
										}
							  			},
									  onFailure: function(transport){ 
							  			var responseTexts = transport.responseText;
							  			alert(responseTexts);
								  	 }						    		 
								});								
							}
					</script>
					<c:if test="${anagrafe.entity.comunecorrispondenza.codicecomune eq anagrafe.oldAnagrafe.comunecorrispondenza.codicecomune || anagrafe.entity.comunecorrispondenza.codicecomune==null }">
						<spring-form:input id="comunecorrispondenza_id" path="entity.comunecorrispondenza.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comunecorrispondenza_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="40"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comunecorrispondenza_hidden" idInput="comunecorrispondenza_id" afterUpdateElement="ricercaComunecorrispondenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
						<spring-form:errors path="entity.comunecorrispondenza" cssClass="error"/> 
						<spring-form:hidden id="comunecorrispondenza_hidden" path="entity.comunecorrispondenza.codicecomune"  />
					</c:if>
					<c:if test="${(anagrafe.entity.comunecorrispondenza.codicecomune ne anagrafe.oldAnagrafe.comunecorrispondenza.codicecomune) && anagrafe.entity.comunecorrispondenza.codicecomune!=null}">
							<spring-form:input id="comunecorrispondenza_id" path="entity.comunecorrispondenza.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comunecorrispondenza_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('comunecorrispondenza_modificatoOverlay_id')"  size="40"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comunecorrispondenza_hidden" idInput="comunecorrispondenza_id" afterUpdateElement="ricercaComunecorrispondenzaAfterUpdate" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comunecorrispondenza" cssClass="error"/> 
							<spring-form:hidden id="comunecorrispondenza_hidden" path="entity.comunecorrispondenza.codicecomune"  />
							<input type="hidden" id="id_nuovo_comunecorrispondenza" name="oldAnagrafe.comunecorrispondenza.comune"  value="${anagrafe.oldAnagrafe.comunecorrispondenza.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comunecorrispondenza.comune}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comunecorrispondenza.comune}" />
							   <jsp:param value="comunecorrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comunecorrispondenza_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comunecorrispondenza_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comunecorrispondenza" name="oldfieldId"/>
							</jsp:include>
					</c:if>				
					</td>
					<td>
						<fmt:message key="label.sigla_provincia" />
					</td>
					<td colspan="3">
						<c:choose>
							<c:when test="${anagrafe.entity.provinciacorrispondenza eq anagrafe.oldAnagrafe.provinciacorrispondenza || (empty anagrafe.entity.provinciacorrispondenza and empty  anagrafe.oldAnagrafe.provinciacorrispondenza)}">
								<spring-form:input id="provincia_corrispondenza_id" path="entity.provinciacorrispondenza" size="2" />
							</c:when>
							<c:otherwise>
								<spring-form:input cssStyle="background:red;" id="provincia_corrispondenza_id" path="entity.provinciacorrispondenza" size="60" onclick="gestDialogAnagrafe('provinciacorrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_provincia" type="hidden" value="${anagrafe.oldAnagrafe.provinciacorrispondenza}" name="oldAnagrafe.provinciacorrispondenza"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provinciacorrispondenza}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provinciacorrispondenza}" />
								   <jsp:param value="provinciacorrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provinciacorrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provinciacorrispondenza_corrispondenza_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provincia" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.provinciacorrispondenza" cssClass="error"/>	
					</td>
				</tr>
				<%-- GESTIONE DELL'INDIRIZZO DI DATINASCITA/SEDE LEGALE
				START
				 --%>	
				 <%
					String displayDatinascita_azienda= "display:none;";
					String styleDatinascita_azienda = "";
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA)).equals("1")) {
					    displayDatinascita_azienda = "";
					    styleDatinascita_azienda="sezioneDatiMeno";
					} else {
					    displayDatinascita_azienda = "display:none;";
					    styleDatinascita_azienda="sezioneDatiPiu";
					}
				%>				
				<%--  DESCRIZIONE TITOLO --%>
			    <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
			    <tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleDatinascita_azienda%>" id="id_link_dati_nascita_azienda" href="javascript:showHidePanel('id_dati_nascita_aziend_table', 'id_link_dati_nascita_azienda', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dati_nascita_e_codice_fiscale"/>">
							<label for="id_link_dati_nascita_azienda"> <fmt:message key="label.dati_nascita_e_codice_fiscale"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleDatinascita_azienda%>" id="id_link_dati_nascita_azienda" href="javascript:showHidePanel('id_dati_nascita_aziend_table', 'id_link_dati_nascita_azienda', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dati_azienda"/>">
							<label for="id_link_dati_nascita_azienda"><fmt:message key="label.dati_azienda"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<%-- CAMPI DATI AZIENDA (PERSONA GIURIDICA) --%>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
			    	<td>
						<fmt:message key="label.data_costituzione" />
					</td>
					<td colspan="6">
					<fmt:formatDate value="${anagrafe.entity.datanominativo}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nominativo" />
					<fmt:formatDate value="${anagrafe.oldAnagrafe.datanominativo}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nominativo_old" />
					<c:if test="${(data_nominativo == data_nominativo_old) || data_nominativo==null}">
						<spring-form:input id="data_costituzione_id" path="entity.datanominativo" size="8"  onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatacostituzione" idInput="data_costituzione_id" textKey="label.calendar"/>
					</c:if>
					<c:if test="${(data_nominativo != data_nominativo_old) && data_nominativo!=null}">
						<spring-form:input id="data_costituzione_id" path="entity.datanominativo" cssStyle="background:red;" size="8" onclick="gestDialogAnagrafe('data_costituzione_modificato_dialog')" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldatacostituzione" idInput="data_costituzione_id" textKey="label.calendar"/>
					    <spring-form:hidden path="oldAnagrafe.datanominativo" id="id_nuovo_datacostituzione" />
							<div id="data_costituzione_modificato_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="data_costituzione_modificato_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
								    	<td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
									    <td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristinaCheckbox('data_costituzione_id','id_nuovo_datacostituzione','data_costituzione_modificato_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_costituzione_id','data_costituzione_modificato_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
					</c:if>
						<spring-form:errors path="entity.datanominativo" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="label.comune_nascita" />
					</td>
					<td > 
					<c:if test="${(anagrafe.entity.comuneNascita.codicecomune eq anagrafe.oldAnagrafe.comuneNascita.codicecomune) || anagrafe.entity.comuneNascita.codicecomune==null }">
						<spring-form:input id="comune_nascita_id" path="entity.comuneNascita.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_nascita_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="28"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_nascita_hidden" idInput="comune_nascita_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
						<spring-form:errors path="entity.comuneNascita" cssClass="error"/> 
						<spring-form:hidden id="comune_nascita_hidden" path="entity.comuneNascita.codicecomune"  />
					</c:if>
					<c:if test="${(anagrafe.entity.comuneNascita.codicecomune ne anagrafe.oldAnagrafe.comuneNascita.codicecomune) && anagrafe.entity.comuneNascita.codicecomune!=null }">
							<spring-form:input id="comune_nascita_id" path="entity.comuneNascita.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comune_nascita_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('comunenascita_modificatoOverlay_id')"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_nascita_hidden" idInput="comune_nascita_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneNascita" cssClass="error"/> 
							<spring-form:hidden id="comune_nascita_hidden" path="entity.comuneNascita.codicecomune"  />
							<input type="hidden" id="id_nuovo_comunenascita" name="oldAnagrafe.comuneNascita.comune"  value="${anagrafe.oldAnagrafe.comuneNascita.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comuneNascita.comune}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comuneNascita.comune}" />
							   <jsp:param value="comunenascita_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comunenascita_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_nascita_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comunenascita" name="oldfieldId"/>
							</jsp:include>
					</c:if>				
					</td>
					<td>
						<fmt:message key="label.data_nascita" />
					</td>
					<td colspan="3">
					<fmt:formatDate value="${anagrafe.entity.datanascita}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nascita" />
					<fmt:formatDate value="${anagrafe.oldAnagrafe.datanascita}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nascita_old" />
					<c:if test="${(data_nascita eq data_nascita_old)  || data_nascita==null}">
						<spring-form:input id="data_nascita_id" path="entity.datanascita" size="8" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatanascita" idInput="data_nascita_id" textKey="label.calendar"/>
					</c:if>
					<c:if test="${(data_nascita ne data_nascita_old) && data_nascita ne null}">
						<spring-form:input  id="data_nascita_id" path="entity.datanascita" cssStyle="background:red;" onclick="javascript:gestDialogAnagrafe('data_nascita_modificato_dialog');" onblur="isValidDate(this,true);" size="8" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldatanascita" idInput="data_nascita_id" textKey="label.calendar"/>
						<spring-form:hidden path="oldAnagrafe.datanascita" id="id_nuovo_datanascita" />
							<div id="data_nascita_modificato_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="data_nascita_modificato_dialogInner" class="dialog">
									<div style="width: 100%" align="right">
										<a title="<fmt:message key="button.back" />" href="javascript:gestDialogAnagrafe('data_nascita_modificato_dialog');" ><img src="${pageContext.request.contextPath }/images/cross.gif" border="0"/></a>
									</div>
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
								    	<td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
									    <td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('data_nascita_id','id_nuovo_datanascita','data_nascita_modificato_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_nascita_id','data_nascita_modificato_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
					</c:if>
						<spring-form:errors path="entity.datanascita" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<c:choose>
							<c:when test="${anagrafe.entity.tipoanagrafe=='F'}">
								<fmt:message key="label.codice_fiscale" />
							</c:when>
							<c:otherwise>
								<fmt:message key="label.codice_fiscale_impresa" />
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${anagrafe.entity.codicefiscale eq anagrafe.oldAnagrafe.codicefiscale || (empty anagrafe.entity.codicefiscale and empty  anagrafe.oldAnagrafe.codicefiscale)}">
								<spring-form:input id="codice_fiscale_id" path="entity.codicefiscale" size="24" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="codice_fiscale_id" path="entity.codicefiscale" size="24" onclick="gestDialogAnagrafe('codicefiscale_modificatoOverlay_id')"/>
								<input id="id_nuovo_codicefiscale" type="hidden" value="${anagrafe.oldAnagrafe.codicefiscale}" name="oldAnagrafe.codicefiscale"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.codicefiscale}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.codicefiscale}" />
								   <jsp:param value="codicefiscale_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="codicefiscale_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="codice_fiscale_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_codicefiscale" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.codicefiscale" cssClass="error"/> 
					</td>					
					<td id="functions" colspan="4">
						<ul>
							<c:if test="${anagrafe.entity.tipoanagrafe=='F'}">
							<li><a href="javascript:calcolaCodicefiscale();"><fmt:message key="button.genera_codice_fiscale" /></a></li>
							</c:if>
							<!-- §§§BEGIN§§§ -->
							<c:if test="${inite:isEnterprise()}">		
								<c:if test="${VERTICALIZZAZIONE_WSANAGRAFE_REQUEST eq true}">
									<c:if test="${anagrafe.entity.id.codice!=null}">
										<c:if test="${ anagrafe.entity.tipoanagrafe eq 'G' or (anagrafe.entity.tipoanagrafe eq personaFisicaval && ESCLUDI_RICERCA_PER_PF_REQUEST eq 1 )}">
												<li><a href="javascript:controlloAggiornamentiAnagrafeByCF();"><fmt:message key="button.controlla_aggiornamenti" /></a></li>
												<script type="text/javascript">
												function controlloAggiornamentiAnagrafeByCF()
												{
													if(document.getElementById('codice_fiscale_id').value!='')
													{
														javascript:doSubmit('${anagrafe.prefixPopup}controlloAggiornamentiAnagrafeByCF.htm','',document.inviodati);
													}else
													{
														alert('Codice Fiscale non presente')
													}
												}
												</script>
										</c:if>		
								    </c:if>
							    </c:if>
						    </c:if>
						    <!-- §§§END§§§ -->
						</ul>
					</td>
				</tr>
				<c:if test="${anagrafe.entity.tipoanagrafe=='F'}">
					<tr  id="id_dati_nascita_aziend_table"" style="<%=displayDatinascita_azienda%>;">
						<td class="titoloSottoSezione" colspan="4">
							<fmt:message key="label.impresa_individuale" />
							(<label style="font-size:13px;"><fmt:message key="label.descrizione_impresa_individuale" /></label>
							 <init:help idHelp="impresa_individuale_help_id" textKey="anagrafe.help.impresa_individuale" />)
						</td>
					</tr>
				</c:if>
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">		
					<td>
						<fmt:message key="label.partita_iva" />
					</td>
					<td>
						<c:choose>
							<c:when test="${anagrafe.entity.partitaiva eq anagrafe.oldAnagrafe.partitaiva || (empty anagrafe.entity.partitaiva and empty  anagrafe.oldAnagrafe.partitaiva)}">
								<spring-form:input id="partita_iva_id" path="entity.partitaiva" size="24" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="partita_iva_id" path="entity.partitaiva" size="24" onclick="gestDialogAnagrafe('partitaiva_modificatoOverlay_id')"/>
								<input id="id_nuovo_partitaiva" type="hidden" value="${anagrafe.oldAnagrafe.partitaiva}" name="oldAnagrafe.partitaiva"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.partitaiva}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.partitaiva}" />
								   <jsp:param value="partitaiva_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="partitaiva_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="partita_iva_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_partitaiva" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.partitaiva" cssClass="error"/> 
					</td>
					<c:if test="${anagrafe.entity.tipoanagrafe=='G'}">
					<td id="functions" colspan="2">
						<c:if test="${anagrafe.entity.id.codice!=null}">
							<c:if test="${VERTICALIZZAZIONE_WSANAGRAFE_REQUEST eq true}">
								<ul>
									<li><a href="javascript:controlloAggiornamentiAnagrafeByPI()"><fmt:message key="button.controlla_aggiornamenti" /></a></li>
								</ul>
								<script type="text/javascript">
									function controlloAggiornamentiAnagrafeByPI()
									{
										if(document.getElementById('partita_iva_id').value!='')
										{
											javascript:doSubmit('${anagrafe.prefixPopup}controlloAggiornamentiAnagrafeByPI.htm','',document.inviodati);
										}else
										{
											alert('Partita IVA non presente')
										}
									}
								</script>
							</c:if>		
						</c:if>	
					</td>
					</c:if>
				</tr>
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="anagrafe.label.reg_ditte" />
					</td>
					<td> 
						<c:choose>
							<c:when test="${anagrafe.entity.regditte eq anagrafe.oldAnagrafe.regditte || (empty anagrafe.entity.regditte and empty  anagrafe.oldAnagrafe.regditte)}">
								<spring-form:input id="reg_ditte_id" path="entity.regditte"  size="24"/>
					        </c:when>
					        <c:otherwise>
								<spring-form:input id="reg_ditte_id" cssStyle="background:red;" path="entity.regditte" onclick="gestDialogAnagrafe('regditte_modificatoOverlay_id')"  size="24"/>
								<input id="id_nuovo_regditte" type="hidden" value="${anagrafe.oldAnagrafe.regditte}" name="oldAnagrafe.regditte"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.regditte}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.regditte}" />
								   <jsp:param value="regditte_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="regditte_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="reg_ditte_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_regditte" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.regditte" cssClass="error"/> 					</td>
					<td>
						<fmt:message key="label.data" />
					</td>
					<td colspan="3">
					    <fmt:formatDate value="${anagrafe.entity.dataregditte}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regditte" />
					    <fmt:formatDate value="${anagrafe.oldAnagrafe.dataregditte}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regditte_old" />
						<c:if test="${(data_regditte == data_regditte_old) || data_regditte==null}">
							<spring-form:input id="data_reg_ditte_id" path="entity.dataregditte" size="8" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="caldatarefgditte" idInput="data_reg_ditte_id" textKey="label.calendar"/>
						</c:if>
						
						<c:if test="${(data_regditte != data_regditte_old) && data_regditte!=null}">
						<spring-form:input id="data_reg_ditte_id" path="entity.dataregditte" cssStyle="background:red;" size="8" onclick="gestDialogAnagrafe('dataregditte_modificatoOverlay_id')" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldatarefgditte" idInput="data_reg_ditte_id" textKey="label.calendar"/>
					    <spring-form:hidden path="oldAnagrafe.dataregditte" id="id_nuovo_dataregditte" />
						   <div id="dataregditte_modificatoOverlay_id" style="display: none;" dojoType="dijit.Dialog">
								<div id="dataregditte_modificatoInner_id" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
								    	<td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
									    <td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('data_reg_ditte_id','id_nuovo_dataregditte','dataregditte_modificatoOverlay_id')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_reg_ditte_id','dataregditte_modificatoOverlay_id')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>
						<spring-form:errors path="entity.dataregditte" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="label.comune_reg_ditte" />
					</td>
					<td colspan="5">
					    <c:if test="${(anagrafe.entity.comunecomregditte.codicecomune eq anagrafe.oldAnagrafe.comunecomregditte.codicecomune) || anagrafe.entity.comunecomregditte.codicecomune==null }">
							<spring-form:input id="comune_reg_ditte_id" path="entity.comunecomregditte.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_reg_ditte_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_ditte_hidden" idInput="comune_reg_ditte_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comunecomregditte" cssClass="error"/> 
							<spring-form:hidden id="comune_reg_ditte_hidden" path="entity.comunecomregditte.codicecomune"  />
						</c:if>
						<c:if test="${(anagrafe.entity.comunecomregditte.codicecomune ne anagrafe.oldAnagrafe.comunecomregditte.codicecomune) && anagrafe.entity.comunecomregditte.codicecomune!=null }">
							<spring-form:input id="comune_reg_ditte_id" path="entity.comunecomregditte.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comunecorrispondenza_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('comunecomregditte_modificatoOverlay_id')"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_ditte_hidden" idInput="comune_reg_ditte_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comunecomregditte" cssClass="error"/> 
							<spring-form:hidden id="comune_reg_ditte_hidden" path="entity.comunecomregditte.codicecomune"  />
							<input type="hidden" id="id_nuovo_comunecomregditte" name="oldAnagrafe.comunecomregditte.comune"  value="${anagrafe.oldAnagrafe.comunecomregditte.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comunecomregditte.comune}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comunecomregditte.comune}" />
							   <jsp:param value="comunecomregditte_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comunecomregditte_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_reg_ditte_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comunecomregditte" name="oldfieldId"/>
							</jsp:include>
						</c:if>			
					</td>
				</tr>
			    <%-- VISUALIZZATA SE SCELTO PERSONAGIURIDICA
				START --%>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="label.reg_trib" />
					</td>
					<td >
					   <c:choose>
							<c:when test="${anagrafe.entity.regtrib eq anagrafe.oldAnagrafe.regtrib || (empty anagrafe.entity.regtrib and empty  anagrafe.oldAnagrafe.regtrib)}">
								<spring-form:input id="reg_trib_id" path="entity.regtrib" size="18" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input id="reg_trib_id" cssStyle="background:red;" path="entity.regtrib" onclick="gestDialogAnagrafe('regtrib_modificatoOverlay_id')"  size="18"/>
								<input id="id_nuovo_reg_trib" type="hidden" value="${anagrafe.oldAnagrafe.regtrib}" name="oldAnagrafe.numeroelencopro"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.regtrib}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.regtrib}" />
								   <jsp:param value="regtrib_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="regtrib_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="reg_trib_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_reg_trib" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
					   </c:choose>    
					   <spring-form:errors path="entity.regtrib" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.data" />
					</td>
					<td colspan="3">
					    <fmt:formatDate value="${anagrafe.entity.dataregtrib}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regtrib" />
					    <fmt:formatDate value="${anagrafe.oldAnagrafe.dataregtrib}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regtrib_old" />
						<c:if test="${(data_regtrib == data_regtrib_old) || data_regtrib==null}">
							<spring-form:input id="data_reg_trib_id" path="entity.dataregtrib" size="8" />
							<init:calendar imagePath="/images/cal.gif" idImage="caldataregtrib" idInput="data_reg_trib_id" textKey="label.calendar"/>
						</c:if>
						<c:if test="${(data_regtrib != data_regtrib_old) && data_regtrib!=null}">
							<spring-form:input id="data_reg_trib_id" path="entity.dataregtrib" cssStyle="background:red;" size="8" onclick="gestDialogAnagrafe('dataregtrib_dialog')" />
							<init:calendar imagePath="/images/cal.gif" idImage="caldataregtrib" idInput="data_reg_trib_id" textKey="label.calendar"/>
					    	<spring-form:hidden path="oldAnagrafe.dataregtrib" id="id_nuovo_dataregtrib" />
							<div id="dataregtrib_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="dataregtrib_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
								    	<td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
									    <td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('data_reg_trib_id','id_nuovo_dataregtrib','dataregtrib_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_reg_trib_id','dataregtrib_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>
						<spring-form:errors path="entity.dataregtrib" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="label.comune_reg_trib" />
					</td>
					<td colspan="5">
						<c:if test="${(anagrafe.entity.comuneregtrib.codicecomune eq anagrafe.oldAnagrafe.comuneregtrib.codicecomune) || anagrafe.entity.comuneregtrib.codicecomune==null }">
						<spring-form:input id="comune_reg_trib_id" path="entity.comuneregtrib.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_reg_trib_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="28"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_trib_hidden" idInput="comune_reg_trib_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
						<spring-form:errors path="entity.comuneregtrib" cssClass="error"/> 
						<spring-form:hidden id="comune_reg_trib_hidden" path="entity.comuneregtrib.codicecomune"/>
						</c:if>
						<c:if test="${(anagrafe.entity.comuneregtrib.codicecomune ne anagrafe.oldAnagrafe.comuneregtrib.codicecomune) && anagrafe.entity.comuneregtrib.codicecomune!=null }">
							<spring-form:input id="comune_reg_trib_id" path="entity.comuneregtrib.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comune_reg_trib_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('comuneregtrib_modificatoOverlay_id')"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_trib_hidden" idInput="comune_reg_trib_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneregtrib" cssClass="error"/> 
							<spring-form:hidden id="comune_reg_trib_id" path="entity.comuneregtrib.comune"  />
							<input type="hidden" id="id_nuovo_comuneregtrib" name="oldAnagrafe.comuneregtrib.comune"  value="${anagrafe.oldAnagrafe.comuneregtrib.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comuneregtrib.comune}" />
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comuneregtrib.comune}" />
							   <jsp:param value="comuneregtrib_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comuneregtrib_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_reg_trib_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comuneregtrib" name="oldfieldId"/>
							</jsp:include>
						</c:if>			
					</td>
				</tr>	
				
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="label.provincia_area" />
					</td>
					<td colspan="5">					
						 <c:choose>
							<c:when test="${anagrafe.entity.provinciarea eq anagrafe.oldAnagrafe.provinciarea || (empty anagrafe.entity.provinciarea and empty  anagrafe.oldAnagrafe.provinciarea)}">
								<spring-form:input id="provincia_provinciarea_id" path="entity.provinciarea" size="2" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="provincia_provinciarea_id" path="entity.provinciarea" size="2" onclick="gestDialogAnagrafe('provinciarea_modificatoOverlay_id')"/>
								<input id="id_nuovo_provinciarea" type="hidden" value="${anagrafe.oldAnagrafe.provinciarea}" name="oldAnagrafe.provinciarea"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provinciarea}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provinciarea}" />
								   <jsp:param value="provinciarea_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provinciarea_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provincia_provinciarea_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provinciarea" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>
						<spring-form:errors path="entity.provinciarea" cssClass="error"/>					
					</td>
				</tr>	
				<tr id="id_dati_nascita_aziend_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<fmt:message key="label.numero_iscrizione_rea" />
					</td>
					<td colspan="1">
						 <c:choose>
							<c:when test="${anagrafe.entity.numiscrrea eq anagrafe.oldAnagrafe.numiscrrea || (empty anagrafe.entity.numiscrrea and empty  anagrafe.oldAnagrafe.numiscrrea)}">
								<spring-form:input id="numiscrrea_id" path="entity.numiscrrea" size="15"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input id="numiscrrea_id" cssStyle="background:red;" path="entity.numiscrrea" onclick="gestDialogAnagrafe('numrea_modificatoOverlay_id')"  size="15"/>
								<input id="id_nuovo_num_rea" type="hidden" value="${anagrafe.oldAnagrafe.numiscrrea}" name="oldAnagrafe.numiscrrea"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.numiscrrea}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.numiscrrea}" />
								   <jsp:param value="numrea_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="numrea_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="numiscrrea_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_num_rea" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.numiscrrea" cssClass="error"/> 
					</td>
					<td>
						<fmt:message key="label.data" />
					</td>
					<td colspan="3">
					    <fmt:formatDate value="${anagrafe.entity.dataiscrrea}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_iscrrea" />
					    <fmt:formatDate value="${anagrafe.oldAnagrafe.dataiscrrea}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_iscrrea_old" />
						<c:if test="${(data_iscrrea == data_iscrrea_old) || data_iscrrea==null}">
								<spring-form:input id="dataiscrrea_id" path="entity.dataiscrrea" size="8"/>
								<init:calendar imagePath="/images/cal.gif" idImage="caldataiscrrea" idInput="dataiscrrea_id" textKey="label.calendar"/>
						</c:if>
						<c:if test="${(data_iscrrea != data_iscrrea_old) && data_iscrrea!=null}">
							<spring-form:input id="dataiscrrea_id" path="entity.dataiscrrea" cssStyle="background:red;" size="8" onclick="gestDialogAnagrafe('dataiscrrea_dialog')" />
							<init:calendar imagePath="/images/cal.gif" idImage="caldataiscrrea" idInput="dataiscrrea_id" textKey="label.calendar"/>
					    	<spring-form:hidden path="oldAnagrafe.dataiscrrea" id="id_nuovo_dataiscrrea" />
							<div id="dataiscrrea_dialog" style="display: none;"  dojoType="dijit.Dialog">
								<div id="dataiscrrea_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
								    	<td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
									    <td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('dataiscrrea_id','id_nuovo_dataiscrrea','dataiscrrea_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('dataiscrrea_id','dataiscrrea_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
							</c:if>
						<spring-form:errors path="entity.dataiscrrea" cssClass="error"/> 
					</td>
				</tr>
				<tr>	
					<td><fmt:message key="label.numero_matricola_inail" /></td>
						<td colspan="1">
							<spring-form:input id="inailMatricola_id" path="entity.inailMatricola" size="15"/>
							<spring-form:errors path="entity.inailMatricola" cssClass="error"/> 
						</td>
						<td>
							<fmt:message key="label.sede_iscrizione_inail" />
						</td>
						<td colspan="3">
							<spring-form:input id="sedeInail_id" path="entity.sedeInail.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'sedeInail_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
							<init:autocompleter methodAjax="findSediINAIL.htm" idHidden="sedeInail_hidden" idInput="sedeInail_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
							<spring-form:errors path="entity.sedeInail" cssClass="error"/> 
							<spring-form:hidden id="sedeInail_hidden" path="entity.sedeInail.codice"  />
						</td>
					</tr>
					<tr>	
					    <td><fmt:message key="label.numero_matricola_inps" /></td>
						<td colspan="1">
							<spring-form:input id="inpsMatricola_id" path="entity.inpsMatricola" size="15"/>
			                <spring-form:errors path="entity.inpsMatricola" cssClass="error"/> 
						</td>
						<td>
							<fmt:message key="label.sede_iscrizione_inps" />
						</td>
						<td colspan="3">
							<spring-form:input id="sedeInps_id" path="entity.sedeInps.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'sedeInps_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
							<init:autocompleter methodAjax="findSediINPS.htm" idHidden="sedeInps_hidden" idInput="sedeInps_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
							<spring-form:errors path="entity.sedeInps" cssClass="error"/> 
							<spring-form:hidden id="sedeInps_hidden" path="entity.sedeInps.codice"  />
						</td>
					</tr>				
				    <tr>
				        <td><fmt:message key="label.numero_matricola_cassaedile" /></td>
						<td colspan="1">
							<spring-form:input id="inpsMatricola_id" path="entity.cassaedileMatricola" size="15"/>
							<spring-form:errors path="entity.cassaedileMatricola" cssClass="error"/> 
						</td>
						<td><fmt:message key="label.sede_iscrizione_cassaedile" /></td>
						<td colspan="3">
							<spring-form:input id="sedeCassaedile_id" path="entity.sedeCassaedile.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'sedeCassaedile_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
							<init:autocompleter methodAjax="findSediCassaedile.htm" idHidden="sedeCassaedile_hidden" idInput="sedeCassaedile_id" inputTitleKey="label.ricerca_sede_cassa_edile"></init:autocompleter>
							<spring-form:errors path="entity.sedeCassaedile" cssClass="error"/> 
							<spring-form:hidden id="sedeCassaedile_hidden" path="entity.sedeCassaedile.codice"  />
						</td>
				   </tr>
				</c:if>	
				<%-- END
				 VISUALIZZATA SE SCELTO PERSONAGIURIDICA
			  	 GESTIONE INFORMAZIONI ALBO 
				 START
				 --%>	
				<tr id="sezione_albo_id" class="titoloSezione" style="<%=displaySezioneAlbo%>;">
					<td colspan="5">
						<fmt:message key="label.albo" />
					</td>
				</tr>
				<tr id="sezione_albo_id" style="<%=displaySezioneAlbo%>;">
					<td>
						<fmt:message key="label.albo"/>
					</td>	
					<td>
					    <c:if test="${(anagrafe.entity.elenchiprofessionalibase.id eq anagrafe.entity.elenchiprofessionalibase.id) || anagrafe.entity.elenchiprofessionalibase.id==null }">
							<spring-form:input id="albo_id" path="entity.elenchiprofessionalibase.epDescrizione" cssClass="searchbox" onchange="checkValue(this,'albo_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="24"/>
							<init:autocompleter methodAjax="findElenchiprofessionalibase.htm" idHidden="albo_hidden" idInput="albo_id" inputTitleKey="label.ricerca_albo_professioni"></init:autocompleter>
							<spring-form:errors path="entity.elenchiprofessionalibase" cssClass="error"/> 
							<spring-form:hidden id="albo_hidden" path="entity.elenchiprofessionalibase.id"  />
						</c:if>
					    <c:if test="${(anagrafe.entity.elenchiprofessionalibase.id ne anagrafe.entity.elenchiprofessionalibase.id) && anagrafe.entity.elenchiprofessionalibase.id!=null }">
							<spring-form:input id="albo_id" path="entity.elenchiprofessionalibase.epDescrizione" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'albo_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gestDialogAnagrafe('albo_modificatoOverlay_id')"  size="24"/>
							<init:autocompleter methodAjax="findElenchiprofessionalibase.htm" idHidden="albo_hidden" idInput="albo_id"  inputTitleKey="label.ricerca_albo_professioni"></init:autocompleter>
							<spring-form:errors path="entity.elenchiprofessionalibase" cssClass="error"/> 
							<spring-form:hidden id="albo_hidden" path="entity.elenchiprofessionalibase.id"  />
							<input type="hidden" id="id_nuovo_albo" name="oldAnagrafe.elenchiprofessionalibase.epDescrizione"  value="${anagrafe.oldAnagrafe.elenchiprofessionalibase.epDescrizione}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp" >
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.elenchiprofessionalibase.epDescrizione}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.elenchiprofessionalibase.epDescrizione}"/>
							   <jsp:param value="albo_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="albo_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="albo_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_albo" name="oldfieldId"/>
							</jsp:include>
						</c:if>
					</td>
					<td>
						<fmt:message key="label.provincia"/>
					</td>
					<td>
						 <c:choose>
							<c:when test="${anagrafe.entity.provinciaelencopro eq anagrafe.oldAnagrafe.provinciaelencopro || (empty anagrafe.entity.provinciaelencopro and empty  anagrafe.oldAnagrafe.provinciaelencopro)}">
								<spring-form:input id="provincia_provinciaelencopro_id" path="entity.provinciaelencopro" size="2" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="provincia_provinciaelencopro_id" path="entity.provinciaelencopro" size="2" onclick="gestDialogAnagrafe('provinciaelencopro_modificatoOverlay_id')"/>
								<input id="id_nuovo_provinciaelencopro" type="hidden" value="${anagrafe.oldAnagrafe.provinciaelencopro}" name="oldAnagrafe.provinciaelencopro"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provinciaelencopro}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provinciaelencopro}" />
								   <jsp:param value="provinciaelencopro_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provinciaelencopro_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provincia_provinciaelencopro_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provinciaelencopro" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.provinciaelencopro" cssClass="error"/>
					</td>
				</tr>
				<tr id="sezione_albo_id" style="<%=displaySezioneAlbo%>;">
					<td>
						<fmt:message key="label.numero" />
					</td>
					<td colspan="3">
						<c:choose>
							<c:when test="${anagrafe.entity.numeroelencopro eq anagrafe.oldAnagrafe.numeroelencopro || (empty anagrafe.entity.numeroelencopro and empty  anagrafe.oldAnagrafe.numeroelencopro)}">
								<spring-form:input id="numero_id" path="entity.numeroelencopro" size="27" />
						    </c:when>
						    <c:otherwise>
							<spring-form:input id="numero_id" cssStyle="background:red;" path="entity.numeroelencopro" onclick="gestDialogAnagrafe('numeroelencopro_modificatoOverlay_id')"  size="27"/>
								<input id="id_nuovo_numeroelencopro" type="hidden" value="${anagrafe.oldAnagrafe.numeroelencopro}" name="oldAnagrafe.numeroelencopro"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.numeroelencopro}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.numeroelencopro}" />
								   <jsp:param value="numeroelencopro_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="numeroelencopro_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="numero_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_numeroelencopro" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.numeroelencopro" cssClass="error"/>
					</td>
				</tr>
			    <%-- SEZIONE INFORMAZIONE ALBO
			     END --%>
			  <%
					String displayAltriDati = "display:none;";
					String styleAltri = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_ALTRI_DATI)).equals("1")) {
					    displayAltriDati = "";
					    styleAltri="sezioneDatiMeno";
					} else {
					    displayAltriDati = "display:none;";
					    styleAltri="sezioneDatiPiu";
					}
				%>
			    <tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleAltri%>" id="id_link_altridati" href="javascript:showHidePanel('id_altridati_table', 'id_link_altridati', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_ALTRI_DATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.altri_dati"/>">
							<label for="id_link_altridati"><fmt:message key="label.altri_dati"/></label>
						</a>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.telefono" />
					</td>
					<td>
						<c:choose>
							<c:when test="${anagrafe.entity.telefono eq anagrafe.oldAnagrafe.telefono || (empty anagrafe.entity.telefono and empty  anagrafe.oldAnagrafe.telefono)}">
								<spring-form:input id="telefono_id" path="entity.telefono" size="25" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="telefono_id" path="entity.telefono" size="25" onclick="gestDialogAnagrafe('telefono_corrispondenza_modificatoOverlay_id')"/>
								<input id="id_nuovo_telefono" type="hidden" value="${anagrafe.oldAnagrafe.telefono}" name="oldAnagrafe.telefono"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.telefono}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.telefono}" />
								   <jsp:param value="telefono_corrispondenza_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="telefono_corrispondenza_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="telefono_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_telefono" name="oldfieldId"/>
								</jsp:include>	
						  	</c:otherwise>
						 </c:choose> 	 
						<spring-form:errors path="entity.telefono" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.cellulare" />
					</td>
					<td colspan="3">
					    <c:choose>
							<c:when test="${anagrafe.entity.telefonocellulare eq anagrafe.oldAnagrafe.telefonocellulare || (empty anagrafe.entity.telefonocellulare and empty  anagrafe.oldAnagrafe.telefonocellulare)}">
								<spring-form:input id="telefonocellulare_id" path="entity.telefonocellulare" size="25" />
						    </c:when>
							<c:otherwise>
								<spring-form:input cssStyle="background:red;" id="telefonocellulare_id" path="entity.telefonocellulare" size="25" onclick="gestDialogAnagrafe('telefonocellulare_modificatoOverlay_id')"/>
								<input id="id_nuovo_telefonocellulare" type="hidden" value="${anagrafe.oldAnagrafe.telefonocellulare}" name="oldAnagrafe.telefonocellulare"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.telefonocellulare}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.telefonocellulare}" />
								   <jsp:param value="telefonocellulare_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="telefonocellulare_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="telefonocellulare_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_telefonocellulare" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.telefonocellulare" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.fax" />
					</td>
					<td>
						<c:choose>
							<c:when test="${anagrafe.entity.fax eq anagrafe.oldAnagrafe.fax || (empty anagrafe.entity.fax and empty  anagrafe.oldAnagrafe.fax)}">
								<spring-form:input id="fax_id" path="entity.fax" size="25" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="fax_id" path="entity.fax" size="25" onclick="gestDialogAnagrafe('fax_modificatoOverlay_id')"/>
								<input id="id_nuovo_fax" type="hidden" value="${anagrafe.oldAnagrafe.fax}" name="oldAnagrafe.fax"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.fax}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.fax}" />
								   <jsp:param value="fax_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="fax_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="fax_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_fax" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
				        </c:choose>			
						<spring-form:errors path="entity.fax" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.email" />
					</td>
					<td colspan="3">
		              	<c:choose>
							<c:when test="${anagrafe.entity.email eq anagrafe.oldAnagrafe.email || (empty anagrafe.entity.email and empty  anagrafe.oldAnagrafe.email)}">		
								<spring-form:input id="email_id" path="entity.email" size="40" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="email_id" path="entity.email" size="40" onclick="gestDialogAnagrafe('email_modificatoOverlay_id')"/>
								<input id="id_nuovo_email" type="hidden" value="${anagrafe.oldAnagrafe.email}" name="oldAnagrafe.email"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.email}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.email}" />
								   <jsp:param value="email_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="email_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="email_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_email" name="oldfieldId"/>
								</jsp:include>	
						   </c:otherwise>
						</c:choose>   
						<spring-form:errors path="entity.email" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.pec" />
					</td>
					<td colspan="5">
						<c:choose>
							<c:when test="${anagrafe.entity.pec eq anagrafe.oldAnagrafe.pec || (empty anagrafe.entity.pec and empty  anagrafe.oldAnagrafe.pec)}">		
								<spring-form:input id="pec_id" path="entity.pec" size="35" maxlength="320" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="pec_id" path="entity.pec" size="40" onclick="gestDialogAnagrafe('pec_modificatoOverlay_id')"/>
								<input id="id_nuovo_pec" type="hidden" value="${anagrafe.oldAnagrafe.pec}" name="oldAnagrafe.pec"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.pec}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.pec}" />
								   <jsp:param value="pec_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="pec_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="pec_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_pec" name="oldfieldId"/>
								</jsp:include>	
						   </c:otherwise>
						 </c:choose>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.referente" />
					</td>
					<td colspan="5">
						<c:choose>
							<c:when test="${anagrafe.entity.referente eq anagrafe.oldAnagrafe.referente || (empty anagrafe.entity.referente and empty  anagrafe.oldAnagrafe.referente)}">		
								<spring-form:input id="referente_id" path="entity.referente" size="35" />
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssStyle="background:red;" id="referente_id" path="entity.referente" size="35" onclick="gestDialogAnagrafe('referente_modificatoOverlay_id')"/>
								<input id="id_nuovo_referente" type="hidden" value="${anagrafe.oldAnagrafe.referente}" name="oldAnagrafe.referente"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp" >
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.referente}" />
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.referente}" />
								   <jsp:param value="referente_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="referente_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="referente_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_referente" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.referente" cssClass="error"/>
					</td>
				</tr>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A 1 DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A 0 DOVRà MOSTRALO NON SELSZIONATO
				    String showCheckedInviomail="";
					if(((Boolean) request.getAttribute("inviomail")!=null))
					{
						if ((Boolean) request.getAttribute("inviomail")==true) {
					    	showCheckedInviomail = "checked='checked'";
	    				} else {
	    					showCheckedInviomail = "";
						}
					}
				%>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.flag_invio_mail" />
					</td>
					<td colspan="5">
						<c:if test="${anagrafe.entity.invioemail == anagrafe.oldAnagrafe.invioemail}">
						<spring-form:checkbox id="flag_invio_mail_id" path="entity.invioemail" value="true" />
						</c:if>
						<c:if test="${anagrafe.entity.invioemail != anagrafe.oldAnagrafe.invioemail}">
					    <input type="checkbox" id="flag_invio_mail_id" <%=showCheckedInviomail%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.invioemail"  value="true" onmouseover="javascript:gestDialogAnagrafe('inviomail_dialog');" />
					    <input type="hidden" id="id_nuovo_invio_mail"  name=""  value="${anagrafe.oldAnagrafe.invioemail}"/> 
						<div id="inviomail_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="inviomail_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
									    <c:if test="${anagrafe.entity.invioemail==true}">
								    		<td  class="parametri"><fmt:message key="label.si" /></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.invioemail==false}">
								    		<td  class="parametri"><fmt:message key="label.no" /></td>
								    	</c:if>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.invioemail==true}">
									       <td  class="parametri"><fmt:message key="label.si" /></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.invioemail==false}">
									       <td  class="parametri"><fmt:message key="label.no" /></td>
									    </c:if>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="ripristinaCheckbox('flag_invio_mail_id','id_nuovo_invio_mail','inviomail_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accettaCheckbox('flag_invio_mail_id','inviomail_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>	
						<spring-form:errors  path="entity.invioemail" cssClass="error"/>	
						<fmt:message key="anagrafe.label.descrizione_flag_invio_mail"/>
					</td>
					
				</tr>
				
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A true DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO INVIOMAIL SARà UGUALE A false DOVRà MOSTRALO NON SELSZIONATO
				    String showCheckedInviomailtecnico="";
					if(((Boolean) request.getAttribute("inviomailtec")!=null))
					{
						if ((Boolean) request.getAttribute("inviomailtec")==true) {
						    showCheckedInviomailtecnico = "checked='checked'";
	    				} else {
	    				showCheckedInviomailtecnico = "";
						}
					}
				%>
				
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.flag_invio_mail_tecnico" />
					</td>
					<td colspan="5">
						<c:if test="${anagrafe.entity.invioemailtec == anagrafe.oldAnagrafe.invioemailtec}">
						<spring-form:checkbox id="flag_invio_mailtec_id" path="entity.invioemailtec" value="true" />
						</c:if>
						<c:if test="${anagrafe.entity.invioemailtec != anagrafe.oldAnagrafe.invioemailtec}">
					    <input type="checkbox" id="flag_invio_mailtec_id" <%=showCheckedInviomailtecnico%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.invioemailtec"  value="true" onmouseover="javascript:gestDialogAnagrafe('inviomailtec_dialog');" />
					    <input type="hidden" id="id_nuovo_invio_mailtec"  name=""  value="${anagrafe.oldAnagrafe.invioemailtec}"/>
						<div id="inviomailtec_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="inviomailtec_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato" /></td>
									</tr>
									<tr>
									    <c:if test="${anagrafe.entity.invioemailtec==true}">
								    		<td  class="parametri"><fmt:message key="label.si" /></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.invioemailtec==false}">
								    		<td  class="parametri"><fmt:message key="label.no" /></td>
								    	</c:if>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato" /></td>
									</tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.invioemailtec==true}">
									       <td  class="parametri"><fmt:message key="label.si" /></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.invioemailtec==false}">
									       <td  class="parametri"><fmt:message key="label.no" /></td>
									    </c:if>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe" /></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="ripristinaCheckbox('flag_invio_mailtec_id','id_nuovo_invio_mailtec','inviomailtec_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accettaCheckbox('flag_invio_mailtec_id','inviomailtec_dialog')" ><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>	
						<spring-form:errors path="entity.invioemailtec" cssClass="error"/>
						<fmt:message key="anagrafe.label.descrizione_flag_invio_mail_tecnico"/>
						
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.note" />
					</td>
					<td colspan="5">
						<spring-form:textarea id="note_id" path="entity.note" cols="60" rows="5" />
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.flagDisabilitato" />
					</td>
					<td colspan="5">
						<spring-form:checkbox path="entity.flagDisabilitato" value="1" />
						<spring-form:errors path="entity.flagDisabilitato" cssClass="error"/>
						<fmt:message key="anagrafe.label.descrizione_flagDisabilitato"/>
					</td>
				</tr>				
				<%-- START SEZIONE PARAMETRI FRONTOFFICE --%>	
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">			
					<%
						String displayParametriFrontoffice = "display:none;";
						String styleParametriFrontoffice = "";
						//gestisce la visualizzazione della tabella Parametri Frontoffice
						if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE)).equals("1")) {
						    displayParametriFrontoffice = "";
						    styleParametriFrontoffice="sezioneDatiMeno";
						} else {
						    displayParametriFrontoffice = "display:none;";
						    styleParametriFrontoffice="sezioneDatiPiu";
						}
					%>			
				    <tr class="titoloSezione">
						<td colspan="6">
							<a class="<%=styleParametriFrontoffice%>" id="id_link_parametrifrontoffice" href="javascript:showHidePanel('id_parametrifrontoffice_table', 'id_link_parametrifrontoffice', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.parametri_frontoffice"/>">
								<label for="id_link_parametrifrontoffice"><fmt:message key="label.parametri_frontoffice"/></label>
							</a>
						</td>
					</tr>		
					<tr id="id_parametrifrontoffice_table" style="<%=displayParametriFrontoffice%>;">
						<td>
							<fmt:message key="label.utente_tester" />
						</td>
						<td colspan="5">
							<spring-form:checkbox id="foUtentetester_id" path="entity.foUtentetester"/>
							<spring-form:errors path="entity.foUtentetester" cssClass="error"/>
							<fmt:message key="anagrafe.label.descrizione_foUtentetester"/>
						</td>
					</tr>	
				</c:if>
				<%-- END SEZIONE PARAMETRI FRONTOFFICE--%>				
			</table>	
		</spring-form:form>
	</div>
	<%
		AnagrafeCommand command = (AnagrafeCommand)request.getAttribute("anagrafe");
		Boolean isPopoup=false;
		String uriBack = "/anagrafe/view.htm?codice=" + request.getParameter("codice");
		if(command.getPopup()!=null && command.getPopup().equals(new Boolean(true)))
		{
		 isPopoup=true;
		 String prefix=command.getPrefixPopup();
		 uriBack="/anagrafe/"+prefix+"view.htm?codice=" + request.getParameter("codice")+"&popupCaller=richiedenteIdCodice";
		 
		}
		
	    String urlschedeanagrafe = BackofficeNETConstants.getURL_SCHEDE_ANAGRAFE()+"?Software=TT&CodiceAnagrafe="+command.getEntity().getId().getCodice();
	    if(isPopoup){
	    	urlschedeanagrafe = BackofficeNETConstants.getUrlToPopupdecorator(request,urlschedeanagrafe,uriBack, null);
	    }else{
			urlschedeanagrafe = BackofficeNETConstants.getUrlTo(request,urlschedeanagrafe,uriBack, null , false);
	    }
	    pageContext.setAttribute("urlschedeanagrafe", urlschedeanagrafe);
	%>
	<div id="functions">
		<ul>
			<c:if test="${anagrafe.entity.id.codice==null}">
			   <li><a href="javascript:doSubmit('${anagrafe.prefixPopup}insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li> 
			</c:if>
			<c:if test="${anagrafe.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				 <% // §§§BEGIN§§§ %>
			    <c:if test="${inite:isEnterprise()}">
			    	<li><a href="${urlschedeanagrafe}"><fmt:message key="button.schede" /></a></li>
			    </c:if>	
				 <% // §§§END§§§ %>
				<c:if test="${anagrafe.popup eq false or empty anagrafe.popup}">
					<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>				
				   
				    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listscadenze.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.notifiche" /></a></li>
				   	<%-- STAMPA --%>
					<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_RICHIEDENTI_DOCUMENTI_TIPO());%>
					<c:set var="_URL_STAMPA" value="${URL_STAMPA}?CodiceAnagrafe=${anagrafe.entity.id.codice}&Doc_base=SCHEDAANAGRAFE.RTF" />
					<c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}" />
					<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a></li>
					<%-- END STAMPA --%>
				    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listemail.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.email" /></a></li>
					<%--			
				    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listdocumenti.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.documenti" /></a></li>
				     --%>
				    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listanagrafestorico.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.anagrafe_storico" /></a></li>
				    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/lististanzerichiedenti.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.procedimenti" /></a></li>
			    </c:if>
			</c:if>
			<c:if test="${anagrafe.popup eq false or empty anagrafe.popup}">
				<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${anagrafe.popup eq true}">
				<li><a href="javascript:self.close()"><fmt:message key="button.back" /></a></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${anagrafe.popup eq true}">
		<%if(StringUtils.defaultString(request.getParameter("done"),"false").equalsIgnoreCase("true")){ 		
		String descrizioneRichiedente = ((AnagrafeCommand)request.getAttribute("anagrafe")).getEntity().getDescrizioneRichiedente().replace("'","\\'"); 		
		%>			
		<script type="text/javascript">
		jQuery(document).ready(function(){
			opener.jQuery('#${anagrafe.popupCaller}').val('<%= descrizioneRichiedente%>');
			opener.jQuery('#${anagrafe.popupCaller}_hidden').val('${anagrafe.entity.id.codice}');
			opener.jQuery('#${anagrafe.popupCaller}').change();
		});
		</script>				
		<%} %>
	</c:if>	
</body>
</html>