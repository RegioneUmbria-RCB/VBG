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

<spring-form:form commandName="cartInfoDizionarioHelper" name="innerForm" id="innerForm_ID">
	

    <fieldset style="border-color: black;">
	   <div>
	  		
	   </div>
    </fieldset>
	<div>
		<table cellpadding="2" cellspacing="2" width="100%">
			<thead class="header">
				<tr>
					<td style="background-color: #DEDDCC"><b><fmt:message key="label.codice_amministrazione_cart" /></b></td>
				    <td style="background-color: #DEDDCC"><b><fmt:message key="label.amministrazione" /></b></td>
				    <td style="background-color: #DEDDCC"><b><fmt:message key="label.movimento_natura_scia" /></b></td>
				    <td style="background-color: #DEDDCC"><b><fmt:message key="label.movimento_natura_ordinario" /></b></td>
				    <td style="background-color: #DEDDCC"><b><fmt:message key="label.movimento_natura_comunicazione" /></b></td>
				</tr>
			</thead>
			<tbody class="tbody">
			<%int i=1;%>
			<c:forEach items="${cartInfoDizionarioHelper.endoTipo1Helpers}" var="endo_tipo1_var" varStatus="a">
				<tr valign="top" >
					<td style="border-bottom: thin solid;">${endo_tipo1_var.codiceAmministrazioneCart}</td>
					<td style="border-bottom: thin solid;">
					
						<c:if test="${endo_tipo1_var.amministrazioni.id.codice!=null }">
							<b>${endo_tipo1_var.amministrazioni.amministrazione}</b>
						</c:if>
						<c:if test="${endo_tipo1_var.amministrazioni.id.codice ==null }">
							<b style="color: red;"><fmt:message key="label.non_configurato" /></b>
						</c:if>	
						<br /><br />			  
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni${a.index}"/>		
							<jsp:param name="propertyPath" value="amministrazioniTransient" />				
							<jsp:param name="pathPropertyDescription" value="amministrazioniTransient.amministrazione" />
							<jsp:param name="pathPropertyCode" value="codiciAmministrazioni" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />
							<jsp:param name="titleKey" value="label.ricerca_amministrazioni" />
							<jsp:param name="id_help" value="help_amministrazione" />
							<jsp:param name="autocompleterInputSize" value="40" />
						</jsp:include>
						<input type="hidden" name=codiciAmministrazioniCart value="${endo_tipo1_var.codiceAmministrazioneCart}">
					</td>
					<td style="border-bottom: thin solid;">
						
						<c:if test="${not empty endo_tipo1_var.tipimovimento.id.tipomovimento}">
							
							<b id="movimento_<%=i %>_scia"
								data-amministrazione="${endo_tipo1_var.codiceAmministrazioneCart}" 
								data-tipomovimento="${endo_tipo1_var.tipimovimento.id.tipomovimento}"
								data-tipo="scia">${endo_tipo1_var.tipimovimento.descrizioneEstesa}
								<a class="eliminaRiga" 
									href="javascript:eliminaMovimento('movimento_<%=i %>_scia')" 
									title="<fmt:message key="button.delete" /> ${endo_tipo1_var.tipimovimento.descrizioneEstesa}">
								<label><fmt:message key="label.edit.record.image" /></label></a>							
							</b>

						</c:if>
						<c:if test="${endo_tipo1_var.tipimovimento==null && endo_tipo1_var.tipimovimento.id.tipomovimento!=''}">
							<b style="color: red;"><fmt:message key="label.non_configurato" /></b>
						</c:if>
						<br /><br />
								
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="tipimovimento${a.index}"/>		
								<jsp:param name="propertyPath" value="tipimovimentoTransient" />				
								<jsp:param name="pathPropertyDescription" value="tipimovimentoTransient.descrizioneEstesa" />
								<jsp:param name="pathPropertyCode" value="codiciTipimovimento" />
								<jsp:param name="autocompleterAjax" value="findTipiMovimentoForSoftware.htm?codice=" />
								<jsp:param name="titleKey" value="label.ricerca_tipi_movimento" />
								<jsp:param name="id_help" value="help_tipi_mov" />
								<jsp:param name="autocompleterInputSize" value="30" />
						</jsp:include>
					</td>	
					
					<td style="border-bottom: thin solid;">
						
						<c:if test="${not empty endo_tipo1_var.tipimovimentoOrdinario.id.tipomovimento}">
							<b id="movimento_<%=i %>_ordinario"
								data-amministrazione="${endo_tipo1_var.codiceAmministrazioneCart}" 
								data-tipomovimento="${endo_tipo1_var.tipimovimentoOrdinario.id.tipomovimento}"
								data-tipo="ordinario">${endo_tipo1_var.tipimovimentoOrdinario.descrizioneEstesa}
								<a class="eliminaRiga" 
									href="javascript:eliminaMovimento('movimento_<%=i %>_ordinario')" 
									title="<fmt:message key="button.delete" /> ${endo_tipo1_var.tipimovimentoOrdinario.descrizioneEstesa}">
								<label><fmt:message key="label.edit.record.image" /></label></a>
							</b>
						</c:if>
						<c:if test="${endo_tipo1_var.tipimovimentoOrdinario==null && endo_tipo1_var.tipimovimentoOrdinario.id.tipomovimento!=''}">
							<b style="color: red;"><fmt:message key="label.non_configurato" /></b>
						</c:if>
						<br /><br />
								
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="tipimovimentoOrdinario${a.index}"/>		
								<jsp:param name="propertyPath" value="tipimovimentoOrdinarioTransient" />				
								<jsp:param name="pathPropertyDescription" value="tipimovimentoOrdinarioTransient.descrizioneEstesa" />
								<jsp:param name="pathPropertyCode" value="codiciTipimovimentoOrdinario" />
								<jsp:param name="autocompleterAjax" value="findTipiMovimentoForSoftware.htm?codice=" />
								<jsp:param name="titleKey" value="label.ricerca_tipi_movimento" />
								<jsp:param name="id_help" value="help_tipi_mov2" />
								<jsp:param name="autocompleterInputSize" value="30" />
						</jsp:include>
					</td>	
					
					
					<td style="border-bottom: thin solid;">
						
						<c:if test="${not empty endo_tipo1_var.tipimovimentoComunicazione.id.tipomovimento}">
							<b id="movimento_<%=i %>_comunicazione"
								data-amministrazione="${endo_tipo1_var.codiceAmministrazioneCart}" 
								data-tipomovimento="${endo_tipo1_var.tipimovimentoComunicazione.id.tipomovimento}"
								data-tipo="comunicazione">${endo_tipo1_var.tipimovimentoComunicazione.descrizioneEstesa}
								<a class="eliminaRiga" 
									href="javascript:eliminaMovimento('movimento_<%=i %>_comunicazione')" 
									title="<fmt:message key="button.delete" /> ${endo_tipo1_var.tipimovimentoComunicazione.descrizioneEstesa}">
								<label><fmt:message key="label.edit.record.image" /></label></a>
							</b>
						</c:if>
						<c:if test="${endo_tipo1_var.tipimovimentoComunicazione==null && endo_tipo1_var.tipimovimentoComunicazione.id.tipomovimento!=''}">
							<b style="color: red;"><fmt:message key="label.non_configurato" /></b>
						</c:if>
						<br /><br />
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="tipimovimentoComunicazione${a.index}"/>		
								<jsp:param name="propertyPath" value="tipimovimentoComunicazioneTransient" />				
								<jsp:param name="pathPropertyDescription" value="tipimovimentoComunicazioneTransient.descrizioneEstesa" />
								<jsp:param name="pathPropertyCode" value="codiciTipimovimentoComunicazione" />
								<jsp:param name="autocompleterAjax" value="findTipiMovimentoForSoftware.htm?codice=" />
								<jsp:param name="titleKey" value="label.ricerca_tipi_movimento" />
								<jsp:param name="id_help" value="help_tipi_mov3" />
								<jsp:param name="autocompleterInputSize" value="30" />
						</jsp:include>
					</td>
					
				</tr>
				
			<%i++; %>
			
	   		</c:forEach>
	   		
	   		</tbody>
	   
	</table>
	
	
	</div>
	<label for="escludiDisabilitati_id">Selezionare se si intende escludere l'importazione degli endo disabilitati</label>
	<spring-form:checkbox id="escludiDisabilitati_id" path="escludiDisabilitati" />
	
