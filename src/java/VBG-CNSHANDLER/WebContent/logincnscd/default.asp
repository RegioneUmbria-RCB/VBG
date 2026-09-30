<%
Response.expires = -10000
dim isDebug
dim isDebugStr
isDebug = false
isDebugStr = request("debug")
if isDebugStr <> "" then
	isDebug = true
end if
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<title>Post to CNS Handler...</title>
</head>
<body>

<form name="invio" method="post" action="../cnshandler/process?<%=Request.Querystring%>">
	
	<input type="hidden" name="subjectDN" value="<%=Request.ClientCertificate("Subject")%>"/>
	<input type="hidden" name="issuerDN" value="<%=Request.ClientCertificate("Issuer")%>"/>
	<%if isDebug then%>
		Issuer:<br><%=Request.ClientCertificate("Issuer")%><br> 
		Subject:<br><%=Request.ClientCertificate("Subject")%><br>
		<input type="submit" value="login"/>
	<%end if%>	
</form>
<%if not isDebug then%>
<script type="text/javascript">
	document.forms[0].submit();
</script>
<%end if%>
</body>
</html>