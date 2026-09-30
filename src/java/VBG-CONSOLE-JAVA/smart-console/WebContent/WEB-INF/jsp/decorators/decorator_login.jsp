<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<title>/SIGePro/ - <decorator:title default="no title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/layouts/login.css" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" /> 
	<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico" type="image/x-icon" />
	<script type='text/javascript' src="<%=request.getContextPath() %>/scripts/prototype.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/gruppoinit.js" ></script>
</head>
<body>
	<decorator:body/>
</body>
</html>
