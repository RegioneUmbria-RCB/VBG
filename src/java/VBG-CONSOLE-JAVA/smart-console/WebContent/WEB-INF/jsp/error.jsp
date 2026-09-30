<%@ include file="includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Errore</title>
</head>
<body>
<div id="subcontent">
	<jsp:include page="includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="${commandName}" />
	</jsp:include>
</div>
<%
	String qs = "";
	String method = (String)request.getAttribute("method")+"?";
	java.util.Map queryStringParams = (java.util.Map)request.getAttribute("queryStringParams");
	java.util.Iterator it = queryStringParams.keySet().iterator();
	String currentKey = "";
	while(it.hasNext()){
	    currentKey = (String)it.next();
	    qs +=  currentKey + "=" + queryStringParams.get(currentKey) + "&";
	}
	if(!qs.isEmpty()){
		qs = qs.substring(0,qs.lastIndexOf("&"));
	}
	pageContext.setAttribute("queryString",method+qs);
	
 %>
<div id="functions">
<ul>
	<li>
		<a href="javascript:doHref('${queryString}','')">
			<fmt:message key="button.back" />
		</a>
	</li>
</ul>
</div>
</body>
</html>