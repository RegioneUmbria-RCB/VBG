<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
	<div>
		<div class="jmesa" >
        		<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
					<thead>
					<tr class="header">
						<td><fmt:message key="label.convocazione_n" /></td>
						<td><fmt:message key="label.data"/></td>
						<td width="5%"><fmt:message key="label.ora"/></td>
						<td width="5%"><fmt:message key="label.convocazione_effettiva"/></td>
						<td width="10%"><fmt:message key="label.azioni"/></td>
					</tr>
					</thead>
					<tbody class="tbody">
					    <c:if test="${fn:length(convocazionis)>0}">
						<c:forEach items="${convocazionis}" var="convocazione" varStatus="indice">
						<c:set var="trStyle" value="odd"/>
						<c:if test="${(indice.index mod 2) eq 0}">
							<c:set var="trStyle">even</c:set>
						</c:if>		
						<c:set var="numeroConvocazione" value="${ (indice.index + 1)}"/>
						<tr class="${trStyle}">
							<td>${numeroConvocazione}</td>
	                       	<td><fmt:formatDate value="${convocazione.dataconvocazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
	                       	<td>${convocazione.oraconvocazione}</td>
	                       	<td>
	                       		<c:set var="effettivaChecked" value="" />
	                       		<c:if test="${convocazione.flagEffettiva eq true}">
	                       			<c:set var="effettivaChecked" value="checked" />
	                       		</c:if>
	                       		<input type="radio" name="effettiva" ${effettivaChecked} onclick="setEffettiva(${convocazione.id.codice});"/>
							</td>
							<td>
								<a class="dettaglioColumn" href="javascript:dettaglioConvocazione(${convocazione.id.codice})"  title="<fmt:message key="label.edit.record" />&nbsp;<fmt:message key="label.convocazione_n" /> ${numeroConvocazione}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>								
								<a class="eliminaRiga" href="javascript:eliminaConvocazione(${convocazione.id.codice})"  title="<fmt:message key="label.elimina" />&nbsp;<fmt:message key="label.convocazione_n" /> ${numeroConvocazione}">
									<label><fmt:message key="label.azioni" /></label>
								</a>
	                        </td>
	                    </tr>
	                    </c:forEach>
	                    </c:if>		
	                    <tr>
							<td colspan="5" align="right">
								<a class="addColumn" href="javascript:addConvocazione();" title="<fmt:message key="label.nuova" />&nbsp;<fmt:message key="label.convocazione" />">
									<label><fmt:message key="label.add.record.image" /></label>
								</a>
							</td>
						</tr>				
	                </tbody>
				</table>
 		</div>