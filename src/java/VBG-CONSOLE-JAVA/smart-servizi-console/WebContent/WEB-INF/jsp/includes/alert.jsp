<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<c:if test="${param.commandName == null}">
	<c:set var="param.commandName" value="nuovaIstanzaCommand"></c:set>
</c:if>
<spring:hasBindErrors name="${param.commandName }">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
		<span class="ui-icon ui-icon-alert" style="float: left;margin-top: 2px;margin-right: .3em;"></span> 
		<strong><fmt:message key="alert.controllare-i-dati-inseriti" /></strong>
		<ul style="clear: both;">
		<c:forEach items="${errors.allErrors }" var="err">       	
			<li>
				<spring:message code="${err.code }" arguments="${err.arguments }" text="${err.defaultMessage}" htmlEscape="false" />				
			</li>       
		</c:forEach>
		</ul>
	</div>
</spring:hasBindErrors>