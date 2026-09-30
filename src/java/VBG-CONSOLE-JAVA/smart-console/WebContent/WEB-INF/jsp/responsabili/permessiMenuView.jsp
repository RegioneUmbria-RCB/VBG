<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
		<title>
			<fmt:message key="form.responsabili.permessimenu.title" />: ${responsabile.responsabile}
		</title>
	</head>
	<body>		
		<span class="titoloPagina">
		<fmt:message key="form.responsabili.permessimenu.title" />: ${responsabile.responsabile}		
		</span>	
		<div id="subcontent">
			<spring-form:form commandName="responsabile" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
        			<jsp:param name="commandName" value="responsabile" />
    			</jsp:include>
				<table>
					<tr>
						<th>&nbsp;</th>
						<th><fmt:message key="form.responsabili.permessimenu.software" /></th>
						<th><fmt:message key="form.responsabili.permessimenu.descrizione" /></th>
						<th><fmt:message key="form.responsabili.permessimenu.attivo" /> (<c:out value="${fn:length(responsabile.menuAbilitati)}"/>/<c:out value="${fn:length(menu)}"/>)</th>
					</tr>
					
<spring:bind path="menuAbilitati">
	<c:forEach var="menu" items="${menu}" varStatus="counter">
	
						<tr>
							<td>${counter.count}</td>
							<td>${menu.software}</td>
							<td>${menu.descrizione}</td>
							<td>
								<input type="hidden" name="_${status.expression}" value="1"/>
								<%--// TODO controllare! --%>
								<c:set var="_checked" value="" />
								<c:forEach var="d" items="${responsabile.menuAbilitati}">
									<c:if test="${d.id == menu.id}"><c:set var="_checked" value="checked" /></c:if>
								</c:forEach>
								
								<input type="checkbox" name="${status.expression}" value="${menu.id}" ${_checked } />
								
							</td> 
						</tr>
	</c:forEach>
</spring:bind>
					
				</table>
			</spring-form:form>	
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doSubmit('permessiMenuUpdate.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doHref('view.htm?codice=${responsabile.id.codice}','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>