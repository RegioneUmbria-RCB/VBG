<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", 0); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message	key="form.oggettifilesystem.launch.titolo" /></title>
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
	<span class="titoloPagina"><fmt:message	key="form.oggettifilesystem.launch.titolo" /></span>
	<div id="subcontent">
		<c:if test="${errorString != null && errorString != ''}">
			<div id="error_msg" class="error_header" style="padding-bottom:1px;">${errorString}</div>
		
			<script type="text/javascript">
		            $('error_msg').pulsate({ pulses: 2, duration: 1.0 });
		    </script>
		</c:if>
		<c:if test="${errorString == null || errorString == ''}">
			<spring-form:form commandName="oggettiFileSystem" name="filetofile"
				enctype="multipart/form-data">
				<fieldset>
				<legend><fmt:message key="form.oggettifilesystem.launch.filetofile.title" /></legend>			
				<table>
					<tr>
						<td><fmt:message key="form.oggettifilesystem.launch.filetofile.description" /></td>
					</tr>
					<tr>
						<td>
							<fmt:message key="form.oggettifilesystem.launch.filetofile.numdocuments">
								<fmt:param value="${rootPath }"></fmt:param>
								<fmt:param value="${oggettiFileSystemCommand.fileCount }"></fmt:param>
							</fmt:message>
						</td>
					</tr>
				</table>
				<div id="functions">
					<ul>
						<li><a href="javascript:ottimizzaFileSystem()"><fmt:message key="form.oggettifilesystem.launch.runprocedure" /></a></li>
					</ul>
				</div>
				</fieldset>	
			</spring-form:form>
			<spring-form:form commandName="oggettiFileSystem" name="blobtofile"
				enctype="multipart/form-data">
				<fieldset>
				<legend><fmt:message key="form.oggettifilesystem.launch.blobtofile.title" /></legend>				
				<table>
					<tr>
						<td><fmt:message key="form.oggettifilesystem.launch.blobtofile.description" /></td>
					</tr>
					<%--
					<tr>
						<td>
							<fmt:message key="form.oggettifilesystem.launch.blobtofile.numdocuments">
								<fmt:param value="${oggettiFileSystemCommand.blobCount }"></fmt:param>
							</fmt:message>
						</td>
					</tr>
					 --%>
					<tr>
						<td>
							<c:if test="${oggettiFileSystemCommand.setBlobNull == true}">
								<input type="checkbox" checked="checked" name="setBlobNull" id="setBlobNullChk"></input>
							</c:if>
							<c:if test="${oggettiFileSystemCommand.setBlobNull == false}">
								<input type="checkbox" name="setBlobNull" id="setBlobNullChk"></input>
							</c:if>
							&nbsp;<label for="setBlobNullChk"><fmt:message key="form.oggettifilesystem.launch.deleteblob"/></label>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="form.oggettifilesystem.launch.maxdocuments"/>
							&nbsp;<input type="text" name="maxDocs" id="maxDocsInput" value=""></input>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="form.oggettifilesystem.launch.maxminutes"/>
							&nbsp;<input type="text" name="maxMinutes" id="maxMinutesInput" value=""></input>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="form.oggettifilesystem.launch.mincodiceoggetto"/>
							&nbsp;<input type="text" name="minCodiceOggetto" id="minCodiceOggetto" value=""></input>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" name="verificaIncongruenze" id="verificaIncongruenzeChk"></input>
							&nbsp;<label for="verificaIncongruenzeChk"><fmt:message key="form.oggettifilesystem.launch.checkdiscrepancy"/></label>
						</td>
					</tr>
				</table>
				<div id="functions">
					<ul>
						<li><a href="javascript:spostaBlobSuFileSystem()"><fmt:message key="form.oggettifilesystem.launch.runprocedure" /></a></li>
						<li><a href="javascript:stopSpostaBlobSuFileSystem()">ferma la procedura</a></li>
					</ul>
				</div>
				</fieldset>	
			</spring-form:form>
			<script>
				var running = false; 
				var tot_objs = 0;
				var handled_objs = 0;
				var refreshProcess;
				var progressBar;
				
				function displayStatus(){
					if(progressBar){
						if(tot_objs == 0){
							tot_objs = 100;
						}
						//nel caso di conteggi degli oggetti da eleborare passati come argomento con valore errato 
						//si evita che la barra di avanzamento superi il 100%
						if(handled_objs > tot_objs){
							handled_objs = tot_objs;
						}
						progressBar.reportprogress(handled_objs, tot_objs);
					}
					/*
					var progress = dijit.byId('status_div');
					if(progress){
						progress.update({'progress':displayVal});
					}
					*/
					/*
					var span = document.getElementById('status_text');
					span.innerText = displayVal + " % " + '<fmt:message key="upgr.upgrade.running.status" />';
					*/
				}

				function ottimizzaFileSystem(){
					//alert("lancio procedura di ottimizzazione");
					if(!running){
						running = true;
						var ts = new Date().getTime();	
						var sendData = {
							_ts: ts,
							objectCount: ${oggettiFileSystemCommand.fileCount}
						};
						jQuery.ajax({
							url: '<%=request.getContextPath()%>/oggettifilesystem/ottimizzaFileSystem.htm',
							dataType: 'json',
							async: true,
							cache: false,
							data: sendData,
							success: procedureCompleted,
							error: handleError
						});
						startPolling();
					}
					else{
						alert("<fmt:message key="form.oggettifilesystem.launch.alreadyrunning.msg"/>");
					}
				}
				
				function spostaBlobSuFileSystem(){
					//alert("lancio procedura di ottimizzazione");
					if(!running){
						running = true;
						var ts = new Date().getTime();	
						var _setBlobNull = true;
						var setBlobNullChk = jQuery("#setBlobNullChk");
						if(setBlobNullChk && setBlobNullChk.length > 0){
							_setBlobNull = setBlobNullChk[0].checked ? true : false;
						}
						var verifIncon = false;
						var verifInconChk = jQuery("#verificaIncongruenzeChk");
						if(verifInconChk && verifInconChk.length > 0){
							verifIncon = verifInconChk[0].checked ? true : false;
						}
						var sendData = {
							_ts: ts,
							setBlobNull: _setBlobNull,
							maxDocs: jQuery("#maxDocsInput").val(),
							maxMinutes: jQuery("#maxMinutesInput").val(),
							blobCount: ${oggettiFileSystemCommand.blobCount},
							verificaIncongruenze: verifIncon,
							minCodiceOggetto: jQuery("#minCodiceOggetto").val()
						};
						jQuery.ajax({
							url: '<%=request.getContextPath()%>/oggettifilesystem/spostaBlobSuFileSystem.htm',
							dataType: 'json',
							type: 'POST',
							async: true,
							cache: false,
							data: sendData,
							success: procedureCompleted,
							error: handleError
						});
						startPollingBlob();
					}
					else{
						alert("<fmt:message key="form.oggettifilesystem.launch.alreadyrunning.msg"/>");
					}
				}
				
				function procedureCompleted(data, status, xhr){
					if(data && data.msg){
						alert(data.msg);
					}
					else{
						alert("<fmt:message key="form.oggettifilesystem.launch.completed.msg"/>");
					}
					goToResults();
				}
				
				function stopSpostaBlobSuFileSystem(){
					var ts = new Date().getTime();	
					var sendData = {
						_ts: ts,
						idcomunealias: '${idcomunealias}'
					};
					jQuery.ajax({
						url: '<%=request.getContextPath()%>/oggettifilesystem/stopRunningStatus.htm',
						type: 'POST',
						dataType: 'json',
						cache: false,
						data: sendData						
					});
				}
				
				function refreshStatus(){
					var ts = new Date().getTime();	
					var sendData = {
						_ts: ts,
						idcomunealias: '${idcomunealias}'
					};
					jQuery.ajax({
						url: '<%=request.getContextPath()%>/oggettifilesystem/refreshStatus.htm',
						type: 'POST',
						dataType: 'json',
						cache: false,
						data: sendData,
						success: refreshCallback,
						error: refreshFailure
					});
				}
				
				function handleError(xhr, textStatus, errorThrown){
					var msg = xhr.responseText;
					if(!msg || msg == ''){
						msg = "<fmt:message key="upgr.upgrade.input.error.msg"/>";
					}
					alert(msg);
					//goToResults();
				}
				
				function startPolling(data, status, xhr){
					//var div = document.getElementById('status_div');
					var div = document.getElementById('progressbar');
					div.style.display = "block";
					setTimeout(startInterval, 2000);
				}
				
				function startPollingBlob(data, status, xhr){
					//var div = document.getElementById('status_div');
					var div = document.getElementById('statusBlob');
					div.style.display = "block";
					setTimeout(startIntervalBlob, 2000);
				}
				
				function startIntervalBlob(){
					refreshStatusBlob();
					refreshProcess = setInterval(refreshStatusBlob, 4000);
				}
				
				function refreshStatusBlob(){
					var ts = new Date().getTime();	
					var sendData = {
						_ts: ts,
						idcomunealias: '${idcomunealias}'
					};
					jQuery.ajax({
						url: '<%=request.getContextPath()%>/oggettifilesystem/refreshStatus.htm',
						type: 'POST',
						dataType: 'json',
						cache: false,
						data: sendData,
						success: refreshCallbackBlob,
						error: refreshFailure
					});
				}
				
				function refreshCallbackBlob(data, status, xhr){
					
					var respObj = data;
					handled_objs = respObj.handledCount;
					tot_objs = respObj.totalCount;
					
					if(! respObj.running){
						goToResults();
					}
					else{
						displayStatusBlob(respObj.maxCodiceOggetto, respObj.minCodiceOggetto, respObj.codiceOggettoCorrente, handled_objs, respObj.running);
					}
				}
				function displayStatusBlob(maxCodiceOggetto, minCodiceOggetto, codiceOggettoCorrente, oggettiElaborati, running){
					

					if(progressBar){
						jQuery('#statusBlob').html('In corso: ' + running + '<br />Minimo: '+minCodiceOggetto+'<br />Massimo: '+maxCodiceOggetto+'<br />Attuale: '+codiceOggettoCorrente+"<br/> totale elaborati: "+oggettiElaborati);
					}
					
				}
				
				function startInterval(){
					/*
					var span = document.getElementById('status_text');
					span.innerText = "0.0 % " + '<fmt:message key="upgr.upgrade.running.status" />';
					*/
					refreshStatus();
					refreshProcess = setInterval(refreshStatus, 5000);
				}	
				
				function initGUI(){
					/*
					var pBarParams = {
						places: 2, 
						progress: 0, 
						maximum: 100,
						style: "display: none; width: 400px; vertical-align: middle; font-weight: bold;"
					};
					*/
					if(jQuery("#progressbar").reportprogress != undefined){
						progressBar = jQuery("#progressbar");
						progressBar.reportprogress(0);
					}
				}
				
				function refreshCallback(data, status, xhr){
					var respObj = data;
					handled_objs = respObj.handledCount;
					tot_objs = respObj.totalCount;
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
					window.location.href = '<%=request.getContextPath()%>/oggettifilesystem/displayResults.htm?_ts=' + _ts;
				}
				
				function stopPolling(){
					if(running){
						clearInterval(refreshProcess);
						refreshProcess = undefined;
					}
					//alert("setup terminato!");
					running = false;				
				}
				
				function refreshFailure(xhr,  textStatus, errorThrown){
					var msg = xhr.responseText;
					if(!msg || msg == ''){
						msg = "<fmt:message key="upgr.upgrade.input.error.msg"/>";
					}
					alert(msg);
					goToResults();
				}
				
	    		jQuery(document).ready(function(){
	    			initGUI();
	    		});
	    		
	    		(function($) {	
	    			//Main Method
	    			$.fn.reportprogress = function(val,maxVal) {			
	    				var max=100;
	    				if(maxVal)
	    					max=maxVal;
	    				return this.each(
	    					function(){		
	    						var div=jQuery(this);
	    						var innerdiv=div.find(".progress");
	    						
	    						if(innerdiv.length!=1){						
	    							innerdiv=jQuery("<div class='progress'></div>");					
	    							div.append("<div class='text'>&nbsp;</div>");
	    							jQuery("<span class='text'>&nbsp;</span>").css("width",div.width()).appendTo(innerdiv);					
	    							div.append(innerdiv);					
	    						}
	    						var width=Math.round(val/max*100);
	    						innerdiv.css("width",width+"%");	
	    						var progressLabel=Math.round(val/max*10000)/100;
	    						div.find(".text").html(progressLabel+" %");
	    					}
	    				);
	    			};
	    		})(jQuery);		
	    	</script>
			<br/>
			<div id="progressbar" style="display: none; width: 400px; height: 35px;">
			</div>
			<div id="statusBlob" style="display: none; font-size: 1.5em">
			</div>
		</c:if>
	</div>
</body>
</html>