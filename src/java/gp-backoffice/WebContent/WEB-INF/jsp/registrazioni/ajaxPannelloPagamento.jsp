<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<form name="ajaxPannelloPagamentoForm" method="post" action="effettuaPagamento.htm">
<input type="hidden" name="codice" value="${param.codice}">
<input type="hidden" name="nrRata" value="${param.nrRata}">
	
<table>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.importo" /> </td>
			<td><input id="importo_id" name="importo" size="10" maxlength="10" value="${fn:replace(rimanenza,'.',',')}" style="text-align: right;" onblur="checkCurrencyValue(this)" /> <b>&euro;</b></td>			
			<td><fmt:message key="form.registrazioniInOut.datadistinta" /></td>
			<td><input id="dataDistinta_id" name="dataDistinta" size="10" 
						maxlength="10" onblur="isValidDate(this,true);" 
						value='<fmt:formatDate value="${datadistinta}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>' /> 				
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.tipimodalitapagamento" /></td>
			<td>
			<select name="tipimodalitapagamento">
				<c:forEach items="${tipimodalitapagamentoList}" var="tmp">
					<c:set var="_selected_"></c:set>
					<c:if test="${tmp.id.codice eq modPag.id.codice}">
					<c:set var="_selected_"> selected="selected" </c:set>
					</c:if>			
					<option value="${tmp.id.codice}" ${_selected_}>${tmp.mpDescrestesa}</option>
				</c:forEach>
			</select></td>
			<td><fmt:message key="form.registrazioniInOut.riferimentipagamento" /></td>
			<td><input id="riferimentiPagamento_id" name="riferimentiPagamento" maxlength="500"/></td>			
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.dataincasso" /></td>
			<td><input id="dataIncasso_id" name="dataIncasso" size="10" maxlength="10" onblur="isValidDate(this,true);" 
				value="<fmt:formatDate value="${dataincasso}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>"/> 
			</td>
			<td><fmt:message key="form.registrazioniInOut.note" /></td>
			<td><textarea id="note_id" path="note" cols="20" rows="3"></textarea></td>
		</tr>
	</table>
	<br class="clear" />
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('insertEffettuaPagamento.htm','',document.ajaxPannelloPagamentoForm)"><fmt:message key="button.update" /></a></li>
</ul>
</div>
</form>