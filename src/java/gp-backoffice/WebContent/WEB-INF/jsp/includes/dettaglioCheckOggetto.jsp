<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>



<c:set var="_controllook" value="${param.controllook}"/>
<c:choose>
	<c:when test="${_controllook == '0'}">
		<img src="${pageContext.request.contextPath}/images/error.png" alt="Documento non valido" title="Documento non valido" />
	</c:when>
	<c:when test="${_controllook == '1'}">
		<img src="${pageContext.request.contextPath}/images/success.png" alt="Documento valido" title="Documento valido" />
	</c:when>
	<c:otherwise>
 		<img src="${pageContext.request.contextPath}/images/warning.gif" alt="Documento da verificare" title="Documento da verificare" />
	</c:otherwise>
</c:choose>

