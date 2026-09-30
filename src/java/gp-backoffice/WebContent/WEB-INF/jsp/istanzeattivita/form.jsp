<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.NEW}">
			<fmt:message key="istanzeattivita.label.nuovo_istanzeattivita.title" />
		</c:if> 
		<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.VIEW}">
			<fmt:message key="istanzeattivita.label.dettaglio_istanzeattivita.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.NEW}">
			<fmt:message key="istanzeattivita.label.nuovo_istanzeattivita.title" />
		</c:if> 
		<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.VIEW}">
			<fmt:message key="istanzeattivita.label.dettaglio_istanzeattivita.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	 <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeattivita/view" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeattivita.istanza.id.codice}</c:param>
	</c:import>
	<br class="break" />
	<div id="subcontent">
		<spring-form:form commandName="istanzeattivita" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeattivita" />
		    </jsp:include>
			
			<%-- Start New --%>
		    <c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.NEW}">
			<table>
				<tr>
					<td><fmt:message key="label.tipo_informazione" /></td>
					<td colspan="3">	
						<script type="text/javascript">
							function refreshPage(inputField,listItem){
								var a = listItem.id;							
								doHref('createStep2.htm?codiceSettore='+a,'');								
							}							
						</script>		
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="settori" />		
							<jsp:param name="propertyPath" value="settori" />				
							<jsp:param name="pathPropertyDescription" value="settori.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="settori.id.codicesettore" />
							<jsp:param name="autocompleterAjax" value="findSettori.htm?flagDisabilitato=false" />							
							<jsp:param name="titleKey" value="label.ricerca_settori" />
							<jsp:param name="afterUpdateElement" value="refreshPage" />
						</jsp:include>	
						<script type='text/javascript'>
							$('settori_id').focus();
						</script>										
					</td>										
				</tr>			
				
				<%-- Start settore non multiplo --%>
				<c:if test="${istanzeattivita.isStep2 eq true and istanzeattivita.isInsMultiplo eq false}">
				<tr>
					<td><fmt:message key="label.dettaglio_informazione" /></td>
					<td colspan="3">
						<script type="text/javascript">
						
							function popolacamponote(inputField,listItem) {
								var a = listItem.id;
								document.getElementById('attivita_hidden').value = a;
							    ricavaCamponote(a);
							}
						
							function filtertiposettore(element, entry) {
								return entry + "&codicesettore=" + document.getElementById("settori_hidden").value;								
							}
						</script>				
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="attivita" />		
							<jsp:param name="propertyPath" value="entity.attivita" />				
							<jsp:param name="pathPropertyDescription" value="entity.attivita.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="entity.attivita.id.codiceistat" />
							<jsp:param name="autocompleterAjax" value="findAttivita.htm?flagDisabilitato=false" />							
							<jsp:param name="titleKey" value="label.ricerca_attivita" />
							<jsp:param name="ajaxCallBack" value="filtertiposettore" />
							<jsp:param name="afterUpdateElement" value="popolacamponote" />
						</jsp:include>							
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="entity.note" rows="3" cols="67" />					
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
					<c:if test="${istanzeattivita.isUnitaMisura}">
					<tr>
						<td>
							${istanzeattivita.entity.attivita.settori.tipiunitamisura.umDescrbreve}
						</td>
						<td>
							<spring-form:input id="metriq_id" path="entity.metriq" size="12" cssStyle="text-align:right;"  onblur="checkNumberValue(this);"/>						
							<spring-form:errors path="entity.metriq" cssClass="error"/>
						</td>
					</tr>		
					</c:if>		
					<script type='text/javascript'>
						$('attivita_id').focus();
					</script>	
				</c:if>										
			</table>
			<%-- End settore non multiplo --%>
			
			<%-- Start settore Multiplo --%>
			<c:if test="${istanzeattivita.isInsMultiplo eq true }">	
			<%
				String styleCodiceIstat = "display:none;";				
				//gestisce la visualizzazione della colonna codiceistat
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT)).equals("1")) {
				  	styleCodiceIstat="";				  	
				} else {		    
		    		styleCodiceIstat="display:none;";
				}
				String styleIstat = "display:none;";
				//gestisce la visualizzazione della colonna istat
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT)).equals("1")) {
				  	styleIstat="";
				} else {		    
		    		styleIstat="display:none;";
				}
			%>
			<br class="break" />
			<fieldset style="width: 60%;">
				<legend><fmt:message key="istanzeattivita.label.elenco_dettagli_informazione.table" /></legend>
				<div class="jmesa">				
					<%-- <%Integer tabIndex = 2; %> --%>
					<table class="table">
						<thead>
							<tr class="header">
								<td style="<%= styleCodiceIstat%>"><fmt:message key="istanzeattivita.label.codiceistat"/></td>
								<td style="<%= styleIstat%>"><fmt:message key="attivita.label.istat"/></td>
								<c:if test="${!istanzeattivita.isUnitaMisura}">
									<td width="2%" align="center"><fmt:message key="label.presente"/></td>
								</c:if>
								<c:if test="${istanzeattivita.isUnitaMisura}">
									<td width="2%" align="center">${istanzeattivita.settori.tipiunitamisura.umDescrbreve}</td>
								</c:if>
							</tr>
						</thead>
				    	<tbody class="tbody">				
						    <c:forEach items="${istanzeattivita.istAttSettoreList}" var="current" varStatus="a">
						    <tr>
						    	<td style="<%= styleCodiceIstat%>">${current.attivita.id.codiceistat}</td>
						    	<td style="<%= styleIstat%>">${current.attivita.istat}</td>							    	
						    	<%-- Visualizzazione checkbox --%>
						    	<c:if test="${!istanzeattivita.isUnitaMisura}">
						    	<td align="center">					    	
							    	<c:set var="_checked" value="" />		
							    	<c:set var="_disabled" value="" />						
									<c:if test="${istanzeattivita.attivitaPresentiMap[current.attivita.id.codiceistat]}" var="attivita">
										<c:set var="_checked" value="checked" />
										<c:set var="_disabled" value="disabled" />
									</c:if>			
									<spring:bind path="istanzeattivita.attivitaPresentiMap[${current.attivita.id.codiceistat}]">								
										<input align="middle" type="checkbox" id="presente_id${a.index}" name="${status.expression}" ${_checked } ${_disabled }/>									
									</spring:bind>						
								</td>
								</c:if>
								<%-- Visualizzazione campo UnitaMisura --%>
								<c:if test="${istanzeattivita.isUnitaMisura}">								
								<td>																
							    	<c:set var="_disabled" value="" />						
									<c:if test="${istanzeattivita.attivitaPresentiUMMap[current.attivita.id.codiceistat]!=null and istanzeattivita.attivitaPresentiUMMap[current.attivita.id.codiceistat]!=0}" var="attivitaMQ">										
										<c:set var="_disabled" value="disabled" />									
									</c:if>												
									<spring:bind path="istanzeattivita.attivitaPresentiUMMap[${current.attivita.id.codiceistat}]">								
										<input title="${current.attivita.settori.tipiunitamisura.umDescrestesa}" style="text-align: right;" type="text" id="metriq_id${a.index}" name="${status.expression}" value="${status.value}" size="12" onblur="checkNumberValue(this);" ${_disabled } />									
									</spring:bind>			
									<c:remove var="_disabled" />										
								</td>															
								</c:if>
						    </tr>			    
							</c:forEach>
						</tbody>
					</table>	
			  	</div>		
				</fieldset>					
			</c:if>	
			<%-- End settore Multiplo --%>			
			</c:if>
			<%-- End New --%>
			
			<%-- Start View --%>		
			<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.VIEW}">
			<table>
				<tr>
					<td><fmt:message key="label.tipo_informazione" /></td>
					<td>
						<spring-form:input id="settore_id" path="entity.attivita.settori.descrizioneEstesa" size="67" disabled="true"/>						
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.dettaglio_informazione" /></td>
					<td>
						<spring-form:input id="attivita_id" path="entity.attivita.descrizioneEstesa" size="67" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.note" /></td>
					<td>
						<spring-form:textarea id="note_id" path="entity.note" rows="3" cols="67" />						
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${istanzeattivita.isUnitaMisura}">
					<tr>
						<td title="${istanzeattivita.entity.attivita.settori.tipiunitamisura.umDescrestesa}">${istanzeattivita.entity.attivita.settori.tipiunitamisura.umDescrbreve}</td>
						<td>
							<spring-form:input id="metriq_id" title="${istanzeattivita.entity.attivita.settori.tipiunitamisura.umDescrestesa}" path="entity.metriq" size="12" cssStyle="text-align: right;" onblur="checkNumberValue(this);"/>
						</td>
					</tr>
				</c:if>
			</table>
			
			</c:if>
			<%-- End View --%>				
		</spring-form:form>
	</div>
	<script type='text/javascript'>
	if($('note_id')){
		$('note_id').focus();
	}
				function ricavaCamponote(obj) {
						new Ajax.Request(
							'${pageContext.request.contextPath}/attivita/ajaxFindAttivitaById.htm?codice='+obj,
							{
								method : 'post',
								onSuccess : function(transport) {							
									var response = transport.responseText;							
									$("note_id").innerHTML =response;
									applyStyle();			
								},
								onFailure : function(transport) {
									var response = transport.responseText;
								}
							});
						}
				
				
			</script>
	<div id="functions">
		<ul>
			<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.NEW}">
				<c:if test="${istanzeattivita.isStep2 eq true }">
					<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				</c:if>
			</c:if>
			<c:if test="${istanzeattivita.displayMode==istanzeattivita.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanzeattivita.istanza.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>		
</body>
</html>