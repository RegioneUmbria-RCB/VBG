<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<%--
	parametri obbligatori:
		idElemento: l'identificativo dell'elemento che indica il Div sul quale andare a scrivere le informazioni sull'oggetto
		(DEVE ESSERE UNIVOCO ALL'INTERNO DELLA PAGINA DOVE VIENE USATO)
		fileId:	Il codiceOggetto per scaricare le informazioni sul file (Può essere vuoto)
	parametri opzionali:	
		mostralabel: accetta valori true o false (può essere nullo). Indica se mostrare anche il nome del file e la dimensione a fianco dell'icona di download
		mostraNomeFile: se mostralabel true allora mostra anche il nome del file. Accetta valori true o false (può essere nullo).
		readonly: accetta valori true o false (può essere nullo). Indica se mostrare le funzioni di modifica del file
		styleHref: lo style da applicare ai collegamenti href
		showEditDocs: mostra le funzionalità di modifica del file
		ATTENZIONE!!! SE SI MODIFICA QUESTA PAGINA E' NECESSARIO VERIFICARE LA CLASSE org.jmesa.customColumn.LinkOggetti.java ed i suoi usi
						es:Comunicazioni sulle graduatorie
		
 --%>
<c:if test="${not empty param.fileId}">
<c:set var="currtime"><%=System.currentTimeMillis() %></c:set>
<script type="text/javascript">
	jQuery(document).ready(function(){
		viewOggetto_${param.idElemento}_${currtime}_fx();
	});
	
	function viewOggetto_${param.idElemento}_${currtime}_fx (){
		if('${param.idElemento}' != ''){
			var jqxhr = jQuery.ajax({
				  url: "../file/ajaxViewOggettoList.htm",
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  data: "fileId=${param.fileId}&mostralabel=${param.mostralabel}&showEditDocs=${param.showEditDocs}&mostraNomeFile=${param.mostraNomeFile}&mostrastorico=true&readonly=${param.readonly}&jsFx=viewOggetto_${param.idElemento}_${currtime}_fx&styleHref=${param.styleHref}",
				  success: function(dataResult) { 
					   $('id_${param.idElemento}_${currtime}').innerHTML = dataResult;
					   applyStyle();
					},
				  error: function(dataError){
					  $('id_${param.idElemento}_${currtime}').innerHTML = dataError.innerText;
				  }	
				});
		}
	}
	

	
	var myDialog${param.idElemento}_${currtime};
	
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
	
	async function tabStoricoOggetto(divId, id){
		
	    window.vbg.mostraModalCaricamento();
	    
	    
		const data = new URLSearchParams();
		data.append('codiceOggetto',id);
		data.append('idElemento', '${param.idElemento}');
	
		
		const response = await fetch("${pageContext.request.contextPath}/file/ajaxListaOggettiStorico.htm", {
            method: "POST",
            cache: "no-cache",
            body: data                
		});		
		
		let messaggio = await response.text();
		
		
		vbg.nascondiModalCaricamento();
		
		if (response.status === 200) {

			let divModalId = 'storico-oggetto-div';
			let divModalIdBody = 'storico-oggetto-div-body';
			let vbgModalOggetti  =  document.getElementById(divModalId);
			
			if(!vbgModalOggetti){	
				let modal = document.createElement('div');
				
				modal.innerHTML = `<vbg-modal id='\${divModalId}'> 
									<div slot='body' id='\${divModalIdBody}'></div>
									</vbg-modal>`;
								
				
				document.body.appendChild(modal);
			}
		
			vbgModalOggetti  =  document.getElementById(divModalId);
			
			impostaInnerHTMLConScript(document.getElementById(divModalIdBody), messaggio)
			
	     	
	     	vbgModalOggetti.open();
			
			
		}else{				
			
			alert( messaggio );
			
		}
	    
	    /*
		// TODO FETCH AL POSTO DI JQuery.ajax
		var call = jQuery.ajax
		({
			url: "${pageContext.request.contextPath}/file/ajaxListaOggettiStorico.htm",
			context: document.body,
			cache: false,				
			dataType: "html",
			data: "codiceOggetto="+id+"&idElemento=${param.idElemento}",
			success: function(dataResult) 
			{
				
					let divModalId = 'storico-oggetto-div';
					let divModalIdBody = 'storico-oggetto-div-body';
					let vbgModalOggetti  =  document.getElementById(divModalId);
					
					if(!vbgModalOggetti){	
						let modal = document.createElement('div');
						
						modal.innerHTML = `<vbg-modal id='\${divModalId}'> 
											<div slot='title'><h2><fmt:message key='label.lista_storico' /></h2></div>
											<div slot='body' id='\${divModalIdBody}'></div>
											</vbg-modal>`;
										
						
						document.body.appendChild(modal);
					}
				
					vbgModalOggetti  =  document.getElementById(divModalId);
					document.getElementById(divModalIdBody).innerHTML = dataResult;
			     	window.vbg.nascondiModalCaricamento();
			     	vbgModalOggetti.open();
			 },
			 error: function(dataError)
			 {
		     	myDialog${param.idElemento}_${currtime} = new dijit.Dialog({
			        title: "<fmt:message key='label.lista_storico' />:",
			        style: "width: 600px;higth: 400px",
			    });
					myDialog${param.idElemento}_${currtime}.set("content", dataError);
			     	myDialog${param.idElemento}_${currtime}.show();	
			 }
		});
		
		
		
		*/
    }
	
	function close()
	{
		myDialog${param.idElemento}_${currtime}.hide();
		//myDialog${param.idElemento}.destroy();
	}

		

	
	
</script>
<style>
.el-mostra-oggetto {
    display: block;
    white-space: nowrap
}

.el-mostra-oggetto > a{
    min-width: 16px;
    display: inline-block;
}

.jmesa .highlight .el-mostra-oggetto > a {
    color: var(--accent-color);
}
</style>
    

	<span id="id_${param.idElemento}_${currtime}" class="el-mostra-oggetto"><i class="fa fa-spinner fa-spin"></i></span>

    
</c:if>