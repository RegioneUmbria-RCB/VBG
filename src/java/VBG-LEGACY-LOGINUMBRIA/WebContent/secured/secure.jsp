<%@ page import="java.util.Enumeration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Secured</title>
	<script type="text/javascript">
		function logout(token){
			var loginurl = "<%=request.getScheme()%>://<%=request.getLocalName()%>:<%=request.getLocalPort()%><%=request.getContextPath()%>/";
			document.location.href="logout?Token="+token+'&return_to='+loginurl;
			return;
		}
	</script>
</head>
<body>
	<%
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
		    String key = (String)en.nextElement();
		    out.print(key+" = "+request.getParameter(key));
		    out.print("<br />");
		}
	%>
	<input type="button" value="Logout" onclick="logout('<%=request.getParameter("Token")%>');"/>
</body>
</html>