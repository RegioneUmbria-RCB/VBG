<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.riversa_pratiche_dossier" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.riversa_pratiche_dossier" /></span>
	 	
<div id="subcontent">
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<br class="clear" />
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="dossierCommand" />
	</jsp:include>
	
	<fieldset>
		<fmt:message key="label.descrizione_riversa_pratiche_dossier" />
	</fieldset>
	
	<br class="clear" />
	<select id="software_id${a.index}">
	   <c:forEach items="${softwareAttivi}" var="software" varStatus="a">
	   		<option  value="${software.codice}">${software.descrizione}</option>
	
	   </c:forEach>
    </select>
</div>

   <script type="text/javascript">

		function riversa()
		{
			
			var software = jQuery('#software_id').val();
			doHref('riversaPratiche.htm?softwareCode='+software,'');
		}
	
</script>

<br />
<div id="functions">
	<li><a href="javascript:riversa()"><fmt:message key="button.ok" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</div>

</body>
</html>