<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.security.LoggedUser"%>
<%@ page import="org.springframework.security.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.context.SecurityContext"%>
<%@ page import="org.springframework.security.userdetails.UserDetails"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.util.Date"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<title><fmt:message key="label.appname"/> - <decorator:title default=""/></title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<%
		SecurityContext sc = SecurityContextHolder.getContext();
		UserDetails ud = (UserDetails)sc.getAuthentication().getPrincipal();
		LoggedUser user = (LoggedUser)ud;
		String stileBO = (String)user.getImpostazioniUtente().get(WebConstants.CSS_USER_PREF_STYLE);
	%>
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/styles/<%=stileBO %>" />
	<!-- Java script per il calendario -->
	<!-- ####################################################################################################### -->
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/calendar/css/jscal2.css" />
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/calendar/css/border-radius.css" />
    <!-- ####################################################################################################### -->
	<!--[if lt IE 7]>
	<style type="text/css">
		table {
			font-size: 1em;
		}
	</style>
	<![endif]-->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug:false"></script>	
	<script type="text/javascript">
	  	dojo.require("dojo.data.ItemFileReadStore");
	  	dojo.require("dojo.parser");
	  	dojo.require("dijit.Tree");
	  	dojo.require("dijit.Menu");
	  	dojo.require("dijit.Dialog");
	  	dojo.require("dijit.layout.ContentPane");
	  	dojo.require("dijit.Tooltip");
	</script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/dojo/dijit/themes/nihilo/nihilo.css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.3.2.min.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/prototype.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/scriptaculous/scriptaculous.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.jmesa.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jmesa.min.js"></script>
	<!-- Java script per il calendario -->
	<!-- ####################################################################################################### -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/jscal2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/unicode-letter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/lang/it.js"></script>
    <!-- ####################################################################################################### -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jScrollPane-1.2.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload.js"></script>
	
	<%-- TinyMCE Editor --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/tinymce/tinymce.min.js"></script>
	
	<script type="text/javascript">
		<!--
		function searchAll(inputField,evt,minChars){
			if(checkMinChars(inputField,minChars)){
				var charCode = (evt.which) ? evt.which : event.keyCode;
				if (charCode == '<fmt:message key="ajax.searchall.key" />'){
					inputField.value='%';
				}
			}
		}	
		function checkMinChars(inputField,minChars){	
			if(inputField.value.length + 1 < minChars){
				return false;
			}
			return true;
		}	
		//-->
		/*
		* funzione che chiude la finestra popup invocando prima una funzione callback della finestra principale che aveva aperto la popup
		* la funzione da invocare è passata come argomento, può essere direttamente un riferimento alla function
		* oppure una stringa che definisce il nome della funzione da ricercare nel contesto della finestra chiamante.
		*/
		function closeAndCallback(callMeOnClose){
			var parentWin = window.opener;
			if(parentWin){
				if(callMeOnClose && typeof callMeOnClose == "string"){
					callMeOnClose = parentWin[callMeOnClose];
				}
				if(callMeOnClose && typeof callMeOnClose == "function"){
					callMeOnClose();
				}
				self.close();
			}
		}
			
	</script>
</head>
<body>
	<div id="content">			
  		<decorator:body/>
	</div>
</body>
</html>