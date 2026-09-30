<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<script type="text/javascript">
	function submitForm(viewAltri){
		$('#viewAltriId').val(viewAltri);
		$.blockUI();
		document.formProcedimenti.submit();
	}
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione" ><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand" name="formProcedimenti">
		<c:if test="${nuovaIstanzaCommand.procedimentiHelper != null}">
			<%@ include file="../includes/alertOneri.jsp" %>
			<div class="titolo_sezione toggle"><fmt:message key='label.endo-principale' /></div>
			<div id="sez_procedimento_principale" class="sezione lista-endo">
				<c:choose>
				<c:when test="${nuovaIstanzaCommand.procedimentiHelper.procedimentoPrincipale != null}">
				<c:set value="${nuovaIstanzaCommand.procedimentiHelper.procedimentoPrincipale}" var="procPrincipale" />
				<ul>
					<li>${procPrincipale.famigliaKey.descrizione }<c:if test="${empty procPrincipale.famigliaKey.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
						<ul>
							<li>${procPrincipale.categoriaKey.descrizione }<c:if test="${empty procPrincipale.categoriaKey.descrizione }"><fmt:message key='label.categoria-endo-non-specificata' /></c:if>
								<ul>
									<li><input type="checkbox" name="codiceProcPrincipale" value="${procPrincipale.procedimento.codice}" checked="checked" disabled="disabled" /> ${procPrincipale.procedimento.descrizione }</li>
								</ul>
							</li>
						</ul>
					</li>
				</ul>
				</c:when>
				<c:otherwise><fmt:message key='label.nessun-endo' /></c:otherwise>
				</c:choose>
			</div>
			<div class="titolo_sezione toggle"><fmt:message key='label.endo-proposto' /></div>
			<div id="sez_procedimenti_proposti" class="sezione lista-endo">
				<c:choose>
				<c:when test="${not empty nuovaIstanzaCommand.procedimentiHelper.procedimentiProposti}">
				<ul>
				<c:forEach items="${nuovaIstanzaCommand.procedimentiHelper.procedimentiProposti }" var="procPropostoFamiglia" varStatus="idxPropostoFam">
					<li>${procPropostoFamiglia.key.descrizione }<c:if test="${empty procPropostoFamiglia.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
					<ul>
					<c:forEach items="${procPropostoFamiglia.value }" var="procPropostoCategoria" varStatus="idxPropostoCat">
						<li>${procPropostoCategoria.key.descrizione }<c:if test="${empty procPropostoCategoria.key.descrizione }"><fmt:message key='label.categoria-endo-non-specificata' /></c:if>
						<ul>
						<c:forEach items="${procPropostoCategoria.value }" var="procProposto" varStatus="idxProposto">
							<li><input type="checkbox" name="codiceProcProposto" value="${procProposto.value.procedimento.codice}" checked="checked" disabled="disabled" />${procProposto.key.descrizione }</li>
						</c:forEach>
						</ul>
						</li>
					</c:forEach>
					</ul>
					</li>	
				</c:forEach>
				</ul>
				</c:when>
				<c:otherwise><fmt:message key='label.nessun-endo' /></c:otherwise>
				</c:choose>				
			</div>
			<div class="titolo_sezione toggle"><fmt:message key='label.endo-attivabile' /></div>
			<div id="sez_procedimenti_attivabili" class="sezione lista-endo">
				<c:choose>
				<c:when test="${not empty nuovaIstanzaCommand.procedimentiHelper.procedimentiAttivabili }">
				<ul>
				<c:forEach items="${nuovaIstanzaCommand.procedimentiHelper.procedimentiAttivabili }" var="procAttivabileFamiglia" varStatus="idxAttivabileFam">
					<li>${procAttivabileFamiglia.key.descrizione }<c:if test="${empty procAttivabileFamiglia.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
					<ul>
					<c:forEach items="${procAttivabileFamiglia.value }" var="procAttivabileCategoria" varStatus="idxAttivabileCat">
						<li>${procAttivabileCategoria.key.descrizione }<c:if test="${empty procAttivabileCategoria.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
						<ul>
						<c:forEach items="${procAttivabileCategoria.value }" var="procAttivabile" varStatus="idxAttivabile">
							<li>
								<c:choose>
								<c:when test="${procAttivabile.value.selezionato eq true }">
								<input type="checkbox" name="codiceProcAttivabile" value="${procAttivabile.value.procedimento.codice}" checked="checked" />
								</c:when>
								<c:otherwise>
								<input type="checkbox" name="codiceProcAttivabile" value="${procAttivabile.value.procedimento.codice}" />
								</c:otherwise>
								</c:choose>
								${procAttivabile.key.descrizione }
								</li>
						</c:forEach>
						</ul>
						</li>
					</c:forEach>
					</ul>
					</li>
				</c:forEach>
				</ul>
				</c:when>
				<c:otherwise><fmt:message key='label.nessun-endo' /></c:otherwise>
				</c:choose>
			</div>
		</c:if>
		<c:if test="${nuovaIstanzaCommand.procedimentiHelper == null}">
			<fmt:message key='label.nessun-endo' />
		</c:if>	
		<input type="hidden" name="viewAltri" id="viewAltriId" value="0" />
		<c:if test="${procedimentiAltriHelper !=null }">
			<div class="titolo_sezione toggle"><fmt:message key='label.endo-altri' /></div>
			<div id="sez_procedimenti_altri" class="sezione lista-endo">	
			<ul>
				<c:forEach items="${procedimentiAltriHelper.procedimentiAltri }" var="procAltriFamiglia">
					<c:if test="${procAltriFamiglia.key.visualizza eq true }">
					<li>${procAltriFamiglia.key.descrizione }<c:if test="${empty procAltriFamiglia.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
					<ul>
					<c:forEach items="${procAltriFamiglia.value }" var="procAltriCategoria">
						<c:if test="${procAltriCategoria.key.visualizza eq true }">
						<li>${procAltriCategoria.key.descrizione }<c:if test="${empty procAltriCategoria.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
						<ul>
						<c:forEach items="${procAltriCategoria.value }" var="procAltri">
							<c:if test="${procAltri.key.visualizza eq true }">
							<li>
								<c:choose>
								<c:when test="${procAltri.value.selezionato eq true }">
								<input type="checkbox" name="codiceProcAltri" value="${procAltri.value.procedimento.codice}" checked="checked" />
								</c:when>
								<c:otherwise>
								<input type="checkbox" name="codiceProcAltri" value="${procAltri.value.procedimento.codice}" />
								</c:otherwise>
								</c:choose>
								${procAltri.key.descrizione }
							</li>
							</c:if>
						</c:forEach>
						</ul>
						</li>
						</c:if>
					</c:forEach>
					</ul>
					</li>
					</c:if>
				</c:forEach>
			</ul>
			</div>
			<div class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.altri-endo' />" onclick="submitForm('1')" />
			</div>
		</c:if>
		<%@ include file="../includes/pager.jsp" %>
	</spring-form:form>
</body>
</html>