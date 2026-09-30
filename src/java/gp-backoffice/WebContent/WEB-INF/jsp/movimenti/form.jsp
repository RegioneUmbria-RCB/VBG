<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.web.MovimentiCommand" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>	
	<fmt:message key="label.movimenti_istanza" />
</title>
</head>

<body>
<style>
	.data_scadenza{ 
		background-color: lightgrey; 
		padding:5px; 
		background-image: linear-gradient(45deg, #f0f0f0 5.56%, #ffffff 5.56%, #ffffff 50%, #f0f0f0 50%, #f0f0f0 55.56%, #ffffff 55.56%, #ffffff 100%);
		background-size: 12.73px 12.73px; 
		min-width: 250px; 
		border: 2px solid maroon; 
		font-size: 1.1em; 
		font-weight: bold	
	}
	
</style>
<span class="titoloPagina">
	<fmt:message key="label.movimenti_istanza" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
   	<jsp:param name="path" value="../movimenti/view" />
   	<jsp:param name="qs" value="codice%3D${movimentiCommand.entity.id.codice}"/>
</jsp:include>
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${movimentiCommand.entity.istanza.id.codice}</c:param>
</c:import>	
<br class="clear" />
<div id="subcontent">
	<%-- PANNELLO EVENTI --%>
	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">
	    <jsp:include page="../includes/pannelloEventi.jsp" >
	        <jsp:param name="codMov" value="${movimentiCommand.entity.id.codice}" />
	    </jsp:include>
    </c:if>
	<spring-form:form commandName="movimentiCommand" name="inviodati">
<c:if test="${not empty movimentiCommand.entity.amministrazioniStc}">
	<spring-form:hidden id="amministrazione_stc_tipo_hidden" path="entity.amministrazioniStc.id.codice"/>
</c:if>	
	<spring-form:hidden id="codiceoggettonotifica_hidden" path="entity.oggettoNotifica.id.codice"/>
	<spring-form:hidden id="codiceoggettonotifica_hidden_nome_file" path="entity.oggettoNotifica.nomefile"/>
		<c:if test="${empty movimentiCommand.entity.tipomovimento.statoistanza.id.codicestato}">
			<c:if test="${not empty statiistanzaList}">
				<div class="parametriDiv">
					<div class="etichetta">
						<div><fmt:message key="label.stato_attuale_dell_istanza" />:</div>
					</div>		
					<div class="parametro ">       		 	
						<div class="inline-ui-cell">
							<spring-form:select id="statoistanza_id" path="statiistanza.id.codicestato">
								<spring-form:options items="${statiistanzaList}" itemValue="id.codicestato" itemLabel="stato"/>
							</spring-form:select>
							<init:help idHelp="helpChiusuraIstanza" textKey="help.movimenti.chiusura.istanza" />
						</div>
					</div>
				</div>
			</c:if>	
		</c:if>
		<br class="clear"/>								
		
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="movimentiCommand" />
    </jsp:include>
	<table width="100%">
	<c:set var="_URL_BACK" value="../movimenti/view.htm?codice=${movimentiCommand.entity.id.codice}" scope="page"/>
	<c:set var="_URL_BACK_ENCODED" ><%= URLEncoder.encode((String)pageContext.getAttribute("_URL_BACK") ,"UTF-8") %></c:set>	
	<c:set var="VERTICALIZZAZIONE_STC_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_STC)%></c:set>
	<c:set var="VERTICALIZZAZIONE_INFOCAMERA_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_INFOCAMERA)%></c:set>
	<c:set var="readOnly" value="<%= Boolean.FALSE %>" />
	
	<c:if test="${movimentiHelper.movimentoAvvio eq true}">
		<c:set var="readOnly" value="<%= Boolean.TRUE %>" />
	</c:if>
	
	
	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">
		<c:if test="${not empty pecId}">
			<tr>
				<td colspan="4" style="vertical-align: middle;">
					Il movimento è collegato con le seguenti mail/pec:
					<c:forEach items="${pecId}" var="pec"> 
					 	<div style="width: 100%; border: 1px dotted maroon;padding-left: 10px;padding-top: 4px; padding-bottom: 4px;">
					 		inviata da <b>${pec.pecFrom}</b><br /> 
					 		ricevuta il giorno <b><fmt:formatDate value="${pec.pecDate}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" /></b>
					 		<br />con oggetto <b>${pec.pecSubject}</b>
						 	<a class="vbg-btn btn-info" title="Il movimento è collegato con la pec, cliccare per maggiori dettagli" href="javascript:location.href='${pageContext.request.contextPath}/pecinbox/dettaglioPEC.htm?codicePec='+encodeURIComponent('${pec.id.id}')+'&provenienza=MOVIMENTI';">
						 	</a>
					 	</div>
				 	</c:forEach>
				</td>
			</tr>	
		</c:if>
		<tr>
			<td>&nbsp;</td>
			<td colspan="3" class="inline-ui-cell" style="vertical-align: middle;">
				<c:if test="${VERTICALIZZAZIONE_STC_IN_REQUEST eq true}">
					<jsp:include page="../includes/funzioni_stc.jsp">
						<jsp:param name="codiceIstanza" value="${movimentiCommand.entity.istanza.id.codice}" />
						<jsp:param name="codiceMovimento" value="${movimentiCommand.entity.id.codice}" />
						<jsp:param name="funzioneRichiesta" value="richiestaPraticaMovimento" />
						<jsp:param name="returnTo" value="${_urlback}" />
						<jsp:param name="flagStc" value="${movimentiCommand.entity.tipomovimento.flagStc}" />
						<jsp:param name="inviatoConStc" value="${movimentiCommand.entity.inviatoConStc}" />
						<jsp:param name="creatoDaStc" value="${movimentiCommand.entity.creatoDaStc}" />
						<jsp:param name="idAttDest" value="${movimentiCommand.entity.idAttDest}" />
						<jsp:param name="statoAttDest" value="${movimentiCommand.entity.statoAttDest}" />
					</jsp:include>
					<c:if test="${not empty movimentiCommand.entity.oggettoNotifica.id.codice }">
						<a class="vbg-stack elab_image elab_image_38" title="<fmt:message key="label.visualizza_dettaglio_notifica_stc" />" href="javascript:historySet('${_URL_BACK_ENCODED}','../movimenti/dettaglioNotifica.htm?codicemovimento=${movimentiCommand.entity.id.codice}')">
							<i class="vbg-stack-btn btn-dettaglio-notifica"></i>
						</a>
					</c:if>
				</c:if>
				<c:if test="${VERTICALIZZAZIONE_INFOCAMERA_IN_REQUEST eq true}">
					<jsp:include page="../includes/funzioni_infocamere.jsp">
						<jsp:param name="codiceMovimento" value="${movimentiCommand.entity.id.codice}" />
						<jsp:param name="flagCamcom" value="${movimentiCommand.entity.tipomovimento.flagCamcom}" />
						<jsp:param name="inviatoACamcom" value="${movimentiCommand.entity.inviatoACamcom}" />						
					</jsp:include>
				</c:if>
				<%--
				<c:if test="${not empty movimentiCommand.entity.numprotMittente or not empty movimentiCommand.entity.dataProtMittente}">					
					<a title="<fmt:message key="label.movimenti_visualizza_dettagli_protocollo_mittente" />" href="javascript: void 0" onclick="dijit.byId('dettaglioProtMittDiv').show();"><img alt="<fmt:message key="label.movimenti_visualizza_dettagli_protocollo_mittente" />" 
						style="vertical-align: middle;" border="0" src="${pageContext.request.contextPath}/images/info.gif" /></a>
					<div dojoType="dijit.Dialog" id="dettaglioProtMittDiv" style="overflow: inherit;" title="<fmt:message key="label.info_dettagli_protocollo_mittente" />">
						<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 300px; height: 100px;">
								<div class="parametriDiv">
										<div class="etichetta">
											<div><fmt:message key="label.numero_protocollo_mittente" />:</div>
										</div>		
										<div class="parametro">       		 	
											<div>
													${movimentiCommand.entity.numprotMittente}										
											</div>
										</div>
								</div>	
								<div class="parametriDiv">
								<div class="etichetta">
											<div><fmt:message key="label.data_protocollo_mittente" />:</div>
										</div>		
										<div class="parametro">       		 	
											<div>
												<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${movimentiCommand.entity.dataProtMittente}" />
											</div>
										</div>	
								</div>										
						</div>
					</div>
					
				</c:if>
				 --%>
				
				<c:if test="${not empty movimentiCommand.entity.movimentiAttis}">
				    <c:set scope="page" value="0" var="isRicevuto"></c:set>
				    <c:forEach items="${movimentiCommand.entity.movimentiAttis}" var="movAtti">
				        <c:if test="${movAtti.dataRicezioneAtto != null}">
				         	<c:set scope="page" value="1" var="isRicevuto"></c:set>
				        </c:if>
				    
				    </c:forEach>
					<c:if test="${isRicevuto=='0'}">
					&nbsp;
					<a class="vbg-btn btn-a" href="javascript:void(0);" title="<fmt:message key="movimentimail.label.ricerca_atto.title" />"
							onclick="ricercaAtto('${movimentiCommand.entity.id.codice}')">  
					</a>
							<div dojoType="dijit.Dialog" id="ricercaAttoDiv"	title="<fmt:message key="movimentimail.label.ricerca_atto.title" />" ></div>
					</c:if>
					<c:if test="${isRicevuto eq '1'}">
					&nbsp;
					<a class="vbg-btn btn-a-verde"  href="javascript:void(0);" title="<fmt:message key="movimentimail.label.ricerca_atto.title" />"
							onclick="ricercaAtto('${movimentiCommand.entity.id.codice}')">  
					</a>
					<div dojoType="dijit.Dialog" id="ricercaAttoDiv"	title="<fmt:message key="movimentimail.label.ricerca_atto.title" />" ></div>
					</c:if>
				</c:if>
			</td>
		</tr>
	</c:if>	
		<tr>
			<td><fmt:message key="label.tipomovimento" /></td>
			<td colspan="3" class="inline-ui-cell">
			<script type="text/javascript">
				function checkTipomovimento(inputField,listItem){
					var idElemento='tipoMovimentoInputId';
					var a = listItem.id;
					document.getElementById(idElemento+'_hidden').value = a;
					if($('id2_'+idElemento).style.display == 'inline'){
						document.getElementById(idElemento+'_id2').value = inputField.value;
						$(idElemento+'_id2'+'_choices').fade();
					}else{
						document.getElementById(idElemento+'_id1').value = inputField.value;
						$(idElemento+'_id1'+'_choices').fade();
					}
					checkEsito(a);					
				}				
				function checkEsito(tipoMovimento){
						new Ajax.Request('../tipimovimento/ajaxEsitoTipomovimento.htm', {
						  method: 'post',
						  parameters: {tipoMovimento: tipoMovimento},
						  onSuccess: function(transport){
							  var response = transport.responseText;
							  result = response.split("#");
							  if(result[0]!='0'){
							   	  document.getElementById("div_esito_id").style.display='';
							  }else{
								  document.getElementById("div_esito_id").style.display='none';
							  }
							  <c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.NEW}">
							  if(document.getElementById('pubblica_id')){
								  if(result[1]=='1'){
									  document.getElementById('pubblica_id').checked=true;
								  }else{
									  document.getElementById('pubblica_id').checked=false;
								  }
							  }
							  if(document.getElementById('pubblicaparere_id')){
								  if(result[2]=='1'){
									  document.getElementById('pubblicaparere_id').checked=true;
								  }else{
									  document.getElementById('pubblicaparere_id').checked=false;
								  }
							  }
							  </c:if>
						  },
						  onFailure: function(transport){ 
							var response = transport.responseText;
						    alert(response); 
						    }						    		 
					} );			
				}			
			</script>
				<jsp:include page="../includes/tipimovimentosearch.jsp" >
					<jsp:param name="idElemento" value="tipoMovimentoInputId" />
					<jsp:param name="pathTipomovimento" value="entity.tipomovimento" />
					<jsp:param name="afterUpdateElement" value="checkTipomovimento" />
					<jsp:param name="readOnly" value="${readOnly}" />					
				</jsp:include>					
			</td>					
		</tr>
		<tr>
			<td><fmt:message key="label.movimento" /></td>
			<td colspan="3" class="inline-ui-cell">
				<spring-form:input id="movimento_id" path="entity.movimento" size="70" readonly="${readOnly}" />
				<c:set var="helpMovimentoText"><fmt:message key="label.selasciatovuoto" />&nbsp;<fmt:message key="label.tipomovimento" /></c:set>
				<init:help idHelp="helpMovimento" text="${helpMovimentoText}" />				
				<spring-form:errors path="entity.movimento" cssClass="error"/>
			</td>
		</tr>	
		
		
		<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnEndoprocedimenti')}">
						
		
		<c:if test="${movimentiCommand.inserimentoVeloce eq false}">
			<tr>			
				<td>
					<fmt:message key="label.inventarioprocedimento" />
				</td>
				<td colspan="3" class="inline-ui-cell">
					
					<jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="inventarioprocedimento" />					
						<jsp:param name="propertyPath" value="entity.endoprocedimento" />	
						<jsp:param name="pathPropertyDescription" value="entity.endoprocedimento.procedimento" />
						<jsp:param name="pathPropertyCode" value="entity.endoprocedimento.id.codice" />
						<jsp:param name="autocompleterAjax" value="findIstanzeprocedimentiEndo.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}" />
						<jsp:param name="titleKey" value="label.ricerca_inventarioprocedimento" />
						<jsp:param name="readOnly" value="${readOnly}" />		
					</jsp:include>
					<c:if test="${movimentiCommand.entity.endoprocedimento.id.codice !=null}">
						<span id="functions">
							<ul>
								<li><a href="javascript:void(0)" onclick="javascript:tabMessaggioEndo('dettaglioDialogDiv${movimentiCommand.entity.endoprocedimento.id.codice}','${movimentiCommand.entity.endoprocedimento.id.codice}','${movimentiCommand.entity.istanza.id.codice}','${movimentiCommand.entity.id.codice}');"><fmt:message key="button.estremi_atto" /></a></li>
							</ul>
						</span>	
					</c:if>
						<div dojoType="dijit.Dialog" id="dettaglioDialogDiv${movimentiCommand.entity.endoprocedimento.id.codice}" title="<fmt:message key="label.atto" />: ">
							<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 550px; height: 350px;">
								<div id="dettaglio${movimentiCommand.entity.endoprocedimento.id.codice}"></div>
							</div>
							<!-- Permette di sovrascivere l'evento di chiusura del dialog e in più aggiungere altre funzioni 
							     In questo caso alla chiusa del dialog chiude anche il calendare se era aperto-->
							<script type="text/javascript">
								dojo.addOnLoad(function() {
									myDialog = dijit.byId("dettaglioDialogDiv${movimentiCommand.entity.endoprocedimento.id.codice}");
									dojo.connect(myDialog, "hide", function(){
							    	// alla chiusura del div di dialog devo chiudere anche il calendar se è ancora aperto
								    	if (typeof calendar != "undefined"){
								    		calendar.hide();
										}
							        });
								});
								
								function tabMessaggioEndo(divId, id, istanza,codicemovimento){
									
										dijit.byId(divId).show();
									    dettaglioTab(id, istanza,codicemovimento);		
								}
								
								function dettaglioTab(id, istanza,codicemovimento) {
										
										new Ajax.Request(
												'${pageContext.request.contextPath}/ajax/dettaglioIstanzaprocedimento.htm?isCallFromMovimenti=true&codiceinventario='+ id+ '&codiceistanza='+ istanza+'&codiceMovimento='+ codicemovimento,
												{
													method : 'post',
													onSuccess : function(transport) {							
														var response = transport.responseText;
														
														//$("dettaglio" + id).innerHTML = parseScript(response);
														$("dettaglio" + id).innerHTML =parseAjaxResponse(response, false, true);
														applyStyle();
													},
													onFailure : function(transport) {
														var response = transport.responseText;
														alert(response);
													}
												});
									}
								
								function setupCal(inputId, imageId){
									RANGE_CAL_1 = new Calendar({
											inputField: inputId,
											dateFormat: "%d/%m/%Y",
											trigger: imageId,
											bottomBar: false,
											onSelect: function() {
										var date = Calendar.intToDate(this.selection.get());
										this.hide();
									}
									})
								
								}
	
							</script>
						</div>
						    
					
				</td>
			</tr>		
			<tr>
				<td>
					<fmt:message key="label.amministrazione" />
				</td>			
				<td colspan="3" class="inline-ui-cell">
				    <jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="amministrazioni" />					
						<jsp:param name="propertyPath" value="entity.amministrazioni" />	
						<jsp:param name="pathPropertyDescription" value="entity.amministrazioni.amministrazione" />
						<jsp:param name="pathPropertyCode" value="entity.amministrazioni.id.codice" />
						<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />
						<jsp:param name="titleKey" value="label.ricerca_amministrazione" />	
						<jsp:param name="readOnly" value="${readOnly}" />							
					</jsp:include>
					
					<div id="amministrazione_collegata_id" style="display: none;">
							<a href="javascript: void 0" onclick="amministrazioneCollegata()" 											
											aria-hidden="true" 
											class="form-group fa fa-link"><fmt:message key="label.amministrazione_collegata" /></a>
					</div>
								    
				</td>		
			</tr>
			<tr>
				<td>
					<fmt:message key="label.ufficio" />
				</td>			
				<td colspan="3" class="inline-ui-cell">
					<script type="text/javascript">
						function filterAmministrazione(element, entry) { 
							if(document.getElementById("amministrazioni_hidden").value!=''){
								return entry + "&codiceAmministrazione=" + document.getElementById("amministrazioni_hidden").value;
							}else{
								alert('<fmt:message key="javascript.alert.selezionare_amministrazione" />');
								return entry + "&codiceAmministrazione=-100000";
							}
						}
					</script>
				    <jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="ufficio" />					
						<jsp:param name="propertyPath" value="entity.amministrazionireferenti" />	
						<jsp:param name="pathPropertyDescription" value="entity.amministrazionireferenti.ufficio" />
						<jsp:param name="pathPropertyCode" value="entity.amministrazionireferenti.id.codice" />
						<jsp:param name="autocompleterAjax" value="findAmmnistrazionireferenti.htm" />
						<jsp:param name="titleKey" value="label.ricerca_ufficio" />
						<jsp:param name="ajaxCallBack" value="filterAmministrazione" />
						<jsp:param name="readOnly" value="${readOnly}" />
					</jsp:include>			    
				</td>		
			</tr>
		</c:if>
		<%-- SETTO LA DATA DI DEFAULT 
				NON LO POSSO FARE SUL CONTROLLER ALTRIMENTI HIBERNATE ESEGUE UN UPDATE 
		--%>
		
		
		</c:if>
		
		<fmt:formatDate var="data_movimento" scope="page" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${movimentiCommand.entity.data}"/>
		<fmt:formatDate var="ora_movimento" scope="page" pattern="HH:mm" value="${movimentiCommand.entity.data}"/>
		
		<c:if test="${movimentiCommand.displayMode ne movimentiCommand.displayConstants.NEW_SCADENZA}">
			<c:if test="${empty movimentiCommand.entity.data}">
				<c:if test="${!isNonProponiData}">
					<fmt:formatDate var="data_movimento" scope="page" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="<%=new java.util.Date()%>"/>
					<fmt:formatDate var="ora_movimento" scope="page" pattern="HH:mm" value="<%=new java.util.Date()%>"/>
				</c:if>
			</c:if>
		</c:if>
		<tr>
			<td><fmt:message key="label.data" /></td>
			<td class="inline-ui-cell" style="min-width: 300px;">
					<%--
						la data del movimento sarà readonly se:
						1: E' MOVIMENTO DI AVVIO (VARIABILE readOnly)
						2: E' MOVIMENTO CHE E' STATO NOTIFICATO TRAMITE STC (VARIABILE readOnlyMovStc): IN QUESTO CASO LA DATA/ORA VENGONO IMPOSTATE A QUELLA ODIERNA DALLA NOTIFICA STC
					 --%>
					<c:set var="dataReadonly" value="${readOnly}"></c:set>
					<c:set var="titleNonModificabile" value=""></c:set>
					<c:if test="${ dataReadonly eq true}">
						<c:set var="titleNonModificabile" value="La data non è modificabile in quanto movimento di avvio dell'istanza"></c:set>
					</c:if>
					<c:if test="${ dataReadonly eq false}">
						<c:set var="dataReadonly" value="${readOnlyMovStc}"></c:set>
						<c:set var="titleNonModificabile" value="La data non è modificabile in quanto l'attività è stata notificata ad altro modulo"></c:set>
					</c:if>
					
					
				
					<c:choose>
					<c:when test="${ dataReadonly eq false}">
					
					<spring:bind path="entity.data">
							<input type="text" size="10" id="data_id" maxlength="10" name="${status.expression}" value="${data_movimento}"  onblur="isValidDate(this,true);" />
					</spring:bind>

						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.data" cssClass="error"/>
					
					<spring:bind path="entity.oraInserimento">
							<input type="text" size="6" maxlength="5" name="${status.expression}" value="${ora_movimento}"  onblur="checkTime(this,true)"/>
					</spring:bind>
						
					</c:when>
					<c:otherwise>
						<b>
						<fmt:formatDate value="${movimentiCommand.entity.data}" pattern="dd/MM/yyyy" /> -
						<c:out value="${movimentiCommand.entity.oraInserimento}"></c:out>
						</b> 
						<init:help idHelp="datareadonlyhelp" text="${titleNonModificabile}"/>
					</c:otherwise>
					</c:choose>	
			</td>

			<td colspan="2"width="10%" class="inline-ui-cell data_scadenza" style="">
				 <fmt:message key="label.data_scadenza" />
