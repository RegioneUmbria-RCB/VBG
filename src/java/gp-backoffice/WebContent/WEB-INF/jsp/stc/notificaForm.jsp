<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.stc.notifica.title" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message
	key="form.stc.notifica.title" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../istanzerichiedenti/view" />
</jsp:include>
<c:set var="VERTICALIZZAZIONE_GENERA_ALLEGATI_CART"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_CART_GENERA_ALLEGATI_NOTIFICA)%></c:set>
<div id="subcontent">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${stcNotificaCommand.entity.movimento.istanza.id.codice}</c:param>
		</c:import>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.movimento" />:</div>
				<div><fmt:message key="form.stc.notifica.endoprocedimento" />:</div>
				<div><fmt:message key="form.stc.notifica.amministrazione" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${stcNotificaCommand.entity.movimento.movimento} - [${stcNotificaCommand.entity.movimento.tipomovimento.id.tipomovimento}]							
				</div>
				<div><c:out value="${stcNotificaCommand.entity.movimento.endoprocedimento.procedimento}" default="-"/></div>		
				<div>
					${stcNotificaCommand.entity.movimento.amministrazioniStc.amministrazione}
					<a href="javascript: void 0"; onclick="cambiaAmministrazioneDest();" style="position: absolute;" class="eliminaRiga" title="Scegli un'altra amministrazione"><label><fmt:message key="label.del"/></label></a> 
				</div>
			</div>
		</div>
		<br class="clear" />

