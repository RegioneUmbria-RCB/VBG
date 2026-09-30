<%-- doctype eliminato per permettere all'iframe di espandere su tutta la finestra tramite l'uso della table e degli attributi width ed height --%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.context.SecurityContext"%>
<%@ page import="org.springframework.security.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.userdetails.UserDetails"%>
<%@ page import="it.gruppoinit.pal.gp.core.security.LoggedUser"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<title><fmt:message key="label.appname"/></title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<%
		SecurityContext sc = SecurityContextHolder.getContext();
		UserDetails ud = (UserDetails)sc.getAuthentication().getPrincipal();
		LoggedUser user = (LoggedUser)ud;
		String stileBO = (String)user.getImpostazioniUtente().get(WebConstants.CSS_USER_PREF_STYLE);
	%>
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/sigeproExternal/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/styles/<%=stileBO %>" />	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/superfish/sigeproExternal/superfish.css" />
	<!--[if gt IE 8]>	
		<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/superfish/superfishIE9Hack.css" />
	<![endif]-->
	
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
	  	dojo.require("dijit.MenuItem");
	  	dojo.require("dijit.MenuSeparator");
	  	dojo.require("dijit.Menu");
	  	dojo.require("dijit.form.DropDownButton");
	  	dojo.require("dijit.Dialog");
	  	dojo.require("dijit.layout.TabContainer");
	  	dojo.require("dijit.layout.ContentPane");
	  	dojo.require("dijit.Tooltip");
	</script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/dojo/dijit/themes/nihilo/nihilo.css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/hoverIntent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/superfish.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/supersubs.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/prototype.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/scriptaculous/scriptaculous.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.jmesa.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jmesa.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload.js"></script>

	<%-- TinyMCE Editor --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jscripts/tiny_mce/tiny_mce.js" ></script>
	
	<script type="text/javascript">
	<!--	
		jQuery(document).ready(function(){
			jQuery('ul.sf-menu').supersubs({ 
            	minWidth:    10,
            	maxWidth:    20,
            	extraWidth:  1
        	}).superfish({
        		delay:       800,
            	animation:   {opacity:'show',height:'show'},
            	speed:       'normal',
            	autoArrows:  true,
            	dropShadows: true
        	});
		});	

		function getSubMenu(id){
			new Ajax.Request('${pageContext.request.contextPath}/menu/getSubMenu.htm?menu='+id, {
				  method: 'post',	
				  onSuccess: function(transport){
					$(id).innerHTML = transport.responseText;
					applyStyle();
					jQuery(document).ready(function(){
						jQuery('ul.sf-sub-menu-'+id).supersubs({ 
			            	minWidth:    10,
			            	maxWidth:    20,
			            	extraWidth:  1
			        	}).superfish({
			        		delay:       800,
			            	animation:   {opacity:'show',height:'show'},
			            	speed:       'normal',
			            	autoArrows:  true,
			            	dropShadows: true
			        	});		    
					});
			      },
				  onFailure: function(transport){ 
					$(id).innerHTML="<li><a href=\"#\">Errore durante il caricamento del sottomenu</a></li>"; 
				  }						    		 
			});
		}
	//-->
	</script>
</head>
<body>
	<table width="99%" height="100%" style="font-size: 100%; margin-left: 1px;" cellpadding="0px" cellspacing="0px" border="0">
	<!-- start header -->
		<tr>
			<td>
				<div id="header">
					<jsp:include page="../includes/header.jsp" />
				</div>
			</td>
		</tr>
	<!-- end header -->
	<!-- start menu -->
		<tr>
			<td>
				<div id="menu" style="font-size: 11px; width: 100%">	
					<c:import url="/menu/getMenuPrimoLivelloSF.htm" />
				</div>
			</td>
		</tr>
	<!-- end menu -->
	<!-- start external page -->
		<tr>
			<td height="100%">
				<decorator:body/>
			</td>
		</tr>
	<!-- end external page -->
	</table>
</body>
</html>