<br />
				<spring-form:input id="dataScadenza_id" path="entity.dataScadenza" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldataScadenza" idInput="dataScadenza_id" textKey="label.calendar"/>
				<spring-form:errors path="entity.dataScadenza" cssClass="error"/>
				<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW or movimentiCommand.displayMode eq movimentiCommand.displayConstants.NEW}">					
					<c:if test="${not empty movimentiCommand.entity.id.codice}">
						<a class="vbg-btn btn-salva" id="mod_scadenza_id" href="javascript:aggiornaScadenza()" title="<fmt:message key="label.movimenti_aggiorna_scadenza" />">
						</a>
						<script type="text/javascript">
							function aggiornaScadenza(){
								doSubmit('updateScadenza.htm','', document.inviodati);
							}
						</script>
					</c:if>
				</c:if>
			</td>
		</tr>		
		<tr>
			<td><fmt:message key="label.numero_protocollo" /></td>
			<td class="inline-ui-cell" style="min-width: 300px;">
			
				<c:set var="readonly_protocollo" value="false"></c:set>
				<c:if test="${PROTOCOLLO_READONLY eq true or  readOnly eq true}">
					<c:set var="readonly_protocollo" value="true"></c:set>
				</c:if>
				
			
				<spring-form:input id="numeroprotocollo_id" path="entity.numeroprotocollo" size="10" readonly="${readonly_protocollo}" cssStyle="float:left;"/>
				<spring-form:errors path="entity.numeroprotocollo" cssClass="error"/>		
			</td>
			<td class="inline-ui-cell" style="min-width: 110px;"><fmt:message key="label.data_protocollo" /></td>
			<td class="inline-ui-cell">
				<spring-form:input id="dataprotocollo_id" path="entity.dataprotocollo" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${readonly_protocollo}"/>
				<c:if test="${movimentiHelper.movimentoAvvio eq false}">
					<c:if test="${readonly_protocollo eq false }">
						<init:calendar imagePath="/images/cal.gif" idImage="caldataprotocollo" idInput="dataprotocollo_id" textKey="label.calendar"/>
					</c:if>
					<spring-form:errors path="entity.dataprotocollo" cssClass="error"/>
				</c:if>
			</td>
		</tr>
		<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">
		<c:set var="VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IN_REQUEST"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)%></c:set>				
			<c:choose>
				<c:when test="${VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IN_REQUEST eq true}">		
				<tr>
					<td>&nbsp;</td>
					<td colspan="3" class="inline-ui-cell">					
					<span id="functions">
						<ul>				
							<c:if test="${empty movimentiCommand.entity.numeroprotocollo}">
								<c:if test="${readOnly eq false}">
									<%-- //FIXME verificare se il controllo di fkidprotocollo è corretto --%>
									<c:if test="${empty movimentiCommand.entity.fkidprotocollo}">
										<li><a href="javascript:historySet('${_urlback }','../protocollazione/create.htm?provenienza=M&codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}','');"><fmt:message key="button.protocolla"/></a></li>
										<c:if test="${metti_alla_firma != null }">
										<li><a href="javascript:historySet('${_urlback }','../protocollazione/create.htm?mettiallafirma=true&provenienza=M&codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}','');"><fmt:message key="button.metti_alla_firma"/></a></li>
										</c:if>
									</c:if>
									<c:if test="${not empty movimentiCommand.entity.fkidprotocollo}">
										<c:if test="${metti_alla_firma != null }">
										<li class="buttondisabled"><a title="<fmt:message key="label.movimento_messo_alla_firma"/>"><fmt:message key="button.protocolla"/></a></li>
										<li class="buttondisabled"><a title="<fmt:message key="label.movimento_messo_alla_firma"/>"><fmt:message key="button.metti_alla_firma"/></a></li>
										</c:if>
										<c:if test="${metti_alla_firma == null }">
										<li class="buttondisabled"><a><fmt:message key="button.protocolla"/></a></li>
										</c:if>
									</c:if>
								</c:if>
							</c:if>
							<c:if test="${not empty movimentiCommand.entity.numeroprotocollo}">
								<%-- LEGGI PROTOCOLLO --%>
								<c:set var="_VISUALIZZABOTTONELEGGI"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI)%></c:set>
								<c:if test="${_VISUALIZZABOTTONELEGGI eq '1' }">
									<li><a href="javascript:historySet('${_urlback }','../protocollazione/leggiProtocollo.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}','');" title="<fmt:message key="label.leggi_protocollo"/>"><fmt:message key="label.leggi"/></a></li>
								</c:if>
								<%-- ACCETTA PROTOCOLLO --%>
								<c:if test="${accettaProtocolloMovimenti eq '1'}">
									<li id="accettaprotocollobutton_id"><a
														href="javascript:accettaProtocollo('${movimentiCommand.entity.id.codice}')"><fmt:message key="label.accetta_protocollo" /></a></li>
								</c:if>
								<c:if test="${accettaProtocolloMovimenti eq 'KO' }">
														<li id="link_is_esitato"><a
															class="vbg-btn btn-avvisi" href="javascript:void(0);"
															style="cursor: pointer;"
															onclick="dijit.byId('infoIsEsitato').show();"
															title="${erroreTitoloAccettaProtocollo }"> </a></li>
														<div id="infoIsEsitato" dojoType="dijit.Dialog"
															title="${erroreTitoloAccettaProtocollo }"
															style="display: none;">
															<div id="infoIsEsitato_content">
																<c:out value="${erroreMessaggioAccettaProtocollo}" escapeXml="true" />
															</div>
														</div>
													</c:if>								
								<c:set var="_VISUALIZZABOTTONESTAMPA"><%=request.getAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA)%></c:set>
								<c:if test="${_VISUALIZZABOTTONESTAMPA eq '1' }">
									<li><a href="javascript:historySet('${_urlback }','../protocollazione/stampaView.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}','');" title="<fmt:message key="label.stampa"/>"><fmt:message key="label.stampa"/></a></li>
								</c:if>
							</c:if>
								<c:if test="${isDocEr eq true}">
									<li><a href="javascript:historySet('${_urlback }','../protocollazione/createUnitaDocumentale.htm?provenienza=M&codiceMovimento=${movimentiCommand.entity.id.codice}','');"><fmt:message key="label.riversa_documenti_docer"/></a></li>
									<li><a href="javascript:historySet('${_urlback }','../protocollazione/create.htm?provenienza=M&codiceMovimento=${movimentiCommand.entity.id.codice}&registrazioneDocer=true','');"><fmt:message key="label.registrazione_docer"/></a></li>
								</c:if>							
					</ul>
					</span>
							<c:if test="${not empty movimentiCommand.entity.numeroprotocollo}">
											<%-- ANNULLA PROTOCOLLO --%>
											<%--  ANNULLA PROTOCOLLO VENIVA USATO SOLO CON PROTOCOLLO SIGEPRO E IRIDE MA IRIDE NON LO USA
											<span id="link_annulla_protocollo" style="display: none;">
												<a href="javascript:historySet('${_urlback }','../protocollazione/annullaProtocolloView.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}','');"><fmt:message key="label.annulla_protocollo"/></a>
											</span>
											 --%>
											
											<span id="link_protocollo_annullato" style="display: none;">
												<a class="warning_image" href="javascript:void(0);" onclick="dijit.byId('infoProtocollo').show();"  title="<fmt:message key="label.messaggi_annulla_protocollo"/>"><label>warn</label></a>
											</span>
											<div id="infoProtocollo" dojoType="dijit.Dialog" title="<fmt:message key="label.messaggi_annulla_protocollo"/>" style="display: none;">
												<div id="infoProtocollo_content"></div>
											</div>
											<script type="text/javascript">
												function checkProtocolloAnnullato(){											
													var jhqrPr = jQuery.ajax({
														  url: '../protocollazione/ajaxCheckProtocolloAnnullato.htm',
														  context: document.body,
														  cache: false,
														  data: "codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}",
														  dataType: "html",
														  success: function(data) {
															  if(data!=''){
																$('infoProtocollo_content').innerHTML=data;
																$('link_protocollo_annullato').style.display='';
																applyStyle();
															  }else{
																 // $('link_annulla_protocollo').style.display='';
															  }
															},
															error: function(jqXHR, textStatus, errorThrown){
																alert(jqXHR.responseText);														
															}
														});		
												}	
												jQuery(document).ready(function () {
													checkProtocolloAnnullato();
												});	
											</script>
								</c:if>
								<%-- ANNULLA PROTOCOLLO gestisciFascicolo --%>		
								<%-- FASCICOLA 	--%>		
								<c:if test="${gestisciFascicolo eq true }">	
										&nbsp;							
										<span id="link_crea_fascicolo"  style="display: none;">
										<span id="functions">
										<ul>
											<li><a href="javascript:historySet('${_urlback }','../protocollazione/creaFascicoloView.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}&provenienza=<%=ProtocollazioneCommand.PROVENIENZA_MOVIMENTI%>','');" title="<fmt:message key="label.crea_fascicolazione"/>"><fmt:message key="label.crea_fascicolazione"/></a></li>
										</ul>	
										</span>	
										<span id="link_messaggi_fascicolo" style="display: none;">
											<a id="link_fascicolo_href" class="info_image" href="javascript:void(0);" onclick="dijit.byId('infoFascicolo').show();" title="<fmt:message key="label.messaggi_fascicolazione"/>"><label>info</label></a>
										</span>
										<div id="infoFascicolo" dojoType="dijit.Dialog" title="<fmt:message key="label.messaggi_fascicolazione"/>" style="display: none;">
											<span id="infoFascicolo_content"></span>
										</div>							
										<script type="text/javascript">
											function checkProtocolloFascicolato(){											
												var jhqrPr = jQuery.ajax({
													  url: '../protocollazione/ajaxCheckFascicolato.htm',
													  context: document.body,
													  cache: false,
													  data: "codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}",
													  dataType: "html",
													  success: function(data) {
														  if(data!=''){
															  if(data=='no'){
																$('link_crea_fascicolo').style.display='';
															  }else{
																  if(data.match("^AVVERTIMENTO")){
																	$('link_fascicolo_href').className='warning_image';
																	data = data.replace('AVVERTIMENTO','');
																  }
																$('infoFascicolo_content').innerHTML=data;
																$('link_messaggi_fascicolo').style.display='';
																applyStyle();
															  }														
														  }
														},
														error: function(jqXHR, textStatus, errorThrown){
															$('infoFascicolo_content').innerHTML=jqXHR.responseText;
															$('link_messaggi_fascicolo').style.display='';									
														}
													});		
											}	
											jQuery(document).ready(function () {
												checkProtocolloFascicolato();
											});
									</script>
								</c:if>
								
							<%-- FASCICOLA --%>
							
					
					
					</td>
				</tr>
				</c:when>
			</c:choose>	
			<!-- Sezioni riferimenti protocollo -->
			
			<!-- Gestisce la visisibilità> -->
			<c:set scope="page" value="${movimentiCommand.entity.endoprocedimento.id.codice!=null &&  (movimentiCommand.entity.creatoDaStc == null || movimentiCommand.entity.creatoDaStc eq false) && movimentiHelper.movimentoAvvio eq false }" var="readOnlyRifProtMitt"></c:set>
			<c:choose>
				<c:when test="${readOnlyRifProtMitt}">
					<c:set scope="page" value="true" var="isVisibile"></c:set>
				</c:when>
			    <c:otherwise>
			  
			        <c:choose>
			        	<c:when test="${not empty movimentiCommand.entity.numprotMittente}">
			        		<c:set scope="page" value="true" var="isVisibile"></c:set>
			        	</c:when>
			            <c:when test="${movimentiCommand.entity.dataProtMittente!=null}">
			        		<c:set scope="page" value="true" var="isVisibile"></c:set>
			        	</c:when>
			            <c:otherwise>
			            	<c:set scope="page" value="false" var="isVisibile"></c:set>
			            </c:otherwise>
			        </c:choose>
			        
			        
			    </c:otherwise>
			</c:choose>
				<c:if test="${isVisibile}">
				<tr>
				    <td colspan="4" class="titoloSezione"><fmt:message key="label.riferimenti_protocollo_mittente" /></td>
				</tr>
				<tr>
				<td><fmt:message key="label.numero_protocollo" /></td>
				<td class="inline-ui-cell">
					<spring-form:input id="numeroprotocollomitt_id" path="entity.numprotMittente" size="10" readonly="${!readOnlyRifProtMitt}" cssStyle="float:left;"/>
					<spring-form:errors path="entity.numprotMittente" cssClass="error"/>
							
				</td>
				<td><fmt:message key="label.data_protocollo" /></td>
				<td class="inline-ui-cell">
				    <spring-form:input id="dataprotocollomitt_id" path="entity.dataProtMittente" size="10" onblur="isValidDate(this,true);" readonly="${!readOnlyRifProtMitt}" />
					<c:if test="${readOnlyRifProtMitt}">
						<init:calendar imagePath="/images/cal.gif" idImage="caldataprotocollomitt" idInput="dataprotocollomitt_id" textKey="label.calendar"/>
						<spring-form:hidden path="entity.dataProtMittente" id="dataprotocollomitt_id" />
					</c:if>
					<%-- 
					<spring-form:input id="dataprotocollomitt_id" path="entity.dataProtMittente" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${readOnly}"/>
					<init:calendar imagePath="/images/cal.gif" idImage="caldataprotocollomitt" idInput="dataprotocollomitt_id" textKey="label.calendar"/>
					<spring-form:errors path="entity.dataProtMittente" cssClass="error"/>
					--%>
				</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="4" style="font-size: 1px;">&nbsp;</td>
				</tr>				
				</c:if>
				
		</c:if>
		<c:set var="displayEsito">display:</c:set>	
		<c:if test="${movimentiCommand.entity.tipomovimento.tipologiaesito eq 0}">
			<c:set var="displayEsito">display:none;</c:set>
		</c:if>
			<tr style="${displayEsito}" id="div_esito_id">
				<td><fmt:message key="label.esito_positivo" /></td>
				<td colspan="3" class="inline-ui-cell">
					<spring-form:select id="esito_id" path="entity.esito">
						<spring-form:option value="true"><fmt:message key="label.si" /></spring-form:option>
						<spring-form:option value="false"><fmt:message key="label.no" /></spring-form:option>					
					</spring-form:select>
					<spring-form:errors path="entity.esito" cssClass="error"/>
				</td>
			</tr>
		
		
		<!-- ---------------------- INFO AGGIUNTIVE PER UN MOVIMENTO CREATO DA UNA COMMISSIONE -------------------------------- -->
		<!-- --------------------------------------------------START----------------------------------------------------------- -->
		<c:if test="${movimentiCommand.commissioniedilizieR.id.codice!=null}">
		<tr>
			<td>&nbsp;</td>
			<td colspan="3" >
			<div class="vbg-form">
				<fieldset>
					<legend><fmt:message key="label.dettaglio_commissione_edilizia"/></legend>
					<table cellspacing="3" cellpadding="1">	
						<tr>
							<td style="font-weight:bold; color:#696969;">
								<fmt:message key="label.numero_commissione" />
							</td>
							<td>
								<a href="../commissioniediliziet/listCommissioniedilizieR.htm?codiceCommissione=${movimentiCommand.commissioniedilizieR.commissioniedilizieT.id.codice}">${movimentiCommand.commissioniedilizieR.commissioniedilizieT.numprotocollo}</a>
							</td>
							<td style="font-weight:bold; color:#696969;">
								<fmt:message key="label.del" />
							</td>
							<td>
							    <fmt:formatDate value="${movimentiCommand.commissioniedilizieR.commissioniedilizieT.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
							</td>
						</tr>
						<tr>
							<td style="font-weight:bold; color:#696969;">
								<fmt:message key="label.o_d_g" />:
							</td>
							<td colspan="3">	
								${movimentiCommand.commissioniedilizieR.ordine}
							</td>
						</tr>
						<tr>
							<td style="font-weight:bold; color:#696969;">
								<fmt:message key="label.tipo_parere" />:
							</td>
							<td colspan="3">	
								${movimentiCommand.commissioniedilizieR.commedilizieTipopareri.descrizione}
							</td>
						</tr>
					</table>
				</fieldset>
				</div>
			</td>
		</tr>
		</c:if>
		<!-- ---------------------- INFO AGGIUNTIVE PER UN MOVIMENTO CREATO DA UNA COMMISSIONE -------------------------------- -->
		<!-- --------------------------------------------------END----------------------------------------------------------- -->
		<c:if test="${movimentiCommand.inserimentoVeloce eq false}">
			<tr>
				<td><fmt:message key="label.parere" /></td>
				<td colspan="3" class="inline-ui-cell">
					<spring-form:textarea id="parere_id" path="entity.parere" cols="100" rows="9" />				
					<spring-form:errors path="entity.parere" cssClass="error"/>				
				</td>
			 </tr>
			 <tr>
			 	<td><fmt:message key="label.pubblica_parere" /></td>
			 	<td colspan="3" valign="top" class="inline-ui-cell">
					<spring-form:checkbox id="pubblicaparere_id" path="entity.pubblicaparere" />
					<c:set var="helpPubblicaparereText"><fmt:message key="help.pubblica_parere" /></c:set>
					<init:help idHelp="helpPubblicaparere" text='${helpPubblicaparereText }' />
				</td>
			 </tr>
		 </c:if>
		 
		 <tr>
			<td><fmt:message key="label.note" /></td>
			<td colspan="3" class="inline-ui-cell">
				<c:if test="${movimentiCommand.entity.creatoDaStc eq false or empty movimentiCommand.entity.creatoDaStc}">
						<spring-form:textarea id="note_id" path="entity.note" cols="100" rows="5" />
						<spring-form:errors path="entity.note" cssClass="error"/>
				</c:if>
				<c:if test="${movimentiCommand.entity.creatoDaStc eq true}">
						<spring-form:textarea id="note_readonly_id" path="entity.note" cols="100" rows="5" readonly="true"/>
						<spring-form:hidden id="note_hidden" path="entity.oldNota"/>
						<spring-form:errors path="entity.note" cssClass="error"/>
						<input type="checkbox" id="ckb_note" value="true" name="entity.changeNote" onchange="attivaCampoNote();">
						<init:help idHelp="helpmodificanota" textKey="help.modifica_note_movimento" />
					<script type="text/javascript">
					   function attivaCampoNote()
					   	{
						   	var note=document.getElementById('note_hidden').value;
						   	if(document.getElementById('ckb_note').checked)
						   	{
						   		alert('Attenzione, ora sarà possibile modificare le note. La modifica sarà registrata dal sistema');
	        					document.getElementById('note_readonly_id').readOnly=false;
	        					
					   		}else
					   		{
					   			document.getElementById('note_readonly_id').readOnly=true;
					   			document.getElementById('note_readonly_id').value=note;
					   		}
					   	}	
					</script>
				</c:if>
			</td>
		 </tr>
		 <c:if test="${movimentiCommand.inserimentoVeloce eq false}">
			 <tr>
				<td><fmt:message key="label.pubblica" /></td>
				<td colspan="3" class="inline-ui-cell">
					<spring-form:checkbox id="pubblica_id" path="entity.pubblica" />
					<c:set var="helpPubblicaText"><fmt:message key="help.pubblica_movimento" /></c:set>
					<init:help idHelp="helpPubblica" text='${helpPubblicaText }' />
					<spring-form:errors path="entity.pubblica" cssClass="error"/>
				</td>
			 </tr>
			 <tr>
				<td><fmt:message key="label.movimento_da_visionare" /></td>
				<td colspan="3" class="inline-ui-cell">
					<spring-form:checkbox id="flagDaLeggere_id" path="entity.flagDaLeggere" />
					<c:set var="helpFlagDaLeggereText"><fmt:message key="help.visiona_movimento" /></c:set>
					<init:help idHelp="helpFlagDaLeggere" text='${helpFlagDaLeggereText }' />
					<spring-form:errors path="entity.flagDaLeggere" cssClass="error"/>
				</td>
			 </tr>
			 <tr>
			 <!-- Gestisce la configurazione utente per decidere se dopo aver salvato 
			      rimane sul form del movimento parmetro==0 o torna sulla lista di elaborazione parametro==1 -->
				    <%
						String isChecked = "";
						//gestisce la visualizzazione della ricerca per altri indirizzi
						if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI))) {
						    isChecked = "checked";
						} else {
						    isChecked = "";
						}
					%>
					<td><fmt:message  key="label.salva_ed_esci" /></td>
					<td class="inline-ui-cell">
						<input type="checkbox" value="1" name="salvaEdEsci" <%=isChecked%> onclick="javascript:saveUserPreference('<%=WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI%>',(this.checked==true) ? 1 : 0)"/>
						<c:set var="helpSalvaEdEsciText"><fmt:message key="label.help.salva_ed_esci" /></c:set>
						<init:help idHelp="helpSalvaEdEsci" text='${helpSalvaEdEsciText }' />
					</td>
			 </tr>
		 </c:if>
		 <c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">
		  <tr>
			<td colspan="4" style="text-align: right;">		
				<c:set var="helpOperatoreEDataText"><fmt:message key="help.operatore_data_inserimento_movimento" /></c:set>
				<init:help idHelp="helpOperatoreEData" text='${helpOperatoreEDataText }' />
				${movimentiCommand.entity.responsabile.responsabile}<br />
				<fmt:formatDate value="${movimentiCommand.entity.datainserimento}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>"/>
			</td>
		 </tr>
		 </c:if>
		 <%--
			 <c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.NEW}">
			 	<c:if test="${movimentiCommand.inserimentoVeloce eq false}">
					 <tr>
						<td><fmt:message  key="label.registrazione_onere" /></td>
						<td colspan="3">
							<spring-form:checkbox id="registraOnere_id" path="flagRegistraOnere" />
							<fmt:message  key="label.registrazione_onere.help" />
						</td>
					 </tr>
					 <tr>
						<td><fmt:message  key="label.causale_onere" /></td>
						<td colspan="3">
							<spring-form:select id="causaleonere_id" path="tipicausalioneri.id.codice">
								<spring-form:options items="${tipicausalionerilist}" itemValue="id.codice" itemLabel="coDescrizione"/>
							</spring-form:select>
						</td>
					 </tr>
				 </c:if>
			 </c:if>
		 --%>
		 
	</table>
	<script type="text/javascript">
	
	
	</script>	
