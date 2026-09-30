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
		$(document).ready(function(){
			$("#button_conferma_ruolo").click(function(){
				$.blockUI();
			});
			$("#button_annulla_ruolo").click(function(){
				$.blockUI();
				window.location.replace("view.htm");
			});
		});
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }"></c:out></div>
		
	<%@ include file="../includes/alert.jsp" %>
		
	<spring-form:form action="confermaRuolo.htm" method="post" commandName="nuovaIstanzaCommand" id="confermaRuolo">
	<input type="hidden" name="tipo" value="${tipo }" />
	<div class="titolo_sezione"><fmt:message key="label.nuovo-soggetto" />&nbsp;
	<c:choose>
			<c:when test="${tipo eq 'PF' }">
				(<fmt:message key="label.persona-fisica" />)
			</c:when>
			<c:otherwise>
				(<fmt:message key="label.persona-giuridica" />)
			</c:otherwise>
	</c:choose>
	</div>
	<div id="scelta_tipo_soggetto" class="sezione">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><fmt:message key="label.ruolo" /></td>
				<td>				
				<c:choose>
				<c:when test="${tipo eq 'PF' }">
				<spring-form:select path="altroSoggettoPFH.soggetto.tipoRapporto.idRuolo" cssErrorClass="validation_error_input">
					<spring-form:option value=""></spring-form:option>
					<c:forEach items="${tipiSoggettoPF }" var="tspf">
						<spring-form:option value="${tspf.id.codice}">${tspf.tiposoggetto}<c:if test="${tspf.foObbligatorio eq true }">*</c:if></spring-form:option>
					</c:forEach>
				</spring-form:select>
				<c:if test="${nuovaIstanzaCommand.showDescrizioneRuoloAltri eq true }">
				<spring-form:input path="descrizioneRuoloAltri" size="40" cssErrorClass="validation_error_input" />
				</c:if>
				</c:when>
				<c:otherwise>
				<spring-form:select path="altroSoggettoPGH.soggetto.tipoRapporto.idRuolo" cssErrorClass="validation_error_input">
					<spring-form:option value=""></spring-form:option>
					<c:forEach items="${tipiSoggettoPG }" var="tspg">
						<spring-form:option value="${tspg.id.codice}">${tspg.tiposoggetto}<c:if test="${tspg.foObbligatorio eq true }">*</c:if></spring-form:option>
					</c:forEach>
				</spring-form:select>
				<c:if test="${nuovaIstanzaCommand.showDescrizioneRuoloAltri eq true }">
				<spring-form:input path="descrizioneRuoloAltri" size="40" cssErrorClass="validation_error_input" />
				</c:if>
				</c:otherwise>
				</c:choose>			
				</td>
				<td class="sezione_table_buttons">
				<input type="submit" value="<fmt:message key="button.conferma" />" id="button_conferma_ruolo" />
				<input type="button" value="<fmt:message key="button.annulla" />" id="button_annulla_ruolo" />
				</td>
			</tr>
		</table>	
	</div>
	
	</spring-form:form>
	
</body>
</html>