<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.dataimport.web.helper.ImportParameters"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="it.gruppoinit.dataimport.web.helper.ImportParameters"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/import-taglibs.jsp"%>
<%  
	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title><fmt:message key="import.inputpage.title" /></title>
</head>
<body>
	<style type="text/css">
		/* progress bar container */
		#progressbar{
		border:1px solid black;
		width:200px;
		height:20px;
		position:relative;
		color:black;
		}
		/* color bar */
		#progressbar div.progress{
		position:absolute;
		width:0;
		height:100%;
		overflow:hidden;
		background-color:#64AA10;
		}
		/* text on bar */
		#progressbar div.progress .text{
		position:absolute;
		text-align:center;
		font-size: 14pt;
		color:white;
		}
		/* text off bar */
		#progressbar div.text{
		position:absolute;
		width:100%;
		height:100%;
		text-align:center;
		font-size: 14pt;
		}
		
	.evidenziato{ 
		background-color: lightgrey; 
		padding:5px; 
		background-image: linear-gradient(45deg, #f0f0f0 5.56%, #ffffff 5.56%, #ffffff 50%, #f0f0f0 50%, #f0f0f0 55.56%, #ffffff 55.56%, #ffffff 100%);
		background-size: 12.73px 12.73px; 
		min-width: 250px; 
		border: 2px solid maroon; 
		font-size: 1.1em; 
		font-weight: bold	
	}
	
	 fieldset {
	    border: 1px solid #ced4da;
	    padding: 12px;
	    margin-bottom: 8px;
	    margin-top: 8px;
	}


	 fieldset > legend {
	    border: 1px solid #ced4da;
	    padding: 4px ;
	    text-transform: uppercase;
	    font-size: 1.2em;
	}
	
	fieldset > div {
	   padding: 4px;
	  
	}
	
	input,  select,  textarea, l {
	    padding: 6px 12px;
	    box-sizing: border-box;
	    font-size: 1em;
	    border: 1px solid  #ced4da;
	    margin-bottom: 2px;
	    vertical-align:middle
	}
	input[type=checkbox] {
	    min-width: auto;
	    padding: 0;
	    margin-left: 0;
	    height: 1.1em;
	}
	
	.action-btn {
	
		display: block;
		height: 30px;
		background-color: #EEEEEE;
		max-width: 600px;
		
	}

	.help {
		
		font-weight: bold;
		background-color: #e7f5e4;
		padding: 4px;
	}

	.help:before {
		content:url(${pageContext.request.contextPath}/images/help.gif);
		padding-right: 2px;
		vertical-align:middle
	}	
	
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload_iframe.js"></script>
	<script type="text/javascript">
		var running = false;
		var executionTimestamp = 0;
		var istanzeDaEleborare = 0;
		var istanzeElaborate = 0;
		var numeroErrori = 0;
		var refreshProcess;
		var progressBar;
		var resultsPage;
		var uploadCtrl;
		
		function preventSubmit(event){
			event.preventDefault();
			event.stopPropagation();
		}
		
		function startImport(event){
			//event.preventDefault();//impedisco il normale submit del form
			//event.stopPropagation();
			start('<%=request.getContextPath()%>/import/importaPraticheStart.htm', startImportCallback);
			return false;
		}
	
		function start(formActionUrl, callbackFunction){
			//alert("Evento submit annullato!!!");
			if(!running){
				var ts = new Date().getTime();	
				var dbPathInput = jQuery("#dbPathTxt");
				var dbZipFile = jQuery("#uploadText");
				var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
				var remoteDbChk = jQuery("#dbModeRemoteChk");
				var uploadDbChk = jQuery("#dbModeUploadChk");
				var userTxt = jQuery("#dbUserTxt");
				var passwordTxt = jQuery("#dbPasswordTxt");
				var errorMessage;
				if(remoteDbChk[0].checked == true){
					if(dbPathInput.val() == "" || dbPathInput.val() == undefined){
						errorMessage = '<fmt:message key="import.inputpage.missingdbpath.msg"/>';
					}
				}
				else{
					if(dbZipFile.val() == "" || dbZipFile.val() == undefined){
						errorMessage = '<fmt:message key="import.inputpage.missingdbzip.msg"/>';
					}
				}
				if(!errorMessage){
					uploadCtrl.setAction(formActionUrl);
					uploadCtrl.setOnComplete(callbackFunction);
					var sendData = {
						_ts: ts,
						dbPath: dbPathInput.val(),
						dbUser: userTxt.val(),
						dbPassword: passwordTxt.val(),
						idcomunealias: idComuneAliasHidden.val()
					};
					uploadCtrl.setData(sendData);
					running = true;
					uploadCtrl.submit();
					//displayProgressBar(true);
					/*
					jQuery.ajax({
						url: '<%=request.getContextPath()%>/import/importaPraticheStart.htm',
						type: 'POST',
						dataType: 'json',
						data: sendData,
						success: startImportCallback,
						error: handleAjaxError
					});
					*/
				}
				else{
					alert(errorMessage);
				}
			}
			else{
				alert('<fmt:message key="import.inputpage.alreadyrunning.msg"/>');
			}
		}
		
		function handleAjaxError(jqXHR, textStatus, errorThrown){
			var msg = "Errore";
			if(textStatus && textStatus != "error"){
				msg = msg + "(" + textStatus + ")";
			}
			if(errorThrown){
				msg += "\r\n" + errorThrown;
			}
			if(!msg || msg == ''){
				msg = "<fmt:message key="import.inputpage.error.generic.msg"/>";
			}
			//stopRefreshing();
			displayError(msg);
		}
		
		function startImportCallback(file, response){
			if(response){
				if(!response.error){
					executionTimestamp = response.importTimestamp;
					istanzeDaEleborare = response.importingInstances;
					istanzeElaborate = 0;
					var numInst = response.importedInstances;
					var numAnag = response.importedRegistries;
					var dbPath = response.dbPath;
					var dbPathInput = $("#dbPathTxt");
					dbPathInput.val(dbPath);
					setFormAfterUpload();
					if(numInst > 0 || numAnag > 0){
						var msg = "Attenzione: \r\n";
						if(numInst){
							msg += "<fmt:message key="import.inputpage.error.istanzeimportate.msg"/>\r\n";
						}
						if(numAnag){
							msg += "<fmt:message key="import.inputpage.error.anagrafeimp.msg"/>\r\n";
						}
						msg += "<fmt:message key="import.inputpage.confirm.proceed.msg"/>";
						if(confirm(msg)){
							runImport();
						}
					}
					else{
						runImport();
					}
				}
				else{
					running = false;
					alert(response.error);
				}
			}
			else{
				alert("<fmt:message key="import.inputpage.error.startimportfailed.msg"/>");
			}
		}
		
		function runImport(){
			running = true;
			displayProgressBar(true);
			resultsPage = "displayImportResults.htm";
			var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
			var dbPathTxt = jQuery("#dbPathTxt");
			var documentsRootPathTxt = jQuery("#documentsRootPathTxt");
			var oggettiPresentiInFileSystemChk = document.getElementById('oggettiPresentiInFileSystemChk');
			var autoGenerateDocumentiIstanzaChk = jQuery("#autoGenerateDocumentiIstanzaChk");
			var autoGenerateIstanzeOneriChk = jQuery("#autoGenerateIstanzeOneriChk");
			var autoGeneratePermIstanzeChk = jQuery("#autoGeneratePermIstanzeChk");
			var isertAnagrafeChk = jQuery("#isertAnagrafeChk");
			var updateAnagrafeChk = jQuery("#updateAnagrafeChk");
			var forzaInserimentoAnagrafeChk = jQuery("#forzaInserimentoAnagrafeChk");
			var escludiControlliSuAnagraficheDisabilitateChk = jQuery("#escludiControlliSuAnagraficheDisabilitateChk");
			var ricercaSoloCFPIChk = jQuery("#ricercaSoloCFPIChk");
			var escludiVerificaIncongruenzeChk = jQuery("#escludiVerificaIncongruenzeChk");
			var fascicolaIstanzaChk = jQuery("#fascicolaIstanzaChk");
			var protocollaIstanzaChk = jQuery("#protocollaIstanzaChk");
			var sendData = {
				dbPath: dbPathTxt.val(),
				idcomunealias: idComuneAliasHidden.val(),
				documentsRootPath: documentsRootPathTxt.val(),
				oggettiPresentiInFileSystem : oggettiPresentiInFileSystemChk.checked,
				autoGenerateDocumentiIstanza: (autoGenerateDocumentiIstanzaChk[0].checked ? true : false),
				autoGenerateIstanzeOneri: (autoGenerateIstanzeOneriChk[0].checked ? true : false),
				autoGeneratePermIstanze: (autoGeneratePermIstanzeChk[0].checked ? true : false),
				isertAnagrafe: (isertAnagrafeChk[0].checked ? true : false),
				updateAnagrafe: (updateAnagrafeChk[0].checked ? true : false),
				forzaInserimentoAnagrafe: (forzaInserimentoAnagrafeChk[0].checked ? true : false),
				escludiControlliSuAnagraficheDisabilitate: (escludiControlliSuAnagraficheDisabilitateChk[0].checked ? true : false),
				ricercaSoloCFPI: (ricercaSoloCFPIChk[0].checked ? true : false),
				escludiVerificaIncongruenze: (escludiVerificaIncongruenzeChk[0].checked ? true : false),
				importTimestamp: executionTimestamp,
				fascicolaIstanza: fascicolaIstanzaChk[0].checked ? true : false,
				protocollaIstanza: protocollaIstanzaChk[0].checked ? true : false
			};
			jQuery.ajax({
				url: '<%=request.getContextPath()%>/import/importaPraticheRun.htm',
				type: 'POST',
				dataType: 'json',
				data: sendData,
				success: runImportCallback,
				error: handleAjaxError
			});
			startRefreshing(refreshForImport);
		}
		
		function runImportCallback(data, textStatus, jqXHR){
			var msg = "";
			if(data){
				if(!data.error){
					msg = "<fmt:message key="import.inputpage.importend.msg"/>";
					msg += "\r\n" + data.processedCount + " <fmt:message key="import.inputpage.numprocessed.msg"/>";
					msg += "\r\n" + data.successCount + " <fmt:message key="import.inputpage.numimported.msg"/>";
					msg += "\r\n" + data.failureCount + " <fmt:message key="import.inputpage.numerrors.msg"/>";
					running = false;
					alert(msg);
				}
				else{
					msg = "<fmt:message key="import.inputpage.error.aborted.msg"/>\r\n" + data.error;
					istanzeElaborate = istanzeDaEleborare;
					displayStatus();
					alert(msg);
					goToResults();
				}
			}
			//goToResults();
		}
		
		function stopRefreshing(){
			if(running){
				clearInterval(refreshProcess);
				refreshProcess = undefined;
			}
			running = false;				
		}
		
		function startRefreshing(refreshFunction){
			displayProgressBar(true);
			refreshProcess = setInterval(refreshFunction, 5000);
		}
		
		function displayProgressBar(displayIt){
			if(displayIt == undefined)displayIt = true;
			var div = document.getElementById('progressbar');
			div.style.display = displayIt ? "block" : "none";			
		}
		
		function refreshForImport(){
			refresh('<%=request.getContextPath()%>/import/refreshImportStatus.htm');
		}
		
		function refreshForLink(){
			refresh('<%=request.getContextPath()%>/import/refreshLinkStatus.htm');
		}

		function refreshForSubentri(){
			refresh('<%=request.getContextPath()%>/import/refreshSubentriStatus.htm');
		}
		
		function refreshForMercati(){
			refresh('<%=request.getContextPath()%>/import/refreshMercatiStatus.htm');
		}
		
		function refresh(controllerUrl){
			var ts = new Date().getTime();	
			var sendData = {
				importTimestamp: executionTimestamp,
				dbPath: jQuery("#dbPathTxt").val()//,
				//idcomunealias: '${param.idcomunealias}'
			};
			jQuery.ajax({
				url: controllerUrl,
				type: "POST",
				dataType: 'json',
				data: sendData,
				success: refreshCallback,
				error: handleAjaxError
			});
		}
		
		function refreshCallback(data, textStatus, jqXHR){
			var respObj = data;
			istanzeElaborate = respObj.processedCount;
			if(! respObj.running || istanzeElaborate == istanzeDaEleborare){
				displayStatus();
				goToResults();
			}
			else{
				displayStatus();
			}			
		}
				
		function displayStatus(){
			if(progressBar){
				if(istanzeDaEleborare == 0){
					istanzeDaEleborare = 100;
				}
				progressBar.reportprogress(istanzeElaborate, istanzeDaEleborare);
			}
		}
		
		function goToAllResults(){
			if(!running){
				executionTimestamp = 0;
				var ts = new Date().getTime();	
				var dbPathInput = jQuery("#dbPathTxt");
				var dbUserInput = jQuery("#dbUserTxt");
				if(dbPathInput && dbPathInput.val() != "" && dbPathInput.val() != undefined){
					window.location.href = '<%=request.getContextPath()%>/import/displayImportResults.htm?importTimestamp='+executionTimestamp+'&dbPath='+dbPathInput.val()+'&dbUser='+dbUserInput.val()+'&idcomunealias=${idcomunealias}';
				}
				else{
					alert('<fmt:message key="import.inputpage.missingdbpath.msg"/>');
				}
			}
			else{
				alert('<fmt:message key="import.inputpage.alreadyrunning.msg"/>');
			}
		}
		
		function goToResults(){
			var _ts = new Date().getTime();	
			stopRefreshing();
			window.location.href = '<%=request.getContextPath()%>/import/' + resultsPage + '?importTimestamp='+executionTimestamp+'&dbPath='+jQuery("#dbPathTxt").val()+'&idcomunealias=${idcomunealias}';
		}
		
		function startLink(){
			start('<%=request.getContextPath()%>/import/collegaPraticheStart.htm', startLinkCallback);
		}
		
		function startLinkCallback(file, response){
			if(response){
				if(!response.error){
					executionTimestamp = response.importTimestamp;
					istanzeDaEleborare = response.importingInstances;
					var dbPath = response.dbPath;
					var dbPathInput = $("#dbPathTxt");
					dbPathInput.val(dbPath);
					setFormAfterUpload();
					runLink();
				}
				else{
					running = false;
					alert(response.error);
				}
			}
			else{
				alert("<fmt:message key="import.inputpage.error.startlinkfailed.msg"/>");
			}
		}
		
		function runLink(){
			running = true;
			displayProgressBar(true);
			resultsPage = "displayLinkResults.htm";
			var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
			var dbPathTxt = jQuery("#dbPathTxt");
			var sendData = {
				dbPath: dbPathTxt.val(),
				idcomunealias: idComuneAliasHidden.val(),
				importTimestamp: executionTimestamp
			};
			jQuery.ajax({
				url: '<%=request.getContextPath()%>/import/collegaPraticheRun.htm',
				type: 'POST',
				dataType: 'json',
				data: sendData,
				success: runLinkCallback,
				error: handleAjaxError
			});
			startRefreshing(refreshForLink);
		}
		
		function runLinkCallback(data, textStatus, jqXHR){
			var msg = "";
			if(data){
				if(!data.error){
					msg = "<fmt:message key="import.inputpage.linkend.msg"/>";
					msg += "\r\n" + data.processedCount + " <fmt:message key="import.inputpage.numlinkprocessed.msg"/>";
					msg += "\r\n" + data.successCount + " <fmt:message key="import.inputpage.numlinkimported.msg"/>";
					msg += "\r\n" + data.failureCount + " <fmt:message key="import.inputpage.numlinkerrors.msg"/>";
					running = false;
					alert(msg);
				}
				else{
					msg = "<fmt:message key="import.inputpage.error.aborted.msg"/>\r\n" + data.error;
					alert(msg);
					goToResults();
				}
			}
		}
		
		function startMercati(){
			start('<%=request.getContextPath()%>/import/importMercatiStart.htm', startMercatiCallback);
		}

		function startMercatiCallback(file, response){
			if(response){
				if(!response.error){
					executionTimestamp = response.importTimestamp;
					istanzeDaEleborare = response.importingMercati;
					istanzeElaborate = 0;
					var numSub = response.importedMercati;
					var dbPath = response.dbPath;
					var dbPathInput = $("#dbPathTxt");
					dbPathInput.val(dbPath);
					setFormAfterUpload();
					if(numSub > 0){
						var msg = "Attenzione: \r\n";
						msg += "<fmt:message key="import.inputpage.error.mercatidimportati.msg"/>\r\n";
						msg += "<fmt:message key="import.inputpage.confirm.proceed.msg"/>";
						if(confirm(msg)){
							runMercati();
						}
					}
					else{
						runMercati();
					}
				}
				else{
					running = false;
					alert(response.error);
				}
			}
			else{
				alert("<fmt:message key="import.inputpage.error.startimportfailed.msg"/>");
			}
		}
		
		function runMercati(){
			running = true;
			displayProgressBar(true);
			resultsPage = "displayMercatiResults.htm";
			var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
			var dbPathTxt = jQuery("#dbPathTxt");
			var sendData = {
				dbPath: dbPathTxt.val(),
				idcomunealias: idComuneAliasHidden.val(),
				importTimestamp: executionTimestamp
			};
			jQuery.ajax({
				url: '<%=request.getContextPath()%>/import/importMercatiRun.htm',
				type: 'POST',
				dataType: 'json',
				data: sendData,
				success: runSubentriCallback,
				error: handleAjaxError
			});
			startRefreshing(refreshForMercati);
		}
		
		function runMercatiCallback(data, textStatus, jqXHR){			
			var msg = "";
			if(data){
				if(!data.error){
					msg = "<fmt:message key="import.inputpage.importend.msg"/>";
					msg += "\r\n" + data.processedCount + " <fmt:message key="import.inputpage.nummercatidprocessed.msg"/>";
					msg += "\r\n" + data.successCount + " <fmt:message key="import.inputpage.nummercatidimported.msg"/>";
					msg += "\r\n" + data.failureCount + " <fmt:message key="import.inputpage.nummercatiderrors.msg"/>";
					running = false;
					alert(msg);
				}
				else{
					msg = "<fmt:message key="import.inputpage.error.aborted.msg"/>\r\n" + data.error;
					istanzeElaborate = istanzeDaEleborare;
					displayStatus();
					alert(msg);
					goToResults();
				}
			}
		}
		
		function startSubentri(){
			start('<%=request.getContextPath()%>/import/inserimentoSubentriStart.htm', startSubentriCallback);
		}

		function startSubentriCallback(file, response){
			if(response){
				if(!response.error){
					executionTimestamp = response.importTimestamp;
					istanzeDaEleborare = response.importingSubentri;
					istanzeElaborate = 0;
					var numSub = response.importedSubentri;
					var dbPath = response.dbPath;
					var dbPathInput = $("#dbPathTxt");
					dbPathInput.val(dbPath);
					setFormAfterUpload();
					if(numSub > 0){
						var msg = "Attenzione: \r\n";
						msg += "<fmt:message key="import.inputpage.error.subentriimportati.msg"/>\r\n";
						msg += "<fmt:message key="import.inputpage.confirm.proceed.msg"/>";
						if(confirm(msg)){
							runSubentri();
						}
					}
					else{
						runSubentri();
					}
				}
				else{
					running = false;
					alert(response.error);
				}
			}
			else{
				alert("<fmt:message key="import.inputpage.error.startimportfailed.msg"/>");
			}
		}
		
		function runSubentri(){
			running = true;
			displayProgressBar(true);
			resultsPage = "displaySubentriResults.htm";
			var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
			var dbPathTxt = jQuery("#dbPathTxt");
			var sendData = {
				dbPath: dbPathTxt.val(),
				idcomunealias: idComuneAliasHidden.val(),
				importTimestamp: executionTimestamp
			};
			jQuery.ajax({
				url: '<%=request.getContextPath()%>/import/inserimentoSubentriRun.htm',
				type: 'POST',
				dataType: 'json',
				data: sendData,
				success: runSubentriCallback,
				error: handleAjaxError
			});
			startRefreshing(refreshForSubentri);
		}
		
		function runSubentriCallback(data, textStatus, jqXHR){			
			var msg = "";
			if(data){
				if(!data.error){
					msg = "<fmt:message key="import.inputpage.importend.msg"/>";
					msg += "\r\n" + data.processedCount + " <fmt:message key="import.inputpage.numsubentriprocessed.msg"/>";
					msg += "\r\n" + data.successCount + " <fmt:message key="import.inputpage.numsubentriimported.msg"/>";
					msg += "\r\n" + data.failureCount + " <fmt:message key="import.inputpage.numsubentrierrors.msg"/>";
					running = false;
					alert(msg);
				}
				else{
					msg = "<fmt:message key="import.inputpage.error.aborted.msg"/>\r\n" + data.error;
					istanzeElaborate = istanzeDaEleborare;
					displayStatus();
					alert(msg);
					goToResults();
				}
			}
		}
		
		
		function setFormAfterUpload(){
			$("#uploadText").val("");
			$("#dbModeRemoteChk").click();
		}

		(function($) {	
			//Main Method
			$.fn.reportprogress = function(val,maxVal) {			
				var max=100;
				if(maxVal)
					max=maxVal;
				return this.each(
					function(){		
						var div=$(this);
						var innerdiv=div.find(".progress");
						
						if(innerdiv.length!=1){						
							innerdiv=$("<div class='progress'></div>");					
							div.append("<div class='text'>&nbsp;</div>");
							$("<span class='text'>&nbsp;</span>").css("width",div.width()).appendTo(innerdiv);					
							div.append(innerdiv);					
						}
						var width=Math.round(val/max*100);
						innerdiv.css("width",width+"%");	
						div.find(".text").html(width+" %");
					}
				);
			};
		})(jQuery);		
		
		jQuery(document).ready(function(){
			initImportPage();
		});
		
		function initImportPage(){
			if($("#progressbar").reportprogress != undefined){
				progressBar = $("#progressbar");
				progressBar.reportprogress(0);
			}
			$("#dbModeUploadChk").click(toggleDBUpload);
			$("#dbModeRemoteChk").click(toggleDBUpload);
			uploadCtrl = new AjaxUpload('#uploadDbButton', {
				action: '../import/importaPraticheStart.htm',
				name: 'importFile',
				responseType: 'json',
				autoSubmit: false,
				submitIfEmpty: true,
				onChange: displayFile,
				//onSubmit: startPolling,
				onComplete: startImportCallback 
			});
			$("#launchImportForm").submit(preventSubmit);
			var toCheck = $("#dbModeRemoteChk");
			if(!toCheck[0].checked){
				toCheck = $("#dbModeUploadChk");
			}
			toCheck.click();
		}
		
		function displayFile(file , ext){
			var pDisplay = document.getElementById('uploadText');
			pDisplay.value = file;
		}

		function toggleDBUpload(){
			var clickElement = $(this);
			if(clickElement.attr("id") == "dbModeRemoteChk"){
				$("#dbPathTxt").attr("readonly",false);
				$("#dbPathTxt").css("background-color","white");
				uploadCtrl.disable();
				$("#uploadText").attr("readonly",true);
				$("#uploadText").css("background-color","lightGray");
			}
			else{
				$("#dbPathTxt").attr("readonly",true);
				$("#dbPathTxt").css("background-color","lightGray");
				uploadCtrl.enable();
				$("#uploadText").attr("readonly",false);
				$("#uploadText").css("background-color","white");
			}
		}
		
		var refreshErrors = [];
		var maxErrors = 100;
		
		function displayError(errMsg){
			var statusBar = $('#statusbar');
			statusBar.show();
			var errorList = $('#errorlist');
			//statusBar.fadeOut();
			errorList.empty();
			errMsg = new Date().toLocaleString() + ": " + errMsg;
			var errCount = refreshErrors.push(errMsg);
			if(errCount > maxErrors){
				refreshErrors.shift();
			}
			for ( i = 0; i < refreshErrors.length; i++) {
				errorList.append('<li class="error">' + refreshErrors[i] + '</li>');
			}
			//statusBar.fadeIn();
		}
		
		function downloadZippedDerby(){
			window.location.href = '<%=request.getContextPath()%>/import/downloadLastImportedDB.htm';
		}
		
		function abort(){
			if(!running){
				alert("<fmt:message key="import.inputpage.error.notrunning.msg"/>");
			}
			else{
				//window.location.href = '<%=request.getContextPath()%>/import/abortImport.htm';
				jQuery.ajax({
					url: '<%=request.getContextPath()%>/import/abortImport.htm',
					type: 'POST',
					dataType: 'json',
					success: abortCallback,
					error: handleAjaxError
				});
			}
		}
		
		function abortCallback(data, textStatus, jqXHR){
			if(data && data.msg){
				alert(data.msg);
			}
		}
		
		
	</script>
	<span class="titoloPagina">
		<fmt:message key="import.inputpage.title"/>
	</span>
	<spring-form:form commandName="parametriImport" name="launchImport" id="launchImportForm" enctype="multipart form-data">
		<div><fmt:message key="import.inputpage.msg" /></div>
		<div style="display:inline-table; position: relative; left: 4px;">
			<input type="hidden" name="idcomunealias" value="${param.idcomunealias}" id="idComuneAliasHidden"/>
			
			<fieldset>
				<legend><fmt:message key="import.inputpage.database" /></legend>

					<div>
						<spring-form:radiobutton path="dbMode" id="dbModeRemoteChk" value="<%=ImportParameters.DB_MODE_REMOTE %>"/>
						<label for="dbModeRemoteChk"><b><fmt:message key='import.inputpage.dbmoderemote.label' /></b></label>
					
						<spring-form:input path="dbPath" id="dbPathTxt" cssStyle="width: 300px; border-style:dotted; border-width: thin; border-color: #999999;"/>
						<span class="help"><fmt:message key="import.inputpage.dbpath.label" /></span> 
					<div>
					
					<div>
						<spring-form:radiobutton path="dbMode" id="dbModeUploadChk" value="<%=ImportParameters.DB_MODE_UPLOAD %>"/>
						<label for="dbModeUploadChk"><b><fmt:message key="import.inputpage.dbmodeupload.label" /></b></label>
						<span id="uploadDbButton" class="upload">
							<img src="${pageContext.request.contextPath}/images/add.png" border="0" alt="<fmt:message key="import.inputpage.dbuploadzip.alt" />"/>&nbsp;&nbsp;
							<input type="text" name="db_file_view" id="uploadText" class="upload" readonly="readonly" style="border-style:dotted; border-width: thin; border-color: #999999; ; width: 270px;"/>
						</span>
						<span class="help">
							<fmt:message key="import.inputpage.dbuploadzip.label" />
						</span>
				</div>
								
				<div style="margin-top: 10px;">
					<spring-form:input path="dbUser" id="dbUserTxt"/>
					<b><fmt:message key="import.inputpage.dbuser.label" /></b>
				</div>
				<div>
					<spring-form:input path="dbPassword" id="dbPasswordTxt"/>
					<b><fmt:message key="import.inputpage.dbpassword.label" /></b>				
				</div>
				
					
			</fieldset>
			
			

			<fieldset>
				<legend><fmt:message key="import.inputpage.documenti_da_importare" /></legend>
				<div>
					<spring-form:input path="documentsRootPath" id="documentsRootPathTxt" size="50"/>
					<label for="documentsRootPathTxt"><b><fmt:message key="import.inputpage.documentsrootpath.label" /></b></label>
				</div>
				<div class="evidenziato">
					<spring-form:checkbox path="oggettiPresentiInFileSystem" id="oggettiPresentiInFileSystemChk" value="true"/>
					<label for="oggettiPresentiInFileSystemChk"><b><fmt:message key="import.inputpage.documentsrootpath.documenti-presenti-in-file-system" /></b></label>
				</div>
			</fieldset>
			
			
			<fieldset>
				<legend><fmt:message key="import.inputpage.altri_parametri" /></legend>
			
					<div>
						<spring-form:checkbox path="autoGenerateDocumentiIstanza" id="autoGenerateDocumentiIstanzaChk" value="true"/>
						<label for="autoGenerateDocumentiIstanzaChk"><fmt:message key="import.inputpage.autogeneratedocumentiistanza.label" /></label>
					</div>
					<div>
					<spring-form:checkbox path="autoGenerateModellidinamici" id="autoGenerateModellidinamiciChk" value="true"/>
					<label for="autoGenerateModellidinamiciChk"><fmt:message key="import.inputpage.autogeneratemodellidinamici.label" /></label>			
					</div>
					<div>
					<spring-form:checkbox path="autoGenerateIstanzeOneri" id="autoGenerateIstanzeOneriChk" value="true"/>
					<label for="autoGenerateIstanzeOneriChk"><fmt:message key="import.inputpage.autogenerateistanzeoneri.label" /></label>
					</div>
					<div>
					<spring-form:checkbox path="autoGeneratePermIstanze" id="autoGeneratePermIstanzeChk" value="true"/>
					<label for="autoGeneratePermIstanzeChk"><fmt:message key="import.inputpage.autogeneratepermessiistanze.label" /></label>
					</div>
					<div>
					<spring-form:checkbox path="isertAnagrafe" id="isertAnagrafeChk" value="true"/>
					<label for="isertAnagrafeChk"><fmt:message key="import.inputpage.isertanagrafe.label" /></label>
					</div>
					<div>
					<spring-form:checkbox path="updateAnagrafe" id="updateAnagrafeChk" value="true"/>
					<label for="updateAnagrafeChk"><fmt:message key="import.inputpage.updateanagrafe.label" /></label>
					</div>
					<div>

					<spring-form:checkbox path="forzaInserimentoAnagrafe" id="forzaInserimentoAnagrafeChk" value="true"/>
					<label for="forzaInserimentoAnagrafeChk"><fmt:message key="import.inputpage.forzainserimentoanagrafe.label" /></label>
					</div>
					<div>

					<spring-form:checkbox path="escludiControlliSuAnagraficheDisabilitate" id="escludiControlliSuAnagraficheDisabilitateChk" value="true"/>
					<label for="escludiControlliSuAnagraficheDisabilitateChk"><fmt:message key="import.inputpage.autogeneratedocumentiistanza.label" /></label>
					</div>
					<div>

					<spring-form:checkbox path="ricercaSoloCFPI" id="ricercaSoloCFPIChk" value="true"/>
					<label for="ricercaSoloCFPIChk"><fmt:message key="import.inputpage.escludicontrollisuanagrafichedisabilitate.label" /></label>
					</div>
					<div>

					<spring-form:checkbox path="escludiVerificaIncongruenze" id="escludiVerificaIncongruenzeChk" value="true"/>
					<label for="escludiVerificaIncongruenzeChk"><fmt:message key="import.inputpage.escludiverificaincongruenze.label" /></label>
					</div>
					<div>

					<spring-form:checkbox path="protocollaIstanza" id="protocollaIstanzaChk" value="true"/>
					<label for="protocollaIstanzaChk"><fmt:message key="import.inputpage.protocollaistanza.label" /></label>
					</div>
					<div>

					<spring-form:checkbox path="fascicolaIstanza" id="fascicolaIstanzaChk" value="true"/>
					<label for="fascicolaIstanzaChk"><fmt:message key="import.inputpage.fascicolaistanza.label" /></label>
					</div>


			</fieldset>
			<fieldset>
				<legend><fmt:message key="import.inputpage.funzioni" /></legend>
					<span id="startImportBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="startImport();">			
						<span id="startImportButton">
							<input type="image" src="${pageContext.request.contextPath}/images/table/nextPage.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.inputpage.launchimport.label"/></b>
						</span>
					</span>		
					<br />
					<span id="startMercatiBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="startMercati();">	
						<span id="startMercatiButton">
							<input type="image" src="${pageContext.request.contextPath}/images/lettera_m.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.inputpage.inserimentoMercati.label"/></b>
						</span>
					</span>		
					<br />
					<span id="startSubentriBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="startSubentri();">	
						<span id="startSubentriButton">
							<input type="image" src="${pageContext.request.contextPath}/images/lettera_s.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.inputpage.inserimentoSubentri.label"/></b>
						</span>
					</span>		
					<br />
					<span id="startLinkBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="startLink();">	
						<span id="startLinkButton">
							<input type="image" src="${pageContext.request.contextPath}/images/table/clear.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.inputpage.launchlink.label"/></b>
						</span>
					</span>		
					<br />
					<span id="abortBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="abort();">	
						<span id="abortButton">
							<input type="image" src="${pageContext.request.contextPath}/images/cross.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.inputpage.abort.label"/></b>
						</span>
					</span>		
					<br />
					<span id="displayResultsBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="goToAllResults();">			
						<span id="displayResultsButton">
							<input type="image" src="${pageContext.request.contextPath}/images/table/csv.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.inputpage.displayerrors.label"/></b>
						</span>
					</span>
					<br />
					<span id="downloadZipBtn" class="upload action-btn" style="border: medium; border-color: black; cursor: pointer;" onclick="downloadZippedDerby();">			
						<span id="downloadZipButton">
							<input type="image" src="${pageContext.request.contextPath}/images/download.gif"/>&nbsp;&nbsp;
							<b><fmt:message key="import.outputpage.downloadderby.label"/></b>
						</span>
					</span>	
				</fieldset>						
		</div>
	</spring-form:form>
	<br />
	<div id="progressbar" style="display: none; width: 400px; height: 35px;">
	</div>
	<br />
	<div id="statusbar" class="error" style="font-size: small; display: none; height: 350px; overflow-y: auto;">
		<fmt:message key="import.inputpage.refresherrors.label" />:<br />
		<ul id="errorlist">
		</ul>
	</div>
	
</body>
</html>