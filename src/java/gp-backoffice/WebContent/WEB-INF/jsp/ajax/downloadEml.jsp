<%@ include file="../includes/taglibs.jsp" %>


<c:set var="_codAllegato"
		value="${param.codiceallegato}" />
<c:if test="${not empty param.codiceallegato}">
	<c:set var="_codAllegato"
		value="${param.codiceallegato}" />
</c:if>
<c:if test="${empty param.codiceallegato}">
	<c:set var="_codAllegato"
		value="${codAllegato}" />
</c:if>

<c:set var="_isDownloadEml"
		value="${isDownloadEml}" />
<c:if test="${empty isDownloadEml}">
	<c:set var="_isDownloadEml"
		value="${isDownloadEml}" />
</c:if>

<c:if test="${_isDownloadEml}">


    <td width="13%">
		<a class="downloadEmlColumn"  href="javascript:downloadEml_${codAllegato}('${message_id}')" title="<fmt:message key="label.download_eml" /> ">
			<label><fmt:message key="label.download" /></label>
	    </a>
  	</td>
	</c:if>
	<c:if test="${!_isDownloadEml}">
		<div id="downloadEml_content_${_codAllegato}"></div>
		<div id="downloadEml_content_error_${_codAllegato}"></div>
	</c:if>	
	<%--
	<div id="dialog-2" title="Errore durante il download">
  								
    </div>
    --%>
    <div id="dialog-error" title="Errore durante il download">
   		<div id="content_error"> </div>
	</div>

								
<script type="text/javascript">
			
			jQuery(document).ready(function () {
				showDownloadEml(${_codAllegato});
			});
			
			
			function showDownloadEml(codiceAllegato){											
				var jhqrPr = jQuery.ajax({
					  url: '../ajax/showDownloadEml.htm',
					  context: document.body,
					  cache: false,
					  data: "codiceAllegato="+codiceAllegato,
					  dataType: "html",
					  success: function(data) {													  
						  if(data!=''){
							  $('downloadEml_content_'+codiceAllegato).innerHTML=data;
							  applyStyle();
						  }else{
							 // $('link_annulla_protocollo').style.display='';
						  }
						},
						error: function(jqXHR, textStatus, errorThrown){
							console.error("Errore nella richiesta del protocollo annullato: "+jqXHR.responseText);														
						}
					});		
			}
			
			function downloadEml_${param.codiceallegato}(idmessage){
			
				disableFunctions();
				var jhqrPr = jQuery.ajax({
					  url: '../movimentiallegati/ajaxDownloadEml.htm',
					  context: document.body,
					  cache: false,
					  data: "idmessage="+idmessage,
					  dataType: "html",
					  success: function(data) {	
						  console.log(data)
						  viewOggetto_${param.codiceallegato}(data);
						  jQuery(${param.id_ckh}).attr("disabled", false); 
						  jQuery(${param.id_radio_button}).attr("disabled", false);  
						  
						},
						error: function(jqXHR, textStatus, errorThrown){
							enableFunctions();
							//var myDialog=new dijit.Dialog(
							//{
						    //    title:"Errore durante il download",
						    //    content:"Impossibile effettuare il download:<br/><br/>"+jqXHR.responseText,
						    //    style: "width: 300px"
								        
						    //}
						//);
						//myDialog.show();
						jQuery(function() {
							jQuery('#content_error').append(jqXHR.responseText);
				            jQuery("#dialog-error").dialog({
				            	//title: "Note",
				                modal: true,
				                width:'300',
				                height:'auto',
				                resizable:true,
				                autoOpen: true
				            });
				          });
						}
					});		
			}
			
			function viewOggetto_${param.codiceallegato} (codiceoggetto){
				if('${param.codiceallegato}' != ''){
					var jqxhr = jQuery.ajax({
						  url: "../file/ajaxViewOggettoList.htm",
						  context: document.body,
						  cache: false,				
						  dataType: "html",
						  data: "fileId="+codiceoggetto+"&mostralabel=${param.mostralabel}&mostraNomeFile=${param.mostraNomeFile}&readonly=${param.readonly}&jsFx=viewOggetto_${param.idElemento}_${currtime}_fx&styleHref=${param.styleHref}",
						  success: function(dataResult) { 
							  $('downloadEml_content_${param.codiceallegato}').innerHTML = dataResult;
							 
							  enableFunctions();
							  applyStyle();
							},
						  error: function(dataError){
							  $('downloadEml_content_${param.codiceallegato}').innerHTML = dataError.innerText;
							  enableFunctions();
						  }	
						});
				}
			}
			
			
			function tabRicercaECreaMessaggio(divId, codMovimento, codTipomovimento){
				
				dijit.byId(divId).show();
				ricercaECreaMessaggioTab(codMovimento,codTipomovimento);		
			}
			
			<%-- Carica tramite una chiamta ajax la pagina createSearchDocumentiAllegati.jsp  --%>
			function ricercaECreaMessaggioTab(codMovimento,codTipomovimento) {
				
				
				}
			
			
			
			
		</script>