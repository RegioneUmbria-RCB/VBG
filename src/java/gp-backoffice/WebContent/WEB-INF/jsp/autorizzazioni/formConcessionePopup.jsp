<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
   <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       <title>
           <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
               <fmt:message key="label.nuova_concessione" />
           </c:if>
           <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
               <fmt:message key="label.dettaglio_concessione" />
           </c:if>
       </title>
       <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
   </head>
   <body>
		<script type="text/javascript">
		    var qstring = "codiceIstanza=${param.codiceIstanza}";
		</script>
		<span class="titoloPagina">
			<fmt:message key="label.nuova_concessione" />
		</span>
        <jsp:include page="../includes/innerNavigation.jsp">
           <jsp:param name="navmode" value="form" />
       </jsp:include>
       <div id="subcontent">
           <spring-form:form commandName="concessioniCommand" name="inviodati">
               <jsp:include page="../includes/displayGlobalMessages.jsp">
                   <jsp:param name="commandName" value="concessioniCommand" />
              	</jsp:include>
				<div class="vbg-form">
                   <fieldset>
                       <legend><fmt:message key="label.concessione_dati_della_concessione" /></legend>
                       <div class="form-group">
                           <label><fmt:message key="label.registro" /><a name="autorizzazione"></a></label>
                           <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
                               <script type="text/javascript">
                                   function updateRegistroConcessione(inputField, listItem) {
                                       var a = listItem.id;
                                       document.getElementById('registro_concessione_id').value = inputField.value;
                                       document.getElementById('registro_concessione_hidden').value = a;
                                       $('registro_concessione_id_choices').fade();
                                       if (document.getElementById('registro_concessione_hidden').value != '') {
                                           doSubmit('updateRegistroConcessione.htm?codice=${concessioniCommand.concessioneInsert.id.codice}&' + qstring + '&codiceAnagrafe=' + document.getElementById('titolare_hidden').value + '&codiceRegistro=' + document.getElementById('registro_concessione_hidden').value + '&decorator=popup', '', document.inviodati);
                                       }
                                   }
                               </script>
                               <spring-form:input id="registro_concessione_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registro_concessione_hidden')" onkeydown="javascript:return searchAll(this,event)"
                                   size="70" />
                               <init:autocompleter methodAjax="findTipologiaRegistriPerManifestazioni.htm" afterUpdateElement="updateRegistroConcessione" idHidden="registro_concessione_hidden" idInput="registro_concessione_id" inputTitleKey="label.ricerca_tipo_registro" />
                               <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.trDescrizione" cssClass="error" />
                               <spring-form:hidden id="registro_concessione_hidden" path="autorizzazione.tipologiaregistro.id.codice" />
                           </c:if>
                           <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
                               <input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizione}" />
                           </c:if>
                       </div>
                       <c:if test="${concessioniCommand.registroConcessioneProtocollo eq false}">
                           <div class="form-group">
                               <label><fmt:message key="label.concessione_numero_concessione" /></label>
                               <spring-form:input id="entity_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" />
                               <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />
                               <label><fmt:message key="label.concessione_data_validita_concessione" /></label>
                               <spring-form:input id="autorizdata_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" onblur="isValidDate(this,true);" />
                               <init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdata_id" idInput="autorizdata_id" textKey="label.calendar" />
                               <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error" />
                           </div>
                           <div class="form-group">
                               <label><fmt:message key="label.concessione_data_rilascio_concessione" /></label>
                               <spring-form:input id="dataRilascio_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio" size="10" onblur="isValidDate(this,true);" />
                               <init:calendar imagePath="/images/cal.gif" idImage="cal_dataRilascio_id" idInput="dataRilascio_id" textKey="label.calendar" />
                               <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio" cssClass="error" />
                               <c:if test="${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.flagGestanzianita}">
								 	<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
								 	<spring-form:input id="dataAnzianita_id2" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita" size="10" onblur="isValidDate(this,true);" />								 	
							 		<init:calendar imagePath="/images/cal.gif"	idImage="cal_dataAnzianita_id2" idInput="dataAnzianita_id2" textKey="label.calendar"/> 
								 	<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita" cssClass="error" />
							 	</c:if>
                           </div>
                       </c:if>
                  
                   <%-- QUESTO È IL CASO IN CUI VISUALIZZO UN'AUTORIZZAZIONE ATTIVA --%>
                   <c:if test="${concessioniCommand.registroConcessioneProtocollo eq true}">
                       <div class="form-group">
                           <b><fmt:message key="label.concessione_estremi_da_protocollo" /></b>
                       </div>
                       <div class="form-group">
                           <label><fmt:message key="label.concessione_numero_concessione" /></label>
                           <input type="text" value=" ${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero }" readonly>
                           <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />
                           <label><fmt:message key="label.concessione_data_validita_concessione" /></label>                           
                           <c:if test="${not empty concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata }">
								<input type="text" value="<fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" readonly/>
                               <%-- <fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /> --%>
                           </c:if>
                           <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error" />                           
                       </div>
                       <div class="form-group">
                           <label><fmt:message key="label.concessione_data_rilascio_concessione" /></label>
                           <c:if test="${not empty concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio }">
                           		<input type="text" value="<fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" readonly />
                               <%-- <fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /> --%>
                           </c:if>
                           <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataRilascio" cssClass="error" /> 
                           <c:if test="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.flagGestanzianita}">
                       			<label><fmt:message key="label.concessione_data_anzianita_autorizzazione" /></label>
                       			<input type="text" value="<fmt:formatDate value="${ concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.dataAnzianita }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />" readonly />
							 	
                           </c:if>                       
                       </div>
                  	</c:if>
					<div class="form-group">
					    <label><fmt:message key="label.concessione_titolare" /></label>
				        <spring-form:input id="titolare_id" size="70" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
				        <init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare" />
				        <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe" cssClass="error" />
				        <spring-form:hidden id="titolare_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice" />
					</div>
					
					<div class="form-group">
						<label><fmt:message key="mercatid.label.occupante" /></label>							
						<spring-form:input id="occupante_id" size="70" 
							path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.descrizioneRichiedente" 
							cssClass="searchbox" onchange="checkValue(this,'occupante_hidden')" 
								onkeydown="javascript:return searchAll(this,event)"/> 
						<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="occupante_hidden" 
							idInput="occupante_id" inputTitleKey="label.ricerca"/>
						<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante" cssClass="error" /> 
						<spring-form:hidden id="occupante_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice" />
					</div>	
                    <div class="form-group">
                        <label><fmt:message key="label.concessione_tipologia_concessione" /></label>
                        <spring-form:select id="selectTipoConcessione" path="concessioneInsert.concessionitipi.tipoconcessione" onchange="showTipologia(this);">
                            <spring-form:option value=""></spring-form:option>
                            <spring-form:options items="${concessionitipis}" itemLabel="descrizione" itemValue="tipoconcessione"></spring-form:options>
                        </spring-form:select>
                        <spring-form:errors path="concessioneInsert.concessionitipi" cssClass="error" />
                    </div>
                       <div class="form-group" id="tipologiaStagionaleDiv">
                           <label><fmt:message key="label.concessione_tipologia_stagionale_da" /></label>
                           <spring-form:input id="stagionaleda_id" path="concessioneInsert.stagionaledaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" />
                           <spring-form:errors path="concessioneInsert.stagionaleda" cssClass="error" />
                           <label><fmt:message key="label.concessione_tipologia_stagionale_a" /></label>
                           <spring-form:input id="stagionalea_id" path="concessioneInsert.stagionaleaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" />
                           <spring-form:errors path="concessioneInsert.stagionalea" cssClass="error" />
                       </div>
                       <div class="form-group">
                           <label><fmt:message key="label.concessione_scadenza" /></label>
                           <spring-form:input id="scadenza_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.datascadenza" size="10" onblur="isValidDate(this,true);" />
                           <init:calendar imagePath="/images/cal.gif" idImage="cal_scadenza_id" idInput="scadenza_id" textKey="label.calendar" />
                           <spring-form:errors path="concessioneInsert.datascadenza" cssClass="error" />
                       </div>
                       <div class="form-group">
                           <label><fmt:message key="label.concessione_causale_acquisizione" /></label>
                           <spring-form:select id="selectCausaleAcquisizione" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice" onchange="viewDataAffitto()">
                               <spring-form:option value="">
                                   <fmt:message key="label.seleziona" />
                               </spring-form:option>
                               <spring-form:options items="${concessionicausalisAcq}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
                           </spring-form:select>
                           <spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice" cssClass="error" />
                           <div id="dataAffitto">
								<label><fmt:message key="label.concessione_data_fine_affitto" /></label>
								<spring-form:input id="dataFineAffitto_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataFineAffitto" size="10" onblur="isValidDate(this,true);" /> 
								<init:calendar imagePath="/images/cal.gif" idImage="cal_dataFineAffitto_id" idInput="dataFineAffitto_id" textKey="label.calendar"/> 
								<spring-form:errors path="concessioneInsert.autorizzazioniByFkAutconcAutatt.dataFineAffitto" cssClass="error" />
							</div>
                       </div>
                       <div class="form-group">
                           <input type="hidden" name="concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.flagAttiva" value="true" />
                       </div>
                       </fieldset>
                       
						<fieldset>
							<legend><fmt:message key="label.concessione_dati_della_manifestazione" /></legend>
							<div class="form-group" id="mercati">
								<label><fmt:message key="label.manifestazione" /></label>
							    <b>${concessioniCommand.concessioneInsert.mercati.descrizione}</b>
								<spring-form:hidden id="mercati_hidden" path="concessioneInsert.mercati.id.codice" />
							</div>
							<c:if test="${concessioniCommand.concessioneInsert.mercatiUso.id.codice != null}">
								<div class="form-group" id="mercatiUso">
								    <label><fmt:message key="label.mercati_uso" /></label>
								    <b>${concessioniCommand.concessioneInsert.mercatiUso.descrizione}</b>
									<spring-form:hidden path="concessioneInsert.mercatiUso.id.codice" id="mercatiUso_hidden" />
								</div>
							</c:if>
							<c:if test="${concessioniCommand.concessioneInsert.mercatiUso.id.codice == null}">
								<div class="form-group" id="mercatiUso">
								    <label><fmt:message key="label.mercati_uso" /></label>
								    <script type="text/javascript">
										function filtermercato(element, entry) {
										    return entry + "&codiceMercato=" + document.getElementById("mercati_hidden").value;
										}

										function setHiddenFieldmercati(inputField, listItem) {
										    var a = listItem.id;
										    document.getElementById('mercatiUso_id').value = inputField.value;
										    document.getElementById('mercatiUso_hidden').value = a;
										    assegnaPosteggio();
										}
									</script>
									<jsp:include page="../includes/autocompletergenerico.jsp">
										<jsp:param name="idElemento" value="mercatiUso" />
										<jsp:param name="propertyPath" value="concessioneInsert.mercatiUso" />
										<jsp:param name="pathPropertyDescription" value="concessioneInsert.mercatiUso.descrizione" />
										<jsp:param name="pathPropertyCode" value="concessioneInsert.mercatiUso.id.codice" />
										<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
										<jsp:param name="autocompleterInputSize" value="50" />
										<jsp:param name="ajaxCallBack" value="filtermercato" />
										<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati" />
										<jsp:param name="titleKey" value="label.giorno" />
									</jsp:include>
								</div>
							</c:if>
							<div class="form-group" id="mercatiPosteggio">
							    <label><fmt:message key="label.posteggio" /></label>
							    <c:if test="${param.status_msg ne '01'}">
									<spring-form:select id="selectPosteggio" path="concessioneInsert.mercatiD.id.codice" />
								</c:if>
								<c:if test="${param.status_msg eq '01'}">
									<b>${concessioniCommand.concessioneInsert.mercatiD.codiceposteggio}</b>
								</c:if>
								<spring-form:errors path="concessioneInsert.mercatiD.id.codice" cssClass="error" />
							</div>
						</fieldset>
                  </div>
				</spring-form:form>
       		</div>
       		
       		<jsp:include page="../autorizzazioni/funzioniJS.jsp" />

		<script type="text/javascript">
           var posteggioSelezionato = '${concessioniCommand.concessioneInsert.mercatiD.id.codice}';
           var isEditmode = false; 
           <c:if test = "${param.status_msg eq '01'}" >
               isEditmode = true; 
           </c:if>




