<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.ricerca_autorizzazioni.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.ricerca_autorizzazioni.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="autorizzazioniFilter" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
	        <jsp:param name="commandName" value="autorizzazioniFilter" />
	    </jsp:include>
	    <table width="100%">
			<tr class="titoloSezione">
				<td colspan="4"><fmt:message key="label.dati_autorizzazione" /></td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.numero" />
				</td>
				<td>
					<spring-form:input id="autoriznumero_id" path="autoriznumero" size="10" />
					<spring-form:errors path="autoriznumero" cssClass="error" delimiter="," />
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.periodo" />
				</td>
				<td>
					<fmt:message key="label.da" />
					<spring-form:input id="dallaData_id" path="dallaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData_id" textKey="label.calendar" /> 			  	
					<spring-form:errors path="dallaData" cssClass="error" delimiter="," />
					&nbsp;
					<fmt:message key="label.a" />
					<spring-form:input id="allaData_id" path="allaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData_id" textKey="label.calendar" /> 
					<spring-form:errors path="allaData" cssClass="error" delimiter="," />
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.data_scadenza" />
				</td>
				<td>
					<fmt:message key="label.da" />
					<spring-form:input id="dallaDataScadenza_id" path="dallaDataScadenza" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataInizioScadenza" idInput="dallaDataScadenza_id" textKey="label.calendar" /> 			  	
					<spring-form:errors path="dallaDataScadenza" cssClass="error" delimiter="," />
					&nbsp;
					<fmt:message key="label.a" />
					<spring-form:input id="allaDataScadenza_id" path="allaDataScadenza" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataFineScadenza" idInput="allaDataScadenza_id" textKey="label.calendar" /> 
					<spring-form:errors path="allaDataScadenza" cssClass="error" delimiter="," />
				</td>
			</tr>
			<tr>
	    		<td>
		    		<fmt:message key="label.comune" />
		    	</td>
			    <td>
			    	<spring-form:input path="autorizcomune.descrizioneEstesa" id="autorizcomune_id" cssClass="searchbox"  onchange="checkValue(this,'autorizcomune_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
			    	<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="autorizcomune_hidden" idInput="autorizcomune_id" inputTitleKey="label.ricerca_comune" minChars="3"/>
					<spring-form:hidden id="autorizcomune_hidden" path="autorizcomune.codicecomune" />
					<spring-form:errors path="autorizcomune" cssClass="error" />
				</td>
			</tr> 
			<tr>
				<td>
		    		<fmt:message key="label.registro" />
		    	</td>
		    	<td>
		    		<spring-form:input path="tipologiaregistro.trDescrizione" id="autorizregistro_id" cssClass="searchbox"  onchange="checkValue(this,'autorizregistro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
		    		<init:autocompleter methodAjax="findTipologiaRegistri.htm" idHidden="autorizregistro_hidden" idInput="autorizregistro_id" inputTitleKey="label.ricerca_tipo_registro" />
					<spring-form:hidden id="autorizregistro_hidden" path="tipologiaregistro.id.codice" />
					<spring-form:errors path="tipologiaregistro" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td>
		    		<fmt:message key="label.anagrafe" />
		    	</td>
				<td>
					<spring-form:input id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden');" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
					<init:autocompleter methodAjax="findAnagrafe.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
					<spring-form:errors path="anagrafe" cssClass="error" /> 
					<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
				</td>
			</tr>
			<tr>
				<td>
		    		<fmt:message key="label.includi_aut_cessate" />
		    	</td>
				<td>
					<spring-form:checkbox id="aut_cess_id" path="includiCessate" />
					<spring-form:errors path="includiCessate" cssClass="error" /> 
				</td>
			</tr>
			<tr class="titoloSezione">
				<td colspan="4"><fmt:message key="label.dati_istanza"/></td>
			</tr>
			<c:if test="${fn:length(comuniassociatiListInRequest) > 1}">
				<tr id="elementIdBeforeCombo">
						<td width="15%"></td>
						<td colspan="3"></td>
				</tr>
					<jsp:include page="../includes/comboComuni.jsp">
						<jsp:param name="mostraTutti" value="true" />
						<jsp:param name="readOnly" value="false" />
						<jsp:param name="commandPropertyPath" value="istanzeFilter.comune" />
						<jsp:param name="colspan" value="4" />
						<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
					</jsp:include>
			</c:if>
			<tr>
				<td><fmt:message key="label.numeroistanza" /></td>
				<td colspan="3">
					<spring-form:input id="numeroistanza_id" path="istanzeFilter.numeroistanza" size="20"/>
				</td>
			</tr>
			<tr>
					<td><fmt:message key="label.data_presentazione" /></td>
					<td colspan="3">
						<fmt:message key="label.dalla_data" />
						<spring-form:input id="dallaData_ist_id" path="istanzeFilter.dallaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio_" idInput="dallaData_ist_id" textKey="label.calendar" /> 			  	
						<spring-form:errors path="istanzeFilter.dallaData" cssClass="error" delimiter="," />
						&nbsp;
						<fmt:message key="label.alla_data" />
						<spring-form:input id="allaData_ist_id" path="istanzeFilter.allaData" size="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="calDataFine_" idInput="allaData_ist_id" textKey="label.calendar" /> 
						<spring-form:errors path="istanzeFilter.allaData" cssClass="error" delimiter="," />
					</td>
			</tr>
			<tr>
				<td><fmt:message key="label.richiedente" /></td>
				<td colspan="3">
					<jsp:include page="../includes/anagraficasearch.jsp" >
						<jsp:param name="idElemento" value="richiedenteIdCodice" />						
						<jsp:param name="pathAnagrafica" value="istanzeFilter.richiedente" />
						<jsp:param value="true" name="anagrafeHideFunctions"/>
						<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?statoAnagrafe=ALL" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td valign="top"><fmt:message key="label.alberoproc" /></td>
				<td>
					<jsp:include page="../includes/searchAlberoProc.jsp">
						<jsp:param name="propertyPath" value="istanzeFilter.alberoproc" />								
						<jsp:param name="pathPropertyDescription" value="istanzeFilter.alberoproc.vwAlberoproc.scDescrizione" />
						<jsp:param name="pathPropertyCode" value="istanzeFilter.alberoproc.id.codice" />
						<jsp:param name="isSelectLeafDisable" value="true" />
						<jsp:param name="isSelectNodoPadre" value="true" />
					</jsp:include>
				</td>						
			</tr>
			<tr>
				<td><fmt:message key="label.tipiprocedure.procedura" /></td>
				<td colspan="3">
					<jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="tipiprocedure_id" />				
						<jsp:param name="propertyPath" value="istanzeFilter.procedura" />			
						<jsp:param name="pathPropertyDescription" value="istanzeFilter.procedura.procedura" />
						<jsp:param name="pathPropertyCode" value="istanzeFilter.procedura.id.codice" />
						<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=true" />
						<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
					</jsp:include>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.indirizzo" /></td>	
				<td>						
					<jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="stradario_id" />
						<jsp:param name="propertyPath" value="istanzeFilter.istanzestradario.stradario" />								
						<jsp:param name="pathPropertyDescription" value="istanzeFilter.istanzestradario.stradario.descrizione" />
						<jsp:param name="pathPropertyCode" value="istanzeFilter.istanzestradario.stradario.id.codice" />
						<jsp:param name="autocompleterAjax" value="findStradario.htm" />
						<jsp:param name="titleKey" value="label.ricerca_stradario" />
					</jsp:include>
				</td>
			</tr>
			<tr>		
				<td>
					<fmt:message key="label.cap" />
				</td>
				<td>
					<spring-form:input id="cap_id" path="istanzeFilter.istanzestradario.cap" size="5" />
				</td>												
			</tr>				
			<tr class="titoloSezione">
				<td colspan="4" style="font-size: 2px;">&nbsp;</td>
			</tr>
			<tr>
				<td >
					<fmt:message key="label.ordinare_la_lista_per" />
				</td>
				<td >
					<spring-form:select id="ordinamento_id" path="orderBy" onchange="savePreferenceFieldOrder('ordinamento_id')">
					    <spring-form:option value="autorizdata,autoriznumero,tipologiaregistro.trDescrizione"><fmt:message key="label.data_numero_registro"/></spring-form:option>
					    <spring-form:option value="istanza.data"><fmt:message key="label.data_presentazione"/></spring-form:option>
					    <spring-form:option value="anagrafeautotizzazione"><fmt:message key="label.richiedente"/></spring-form:option>
					    <spring-form:option value="istanza.numeroistanza"><fmt:message key="label.num_istanza"/></spring-form:option>
					    <spring-form:option value="autoriznumero,autorizdata,tipologiaregistro.trDescrizione"><fmt:message key="label.numero_data_registro"/></spring-form:option>
					    <spring-form:option value="istanza.istanzestradario.stradario.descrizione"><fmt:message key="label.localizzazione"/></spring-form:option> 					    
					</spring-form:select>
					<!--Gestisce il tipo ordinamento nella ricerca delle autorizzaizoni (ASC,DESC)  -->
				   
				   
						<% 

							String ordinamentoAsc = "";
					        String ordinamentoDesc = "";
							//gestisce la visualizzazione della ricerca per altri indirizzi
							if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ORDINAMENTO_AUTORIZZAZIONI)).equals("ASC")) {
						
							    ordinamentoAsc = "selected";
							    ordinamentoDesc = "";
							} else {
							    ordinamentoAsc = "";
							    ordinamentoDesc = "selected";
							}
					
						%>
					
					<spring-form:select id="ordinamentoascdesc_id" path="orderAscDesc" onchange="savePreferenceTypeOrder('ordinamentoascdesc_id')">
					    <option value="<%=DAOOrderTypeEnum.ASC%>" <%=ordinamentoAsc%>><fmt:message key="label.ordinamento_asc"/></option>
					    <option value="<%=DAOOrderTypeEnum.DESC%>" <%=ordinamentoDesc%>><fmt:message key="label.ordinamento_desc"/></option>
					</spring-form:select>
					
				 </td>
			</tr>	
		</table>
		
		
		<%
			String urlStampe = BackofficeNETConstants.getUrlToPopupdecorator(request,BackofficeNETConstants.getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI()+"?SoloDocTipo=1&windowed=S","",(String)session.getAttribute(WebConstants.SOFTWARE));
			pageContext.setAttribute("url_stampe", urlStampe);
		%>	
		
		<script type='text/javascript'>
			$('autoriznumero_id').focus();	
			
			
			function savePreferenceTypeOrder(obj){
				var a=document.getElementById(obj).value;
				saveUserPreference('<%=WebConstants.CONF_UTENTE_ORDINAMENTO_AUTORIZZAZIONI%>',a);
			}
			
			function savePreferenceFieldOrder(obj){
				var a=document.getElementById(obj).value;
				saveUserPreference('<%=WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_AUTORIZZAZIONI%>',a);
			}
			
			
			function stampa(){
				var urlStampe = '<%=request.getContextPath()%>/autorizzazioni/popupstampa.htm?';
				urlParams = jQuery('form').serialize();
				//urlStampe = urlStampe + escape("&" + urlParams);
				urlStampe = urlStampe + "&" + urlParams;
				console.info(urlStampe);
				var wii = window.open(urlStampe,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
			}
			
			
			
			
		</script>	
	</spring-form:form>
	<%-- 
	//function stampa(){
			//	var stampadocumento= window.open("<%=request.getContextPath()%>/autorizzazioni/popupstampa.htm",69,"status=1,menubar=0,scrollbars=1,width=800, height=600");			
			//}
	--%>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('list.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
			<%-- <li><a href="javascript:doSubmit('popupstampa.htm','',document.inviodati)"><fmt:message key="button.stampa" /></a></li> --%>
			<li><a href="javascript:void 0" onclick="stampa();"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:doHref('createSearch.htm?resetAttrs=false','')"><fmt:message key="button.reset" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>