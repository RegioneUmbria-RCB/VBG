<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isErrorPage="true"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ include file="includes/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
<meta http-equiv="Content-Type; pragma"
	content="text/html; charset=UTF-8; no-cache" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/jquery-1.3.2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/dojo/dojo/dojo.js"
	djConfig="parseOnLoad:true, isDebug:false"></script>
<script type="text/javascript">
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dojo.parser");
	dojo.require("dijit.Tree");
	dojo.require("dijit.Menu");
	dojo.require("dijit.Dialog");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.Tooltip");
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/scripts/dojo/dijit/themes/nihilo/nihilo.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles/standard.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/layouts/layout.css" />
<style type="text/css">
td {
	text-align: left;
}

#functions {
	width: 200px;
	margin-left: auto;
	margin-right: auto;
}
</style>
<title>Errore</title>
<script type="text/javascript">
	function sendMailTo() {
		var nodes = document.getElementById("err_table").getElementsByTagName(
				"td");
		var text = "";
		for ( var i = 0; i < nodes.length; i++) {
			text += escape(nodes[i].innerHTML) + '%0A';
		}
		text = text.substring(0, 1500);
		location.href = "mailto:<spring:message code='mail.assistenza.applicativo' />?subject=Richiesta assistenza&body="
				+ text + "";
	}
	function viewErrDetail() {
		var el = document.getElementById('err_table');
		showHideElement(el);
	}
	function goBack(useHistory) {
		if (window.opener != null) {
			self.close();
		} else {
			if (useHistory) {
				historyBack('');
			} else {
				history.back();
			}
		}
	}
</script>
</head>
<body>
	<br />
	<br />
	<center>
		<div style="width: 80%; color: gray;">
			<fieldset>
				<legend>

					<img src="${pageContext.request.contextPath}/images/big_error.gif"
						align="middle" alt="Errore"></img>

				</legend>
				<div style="font-family: Arial; font-size: 24px; font-weight: bold;">ERRORE</div>
				<div style="padding: 50px">
					<cite style="font-weight: bold;"> <fmt:message
							key="alert.bug_sessione_condivisa" />
					</cite>
				</div>
			</fieldset>
		</div>		
	</center>
</body>
</html>