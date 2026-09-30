<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet"></link>
<title>Esito Presentazione Domanda</title>
</head>
<%
String errMsg = (String) request.getAttribute("error");
%>
<body>
	<div class="titolo">Esito Presentazione Domanda</div>
	<div class="descrizione">
		<%
		if(null != errMsg){
		    
		%>
		<div class="error" style="font-size: 14px;">
			<%= errMsg %>
		</div>
		<%
		}else{
		%>
		<div style="font-size: large;">La domanda è stata presentata correttamente e trasmessa al circuito regionale.</div>
		
		<%
		}
		%>
		</div>
		<br />
		<c:if test="${not empty domandaPresentata}">
			<div class="inputForm">				
					<fieldset style="border: dotted; border-width: thin; "><legend>Riferimenti pratica telematica</legend>
						<div>
							<label>Codice pratica:</label>
								<span><b>${domandaPresentata.identificativodomanda}</b></span>
						</div>
						<div>
							<label>Data invio:</label>
								<span><b><fmt:formatDate value="${domandaPresentata.datainvio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></b></span>
						</div>
					</fieldset>
					<br />
					<label>
					Scarica il PDF della <a href="${pageContext.request.contextPath}/cart/ajaxDownloadPDFRicevuta.htm">ricevuta istanza</a>
					</label>	
					<br />
					<fieldset style="width: 100%; height: 600px; padding: 2px; overflow: auto">
						<object data="${pageContext.request.contextPath}/cart/ajaxDownloadPDFRicevuta.htm?no_dialog=true" type="application/pdf" style="width:100%; height:100%" standby="Caricamento file...">
						  	<p>Anteprima del documento non disponibile.</p>
						  	<p>
						  	
						  	</p>
						</object>	
					</fieldset>
			</div>
			<i>Per future referenze presso il comune siete pregati di stampare questa pagina</i>
		</c:if>
		<br />
		<br />
		<div>
		    	<input type="button" class="bottone-cart" onclick="document.location.href='<%=request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="Chiudi"/>
		</div>
	<%@ include file="../includes/alert.jsp" %>
</body>
</html>