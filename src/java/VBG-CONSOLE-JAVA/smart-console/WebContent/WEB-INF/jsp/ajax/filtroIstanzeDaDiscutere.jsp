<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="commissioniediliziet" name="innnerForm">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="commissioniediliziet" />
	</jsp:include>
	<div id="subcontent">
	<table width="100%" >
		<tr>
			<td>
				<fmt:message key="label.data" />
			</td>
			<td>
				<input type="text" id="data_id"
					value="<fmt:formatDate value="${commissioniediliziet.dataFiltro}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
					name="dataFiltro" size="8"
							 onblur="isValidDate(this,true);"/>
	                        <a id="caldata" 
							 title="<fmt:message key="label.calendar"/>"> 
							 <img src="${pageContext.request.contextPath}/images/cal.gif" alt="<fmt:message key="label.calendar"/>"/></a>
							 <script type='text/javascript'>
							 	setTimeout('setupCal("data_id", "caldata")', 2000);							 
							 </script>
			     <spring-form:errors path="dataFiltro" cssClass="error"/>
				 <fmt:message key="label.data_descrizione" />
			</td>
		<tr>
			<td>
				<div id="functions">
					<ul>
						<li><a href="javascript:seachIstazeDaDiscure();"><fmt:message key="button.avanti" /></a></li>
					</ul>
				</div>
			</td>
		</tr>
	</table>
	</div>
</spring-form:form>

