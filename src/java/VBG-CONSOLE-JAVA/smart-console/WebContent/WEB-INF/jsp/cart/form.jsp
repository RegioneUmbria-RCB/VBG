<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="stp.label.pannellocontrollo.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="stp.label.pannellocontrollo.title" /></span>
	 	<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../cart/view" />
		</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<br class="clear" />
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="cart" />
	</jsp:include>
	<c:if test="${param.disableSchemaValidation eq true}">
		<br class="clear"/>
		<b>
		<span class="error_header">
			<fmt:message key="stp.label.messaggio_errore_validazione_schema" />
		</span>	
		</b>
		<div id="functions">
			<li><a href="javascript:doHref('eseguiOperazione.htm?operazione=${param.operazione}&idEgov=${param.idEgov }&servizio=${param.servizio }&disableSchemaValidation=${param.disableSchemaValidation }&ts_'+new Date().getTime(),'')"><fmt:message key="button.ripeti" /></a></li>
		</div>
		<br class="clear"/>
		<br class="clear"/>
	</c:if>	
	
	<fieldset>
	
<div class="jmesa">
<table border="0" cellpadding="2" cellspacing="0" class="table" width="100%">
	<tbody class="tbody">
		<tr class="titoloSezione">
			<td colspan="2"><fmt:message key="stp.label.richiestesuap" /></td>		
		</tr>	
		<!--
		<tr>
			<td width="15%"><fmt:message key="stp.label.richieste_inviodizionario" /></td>		
			<td>
				<span id="functions">
					<ul>				
						<li><a href="javascript:richiestaDizionario();" id="richiestaDizionarioId"><fmt:message key="stp.label.richieste_inviodizionario" /></a></li>
					</ul>
				</span>
			<init:help idHelp="helpdizionario"	textKey="stp.help.inviodizionario" /></td>
		</tr>
		-->
		<tr>
			<td width="15%"><fmt:message key="stp.label.richiesta_schede_dizionario" /></td>		
			<td>
				<span id="functions">
					<ul>				
						<li><a href="javascript:richiestaSchedeDizionario('1');" id="richiestaSchedeDizionario1"> 	<fmt:message key="label.aggiorna" /> <fmt:message key="stp.label.richiesta_schede_dizionario_tipo1" /></a></li>
						<li><a href="javascript:richiestaSchedeDizionario('2');" id="richiestaSchedeDizionario2"><fmt:message key="label.aggiorna" />  <fmt:message key="stp.label.richiesta_schede_dizionario_tipo2" /></a></li>
					</ul>
				</span>
			<init:help idHelp="richiestaSchedeDizionario" textKey="stp.help.richiesta_schede_dizionario" /></td>
		</tr>
		<tr class="titoloSezione">
			<td colspan="2"><fmt:message key="stp.label.controllomessaggiregione" /></td>		
		</tr>
		<!--  		
				<tr class="even">
					<td ><fmt:message key="stp.label.disponibilitadizionario" /></td>		
					<td>
					
						
					
					
						<c:if test="${not empty messaggiDisponibilitaDizionario}">
							<select name="disponibilitaDizionario" id="disponibilitaDizionario">
								<c:forEach items="${messaggiDisponibilitaDizionario}" var="messaggio">
									<option value="${messaggio}">${messaggio}</option>
								</c:forEach>
							</select>
							<jsp:include page="../cart/funzioni_cart.jsp" >
								<jsp:param name="idElemento" value="disponibilitaDizionario" />
								<jsp:param name="showElabora" value="false" />								
							</jsp:include>
						</c:if>	
						<c:if test="${not empty messaggioErroreDisponibilitaDizionario}">
							<span class="error_header">${messaggioErroreDisponibilitaDizionario}</span>
						</c:if>
					</td>					
				</tr>
				<tr class="titoloSezione">
					<td colspan="2"></td>
				</tr>	
				-->				
				<%if(ORMHelper.isConsoleRegionale()){ %>
				
				<tr class="odd">
					<td><fmt:message key="stp.label.inviodizionario" /></td>		
					<td>
					<c:if test="${fn:length(dizionaridaElaborare) > 0}">
						<select name="invioDizionario"  id="invioDizionario">
							<c:forEach items="${dizionaridaElaborare}" var="messaggio">
								<option value="${messaggio.id}">Dizionario del <fmt:formatDate value="${messaggio.dataPubblicazione}" pattern="dd/MM/yyyy hh:mm:ss"/></option>
							</c:forEach>
						</select>
						<jsp:include page="../cart/funzioni_cart.jsp" >
							<jsp:param name="idElemento" value="invioDizionario" />
							<jsp:param name="showPreElabora" value="false" />
							<jsp:param name="showElabora" value="true" />										
						</jsp:include>
					</c:if>					
					<c:if test="${not empty messaggioErroreInvioDizionario}">
						<span class="error_header">${messaggioErroreInvioDizionario}</span>
					</c:if>
					</td>										
				</tr>	
				<%} %>
				<%--
				<tr class="titoloSezione">
					<td colspan="2"></td>
				</tr>										
				<tr  class="even">
					<td><fmt:message key="stp.label.disponibilitascheda1" /></td>					
					<td>
					<c:if test="${not empty messaggiDisponibilitaEndo1}">
						<select name="disponibilitaEndo1" id="disponibilitaEndo1">
							<c:forEach items="${messaggiDisponibilitaEndo1}" var="messaggio">
								<option value="${messaggio}">${messaggio}</option>
							</c:forEach>
						</select>
						<jsp:include page="../cart/funzioni_cart.jsp" >
							<jsp:param name="idElemento" value="disponibilitaEndo1" />
						</jsp:include>
					</c:if>
					<c:if test="${not empty messaggioErroreDisponibilitaEndo1}">
						<span class="error_header">${messaggioErroreDisponibilitaEndo1}</span>
					</c:if>
					</td>										
				</tr>		
				<tr class="titoloSezione">
					<td colspan="2"></td>
				</tr>					
				<tr  class="odd">
					<td><fmt:message key="stp.label.invioscheda1" /></td>					
					<td>
					<c:if test="${not empty messaggiInvioSchedaEndo1}">
						<select name="invioSchedaEndo1" id="invioSchedaEndo1">
							<c:forEach items="${messaggiInvioSchedaEndo1}" var="messaggio">
								<option value="${messaggio}">${messaggio}</option>
							</c:forEach>
						</select>
						<jsp:include page="../cart/funzioni_cart.jsp" >
							<jsp:param name="idElemento" value="invioSchedaEndo1" />		
							<jsp:param name="showElaboraTutti" value="true" />										
						</jsp:include>
					</c:if>
					<c:if test="${not empty messaggioErroreInvioSchedaEndo1}">
						<span class="error_header">${messaggioErroreInvioSchedaEndo1}</span>
					</c:if>
					</td>										
				</tr>		
				<tr class="titoloSezione">
					<td colspan="2"></td>
				</tr>		
				<tr class="even">
					<td><fmt:message key="stp.label.disponibilitascheda2" /></td>							
					<td>
					<c:if test="${not empty messaggiDisponibilitaEndo2}">
						<select name="disponibilitaEndo2" id="disponibilitaEndo2">
							<c:forEach items="${messaggiDisponibilitaEndo2}" var="messaggio">
								<option value="${messaggio}">${messaggio}</option>
							</c:forEach>
						</select>
						<jsp:include page="../cart/funzioni_cart.jsp" >
							<jsp:param name="idElemento" value="disponibilitaEndo2" />					
						</jsp:include>
					</c:if>	
					<c:if test="${not empty messaggioErroreDisponibilitaEndo2}">
						<span class="error_header">${messaggioErroreDisponibilitaEndo2}</span>
					</c:if>
					</td>					
				</tr>		
				<tr class="titoloSezione">
					<td colspan="2"></td>
				</tr>			
				<tr class="odd">
					<td><fmt:message key="stp.label.invioscheda2" /></td>					
					<td>
					<c:if test="${not empty messaggiInvioSchedaEndo2}">
						<select name="invioSchedaEndo2"  id="invioSchedaEndo2">
							<c:forEach items="${messaggiInvioSchedaEndo2}" var="messaggio">
								<option value="${messaggio}">${messaggio}</option>
							</c:forEach>
						</select>
						<jsp:include page="../cart/funzioni_cart.jsp" >
							<jsp:param name="idElemento" value="invioSchedaEndo2" />
							<jsp:param name="showElaboraTutti" value="true" />
						</jsp:include>
					</c:if>
					<c:if test="${not empty messaggioErroreInvioSchedaEndo2}">
						<span class="error_header">${messaggioErroreInvioSchedaEndo2}</span>
					</c:if>
					</td>										
				</tr>			
				 --%>
		<tr class="titoloSezione">
			<td colspan="2">&nbsp;</td>		
		</tr>	
		<tr>
			<td width="15%"><fmt:message key="label.ricarica_configurazioni" /></td>		
			<td>
				<span id="functions">
					<ul>				
						<li><a href="javascript:ricaricaConfigurazioni();" id="ricaricaConfigurazioniId"><fmt:message key="label.aggiorna" /></a></li>
					</ul>
				</span>
			</td>
		</tr>						
	</tbody>
