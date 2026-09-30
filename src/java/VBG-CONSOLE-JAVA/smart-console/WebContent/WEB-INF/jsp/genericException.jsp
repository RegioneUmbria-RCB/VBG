<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="org.slf4j.LoggerFactory"%>
<%@ page import="org.slf4j.Logger"%>
<%@ page import="it.gruppoinit.pal.gp.backoffice.web.WelcomeController"%>
<%@ page isErrorPage="true" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.net.URLDecoder" %>

<%@ include file="includes/taglibs.jsp" %>
<% final Logger log = LoggerFactory.getLogger("generic-exception"); %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type; pragma" content="text/html; charset=UTF-8; no-cache" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.3.2.min.js"></script>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/gruppoinit.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/dojo/dijit/themes/nihilo/nihilo.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles/standard.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
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
		function sendMailTo(){
			var nodes = document.getElementById("err_table").getElementsByTagName("td");
			var text = "";
			for(var i=0;i<nodes.length;i++){
				text += escape(nodes[i].innerHTML)+'%0A';
			}
			text = text.substring(0,1500);
			location.href="mailto:<spring:message code='mail.assistenza.applicativo' />?subject=Richiesta assistenza&body="+text+"";
		}
		function viewErrDetail(){
			var el = document.getElementById('err_table');
			showHideElement(el);
		}
		function goBack(useHistory){
			if(window.opener!=null){
				self.close();
			}else{
				if(useHistory){
					historyBack('');
				}else{
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
					<div>
						<img src="${pageContext.request.contextPath}/images/big_error.gif" align="middle" alt="Errore"></img>
					</div>
				</legend>
				<div style="font-family: Arial; font-size: 24px; font-weight: bold;">ERRORE</div>
				<div style="padding: 50px">
					<cite style="font-weight: bold;">
						<c:out escapeXml="true" value="${pageContext.exception.message}" default="L'applicazione ha generato un errore inatteso."/>
					</cite>
				</div>
			</fieldset>
		</div>
		<div id="functions">
			<ul style="align: center">
				<c:if test="${not empty HistoryBuffer }">
					<li><a href="javascript:goBack(true)" title="Torna indietro">Indietro</a></li>
				</c:if>
				<c:if test="${empty HistoryBuffer }">
					<li><a href="javascript:goBack(false)" title="Torna indietro">Indietro</a></li>
				</c:if>
				<li><a href="javascript:viewErrDetail()" title="Visualizza dettaglio errore">Dettaglio</a></li>
			</ul>
		</div>
		<br />
		<br />
		<table id="err_table" border="1" width="80%" style="display: none;">
			<tr>
				<td colspan='2' style='text-align: center'>DETTAGLIO ERRORE (da comunicare al servizio assistenza)
				<c:set var="mailassistenza"><spring:message code="mail.assistenza.applicativo" /></c:set>
				<c:if test="${not empty mailassistenza}">
					<br/><b class="error">${mailassistenza}</b>
				</c:if></td>
			</tr>
			<tr valign="top">
				<td>Version</td>
				<td>App: ${app_version} - DB: ${db_version}</td>
			</tr>
			<tr valign="top">
				<td>IP</td>
				<td>${pageContext.request.localAddr}</td>
			</tr>
			<tr valign="top">
				<td>DNS</td>
				<td>${pageContext.request.localName}</td>
			</tr>
			<tr valign="top">
				<td>Port</td>
				<td>${pageContext.request.localPort}</td>
			</tr>
			<tr valign="top">
				<td>HTTP</td>
				<td>${pageContext.request.method}</td>
			</tr>
			<tr valign="top">
				<td>URI</td>
				<td>${pageContext.errorData.requestURI}</td>
			</tr>
			<tr valign="top">
				<td>QueryString</td>
				<td>${pageContext.request.queryString}</td>
			</tr>
			<tr valign="top">
				<td>Status</td>
				<td>${pageContext.errorData.statusCode}</td>
			</tr>		
			<tr>
				<td valign="top">Headers</td>
				<td>
					<c:forEach items='${header}' var='h'>
			         <ul>
			            <li>Header Name: <c:out value='${h.key}'/></li>
			            <li>Header Value: <c:out value='${h.value}'/></li>
			         </ul>
      				</c:forEach>
				</td>
			</tr>
			<tr>
				<td valign="top">Message</td>
				<td>
				<cite style="font-weight: bold;">
					<c:out escapeXml="true" value="${pageContext.exception}" default="L'applicazione ha generato un errore inatteso."/>
					<% log.error("Errore visualizzato ed inviato per email (se configurato):",pageContext.getException()); %>
				</cite>
				</td>
			</tr>
			<tr>
				<td valign="top">Stack</td>
				<td>
				<cite>
					<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
						${trace}<br />
					</c:forEach>
				</cite>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
