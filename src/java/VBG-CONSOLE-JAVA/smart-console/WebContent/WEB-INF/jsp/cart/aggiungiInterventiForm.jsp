<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<spring-form:form commandName="cartHelper" name="innerForm">
	<div class="jmesa">
	    <fmt:message key="stp.label.importa_intervento_help" />
		<table border="0"  cellpadding="2" cellspacing="0" class="table" width="100%">
			<thead class="header">
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td width="5%"><fmt:message key="label.aggiungi" /></td>
				</tr>
			</thead>
			<tbody class="tbody">
			<%int i=1;%>
			<c:forEach items="${stpTipologieEndo2s}" var="stpTipologieEndo2" varStatus="a">
				<c:if test="${stpTipologieEndo2.descrizione ne 'Avvio'}">
				<tr class="<%=(i%2)==0?"odd":"even"%>" valign="top">
					<td>${stpTipologieEndo2.descrizione}</td>
				    <td><input type="checkbox" value="${stpTipologieEndo2.id.codice}" name="codiciStpTipologiaEndo2"/></td>
				</tr>
				</c:if>
			<%i++; %>
	   		</c:forEach>
	   		</tbody>
	   
	</table>
	</div>
</spring-form:form>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('importaInterventi.htm','',document.innerForm)"><fmt:message key="button.importa" /></a></li>
	<li><a href="javascript:void(0)" onClick="dijit.byId('dialogDiv').hide()"><fmt:message key="button.annulla" /></a></li>
</ul>
</div>


