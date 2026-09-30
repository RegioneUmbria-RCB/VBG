<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
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
<c:if test="${not empty param.codiceIstanza}">
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${param.codiceIstanza}</c:param>
	</c:import>
</c:if>
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
					<table>
						<tr>
							<td colspan="4" class="success_header">
								<fmt:message key="label.l_autorizzazione_e_stata_subentrata_dalla" />&nbsp;								
								${autorizzazioniCommand.subentro.autorizzazioni.transientEstremiAut} 
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="label.autorizzazione_registro" />
								<a name="autorizzazione"></a>
							</td>
							<td colspan="3">
								<spring-form:input id="registro_id" path="subentro.tipologiaregistro.trDescrizione" disabled="true" size="70"/>
							</td>
						</tr>
						
							<tr>
								<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
								<td>
									<spring-form:input id="concessioneNumeroAutorizzazione_id" path="subentro.autoriznumero" size="10" disabled="true" />									
								</td>
								<td>
									<fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" />
								</td>
								<td>
									<spring-form:input id="autorizdata2_id" path="subentro.autorizdata" size="10" disabled="true" /> 								
								</td>
							</tr>
							<tr>
								<td><fmt:message key="label.concessione_scadenza" /></td>
								<td colspan="3">
									<spring-form:input id="concessionescadenza_id" path="subentro.datascadenza" size="10" disabled="true" />									
								</td>
							</tr>							
							<tr>
								<td><fmt:message key="label.concessione_autorizzata_da" /></td>
								<td>
									<spring-form:input id="responsabile_id" size="50" path="subentro.autorizresponsabile" disabled="true"/>								
								</td>
								<td><fmt:message key="label.concessione_autorizzata_il" /></td>
								<td>
									<spring-form:input id="autorizdataregistr_id" path="subentro.autorizdataregistr" size="10" disabled="true"/> 
								</td>
							</tr>
							<tr>
								<td><fmt:message key="label.anagrafe" /></td>
								<td colspan="3">
									<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" disabled="true" /> 

								</td>
							</tr>									
							<tr>
							<td>
								<fmt:message key="label.comune" />
								<a name="autorizzazione"></a>
							</td>
							<td colspan="3">
								<spring-form:input id="comune_id" path="subentro.autorizcomune.comune" disabled="true" size="70"/>								
							</td>
						</tr>				
						<c:if test="${not empty concessionicausalis}">
							<tr id="dati_cessazione_id">
								<td><fmt:message key="label.causale_cessazione" /></td>
								<td>
									<spring-form:select id="selectCausaleCessazione" disabled="true" path="subentro.concessionicausaliByFkAutsubConccausCess.id.codice">
										<spring-form:option value=""></spring-form:option>
										<spring-form:options items="${concessionicausalis}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
									</spring-form:select>
								</td>
								<td><fmt:message key="label.data_cessazione" /></td>
								<td>
									<spring-form:input id="cessazione_id" path="subentro.dataCessazione" size="10" disabled="true"/> 								
								</td>			
							</tr>
						</c:if>
						<c:if test="${empty concessionicausalis}">
							<tr id="dati_cessazione_id">
								<td><fmt:message key="label.data_cessazione" /></td>
								<td colspan="3">
									<spring-form:input id="cessazione_id" path="subentro.dataCessazione" size="10" disabled="true" /> 
								</td>			
							</tr>
						</c:if>						
					</table>
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
					<fieldset><legend>	<a class="<%=styleAutConc %>" 
							id="id_link_autconc" 
							href="javascript:showHidePanel('id_autconc_table', 'id_link_autconc', '<%= WebConstants.CONF_UTENTE_NUOVA_AUT_VIS_AUTORIZZAZIONI %>', '${pageContext.request.contextPath}/images/','div');"	
							title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.autorizzazioni_presenti_nell_istanza"/>">
							<label for="id_link_autconc"><fmt:message key="label.autorizzazioni_presenti_nell_istanza"/></label>
						</a>						
					</a></legend>
					<div id="id_autconc_table" style="<%=displayAutConc%>">
						 <div class="jmesa" >
						 	
							<table class="table">
								<thead class="header">
								<tr>
									<td><fmt:message key="label.numero"/></td>
									<td><fmt:message key="label.data"/></td>
									<td><fmt:message key="label.comune"/></td>
									<td><fmt:message key="label.registro"/></td>
									<td><fmt:message key="label.stato"/></td>
									<td><fmt:message key="label.edit.record"/></td>
								</tr>
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
									<td>${curr_auth.autorizcomune.comune}</td>
									<td>${curr_auth.tipologiaregistro.trDescrizione}</td>
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
						</div>
						</fieldset>
					</c:if>				
				
				</c:if>
		</c:if>		
			<spring-form:form commandName="autorizzazioniCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="autorizzazioniCommand" />
			</jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.autorizzazione_registro" />
						<a name="autorizzazione"></a>
					</td>
					<td colspan="3">
					<%--
						<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
					 --%>	
							<script type="text/javascript">
								function updateRegistro(inputField,listItem){
									var a = listItem.id;
									document.getElementById('registro_id').value = inputField.value;
									document.getElementById('registro_hidden').value = a;
									$('registro_id_choices').fade();
									if(document.getElementById('registro_hidden').value!=''){
										doSubmit('updateRegistroAutorizzazione.htm?codice=${autorizzazioniCommand.entity.id.codice}&'+qstring+'&codiceRegistro=' + document.getElementById('registro_hidden').value,'',document.inviodati);
									}
								}
							</script>
							<spring-form:input id="registro_id" path="entity.tipologiaregistro.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
							<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="updateRegistro"  idHidden="registro_hidden"  idInput="registro_id" inputTitleKey="label.ricerca_tipo_registro"/>
							<spring-form:errors	path="entity.tipologiaregistro.trDescrizione" cssClass="error"	/>
							<spring-form:hidden id="registro_hidden" path="entity.tipologiaregistro.id.codice"  />
						<%--
						</c:if>
						<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
							<spring-form:input id="registro_id" path="entity.tipologiaregistro.trDescrizione" disabled="true" size="70"/>
							<spring-form:errors	path="entity.tipologiaregistro.trDescrizione" cssClass="error"	/>
							<spring-form:hidden id="registro_hidden" path="entity.tipologiaregistro.id.codice"  />
						</c:if>
						 --%>
					</td>
				</tr>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
					<c:if test="${autorizzazioniCommand.registroAutorizzazioneProtocollo eq false}">
						<tr>
							<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
							<td><spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autoriznumero" size="10"  /> 
							<spring-form:errors	path="entity.autoriznumero" cssClass="error" /></td>
							<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
							<td><spring-form:input id="autorizdata2_id" path="entity.autorizdata" size="10" onblur="isValidDate(this,true);" />
							 <init:calendar imagePath="/images/cal.gif"	idImage="cal_autorizdata2_id" idInput="autorizdata2_id" textKey="label.calendar"/> 
							 <spring-form:errors path="entity.autorizdata" cssClass="error" />
							</td>
						</tr>
					</c:if>
					
					<c:if test="${autorizzazioniCommand.registroAutorizzazioneProtocollo eq true}">
						<tr>
							<td colspan="4"><fmt:message key="label.autorizzazione_estremi_da_protocollo" /></td>
						</tr>
						<tr>
							<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
							<td><spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autoriznumero" size="10" readonly="true" /> 
							<spring-form:errors	path="entity.autoriznumero" cssClass="error" /></td>
							<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
							<td><spring-form:input id="autorizdata2_id" path="entity.autorizdata" size="10" readonly="true" />
							  
							 <spring-form:errors path="entity.autorizdata" cssClass="error" />
							</td>
						<%--
							<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
							<td><input type="text" name="_entity.autoriznumero" size="10" value="" disabled="disabled"/> 
							<spring-form:errors	path="entity.autoriznumero" cssClass="error" /></td>
							<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
							<td><input type="text" name="_entity.autorizdata" size="10" disabled="disabled"/>
							<spring-form:errors path="entity.autorizdata" cssClass="error" /></td>
							 --%>
						</tr>
					</c:if>
					
				</c:if>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
					<tr>
						<td><fmt:message key="label.autorizzazione_numero_autorizzazione" /></td>
						<td>
							<spring-form:input id="concessioneNumeroAutorizzazione_id" path="entity.autoriznumero" size="10"/>
							<spring-form:errors	path="entity.autoriznumero" cssClass="error" /></td>
						<td><fmt:message key="label.autorizzazione_data_rilascio_autorizzazione" /></td>
						<td>
							<spring-form:input id="autorizdata2_id" path="entity.autorizdata" size="10" onblur="isValidDate(this,true);" /> 
							<init:calendar imagePath="/images/cal.gif"	idImage="cal_autorizdata2_id" idInput="autorizdata2_id" textKey="label.calendar"/> 
							<spring-form:errors path="entity.autorizdata" cssClass="error" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td><fmt:message key="label.concessione_scadenza" /></td>
					<td colspan="3">
						<spring-form:input id="datascadenza_id" path="entity.datascadenza" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif"	idImage="cal_datascadenza_id" idInput="datascadenza_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.datascadenza" cssClass="error" />
					</td>
				</tr>		
				<tr>
					<td><fmt:message key="label.concessione_autorizzata_da" /></td>
					<td><spring-form:input id="responsabile_id" size="50" path="entity.autorizresponsabile" />
					<spring-form:errors path="entity.autorizresponsabile" cssClass="error" />
					</td>
					<td><fmt:message key="label.concessione_autorizzata_il" /></td>
						<td><spring-form:input id="autorizdataregistr_id" path="entity.autorizdataregistr" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdataregistr_id" idInput="autorizdataregistr_id" textKey="label.calendar" /> 
						<spring-form:errors	path="entity.autorizdataregistr" cssClass="error"/></td>
				</tr>					
				<tr>
					<td>
						<fmt:message key="label.comune" />
						<a name="autorizzazione"></a>
					</td>
					<td colspan="3">
						<spring-form:input id="comune_id" path="entity.autorizcomune.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
						<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="comune_hidden"  minChars="2" idInput="comune_id" inputTitleKey="label.ricerca_comune"/>
						<spring-form:errors	path="entity.autorizcomune" cssClass="error"	/>
						<spring-form:hidden id="comune_hidden" path="entity.autorizcomune.codicecomune"  />
					</td>
				</tr>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
					<c:if test="${not empty param.codiceAnagrafe}">
						<tr>
							<td><fmt:message key="label.anagrafe" /></td>
							<td colspan="3">
								<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" disabled="true" /> 
								<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
								<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" /></td>
						</tr>					
					</c:if>
					<c:if test="${empty param.codiceAnagrafe}">
						<tr>
							<td><fmt:message key="label.anagrafe" /></td>
							<td colspan="3">
								<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
								<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
								<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
								<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" /></td>
						</tr>					
					</c:if>
				</c:if>
				<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
					<c:if test="${not empty param.codiceAnagrafe}">
						<tr>
							<td><fmt:message key="label.anagrafe" /></td>
							<td colspan="3">
								<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" disabled="true" /> 
								<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
								<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" /></td>
						</tr>					
					</c:if>
					<c:if test="${empty param.codiceAnagrafe}">
						<tr>
							<td><fmt:message key="label.anagrafe" /></td>
							<td colspan="3">
							<spring-form:input id="titolare_id" size="70" path="entity.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
							<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
							<spring-form:errors path="entity.anagrafe" cssClass="error" /> 
							<spring-form:hidden id="titolare_hidden" path="entity.anagrafe.id.codice" /></td>
						</tr>					
					</c:if>
				</c:if>
				<tr>
					<td><label for="flag_attiva_id"><fmt:message key="label.concessione_attiva" /></label></td>
					<td colspan="3"><spring-form:checkbox path="entity.flagAttiva" id="flag_attiva_id" onclick="gestFlagAttiva();"/></td>
				</tr>
				<c:if test="${not empty concessionicausalis}">
					<tr id="dati_cessazione_id">
						<td><fmt:message key="label.causale_cessazione" /></td>
						<td><spring-form:select id="selectCausaleCessazione" path="entity.concessionicausaliByFkAutConccausCess.id.codice">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${concessionicausalis}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
						</spring-form:select><spring-form:errors path="entity.concessionicausaliByFkAutConccausCess.id.codice" cssClass="error" /></td>
						<td><fmt:message key="label.data_cessazione" /></td>
						<td><spring-form:input id="cessazione_id" path="entity.dataCessazione" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.dataCessazione" cssClass="error" /></td>			
					</tr>
				</c:if>
				<c:if test="${empty concessionicausalis}">
					<tr id="dati_cessazione_id">
						<td><fmt:message key="label.data_cessazione" /></td>
						<td colspan="3"><spring-form:input id="cessazione_id" path="entity.dataCessazione" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_cessazione_id" idInput="cessazione_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.dataCessazione" cssClass="error" /></td>			
					</tr>
				</c:if>
				<c:if test="${not empty statiIstanzaList}">
					<tr id="dati_chiusura_id">
						<td><fmt:message key="label.tipo_chiusura" /></td>
						<td colspan="3">
							<spring-form:select id="selectChiusuraIstanza" path="statoistanza.id.codicestato">
								<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>
								<spring-form:options items="${statiIstanzaList}" itemLabel="stato" itemValue="id.codicestato"></spring-form:options>
							</spring-form:select>
						</td>			
					</tr>
				</c:if>
			</table>
		</spring-form:form>
	</div>
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
		
	
		function convalida(){
			if(document.getElementById('selectChiusuraIstanza')){
				if(document.getElementById('selectChiusuraIstanza').value==''){
					alert('<fmt:message key="label.tipo_chiusura" /> <fmt:message key="field.required" />');
					document.getElementById('selectChiusuraIstanza').focus();				
					return false;
				}
			}
			return true;	
		}	
	
		</script>
		<div id="functions">
		<ul>
			<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.NEW}">
				<li><a href="javascript:if(convalida()){doSubmit('insertAutorizzazione.htm?'+qstring,'',document.inviodati)}"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.EDIT}">
				<li><a href="javascript:if(convalida()){doSubmit('updateAutorizzazione.htm?codice=${autorizzazioniCommand.entity.id.codice}&'+qstring,'',document.inviodati)}"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteAutorizzazione.htm?codice=${autorizzazioniCommand.entity.id.codice}&'+qstring,'<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				<c:if test="${autorizzazioniCommand.manifestazioniConfigurate eq true}">
				<li><a href="javascript:historySet('${_urlback}','../mercatipresenzestorico/listDaAutorizzazione.htm?autId=${autorizzazioniCommand.entity.id.codice}','')"><fmt:message key="button.presenze" /></a></li>
				</c:if>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
		</div>
	</c:if>
</c:otherwise>
</c:choose>
<c:if test="${autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.DELETED or autorizzazioniCommand.displayMode eq autorizzazioniCommand.displayConstants.READONLY}">
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</c:if>
</body>
</html>