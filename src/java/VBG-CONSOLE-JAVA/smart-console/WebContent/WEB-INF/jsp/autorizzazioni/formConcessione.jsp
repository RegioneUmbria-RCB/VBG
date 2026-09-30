<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
	<fmt:message key="label.nuova_concessione" />
</c:if> <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
	<fmt:message key="label.dettaglio_concessione" />
</c:if></title>
</head>
<body>
<script type="text/javascript">
		var qstring = "codiceIstanza=${param.codiceIstanza}";
</script>
<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
	<jsp:include page="../includes/history.jsp">
   		<jsp:param name="path" value="../autorizzazioni/createConcessione" />
   	</jsp:include>	
</c:if>   
<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
	<jsp:include page="../includes/history.jsp">
   		<jsp:param name="path" value="../autorizzazioni/viewConcessione" />
   	</jsp:include>	
</c:if>
<span class="titoloPagina"> <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
	<fmt:message key="label.nuova_concessione" />
</c:if> <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
	<fmt:message key="label.dettaglio_concessione" />
</c:if> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${param.codiceIstanza}</c:param>
</c:import>
<br class="clear" />	
<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.DELETED}">
		<div id="subcontent">
			<spring-form:form commandName="concessioniCommand" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
					<jsp:param name="commandName" value="concessioniCommand" />
				</jsp:include>
			</spring-form:form>				
		</div>
