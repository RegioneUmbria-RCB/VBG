<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.gestione_attivita" /></title>
		<script>
			vbg.ready(() => {
			if(${cartograficoAttivo} === true){
				
				let buttonMostraInMappa = document.querySelector('.mostra-mappa');
				buttonMostraInMappa.addEventListener('click',async (e) => {
					e.preventDefault();
					window.vbg.mostraModalCaricamento();
					try
					{
						await visualizzaMappa();
					}
					catch(error) {
						console.log(error);
						alert('Si sono verificati errori durante l\'apertura della mappa: ' + error);
					}
					window.vbg.nascondiModalCaricamento();
				});
				
			}
			
			
			async function visualizzaMappa(){
				
				const response = await fetch('../istanzestradariocartografico/jsonMostraAttivitaInMappa.htm', {
	        		method: 'POST',
	                headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json',
	                }
	        	});
				
				const jsResponse = await response.json();
				
				if(jsResponse.esito.esito == "KO"){
					throw new Error(jsResponse.esito.exceptions.join(' - '));
				}
				
				if( jsResponse.method === "GET" ){
					location.replace(jsResponse.url);
					return;
				}
				if( jsResponse.method === "POST" ){
					const formMappa = document.createElement("form");
					formMappa.method = "POST";
					formMappa.action = jsResponse.url;
					console.log(jsResponse.body);
					jsResponse.body.forEach(item => {
						const input = document.createElement("input");
						input.type = "hidden";
						input.name = item.chiave;
						input.value = item.valore;
						formMappa.appendChild(input);
					});
					
					document.body.appendChild(formMappa);
					formMappa.submit();
					return;
				}
			}
			
			function addSchede()
			{
				var a = document.getElementById("dyn2Modellit_id_hidden");
				if(a.value!='')
				{
					doSubmit('../iattivita/addSchedaDinamicaToIattivita.htm?codicescheda='+a.value,'',document.inviodati_second);
				}else
				{
					alert('Deve essere scelta una scheda da collegare');
				}
			}
			
			let addScheda = document.querySelector('#add_scheda_id');
			if (addScheda !== null) {
				addScheda.addEventListener('click',()=>{
					addSchede();	
				});
			}
			
			let exp = document.querySelector('#export_iattivta_id');
			if(exp !== null){
				exp.addEventListener('click',(e)=>{
					exportIAttivita(${numeroAttivitaTrovate});
				});	
			}
			
			function exportIAttivita(numAttivita){		
				if(numAttivita>0) {
					if (${OBS_EXPORT}) {
						disableFunctions();
						var secondDlg = new dijit.Dialog({
				            title: "<fmt:message key="label.export_dati" />" ,
				            style: "overflow:auto; width: 480px;height: 300px;"
				        });
						new Ajax.Request('<%=request.getContextPath()%>/ajax/exportIAttivita.htm', {
							  method: 'post',
							  parameters: {},
							  onSuccess: function(transport){
								  enableFunctions();
								  var response = transport.responseText;		
								  result = parseAjaxResponse(response, true, false);
								  secondDlg.attr("content", result);
							      secondDlg.show();							  
							  },
							  onFailure: function(transport){ 
								  enableFunctions();
							  	  var response = transport.responseText;
								  secondDlg.attr("content", response);
								  secondDlg.show();	
							  }
						});
					}
					else
					{
						goToExportPentahoPanel('ATT');
					}
				}
				else {
					alert('Attenzione, la ricerca non ha prodotto una lista di attività per effettuare l\'esportazione');	
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
			
			let expD = document.querySelector('#export_iattivta_in_data_id');
			if(expD !== null){
				expD.addEventListener('click',(e)=>{
					exportIAttivitaInData(${numeroAttivitaTrovate});
				});
			}
						
			function exportIAttivitaInData(numAttivita){	
				if(numAttivita>0) {
					var secondDlg_indata = new dijit.Dialog({
			            title: "<fmt:message key="label.export_dati" />" ,
			            style: "overflow:auto; width:480px;height: 300;"
			        });
					
					if (${OBS_EXPORT}) {
						disableFunctions(); 
						new Ajax.Request('<%=request.getContextPath()%>/ajax/exportIAttivita.htm?contestoExport=ATS', {
							  method: 'post',
							  parameters: {},
							  onSuccess: function(transport){
								  enableFunctions();
								  var response = transport.responseText;		
								  result = parseAjaxResponse(response, true, false);
								  secondDlg_indata.attr("content", result);
								  secondDlg_indata.show();							  
							  },
							  onFailure: function(transport){ 
								  enableFunctions();
							  	  var response = transport.responseText;
							  	  secondDlg_indata.attr("content", response);
							  	  secondDlg_indata.show();	
							  }
						});
					}
					else {
						goToExportPentahoPanel('ATS');
					}
				}
				else {
				    alert('Attenzione, la ricerca non ha prodotto una di attività per effettuare l\'esportazione');
				}
			}
			
			function changeEsportazionePentaho(id){
				secondDlg.hide();
				var qs = getQueryStringFromForm(document.inviodati_second);
				var codExpAndComune=id.value;
				new Ajax.Request('<%=request.getContextPath()%>/ajax/exportIAttivitaPentaho.htm?codiceExpAndcomune='+codExpAndComune+'&'+qs, {
					  method: 'post',	
					  onSuccess: function(transport){
						  enableFunctions();
						  var response = transport.responseText;		
						  result = parseAjaxResponse(response, true, false);
						  secondDlg.attr("content", result);
					      secondDlg.show();							  
					  },
					  onFailure: function(transport){ 
						  enableFunctions();
					  	  var response = transport.responseText;
						  secondDlg.attr("content", response);
						  secondDlg.show();	
					  }					    		 
				});
			}
			
			function goToExportPentahoPanel(contesto){
				var url  = URLDecode('${_urlback}');			
				ajaxHistorySet(url);			
				if(contesto == 'ATS') {
					setTimeout("doSubmit('../iattivita/createExportModalitaPentaho.htm?1=1&contestoExport=ATS','',document.inviodati_second)",10);
				}
				else {
					setTimeout("doSubmit('../iattivita/createExportModalitaPentaho.htm?1=1','',document.inviodati_second)",10);
				}
			}
			
			
			
			function esportaPentaho(){
				
				var qs = getQueryStringFromForm(document.iattivita);
				doSubmit('esportaPentaho.htm?='+ qs,'',document.inviodati_second);
			}
			
			function esportaInData(){
				var codice = getSelectLabelAndValue(document.getElementById("tipoEsportazione_in_data_id"));
				var email = document.getElementById("responsabile_email_id").value;
				var data = document.getElementById("data_id").value;
				var checkInvioMail = false;
				if(document.getElementById("invio_email_id").checked)
				{
					if(email == '' || email == null )
					{
						alert('Attenzione, si è deciso di inviare l\'esportazione per e-mail, ma non ne è stata configurata una');
					    checkInvioMail = false;
					}else
					{
						checkInvioMail=true;
					}
				}
				if(codice){
					if(codice[0]!=''){
						doSubmit('ajaxExportInData.htm?codice='+ codice[0] + '&descrizione=' + escape(codice[1])+'&email='+email+'&isInviaMail='+checkInvioMail+'&dataEsportazione='+data,'',document.inviodati_second);
					}
				}
			}
			

			
			// Javascrip per mostrare il calendario (Viene richiamto all'interno della jsp ajax/exportIAttivitaInData.jsp)
			function setupCal(inputId, imageId){
				RANGE_CAL_1 = new Calendar({
					inputField: inputId,
					dateFormat: "%d/%m/%Y",
					trigger: imageId,
					bottomBar: false,
					onSelect: function() {
						var date = Calendar.intToDate(this.selection.get());
						this.hide();
					}
				})
			}
			
			function isDataPresent() {
				if(document.getElementById('data_id').value=='') {
					alert('Data Obbligatoria');
					return false;
				}
				return true;
			}
			
			let secondDlg_indata = new dijit.Dialog({
	            title: "<fmt:message key="label.schede_dell_attivita" />" ,
	            style: "overflow:auto; width: 650px;height: 300;"
	        });
			
			function showModelliDinamiciAttivita(codice,codModello){	
				disableFunctions(); 
				new Ajax.Request('<%=request.getContextPath()%>/iattivitadyn2dati/ajaxViewModelli.htm?codiceAttivita='+codice+'&codiceModello='+codModello, {
					method: 'post',
					parameters: {},
					onSuccess: function(transport){
						enableFunctions();
						var response = transport.responseText;		
						result = parseAjaxResponse(response, true, false);
						secondDlg_indata.attr("content", result);
						secondDlg_indata.show();							  
					},
					onFailure: function(transport){}
				});	
			}
		});
			
			function esporta(){
				var codice = getSelectLabelAndValue(document.getElementById("tipoEsportazione_id"));
				var email = document.getElementById("responsabile_email_id").value;
				var checkInvioMail = false;
				if(document.getElementById("invio_email_id").checked)
				{
					if(email == '' || email == null )
					{
						alert('Attenzione, si è deciso di inviare l\'esportazione per e-mail, ma non ne è stata configurata una');
						checkInvioMail = false;
					}else
					{
					checkInvioMail=true;
					}
				}
				if(codice){
					if(codice[0]!=''){
						document.location.href='ajaxExport.htm?codice='+ escape(codice[0]) + '&descrizione=' + escape(codice[1])+'&email='+email+'&isInviaMail='+checkInvioMail;
					}
				}
			}
			
			
			function getSelectLabelAndValue(selectObj){
				var pos=selectObj.selectedIndex;
				var valore='';
				var testo='';
				if (pos>-1) {
					valore=selectObj.options[pos].value;
					testo=selectObj.options[pos].label;
				} 
				var valori =	new Array(valore, testo);
				return valori;
			}
		</script>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.gestione_attivita" /></span>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../iattivita/list" />
	</jsp:include>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">			

			<form name="inviodati" action="list.htm">
				${htmltable}
			</form>
			<br />
			<spring-form:form commandName="iattivitaCommand" name="inviodati_second">
			<c:if test="${isFunzioneDiUtility}">
			<table>
				<tr>
					<td>
						<fmt:message key="label.selziona_scheda" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
							<jsp:param name="idElemento" value="dyn2Modellit_id" />		
							<jsp:param name="propertyPath" value="dyn2Modellit" />				
							<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
							<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
							<jsp:param name="autocompleterAjax" value="findDyn2ModelliAttivitaCurretSoftwareOrTT.htm?codicesoftware=${iattivitaCommand.attivitaFilter.software.codice}" />	
							<jsp:param name="titleKey" value="label.ricerca_modelli" />
							<jsp:param name="id_help" value="help_modello" />
							<jsp:param name="help" value="help.modelli_archivi_base" />
						</jsp:include>
					</td>
				</tr>
			</table>
			</c:if>	
			</spring-form:form>
			<c:set var="_isIstanza">false</c:set>
			<c:if test="${not empty iattivitaCommand.istanza.id.codice }">
				<c:set var="_isIstanza">true</c:set>
			</c:if>		
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="label.gestione_attivita" />';
				var isIstanza=${_isIstanza};
				function viewOrAssign(idattivita){	
					if(isIstanza){
						var confirmed = confirm('<fmt:message key="javascript.confirm.associa_attivita" />');
						if(confirmed){
							doHref('../iattivita/viewOrAssign.htm?codice='+idattivita,'');
						}
					}else{					
							historySet('${_urlback }','../iattivita/viewOrAssign.htm?codice='+idattivita,'');
					}
				}
			</script>			
		</div>
		<div class="form-button">
			<c:if test="${isFunzioneDiUtility eq false}">
				<a class="btn btn-primary" href="#" onclick="window.open('popupstampa.htm',69,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a>
				<a class="btn btn-primary" id="export_iattivta_id" href="javascript:void(0);"><fmt:message key="button.esporta" /></a>
				<a class="btn btn-primary" id="export_iattivta_in_data_id" href="javascript:void(0);"><fmt:message key="button.esporta_in_data" /></a>
			</c:if>
			<c:if test="${isFunzioneDiUtility eq true}">
				<a id="add_scheda_id" class="btn btn-primary"><fmt:message key="button.aggiungi_scheda" /></a>
			</c:if>
			<c:if test="${cartograficoAttivo eq true }">
				<a class="btn btn-primary mostra-mappa" href="javascript: void 0;"><fmt:message key="button.mostra_in_mappa" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		</div>
		<script type="javascript"></script>
	</body>		
</html>