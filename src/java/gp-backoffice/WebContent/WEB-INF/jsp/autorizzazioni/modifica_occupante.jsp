<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<c:set var="idAutorizzazione" />
<c:if test="${not empty param.idAutorizzazione}">
	<c:set var="idAutorizzazione" value="${param.idAutorizzazione}" />
</c:if>

<c:set var="paginaChiamante" />
<c:if test="${not empty param.paginaChiamante}">
	<c:set var="paginaChiamante" value="${param.paginaChiamante}" />
</c:if>
<c:choose>
	<c:when test="${empty idAutorizzazione}">
		<h1>Non è stato impostato il parametro idAutorizzazione</h1>
	</c:when>
	<c:otherwise>
		<a class="btn btn-primary" 
		href="javascript:doHref('../autorizzazioni/modificaOccupante.htm?idAutConc=${idAutorizzazione}&return_to_page='+encodeURIComponent('${paginaChiamante}'),'')"><fmt:message key="button.modify" /></a>
	</c:otherwise>
</c:choose>