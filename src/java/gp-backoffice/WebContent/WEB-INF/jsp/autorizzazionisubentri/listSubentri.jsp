<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService.ENUM_COPIA_ONERI"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_subentri.title" />
	</title>
</head>
<body>
	<style>
		.form-group > span{
			padding-left: 20px;
		}
		.form-group > span > em{
			font-weight: bold;
			text-decoration: underline; 	
		}
	</style>
	<span class="titoloPagina">
		<fmt:message key="label.gestione_subentri.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
		<script type="text/javascript">
			//<![CDATA[
				 const array=new Array();
		   		 function changeStatus(obj){
		   		 	var i=0;
		   		 	while(i<array.length){
						if(obj.checked){
							$('check'+array[i]).checked=true;
						}else{
							$('check'+array[i]).checked=false;
						}
						i++;
					}
		   		 }
		   		 function enableDisableNumAutField(autNumFieldId,flagMantieniNumId, regAuto){
			   		 if(regAuto == 'true'){
			   			 $(autNumFieldId).value = 'Numerazione da registro';
				   		 $(autNumFieldId).disabled = true;
				   		 if(flagMantieniNumId!='')
				   		 {
				   		 	$(flagMantieniNumId).disabled = false;
				   		    $(flagMantieniNumId).checked = false;
				   		 }
			   		 }else{
				   		if($(autNumFieldId).value == 'Numerazione da registro'){
			   				$(autNumFieldId).value = '';
				   		}
			   			$(autNumFieldId).disabled = false;
				   		 if(flagMantieniNumId!='')
				   		 {
			   				 $(flagMantieniNumId).disabled = true;
			   				 $(flagMantieniNumId).checked = false;
				   		 }
			   		 }
		   		 }
		   		 function resetNumAutField(autNumFieldId,autHiddenFieldId,oldRegAutoValue){
			   		if($(autHiddenFieldId).value == ''){
			   			enableDisableNumAutField(autNumFieldId,'', oldRegAutoValue);
			   		}
		   		 }		   		
				
		   		             
				function visualizzaDataAffitto(inputField, listItem) {
				    var codice = listItem.id;

				    document.getElementById('causale_acq_id').value = inputField.value;
				    document.getElementById('causale_acq_hidden').value = codice;
				    console.log(codice);
				    dataFineAffittoDisplay(codice);
				}

				function dataFineAffittoDisplay(codice) {

				    new Ajax.Request('<%=request.getContextPath()%>/autorizzazionisubentri/isDataFineAffitto.htm', {
				        method: 'get',
				        parameters: { codiceConcCausale: codice },
				        onSuccess: (transport) => {
				            var flag = transport.responseText;
				            console.log(flag);
				            if (flag == 'true') {
				                document.getElementById('dataAffitto').style.display='';
				            }
				            else {
				            	document.getElementById('dataAffitto').style.display='none';
				                document.getElementById('autorizdatafineaff_id').value = '';
				            }
				        },
				        onFailure: function () { }

				    });
				};
				function pushToArray(valore){
		   			array.push(valore);
		   		}
				function validaDatiObbligatori(){
					let causaleCessazione = document.getElementById('causale_cess_id').value;
					let causaleAcquisizione = document.getElementById('causale_acq_id').value;
					let dataCessazione = document.getElementById('dataCessazione_id').value;
					let dataFineAffitto = document.getElementById('autorizdatafineaff_id').value;
					let comportOneri = document.getElementById('subentriComportamentoOneri').value;
					let returnVal = true;
					if(causaleCessazione=='' || 	
								causaleAcquisizione=='' || 
								dataCessazione=='' || 
								(document.getElementById('dataAffitto').style.display == '' && dataFineAffitto=='') ||
								comportOneri==''){
							alert('Selezionare i dati obbligatori');
							returnVal = false;
						
					}
					return returnVal;
				}
				function insertSubentri(){
					
					if(validaDatiObbligatori()){
						
						doSubmit('validateInsertSubentri.htm','Attenzione! Stai per eseguire il subentro delle Autorizzazioni/Concessioni selezionate. Continuare?');
					}
				}
				
				vbg.ready(() => {
					const checkBoxes = document.querySelectorAll('.chk_seleziona');
					checkBoxes.forEach( chk => {
						chk.addEventListener('click', function(e) {
							e.stopPropagation();
						});
					});
					
					if(document.getElementById('causale_acq_hidden').value == '') {
						document.getElementById('dataAffitto').style.display='none';
					}else{
						dataFineAffittoDisplay(document.getElementById('causale_acq_hidden').value);
					}
					
				});
				
				
				
		    //]]> 
		</script>
	 	<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}</c:param>
	 	</c:import>
	 	<br class="clear"/>
	 	<div>
			<spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati" cssClass="vbg-form">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
		        	<jsp:param name="commandName" value="autorizzazioniSubentriCommand" />
		    	</jsp:include>
		    	
				<div>
					<div class="form-group">
	                  <label>* <fmt:message key="label.causale_cessazione" /></label>
	                  <spring-form:input id="causale_cess_id" path="causaleCessazione.descrizione" cssClass="searchbox" onchange="checkValue(this,'causale_cess_hidden')" size="50"  onkeydown="javascript:return searchAll(this,event)" />
							<init:autocompleter methodAjax="findConcessioniCausali.htm?flagStorico=true" idHidden="causale_cess_hidden" idInput="causale_cess_id" inputTitleKey="label.ricerca_causale"/>
							<spring-form:errors path="causaleAcquisizione" cssClass="error" />  
							<spring-form:hidden id="causale_cess_hidden" path="causaleCessazione.id.codice"  />
					  <span>
					  <label>* <fmt:message key="label.data_cessazione" /></label>
	                  <spring-form:input tabindex="3" id="dataCessazione_id" path="dataCessazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
							<init:calendar imagePath="/images/cal.gif" idImage="calDataCessazione" idInput="dataCessazione_id" textKey="label.calendar"/>
				   			<spring-form:errors path="dataCessazione" cssClass="error" />
				   	</span>	
					</div>
					<div class="form-group">
	                  <label>* <fmt:message key="label.causale_acquisizione" /></label>
	                  <spring-form:input id="causale_acq_id" path="causaleAcquisizione.descrizione" cssClass="searchbox" onchange="checkValue(this,'causale_acq_hidden')" size="50" onkeydown="javascript:return searchAll(this,event)" />
							<init:autocompleter methodAjax="findConcessioniCausali.htm?flagStorico=false" afterUpdateElement="visualizzaDataAffitto"  idHidden="causale_acq_hidden" idInput="causale_acq_id" inputTitleKey="label.ricerca_causale"/>
							<spring-form:errors path="causaleAcquisizione" cssClass="error" />  
							<spring-form:hidden id="causale_acq_hidden" path="causaleAcquisizione.id.codice"  />
						<span id="dataAffitto">	
						<label>* <fmt:message key="label.concessione_data_fine_affitto" /></label>
		                  	<spring-form:input id="autorizdatafineaff_id" path="filter.dataFineAffitto" size="10" onblur="isValidDate(this,true);" /> 
								<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdatafineaff_id" idInput="autorizdatafineaff_id" textKey="label.calendar" /> 
								<spring-form:errors	path="filter.dataFineAffitto" cssClass="error"/>
						</span>
					</div>
					<div class="form-group">
	                  <label>* <fmt:message key="label.comportamento_oneri" /></label>				
								<spring-form:select id="subentriComportamentoOneri" path="subentriComportamentoOneri">
									<spring-form:option value=""><fmt:message key="label.selezionare_un_elemento" /></spring-form:option>
									<spring-form:option value="<%=ENUM_COPIA_ONERI.COPIARE_ONERI_NON_PAGATI %>"><fmt:message key="label.copia_oneri_non_pagati" /></spring-form:option>
									<spring-form:option value="<%=ENUM_COPIA_ONERI.NON_COPIARE_ONERI_NON_PAGATI %>"><fmt:message key="label.non_copiare_oneri_non_pagati" /></spring-form:option>
								</spring-form:select>	
	                </div>
                  			
				</div>
				<br />
				<h2><fmt:message key="label.lista_autorizzazioni_concessioni" /> &nbsp;
				<input type="checkbox" id="seleziona_tutte" onclick="changeStatus(this);" 
				title="<fmt:message key='label.checkbox.selDeselAll' />" checked="checked" />
				<label style="font-size:12px" for="seleziona_tutte"><fmt:message key="label.seleziona_deseleziona_tutti" /></label></h2>
					<c:forEach items="${autorizzazioniSubentriCommand.listAutDaSubentrare}" var="curr_auth" varStatus="authIdx" >
						<fieldset class="collassabile" data-collassato="false">
							<legend>
								<c:choose>
									<c:when test="${curr_auth.concessione.id.codice!=null}"><fmt:message key="label.concessione" /></c:when>
									<c:otherwise><fmt:message key="label.autorizzazione" /></c:otherwise>
								</c:choose>
								&nbsp;<spring-form:checkbox path="listAutDaSubentrare[${authIdx.index}].daSubentrare" cssClass="chk_seleziona" id="check${authIdx.index }" title="Segna per subentro" />
								<b>${curr_auth.autorizzazione.autoriznumero }</b>
								-
								<c:if test="${curr_auth.autorizzazione.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
								<c:if test="${curr_auth.autorizzazione.flagAttiva ne true}"><b><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.autorizzazione.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></b></c:if>
									<script type="text/javascript">
										pushToArray('${authIdx.index}');
									</script>	
							</legend>
							<div class="form-group">
								  <span>
									<label><fmt:message key="label.numero"/>:</label>
									<em>${curr_auth.autorizzazione.autoriznumero }</em>
										<c:choose>
											<c:when test="${curr_auth.registroAutomatico eq true}">
												<spring-form:input id="autoriznum_id${authIdx.index}" path="listAutDaSubentrare[${authIdx.index}].autoriznumeroSubentro" disabled="true" />
												<script type="text/javascript">
													$('autoriznum_id${authIdx.index}').value="Numerazione da registro";
												</script>
													<spring-form:checkbox 
													id="flagMantieniNumero_id${authIdx.index}" cssStyle="vertical-align: middle;" path="listAutDaSubentrare[${authIdx.index}].flagMantieniNumero" 
													title="Se il registro non prevede l'assegnazione automatica del numero, il chekbox non sarà selezionabile" 
													/>&nbsp;<label for="flagMantieniNumero_id${authIdx.index}"><fmt:message key="label.usa_stesso_numero" /></label>
												
											</c:when>
											<c:otherwise>
												<spring-form:input id="autoriznum_id${authIdx.index}" path="listAutDaSubentrare[${authIdx.index}].autoriznumeroSubentro" />
											</c:otherwise>
										</c:choose>
									</span>
							</div>
							<div class="form-group">									
									<span>
									<label><fmt:message key="label.data"/>:</label>
									<em><fmt:formatDate value="${curr_auth.autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></em>
									<spring-form:input id="autdatasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizdataSubentro" size="10" onblur="isValidDate(this,true);" />
									<init:calendar imagePath="/images/cal.gif" idImage="cal_image${authIdx.index }" idInput="autdatasub_id${authIdx.index }" textKey="label.calendar" />
									</span>
									<span>
									<label><fmt:message key="label.data_scadenza"/>:</label>
									<em><fmt:formatDate value="${curr_auth.autorizzazione.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></em>
									<spring-form:input id="autdatascadenzasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizdatascadenzaSubentro" size="10" onblur="isValidDate(this,true);" />
									<init:calendar imagePath="/images/cal.gif" idImage="cal_image_s_${authIdx.index }" idInput="autdatascadenzasub_id${authIdx.index }" textKey="label.calendar" />						
									</span>
							</div>
							<div class="form-group">
								  <span>
									<label><fmt:message key="label.comune"/>:</label>
									<em>${curr_auth.autorizzazione.autorizcomune.comune}</em>
									<spring-form:input id="autcomsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizcomuneSubentro.descrizioneEstesa" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autcomsub_hidden${authIdx.index }')" size="30" onkeydown="javascript:return searchAll(this,event)" />
									<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="autcomsub_hidden${authIdx.index }" idInput="autcomsub_id${authIdx.index }" inputTitleKey="label.ricerca_comune"/>  
									<spring-form:hidden id="autcomsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizcomuneSubentro.codicecomune"  />
								  </span>	
								  <span>
									<label><fmt:message key="label.registro"/>:</label>
										<em>${curr_auth.autorizzazione.tipologiaregistro.trDescrizione}</em>
										<script type="text/javascript">
											function setHiddenFieldregistro${authIdx.index}(inputField,listItem){
												var codReg = listItem.id;
												var regAuto = jQuery(listItem).attr('name');
												var autNumFieldId = 'autoriznum_id${authIdx.index}';
												var flagMantieniNumId = 'flagMantieniNumero_id${authIdx.index}';
												$('autregsub_id${authIdx.index}').value = inputField.value;
												$('autregsub_hidden${authIdx.index}').value = codReg;
												$('autregsub_id${authIdx.index}_choices').fade();
												enableDisableNumAutField(autNumFieldId,flagMantieniNumId, regAuto);
											}	
										</script>				
										<spring-form:input id="autregsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizregistroSubentro.trDescrizione" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autregsub_hidden${authIdx.index }');resetNumAutField('autoriznum_id${authIdx.index}','autregsub_hidden${authIdx.index }','${curr_auth.registroAutomatico}');" size="30" onkeydown="javascript:return searchAll(this,event)" />
										<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="setHiddenFieldregistro${authIdx.index}" idHidden="autregsub_hidden${authIdx.index }" idInput="autregsub_id${authIdx.index }" inputTitleKey="label.ricerca_tipo_registro"/>  
										<spring-form:hidden id="autregsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizregistroSubentro.id.codice"  />
									</span>
						</div>
						<div class="form-group">									
									<span>
									
									<label><fmt:message key="label.istanza"/>:</label>
										<em><c:out value="${curr_auth.autorizzazione.istanza.numeroistanza}" default="-" /></em>
									</span>
									<span>
									<label><fmt:message key="label.concessione_titolare"/>:</label>
										<em>${curr_auth.autorizzazione.anagrafe.descrizioneRichiedente}</em>
									</span>
									<span>
									<c:if test="${ not empty curr_auth.autorizzazione.occupante.id.codice }">
										<label><fmt:message key="mercatid.label.occupante"/>:</label>
											<em>${curr_auth.autorizzazione.occupante.descrizioneRichiedente}</em>
										</span>
									</c:if>
						</div>
						<c:if test="${curr_auth.concessione.id.codice!=null}">
							<div class="form-group">									
										<span>
										<label><fmt:message key="label.manifestazione"/>:</label>
											<em>${curr_auth.concessione.mercati.descrizione } - ${curr_auth.concessione.mercatiUso.descrizione } - ${curr_auth.concessione.mercatiD.codiceposteggio}</em>
										</span>
							</div>	
						</c:if>
						<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.id.codice!=null}">
							<div class="form-group">		
							<fieldset class="aut_collegata">
								<legend><i class="fa fa-sm fa-link"><fmt:message key="label.autorizzazione_collegata" /></i>
								<b>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autoriznumero }</b> - 
								<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
								<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
								</legend>
								
								<div class="form-group">
								  <span>
									<label><fmt:message key="label.numero"/></label>
										<em>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autoriznumero }</em>
										<c:choose>
										<c:when test="${curr_auth.autorizzazioneCollegataHelper.registroAutomatico eq true}">
											<input id="autoriznumAutColl_id${authIdx.index}"  type="text" value="Numerazione da registro" disabled="disabled" />
											<script type="text/javascript">
													$('autoriznumAutColl_id${authIdx.index}').value="Numerazione da registro";
											</script>											
											<spring-form:checkbox 
											id="flagAutCollMantieniNumero_id${authIdx.index}" cssStyle="vertical-align: middle;" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.flagMantieniNumero" 
											title="Se il registro non prevede l'assegnazione automatica del numero, il chekbox non sarà selezionabile" 
											/>&nbsp;<label for="flagAutCollMantieniNumero_id${authIdx.index}"><fmt:message key="label.usa_stesso_numero" /></label>
												
										</c:when>
										<c:otherwise>
											<spring-form:input id="autoriznumAutColl_id${authIdx.index}" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autoriznumeroSubentro" />
										</c:otherwise>
										</c:choose>
									</span>
								</div>
								<div class="form-group">	
									<span>
									<label><fmt:message key="label.data"/></label>
										<em><fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></em>
										<spring-form:input id="autColldatasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizdataSubentro" size="10" onblur="isValidDate(this,true);" />
										<init:calendar imagePath="/images/cal.gif" idImage="calColl_image${authIdx.index }" idInput="autColldatasub_id${authIdx.index }" textKey="label.calendar" />
										</span>
										<span>
											<label><fmt:message key="label.data_scadenza"/></label>
												<em><fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></em>
												<spring-form:input id="autColldatascadenzasub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizdatascadenzaSubentro" size="10" onblur="isValidDate(this,true);" />
												<init:calendar imagePath="/images/cal.gif" idImage="calColl_image_s_${authIdx.index }" idInput="autColldatascadenzasub_id${authIdx.index }" textKey="label.calendar" />							
										</span>
									</div>
									
									<div class="form-group">
										  <span>
											<label><fmt:message key="label.comune"/></label>
											<em>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizcomune.comune}</em>
											<spring-form:input id="autCollcomsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizcomuneSubentro.descrizioneEstesa" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autCollcomsub_hidden${authIdx.index }')" size="30" onkeydown="javascript:return searchAll(this,event)" />
											<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="autCollcomsub_hidden${authIdx.index }" idInput="autCollcomsub_id${authIdx.index }" inputTitleKey="label.ricerca_comune"/>  
											<spring-form:hidden id="autCollcomsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizcomuneSubentro.codicecomune"  />
										   </span>	
										  <span>
											<label><fmt:message key="label.registro"/></label>
												<em>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione}</em>
												<script type="text/javascript">
													function setHiddenFieldregistroAutColl${authIdx.index}(inputField,listItem){
														var codReg = listItem.id;
														var regAuto = listItem.name;
														var autNumFieldId = 'autoriznumAutColl_id${authIdx.index}';
														$('autCollregsub_id${authIdx.index}').value = inputField.value;
														$('autCollregsub_hidden${authIdx.index}').value = codReg;
														$('autCollregsub_id${authIdx.index}_choices').fade();
														enableDisableNumAutField(autNumFieldId,'', regAuto);
													}	
												</script>
												<spring-form:input id="autCollregsub_id${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizregistroSubentro.trDescrizione" cssClass="searchbox" cssStyle="padding-left:18px;" onchange="checkValue(this,'autCollregsub_hidden${authIdx.index }');resetNumAutField('autoriznumAutColl_id${authIdx.index}','autCollregsub_hidden${authIdx.index }','${curr_auth.autorizzazioneCollegataHelper.registroAutomatico}');" size="30" onkeydown="javascript:return searchAll(this,event)" />
												<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="setHiddenFieldregistroAutColl${authIdx.index}" idHidden="autCollregsub_hidden${authIdx.index }" idInput="autCollregsub_id${authIdx.index }" inputTitleKey="label.ricerca_tipo_registro"/>  
												<spring-form:hidden id="autCollregsub_hidden${authIdx.index }" path="listAutDaSubentrare[${authIdx.index}].autorizzazioneCollegataHelper.autorizregistroSubentro.id.codice"  />
											</span>
									</div>									
									<div class="form-group">									
										<span>
										<label><fmt:message key="label.istanza"/></label>
											<em><c:out value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.istanza.numeroistanza}" default="-" /></em>
										</span>
										<span>
										<label><fmt:message key="label.concessione_titolare"/></label>
											<em>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.anagrafe.descrizioneRichiedente}</em>
										</span>
										<span>
										<c:if test="${ not empty curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.occupante.id.codice }">
											<label><fmt:message key="mercatid.label.occupante"/></label>
												<em>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.occupante.descrizioneRichiedente}</em>
											</span>
										</c:if>
									</div>
							</fieldset>	
							</div>
						</c:if>										
						</fieldset>
					</c:forEach>
			</spring-form:form>
		</div>
	</div>
	<div class="form-buttons">
		<a class="btn btn-primary" href="javascript:insertSubentri()"><fmt:message key="button.subentro" /></a>
		<a class="btn btn-primary" href="javascript:doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }&return_to=addToList','');"><fmt:message key="button.aggiungi" /></a>
		<a class="btn btn-primary" href="javascript:doSubmit('removeFromList.htm','');"><fmt:message key="button.rimuovi" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }&resetAttrs=true','');"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>