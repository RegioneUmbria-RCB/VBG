<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
	<fieldset><legend><span class="titoloPagina"></span></legend>
	    <div class="clear" ></div>
		<div class="jmesa" >
			<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td colspan="2"><fmt:message key="label.descrizione" /></td>
					</tr>
				</thead>
				<tbody class="tbody" >
				<%int i = 0; %>
				<c:forEach var="listaSchede_var" items="${listaSchede}" varStatus="listaSchedeStatus">
				<c:if test="${fn:length(listaSchede)>=0}">
					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>
							<a href="javascript:doSubmit('update.htm?codiceScheda=${listaSchede_var.id.codice }','<fmt:message key="javascript.confirm.update_mappature_cambia_scheda" />',document.inviodati)" title="<fmt:message key="mappature.label.visualizza_mappature_scheda" />">${listaSchede_var.descrizione}</a>
						</td>							
					</tr>
					<%i++; %>					
				</c:if>				
				</c:forEach>
				</tbody>
			</table>
		</div>
	</fieldset>