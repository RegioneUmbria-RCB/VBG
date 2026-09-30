<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
<fmt:message key="alberoprocpeopleoper.label.dettaglio_alberoprocpeopleoper.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
<fmt:message key="alberoprocpeopleoper.label.dettaglio_alberoprocpeopleoper.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<div class="parametriDiv">
			<div class="etichetta">
	        	<div>
	        		${alberoprocpeopleoper.alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br /> 
	        	<div>
	        		${alberoprocpeopleoper.alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
       		</div>
       		<div class="clear"></div>
	<spring-form:form commandName="alberoprocpeopleoper" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="alberoprocpeopleoper" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="alberoprocpeopleoper.label.settore" /></td>
			<td><spring-form:input id="settore_id" path="settore" size="50" maxlength="50" />
			<init:help idHelp="help1" textKey="alberoprocpeopleoper.help.settore"/>
			<spring-form:errors path="settore" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="alberoprocpeopleoper.label.operazione" /></td>
			<td><spring-form:input id="operazione_id" path="operazione" size="50" maxlength="50"/>
			<init:help idHelp="help2" textKey="alberoprocpeopleoper.help.operazione"/>
			<spring-form:errors path="operazione" cssClass="error"/></td>
		</tr>
	</table>
	
	<script type='text/javascript'>
		$('settore_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${alberoprocpeopleoper.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${alberoprocpeopleoper.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?alberoproc.id.codice=${alberoprocpeopleoper.alberoproc.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
