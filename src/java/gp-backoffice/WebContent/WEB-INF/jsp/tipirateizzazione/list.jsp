<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.oneritipirateizzazione.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.oneritipirateizzazione.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipirateizzazione/list" />
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
				<table class="vbg-table">
					<thead>
						<tr>							
							<th><fmt:message key="label.codice"/></th>
							<th><fmt:message key="label.descrizione"/></th>
							<th><fmt:message key="label.azioni"/></th>					
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lista}" var="rateizzazione">
							<tr>								
								<td>
									<a href="javascript:historySet('${_urlback }','../tipirateizzazione/view.htm?codice=${rateizzazione.id}','') ">
									${rateizzazione.id}
									</a></td>
								<td>${rateizzazione.descrizione}</td>
								<td><i class="fa fa-times vbg-link fa-lg togli-rateizzazione azione"></i></td>
							<tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>