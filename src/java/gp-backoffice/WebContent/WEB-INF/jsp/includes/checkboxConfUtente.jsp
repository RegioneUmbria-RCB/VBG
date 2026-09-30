<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="idConfUtente" value="" scope="page" />
<c:if test="${not empty param.idConfUtente}">
	<c:set var="idConfUtente" value="${param.idConfUtente}" scope="page"/>
</c:if>
<c:set var="idElement" value="" />
<c:if test="${not empty param.idElement}">
	<c:set var="idElement" value="${param.idElement}" />
</c:if>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE PRINCIPALE --%>
<%
         String daEffettuareChecked = "";	
         if ("1".equals((String) request.getAttribute((String)pageContext.getAttribute("idConfUtente")))) {
     		daEffettuareChecked  = " checked "; 
		} 
%>
<input type="checkbox" 
	id="${idElement}" 
	<%= daEffettuareChecked  %> 
 	 onclick="javascript:saveUserPreference('<%=(String)pageContext.getAttribute("idConfUtente")%>',(this.checked==true) ? 1 : 0);" />
<%-- END SEZIONE PRINCIPALE --%>