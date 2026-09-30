<?xml version="1.0" encoding="UTF-8" ?>
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
	<div class="titolo"><fmt:message key='label.richieste-suap' /></div>
	<div class="descrizione"></div>
	
	
	<fieldset style="padding: 2px;">

	<%@ include file="../includes/chiudiPaginaIniziale.jsp" %>
	
	
	
	
	
	<br />
	<div id="richieste_id" >
	
	<form name="istanzeForm" action="richieste.htm">
		${htmlTable }		
	</form>
	
	
	</div>
	

	<br />
	
	<div id="myDialog"><div id="myDialogText"></div></div>
	
	<script type="text/javascript">
	
	
	
	
		function callback() {
	      setTimeout(function() {
	        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
	      }, 1000 );
	    };
	 
	
		function showPanelNotifiche(numNotifiche){
			
			$('#nnotificheId').text(numNotifiche);
			var options = {};
			$('#richieste_id').show( 'blind', options, 500, callback );			
		}
	
		
		$(function(){
			
		$( ".segna-letto-link" ).button({
  			  label: "Elimina dalla lista"
  		});	
		
		$( ".segna-letto-link" ).click(function() {
			if(confirm('Attenzione! Confermate l\'eliminazione della richiesta?')){
			var url = '${pageContext.request.contextPath}/istanze/ajaxsegnacomeletto.htm?' + $(this).data("qsmac");
			
			var ajaxOpts = {
					 
					context: this,
					type: 'POST',
					success: function(data){
						$("#myDialogText").html(data);
						$("#myDialog").dialog({
							 resizable: false,
							 modal: true,
							 width:'auto',
							 title: 'Lista degli allegati'
							}
						);
					}
				};
			    			
				$.ajax(url,ajaxOpts);  
			}
		});
		
		$( ".dettaglio-link" ).button({
			  label: "Vai al dettaglio"
		});	
		
		$( ".dettaglio-link" ).click(function() {
			
			var url = '${pageContext.request.contextPath}/istanze/dettagliorichiesta.htm?' + $(this).data("qsmac");
			document.location.href=url;			

		});
		
		
		
		$.ajax({
		    type: 'GET',
		    url: '../istanze/ajaxCountRichiesteNonLette.htm',
		    success: function(data) {		    	
		    	 if (data.items>=0){
		    		showPanelNotifiche(data.items);
		    	 }
		    },
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		        // alert("Errore durante il recupero dei documenti dal server");
		    },
		    dataType: "json"
			});
		
		});
	</script>
	<input type="button" id="chiudi" data-custatt="session" class="bottone-cart" onclick="document.location.href='../istanze/listFoDomande.htm'" value="Chiudi"/>
</body>
</html>