<spring-form:form commandName="stcNotificaCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="stcNotificaCommand" />
	</jsp:include>

	<spring-form:hidden path="codiceMovimento"/>

	<fieldset><legend><fmt:message key="form.stc.notifica.altridati" /></legend>
	
	<c:if test="${stcNotificaCommand.displayMode==stcNotificaCommand.displayConstants.PRATICA_STORICA}">
		<c:if test="${not stcNotificaCommand.trovataPraticaCollegata}">
			<fmt:message key="form.stc.notifica.alert.praticastorica" />
		</c:if>					
	</c:if>
	<c:if test="${stcNotificaCommand.trovataPraticaCollegata}">
				<div id="status_msg" class="success_header">
					<fmt:message key="form.stc.notifica.message.praticatrovata.inizio" />		
				</div>
					<ul> 
						<li><fmt:message key="form.stc.notifica.numeroPratica" />:&nbsp;
							<b>${stcNotificaCommand.praticaCollegata.numeroPratica}</b></li>
						<li><fmt:message key="form.stc.notifica.richiedente" />:&nbsp;
							<b>${stcNotificaCommand.praticaCollegata.richiedente.anagrafica.nome}&nbsp;
								${stcNotificaCommand.praticaCollegata.richiedente.anagrafica.cognome}							
						</b></li>
					</ul>
					<%int j=1; %>
					<div><fmt:message
							key="form.stc.praticacollegata.localizzazione" />
						<div class="jmesa" id="localizzazione">
							<table border="0" width="50%" cellpadding="2" cellspacing="0">
								<thead>
									<tr class="header">
										<td width="70%"><fmt:message key="form.stc.praticacollegata.localizzazione.denominazione"/></td>
										<td><fmt:message key="form.stc.praticacollegata.localizzazione.civico"/></td>
									</tr>
								</thead>
								<tbody class="tbody">
								<% j=1;%>
									<c:forEach items="${stcNotificaCommand.praticaCollegata.localizzazione}" var="localizzazione_var">
										<tr>
											<td class="<%=(j%2)==0?"odd":"even"%>">${localizzazione_var.denominazione}</td>
											<td class="<%=(j%2)==0?"odd":"even"%>">${localizzazione_var.civico}</td>						
										</tr>
										<%j++; %>
									</c:forEach>
								</tbody>
							</table>
						</div>						
					</div>					
										
					
					<br/>
					<fmt:message key="form.stc.notifica.message.praticatrovata.fine" />
					
	</c:if>
	
	<table>	
	<c:if test="${stcNotificaCommand.displayMode==stcNotificaCommand.displayConstants.PRATICA_STORICA}">
		<c:if test="${not stcNotificaCommand.overridePraticaStorica}">
				<tr>
					<td><fmt:message key="form.stc.notifica.numeroPratica" /></td>				
					<td>
						<spring-form:input tabindex="1" path="entity.numeroPratica"	id="numeropratica_id" cssStyle="text-align: right;" size="10" /> 
						<spring-form:errors path="entity.numeroPratica" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="form.stc.notifica.numeroProtocolloGenerale" /></td>				
					<td>
						<spring-form:input tabindex="1" path="entity.numeroProtocolloGenerale" cssStyle="text-align: right;" size="10" /> 
						<spring-form:errors path="entity.numeroProtocolloGenerale" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="form.stc.notifica.dataProtocolloGenerale" /></td>				
					<td>
						<spring-form:input tabindex="1" path="entity.dataProtocolloGenerale" id="dataProtocolloGenerale_id" size="10" maxlength="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="calDataProtocolloGenerale" idInput="dataProtocolloGenerale_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.dataProtocolloGenerale" cssClass="error" />
					</td>
				</tr>	
		</c:if>		
	</c:if>
	
	
	<c:if test="${stcNotificaCommand.entity.altriDatiList != null &&  not empty stcNotificaCommand.entity.altriDatiList}">
			
		<c:forEach items="${stcNotificaCommand.entity.altriDatiList}" var="current" varStatus="a">
			<c:choose>
			<c:when test="${current.chiave.flagHelp eq true}">
					<tr>
						<td colspan="2">						
						<div class="alert alert-info">${current.chiave.helpText}</div>			
							<spring:bind path="entity.altriDatiList[${a.index}].chiave.id.codice">
								<input type="hidden" name="${status.expression}" value="${status.value}" />
							</spring:bind>
							<spring:bind path="entity.altriDatiList[${a.index}].valore">
								<input type="hidden" name="${status.expression}"  
									value="<c:out value="${status.value}" escapeXml="true" />" /> &nbsp;							 
							</spring:bind>			
						</td>
					</tr>
			</c:when>
			<c:otherwise>
				<c:if test="${current.chiave.nomeCampo ne '$_RIF_PROT_$'}">
					<tr>
						<td>${current.chiave.etichetta}</td>
						
						<td>
						<spring:bind path="entity.altriDatiList[${a.index}].chiave.id.codice">
							<input type="hidden" name="${status.expression}" value="${status.value}" />
						</spring:bind>
						<spring:bind path="entity.altriDatiList[${a.index}].valore">
							<input size="40" type="text" name="${status.expression}" 
								onblur="checkRequired(this,'${current.chiave.obbligatorio}');" 
								value="<c:out value="${status.value}" escapeXml="true" />" /> &nbsp;
							<c:if test="${not empty current.chiave.helpText}"> 
							<init:help idHelp="helpdyn_${a.index}" text="${current.chiave.helpText}"/>
							</c:if>
						</spring:bind>
						</td>
					</tr>
				</c:if>
			</c:otherwise>
			</c:choose>
		</c:forEach>
		
		
		
		
		<c:if test="${stcNotificaCommand.rifProto}">
			<tr>
				<td>
					<fmt:message key="form.stc.notifica.riferimenti_protocollazione_sceltamovimento" />	
				</td>				
				<td>				
				<c:choose>
					<c:when test="${not empty stcNotificaCommand.listMovimentiRifProto }">
						<spring-form:select path="movimentoPerRiferimentiProt.id.codice">
							<c:forEach items="${stcNotificaCommand.listMovimentiRifProto}" var="mov">
								<spring-form:option value="${mov.id.codice}">
									[${mov.tipomovimento.id.tipomovimento}] - ${mov.movimento} - rifprot [${mov.numeroprotocollo}, <fmt:formatDate value="${mov.dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>]
								</spring-form:option>
							</c:forEach>					
						</spring-form:select>				
						<init:help idHelp="helprifproto_id" textKey="form.stc.notifica.riferimenti_protocollazione_sceltamovimento.help"/>
					</c:when>
					<c:otherwise>
						<div id="rifproto_empty_id" class="warningLine">
							<fmt:message key="form.stc.notifica.riferimenti_protocollazione_sceltamovimento.nessun_movimento" />			
						</div>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</c:if>			
	</c:if>
	
	<c:if test="${stcNotificaCommand.displayMode==stcNotificaCommand.displayConstants.PRATICA_STORICA}">
		<%--
		<tr>
			<td>
				<label for="prosegui"><fmt:message key="form.stc.notifica.overridePraticaStorica" />:</label>  
			</td>
			<td><spring-form:checkbox path="overridePraticaStorica" id="prosegui" onclick="setOverridePraticaStorica(this);" />
			</td>
		</tr>
 		--%>
	</c:if>
		
	</table>
	
	<br />
	</fieldset>
    <br />
<c:if test="${stcNotificaCommand.displayMode!=stcNotificaCommand.displayConstants.PRATICA_STORICA || stcNotificaCommand.overridePraticaStorica}">
	
			<script type="text/javascript">
			
			function selezionaDocumenti(checkIt){
				
				jQuery("input[id^='di_']").prop('checked', checkIt);
				jQuery("input[id^='ma_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='altrima_']").not(':disabled').prop('checked', checkIt);
				jQuery("input[id^='ia_']").prop('checked', checkIt);
				jQuery("input[id^='docanagr_']").prop('checked', checkIt);
				jQuery("input[id^='dp_']").prop('checked', checkIt);
			}
			
			function selezionaAndDeselezionaTutti(){
				var checkIt = jQuery('#a_check_allegati').prop('checked');
				selezionaDocumenti(checkIt);
			}
			function visualizzazioneSezione(idSezione, isMostra){
				if(isMostra){
					jQuery(idSezione).each(function(){
						jQuery(this).show();						
					});
				}else{
					jQuery(idSezione).each(function(){
						jQuery(this).hide();						
					});
				}
			}
			function deselezionaTutti(){
				var checkIt = false;
				selezionaDocumenti(checkIt);
				// visualizzazioneSezione('.div_docs_movimento',false);
				visualizzazioneSezione('.div_docs_movimenti',false);
				visualizzazioneSezione('.div_docs_istanza',false);
				visualizzazioneSezione('.div_docs_istanza_procura',false);
				visualizzazioneSezione('.div_docs_endoprocedimenti',false);
				visualizzazioneSezione('.div_docs_anagrafica',false);
				visualizzazioneSezione('.div_schede',false);
			}
	
			</script>
<c:if test="${isNotificaInteraPratica eq false}">  		
		<c:if test="${isPresentiSubEndo eq true }">
			<div style="width: 100%; border: 2px dotted maroon; padding-left: 10px; padding-top: 4px; padding-bottom: 4px;">
				<init:editLabel key="label.notifica_stc_messaggio_avviso_sub_endo" role="ROLE_EDITLABEL" />
				
				<ul style="font-weight: bold">
				<c:forEach var="endo" items="${ listaSubEndo }">
					<li>${ endo.descrizione } <span style="display: none">${ endo.id }</span></li>
				</c:forEach>
				</ul>
				<script type="text/javascript">
				
				jQuery(function() {
					deselezionaTutti();
				});
				</script>
			</div>
		</c:if>
</c:if>		
		
		<fieldset><legend><fmt:message	key="label.sezione_documenti" /></legend>
	            <div> 
	                <div style="float:right; ">
					<input id="a_check_allegati" type="checkbox" onclick="selezionaAndDeselezionaTutti()"></input>
					<label id="message_label"><fmt:message key="label.seleziona_deseleziona_tutti" /></label>
				    </div>
				</div>
				
			<div class="jmesa">
			<table border="0" cellpadding="2" cellspacing="0" class="table">
				 <c:if test="${not empty stcNotificaCommand.entity.documentiHelper.documentiMovimentoList || stcNotificaCommand.flagTrasmettiZipLogico}">
       			  <%int i=1;%>
				<tr class="header div_docs_movimento div_docs_movimenti">
					<td colspan="6">
						<fmt:message key="label.documenti_movimento" />
					</td>
				</tr>
				

				
						<jsp:include page="../includes/ziplogicosection.jsp">
							<jsp:param name="movimento" value="${stcNotificaCommand.movimentoPerRiferimentiProt.movimento}" />
							<jsp:param name="codicemovimento" value="${stcNotificaCommand.codiceMovimento}"/>
							<jsp:param name="isZipLogico" value="${stcNotificaCommand.flagTrasmettiZipLogico}"/>
							<jsp:param name="displayNone" value="<%=false %>"/>
							<jsp:param name="labelForFlgZipLogicoChbx" value="label.movimenti_zip_logico.notifica"/>
							<jsp:param name="commandPathProperty" value="flagTrasmettiZipLogico"/>
							<jsp:param name="help" value="label.movimenti_zip_logico.notifica.help"/>
							<jsp:param name="hideDocAltrimov" value=".div_docs_movimenti"/>
							<jsp:param name="inputMaChbx" value="input[id^='ma_']" />
							<jsp:param name="hideDocist" value=".div_docs_istanza"/>
							<jsp:param name="inputIstChbx" value="input[id^='di_']" />
							<jsp:param name="hideDocproc" value=".div_docs_istanza_procura"/>
							<jsp:param name="inputProcChbx" value="input[id^='dp_']" />
							<jsp:param name="hideDocendo" value=".div_docs_endoprocedimenti"/>
							<jsp:param name="inputEndoChbx" value="input[id^='ia_']" />
							<jsp:param name="hideDocanag" value=".div_docs_anagrafica"/>
							<jsp:param name="inputAnagChbx" value="input[id^='docanagr_']" />
							<jsp:param name="hideDoccds" value=".hide_doc_cds"/>
							<jsp:param name="inputCdsChbx" value="input[id^='cds_']" />
							<jsp:param name="isRadioBtn" value="<%=true %>" />
						</jsp:include>
				
				
				
				<c:forEach items="${stcNotificaCommand.entity.documentiHelper.documentiMovimentoList}" var="current" varStatus="a">
					<tr class="div_docs_movimento div_docs_movimenti  riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
						<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
						<td width="75%" colspan="5">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="6"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								
			            	</tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b">	
							    <tr class="<%=(i%2)==0?"odd":"even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
								<td width="50%">${var.descrizione}</td>
								<td width="35%">${var.nomeFile}</td>
								<td width="10%">${var.note}</td>
								<c:if test="${var.codiceOggetto!=null}">
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							    
   							    <td>
   							    	<c:if test="${not empty tipoDocList }">
   							    	<spring:bind path="entity.documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].tipoDocumento">
										<select id="docTipo_${b.index}" name="<c:out value="${status.expression}"/>">
   							    			<option value=""></option>
   							    			<c:forEach items="${tipoDocList }" var="td">
   							    				<option value="${td.chiave }">${td.valore }</option>
   							    			</c:forEach>
   							    		</select>
									</spring:bind>
									</c:if>
   							    </td>  							 
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td  class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">									
									<spring:bind path="entity.documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<input id="ma_${a.index}_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>  />
									</spring:bind>
								</td>
								</c:if>
							   	
							   	<c:if test="${var.codiceOggetto==null && not empty var.messageId}">
								<!-- se esiste un record con oggetto null, ma messageid popolato carica automaticamente il codice per scaricare 
							    l'allegato eml dal server di posta configurato -->
									<td colspan="3">
										<jsp:include page="../ajax/downloadEml.jsp" >
									   		<jsp:param name="codiceallegato" value="${var.id.codice}" />
									   		<jsp:param name="idElemento" value="docist${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
											<jsp:param name="mostralabel" value="true" />
											<jsp:param name="id_ckh" value="ma_${a.index}_${b.index}" />
									   	</jsp:include>
								  	</td>
									<td>									
									<spring:bind path="entity.documentiHelper.documentiMovimentoList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<input id="ma_${a.index}_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" disabled="disabled" value="true" title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />" <c:if test="${status.value}">checked="checked"</c:if>  />
									</spring:bind>
									</td>
						  		</c:if>	
							</tr>
							<%i++;%>								
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:forEach>	
		   </c:if>		
		   <c:if test="${not empty stcNotificaCommand.entity.documentiHelper.documentiAltriMovimentiList}">
		   <%int j=1;%>
		   	<tr class="header div_docs_movimenti">
				<td colspan="5">
					<fmt:message key="label.documenti_altri_movimenti" />
				</td>
			</tr>
			<c:forEach items="${stcNotificaCommand.entity.documentiHelper.documentiAltriMovimentiList}" var="current" varStatus="a">
				<tr class="div_docs_movimenti riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
					<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
					<td width="75%" colspan="5">
					<table width="100%">
						<tr class="header" >
						    <td width="98%" colspan="6" ><fmt:message key="movimentimail.label.documento" /> </td>
							<td width="2%" ><fmt:message key="label.seleziona" /></td>
			            </tr>
						<c:forEach items="${current.valore}" var="var" varStatus="b" > 							
							<tr class="<%=(j%2)==0?"odd":"even"%> riga-allegati-selezionabili" 
									data-codiceoggetto="${ var.codiceOggetto }">
							<td width="50%">${var.descrizione}</td>
							<td width="35%">${var.nomeFile}</td>
							<td width="10%">${var.note}</td>
							<c:if test="${var.codiceOggetto!=null}">
							<td width="13%">
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
		       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
		       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
		   						</jsp:include>
  							</td>
  							<td>
	 							 <c:if test="${not empty tipoDocList }">
	 							 <spring:bind path="entity.documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].tipoDocumento">
									<select id="docTipo_${b.index}" name="<c:out value="${status.expression}"/>">
	 							    	<option value=""></option>
	 							    	<c:forEach items="${tipoDocList }" var="td">
	 							    		<option value="${td.chiave }">${td.valore }</option>
	 							    	</c:forEach>
	 							    </select>
								</spring:bind>
								</c:if>
   							</td> 
  							<td>
  							    <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
		       						<jsp:param name="controllook" value="${var.controllook}" />
		   						</jsp:include>
							</td>	
							<td  class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
								<spring:bind path="entity.documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
								<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
								<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
									<input id="ma_${a.index}_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if> />
								</spring:bind>
							</td>
							</c:if>
							
							<c:if test="${var.codiceOggetto==null && not empty var.messageId}">
							<!-- se esiste un record con oggetto null, ma messageid popolato carica automaticamente il codice per scaricare 
						    l'allegato eml dal server di posta configurato -->
								<td colspan="2">
									<jsp:include page="../ajax/downloadEml.jsp" >
								   		<jsp:param name="codiceallegato" value="${var.id.codice}" />
								   		<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
										<jsp:param name="mostralabel" value="true" />
										<jsp:param name="id_ckh" value="altrima_${a.index}_${b.index}" />
								   	</jsp:include>
						  		</td>
								<td>
									<spring:bind path="entity.documentiHelper.documentiAltriMovimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
										<input id="altrima_${a.index}_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" title="<fmt:message key="label.allegato_eml_non_selezionabile.help" />" disabled="disabled" value="true" <c:if test="${status.value}">checked="checked"</c:if> />
									</spring:bind>
								</td>
						  		</c:if>	
						<%j++; %>	
						</c:forEach>
						
					</table>
					</td>
				</tr>
			</c:forEach>	
        </c:if>
<%-- REDMINE #566 punto 2 Se Flag Tipimov_stc_mapping.flag_notificaInteraPratica = 1 nella UI di notifica togliere le sez. doc-istanza e doc-endo --%>        
		<c:if test="${isNotificaInteraPratica eq false}">        
		    <!-- GESTIONE DELLA  VISUALIZZAZIONE DEI DOCUMENTI DELL' ISTANZA  -->
        <c:if test="${not empty stcNotificaCommand.entity.documentiHelper.documentiIstanzaList}">
        <%int k=1;%>
			<tr class="header div_docs_istanza">
				<td colspan="5">
					<fmt:message key="label.allegati_istanza" />
				</td>
			</tr>
			<c:forEach items="${stcNotificaCommand.entity.documentiHelper.documentiIstanzaList}" var="current" varStatus="a">
				<tr class="div_docs_istanza riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
					<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
					<td width="75%" colspan="5">
					<table width="100%">
						<tr class="header" >
						    <td width="98%" colspan="6"><fmt:message key="movimentimail.label.documento" /></td>
							<td width="2%" ><fmt:message key="label.seleziona" /></td>
							
			            </tr>
						<c:forEach items="${current.valore}" var="var" varStatus="b">	
							<tr class="<%=(k%2)==0?"odd":"even"%> riga-allegati-selezionabili"  data-codiceoggetto="${ var.codiceOggetto }">
								<td width="50%">${var.documento}</td>
								<td width="35%">${var.nomeFile}</td>
								<td width="10%">${var.note}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							    <td>
		 							 <c:if test="${not empty tipoDocList }">
		 							 <spring:bind path="entity.documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].tipoDocumento">
										<select id="docTipo_${b.index}" name="<c:out value="${status.expression}"/>">
		 							    	<option value=""></option>
		 							    	<c:forEach items="${tipoDocList }" var="td">
		 							    		<option value="${td.chiave }">${td.valore }</option>
		 							    	</c:forEach>
		 							    </select>
									</spring:bind>
									</c:if>
   							    </td> 
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
									<spring:bind path="entity.documentiHelper.documentiIstanzaList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
									<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
									<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
										<input id="di_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
									</spring:bind>
								</td>	
								
							</tr>
							<%k++; %>								
						</c:forEach>
					</table>
					</td>
				</tr>
			</c:forEach>	
	     </c:if>
	     
	     
	     <!-- GESTIONE DELLA  VISUALIZZAZIONE DELLE PROCURE  -->
	        <c:if test="${not empty stcNotificaCommand.entity.documentiHelper.istanzeprocureList}">
	        <%int k=1;%>
				<tr class="header div_docs_istanza_procura">
					<td colspan="5">
						<fmt:message key="label.documenti_procure" />
					</td>
				</tr>
				<c:forEach items="${stcNotificaCommand.entity.documentiHelper.istanzeprocureList}" var="current" varStatus="a">
					<tr class="div_docs_istanza_procura riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"> 
						<td width="25%"  style="vertical-align: top;" ><b>${current.chiave}</b></td>
						<td width="75%" colspan="5">
						<table width="100%">
							<tr class="header" >
							    <td width="98%" colspan="6"><fmt:message key="movimentimail.label.documento" /></td>
								<td width="2%" ><fmt:message key="label.seleziona" /></td>
								
				            </tr>
							<c:forEach items="${current.valore}" var="var" varStatus="b" >	
								<tr class="<%=(k%2)==0?"odd":"even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
									<td width="50%">Documento della procura di ${var.anagrafeProcuratore.descrizioneRichiedente}</td>
									<td width="35%">${var.nomeFile}									
										<c:if test="${not empty var.codiceOggettoDocId }">
											<p />
											${var.nomeFileDocId}
										</c:if>
									</td>
									<td width="10%">&nbsp;</td>
									<td width="13%">
										<jsp:include page="../includes/visualizzaOggetto.jsp" >
				       						<jsp:param name="idElemento" value="docProc${var.id.codice }" />
				       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
				   						</jsp:include>
										<c:if test="${not empty var.codiceOggettoDocId }">
										<p />
											<jsp:include page="../includes/visualizzaOggetto.jsp" >
					       						<jsp:param name="idElemento" value="docProcDocId${var.id.codice }" />
					       						<jsp:param name="fileId" value="${var.codiceOggettoDocId}" />
					   						</jsp:include>
										</c:if>				   						
	   							    </td>
	   							    <td>
			 							 <c:if test="${not empty tipoDocList }">
			 							 <spring:bind path="entity.documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].tipoDocumento">
											<select id="docTipo_${b.index}" name="<c:out value="${status.expression}"/>">
			 							    	<option value=""></option>
			 							    	<c:forEach items="${tipoDocList }" var="td">
			 							    		<option value="${td.chiave }">${td.valore }</option>
			 							    	</c:forEach>
			 							    </select>
										</spring:bind>
										</c:if>
   							    	</td> 
   							    	<td>
	   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
				       						<jsp:param name="controllook" value="${var.controllook}" />
				   						</jsp:include>
   							   		</td>	
									<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
										<spring:bind path="stcNotificaCommand.entity.documentiHelper.istanzeprocureList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
											<input id="dp_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
										</spring:bind>
									</td>	
									
								</tr>
								<%k++; %>								
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:forEach>	
		  </c:if>
	     
	     
	     
	     <!-- SEZIONE DOCUMENTI ENDO -->
	     <c:if test="${not empty stcNotificaCommand.entity.documentiHelper.documentiEndoprocedimentiList}">
          <%int b=1;%>
			<tr class="header div_docs_endoprocedimenti">
				<td colspan="5">
					<fmt:message key="label.allegati_endoprocedimenti" />
				</td>
			</tr>
			<c:forEach items="${stcNotificaCommand.entity.documentiHelper.documentiEndoprocedimentiList}" var="current" varStatus="a">
				<tr class="div_docs_endoprocedimenti riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
					<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
					<td width="75%" colspan="5">
					<table width="100%">
						<tr class="header" >
						    <td width="98%" colspan="6"><fmt:message key="movimentimail.label.documento" /></td>
							<td width="2%" ><fmt:message key="label.seleziona" /></td>
			            </tr>
						<c:forEach items="${current.valore}" var="var" varStatus="b">	
							<tr class="<%=(b%2)==0?"odd":"even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
								<td width="50%">${var.allegatoextra}</td>
								<td width="35%">${var.nomeFile}</td>
								<td width="10%">${var.note}</td>
								<td width="13%">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
			       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
			   						</jsp:include>
   							    </td>
   							    <td>
		 							 <c:if test="${not empty tipoDocList }">
		 							 <spring:bind path="entity.documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].tipoDocumento">
										<select id="docTipo_${b.index}" name="<c:out value="${status.expression}"/>">
		 							    	<option value=""></option>
		 							    	<c:forEach items="${tipoDocList }" var="td">
		 							    		<option value="${td.chiave }">${td.valore }</option>
		 							    	</c:forEach>
		 							    </select>
									</spring:bind>
									</c:if>
   							    </td> 
   							    <td>
   							        <jsp:include page="../includes/dettaglioCheckOggetto.jsp" >
			       						<jsp:param name="controllook" value="${var.controllook}" />
			   						</jsp:include>
   							    </td>	
								<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
									<spring:bind path="entity.documentiHelper.documentiEndoprocedimentiList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
										<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
										<!-- Controllo se è protocollazione da instanza o movimento, nel caso sia da istanza  protocolloCommand.movimento.id.codice == null i documenti saranno tutti spuntati -->
										<input id="ia_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="checked"</c:if>/>
									</spring:bind>
								</td>	
							 </tr>
							<%b++; %>								
						</c:forEach>
					</table>
					</td>
				</tr>
			</c:forEach>	
			</c:if> 
        </c:if>			 
			 <!-- SEZIONE DOCUMENTI DELLE ANAGRAFICHE -->
			<c:if test="${not empty stcNotificaCommand.entity.documentiHelper.documentiAnagrafeList}">
			<%int p=1;%>
			<tr class="header div_docs_anagrafica">
					<td colspan="5">
						<fmt:message key="label.allegati_anagrafiche" />
					</td>
			</tr>
			<c:forEach items="${stcNotificaCommand.entity.documentiHelper.documentiAnagrafeList}" var="current" varStatus="a">
			<tr class="div_docs_anagrafica riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
				<td width="25%"  style="vertical-align: top;" >${current.chiave}</td>
				<td width="75%" colspan="5">
				<table width="100%">
					<tr class="header" >
					    <td width="98%" colspan="6"><fmt:message key="movimentimail.label.documento" /></td>
						<td width="2%" ><fmt:message key="label.seleziona" /></td>
		            </tr>
					<c:forEach items="${current.valore}" var="var" varStatus="b">	
						<tr class= "<%=(p%2)==0?"odd":"even"%> riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
							<td width="50%">${var.documento}</td>
							<td width="35%">${var.nomeFile}</td>
							<td width="10%">&nbsp;</td>
							<td width="13%">
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
		       						<jsp:param name="idElemento" value="docist${var.id.codice }" />
		       						<jsp:param name="fileId" value="${var.codiceOggetto}" />
		   						</jsp:include>
  							</td>
  							<td>
	 							 <c:if test="${not empty tipoDocList }">
	 							 <spring:bind path="entity.documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].tipoDocumento">
									<select id="docTipo_${b.index}" name="<c:out value="${status.expression}"/>">
	 							    	<option value=""></option>
	 							    	<c:forEach items="${tipoDocList }" var="td">
	 							    		<option value="${td.chiave }">${td.valore }</option>
	 							    	</c:forEach>
	 							    </select>
								</spring:bind>
								</c:if>
   							</td>
   							<td>&nbsp;</td>							
							<td class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }">
								<spring:bind path="entity.documentiHelper.documentiAnagrafeList[${a.index}].valore[${b.index}].transientSegnaPerInvio">
								<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
						        <input id="docanagr_${b.index}" type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if>" />
							    </spring:bind>
							</td>	
						</tr>
						<%p++; %>								
					</c:forEach>
				</table>
				</td>
			</tr>
		</c:forEach>
		</c:if>
		  </table>
	</fieldset>
    <br />
		
		<c:if test="${stcNotificaCommand.entity.modelliList != null &&  not empty stcNotificaCommand.entity.modelliList}">
		<fieldset class="div_schede"><legend><fmt:message key="form.stc.notifica.schede" /></legend>
		<div class="jmesa">
		<table border="0" width="100%" cellpadding="2" cellspacing="0"
			class="table">
			<thead>
				<tr class="header">
					<td><fmt:message key="form.stc.notifica.modello" /></td>
					<td width="10%"><fmt:message key="label.seleziona" /></td>
				</tr>
			</thead>
			<tbody>
			
					
				<c:forEach items="${stcNotificaCommand.entity.modelliList}" var="current" varStatus="a">
				<tr>
					<td class="odd">${current.chiave.descrizione}</td>
					<td class="odd">
					
					<spring:bind path="entity.modelliList[${a.index}].valore">
						<input type="hidden" name="_<c:out value="${status.expression}"/>" value="visible" />
						<input type="checkbox" name="<c:out value="${status.expression}"/>" value="true" <c:if test="${status.value}">checked="true"</c:if> />
					</spring:bind>	
					</td>
				</tr>
				</c:forEach>
			
			</tbody>
		</table>
		
		
		</div>
		</fieldset>
		
	</c:if>	
