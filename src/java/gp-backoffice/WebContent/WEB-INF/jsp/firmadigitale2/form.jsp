<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="label.firma_digitale" /></title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug:false"></script>	
	<script type="text/javascript">
	  	dojo.require("dojo.data.ItemFileReadStore");
	  	dojo.require("dojo.parser");
	  	dojo.require("dijit.Tree");
	  	dojo.require("dijit.Menu");
	  	dojo.require("dijit.Dialog");
	  	dojo.require("dijit.layout.ContentPane");
	  	dojo.require("dijit.Tooltip");
	</script>
	<style type="text/css"> 
	<!--
	#file_info {
		width: 98%;
	}
	#anteprima {
		margin-top: 10px;
		width: 98%;
		height: 480px;
	}	
	-->
	</style>
	<script type="text/javascript">
		var firmaSessionId = "${firmaSessionId}";
		var firmaFileIdArrayVar = "${firmaFileId}";
		var processoInEsecuzione = false;
		var mettiAllaFirma = "${mettiAllaFirma}";
		var firmaFileIdArray = [];
		
		function start(){
			processoInEsecuzione = true;
			disableFirmaButton();
			initFirmaFileIdArray();
			downloadJWSApplication();
			getSignedFiles();
		}
		
		function initFirmaFileIdArray(){
			var _fileIdArray = firmaFileIdArrayVar.split(',');
			for(var i = 0; i < _fileIdArray.length; i++) {
				var el = {fileId:_fileIdArray[i], signed:"FALSE"};
				firmaFileIdArray.push(el);
			}
		}
	
		function downloadJWSApplication(){
			location.href="/firma/signApp?sessionId="+firmaSessionId+"&fileId="+firmaFileIdArrayVar;
		}
		
		function disableFirmaButton(){
			jQuery("#firma_id").click(function () {return false;});
			jQuery("#firma_id").css({"background-color": "grey", "border":"1px solid grey"});
		}
		
		function chiudi(){
			if(processoInEsecuzione && !isProcessoTerminato()){
				if(!confirm("Attenzione! Il processo di firma non è stato completato.")){
					return;
				}
			}
			var postData = jQuery("#theFormId").serializeArray();
			jQuery.ajax({
				  url: "${pageContext.request.contextPath}/file/ajaxUnlockFiles.htm",
				  context: document.body,
				  cache: false,
				  type: 'POST',
				  dataType: "html",
				  data: postData,
				  success: function(dataResult) {
					  historyBack('');  
				  },
				  error: function(dataError){						  
					  historyBack('');		   
				  }	
			});
		}
		
		function getSignedFiles(){
			var callMe = false;
			for(var i = 0; i < firmaFileIdArray.length; i++) {
				if(firmaFileIdArray[i].signed == "FALSE"){
					getSignedFile(firmaFileIdArray[i].fileId);
					callMe = true;
				}
			}
			if(callMe){
				setTimeout(getSignedFiles,3000);
			}
		}
		
		function getSignedFile(fileId){	
			jQuery.ajax({
				  url: "${pageContext.request.contextPath}/firmadigitale2/ajaxGetSignedFile.htm",				
				  data: {firmaSessionId:firmaSessionId, firmaFileId:fileId, mettiAllaFirma:mettiAllaFirma},
				  cache: false,
				  type: 'GET',
				  dataType: "html",
				  success: function(dataResult) {
					  if(dataResult == 'TRUE'){
						  updateEsitoFirma(fileId, "TRUE");
					  }else{
						  updateEsitoFirma(fileId, "FALSE");
					  }
				  },
				  error: function(dataError){						  
					  updateEsitoFirma(fileId, "ERROR");   
				  }	
			});
		}
		
		function updateFirmaFileIdArray(fileId,signed){
			for(var i = 0; i < firmaFileIdArray.length; i++) {
				if(firmaFileIdArray[i].fileId == fileId){
					firmaFileIdArray[i].signed = signed;
				}
			}
		}
		
		function updateEsitoFirma(fileId, signed){
			updateFirmaFileIdArray(fileId, signed);
			if(signed == 'TRUE'){
				jQuery("#file_da_firmare_esito_"+fileId).html("<label style='color: green; font-weight:bold'>Processo di firma completato.</label>");
			}
			if(signed == 'FALSE'){
				jQuery("#file_da_firmare_esito_"+fileId).html("<label style='color: black; font-weight:bold'>Processo di firma in esecuzione, attendere il completamento del processo prima di chiudere la pagina...</label>");
			}
			if(signed == 'ERROR'){
				jQuery("#file_da_firmare_esito_"+fileId).html("<label style='color: red; font-weight:bold'>Attenzione! Si è verificato un errore durante il completamento del processo di firma. Chiudere la pagina e riprovare</label>");
			}
		}
		
		function isProcessoTerminato(){
			var isTerminato = true;
			for(var i = 0; i < firmaFileIdArray.length; i++) {
				if(firmaFileIdArray[i].signed == "FALSE"){
					isTerminato = false;
				}
			}
			return isTerminato;
		}
		
		
		
		(function( $ ) {$(function() {
		
			$(".toggle").each(function () {			
				var titolo = $(this).html();
				$(this).html('[+] '+titolo).css("cursor", "pointer");
				$(this).click(function() {
				  if ($(this).text().slice(0,3) === '[-]') {
					  $(this).next().hide();
					  $(this).parent().addClass("anteprima-chiuso");
					  $(this).html('[+] '+titolo);
	              }else{
	            	  $(this).html('[-] '+titolo);
	            	  $(this).next().show();
	            	  $(this).parent().removeClass("anteprima-chiuso");
	            	  caricaAnteprima($(this).parent());
	              }
				});		
			});
		});})(jQuery);
		
		
		function apriChiudiAnteprime(){
			jQuery(".toggle").each(function () {
				var titolo = jQuery(this).text().slice(3);			
				if (jQuery(this).text().slice(0,3) === '[-]') {
					jQuery(this).next().hide();
					jQuery(this).parent().addClass("anteprima-chiuso");
					jQuery(this).html('[+] '+titolo);
	            }else{
	            	jQuery(this).html('[-] '+titolo);
	            	jQuery(this).next().show();
	            	jQuery(this).parent().removeClass("anteprima-chiuso");
	            	  caricaAnteprima(jQuery(this).parent());
	            }
			});
		}
		
		function caricaAnteprima(el){
			if(el.find('object').length > 0){
				return;
			}
			var anteprima = jQuery("<object data='${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=" + el.data('fileId') + "&no_dialog=true' width='100%' height='95%' type='"+el.data('mimeType')+"' />");
			anteprima.appendTo(el);
		}
	</script>
