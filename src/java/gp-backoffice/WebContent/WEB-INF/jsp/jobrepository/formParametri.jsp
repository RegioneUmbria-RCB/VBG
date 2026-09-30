<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="jobrepository.label.dettaglio.title" />

</title>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="jobrepository.label.dettaglio.title" />

	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="jobrepository" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="jobrepository" />
			</jsp:include>
			<table>
				<c:forEach items="${jobrepository.jobRepositoryParams}"
					var="jobRepParam" varStatus="a">
					<tr>
						<td>${jobRepParam.etichetta}
							<td><spring:bind path="jobRepositoryParams[${a.index}].id">
									<input type="hidden" name="${status.expression}"
										value="${status.value}" />
								</spring:bind> <spring:bind path="jobRepositoryParams[${a.index}].valore">
									<input name="${status.expression}" value="${status.value}" size="100">
								</spring:bind></td>
							<td>${jobRepParam.descrizione}</td>
					</tr>
					
				</c:forEach>
                <tr><td colspan="2">(*) Campo Obbligatorio</td></tr>



			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${isView eq 0}">
				<li><a
					href="javascript:doSubmit('insertParametri.htm','',document.inviodati)"><fmt:message
							key="button.insert" /></a></li>
			</c:if>
			<c:if test="${isView eq 1}">
				<li><a
					href="javascript:doSubmit('updateParametri.htm','',document.inviodati)"><fmt:message
							key="button.update" /></a></li>
				<li><a
					href="javascript:doSubmit('deleteParametri.htm?codice=${jobrepository.id}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
							key="button.delete" /></a></li>
			</c:if>
			<li><a
				href="javascript:doHref('view.htm?codice=${jobrepository.id}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>