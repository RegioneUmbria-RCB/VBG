<%@ page import="it.gruppoinit.pal.gp.areariservata.filter.SitemeshDecoratorFilter" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page isErrorPage="true" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
		<link type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.9.2.custom.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$("input:button").button();
		});
		function doStartAfterSessionExpired(urlback){
			if(window.opener!=null){
				self.close();
			}else{
				if(urlback == null || urlback == ''){
					self.close();
				}else{
					location.href=urlback;
				}
			}
		}
		</script>
		<title>SESSIONE TERMINATA</title>
	</head>
	<body>
	<%
		Cookie cookies[] = request.getCookies();
		Cookie cookie = null;
		
		String urlBack = "";
		if (cookies != null) {
		    Map<String,Cookie> hmc = new HashMap<String, Cookie>(); 
			for (int i = 0; i < cookies.length; i++) {
			    hmc.put(cookies[i].getName(), cookies[i]);				
			}
		    cookie = hmc.get(SitemeshDecoratorFilter.AREARISERVATA_NETURL);
		    if(cookie == null){
				cookie = hmc.get("areariservata");
				String cookieValue = (cookie == null) ? "" : cookie.getValue();
				urlBack = request.getContextPath()+"/?"+cookieValue;
		    }else{
				// torna a url Back applicativo chiamante ES.: Area Riservata .NET
				urlBack = cookie.getValue();
		    }
		}

	%>
	<br />
	<center>	
		<div>
			<fieldset>
				<div><b>SESSIONE TERMINATA</b></div>
			</fieldset>
		</div>
		<br />
		<%--
			<input type="button" onclick="doStartAfterSessionExpired('<%=urlBack%>');" value="NUOVO ACCESSO" />
		 --%>
	</center>
</body>
</html>