<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${ruolo.id.codice==null}">
			<fmt:message key="ruoli.label.nuovo_ruolo.title" />
		</c:if> 
		<c:if test="${ruolo.id.codice!=null}">
			<fmt:message key="ruoli.label.dettaglio_ruolo.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${ruolo.id.codice==null}">
	<fmt:message key="ruoli.label.nuovo_ruolo.title" />
</c:if> 
<c:if test="${ruolo.id.codice!=null}"> 
	<fmt:message key="ruoli.label.dettaglio_ruolo.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<%
String displayflagGestmovimenti="display:none";
String displayflagDisgestmovamm="display:none";
%>




<div id="subcontent">
	<spring-form:form commandName="ruolo" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="ruolo" />
    </jsp:include>

	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="ruolo" size="100" />
			<spring-form:errors path="ruolo" cssClass="error"/></td> 
		</tr>
		<tr>
			<td><fmt:message key="ruoli.label.readonly" /></td>
			<td><spring-form:checkbox id="readonly_id" path="readonly" onclick="abilitaGestioneMov();"/>
			<init:help idHelp="help1" textKey="ruoli.help.readonly"/>
			<spring-form:errors path="readonly" cssClass="error"/></td>
		</tr>
		<tr id="flagGestmovimenti_id_tr_title" style="<%=displayflagGestmovimenti%>"  class="titoloSezione">
			<td  colspan="2"><fmt:message key="ruoli.label.descrizione_sezione"/></td>
		</tr>
		<tr id="flagGestmovimenti_id_tr" style="<%=displayflagGestmovimenti%>">
			<td><fmt:message key="ruoli.label.flagGestmovimenti" /></td>
			<td><spring-form:checkbox id="flagGestmovimenti_id" path="flagGestmovimenti" onclick="abilitaFlagDisgestmovamm();"/>
			<init:help idHelp="help2" textKey="ruoli.help.flagGestmovimenti"/>
			<spring-form:errors path="flagGestmovimenti" cssClass="error"/></td>
		</tr>
		<tr id="flagDisgestmovamm_id_tr" style="<%=displayflagDisgestmovamm%>">
			<td><fmt:message key="ruoli.label.flagDisgestmovamm" /></td>
			<td><spring-form:checkbox id="flagDisgestmovamm_id" path="flagDisgestmovamm" />
			<init:help idHelp="help3" textKey="ruoli.help.flagDisgestmovamm"/>
			<spring-form:errors path="flagDisgestmovamm" cssClass="error"/></td>
		</tr>
		<c:if test="${isDocErAttivo}">
		<%--
			<tr>
				<td><fmt:message key="label.cod_ruolo_docer" /></td>
				<td><spring-form:input id="codDocer_id" path="codDocer" size="60" />
				<spring-form:errors path="codDocer" cssClass="error"/>
				<span id="docerStatus_id">
					
				</span>
				</td> 
			</tr>
			 --%>
		</c:if>
	</table>
	<script type='text/javascript'>
		$('descrizione_id').focus();
		 abilitaGestioneMov();
		 abilitaFlagDisgestmovamm();
		function abilitaGestioneMov(){
			if($('readonly_id').checked){
				$('flagGestmovimenti_id_tr_title').appear();
				$('flagGestmovimenti_id_tr').appear();
			}else{
				$('flagGestmovimenti_id_tr').fade();
				$('flagGestmovimenti_id_tr_title').fade();
				$('flagDisgestmovamm_id_tr').fade();
				$('flagGestmovimenti_id').checked=false;
				$('flagDisgestmovamm_id').checked=false;
			}
		}
		function abilitaFlagDisgestmovamm(){
			if($('flagGestmovimenti_id').checked){
				$('flagDisgestmovamm_id_tr').appear();
			}else{
				$('flagDisgestmovamm_id_tr').fade();
				$('flagDisgestmovamm_id').checked=false;
			}
		}
		
		
		function mostraOK(){
			jQuery('#docerStatus_id').html('<img title="L\'Informazione esiste in DOCER" src=\"${pageContext.request.contextPath}/images/success.png\" />');
		}
		
		function mostraKO(){
			jQuery('#docerStatus_id').html(htmlKO);
		}
		function mostraERRORE(errore){
			jQuery('#docerStatus_id').html(htmlKO+'&nbsp;<span class="error_header">'+errore+'</span>');
		}

		var htmlVerifica = "Verifica esistenza gruppo in  docer in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
		var htmlCreazione = "Creazione gruppo in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
		var htmlKO = '<a href="javascript:void(0)" onclick="creaGruppo()"><img src=\"${pageContext.request.contextPath}/images/warning.gif\" /></a>';
		
		function verificaGruppo(){
			
			var codiceGruppo = jQuery('#codDocer_id').val();
			if( codiceGruppo !=''){
				jQuery('#docerStatus_id').html( htmlVerifica);
			var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/controllaEsistenzaGruppo.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceGruppo="+jQuery('#codDocer_id').val(),
				  dataType: "text",
				  success: function(data) {
					  if(data){
							if(data=='true'){
								mostraOK();
							}else if (data=='false'){
								mostraKO();
							}else{
								mostraERRORE(data);
							}
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
				}
			});		
			}
		}
		
		
		function creaGruppo(){
			var codiceGruppo = jQuery('#codDocer_id').val();
			if( codiceGruppo !=''){
				if(confirm('Attenzione il gruppo non sembra essere presente in DOCER. Procedendo verra\' creato il gruppo.')){
				jQuery('#docerStatus_id').html( htmlCreazione );
				var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/creaGruppo.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceGruppo="+jQuery('#codDocer_id').val()+"&descrizioneGruppo="+jQuery('#descrizione_id').val(),
				  dataType: "text",
				  success: function(data) {
					  if(data){
						  if(data=='true'){
								mostraOK();
							}else if (data=='false'){
								mostraKO();
							}else{
								mostraERRORE(data);
							}
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
				}
			});
			}
		}
		}
		
		<c:if test="${isDocErAttivo}">
			jQuery(document).ready(function(){
				
				// verificaGruppo();
				
			});
		</c:if>

	</script>	
</spring-form:form>

<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
	<ul>	
	<c:forEach items="${ listaRuoli }" var="ruoloName">
	<li>
		${ruoloName.codice } - ${ruoloName.descrizione }  
	</li>
	</c:forEach>
	</ul>
</spring-security:authorize> 

</div>
<div id="functions">
<ul>
	<c:if test="${ruolo.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${ruolo.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		<c:if test="${isDocErAttivo}">
			<li><a href="javascript:doHref('configurazioniProtocollo.htm?codice=${ruolo.id.codice}','')"><fmt:message key="button.configurazioni_protocollo" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('createRuoliResponsabili.htm?codice=${ruolo.id.codice}','')"><fmt:message key="ruoli.button.ruoli_responsabili" /></a></li>
		<li><a href="javascript:doHref('createRuoliAmministrazioni.htm?codice=${ruolo.id.codice}','')"><fmt:message key="ruoli.button.ruoli_amministrazioni" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
