<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.util.IdComuneFilter"%>
<%@ page import="org.slf4j.LoggerFactory"%>
<%@ page import="org.slf4j.Logger"%>
<%@ page import="it.gruppoinit.pal.gp.backoffice.web.WelcomeController"%>
<%@ page isErrorPage="true" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.net.URLDecoder" %>

<%@ include file="includes/taglibs.jsp" %>
<% final Logger log = LoggerFactory.getLogger("generic-exception"); 


String mostraErroriStr  = (String)request.getSession().getAttribute(IdComuneFilter._MOSTRA_ERRORI_DETTAGLIATI);
boolean mostraErrori = mostraErroriStr!=null && mostraErroriStr.equalsIgnoreCase("true");

StringBuilder sb = new StringBuilder();	
StackTraceElement[] stackT = pageContext.getException().getStackTrace();
for(int i=0; i<stackT.length;i++){
    
    sb.append(stackT[i]).append("<br />");
}

String stackB64 = Base64.encodeBase64String(sb.toString().getBytes());
String errB64 = "";
if(pageContext.getException()!=null && pageContext.getException().getMessage()!=null){
    errB64  = Base64.encodeBase64String(pageContext.getException().getMessage().getBytes());
}
%>
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
	function revealErr(){
		
		let msg = atob(document.getElementById('errB64').innerText);
		let stack = atob(document.getElementById('stackB64').innerText);		
		let outputErroriDiv = document.getElementById('outputErrori');
		outputErroriDiv.innerHTML = `
		<div class="error_header" style="padding-top: 20px; font-size: 2.0em;">\${msg}</div> 
		<div style="text-align: left; padding-left:100px; border-style: dotted;">\${stack}</div>
		`;
	}	
		
	</script>
</head>
<body>


<c:set var="messaggioErrore" value="${pageContext.exception.message}" scope="page"/>


<%
out.print("<div style=\"display: none\" id=\"errB64\">" + errB64+"</div>");
out.print("<div style=\"display: none\" id=\"stackB64\">" + stackB64+"</div>");

%>

<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
	
		<c:choose>
		
			<c:when test="${fn:indexOf(pageContext.exception.message,'UncategorizedSQLException')>=0}">
				<c:set var="messaggioErrore" value="Errore generico" scope="page"/>

			</c:when>
			<c:when test="${fn:indexOf(pageContext.exception.message,'ORA-')>=0}">
				<c:set var="messaggioErrore" value="Errore generico" scope="page"/>

			</c:when>
			<c:when test="${fn:indexOf(pageContext.exception.message,'SQLState')>=0}">
				<c:set var="messaggioErrore" value="Errore generico" scope="page"/>

			</c:when>
			<c:when test="${fn:indexOf(pageContext.exception.message,'SQLError')>=0}">
				<c:set var="messaggioErrore" value="Errore generico" scope="page"/>

			</c:when>
			<c:when test="${fn:indexOf(pageContext.exception.message,'com.mysql')>=0}">
				<c:set var="messaggioErrore" value="Errore generico" scope="page"/>

			</c:when>
			<c:otherwise>
				
			</c:otherwise>
			
		</c:choose>
		
	

</c:forEach>

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
						<%if(mostraErrori){ %>
						<c:out escapeXml="true" value="${ messaggioErrore }" default="L'applicazione ha generato un errore inatteso."/>
						<% }else{ 
							%>
							L'applicazione ha generato un errore inatteso.
						<% } %>
					</cite>
					<div id="outputErrori"></div>
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
				<%if( mostraErrori){ %>
					<li><a href="javascript:viewErrDetail()" title="Visualizza dettaglio errore">Dettaglio</a></li>
				<% }else{ %>
					<li><a href="javascript:revealErr()" title="Visualizza dettaglio errore">Dettaglio</a></li>
				<% } %>
			</ul>
		</div>
		<br />
		<br />
		
		<%if( mostraErrori){ %>
		<table id="err_table" border="1" width="80%" style="display: none;">
			<tr>
				<td colspan='2' style='text-align: center'>DETTAGLIO ERRORE (da comunicare al servizio assistenza)
				<c:set var="mailassistenza"></c:set>
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
			<%--
			<tr valign="top">
				<td>QueryString</td>
				<td><c:out escapeXml="true" value="${pageContext.request.queryString}" /></td>
			</tr>
			 --%>
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
					<c:out escapeXml="true" value="${ messaggioErrore }" default="L'applicazione ha generato un errore inatteso."/>
					<% log.error("Errore visualizzato ed inviato per email (se configurato):",pageContext.getException()); %>
				</cite>
				</td>
			</tr>
			<%if( mostraErrori){ %>
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
			<% } %>
		</table>
		
		<% } %>
		
		
	</center>
</body>
</html>

