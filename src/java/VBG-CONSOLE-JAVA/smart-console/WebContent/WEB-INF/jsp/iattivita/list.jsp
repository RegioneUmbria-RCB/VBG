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
		<div id="functions">
			<ul>
			    <c:if test="${isFunzioneDiUtility eq false}">
					<%-- <li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>  --%>				
					<%-- STAMPA DOCUMENTO TIPO --%>
					<li><a href="#" onclick="window.open('popupstampa.htm',69,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a></li>
					<%-- END STAMPA DOCUMENTO TIPO --%>				
					<li><a href="javascript:exportIAttivita(${numeroAttivitaTrovate});"><fmt:message key="button.esporta" /></a></li>
					<li><a href="javascript:exportIAttivitaInData(${numeroAttivitaTrovate});"><fmt:message key="button.esporta_in_data" /></a></li>
				</c:if>
				<c:if test="${isFunzioneDiUtility eq true}">
					<li><a href="javascript:addSchede()"><fmt:message key="button.aggiungi_scheda" /></a></li>
				</c:if>
					<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		<script type="text/javascript">
		
		function addSchede()
		{
			var  a =document.getElementById("dyn2Modellit_id_hidden");
			if(a.value!='')
			{
				doSubmit('../iattivita/addSchedaDinamicaToIattivita.htm?codicescheda='+a.value,'',document.inviodati_second);
			}else
			{
				alert('Deve essere scelta una scheda da collegare');
			}
		}
		
		function exportIAttivita(numAttivita){		
			
			if(numAttivita>0)
			{
			var secondDlg = new dijit.Dialog({
	            title: "<fmt:message key="label.export_dati" />" ,
	            style: "overflow:auto; width: 650px;height: 300px;"
	        });
			disableFunctions(); 
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
			}else
			{
				alert('Attenzione, la ricerca non ha prodotto una lista di attività per effettuare l\'esportazione');	
			}
		}
		
		
		function exportIAttivitaInData(numAttivita){	
			
			if(numAttivita>0)
			{
				
			var secondDlg_indata = new dijit.Dialog({
	            title: "<fmt:message key="label.export_dati" />" ,
	            style: "overflow:auto; width: 650px;height: 300;"
	        });
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
			
			}else
			{
			    alert('Attenzione, la ricerca non ha prodotto una di attività per effettuare l\'esportazione');
			}
		}
		
		
		
		
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
					document.location.href='ajaxExport.htm?codice='+ codice[0] + '&descrizione=' + escape(codice[1])+'&email='+email+'&isInviaMail='+checkInvioMail;
				}
			}
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
					
					//document.location.href='ajaxExportInData.htm?codice='+ codice[0] + '&descrizione=' + escape(codice[1])+'&email='+email+'&isInviaMail='+checkInvioMail;
					
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
		
		function isDataPresent()
		{
			if(document.getElementById('data_id').value=='')
			{
				alert('Data Obbligatoria');
				return false;
			}
			return true;
		}
		
		
		var secondDlg_indata = new dijit.Dialog({
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
						  onFailure: function(transport){ 
							  //enableFunctions();
						  	  //var response = transport.responseText;
						  	  //secondDlg_indata.attr("content", response);
						  	  //secondDlg_indata.show();	
						  }
					});	
					
					
					
					
				}
		
		
		
		
		</script>	
	</body>		
</html>