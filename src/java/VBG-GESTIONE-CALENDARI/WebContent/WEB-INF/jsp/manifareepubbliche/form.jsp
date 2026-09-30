<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.manifestazione-aree-pubbliche" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.manifestazione-aree-pubbliche" />
	</div>
	<div class="descrizione"></div>
	<c:if test="${param.ret eq 0 }">
		<label style="color: green; font-weight: bold;"><fmt:message key='label.salvataggio-effettuato-con-successo' /></label>			
	</c:if>
	<spring-form:form commandName="manifareepubblicheCommand" action="salva.htm" method="post" name="viewForm" id="viewFormId">
		<input type="hidden" name="returnto" value="${returnto }" />
	    <table class="sezione_table" border="0">	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.data-inserimento' />*</td>
				<td colspan="3">
					<spring-form:input path="entity.dataInserimento" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="entity.dataInserimento" cssClass="validation_error" />
				</td>
			</tr>	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.denominazione' />*</td>
				<td colspan="3">
					<spring-form:input path="entity.denominazione" cssErrorClass="validation_error_input" maxlength="1000" size="60" />
					<spring-form:errors path="entity.denominazione" cssClass="validation_error" />
				</td>
			</tr>	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.tipologia' />*</td>
				<td>
					<spring-form:select path="entity.tipologia" cssErrorClass="validation_error_input">				
						<spring-form:option value="MERCATO"></spring-form:option>
						<spring-form:option value="FIERA"></spring-form:option>
					</spring-form:select>
					<spring-form:errors path="entity.tipologia" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.cadenza' />*</td>
				<td>
					<spring-form:select path="entity.cadenza" cssErrorClass="validation_error_input">				
						<spring-form:option value=""></spring-form:option>				
						<spring-form:option value="GIORNALIERA"></spring-form:option>
						<spring-form:option value="SETTIMANALE"></spring-form:option>
						<spring-form:option value="QUINDICINALE"></spring-form:option>
						<spring-form:option value="MENSILE"></spring-form:option>
						<spring-form:option value="TRIMESTRALE"></spring-form:option>
						<spring-form:option value="SEMESTRALE"></spring-form:option>
						<spring-form:option value="ANNUALE"></spring-form:option>
						<spring-form:option value="ESTIVO"></spring-form:option>
					</spring-form:select>
					<spring-form:errors path="entity.cadenza" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.giornate' /></td>
				<td>
					<spring-form:checkbox path="entity.mon" value="true" /><fmt:message key='label.mon' />&nbsp;
					<spring-form:checkbox path="entity.tue" value="true" /><fmt:message key='label.tue' />&nbsp;
					<spring-form:checkbox path="entity.wen" value="true" /><fmt:message key='label.wend' />&nbsp;
					<spring-form:checkbox path="entity.thu" value="true" /><fmt:message key='label.thu' /><br/>
					<spring-form:checkbox path="entity.frid" value="true" /><fmt:message key='label.frid' />&nbsp;
					<spring-form:checkbox path="entity.sat" value="true" /><fmt:message key='label.sat' />&nbsp;
					<spring-form:checkbox path="entity.sun" value="true" /><fmt:message key='label.sun' />&nbsp;
					<%-- <spring-form:errors path="cadenza" cssClass="validation_error" /> --%>
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.periodo-svolgimento' /></td>
				<td colspan="3">
					<!--  <c:set var="codiceManifestazione" value="${manifestazioniCommand.festeSagre.id.codice}" />--> 
					<spring-form:input path="entity.periodoSvolgimento" cssErrorClass="validation_error_input" size="60" />
					<spring-form:errors path="entity.periodoSvolgimento" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.numero-posteggi-assegnati' /></td>
				<td colspan="3">
					<!--  <c:set var="codiceManifestazione" value="${manifestazioniCommand.festeSagre.id.codice}" />--> 
					<spring-form:input path="entity.numeroPosteggiAssegnati" cssErrorClass="validation_error_input" size="5" />
					<spring-form:errors path="entity.numeroPosteggiAssegnati" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.numero-posteggi-spuntisti' /></td>
				<td colspan="3">
					<!--  <c:set var="codiceManifestazione" value="${manifestazioniCommand.festeSagre.id.codice}" />--> 
					<spring-form:input path="entity.numeroPosteggiSpuntisti" cssErrorClass="validation_error_input"  size="5" />
					<spring-form:errors path="entity.numeroPosteggiSpuntisti" cssClass="validation_error" />
				</td>
			</tr>	
			
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.comune-svolgimento' />*</td>
				<td>
					<spring-form:select path="entity.comuni.codicecomune" cssErrorClass="validation_error_input">
						<c:if test="${fn:length(comuni) gt 1 }">
						<spring-form:option value=""></spring-form:option>
						</c:if>
						<spring-form:options items="${comuni }" itemLabel="comune.comune" itemValue="id.codicecomune" />
					</spring-form:select>
					<spring-form:errors path="entity.comuni.codicecomune" cssClass="validation_error" />
				</td>
			</tr>
			
		</table>
	
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">
			<spring-security:authorize ifNotGranted="ROLE_READONLY">
			<input type="button" value="<fmt:message key='button.salva' />" id="salva" />
			<c:if test="${manifareepubblicheCommand.entity.id.codice !=null}">
				<input type="button" value="<fmt:message key='button.elimina' />" id="delete" />
			</c:if>
		
			</spring-security:authorize>
			<input type="button" value="<fmt:message key='button.chiudi' />" id="chiudi" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">
		$(".data").datepicker();
		
		$("#salva").click(function(){
			document.viewForm.submit();
		});
		
		$("#delete").click(function(){
			if(confirm("<fmt:message key='alert.elimina' />")){
			window.location.replace("${pageContext.request.contextPath}/manifareepubbliche/delete.htm?codice=${manifareepubblicheCommand.entity.id.codice}");
			}
		});
		
	</script>
	<c:choose>
	<c:when test="${returnto eq 'list'}">
	<script type="text/javascript">
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/manifareepubbliche/search.htm");
		});
	</script>
	</c:when>
	<c:otherwise>
	<script type="text/javascript">
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/home/start.htm");
		});
	</script>
	</c:otherwise>
	</c:choose>
</body>
</html>