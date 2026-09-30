<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
				<fmt:message key="label.nuova_autorizzazione" />
			</c:if> 
			<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT or 
						autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.DELETED or 
						autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.READONLY}">
				<fmt:message key="label.dettaglio_autorizzazione" />
			</c:if>
		</title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
		<style type="text/css">
			#concessioneNumeroAutorizzazione_id {    
			    margin-right: 25px;
			}
			#dataRilascio_id:read-only {
			    margin-right: 25px;
			}
		</style>
	</head>
<body>
<script type="text/javascript">
		var qstring = "codiceIstanza=${param.codiceIstanza}&codiceMovimento=${param.codiceMovimento}&codiceAnagrafe=${param.codiceAnagrafe}";
</script>
<span class="titoloPagina"> 
	<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
		<fmt:message key="label.nuova_autorizzazione" />
	</c:if> 
	<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT or 
				autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.DELETED or 
				autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.READONLY}">
				<fmt:message key="label.dettaglio_autorizzazione" />		
	</c:if> 
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../autorizzazioni/viewAutorizzazione" />
</jsp:include>
<div class="vbg-form">
	<fieldset>
		<legend><fmt:message key="label.dati_istanza"/></legend> 
		<c:if test="${not empty param.codiceIstanza}">
			<c:import url="/ajax/dettaglioIstanza.htm">
				<c:param name="codIstanza">${param.codiceIstanza}</c:param>
			</c:import>
		</c:if>
	</fieldset>
</div>

<c:if test="${not empty param.codiceAnagrafe}">
	<c:import url="/ajax/dettaglioAnagrafe.htm?showParametriDiv=true"/>
</c:if>
<br class="clear" />

