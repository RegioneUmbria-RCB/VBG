<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_verticalizzazioni_base" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_verticalizzazioni_base" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../verticalizzazionibase/list" />
	</jsp:include>
	<div id="subcontent">
		<div class="jmesa">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
			    <td width="4%" colspan="2" ><fmt:message key="label.azioni" /></td>
				<td ><fmt:message key="label.regola" /> </td>
				<td ><fmt:message key="label.descrizione" /></td>
            </tr>
		</thead>
		<tbody class="tbody">
		<%int j=1;%>
		<c:forEach items="${verticalizzazionibaseList}" var="verticalizzazionebase">
			<tr class="<%=(j%2)==0?"odd":"even"%>">
			    <td>				
					<a class="verticalizzazioniparametriColumn" href="javascript:doHref('listregoleconfigurate.htm?codice=${verticalizzazionebase.modulo }','');" title="<fmt:message key="label.lista_regole" /> ${verticalizzazionebase.modulo}">
						<label><fmt:message key="label.edit.record.image" /></label>
					</a> 					
			    	</td>
			    	<td>
			    	<c:if test="${fn:length(verticalizzazionebase.verticalizzazioniparametribases)>0}">
			    	<%--
				    	href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+escape('../verticalizzazionibase/listparametribase.htm?codice=${verticalizzazionebase.modulo }'),'')" 
			    	--%>				
					<a class="verticalizzazioniColumn" href="javascript:doHref('listregoleconfigurate.htm?codice=${verticalizzazionebase.modulo }','');" title="<fmt:message key="label.lista_parametri_regola" /> ${verticalizzazione.modulo}">
						<label><fmt:message key="label.edit.record.image" /></label>
					</a>
					</c:if> 					
				</td>
			    <c:if test="${verticalizzazionebase.flagConfigurata eq true}">
			    <td class="red">${verticalizzazionebase.modulo}</td>
			    </c:if>
			    <c:if test="${verticalizzazionebase.flagConfigurata eq false}">
			    <td>${verticalizzazionebase.modulo}</td>
			    </c:if>
				<td><c:out value="${verticalizzazionebase.descrizione}" escapeXml="true"/></td>
			</tr>
			<%j++; %>
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