</spring-form:form>
</div>
	<%
		MovimentiCommand command = (MovimentiCommand)request.getAttribute("movimentiCommand");
	    String uriBack = "../movimenti/view.htm?codice=" + command.getEntity().getId().getCodice();   
	%>
		<c:set var="urldoctipo" value="${inite:linkStampeIstanza(pageContext.request, _URL_BACK, software, false, movimentiCommand.entity.istanza.id.codice, movimentiCommand.entity.id.codice)}" />			

<div id="functions">
<ul>
	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.NEW_SCADENZA}">
			<li><a href="javascript:doSubmit('insertScadenza.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.NEW}">
		<%-- INSERISCI --%>
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">
		<c:if test="${isModificaMovimento}">
			<%-- SALVA --%>
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<%-- CANCELLA --%>
				<c:if test="${movimentiHelper.movimentoAvvio eq false}">			
					
					<%-- 
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				    --%>
				    <li><a href="javascript:cancellaConfirm('cancellaMovimentoDialogDiv')"><fmt:message key="button.delete" /></a>
						<div dojoType="dijit.Dialog" id="cancellaMovimentoDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />"  style="display: none;">
							<input type="checkbox" id="cancellazionemovimentochk_id" onclick="showHideDiv('doDeleteId')"/>
							<label for="cancellazionemovimentochk_id">
								<fmt:message key="label.messaggio_cancellazione_movimento_per_operatore">
									<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
									<fmt:param>${movimentiCommand.entity.movimento} [${movimentiCommand.entity.tipomovimento.id.tipomovimento}]</fmt:param>
								</fmt:message>
							</label>
							<br /><br />
							<div id="sezione_pwd_id" style="display: none;">
								<fmt:message key="label.password_richiesta" />
								<input type="password" id="password_cancellazione_id" name="password_cancellazione"  />
								<span id="message_pwd_error"></span>
							</div>
							<div id="functions">
								<ul>
									<%-- <li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li> --%>
									<li style="display: none;" id="doDeleteId"><a id="pippo" href="#"><fmt:message key="button.delete" /></a></li>
									<li><a href="javascript:void 0" onclick="dijit.byId('cancellaMovimentoDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
								</ul>
							</div>
							<br class="clear" />	
						</div>
						<script type="text/javascript">
							
						
						jQuery(function (){
							jQuery("#pippo").on('click', deleteRecord);							
						})
						
						
						    function cancellaConfirm(idDiv){
								
								// check verticalizzazione gest cancellazione
								var jhqrPr = jQuery.ajax({
									  url: '../movimenti/ajaxControllaAttivaVerticalizzazioneGestCancellazioni.htm',
									  context: document.body,
									  cache: false,
									  data: { nomeParametro: "PWD_MOVIMENTO"},
									  dataType: "html",
									  success: function(data) {
											if(data=='true')
											{
												$('sezione_pwd_id').style.display='';
											}
										},
										error: function(jqXHR, textStatus, errorThrown){
											alert(jqXHR.responseText);														
										}
									});	
								//
								
								dijit.byId(idDiv).show();
							}	
							
						    function deleteRecord(e)
						    {
						    	e.preventDefault();

						    	// check verticalizzazione gest cancellazione
						    	var pass=$('password_cancellazione_id').value;
								var jhqrPr = jQuery.ajax({
									  url: '../movimenti/ajaxControllaPasswordCancellazioni.htm',
									  method: 'POST',
									  type: 'POST', // For jQuery < 1.9
									  context: document.body,
									  cache: false,
									  data: { nomeParametro: "PWD_MOVIMENTO",password:pass },
									  dataType: "html",
									  success: function(data) {
										    if(data=='true' || data=='no_attivo')
											{doSubmit('delete.htm','',document.inviodati)}
										    else{
										    	jQuery("#message_pwd_error").css("color", "#cd0a0a;");
										    	jQuery("#message_pwd_error").text(data);
										    	jQuery('#message_pwd_error').show();
										    	jQuery('#message_pwd_error').delay(2000).fadeOut();
										    	$('password_cancellazione_id').value='';
										    }
										},
										error: function(jqXHR, textStatus, errorThrown){
											alert(jqXHR.responseText);														
										}
									});	
						    }
						</script>								
					</li>
				
				
				
				
				</c:if>
		<%-- CREA ALLEGATO --%>
		
		<c:if test="${movimentiHelper.cdsCommissione eq true}">
			<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../commissioniediliziet/listCommissioniDelMovimento.htm?codiceMovimento=${movimentiCommand.entity.id.codice}','')"><fmt:message key="button.cds"/></a></li>
		</c:if>		
		
		
		<li><a href="javascript:void(0);"
		onclick="tabRicercaECreaMessaggio('ricercaDocTipoDiv', ${movimentiCommand.entity.id.codice}, '${movimentiCommand.entity.tipomovimento.id.tipomovimento}')"><fmt:message key="button.crea_allegato" /></a></li>
		
		<div dojoType="dijit.Dialog" id="ricercaDocTipoDiv" style="overflow: inherit;" title="<fmt:message key="label.crea_allegato" />: ">
			<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 900px; height: 400px;">
				<div id="ricercaDocTipo">
	            
	
				</div>
			</div>
		</div>
		
		
		<%-- Sono gli script che vengono richiamati all'interno della jsp createSearchDocumentiAllegati.jsp 
		     la pagina viene caricata dalla chiamata ajax del js "ricercaECreaMessaggioTab(codMovimento,codTipomovimento)" --%>
		
		<script type="text/javascript">
		

		
		
		<%-- Apre il dialog che andrà a contenere la pagina createSearchDocumentiAllegati.jsp  --%>
		
		function tabRicercaECreaMessaggio(divId, codMovimento, codTipomovimento){
			
			dijit.byId(divId).show();
			ricercaECreaMessaggioTab(codMovimento,codTipomovimento);	
		}
		
		<%-- Carica tramite una chiamta ajax la pagina createSearchDocumentiAllegati.jsp  --%>
		function ricercaECreaMessaggioTab(codMovimento,codTipomovimento) {
			
			jQuery.ajax({
				  url: '${pageContext.request.contextPath}/movimenti/ajaxCreateRicercaLettereTipo.htm',
				  context: document.body,
				  cache: false,
				  data: 'codiceMovimento='+ codMovimento+ '&codiceTipomovimento=' + codTipomovimento,
				  dataType: "html",
				  success: function(data) {
					  	jQuery('#ricercaDocTipo').html(data);
					  	jQuery('#letteretipo_id1_choices').css('position', 'relative');
						jQuery('#letteretipo_id2_choices').css('position', 'relative');
						jQuery('#functions').css("display","block");
						applyStyle();
						
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
				}
			});
			}
		
		<%-- Javascript che permette di passare alle due diverse visulaizzazione dei tipi documenti da cui creare l'allegtao.
		  
		     CASO A: "id" not cheked lista dei tipi documenti associati al tipo movimento
		     CASO B: "id"  cheked ricerca ajax sulle lettere tipo del software corrente e software TT
		 --%>
		
		function mostraRicercaDocTotali(id){
			if(document.getElementById(id).checked)
			{
				$('id_campo_ricerca_doc_tipo').style.display="";
				$('id_table_doc_tipo').style.display="none";
			}else
			{
				$('id_campo_ricerca_doc_tipo').style.display="none";
				$('id_table_doc_tipo').style.display="";
			}
			
			
			
		}
		<%-- Funzione js che permette di switchare tra la ricerca delle lettere tipo del softwarew corrente o software TT
		    
		    CASO A: "id_flag_letteretipo" not cheked filtro software corrente
		    CASO B: "id_flag_letteretipo"  cheked filtro software TT
		
		--%>   
		
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
		
		<%--  
		  
		  JS che fa una chiamata ajax che genera una allegato richiamando una funzione ASP e apre l'applet per la modifica
		  in line del documento creato.
		
		--%>
		
		function creaAllegato(codiceDoc,codiceIst,codiceMov,tipoMov){
			var d = new Date();
		    var millis = d.getMilliseconds();
		    disableFunctions();
			new Ajax.Request(
					'${pageContext.request.contextPath}/movimenti/ajaxCreateLettereTipo.htm?codiceDocumento='+codiceDoc+
	  				 '&codiceIstanza='+codiceIst+'&codiceMovimento='+codiceMov+'&tipoMovimento='+tipoMov,
					{
						method : 'get',
						parameters: {func: 'closeEditDocs', ts: millis},
						onSuccess : function(transport) {
							enableFunctions();
							var response = transport.responseText;							
							$("ricercaDocTipo").innerHTML = parseAjaxResponse(response,false,true);
							parseAjaxResponse(response,true,false);
							applyStyle();
						},
						onFailure : function(transport) {
							enableFunctions();
							var response = transport.responseText;
							// alert(response);
							new dijit.Dialog({
						        title: 'Errore nella creazione della lettera',
						        content: response
						        
						    }).show();
							closeEditDocsWithoutReload();
						}
					});
			}
		
		function modificaAllegatoCreato(id){
			location.href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId="+id;
		}
		
		<%--
		function modificaAllegatoCreato(codiceDoc){
			var d = new Date();
		    var millis = d.getMilliseconds();
		    disableFunctions();
			new Ajax.Request(
					'${pageContext.request.contextPath}/movimenti/ajaxModificaAllegatoCreato.htm?codiceDocumento='+codiceDoc,
					{
						method : 'get',
						parameters: {func: 'closeEditDocs', ts: millis},
						onSuccess : function(transport) {
							enableFunctions();
							var response = transport.responseText;							
							$("ricercaDocTipo").innerHTML = parseAjaxResponse(response,false,true);
							parseAjaxResponse(response,true,false);
						},
						onFailure : function(transport) {
							enableFunctions();
							var response = transport.responseText;
							// alert(response);
							new dijit.Dialog({
						        title: 'Errore nella creazione della lettera',
						        content: response
						        
						    }).show();
							closeEditDocsWithoutReload();
						}
					});
			}
		--%>
		
		<%--
		   JS che al salvataggio del documento chiude l'applet (verra passato come parametro delle request sulla "redirect" del metodo 
		   '/movimenti/ajaxCreateLettereTipo.htm')
		--%>
		
		function closeEditDocs(){
			dijit.byId('ricercaDocTipoDiv').hide();
			location.reload(true);
		}
		function closeEditDocsWithoutReload(){
			dijit.byId('ricercaDocTipoDiv').hide();
			
		}
			
		<%--
		 
		 Funzione JS richimata da afterUpdateElement delle jsp include letteretipiSearch.jsp che alla scelta dellla lettera tipo
		 automaticamente richiama la funzione JS "creaAllegato(...)"
		
		--%>	
		function setHiddenFieldLettereTipo(inputField,listItem){
			var a = listItem.id;
			document.getElementById('letteretipo_id1').value = inputField.value;
			document.getElementById('letteretipo_hidden').value = a;
            if(${isFileSystemAttivo})
             {
            	disableFunctions();
		  		creaAllegatoVisualizzaAllegato(a,${movimentiCommand.entity.istanza.id.codice},${movimentiCommand.entity.id.codice},$('tipoMovimentoInputId_hidden').value);		 
		  		enableFunctions();
		  		dijit.byId('ricercaDocTipoDiv').hide();
		     }else
             {
            	 creaAllegato(a,${movimentiCommand.entity.istanza.id.codice},${movimentiCommand.entity.id.codice},$('tipoMovimentoInputId_hidden').value);
             }
		}


		function creaAllegatoVisualizzaAllegato(codiceDoc,codiceIst,codiceMov,tipoMov){
			var d = new Date();
		    var millis = d.getMilliseconds();
		    
		    window.open('../movimenti/ajaxCreateLettereTipo.htm?codiceDocumento='+codiceDoc+
 				 '&codiceIstanza='+codiceIst+'&codiceMovimento='+codiceMov+'&tipoMovimento='+tipoMov);
			}

		function creaAllegatoVisualizzaAllegatoDallaLista(codiceDoc,codiceIst,codiceMov,tipoMov)
		{
			disableFunctions();
			creaAllegatoVisualizzaAllegato(codiceDoc,codiceIst,codiceMov,tipoMov);
			enableFunctions();
			dijit.byId('ricercaDocTipoDiv').hide();
		}
		
		
		function ricercaAtto(codMovimento){
			disableFunctions();
			var jqxhr = jQuery.ajax({
				  url: "${pageContext.request.contextPath}/movimentiatti/ajaxRicercaAtto.htm",
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  data: "codicemovimento="+codMovimento,
				  success: function(dataResult) {
					  enableFunctions();
					  	dijit.byId('ricercaAttoDiv').attr("style", "overflow:auto; width:600px");
			            dijit.byId('ricercaAttoDiv').attr("content", dataResult);
				  		dijit.byId('ricercaAttoDiv').show();
					  },
				  error: function(dataError){		
					  enableFunctions();
					  alert(dataError)
					  dijit.byId('ricercaAttoDiv').attr("content", dataError);	
					  dijit.byId('ricercaAttoDiv').show();
				  }	
				});		
		}
		
		</script>	
		
		
			
		
		
		<!-- §§§BEGIN§§§ -->		
		<%--  
			<%pageContext.setAttribute("URL_STAMPA",SigeproMSConstants.getURL_STAMPE_MOVIMENTO_CREA_ALLEGATO());%>
		
		
			<c:set var="_URL_STAMPA" value="${URL_STAMPA}?modo=LD&codiceIstanza=${movimentiCommand.entity.istanza.id.codice}&codiceMovimento=${movimentiCommand.entity.id.codice}&TipoMovimento=${movimentiCommand.entity.tipomovimento.id.tipomovimento}" /><c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _URL_BACK , null, true)}" />
			<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.crea_allegato" /></a></li>
		
		--%>
		<!-- §§§END§§§ -->
		
		</c:if>
		<%-- ALLEGATI --%>
			<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimentiallegati/list.htm?codiceMovimento=${movimentiCommand.entity.id.codice}')"><fmt:message key="button.allegati" /></a></li>
		<%-- DOCUMENTI TIPO --%>
		<!-- §§§BEGIN§§§ -->	
			
			<li><a href="${urldoctipo}"><fmt:message key="button.documenti_tipo" /></a></li>
		
		<!-- §§§END§§§ -->			
		<%-- INVIA E-MAIL --%>
		<c:if test="${isModificaMovimento}">
			<c:if test="${isDPR160 eq true}">
				<c:if test="${isP7mDPR160Allegato eq true && isViewInviaEmail eq true}">
					<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimentimail/createMail.htm?codicemovimento=${movimentiCommand.entity.id.codice}&codiceistanza=${movimentiCommand.entity.istanza.id.codice}')"><fmt:message key="button.inviamail" /></a></li>
				</c:if>
				<c:if test="${isP7mDPR160Allegato eq false}">
					<li class="buttondisabled"><a  href="javascript:void(0);" title="<fmt:message key="alert.dpr160.firmare_allegato_per_abilitare_mail" />"><fmt:message key="button.inviamail" /></a></li>
				</c:if>
			</c:if>
			<c:if test="${isDPR160 eq false && isViewInviaEmail eq true}">
				<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimentimail/createMail.htm?codicemovimento=${movimentiCommand.entity.id.codice}&codiceistanza=${movimentiCommand.entity.istanza.id.codice}')"><fmt:message key="button.inviamail" /></a></li>
			</c:if>
		</c:if>
		<%-- ARCHIVIO MAIL --%>
		<c:if test="${isArchivioMailVisibile eq true }">
			<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimentimail/list.htm?codicemovimento=${movimentiCommand.entity.id.codice}&codiceistanza=${movimentiCommand.entity.istanza.id.codice}')"><fmt:message key="button.archivio_email" /></a></li>
		</c:if>
		<%-- SCHEDE --%>
			<%							
				pageContext.setAttribute("URL_ISTANZEDYN2_MODELLI", BackofficeNETConstants.getURL_ISTANZE_DYN2_MODELLI());
			%>
			<c:set var="_URL_ISTANZEDYN2_MODELLI" value="${URL_ISTANZEDYN2_MODELLI}?CodiceIstanza=${movimentiCommand.entity.istanza.id.codice}&CodiceMovimento=${movimentiCommand.entity.id.codice}"/>			
			<c:set var="_URL_ISTANZEDYN2_MODELLI" value="${inite:linkschedemovimento(pageContext.request, _URL_BACK, null, false, movimentiCommand.entity.istanza.id.codice, movimentiCommand.entity.id.codice)}" />
			
		<li><a href="${_URL_ISTANZEDYN2_MODELLI}"><fmt:message key="button.schede" /></a></li>
		<%-- NOTIFICA STC --%>
		<c:if test="${VERTICALIZZAZIONE_STC_IN_REQUEST eq true}">
			<c:if test="${movimentiCommand.entity.tipomovimento.flagStc eq true}">
				<c:if test="${isModificaMovimento}">	
					<c:if test="${movimentiCommand.entity.inviatoConStc eq 0 or empty movimentiCommand.entity.inviatoConStc}">
						<li><a href="javascript:scegliEnteDestinatario()"><fmt:message key="button.stc.invianotifica" /></a></li>						
						<script type="text/javascript">
							function scegliEnteDestinatario(){
								
								historySet('${_URL_BACK_ENCODED}','../movimenti/associaEnteDestinatario.htm?codiceMovimento=${movimentiCommand.entity.id.codice}', '');	
							}							
						</script>
					</c:if>	
					<c:if test="${visualizzaBottoneSbloccaNotifica eq true}">
						<li><a href="javascript:doHref('../movimenti/updateRimuoviNotificaConErrore.htm', 'Attenzione!! annullare l\'invio della notifica? l\'operazione sara\' riportata nei logs applicativi.');"><fmt:message key="button.stc.sblocca_notifica_con_errore" /></a></li>
					</c:if>
				</c:if>
				<c:if test="${movimentiCommand.entity.inviatoConStc eq 1}">
					<!-- <li class="buttondisabled"><a title="<fmt:message key='label.movimento_notificato_con_stc'/>"><fmt:message key="button.stc.invianotifica" /></a></li>  -->
				<li class="button"><a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimenti/dettaglioNotifica.htm?codicemovimento=${movimentiCommand.entity.id.codice}')" ><fmt:message key="button.stc.dettaglio_notifica" /></a></li>
				</c:if>
				<c:if test="${movimentiCommand.entity.inviatoConStc eq 2}">
					<c:if test="${isModificaMovimento}">											
						<li><a href="javascript:riattivaNotificaStc()"><fmt:message key="button.stc.riattiva_notifica_stc" /></a></li>						
						<script type="text/javascript">
							function riattivaNotificaStc(){
								
								historySet('${_URL_BACK_ENCODED}','../movimenti/riattivanotificaSTC.htm?codice=${movimentiCommand.entity.id.codice}', '');	
							}
						</script>
					</c:if>			

				</c:if>
			</c:if>	
		</c:if>
		<%-- INVIA CAMERA COMMERCIO --%>
		<!-- §§§BEGIN§§§ -->
		
			<c:if test="${VERTICALIZZAZIONE_INFOCAMERA_IN_REQUEST eq true}">
				<c:if test="${movimentiCommand.entity.tipomovimento.flagCamcom eq true}">
					<c:if test="${isModificaMovimento}">
						<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../infocamera/infocamera.htm?codiceistanza=${movimentiCommand.entity.istanza.id.codice}&codicemovimento=${movimentiCommand.entity.id.codice}')"><fmt:message key="button.invio_infocamera" /></a></li>
					</c:if>	
				</c:if>
			</c:if>	
		
		<!-- §§§END§§§ -->
		<%-- EVENTI --%>
		<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../istanzeeventi/list.htm?codicemovimento=${movimentiCommand.entity.id.codice}')"><fmt:message key="button.eventi" /></a></li>
		
		<!--  ZIP LOGICO  -->
		<c:choose>
			<c:when test="${ifZipLogicoExists eq true}">
				<li>
					<a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimentiziplogico/view.htm?codicemovimento=${movimentiCommand.entity.id.codice}')">
						<fmt:message key="label.movimenti_zip_logico.zip_logico" />
						&nbsp;
						<img src="../images/success.png" align="right"/>	
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<c:if test="${ empty movimentiCommand.entity.numeroprotocollo and empty movimentiCommand.entity.dataprotocollo }">
					<li><a href="javascript:historySet('${_URL_BACK_ENCODED}','../movimentiziplogico/list.htm?codicemovimento=${movimentiCommand.entity.id.codice}')"><fmt:message key="label.movimenti_zip_logico.zip_logico" /></a></li>
				</c:if>
			</c:otherwise>
		</c:choose>
		<!-- ///// -->
		
	</c:if>
	<%-- CHIUDI --%>
	<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>

