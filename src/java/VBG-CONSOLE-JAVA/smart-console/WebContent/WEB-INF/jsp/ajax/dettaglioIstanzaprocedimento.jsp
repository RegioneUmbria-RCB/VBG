<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<spring-form:form commandName="istanzeprocedimentiCommand" name="innerForm">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="istanzeprocedimentiCommand" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.numero" /></td>
			<td><spring-form:input path="entity.protNum" size="10" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.data" /></td>
			
			<td><spring-form:input 
					path="entity.protDel" 
					id="data_prot_del_id${istanzeprocedimentiCommand.entity.id.codiceinventario}"	
					size="10" onblur="isValidDate(this,true);" /> 
					<a id="caldataprotdel${istanzeprocedimentiCommand.entity.id.codiceinventario}" 
						 title="<fmt:message key="label.calendar"/>"> 
						 <img src="${pageContext.request.contextPath}/images/cal.gif" alt="<fmt:message key="label.calendar"/>"/></a>				
					 <script type='text/javascript'>
					 	setTimeout('setupCal("data_prot_del_id${istanzeprocedimentiCommand.entity.id.codiceinventario}", "caldataprotdel${istanzeprocedimentiCommand.entity.id.codiceinventario}")', 2000);							 
					</script>
			</td>
	   </tr>
	   <tr>
			<td><fmt:message key="label.tipo_atto" /></td>
			<td><spring-form:input path="entity.tipoAtto" size="58" maxlength="100" /></td>
		</tr>
		<c:if test="${istanzeprocedimentiCommand.entity.acquisito eq true}">
			<tr><td colspan="2"><b><fmt:message key="label.acquisito" /></b></td></tr>
		</c:if>
		<tr>
			<td><fmt:message key="label.rilasciato_da" /></td>
			<td><spring-form:input path="entity.rilasciatoDa" size="58" maxlength="100" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.note" /></td>
			<td><spring-form:textarea path="entity.note" cols="60" rows="4" /></td>
		</tr>
	</table>
	<c:if test="${isModificaIstanza eq true }">
		<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('../istanzeprocedimenti/updateDettaglio.htm?isCallFromMovimenti=${isCallFromMovimenti}&codiceMovimento=${codiceMovimento}','',document.innerForm)"><fmt:message key="button.update" /></a></li>
		</ul>
		</div>
	</c:if>
</spring-form:form>

