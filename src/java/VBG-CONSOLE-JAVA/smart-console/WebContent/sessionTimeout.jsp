<%@ page isErrorPage="true"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type; pragma" content="text/html; charset=UTF-8; no-cache" />
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles/standard.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
	<style type="text/css">
		#functions {
			width: 70px;
			margin-left: auto;
  			margin-right: auto;
		}
	</style>
	<title>Sessione scaduta</title>
</head>
<body>
	<%
		//recupero i cookie necessari alla corretta gestione dell'url back
		Cookie cookies[] = request.getCookies();
		Cookie cookieIdcomunealias = null;
		Cookie cookieIdente = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(WebConstants.IDCOMUNE_ALIAS)) {
				    cookieIdcomunealias = cookies[i];					
				}
				if (cookies[i].getName().equals(WebConstants.IDENTE_SDEPROXY)) {
				    cookieIdente = cookies[i];
				}
			}
		}
		String cookieIdcomunealiasValue = (cookieIdcomunealias == null) ? "" : cookieIdcomunealias.getValue();
		String cookieIdenteValue = (cookieIdente == null) ? "" : cookieIdente.getValue();
		String urlBack = request.getContextPath()+"/welcome/start.htm?"+WebConstants.IDCOMUNE_ALIAS+"="+cookieIdcomunealiasValue+"&"+WebConstants.IDENTE_SDEPROXY+"="+cookieIdenteValue;
	%>
	<br />
	<center>	
		<div style="width: 80%; color: gray;">
			<fieldset>
				<legend>
					<div>
						<img src="${pageContext.request.contextPath}/images/big_error.gif" align="middle" alt="Errore"></img>
					</div>
				</legend>
				<br />
				<div style="font-family: Arial; font-size: 20px; font-weight: bold;">SESSIONE SCADUTA</div>
				<br />
			</fieldset>
		</div>
		<br />
		<input type="button" onclick="doStartAfterSessionExpired('<%=urlBack%>');" value="Login" style="background-color: #B61218; color: white;" />
	</center>
</body>
</html>
<%session.invalidate(); %>
