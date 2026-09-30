<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<c:set var="idAutorizzazioneSubentri" />
<c:if test="${not empty param.idAutorizzazioneSubentri}">
	<c:set var="idAutorizzazioneSubentri" value="${param.idAutorizzazioneSubentri}" />
</c:if>

<c:set var="paginaChiamante" />
<c:if test="${not empty param.paginaChiamante}">
	<c:set var="paginaChiamante" value="${param.paginaChiamante}" />
</c:if>
<c:choose>
	<c:when test="${empty idAutorizzazioneSubentri}">
		<h1>Non è stato impostato il parametro idAutorizzazioneSubentri</h1>
	</c:when>
	<c:otherwise>
		
		<a class="dettaglio" title="<fmt:message key="button.modify" /> <fmt:message key="label.data_cessazione" />"
		href="javascript:doHref('../autorizzazioni/modificaDataCessazioneSubentro.htm?idSubentro=${idAutorizzazioneSubentri}&return_to_page='+encodeURIComponent('${paginaChiamante}'),'')">
		<i class="fa fa-edit" aria-hidden="true"></i>		
		</a>
	</c:otherwise>
</c:choose>