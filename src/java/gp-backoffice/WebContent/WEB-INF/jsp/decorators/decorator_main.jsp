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
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<c:set var="_title_"><decorator:title default=""/></c:set>
	<title><fmt:message key="label.appname"/> - ${fn:replace(_title_,'<br />', '')}</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<%
		SecurityContext sc = SecurityContextHolder.getContext();
		UserDetails ud = (UserDetails)sc.getAuthentication().getPrincipal();
		LoggedUser user = (LoggedUser)ud;
		String stileBO = (String)user.getImpostazioniUtente().get(WebConstants.CSS_USER_PREF_STYLE);
	%>
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/cart/smoothness/jquery-ui-1.11.1.custom.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/styles/<%=stileBO %>" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/superfish/superfish.css" />
	<!--[if gt IE 8]>	
		<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/superfish/superfishIE9Hack.css" />
	<![endif]-->
	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/jScrollPane.css" />
	<%-- Java script per il calendario --%>
	<%-- ####################################################################################################### --%>
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/calendar/css/jscal2.css" />
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/calendar/css/border-radius.css" />
    <%--  ####################################################################################################### --%>
    
    <%--  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome/5.15.4-web/css/all.min.css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome/5.15.4-web/css/v4-shims.min.css"></link>

    <link href="${pageContext.request.contextPath}/css/vbg-modern/style.css" rel="stylesheet" crossorigin="anonymous">
    
	<!--[if lt IE 7]>
	<style type="text/css">
		table {
			font-size: 1em;
		}
	</style>
	<![endif]-->
	
	<style type="text/css">
		.scroll-pane {
			width: 600px;
			height: 500px;
			overflow: auto;
			background-color: white;
			border: 1px #ccc solid;
		}
	</style>
	 
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
	
	<%-- 
		questo controllo serve per evitare che il chiudi di una pagina di SigeproMS apra la
		pagina di Sigepro2 all'interno dell'iframe
    
	--%>
	<c:if test="${req_param_visualizzaScriptLocation eq true }">
		<%-- BEGIN DISABILITATO PER MOTIVI DI VULNERABILITA' --%>
		<script type="text/javascript">
			
			if (window!=top){
				top.location.href=location.href;
		    }
			
	    </script>
	    <%-- END DISABILITATO PER MOTIVI DI VULNERABILITA' --%>
	</c:if>
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/hoverIntent.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/superfish.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/supersubs.js?<%=vJS %>"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/prototype.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/scriptaculous/scriptaculous.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.jmesa.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jmesa.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js?<%=vJS %>"></script>
	<%-- Java script per il calendario --%>
	<%-- ####################################################################################################### --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/jscal2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/unicode-letter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/lang/it.js"></script>
    <%-- ####################################################################################################### --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jScrollPane-1.2.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload.js?<%=vJS %>"></script>
	
	<%-- TinyMCE Editor --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jscripts/tiny_mce/tiny_mce.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-ready.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-modal.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-modal-attendere-prego.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-input-filters.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-validation.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-fieldset.js?<%=vJS %>"></script>
    
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	
	
	<script type="text/javascript">
		
		jQuery(document).ready(function(){
			var vName = window.name;
			
			if(vName === ''){
				vName = new URLSearchParams(window.location.search).get('windowName') ?? "";			
			}
			var jhqrPr = jQuery.ajax({
				  url: '../json/checkOpenTab.htm',
				  context: document.body,
				  cache: false,
				  data: "windowName="+vName,
				  dataType: "json",
				  success: function(data) {
					  if(data.errore){
						  if(document.location.pathname.indexOf('erroresessioneduplicata')<0){
						  	location.replace('ajaxerroresessioneduplicata.htm');
						  }
					  }
					  if(data.windowName){
						  if(data.windowName!=''){
							  window.name = data.windowName; 
						  }
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
				}
			});
		});
		jQuery(document).ready(function(){
			jQuery('ul.sf-menu').supersubs({ 
            	minWidth:    10,
            	maxWidth:    15,
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
			var d = new Date();
			new Ajax.Request('${pageContext.request.contextPath}/menu/getSubMenu.htm?menu='+id+'&millis='+d.getTime(), {
				  method: 'post',	
				  onSuccess: function(transport){
					$(id).innerHTML = transport.responseText;
					//applyStyle();
					jQuery(document).ready(function(){
						jQuery('ul.sf-sub-menu-'+id).supersubs({ 
			            	minWidth:    10,
			            	maxWidth:    15,
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

		
	</script>
	<script type="text/javascript">
		$(function(){
			$('.scroll-pane').jScrollPane(
					{
						showArrows: true, 
						scrollbarWidth: 100,
						reinitialiseOnImageLoad: false,
						maintainPosition: true
					}
				);
		});	
		
		
	</script>
	<script type="text/javascript">
	 window.history.forward();

	</script>
	<decorator:head />
</head>
<body class="nihilo" >
<!-- start header -->
<div id="header">
	<jsp:include page="../includes/header.jsp" />
</div>

<!-- end header -->
<!-- start menu -->
<div id="menu">
	<c:import url="/menu/getMenuPrimoLivelloSF.htm" />
	<label class="modulo"><c:import url="/ajax/findCurrentSoftware.htm" /></label>
	<%-- in fase di sviluppo utilizzare la jsp locale --%>
	<%-- 
	<jsp:include page="../includes/menu.jsp" /> 
	--%>
</div>
<!-- end menu -->
<!-- start wrapper -->
<div id="wrapper">
	<!-- start page -->
	<div id="page">
		<!-- start content -->
		<div id="content">
			<%if ( request.getSession().getAttribute("AbilitaCancellazioneMasterSuSlave") != null			
				&& ((Boolean)request.getSession().getAttribute("AbilitaCancellazioneMasterSuSlave")).booleanValue()) {%>
				<div style="border:dotted 2px red; width:100%; color: red; font-weight:bolder; padding: 10px">
				<fmt:message key="label.utente_abilitato_alla_cancellazione_master" />
				<ul id="functions">
					<li><a href="javascript:void(0)" onclick="disabilitaCancellazioneMaster();">Disabilita</a></li>
				</ul>
					<script type="text/javascript">
						function disabilitaCancellazioneMaster(){
							jQuery.ajax({
								  url: '../admin/ajaxAbilitaCancellazioneMasterSuSlave.htm',
								  context: document.body,
								  cache: false,
								  data: "abilita=false",
								  dataType: "text",
								  success: function(data) {
										document.location.reload();
								  },
								  error: function(jqXHR, textStatus, errorThrown){
										console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
								}
							});
						}
					</script>
				<br class="clear"/>	
				</div>
			<%} %>
			<div id="navigation"><div id="help"><jsp:include page="../includes/help.jsp" /></div></div>
  			<decorator:body/>
		</div>
		<!-- end content -->
		<br />
	</div>
	<!-- end page -->
</div>
<!-- end wrapper -->
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cookie_script.js"></script>-->
<%-- 	
<div id="footer">
	<jsp:include page="../includes/footer.jsp" />
</div>
--%>



<jsp:include page="../decorators/scriptInizializzazioneDecorators.jsp" />

</body>
</html>
