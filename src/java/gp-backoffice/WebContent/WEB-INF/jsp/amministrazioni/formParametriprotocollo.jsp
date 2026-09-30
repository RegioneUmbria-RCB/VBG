<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="amministrazioni.label.parametro_protocollo" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="amministrazioni.label.parametro_protocollo" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../amministrProtocollo/listparametriprotocollo" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="amministrProtocollo" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="amministrProtocollo" />
    </jsp:include>
    <table>
        <c:if test="${amministrProtocollo.id.codice==null}">
        <tr>
			<td><fmt:message key="label.comune" /></td>
			<td>
			    <spring-form:select  path="comuni.codicecomune">
			     	<option value="">Tutti</option>
			        <c:forEach items="${responsabilicomunis}" var="respcomuni">
			       		<option value="${respcomuni.comune.codicecomune}">${respcomuni.comune.comune}</option>
			        </c:forEach>
			  </spring-form:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.software" /></td>
			<td>
			    <spring-form:select  path="software.codice">
			     	<%-- <option value="">Tutti</option>--%>
			        <c:forEach items="${responsabilisoftwares}" var="respsoftware">
			       		<option value="${respsoftware.software.codice}">${respsoftware.software.descrizione}</option>
			        </c:forEach>
			  </spring-form:select>
			</td>
		</tr>
		</c:if>
		<c:if test="${amministrProtocollo.id.codice!=null}">
		<tr>
			<td><fmt:message key="label.comune" /></td>
			<td>
				<b>
				<c:choose>
					<c:when test="${not empty amministrProtocollo.comuni.comune}">${amministrProtocollo.comuni.comune}
					</c:when>
					<c:otherwise><fmt:message key="label.tutti" /></c:otherwise>
				</c:choose>
				</b>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.software" /></td>
			<td>
				<b>${amministrProtocollo.software.descrizione}</b>
								
			</td>
		</tr>

		</c:if>
		
        <tr>
			<td><fmt:message key="label.protUo" /></td>
			<td><spring-form:input id="protUo_id" path="protUo" size="20" />
			<init:help idHelp="help4" textKey="help.protocollo_unita_organizzativa"/>
			<spring-form:errors path="protUo" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="amministrazioni.label.protRuolo" /></td>
			<td><spring-form:input id="protRuolo_id" path="protRuolo" size="20" />
			<init:help idHelp="help5" textKey="help.protocollo_ruolo"/>
			<spring-form:errors path="protRuolo" cssClass="error"/></td>
		</tr>
	</table>
    
    
</spring-form:form>
</div> 
<div id="functions">
<ul>
	<c:if test="${amministrProtocollo.id.codice==null}">
		<li><a href="javascript:doSubmit('insertParametriProtocollo.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
    <c:if test="${amministrProtocollo.id.codice!=null}">
    
        <li><a href="javascript:doSubmit('updateParametriProtocollo.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>	
        <li><a href="javascript:doSubmit('deleteParametriProtocollo.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>	
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
