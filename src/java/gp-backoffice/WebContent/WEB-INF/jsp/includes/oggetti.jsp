<%@page import="java.net.URLEncoder"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- 
	
	sono necessari come parametri
	param.idElemento = serve per individuare l'id dello span dove visualizzare le informazioni
	param.codiceOggetto = serve per individuare il codice oggetto per trovare il file e le informazioni associate
	param.codiceOggettoId = serve ad identificare l'elemento della pagina che contiene il riferimento al codice dell'oggetto
	param.nomefileId = serve ad identificare l'elemento della pagina che contiene il riferimento al nomefile dell'oggetto
	param.codiceIstanza = se l'oggetto è legato ad una tabella delle istanze allora indica il codice dell'istanza di riferimento
	param.showFirma se true e l'oggetto è readonly mostra comunque il bottone Firma (SERVE PER LA FUNZIONALITA' METTIALLAFIRMA)
	param.pageFirmaCallbackFunction è la funzione di callback che viene richiamata quando la pagina di firma viene chiusa
	param.parametroLogOperazione 	(opzionale, accetta valori 1 o 0 o non specificato) 
								  	se specificato allora viene messo nel dom un parametro aggiuntivo che indica che l'utente ha rimosso il file. 
									Serve per loggare l'operazione di rimozione del File
	
 --%>
 <%
String lcl_stcIddocumento = URLEncoder.encode(StringUtils.defaultString(request.getParameter("stcIddocumento"))) ;
String lcl_stcIdallegato= URLEncoder.encode(StringUtils.defaultString(request.getParameter("stcIdallegato")));
pageContext.setAttribute("lcl_stcIddocumento", lcl_stcIddocumento);
pageContext.setAttribute("lcl_stcIdallegato", lcl_stcIdallegato);
%>
 
 
 
 	<c:if test="${empty param.idElemento}">
		[oggetti.jsp]  Attenzione !! non è stato settato il parametro idElemento.
	</c:if>
 	<c:if test="${empty param.codiceOggettoId}">
		[oggetti.jsp]  Attenzione !! non è stato settato il parametro codiceOggettoId.
	</c:if>
	<c:if test="${empty param.nomefileId}">
		[oggetti.jsp]  Attenzione !! non è stato settato il parametro nomefileId.
	</c:if>
	<span id="${param.idElemento}">
		<i class="fa fa-spinner fa-spin"></i>
	</span>
