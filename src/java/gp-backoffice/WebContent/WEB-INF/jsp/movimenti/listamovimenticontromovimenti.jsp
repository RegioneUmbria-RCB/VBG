<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	<div>
		<div class="jmesa" >
			<form name="tipomovimentoDisForm">
        		<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
					<thead>
					<tr class="header">
						<td colspan="6">
							<fmt:message key="label.movimento" />: 
							<c:set var="descrizioneMovimento" value="${movimento.movimento}"/>
							<c:if test="${empty movimento.movimento}">
								<c:set var="descrizioneMovimento" value="${movimento.tipomovimento.movimento}"/>
							</c:if>
							 ${descrizioneMovimento}
						</td>
					</tr>
					<tr class="header">
						<td width="5%"><fmt:message key="label.data"/></td>
						<td><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.endoprocedimento"/></td>
						<td width="5%"><fmt:message key="label.amministrazione"/></td>
						<td width="5%"><fmt:message key="label.data_scadenza"/></td>
						<td width="10%"><fmt:message key="label.azioni"/></td>
					</tr>
					</thead>
					<tbody class="tbody">
						<c:forEach items="${movimentiList}" var="movimento" varStatus="indice">
						<c:set var="trStyle" value="odd"/>
						<c:if test="${(indice.index mod 2) eq 0}">
							<c:set var="trStyle">even</c:set>
						</c:if>		
						<tr class="${trStyle}">
							<c:if test="${tipo eq 'padre'}">
								
								<c:set var="movimentoStyle" value="movimentoEffettuato"/>
								<c:if test="${empty movimento.movimentoByFkPadre.data}">
									<c:set var="movimentoStyle">movimentoDaEffettuare</c:set>
								</c:if>	
								
								
								<td><fmt:formatDate value="${movimento.movimentoByFkPadre.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
								<td class="${movimentoStyle}">${movimento.movimentoByFkPadre.tipomovimento.movimento}</td>
								<td>${movimento.movimentoByFkPadre.endoprocedimento.procedimento}</td>
								<td>${movimento.movimentoByFkPadre.amministrazioni.amministrazione}</td>
		                       	<td><fmt:formatDate value="${movimento.movimentoByFkPadre.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
		                        <td>
	                       			<jsp:include page="../includes/funzioni_movimenti.jsp">
										<jsp:param name="codiceMovimentoId" value="${movimento.movimentoByFkPadre.id.codice}" />
										<jsp:param name="funzioneRichiesta" value="visualizzamovimentiPadre" />
										<jsp:param name="mostraAbilitato" value="${fn:length(movimento.movimentoByFkPadre.movimentiContromovimentisForFkFiglio)}" />
									</jsp:include>&nbsp;
									<jsp:include page="../includes/funzioni_movimenti.jsp">
										<jsp:param name="codiceMovimentoId" value="${movimento.movimentoByFkPadre.id.codice}" />
										<jsp:param name="funzioneRichiesta" value="visualizzamovimentiFiglio" />
										<jsp:param name="mostraAbilitato" value="${fn:length(movimento.movimentoByFkPadre.movimentiContromovimentisForFkPadre)}" />
									</jsp:include>
								</td>
							</c:if>	
							<c:if test="${tipo ne 'padre'}">
								<c:set var="movimentoStyle" value="movimentoEffettuato"/>
								<c:if test="${empty movimento.movimentoByFkFiglio.data}">
									<c:set var="movimentoStyle">movimentoDaEffettuare</c:set>
								</c:if>	
								<td><fmt:formatDate value="${movimento.movimentoByFkFiglio.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
								<td class="${movimentoStyle}">${movimento.movimentoByFkFiglio.tipomovimento.movimento}</td>
								<td>${movimento.movimentoByFkFiglio.endoprocedimento.procedimento}</td>
								<td>${movimento.movimentoByFkFiglio.amministrazioni.amministrazione}</td>
		                       	<td><fmt:formatDate value="${movimento.movimentoByFkFiglio.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
		                        <td>
		                       		<jsp:include page="../includes/funzioni_movimenti.jsp">
										<jsp:param name="codiceMovimentoId" value="${movimento.movimentoByFkFiglio.id.codice}" />
										<jsp:param name="funzioneRichiesta" value="visualizzamovimentiPadre" />
										<jsp:param name="mostraAbilitato" value="${fn:length(movimento.movimentoByFkFiglio.movimentiContromovimentisForFkFiglio)}" />
									</jsp:include>&nbsp;
									<jsp:include page="../includes/funzioni_movimenti.jsp">
										<jsp:param name="codiceMovimentoId" value="${movimento.movimentoByFkFiglio.id.codice}" />
										<jsp:param name="funzioneRichiesta" value="visualizzamovimentiFiglio" />
										<jsp:param name="mostraAbilitato" value="${fn:length(movimento.movimentoByFkFiglio.movimentiContromovimentisForFkPadre)}" />
									</jsp:include>
								</td>
							</c:if>
	                    </tr>
	                    </c:forEach>
	                </tbody>
				</table>				
			</form>
 		</div>
 	</div>	