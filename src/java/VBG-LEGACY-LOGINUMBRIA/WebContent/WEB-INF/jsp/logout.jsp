<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="it.gruppoinit.auth.util.AuthCostants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<style>
		.log{
			text-align: left;
		}
	</style>
	
	<title>Logout...</title>
</head>
<body>
	<center>
		<div style="font-family:Arial;width: 350px; height: 200px; background-color: #E5E5E5;padding 10px;">
         <h3>Logout effettuato con successo</h3>
         <br />
         <h2>Per maggior sicurezza chiudere il browser</h2>
         </div>
	</center>
</body>
</html>
<%session.invalidate();%>