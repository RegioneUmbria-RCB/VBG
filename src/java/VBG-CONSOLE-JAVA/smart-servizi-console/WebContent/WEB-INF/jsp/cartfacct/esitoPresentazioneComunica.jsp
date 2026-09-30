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
<title>Esito Presentazione Pratica</title>
</head>
<%
String errMsg = (String) request.getAttribute("error");
%>
<body>
	<div class="titolo">Esito Presentazione Pratica</div>
	<div class="descrizione">
		<%
		if(null != errMsg){
		    
		%>
		<div class="error" style="font-size: 14px;">
			Si è verificato un errore durante l'invio della domanda.			
			<div style="padding: 20px; border: 1px solid black;">
				Dettaglio:
				<div>  
					<%= errMsg %>
				</div>
			</div>
		</div>
		<%
		}else{
		%>
		
		<div style="font-size: large; padding: 20px;">La pratica è stata presentata correttamente e trasmessa a STAR WEB. 
			<br />
				Si prega di accedere su quest'ultimo per il completamento degli adempimenti necessari alla trasmissione della istanza prodotta al SUAP di riferimento</div>		
		<%
		}
		%>
		</div>
		<br />
		
	<%@ include file="../includes/alert.jsp" %>
</body>
</html>