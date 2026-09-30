<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="label.firma_digitale" /></title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/dojo/dojo/dojo.js" 
	djConfig="parseOnLoad:true, isDebug:false"></script>	
	<script type="text/javascript">
	  	dojo.require("dojo.data.ItemFileReadStore");
	  	dojo.require("dojo.parser");
	  	dojo.require("dijit.Tree");
	  	dojo.require("dijit.Menu");
	  	dojo.require("dijit.Dialog");
	  	dojo.require("dijit.layout.ContentPane");
	  	dojo.require("dijit.Tooltip");
	</script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/dojo/dijit/themes/nihilo/nihilo.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/styles/standard.css" />
	<style type="text/css"> 
	<!--
	body {
	 	margin: 2em;
	}
	#pdf {
		width: 100%;
		height: 480px;
	} 
	#pdf p {
	   padding: 1em;
	} 
	#pdf object {
	   display: block;
	}
	#app {
		width: 100%;
		height: 70px;
	}
	#functions {
		
	}
	-->
	</style>
	<script type="text/javascript">
	
	$(window).bind('beforeunload', function(event) {
		if(!chiusaNormalmente){
	   		chiudi(false);
		}
	});

	
		var chiusaNormalmente = false;
		function chiudi(closeWindow){
		
			var postData = jQuery("#theFormId").serializeArray();
			var jqxhr = jQuery.ajax({
				  url: "${pageContext.request.contextPath}/file/ajaxUnlockFiles.htm",
				  context: document.body,
				  cache: false,
				  type: 'POST',
				  dataType: "html",
				  data: postData,
				  success: function(dataResult) {		
					  <c:if test="${not empty param.nofinestra}">
					  	historyBack('');
					  </c:if>
					  <c:if test="${empty param.nofinestra}">

						<c:if test="${not empty func}">
							window.opener.${func}();
						</c:if>
						if(closeWindow){
							chiusaNormalmente = true;
							window.close();
						}
						
						</c:if>
					},
				  error: function(dataError){						  
					//  alert("error:" + dataError);		   
				  }	
				});
		}
		
		function getListaCodiciOggetto(){
			return '${codiceoggetto}';
		}
		
		var contatoredocumenti = ${fn:length(listaFilesDaFirmare)};
		
		function setEsitoFirma(codiceoggetto, codice, messaggio){
			contatoredocumenti--;
			var esito = "";
			if(codice=='OK'){
				esito = '<img src="${pageContext.request.contextPath}/images/success.png" />';
			}else{
				esito = '<img src="${pageContext.request.contextPath}/images/error.png" />&nbsp;' + messaggio;
			}
			jQuery('#contatore_file_firmati').html(contatoredocumenti);
			jQuery('#file_da_firmare_'+codiceoggetto+'_esito').html(esito);
			jQuery('#file_da_firmare_'+codiceoggetto).show();
			var id = 'file_da_firmare_'+codiceoggetto;
			setTimeout('jQuery(\'#'+id+'\').hide();',2000);
		}
		
	</script>
</head>
<body>
<!-- §§§BEGIN§§§ -->

	<span class="titoloPagina"> 
		<fmt:message key="label.firma_digitale" />
	</span>
	<div id="subcontent">
		<c:choose>
			<c:when test="${FIRMA_MULTIPLA eq false }">
				<fieldset id="pdf"><legend><fmt:message key="label.firma_digitale_anteprima" /></legend>
				<br />
				<object data="${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=${codiceoggetto}&no_dialog=true&ts_=<%= System.currentTimeMillis() %>" type="${mime_type }" width="100%" height="80%" standby="Caricamento file...">
			  		<p><fmt:message key="label.firma_digitale_anteprima_error" /></p>
				</object>	
				</fieldset>
			</c:when>
			<c:otherwise>
				
			</c:otherwise>
		</c:choose>
		
			<fieldset> <span id="contatore_file_firmati">${fn:length(listaFilesDaFirmare)}</span> <fmt:message key="label.documenti_da_firmare" /> <fmt:message key="label.di" /> ${fn:length(listaFilesDaFirmare)}
				
			<ul>
				<c:forEach items="${listaFilesDaFirmare}" var="fileDF">			
					<li id="file_da_firmare_${fileDF.codice}" style="display: none;">
							<b>${fileDF.descrizione}</b> (${fileDF.codice})<span id="file_da_firmare_${fileDF.codice }_esito"></span>		
					</li>
				</c:forEach>
			</ul>
			</fieldset>
		
		<br />
		<fieldset id="app">
			<script src="${pageContext.request.contextPath}/scripts/deployJava.js"></script>
			<script>
			    var attributes = {width:'100%', height:'100%'} ;
			    var parameters = {
						dataUrl:'${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/firmadigitale/process.htm?ts_=<%= System.currentTimeMillis() %>', 
			    		jnlp_href:'${pageContext.request.contextPath}/simple_sign_app.jnlp'} ; 
			    var version = '1.6' ;
			    deployJava.runApplet(attributes, parameters, version);
			</script>
		</fieldset>
	</div>
	
	<form name="theForm" id="theFormId" method="post">
		<c:forEach items="${listaFilesDaFirmare}" var="fileDF">			
			<input type="hidden" name="codiceoggetto" value="${fileDF.codice}"/>
		</c:forEach>
	
	</form>
	
	

<!-- §§§END§§§ -->
<div id="functions">
	<ul>
		<li>
			<a href="javascript:chiudi(true);"><fmt:message key="button.back" /></a>
		</li>
	</ul>
</div>
</body>
</html>
