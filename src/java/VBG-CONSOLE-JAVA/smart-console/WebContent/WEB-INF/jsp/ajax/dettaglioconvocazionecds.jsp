<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<spring-form:form commandName="convocazione" name="innerForm" id="innerForm_id">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="convocazione" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.data" /></td>		
			<td><spring-form:input 
					path="dataconvocazione" 
					id="dataconvocazione_id"	
					size="10" onblur="isValidDate(this,true);" /> 
					<a id="caldataconvocazione" 
						 title="<fmt:message key="label.calendar"/>"> 
						 <img src="${pageContext.request.contextPath}/images/cal.gif" alt="<fmt:message key="label.calendar"/>"/></a>				
					 <script type='text/javascript'>
					 	setTimeout('setupCal("dataconvocazione_id", "caldataconvocazione")', 2000);							 
					</script>
			</td>
	   </tr>
		<tr>
			<td><fmt:message key="label.ora" /></td>
			<td><spring-form:input path="oraconvocazione" size="6" maxlength="5" onblur="isValidOra(this,true);" /></td>
		</tr>
	</table>
	<div id="functions">
	<ul>
		<c:if test="${insert eq true }">
			<li><a href="javascript:insertConvocazione()"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${empty insert or insert eq false}">
			<li><a href="javascript:updateConvocazione()"><fmt:message key="button.update" /></a></li>
		</c:if>
	</ul>
	</div>
</spring-form:form>
<br class="clear" />