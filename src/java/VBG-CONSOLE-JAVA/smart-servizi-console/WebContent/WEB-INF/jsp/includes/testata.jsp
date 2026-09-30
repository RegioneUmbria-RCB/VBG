<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!-- testata -->
<div id="header">
	<div class="testata"><fmt:message key="label.area-riservata" /></div>	
	<div class="testata2">
	<%
	pageContext.setAttribute("dc", request.getSession().getAttribute("denominazioneComune"));
	pageContext.setAttribute("ds", request.getSession().getAttribute("denominazioneSportello")); 
	%>
	<c:out value="${dc }" default="" />&nbsp;<c:out value="${ds }" default="" />
	</div>			
</div>
<div id="header_user">
	<label><spring-security:authentication property="principal.anagrafe" /></label>
</div>
<!-- testata end -->