<c:choose>
	<c:when test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.READONLY}">
		<div id="subcontent">
			<spring-form:form commandName="autorizzazioniCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="autorizzazioniCommand" />
			</jsp:include>
			<div class="vbg-form">
				<fieldset>
					<legend>Autorizzazioni</legend>			
					<div class="form-group">
						<div class="success_header"><fmt:message key="label.l_autorizzazione_e_stata_subentrata_dalla" /></div>
						${autorizzazioniCommand.subentro.autorizzazioni.transientEstremiAut}
					</div>								
					<div class="form-group">
						<label>
							<fmt:message key="label.autorizzazione_registro" />
							<a name="autorizzazione"></a>
						</label>				
						<spring-form:input id="registro_id" path="subentro.tipologiaregistro.trDescrizioneCompleta" disabled="true" size="70"/>				
					</div>						
					<div class="form-group">
						<label><fmt:message key="label.autorizzazione_numero_autorizzazione" /></label>
						<spring-form:input id="concessioneNumeroAutorizzazione_id" path="subentro.autoriznumero" size="10" disabled="true" />	
						<label><fmt:message key="label.autorizzazione_data_validita_autorizzazione" /></label>
						<spring-form:input id="autorizdata2_id" path="subentro.autorizdata" size="10" disabled="true" />				
					</div>							
					<div class="form-group">
						<label><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></label>
						<spring-form:input id="autorizdatadataRilascio_id" path="subentro.dataRilascio" size="10" disabled="true" />						
						<label><fmt:message key="label.autorizzazione_data_anzianita_autorizzazione" /></label>
						<spring-form:input id="dataAnzianita_id" path="subentro.dataAnzianita" size="10" disabled="true" />	
					</div>							
					<div class="form-group">
						<label><fmt:message key="label.concessione_scadenza" /></label>
						<spring-form:input id="concessionescadenza_id" path="subentro.datascadenza" size="10" disabled="true" />									
					</div>							
					<div class="form-group">
						<label><fmt:message key="label.concessione_autorizzata_da" /></label>
						<spring-form:input id="responsabile_id" size="50" path="subentro.autorizresponsabile" disabled="true"/>					
						<label><fmt:message key="label.concessione_autorizzata_il" /></label>
						<spring-form:input id="autorizdataregistr_id" path="subentro.autorizdataregistr" size="10" disabled="true"/> 				
					</div>							
					<div class="form-group">
						<label><fmt:message key="label.anagrafe" /></label>
						<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" disabled="true" />
					</div>		
						<div class="form-group">
							<label><fmt:message key="mercatid.label.occupante" /></label>
							<spring-form:input id="occupante_id" size="70" path="entity.occupante.descrizioneRichiedente" disabled="true" />
						</div>							
					<div class="form-group">
						<label>
							<fmt:message key="label.comune" />
							<a name="autorizzazione"></a>
						</label>
						<spring-form:input id="comune_id" path="subentro.autorizcomune.comune" disabled="true" size="70"/>				
					</div>										
					<c:if test="${not empty concessionicausalis}">
						<div class="form-group" id="dati_cessazione_id">
							<label><fmt:message key="label.causale_cessazione" /></label>
							<spring-form:select id="selectCausaleCessazione" disabled="true" path="subentro.concessionicausaliByFkAutsubConccausCess.id.codice">
								<spring-form:option value=""></spring-form:option>
								<spring-form:options items="${concessionicausalis}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
							</spring-form:select>					
							<label><fmt:message key="label.data_cessazione" /></label>
							<spring-form:input id="cessazione_id" path="subentro.dataCessazione" size="10" disabled="true"/> 								
						</div>
					</c:if>
					<c:if test="${empty concessionicausalis}">
						<div class="form-group" id="dati_cessazione_id">
							<label><fmt:message key="label.data_cessazione" /></label>
							<spring-form:input id="cessazione_id" path="subentro.dataCessazione" size="10" disabled="true" /> 								
						</div>
					</c:if>	
				</fieldset>
			</div>		
			</spring-form:form>
		</div>		
	</c:when>
	<c:otherwise>
	<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.DELETED}">
		<div id="subcontent">
			<spring-form:form commandName="concessioniCommand" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
					<jsp:param name="commandName" value="concessioniCommand" />
				</jsp:include>
			</spring-form:form>				
		</div>
	</c:if>	
	<c:if test="${autorizzazioniCommand.displayMode ne autorizzazioniCommand.displayConstants.DELETED}">	
		<div id="subcontent">
		<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">		
			<c:if test="${not empty autConcHelper}">
				<c:if test="${not empty autConcHelper.autorizzazioni}">
					<%
					String displayAutConc = "display:none;";
					String styleAutConc = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_NUOVA_AUT_VIS_AUTORIZZAZIONI)).equals("1")) {
					    displayAutConc = "";
					    styleAutConc="sezioneDatiMeno";
					} else {
					    displayAutConc = "display:none;";
					    styleAutConc="sezioneDatiPiu";
					}
					%>
					<fieldset>
					<legend>
						<a class="<%=styleAutConc %>" 
							id="id_link_autconc" 
							title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.autorizzazioni_presenti_nell_istanza"/>">
							<label for="id_link_autconc"><fmt:message key="label.autorizzazioni_presenti_nell_istanza"/></label>
						</a>						
					</legend>
					<div id="id_autconc_table" style="<%=displayAutConc%>">
						<table class="vbg-table">
							<thead class="header">
								<th><fmt:message key="label.numero"/></th>
								<th><fmt:message key="label.autorizzazione_data_validita_autorizzazione"/></th>
								<th><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione"/></th>
								<th><fmt:message key="label.comune"/></th>
								<th><fmt:message key="label.registro"/></th>
								<th><fmt:message key="label.stato"/></th>
								<th><fmt:message key="label.edit.record"/></th>
							</thead>
							<tbody class="tbody">
							<%int x=0; %>
							<c:forEach items="${autConcHelper.autorizzazioni}" var="curr_auth" >
								<tr class="<%=(x%2)==0?"odd":"even"%>">
									<td>
									<a href="javascript:doHref('../autorizzazioni/viewAutorizzazione.htm?codiceIstanza=${curr_auth.istanza.id.codice}&codiceMovimento=${curr_auth.movimenti.id.codice}&codice=${curr_auth.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${curr_auth.id.codice }">
									${curr_auth.autoriznumero }</a>
									</td>
									<td><fmt:formatDate value="${curr_auth.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td><fmt:formatDate value="${curr_auth.dataRilascio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td>${curr_auth.autorizcomune.comune}</td>
									<td>${curr_auth.tipologiaregistro.trDescrizioneCompleta}</td>
									<td>
										<c:if test="${curr_auth.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
										<c:if test="${curr_auth.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
									</td>
									<td>
										<a class="dettaglioColumn" href="javascript:doHref('../autorizzazioni/viewAutorizzazione.htm?codiceIstanza=${param.codiceIstanza}&codiceMovimento=${param.codiceMovimento}&codice=${curr_auth.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${curr_auth.id.codice }">
											<label><fmt:message key="label.edit.record.image" /></label>
										</a>
									</td>
								</tr>						
							<%x++; %>
							</c:forEach>
							</tbody>
						</table>
					</div>
					</fieldset>
				</c:if>				
			</c:if>
		</c:if>		
		<spring-form:form commandName="autorizzazioniCommand" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="autorizzazioniCommand" />
		</jsp:include>
		
		<div class="vbg-form">
			<fieldset>
				<legend><fmt:message key="label.autorizzazioni" /></legend>			
				<div class="form-group">
					<label><fmt:message key="label.autorizzazione_registro" /></label>
					<script type="text/javascript">
						function updateRegistro(inputField,listItem){
							var a = listItem.id;
							document.getElementById('registro_id').value = inputField.value;
							document.getElementById('registro_hidden').value = a;
							/* $('registro_id_choices').fade(); */
							if(document.getElementById('registro_hidden').value!=''){
								doSubmit('updateRegistroAutorizzazione.htm?codice=${autorizzazioniCommand.entity.id.codice}&'+qstring+'&codiceRegistro=' + document.getElementById('registro_hidden').value,'',document.inviodati);
							}
						}
					</script>
					<spring-form:input id="registro_id" path="entity.tipologiaregistro.trDescrizioneCompleta" cssClass="searchbox" onchange="checkValue(this,'registro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
					<init:autocompleter methodAjax="findTipologiaRegistri.htm?codicecomune=${autorizzazioniCommand.entity.istanza.comune.codicecomune}" afterUpdateElement="updateRegistro"  idHidden="registro_hidden"  idInput="registro_id" inputTitleKey="label.ricerca_tipo_registro"/>
					<spring-form:errors	path="entity.tipologiaregistro.trDescrizioneCompleta" cssClass="error"	/>
					<spring-form:hidden id="registro_hidden" path="entity.tipologiaregistro.id.codice"  />
				</div>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
				    <c:if test="${autorizzazioniCommand.registroAutorizzazioneProtocollo eq false}">
				        <div class="form-group">
				            <label>
				                <fmt:message key="label.autorizzazione_numero_autorizzazione" />
				            </label>
				            <spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autoriznumero" size="10" />
				            <spring-form:errors path="entity.autoriznumero" cssClass="error" />
				            <label>
				                <fmt:message key="label.autorizzazione_data_validita_autorizzazione" />
				            </label>
				            <spring-form:input id="autorizdata2_id" path="entity.autorizdata" size="10" onblur="isValidDate(this,true);" />
				            <init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdata2_id" idInput="autorizdata2_id" textKey="label.calendar" />
				            <spring-form:errors path="entity.autorizdata" cssClass="error" />
				        </div>
				        <div class="form-group">
				            <label>
				                <fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" />
				            </label>
				            <spring-form:input id="dataRilascio_id" path="entity.dataRilascio" size="10" onblur="isValidDate(this,true);" />
				            <init:calendar imagePath="/images/cal.gif" idImage="cal_dataRilascio_id" idInput="dataRilascio_id" textKey="label.calendar" />
				            <spring-form:errors path="entity.dataRilascio" cssClass="error" />
				            <c:if test="${autorizzazioniCommand.entity.tipologiaregistro.flagGestanzianita eq true}">
				                <label>
				                    <fmt:message key="label.autorizzazione_data_anzianita_autorizzazione" />
				                </label>
				                <spring-form:input id="dataAnzianita_id" path="entity.dataAnzianita" size="10" onblur="isValidDate(this,true);" />
				                <init:calendar imagePath="/images/cal.gif" idImage="cal_dataAnzianita_id" idInput="dataAnzianita_id" textKey="label.calendar" />
				                <spring-form:errors path="entity.dataAnzianita" cssClass="error" />
				            </c:if>
				        </div>
				    </c:if>
				    <c:if test="${autorizzazioniCommand.registroAutorizzazioneProtocollo eq true}">
				        <div class="form-group">				            
			               <b><fmt:message key="label.autorizzazione_estremi_da_protocollo" /></b>				            
				        </div>
				        <div class="form-group">
				            <label>
				                <fmt:message key="label.autorizzazione_numero_autorizzazione" />
				            </label>
				            <spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autoriznumero" size="10" readonly="true" />
				            <spring-form:errors path="entity.autoriznumero" cssClass="error" />
				            <label>
				                <fmt:message key="label.autorizzazione_data_validita_autorizzazione" />
				            </label>
				            <spring-form:input id="autorizdata2_id" path="entity.autorizdata" size="10" readonly="true" />
				            <spring-form:errors path="entity.autorizdata" cssClass="error" />
				        </div>
				        <div class="form-group">
				            <label>
				                <fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" />
				            </label>
				            <spring-form:input id="dataRilascio_id" path="entity.dataRilascio" size="10" readonly="true" />
				            <spring-form:errors path="entity.dataRilascio" cssClass="error" />
				            <c:if test="${autorizzazioniCommand.entity.tipologiaregistro.flagGestanzianita eq true}">
				                <label>
				                    <fmt:message key="label.autorizzazione_data_anzianita_autorizzazione" />
				                </label>
				                <spring-form:input id="dataAnzianita_id" path="entity.dataAnzianita" size="10" onblur="isValidDate(this,true);" />
				                <init:calendar imagePath="/images/cal.gif" idImage="cal_dataAnzianita_id" idInput="dataAnzianita_id" textKey="label.calendar" />
				                <spring-form:errors path="entity.dataAnzianita" cssClass="error" />
				            </c:if>
				        </div>
				    </c:if>
				</c:if>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
				    <div class="form-group">
				        <label>
				            <fmt:message key="label.autorizzazione_numero_autorizzazione" />
				        </label>
				        <spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autoriznumero" size="10" />
				        <spring-form:errors path="entity.autoriznumero" cssClass="error" />
				        <label>
				            <fmt:message key="label.autorizzazione_data_validita_autorizzazione" />
				        </label>
				        <spring-form:input id="autorizdata2_id" path="entity.autorizdata" size="10" onblur="isValidDate(this,true);" />
				        <init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdata2_id" idInput="autorizdata2_id" textKey="label.calendar" />
				        <spring-form:errors path="entity.autorizdata" cssClass="error" />
				    </div>
				    <div class="form-group">
				        <label>
				            <fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" />
				        </label>
				        <spring-form:input id="dataRilascio_id" path="entity.dataRilascio" size="10" onblur="isValidDate(this,true);" />
				        <init:calendar imagePath="/images/cal.gif" idImage="cal_dataRilascio_id" idInput="dataRilascio_id" textKey="label.calendar" />
				        <spring-form:errors path="entity.dataRilascio" cssClass="error" />
				        <c:if test="${autorizzazioniCommand.entity.tipologiaregistro.flagGestanzianita eq true}">
				            <label>
				                <fmt:message key="label.autorizzazione_data_anzianita_autorizzazione" />
				            </label>
				            <spring-form:input id="dataAnzianita_id" path="entity.dataAnzianita" size="10" onblur="isValidDate(this,true);" />
				            <init:calendar imagePath="/images/cal.gif" idImage="cal_dataAnzianita_id" idInput="dataAnzianita_id" textKey="label.calendar" />
				            <spring-form:errors path="entity.dataAnzianita" cssClass="error" />
				        </c:if>
				    </div>
				</c:if>
				<div class="form-group">
					<label><fmt:message key="label.concessione_scadenza" /></label>
					<spring-form:input id="datascadenza_id" path="entity.datascadenza" size="10" onblur="isValidDate(this,true);" /> 
					<init:calendar imagePath="/images/cal.gif"	idImage="cal_datascadenza_id" idInput="datascadenza_id" textKey="label.calendar"/> 
					<spring-form:errors path="entity.datascadenza" cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message key="label.concessione_autorizzata_da" /></label>
					<spring-form:input id="responsabile_id" size="50" path="entity.autorizresponsabile" />
					<spring-form:errors path="entity.autorizresponsabile" cssClass="error" />
					<label><fmt:message key="label.concessione_autorizzata_il" /></label>
					<spring-form:input id="autorizdataregistr_id" path="entity.autorizdataregistr" size="10" onblur="isValidDate(this,true);" /> 
					<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdataregistr_id" idInput="autorizdataregistr_id" textKey="label.calendar" /> 
					<spring-form:errors	path="entity.autorizdataregistr" cssClass="error"/>
				</div>
				<div class="form-group">
					<label><fmt:message key="label.comune" /><a name="autorizzazione"></a></label>
					<spring-form:input id="comune_id" path="entity.autorizcomune.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
					<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="comune_hidden"  minChars="2" idInput="comune_id" inputTitleKey="label.ricerca_comune"/>
					<spring-form:errors	path="entity.autorizcomune" cssClass="error"/>
					<spring-form:hidden id="comune_hidden" path="entity.autorizcomune.codicecomune" />
				</div>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
					<c:if test="${not empty param.codiceAnagrafe}">
						<div class="form-group">
							<label><fmt:message key="label.anagrafe" /></label>
							<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" disabled="true" /> 
							<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
							<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" />
						</div>				
					</c:if>
					<c:if test="${empty param.codiceAnagrafe}">
						<div class="form-group">
							<label><fmt:message key="label.anagrafe" /></label>							
							<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
							<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
							<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
							<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" />
						</div>				
					</c:if>
						<div class="form-group">
								<label><fmt:message key="mercatid.label.occupante" /></label>							
								<spring-form:input id="occupante_id" size="70" path="entity.occupante.descrizioneRichiedente" 
									cssClass="searchbox" onchange="checkValue(this,'occupante_hidden')" 
										onkeydown="javascript:return searchAll(this,event)"/> 
								<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="occupante_hidden" 
									idInput="occupante_id" inputTitleKey="label.ricerca"/>
								<spring-form:errors path="entity.occupante" cssClass="error" /> 
								<spring-form:hidden id="occupante_hidden" path="entity.occupante.id.codice" />
						</div>
							
				</c:if>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
					<c:if test="${not empty param.codiceAnagrafe}">
						<div class="form-group">
							<label><fmt:message key="label.anagrafe" /></label>							
							<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" disabled="true" /> 
							<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
							<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" />
						</div>					
					</c:if>
					<c:if test="${empty param.codiceAnagrafe}">
						<div class="form-group">
							<label><fmt:message key="label.anagrafe" /></label>							
							<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
							<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
							<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
							<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" />
						</div>					
					</c:if>

						<div class="form-group">
								<label><fmt:message key="mercatid.label.occupante" /></label>		
								<spring-form:input id="occupante_id" size="70" path="entity.occupante.descrizioneRichiedente" disabled="true" />
								<spring-form:hidden id="occupante_hidden" path="entity.occupante.id.codice" />
								<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
									<jsp:include page="modifica_occupante.jsp" >
											<jsp:param name="idAutorizzazione" value="${autorizzazioniCommand.entity.id.codice}" />
											<jsp:param name="paginaChiamante" value="${_urlback}" />
									</jsp:include>
								</spring-security:authorize>
						</div>
				</c:if>
				<div class="form-group">
					<label><fmt:message key="label.concessione_causale_acquisizione" /></label>					
					<spring-form:select id="selectCausaleAcquisizione" path="entity.concessionicausaliByFkAutConccausAcq.id.codice">
						<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
						<spring-form:options items="${concessionicausalisAcq}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
					</spring-form:select>
					<spring-form:errors path="entity.concessionicausaliByFkAutConccausAcq.id.codice" cssClass="error" />
					<div id="dataAffitto">
						<label><fmt:message key="label.concessione_data_fine_affitto" /></label>						
						<spring-form:input id="dataFineAffitto_id" path="entity.dataFineAffitto" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_dataFineAffitto_id" idInput="dataFineAffitto_id" textKey="label.calendar" /> 
						<spring-form:errors	path="entity.dataFineAffitto" cssClass="error"/>					
					</div>					
				</div>
				<div class="form-group">
					<label for="flag_attiva_id"><fmt:message key="label.concessione_attiva" /></label>
					<spring-form:checkbox path="entity.flagAttiva" id="flag_attiva_id"/>
				</div>
				<c:if test="${not empty concessionicausalis}">
					<div class="form-group" id="dati_cessazione_id">
						<label><fmt:message key="label.causale_cessazione" /></label>
						<spring-form:select id="selectCausaleCessazione" path="entity.concessionicausaliByFkAutConccausCess.id.codice">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${concessionicausalis}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
						</spring-form:select><spring-form:errors path="entity.concessionicausaliByFkAutConccausCess.id.codice" cssClass="error" />
						<label><fmt:message key="label.data_cessazione" /></label>
						<spring-form:input id="cessazione_id" path="entity.dataCessazione" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.dataCessazione" cssClass="error" />			
					</div>
				</c:if>
				<c:if test="${empty concessionicausalis}">
					<div class="form-group" id="dati_cessazione_id">
						<label><fmt:message key="label.data_cessazione" /></label>
						<spring-form:input id="cessazione_id" path="entity.dataCessazione" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.dataCessazione" cssClass="error" />			
					</div>
				</c:if>
				<c:if test="${not empty statiIstanzaList}">
					<div class="form-group" id="dati_chiusura_id">
						<label><fmt:message key="label.tipo_chiusura" /></label>						
						<spring-form:select id="selectChiusuraIstanza" path="statoistanza.id.codicestato">
							<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
							<spring-form:options items="${statiIstanzaList}" itemLabel="stato" itemValue="id.codicestato"></spring-form:options>
						</spring-form:select>									
					</div>
				</c:if>

				</fieldset>
				<fieldset id="elenco-firmatari">
					<legend>Firmatario</legend>
					<div class="form-group">
						<spring-form:select path="codiceFirmatario" >
							<spring-form:options items="${elencoFirmatari}" itemLabel="descrizione" itemValue="codice" />
						</spring-form:select>
					</div>
				</fieldset>
		</div>

			
		<vbg-modal id="vbg-modal-dettaglio-mercati-spuntisti"></vbg-modal>			
			
			<!-- Elemento di iclusi passato come parametro
					- areareadonly: parametro per la jsp autorizzazioni/datidehors.jsp
			 -->
			<table width="100%">			
			<c:if test="${not empty paginaInclude}">
				<jsp:include page="${paginaInclude}">
					<jsp:param name="areareadonly"  value="${value0or1}" />
				</jsp:include>	
			</c:if>
			</table>
		</spring-form:form>
	</div>
	<div class="form-button">		
		<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
			<a class="btn btn-primary" id="btnInsert"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
			<c:if test="${autorizzazioniCommand.entity.modificaBloccata eq false}">
				<a class="btn btn-primary" id="btnUpdate" ><fmt:message key="button.update" /></a>
			</c:if>
			<c:if test="${not empty autorizzazioniCommand.entity.istanza.id.codice}"> <%-- se l'autorizzazione è stata generata da un'istanza, mostro il bottone per accedere ai documenti della stessa --%>
				<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../documentiautorizzazione/list.htm?codiceautorizzazione=${autorizzazioniCommand.entity.id.codice}','')"><fmt:message key="documentiautorizzazione.button.documentiautorizzazione"/></a>
			</c:if> 
			<c:if test="${autorizzazioniCommand.manifestazioniConfigurate eq true}">
			<c:if test="${isViewPresenze}">
				<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../mercatipresenzestorico/listDaAutorizzazione.htm?autId=${autorizzazioniCommand.entity.id.codice}','')"><fmt:message key="button.presenze" /></a>
			</c:if>
			<c:if test="${isTipoInformazioni}">
				<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../autorizzazioni/merceologie.htm?idautorizzazione=${autorizzazioniCommand.entity.id.codice}','')"><fmt:message key="label.dettaglio_informazione" /></a>				
			</c:if>	
			</c:if>
			<c:if test="${isGestioneSpuntistiAttiva}">
				<a class="btn btn-primary" id="btnVisualizzaDettaglioSpuntista" ><fmt:message key="label.spuntista_mercati" /></a>
		    </c:if>
		    <c:if test="${autorizzazioniCommand.entity.modificaBloccata eq false && isAutorizzazioneAttoCollegatoAConcessione eq false}">
				<a class="btn btn-primary" href="javascript:doSubmit('validaDeleteAutorizzazione.htm?codice=${autorizzazioniCommand.entity.id.codice}&'+qstring+'&return_to_page='+encodeURIComponent('${_urlback}'),'<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
		</c:if>
		<a class="btn btn-secondary" class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>		
	</div>	
</c:if>
</c:otherwise>
</c:choose>
<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.DELETED or autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.READONLY}">
	<div class="form-button">
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>		
	</div>