<script type="text/javascript">

	<%--
								FUNZIONI GENERALI 
									INIZIO
	--%>

	<%-- 
		Questa funzione viene chiamata per riportare il controllo allo stato iniziale
		quindi presentare la maschera di nessun file allegato o file presente
		è la prima chiamata che viene eseguita quando la pagina jsp viene renderizzata vedi sotto
	--%>
	function getDefaultUploadMsg${param.idElemento}(){
		var content${param.idElemento} = '';
		document.getElementById('${param.idElemento}').innerHTML = getSpinner();
		var call_msg = new Ajax.Request('<%=request.getContextPath()%>/file/ajaxViewOggetto.htm?fileId=${param.codiceOggetto}&idElemento=${param.idElemento}&stcIddocumento=${fn:replace(lcl_stcIddocumento,"'","\\'")}&stcIdallegato=${fn:replace(lcl_stcIdallegato,"'","\\'")}&showFirma=${param.showFirma}&pageFirmaCallbackFunction=${param.pageFirmaCallbackFunction}', {
				  method: 'post',	
				  onSuccess: function(transport){ 
					content${param.idElemento} = transport.responseText;
		  			document.getElementById('${param.idElemento}').innerHTML = content${param.idElemento};
		  			applyStyle();
		  		  },
				  onFailure: function(transport){ 
					  printResult(transport, "Errore durante il recupero dell'oggetto [${param.codiceOggetto}].");
				  }						    		 
			});
	}

	<%-- 
		Questa è la prima chiamata che viene eseguita quando la pagina jsp viene renderizzata
		per visualizzare il contenuto di default del controllo 
	--%>
	getDefaultUploadMsg${param.idElemento}();
	<%--
		Questa funzione viene invocata per renderizzare il controllo dell'upload
		con i vari tab
	--%>
	function showUploadControl${param.idElemento}(){
		
		var contentUpload = '';
		document.getElementById('${param.idElemento}').innerHTML = getSpinner();
		var call = new Ajax.Request('<%=request.getContextPath()%>/file/ajaxShowUpload.htm?idElemento=${param.idElemento}', {
					  method: 'post',	
					  onSuccess: function(transport){ 
						contentUpload = transport.responseText;
						try{
							if(document.getElementById('${param.idElemento}')){								
								document.getElementById('${param.idElemento}').innerHTML = contentUpload;
								applyStyle();
							}
						}catch(err){
							  txt="There was an error on this page.\r\n";
							  txt+="Error name: " + err.name + "\r\n";
							  txt+="Error number: " + err.number + "\r\n";
							  txt+="Error description: " + err.description + "\r\n";							  
							  txt+="Click OK to continue.\r\n";
							  alert(txt);
						}
						applyStyle();
					},
					  onFailure: function(transport){  }						    		 
				});
		
	}

	<%--
		Questa funzione è usata per passare da un tab ad un'altro tra quelli presenti nel 
		controllo di upload		 
	--%>
	function showTab${param.idElemento}(layerEl){
		
		if (layerEl == 'file'){
			
			document.getElementById('uploadFileTabHref${param.idElemento}').className = 'SchedaAttiva';
			document.getElementById('uploadLibraryTabHref${param.idElemento}').className = 'Scheda';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTabHref${param.idElemento}').className = 'Scheda';	
			}
			document.getElementById('uploadFileTab${param.idElemento}').style.display = 'block';
			document.getElementById('uploadLibraryTab${param.idElemento}').style.display = 'none';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTab${param.idElemento}').style.display = 'none';
			}	
		}else if(layerEl == 'library'){

			document.getElementById('uploadFileTabHref${param.idElemento}').className = 'Scheda';
			document.getElementById('uploadLibraryTabHref${param.idElemento}').className = 'SchedaAttiva';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTabHref${param.idElemento}').className = 'Scheda';	
			}
			document.getElementById('uploadFileTab${param.idElemento}').style.display = 'none';
			document.getElementById('uploadLibraryTab${param.idElemento}').style.display = 'block';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTab${param.idElemento}').style.display = 'none';
			}	
		}else if(layerEl == 'scanner'){

			document.getElementById('uploadFileTabHref${param.idElemento}').className = 'Scheda';
			document.getElementById('uploadLibraryTabHref${param.idElemento}').className = 'Scheda';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTabHref${param.idElemento}').className = 'SchedaAttiva';	
			}	
			document.getElementById('uploadFileTab${param.idElemento}').style.display = 'none';
			document.getElementById('uploadLibraryTab${param.idElemento}').style.display = 'none';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTab${param.idElemento}').style.display = 'block';
			}		
		}
	}
	<%--
							FUNZIONI GENERALI 
								FINE
	--%>


	<%--
						FUNZIONI TAB "DA FILE" 
								INIZIO
	Questa funzione invoca il componente AjaxUpload che esegue una chiamata a 	
	../file/ajaxUploadCall.htm ed inserisce il file nella tabella oggetti
	viene gestito anche l'inserimento del file nella libreria oggetti						
	--%>
	function uploadThis${param.idElemento}(){
		
		
		var extensionAllowed = '<fmt:message key="form.oggetti.javascript.file.extensions.allowed" />';
		if('${param.overrideExtensionsAllowed}'!=''){
			extensionAllowed = '${param.overrideExtensionsAllowed}';
		}			
		var regexpExtension = new RegExp('^('+extensionAllowed+')$');
		new AjaxUpload('#button${param.idElemento}', {
	    action: '../file/ajaxUploadCall.htm',	    
	    name: 'fileUpload',
	    autoSubmit: true,
	    onChange : function(file , ext){
	        var libreriaCheched = document.getElementById('libreriachk${param.idElemento}').checked;
	        var validationError = false;
	        if(libreriaCheched){
		        if(document.getElementById('tipologiaOggettoUpload').value==''){
			        	alert('<fmt:message key="form.oggetti.javascript.file.alert.tipologia" />');
			        	return false;
		        }
		        if(document.getElementById('descrizioneUpload').value==''){
		        	alert('<fmt:message key="form.oggetti.javascript.file.alert.descrizione" />');
		        	return false;
	        	}
	        }

		    },
		onSubmit : function(file , ext){
				if (ext && regexpExtension.test(ext)){
					//* Setting data 
						this.setData({
							'libreriachk': document.getElementById('libreriachk${param.idElemento}').checked,
							'tipologiaOggetto': document.getElementById('tipologiaOggettoUpload').value,
							'descrizione': document.getElementById('descrizioneUpload').value,
							'codiceIstanza': '${param.codiceIstanza}'
						});			
					//*/		
					$('uploadText${param.idElemento}').innerHTML= getSpinner()+' <fmt:message key="form.oggetti.javascript.file.innerhtml.loading" />' + file;	
				} else {		
					var alertMesg = '<fmt:message key="form.oggetti.javascript.file.innerhtml.error.file.extension" />'+extensionAllowed; 			
					alert(alertMesg);
					// extension is not allowed
					document.getElementById('uploadText${param.idElemento}').innerHTML = alertMesg;
					// cancel upload
					return false;				
				}	        
		},
		onComplete : function(file, response){
			if(isNaN(response)){				
				document.getElementById('uploadText${param.idElemento}').innerHTML = '<b style="color: red"><fmt:message key="form.oggetti.javascript.file.innerhtml.error.upload" />: ' + file+'<br />'+response+"</b>";
			}else{
				document.getElementById('${param.codiceOggettoId}').value = response;
				document.getElementById('uploadText${param.idElemento}').innerHTML = '<fmt:message key="form.oggetti.javascript.file.innerhtml.success.upload" />'+ file + '. <fmt:message key="form.oggetti.javascript.file.innerhtml.success.upload.postfazione" />';
			}
		}
	});
		

}
	<%--
		Questa funzione resetta l'associazione sul campo nascosto legato al command
		E' comunque necessario salvare i dati del form
	--%>
	function resetOggetto${param.idElemento}(message){	
		if('1'==='${param.parametroLogOperazione}'){
			message+='\nL\'operazione sara\' riportata nei logs.';
		}
		if(checkConfirmMessage(message)){	
			document.getElementById('${param.idElemento}').innerHTML= '<fmt:message key="form.oggetti.javascript.file.reset.oggetto" />';			 
			document.getElementById('${param.codiceOggettoId}').value = '';
			document.getElementById('${param.nomefileId}').value = '';
			if('1'==='${param.parametroLogOperazione}'){
				jQuery( "#${param.nomefileId}" ).after( "<input type=\"hidden\" name=\"<%= WebConstants.LOG_CANCELLAZIONE_FILE_REQ_ATTR %>\" value=\"${param.codiceIstanza}-${param.codiceOggetto}\"/>" );
			}
		}
	}

	var myDialog;
	
	function ripristina${param.idElemento}(idOggettoStorico){
		if( confirm("Procedere con il ripristino? (L'operazione sarà riportata nei log)") )
		{
			console.log(idOggettoStorico);
			disableFunctions();
			var jqxhr = jQuery.ajax({
				  url: "../file/ajaxRipristinaFile.htm?id=" + idOggettoStorico,
				  cache: false,
				  success: function(dataResult) {
					console.log("ripristinato!");
					location.reload();
				  },
				  error: function(dataError){					  
					  console.log("NON ripristinato!");

				  },
				  always:function(){					  
					  enableFunctions();
				  }
					  
					  
			});
		}
	}
	
	function tabStoricoOggetto${param.idElemento}(divId, id) {
		var call = jQuery.ajax
		({
			url: "${pageContext.request.contextPath}/file/ajaxListaOggettiStorico.htm",
			context: document.body,
			cache: false,				
			dataType: "html",
			data: "codiceOggetto="+id+"&idElemento=${param.idElemento}",
			success: function(dataResult) 
			{ 
			   	myDialog = new dijit.Dialog
			   	({
					title: "<fmt:message key='label.lista_storico' />:",
					style: "width: 600px;higth: 400px",
					content: dataResult,
					id:"lista_storico_oggettiDiv"
			  	});
				myDialog.show();
			 },
			 error: function(dataError)
			 {
				myDialog = new dijit.Dialog
				({
					title: "Programmatic Dialog Creation",
					style: "width: 600px;higth: 400px",
					content: dataError,
					id:"lista_storico_oggettiDiv"
				});
				myDialog.show();
			 }
		});
    }


	function close()
	{
		myDialog.hide();
	}
	
	<%--
		la funzione è usata nel tab "Da file" quando l'utente clicca sul checkbox 
		libreriachk per aggiungere il file che carica alla libreria oggetti
	--%>
	function showLibrary${param.idElemento}(obj){
		if(obj.checked){
			document.getElementById('libreriachkDiv${param.idElemento}').style.display='block';
		}else{
			document.getElementById('libreriachkDiv${param.idElemento}').style.display='none';
		}	
	}

	<%--
							FUNZIONI TAB "DA FILE" 
									FINE


							FUNZIONI TAB "DA LIBRERIA" 
									INZIO	
									

		funzione che crea un oggetto autocompleter sul tab della ricerca file da libreria oggetti 	
	--%>
	var completerLibrary${param.idElemento} = null;
	function createAjaxCompleter${param.idElemento}(){

			document.getElementById('tr_libreriaFile${param.idElemento}_id').style.display='';
			if(!completerLibrary${param.idElemento}){
			completerLibrary${param.idElemento} = 
							new Ajax.Autocompleter("libreriaFile${param.idElemento}_id", 
									"libreriaFile${param.idElemento}_id_choices", 
								"<%=request.getContextPath()%>/ajax/findOggettiinfo.htm", 
								{	paramName :"textToSearch",
									minChars :1,
									frequency: 0.7,
									afterUpdateElement :setHiddenCodiceOggetto${param.idElemento},
									callback: filterLibrary${param.idElemento}
								}
							);
			}
	}
	<%--
		funzione di callback usata da createAjaxCompleter per usare come filtro anche la tipologia oggetto 	
	--%>
	function filterLibrary${param.idElemento}(element, entry) { 
	      return entry + "&tipologiaOggetto=" + document.getElementById("tipologiaOggettoUpload_2").value;
	}
	<%--
		funzione di chiamata da createAjaxCompleter, nella funzionalità  di scelta 
		di un file dalla libreria, alla scelta dell'utente di un file 	
	--%>
	function setHiddenCodiceOggetto${param.idElemento}(inputField,listItem){
		var a = listItem.id;
		document.getElementById('libreriaFile${param.idElemento}_id').value = inputField.value;
		document.getElementById('${param.codiceOggettoId}').value = a;
		document.getElementById('libreriaFile${param.idElemento}_id_choices').fade();	 
		document.getElementById('uploadTextLibrary${param.idElemento}').innerHTML = '<fmt:message key="form.oggetti.javascript.file.innerhtml.success" />'
	}
	
	function editDocs${param.idElemento}(id){
		location.href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId="+id;
	}
	
	function getSpinner(){
		return '<i class="fas fa-spinner fa-spin"></i>';	
		
	}	

	<%--
						FUNZIONI TAB "DA LIBRERIA" 
								FINE	
	--%>
</script>