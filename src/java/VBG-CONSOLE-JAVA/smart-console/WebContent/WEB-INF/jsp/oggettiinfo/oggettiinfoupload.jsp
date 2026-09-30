<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- 

	sono necessari come parametri
	param.idElemento = serve per individuare l'id dello span dove visualizzare le informazioni
	param.codiceOggetto = serve per individuare il codice oggetto per trovare il file e le informazioni associate
	param.idComuneOggetto = l'idcomune della tabella oggetti
	
 --%>
 
 	 <c:if test="${empty param.idComuneOggetto}">
		[oggettiinfoupload.jsp]  Attenzione !! non è stato settato il parametro idComuneOggetto.
	</c:if>
	
<span id="${param.idElemento}"> </span>
<script type="text/javascript">
// <!--
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
		var call_msg = new Ajax.Request('<%=request.getContextPath()%>/file/ajaxViewOggetto.htm?fileId=${param.codiceOggetto}&idComuneOggetto=${param.idComuneOggetto}&idElemento=${param.idElemento}&isLibreria=${param.isLibreria}', {
				  method: 'post',	
				  onSuccess: function(transport){ 
					content${param.idElemento} = transport.responseText;
		  			document.getElementById('${param.idElemento}').innerHTML = content${param.idElemento}; 
		  			},
				  onFailure: function(transport){  }						    		 
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
		var call = new Ajax.Request('<%=request.getContextPath()%>/file/ajaxShowUpload.htm?idElemento=${param.idElemento}&isLibreria=${param.isLibreria}', {
					  method: 'post',	
					  onSuccess: function(transport){ 
						contentUpload = transport.responseText;
						try{
							if(document.getElementById('${param.idElemento}')){								
								document.getElementById('${param.idElemento}').innerHTML = contentUpload;
							}
						}catch(err){
							  txt="There was an error on this page.\n\n";
							  txt+="Error name: " + err.name + "\n\n";
							  txt+="Error number: " + err.number + "\n\n";
							  txt+="Error description: " + err.description + "\n\n";							  
							  txt+="Click OK to continue.\n\n";
							  alert(txt);
						  }
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
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTabHref${param.idElemento}').className = 'Scheda';	
			}
			document.getElementById('uploadFileTab${param.idElemento}').style.display = 'block';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTab${param.idElemento}').style.display = 'none';
			}	
		}else if(layerEl == 'scanner'){

			document.getElementById('uploadFileTabHref${param.idElemento}').className = 'Scheda';
			if(document.getElementById('uploadScannerTabHref${param.idElemento}')){
				document.getElementById('uploadScannerTabHref${param.idElemento}').className = 'SchedaAttiva';	
			}	
			document.getElementById('uploadFileTab${param.idElemento}').style.display = 'none';
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
		
		if('${param.overrideExtensionsAllowed}'!=''){
			var extensionAllowed = '${param.overrideExtensionsAllowed}';
		}else{
			var extensionAllowed = '<fmt:message key="form.oggetti.javascript.file.extensions.allowed" />';
		}			
		var regexpExtension = new RegExp('^('+extensionAllowed+')$');
		new AjaxUpload('#button${param.idElemento}', {
	    action: '../file/ajaxUploadCall.htm',
	    name: 'fileUpload',
	    autoSubmit: true,
	    onChange : function(file , ext){
	        var libreriaCheched = false; //document.getElementById('libreriachk${param.idElemento}').checked;
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
							'libreriachk': 'false',
							'tipologiaOggetto': '',
							'descrizione': ''
						});			
					//*/		
					$('uploadText${param.idElemento}').innerHTML= '<img src="../images/spinner.gif"> <fmt:message key="form.oggetti.javascript.file.innerhtml.loading" />' + file;	
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
				document.getElementById('uploadText${param.idElemento}').innerHTML = '<b style="color: red"><fmt:message key="form.oggetti.javascript.file.innerhtml.error.upload" />: </b>' + file;
			}else{
				document.getElementById('${param.codiceOggettoId}').value = response;
				document.getElementById('uploadText${param.idElemento}').innerHTML = '<fmt:message key="form.oggetti.javascript.file.innerhtml.success.upload" />'+ file;
			}
		}
	});

}
	<%--
		Questa funzione resetta l'associazione sul campo nascosto legato al command
		E' comunque necessario salvare i dati del form
	--%>
	function resetOggetto${param.idElemento}(message){	
		if(checkConfirmMessage(message)){	
		document.getElementById('${param.idElemento}').innerHTML= '<fmt:message key="form.oggetti.javascript.file.reset.oggetto" />';
		document.getElementById('${param.codiceOggettoId}').value = '';
		}
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
	function createAjaxCompleter${param.idElemento}(){

			document.getElementById('tr_libreriaFile${param.idElemento}_id').style.display='block';
			var completerLibrary${param.idElemento} = 
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

	var editDocsDlg${param.idElemento};
	
	function editDocs${param.idElemento}(id){	
		// create the dialog
	    editDocsDlg${param.idElemento} = new dijit.Dialog({
	        title: '<fmt:message key="label.applet_modifica_doc" /> <fmt:message key="label.applet_modifica_doc.close" />'
	    });
	    var d = new Date();
	    var millis = d.getMilliseconds();
	    new Ajax.Request('${pageContext.request.contextPath}/file/editDocApplet.htm', {
			  method: 'get',
			  parameters: {fileId: id, func: 'closeEditDocs${param.idElemento}', ts: millis},
			  onSuccess: function(transport){				 
		        // set the content of the dialog
		        editDocsDlg${param.idElemento}.set("content", transport.responseText);
		        editDocsDlg${param.idElemento}.show();
			  },
			  onFailure: function(){}			  
		});
	}
	
	function closeEditDocs${param.idElemento}(){
		getDefaultUploadMsg${param.idElemento}();
		editDocsDlg${param.idElemento}.hide();
	}
// -->
</script>