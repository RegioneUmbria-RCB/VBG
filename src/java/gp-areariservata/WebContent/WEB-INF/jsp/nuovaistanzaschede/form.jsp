<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet" ></link>
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.uploadDatiDinamici.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-schededinamiche.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#annulla_scheda_id").click(function(){
			$.blockUI();
			window.location.replace("view.htm");
		});
		//$(".help_image").tooltip();
		//$("div#functions > ul > li > a").button();
		//$("div.divEliminazioneBlocco > a").button();
	});
	</script>
	
	<script type="text/javascript">

	var dataChanged = false;
	
	<c:if test="${not empty param.dataChanged}">
		dataChanged = ${param.dataChanged}; 
	</c:if>
	
	function changeData(){
		if(dataChanged){
			if( confirm("<fmt:message key='alert.salvare-i-dati' />")){
				dataChanged = false;
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	function infoWarningMsg(){
		$("#change_data_status_msg").text("<< <fmt:message key='label.salvare-i-dati-della-scheda' />");
		$("#change_data_status_msg").show();
		
	}
	
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="saveScheda.htm" method="post" commandName="nuovaIstanzaCommand" id="schede_form" enctype="multipart/form-data">
		<div class="titolo_sezione">${schedaH.scheda.descrizione }</div>
		<div id="scheda" class="sezione">
			<input type="hidden" name="codiceModello" value="${schedaH.scheda.codice }"/>
		    ${html }
		    <input type="button" value="<fmt:message key='button.indietro' />" id="annulla_scheda_id" />
		    <input type="submit" value="<fmt:message key='button.salva' />" id="salva_scheda_id" />
		    <span id="change_data_status_msg" class="validation_error" style="display: none;"></span>
		</div>
	</spring-form:form>
</body>
</html>