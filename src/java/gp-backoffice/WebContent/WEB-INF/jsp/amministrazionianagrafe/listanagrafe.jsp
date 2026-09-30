<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
		<fmt:message key="label.amministrazione_anagrafe.title" />		
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.amministrazione_anagrafe.title" />	
	</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
        <jsp:param name="commandName" value="amministrazionianagrafe" />
    </jsp:include>
    
 	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.amministrazione" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${amministrazione.descrizioneEstesa}</div>
		</div>
	</div>
	<br class="clear"/>
	
	
    <table width="100%">  
    	<tr><td>&nbsp;</td></tr> 
        <tr>
			<td>
			    <script type='text/javascript'>
					function setHiddenFieldAnagrafe(inputField,listItem){
						var a = listItem.id;
						nuovaAnagrafe(a);
					}				
				</script>  
				
				<%-- 
				
				<input id="responsabile_id" name="responsabile" class="searchbox" size="40" onkeydown="return searchAll(this,event)" />
				<init:autocompleter methodAjax="findResponsabili.htm" afterUpdateElement="setHiddenFieldResponsabile"  idHidden="responsabile_id_hidden" idInput="responsabile_id" inputTitleKey="" minChars="1" />
				<input type="hidden" id="responsabile_id_hidden" name="responsabile_id_hidden" />
				--%>
				<fmt:message key="label.utente_scrivania" />	
				<jsp:include page="../includes/autocompletergenerico.jsp">
					<jsp:param name="idElemento" value="anagrafe_id" />
					<jsp:param name="propertyPath" value="amministrazionianagrafe.anagrafe" />
					<jsp:param name="pathPropertyDescription" value="amministrazionianagrafe.anagrafe.descrizioneRichiedente" />
					<jsp:param name="pathPropertyCode" value="amministrazionianagrafe.anagrafe.id.codice" />
					<jsp:param name="autocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=F" />
					<jsp:param name="afterUpdateElement" value="setHiddenFieldAnagrafe"/>
					<jsp:param name="titleKey" value="label.ricerca_anagrafe" />
				</jsp:include>
			    <fmt:message key="label.selezione_anagrafe_help" />
				
		    	<%-- <a class="generaallegato vbg-btn btn-aggiungi" title="aggiungi" href="#" onclick="nuovoResponsabile()"> --%>
	    	</td>
	    </tr>
	    <tr>
        	<td><div id="messaggioErrore" class="error_header" style="display:none;"></div></td>
        </tr>
		<tr>
			<td>
			
			<div class="jmesa">
				<table class="table">
					<thead>
						<tr class="header">	
							<td><fmt:message key="label.utente_scrivania" /></td>
							<td>
								<fmt:message key="label.puo_eseguire_movimenti" />
								<init:help idHelp="help_puo_eseguire_movimenti" textKey="label.puo_eseguire_movimenti.help"/>
							</td>
							<td><fmt:message key="label.elimina" /></td>
						</tr>
					</thead>
					<tbody id="dettaglioGruppi">
			
			
			
					</tbody>
			</table>
				
		</div>
			
			</td>
	    </tr>
		
    </table>

</div>

<div id="functions">
	<ul>
	    <li><a href="javascript:doHref('../amministrazioni/view.htm?codice=${amministrazione.id.codice}','')"><fmt:message key="button.back" /></a></li>
    </ul>
</div>

<script type="text/javascript">

var visualizzaDettaglioInfo = function(codiceAnagrafe){
	
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/amministrazionianagrafe/ajaxDettaglioAnagrafe.htm?codiceamministrazione=${amministrazione.id.codice}&codiceAnagrafeInserito='+codiceAnagrafe,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  if(data){
					  jQuery('#dettaglioGruppi').html(data);
					  jQuery('#dettaglioGruppi').show();
				  }					  
			  }
		});

}



function nuovaAnagrafe(codiceAnagrafe){
	// elimino l'eventuale messaggio di errore se presente
	jQuery('#messaggioErrore').hide();
	//var codiceResponsabile = jQuery('#responsabile_id_hidden').val();
	if(codiceAnagrafe!=''){
		var jhqrPr = jQuery.ajax({
			  url: "${pageContext.request.contextPath}/amministrazionianagrafe/ajaxAssegnaAnagrafe.htm?codiceAmministrazione=${amministrazione.id.codice}&codiceAnagrafe="+codiceAnagrafe,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
					  verificaErroreoSuccesso(data,codiceAnagrafe);
					  azzeraAutoCompleter();
			  }
			});
		}
	
	}
	function azzeraAutoCompleter(){
		jQuery('#anagrafe_id_id').val('');
		jQuery('#anagrafe_id_hidden').val('');
	}
function eliminaRiga(idRiga){		
	if(confirm('<fmt:message key="javascript.confirm.delete" />')){
		// elimino l'eventuale messaggio di errore se presente
		jQuery('#messaggioErrore').hide();
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/amministrazionianagrafe/ajaxEliminaAnagrafe.htm?idRiga='+idRiga,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
					  verificaErroreoSuccesso(data,'');
			  }
			});
		
	}
}	

function cancellaErrore(){
	jQuery('#messaggioErrore').hide();
	jQuery('#messaggioErrore').html('');
}

function verificaErroreoSuccesso(data,codiceAnagrafe){
	 // verifica errori o altro e aggiorna
	 if(data=='OK'){
	 	visualizzaDettaglioInfo(codiceAnagrafe);
	 }else{
		 jQuery('#messaggioErrore').html(data);
		 jQuery('#messaggioErrore').show();
	 }
}


jQuery(document).ready(function(){
	visualizzaDettaglioInfo(''); 
});


</script>

</body>
</html>
