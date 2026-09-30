<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

		<spring-form:form commandName="stpEndoTipo2" name="inviodatistp">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="stpEndoTipo2" />
		    </jsp:include>
			<table>
			    <tr>
					<td>
						Tipo
					</td>
					<td>
						<spring-form:select id="tipo_id" path="tipo" onchange="mostranascondiintervento();" >
								<spring-form:option value="ATTIVITA" label="">ATTIVITA</spring-form:option>
								<spring-form:option value="CATEGORIA" label="">CATEGORIA</spring-form:option>
								<spring-form:option value="ENDO" label="">ENDO</spring-form:option>
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td>
						Codice attività
					</td>
					<td>
						<spring-form:input id="codiceEndoRegionale_id" cssStyle="text-align: right;" path="codiceEndoRegionale" size="10" maxlength="20" />
						<spring-form:errors path="codiceEndoRegionale" cssClass="error"/>
					</td>
				</tr>
				<c:set var="mostraIntervento" value="true"></c:set>
					<c:set var="mostraInterventoDisplay" value="display: none;"></c:set>
				
				<c:if test="${stpEndoTipo2.tipo eq 'ENDO' or empty stpEndoTipo2.tipo }">
					<c:set var="mostraIntervento" value="true"></c:set>				
					<c:set var="mostraInterventoDisplay" value=""></c:set>
				</c:if>
				<tr id="tipointerventoId" style="${mostraInterventoDisplay}">
					<td>
						Tipo di intervento
					</td>
					<td>
						<spring-form:select id="stpTipologieEndo2_id" path="stpTipologieEndo2.id.codice"  >
								<spring-form:option value="" label="">seleziona...</spring-form:option>
						<c:forEach items="${stpTipologieEndo2s}" var="stpTipologieEndo2_var">
								<spring-form:option value="${stpTipologieEndo2_var.id.codice}" label="${stpTipologieEndo2_var.descrizione}"></spring-form:option>
						</c:forEach>
						</spring-form:select>
						<spring-form:errors path="stpTipologieEndo2" cssClass="error"/>
					</td>
				</tr>
			</table>			
		</spring-form:form>
				
	<div id="functions">
		<ul>
			<c:if test="${isInsert eq true}">
				<li><a href="javascript:doSubmit('insertStpEndo2.htm','',document.inviodatistp)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${isInsert eq false}">
				<li><a href="javascript:doSubmit('updateStpEndo2.htm','',document.inviodatistp)"><fmt:message key="button.update" /></a></li>				
			</c:if>
		</ul>
	</div>
