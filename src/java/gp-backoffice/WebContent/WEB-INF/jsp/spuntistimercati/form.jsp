<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.spuntisti_mercati.title" />		
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.spuntisti_mercati.title" />	
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
        <jsp:param name="commandName" value="spuntistimercati" />
    </jsp:include>
    <spring-form:form commandName="spuntistimercati" name="inviodati">
	    
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.autorizzazione" />:</span>
			<span class="header_dato_valore">
			${spuntistimercati.autorizzazioni.autoriznumero} - <fmt:formatDate value="${spuntistimercati.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
			- ${spuntistimercati.autorizzazioni.autorizcomune.descrizioneEstesa} - ${spuntistimercati.autorizzazioni.tipologiaregistro.trDescrizione}
			</span>
		</div>
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.intestatario" />:</span>
			<span class="header_dato_valore">
			${spuntistimercati.autorizzazioni.anagrafe.descrizioneRichiedente}
			</span>
		</div>	
	    <br />
	    <table width="100%">
	       
	        <tr>
				<td>
					<fmt:message key="label.mercato" />
				    <script type='text/javascript'>
						function setHiddenFieldMercato(inputField,listItem){
							var a = listItem.id;
							document.getElementById('mercati_hidden').value = a;
							document.getElementById("mercatiUso_hidden").value='';
							document.getElementById("mercatiUso_id").value='';	
						}				
					</script>
					<jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="mercati" />
						<jsp:param name="propertyPath" value="mercati" />
						<jsp:param name="pathPropertyDescription" value="mercati.descrizione" />
						<jsp:param name="pathPropertyCode" value="mercati.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMercati.htm" />
						<jsp:param name="afterUpdateElement" value="setHiddenFieldMercato" />	
						<jsp:param name="titleKey" value="label.ricerca_mercati" />
					</jsp:include>
					
					
					<script type="text/javascript">
						function filter(element, entry) {
							return entry + "&codiceMercato=" + document.getElementById("mercati_hidden").value;								
						}
					</script>
					<fmt:message key="label.giorno" />
					<jsp:include page="../includes/autocompletergenerico.jsp">
						<jsp:param name="idElemento" value="mercatiUso" />
						<jsp:param name="propertyPath" value="mercatiUso" />
						<jsp:param name="pathPropertyDescription" value="mercatiUso.descrizione" />
						<jsp:param name="pathPropertyCode" value="mercatiUso.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
						<jsp:param name="ajaxCallBack" value="filter" />
						<jsp:param name="titleKey" value="label.ricerca_mercati_uso" />
					</jsp:include>  
		    	</td>
		    </tr>
		    <tr>
		    	<td><a class="generaallegato vbg-btn btn-aggiungi" title="aggiungi" href="javascript:nuovaSpunta(${idautorizzazione});"> </td>
		    
		    </tr>
		    <tr>
	        	<td><div id="messaggioErrore" class="error_header" style="display:none;"></div></td>
	        </tr>
			<tr>
				<td >
				
				<div id="listaMercati">&nbsp;<img src='${pageContext.request.contextPath}/images/spinner.gif' /></div>	
					
					<!-- img src="${pageContext.request.contextPath}/images/add.gif"/>  --></a>
					
					<p>&nbsp;</p>
				</div>
				
				</td>
		    </tr>
			
	    </table>
	</spring-form:form>
</div>

<div id="functions">
	<ul>
	    <%-- <li><a href="javascript:doHref('view.htm?codice=${mercato.id.codice}','')"><fmt:message key="button.back" /></a></li>--%>
	    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
    </ul>
</div>

