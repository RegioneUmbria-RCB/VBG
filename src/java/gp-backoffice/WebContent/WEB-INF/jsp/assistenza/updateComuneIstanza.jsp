<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.pannello_di_amministrazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.pannello_di_amministrazione" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../assistenza/view" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="administration" />
	</jsp:include>
	<div id="subcontent">
	<form name="inviodati" id="updateComuneIstanzaForm">	
	<fieldset>
	<table>
		<tr>
			<td width="200px;">Software selezionato </td>
			<td><b><c:import url="/ajax/findCurrentSoftware.htm" /></b></td>
		</tr>
		<tr>
			<td>Scegli un altro software </td>
			<td>
				<select name="softwareCorrente" id="softwareCorrenteId" onchange="cambiaSoftware(this)">
					<option value="">...</option>
					<c:forEach items="${softwares}" var="software">
						<option value="${software.codice }">${software.descrizione}</option>
					</c:forEach>
				</select>
			</td>
		</tr>	
		<tr>
			<td>Cerca l'istanza:</td>
			<td>
				<input type="text" id="istanze_id" name="numeroistanza" 
						class="searchbox" 
						onchange="checkValue(this,'istanze_hidden')" 
						onkeydown="javascript:return searchAll(this,event)"  size="67" />
					<init:autocompleter methodAjax="findIstanze.htm" 
						idHidden="istanze_hidden" idInput="istanze_id" 
						inputTitleKey="label.ricerca_istanza" />					
					<input type="hidden" id="istanze_hidden" name="codiceIstanza" />		
			</td>
		</tr>
	</table>
	</fieldset>					
	<fieldset>
	<table>
		<tr>
			<td width="200px;">Comune di destinazione:</td>
			<td>
				<select name="comuneA" id="codicecomuneId">			
					<option value="">Scegli ... </option>
					<c:forEach items="${comuniassociatis}" var="comuniassociati">
					<option value="${comuniassociati.id.codicecomune}">${comuniassociati.comune.comune}</option>
					</c:forEach>
				</select>	
			</td>
		</tr>		
	</table>
	</fieldset>		
		<div id="functions">
			<ul>
				<li><a href="javascript:void 0;" onclick="spostaIstanzaConfirm();">Modifica comune</a></li>
				<li><a href="javascript:doHref('../assistenza/view.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>	
				<div dojoType="dijit.Dialog" id="spostaIstanzeDialogDiv" title="Modifica comune pratica"  style="display: none;">
					<input type="checkbox" id="cancellazioneistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
					<label for="cancellazioneistanzachk_id">
						Spostare la pratica al nuovo comune?

						<p />
						<div class="error">
							 L'operazione sarà riportata nei Log
							 </b>
						 </div>
					</label>
					<div id="functions">
						<ul>
							<li style="display: none;" id="doDeleteId"><a href="javascript:spostaPratica()"><fmt:message key="button.ok" /></a></li>
							<li><a href="javascript:void 0" onclick="dijit.byId('spostaIstanzeDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
						</ul>
					</div>
					<br class="clear" />	
				</div>
		</form>		
	</div>	
<script type="text/javascript">

function spostaIstanzaConfirm(){
	dijit.byId('spostaIstanzeDialogDiv').show();
}

function spostaPratica(){

	doSubmit('../assistenza/updateComuneIstanzaExec.htm','',$('updateComuneIstanzaForm'));	
}
function cambiaSoftware(obj){
	document.location.replace('updateComuneIstanzaView.htm?software='+getValoreDellaSelect(obj));
}

</script>	
</body>
</html>