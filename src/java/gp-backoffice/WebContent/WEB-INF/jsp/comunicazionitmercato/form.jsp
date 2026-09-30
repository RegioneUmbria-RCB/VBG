<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.comunicazionitmercato.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.comunicazionitmercato.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="comunicazionitmercato" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="comunicazionitmercato" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.data" />
					</td>
					<td>
						<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
						    <spring-form:input  tabindex="1" id="data_id" path="comunicazioniT.data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			   				<init:calendar idImage="caldata" idInput="data_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
			   				<spring-form:errors	path="entity.comunicazioniT.data" cssClass="error" />
						</c:if>
						<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
							 <spring-form:input id="data_id" path="comunicazioniT.data" size="8" readonly="true" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.oggetto_comunicazione" />
					</td>
					<td>
					    <c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
							<spring-form:input id="descrizione_id" path="comunicazioniT.descrizione" size="70" />
							<spring-form:errors path="comunicazioniT.descrizione" cssClass="error"/>
						</c:if>
						<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
							<spring-form:input id="descrizione_id" path="comunicazioniT.descrizione" size="70" readonly="true" />
						</c:if>
					</td>
				</tr>
				
				<!-- MOVIMENTO ED AMMINISTRAZIONE -->
				<tr>
					<td>
						<fmt:message key="label.movimento" />
					</td>
					<td colspan="3">
					<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
						<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento" value="comunicazioniT.tipimovimento" />
							<jsp:param name="includiDisabilitate" value="false" />
						</jsp:include>	
				    </c:if>	
					<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >		
					    <spring-form:input id="entity.tipimovimento_id" path="comunicazioniT.tipimovimento.movimento" size="70" readonly="true"/>
					</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.amministrazione" />
					</td>
					<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
					<td colspan="3">
					     
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />		
							<jsp:param name="propertyPath" value="comunicazioniT.amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="comunicazioniT.amministrazioni.amministrazione" />
							<jsp:param name="pathPropertyCode" value="comunicazioniT.amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />	
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
							<jsp:param name="id_help" value="help_amministrazione" /> 
						</jsp:include>
					</td>
					</c:if>
				    <c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
					<td>
						<spring-form:input id="entity.amministrazione_id" path="comunicazioniT.amministrazioni.amministrazione" size="70" readonly="true"/>
				    </td>
				    </c:if>
				</tr>
				
				<!-- PROTOCOLLAZIONE -->
				
				<tr>
					<td>
						<fmt:message key="label.flag_protocolla_movimento" />
					</td>
					<td colspan="3">
					   <c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
							<spring-form:checkbox id="flgProtocolla_id" path="comunicazioniT.flgProtocolla"  /> <%-- onchange="javascript:chekprotocolloattivo()" --%>
					   </c:if>
					   <c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
						    <b>
						    <c:if test="${true}" >
								<fmt:message key="label.si" />
								<%--  ${graduatorietcom.messageProtocolloNonAttivo} --%>
							</c:if>
							<c:if test="${false}" >
								<fmt:message key="label.no" />
							</c:if>
							</b>
						</c:if>
						<spring-form:errors path="comunicazioniT.flgProtocolla" cssClass="error"/>
						<span id="warn_mess"></span>
					</td>
					<script type='text/javascript'>
					function chekprotocolloattivo(){
					    
						if($("flgProtocolla_id").checked)
						{
						new Ajax.Request('${pageContext.request.contextPath}/ajax/checkProtocolloAttivo.htm', {
							method: 'post',	
							onSuccess: function(transport){						
							  $("warn_mess").innerHTML = transport.responseText;
		    				  $("warn_mess").style.display='';
		    				  applyStyle();		  
		     				},
		     				onFailure: function(transport){ 
		     				  //$("lista_alberoprocateco").innerHTML= transport.responseText;
		     				 // $("lista_alberoprocateco").style.display='';     				      				  
		     				 }						    		 
		     			});
						}else
						{
							 $("warn_mess").style.display='none';
						}
					}
					</script>	
				</tr>
				<tr>
					<td>
						<fmt:message key="label.passo_protocollazione" />
					</td>
					<td>
					    <c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
						<spring-form:select path="comunicazioniT.protDopoCreazioneAllegato">
						    <spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
							<spring-form:option value="0"><fmt:message key="label.prima_della_creazione_dell_allegato" /></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.dopo_la_creazione_dell_allegato" /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="comunicazioniT.protDopoCreazioneAllegato" cssClass="error"/>
						</c:if>
						<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
							<b>
							<c:if test="${comunicazionitmercato.entity.comunicazioniT.protDopoCreazioneAllegato==0 || comunicazionitmercato.entity.comunicazioniT.protDopoCreazioneAllegato==null}" >
								<fmt:message key="label.prima_della_creazione_dell_allegato" />
								<%-- ${graduatorietcom.messageProtocolloNonAttivo} --%>
							</c:if>
							<c:if test="${comunicazionitmercato.entity.comunicazioniT.protDopoCreazioneAllegato==1}" >
								<fmt:message key="label.dopo_la_creazione_dell_allegato" />
							</c:if>
							</b>
						</c:if>
						&nbsp;(<fmt:message key="label.passo_protocollazione.help" />)
					</td>
				</tr>
				
				<!--  ALLEGATO e FIRMATARI DELL'ALLEGATO -->
				
				<tr>
				<td>
					<fmt:message key="label.tipo_allegato_generare" />
				</td>
				<td colspan=3" class="inline-ui-cell">
				<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
					<script type="text/javascript">
						function updateLettereTipo(inputField,listItem){
							var idElemento='letteretipo';
							var a = listItem.id;
							if(a!=''){
								jQuery('#metti_alla_firma_id').show();
							}else{
								jQuery('#metti_alla_firma_id').hide();									
							}
							document.getElementById(idElemento+'_hidden').value = a;
							if(document.getElementById(idElemento+'_id1')){
								document.getElementById(idElemento+'_id1').value = inputField.value;
							}
							if(document.getElementById(idElemento+'_id2')){
								document.getElementById(idElemento+'_id2').value = inputField.value;
							}
							if($(idElemento+'_id1'+'_choices')){
								$(idElemento+'_id1'+'_choices').fade();
							}
							if($(idElemento+'_id2'+'_choices')){
								$(idElemento+'_id2'+'_choices').fade();
							}
						}
					
						function onChangeCallLT(obj, hiddenFieldId){
							checkValue(obj,hiddenFieldId);
							if ($(hiddenFieldId).value == ''){
								jQuery('#metti_alla_firma_id').hide();
							}									
						}

					</script>
					<jsp:include page="../includes/letteretipoSearch.jsp" >
						<jsp:param name="idElemento" value="letteretipo" />		
						<jsp:param name="propertyPath" value="comunicazioniT.letteretipo" />				
						<jsp:param name="pathPropertyDescription" value="comunicazioniT.letteretipo.descrizione" />
						<jsp:param name="pathPropertyCode" value="comunicazioniT.letteretipo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
						<jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
						<jsp:param name="id_help" value="help_doc_tipo" />
						<jsp:param name="onchangeCallback" value="onChangeCallLT(this,'letteretipo_hidden')"/>
						<jsp:param name="afterUpdateElement" value="updateLettereTipo" />
					</jsp:include>
					
					<spring-form:checkbox id="flgTrasformaPdf_id" path="comunicazioniT.flgTrasformaPdf" /><label for="flgTrasformaPdf_id">Trasforma il documento in pdf</label>
					
				</c:if>
				<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
					<spring-form:input id="entity.letteretipo.descrizione_id" path="comunicazioniT.letteretipo.descrizione" size="70" readonly="true"/>
					<spring-form:checkbox id="flgTrasformaPdf_id" path="comunicazioniT.flgTrasformaPdf" disabled="true"/> Trasforma il documento in pdf
				</c:if>
					
			</td>
			</tr>
				
			<c:set var="displayMettiAllaFirma" value="display: none;"/>				
				<c:if test="${comunicazionitmercato.entity.comunicazioniT.flagMettiallafirma eq true}" >
					<c:set var="displayMettiAllaFirma" value=""/>
				</c:if>
				<tr id="metti_alla_firma_id" style="${displayMettiAllaFirma}">
					<td>
					</td>
					<td colspan="3">
						<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
							<spring-form:checkbox id="flagmettiallafirma_id" path="comunicazioniT.flagMettiallafirma" onclick="mostranascondifirmatari(this)"/>
						</c:if>
						<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
							<spring-form:checkbox id="flagmettiallafirma_id" disabled="true" path="comunicazioniT.flagMettiallafirma" />
						</c:if>
						<label for="flagmettiallafirma_id"><fmt:message key="label.gli_allegati_generati_devono_essere_firmati" /></label>
						<br />
						
						
						
						<fieldset id="fieldset_firmatari" style="${displayMettiAllaFirma}">
						<script type="text/javascript">
						
						function mostranascondifirmatari(obj){
							if(jQuery(obj).is(':checked')){
								jQuery('#fieldset_firmatari').show();
							}else{
								jQuery('#fieldset_firmatari').hide();
							} 
						}
						function updateFirmatari(inputField,listItem){
							var idElemento='listaFirmatari_id';
							var a = listItem.id;
							var descrizione = inputField.value;
							document.getElementById(idElemento+'_hidden').value = '';
							document.getElementById(idElemento+'_id').value = '';
							$(idElemento+'_id'+'_choices').fade();
							
							aggiungiFirmatario(a,descrizione);
							
						}
											
						function aggiungiFirmatario(codice, descrizione){
							if (codice){
								if(codice!=''){									
									var idFirmatario = 'firmatario_id_'+codice;
									if(!(jQuery('#'+idFirmatario).length > 0)){
										jQuery("#listaFirmatariContent ul")
											.append('<li id=\''+idFirmatario+'\'><a href="javascript:deleteFirmatario('+codice
													+ ')"><img src="${pageContext.request.contextPath}/images/cross.gif" border="0" /></a>&nbsp;'+ descrizione +'</li>');
											addFirmatarioToEntity(codice);
									}
								}
							}
						}
						
						function deleteFirmatario(codice){
							if (codice){
								if(codice!=''){									
									var idFirmatario = 'firmatario_id_'+codice;
									if((jQuery('#'+idFirmatario).length > 0)){
										 removeFirmatarioToEntity(codice);
										 jQuery("#"+idFirmatario).remove();
									}
								}
							}
						}
												
						function addFirmatarioToEntity(codice){
							if(codice){
								if(codice!=''){
									var valoriPresenti = jQuery('#valore_entity_firmatari').val();
									var arValori = valoriPresenti.split(',');
									var trovato = false;
									for(var i=0;i<arValori.length;i++){
										if(arValori[i] == codice){
											trovato=true;
											break;
										}
									}
									if(!trovato){
										jQuery('#valore_entity_firmatari').val(valoriPresenti+codice+',');
									}
								}
							}		
						}
						function removeFirmatarioToEntity(codice){
							if(codice){
								if(codice!=''){
									var valoriPresenti = jQuery('#valore_entity_firmatari').val();
									var arValori = valoriPresenti.split(',');
									var trovato = false;
									for(var i=0;i<arValori.length;i++){
										if(arValori[i] == codice){
											trovato=true;
											break;
										}
									}
									if(trovato){
										jQuery('#valore_entity_firmatari').val(valoriPresenti.replace((codice+','),''));
									}
								}
							}		
						}
						</script>
							<legend><b><fmt:message key="label.seleziona_i_firmatari" /></b></legend>
							<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="listaFirmatari_id" />
									<jsp:param name="propertyPath" value="firmatari" />				
									<jsp:param name="pathPropertyDescription" value="firmatari.descrizione" />
									<jsp:param name="pathPropertyCode" value="firmatari.codice" />
									<jsp:param name="autocompleterAjax" value="findResponsabili.htm" />	
									<jsp:param name="titleKey" value="label.ricerca_responsabile" />
									<jsp:param name="id_help" value="help_responsabili" />
									<jsp:param name="afterUpdateElement" value="updateFirmatari" />
								</jsp:include>
							</c:if>
						
							<spring-form:hidden path="comunicazioniT.listaFirmatari" id="valore_entity_firmatari"/>
							<spring-form:errors path="comunicazioniT.listaFirmatari" cssClass="error"/>
							<div id="listaFirmatariContent">
								<ul style="list-style: none;">
									
								</ul>
								${listaFirmatariContent}
							</div>
						
						</fieldset>
						
						
					</td>
				</tr>
				<!-- TEMPLATE MAIL DA INVIARE -->
				<tr>
					<td>
						<fmt:message key="label.mail_da_inviare" />
					</td>
					<td colspan="3">
					<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione==0}" >
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="mailtipo" />		
						<jsp:param name="propertyPath" value="comunicazioniT.mailtipo" />				
						<jsp:param name="pathPropertyDescription" value="comunicazioniT.mailtipo.descrizione" />
						<jsp:param name="pathPropertyCode" value="comunicazioniT.mailtipo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMailtipo.htm?ambito=M" />	
						<jsp:param name="titleKey" value="label.ricerca_mail_tipo" />
						<jsp:param name="id_help" value="help_mailtipo" /> 
					</jsp:include>
					</c:if>
					<c:if test="${comunicazionitmercato.entity.comunicazioniT.statoElaborazione!=0}" >
						<spring-form:input id="entity.mailtipo.descrizione_id" path="comunicazioniT.mailtipo.descrizione" size="70" readonly="true"/>
					</c:if>
					</td>
				</tr>
				<c:if test="${comunicazionitmercato.entity.id.codice!=null && comunicazionitmercato.comunicazioniT.statoElaborazione == 0}">
				<tr>
					<td><fmt:message key="label.blocca_configurazione_comunicazione" /></td>
					<td>
						<spring-form:checkbox id="flagbloccaconfigurazione_id" path="flagbloccaconfigurazione" />
					    <fmt:message key="label.help.blocca_configurazione_comunicazione" />
					</td>
				</tr>	
				</c:if>
				
			
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
	<ul>
	    <%-- 
		<c:if test="${comunicazionitmercato.entity.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		--%>
		<c:if test="${comunicazionitmercato.entity.id.codice!=null}">
			<c:if test="${comunicazionitmercato.comunicazioniT.statoElaborazione == 0}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<%-- <c:if test="${comunicazionitmercato.comunicazioniT.statoElaborazione == 0}"> --%>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			<%-- </c:if> --%>
		</c:if>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		<%-- <li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li> --%>
	</ul>
	</div>
	<br /><br />
	<div class="titoloSezione"><fmt:message key="label.lista_comunicazioni" /></div>
	<form name="inviodati.inner" action="view.htm">
			${htmltable}
			<input type="hidden" name="codice" value="${comunicazionitmercato.entity.id.codice}"/>
	</form>
	
	<script type="text/javascript">
			var _jmesaUrl='view.htm?codice="${comunicazionitmercato.entity.id.codice}&';
			var _captionTab='<fmt:message key="comunicazionitmercato.label.lista_comunicazionitmercato.title" />';
		</script>
	
	
	<div id="functions">
	<ul>
		<c:if test="${comunicazionitmercato.entity.id.codice!=null && comunicazionitmercato.comunicazioniT.statoElaborazione != 0 && comunicazionitmercato.comunicazioniT.statoElaborazione != 2}">
			<li><a href="javascript:doSubmit('elaboraComunicazione.htm','',document.inviodati)"><fmt:message key="button.elabora_comunicazioni" /></a></li>
		</c:if>
	</ul>
	</div>
	
</body>
</html>