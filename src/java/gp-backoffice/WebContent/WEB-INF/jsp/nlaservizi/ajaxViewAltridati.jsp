<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<br class="clear" />
<br class="clear" />

<fieldset>
	<legend>
		<b><fmt:message key="label.nla_servizi.altri_dati" /></b>
	</legend>
	<div class="jmesa">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td width="5%"><fmt:message key="label.nome_parametro" /></td>
					<td width="20%"><fmt:message key="label.valore" /></td>
					<td width="2%" align="center"><fmt:message key="label.azioni" /></td>
				</tr>
			</thead>
			<tbody class="tbody">
				<%
				    int i = 1;
				%>
				<c:forEach items="${altriDatis}" var="ad_var">
					<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>" valign="top">
						<td>${ad_var.nomeparametro}</td>
						<td>${ad_var.valore}</td>
						<td><a class="eliminaRiga" style="float: none;"
							href="javascript:eliminaParametro(${ad_var.id.codice})"
							title="<fmt:message key="label.elimina" /> ${ad_var.id.codice}">
								<label><fmt:message key="label.elimina.image" /></label>
						</a></td>
					</tr>
					<%
					    i++;
					%>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<a class="addColumn" style="float: none;"
		href="javascript:aggiungiParametro(${servizio.id.codice})"
		title="<fmt:message key="label.nuovo" /> <fmt:message key="label.parametro" />">
		<label><fmt:message key="label.add.record.image" /></label>
	</a>


</fieldset>
</div>


