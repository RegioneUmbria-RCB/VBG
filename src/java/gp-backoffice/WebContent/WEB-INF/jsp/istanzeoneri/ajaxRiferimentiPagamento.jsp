<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

    <spring-form:form commandName="istanzeoneriRiferimentiPagamento" name="innerForm">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeoneriRiferimentiPagamento" />
		    </jsp:include>
	<table  >
		<tr>
			<td>
				<fmt:message key="label.modalita_pagamento" />
			</td>
			<td>
				<spring-form:select id="modalitapagamento_id" path="tipimodalitapagamento.id.codice"> 
					<spring-form:option value="" ><fmt:message key='label.seleziona'/></spring-form:option>
					<spring-form:options items="${tipimodalitapagamentos}" itemLabel="mpDescrestesa" itemValue="id.codice" />
				</spring-form:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.riferimento_documento" />
			</td>
			<td>
				<spring-form:input id="docriferimento_id" path="docriferimento"  size="60" />
				<spring-form:errors path="docriferimento" cssClass="error"/>  
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.note" />
			</td>
			<td>
				<spring-form:textarea  id="note_id" path="note" rows="4" cols="60" />
				<spring-form:errors path="note" cssClass="error"/>  
			</td>
		</tr>
	</table>
	<input type="hidden" name="id.codice" value="${istanzeoneriRiferimentiPagamento.id.codice}" />
	<input type="hidden" name="istanza.id.codice" value="${istanzeoneriRiferimentiPagamento.istanza.id.codice}" />
    
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('updateRiferimentiPagamento.htm','',document.innerForm)"><fmt:message key="button.update" /></a></li>
		</ul>
	</div>
	</spring-form:form>


