<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp" %>
<div id="innernav">
<c:choose>
<c:when test="${param.navmode=='search'}">
	<label class="selected"><fmt:message key="label.nav.search"/></label>
</c:when>
<c:otherwise>
	<label><fmt:message key="label.nav.search"/></label>
</c:otherwise>
</c:choose>
<label>|</label>
<c:choose>
<c:when test="${param.navmode=='list'}">
	<label class="selected"><fmt:message key="label.nav.result"/></label>
</c:when>
<c:otherwise>
	<label><fmt:message key="label.nav.result"/></label>
</c:otherwise>
</c:choose>
<label>|</label>
<c:choose>
<c:when test="${param.navmode=='form'}">
	<label class="selected"><fmt:message key="label.nav.form"/></label>
</c:when>
<c:otherwise>
	<label><fmt:message key="label.nav.form"/></label>
</c:otherwise>
</c:choose>
</div>