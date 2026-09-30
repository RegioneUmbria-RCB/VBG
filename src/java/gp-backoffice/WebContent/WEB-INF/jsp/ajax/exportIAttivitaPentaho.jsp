<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="iattivitaCommand" name="iattivita">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="iattivitaCommand" />
	</jsp:include>
	<div id="subcontent">
	
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
			<%-- <td><b>${iattivitaCommand.responsabile.email}</b></td> --%>
		</tr>					
		<tr>
			<td><fmt:message key="label.esportazione"/></td>
			<td>
		        <!--  
				<select id="tipoEsportazione_id" name="tipoEsportazione" >
					<c:forEach items="${listaEsportazioni}" varStatus="status" var="exp">				    
					    	<option value="${tipoEsportazione.id.codice}" >${tipoEsportazione.descrizione}</option>			    
					</c:forEach>
				</select>
				-->
				<spring-form:select path="esportazioni" onchange="changeEsportazionePentaho(this)">
				   <%--<spring-form:options items="${listaEsportazioni}" itemValue="id.codice" itemLabel="descrizione"/> --%>
				   <c:forEach items="${listaEsportazioni}" var="esportazioni" varStatus="a">
				   		<option value="${esportazioni.id.codice}@${esportazioni.id.idcomune}">${esportazioni.descrizione}</option>
				   	</c:forEach>
				</spring-form:select>
				
			</td>
		</tr>
		
		
		 <c:if test="${not empty iattivitaCommand.esportazioni.parametriesportaziones}">
		    <tr>
		    	<td class="titoloSezione" colspan="2">
		    		<fmt:message key="label.lista_parametri" />
		    	</td>
		    </tr>
		  </c:if>
		 <c:forEach items="${iattivitaCommand.esportazioni.parametriesportaziones}" var="current" varStatus="a">
				<tr>
					<td><label>${current.parametro}</label></td>
					<td>
					<spring:bind path="esportazioni.parametriesportaziones[${a.index}].id.codice">
						<input type="hidden" name="${status.expression}" value="${status.value}" />
					</spring:bind> 
					<spring:bind path="esportazioni.parametriesportaziones[${a.index}].value">
						<input  type="text" name="${status.expression}"  value="${status.value}" />					 
					</spring:bind>
					<label>${current.descrizione}</label>
					<spring-form:errors path="esportazioni.parametriesportaziones[${a.index}].parametro" cssClass="error" />
					</td>
				</tr>
			</c:forEach>
		
	</table>	
	<div id="functions">
		<ul>
			<li><a href="javascript:esportaPentaho()"><fmt:message key="button.esporta" /></a></li>
		</ul>
	</div>

	</div>
</spring-form:form>






		

	
	