<script type="text/javascript">

const codiceComunePratica = '${movimentiCommand.entity.istanza.comune.codicecomune}';

const isAmministrazioneCollegataCall = async () => {

	let codiceAmmninistrazione = document.getElementById('amministrazioni_hidden').value;
	
	
	if(!codiceAmmninistrazione || codiceAmmninistrazione==''){
		return false;		
	}
	let formData = new FormData();
    formData.append('codice', codiceAmmninistrazione);
    formData.append('codicecomune', codiceComunePratica);
    
	let response = await fetch("../amministrazioni/ajaxIsAmministrazioneCollegata.htm" , {
        method: "POST",
        cache: "no-cache",
        body: formData
    });
    let risultato = await response.json();

    if(risultato.codice==='OK'){
		document.getElementById('amministrazione_collegata_id').style.display='';
	}else{
		document.getElementById('amministrazione_collegata_id').style.display='none';
	}
    
}
	
const amministrazioneCollegata = async () => {

	
	let codiceAmmninistrazione = document.getElementById('amministrazioni_hidden').value;
	
	
	if(!codiceAmmninistrazione || codiceAmmninistrazione==''){
		return;		
	}
	
	vbg.mostraModalCaricamento();
	let { modalBody, modalFooter, modal } = initModal();
	
	let formData = new FormData();
    formData.append('codice', codiceAmmninistrazione);
    formData.append('codicecomune', codiceComunePratica);
    
	let response = await fetch("../amministrazioni/ajaxDettaglioAmministrazioneCollegata.htm" , {
        method: "POST",
        cache: "no-cache",
        body: formData
    });
	
    let resp = await response.json();
    
    let output = `
					<fieldset class="amministrazione_help">
						<legend>
							<b>\${resp.amministrazione}</b>
						</legend>
				`;
				if(resp.partitaiva && resp.partitaiva!='') {		
							output += `
					    <div>
							<span><fmt:message key="label.partitaiva" /></span>
							<b>\${resp.partitaiva}</b>
						</div>
						`;
				}
				if(resp.ufficio && resp.ufficio!='') {		
					output += `<div>
									<span><fmt:message key="label.ufficio" /></span>
									<b>\${resp.ufficio}</b>
								</div>
								`;
				}
					    
				if(resp.referente && resp.referente!='') {
					output += `  <div>
							<span><fmt:message key="label.referente" /></span>
							<b >\${resp.referente}</b>
						</div>
						`;
				}
				if(resp.indirizzo && resp.indirizzo!='') {
					output += ` <div>
							<span><fmt:message key="label.indirizzo" /></span>
							<b >\${resp.indirizzo}</b>
						</div>`;
				}
				if(resp.citta && resp.citta!='') {
					output += ` <div>
							<span><fmt:message key="label.citta" /></span>
							<b >\${resp.citta}</b>
						</div>
						`;
				}
				if(resp.cap && resp.cap!='') {
					output += ` <div>
							<span><fmt:message key="label.cap" /></span>
							<b>\${resp.cap}</b>
						</div>
						`;
				}
				if(resp.provincia && resp.provincia!='') {
					output += ` 
					<div>
					        <span><fmt:message key="label.provincia" /></span>
							<b>\${resp.provincia}</b>
						</div>
						`;
				}						
				if(resp.telefono1 && resp.telefono1!='') {
					output += ` 
						<div>
							<span><fmt:message key="label.telefono1" /></span>
							<b>\${resp.telefono1}</b>
						</div>
						`;
				}		
				if(resp.telefono2 && resp.telefono2!='') {
					output += ` <div>
					        <span><fmt:message key="amministrazioni.label.telefono2" /></span>
							<b>\${resp.telefono2}</b>
						</div>
						`;
				}		
				if(resp.fax && resp.fax!='') {
					output += ` <div>
							<span><fmt:message key="label.fax" /></span>
							<b >\${resp.fax}</b>
					    </div>
					    `;
				}	
				if(resp.email && resp.email!='') {
					output += `   <div>
					     	<span><fmt:message key="label.email" /></span>
							<b >\${resp.email}</b>
					    </div>
					    `;
				}						    
				if(resp.pec && resp.pec!='') {
					output += `   	    <div>
					     	<span><fmt:message key="label.pec" /></span>
							<b >\${resp.pec}</b>
					    </div>
					    `;
				}		
				if(resp.web && resp.web!='') {
					output += `   	    	     <div>
							<span><fmt:message key="label.web" /></span>
							<b >\${resp.web}</b>
						 </div> `;
				}		
				output += `
					</fieldset>     				
    			 `;    
    					  
	modalBody.innerHTML = output;    					  
    vbg.nascondiModalCaricamento();
    modal.open();   	
}
	