</c:if>
<c:if test="${concessioniCommand.displayMode ne concessioniCommand.displayConstants.DELETED}">
		<div id="subcontent"><spring-form:form commandName="concessioniCommand" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="concessioniCommand" />
		</jsp:include>
		<table width="100%">
			<tr class="titoloSezione">
				<td colspan="4"><fmt:message key="label.concessione_dati_della_concessione" /></td>
			</tr>
			<%
			    String mercatiUso = "";
				String mercatiPosteggio = "display:none;";
			%>
			<tr>
					<td>
						<fmt:message key="label.registro" />
						<a name="autorizzazione"></a>
					</td>
					<td colspan="3">
					
						<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
					 	
							<script type="text/javascript">
								function updateRegistroConcessione(inputField,listItem){
									var a = listItem.id;
									document.getElementById('registro_concessione_id').value = inputField.value;
									document.getElementById('registro_concessione_hidden').value = a;
									$('registro_concessione_id_choices').fade();
									if(document.getElementById('registro_concessione_hidden').value!=''){
										//doSubmit('updateRegistroConecessione.htm?codice=${concessioniCommand.entity.id.codice}&'+qstring+'&codiceRegistro=' + document.getElementById('registro_concessione_hidden').value,'',document.inviodati);
										doSubmit('updateRegistroConecessione.htm?codice=${concessioniCommand.entity.id.codice}&'+qstring+'&codiceAnagrafe='+document.getElementById('titolare_hidden').value+'&codiceRegistro=' + document.getElementById('registro_concessione_hidden').value,'',document.inviodati);
									}
								}
							</script>
							<spring-form:input id="registro_concessione_id" path="autorizzazione.tipologiaregistro.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registro_concessione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
							<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="updateRegistroConcessione"  idHidden="registro_concessione_hidden"  idInput="registro_concessione_id" inputTitleKey="label.ricerca_tipo_registro"/>
							<spring-form:errors	path="autorizzazione.tipologiaregistro.trDescrizione" cssClass="error"	/>
							<spring-form:hidden id="registro_concessione_hidden" path="autorizzazione.tipologiaregistro.id.codice"  />
						<%--
						</c:if>
						<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
							<spring-form:input id="registro_id" path="entity.tipologiaregistro.trDescrizione" disabled="true" size="70"/>
							<spring-form:errors	path="entity.tipologiaregistro.trDescrizione" cssClass="error"	/>
							<spring-form:hidden id="registro_hidden" path="entity.tipologiaregistro.id.codice"  />
						</c:if>
						 --%>
						 </c:if>
						 <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
						 	<input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizione}"/>	
						</c:if>
					</td>
				</tr>
			
			
			
			
			
			<%-- 	
			<tr>
				<td width="10%"><fmt:message key="label.registro" /></td>
				<td colspan="3">
					<input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizione}"/>
				</td>
			</tr>
			--%>	
	<c:choose>
		<c:when test="${concessioniCommand.subentroPresente eq true}">
				<%-- 
					QUESTO È IL CASO IN CUI VISUALIZZO UN'AUTORIZZAZIONE CHE È STATA SUBENTRATA E DEVO VISUALIZZARE 
					I DATI DEL SUBENTRO E NON QUELLI DELL'AUTORIZZAZIONE ATTIVA
				--%>
					<tr>
						<td width="10%"><fmt:message key="label.concessione_numero_concessione" /></td>
						<td><spring-form:input id="entity_id" path="subentro.autoriznumero" size="10" disabled="true" /></td>
						<td colspan="2">
							<fmt:message key="label.concessione_data_rilascio_concessione" />
							<spring-form:input id="autorizdata_id" path="subentro.autorizdata" size="10" disabled="true" />
						</td>
					</tr>
					<%--
					<tr>
						<td><fmt:message key="label.concessione_autorizzata_da" /></td>
						<td><spring-form:input id="responsabile_id" size="50" path="subentro.autorizresponsabile" disabled="true"/>
						
						</td>
						<td><fmt:message key="label.concessione_autorizzata_il" /></td>
							<td><spring-form:input id="autorizdataregistr_id" path="subentro.autorizdataregistr" size="10" disabled="true"/> 
							</td>
					</tr>
					 --%>
					<tr>
						<td><fmt:message key="label.concessione_titolare" /></td>
						<td colspan="3">
							<spring-form:input id="titolare_id" disabled="true" size="70" path="subentro.anagrafe.descrizioneRichiedente"/> 
							<spring-form:hidden id="titolare_hidden" path="subentro.anagrafe.id.codice" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.concessione_tipologia_concessione" /></td>
						<td colspan="3">
							<spring-form:input disabled="true" id="selectTipoConcessione" size="50" path="entity.concessionitipi.descrizione" />
							<spring-form:hidden id="hidden_tipoconcessione" path="entity.concessionitipi.tipoconcessione"/>
						</td>
					</tr>
					<tr id="tipologiaStagionaleDiv">
						<td><fmt:message key="label.concessione_tipologia_stagionale_da" /></td>
						<td>
							<spring-form:input id="stagionaleda_id" path="entity.stagionaledaTransient" size="6" maxlength="5" disabled="true"/> 
						</td>
						<td colspan="2"><fmt:message key="label.concessione_tipologia_stagionale_a" /> 
							<spring-form:input id="stagionalea_id" path="entity.stagionaleaTransient" size="6" maxlength="5"  disabled="true" />
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.concessione_scadenza" /></td>
						<td colspan="3">
							<spring-form:input id="scadenza_id" path="subentro.datascadenza" size="10" disabled="true" /> 
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.concessione_causale_acquisizione" /></td>
						<td colspan="3">	
							<spring-form:input id="selectCausaleAcquisizione" path="subentro.concessionicausaliByFkAutsubConccausAcq.descrizione" size="50" disabled="true"/>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.causale_cessazione" /></td>
						<td><spring-form:input path="subentro.concessionicausaliByFkAutsubConccausCess.descrizione" size="50" disabled="true"/></td>
						<td><fmt:message key="label.data_cessazione"/></td>
						<td><spring-form:input path="subentro.dataCessazione" size="10" disabled="true"/></td>									
					</tr>
					<tr class="titoloSezione">
						<td colspan="4"><fmt:message key="label.concessione_dati_della_manifestazione" /></td>
					</tr>
					<tr id="mercati">
						<td><fmt:message key="label.manifestazione" /></td>
						<td colspan="3">
							<spring-form:input disabled="true" id="mercati_id" path="entity.mercati.descrizione" size="70" />
							<spring-form:hidden id="mercati_hidden" path="entity.mercati.id.codice" />
						</td>
					</tr>
					<tr id="mercatiUso" style="<%=mercatiUso%>">
						<td><fmt:message key="label.mercati_uso" /></td>
						<td colspan="3">
							<spring-form:input path="entity.mercatiUso.descrizione" disabled="true" size="35"/>	
							<spring-form:hidden path="entity.mercatiUso.id.codice" />
						</td>
					</tr>
					<tr id="mercatiPosteggio">
						<td><fmt:message key="label.posteggio" /></td>
						<td colspan="3">
							<spring-form:input path="entity.mercatiD.codiceposteggio" disabled="true"/>	
							<spring-form:hidden path="entity.mercatiD.id.codice" />
						</td>
					</tr>
					<c:if test="${concessioniCommand.inserisciAutorizzazione eq true}">
						<tr class="titoloSezione">
							<td colspan="4"><spring-form:hidden path="inserisciAutorizzazione" /> 
								<label for="inserisciAutorizzazione_id">
									<fmt:message key="label.concessione_dati_della_autorizzazione_inserisci_autorizzazione" />
								</label>
							</td>
						</tr>
					</c:if>	
					<tr id="autorizzazionetitle_div" class="titoloSezione">
						<td colspan="4"><fmt:message key="label.concessione_dati_della_autorizzazione" /></td>
					</tr>
					<tr id="autorizzazione_div">
						<td><fmt:message key="label.autorizzazione_registro" /></td>
						<td colspan="3"><spring-form:input id="registro_id" path="subentroAutCollegata.tipologiaregistro.trDescrizione" size="70" disabled="true"/> </td>
					</tr>
					<tr id="autorizzazione_div2">
						<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
						<td>
							<spring-form:input id="concessioneNumeroAutorizzazione_id" disabled="true" path="subentroAutCollegata.autoriznumero" size="10" /> 
							<spring-form:errors	path="subentroAutCollegata.autoriznumero" cssClass="error" />
						</td>
						<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
						<td><spring-form:input id="autorizdata2_id" readonly="true" path="subentroAutCollegata.autorizdata" size="10" disabled="true"/></td>
					</tr>
					<tr id="autorizzazione_div4">
						<td>
							<fmt:message key="label.concessione_autorizzata_da" />
						</td>
						<td>
							<spring-form:input id="responsabile_id" size="50" path="subentroAutCollegata.autorizresponsabile" disabled="true"/>
						</td>
						<td><fmt:message key="label.concessione_autorizzata_il" /></td>
						<td>
							<spring-form:input id="autorizdataregistr_id" path="subentroAutCollegata.autorizdataregistr" size="10" disabled="true"/> 
						</td>
					</tr>	
		</c:when>
		<c:otherwise>		
			<%-- QUESTO È IL CASO IN CUI VISUALIZZO UN'AUTORIZZAZIONE ATTIVA --%>
				<c:if test="${concessioniCommand.registroConcessioneProtocollo eq false}">
					<tr>
						<td width="10%"><fmt:message key="label.concessione_numero_concessione" /></td>
						<td>
							<spring-form:input id="entity_id" path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" /> 
							<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />
						</td>
						<td>
							<fmt:message key="label.concessione_data_rilascio_concessione" />
						</td>
						<td>	
							<spring-form:input id="autorizdata_id" path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" onblur="isValidDate(this,true);" />
						 	<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdata_id" idInput="autorizdata_id" textKey="label.calendar" /> 
						 	<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error"	/>
						</td>
					</tr>
				</c:if>
				<c:if test="${concessioniCommand.registroConcessioneProtocollo eq true}">
					<tr>
						<td colspan="4"><fmt:message key="label.concessione_estremi_da_protocollo" /></td>
					</tr>
					<tr>
						<td><fmt:message key="label.concessione_numero_concessione" /></td>
						<td>
							${ concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.autoriznumero }
							<%--
								<input type="text" name="_entity.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" disabled="disabled" value="" />
							 --%> 
							<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />
						</td>
						<td><fmt:message key="label.concessione_data_rilascio_concessione" /></td>
						<td>
							<c:if test="${not empty concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.autorizdata }">
								<fmt:formatDate value="${ concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.autorizdata }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
							</c:if>
							<%--	
								<input type="text" name="_entity.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" disabled="disabled" value=""/>
							 --%> 
							<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error" />
						</td>
					</tr>
				</c:if>
				<%--
				<tr>
					<td><fmt:message key="label.concessione_autorizzata_da" /></td>
					<td><spring-form:input id="responsabile_id" size="50" path="entity.autorizzazioniByFkAutconcAutatt.autorizresponsabile" />
					<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autorizresponsabile" cssClass="error" />
					</td>
					<td><fmt:message key="label.concessione_autorizzata_il" /></td>
						<td><spring-form:input id="autorizdataregistr_id" path="entity.autorizzazioniByFkAutconcAutatt.autorizdataregistr" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdataregistr_id" idInput="autorizdataregistr_id" textKey="label.calendar" /> 
						<spring-form:errors	path="entity.autorizzazioniByFkAutconcAutatt.autorizdataregistr" cssClass="error"/></td>
				</tr>
				 --%>
				<tr>
					<td><fmt:message key="label.concessione_titolare" /></td>
					<td colspan="3">
						<spring-form:input id="titolare_id" size="70" path="entity.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
						<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.anagrafe" cssClass="error" /> <spring-form:hidden id="titolare_hidden" path="entity.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_tipologia_concessione" /></td>
					<td colspan="3">
						<spring-form:select id="selectTipoConcessione" path="entity.concessionitipi.tipoconcessione" onchange="showTipologia(this);">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${concessionitipis}" itemLabel="descrizione" itemValue="tipoconcessione"></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="entity.concessionitipi" cssClass="error" />
					</td>
				</tr>
				<tr id="tipologiaStagionaleDiv">
					<td><fmt:message key="label.concessione_tipologia_stagionale_da" /></td>
					<td>
						<spring-form:input id="stagionaleda_id" path="entity.stagionaledaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" /> 
						<spring-form:errors	path="entity.stagionaleda" cssClass="error"	/></td>
					<td>
						<fmt:message key="label.concessione_tipologia_stagionale_a" /> 
					</td>
					<td>
						<spring-form:input id="stagionalea_id" path="entity.stagionaleaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" />
						<spring-form:errors path="entity.stagionalea" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_scadenza" /></td>
					<td colspan="3">
						<spring-form:input id="scadenza_id" path="entity.autorizzazioniByFkAutconcAutatt.datascadenza" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_scadenza_id" idInput="scadenza_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.datascadenza" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_causale_acquisizione" /></td>
					<td colspan="3">
						<spring-form:select id="selectCausaleAcquisizione" path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice">
							<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
							<spring-form:options items="${concessionicausalisAcq}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><label for="flag_attiva_id"><fmt:message key="label.concessione_attiva" /></label></td>
					<td colspan="3"><spring-form:checkbox path="entity.autorizzazioniByFkAutconcAutatt.flagAttiva" id="flag_attiva_id" onclick="gestFlagAttiva();"/></td>
				</tr>
				<tr id="dati_cessazione_id">
					<td><fmt:message key="label.causale_cessazione" /></td>
					<td>
						<spring-form:select id="selectCausaleCessazione" path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.id.codice">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${concessionicausalisCess}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.id.codice" cssClass="error" />
					</td>
					<td><fmt:message key="label.data_cessazione" /></td>
					<td>
						<spring-form:input id="cessazione_id" path="entity.autorizzazioniByFkAutconcAutatt.dataCessazione" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.dataCessazione" cssClass="error" />
					</td>			
				</tr>
				
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.concessione_dati_della_manifestazione" /></td>
				</tr>
				<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
					<!-- L'albero del procedimento scelto ha un mercato configurato -->
					<c:if test="${concessioniCommand.isManifestazioneDaProcedimentoPresente}">
					    <input type="hidden" value="${concessioniCommand.isManifestazioneDaProcedimentoPresente}" name="concessioniCommand.isManifestazioneDaProcedimentoPresente"></input>
						<tr id="mercati">
							<td><fmt:message key="label.manifestazione" /></td>
							<td colspan="3">
								<spring-form:input readonly="true" id="mercati_id" path="entity.mercati.descrizione" size="70"	/> 
								<spring-form:hidden id="mercati_hidden" path="entity.mercati.id.codice" />
								<a title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />" href="javascript:composizioneMercato(document.getElementById('mercati_hidden').value,document.getElementById('mercatiUso_hidden').value);">
									<img align="bottom" src="${pageContext.request.contextPath}/images/search.gif" title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />"/>
								</a>
							</td>
						</tr>
						
						<tr id="mercatiUso" style="<%=mercatiUso%>">
							<td><fmt:message key="label.mercati_uso" /></td>
							<td colspan="3">
								<spring-form:input path="entity.mercatiUso.descrizione" readonly="true" size="35"/>	
								<spring-form:hidden path="entity.mercatiUso.id.codice" id="mercatiUso_hidden"/>
							</td>
						</tr>
						
						<tr id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
							<td><fmt:message key="label.posteggio" /></td>
							<td colspan="3">
								<div style="float: left;">
								<spring-form:select id="selectPosteggio" path="entity.mercatiD.id.codice" onchange="mostraModificaPosteggio(this);" />	
								</div>
								<div style="float: left;">
								<a class="addColumn" href="javascript:nuovoPosteggio();" title="<fmt:message key="label.aggiungi_posteggio" />">
									<label><fmt:message key="label.add.record.image" /></label>
								</a>
								<a id="posteggioDettaglio_id" style="display: none;" class="dettaglioColumn" href="javascript:modificaPosteggio();"  title="<fmt:message key="label.edit.record" /> <fmt:message key="label.posteggio" />">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								</div>
								<spring-form:errors path="entity.mercatiD.id.codice" cssClass="error" />
							</td>
						</tr>
					</c:if>
					<!-- L'albero del procedimento non scelto ha un mercato configurato, c'è la possibilità di scegliere tra quelli config. -->
					<c:if test="${!concessioniCommand.isManifestazioneDaProcedimentoPresente}">
						<tr id="mercati">
							<td><fmt:message key="label.manifestazione" /></td>
							<td colspan="3">
							<%-- 
							
								<spring-form:input readonly="true" id="mercati_id" path="entity.mercati.descrizione" size="70"	/> 
								<spring-form:hidden id="mercati_hidden" path="entity.mercati.id.codice" />
								<a title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />" href="javascript:composizioneMercato(document.getElementById('mercati_hidden').value,document.getElementById('mercatiUso_hidden').value);">
									<img align="bottom" src="${pageContext.request.contextPath}/images/search.gif" title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />"/>
								</a>
							--%>	
								
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="mercati" />		
								<jsp:param name="propertyPath" value="entity.mercati" />				
								<jsp:param name="pathPropertyDescription" value="entity.mercati.descrizione" />
								<jsp:param name="pathPropertyCode" value="entity.mercati.id.codice" />
								<jsp:param name="autocompleterAjax" value="findMercati.htm" />	
								<jsp:param name="titleKey" value="label.manifestazione" />
							</jsp:include>	
								
								
								
							</td>
						</tr>
						 
						<tr id="mercatiUso" style="<%=mercatiUso%>">
							<td><fmt:message key="label.mercati_uso" /></td>
							<td colspan="3">
								<script type="text/javascript">
									function filtermercato(element, entry) { 
										return entry + "&codiceMercato=" + document.getElementById("mercati_hidden").value;
									}
								</script>
								<script type="text/javascript">
									function setHiddenFieldmercati(inputField,listItem){
										var a = listItem.id;
										document.getElementById('mercatiUso_id').value = inputField.value;
										document.getElementById('mercatiUso_hidden').value = a;
										assegnaPosteggio();
										}
								</script>
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="mercatiUso" />		
									<jsp:param name="propertyPath" value="entity.mercatiUso" />				
									<jsp:param name="pathPropertyDescription" value="entity.mercatiUso.descrizione" />
									<jsp:param name="pathPropertyCode" value="entity.mercatiUso.id.codice" />
									<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
									<jsp:param name="ajaxCallBack" value="filtermercato"/>
									<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati"/>
									<jsp:param name="titleKey" value="label.giorno" />
								</jsp:include>		
							</td>
							
						<%--
							<td colspan="3">
								<spring-form:input path="entity.mercatiUso.descrizione" readonly="true"/>	
								<spring-form:hidden path="entity.mercatiUso.id.codice" id="mercatiUso_hidden"/>
							</td>
						--%>
						</tr>
						
						<tr id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
							<td><fmt:message key="label.posteggio" /></td>
							<td colspan="3">
								
								
								<div style="float: left;">
								<spring-form:select id="selectPosteggio" path="entity.mercatiD.id.codice" onchange="mostraModificaPosteggio(this);" />	
								</div>
								<div style="float: left;">
								<a class="addColumn" href="javascript:nuovoPosteggio();" title="<fmt:message key="label.aggiungi_posteggio" />">
									<label><fmt:message key="label.add.record.image" /></label>
								</a>
								<a id="posteggioDettaglio_id" style="display: none;" class="dettaglioColumn" href="javascript:modificaPosteggio();"  title="<fmt:message key="label.edit.record" /> <fmt:message key="label.posteggio" />">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								</div>
								<spring-form:errors path="entity.mercatiD.id.codice" cssClass="error" />
							</td>
						</tr>
					
					</c:if>
				</c:if>
				
				
				
				
				
				<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
				<tr id="mercati">
					<td><fmt:message key="label.manifestazione" /></td>
					<td colspan="3">
						<spring-form:input readonly="true" id="mercati_id" path="entity.mercati.descrizione" size="70"	/> 
						<spring-form:hidden id="mercati_hidden" path="entity.mercati.id.codice" />
						<a title="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />" href="javascript:composizioneMercato(document.getElementById('mercati_hidden').value,document.getElementById('mercatiUso_hidden').value);">
							<img src="${pageContext.request.contextPath}/images/search.gif" alt="<fmt:message key="label.visualizza_la_composizione_della_manifestazione" />"/>
						</a>
					</td>
				</tr>
				<tr id="mercatiUso" style="<%=mercatiUso%>">
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<spring-form:input path="entity.mercatiUso.descrizione" readonly="true" size="35"/>	
						<spring-form:hidden path="entity.mercatiUso.id.codice" id="mercatiUso_hidden" />
					</td>
				</tr>
				<tr id="mercatiPosteggio">
					<td><fmt:message key="label.posteggio" /></td>
					<td colspan="3">
						<div style="float: left;">
							<spring-form:input path="entity.mercatiD.codiceposteggio" readonly="true"/>	
							<spring-form:hidden id="posteggio_id" path="entity.mercatiD.id.codice" />
							<spring-form:errors path="entity.mercatiD.id.codice" cssClass="error" />
						</div>
						<div style="float: left;">
							<a id="posteggioDettaglio_id" class="dettaglioColumn" href="javascript:modificaPosteggio();"  title="<fmt:message key="label.edit.record" /> <fmt:message key="label.posteggio" />">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</div>
					</td>
				</tr>		
				</c:if>
			
				
				<c:set var="readonlyAutColl">false</c:set>
				<c:set var="readonlyAutCollSearchClass">searchbox</c:set>
				<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
					<c:set var="readonlyAutColl">true</c:set>
					<c:set var="readonlyAutCollSearchClass"></c:set>
				</c:if>
				<tr class="titoloSezione">
					<td colspan="4">
						<a name="autorizzazione"></a>
						<spring-form:checkbox path="inserisciAutorizzazione" id="inserisciAutorizzazione_id" onclick="visualizzaAutorizzazione();"/> 
						<label for="inserisciAutorizzazione_id"><fmt:message key="label.concessione_dati_della_autorizzazione_inserisci_autorizzazione" /></label>
					</td>
				</tr>
				<tr id="autorizzazione_cerca_title_div" class="titoloSezione">
					<td colspan="4"><fmt:message key="label.cerca_un_autorizzazione_esistente" /></td>
				</tr>
				<tr id="autorizzazione_cerca_div">
					<td><fmt:message key="label.autorizzazione" /></td>
					<td colspan="3">
						<script type="text/javascript">
							function cercaAut(inputField,listItem){
								var a = listItem.id;
								document.getElementById('cerca_aut_id').value = inputField.value;
								document.getElementById('cerca_aut_id_hidden').value = a;
								$('cerca_aut_id_choices').fade();
								
								if(document.getElementById('cerca_aut_id_hidden').value!=''){
								}
							}
						</script>					
						<spring-form:input id="cerca_aut_id" path="autorizzazioneAssociata.autoriznumero" cssClass="searchbox" onchange="checkValue(this,'cerca_aut_id_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
						<init:autocompleter methodAjax="findAutorizzazioniIstanza.htm?codiceIstanza=${param.codiceIstanza}" afterUpdateElement="cercaAut"  idHidden="cerca_aut_id_hidden"  idInput="cerca_aut_id" inputTitleKey="label.cerca_un_autorizzazione_esistente_per_istanza"/>
						<spring-form:hidden id="cerca_aut_id_hidden" path="autorizzazioneAssociata.id.codice"  />	
						<div id="functions">
							<ul>							
								<li><a href="javascript:historySet('${_urlback}','../autorizzazioni/createAutorizzazione.htm?codiceIstanza=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.id.codice }','')"><fmt:message key="button.nuova_autorizzazione" /></a></li>							
							</ul>
						</div>				
					</td>
				</tr>	
				<tr id="autorizzazionetitle_div" class="titoloSezione">
					<td colspan="4"><fmt:message key="label.concessione_dati_della_autorizzazione" /></td>
				</tr>
				<tr id="autorizzazione_div">
					<td><fmt:message key="label.autorizzazione_registro" /></td>
					<td colspan="3">
						<script type="text/javascript">
							function updateRegistro(inputField,listItem){
								var a = listItem.id;
								document.getElementById('registro_id').value = inputField.value;
								document.getElementById('registro_hidden').value = a;
								$('registro_id_choices').fade();
								if(document.getElementById('registro_hidden').value!=''){
									doSubmit('updateRegistroConcessione.htm?codice=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.id.codice}&codiceIstanza=${param.codiceIstanza}&codiceRegistro=' + document.getElementById('registro_hidden').value+'#autorizzazione','',document.inviodati);
								}
							}
						</script>
						<spring-form:input readonly="${readonlyAutColl}" id="registro_id" path="entity.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione" cssClass="${readonlyAutCollSearchClass}" onchange="checkValue(this,'registro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
						<c:if test="${readonlyAutColl eq 'false'}"> 
							<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="updateRegistro"  idHidden="registro_hidden"  idInput="registro_id" inputTitleKey="label.ricerca_tipo_registro"/>
						</c:if>
						<spring-form:errors	path="entity.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione" cssClass="error"	/>
						<spring-form:hidden id="registro_hidden" path="entity.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.id.codice"  />					
					</td>
				</tr>				
				<c:if test="${concessioniCommand.registroAutorizzazioneProtocollo eq false}">
					<tr id="autorizzazione_div2">
						<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
						<td>
							<div style="float: left;">
								<spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autorizzazioniByFkAutconcAutcoll.autoriznumero" readonly="${readonlyAutColl}"  size="10" />
								<spring-form:errors	path="entity.autorizzazioniByFkAutconcAutcoll.autoriznumero" cssClass="error" />
							</div>
							<c:if test="${readonlyAutColl eq 'true'}">
								<c:if test="${ not empty concessioniCommand.entity.autorizzazioniByFkAutconcAutcoll.id.codice }">
									<div style="float: left;">
										<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?codice=${concessioniCommand.entity.autorizzazioniByFkAutconcAutcoll.id.codice }&codiceIstanza=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${concessioniCommand.entity.autorizzazioniByFkAutconcAutcoll.transientEstremiAut}">
											<label><fmt:message key="label.edit.record.image" /></label>
										</a>
									</div>
								</c:if>
							 </c:if>							
						</td>
						<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
						<td>
							<spring-form:input id="autorizdata2_id" path="entity.autorizzazioniByFkAutconcAutcoll.autorizdata" readonly="${readonlyAutColl}" size="10" onblur="isValidDate(this,true);" />
						 	<c:if test="${readonlyAutColl eq 'false'}">
						 		<init:calendar imagePath="/images/cal.gif"	idImage="cal_autorizdata2_id" idInput="autorizdata2_id" textKey="label.calendar"/> 
						 	</c:if>
						 	<spring-form:errors path="entity.autorizzazioniByFkAutconcAutcoll.autorizdata" cssClass="error" />
						</td>
					</tr>
				</c:if>
				<c:if test="${concessioniCommand.registroAutorizzazioneProtocollo eq true}">		
					<tr id="autorizzazione_div2">
						<td colspan="4"><fmt:message key="label.autorizzazione_estremi_da_protocollo" /></td>
					</tr>
				
					<tr id="autorizzazione_div3">
						<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
						<td>
							<spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autorizzazioniByFkAutconcAutcoll.autoriznumero" size="10" disabled="true"/> 
							<spring-form:errors	path="entity.autorizzazioniByFkAutconcAutcoll.autoriznumero" cssClass="error" />
						</td>
						<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
						<td>
							<spring-form:input id="autorizdata2_id" path="entity.autorizzazioniByFkAutconcAutcoll.autorizdata" size="10" disabled="true"/>
						 	<spring-form:errors path="entity.autorizzazioniByFkAutconcAutcoll.autorizdata" cssClass="error" />
						</td>
					</tr>
				</c:if>
				<tr id="autorizzazione_div4">
					<td><fmt:message key="label.concessione_autorizzata_da" /></td>
					<td>
						<spring-form:input id="responsabile_id" size="50" readonly="${readonlyAutColl}"  path="entity.autorizzazioniByFkAutconcAutcoll.autorizresponsabile" />
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutcoll.autorizresponsabile" cssClass="error" />
					</td>
					<td><fmt:message key="label.concessione_autorizzata_il" /></td>
					<td>
						<spring-form:input id="autorizdataregistr_id" readonly="${readonlyAutColl}"  path="entity.autorizzazioniByFkAutconcAutcoll.autorizdataregistr" size="10" onblur="isValidDate(this,true);" />
						<c:if test="${readonlyAutColl eq 'false'}"> 
							<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdataregistr_id" idInput="autorizdataregistr_id" textKey="label.calendar" />
						</c:if> 
						<spring-form:errors	path="entity.autorizzazioniByFkAutconcAutcoll.autorizdataregistr" cssClass="error"/>
					</td>
				</tr>																	
			</c:otherwise>
	</c:choose>		
			
		</table>
	</spring-form:form>
	</div>
		
	<script type="text/javascript">
		var isEditmode = true;
		var posteggioSelezionato = '${concessioniCommand.entity.mercatiD.id.codice}';			
	</script>
		
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">	
		<script type="text/javascript">
			var isEditmode = false;
		</script>	
	</c:if>
	<script type="text/javascript">
	   function gestFlagAttiva(){
			var id_div_cessazione = 'dati_cessazione_id';
			var chkObj = document.getElementById('flag_attiva_id');
			if(chkObj){
				if(chkObj.checked){
					hideDiv(id_div_cessazione);
				}else{
					showDiv(id_div_cessazione);
				}
			}
		}
		gestFlagAttiva();
	
		function visualizzaAutorizzazione(){
			showHideDiv('autorizzazione_cerca_title_div');
			showHideDiv('autorizzazione_cerca_div');
			showHideDiv('autorizzazionetitle_div');
			showHideDiv('autorizzazione_div');
			showHideDiv('autorizzazione_div2');
			showHideDiv('autorizzazione_div3');
			showHideDiv('autorizzazione_div4');
		}
	
		function assegnaPosteggio(){		
			removeOptionSelected("selectPosteggio");
			var idMercato=document.getElementById("mercati_hidden").value;
			var idGiorno=document.getElementById("mercatiUso_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggiNonAssegnati.htm', {
				  method: 'post',
				  parameters: {codiceMercato: idMercato, codiceMercatiUso: idGiorno ,limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiPosteggio").appear();
					  var opts=response.split("-SEP-");
					  var itemSelected = false;
 				      $('selectPosteggio').options[$('selectPosteggio').options.length] = new Option('<fmt:message key="label.select.default" />','');
					  for(var i=0;i<((opts.length)-1); i++ ){
						  if(posteggioSelezionato == opts[i]){
							  itemSelected = true; 
						  }else{
							  itemSelected = false;
						  }
	 				      $('selectPosteggio').options[$('selectPosteggio').options.length] = new Option(opts[i+1],opts[i],false,itemSelected);
						  i++;
					  }
				    },
				  onFailure: function(transport){  
				    printResult(transport, "Errore durante la ricerca di posteggi");
				  }
			});
		}

		function mercatiUsoDisplay(){
				removeOptionSelected("selectMercatiUso");
				var idMercato=document.getElementById("mercati_hidden").value;
				new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
					  method: 'post',
					  parameters: {code: idMercato, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  $("mercatiUso").appear();
						  var opts=response.split(",");
						  for(var i=0;i<((opts.length)-1); i++ ){
							  var j=i+1;
							  $('selectMercatiUso').add(new Option(opts[j],opts[i]),null);
							  i++;
						  }
					    },
					  onFailure: function(){  }
									  
					  });
						  
			}
	
	
			function removeOptionSelected(opt)
			{
			  var elSel = document.getElementById(opt);
			  var i;
			  for (i = elSel.length - 1; i>=0; i--) {
			    if (elSel.options[i]) {
			       elSel.remove(i);
			    }
			  }
			}
	
		
			function showTipologia(obj){
					var pos=document.getElementById('selectTipoConcessione').selectedIndex;
					var valore_nascosto = '';
					if(document.getElementById('hidden_tipoconcessione')){
						valore_nascosto = document.getElementById('hidden_tipoconcessione').value;
					}
					
					if (pos>-1 || valore_nascosto!='') {
						var itemSelected = null;
						if(valore_nascosto==''){
							itemSelected = document.getElementById('selectTipoConcessione').options[pos].value;
						}else{
							itemSelected = valore_nascosto;
						}
						if(itemSelected==''){
							hideDiv('tipologiaStagionaleDiv');
							return;
						}
						var call_msg = new Ajax.Request('<%=request.getContextPath()%>/concessionitipi/ajaxIsConcessioneStagionale.htm?tipoconcessione=' + itemSelected,
						{
							method : 'post',
							onSuccess : function(transport) {
								var risposta = transport.responseText;
								showHidestagionaleDiv(risposta);
							},
							onFailure : function(transport) {
								var responseTexts = transport.responseText;
								alert(responseTexts);
							}
						});
	
			}
		}
	
		function showHidestagionaleDiv(isStagionale) {
			if (isStagionale == "true") {
				document.getElementById("tipologiaStagionaleDiv").style.display = "";
			} else {
				document.getElementById("tipologiaStagionaleDiv").style.display = "none";
			}
		}
		
		if(!isEditmode){
			if (document.getElementById("mercati_hidden")!=null && document.getElementById("mercati_hidden").value != '') {
				assegnaPosteggio();
				// mercatiUsoDisplay();
			}
		}
		
		showTipologia(document.getElementById('selectTipoConcessione'));

		showHideDiv('autorizzazione_cerca_title_div');
		showHideDiv('autorizzazione_cerca_div');
		showHideDiv('autorizzazionetitle_div');
		showHideDiv('autorizzazione_div');
		showHideDiv('autorizzazione_div2');
		showHideDiv('autorizzazione_div3');
		showHideDiv('autorizzazione_div4');


		function inserisci(){
			if(convalida()){
				doSubmit('insertConcessione.htm?codiceIstanza=${param.codiceIstanza}','',document.inviodati);
			}
		}
	
		function aggiorna(){
			if(convalida()){
				doSubmit('updateConcessione.htm?codice=${concessioniCommand.entity.id.codice}&codiceIstanza=${param.codiceIstanza}','',document.inviodati);
			}
		}

		function convalida(){
			if(document.getElementById('selectPosteggio')){
				if(document.getElementById('selectPosteggio').value==''){
					alert('<fmt:message key="label.mercaD.obbligatorio" />');
					document.getElementById('selectPosteggio').focus();
					return false;
				}
			}
			if(document.getElementById('flag_attiva_id')){
				if(document.getElementById('flag_attiva_id').checked==false){
					if(document.getElementById('selectCausaleCessazione').value==''){
						alert('<fmt:message key="field.required" />');
						document.getElementById('selectCausaleCessazione').focus();
						return false;
					}
					if(document.getElementById('cessazione_id').value==''){
						alert('<fmt:message key="field.required" />');
						document.getElementById('cessazione_id').focus();
						return false;
					}
				}
			}			
			return true;
		}

	//	function nuovoPosteggio(){
	//		if (document.getElementById("mercati_hidden").value != '') {
	//			historySet('${_urlback}', '../mercatid/create.htm?codicemercato='+document.getElementById("mercati_hidden").value, '');
	//		}
	//	}
	/*	
	function modificaPosteggio(){
			if (document.getElementById('selectPosteggio')) {
				if (document.getElementById('selectPosteggio').value != '') {
					historySet('${_urlback}', '../mercatid/view.htm?codice='+document.getElementById('selectPosteggio').value, '');
				}
			} else if(document.getElementById('posteggio_id')){
				if (document.getElementById('posteggio_id').value != '') {
					historySet('${_urlback}', '../mercatid/view.htm?codice='+document.getElementById('posteggio_id').value, '');
				}
			}
		}
	*/
		
		
		function modificaPosteggio(){
			if (document.getElementById('selectPosteggio')) {
				if (document.getElementById('selectPosteggio').value != '') {
					doSubmit('updatePosteggio.htm','',document.inviodati);
				}
			} else if(document.getElementById('posteggio_id')){
				if (document.getElementById('posteggio_id').value != '') {
					doSubmit('updatePosteggio.htm','',document.inviodati);
				}
			}
		}

		
		function nuovoPosteggio(){
			if (document.getElementById("mercati_hidden").value != '') {
				doSubmit('addPosteggio.htm','',document.inviodati);
			}
		}

		mostraModificaPosteggio('selectPosteggio');
		function mostraModificaPosteggio(selectPosteggio){
			if (document.getElementById('selectPosteggio')) {
				if (document.getElementById('selectPosteggio').value != '') {
					showDiv('posteggioDettaglio_id');
				}else{
					hideDiv('posteggioDettaglio_id');
				}
                // gestisce la visualizzazione dell'icona di modifica, quando si
                //torna alla pagina delle concessioni dalla pagina di modifica del
                //posteggio
				if(${concessioniCommand.entity.mercatiD.id.codice!=null})
				{
					showDiv('posteggioDettaglio_id');
				}
			}
		}

		function composizioneMercato(codiceMercato, codiceUso){
			if(codiceMercato!='' && codiceUso!=''){
				historySet('${_urlback}', '../mercatid/list.htm?codicemercato='+codiceMercato+"&codiceuso="+codiceUso,'');
			}			
		}
	</script>
	<c:if test="${concessioniCommand.inserisciAutorizzazione eq true}">
	
	<script type="text/javascript">
		showHideDiv('autorizzazione_cerca_title_div');
		showHideDiv('autorizzazione_cerca_div');
		showDiv('autorizzazionetitle_div');
		showDiv('autorizzazione_div');
		showDiv('autorizzazione_div2');
		showDiv('autorizzazione_div3');
		showHideDiv('autorizzazione_div4');
	</script>
	</c:if>
