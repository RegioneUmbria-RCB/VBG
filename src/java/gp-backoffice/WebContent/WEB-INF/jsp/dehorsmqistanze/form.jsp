<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="label.mofifica_situazione_dehors" />
</title>
</head>
<body>
<span class="titoloPagina"> 
	<fmt:message key="label.mofifica_situazione_dehors" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
<spring-form:form commandName="autorizzazioniCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="autorizzazioniCommand" />
	</jsp:include>
	<c:set value="0" var="areareadonlyValue"></c:set>
	<c:if test="${autorizzazioniCommand.dehorsMqIstanze.id.codice !=null}">
	   <c:set value="1" var="areareadonlyValue"></c:set>
	</c:if>	
	<table>
	<c:if test="${autorizzazioniCommand.entity.flagAttiva eq false || autorizzazioniCommand.entity.flagAttiva==null}">
	<tr>
	    <td colspan="2" style="color: red; font-weight:  bold; "><fmt:message key="label.autorizzazione_cessata_impossibile_modificare_dehors" /></td>
	</tr>
	</c:if>
		<jsp:include page="../autorizzazioni/datiDehors.jsp">
			<jsp:param name="intestazione" value="0" />
			<jsp:param name="calcolarimanenza" value="0" />
			<jsp:param name="areareadonly" value="${areareadonlyValue}" />
		</jsp:include>	
	</table>
	<script type='text/javascript'>
	
    </script>
</spring-form:form>
</div>
<div id="functions">
<ul>

    <c:if test="${autorizzazioniCommand.entity.flagAttiva eq true}">
    <c:if test="${autorizzazioniCommand.dehorsMqIstanze.id.codice ==null}">
    	<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
    </c:if>
	<c:if test="${autorizzazioniCommand.dehorsMqIstanze.id.codice !=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	</c:if>
	<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>