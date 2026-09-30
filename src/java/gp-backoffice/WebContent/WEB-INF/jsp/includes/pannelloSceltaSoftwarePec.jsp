<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>

		<c:set var="SOFTWARE_CORRENTE_VAR" value="<%=ORMHelper.getSoftware()%>"/>
		<c:set var="SOFTWARE_TT" value="<%=WebConstants.SOFTWARE_TT%>"/>
		<c:set var="IS_SOFTWARE_TT" value="false"/>
		<c:if test="${SOFTWARE_CORRENTE_VAR eq  SOFTWARE_TT}">
			<c:set var="IS_SOFTWARE_TT" value="true"/>
		</c:if>			
    	<script type="text/javascript">

			var isSoftwareTT = ${IS_SOFTWARE_TT};
			function setValuesAndGo(){
				if($('software_var_hidden').value!=''){
					$('go_to_url_id').value=$('go_to_url_id').value+"&software=" + $('software_var_hidden').value;
					vaiAFunzione($('go_to_url_id').value,$('tipometodo_id').value,$('hidefunctions_id').value, $('confirmmessage_id').value);
				}else{
					alert('<fmt:message key="label.software" /> <fmt:message key="alert.required" />');
					jQuery('#software_var_hidden').focus();
				}
			}			
			
			function vaiAFunzione(goToUrl,tipoMetodo,isDisableFunctions, confirmMessage,idPec){
				var ancora = escape("#ancora_pec_id_" + idPec);
				if(tipoMetodo=='historySet'){
					historySet('${_urlback}'+ancora, goToUrl, confirmMessage);
				}else if(tipoMetodo=='doHref'){
					doHref(url, confirmMessage);						
				}
				if(isDisableFunctions){
					disableFunctions();
				}
			}
			
			function pannelloSceltaSoftware(goToUrl,tipoMetodo,isDisableFunctions, confirmMessage, idPec,idAccount){										
				
				disableFunctions();
				jQuery.ajax({
					url: "../pecinbox/ajaxScaricaPEC.htm?codicePec=" + encodeURIComponent( idPec )+"&idAccount="+idAccount, 
					dataType: 'text',
					cache: false,	
					success: function(data){
						if( data == 'OK' ){
							$('go_to_url_id').value = goToUrl;					
							$('tipometodo_id').value = tipoMetodo;
							$('hidefunctions_id').value = isDisableFunctions;
							$('confirmmessage_id').value = confirmMessage;
							mostraPannello();
						}else{
							enableFunctions();
							displayErrorMessage(data);
						}
					},
					error: mostraErroriCallback
				});											
			}
			
			function mostraPannello(){
				enableFunctions();
				dijit.byId('pannelloSceltaSoftwareDiv').show();
			}
			
		</script>
		
		<div dojoType="dijit.Dialog" id="pannelloSceltaSoftwareDiv" title="Scegli il modulo di destinazione"  style="height: auto;min-width: 400px;">
			<div style="height: 200px;">	

		<input type="hidden" name="go_to_url" id="go_to_url_id" />	
		<input type="hidden" name="tipometodo" id="tipometodo_id" />
		<input type="hidden" name="hidefunctions" id="hidefunctions_id" />
		<input type="hidden" name="confirmmessage" id="confirmmessage_id" />
			
			
		<select id="software_var_hidden" name="software_id">
			<option value=""><fmt:message key="label.select.default" /></option>
			<c:if test="${not empty softwareListCreaPraticaOrMovimento}">
				<c:forEach items="${ softwareListCreaPraticaOrMovimento }" var="soft">
					<option value="${soft.codice }">${soft.descrizione}</option>
				</c:forEach>
			</c:if>
		</select>
		
			<div id="functions">
				<ul>
					<li id="OkId"><a href="javascript:setValuesAndGo()"><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void 0" onclick="dijit.byId('pannelloSceltaSoftwareDiv').hide();"><fmt:message key="button.annulla" /></a></li>
				</ul>
			</div>
			<br class="clear" />	
			</div>
		</div>
		
    