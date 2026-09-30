<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${graduatorietcom.entity.id.codice==null}">
			<fmt:message key="label.nuovo_graduatorietcom.title" />
		</c:if> 
		<c:if test="${graduatorietcom.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_graduatorietcom.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${graduatorietcom.entity.id.codice==null}">
			<fmt:message key="label.nuovo_graduatorietcom.title" />
		</c:if> 
		<c:if test="${graduatorietcom.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_graduatorietcom.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../graduatorietcom/view" />
    </jsp:include>
	<div id="subcontent">
	    <div class="parametriDiv">
			<div class="etichetta">
		      	<div><fmt:message key="label.bando"/>:</div>
		        <div><fmt:message key="label.graduatoria"/>:</div>
		        
		    </div>        
		    <div class="parametro">
		      	<div>${graduatoriet.bandi.descrizione}</div>
		        <div>${graduatoriet.descrizione}</div>
	        </div>
		</div>
		<br class="clear" />
		<spring-form:form commandName="graduatorietcom" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="graduatorietcom" />
		    </jsp:include>
			<table width="100%" >
				<tr>
					<td>
						<fmt:message key="label.data" />
					</td>
					
					<td colspan="3">
						<c:if test="${graduatorietcom.entity.id.codice==null}" >
							<spring-form:input id="data_id" path="entity.data" size="8" />
							<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						</c:if>
						<c:if test="${graduatorietcom.entity.id.codice!=null}" >
						  	<spring-form:input id="data_id" path="entity.data" size="8" readonly="true" />
						</c:if>
							
						<spring-form:errors path="entity.data" cssClass="error"/>
					</td>
					
				</tr>
				<tr>
					<td>
						<fmt:message key="label.oggetto_comunicazione" />
					</td>
					<td colspan="3">
					    <c:if test="${graduatorietcom.entity.id.codice==null}" >
							<spring-form:input id="descrizione_id" path="entity.descrizione" size="70"/>
						</c:if>
					    <c:if test="${graduatorietcom.entity.id.codice!=null}" >
							<spring-form:input id="descrizione_id" path="entity.descrizione" size="70" readonly="true" />
						</c:if>
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.movimento" />
					</td>
					<td colspan="3">
					     <c:if test="${graduatorietcom.entity.id.codice==null}" >
						<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento" value="entity.tipimovimento" />
							<jsp:param name="includiDisabilitate" value="false" />
						</jsp:include>		
						</c:if>
						<c:if test="${graduatorietcom.entity.id.codice!=null}" >
							<spring-form:input id="entity.tipimovimento_id" path="entity.tipimovimento.movimento" size="70" readonly="true"/>
						</c:if>
					</td>
				</tr>
				<c:if test="${graduatorietcom.entity.id.codice==null}" >
				<tr>
					<td>
						<fmt:message key="label.amministrazione" />
					</td>
					<td colspan="3">
					     <c:if test="${graduatorietcom.entity.id.codice==null}" >
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />		
							<jsp:param name="propertyPath" value="entity.amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="entity.amministrazioni.amministrazione" />
							<jsp:param name="pathPropertyCode" value="entity.amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />	
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
							<jsp:param name="id_help" value="help_amministrazione" /> 
						</jsp:include>
						</c:if>
						
					</td>
				</tr>
				</c:if>
				<c:if test="${graduatorietcom.entity.id.codice!=null}" >
					<tr>
					    <td>
							<fmt:message key="label.amministrazione" />
						</td>
						<td colspan="3">
							<spring-form:input id="entity.amministrazione_id" path="entity.amministrazioni.amministrazione" size="70" readonly="true"/>
					    </td>
				    </tr>
				</c:if>	
				<tr>
					<td>
						<fmt:message key="label.flag_protocolla_movimento" />
					</td>
					<td colspan="3">
					    <c:if test="${graduatorietcom.entity.id.codice==null}" >
							<spring-form:checkbox id="flgProtocolla_id" path="entity.flgProtocolla"  onchange="javascript:chekprotocolloattivo()"/>
						</c:if>
						<c:if test="${graduatorietcom.entity.id.codice!=null}" >
						    <c:if test="${graduatorietcom.entity.flgProtocolla==true}" >
								<fmt:message key="label.si" />
								${graduatorietcom.messageProtocolloNonAttivo}
							</c:if>
							<c:if test="${graduatorietcom.entity.flgProtocolla==false}" >
								<fmt:message key="label.no" />
							</c:if>
						</c:if>
						<spring-form:errors path="entity.flgProtocolla" cssClass="error"/>
						<span id="warn_mess"></span>
					</td>
						<script type='text/javascript'>
						function chekprotocolloattivo(){
						    
							if($("flgProtocolla_id").checked)
							{
							new Ajax.Request('${pageContext.request.contextPath}/graduatorietcom/ajaxCheckProtocolloAttivo.htm', {
								method: 'post',	
								onSuccess: function(transport){						
								  $("warn_mess").innerHTML = transport.responseText;
			    				  $("warn_mess").style.display='';     				  
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
						<fmt:message key="label.tipo_allegato_generare" />
					</td>
					<td colspan=3">
					<c:if test="${graduatorietcom.entity.id.codice==null}" >
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
							<jsp:param name="propertyPath" value="entity.letteretipo" />				
							<jsp:param name="pathPropertyDescription" value="entity.letteretipo.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.letteretipo.id.codice" />
							<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
							<jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
							<jsp:param name="id_help" value="help_doc_tipo" />
							<jsp:param name="onchangeCallback" value="onChangeCallLT(this,'letteretipo_hidden')"/>
							<jsp:param name="afterUpdateElement" value="updateLettereTipo" />
						</jsp:include>
						
					</c:if>
					<c:if test="${graduatorietcom.entity.id.codice!=null}" >
						<spring-form:input id="entity.letteretipo.descrizione_id" path="entity.letteretipo.descrizione" size="70" readonly="true"/>
					</c:if>
						
				</td>
				</tr>
				<c:set var="displayMettiAllaFirma" value="display: none;"/>				
				<c:if test="${graduatorietcom.entity.flagMettiallafirma eq true}" >
					<c:set var="displayMettiAllaFirma" value=""/>
				</c:if>
				<tr id="metti_alla_firma_id" style="${displayMettiAllaFirma}">
					<td>
					</td>
					<td colspan="3">
						<c:if test="${graduatorietcom.entity.id.codice==null}" >
							<spring-form:checkbox id="flagmettiallafirma_id" path="entity.flagMettiallafirma" onclick="mostranascondifirmatari(this)"/>
						</c:if>
						<c:if test="${graduatorietcom.entity.id.codice != null}" >
							<spring-form:checkbox id="flagmettiallafirma_id" disabled="true" path="entity.flagMettiallafirma" />
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
							<c:if test="${graduatorietcom.entity.id.codice==null}" >
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
							
							<spring-form:hidden path="entity.listaFirmatari" id="valore_entity_firmatari"/>
							<spring-form:errors path="entity.listaFirmatari" cssClass="error"/>
							<div id="listaFirmatariContent">
								<ul style="list-style: none;">
									
								</ul>
								${listaFirmatariContent}
							</div>
						
						</fieldset>
						
						
					</td>
				</tr>
				
				<tr>
					<td>
						<fmt:message key="label.mail_da_inviare" />
					</td>
					<td colspan="3">
					<c:if test="${graduatorietcom.entity.id.codice==null}" >
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="mailtipo" />		
						<jsp:param name="propertyPath" value="entity.mailtipo" />				
						<jsp:param name="pathPropertyDescription" value="entity.mailtipo.descrizione" />
						<jsp:param name="pathPropertyCode" value="entity.mailtipo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMailtipo.htm?ambito=M" />	
						<jsp:param name="titleKey" value="label.ricerca_mail_tipo" />
						<jsp:param name="id_help" value="help_mailtipo" /> 
					</jsp:include>
					</c:if>
					<c:if test="${graduatorietcom.entity.id.codice!=null}" >
						<spring-form:input id="entity.mailtipo.descrizione_id" path="entity.mailtipo.descrizione" size="70" readonly="true"/>
					</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.riservata_a" />
					</td>
					<td colspan="3">
					<c:if test="${graduatorietcom.entity.id.codice==null}" >
						<spring-form:select  path="entity.destinatari">
							<spring-form:option value="<%=WebConstants.COMUNICAZIONE_TUTTI_SOGGETTI_GRADUATORIA%>"><fmt:message key="graduatorietcom.label.comunicazione_tutti" /></spring-form:option>
							<spring-form:option value="<%=WebConstants.COMUNICAZIONE_SOLO_TITOLARI_CONCESSIONE%>"><fmt:message key="graduatorietcom.label.comunicazione_solo_titolari" /></spring-form:option>
							<spring-form:option value="<%=WebConstants.COMUNICAZIONE_SOLO_NON_TITOLARI_CONCESSIONI%>"><fmt:message key="graduatorietcom.label.comunicazione_solo_non_titolari" /></spring-form:option>
						</spring-form:select>
					</c:if>
					<c:if test="${graduatorietcom.entity.id.codice!=null}" >
						<c:if test="${graduatorietcom.entity.destinatari eq 'T'}" >
								<b><fmt:message key="graduatorietcom.label.comunicazione_tutti" /></b>
						</c:if>
						<c:if test="${graduatorietcom.entity.destinatari eq 'C'}" >
								<b><fmt:message key="graduatorietcom.label.comunicazione_solo_titolari" /></b>
						</c:if>
						<c:if test="${graduatorietcom.entity.destinatari eq 'N'}" >
								<b><fmt:message key="graduatorietcom.label.comunicazione_solo_non_titolari" /></b>
						</c:if>
					
					</c:if>	
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.posizione_dalla" />
					</td>
					<td>
					    <c:if test="${graduatorietcom.entity.id.codice==null}" >
							<spring-form:input id="posizioneDa_id" path="entity.posizioneDa" size="6" />
						</c:if>
						<c:if test="${graduatorietcom.entity.id.codice!=null}" >
							<spring-form:input id="posizioneDa_id" path="entity.posizioneDa" size="6" readonly="true"/>
						</c:if>
						<spring-form:errors path="entity.posizioneDa" cssClass="error"/>
					</td>
					<td>
						<fmt:message key="label.posizione_alla" />
					</td>
					<td>
					    <c:if test="${graduatorietcom.entity.id.codice==null}" >
							<spring-form:input id="posizioneDa_id" path="entity.posizioneAl" size="6" />
						</c:if>
						 <c:if test="${graduatorietcom.entity.id.codice!=null}" >
						 	<spring-form:input id="posizioneDa_id" path="entity.posizioneAl" size="6" readonly="true" />
						 </c:if>
						<spring-form:errors path="entity.posizioneAl" cssClass="error"/>
					</td>
				</tr>
				
				<%-- SCHEDE --%>
				
				<tr class="titoloSezione">
					<td  colspan="4"><fmt:message key="label.tab_schede"/></td>
				</tr>	
				
				<tr>
					
					
					<c:if test="${graduatorietcom.entity.id.codice==null}" >
					<td><fmt:message key="label.scheda" /></td>
					<td colspan="3" >
						
							<script type="text/javascript">
							
							function changeSchedaDinamica(inputField,listItem){
								
								var a = listItem.id;
								document.getElementById('schedaDinamica_id').value = inputField.value;
								document.getElementById('schedaDinamica_hidden').value = a;
								//doSubmit('changeScheda.htm?utilityAttivita=${param.utilityAttivita}','',document.inviodati);
								setTimeout('doSubmit("changeScheda.htm?utilityAttivita=${param.utilityAttivita}&ts_='+Date()+'#listaCampiAncor","",document.inviodati)',150);	
							}					

							
							function resetSchedaValue(inputField, hiddenFieldId) {
								if (inputField.value == ''){
									$(hiddenFieldId).value = '';
								}
								setTimeout('doSubmit("changeScheda.htm?utilityAttivita=${param.utilityAttivita}&ts_='+Date()+'#listaCampiAncor","",document.inviodati)',150);								
							}
							
							function filterSchedeBySoftware(element, entry) {
								var software = $('id_flag_schededinamiche').checked?$('id_flag_schededinamiche').value:'';
								if(software=='' || software!='TT'){
									software = getValoreDellaSelect($('modulosoftware_id'));
								}								
								return entry + "&codicesoftware="+software+"&ts_=" + Date();								
							}
							
							
							
							function renderCondizioni(obj, idx, nomeElementoValore, nomeElementoValore_hidden_fld_id){
								var elDestinatario = jQuery("#valore_"+idx);
								var codiceCampo = getValoreDellaSelect(obj);
								var valore = jQuery('#'+nomeElementoValore_hidden_fld_id).val();
								if(codiceCampo!=''){
									
									var jqxhr = jQuery.ajax({
										  url: "ajaxRenderCampo.htm",
										  context: document.body,
										  cache: false,				
										  dataType: "html",
										  data: "codiceCampo="+codiceCampo+"&idx="+idx+"&nomeElementoValore="+nomeElementoValore+"&valore="+valore,
										  success: function(dataResult) {
										  	elDestinatario.html(dataResult);
 											},
										  error: function(dataError){						  
											  elDestinatario.html(dataResult);											   
										  }	
										});		
								}else{									
									elDestinatario.html('');
								}
							}
							function addCampoDinamico(){								
								doSubmit('addCampoAScheda.htm?utilityAttivita=${param.utilityAttivita}&ts_='+Date()+'#listaCampiAncor','',document.inviodati);							
							}
							
							function removeCampoDinamico(idx){
								doSubmit('removeCampoScheda.htm?utilityAttivita=${param.utilityAttivita}&idx='+idx+'&ts_='+Date()+'#listaCampiAncor','<fmt:message key="javascript.confirm.delete" />',document.inviodati);
							}
							
							</script>
							
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="schedaDinamica" />
								<jsp:param name="propertyPath" value="schedaDinamicaFilter" />							
								<jsp:param name="pathPropertyDescription" value="schedaDinamicaFilter.scheda.descrizione" />
								<jsp:param name="pathPropertyCode" value="schedaDinamicaFilter.scheda.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm" />							
								<jsp:param name="titleKey" value="label.ricerca_schedaDinamica" />
								<jsp:param name="ajaxCallBack" value="filterSchedeBySoftware"/>
								<jsp:param name="afterUpdateElement" value="changeSchedaDinamica"/>
								<jsp:param name="inputIdOnChange" value="resetSchedaValue(this,'schedaDinamica_hidden')" />
							</jsp:include>
						
							<input type="checkbox" id="id_flag_schededinamiche" value="TT"/>						
							<init:help idHelp="help_schede_tt" textKey="help.ricerca_per_software_TT"/>
								
						</td>
						</c:if>
						
				</tr>		
				<c:if test="${graduatorietcom.entity.id.codice==null}" >
				<tr>
					<td colspan="7">
					
					<a name="listaCampiAncor"> </a>
					<c:if test="${not empty graduatorietcom.schedaDinamicaFilter.righe}">
					<fieldset>
						<table style="width:1000px">
							<tr class="titoloSezione">
								<td style="width: 50px">&nbsp;</td>
								<td style="width: 50px"></td>
								<td style="width: 400px"><fmt:message key="label.campo" /></td>
								<td style="width: 150px"><fmt:message key="label.criterio" /></td>
								<td style="width: 400px"><fmt:message key="label.valore" /></td>
								<td style="width: 50px"></td>
								<td style="width: 25px"></td>							
							</tr>
							<c:forEach items="${graduatorietcom.schedaDinamicaFilter.righe }" var="riga_var" varStatus="rigaStatus">
				
							<tr>								
								<td>
								
									
									<spring:bind path="schedaDinamicaFilter.righe[${rigaStatus.index}].andOr">
									<c:choose>
										<c:when test="${rigaStatus.index eq 0}">
											<input type="hidden" name="${status.expression}" value="" />
										</c:when>
										<c:otherwise>
											<select name="${status.expression}">
												<option value=""></option>
												<option value="<%=AndOrRestriction.AND %>" <c:if test="${ status.value eq 'AND'}"> selected </c:if> >E</option>
												<option value="<%=AndOrRestriction.OR %>" <c:if test="${ status.value eq 'OR'}"> selected </c:if> >O</option>
											</select>
										</c:otherwise>
									</c:choose>												
									</spring:bind>
								</td>
								<td>
									<spring:bind path="schedaDinamicaFilter.righe[${rigaStatus.index}].parentesiSx">
										<select name="${status.expression}">
											<option value=""></option>
											<option value="(" <c:if test="${ status.value eq '('}"> selected </c:if> >(</option>
										</select>											
									</spring:bind>
								</td>
								<td>
								<c:set var="nomeElementoValore" value="" />
								
								<spring:bind path="schedaDinamicaFilter.righe[${rigaStatus.index}].valore">
									<c:set var="nomeElementoValore" value="${status.expression}" />
									<input type="hidden" id="hidden_fld_id_${rigaStatus.index}" name="${ nomeElementoValore }_hidden_fld" value="${ status.value }"/>
								</spring:bind>
								
									<spring:bind path="schedaDinamicaFilter.righe[${rigaStatus.index}].campo.id.codice">
										<select name="${status.expression}" id="select_campi_${rigaStatus.index}"
											onchange="renderCondizioni(this, ${rigaStatus.index}, '${nomeElementoValore}', 'hidden_fld_id_${rigaStatus.index}')">
											<option value=""></option>												
											<c:forEach items="${graduatorietcom.schedaDinamicaFilter.listaCampiModello}" var="d2c_var" varStatus="d2c_varStatus">
												<option value="${d2c_var.id.codice}"  <c:if test="${ status.value eq d2c_var.id.codice}"> selected </c:if> >${d2c_var.nomecampo}</option>	
											</c:forEach>	
										</select>											
									</spring:bind>
								</td>
								
								<td>
									<spring:bind path="schedaDinamicaFilter.righe[${rigaStatus.index}].tipoConfronto">
										<select name="${status.expression}">												
											<option value="<%=FieldOperationsEnum.EQ %>" <c:if test="${ status.value eq 'EQ'}"> selected </c:if> >è uguale a</option>
											<option value="<%=FieldOperationsEnum.NE %>" <c:if test="${ status.value eq 'NE'}"> selected </c:if> >non è uguale a</option>
											<option value="<%=FieldOperationsEnum.STARTSWITH %>" <c:if test="${ status.value eq 'STARTSWITH'}"> selected </c:if> >inizia per</option>
											<option value="<%=FieldOperationsEnum.ENDSWITH %>" <c:if test="${ status.value eq 'ENDSWITH'}"> selected </c:if> >finisce per</option>
											<option value="<%=FieldOperationsEnum.CONTAINS %>" <c:if test="${ status.value eq 'CONTAINS'}"> selected </c:if> >è simile</option>
											<option value="<%=FieldOperationsEnum.GE %>" <c:if test="${ status.value eq 'GE'}"> selected </c:if> >è maggiore uguale a</option>
											<option value="<%=FieldOperationsEnum.GT %>" <c:if test="${ status.value eq 'GT'}"> selected </c:if> >è maggiore di</option>
											<option value="<%=FieldOperationsEnum.LE %>" <c:if test="${ status.value eq 'LE'}"> selected </c:if> >è minore uguale a</option>
											<option value="<%=FieldOperationsEnum.LT %>" <c:if test="${ status.value eq 'LT'}"> selected </c:if> >è minore di</option>
											<%-- NON è VUOTO NON SI PUO' TESTARE IN QUANTO LA RIGA NON E' NEL DB 
												<option value="<%=FieldOperationsEnum.ISEMPTY %>" <c:if test="${ status.value eq 'ISEMPTY'}"> selected </c:if> >è vuoto</option>
											 --%>
											<option value="<%=FieldOperationsEnum.ISNOTEMPTY %>" <c:if test="${ status.value eq 'ISNOTEMPTY'}"> selected </c:if> >non è vuoto</option>
										</select>											
									</spring:bind>
								</td>
								<td>
									<script type="text/javascript">
										jQuery(document).ready(function(){
											renderCondizioni($('select_campi_${rigaStatus.index}'), ${rigaStatus.index}, '${nomeElementoValore}', 'hidden_fld_id_${rigaStatus.index}');
							    		});
									</script>
									<div id="valore_${rigaStatus.index}"></div>										
								</td>
								<td>
									<spring:bind path="schedaDinamicaFilter.righe[${rigaStatus.index}].parentesiDx">
										<select name="${status.expression}">
											<option value=""></option>
											<option value=")" <c:if test="${ status.value eq ')'}"> selected </c:if> >)</option>
										</select>											
									</spring:bind>
								</td>
								<td>
									<a style="float: left;" class="eliminaRiga" href="javascript:removeCampoDinamico(${rigaStatus.index});" title="<fmt:message key="label.rimuovi_filtro" />">
										<label><fmt:message key="label.del.record.image" /></label>
									</a>
								</td>									
							</tr>				
							</c:forEach>
						</table>
						
					</fieldset>
					</c:if>						
						<a style="float: left;" class="addColumn" href="javascript:addCampoDinamico();" title="<fmt:message key="label.aggiungi_filtro" />">
							<label><fmt:message key="label.add.record.image" /></label>
						</a>				
					</td>
				</tr>
				</c:if>
         
				<c:if test="${graduatorietcom.entity.id.codice!=null}" >
				<tr>
					<td colspan="7" style="padding-top: 20px;">
					<b>${graduatorietcom.entity.dyn2filtri}</b>
					<%-- <spring-form:textarea id="dyn2filtri_id" path="entity.dyn2filtri" rows="2" cols="150"/></td>--%>
				</tr>	
				</c:if>
						
				<%-- SCHEDE END --%>	
						
			</table>
			
	<c:if test="${graduatorietcom.entity.id.codice!=null}" >	
	
	<div class="titoloSezione"><fmt:message key="label.lista_comunicazioni" /></div>
	<form name="inviodati" action="view.htm">
				${htmltable}
				
				
	</form>
	
	<input type="hidden" name="codice" value="${graduatorietcom.entity.id.codice}"/>
	<%-- 
	<div class="jmesa" >
		<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td><fmt:message key="form.graduatoried.posizione" /></td>
					<td><fmt:message key="form.graduatoried.richiedente" /></td>
					<td><fmt:message key="label.istanza" /></td>
					<td><fmt:message key="label.movimento" /></td>
					<td><fmt:message key="label.allegato" /></td>
					<td width="10%">
					    <table>
					       <tr>
					       		<td><fmt:message key="label.email" /></td>
					       		<td><fmt:message key="label.accettata" /></td>
					       		<td><fmt:message key="label.consegnata" /></td>
					       </tr>
					    </table>
					</td>
					<td width="25%" ><fmt:message key="label.evento" /></td>
					<td><fmt:message key="label.letto" /></td>
					<td><fmt:message key="label.azioni" /></td>			
				</tr>
			</thead>
			<tbody class="tbody" >
			<%int i=1;%>
			<c:forEach var="graduatoriedComDTO_var" items="${graduatoriedComDTOs}" varStatus="graduatoriedCampiStatus">

			<tr class="<%=(i%2)==0?"odd":"even"%>"  >
				<td>${graduatoriedComDTO_var.graduatoried.posizione}</td>
				<td>${graduatoriedComDTO_var.graduatoried.istanza.richiedente.descrizioneRichiedente}
					<c:if test="${graduatoriedComDTO_var.graduatoried.istanza.titolarelegale != null}">
					<br /><fmt:message key="label.titolarelegale"/><br/>${graduatoriedCampi_var.graduatoried.istanza.titolarelegale.descrizioneRichiedente}
					</c:if>
				</td>
				<td>
					<a href ="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${graduatoriedComDTO_var.graduatoried.istanza.id.codice }&software=${graduatoriedComDTO_var.graduatoried.istanza.software }');" >${graduatoriedComDTO_var.graduatoried.istanza.numeroistanza }</a >					
				</td>
				<td>
					<a href ="javascript:historySet('${_urlback}','../movimenti/view.htm?codice=${graduatoriedComDTO_var.movimenti }');" >${graduatoriedComDTO_var.descMovimenti }</a >					
				</td>
				
				
				<td>
				    <c:if test="${graduatoriedComDTO_var.oggetto!=null}">
					<jsp:include page="../includes/visualizzaOggetto.jsp" >
      						<jsp:param name="idElemento" value="docIstanza${graduatoriedComDTO_var.oggetto}" />
      						<jsp:param name="fileId" value="${graduatoriedComDTO_var.oggetto}" />
		   			</jsp:include>
		   			</c:if>
				</td>
				
				
				--%>
				
				<%-- 
				<td>
				    <c:if test="${graduatoriedComDTO_var.movimentimail!=null}">
						<a href ="javascript:historySet('${_urlback}','../movimentimail/view.htm?codice=${graduatoriedComDTO_var.movimentimail }');" ><img src="${pageContext.request.contextPath}/images/email.png"/></a >
		   			</c:if>
				</td>
	          	--%>
	          	
	         	<%--
	           <c:if test="${graduatoriedComDTO_var.movimentimail!=null}">
	           <td>
	                <table>
	           			 <tr>
	           			 	<td>
							&nbsp;
							<a href="javascript: void 0" title="<fmt:message key="movimentimail.label.lista_movimentimail.title"/> - ${graduatoriedComDTO_var.descMovimenti}" onclick="mostraEmail('movimentiMail_${graduatoriedComDTO_var.movimenti}',${graduatoriedComDTO_var.movimenti});"><img src="${pageContext.request.contextPath}/images/email.png"/></a>									
							<div id="movimentiMail_${graduatoriedComDTO_var.movimenti}" 
							dojoType="dijit.Dialog" 
							title="<fmt:message key="movimentimail.label.lista_movimentimail.title"/> - ${graduatoriedComDTO_var.descMovimenti}" style="width: 80%;height:80%; display: none;">
						    	<div id="movimentiMail_${graduatoriedComDTO_var.movimenti}_inner" style="width: 100%; height: 100%;">
						    		
						    	</div>
						    </div>
						    </td>
						    <td style="padding-left: 40px;">
						        <c:if test="${graduatoriedComDTO_var.accettata eq true}">
						    		<img src="${pageContext.request.contextPath}/images/accept.gif"/>
						    	</c:if>
						    	<c:if test="${graduatoriedComDTO_var.accettata eq false}">
						    		&nbsp;
						    	</c:if>
				  			</td>
				  			<td style="padding-left: 40px;">
				  			    <c:if test="${graduatoriedComDTO_var.consegnata eq true }">
						    		<img src="${pageContext.request.contextPath}/images/accept.gif"/>
						    	</c:if>
						    	<c:if test="${graduatoriedComDTO_var.consegnata eq false}">
						    	&nbsp;
						    	</c:if>
				  			</td>
				  		</tr>
				  </table>
				</td>    
				</c:if>
				<c:if test="${graduatoriedComDTO_var.movimentimail==null}">
					<td>&nbsp;</td>
				</c:if>
	            <td>
	                 <c:choose>
		                 <c:when test="${graduatoriedComDTO_var.movimenti !=null}">
	   							<a href ="javascript:historySet('${_urlback}','../istanzeeventi/list.htm?codicemovimento=${graduatoriedComDTO_var.movimenti }');" >${graduatoriedComDTO_var.istanzeeventi.descrizione}</a >
					     </c:when>
					     <c:otherwise>
					      	<a href ="javascript:historySet('${_urlback}','../istanzeeventi/list.htm?codiceIstanza=${graduatoriedComDTO_var.graduatoried.istanza.id.codice}');" >${graduatoriedComDTO_var.istanzeeventi.descrizione}</a > 
						 </c:otherwise>
					</c:choose>
	               
	                	            
	            </td>
	              <td>
	               	 <c:choose>
						<c:when test="${graduatoriedComDTO_var.istanzeeventi.flagLetto eq 'true'}">
   							<img src="${pageContext.request.contextPath}/images/accept.gif"></img>
						</c:when>
						<c:when test="${graduatoriedComDTO_var.istanzeeventi.flagLetto eq 'false'}">
   							 <input id="flagLettoId${graduatoriedComDTO_var.istanzeeventi.id.codice }" type="checkbox" onclick="changeCheckboxValue(flagLettoId${graduatoriedComDTO_var.istanzeeventi.id.codice },'${pageContext.request.contextPath}/istanzeeventi/ajaxChangeFlagLetto.htm?codice=${graduatoriedComDTO_var.istanzeeventi.id.codice}')" ${graduatoriedComDTO_var.istanzeeventi.flagLetto?'checked':''} />
						</c:when>
						 <c:otherwise> 
						  
						 </c:otherwise>
					</c:choose>
	            </td>
	            <td>
	                 <c:if test="${graduatoriedComDTO_var.movimentimail==null}">
		            	<a class="rielabora" href="javascript:historySet('${_urlback}', '../graduatorietcom/elaboraGraduatoriadcom.htm?codice=${graduatoriedComDTO_var.id.codice}', '')" title="<fmt:message key="label.rielabora" />${graduatorietcom_var.codice}">
							<label><fmt:message key="label.rielabora" /></label>
						</a>
					</c:if>
					<a class="eliminaRiga" href="javascript:doSubmit('deleteGraduatoriadcom.htm?codice=${graduatoriedComDTO_var.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.azioni"/>  ">
						<label><fmt:message key="label.azioni" /></label>
					</a>
	           
	            
	            </td>
				
			
			
			</tr>
			<%i++;%>
			</c:forEach>
			</tbody>
		</table>
	   </div>
	   
	   --%>
	</c:if>
			
			
			
			<script type='text/javascript'>
				$('data_id').focus();
				
				function switchAutocompleterletteretipo(){
					if($('id_flag_letteretipo').checked){
					    $('id1_letteretipo').style.display="inline";
					    $('id2_letteretipo').style.display="none";
					    $('letteretipo_id2').value='';
					    $('letteretipo_hidden').value='';
					}else{
						$('id1_letteretipo').style.display="none";
						$('id2_letteretipo').style.display="inline";
						$('letteretipo_id1').value='';
					    $('letteretipo_hidden').value='';
					}
				}
				
				function mostraEmail(divId,codiceMov){
					var jqxhr = jQuery.ajax({
						  url: "../movimentimail/ajaxMostraEmail.htm",
						  context: document.body,
						  cache: false,				
						  dataType: "html",
						  data: "codicemovimento="+codiceMov+"&codiceistanza=",
						  success: function(dataResult) { 
							   $(divId + "_inner").innerHTML = dataResult;
							   dijit.byId(divId).show();
							},
						  error: function(dataError){						  
							  $(divId + "_inner").innerHTML = dataResult;
							   dijit.byId(divId).show();
						  }	
						});		
					
				}
				
				function verificaDocumentiFirmati(codiceGraduatoriaD){
					
					var jqxhr = jQuery.ajax({
						  url: "../graduatorietcom/ajaxReportDocDaFirmare.htm",
						  context: document.body,
						  cache: false,				
						  dataType: "html",
						  data: "codice="+codiceGraduatoriaD,
						  success: function(dataResult) { 
							   $("dialogsDocDaFirmare_inner").innerHTML = dataResult;
							   dijit.byId("dialogsDocDaFirmare").show();
							},
						  error: function(dataError){						  
							  $("dialogsDocDaFirmare_inner").innerHTML = dataResult;
							   dijit.byId("dialogsDocDaFirmare").show();
						  }	
						});		
					
				}
				</script>	
		</spring-form:form>
		
		<div dojoType="dijit.Dialog" id="dialogsDocDaFirmare" title="<fmt:message key="label.documenti_da_firmare" />"  style="display: none;">		
			<div id="dialogsDocDaFirmare_inner">
			</div>
		</div>
		
	</div>
	<div id="functions">
		<ul><%-- <fmt:message key="javascript.confirm.insert_comunicazioni" /> --%>
			<c:if test="${graduatorietcom.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${graduatorietcom.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doHref('elaboraTutteGraduatoriadcom.htm?codice=${graduatorietcom.entity.id.codice}','')"><fmt:message key="button.elabora_comunicazioni" /></a></li>
				<li><a href="javascript:doHref('delete.htm?codice=${graduatorietcom.entity.id.codice}','<fmt:message key="javascript.confirm.delete_comunicazioni" />')"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>