<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<title><fmt:message key="label.appname"/> - <decorator:title/></title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/styles/standard.css" />	
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
	 
	 <%-- 
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.jmesa.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jmesa.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>
	--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.3.2.min.js"></script>
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajaxupload.js"></script> --%>
</head>
<body>
<%--
<!-- start header -->
<div id="header">
	
</div>
<!-- end header -->
<!-- start menu -->
<div id="menu">

</div>
 --%>
<!-- end menu -->
<!-- start wrapper -->
<%--
<div id="wrapper">
	<!-- start page -->
	<div id="page">
		<!-- start content -->
		 --%>
		<div id="content">			
  			<decorator:body/>
		</div>
		<%--
		<!-- end content -->
		<br />
	</div>
	<!-- end page -->
</div>
 --%>
<!-- end wrapper -->
</body>
</html>