</spring-form:form>

<script type="text/javascript">


function forzaAggiornaDizionario( formObj , formId){	
		
		doSubmit('${pageContext.request.contextPath}/pannelloconsole/proseguiElaborazioneSenzaValidare.htm','Proseguire senza validare i dati immessi?', formObj);
/*
jQuery.ajax({
	 		url: '${pageContext.request.contextPath}/pannelloconsole/ajaxProseguiElaborazioneSenzaValidare.htm', 
	 		dataType: 'html',
	 		type: 'POST',
	 		data: jQuery( formObj ).serialize(),
	 		cache: false,	
	 		timeout: 600000,
	 		success: function (data, textStatus, jqXHR) {
	 			stopMonitor();		  	
	 		},
	 		error:function (jqXHR, textStatus, errorThrown) {
	
	 			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
	 		}
	 	});				
		opendialog();
		startMonitor();	
	}
	*/
}

function eliminaMovimento( elId ){	
	
		var tipomovimento=jQuery('#'+elId).data('tipomovimento');
		var tipo=jQuery('#'+elId).data('tipo');
		var amministrazione=jQuery('#'+elId).data('amministrazione');
		disableFunctions();
jQuery.ajax({
	 		url: '${pageContext.request.contextPath}/pannelloconsole/ajaxEliminaMovimento.htm', 
	 		dataType: 'html',
	 		type: 'POST',
	 		data: 'tipo='+tipo+'&amministrazione='+amministrazione,
	 		cache: false,	
	 		timeout: 120000,
	 		success: function (data, textStatus, jqXHR) {
	 			jQuery('#'+elId).html('');
	 			enableFunctions();
	 		},
	 		error:function (jqXHR, textStatus, errorThrown) {
	 			enableFunctions();
	 			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
	 		}
	 	});				

	
	
}
	
	
function aggiornaDizionario(formObj , formId){
	
	doSubmit('${pageContext.request.contextPath}/pannelloconsole/proseguiElaborazione.htm','', formObj);
	/*
	jQuery.ajax({
 		url: '${pageContext.request.contextPath}/pannelloconsole/ajaxProseguiElaborazione.htm', 
 		dataType: 'html',
 		type: 'POST',
 		data: jQuery( formObj ).serialize(),
 		cache: false,	
 		timeout: 600000,
 		success: function (data, textStatus, jqXHR) {
 			if(data!='OK'){
 				jQuery('#output').prepend(
						"<p><strong>"+ data + "</strong></p>");
 			}
 			stopMonitor();	  	
 		},
 		error:function (jqXHR, textStatus, errorThrown) {

 			jQuery('#genericDialogContainer').html("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
 		}
 	});		
	
	opendialog();
	startMonitor();	
	*/
}

