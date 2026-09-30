<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<%String  vJS = "1.7_2016-08-02_01.09";%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<title><decorator:title /></title>
		<link href="https://fonts.googleapis.com/css?family=Merriweather:400,700" rel="stylesheet" type="text/css">
		<link type="text/css" href="${pageContext.request.contextPath}/css/layouts/layout.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" />		
		<link type="text/css" href="${pageContext.request.contextPath}/css/cart/smoothness/jquery-ui-1.11.1.custom.css" rel="stylesheet" />	
		<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/cart/autocompiler/init-autocompiler.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet">
		<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet">
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-1.8.3.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/autoNumeric-1.7.5.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.validate.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/additional-methods.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/localization/messages_it.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.ui.datepicker-it.js?<%=vJS %>"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.blockUI.js?<%=vJS %>"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.jmesa.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jmesa.min.js?<%=vJS %>"></script>		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tooltip.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tmpl.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/gruppoinit.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/autocompiler/init-autocompiler.js?<%=vJS %>"></script>
		<%-- FOGLI DI STILE AREA RISERVATA --%>		
		<link href="${pageContext.request.contextPath}/css/cart/less/less.include.css" type="text/css" rel="stylesheet" />		
		<link href="${pageContext.request.contextPath}/css/cart/stili2.star.css" type="text/css" rel="stylesheet" />
		<style>
			.logo-star{
				height: auto; 
		    	width: auto; 
		    	max-width: 50px; 
		    	max-height: 50px; 
				float: left;			
			}
			.logo-spid-agid{
				height: auto; 
		    	width: auto; 
		    	max-width: 300px; 
		    	max-height: 300px; 
				float: right;
			}
			.titolo-app{
				padding-top: 13px;
				padding-left: 20px;
				float: left;
				font-size: 1em;
				color: #b71218;
				text-transform: uppercase;
		    	text-shadow: #bbbbbb 3px 3px 3px;
		    	font-size: large;
		    	font-weight: bold;
			}
		</style> 
		<decorator:head />
		<script type="text/javascript">
			jQuery(document).ready(function(){	
				jQuery.datepicker.regional['it'];
				jQuery.datepicker.setDefaults({
					inline: true,
					dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "1900:+00"});
				// correggiAllineamentoImmagine();
				jQuery('#ui-datepicker-div').hide();
				jQuery("input:submit").button();
				jQuery("input:file").button();
				jQuery("input:button").button();
				jQuery("button").button();
				jQuery("submit").button();

				inizializzaFACCT();
			});	
		</script>		
	</head>
	<body>
		<div id="hrTop">
		</div>
		<div id="messaggioAggiornamento_id" style="width:100%; text-align:center; font-size:20px; color:#FF0000; padding:40px; display:none"></div>
		<div style="margin: 8px; position: relative;">
			<div class="titolo-app">
				<fmt:message key="label.anteprima_modello" />
			</div>			
	
			<div style="clear: both;"></div>
			<!-- 
			<div style="float: right;">
				Ente: <span style="font-weight: bold;"><%=request.getSession().getAttribute(WebConstants.ENTE_IN_SESSION_VARIABLE_NAME) %></span>
			</div>
			 -->	
		</div>	
		<div id="spacer" style="clear: both;"><% if(request.getSession().getAttribute("_WHERE_IS_FRED_")!=null){out.print(request.getSession().getAttribute("_WHERE_IS_FRED_"));} %></div>
		<div style="margin: 8px; margin-bottom: 45px;">
			<decorator:body />
		</div>
	</body>
</html>