</c:if>
<script type="text/javascript">
	vbg.ready(() => {
		
		var gestFlagAttiva = function (){
			let id_div_cessazione = 'dati_cessazione_id';
			let chkObj = document.getElementById('flag_attiva_id');
			if(chkObj){
				if(chkObj.checked){
					hideDiv(id_div_cessazione);
				}else{
					showDiv(id_div_cessazione);
				}
			}
		}
		
		let flag_attiva = document.getElementById('flag_attiva_id');
		if( flag_attiva ) {
			flag_attiva.onclick = function(){
				console.log('gestFlagAttiva');
				gestFlagAttiva();
			}
			
		}

		var mostraLinkAutConc = function() {
			
			let panelId = 'id_autconc_table';
			let elemImg = 'id_link_autconc';
			let userprefParam = '<%= WebConstants.CONF_UTENTE_NUOVA_AUT_VIS_AUTORIZZAZIONI %>';
			let imagePath = '${pageContext.request.contextPath}/images/';
			let tagElement = 'div';
			
			showHidePanel(panelId, elemImg, userprefParam, imagePath,tagElement);
		}
		
		let id_link_autconc = document.getElementById('id_link_autconc');
		if( id_link_autconc ){
			id_link_autconc.onclick = function(){
				console.log('mostraLinkAutConc');
				mostraLinkAutConc();	
			}
		}
		
		function update(){
			if( convalida() ){
				let codice = '${autorizzazioniCommand.entity.id.codice}';
				doSubmit('updateAutorizzazione.htm?codice=' + codice + '&' + qstring ,'',document.inviodati);
			}
		}
		
		function insert(){
			if( convalida() ){
				doSubmit('insertAutorizzazione.htm?' + qstring,'',document.inviodati);
			}
		}
		
		function convalida(){
			if(document.getElementById('selectChiusuraIstanza')){
				if(document.getElementById('selectChiusuraIstanza').value==''){
					alert('<fmt:message key="label.tipo_chiusura" /> <fmt:message key="field.required" />');
					document.getElementById('selectChiusuraIstanza').focus();				
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
		
		var visualizzaDettaglioAutorizSpuntista = async  function(){
			window.vbg.mostraModalCaricamento();
			
			let url = '${pageContext.request.contextPath}/spuntistimercati/ajaxMercatiSpuntisti.htm?idautorizzazione=${codiceAut}';
			const response = await fetch(url, {
				method : 'GET',
				context: document.body,
				cache: 'no-cache'				
			});

			if(response.status === 200){
				
				const text = await response.text();
				window.vbg.nascondiModalCaricamento();
				document.querySelector('#vbg-modal-dettaglio-mercati-spuntisti').innerHTML = text;
				document.querySelector('#vbg-modal-dettaglio-mercati-spuntisti').open();
				
			}else{
				window.vbg.nascondiModalCaricamento();
				console.log('Errore:',await response.text());
			}
			
		}
		
		let btnVisualizzaDettaglioSpuntista = document.getElementById('btnVisualizzaDettaglioSpuntista');
		if(btnVisualizzaDettaglioSpuntista) {
			btnVisualizzaDettaglioSpuntista.onclick = function(){
				visualizzaDettaglioAutorizSpuntista();
			}
		}
		
		
		function viewDataAffitto(){	
			
			let causale = document.querySelector('#selectCausaleAcquisizione').value;
			dataFineAffittoDisplay(causale);
		}
		
		async function dataFineAffittoDisplay(codice) {
			if(!codice){
			 	document.querySelector('#dataAffitto').hide();			 	
             	return;
			}

			const response = await fetch("${pageContext.request.contextPath}/autorizzazionisubentri/isDataFineAffitto.htm?codiceConcCausale="+codice, {
		        method: "GET",
		        cache: "no-cache"		        
			});
			
			if(response.status === 200){				
				let flag = await response.text();
	            if (flag == 'true') {
	            	document.querySelector('#dataAffitto').show();
	                document.querySelector('#dataAffitto').style.display = 'inline-block';           
	            }
	            else {
	            	document.querySelector('#dataAffitto').hide();	                
	                document.getElementById('dataFineAffitto_id').innerHTML = '';
	            }
	           
			}else{
				
			}		   
		};
		
		
		let selectCaualeAcquisizione = document.getElementById('selectCausaleAcquisizione');
		selectCaualeAcquisizione.onchange = function(){
			dataFineAffittoDisplay(selectCaualeAcquisizione.value);
		}
		
		let btnInsert = document.getElementById('btnInsert');
		if(btnInsert){
			btnInsert.onclick = function(){
				insert();	
			}
		}
		
		let btnUpdate = document.getElementById('btnUpdate');
		if(btnUpdate){
			btnUpdate.onclick = function(){
				update();	
			}
		}
		
		gestFlagAttiva();
		viewDataAffitto();
		
		let mostraFirmatari = '${mostraFirmatari}';
		if( mostraFirmatari != '1' ){
			document.getElementById('elenco-firmatari').style.display = 'none';
		}
		else {
			let firmatarioDefault = '${codiceFirmatario}';
			if( firmatarioDefault != '' ){
				document.getElementById('codiceFirmatario').value = firmatarioDefault;	
			}
		}
		
	});
</script>
</body>
</html>