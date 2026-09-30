<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.gruppi_endoprocedimenti_t" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.gruppi_endoprocedimenti_t" /></span>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="file" />
    </jsp:include>
<div id="subcontent">
<c:if test="${errorString == null || errorString == ''}">
<br/>
</c:if>
<c:if test="${errorString != null && errorString != ''}">
	<div id="error_msg" class="error_header" style="padding-bottom:1px;">${errorString}</div>

	
</c:if>
<%String displayUpload="display:none;"; %>

<spring-form:form commandName="file" name="inviodati"
	enctype="multipart/form-data">
	<table>
		<tr>
			<td><fmt:message key="form.notificheausl.importexcel.nomefile" /></td>
			<td><input name="file" id="file" type="file" size="80" tabindex="0" onchange="controlla_estensione();" /> 
			<init:help idHelp="help1" textKey="form.notificheausl.importexcel.help"/>
			</td>
		</tr>
	</table>
</spring-form:form></div>
<script>
	
	function salvaDati(obj){
		var valore = "false";
		if(obj){
			if(obj.checked==true){
				valore = "true";
			}
		}
		saveUserPreference('<%= WebConstants.CONF_UTENTE_UPDATE_NOTIFICHE_AUSL %>',valore);
	}
	
	function get_estensione() {
		path=document.inviodati.file.value;
		var posizione_punto = path.lastIndexOf(".");
		var lunghezza_stringa = path.length;
		var estensione = path.substring(posizione_punto + 1, lunghezza_stringa);
		estensione=estensione.toLowerCase();
		return estensione;
	}
	function controlla_estensione() {
		if ( get_estensione() != "xlsx") {
			alert("Il file deve avere estensione xls");
			document.getElementById("uploadButton").style.display="none";
		}else{
			document.getElementById("uploadButton").style.display="inline";
			
		}
	}
	var checkedUpdateDati=document.getElementById('updateDati').checked;
</script>
<div id="functions">
<ul>
	<li id="uploadButton" style="<%=displayUpload %>">
		<a href="javascript:doSubmit('uploadExcel.htm','',document.inviodati)">Upload</a>
	</li>
	<li><a href="javascript:doHref('list.htm?filterIndirizzo=&filterRagSoc=','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>