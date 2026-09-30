<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/cart/smoothness/jquery-ui-1.11.1.custom.css" rel="stylesheet"  ></link>
	<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet"  ></link>
	<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet" ></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.ui.datepicker-it.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tooltip.min.js"></script>
	<%-- TODO spostare lo script da scripts/cart a scripts dopo aver unificato le versioni di jQUery ed effettuato i test per le regressioni --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/gruppoinit.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/autoNumeric-1.7.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/init-facct.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/init-schededinamiche.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.uploadDatiDinamici.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
	
	<title>
	
		<fmt:message key="label.anteprima_modello" />
	
	</title>
</head>
<body>
	
	<script type="text/javascript">
		jQuery(document).ready(function(){
			//if(initUIControls){
				initUIControls();
			//}
		});
	</script>

	<div id="subcontent">
	<spring-form:form commandName="info" name="inviodati">
		
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="info" />
		</jsp:include>
		
		<br class="clear" />
			
		
		<div id="change_data_status_msg" class="warning_header" style="display: none;text-transform: uppercase;"></div>
			${modello}	

	</spring-form:form>
	</div>

		
		<br class="clear" />

	<div id="functions">
	<ul>
	
		
		<li><a href="javascript:window.close();"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>