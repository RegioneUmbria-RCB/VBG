<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<spring-form:form commandName="configurazioneutenteCommand" name="innerForm">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="configurazioneutenteCommand" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="linkPreferitiUtente.descrizione" size="60" maxlength="150"/></td>
		</tr>
		<tr>
			<td><fmt:message key="label.url" />(*)</td>
			<td>
			    <spring-form:input id="url_id" path="linkPreferitiUtente.url" size="60" maxlength="255" /><br />
			</td>
			
		</tr>
		<%-- 
		<tr>
			<td><fmt:message key="label.target" /></td>
			<td><spring-form:input id="target_id" path="linkPreferitiUtente.target" size="20" maxlength="10" /></td>
		</tr>
		--%>
		
		<c:if test="${not empty configurazioneutenteCommand.linkPreferitiUtente.target}">
			<c:set value="checked=true" var="checked" scope="page"></c:set>
		</c:if>
		<c:if test="${empty configurazioneutenteCommand.linkPreferitiUtente.target}">
			<c:set value="" var="checked" scope="page"></c:set>
		</c:if>
		<tr>
			<td><fmt:message key="label.esterno" /></td>
			<%-- <td><spring-form:checkbox id="isEsternoTransient_id" path="linkPreferitiUtente.isEsternoTransient"  /></td> --%>
			<td><input type="checkbox" id="isEsternoTransient_id" name="linkPreferitiUtente.isEsternoTransient" value="1" ${checked} /></td>
			<td></td>
		</tr>
		<tr>
			<td><fmt:message key="label.ordine" /></td>
			<td><spring-form:input path="linkPreferitiUtente.ordine" size="5" /></td>
		</tr>
	</table>
	<div id="functions">
	<ul>
	    <c:if test="${configurazioneutenteCommand.linkPreferitiUtente.id.codice==null }">
	   		<li><a href="javascript:aggiungiLinkPreferito('')"><fmt:message key="button.aggiungi" /></a></li>
	    </c:if>
	    <c:if test="${configurazioneutenteCommand.linkPreferitiUtente.id.codice!=null }">
	    	<li><a href="javascript:aggiungiLinkPreferito(${configurazioneutenteCommand.linkPreferitiUtente.id.codice })"><fmt:message key="button.update" /></a></li>
	    </c:if>
	</ul>
	</div>
	<br /><br />
	<table style="padding-top: 20px;">
		<tr>
			<td><fmt:message key="help.inserimento_url_preferiti" /></td>
		</tr>
		<tr>
			<td><img alt="Esempio link interno" src="${pageContext.request.contextPath}/images/link_indirizzi.gif" /> </td>
		</tr>
	</table>
</spring-form:form>