</c:if>		
<div id="functions">
<ul>
<c:if test="${stcNotificaCommand.displayMode!=stcNotificaCommand.displayConstants.PRATICA_STORICA || stcNotificaCommand.overridePraticaStorica}">	
	<li><a href="javascript:doSubmit('notificaAttivita.htm','',document.inviodati)"><fmt:message
		key="button.stc.invianotifica" /></a>
	</li>
</c:if>	
		<%-- GENERA ALLEGATI X NOTIFICA CART --%
		<c:if test="${VERTICALIZZAZIONE_GENERA_ALLEGATI_CART eq true}">
			<li><a href="javascript:historySet('../movimenti/associaEnteDestinatario.htm?codiceMovimento=${stcNotificaCommand.codiceMovimento}','../cart/generaAllegatiNotifica.htm?codiceIstanza=${stcNotificaCommand.codiceIstanza}&codiceMovimento=${stcNotificaCommand.codiceMovimento}&resetdomanda=true')"><fmt:message key="button.genera_allegati_cart" /></a></li>
			<%-- 
			<li><a href="javascript:historySet('../movimenti/associaEnteDestinatario.htm?codiceMovimento=${stcNotificaCommand.codiceMovimento}','../cart/generaAllegatiNotifica.htm?codiceIstanza=${stcNotificaCommand.codiceIstanza}&codiceMovimento=${stcNotificaCommand.codiceMovimento}')"><fmt:message key="button.genera_allegati_cart" /></a></li>
			
		</c:if>	
		--%>
		
