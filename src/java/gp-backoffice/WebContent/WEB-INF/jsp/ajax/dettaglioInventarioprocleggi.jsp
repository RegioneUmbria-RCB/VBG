<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="inventarioprocedimenti" />
	</jsp:include>
	<div id="subcontent">
	<table>
		<tr>
			<td><fmt:message key="label.riferimento" /></td>
			<td><input type="text" id="riferimenti_id${inventarioprocedimenti.inventarioprocLeggi.id.codice }" value="${inventarioprocedimenti.inventarioprocLeggi.riferimenti}" size="90" maxlength="100"/></td>
		</tr>
	</table>
	</div>
	<div id="functions">
	<ul>
		<li>
		<a href="javascript:doSubmit('updateInventarioprocLeggi.htm?codice=${inventarioprocedimenti.inventarioprocLeggi.id.codice }&riferimenti='+$('riferimenti_id${inventarioprocedimenti.inventarioprocLeggi.id.codice }').value,'',document.inviodati)"><fmt:message
			key="button.update" /></a>
		</li>
	</ul>
	</div>
</spring-form:form>