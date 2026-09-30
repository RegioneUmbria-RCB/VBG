<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<div id="change_data_status_msg" class="validation_error" style="display: none;"></div>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand" id="schede_form">
		<div id="tabs">
		     <ul>
		         <c:forEach items="${schedeH }" var="schedaH">
		         <li><a href="${pageContext.request.contextPath}/nuovaistanzaschede/getScheda.htm?codiceModello=${schedaH.scheda.codice }">
		         	<span>
		         	${schedaH.scheda.descrizione }
		         	</span></a><c:if test="${schedaH.obbligatorio eq true}"> *</c:if><c:if test="${schedaH.confirmed eq true}"> (<fmt:message key='label.scheda-completata' />)</c:if></li>	
		         </c:forEach>	 
		     </ul>
		</div>
		<input type="hidden" name="schede_ok" value="${schede_ok }" />
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>