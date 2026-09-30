<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title><fmt:message key="form.report.titolo" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="form.report.titolo" /></span>
<div id="subcontent">
<spring-form:form commandName="file" name="inviodati" enctype="multipart/form-data">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="file" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.report.nomefile" /></td>
			<td><input name="file" type="file" size="100" tabindex="0"/></td>  
		</tr>
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
<li><a href="javascript:doSubmit('uploadReport.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
<li><a href="javascript:doHref('../leggi/list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>