<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	<div>
		<div class="jmesa" >
			<form name="tipomovimentoDisForm">
        		<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
					<thead>
					<tr class="header">
						<td><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.endoprocedimento"/></td>
						<td width="5%"><fmt:message key="label.amministrazione"/></td>
						<td width="5%"><fmt:message key="label.data_scadenza"/></td>
						<td width="10%"><fmt:message key="label.azioni"/></td>
					</tr>
					</thead>
					<tbody class="tbody">
					    <c:if test="${fn:length(movimentidiabilitati)>0}">
						<c:forEach items="${movimentidiabilitati}" var="movimento" varStatus="indice">
						<c:set var="trStyle" value="odd"/>
						<c:if test="${(indice.index mod 2) eq 0}">
							<c:set var="trStyle">even</c:set>
						</c:if>		
						<tr class="${trStyle}">
							<td>${movimento.tipomovimento.movimento}</td>
							<td>${movimento.endoprocedimento.procedimento}</td>
							<td>${movimento.amministrazioni.amministrazione}</td>
	                       	<td><fmt:formatDate value="${movimento.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
	                       	<td>
	                       		<input type="checkbox" name="codiceMovimentoDisabilitato"  value="${movimento.id.codice}"/>
							</td>
	                    </tr>
	                    </c:forEach>
	                    </c:if>		
	                </tbody>
				</table>
				<input type="hidden" name="codiceIstanza" value="${param.codiceIstanza}">
			</form>
 		</div>
 		<c:if test="${fn:length(movimentidiabilitati)>0}">
	 		<div id="functions">
				<ul>
					<li class="btn btn-primary gp-restyled"><a href="javascript:riattiva(document.tipomovimentoDisForm);"><fmt:message key="button.riattiva" /></a></li>
				</ul>
			</div>
		</c:if>
 	</div>	