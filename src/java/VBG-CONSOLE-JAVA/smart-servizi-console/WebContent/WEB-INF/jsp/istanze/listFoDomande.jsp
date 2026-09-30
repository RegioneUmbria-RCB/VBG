<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.service.FoStatiDomandaService.StatoDomandaFacctEnum"%>
<%@page import="it.gruppoinit.pal.gp.areariservata.web.filter.ServiziResolverFilter.ServiziEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomande"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.istanze-presentate' /></title>
</head>
<body>
<style>

	.panelNotifiche{
	
		width: 80%;
		border: dotted;
		border-color: red;
		padding: 10px;
		margin: 5px;
		display: none;
	}
</style>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp">
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>
	<div class="titolo"><fmt:message key='label.istanze-presentate' /></div>
	<div class="descrizione"></div>
	
	
	<fieldset style="padding: 2px;">
	<div class="titolo">filtri selezione</div>
	<spring-form:form commandName="filtriRicercafoDomande" name="inviodati">
	<table>
		<tr>
			<td>Intestatario:</td>
			<td><spring-form:input id="titolarepratica_id" path="titolarePratica" size="15" tabindex="0" /></td>
			<td>Dalla data:</td>
			<td><spring-form:input id="dataInvioDa_id" path="dataInvioDa" size="10" tabindex="0" onblur="isValidDate(this,true);"/></td>
			<td>Alla data:</td>
			<td><spring-form:input id="dataInvioA_id" path="dataInvioA" size="10" tabindex="0" onblur="isValidDate(this,true);"/></td>
			<td><input type="button" class="bottone-cart" onclick="cerca();" value="<fmt:message key='button.cerca' />" /></td>
		</tr>	
	</table>
	<div style="padding-top: 20px;">
	<%
			
			boolean mostraBottoniCross = true;
			Cookie[] cs = request.getCookies();
			String cookieName = "bottonicross_" + ORMHelper.getIdcomuneAlias();
			if(cs != null){
				for(int i=0;i<cs.length;i++){
					Cookie c = cs[i];
					// System.out.print(c.getName());
					if(c.getName().equalsIgnoreCase(cookieName)){
					    mostraBottoniCross  = false;
					    break;
					}
				}
			}
			if(mostraBottoniCross){
			%>
				<input type="button" class="bottone-cart" onclick="vaiAIncompilazione();" value="<fmt:message key="label.istanze-in-sospeso" />" />
			<%} %>
	
			<%@ include file="../includes/chiudiPaginaIniziale.jsp" %>
	</div>
	</spring-form:form>
	</fieldset>
	
	<br />
	<div id="richieste_id" class="panelNotifiche" >
			<span id="richieste_id_inner"></span>
			<input type="button" class="bottone-cart" onclick="vaiARichieste();" value="<fmt:message key="label.visualizza" />" />
	</div>
	
	<form name="istanzeForm" action="listFoDomande.htm">
		${htmlTable }		
	</form>
	<br />
	
	<div id="myDialog"><div id="myDialogText"></div></div>
	
	<script type="text/javascript">
	
	
	function vaiARichieste(){
		document.location.href="${pageContext.request.contextPath}/istanze/richieste.htm";
	}
	
		function callback() {
	      setTimeout(function() {
	        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
	      }, 1000 );
	    };
	 
	
		function showPanelNotifiche(numNotifiche){
			var text = 'E\' presente una richiesta non letta';
			if(numNotifiche>1){
				text = 'Sono presenti '+ numNotifiche + ' richieste non lette.';
				//Sono presenti <span id="nnotificheId"></span> richieste non lette.
			}
			$('#richieste_id_inner').text(text);
			var options = {};
			$('#richieste_id').show( 'blind', options, 500, callback );		
				
		}
	
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}	
		function cerca(){
			document.inviodati.submit();
		}
		function vaiAIncompilazione(){
			
			location.href="${pageContext.request.contextPath}/<%= ORMHelper.getIdente()%>/<%= ServiziEnum.INCOMPILAZIONE.toString().toLowerCase()%>";
		}
		
		// INCLUDE
		<jsp:include page="../includes/fodomande_js.jsp"></jsp:include>
		
		
			
		
			
			function inizializzadownload(){
				
				$( ".downloadlink" ).button({
	  			  label: "Scarica gli allegati"
		  		});    		
		  		$( ".downloadlink" ).click(function() {
		  			var url = '${pageContext.request.contextPath}/istanze/ajaxmostraallegati.htm?' + $(this).data("qsmac");
		  			
		  			var ajaxOpts = {
		  					 
		  					context: this,
		  					type: 'POST',
		  					success: function(data){
		  						$("#myDialogText").html(data);
		  						$("#myDialog").dialog({
		  							 resizable: false,
		  							 modal: true,
		  							 width:'auto',
		  							 title: 'Lista dei documenti'
		  							}
		  						);
		  					}
		  				};
		  			    			
		  			$.ajax(url,ajaxOpts);  
		  		});
			}	
				
			function inizializzadettaglio(){
				$( ".dettagliolink" ).button({
		  			  label: "Dettaglio"
	  			});
	    		$( ".dettagliolink" ).click(function() {
	    			
	    			var url = '${pageContext.request.contextPath}/istanze/dettagliopratica.htm?' + $(this).data("qsmac");    			
	   			    document.location.href = url;			
	    			  
	    		});
				
			}			
		

		
		$(function(){
			
			
			if(window.find){
				$('#finder').show();
				
			}
			
			inizializzadownload();

			inizializzadettaglio();
    	
			
			$( ".assegnazioneIntermediario" ).button({
	  			  label: "Modifica"
		  		});    		
		  	$( ".assegnazioneIntermediario" ).click(function() {
		  		cambiaintermediario($(this));
		  	});
						
    		
    		$( ".stato_istanza" ).each(function() {
    			var idDomanda = $( this ).data("identificativo");
    			var idComuneDomanda = $( this ).data("idcomunedomanda");
				if (idDomanda){
					verificaStato($(this), idDomanda, idComuneDomanda);
				}
	        });
		

    		
		$.ajax({
		    type: 'GET',
		    url: '../istanze/ajaxCountRichiesteNonLette.htm',
		    success: function(data) {		    	
		    	 if (data.items>0){
		    		showPanelNotifiche(data.items);
		    	 }
		    },
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		        // alert("Errore durante il recupero dei documenti dal server");
		    },
		    dataType: "json"
			});
		
		
    	var dialogintermediario,formIntermediario,
	      intermediarioNome = $( "#nome_id" ),
	      intermediarioCognome = $( "#cognome_id" ),
	      intermediarioCf = $( "#codicefiscale_id" ),
	      allFields = $( [] ).add( intermediarioNome ).add( intermediarioCognome ).add( intermediarioCf  ),
	      tips = $( ".validateTips" );
    	
    	
    	 formIntermediario = $( "#modificaIntermediario_id" );
		 dialogintermediario = $( "#modificaIntermediarioDivId" ).dialog({
	        autoOpen: false,
	        height: 350,
	        width: 600,
	        modal: true,
	        buttons: {
	          "Conferma": addUser,
	          "Annulla": function() {
	        	  dialogintermediario.dialog( "close" );
	          }
	        },
	        close: function() {	         
	          allFields.removeClass( "ui-state-error" );
	        }
	      });
	    

	    
		function cambiaintermediario(jqObj){
			
			var idDomanda = $( jqObj ).data("iddomanda");
			var idComuneDomanda = $( jqObj ).data("idcomunedomanda");
			var qsMac = $( jqObj ).data("qsmac");
			if (idDomanda && idComuneDomanda && qsMac){
				updateTips('');
				$('#qsmac_hidden_id').val(qsMac);
				$('#iddomanda_hidden_id').val(idDomanda);
				$('#idcomunedomanda_hidden_id').val(idComuneDomanda);
				
				dialogintermediario.dialog( "open" );
			}
			
		}
		
		
		function addUser() {
			
		      var valid = true;
		      allFields.removeClass( "ui-state-error" );		 
		      valid = valid && checkLength( intermediarioNome, "nome", 1, 200 );
		      valid = valid && checkLength( intermediarioCognome, "cognome", 1, 200 );
		      valid = valid && checkLength( intermediarioCf, "codice fiscale", 16, 50 );
		      if ( valid ) {		    	  
		    	  formIntermediario.submit();
		      }
		}
	    
		
	    function updateTips( t ) {
	    	
	    	if(t==''){
	    		tips.removeClass('ui-state-error');
	    	}else{
	    		tips.addClass( "ui-state-error" );
	    	}
	        tips
	          .text( t );	          
	      }
	    
	    function checkLength( o, n, min, max ) {
	        if ( o.val().length > max || o.val().length < min ) {
	          o.addClass( "ui-state-error" );
	          updateTips( "La lunghezza di " + n + " deve essere compresa tra " +
	            min + " e " + max + "." );
	          return false;
	        } else {
	          return true;
	        }
	      }
	
		});
		
 	
	</script>
	
	<div id="modificaIntermediarioDivId" style="display: none;">	
		<form name="modificaIntermediario" id="modificaIntermediario_id" method="post" action="${pageContext.request.contextPath}/istanze/modificaIntermediario.htm">
		
			<input type="hidden" id="qsmac_hidden_id" name="qsmac" value=""/>	
			<input type="hidden" id="iddomanda_hidden_id" name="id" value=""/>
			<input type="hidden" id="idcomunedomanda_hidden_id" name="idComuneDomanda" value=""/>
			<input type="hidden" id="qspage_hidden_id" name="qspage" value="<%= URLEncoder.encode((request.getQueryString()==null?"": request.getQueryString()))%>"/>
			Attenzione! Si stanno per modificare i diritti di accesso alla pratica. <br />
			Indicare i dati del nuovo intermediario.
			<div style="color: red">Tutti i campi sono obbligatori.
				<div class="validateTips ui-state-error"  ></div>
			</div>
			<table style="margin-top: 20px;">
			<tr>
				<td>
					<label for="nome_id"><fmt:message key="label.nome" /></label>				
				</td>
				<td>
					<input type="text" size="50" id="nome_id" name="nome" maxlength="200" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="cognome_id"><fmt:message key="label.cognome" /></label>				
				</td>
				<td>
					<input type="text" size="50" id="cognome_id" name="cognome" maxlength="200" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="codicefiscale_id"><fmt:message key="label.codicefiscale" /></label>				
				</td>
				<td>
					<input type="text" size="50" id="codicefiscale_id" name="codicefiscale" maxlength="50" />
				</td>
			</tr>			
			</table>
		</form>
	 </div>
</body>
</html>