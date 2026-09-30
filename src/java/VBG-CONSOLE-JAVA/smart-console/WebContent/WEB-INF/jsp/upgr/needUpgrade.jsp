<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/upgr-taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../includes/upgr-js-css.jsp"%>
<title><fmt:message key="upgr.upgrade.input.title" /></title>
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
		<script type="text/javascript">
			//dojo.require("dijit.ProgressBar");
			var tot_tasks = 0;
			var executed_tasks = 0;
			var refreshProcess;
			var uploadCtrl;
			var progressBar;
			var running = false;
	
			function displayStatus(){
				var displayVal = 0;
				if(tot_tasks > 0){
					var percentVal = executed_tasks/tot_tasks * 100;
					displayVal = Math.round(percentVal*100)/100;
				}
				/*
				var progress = dijit.byId('status_div');
				if(progress){
					progress.update({'progress':displayVal});
				}
				*/
				if(progressBar){
					progressBar.reportprogress(displayVal);
				}
				/*
				var span = document.getElementById('status_text');
				span.innerText = displayVal + " % " + '<fmt:message key="upgr.upgrade.running.status" />';
				*/
			}
			
			function initFileUpload(){
				uploadCtrl = new AjaxUpload('#uploadButton', {
					action: '../upgrade/executeUpgrade.htm',
					name: 'setupFile',
					responseType: 'json',
					autoSubmit: false,
					submitIfEmpty: true,
					onChange: displayFile,
					//onSubmit: startPolling,
					onComplete: uploadCompleted 
				});
				/*
				var pBarParams = {
					places: 2, 
					progress: 0, 
					maximum: 100,
					style: "display: none; width: 400px; vertical-align: middle; font-weight: bold;"
				};
				*/
				if($("#progressbar").reportprogress != undefined){
					progressBar = $("#progressbar");
					progressBar.reportprogress(0)
				}
				//$("#statusbar").fadeOut();
			}
			
			function displayFile(file , ext){
				var pDisplay = document.getElementById('uploadText');
				pDisplay.value = file;
			}
			
			function uploadSetup(){
				if(!running){
					//running = true;
					uploadCtrl.submit();
					//startPolling();
				}
				else{
					alert("<fmt:message key="upgr.upgrade.input.alreadyrunning.msg"/>");
				}
				return false;
			}
			
			function startPolling(){
				//var div = document.getElementById('status_div');
				var div = document.getElementById('progressbar');
				div.style.display = "block";
				setTimeout(startInterval, 1000);
			}
			
			function stopPolling(){
				//if(running){
					clearInterval(refreshProcess);
					refreshProcess = undefined;
				//}
				running = false;				
			}
			
			function startInterval(){
				/*
				var span = document.getElementById('status_text');
				span.innerText = "0.0 % " + '<fmt:message key="upgr.upgrade.running.status" />';
				*/
				refreshStatus();
				refreshProcess = setInterval(refreshStatus, 5000);
			}
			
			function validateUpload(file, ext){
				
			}
			
			function uploadCompleted(file, response){
				var respObj = eval(response);
				//TODO gestire nuovo JSON restituito
				if(respObj.started){
					startPolling();
				}
				else{
					alert(respObj.msg);
					running = false;
				}
				//goToResults();
			}
			
			function refreshStatus(){
				var ts = new Date().getTime();	
				var sendData = {
					_ts: ts,
					idcomunealias: '${param.idcomunealias}'
				};
				jQuery.ajax({
					url: '<%=request.getContextPath()%>/upgrade/refreshStatus.htm',
					dataType: 'json',
					data: sendData,
					success: refreshCallback,
					error: refreshFailure
				});
			}
			
			function refreshCallback(data, status, xhr){
				var respObj = data;
				executed_tasks = respObj.executedTasks;
				tot_tasks = respObj.totalTasks;
				if(! respObj.running){
					goToResults();
				}
				else{
					displayStatus();
				}
			}
			
			function goToResults(){
				var _ts = new Date().getTime();	
				stopPolling();
				window.location.href = '<%=request.getContextPath()%>/upgrade/displayResults.htm?_ts='+_ts+'&idcomunealias=${param.idcomunealias}';
			}
			
			function refreshFailure(xhr,  textStatus, errorThrown){
				var msg = xhr.responseText;
				if(!msg || msg == ''){
					msg = "<fmt:message key="upgr.upgrade.input.error.msg"/>";
				}
				displayError(msg);
				/*
				alert(msg);
				goToResults();
				*/
			}
			
			var refreshErrors = [];
			
			function displayError(errMsg){
				var statusBar = $('#statusbar');
				statusBar.show();
				var errorList = $('#errorlist');
				//statusBar.fadeOut();
				errorList.empty();
				errMsg = new Date().toLocaleString() + ": " + errMsg;
				var errCount = refreshErrors.push(errMsg);
				/*
				if(errCount > 10){
					refreshErrors.shift();
				}
				*/
				for ( i = 0; i < refreshErrors.length; i++) {
					errorList.append('<li class="error">' + refreshErrors[i] + '</li>');
				}
				//statusBar.fadeIn();
			}
			
    		jQuery(document).ready(function(){
    			initFileUpload();
    		});
    		
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
    		})(jQuery);		</script>
	<span class="titoloPagina">
		<fmt:message key="upgr.upgrade.input.title"/>
	</span>
	<div><fmt:message key="upgr.upgrade.input.msg"/></div>
	<%--
	<div>
		<form action="../upgrade/generateSetupFile.htm"><input type="submit" value="Genera il file setup.xml" name="createSetup"/></form>
	</div>
	 --%>
	<br />
	<div>
			<span id="uploadButton" class="upload">
				<img src="${pageContext.request.contextPath}/images/upgr/upload-file.gif" border="0" alt="Aggiungi file"/>&nbsp;&nbsp;
				<b><fmt:message key="upgr.upgrade.input.upload.file" /></b>
			</span><fmt:message key="upgr.upgrade.input.upload.file.format" />
			<input type="text" name="setup_file_view" id="uploadText" class="upload" readonly="readonly" style="border-style:dotted; border-width: thin; border-color: #999999; ; width: 280px;">			
			</input>
			<br />
			<span id="startSetup" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="uploadSetup();">			
				<span id="refreshButton">
					<input type="image" src="${pageContext.request.contextPath}/images/upgr/run-setup.gif" name="startSetup"/>&nbsp;&nbsp;
					<b><fmt:message key="upgr.upgrade.runupgr.label" /></b>
				</span>
			</span>
		
	</div>
	<br />
	<div id="progressbar" style="display: none; width: 400px; height: 35px;">
<!--		<span style="font-size: 22px;" id="status_text">Running ...</span>
		<div id="progressbar"></div>-->
	</div>
	<br />
	<div id="statusbar" class="error" style="font-size: small; display: none; height: 350px; overflow-y: auto;">
		<fmt:message key="upgr.upgrade.refresherrors.label" />:<br />
		<ul id="errorlist">
		</ul>
	</div>
	<%--
	<div>
		<form action="../upgrade/refreshSessionFactory.htm" method="post"><input type="submit" value="Aggiorna la Session Factory" name="refreshSession"/></form>
	</div>
	 --%>
</body>
</html>