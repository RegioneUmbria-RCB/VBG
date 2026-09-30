<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<form id="sigeprofx000" name="gotoPopupPost000" action="${external_url}" method="post">

<c:forEach var='parameter' items='${paramValues}'>
	<c:if test="${parameter.key ne 'software' and parameter.key ne 'Software' and parameter.key ne 'idcomune' and parameter.key ne 'url' and parameter.key ne 'Token'}">
		<c:forEach var='value' items='${parameter.value}'>
			<input type="hidden" name="${parameter.key}" value="<c:out value='${value}' escapeXml="false"/>" />
		</c:forEach>
	</c:if>
</c:forEach></form>

<script type="text/javascript">
	document.forms["gotoPopupPost000"].submit();
</script>
