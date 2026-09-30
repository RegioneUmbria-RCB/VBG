<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.NEW}">
			<fmt:message key="documentiistanza.label.nuovo_documentiistanza.title" />
		</c:if> 
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.VIEW}">
			<fmt:message key="documentiistanza.label.dettaglio_documentiistanza.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.NEW}">
			<fmt:message key="documentiistanza.label.nuovo_documentiistanza.title" />
		</c:if> 
		<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.VIEW}">
			<fmt:message key="documentiistanza.label.dettaglio_documentiistanza.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
			<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../documentiistanza/view" />
		</jsp:include>
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${documentiistanza.entity.istanza.id.codice}</c:param>
		</c:import>
		<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="documentiistanza" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="documentiistanza" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.data_documento" />
					</td>
					<td>
						<spring-form:input  tabindex="1" id="data_id" path="entity.data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata" idInput="data_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<spring-form:errors	path="entity.data" cssClass="error" />
					</td>
				</tr>			
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:textarea tabindex="2" id="documento_id" path="entity.documento" cols="67" rows="4"/>
						<spring-form:errors path="entity.documento" cssClass="error"/>
						<init:help idHelp="help_documento" textKey="documentiistanza.help.descrizione"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea tabindex="3" id="note_id" path="entity.note" cols="67" rows="5"/>
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.richiesto" />
					</td>
					<td>
						<spring-form:checkbox tabindex="4" id="necessario_id" path="entity.necessario" />
						<spring-form:errors path="entity.necessario" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.presente" />
					</td>
					<td>
						<spring-form:checkbox tabindex="5" id="presente_id" path="entity.presente" />
						<spring-form:errors path="entity.presente" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.valido" />
					</td>
					<td>
						<spring-form:select id="select_valido" path="entity.controllook">
							<spring-form:option value="" >Da verificare</spring-form:option>									
							<spring-form:option value="1">Valido</spring-form:option>
							<spring-form:option value="0">Non valido</spring-form:option>
						</spring-form:select>	
						<spring-form:errors path="entity.controllook" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.documento_da_allegare" />
					</td>
					<td>
					
					<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.NEW}">
							<jsp:include page="../includes/oggetti.jsp" >
		       				<jsp:param name="idElemento" value="oggettoIdCodice" />
		   					<jsp:param name="codiceOggetto" value="${documentiistanza.entity.oggetto.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
		   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
		   					<jsp:param name="codiceIstanza" value="${documentiistanza.entity.istanza.id.codice}" />
		   				</jsp:include>
	    				<spring-form:hidden path="entity.oggetto.id.codice" id="oggetto_id_codice"/>
	    				<spring-form:hidden path="entity.oggetto.nomefile" id="oggetto_nomefile"/>
	    				<spring-form:errors path="entity.oggetto" cssClass="error"/>
					</c:if>
					
					<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.VIEW}">
						<c:choose>
							<c:when test="${(documentiistanza.entity.oggetto.id.codice==null && documentiistanza.entity.stcIdallegato!=null)}">
								<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
		       						<jsp:param name="codiceistanza" value="${documentiistanza.entity.istanza.id.codice}" />
		   							<jsp:param name="stcIddocumento" value="${documentiistanza.entity.stcIddocumento}" />
									<jsp:param name="stcIdallegato" value="${documentiistanza.entity.stcIdallegato}" />
									<jsp:param name="codiceRiferimento" value="${documentiistanza.entity.id.codice}" />
									<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_DOCUMENTI%>" />		   							
								</jsp:include>    				
							</c:when>							
							<c:otherwise>
								<jsp:include page="../includes/oggetti.jsp" >
				       				<jsp:param name="idElemento" value="oggettoIdCodice" />
				   					<jsp:param name="codiceOggetto" value="${documentiistanza.entity.oggetto.id.codice}" />
				   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
				   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
				   					<jsp:param name="codiceIstanza" value="${documentiistanza.entity.istanza.id.codice}" />
				   					<jsp:param name="stcIddocumento" value="${documentiistanza.entity.stcIddocumento}" />
									<jsp:param name="stcIdallegato" value="${documentiistanza.entity.stcIdallegato}" />
				   				</jsp:include>			    				
		    				</c:otherwise>
	    				</c:choose>
	    				<spring-form:hidden path="entity.oggetto.id.codice" id="oggetto_id_codice"/>
	    				<spring-form:hidden path="entity.oggetto.nomefile" id="oggetto_nomefile"/>
	    				<spring-form:errors path="entity.oggetto" cssClass="error"/>
    				</c:if>
					</td>
				</tr>
				<c:if test="${ isDocErAttivo eq true}">
					<c:if test="${ not empty documentiistanza.entity.idDocer }">
					<tr>
						<td>
							<fmt:message key="label.archiviato_docer.list" />
						</td>
						<td>
							<jsp:include page="../documentiistanza/oggettodocer.jsp" >
								<jsp:param name="view" value="form" />	
								<jsp:param name="docnum" value="${documentiistanza.entity.idDocer}" />
								<jsp:param name="identificativo" value="${documentiistanza.entity.id.codice}_${documentiistanza.entity.idDocer}" />
							</jsp:include>
						</td>
					</tr>
					</c:if>
				</c:if>
				
			</table>

		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${documentiistanza.displayMode==documentiistanza.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<c:set var="isDocumentoStcPage" value="${not empty documentiistanza.entity.stcIddocumento or not empty documentiistanza.entity.stcIdallegato}" scope="page"/>
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
						
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type='text/javascript'>
		$('data_id').focus();
		
		function confermaCancellazione(divId){
			dijit.byId(divId).show();
		}
		
	</script>		
</body>
</html>