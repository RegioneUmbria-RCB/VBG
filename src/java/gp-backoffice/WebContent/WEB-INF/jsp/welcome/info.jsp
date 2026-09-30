<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<?xml version="1.0" encoding="UTF-8" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.appname.extended" /></title>
</head>
<body>

<div id="subcontent">

  		<table width="100%">
  			<tr>
  				<td style="text-align: center;"> 	
					<b><fmt:message key="label.appname" /></b>
				</td>
			</tr>
			<tr>
	  			<td style="text-align: center;">
					<b><fmt:message key="label.appname.extended" /></b>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
				<fieldset><br />
					<c:forEach items="${softwareList}" var="software">
						<c:if test="${software.moduloopzionale eq true }">
							${software.descrizionelunga} <br />
						</c:if>	
					</c:forEach>
					<br />
				</fieldset>
			</td>
		</tr>
		<tr>
			<td  style="text-align: center;">	
				<b><fmt:message key="label.realizzato_da" />: <fmt:message key="label.brand" /></b>
			</td>
		</tr>
	</table>
</div>

</body>
</html>