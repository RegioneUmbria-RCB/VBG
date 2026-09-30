<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.areariservata.filter.IdComuneFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%



String mostraErroriStr  = (String)request.getSession().getAttribute(IdComuneFilter._MOSTRA_ERRORI_DETTAGLIATI);
boolean mostraErrori = mostraErroriStr!=null && mostraErroriStr.equalsIgnoreCase("true");


%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>ERRORE</title>
		<link type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.9.2.custom.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript">
		$(document).ready(function () {
			$("button").button();			
		});
		
		function showErr(){
			$('#error_detail_func_show').hide();
			$('#error_detail_func_hide').show();
			$('#error_detail').show();
		}
		function hideErr(){
			$('#error_detail_func_show').show();
			$('#error_detail_func_hide').hide();
			$('#error_detail').hide();
		}
		</script>
	</head>
	<body>
		<div>
			<center>
				<fieldset><legend><b>ERRORE</b></legend>		
					<label><b>Attenzione! Si è verificato un errore imprevisto.</b></label>		
				</fieldset>
			</center>
		</div>
<%if(mostraErrori){ %>
		<div id="error_detail_func_show">
			<button onclick="showErr()">Mostra dettaglio</button>
		</div>
		<div id="error_detail_func_hide" style="display: none;">
			<button onclick="hideErr()">Chiudi dettaglio</button>
		</div>
		
		<div id="error_detail" style="display: none; margin-top: 5px">
			<fieldset>	
				<label><b>Message</b></label><br />
				<cite id="message">
					<c:out value="${pageContext.exception.message}" default="-" escapeXml="false" />
				</cite>
				<br />
				<label><b>Stack</b></label><br />
				<cite id="stack">
					<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
						${trace}<br />
					</c:forEach>
				</cite>
				<label><b>Cause</b></label><br />
				<cite id="cause">
					<c:out value="${pageContext.exception.cause}" default="-" escapeXml="false" />
				</cite>		
			</fieldset>
		</div>
<% } %>		
	</body>
</html>