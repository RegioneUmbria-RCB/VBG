<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
		<fmt:message key="label.autorizzazioni.scegliautorizzazione" />		
	</title>
</head>
<body>
	<span class="titoloPagina">		
			<fmt:message key="label.autorizzazioni.scegliautorizzazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/viewOperazioni" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="istanzaAutConcHelper" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="domain" />
		    </jsp:include>
	<div class="jmesa">	    
	<fieldset><legend><fmt:message key="label.autorizzazioni" /></legend>
		<table class="table" width="100%">
			<thead>
				<tr class="header">
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.stato"/></td>
					<td width="8%"><fmt:message key="label.edit.record"/></td>
				</tr>
			</thead>
			<tbody>
				<%
				    int j=1;
				%>
				<c:forEach items="${istanzaAutConcHelper.autorizzazioni}" var="curr_auth" >
				<tr class="<%=(j%2)==0?"odd":"even"%>">
					<td>${curr_auth.autoriznumero }</td>
					<td><fmt:formatDate value="${curr_auth.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td><fmt:formatDate value="${curr_auth.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${curr_auth.autorizcomune.comune}</td>
					<td>${curr_auth.tipologiaregistro.trDescrizione}</td>
					<td>
						<c:if test="${curr_auth.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<td>
						<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewOperazione.htm?idAutorizzazione=${curr_auth.id.codice}', '')" title="<fmt:message key="label.edit.record" /> ${curr_auth.id.codice }">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</td>
				</tr>
				<%
				    j++;
				%>
				</c:forEach>	
			</tbody>
		</table>
		</fieldset>
	</div>
	</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>