function initModal() {
    const modal = document.querySelector('#mod');
    const modalBody = document.querySelector('#mod-body');
    const modalFooter = document.querySelector('#vbg-modal-footer');
    modalBody.innerHTML = "";
    modalFooter.innerHTML = "";
    return { modalBody, modalFooter, modal };
}

async function accettaProtocollo(codicemovimento){
	  
    var dialogWorking = new dijit.Dialog({
       title: "Operazione in corso..." ,
       style: "overflow:auto; width: 250px;height: 70px;",
       content: "<img src='../images/spinner.gif'/>"
    });		
    dialogWorking.show();
  
    try{
    	let url = "../protocollorest/ajaxAccettaMoProtocollo.htm?codiceMovimento="+codicemovimento;
		const response = await fetch(url, {
	        method: "POST",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });
		//let data = await response.json();
		//console.log(data.status);
		if(response.ok){
			let accettaprotocollobuttonId = document.getElementById('accettaprotocollobutton_id');
			if(accettaprotocollobuttonId){
				console.log('Sto nascondendo il bottone');
				accettaprotocollobuttonId.style.display = 'none'; //Preferisco nasconderlo, se proprio necessario rimuoviamo l'element
				console.log('Ho nascosto il bottone');
			}
			alert("Accettazione andata a buon fine");
		}else{
			alert('Errore durante l accettazione');
		}
    }catch(err){
    	console.log('Errore generico su accettazione : ' + err);
    	alert('Errore durante l accettazione');
    }finally{
    	dialogWorking.hide();
    }
    
}
	
vbg.ready(() => {

	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">
		
		isAmministrazioneCollegataCall();
		
	
	</c:if>
   

	});
			

</script>

	<vbg-modal id="mod" class="vbg-form">
	
        <div slot="body" id="mod-body" class="form-group">
        </div>
        <div slot="footer" id="vbg-modal-footer"></div>
    </vbg-modal>

</div>
</body>
</html>