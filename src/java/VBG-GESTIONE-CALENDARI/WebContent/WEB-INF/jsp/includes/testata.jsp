<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!-- testata -->
<div id="header">
	<div class="testata"><fmt:message key="label.appname" /></div>			
</div>
<div id="header_user">
	<label><spring-security:authentication property="principal.responsabile" /></label>
</div>
<!-- testata end -->