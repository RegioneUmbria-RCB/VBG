<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.dettaglio.elaborazione" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.dettaglio.elaborazione" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<table style="width:500px;">
		<tr class="titoloSezione">
			<td colspan="4">Stato</td>
		</tr>
		<tr>
			<td colspan="4">${dettelaborazione.stato}</td>
		</tr>
		
		<tr class="titoloSezione">
			<td colspan="4">Descrizione</td>
		</tr>
		<tr>
			<td colspan="4">${ricalcoloAreeDesc}</td>
		</tr>
		
		<tr class="titoloSezione">
			<td colspan="4">Data elaborazione</td>
		</tr>
		<tr>
			<td colspan="4">${formattedDate}</td>
		</tr>
		
		<tr class="titoloSezione">
			<td colspan="4">Totali</td>
		</tr>
		<tr>
			<td colspan="4">${dettelaborazione.totali}</td>
		</tr>
		
		<tr class="titoloSezione">
			<td colspan="4">Elaborati</td>
		</tr>
		<tr>
			<td colspan="4">${dettelaborazione.fatti}</td>
		</tr>
		
		<tr class="titoloSezione">
			<td colspan="4">Rimanenti</td>
		</tr>
		<tr>
			<td colspan="4">${dettelaborazione.dafare}</td>
		</tr>
	</table>
</div>
<div id="functions">
	<ul>
		<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		<li><a href="javascript:doHref('../istanzearee/goToDettaglioElaborazione.htm?idRicalcoloAree=${dettelaborazione.pk.id}','')"><fmt:message key="button.aggiorna" /></a></li>
	</ul>
</div>
</body>
</html>