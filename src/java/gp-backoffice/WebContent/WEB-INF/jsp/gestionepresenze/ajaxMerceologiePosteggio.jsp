<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:forEach items="${atts}" var="att">
	<c:if test="${att.flagConsentito eq true}">
		<div title="${att.attivita.istat} ammessa" style="color: #1A6900;">${att.id.fkcodiceattivitaistat}</div>
	</c:if>	
	<c:if test="${att.flagConsentito ne true}">
		<div title="${att.attivita.istat} non ammessa" style="color: #FF0000">${att.id.fkcodiceattivitaistat}</div>
	</c:if>	
</c:forEach>