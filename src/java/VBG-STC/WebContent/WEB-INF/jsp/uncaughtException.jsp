<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type; pragma" content="text/html; charset=UTF-8; no-cache" />
	<style type="text/css">
		td {
			text-align: left;
		}
	</style>
	<title>STC - ERRORE</title>
</head>
<body>
	<br />
	<br />
	<center>
		<table width="800">
			<tr>
				<td style="text-align: center;"><b>STC - ERRORE</b></td>
			</tr>
		</table>
		<br />
		<br />
		<table id="err_table" border="1" width="80%">
			<tr>
				<td valign="top">Descrizione breve</td>
				<td>
				<cite>
				<%	
		 		    if(exception instanceof org.springframework.dao.DataIntegrityViolationException){
		 				out.print(((org.springframework.dao.DataIntegrityViolationException)exception).getMostSpecificCause());
		 		    }else{
						out.print(exception.toString());
		 		    }
		 		%>
				</cite>
				</td>
			</tr>
			<tr>
				<td valign="top">Descrizione dettagliata</td>
				<td>
				<cite>
				<%
		     		if (exception.getStackTrace() != null) {
		 				for(int i = 0; i < exception.getStackTrace().length; i++){
		     		    	out.println(exception.getStackTrace()[i].toString());
		 				}
		 			} else {
		 				out.print("ND");
		 			}
		 		%>
				</cite>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
