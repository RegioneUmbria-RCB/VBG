<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.dataimport.web.helper.DeleteTableProcedureInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/import-taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title><fmt:message key="deletetable.inputpage.title" /></title>
</head>
<body class="center" >
	<style type="text/css">
	
		.center {
			text-align: center;
			
		}
		
		.table-to-delete{
			font-style: italic;
		}
		.table-deleted{
			font-style: normal;
			color: blue;
		}
		#loader-wrapper {
			/*
		    position: fixed;
		    top: 0;
		    left: 0;
		    height: 100%;
		    */
		    width: 100%;
		    z-index: 1000;
		}
		#loader {
		    display: block;
		    position: relative;
		    /*left: 50%;*/
		    top: 50%;
		    width: 150px;
		    height: 150px;
		    /*margin: -75px 0 0 -75px;*/
		    border-radius: 50%;
		    border: 3px solid transparent;
		    border-top-color: #3498db;
		    -webkit-animation: spin 2s linear infinite; /* Chrome, Opera 15+, Safari 5+ */
		    animation: spin 2s linear infinite; /* Chrome, Firefox 16+, IE 10+, Opera */
		}
		 
		#loader:before {
		    content: "";
		    position: absolute;
		    top: 5px;
		    left: 5px;
		    right: 5px;
		    bottom: 5px;
		    border-radius: 50%;
		    border: 3px solid transparent;
		    border-top-color: #e74c3c;
		    -webkit-animation: spin 3s linear infinite; /* Chrome, Opera 15+, Safari 5+ */
		      animation: spin 3s linear infinite; /* Chrome, Firefox 16+, IE 10+, Opera */
		}
		 
		#loader:after {
		    content: "";
		    position: absolute;
		    top: 15px;
		    left: 15px;
		    right: 15px;
		    bottom: 15px;
		    border-radius: 50%;
		    border: 3px solid transparent;
		    border-top-color: #f9c922;
		    -webkit-animation: spin 1.5s linear infinite; /* Chrome, Opera 15+, Safari 5+ */
		      animation: spin 1.5s linear infinite; /* Chrome, Firefox 16+, IE 10+, Opera */
		}
		 
		@-webkit-keyframes spin {
		    0%   {
		        -webkit-transform: rotate(0deg);  /* Chrome, Opera 15+, Safari 3.1+ */
		        -ms-transform: rotate(0deg);  /* IE 9 */
		        transform: rotate(0deg);  /* Firefox 16+, IE 10+, Opera */
		    }
		    100% {
		        -webkit-transform: rotate(360deg);  /* Chrome, Opera 15+, Safari 3.1+ */
		        -ms-transform: rotate(360deg);  /* IE 9 */
		        transform: rotate(360deg);  /* Firefox 16+, IE 10+, Opera */
		    }
		}
		@keyframes spin {
		    0%   {
		        -webkit-transform: rotate(0deg);  /* Chrome, Opera 15+, Safari 3.1+ */
		        -ms-transform: rotate(0deg);  /* IE 9 */
		        transform: rotate(0deg);  /* Firefox 16+, IE 10+, Opera */
		    }
		    100% {
		        -webkit-transform: rotate(360deg);  /* Chrome, Opera 15+, Safari 3.1+ */
		        -ms-transform: rotate(360deg);  /* IE 9 */
		        transform: rotate(360deg);  /* Firefox 16+, IE 10+, Opera */
		    }
		}
	</style>
	<script type="text/javascript">
		var running = false;
		var tabelleDaCancellare = 0;
		var tabelleCancellate = 0;
		var numeroErrori = 0;
		var refreshProcess;
		var progressBar;
		var gotMetadata = false;
		
		function preventSubmit(event){
			event.preventDefault();
			event.stopPropagation();
		}
		
		function startDeleteTable(event){
			//event.preventDefault();//impedisco il normale submit del form
			//event.stopPropagation();
			if(!running){
				var ts = new Date().getTime();	
				var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
				var tabName = jQuery("#tableNameTxt").val();
				var schema = jQuery("#schemaTxt").val();
				var errorMessage;
				if(tabName == "" || tabName == undefined){
					errorMessage = '<fmt:message key="deletetable.inputpage.missingtablename.msg"/>';
				}
				if(schema == "" || schema == undefined){
					errorMessage = '<fmt:message key="deletetable.inputpage.missingschema.msg"/>';
				}
				if(!errorMessage){
					el = $('#btn-confirm-delete');
					el.hide();
					var sendData = jQuery('#launchDeleteForm').serialize();
					running = true;
					displayWaitMessage();
					jQuery.ajax({
						url: '<%=request.getContextPath()%>/import/deleteTableStart.htm',
						type: 'POST',
						dataType: 'json',
						data: sendData,
						success: startDeleteTableCallback,
						error: function(jqXHR, textStatus, errorThrown){
							handleAjaxError(jqXHR, textStatus, errorThrown);
							running = false;
						}
					});
				}
				else{
					showMessage(errorMessage);
				}
			}
			else{
				showMessage('<fmt:message key="import.inputpage.alreadyrunning.msg"/>');
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

		//callback ricezione metadati sulle tabelle da cancellare. chiede conferma all'utente e lancia la procedura vera e propria
		function startDeleteTableCallback(response, textStatus) {
			running = false;
			if (response) {
				if (!response.error) {
					gotMetadata = true;
					tabelleDaCancellare = response.tabelle;
					var el = $('#lbl-todelete');
					el.show();
					el = $('#lbl-deleted');
					el.hide();
					el = $('#btn-confirm-delete');
					var lbl = "<fmt:message key='deletetable.inputpage.confirm.msg' />";
					el.val(lbl);
					el.text(lbl);
					el.show();
					el = $('#btn-process-metadata');
					//el.hide();
					displayTablesStatus(response.tabelle);
				} else {
					showMessage(response.error);
				}
			} else {
				showMessage("<fmt:message key="deletetable.inputpage.error.startdeletefailed.msg"/>");
			}
		}

		function runDeleteTable() {
			running = true;
			jQuery.ajax({
						url : '<%=request.getContextPath()%>/import/deleteTableRun.htm',
				type: 'POST',
				dataType: 'json',
				success: runDeleteTableCallback,
				error: function(jqXHR, textStatus, errorThrown){
					handleAjaxError(jqXHR, textStatus, errorThrown);
					stopRefreshing();
				}
			});
			//disattivo il bottone che avvia l'analisi delle tabelle dipendenti
			var btn = $('#btn-process-metadata');
			btn.attr('disabled',true);
			//disattivo il bottone che lancia la cancellazione
			btn = $('#btn-confirm-delete');
			btn.attr('disabled',true);
			//ne cambio l'etichetta
			var msg = "<fmt:message key='deletetable.inputpage.deleterunning.lbl' ></fmt:message>";
			btn.val(msg);
			btn.data('old-text', btn.text());
			btn.text(msg);
			//avvio il refresh automatico dello stato di avanzamento
			startRefreshing(refreshForDelete);
		}
		
		function runDeleteTableCallback(data, textStatus, jqXHR){
			var msg = "";
			var lbl = "";
			running = false;
			if(data){
				if(!data.error){
					msg = "<fmt:message key='deletetable.inputpage.deleteend.msg' ></fmt:message>";
					lbl = "<fmt:message key='deletetable.inputpage.deleteend.label' ></fmt:message>";
					if(data.tabelle != undefined){
						displayTablesStatus(data.tabelle)
					}
				}
				else{
					msg = "<fmt:message key='deletetable.inputpage.deleteerror.msg'/>\r\n" + data.error;
					lbl = "<fmt:message key='deletetable.inputpage.deletestopped.lbl' ></fmt:message>"
				}
				stopRefreshing();
				var btn = $('#btn-confirm-delete');
				btn.val(lbl);
				btn.text(lbl);
				showMessage(msg);
			}
		}
		
		function stopRefreshing(){
			if(refreshProcess != undefined){
				clearInterval(refreshProcess);
				refreshProcess = undefined;
			}
			var btn = $('#btn-process-metadata');
			btn.attr('disabled',false);
			running = false;				
		}
		
		function startRefreshing(refreshFunction){
			refreshProcess = setInterval(refreshFunction, 3000);
		}
		
		function refreshForDelete(){
			var ts = new Date().getTime();	
			jQuery.ajax({
				url: '<%=request.getContextPath()%>/import/refreshDeleteStatus.htm',
				type: "GET",
				dataType: 'json',
				success: refreshCallback,
				error: handleAjaxError
			});
		}
		
		function refreshCallback(data, textStatus, jqXHR){
			if(! data.error){
				displayTablesStatus(data.tabelle)
			}
			else{
				displayError(data.error);
			}			
		}
				
		jQuery(document).ready(function(){
			initDeleteTablesPage();
		});
		
		function initDeleteTablesPage(){
			$("#launchDeleteForm").submit(preventSubmit);
		}
		
		var refreshErrors = [];
		var maxErrors = 200;
		
		function displayError(errMsg){
			var statusBar = $('#errorsdiv');
			statusBar.show();
			var errorList = $('#errorlist');
			//statusBar.fadeOut();
			errorList.empty();
			errMsg = new Date().toLocaleString() + ": " + errMsg;
			var errCount = refreshErrors.push(errMsg);
			if(errCount >= maxErrors){
				stopRefreshing();
				refreshErrors.shift();
			}
			for ( i = 0; i < refreshErrors.length; i++) {
				errorList.append('<li class="error">' + refreshErrors[i] + '</li>');
			}
			//statusBar.fadeIn();
		}

		function showMessage(msg){
			alert(msg);
		}
		
		function displayTablesStatus(tablesData){
			displayWaitMessage(tablesData == false);
			var statusBar = $('#output-div');
			var tabList = $('#tablelist');
			//statusBar.fadeOut();
			tabList.empty();
			var completed = true;
			if( jQuery.isArray(tablesData)){
				//statusBar.show();
				for ( i = 0; i < tablesData.length; i++) {
					var rowClass = "table-to-delete";
					var status = "da cancellare";
					var strikeStart = "";
					var strikeEnd = "";
					if(tablesData[i].deletedRowsCount != undefined){
						rowClass = "table-deleted";
						status = "<b>" + tablesData[i].deletedRowsCount + " righe cancellate</b>";
						strikeStart = "<strike>";
						strikeEnd = "</strike>";
					}
					else{
						completed = false;
					}
					var htmlRow = '<tr><td class="' + rowClass + '">' + strikeStart +  tablesData[i].tableName + strikeEnd + '</td><td>' + status + '</td></tr>';
					tabList.append(htmlRow);
				}
				if(completed){
					stopRefreshing()
				}
			}
			/*
			else if(tablesData === false){
				statusBar.hide();
			}
			*/
		}

		function displayWaitMessage(show){
			if(show === false){
				$('#loader-wrapper').hide();
				$('#output-div').show();
			}
			else{
				$('#loader-wrapper').show();
				$('#output-div').hide();
			}
		}

		/*
		function abort(){
			if(!running){
				showMessage("<fmt:message key="import.inputpage.error.notrunning.msg"/>");
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
				showMessage(data.msg);
			}
		}
		*/
		
		
	</script>
	<div align="center">
		<div class="titoloPagina" style="margin-top: 20px; margin-bottom: 12px;">
			<fmt:message key="deletetable.inputpage.title"/>
		</div>
		<spring-form:form commandName="parametriDelete" name="launchDelete" id="launchDeleteForm">
			<div>
				<p><fmt:message key="deletetable.inputpage.msg" /><br /><fmt:message key="deletetable.inputpage.msg2" /></p>
			</div>
			<div></div>
			<div style="display:inline-table; padding-left: 4px;">
				<input type="hidden" name="idcomunealias" value="${param.idcomunealias}" id="idComuneAliasHidden"/>
				<table>
					<tr>
						<td width="200px">
							<fmt:message key='deletetable.inputpage.schema.label' />
						</td>
						<td>
							<spring-form:input path="schema" id="schemaTxt" cssStyle="width: 300px; border-style:dotted; border-width: thin; border-color: #999999;" readonly="true"/> 
						</td>
					</tr>
					<tr>
						<td width="200px">
							<fmt:message key='deletetable.inputpage.tablename.label' />
						</td>
						<td>
							<spring-form:input path="tableToDelete.tableName" id="tableNameTxt" cssStyle="width: 300px; border-style:dotted; border-width: thin; border-color: #999999;"/> 
						</td>
					</tr>		
				</table>
				<div style="margin-top: 12px;">
					<button id="btn-process-metadata" type="button" value='<fmt:message key="deletetable.inputpage.launch.label" />' onclick="startDeleteTable()">
						<fmt:message key="deletetable.inputpage.launch.label" />
					</button>
					<button id="btn-confirm-delete" type="button" value='<fmt:message key="deletetable.inputpage.confirm.msg" />' onclick="runDeleteTable()" style="display: none;">
						<fmt:message key="deletetable.inputpage.confirm.msg" />
					</button>
				</div>
				<!-- 
				<span id="startDeleteTableBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="startDeleteTable();">			
					<span>
						<input type="image" src="${pageContext.request.contextPath}/images/table/nextPage.gif"/>&nbsp;&nbsp;
						<b><fmt:message key="deletetable.inputpage.launch.label"/></b>
						<strike></strike>
					</span>
				</span>		
				<br />
				<span id="abortBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="abort();">	
					<span id="abortButton">
						<input type="image" src="${pageContext.request.contextPath}/images/cross.gif"/>&nbsp;&nbsp;
						<b><fmt:message key="deletetable.inputpage.abort.label"/></b>
					</span>
				</span>
				 -->		
			</div>
		</spring-form:form>
		<br />
		<div id="loader-wrapper" style="font-size: 12px; display: none;">
			<p><fmt:message key="deletetable.inputpage.waitrunning.label"/></p>
		    <div id="loader">
		    </div>
		</div>
		<div id="output-div" style="display: none; width: 600px; padding-left: 4px; padding-top: 12px;">
			<div>
				<span id="lbl-todelete">
					<fmt:message key="deletetable.inputpage.tablestodelete.label" />:<br />
				</span>
				<span id="lbl-deleted">
					<fmt:message key="deletetable.inputpage.tablesdeleted.label" />:<br />
				</span>
			</div>
			<table style="width: 480px;">
				<thead>
					<tr>
						<th align="left">Tabella</th>
						<th align="left">Stato</th>
					</tr>
				</thead>
				<tbody id="tablelist"></tbody>
			</table>
		</div>
		<br />
		<div id="errorsdiv" class="error" style="font-size: small; display: none; overflow-y: auto;">
			<fmt:message key="import.inputpage.refresherrors.label" />:<br />
			<ul id="errorlist">
			</ul>
		</div>
	</div>
</body>
</html>