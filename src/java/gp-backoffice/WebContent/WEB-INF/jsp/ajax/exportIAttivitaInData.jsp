<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
		
<fieldset>
	<table>
		<tr>
			<td colspan="2"><fmt:message key="iattivita.label.descrizione_export"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="label.operatore"/>:</td>
			<td><b>${iattivitaCommand.responsabile.responsabile}</b></td>
		</tr>
		<tr>
			<td><fmt:message key="label.indirizzo_email"/>:</td>
			<td><input id="responsabile_email_id" type="text"  value="${iattivitaCommand.responsabile.email}" size="40"/>
			<c:if test="${iattivitaCommand.responsabile.email eq null}">
				<input id="invio_email_id" type="checkbox"/>
			</c:if>
			<c:if test="${iattivitaCommand.responsabile.email ne null}">
				<input id="invio_email_id" type="checkbox" checked="checked"/> (<fmt:message key="label.help.esportazione_invio_mail"/>)
		    </c:if>
			</td>
		<%-- 	<td><b>${iattivitaCommand.responsabile.email}</b></td> --%>
		</tr>			
		<tr>
			<td>
				<fmt:message key="label.data" />
			</td>
			<td>
				<input type="text" id="data_id"
					value="<fmt:formatDate value="${iattivitaCommand.dataEsportazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
					name="dataFiltro" size="8"
							 onblur="isValidDate(this,true);"/>
	                        <a id="caldata" 
							 title="<fmt:message key="label.calendar"/>"> 
							 <img src="${pageContext.request.contextPath}/images/cal.gif" alt="<fmt:message key="label.calendar"/>"/></a>
							 <script type='text/javascript'>
							 	setTimeout('setupCal("data_id", "caldata")', 2000);							 
							 </script>
			     <spring-form:errors path="dataEsportazione" cssClass="error"/>
			</td>		
		</tr>
		<tr>
			<td><fmt:message key="label.esportazione"/></td>
			<td>
				<select id="tipoEsportazione_in_data_id" name="tipoEsportazione" >
					<c:forEach items="${listaEsportazioni}" varStatus="status" var="exp">				    
					    	<option value="${exp.ID}|${exp.IDCOMUNE}" title="${exp.DESCRIZIONE}" label="${exp.DESCRIZIONE}" id="exp_id" >${exp.DESCRIZIONE}</option>			    
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>	
	<div id="functions">
		<ul>
			<li><a href="javascript:esportaInData()"><fmt:message key="button.esporta" /></a></li>
		</ul>
	</div>
</fieldset>
	
	