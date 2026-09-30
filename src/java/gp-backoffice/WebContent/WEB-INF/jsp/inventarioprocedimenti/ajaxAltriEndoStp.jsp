<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${empty cdbs}">		
	<!-- non attivo -->
	</c:when>
	<c:otherwise>
	Questo codice regionale è già usato dai seguenti endo 
	<c:forEach items="${cdbs}" var="cdb">
		<div>
			${cdb.descrizione} (${cdb.codice} )
		</div>
		
	</c:forEach>

	</c:otherwise>
</c:choose>				
