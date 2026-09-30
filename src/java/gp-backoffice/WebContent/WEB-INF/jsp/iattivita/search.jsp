<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_attivita" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.gestione_attivita" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include> 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../iattivita/search" />
	</jsp:include>
	<div id="subcontent">	
		<spring-form:form commandName="iattivitaCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="iattivitaCommand" />
		    </jsp:include>
		    <input type="hidden" id="chiave_ricerca" value="iattivita_search" />
			<input type="hidden" id="nome_form_ricerca" value="iattivitaCommand" />
		    <jsp:include page="../includes/ricerche.jsp">
		    	<jsp:param name="ricerca_attivita" value="1" />
		    	<jsp:param name="ricerca_selezionata" value="${iattivitaCommand.codiceRicerca}" />
		    </jsp:include>
	    	<table width="100%">
				<tr id="elementIdBeforeCombo">
					<td width="15%"></td>
					<td colspan="3"></td>
				</tr>
				<jsp:include page="../includes/comboComuni.jsp">
					<jsp:param name="mostraTutti" value="true" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="attivitaFilter.comune" />
					<jsp:param name="colspan" value="4" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>	    	
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_dell_attivita"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.id_attivita" />
					</td>
					<td colspan="3">
						<spring-form:input id="codice_id" path="attivitaFilter.codiceAttivita" size="5" />
						<spring-form:errors path="attivitaFilter.codiceAttivita" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${OSSERVATORIO_REGIONALE eq true }">
					<tr>
						<td>
							Codice Osservatorio Regionale
						</td>
						<td colspan="3">
							<spring-form:input id="codiceOsservatorio_id" path="attivitaFilter.codiceOsservatorio" size="10" />
							<spring-form:errors path="attivitaFilter.codiceOsservatorio" cssClass="error"/>
						</td>
					</tr>
				</c:if>		
				<tr>
					<td>
						<fmt:message key="label.denominazione_dell_attivita" />
					</td>
					<td colspan="3">
						<spring-form:input id="denominazione_id" path="attivitaFilter.denominazioneAttivita" size="70" />
						<spring-form:errors path="attivitaFilter.denominazioneAttivita" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.attiva" />
					</td>
					<td class="inline-ui-cell" style="min-width: 220px;">
					     <c:choose>
						    <c:when test="${iattivitaCommand.attivitaFilter.attiva eq true}">
						       <c:set var="valueA1" value="selected" scope="page"></c:set>
						    </c:when>
						    <c:when test="${iattivitaCommand.attivitaFilter.attiva eq false}">
						       <c:set var="valueA0" value="selected" scope="page"></c:set>
						    </c:when>
						    <c:otherwise>
						        <c:set var="valueA" value="selected" scope="page"></c:set>
						    </c:otherwise>
						</c:choose>
						<spring-form:select id="attiva_id" path="attivitaFilter.attiva">
						     <option value=""  ${valueA}></option>
						     <option value="1" ${valueA1}><fmt:message key="label.si"/></option>
						     <option value="0" ${valueA0}><fmt:message key="label.no"/></option>
						    <%-- 
						    <spring-form:option value=""></spring-form:option>
						    <spring-form:option value="true"><fmt:message key="label.si"/></spring-form:option>
							<spring-form:option value="false"><fmt:message key="label.no"/></spring-form:option>
							--%>
						</spring-form:select>
					</td>
					<td class="inline-ui-cell">
						<fmt:message key="label.operante" />
					</td>
					<td class="inline-ui-cell">
						<c:choose>
						    <c:when test="${iattivitaCommand.attivitaFilter.operante eq true}">
						       <c:set var="valueO1" value="selected" scope="page"></c:set>
						    </c:when>
						    <c:when test="${iattivitaCommand.attivitaFilter.operante eq false}">
						       <c:set var="valueO0" value="selected" scope="page"></c:set>
						    </c:when>
						    <c:otherwise>
						        <c:set var="valueO" value="selected" scope="page"></c:set>
						    </c:otherwise>
						</c:choose>
						 <spring-form:select id="operante_id" path="attivitaFilter.operante">
						     <option value=""  ${valueO}></option>
						     <option value="1" ${valueO1}><fmt:message key="label.si"/></option>
						     <option value="0" ${valueO0}><fmt:message key="label.no"/></option>
						    <%-- 
						    <spring-form:option value="" ></spring-form:option>
						    <spring-form:option value="0"><fmt:message key="label.si"/></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.no"/></spring-form:option>
							
							--%>
						</spring-form:select>
					</td>					
				</tr>
				<tr>
					<td>
						<fmt:message key="label.modulo_software" />
					</td>
					<td colspan="3">
						<spring-form:select id="modulosoftware_id" path="attivitaFilter.software.codice" onchange="gestDiv(this);">
						    <c:if test="${empty vert_iattivita_grupposoftware && isFunzioneDiUtility eq false  }">
						    <spring-form:option value=""></spring-form:option>
						    </c:if>
						    <spring-form:options items="${softwares}" itemLabel="descrizione" itemValue="codice"/>							
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.tipologia_attivita" />
					</td>
					<td colspan="3">
					<script type="text/javascript">
						
							function filterTipologieBySoftware(element, entry) {
								
								var software = getValoreDellaSelect($('modulosoftware_id'));
								return entry + "&paramSoftware=" + software+"&ts_=" + Date();								
							}
					</script>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="foarjstepstestata_id" />				
							<jsp:param name="propertyPath" value="attivitaFilter.attivitaTipologie" />			
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.attivitaTipologie.descrizione" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.attivitaTipologie.id.codice" />
							<jsp:param name="autocompleterAjax" value="../iattivitatipologie/ajaxFindIAttivitaTipologie.htm" />
							<jsp:param name="titleKey" value="label.ricerca_tipologieattivita" />
							<jsp:param name="ajaxCallBack" value="filterTipologieBySoftware" />
						</jsp:include>		
					</td>
				</tr>
				
				<tr id="id_progetto_table">
					<td valign="top"><fmt:message key="label.alberoproc" /></td>
					<td colspan="3" style="min-width: 850px;">
						<jsp:include page="../includes/searchAlberoProc.jsp">
							<jsp:param name="propertyPath" value="albero" />								
							<jsp:param name="pathPropertyDescription" value="albero.vwAlberoproc.scDescrizione" />
							<jsp:param name="pathPropertyCode" value="albero.id.codice" />
							<jsp:param name="isSelectLeafDisable" value="true" />
							<jsp:param name="isSelectNodoPadre" value="true" />
						</jsp:include>
						<spring-form:checkbox id="checkIntervento_id"  path="attivitaFilter.checkIntervento" value="1" />
						<init:help idHelp="help_help_intervento" textKey="help.ricerca_su_qualsiasi_istanza"/>
					</td>
				</tr>
				
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldTipoMovimento')}">
					<tr id="id_progetto_table">
						<td><fmt:message key="label.tipomovimento" /></td>
						<td colspan="3">
							<jsp:include page="../includes/tipimovimentosearch.jsp" >
								<jsp:param name="idElemento" value="tipoMovimentoInputId" />
								<jsp:param name="pathTipomovimento" value="attivitaFilter.tipimovimento" />
								<jsp:param name="includiDisabilitate" value="true" />
							</jsp:include>					
						</td>
					</tr>
				</c:if>
				<c:if test="${isSettoriVisible eq true }">
				<tr id="id_progetto_table">
					<td>
						<fmt:message key="label.tipo_informazione" />
					</td>
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="settori" />					
							<jsp:param name="propertyPath" value="attivitaFilter.tipoInformazione" />	
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.tipoInformazione.settore" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.tipoInformazione.id.codicesettore" />
							<jsp:param name="autocompleterAjax" value="findSettori.htm" />
							<jsp:param name="titleKey" value="label.ricerca_settori" />
						</jsp:include>
					</td>
				</tr>
				</c:if>
				<c:if test="${isAttivitaVisible eq true }">
				<tr id="id_progetto_table">
					<td>
						<fmt:message key="label.dettaglio_informazione" />
					</td>
					<td colspan="3">
						<script type="text/javascript">
							function filtertiposettore(element, entry) {
								if(document.getElementById("settori_hidden")){	
									return entry + "&codicesettore=" + document.getElementById("settori_hidden").value;
								}else{
									return entry;
								}
							}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="attivita" />			
							<jsp:param name="propertyPath" value="attivitaFilter.dettaglioInformazione" />			
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.dettaglioInformazione.istat" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.dettaglioInformazione.id.codiceistat" />
							<jsp:param name="autocompleterAjax" value="findAttivita.htm" />
							<jsp:param name="titleKey" value="label.ricerca_attivita" />
							<jsp:param name="ajaxCallBack" value="filtertiposettore" />
						</jsp:include>						
					</td>
				</tr>
				</c:if>
				<c:if test="${isArchiviopraticheVisible eq true }">
				<tr id="id_progetto_table">
					<td><fmt:message key="label.archivio_pratiche" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="archiviopratiche_id" />		
							<jsp:param name="propertyPath" value="attivitaFilter.tipiarchivioistanze" />				
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.tipiarchivioistanze.archivio" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.tipiarchivioistanze.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiarchivioistanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
						</jsp:include>
					</td>
				</tr>
				</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldPosizioneArchivio')}">
					<tr id="id_progetto_table">
						<td><fmt:message key="label.posizione_in_archivio" /></td>
						<td colspan="3">						
							<spring-form:input id="posizionearchivio_id" path="attivitaFilter.posizioneInArchivio" size="10"/>						
						</td>
					</tr>
				</c:if>
				<c:if test="${isTipologiaistanzaVisible eq true }">
				<tr id="id_progetto_table">
					<td><fmt:message key="label.tipologia_istanza" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipologia_istanze_id" />
							<jsp:param name="propertyPath" value="attivitaFilter.tipologiaistanza" />							
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.tipologiaistanza.tiDescrizione" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.tipologiaistanza.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipologiaistanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipologiaistanze" />
						</jsp:include>
					</td>
				</tr>
				</c:if>					
				<%-- ENDOPROCEDIMENTI --%>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldEndoProcedimento')}">
					<tr id="id_progetto_table">
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
						</td>
						<td colspan="3">
						
						
												
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="inventarioprocedimento_id" />
								<jsp:param name="propertyPath" value="attivitaFilter.inventarioprocedimenti" />							
								<jsp:param name="pathPropertyDescription" value="attivitaFilter.inventarioprocedimenti.procedimento" />
								<jsp:param name="pathPropertyCode" value="attivitaFilter.inventarioprocedimenti.id.codice" />
								<jsp:param name="autocompleterAjax" value="findInventarioprocedimentoAndSoftware.htm" />							
								<jsp:param name="titleKey" value="label.ricerca_inventarioprocedimento" />
								<jsp:param name="ajaxCallBack" value="filterInventarioBySoftware"/>
							</jsp:include>
						
						<input type="checkbox" id="id_flag_inventarioprocedimento" value="TT"/>						
						<init:help idHelp="help_help_inventario_tt" textKey="help.ricerca_per_software_TT"/>
							<script type="text/javascript">
							function filterInventarioBySoftware(element, entry) {
								var software = $('id_flag_inventarioprocedimento').checked?$('id_flag_inventarioprocedimento').value:'';
								if(software=='' || software!='TT'){
									software = getValoreDellaSelect($('modulosoftware_id'));
								}								
								return entry + "&codicesoftware="+software+"&ts_=" + Date();								
							}
							</script>

						</td>
					</tr>	
				</c:if>		
				<%-- ENDOPROCEDIMENTI --%>
				
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldDescrizioneLavori')}">
					<tr id="id_progetto_table">
						<td><fmt:message key="label.lavori" /></td>
						<td colspan="3">
							<spring-form:textarea id="lavori_id"  path="attivitaFilter.descrizioneLavori" cols="73" rows="2" />
						</td>
					</tr>
				</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldNote')}">
					<tr id="id_progetto_table">
						<td><fmt:message key="label.lavoriestesa" /></td>
						<td colspan="3">
							<spring-form:textarea id="lavoriestesa_id" path="attivitaFilter.note" cols="73" rows="2" />
						</td>
					</tr>
				</c:if>
				
								<%-- SCHEDE --%>
				<tr class="titoloSezione">
					<td  colspan="4"><fmt:message key="label.tab_schede"/></td>
				</tr>	
				<tr>
					<td><fmt:message key="label.scheda" /></td>
					<td colspan="3">
						
							<script type="text/javascript">
							
							function changeSchedaDinamica(inputField,listItem){
								
								var a = listItem.id;
								document.getElementById('schedaDinamica_id').value = inputField.value;
								document.getElementById('schedaDinamica_hidden').value = a;
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
							
							function addCampoDinamico(){								
								doSubmit('addCampoAScheda.htm?utilityAttivita=${param.utilityAttivita}&ts_='+Date()+'#listaCampiAncor','',document.inviodati);							
							}
							
							function removeCampoDinamico(idx){
								doSubmit('removeCampoScheda.htm?utilityAttivita=${param.utilityAttivita}&idx='+idx+'&ts_='+Date()+'#listaCampiAncor','<fmt:message key="javascript.confirm.delete" />',document.inviodati);
							}
							
							</script>
							
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="schedaDinamica" />
								<jsp:param name="propertyPath" value="attivitaFilter.schedaDinamicaFilter" />							
								<jsp:param name="pathPropertyDescription" value="attivitaFilter.schedaDinamicaFilter.scheda.descrizione" />
								<jsp:param name="pathPropertyCode" value="attivitaFilter.schedaDinamicaFilter.scheda.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm" />							
								<jsp:param name="titleKey" value="label.ricerca_schedadinamica" />
								<jsp:param name="ajaxCallBack" value="filterSchedeBySoftware"/>
								<jsp:param name="afterUpdateElement" value="changeSchedaDinamica"/>
								<jsp:param name="inputIdOnChange" value="resetSchedaValue(this,'schedaDinamica_hidden')" />
							</jsp:include>
						
							<input type="checkbox" id="id_flag_schededinamiche" value="TT"/>						
							<init:help idHelp="help_schede_tt" textKey="help.ricerca_per_software_TT"/>
							<fmt:message key="label.ricerca_schedadinamica.non_ha_effetto_su_filtro" />	
						</td>
				    </tr>							
					
					<tr>
						<td colspan="4">
						
						<a name="listaCampiAncor"> </a>
						<c:if test="${not empty iattivitaCommand.attivitaFilter.schedaDinamicaFilter.righe}">
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
								<c:forEach items="${iattivitaCommand.attivitaFilter.schedaDinamicaFilter.righe }" var="riga_var" varStatus="rigaStatus">
					
								<tr>								
									<td>
									
										
										<spring:bind path="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].andOr">
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
										<spring:bind path="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].parentesiSx">
											<select name="${status.expression}">
												<option value=""></option>
												<option value="(" <c:if test="${ status.value eq '('}"> selected </c:if> >(</option>
											</select>											
										</spring:bind>
									</td>
									<td>
									<c:set var="nomeElementoValore" value="" />
									
									
									<spring:bind path="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].valore">
										<c:set var="nomeElementoValore" value="${status.expression}" />
										<input type="hidden" id="hidden_fld_id_${rigaStatus.index}" name="${ nomeElementoValore }_hidden_fld" value="${ status.value }"/>
									</spring:bind>
									
									<script type="text/javascript">								
									
									
									function renderCondizioni_2_${rigaStatus.index}(){
										
										var elDestinatario = jQuery("#valore_${rigaStatus.index}");
										var codiceCampo = $('auto_select_campi_${rigaStatus.index}_hidden').value;
										var valore = jQuery('#hidden_fld_id_${rigaStatus.index}').val();
										console.info(codiceCampo);
										if(codiceCampo!=''){
										
											var jqxhr = jQuery.ajax({
												url: "../ajax/ajaxRenderCampo.htm",
												  context: document.body,
												  cache: false,				
												  dataType: "html",
												  data: "codiceCampo="+codiceCampo+"&idx=${rigaStatus.index}&nomeElementoValore=" + escape('${nomeElementoValore}')+ "&valore="+escape(valore),
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
									
									function renderCondizioni${rigaStatus.index}(inputField,listItem){										
										var a = listItem.id;
										document.getElementById('auto_select_campi_${rigaStatus.index}_id').value = inputField.value;
										document.getElementById('auto_select_campi_${rigaStatus.index}_hidden').value = a;
										renderCondizioni_2_${rigaStatus.index}();
									}					


									function filterCampoByScheda${rigaStatus.index}(element, entry) {
		
										var idScheda = $('schedaDinamica_hidden').value;
										return entry + "&codiceModello="+idScheda+"&ts_=" + Date();								
									}
									

									</script>
									
									<jsp:include page="../includes/autocompletergenerico.jsp" >
										<jsp:param name="idElemento" value="auto_select_campi_${rigaStatus.index}" />
										<jsp:param name="propertyPath" value="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}]" />							
										<jsp:param name="pathPropertyDescription" value="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].campo.nomecampo" />
										<jsp:param name="pathPropertyCode" value="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].campo.id.codice" />
										<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm" />							
										<jsp:param name="titleKey" value="label.ricerca_campodinamico" />
										<jsp:param name="ajaxCallBack" value="filterCampoByScheda${rigaStatus.index}"/>
										<jsp:param name="afterUpdateElement" value="renderCondizioni${rigaStatus.index}"/>										
									</jsp:include>									
									</td>
									
									<td>
										<spring:bind path="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].tipoConfronto">
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
												<option value="<%=FieldOperationsEnum.ISEMPTY %>" <c:if test="${ status.value eq 'ISEMPTY'}"> selected </c:if> >è vuoto</option>
												<option value="<%=FieldOperationsEnum.ISNOTEMPTY %>" <c:if test="${ status.value eq 'ISNOTEMPTY'}"> selected </c:if> >non è vuoto</option>
											</select>											
										</spring:bind>
									</td>
									<td>
										<script type="text/javascript">
											jQuery(document).ready(function(){
												renderCondizioni_2_${rigaStatus.index}();
								    		});
										</script>
										<div id="valore_${rigaStatus.index}"></div>										
									</td>
									<td>
										<spring:bind path="attivitaFilter.schedaDinamicaFilter.righe[${rigaStatus.index}].parentesiDx">
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
				
						
						
						
				<%-- SCHEDE END --%>	
				
				
				
				
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_del_richiedente"/></td>
				</tr>						
				<tr>
					<td><fmt:message key="label.richiedente_soggetti_collegati" /></td>
					<td colspan="3">
						<jsp:include page="../includes/anagraficasearch.jsp" >
							<jsp:param name="idElemento" value="richiedenteIdCodice" />						
							<jsp:param name="pathAnagrafica" value="attivitaFilter.richiedente" />
							<jsp:param value="true" name="anagrafeHideFunctions"/>
						</jsp:include>						
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.cittadinanza" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="cittadinanza_id" />
							<jsp:param name="propertyPath" value="attivitaFilter.cittadinanza" />							
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.cittadinanza.cittadinanza" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.cittadinanza.codice" />
							<jsp:param name="autocompleterAjax" value="findcittadinanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_cittadinanza" />
						</jsp:include>
					</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_localizzazione"/></td>
				</tr>	
				<tr id="id_localizzazione_table">
					<td><fmt:message key="label.area" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="area_id" />				
							<jsp:param name="propertyPath" value="attivitaFilter.aree" />		
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.aree.denominazione" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.aree.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAree.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_aree" />
						</jsp:include>
					</td>
				</tr>
				<tr id="id_localizzazione_table">
					<td><fmt:message key="label.indirizzo" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="stradario_id" />
							<jsp:param name="propertyPath" value="attivitaFilter.stradario" />								
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.stradario.descrizione" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.stradario.id.codice" />
							<jsp:param name="autocompleterAjax" value="findStradario.htm" />
							<jsp:param name="titleKey" value="label.ricerca_stradario" />
						</jsp:include>
					</td>	
				</tr>
				<tr id="id_localizzazione_table">
					<td><fmt:message key="label.circoscrizione" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="stradariozone_id" />
							<jsp:param name="propertyPath" value="attivitaFilter.stradariozone" />								
							<jsp:param name="pathPropertyDescription" value="attivitaFilter.stradariozone.zona" />
							<jsp:param name="pathPropertyCode" value="attivitaFilter.stradariozone.id.codice" />
							<jsp:param name="autocompleterAjax" value="findStradariozone.htm" />
							<jsp:param name="titleKey" value="label.ricerca_stradariozone" />
						</jsp:include>
					</td>	
				</tr>		
				<tr id="id_localizzazione_table">	
					<td>
						<fmt:message key="label.civico" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="civico_id" path="attivitaFilter.civico" size="5" />
					</td>
					<td class="inline-ui-cell" style="min-width: 150px;">
						<c:if test="${isStradariocoloreVisible eq true }">
							<fmt:message key="label.colore" />
						</c:if>
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<c:if test="${isStradariocoloreVisible eq true }">
							<spring-form:select id="colore_id" path="attivitaFilter.stradariocolore.id.codicecolore">
								<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
							    <spring-form:options items="${stradariocoloris}" itemValue="id.codicecolore" itemLabel="colore"/>
							</spring-form:select>
							&nbsp;
						</c:if>
					</td>																	
				</tr>
				
				<tr id="id_localizzazione_table" >
					<td>
						<fmt:message key="label.esponente" />
					</td>
					<td colspan="3">						
						<spring-form:input id="esponente_id" path="attivitaFilter.esponente" 
							size="5" />
					</td>	
				</tr>
				<tr id="id_localizzazione_table">
					<td>
						<fmt:message key="label.scala" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="scala_id" path="attivitaFilter.scala" 
							size="5" />
					</td>
					<td class="inline-ui-cell" style="min-width: 150px;">
						<fmt:message key="label.piano" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="piano_id" path="attivitaFilter.piano" 
							size="5" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" >
					<td>
						<fmt:message key="label.interno" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="interno_id" path="attivitaFilter.interno" 
							size="5" />						
					</td>
					<td class="inline-ui-cell" style="min-width: 150px;">
						<fmt:message key="label.esponente_interno" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="esponente_interno_id" path="attivitaFilter.esponenteinterno" 
							size="5" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" >
					<td>
						<fmt:message key="label.fabbricato" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="fabbricato_id" path="attivitaFilter.fabbricato" 
							size="5" />
					</td>
					<td class="inline-ui-cell" style="min-width: 150px;">
						<fmt:message key="label.frazione" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="frazione_id" path="attivitaFilter.frazione" 
							size="5" />
					</td>
				</tr>
				<tr id="id_localizzazione_table" >
					<td>
						<fmt:message key="label.cap" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="caps_id" path="attivitaFilter.cap" 
							size="5" />
					</td>
					<td class="inline-ui-cell" style="min-width: 150px;">
						<fmt:message key="label.quartiere" />
					</td>
					<td class="inline-ui-cell" style="min-width: 200px;">
						<spring-form:input id="quartiere_id" path="attivitaFilter.quartiere" 
							size="5" />
					</td>
				</tr>		
				
				<tr>
					<td>
						<fmt:message key="label.visualizza_dettaglio_attivita" />					
					</td>
					<td colspan="7">
						<input type="checkbox" id="CONF_UTENTE_LISTATTIVITA_VISATTIVITA_id" 
						    onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_LISTATTIVITA_VISATTIVITA%>',this)" ${CONF_UTENTE_LISTATTIVITA_VISATTIVITA_CHECKED}/>
					</td>
				</tr>
				
				
	    	</table>
	    </spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:searchAttivita();"><fmt:message key="button.search" /></a></li>
			<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>						
		</ul>
	</div>
	<script type="text/javascript">
	
	var executed = false;
	
	function salvaPreferenza(nomeparametro, objchk){
		var valore = "0";	
		if(objchk.checked==true){
			valore="1";	
		}
		saveUserPreference(nomeparametro, valore);
	}
	
	jQuery("*").keypress(function(e) {
	  	  	var code = e.keyCode ? e.keyCode : e.which;
				if(code.toString() == 13) {
				if(!executed){
					executed=true;
						searchAttivita();	   
				}
			}
		});
	
	function ajaxHistorySet(url){
		
		var jhqr = jQuery.ajax({
			  url: '../history/ajaxSet.htm?ReturnTo='+url,
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  success: function(data) { 				   
				} 
			});
	}
	
	function searchAttivita(){
		<c:if test="${empty iattivitaCommand.istanza.id.codice}">
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);
		</c:if>	
		setTimeout("doSubmit('list.htm?isFunzioneDiUtility=${isFunzioneDiUtility}&codiceIstanza=${codiceIstanza}','',document.inviodati)",15);	
	}
	
	function gestDiv(obj){
		var softwareSelezionato = getSelectTextAndValue(obj);
		var visible = false;
		if(softwareSelezionato[0] != ''){
			visibile = showHideElements('id_progetto_table', 'tr');
			if(!visible){
				showHideElements('id_progetto_table', 'tr');
			}
		}else{
			visibile = showHideElements('id_progetto_table', 'tr');
			if(visible){
				showHideElements('id_progetto_table', 'tr');
			}
		}
		
	}
	
	gestDiv($('modulosoftware_id'));
	
	</script>
</body>
</html>