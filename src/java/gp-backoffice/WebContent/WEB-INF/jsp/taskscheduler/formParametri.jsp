<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="taskscheduler.label.dettaglio_parametri.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="taskscheduler.label.dettaglio_parametri.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="taskscheduler" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="taskscheduler" />
	    </jsp:include>
	    <div class="vbg-form">
	        <fieldset>
	            <legend>${taskscheduler.entity.descrizione}</legend>
	            <table class="vbg-table">
	                <thead>
	                    <tr>
	                        <th><fmt:message key="label.parametro" /></th>
	                        <th><fmt:message key="label.valore" /></th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <%Integer tabIndex = 1;%>
	                    <c:forEach items="${taskscheduler.taskschedulerparametriHelpers}" var="parametri_var" varStatus="a">
		                    <tr>
		                        <td>${parametri_var.taskschedulerparametri.id.parametro}</td>
		                        <td>
	                                <spring:bind path="taskschedulerparametriHelpers[${a.index}].taskschedulerparametri.valore">
	                                    <input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>                        
	                                </spring:bind>      
	                                <init:help idHelp="help${a.index}" text="${parametri_var.descrizione}"/>
		                        </td>
		                    </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </fieldset>
	        <div>
	            <a class="btn btn-primary" href="javascript:doSubmit('saveParametri.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
	            <a class="btn btn-secondary" href="javascript:doHref('view.htm?codice=${taskscheduler.entity.id.codice}','')"><fmt:message key="button.back" /></a>
	        </div>
	    </div>
	</spring-form:form>		
</div>
</body>
</html>
