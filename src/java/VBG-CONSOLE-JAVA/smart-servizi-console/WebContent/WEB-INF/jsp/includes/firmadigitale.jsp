<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
		<%--
		/*
		function getListaCodiciOggetto(){
			// il metodo deve ritornare una lista di codici oggetto separati da virgola
			// es: return "100,200";
		}
		function setEsitoFirma(codiceoggetto, codice, messaggio){
			// da utilizzare per aggiornare la porzione di html relativa al singolo file
			// utilizzare il codiceoggetto come id del tag da aggiornare
			// codice = OK (firma corretta), KO (firma con errori)
		}
		*/
		--%>
</script>
<fieldset id="firma-digitale-applet" style="width:450px; height:80px">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/deployJava.js"></script>
	<script type="text/javascript">
	
	    var globalAppletInizializzata = false;
	    function docWriteWrapper(func) {
	        var writeTo = document.getElementById('${param.appletElementId}'),
	            oldwrite = document.write,
	            content = '';	        
	        document.write = function(text) {
	            content += text;
	        }
	        func();
	        writeTo.innerHTML += content;
	        document.write = oldwrite;
	    }	
	    function runFirmaApplet(){
	    	globalAppletInizializzata = true;
	    	var attributes = {width:'450px', height:'60px'} ;
		    var parameters = {
					dataUrl:'${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/firmadigitale/process.htm', 
		    		jnlp_href:'${pageContext.request.contextPath}/simple_sign_app.jnlp'} ; 
		    var version = '1.6' ;
		    docWriteWrapper(function () {
        	    deployJava.runApplet(attributes, parameters, version);
          });
	    }
	    
	    
	</script>
</fieldset>