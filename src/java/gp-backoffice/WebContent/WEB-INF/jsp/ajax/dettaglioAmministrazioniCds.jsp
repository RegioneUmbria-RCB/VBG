<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<spring-form:form commandName="cdsinvitati" name="ammCDSForm">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="cdsinvitati" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td><spring-form:textarea path="note" cols="30" rows="4" /></td>
		</tr>
	</table>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('updateDettaglioAmministrazione.htm','',document.ammCDSForm)"><fmt:message key="button.update" /></a></li>
	</ul>
	</div>
</spring-form:form>
<br class="clear" />