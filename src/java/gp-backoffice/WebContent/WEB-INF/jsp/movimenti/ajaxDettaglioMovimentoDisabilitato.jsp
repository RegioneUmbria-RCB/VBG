<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<c:choose>
	<c:when test="${isEffettuato eq false }">
		<fmt:message key="label.movimento_non_ancora_eseguito" />
	</c:when>
	<c:otherwise>


<table width="90%">
		<tr>
			<td>&nbsp;</td>
			<td colspan="3" style=" vertical-align: middle;">
			<%--
				<c:if test="${VERTICALIZZAZIONE_STC_IN_REQUEST eq true}">
					<jsp:include page="../includes/funzioni_stc.jsp">
						<jsp:param name="codiceIstanza" value="${mov.istanza.id.codice}" />
						<jsp:param name="codiceMovimento" value="${mov.id.codice}" />
						<jsp:param name="funzioneRichiesta" value="richiestaPraticaMovimento" />
						<jsp:param name="returnTo" value="${_urlback}" />
						<jsp:param name="flagStc" value="${mov.tipomovimento.flagStc}" />
						<jsp:param name="inviatoConStc" value="${mov.inviatoConStc}" />
						<jsp:param name="creatoDaStc" value="${mov.creatoDaStc}" />
					</jsp:include>
					<c:if test="${not empty mov.oggettoNotifica.id.codice }">
						<a title="<fmt:message key="label.visualizza_dettaglio_notifica_stc" />" href="javascript:historySet('${_URL_BACK_ENCODED}','../movimenti/dettaglioNotifica.htm?codicemovimento=${mov.id.codice}')"><img alt="<fmt:message key="label.visualizza_dettaglio_notifica_stc" />" style="vertical-align: middle;" border="0" src="${pageContext.request.contextPath}/images/dettaglio_notifica.gif" /></a>
					</c:if>
				</c:if>
				<c:if test="${VERTICALIZZAZIONE_INFOCAMERA_IN_REQUEST eq true}">
					<jsp:include page="../includes/funzioni_infocamere.jsp">
						<jsp:param name="codiceMovimento" value="${mov.id.codice}" />
						<jsp:param name="flagCamcom" value="${mov.tipomovimento.flagCamcom}" />
						<jsp:param name="inviatoACamcom" value="${mov.inviatoACamcom}" />						
					</jsp:include>
				</c:if>
				 --%>
				<c:if test="${not empty mov.numprotMittente or not empty mov.dataProtMittente}">					
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
													${mov.numprotMittente}										
											</div>
										</div>
								</div>	
								<div class="parametriDiv">
								<div class="etichetta">
											<div><fmt:message key="label.data_protocollo_mittente" />:</div>
										</div>		
										<div class="parametro">       		 	
											<div>
												<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${mov.dataProtMittente}" />
											</div>
										</div>	
								</div>										
						</div>
					</div>
					
				</c:if>
				
				
			</td>
		</tr>
		
		<tr>
			<td width="20%" style="border: 1px dotted black;"><fmt:message key="label.tipomovimento" /></td>
			<td colspan="3" style="border: 1px dotted black;">
			<script type="text/javascript">
								
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
			<b>${mov.tipomovimento.movimento} [${mov.tipomovimento.id.tipomovimento}]</b>				
			</td>					
		</tr>
		<tr>
			<td style="border: 1px dotted black;"><fmt:message key="label.movimento" /></td>
			<td colspan="3" style="border: 1px dotted black;">
				<b>${mov.movimento}</b>
			</td>
		</tr>	
		
			<tr>			
				<td  style="border: 1px dotted black;">
					<fmt:message key="label.inventarioprocedimento" />
				</td>
				<td colspan="3" style="border: 1px dotted black;">
					<b>${mov.endoprocedimento.procedimento} (${mov.endoprocedimento.id.codice})</b>
				</td>
			</tr>		
			<tr>
				<td style="border: 1px dotted black;">
					<fmt:message key="label.amministrazione" />
				</td>			
				<td colspan="3" style="border: 1px dotted black;">
					<b>${mov.amministrazioni.amministrazione}</b>
				</td>		
			</tr>
			<tr>
				<td style="border: 1px dotted black;">
					<fmt:message key="label.ufficio" />
				</td>			
				<td colspan="3" style="border: 1px dotted black;">
					<b>${mov.amministrazionireferenti.ufficio}</b>
				</td>		
			</tr>
		<tr>
			<td style="border: 1px dotted black;"><fmt:message key="label.data" /></td>
			<td style="border: 1px dotted black;">
				<b><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${mov.data}" /></b>
			</td>
			<td width="10%" style="border: 1px dotted black;"><fmt:message key="label.data_scadenza" /></td>
			<td style="border: 1px dotted black;"><b>
				<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${mov.dataScadenza}" /></b>
			</td>
		</tr>		
		<tr>
			<td style="border: 1px dotted black;"><fmt:message key="label.numero_protocollo" /></td>
			<td style="border: 1px dotted black;">				
				<b>${mov.numeroprotocollo}</b>
			</td>
			<td style="border: 1px dotted black;"><fmt:message key="label.data_protocollo" /></td>
			<td style="border: 1px dotted black;">
				<b><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${mov.dataprotocollo}" /></b>
			</td>
		</tr>		
		
		<c:set var="displayEsito">display:</c:set>	
		<c:if test="${mov.tipomovimento.tipologiaesito eq 0}">
			<c:set var="displayEsito">display:none;</c:set>
		</c:if>
			<tr style="${displayEsito}" id="div_esito_id">
				<td style="border: 1px dotted black;"><fmt:message key="label.esito_positivo" /></td>
				<td colspan="3" style="border: 1px dotted black;"><b>
					<c:choose>
					<c:when test="${mov.esito eq true }">
						<fmt:message key="label.si" />
					</c:when>
					<c:otherwise>
						<fmt:message key="label.no" />
					</c:otherwise>
					</c:choose>
					</b>
				</td>
			</tr>
		
		<%-- 
		<!-- ---------------------- INFO AGGIUNTIVE PER UN MOVIMENTO CREATO DA UNA COMMISSIONE -------------------------------- -->
		<!-- --------------------------------------------------START----------------------------------------------------------- -->
		<c:if test="${movimentiCommand.commissioniedilizieR.id.codice!=null}">
		<tr>
			<td>&nbsp;</td>
			<td colspan="3">
				<table cellspacing="3" cellpadding="1">
					<tr>
						<td style="font-weight:bold; color:#696969;">
							<fmt:message key="label.numero_commissione" />
						</td>
						<td>
							${movimentiCommand.commissioniedilizieR.commissioniedilizieT.numprotocollo}
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
			</td>
		</tr>
		</c:if>
		<!-- ---------------------- INFO AGGIUNTIVE PER UN MOVIMENTO CREATO DA UNA COMMISSIONE -------------------------------- -->
		<!-- --------------------------------------------------END----------------------------------------------------------- -->
		--%>
		
			<tr>
				<td style="border: 1px dotted black;"><fmt:message key="label.parere" /></td>
				<td colspan="3" style="border: 1px dotted black;">
					<b>${mov.parere}</b>			
				</td>
			 </tr>
			
			 <tr>
			 	<td style="border: 1px dotted black;"><fmt:message key="label.pubblica_parere" /></td>
			 	<td colspan="3" valign="top" style="border: 1px dotted black;">
			 		<b>
			 		<c:choose>
					<c:when test="${mov.pubblicaparere eq true }">
						<fmt:message key="label.si" />
					</c:when>
					<c:otherwise>
						<fmt:message key="label.no" />
					</c:otherwise>
					</c:choose>
			 		</b>
				</td>
			 </tr>
		 	
		 <tr>
			<td style="border: 1px dotted black;"><fmt:message key="label.note" /></td>
			<td colspan="3" style="border: 1px dotted black;">
				<b>${mov.note}</b>			
			</td>
		 </tr>
		 
			 <tr>
				<td style="border: 1px dotted black;"><fmt:message key="label.pubblica" /></td>
				<td colspan="3" style="border: 1px dotted black;">
					<b><c:choose>
					<c:when test="${mov.pubblica eq true }">
						<fmt:message key="label.si" />
					</c:when>
					<c:otherwise>
						<fmt:message key="label.no" />
					</c:otherwise>
					</c:choose></b>					
				</td>
			 </tr>
			 <tr>
				<td style="border: 1px dotted black;"><fmt:message key="label.movimento_da_visionare" /></td>
				<td colspan="3" style="border: 1px dotted black;">
					<b>
					<c:choose>
					<c:when test="${mov.flagDaLeggere eq true }">
						<fmt:message key="label.si" />
					</c:when>
					<c:otherwise>
						<fmt:message key="label.no" />
					</c:otherwise>
					</c:choose>
					</b>
				</td>
			 </tr>
			 
		 
		 
		  <tr>
			<td colspan="4" style="border: 1px dotted black;">		
				
				<b>${mov.responsabile.responsabile}<br />
				<fmt:formatDate value="${mov.datainserimento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></b>
			</td>
		 </tr>
		 
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
	
	
	
	
	
	</c:otherwise>
</c:choose>