</table>
</div>
</fieldset>


    <%--DIV che compare quando viene premuto il punsante Importa interventi --%>
	  
	<div dojoType="dijit.Dialog" id="dialogDiv" title="<fmt:message key="stp.label.aggiungi_interventi" />: ">
		<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px;height: 200px" >
			<div id="dialogAggiungiInterventi"></div>
		</div>
	</div>

	
	
    <%--DIV che compare quando viene premuto il punsante Elabora 
    	vengono mostrate delle informazioni preliminari da configurare prima di eseguire 
    	l'elaborazione del dizionario --%>
	<div dojoType="dijit.Dialog" id="dialogDivPreElaborazione" title="<fmt:message key="stp.label.configurazioni_preliminari" />: " style="width: 90%" >
		<div dojoType="dijit.layout.ContentPane" class="generic_dialog">
			<div id="dialogPreElabora" style="overflow: auto; width: 100%;">&nbsp;</div>
		</div>
	</div>
	



<jsp:include page="../cart/funzioni_cart.jsp" >
	<jsp:param name="funzioneRichiesta" value="javascriptBlock" />
</jsp:include>
<script type="text/javascript">

var dialogWorking = null; 
jQuery(document).ready(function(){
	dialogWorking = new dijit.Dialog({
       title: "Operazione in corso..." ,
       style: "overflow:auto; width: 250px;height: 70px;",
       content: "<img src='../images/spinner.gif'/>"
    });				
}); 
var richiestaDizionario = function(){
	dialogWorking.show();
	var jqxhr = jQuery.ajax({
		  url: "ajaxRichiestaDizionario.htm",
		  context: document.body,
		  cache: false,				
		  dataType: "html",
		  success: function(data) {
			  dialogWorking.hide();
			  dijit.showTooltip(data, dojo.byId('richiestaDizionarioId'));
			  setTimeout(function(){dijit.hideTooltip(dojo.byId('richiestaDizionarioId'))},1500);
			} 
		});
	};
