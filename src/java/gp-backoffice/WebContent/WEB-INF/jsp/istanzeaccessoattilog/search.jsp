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
		<fmt:message key="label.accessoattilog.title" />
	</title>
</head>
<body>

 	<%
		pageContext.setAttribute("SEARCH_AUT_DEFAULT", WebConstants.SEARCH_AUT_DEFAULT);		
	%>
	<span class="titoloPagina">
		<fmt:message key="label.accessoattilog.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanzeaccessoattilog/createSearch" />
	</jsp:include>
	<div id="subcontent">
	<spring-form:form commandName="istanzeAccessoAttiFilter" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
	        <jsp:param name="commandName" value="istanzeAccessoAttiFilter" />
		</jsp:include>		 
		
		<div class="aler alert-danger" id="attenzioneCampiObbligatori" style="display: none">
			E'obbligatorio specificare almeno il nominativo o il numero istanza
		</div>		
		  <table width="100%">
		  	 <tr>
		  		<td>* <fmt:message key="label.nominativo"/></td>
		  		 <td colspan="4">		  		
		  		 	<spring-form:input id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden');" onkeydown="javascript:return searchAll(this,event,3)" size="60"/>
					<init:autocompleter methodAjax="findAnagrafe.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
					<spring-form:errors path="anagrafe" cssClass="error" /> 
					<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
				</td> 
		  	</tr>
	  	 	<tr>
		  		<td>* <fmt:message key="label.numeroistanza" /></td>
		  		<td colspan="4">
		  			<spring-form:input id="numeroistanza_id" path="istanzeFilter.numeroistanza" size="30" />
		  			<spring-form:errors path="istanzeFilter.numeroistanza" cssClass="error" /> 
		  		</td>
		  	</tr>
	  	 	<tr>
	  	 		<td>
					<fmt:message key="label.periodo" />
				</td>
		  		<td class="inline-ui-cell" style="max-width: 100px;">
		  			<fmt:message key="label.dalla_data"/>
	  			</td>
		  		<td class="inline-ui-cell">
		  			<spring-form:input id="dallaData_id" path="dallaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData_id" textKey="label.calendar" /> 			  	
					<spring-form:errors  path="dallaData"  cssClass="error" delimiter="," />
		  		</td>
		  		<td class="inline-ui-cell" style="max-width: 100px;">
		  			<fmt:message key="label.alla_data"/>
	  			</td>
		  		<td class="inline-ui-cell">
		  			<spring-form:input id="allaData_id" path="allaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData_id" textKey="label.calendar" /> 
					<spring-form:errors path="allaData"  cssClass="error" delimiter="," />
		  		</td>
		  	</tr> 
		  	<tr class="titoloSezione">
				<td colspan="5" style="font-size: 2px;">&nbsp;</td>
			</tr>
		  	<tr>
				<td >
					<fmt:message key="label.ordinare_la_lista_per" />
				</td>
				<td colspan="4" class="inline-ui-cell">
					<spring-form:select id="ordinamento_id" path="orderBy" onchange="savePreferenceFieldOrder('ordinamento_id')">
					    <spring-form:option value="anagrafe.descrizioneRichiedente"><fmt:message key="label.nominativo"/></spring-form:option>
					    <spring-form:option value="istanzeFilter.numeroistanza"><fmt:message key="label.numeroistanza"/></spring-form:option>					   				    
					</spring-form:select>
					<!--Gestisce il tipo ordinamento nella ricerca delle autorizzaizoni (ASC,DESC)  -->
				   
				  
						<% 
							String ordinamentoAsc = "";
					        String ordinamentoDesc = "";
							//gestisce la visualizzazione della ricerca per altri indirizzi
							if (((String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_ISTANZEACCESSOATTI)).equals("ASC")) {						
							    ordinamentoAsc = "selected";
							    ordinamentoDesc = "";
							} else {
							    ordinamentoAsc = "";
							    ordinamentoDesc = "selected";
							}					
						%> 
					
					<spring-form:select id="ordinamentoascdesc_id" path="orderAscDesc" onchange="savePreferenceTypeOrder('ordinamentoascdesc_id')">
					    <option value="<%=DAOOrderTypeEnum.ASC%>" ><fmt:message key="label.ordinamento_asc"/></option>
					    <option value="<%=DAOOrderTypeEnum.DESC%>" ><fmt:message key="label.ordinamento_desc"/></option>
					</spring-form:select>
					
				 </td>
			</tr>	
		  </table>
		  
	<script type='text/javascript'>
		$('anagrafe_id').focus();
		
		function savePreferenceTypeOrder(obj){
			var a=document.getElementById(obj).value;
			saveUserPreference('<%=WebConstants.CONF_UTENTE_ORDINAMENTO_ISTANZEACCESSOATTI%>',a);
			console.log(a);
		}
		
		function savePreferenceFieldOrder(obj){
			var a=document.getElementById(obj).value;
			saveUserPreference('<%=WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_ISTANZEACCESSOATTI%>',a);
		}
	
		jQuery(function(){
			var nominativo = jQuery("#anagrafe_id"),
				numIstanza = jQuery("#numeroistanza_id"),
				campi = jQuery("#anagrafe_id, #numeroistanza_id");
			
			campi.attr("placeholder", "Specificare nominativo o numero istanza");
			
			// nominativo.attr("placeholder","Indicare il nominativo.");
			// numIstanza.attr("placeholder","Indicare il numero istanza.");
			
			campi.on('change', (e) => {
				if(nominativo.val() !== '' || numIstanza.val() !== '') {
					campi.removeClass("campo-obbligatorio");
					jQuery("#attenzioneCampiObbligatori").hide();
				}				
			});
			/*
			campi.on('blur', (e) => {
				if(nominativo.val() === '' || numIstanza.val() === '') {
					campi.addClass("campo-obbligatorio");
					jQuery("#attenzioneCampiObbligatori").show();
				}				
			});
			*/
		});			
			
			
		function searchIstanzeAccessoAttiLogs(){
			var url = URLDecode('${_urlback}');
			var codiceAnagrafe = document.getElementById("anagrafe_id").value,
				numeroIstanza = document.getElementById("numeroistanza_id").value;
			if(codiceAnagrafe == "" && numeroIstanza == ""){	
				jQuery("#anagrafe_id,#numeroistanza_id").addClass("campo-obbligatorio");
				// alert("Inserire almeno un valore");
				jQuery("#attenzioneCampiObbligatori").show();
				document.getElementById("anagrafe_id").focus();
			}else{				
				ajaxHistorySet(url);
				setTimeout("doSubmit('list.htm?modalita_ricerca=${TIPO_SEARCH_AUT}','',document.inviodati)",10);						
			}			
		}		
		
		function ajaxHistorySet(url){
			
			var jhqr = jQuery.ajax({
				  url: '../history/ajaxSet.htm?ReturnTo='+url,
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) { 				   
					} 
				});
			
		}
	</script>
	<style>
		.campo-obbligatorio {
			border-color: #B61218;
		}
		
		#attenzioneCampiObbligatori {
			border: 1px solid #B61218;
			color: 	#B61218;
			padding: 12px;
			box-sizing: border-box;
			margin-bottom: 24px;
			background-color: #f2dede;
		}
		
	</style>
		 </spring-form:form>
	</div>

	
	<div id="functions" style="margin-top: 20px;">
		<ul>			
			<li><a href="javascript:searchIstanzeAccessoAttiLogs()"><fmt:message key="button.search" /></a></li>			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>