<script type="text/javascript">

		 	var visualizzaDettaglioInfo = function(){
			
				var jhqrPr = jQuery.ajax({
					  url: '${pageContext.request.contextPath}/spuntistimercati/ajaxListmercatispunta.htm?idautorizzazione=${codice}',
					  context: document.body,
					  cache: false,					  
					  dataType: "html",
					  success: function(data, textStatus, jqXHR){
						  if(data){
							  jQuery('#listaMercati').html(data);
							  jQuery('#listaMercati').show();
						  }					  
					  }
				});
		
		  		}
 
			 function nuovaSpunta(idAut){
				 
				 	var codiceMercato = document.getElementById('mercati_hidden').value;
				 	var codiceMercatouso = document.getElementById('mercatiUso_hidden').value;
					
					// elimino l'eventuale messaggio di errore se presente
					jQuery('#messaggioErrore').hide();
					//var codiceResponsabile = jQuery('#responsabile_id_hidden').val();
					if(idAut!=''){
						var jhqrPr = jQuery.ajax({
							  url: "${pageContext.request.contextPath}/spuntistimercati/ajaxAddSpuntaMercato.htm?idAutorizzazione=${codice}&codiceMercato="+codiceMercato+"&codiceUso="+codiceMercatouso,
							  context: document.body,
							  cache: false,					  
							  dataType: "html",
							  success: function(data, textStatus, jqXHR){
									  verificaErroreoSuccesso(data);
									  azzeraAutoCompleter();
							  }
							});
						}
			
			}
 
			function verificaErroreoSuccesso(data){
				 // verifica errori o altro e aggiorna
				 if(data=='OK'){
				 	visualizzaDettaglioInfo();
				 }else{
					 jQuery('#messaggioErrore').html(data);
					 jQuery('#messaggioErrore').show();
				 }
			}
			
			function azzeraAutoCompleter(){
				jQuery('#mercati_id').val('');
				jQuery('#mercati_hidden').val('');
				//.
				jQuery('#mercatiUso_id').val('');
				jQuery('#mercatiUso_hidden').val('');
			}
			
			function eliminaRiga(idRiga){		
				if(confirm('<fmt:message key="javascript.confirm.delete" />')){
					// elimino l'eventuale messaggio di errore se presente
					jQuery('#messaggioErrore').hide();
					var jhqrPr = jQuery.ajax({
						  url: '${pageContext.request.contextPath}/spuntistimercati/ajaxEliminaSpuntista.htm?idRiga='+idRiga,
						  context: document.body,
						  cache: false,					  
						  dataType: "html",
						  success: function(data, textStatus, jqXHR){
								verificaErroreoSuccesso(data);
						  }
						});
					
				}
			}	

			jQuery(document).ready(function(){
				visualizzaDettaglioInfo(); 
			});  
	
/*

function nuovoResponsabile(codiceResponsabile){
	// elimino l'eventuale messaggio di errore se presente
	jQuery('#messaggioErrore').hide();
	//var codiceResponsabile = jQuery('#responsabile_id_hidden').val();
	if(codiceResponsabile!=''){
		var jhqrPr = jQuery.ajax({
			  url: "${pageContext.request.contextPath}/spuntistimercati/ajaxListmercatispunta.htm?idAutorizzazione=${mercato.id.codice}&codiceResponsabile="+codiceResponsabile,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
					  verificaErroreoSuccesso(data,codiceResponsabile);
					  azzeraAutoCompleter();
			  }
			});
		}
	
	}
	function azzeraAutoCompleter(){
		jQuery('#responsabile_id').val('');
		jQuery('#responsabile_id_hidden').val('');
	}
function eliminaRiga(idRiga){		
	if(confirm('<fmt:message key="javascript.confirm.delete" />')){
		// elimino l'eventuale messaggio di errore se presente
		jQuery('#messaggioErrore').hide();
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/mercati/ajaxEliminaResponsabile.htm?idRiga='+idRiga,
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

function verificaErroreoSuccesso(data,codiceResponsabile){
	 // verifica errori o altro e aggiorna
	 if(data=='OK'){
	 	visualizzaDettaglioInfo(codiceResponsabile);
	 }else{
		 jQuery('#messaggioErrore').html(data);
		 jQuery('#messaggioErrore').show();
	 }
}

 */	
	
 
</script>





</body>
</html>