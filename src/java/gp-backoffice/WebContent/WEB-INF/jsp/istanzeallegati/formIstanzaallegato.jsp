<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${istanzeallegati.entity.id.codice==null}">
			<fmt:message key="label.nuovo_allegato" />
		</c:if> 
		<c:if test="${istanzeallegati.entity.id.codice!=null}">
			<fmt:message key="label.modifica_allegato" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${istanzeallegati.entity.id.codice==null}">
			<fmt:message key="label.nuovo_allegato" />
		</c:if> 
		<c:if test="${istanzeallegati.entity.id.codice!=null}">
			<fmt:message key="label.modifica_allegato" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	 <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeallegati/viewIstanzaAllegato" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeallegati.entity.istanza.id.codice}</c:param>
	</c:import>
	<br class="clear"/>
	<div id="subcontent">
		<spring-form:form commandName="istanzeallegati" name="inviodati" enctype="multipart/form-data">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="istanzeallegati" />
	</jsp:include>	
	<table>
		<tr>
			<td>
				<fmt:message key="label.data_documento" />
			</td>
			<td class="inline-ui-cell">
				<spring-form:input  tabindex="1" id="data_id" path="entity.data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
   				<init:calendar idImage="caldata" idInput="data_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
   				<spring-form:errors	path="entity.data" cssClass="error" />
			</td>
		</tr>			
		<tr>
			<td width="25%"><fmt:message key="label.allegato"/></td>
			<td class="inline-ui-cell">
				<spring-form:input path="entity.allegatoextra" size="50" />
				<spring-form:errors path="entity.allegatoextra" cssClass="error"></spring-form:errors>
			</td>
		</tr>	
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td class="inline-ui-cell">
				<spring-form:textarea path="entity.note" cols="52" rows="4" />
			    <spring-form:errors path="entity.note" cssClass="error"></spring-form:errors>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.valido" />
			</td>
			<td class="inline-ui-cell">
				<spring-form:select id="select_valido" path="entity.controllook">
					<spring-form:option value="" >Da verificare</spring-form:option>									
					<spring-form:option value="1">Valido</spring-form:option>
					<spring-form:option value="0">Non valido</spring-form:option>
				</spring-form:select>	
				<spring-form:errors path="entity.controllook" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.doc_atto" /></td>
			<td class="inline-ui-cell">
			
			<c:if test="${istanzeallegati.displayMode==istanzeallegati.displayConstants.NEW && !isCaricamnetoMultiplo}">
				<jsp:include page="../includes/oggetti.jsp">
				<jsp:param name="idElemento" value="oggettoIdCodice" />
				<jsp:param name="codiceOggetto" value="${istanzeallegati.entity.oggetto.id.codice}" />
				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
				<jsp:param name="nomefileId" value="oggetto_nomefile" />
				<jsp:param name="codiceIstanza" value="${istanzeallegati.entity.istanza.id.codice}" />
				<jsp:param name="parametroLogOperazione" value="1" />
			</jsp:include> 
			</c:if>
			<c:if test="${istanzeallegati.displayMode==istanzeallegati.displayConstants.NEW && isCaricamnetoMultiplo}">
				<jsp:include page="../includes/oggettiMultipli.jsp" />
			</c:if>
			<c:if test="${istanzeallegati.displayMode==istanzeallegati.displayConstants.VIEW}">
				<c:choose>
					
					<c:when test="${(istanzeallegati.entity.oggetto.id.codice==null && istanzeallegati.entity.stcIdallegato!=null)}">			
						<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
      						<jsp:param name="codiceistanza" value="${istanzeallegati.entity.istanza.id.codice}" />
  							<jsp:param name="stcIddocumento" value="${istanzeallegati.entity.stcIddocumento}" />
							<jsp:param name="stcIdallegato" value="${istanzeallegati.entity.stcIdallegato}" />
							<jsp:param name="codiceRiferimento" value="${istanzeallegati.entity.id.codice}" />
							<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_ENDO%>" />		   							
						</jsp:include>    				
					</c:when>					
					<c:otherwise>
						<jsp:include page="../includes/oggetti.jsp">
							<jsp:param name="idElemento" value="oggettoIdCodice" />
							<jsp:param name="codiceOggetto" value="${istanzeallegati.entity.oggetto.id.codice}" />
							<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
							<jsp:param name="nomefileId" value="oggetto_nomefile" />
							<jsp:param name="codiceIstanza" value="${istanzeallegati.entity.istanza.id.codice}" />
							<jsp:param name="stcIddocumento" value="${istanzeallegati.entity.stcIddocumento}" />
							<jsp:param name="stcIdallegato" value="${istanzeallegati.entity.stcIdallegato}" />
							<jsp:param name="parametroLogOperazione" value="1" />
						</jsp:include> 						
					</c:otherwise>
				</c:choose>
			
			</c:if>
				<spring-form:hidden path="entity.oggetto.id.codice" id="oggetto_id_codice" />
				<spring-form:hidden path="entity.oggetto.nomefile" id="oggetto_nomefile" />
			</td>
			<td class="inline-ui-cell">
				<c:if test="${istanzeallegati.entity.stcIdallegato!=null && istanzeallegati.entity.oggetto==null}">
				<div id="functions">
				<ul>
					<li><a href="javascript:doHref('../stc/copiaAllegatoEndoprocedimento.htm?codiceistanzaAllegato=${istanzeallegati.entity.id.codice}"><fmt:message key="button.copia_locale" />${istanzeallegati.entity.stcIdallegato}${istanzeallegati.entity.oggetto}</a></li>
				</ul>
			</div>
			</c:if>
			</td>
		</tr>
	</table>
</spring-form:form> 
	</div>
	<div id="functions">
		<ul>
			<c:if test="${istanzeallegati.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insertIstanzaallegato.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				<c:if test="${!isCaricamnetoMultiplo or  isCaricamnetoMultiplo==null}">
					<li><a title="<fmt:message key="button.documenti_multipli_insert.help" />" href="javascript:doHref('createIstanzaAllegato.htm?codiceinventario=${codiceinventario}&codiceistanza=${istanzeallegati.entity.istanza.id.codice}&isCaricamnetoMultiplo=true','');"><fmt:message key="button.documenti_multipli_insert" /></a></li>
				</c:if>
				<c:if test="${isCaricamnetoMultiplo}">
					<li><a title="<fmt:message key="button.documenti_multipli_insert.help" />" href="javascript:doHref('createIstanzaAllegato.htm?codiceinventario=${codiceinventario}&codiceistanza=${istanzeallegati.entity.istanza.id.codice}&isCaricamnetoMultiplo=false','');"><fmt:message key="button.documenti_singolo_insert" /></a></li>
				</c:if>
				
				
			</c:if>
			<c:if test="${istanzeallegati.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateIstanzaallegato.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>

				
				<c:set var="isDocumentoStcPage" value="${not empty istanzeallegati.entity.stcIddocumento or not empty istanzeallegati.entity.stcIdallegato}" scope="page"/>
				<c:choose>					
					<c:when test="${isDocumentoStcPage eq false}">
						<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />.',document.inviodati)"><fmt:message key="button.delete" /></a></li>
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
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type='text/javascript'>
		// $('data_id').focus();
		
		function confermaCancellazione(divId){
			dijit.byId(divId).show();
		}
		
	</script>

</body>
</html>
