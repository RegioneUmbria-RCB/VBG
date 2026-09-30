<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento"%>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
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
		<br class="clear" />
		<spring-form:form commandName="movimentoPecCommand" name="movimentoDaPecForm" id="movimentoDaPecForm" action="${pageContext.request.contextPath}/pecinbox/creaMovimento.htm">
			<div class="vbg-form">
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
			    <fieldset>
			    	<legend><fmt:message key="pecinbox.label.dati_pec"/></legend>
			    	<jsp:include page="datiPEC.jsp" >
				        <jsp:param name="commandName" value="movimentoPecCommand" />
				    </jsp:include>
				    <div class="form-group">
						<label><fmt:message key="pecinbox.label.scompattaallegaticompressi" /></label>
						<spring-form:checkbox path="scompattaAllegati" value="true" />
						<init:help idHelp="helpScompattaAllegati" textKey="pecinbox.label.scompattaallegaticompressi.helpmovimento" />
					</div>			    
			    </fieldset>
			    <c:if test="${not empty movimentoPecCommand.pec}">
			    <fieldset>
			    	<legend><fmt:message key="pecinbox.label.dati_movimento"/></legend>
			    	<c:if test="${empty error }">
		    			<div class="form-group">
							<label><fmt:message key="label.data" /></label>
							<spring-form:input readonly="true" id="datapec_id" path="movimento.data" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>
						</div>
						<div class="form-group">
							<c:if test="${not movimentoPecCommand.readOnly}">
								<label class="required">*<fmt:message key="pecinbox.label.istanza" /></label>
							</c:if>
							<c:if test="${movimentoPecCommand.readOnly}">
								<label><fmt:message key="pecinbox.label.istanza" /></label>
							</c:if>							
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
						</div>
						<div class="form-group">
							<c:if test="${not movimentoPecCommand.readOnly}">
								<label class="required">*<fmt:message key="label.tipomovimento" /></label>
							</c:if>
							<c:if test="${movimentoPecCommand.readOnly}">
								<label><fmt:message key="label.tipomovimento" /></label>
							</c:if>
							<jsp:include page="../includes/tipimovimentosearch.jsp" >
								<jsp:param name="idElemento" value="tipoMovimentoInputId" />
								<jsp:param name="pathTipomovimento" value="movimento.tipomovimento" />
								<jsp:param name="afterUpdateElement" value="checkTipomovimento" />
								<jsp:param name="readOnly" value="${movimentoPecCommand.readOnly}" />	
								<jsp:param name="tipimovimentoAutocompleterAjax" value="findTipiMovimentoUsatiDalProtocollo.htm?codice=" />
								<jsp:param name="ajaxCallBack" value="filterMovimentiIstanza" />								
							</jsp:include>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.amministrazione"/></label>
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="amministrazioni" />		
								<jsp:param name="propertyPath" value="movimento.amministrazioni" />				
								<jsp:param name="pathPropertyDescription" value="movimento.amministrazioni.descrizioneEstesa" />
								<jsp:param name="pathPropertyCode" value="movimento.amministrazioni.id.codice" />
								<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=false" />	
								<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
							</jsp:include>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.movimento" /></label>
							<spring-form:input id="movimento_id" path="movimento.movimento" size="70" readonly="${movimentoPecCommand.readOnly}" cssStyle="font-weight: bolder;"/>
							<c:set var="helpMovimentoText"><fmt:message key="label.selasciatovuoto" />&nbsp;<fmt:message key="label.tipomovimento" /></c:set>
							<init:help idHelp="helpDescMovimento" text="${helpMovimentoText}" />
						</div>
						<c:set var="displayEsito">display:;</c:set>	
						<c:if test="${movimentiCommand.entity.tipomovimento.tipologiaesito eq 0}">
							<c:set var="displayEsito">display:none;</c:set>
						</c:if>
						<div class="form-group" style="${displayEsito}" id="div_esito_id">
							<label><fmt:message key="label.esito_positivo" /></label>
							<spring-form:select id="esito_id" path="movimento.esito" disabled="${movimentoPecCommand.readOnly}" cssStyle="font-weight: bolder;">
								<spring-form:option value="true"><fmt:message key="label.si" /></spring-form:option>
								<spring-form:option value="false"><fmt:message key="label.no" /></spring-form:option>					
							</spring-form:select>
							<spring-form:errors path="movimento.esito" cssClass="error"/>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.note" /></label>							
							<spring-form:textarea readonly="${movimentoPecCommand.readOnly}" id="lavori_id" path="movimento.note" cols="120" rows="8" cssStyle="font-weight: bolder;"/>							
						</div>
						<c:choose>
							<c:when test="${not empty movimentoPecCommand.pec.dataprotocollo and not empty movimentoPecCommand.pec.numeroprotocollo}">
								<div class="form-group">
									<label><fmt:message key="label.numero_protocollo" /></label>
									<spring-form:input readonly="true" id="numeroprotocollo_id" path="numeroProtocollo" size="10" cssStyle="font-weight: bolder;"/>
									<label><fmt:message key="label.data_protocollo" /></label>
									<spring-form:input readonly="true" id="dataprotocollo_id" path="dataProtocollo" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>
								</div>
							</c:when>
							<c:otherwise>
								<div class="form-group">
									<label><fmt:message key="label.numero_protocollo" /></label>									
									<spring-form:input id="numeroprotocollo_id" path="numeroProtocollo" size="10" cssStyle="font-weight: bolder;"/>									
									<label><fmt:message key="label.data_protocollo" /></label>
									<spring-form:input id="dataprotocollo_id" path="dataProtocollo" size="10" onblur="isValidDate(this,true);" cssStyle="font-weight: bolder;"/>										
									<init:calendar imagePath="/images/cal.gif" idImage="calDataprotocollo" idInput="dataprotocollo_id" textKey="label.calendar" />								
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${not empty error}">
						<div class="form-group">						
							<span class="error">${error}</span>						
						</div>
					</c:if>
		    	</fieldset>
			    </c:if>	
			</div>
		</spring-form:form>			
		<div class="form-button">			
			<c:if test="${empty error and not movimentoPecCommand.readOnly}">
				<a class="btn btn-primary" href="#" id="btn_salva"><fmt:message key="label.salva" /></a>
			</c:if>
			<a class="btn btn-secondary" href="#" id="btn_indietro"><fmt:message key="button.back" /></a>			
		</div>			 		
	
	<script type="text/javascript">

		function checkTipomovimento(inputField,listItem){
			let idElemento='tipoMovimentoInputId';
			let a = listItem.id;
			document.getElementById(idElemento+'_hidden').value = a;
			
			if(document.querySelector('#id2_'+idElemento).style.display == 'inline'){
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
	
		async function checkEsito(tipoMovimento){
			
			const data = new URLSearchParams();
		 	data.append('tipoMovimento',tipoMovimento);		 
		 	
			let response = await fetch('../tipimovimento/ajaxEsitoTipomovimento.htm' , {
	            method: "POST",
	            cache: "no-cache",
	            body: data
	        });
			if(response.status == 200){
				let resp = await response.text();
				 result = resp.split("#");
				 if(result[0]!='0'){
				   	  document.getElementById("div_esito_id").style.display='';
				  }else{
					  document.getElementById("div_esito_id").style.display='none';
				  }
			}else{
				let resp = await response.text();
				alert(resp);
			}					
		}			
	
		function afterUpdateIstanza(inputField,listItem){
			
			let _spanToShow = document.querySelector('#show_' + listItem.id); 
			if(_spanToShow != null){
				inputField.value = _spanToShow.innerText;
				document.querySelector("[name = 'movimento.istanza.id.codice']").value = listItem.id;
			}			
		}
	
		function searchIstanzeParams(element, entry){
			let searchProtChk = document.querySelector('#searchProtocolloMovimentiChk');
			if(searchProtChk){
				if(searchProtChk.checked){
					return entry + "&protInMovimenti=true";
				}
			}
			return entry + "&protInMovimenti=false";
		}

	
		
		vbg.ready(() => {
		  if(document.querySelector('#btn_salva')){
		  	document.querySelector('#btn_salva').addEventListener('click', creaMovimento);
		  }
		  document.querySelector('#btn_indietro').addEventListener('click', indietro);	
		  if(document.querySelector('#tipoMovimentoInputId_id1') && document.querySelector('#tipoMovimentoInputId_id2')){
			  document.querySelector('#tipoMovimentoInputId_id1').style.fontWeight = 'bolder';
			  document.querySelector('#tipoMovimentoInputId_id2').style.fontWeight = 'bolder';
		  }
		  document.querySelector('#istanza_id_id').style.fontWeight = 'bolder';			 
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