<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
		
<fieldset>
	<table>
		
		<tr>
			<td colspan="2"><fmt:message key="iattivita.label.descrizione_export"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="label.operatore"/>:</td>
			<td><b>${reportistanze.responsabili.responsabile}</b></td>
		</tr>
		<tr>
			<td><fmt:message key="label.indirizzo_email"/>:</td>
			<td><input id="responsabile_email_id" type="text"  value="${reportistanze.responsabili.email}" size="40"/>
			<c:if test="${reportistanze.responsabili.email eq null}">
				<input id="invio_email_id" type="checkbox"/>
			</c:if>
			<c:if test="${reportistanze.responsabili.email ne null}">
				<input id="invio_email_id" type="checkbox" checked="checked"/> (<fmt:message key="label.help.esportazione_invio_mail"/>)
		    </c:if>
			</td>
			
			<%-- <td><b>${iattivitaCommand.responsabile.email}</b></td> --%>
		</tr>				
		<tr>
			<td><fmt:message key="label.esportazione"/></td>
			<td>
				<select id="tipoEsportazione_id" name="tipoEsportazione" >
					<c:forEach items="${listaEsportazioni}" varStatus="status" var="exp">				    
					    	<option value="${exp.ID}|${exp.IDCOMUNE}" title="${exp.DESCRIZIONE}" label="${exp.DESCRIZIONE}" id="exp_id" >${exp.DESCRIZIONE}</option>			    
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>	
	<div id="functions">
		<ul>
			<li class="btn btn-primary gp-restyled"><a href="javascript:esporta()"><fmt:message key="button.esporta" /></a></li>
		</ul>
	</div>
</fieldset>
	