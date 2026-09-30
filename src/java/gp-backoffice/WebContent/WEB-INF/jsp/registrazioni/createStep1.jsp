<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.registrazioni.title.search" />
	</title>
</head>
<body>
<span class="titoloPagina">
<fmt:message key="form.registrazioni.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>

<div id="subcontent">
	<spring-form:form commandName="registrazioniFilter" name="inviodati">
	    <jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="registrazioniFilter" />
	    </jsp:include>
	    <jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../registrazioni/createStep1" />
	    </jsp:include>
	<fieldset>
		<fmt:message key="form.registrazioni.createStep1.help">
			<fmt:param><fmt:message key="label.nuova_registrazione_manuale" /></fmt:param>
			<fmt:param><fmt:message key="label.nuova_registrazione_posteggi" /></fmt:param>
		</fmt:message>
	</fieldset>
	</spring-form:form>

</div>
<div id="functions">
<ul>
  <li><a tabindex="15" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../registrazioni/create.htm','');"><fmt:message key="label.nuova_registrazione_manuale" /></a></li>
  <li><a tabindex="15" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../registrazioni/createStepPosteggi.htm','');"><fmt:message key="label.nuova_registrazione_posteggi" /></a></li>
  <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>	
</ul>
</div>

	<script type='text/javascript'>
	//<![CDATA[
		
				
	//]]> 
	</script>		

</body>
</html>
