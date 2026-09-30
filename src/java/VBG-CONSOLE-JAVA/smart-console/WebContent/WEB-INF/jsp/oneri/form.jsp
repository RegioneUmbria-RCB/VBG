<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${oneriCommand.onere.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_inventarioprocedimentioneri.title" />
		</c:if> 
		<c:if test="${oneriCommand.onere.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_inventarioprocedimentioneri.title" />
		</c:if>
	</title>
	<script type="text/javascript">
	function isImportoIstruttoriaImpostabile(inputField,listItem) {
		var a = listItem.id;
		var arrayValori=a.split('#');	
		document.getElementById('tipicausalioneri_id1').value = inputField.value;
		document.getElementById('tipicausalioneri_hidden').value = arrayValori[0];
		if(arrayValori[1]=='true'){
			$('tr_aoImportoistruttoria').appear();
		}else{
			$('tr_aoImportoistruttoria').fade();
		}							
	}				
			
    jQuery(document).ready(function(){
    	initTextEditors();
	});
	
	</script>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${oneriCommand.onere.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.nuovo_inventarioprocedimentioneri.title" />
		</c:if> 
		<c:if test="${oneriCommand.onere.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.dettaglio_inventarioprocedimentioneri.title" />
		</c:if>
	</span>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../oneri/view" />
		<jsp:param name="qs" value="idonere=${oneriCommand.onere.id.codice}"/>
	</jsp:include>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${oneriCommand.endo.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="oneriCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="oneriCommand" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.tipicausalioneri" />
					</td>
					<td>
						<spring-form:hidden path="onere.id.codice" />
						<spring-form:hidden path="onere.inventarioprocedimenti.id.codice" />
						<spring-form:hidden path="onere.inventarioprocedimenti.id.idcomune" />
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipicausalioneri" />	
							<jsp:param name="propertyPath" value="onere.tipicausalioneri" />				
							<jsp:param name="pathPropertyDescription" value="onere.tipicausalioneri.coDescrizione" />
							<jsp:param name="pathPropertyCode" value="onere.tipicausalioneri.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipicausalioneriForInventarioprocedimentioneri.htm" />	
							<jsp:param name="titleKey" value="label.ricerca_tipicausalioneri" />
							<jsp:param name="id_help" value="help_tipicausalioneri" />
							<jsp:param name="readOnly" value="${oneriCommand.onere.tipicausalioneri.id.codice != null}"/>
						</jsp:include>
						<spring-form:hidden path="onere.tipicausalioneri.id.idcomune"  />
						<%-- 
						<c:if test="${oneriCommand.causale.id.codice != null}">			
						</c:if>
						<c:if test="${oneriCommand.causale.id.codice == null}">			
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="tipicausalioneri" />	
								<jsp:param name="propertyPath" value="causale" />				
								<jsp:param name="pathPropertyDescription" value="causale.coDescrizione" />
								<jsp:param name="pathPropertyCode" value="causale.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipicausalioneriForInventarioprocedimentioneri.htm?codice=TT" />	
								<jsp:param name="titleKey" value="label.ricerca_tipicausalioneri" />
								<jsp:param name="id_help" value="help_tipicausalioneri" />
							</jsp:include>
						</c:if>
						 --%>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="inventarioprocedimentioneri.label.comune" />
							</td>
							<td>
								<c:if test="${oneriCommand.displayComuniResponsable eq true}">
									<spring-form:select path="onere.comune.codicecomune">
										<c:if test="${fn:length(oneriCommand.comuniResponsabile) > 1}">
											<spring-form:option value=""><fmt:message key="inventarioprocedimentioneri.label.tuttiicomuni" /></spring-form:option>
										</c:if>
										<spring-form:options items="${oneriCommand.comuniResponsabile}" itemLabel="comune.comune" itemValue="comune.codicecomune" />
									</spring-form:select>
								</c:if>
								<c:if test="${oneriCommand.displayComuniResponsable eq false}">
										<input type="hidden" name="onere.comune.codicecomune" value="${oneriCommand.comuneLocalizzazione.codicecomune}"/>
										<input type="text" readonly="readonly" name="onere.comune.comune" value="${oneriCommand.descrizioneComuneLocalizzazione}" />
								</c:if>
								<%--
								<c:if test="${oneriCommand.displayComuneLocalizzazione eq false}">
									<spring-form:hidden path="onere.comune.codicecomune" />
								</c:if>
								 --%>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.importo_causale" />
					</td>
					<td>
						<spring-form:input id="importo_causale_id" path="onere.importo" cssStyle="text-align:right;" size="8" onblur="checkNumberValue(this);" />
						<spring-form:errors path="onere.importo" cssClass="error"/> <label>€</label>
						<init:help idHelp="importo_help" textKey="inventarioprocedimentioneri.label.importo.help"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="inventarioprocedimentioneri.label.flag_disattivo" />
					</td>
					<td>
						<spring-form:checkbox id="disattivo_id" path="onere.flagDisattivo"  />
						<init:help idHelp="disattivo_help" textKey="inventarioprocedimentioneri.label.flag_disattivo.help"/>
						<spring-form:errors path="onere.flagDisattivo" cssClass="error"/>
					</td>
				</tr>	
				<%-- 			
				<c:set var="displayImportoistruttoria" value="display: none;" />
				<c:if test="${isImportoIstruttoriaImpostabile eq true}">
					<c:set var="displayImportoistruttoria" value="" />
				</c:if>				
				<tr id="tr_aoImportoistruttoria" style="${displayImportoistruttoria}">
					<td>
						<fmt:message key="inventarioprocedimenti.label.importo_istruttoria" />
					</td>
					<td>
						<spring-form:input id="importo_istruttoria_id" path="inventarioprocedimentioneri.importoistruttoria" cssStyle="text-align:right;" size="8" onblur="checkNumberValue(this);" />
						<spring-form:errors path="inventarioprocedimentioneri.importoistruttoria" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.flag_pagato" />
					</td>
					<td>
						<spring-form:checkbox path="inventarioprocedimentioneri.flagPagato"/>
						<spring-form:errors path="inventarioprocedimentioneri.flagPagato" cssClass="error"/> 
					</td>
				</tr>
				--%>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="onere.note" cols="60" rows="4"/>
						<init:help idHelp="inventarioprocedimenti_help" textKey="inventarioprocedimenti.help.inventarioprocedimentioneri_note"/>
						<spring-form:errors path="onere.note" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td class="titoloSezione" colspan="2">
						<fmt:message key="label.parametri_frontoffice" />
					</td>
				</tr>
				
				<tr>
					<td width="2%">
						<fmt:message key="label.modello" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="dyn2Modellit" />		
						<jsp:param name="propertyPath" value="onere.dyn2Modellit" />				
						<jsp:param name="pathPropertyDescription" value="onere.dyn2Modellit.descrizione" />
						<jsp:param name="pathPropertyCode" value="onere.dyn2Modellit.id.codice" />
						<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?isComuneBase=true&codicesoftware=" />
						<jsp:param name="id_help" value="help_modello" />	
						<jsp:param name="help" value="help.modelli_archivi_base" />
					</jsp:include>
					</td>
				</tr>
							
				<tr>
					<td width="2%">
						<fmt:message key="label.campo" />
					</td>
					<td>					
						<script type="text/javascript">					
						
							function setHiddenField(inputField,listItem){
								var a = listItem.id;
								document.getElementById('dyn2Campi_id').value = inputField.value;
								document.getElementById('dyn2Campi_hidden').value = a;
							}
							
						</script>				
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
							<jsp:param name="idElemento" value="dyn2Campi" />		
							<jsp:param name="propertyPath" value="onere.dyn2Campi" />				
							<jsp:param name="pathPropertyDescription" value="onere.dyn2Campi.nomecampo" />
							<jsp:param name="pathPropertyCode" value="onere.dyn2Campi.id.codice" />
							<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?isComuneBase=true&codiceSoftware=" />
							<jsp:param name="id_help" value="help_campo" />	
						</jsp:include>
						<fmt:message key="label.campo_dinamico_oneri.help" />	
						<spring-form:hidden path="onere.dyn2Campi.id.idcomune"  />
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${oneriCommand.onere.id.codice==null}">
				<li><a href="javascript:doSubmit('../oneri/insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${oneriCommand.onere.id.codice!=null}">
				<li><a href="javascript:doSubmit('../oneri/update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('../oneri/delete.htm?idonere=${oneriCommand.onere.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>