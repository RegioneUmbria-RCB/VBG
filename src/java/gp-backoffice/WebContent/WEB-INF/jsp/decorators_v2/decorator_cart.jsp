<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<title>Area Riservata - <decorator:title /></title>	
		
		<link type="text/css" href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" />		
		<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/stili2.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/cart/smoothness/jquery-ui-1.11.1.custom.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/cart/autocompiler/init-autocompiler.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.ui.datepicker-it.js"></script>
<%-- 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js"></script> --%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.blockUI.js"></script>
<%-- 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.min.js"></script> --%>
<%-- 		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.min.js"></script> --%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tooltip.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tmpl.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/gruppoinit.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/autoNumeric-1.7.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/autocompiler/init-autocompiler.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/init-selectsharedmenu.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/init-schededinamiche.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/init-facct.js"></script>
		<%-- FOGLI DI STILE AREA RISERVATA MS --%>
		<%-- 
 		<link id="ctl00_ctl00_cssIncludeLink" href="<%=WebConstants.%>/css/less/less.include.css" type="text/css" rel="stylesheet" />
 		<link id="ctl00_ctl00_cssLink" href="<%=request.getSession().getAttribute("baseAreaRiservataMsUrl")%>/css/stili2.css" type="text/css" rel="stylesheet" />
 		--%>
		<decorator:head />
		<script type="text/javascript">
		

			$(document).ready(function(){	
				
				$.datepicker.regional['it'];
				$.datepicker.setDefaults( {
					inline: true,
					dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "1900:+00"}); 
				
					$('#ui-datepicker-div').hide();
					$("input:submit").button();
					$("input:file").button();
					$("input:button").button();
					$("button").button();
					$("submit").button();
			});
			
			
			
		</script>
		
	</head>
	<body>
		
			<div id="intestazionePagina">
				<div id="hrTop">
				</div>
				<div id="titoloPagina" style="padding-top: 11.5px; text-align: center;">
					<h1>Generazione Allegati per Notifica CART</h1>
				</div>

			</div>
			<div  style="margin: 10px;">
				<decorator:body />
			</div>
		
	</body>
</html>