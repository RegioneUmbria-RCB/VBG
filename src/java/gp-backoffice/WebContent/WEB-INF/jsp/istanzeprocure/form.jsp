<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>	
	<fmt:message key="label.procure_dell_istanza" />
</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.procure_dell_istanza" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../istanzeprocure/view" />
		</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeprocure.istanze.id.codice}</c:param>
	</c:import>
<br class="clear" />
<div id="subcontent">
	<spring-form:form commandName="istanzeprocure" name="inviodati">
	<spring-form:hidden path="id.codice" />
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="istanzeprocure" />
    </jsp:include>
	<table>
	    <tr>
			<td>
				<fmt:message key="label.data_documento" />
			</td>
			<td class="inline-ui-cell">
				<spring-form:input  tabindex="1" id="data_id" path="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
   				<init:calendar idImage="caldata" idInput="data_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
   				<spring-form:errors	path="data" cssClass="error" />
			</td>
		</tr>		
		<c:set var="tipoAnagrafe" scope="page"><%= WebConstants.PERSONA_FISICA %></c:set>
		<tr>
			<td style="vertical-align: top;"><fmt:message key="label.procuratore" /></td>
			<td colspan="5">				
				<jsp:include page="../includes/anagraficasearch.jsp" >
					<jsp:param name="idElemento" value="anagrafeProcuratoreIdCodice" />						
					<jsp:param name="pathAnagrafica" value="anagrafeProcuratore" />
					<jsp:param name="tiposoggetto"  value="${tipoAnagrafe }"/>
					<jsp:param name="anagrafeMinChars" value="1" />
					<jsp:param name="anagrafeAutocompleterAjax" value="findRichiedentiIstanza.htm?codiceIstanza=${istanzeprocure.istanze.id.codice}&tipoAnagrafe=${tipoAnagrafe }" />					
					<jsp:param name="codAnagrafeStorico" value="${istanzeprocure.anagrafeProcuratoreStorico.id.codice}" />
					<jsp:param name="descrizioneAnagrafeStorico" value="${istanzeprocure.anagrafeProcuratoreStorico.descrizioneRichiedente}" />
					<jsp:param name="dataAnagrafeStorico" value="${istanzeprocure.anagrafeProcuratoreStorico.datafinevalidita}" />
					<jsp:param value="true" name="anagrafeHideFunctions"/>
				</jsp:include>
			</td>
		</tr>		
		<tr>
		<td style="vertical-align: top;"><fmt:message key="label.anagrafe_rappresentata_da_procuratore" /></td>
		<td colspan="5">
				<jsp:include page="../includes/anagraficasearch.jsp" >
					<jsp:param name="idElemento" value="anagrafeRappIdCodice" />						
					<jsp:param name="pathAnagrafica" value="anagrafeRappresentato" />
					<jsp:param name="tiposoggetto"  value="${tipoAnagrafe }"/>
					<jsp:param name="anagrafeMinChars" value="1" />
					<jsp:param name="anagrafeAutocompleterAjax" value="findRichiedentiIstanza.htm?codiceIstanza=${istanzeprocure.istanze.id.codice}&tipoAnagrafe=${tipoAnagrafe }" />
					<jsp:param name="codAnagrafeStorico" value="${istanzeprocure.anagrafeRappresentatoStorico.id.codice}" />
					<jsp:param name="descrizioneAnagrafeStorico" value="${istanzeprocure.anagrafeRappresentatoStorico.descrizioneRichiedente}" />
					<jsp:param name="dataAnagrafeStorico" value="${istanzeprocure.anagrafeRappresentatoStorico.datafinevalidita}" />
					<jsp:param value="true" name="anagrafeHideFunctions"/>
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.oggetto" /></td>
			<td colspan="5">
			
				<c:choose>
					<c:when test="${(istanzeprocure.oggetti.id.codice==null && istanzeprocure.stcIdAllegato!=null)}">
						<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
       						<jsp:param name="codiceistanza" value="${istanzeprocure.istanze.id.codice}" />
   							<jsp:param name="stcIddocumento" value="${istanzeprocure.stcIdDocumento}" />
							<jsp:param name="stcIdallegato" value="${istanzeprocure.stcIdAllegato}" />
							<jsp:param name="codiceRiferimento" value="${istanzeprocure.id.codice}" />
							<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_PROCURE%>" />		   							
						</jsp:include>    				
					</c:when>							
					<c:otherwise>				
					<jsp:include page="../includes/oggetti.jsp">
		       				<jsp:param name="idElemento" value="oggettoIdCodice" />
		   					<jsp:param name="codiceOggetto" value="${istanzeprocure.oggetti.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
		   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
		   					<jsp:param name="codiceIstanza" value="${istanzeprocure.istanze.id.codice}" />
							<jsp:param name="stcIddocumento" value="${istanzeprocure.stcIdDocumento}" />
							<jsp:param name="stcIdallegato" value="${istanzeprocure.stcIdAllegato}" />	   	
							<jsp:param name="parametroLogOperazione" value="1" />				
		   			</jsp:include>
		   			</c:otherwise>
		   		</c:choose>
				<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice"/>
   				<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile"/>
   				<spring-form:errors path="oggetti" cssClass="error"/>	
			</td>
		</tr>
		
		
		<tr>
			<td><fmt:message key="label.documento_identita" /></td>
			<td colspan="5">
			
				<c:choose>
					<c:when test="${(istanzeprocure.oggettiDocIdent.id.codice==null && istanzeprocure.stcIdAllDocIde!=null)}">
						<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
       						<jsp:param name="codiceistanza" value="${istanzeprocure.istanze.id.codice}" />
   							<jsp:param name="stcIddocumento" value="${istanzeprocure.stcIdDocDocIde}" />
							<jsp:param name="stcIdallegato" value="${istanzeprocure.stcIdAllDocIde}" />
							<jsp:param name="codiceRiferimento" value="${istanzeprocure.id.codice}" />
							<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_PROCURE%>" />		   							
						</jsp:include>    				
					</c:when>							
					<c:otherwise>				
					<jsp:include page="../includes/oggetti.jsp">
		       				<jsp:param name="idElemento" value="oggettiDocIdentIdCodice" />
		   					<jsp:param name="codiceOggetto" value="${istanzeprocure.oggettiDocIdent.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggettiDocIdent_id_codice" />
		   					<jsp:param name="nomefileId" value="oggettiDocIdent_nomefile" />
		   					<jsp:param name="codiceIstanza" value="${istanzeprocure.istanze.id.codice}" />
							<jsp:param name="stcIddocumento" value="${istanzeprocure.stcIdDocDocIde}" />
							<jsp:param name="stcIdallegato" value="${istanzeprocure.stcIdAllDocIde}" />	   	
							<jsp:param name="parametroLogOperazione" value="1" />				
		   			</jsp:include>
		   			</c:otherwise>
		   		</c:choose>
				<spring-form:hidden path="oggettiDocIdent.id.codice" id="oggettiDocIdent_id_codice"/>
   				<spring-form:hidden path="oggettiDocIdent.nomefile" id="oggettiDocIdent_nomefile"/>
   				<spring-form:errors path="oggettiDocIdent" cssClass="error"/>	
			</td>
		</tr>
		
		
		<tr>
			<td>
				<fmt:message key="label.note" />
			</td>
			<td>
				<spring-form:textarea  id="note_id" path="note" cols="67" rows="5"/>
				<spring-form:errors path="note" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.valido" />
			</td>
			<td>
				<spring-form:select id="select_valido" path="controllook">
					<spring-form:option value="" >Da verificare</spring-form:option>									
					<spring-form:option value="1">Valido</spring-form:option>
					<spring-form:option value="0">Non valido</spring-form:option>
				</spring-form:select>	
				<spring-form:errors path="controllook" cssClass="error" />
			</td>
		</tr>
	</table>
	<script type='text/javascript'>
	
	
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${displayCommand.displayMode == displayCommand.displayConstants.NEW}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${displayCommand.displayMode == displayCommand.displayConstants.EDIT}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>

				<c:set var="isDocumentoStcPage" value="${not empty istanzeprocure.stcIdDocumento or not empty istanzeprocure.stcIdAllegato}" scope="page"/>
				<c:choose>					
					<c:when test="${isDocumentoStcPage eq false}">
						<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
					</c:when>					
					<c:otherwise>					
						<c:choose>					
							<c:when test="${userlogged.flagCancelladocumentistc eq true}">				
								<li><a href="javascript:cancellaDocIstanzaConfirm();"><fmt:message key="button.delete" /></a></li>
								<div dojoType="dijit.Dialog" id="cancellaDocumentiIstanzeDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />" style="display: none;">
										<input type="checkbox" id="cancellazionedocistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
										<label for="cancellazionedocistanzachk_id">
											<fmt:message key="label.messaggio_cancellazione_documento_per_operatore">
												<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
											</fmt:message>
										</label>
										<div id="functions">
											<ul>
												<li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
												<li><a href="javascript:void 0" onclick="dijit.byId('cancellaDocumentiIstanzeDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
											</ul>
										</div>
										<br class="clear" />										
								</div>
								
								<script type="text/javascript">
									function cancellaDocIstanzaConfirm(){
										dijit.byId('cancellaDocumentiIstanzeDialogDiv').show();
									}	
								</script>
								
									
							</c:when>
							<c:otherwise>
								<li class="buttondisabled"><a href="javascript:alert('<fmt:message key="javascript.alert.operatore_non_puo_cancellare_documento_stc" />');"><fmt:message key="form.oggetti.delete" /></a></li>
							</c:otherwise>
						</c:choose>	
					</c:otherwise>	
				</c:choose>


	</c:if>
	<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>