<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${vwConcessionilista.id.codice==null}">
			<fmt:message key="form.vwConcessionilista.title.create" />
		</c:if> 
	</title>
	<style type="text/css">
		select#istanza_comune {
		    margin-left: 94px;
		}
	</style>
</head>
<body>

	<%
		pageContext.setAttribute("SEARCH_CONC_DEFAULT", WebConstants.SEARCH_CONC_DEFAULT);
		pageContext.setAttribute("SEARCH_CONC_PER_GESTIONE_SPUNTA", WebConstants.SEARCH_CONC_PER_GESTIONE_SPUNTA);
	%>

	<span class="titoloPagina">
		<fmt:message key="form.vwConcessionilista.title.create" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
			    <jsp:param name="path" value="../vwconcessionilista/create" />
	</jsp:include> 
	<div id="subcontent">
		<spring-form:form commandName="vwConcessionilista" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="vwConcessionilista" />
	    </jsp:include>
	    <%
		String mercatiUso="display:none;";
		String posteggi="display:none;";
		String intevalloDate="display:none;";
		%>
		<div class="vbg-form" >
			<fieldset>
				<legend><fmt:message key="label.dati_concessione" /></legend>
					<div class="form-group">
						<label><fmt:message key="label.concessione_numero_concessione" /></label>					
						<spring-form:input id="numero_id" path="concNumero" size="20" />
						<spring-form:errors path="concNumero" cssClass="error" />  					
					</div>
					<div class="form-group">
						<label><fmt:message key="label.concessione_titolare" /> o <fmt:message key="mercatid.label.occupante" /></label>					
						<jsp:include page="../includes/anagraficasearch.jsp">
							<jsp:param name="idElemento" value="titolareConcessioneId" />
							<jsp:param name="pathAnagrafica" value="titolareConcessione" />
							<jsp:param name="anagrafeHideFunctions" value="true" />
						</jsp:include>					
					</div>
					<div class="form-group">
						<label><fmt:message key="form.vwConcessionilista.dataRilascio" /></label>
						<fmt:message key="form.vwConcessionilista.data.inizio" />
						<spring-form:input id="dataInizio_id" path="dataInizioRilascio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
						<fmt:message key="form.vwConcessionilista.data.fine" />
						<spring-form:input id="dataFine_id" path="dataFineRilascio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
						<spring-form:errors path="dataFineRilascio" cssClass="error" delimiter=" :"/>  					
					</div>
					<div class="form-group">
						<label><fmt:message key="form.vwConcessionilista.dataScadenza" /></label>
						<fmt:message key="form.vwConcessionilista.data.inizio" />
						<spring-form:input id="dataInizioScadenza_id" path="dataInizioScadenze" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatainizioScadenza" idInput="dataInizioScadenza_id" textKey="label.calendar"/>
						<fmt:message key="form.vwConcessionilista.data.fine" />
						<spring-form:input id="dataFineScadenza_id" path="dataFineScadenze" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafineScadenza" idInput="dataFineScadenza_id" textKey="label.calendar"/>
						<spring-form:errors path="dataFineScadenze" cssClass="error" delimiter=" :"/>  
					</div>
					<div class="form-group">
						<label><fmt:message key="label.data_cessazione" /></label>
						<fmt:message key="form.vwConcessionilista.data.inizio" />
						<spring-form:input id="dataStoricoDaTransient_id" path="dataStoricoDaTransient" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataStoricoDaTransient" idInput="dataStoricoDaTransient_id" textKey="label.calendar"/>
						<fmt:message key="form.vwConcessionilista.data.fine" />
						<spring-form:input id="dataStoricoATransient_id" path="dataStoricoATransient" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataStoricoATransient" idInput="dataStoricoATransient_id" textKey="label.calendar"/>
					</div>
					<div class="form-group">
						<label><fmt:message key="label.registro" /></label>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="contipologiaregistri" />				
							<jsp:param name="propertyPath" value="contipologiaregistri" />			
							<jsp:param name="pathPropertyDescription" value="contipologiaregistri.trDescrizione" />
							<jsp:param name="pathPropertyCode" value="contipologiaregistri.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipologiaRegistriPerManifestazioni.htm" />
							<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
						</jsp:include>
				    </div>
					<div class="form-group" id="mercati">
						<label><fmt:message key="form.vwConcessionilista.mercati" /></label>
						<script type="text/javascript">
							function setHiddenFieldmercati(inputField,listItem){
								var a = listItem.id;
								document.getElementById('mercati_id').value = inputField.value;
								document.getElementById('mercati_hidden').value = a;
								$('mercati_id_choices').fade();	 
								mercatiUsoDisplay();
							}
						</script>
						<spring-form:input id="mercati_id" path="concMercato" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
						<init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
						<spring-form:errors path="concIdmercato" cssClass="error"/> 
						<spring-form:hidden id="mercati_hidden" path="concIdmercato" />
					</div>
			        <div class="form-group" id="mercatiUso" style="<%=mercatiUso%>">
						<label><fmt:message key="form.vwConcessionilista.mercatiUso" /></label>
						<spring-form:select id="selectMercatiUso" path="concIdmercatiuso" />
						<spring-form:errors path="concIdmercatiuso" cssClass="error" /> 
					</div>
					<div class="form-group" id="posteggi" style="<%=posteggi%>">
						<label><fmt:message key="form.vwconcessionilista.posteggio" /></label>
						<spring-form:select id="selectPosteggio" path="concIdposteggio" />
						<spring-form:errors path="concIdposteggio" cssClass="error" /> 
					</div>
					<div class="form-group">
						<label><fmt:message key="form.vwConcessionilista.concessionicausali" /></label>
						<spring-form:select path="iconcCodicecausale">
						<spring-form:option value="0"><fmt:message key="label.select.default"/></spring-form:option>
						<spring-form:options items="${concessioniCausaliList}" itemLabel="descrizione" itemValue="id.codice"/>
						</spring-form:select><spring-form:errors path="iconcCodicecausale" cssClass="error"/> 
					</div>
					<div class="form-group">
						<label><fmt:message key="form.vwConcessionilista.soloConcAttive" /></label>
						<spring-form:checkbox id="ckb_solo_attive_id" path="concAttiva" onchange="mostraIntevalloDate(this)"/>
					</div>
					<div class="form-group" id="dateAattivaid" style="<%=intevalloDate%>">
						<label><fmt:message key="form.vwConcessionilista.data.fine" /></label>
						<spring-form:input id="attiveAllaDataTransient_id" path="attiveAllaDataTransient" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="calAttiveAllaDataTransient_id" idInput="attiveAllaDataTransient_id" textKey="label.calendar"/>
					</div>				
				</fieldset>
				<c:if test="${TIPO_SEARCH_CONC eq SEARCH_CONC_DEFAULT }">
					<fieldset>
						<legend><fmt:message key="label.dati_istanza"/></legend>		
						<c:if test="${fn:length(comuniassociatiListInRequest) > 1}">
							<div class="form-group" id="elementIdBeforeCombo">		
								<jsp:include page="../includes/comboComuni.jsp">
									<jsp:param name="mostraTutti" value="true" />
									<jsp:param name="readOnly" value="false" />
									<jsp:param name="commandPropertyPath" value="istanza.comune" />
									<jsp:param name="colspan" value="4" />
									<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
								</jsp:include>
							</div>
						</c:if>	
						<div class="form-group">
							<label><fmt:message key="label.numeroistanza" /></label>
							<spring-form:input id="numeroistanza_id" path="istanza.numeroistanza" size="20"/>
						</div >
						<div class="form-group">
							<label><fmt:message key="label.data_presentazione" /></label>
							<fmt:message key="label.dalla_data" />
							<spring-form:input id="dallaData_ist_id" path="istanzadataDa" size="10" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio_" idInput="dallaData_ist_id" textKey="label.calendar" /> 			  	
							<spring-form:errors path="istanzadataDa" cssClass="error" delimiter="," />
							<fmt:message key="label.alla_data" />
							<spring-form:input id="allaData_ist_id" path="istanzadataA" size="10" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="calDataFine_" idInput="allaData_ist_id" textKey="label.calendar" /> 
							<spring-form:errors path="istanzadataA" cssClass="error" delimiter="," />
						</div>
						<div class="form-group">
							<label><fmt:message key="label.richiedente" /></label>
							<jsp:include page="../includes/anagraficasearch.jsp" >
								<jsp:param name="idElemento" value="richiedenteIdCodice" />						
								<jsp:param name="pathAnagrafica" value="istanza.richiedente" />
								<jsp:param value="true" name="anagrafeHideFunctions"/>
								<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?statoAnagrafe=ALL" />
							</jsp:include>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.alberoproc" /></label>
							<jsp:include page="../includes/searchAlberoProc.jsp">
								<jsp:param name="propertyPath" value="istanza.alberoproc" />								
								<jsp:param name="pathPropertyDescription" value="istanza.alberoproc.vwAlberoproc.scDescrizione" />
								<jsp:param name="pathPropertyCode" value="istanza.alberoproc.id.codice" />
								<jsp:param name="isSelectLeafDisable" value="true" />
								<jsp:param name="isSelectNodoPadre" value="true" />
							</jsp:include>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.tipiprocedure.procedura" /></label>
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="tipiprocedure_id" />				
								<jsp:param name="propertyPath" value="istanza.procedura" />			
								<jsp:param name="pathPropertyDescription" value="istanza.procedura.procedura" />
								<jsp:param name="pathPropertyCode" value="istanza.procedura.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=true" />
								<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
							</jsp:include>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.indirizzo" /></label>	
							<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="stradario_id" />
								<jsp:param name="propertyPath" value="istanzestradario.stradario" />								
								<jsp:param name="pathPropertyDescription" value="istanzestradario.stradario.descrizione" />
								<jsp:param name="pathPropertyCode" value="istanzestradario.stradario.id.codice" />
								<jsp:param name="autocompleterAjax" value="findStradario.htm" />
								<jsp:param name="titleKey" value="label.ricerca_stradario" />
							</jsp:include>
						</div>
						<div class="form-group">		
							<label><fmt:message key="label.cap" /></label>
							<spring-form:input id="cap_id" path="istanzestradario.cap" size="5" />
						</div>	
					</fieldset>		
				</c:if>		
			
			<div class="form-group">
				
			</div>
			<div class="form-group">
				<label><fmt:message key="label.ordinare_la_lista_per" /></label>

				<spring-form:select id="ordinamento_id" path="orderBy" onchange="savePreferenceFieldOrder('ordinamento_id')">
				    <spring-form:option value="CONC_NUMERO,CONC_DATARILASCIO"><fmt:message key="label.numero_data_rilascio"/></spring-form:option>
				    <spring-form:option value="CONC_DATARILASCIO,CONC_NUMERO"><fmt:message key="label.data_rilascio_numero"/></spring-form:option>
				    <spring-form:option value="DATA_ISTANZA"><fmt:message key="label.data_presentazione"/></spring-form:option>
				    <spring-form:option value="CONC_CODICETITOLARE"><fmt:message key="label.richiedente"/></spring-form:option>
				    <spring-form:option value="IST_NUMEROISTANZA"><fmt:message key="label.num_istanza"/></spring-form:option>
				    <spring-form:option value="STRADARIO_DESCRIZIONE"><fmt:message key="label.localizzazione"/></spring-form:option> 					    		
				</spring-form:select>
				<!--Gestisce il tipo ordinamento nella ricerca delle autorizzaizoni (ASC,DESC)  -->

				<% 
					String ordinamentoAsc = "";
			        String ordinamentoDesc = "";
					//gestisce la visualizzazione della ricerca per altri indirizzi
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ORDINAMENTO_CONCESSIONI)).equals("ASC")) {
				
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
			</div>				
		</div>
	</spring-form:form>
	     <%
			String urlStampe = BackofficeNETConstants.getUrlToPopupdecorator(request,BackofficeNETConstants.getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI()+"?SoloDocTipo=1&windowed=S","",(String)session.getAttribute(WebConstants.SOFTWARE));
			pageContext.setAttribute("url_stampe", urlStampe);
		%>	
		<script type='text/javascript'>
		$('numero_id').focus();
		function mercatiUsoDisplay(){
			removeOptionSelected("selectMercatiUso");
			removeOptionSelected("selectPosteggio");
			var idMercato=document.getElementById("mercati_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiUso").appear();
					  var opts=response.split(",");
					  var select = $('selectMercatiUso');
					  for(var i=1;i<opts.length;i++){
						  select.options[select.options.length] = new Option(opts[i],opts[i-1],false,false);
						  i++;
					  }
				    },
				  onFailure: function(){  }
								  
			});
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggioMercato.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("posteggi").appear();
					  var opts=response.split(",");
					  var select = $('selectPosteggio');
					  select.options[select.options.length] = new Option("<fmt:message key='label.select.default' />","");
					  for(var i=1;i<opts.length;i++){
						  select.options[select.options.length] = new Option(opts[i],opts[i-1],false,false);
						  i++;
					  }
				    },
				  onFailure: function(){  }
								  
			});
		}
		
		function removeOptionSelected(opt){
		  let elSel=document.getElementById(opt);
		  let i;
		  for (i=elSel.length-1;i>=0;i--){
		    if (elSel.options[i]){
		       elSel.remove(i);
		    }
		  }
		}
		
		function savePreferenceTypeOrder(obj){
			let a=document.getElementById(obj).value;
			saveUserPreference('<%=WebConstants.CONF_UTENTE_ORDINAMENTO_CONCESSIONI%>',a);
		}
		
		function savePreferenceFieldOrder(obj){
			let a=document.getElementById(obj).value;
			saveUserPreference('<%=WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI%>',a);
		}
	
		function stampa(){
			let urlStampe = '<%=request.getContextPath()%>/vwconcessionilista/popupstampa.htm?';
			urlParams = jQuery('form').serialize();
			//urlStampe = urlStampe + escape("&" + urlParams);
			urlStampe = urlStampe + "&" + urlParams;
			console.info(urlStampe);
			let wii = window.open(urlStampe,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
		}
		
		function mostraIntevalloDate(val)
		{
			//console.log("Metodo commentato fino a quando non verrà implementata la query di ricerca giusta")
			let checkedValue = document.querySelector('#ckb_solo_attive_id').checked;
			console.log(checkedValue);
			if(checkedValue){
				document.querySelector("#dateAattivaid").show();
				}
			else{
				document.querySelector("#dateAattivaid").hide();
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
		
		function searchConcessioni(){
			var url  = URLDecode('${_urlback}');
			ajaxHistorySet(url);	
			setTimeout("doSubmit('search.htm?codiceIstanza=${codiceIstanza}&modalita_ricerca=${TIPO_SEARCH_CONC}','',document.inviodati)",10);
		}	
			
		</script>
	</div>
	<div class="form-button">
		<a class="btn btn-primary" href="javascript:searchConcessioni();"><fmt:message key="button.search" /></a>
		<a class="btn btn-primary" href="javascript:void 0" onclick="stampa();"><fmt:message key="button.stampa" /></a>
		<a class="btn btn-secondary" href="javascript:historyBack('')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>