<c:if test="${stcNotificaCommand.displayMode==stcNotificaCommand.displayConstants.PRATICA_STORICA && !stcNotificaCommand.overridePraticaStorica}">
	<li><a href="javascript:ricercaPratica();"><fmt:message key="button.stc.ricercapratica" /></a>
	</li>
	<script type="text/javascript">
		function ricercaPratica(){
			/*if($('numeropratica_id').value==''){
				alert('<fmt:message	key="alert.field" /> <fmt:message	key="alert.required" />');
				$('numeropratica_id').focus();
				return;
			}*/
			doSubmit('ricercaPratica.htm','',document.inviodati);
		}
	</script>
</c:if>		
	<li>
		<a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</li>
</ul>
</div>



	<script type='text/javascript'>

    <%-- 
       Funzione che permette di selezionare tutti gli allegati come da inviare
    --%>

	function selezionatutti(){	
			if($('id_check_invia').checked){
				jQuery("input[id^='chk_invia']").click();
				jQuery("input[id^='chk_invia']").attr('checked', true);
				$('label_id_check_invia').innerHTML = '<b><fmt:message key="label.seleziona_tutti_allegati" /></b>';
			}
			else{
				jQuery("input[id^='chk_invia']").click();
				jQuery("input[id^='chk_invia']").attr('checked', false);	
				$('label_id_check_invia').innerHTML = '<b><fmt:message key="label.seleziona_tutti_allegati" /></b>';
			}	
		
	}				

	
	function checkRequired(obj,obbligatorio){

	}


	function setOverridePraticaStorica(obj){
		doSubmit('view.htm','',document.inviodati);
	}

	function cambiaAmministrazioneDest(){

		doHref('../movimenti/cambiaAmministrazioneStc.htm?codiceMovimento=${stcNotificaCommand.entity.movimento.id.codice}','<fmt:message key="javascript.confirm.cambia_amministrazione_stc" />',document.inviodati);
	}
	</script>
</spring-form:form></div>

<script type="text/javascript">	
	jQuery(document).ready(function () {
		hideOtherDocumentSectionsIfFlgZipLogicoChecked(true);
	});
</script>
</body>
</html>
