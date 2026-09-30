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
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
        crossorigin="anonymous"></link>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
        crossorigin="anonymous"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/v2/bower_components/Yamm3/yamm/yamm.css"></link>
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/v3/css/style.css"></link>
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.35.3/es6-shim.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v2/node_modules/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v2/node_modules/jsrender/jsrender.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v2/node_modules/systemjs/dist/system.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/v3/js/sporvic_layout_adjust.js"></script>
	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/cart/smoothness/jquery-ui-1.11.1.custom.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/v3/layouts/layout.css" />
	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/v3/standard.css" />
	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/jScrollPane.css" />
 
	<%-- Java script per il calendario --%>
	<%-- ####################################################################################################### --%>
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/calendar/css/jscal2.css" />
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/calendar/css/border-radius.css" />
    <%--  ####################################################################################################### --%>
    
    
    <%-- <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" crossorigin="anonymous">--%>
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
	<script type="text/javascript">
		if (window!=top){
			top.location.href=location.href;
	    }
    </script>
		
	<script type="text/javascript">
		<!--
		jQuery(document).ready(function(){
			var vName = window.name;
			
				var jhqrPr = jQuery.ajax({
					  url: '../json/checkOpenTab.htm',
					  context: document.body,
					  cache: false,
					  data: "windowName="+vName,
					  dataType: "json",
					  success: function(data) {
						  if(data.errore){
							  if(document.location.pathname.indexOf('erroresessioneduplicata')<0){
							  	alert("Si sta cercando di aprire una nuova sessione.");
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
	</script>
	<script type="text/javascript">
	 window.history.forward();
     
	</script>
	

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

        </script>
        
       
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/hoverIntent.js"></script>

	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/prototype.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/scriptaculous/scriptaculous.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.jmesa.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jmesa.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js?<%=vJS %>"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/jscal2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/unicode-letter.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/calendar/js/lang/it.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jScrollPane-1.2.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload.js?<%=vJS %>"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jscripts/tiny_mce/tiny_mce.js"></script>
	
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-ready.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-modal.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-modal-attendere-prego.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-input-filters.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-validation.js?<%=vJS %>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-fieldset.js?<%=vJS %>"></script>
	
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	
	<decorator:head />
</head>
<body class="nihilo">

<!-- start wrapper -->
<div id="wrapper">
	<!-- start page -->
	<div id="page">
		<!-- start content -->
		<div id="content">
			<div id="navigation"><div id="help"><jsp:include page="../decorators_v3/help.jsp" /></div></div>
  			<decorator:body/>
		</div>
		<!-- end content -->
		<br />
	</div>
	<!-- end page -->
</div>



<jsp:include page="../decorators/scriptInizializzazioneDecorators.jsp" />


</body>
</html>