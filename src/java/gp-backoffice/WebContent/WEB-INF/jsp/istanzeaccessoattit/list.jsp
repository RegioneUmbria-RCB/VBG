<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.accesso_atti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.accesso_atti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanzeaccessoattit/list"/>
    </jsp:include>
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanza.id.codice}</c:param>
	</c:import>
	<div id="subcontent">
		
			<table class="vbg-table">
				<thead>
					<tr>
						<th width="1%"><fmt:message key="label.codice"/></th>
						<th width="60%"><fmt:message key="label.descrizione_fascicolo"/></th>
						<th width="5%"><fmt:message key="label.data_inizio"/></th>
						<th width="5%"><fmt:message key="label.data_fine"/></th>
						<th><fmt:message key="label.operatore"/></th>
						<th width="1%"><fmt:message key="label.pubblica"/></th>
						<th width="1%"><fmt:message key="label.edit.record"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${istanzeaccessoattitList}" var="istanzaaccessoattit_var">
						<tr>
							<td><a href="javascript:historySet('${_urlback}','../istanzeaccessoattit/view.htm?codice=${istanzaaccessoattit_var.id.codice}','')">${istanzaaccessoattit_var.id.codice}</a></td>
							<td>${istanzaaccessoattit_var.descrizioneFascicolo}</td>
							<td><fmt:formatDate value="${istanzaaccessoattit_var.datainizio}" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" /></td>
							<td><fmt:formatDate value="${istanzaaccessoattit_var.datafine}" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" /></td>
							<td>${istanzaaccessoattit_var.responsabili.responsabile}</td>
							<td>
								<c:choose>
									<c:when test="${ istanzaaccessoattit_var.flgPubblica eq true }">
										<fmt:message key="label.si"/>
									</c:when>
									<c:otherwise>
										<fmt:message key="label.no"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../istanzeaccessoattit/view.htm?codice=${istanzaaccessoattit_var.id.codice}','')" title="<fmt:message key="label.edit.record" />${istanzaaccessoattit_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>							
							</td>					
						</tr>
					</c:forEach>				
				</tbody>
				</table>		
		
		
	</div>
	<div class="form-button" style="margin-top: 15px;">
		<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../istanzeaccessoattit/create.htm?codiceIstanza=${istanza.id.codice}', '')"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		
	</div>
</body>
</html>