var ricaricaConfigurazioni = function(){
	dialogWorking.show();
	var jqxhr = jQuery.ajax({
		  url: "ajaxRicaricaConfigurazioni.htm",
		  context: document.body,
		  cache: false,				
		  dataType: "html",
		  success: function(data) {
			  dialogWorking.hide();
			  dijit.showTooltip(data, dojo.byId('ricaricaConfigurazioniId'));
			  setTimeout(function(){dijit.hideTooltip(dojo.byId('ricaricaConfigurazioniId'))},1500);
			} 
		});
	};
	
	var richiestaSchedeDizionario = function(tipo){
		
		doHref('richiestaSchedeDizionario.htm?tipo='+tipo,'<fmt:message key="javascript.confirm.invio_tutte_schede_dizionario" />');
	};


	function tabAggiungiInterventi(divId){
		
		aggiungiInterventi(divId);		
	}


	function aggiungiInterventi(divId) {
		dialogWorking.show();
		new Ajax.Request(
				'${pageContext.request.contextPath}/cart/ajaxLoadStpTipologiaEndo2.htm',
				{
					method : 'post',
					onSuccess : function(transport) {
						dialogWorking.hide();
						dijit.byId(divId).show();
						var response = transport.responseText;						
						$("dialogAggiungiInterventi").innerHTML = response;					
					},
					onFailure : function(transport) {
						dialogWorking.hide();
						var response = transport.responseText;
						alert(response);
					}
				});
	}
	


    <%-- Spostato sulla jsp funzioni_cart.jsp  --%>
    <%--
	function tabPreElabora(divId){
		
		dijit.byId(divId).show();
		preElabora();		
	}
	
	function preElabora() {
			new Ajax.Request(
					'${pageContext.request.contextPath}/cart/ajaxPreElabora.htm',
					{
						method : 'post',
						onSuccess : function(transport) {							
							var response = transport.responseText;					
							$("dialogPreElabora").innerHTML = parseAjaxResponse(response,false,true);
							parseAjaxResponse(response,true,false);					
						},
						onFailure : function(transport) {
							var response = transport.responseText;
							alert(response);
						}
					});
		}


	--%>
	
	
</script>

<%
	String script=(String)request.getAttribute("script");
    pageContext.setAttribute("scrp", script);
%>

${scrp}    
	
	
	
	

<br class="clear"/>
<div id="functions">
	<li><a href="javascript:doHref('view.htm?ts_'+new Date().getTime(),'')"><fmt:message key="stp.label.controllotuttimessaggi" /></a></li>
	<%-- <li><a href="javascript:tabPreElabora('dialogDivPreElaborazione')"><fmt:message key="stp.label.elabora" /></a></li>--%>
	<li><a href="javascript:tabAggiungiInterventi('dialogDiv')"><fmt:message key="stp.label.aggiungi_interventi" /></a></li>
	<li><a href="javascript:historySet('${_urlback}','..%2Fstptipologieendo2/list.htm','')"><fmt:message key="stp.label.azioni_tipologie_endo" /></a></li>
	<li><a href="javascript:historySet('${_urlback}','..%2Fnaturaendo/list.htm','')"><fmt:message key="button.naturaendo" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</div>

</body>
</html>