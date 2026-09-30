<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", 0); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message	key="form.oggettifilesystem.output.titolo" /></title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="form.oggettifilesystem.output.titolo" ></fmt:message>
	</span>
	<div id="subcontent">
	<div>
	
	Minimo: <b>${status.minCodiceOggetto}</b>
	<br />Massimo: <b>${status.countTotal}</b>
	<br />Attuale: <b>${status.codiceOggettoCorrente}</b>
	<br/> totale elaborati: <b>${status.countHandled}</b>
	
	</div>
	
		<fmt:message key="form.oggettifilesystem.output.msg1" >
			<fmt:param value="${status.countHandled}"></fmt:param>
		</fmt:message><br/>
		<fmt:message key="form.oggettifilesystem.output.msg2" >
			<fmt:param value="${status.countMoved}"></fmt:param>
		</fmt:message><br/>
		<fmt:message key="form.oggettifilesystem.output.msg3" >
			<fmt:param value="${status.countErrors}"></fmt:param>
		</fmt:message><br/>
		<fmt:message key="form.oggettifilesystem.output.msg4" >
			<fmt:param value="${status.countWarnings}"></fmt:param>
		</fmt:message><br/>
		<c:if test="${status.countWarnings > 0}">
			<br />
			<span class="titoloTabella"><fmt:message key="form.oggettifilesystem.output.warnings.label" ></fmt:message></span>
<!--			<form name="oggettifilesystemErrorListForm" action="export.htm">-->
			<div class="jmesa">
			<table class="table" border="0"  cellpadding="0"  cellspacing="0"  >
				<thead>
					<!-- 
					<tr class="toolbar" >
						<td colspan="3" >
						<table border="0"  cellpadding="0"  cellspacing="1" >
							<tr>
								<td><a href="javascript:exportErrors();"><img src="${pageContext.request.contextPath}/images/upgr/excel.gif"  title="Estrazione in formato XLS"  alt="excel" /></a></td>
							</tr>
						</table>
						</td>
					</tr>
					 -->
					<tr class="header">
						<td width="15%"><fmt:message key="form.oggettifilesystem.output.error.codiceoggeto.label" /></td>
						<td><fmt:message key="form.oggettifilesystem.output.error.errordesc.label" /></td>
						<td><fmt:message key="form.oggettifilesystem.output.error.exception.label" /></td>
					</tr>
				</thead>
				<tbody class="tbody">
					<% int warningCount = 0; %>
					<% String rowClass = ""; %>
					<c:forEach items="${status.warnings}" var="error">
						<% 
							warningCount++; 
							rowClass = warningCount % 2 == 1 ? "odd" : "even";
						%>
						<tr class="<%= rowClass %>" onmouseover="this.className='highlight'" onmouseout="this.className='<%= rowClass %>'" >
							<td>${error.codiceOggetto}</td>
							<td>${error.errorMessage}</td>
							<td>
								<c:if test="${error.exception != null}">
									<span>${error.exception.class.name}</span><br/>
									<span>${error.exception.message}</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tr class="statusBar" >
					<td align="left"  colspan="3" >${status.countWarnings} warnings</td>
				</tr>
			</table>
			</div>
<!--			</form>-->
		</c:if>
		<c:if test="${status.countErrors > 0}">
			<br />
			<span class="titoloTabella"><fmt:message key="form.oggettifilesystem.output.errors.label" ></fmt:message></span>
			<form name="oggettifilesystemErrorListForm" action="exportErrors.htm">
			<div class="jmesa">
			<table class="table" border="0"  cellpadding="0"  cellspacing="0"  >
				<thead>
					<tr class="toolbar" >
						<td colspan="3" >
						<table border="0"  cellpadding="0"  cellspacing="1" >
							<tr>
								<td><a href="javascript:exportErrors();"><img src="${pageContext.request.contextPath}/images/upgr/excel.gif"  title="Estrazione in formato XLS"  alt="excel" /></a></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr class="header">
						<td width="15%"><fmt:message key="form.oggettifilesystem.output.error.codiceoggeto.label" /></td>
						<td><fmt:message key="form.oggettifilesystem.output.error.errordesc.label" /></td>
						<td><fmt:message key="form.oggettifilesystem.output.error.exception.label" /></td>
					</tr>
				</thead>
				<tbody class="tbody">
					<% int errorCount = 0; %>
					<% String rowClass = ""; %>
					<c:forEach items="${status.errors}" var="error">
						<% 
							errorCount++; 
							rowClass = errorCount % 2 == 1 ? "odd" : "even";
						%>
						<tr class="<%= rowClass %>" onmouseover="this.className='highlight'" onmouseout="this.className='<%= rowClass %>'" >
							<td>${error.codiceOggetto}</td>
							<td>${error.errorMessage}</td>
							<td>
								<c:if test="${error.exception != null}">
									<span>${error.exception.class.name}</span><br/>
									<span>${error.exception.message}</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tr class="statusBar" >
					<td align="left"  colspan="3" >${status.countErrors} errori</td>
				</tr>
			</table>
			</div>
			</form>
		</c:if>
		</div>
		<script type="text/javascript">
			/*
			var _jmesaUrl='displayResults.htm?';
			var _captionTab='<fmt:message key="upgr.upgrade.complete.errorlist.title" />';
			*/
			function exportErrors(){
				document.forms['oggettifilesystemErrorListForm'].submit();
			}
		</script>
	
	</body>
</html>