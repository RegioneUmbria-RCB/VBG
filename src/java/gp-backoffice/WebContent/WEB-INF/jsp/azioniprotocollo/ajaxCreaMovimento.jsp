<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento"%>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="pecinbox.creamovimento.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="pecinbox.creamovimento.title" />
	</span>
	<br  class="clear" />
		<spring-form:form commandName="movimentoPecCommand" name="movimentoDaPecForm" id="movimentoDaPecForm" action="${pageContext.request.contextPath}/pecinbox/creaMovimento.htm">
		<spring-form:hidden path="comune.codicecomune" />
		<spring-form:hidden path="pec.id.id" />
		
			<br />
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="movimentoPecCommand" />
		    </jsp:include>
		    <c:if test="${ not empty movimentoPecCommand.movimento.id.codice }">
		    	<c:choose>
			    	<c:when test="${ not movimentoPecCommand.stcError eq true  }">
					    <div id="status_msg" class="success_header" >
				          <fmt:message key="pecinbox.message.movimentocreato">
				          	<fmt:param>${movimentoPecCommand.movimento.id.codice}</fmt:param>
				          </fmt:message>
					    </div><br/>
				    </c:when>
				    <c:otherwise>
					    <div id="status_msg" class="error_header" >
				          <fmt:message key="pecinbox.message.erroremovimentostc">
				          	<fmt:param>movimentoPecCommand.movimento.id.codice</fmt:param>
				          </fmt:message>
					    </div><br/>
				    </c:otherwise>
			    </c:choose>
		    </c:if>
			<table width="100%" border="0">
				<tr class="titoloSezione">
					<td colspan="6">
						<fmt:message key="pecinbox.label.dati_pec"/>		
					</td>
				</tr>
				<jsp:include page="datiPEC.jsp" >
			        <jsp:param name="commandName" value="movimentoPecCommand" />
			    </jsp:include>
				<tr>
					<td><fmt:message key="pecinbox.label.scompattaallegaticompressi" /></td>
					<td colspan="5">
						<spring-form:checkbox path="scompattaAllegati" value="true" />
						<init:help idHelp="helpScompattaAllegati" textKey="pecinbox.label.scompattaallegaticompressi.helpmovimento" />
					</td>
				</tr>
				<c:if test="${not empty movimentoPecCommand.pec}">
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="pecinbox.label.dati_movimento"/>		
						</td>
					</tr>
					<c:if test="${empty error }">
						<tr>
							<td><fmt:message key="label.data" /></td>
							<td colspan="5">
								<spring-form:input readonly="true" id="datapec_id" path="movimento.data" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: top;">
								<c:if test="${not movimentoPecCommand.readOnly}">
									<label class="required">*</label>
								</c:if>
								<fmt:message key="pecinbox.label.istanza" />
							</td>
							<td colspan="5">
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="istanza_id" />		
									<jsp:param name="propertyPath" value="movimento.istanza" />		
									<jsp:param name="pathPropertyDescription" value="movimento.istanza.numeroistanza" />
									<jsp:param name="pathPropertyCode" value="movimento.istanza.id.codice" />
									<jsp:param name="autocompleterAjax" value="findIstanzeExtended.htm" />
									<jsp:param name="titleKey" value="pecinbox.label.istanza.alt" />
									<jsp:param name="readOnly" value="${movimentoPecCommand.readOnly}" />
									<jsp:param name="autocompleterInputSize" value="90" />
									<jsp:param name="autocompleterMinChars" value="1" />
									<jsp:param name="afterUpdateElement" value="afterUpdateIstanza" />
									<jsp:param name="ajaxCallBack" value="searchIstanzeParams" />
								</jsp:include>
								<input type="checkbox" name="searchProtocolloMovimenti" id="searchProtocolloMovimentiChk"/>
								<init:help idHelp="helpMovimento" textKey="label.cerca_protocollo_in_movimenti" />				
							</td>
						</tr>					
						<tr>
							<td>
								<c:if test="${not movimentoPecCommand.readOnly}">
									<label class="required">*</label>
								</c:if>
								<fmt:message key="label.tipomovimento" />
							</td>
							<td colspan="5">
								<script type="text/javascript">
									function checkTipomovimento(inputField,listItem){
										var idElemento='tipoMovimentoInputId';
										var a = listItem.id;
										document.getElementById(idElemento+'_hidden').value = a;
										if($('id2_'+idElemento).style.display == 'inline'){
											document.getElementById(idElemento+'_id2').value = inputField.value;
											$(idElemento+'_id2'+'_choices').fade();
										}else{
											document.getElementById(idElemento+'_id1').value = inputField.value;
											$(idElemento+'_id1'+'_choices').fade();
										}
										checkEsito(a);					
									}
									
									function filterMovimentiIstanza(element, entry) { 
										if(document.getElementById("istanza_id_hidden")){
											return entry + "&codiceIstanza=" + document.getElementById("istanza_id_hidden").value;
										}else{
											return entry + "&codiceIstanza=null";
										}
									}
									
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
											  },
											  onFailure: function(transport){ 
												var response = transport.responseText;
											    alert(response); 
											    }						    		 
										} );			
									}			
									
									function afterUpdateIstanza(inputField,listItem){
										var spanToShow = jQuery(listItem).find('#show_' + listItem.id);
										if(spanToShow.length > 0){
											inputField.value = spanToShow.text();
											jQuery("[name = 'movimento.istanza.id.codice']").val(listItem.id);
										}
									}
									
									function searchIstanzeParams(element, entry){
										var searchProtChk = jQuery("#searchProtocolloMovimentiChk");
										if(searchProtChk && searchProtChk.length){
											if(searchProtChk.prop('checked')){
												return entry + "&protInMovimenti=true";
											}
										}
										return entry + "&protInMovimenti=false";
									}
								</script>
								<jsp:include page="../includes/tipimovimentosearch.jsp" >
									<jsp:param name="idElemento" value="tipoMovimentoInputId" />
									<jsp:param name="pathTipomovimento" value="movimento.tipomovimento" />
									<jsp:param name="afterUpdateElement" value="checkTipomovimento" />
									<jsp:param name="readOnly" value="${movimentoPecCommand.readOnly}" />	
									<jsp:param name="tipimovimentoAutocompleterAjax" value="findTipiMovimentoUsatiDalProtocollo.htm?codice=" />
									<jsp:param name="ajaxCallBack" value="filterMovimentiIstanza" />								
								</jsp:include>					
									<%-- 	
									<jsp:param name="tipimovimentoAutocompleterAjax" value="findTipiMovimentoUsatiDalProtocollo.htm?codice=" />
									 --%>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="label.movimento" /></td>
							<td colspan="5">
								<spring-form:input id="movimento_id" path="movimento.movimento" size="70" readonly="${movimentoPecCommand.readOnly}" cssStyle="font-weight: bolder;"/>
								<c:set var="helpMovimentoText"><fmt:message key="label.selasciatovuoto" />&nbsp;<fmt:message key="label.tipomovimento" /></c:set>
								<init:help idHelp="helpDescMovimento" text="${helpMovimentoText}" />				
							</td>
						</tr>	
						<c:set var="displayEsito">display:;</c:set>	
						<c:if test="${movimentiCommand.entity.tipomovimento.tipologiaesito eq 0}">
							<c:set var="displayEsito">display:none;</c:set>
						</c:if>
						<tr style="${displayEsito}" id="div_esito_id">
							<td><fmt:message key="label.esito_positivo" /></td>
							<td colspan="5">
								<spring-form:select id="esito_id" path="movimento.esito" disabled="${movimentoPecCommand.readOnly}" cssStyle="font-weight: bolder;">
									<spring-form:option value="true"><fmt:message key="label.si" /></spring-form:option>
									<spring-form:option value="false"><fmt:message key="label.no" /></spring-form:option>					
								</spring-form:select>
								<spring-form:errors path="movimento.esito" cssClass="error"/>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="label.note" />
							</td>
							<td colspan="5">
								<spring-form:textarea readonly="${movimentoPecCommand.readOnly}" id="lavori_id" path="movimento.note" cols="120" rows="8" cssStyle="font-weight: bolder;"/>
							</td>
						</tr>
						<c:if test="${not empty movimentoPecCommand.pec.dataprotocollo and not empty movimentoPecCommand.pec.numeroprotocollo}">
							<tr>
								<td>
									<fmt:message key="label.numero_protocollo" />
								</td>
								<td colspan="2">
									<spring-form:input readonly="true" id="numeroprotocollo_id" path="pec.numeroprotocollo" size="10" cssStyle="font-weight: bolder;"/>
								</td>
								<td>
									<fmt:message key="label.data_protocollo" />
								</td>
								<td colspan="3">
									<spring-form:input readonly="true" id="dataprotocollo_id" path="pec.dataprotocollo" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>
									<%-- 
									<init:calendar imagePath="/images/cal.gif" idImage="calDataprotocollo" idInput="dataprotocollo_id" textKey="label.calendar" />
									--%> 			  	
								</td>
							</tr>
						</c:if>
						</c:if>
						<c:if test="${not empty error}">
							<tr>
								<td colspan="6">
									<span class="error">${error}</span>		
								</td>
							</tr>
						</c:if>
					</c:if>
					<c:if test="${empty movimentoPecCommand.pec and not empty error}">
						<tr>
							<td colspan="6">
								<span class="error">${error}</span>		
							</td>
						</tr>
					</c:if>
				</table>
				
			</spring-form:form>	
			
		<div id="functions">
			<ul>
				<c:if test="${empty error and not movimentoPecCommand.readOnly}">
					<li><a href="javascript:void();" id="btn_salva"><fmt:message key="label.salva" /></a></li>
				</c:if>
				<li><a href="javascript:void();" id="btn_indietro"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
			 		
	</div>

	<script type="text/javascript">
	
	
	function ajaxHistorySet(url){
		new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
			  method: 'get',
			  parameters: {ReturnTo: url, limit: 12},
			  onSuccess: function(transport){},
			  onFailure: function(){}			  
		});
	}
	
	  jQuery(document).ready(function(){
		  
		  jQuery('#btn_salva').click(creaMovimento);
		  jQuery('#btn_indietro').click(indietro);
		  jQuery('#tipoMovimentoInputId_id1').add('#tipoMovimentoInputId_id2').add('#istanza_id_id').css('fontWeight','bolder');
		});
	  
	  function creaMovimento(){
		  doSubmit('${pageContext.request.contextPath}/pecinbox/creaMovimento.htm','', document.movimentoDaPecForm);
		  disableFunctions();
	  }
	  
	  function indietro(){
		  doHref("${pageContext.request.contextPath}/pecinbox/indietro.htm?idPec=${movimentoPecCommand.pec.id.id}", "");
	  }
	</script>	

</body>
</html>