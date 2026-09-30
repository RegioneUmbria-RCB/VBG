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
		
	</style>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload.js"></script>
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
			<table>
				<tr>
					<td width="50%">
						<spring-form:radiobutton path="dbMode" id="dbModeRemoteChk" value="<%=ImportParameters.DB_MODE_REMOTE %>"/>
						<b><fmt:message key='import.inputpage.dbmoderemote.label' /></b>
					<%--
					</td>
					<td width="30%">
					 --%>
						<spring-form:input path="dbPath" id="dbPathTxt" cssStyle="width: 300px; border-style:dotted; border-width: thin; border-color: #999999;"/>
					</td>
					<td width="50%">
						<fmt:message key="import.inputpage.dbpath.label" /> 
					</td>
				</tr>
				<tr>
					<td width="50%">
						<spring-form:radiobutton path="dbMode" id="dbModeUploadChk" value="<%=ImportParameters.DB_MODE_UPLOAD %>"/>
						<b><fmt:message key="import.inputpage.dbmodeupload.label" /></b>
					<%--
					</td>
					<td width="30%">
					 --%>
						<span id="uploadDbButton" class="upload">
						<img src="${pageContext.request.contextPath}/images/add.gif" border="0" alt="<fmt:message key="import.inputpage.dbuploadzip.alt" />"/>&nbsp;&nbsp;
						<input type="text" name="db_file_view" id="uploadText" class="upload" readonly="readonly" style="border-style:dotted; border-width: thin; border-color: #999999; ; width: 270px;"/>
					</td>
					<td width="50%">
						<fmt:message key="import.inputpage.dbuploadzip.label" />
					</td>
				</tr>
			</table>
			<%-- 
			<input type="file"" id="dbZipFile"/>
			--%>
			<%--
			<fmt:message key="upgr.upgrade.input.upload.file.format" />
			 --%>
			
			<spring-form:input path="dbUser" id="dbUserTxt"/>
			<b><fmt:message key="import.inputpage.dbuser.label" /></b>
			<br />
			<spring-form:input path="dbPassword" id="dbPasswordTxt"/>
			<b><fmt:message key="import.inputpage.dbpassword.label" /></b>
			<br />
			<spring-form:input path="documentsRootPath" id="documentsRootPathTxt"/>
			<b><fmt:message key="import.inputpage.documentsrootpath.label" /></b>
			<br />
			<spring-form:checkbox path="autoGenerateDocumentiIstanza" id="autoGenerateDocumentiIstanzaChk" value="true"/>
			<b><fmt:message key="import.inputpage.autogeneratedocumentiistanza.label" /></b>
			<br />
			<spring-form:checkbox path="autoGenerateModellidinamici" id="autoGenerateModellidinamiciChk" value="true"/>
			<b><fmt:message key="import.inputpage.autogeneratemodellidinamici.label" /></b>			
			<br />
			<spring-form:checkbox path="autoGenerateIstanzeOneri" id="autoGenerateIstanzeOneriChk" value="true"/>
			<b><fmt:message key="import.inputpage.autogenerateistanzeoneri.label" /></b>
			<br />
			<spring-form:checkbox path="autoGeneratePermIstanze" id="autoGeneratePermIstanzeChk" value="true"/>
			<b><fmt:message key="import.inputpage.autogeneratepermessiistanze.label" /></b>
			<br />
			<spring-form:checkbox path="isertAnagrafe" id="isertAnagrafeChk" value="true"/>
			<b><fmt:message key="import.inputpage.isertanagrafe.label" /></b>
			<br />
			<spring-form:checkbox path="updateAnagrafe" id="updateAnagrafeChk" value="true"/>
			<b><fmt:message key="import.inputpage.updateanagrafe.label" /></b>
			<br />
			<spring-form:checkbox path="forzaInserimentoAnagrafe" id="forzaInserimentoAnagrafeChk" value="true"/>
			<b><fmt:message key="import.inputpage.forzainserimentoanagrafe.label" /></b>
			<br />
			<spring-form:checkbox path="escludiControlliSuAnagraficheDisabilitate" id="escludiControlliSuAnagraficheDisabilitateChk" value="true"/>
			<b><fmt:message key="import.inputpage.autogeneratedocumentiistanza.label" /></b>
			<br />
			<spring-form:checkbox path="ricercaSoloCFPI" id="ricercaSoloCFPIChk" value="true"/>
			<b><fmt:message key="import.inputpage.escludicontrollisuanagrafichedisabilitate.label" /></b>
			<br />
			<spring-form:checkbox path="escludiVerificaIncongruenze" id="escludiVerificaIncongruenzeChk" value="true"/>
			<b><fmt:message key="import.inputpage.escludiverificaincongruenze.label" /></b>
			<br />
			<spring-form:checkbox path="protocollaIstanza" id="protocollaIstanzaChk" value="true"/>
			<b><fmt:message key="import.inputpage.protocollaistanza.label" /></b>
			<br />
			<spring-form:checkbox path="fascicolaIstanza" id="fascicolaIstanzaChk" value="true"/>
			<b><fmt:message key="import.inputpage.fascicolaistanza.label" /></b>
			<br />
			
			<span id="startImportBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="startImport();">			
				<span id="startImportButton">
					<input type="image" src="${pageContext.request.contextPath}/images/table/nextPage.gif"/>&nbsp;&nbsp;
					<b><fmt:message key="import.inputpage.launchimport.label"/></b>
				</span>
			</span>		
			<br />
			<span id="startLinkBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="startLink();">	
				<span id="startLinkButton">
					<input type="image" src="${pageContext.request.contextPath}/images/table/clear.gif"/>&nbsp;&nbsp;
					<b><fmt:message key="import.inputpage.launchlink.label"/></b>
				</span>
			</span>		
			<br />
			<span id="abortBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="abort();">	
				<span id="abortButton">
					<input type="image" src="${pageContext.request.contextPath}/images/cross.gif"/>&nbsp;&nbsp;
					<b><fmt:message key="import.inputpage.abort.label"/></b>
				</span>
			</span>		
			<br />
			<span id="displayResultsBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="goToAllResults();">			
				<span id="displayResultsButton">
					<input type="image" src="${pageContext.request.contextPath}/images/table/csv.gif"/>&nbsp;&nbsp;
					<b><fmt:message key="import.inputpage.displayerrors.label"/></b>
				</span>
			</span>
			<br />
			<span id="downloadZipBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="downloadZippedDerby();">			
				<span id="downloadZipButton">
					<input type="image" src="${pageContext.request.contextPath}/images/download.gif"/>&nbsp;&nbsp;
					<b><fmt:message key="import.outputpage.downloadderby.label"/></b>
				</span>
			</span>						
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