<%--            function showTipologia(obj) {
               var pos = document.getElementById('selectTipoConcessione').selectedIndex;
               var valore_nascosto = '';
               if (document.getElementById('hidden_tipoconcessione')) {
                   valore_nascosto = document.getElementById('hidden_tipoconcessione').value;
               }

               if (pos > -1 || valore_nascosto != '') {
                   var itemSelected = null;
                   if (valore_nascosto == '') {
                       itemSelected = document.getElementById('selectTipoConcessione').options[pos].value;
                   } else {
                       itemSelected = valore_nascosto;
                   }
                   if (itemSelected == '') {
                       hideDiv('tipologiaStagionaleDiv');
                       return;
                   }
                   var call_msg = new Ajax.Request('<%=request.getContextPath()%>/concessionitipi/ajaxIsConcessioneStagionale.htm?tipoconcessione=' + itemSelected, {
                       method: 'post',
                       onSuccess: function(transport) {
                           var risposta = transport.responseText;
                           showHidestagionaleDiv(risposta);
                       },
                       onFailure: function(transport) {
                           var responseTexts = transport.responseText;
                           alert(responseTexts);
                       }
                   });

               }
           } --%>

           if (!isEditmode) {
               if (document.getElementById("mercati_hidden") != null && document.getElementById("mercati_hidden").value != '') {
                   assegnaPosteggio();
               }
           }

           showTipologia(document.getElementById('selectTipoConcessione'));

           function aggiornaParent() {
               window.opener.jQuery("#conc_table${param.codiceIstanza}").append("<tr><td>${concessioniCommand.concessioneInsert.mercatiD.codiceposteggio}</td><td align='right'><a href=\"javascript:historySet(urlback,'../autorizzazioni/viewConcessione.htm?codiceIstanza=${param.codiceIstanza}&codiceAutorizzazione=${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.id.codice }', '')\">${concessioniCommand.concessioneInsert.autorizzazioniByFkAutconcAutatt.transientEstremiAut}, ${concessioniCommand.concessioneInsert.mercatiUso.descrizione}</a></td></tr>");
               window.opener.jQuery("#nuova_conc${param.codiceIstanza}").html("");
               self.close();
           }
       </script>  
		<div class="form-button">
			<c:if test="${param.status_msg ne '01'}">
				<a class="btn btn-primary" href="javascript:inserisci(true);"><fmt:message key="button.insert" /></a>
				<a class="btn btn-secondary" href="javascript:self.close();"><fmt:message key="button.back" /></a>
			</c:if>
			<c:if test="${param.status_msg eq '01'}">
				<a class="btn btn-secondary" href="javascript:aggiornaParent();"><fmt:message key="button.back" /></a>
			</c:if>
		</div> 
   </body>
</html>