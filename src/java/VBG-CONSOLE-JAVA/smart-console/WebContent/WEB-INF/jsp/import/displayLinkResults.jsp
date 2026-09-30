<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/import-taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", 0); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message	key="import.linkoutputpage.title" /></title>
</head>
<body>
	<script type="text/javascript">
		function exportErrors(){
			document.forms['importErrorListForm'].submit();
		}
		
		function deleteErrors(){
			var ts = new Date().getTime();	
			var dbPathInput = jQuery("#dbPathTxt");
			//var idComuneAliasHidden = jQuery("#idComuneAliasHidden");
			if(dbPathInput && dbPathInput.val() != "" && dbPathInput.val() != undefined){
				running = true;
				var sendData = {
					_ts: ts,
					dbPath: dbPathInput.val()//,
					//idcomunealias: idComuneAliasHidden.val()
				};
				jQuery.ajax({
					url: '<%=request.getContextPath()%>/import/resetLinkErrors.htm',
					type: 'POST',
					dataType: 'json',
					data: sendData,
					success: deleteErrorsCallback,
					error: handleAjaxError
				});
			}
		}
		
		function deleteErrorsCallback(data, textStatus, jqXHR){
			if(data && data.deletedCount != undefined){
				alert(data.deletedCount + " <fmt:message key="import.linkoutputpage.reseterrors.msg"/>");
				document.forms['goToInputForm'].submit();
			}
			else{
				alert("<fmt:message key="import.outputpage.reseterrors.errmsg"/>");
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
				msg = "<fmt:message key="import.inputpage.error.msg"/>";
			}
			alert(msg);
		}
	</script>
	<span class="titoloPagina">
		<fmt:message key="import.linkoutputpage.title" ></fmt:message>
	</span>
	<div id="subcontent">
		<fmt:message key="import.linkoutputpage.numprocessed" >
			<fmt:param value="${numProcessed}"></fmt:param>
		</fmt:message><br/>
		<fmt:message key="import.linkoutputpage.numsuccessful" >
			<fmt:param value="${numSuccessful}"></fmt:param>
		</fmt:message><br/>
		<fmt:message key="import.linkoutputpage.numerrors" >
			<fmt:param value="${numErrors}"></fmt:param>
		</fmt:message><br/>
		<fmt:message key="import.linkoutputpage.numnotprocessed" >
			<fmt:param value="${numNotProcessed}"></fmt:param>
		</fmt:message><br/>
		<c:if test="${numErrors > 0}">
			<br />
			<span class="titoloTabella"><fmt:message key="import.outputpage.errors.label" ></fmt:message></span>
			<form name="goToInputForm" action="importPage.htm">
				<input type="hidden" name="dbPath" id="dbPathTxt" value="${parametriImport.dbPath}"/>
			</form>
			<form name="importErrorListForm" action="exportLinkErrors.htm">
			<input type="hidden" name="dbPath" id="dbPathTxt" value="${parametriImport.dbPath}"/>
			<input type="hidden" name="importTimestamp" value="${parametriImport.importTimestamp}"/>
			<div class="jmesa">
			<table class="table" border="0"  cellpadding="0"  cellspacing="0"  >
				<thead>
					<tr class="toolbar" >
						<td colspan="3" >
						<table border="0"  cellpadding="0"  cellspacing="1" >
							<tr>
								<td><a href="javascript:exportErrors();"><img src="${pageContext.request.contextPath}/images/upgr/excel.gif"  title="<fmt:message key="import.outputpage.errorexport.label" />"  alt="excel" /></a></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr class="header">
						<td><fmt:message key="import.linkoutputpage.error.id.label" /></td>
						<td><fmt:message key="import.linkoutputpage.error.istanza.label" /></td>
						<td><fmt:message key="import.linkoutputpage.error.istanzacollegata.label" /></td>
						<td><fmt:message key="import.outputpage.error.message.label" /></td>
						<td><fmt:message key="import.outputpage.error.importdate.label" /></td>
					</tr>
				</thead>
				<tbody class="tbody">
					<% int errorCount = 0; %>
					<% String rowClass = ""; %>
					<c:forEach items="${errors}" var="error">
						<% 
							errorCount++; 
							rowClass = errorCount % 2 == 1 ? "odd" : "even";
						%>
						<tr class="<%= rowClass %>" onmouseover="this.className='highlight'" onmouseout="this.className='<%= rowClass %>'" >
							<td>${error.id}</td>
							<td>${error.identificativoIstanza}</td>
							<td>${error.identificativoIstanzaCollegata}</td>
							<td>${error.message}</td>
							<td>${error.formattedImportTimestamp}</td>
						</tr>
					</c:forEach>
				</tbody>
				<tr class="statusBar" >
					<td align="left"  colspan="3" >${numErrors} errori</td>
				</tr>
			</table>
			</div>
			</form>
			<span id="resetErrorsBtn" class="upload" style="border: medium; border-color: black; cursor: pointer;" onclick="deleteErrors();">			
				<span id="resetErrorsButton">
					<input type="image" src="${pageContext.request.contextPath}/images/cross.gif"/>&nbsp;&nbsp;
					<b><fmt:message key="import.outputpage.reseterrors.label"/></b>
				</span>
			</span>		
		</c:if>
		</div>
	
	</body>
</html>