</c:if>	

<div id="functions">
<ul>
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
		<li><a href="javascript:inserisci();"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
		<c:if test="${concessioniCommand.subentroPresente eq false}">	
			<li><a href="javascript:aggiorna();"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historySet('${_urlback}','../mercatipresenzestorico/listDaAutorizzazione.htm?autId=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.id.codice}','')"><fmt:message key="button.presenze" /></a></li>
			<li><a href="javascript:doSubmit('deleteConcessione.htm?codice=${concessioniCommand.entity.id.codice}&codiceIstanza=${param.codiceIstanza}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<c:if test="${not empty concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris}">
	<br class="clear" />
	<fieldset>
	<legend><fmt:message key="label.passaggi_della_concessione" /></legend>
			<div class="jmesa" >
					<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td><fmt:message key="label.concessione" /></td>
								<td><fmt:message key="label.autorizzazione" /></td>
								<td><fmt:message key="label.istanza" /></td>
								<td><fmt:message key="label.concessione_titolare" /></td>
								<td><fmt:message key="mercatid.label.occupante"/></td>					
								<td><fmt:message key="label.causale_acquisizione" /></td>
								<td><fmt:message key="label.causale_cessazione" /></td>
								<td><fmt:message key="label.data_cessazione" /></td>
							</tr>
						</thead>
						<tbody class="tbody" >
							<tr class="odd">
								<td>${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.transientEstremiAut}</td>
								<td>${concessioniCommand.entity.autorizzazioniByFkAutconcAutcoll.transientEstremiAut}</td>
								<td>
									<a href ="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.id.codice }&software=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.software.codice }');" >${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.numeroistanza}</a>
								</td>
								<td>${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente}</td>
								<td>
									<c:choose>
										<c:when test="${not empty concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.titolarelegale}">
											${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.titolarelegale.descrizioneRichiedente}
										</c:when>
										<c:otherwise>
											${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.istanza.richiedente.descrizioneRichiedente}
										</c:otherwise>									
									</c:choose>
								</td>					
								<td>${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.descrizione }</td>
								<td>${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.descrizione }</td>
								<td><fmt:formatDate value="${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
							</tr>
							<%int i=1;%>
							<c:forEach var="subentro" items="${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris}" varStatus="subentriStatus">
							<tr class="<%=(i%2)==0?"odd":"even"%>"  >
								<td>${subentro.transientEstremiAut}</td>
								<td>${subentro.autorizzazioneCollegataSubentri.transientEstremiAut }</td>
								<td>
									<a href ="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${subentro.istanze.id.codice }&software=${subentro.istanze.software.codice }');" >${subentro.istanze.numeroistanza}</a>
								</td>
								<td>${subentro.anagrafe.descrizioneRichiedente}</td>
								<td>
									<c:choose>
										<c:when test="${not empty subentro.istanze.titolarelegale}">
											${subentro.istanze.titolarelegale.descrizioneRichiedente}
										</c:when>
										<c:otherwise>
											${subentro.istanze.richiedente.descrizioneRichiedente}
										</c:otherwise>									
									</c:choose>
								</td>				
								<td>${subentro.concessionicausaliByFkAutsubConccausAcq.descrizione }</td>
								<td>${subentro.concessionicausaliByFkAutsubConccausCess.descrizione }</td>
								<td><fmt:formatDate value="${subentro.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
							</tr>
							</c:forEach>			
						</tbody>
					</table>
			</div>			
	</fieldset>
</c:if>
</body>
</html>