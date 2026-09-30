<%@ page import="it.gruppoinit.nlapec.util.PECMessage" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>NLA PEC</title>
	</head>
	<body>
		<h1>NLA PEC</h1>
		<h3>Esito processamento:</h3>
		<%
			List list = (List)request.getAttribute("list"); 
			if(list!=null){
			    out.print("<br />");
				out.print("------------------------------");
				out.print("<br />");
			    out.print("<b>PEC processate: </b>" + list.size());
			    out.print("<br />");
				out.print("------------------------------");
				out.print("<br />");
			    for(int i = 0;i < list.size();i++){
					PECMessage pec = (PECMessage)list.get(i);
					out.print("<b>Id: </b>" + pec.getId());
					out.print("<br />");
					out.print("<b>From: </b>" + pec.getFrom()[0]);
					out.print("<br />");
					out.print("<b>Subject: </b>" + pec.getSubject());
					out.print("<br />");
					out.print("<b>Date: </b>" + pec.getDate());
					out.print("<br />");
					out.print("<b>------------------------------</b>");
					out.print("<br />");
			    }
			}
		%>	
	</body>
</html>