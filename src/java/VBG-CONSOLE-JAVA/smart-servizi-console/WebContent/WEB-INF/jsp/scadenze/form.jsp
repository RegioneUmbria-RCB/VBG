<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.scadenza' /></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.scadenza' /></div>
	<div class="descrizione"></div>
	<c:if test="${not empty errors }">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
			<span class="ui-icon ui-icon-alert" style="float: left;margin-top: 2px;margin-right: .3em;"></span> 
			<strong><fmt:message key='label.errore-generico' /></strong>
			<ul>
			<c:forEach items="${errors }" var="error">
				<li><c:out value="${error.numeroErrore }"></c:out> - <c:out value="${error.descrizione }" escapeXml="false"></c:out></li>
			</c:forEach>
			</ul>
		</div>
		<br />
	</c:if>
	<fieldset>
		<table>
			<tr><td><fmt:message key='label.data' /></td><td>${scadenzaHelper.scadenza.datascadenzastr }</td></tr>
			<tr>
				<td><fmt:message key='label.descrizione' /></td>
				<td>
				<c:if test="${scadenzaHelper.movFatto != null }">
					<c:if test="${not empty scadenzaHelper.movFatto.parere }">
					${scadenzaHelper.movFatto.parere }
					</c:if>
					<c:if test="${empty scadenzaHelper.movFatto.parere }">
					${scadenzaHelper.movFatto.movimento }
					</c:if>
				</c:if>
				<c:if test="${scadenzaHelper.movFatto == null }">
					<c:if test="${not empty scadenzaHelper.movDaFare.parere }">
					${scadenzaHelper.movDaFare.parere }
					</c:if>
					<c:if test="${empty scadenzaHelper.movDaFare.parere }">
					${scadenzaHelper.movDaFare.movimento }
					</c:if>
				</c:if>
				</td>
			</tr>
			<tr><td><fmt:message key='label.note' /></td>
				<td>
				<spring-form:form action="terminaScadenza.htm" method="post" commandName="scadenzaHelper" name="form_termina_scadenza">
					<spring-form:textarea cols="60" rows="10" path="note"></spring-form:textarea>
				</spring-form:form>
				</td>
			</tr>
			<tr><td valign="top"><fmt:message key='label.allegati' /></td>
				<td>
					<table>
						<c:forEach items="${scadenzaHelper.allegatiCaricati}" var="allegatoCaricato" varStatus="allegatoIdx">
						<spring-form:form action="removeAllegato.htm" method="post" commandName="scadenzaHelper" name="form_allegati_remove${allegatoIdx.index }">
						<spring-form:hidden path="note" />
						<tr>
							<td>
							${allegatoCaricato.documento }
							<input type="hidden" name="idAllegato" value="${allegatoCaricato.id}" />
							</td>
							<td><input type="button" value="<fmt:message key='button.elimina' />" onclick="removeAllegato('form_allegati_remove${allegatoIdx.index }')"/></td>
						</tr>
						</spring-form:form>
						</c:forEach>
						<spring-form:form action="uploadAllegato.htm" method="post" commandName="scadenzaHelper" enctype="multipart/form-data" name="upload_allegato">
						<spring-form:hidden path="note" />
						<tr>
							<td><input type="file" name="file" size="38"></input></td>
							<td><input type="button" value="<fmt:message key='button.carica' />" onclick="uploadAllegato()"/></td>
						</tr>
						</spring-form:form>	
					</table>
				</td>
			</tr>
		</table>
	</fieldset>
	<br />
	<input type="button" value="<fmt:message key='button.conferma' />" onclick="termina()" />
	<input type="button" value="<fmt:message key='button.annulla' />" onclick="lista()" />
	
	<script type="text/javascript">
		function lista(){
			if(confirm("<fmt:message key='alert.conferma-annullamento-operazione' />")){
			$.blockUI();
			document.location.href="${pageContext.request.contextPath}/scadenze/list.htm";
			}
		};
		function uploadAllegato(){
			$.blockUI();
			document.forms["upload_allegato"].note.value=document.forms["form_termina_scadenza"].note.value;
			document.forms["upload_allegato"].submit();
		};
		function removeAllegato(formName){
			$.blockUI();
			document.forms[formName].submit();
		};
		function termina(){
			if(confirm("<fmt:message key='alert.conferma-invio-dati' />")){
			$.blockUI();
			document.forms["form_termina_scadenza"].submit();
			}
		};
	</script>
</body>
</html>