function opendialog(){
	
	jQuery('#genericDialogContainer').dialog({ 
		 	autoOpen: false, 
		 	modal : true,
		 	width: 500,
			height: 300,
			resizable: true,
			title: ''
	 });
	 jQuery('#genericDialogContainer').dialog("open");
	 jQuery('#output').html('');
}



var ferma = false;

function startMonitor() {
	
	if (window.ferma == false) {
		
		var date = new Date();
		jQuery.ajax({
			url : "${pageContext.request.contextPath}/pannelloconsole/ajaxStatus.htm",
			success : function(result) {
				jQuery('#output').prepend(
						"<p>" + date.formatTime() + "<br /><strong>"+ result + "</strong></p>");
				setTimeout("startMonitor()",
						2000);
			}
		});

	}
}
function stopMonitor() {
	window.ferma = true;
	var date = new Date();
	jQuery('#output').prepend(
			'<p>'+ date.formatTime() + '<br /><strong>operazione terminata</strong></p>');
}


function chiudi(){
	
	doHref("../pannelloconsole/view.htm",'');	
}
</script>
<div id="genericDialogContainer" style="display:hidden">
	<div id="output"></div>
</div>
	<br class="clear" />	
<div id="functions">
<ul>
	<%-- 
	<li><a href="javascript:doSubmit('importaInterventi.htm','',document.innerForm)"><fmt:message key="button.importa" /></a></li>
	--%>
	<li><a href="javascript:aggiornaDizionario(document.innerForm,'innerForm_ID');">verifica</a></li>
	<li><a href="javascript:forzaAggiornaDizionario(document.innerForm,'innerForm_ID');">aggiorna</a></li>
	<li><a href="javascript:chiudi()"><fmt:message key="button.annulla" /></a></li>
	
</ul>
</div>
</div>

</body>
</html>