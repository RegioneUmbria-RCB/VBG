<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_commissioni_edilizie" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_commissioni_edilizie" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${codiceIstanza}</c:param>
</c:import>	    
    
	<div id="subcontent">
	
		<table id="elementoListaBollettazioni_id" class="vbg-table">
			<thead>
				<tr>
					<th width="2%"><fmt:message key="label.numero_protocollo"/></th>
					<th><fmt:message key="label.data"/></th>
					<th><fmt:message key="label.descrizione"/></th>
					<th><fmt:message key="label.tipologia"/></th>
					<th><fmt:message key="label.stato"/></th>
					<th><fmt:message key="label.azioni"/></th>
				</tr>
			</thead>	
			<tbody>
				<c:forEach items="${listaCommissioniIstanza}" var="commissioniediliziet_var">
					<tr>
						<td>
							<a href="view.htm?codice=${commissioniediliziet_var.id.codice}">${commissioniediliziet_var.numprotocollo}</a>
						</td>
						<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${commissioniediliziet_var.data}"/></td>
						<td>${commissioniediliziet_var.descrizione}</td>	
						<td>${commissioniediliziet_var.commedilizieTipologie.descrizione}</td>
						<td>
							<c:choose>
								<c:when test="${ flagaperta eq true }">								
								<fmt:message key="list.jmesa.celleditor.aperta"/>
								</c:when>
								<c:otherwise><fmt:message key="list.jmesa.celleditor.chiusa"/></c:otherwise>
							</c:choose>	
						</td>
						<td>
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','view.htm?codice=${commissioniediliziet_var.id.codice}','');" title="<fmt:message key="label.edit.record" />${commissioniediliziet_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>									
						</td>
					</tr>
				</c:forEach>
			</tbody>			
		</table>
	</div>
	<div id="functions">
		<ul>			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>