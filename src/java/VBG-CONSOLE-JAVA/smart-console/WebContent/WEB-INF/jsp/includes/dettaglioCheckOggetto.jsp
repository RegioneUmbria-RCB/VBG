<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>



<c:set var="_controllook" value="${param.controllook}"/>
<c:choose>
	<c:when test="${_controllook == '0'}">
		<img src="${pageContext.request.contextPath}/images/error.gif" alt="Documento non valido" title="Documento non valido" />
	</c:when>
	<c:otherwise>
 		&nbsp;
	</c:otherwise>
</c:choose>

