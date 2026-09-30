<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<c:if test="${jobrepository.id==null}">
	<fmt:message key="jobrepository.label.nuovo.title" />
	</c:if>
	<c:if test="${jobrepository.id!=null}">
	<fmt:message key="jobrepository.label.dettaglio.title" />
	</c:if>
</title>
</head>
<body>
<span class="titoloPagina">
	<c:if test="${jobrepository.id==null}">
	<fmt:message key="jobrepository.label.nuovo.title" />
	</c:if>
	<c:if test="${jobrepository.id!=null}">
	<fmt:message key="jobrepository.label.dettaglio.title" />
	</c:if>
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
		<c:if test="${jobrepository.id!=null}">
		<tr>
			<td><fmt:message key="label.codice" /></td>
			<td>
				<spring-form:input path="id" size="5" readonly="true" />				
			</td>
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="jobrepository.jobName" /></td>
			<td>
				<spring-form:input path="jobName" size="50" /> 
				<spring-form:errors path="jobName" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.description" /></td>
			<td>
				<spring-form:input path="description" size="100" /> 
				<spring-form:errors path="description" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.idcomunealias" /></td>
			<td>
				<spring-form:input path="alias" size="20" /> 
				<spring-form:errors path="alias" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.modulo" /></td>
			<td>
				<spring-form:input path="modulo" size="2" /> 
				<spring-form:errors path="modulo" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.triggerType" /></td>
			<td>
				<spring-form:select path="triggerType">
					<spring-form:option value="CRON"></spring-form:option>
					<spring-form:option value="SIMPLE"></spring-form:option>
				</spring-form:select> 
				<spring-form:errors path="triggerType" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.cronExpression" /></td>
			<td>
				<spring-form:input path="cronExpression" size="20" /> 
				<spring-form:errors path="cronExpression" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.startDelay" /></td>
			<td>
				<spring-form:input path="startDelay" size="20" /> 
				<spring-form:errors path="startDelay" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.repeatInterval" /></td>
			<td>
				<spring-form:input path="repeatInterval" size="20" /> 
				<spring-form:errors path="repeatInterval" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.jobClassName" /></td>
			<td>
				<spring-form:input path="jobClassName" size="60" /> 
				<spring-form:errors path="jobClassName" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="jobrepository.label.active" /></td>
			<td><spring-form:checkbox path="active" value="1" /></td>
		</tr>
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${jobrepository.id==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${jobrepository.id!=null}">
		<li><a href="javascript:doSubmit('update.htm?codice=${jobrepository.id}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm?codice=${jobrepository.id}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>