</head>
<body>

	<span class="titoloPagina"> 
		<fmt:message key="label.firma_digitale" />
	</span>
	<c:if test="${not empty error }">
	<div class="error"> 
		${error }
	</div>
	</c:if>
	<div id="subcontent">
		<br />
		<c:if test="${fn:length(listaFilesDaFirmare) > 1}">
		<a href="#" onclick="apriChiudiAnteprime()" id="apriChiudiAnteprime" style="color: black; float: right; margin-right: 5px">[+]/[-]</a>
		<br />
		</c:if>	
		
		
		 <form name="paginaFirmaFrm"  action="${pageContext.request.contextPath}/firmadigitale2/insertTrasformaInPdf.htm?isFirmaRemota=false" method="post">
			<c:forEach items="${codiceoggettoArray}" var="o">
				<input type="hidden" name="codiciDocDaFirmareArray" value="${o}"/>
			</c:forEach>
			
			<c:forEach items="${listaFilesDaFirmare}" var="fileDF">			
				<fieldset id="file_info"><legend>Documento da firmare</legend>
					<b>${fileDF.descrizione}</b> (${fileDF.codice}) 
					
					<span id="file_da_firmare_esito_${fileDF.fileId }">
						  
									<div id="functions">
									<ul>
										<li><a href="javascript:editDocs${fileDF.codice}('${fileDF.codice}');"><fmt:message key="label.modifica" /></a></li>
										<c:if test=""></c:if>
										<c:if test="${fileDF.mimeType !='application/pdf' && fileDF.mimeType != 'application/pkcs7-mime'}">
											<li><a href="javascript:anteprimaPDF${fileDF.codice}(${fileDF.codice})"><fmt:message key="label.anteprima_pdf" /></a></li>
											<li><a href="javascript:trasformaInPDF${fileDF.codice}(${fileDF.codice})"><fmt:message key="label.converti_in_pdf" /></a></li>
										</c:if>

									</ul>
									</div>

					</span>
					<script type="text/javascript">
						function trasformaInPDF${fileDF.codice}(codiceOggetto,movimentiAllegatiId){
							doSubmit('${pageContext.request.contextPath}/firmadigitale2/insertTrasformaInPdf.htm?codiceOggetto='+codiceOggetto+'&mettiAllaFirma=${isMettiAllafirma}&isFirmaRemota=false','<fmt:message key="javascript.confirm.conferma_trasforma_il_documento_in_pdf" />',paginaFirmaFrm);
							//doSubmit('${pageContext.request.contextPath}/insertTrasformaInPdf/insertTrasformaInPdf.htm?codiceMovimentoallegato='+movimentiAllegatiId+'&codiceOggetto='+codiceOggetto+'&lista_doc_da_firmare=${lista_doc_da_firmare}','<fmt:message key="javascript.confirm.conferma_trasforma_il_documento_in_pdf" />');
						}
						function anteprimaPDF${fileDF.codice}(codiceOggetto){
							location.href="${pageContext.request.contextPath}/file/ajaxAnteprimaPdf.htm?fileId="+codiceOggetto
						}
						function editDocs${fileDF.codice}(id){
							location.href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId="+id;
						}
					</script>
				</fieldset>
				<fieldset id="anteprima" class="anteprima-chiuso" data-file-id="${fileDF.codice}" data-mime-type="${fileDF.mimeType }">
					<legend class="toggle"><fmt:message key="label.firma_digitale_anteprima" /></legend>
				</fieldset>
			</c:forEach>
		 </form> 
	</div>
	<form name="theForm" id="theFormId" method="post">
		<c:forEach items="${listaFilesDaFirmare}" var="fileDF">			
			<input type="hidden" name="codiceoggetto" value="${fileDF.codice}"/>
		</c:forEach>
	</form>		
<div id="functions">
	<ul>
		<li><a href="javascript:start();" id="firma_id"><fmt:message key="button.firma" /></a></li>
		<li><a href="javascript:chiudi();" id="chiudi_id"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>
