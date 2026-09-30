<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />	
        <title><decorator:title default="no title" /></title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
		<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
    </head>
    <body onload="window.print();">
    	<%SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_WITH_TIME_FORMAT_PATTERN); %>
        <%=sdf.format(new java.util.Date()) %> - <decorator:title default="no title" />
        <br/>
        <hr noshade="noshade" size="1"/>
        <br/>
        <decorator:body />
    </body>
</html>