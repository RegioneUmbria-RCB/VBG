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
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
        crossorigin="anonymous"></link>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
        crossorigin="anonymous"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/v2/bower_components/Yamm3/yamm/yamm.css"></link>
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/v3/css/style.css"></link>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v2/node_modules/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v2/node_modules/jsrender/jsrender.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v2/node_modules/systemjs/dist/system.js"></script>
	<!-- 
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/sigeproExternal/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/superfish/sigeproExternal/superfish.css" />
	 -->
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/v3/standard.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/v3/layouts/layout.css" />	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/jScrollPane.css" />
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
	  	dojo.require("dijit.form.DropDownButton");
	  	dojo.require("dijit.Dialog");
	  	dojo.require("dijit.layout.TabContainer");
	  	dojo.require("dijit.layout.ContentPane");
	  	dojo.require("dijit.Tooltip");
	</script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/dojo/dijit/themes/nihilo/nihilo.css" />
	
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
	
</head>
<body>
	<!-- start header -->
	<jsp:include page="header.jsp" />
	<!-- end header -->
	<!-- start menu -->
	<jsp:include page="navbar.jsp" />
    <script>
        // set our baseURL reference path
        SystemJS.config({
            baseURL: '${pageContext.request.contextPath}/scripts/v2/js',
            packages: {
                '${pageContext.request.contextPath}/scripts/v2/js': {
                    defaultJSExtensions: true,
                    defaultExtension: 'js'
                }
            }
        });

        (function ($) {
            Promise.all([
                SystemJS.import('menu/jquery-mega-menu'),
                SystemJS.import('nav-banner/jquery-fixed-nav-banner')
            ])
            .then(function () {
                $(function () {
                    var options = {
                       <%-- menuUrl: "${pageContext.request.contextPath}/scripts/v2/mock/menu-reale.json",--%>
                        menuUrl: "${pageContext.request.contextPath}/json/menuv2.htm",
                        templateUrl: "${pageContext.request.contextPath}/scripts/v2/templates/menu-item-template.html",
                        searchResultTemplateUrl: "${pageContext.request.contextPath}/scripts/v2/templates/search-result-template.html",
                    };    
                    
                    $('#menu-root').megaMenu(options);  
                    $('.main-nav-bar').fixedNavBanner(); 
                })
            });
    	}(jQuery));


        </script>
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
			<div id="navigation"><div id="help"><jsp:include page="../decorators_v3/help.jsp" /></div></div>
  			<decorator:body/>
		</div>
		<!-- end content -->
		<br />
	</div>
	<!-- end page -->
</div>
<!-- end wrapper -->
</body>
</html>
