<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.configurazioni-server-email" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.configurazioni-server-email" />
	</div>
	<div class="descrizione"></div>
	<c:if test="${param.ret eq 0 }">
		<label style="color: green; font-weight: bold;"><fmt:message key='label.salvataggio-effettuato-con-successo' /></label>			
	</c:if>
	<spring-form:form commandName="configurazioneemail" action="salva.htm" method="post" name="viewForm" id="viewFormId">
	    <table class="sezione_table" border="0">	
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-from' /></td>
				<td>
					<spring-form:input path="mailFrom" cssErrorClass="validation_error_input" size="60" />
					<spring-form:errors path="mailFrom" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-to' /></td>
				<td>
					<spring-form:textarea path="mailTo" cssErrorClass="validation_error_input" rows="3" cols="60" />
					<spring-form:errors path="mailTo" cssClass="validation_error" />
					<fmt:message key='label.help.mail-from' />
				</td>
			</tr>		
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-server' /></td>
				<td>
					<spring-form:input path="mailServer" cssErrorClass="validation_error_input" size="60" />
					<spring-form:errors path="mailServer" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-port' /></td>
				<td>
					<spring-form:input path="mailPort" cssErrorClass="validation_error_input" size="10" />
					<spring-form:errors path="mailPort" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-use-autentication' /></td>
				<td>
					<spring-form:checkbox path="mailUseAutentication" cssErrorClass="validation_error_input"/>
					<spring-form:errors path="mailUseAutentication" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-protocoll' /></td>
				<td>
				
				<spring-form:select path="mailProtocoll" cssErrorClass="validation_error_input">	
					    <spring-form:option value=""></spring-form:option>				
						<spring-form:option value="SMTP">SMTP</spring-form:option>
					</spring-form:select>
					<spring-form:errors path="mailProtocoll" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-user' /></td>
				<td>
					<spring-form:input path="mailUser" cssErrorClass="validation_error_input" size="40" />
					<spring-form:errors path="mailUser" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-password' /></td>
				<td>
					<spring-form:input path="mailPassword" cssErrorClass="validation_error_input" size="40" />
					<spring-form:errors path="mailPassword" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.mail-disabilitata' /></td>
				<td>
					<spring-form:checkbox path="mailInvioDisabilitato" cssErrorClass="validation_error_input"/>
					<spring-form:errors path="mailInvioDisabilitato" cssClass="validation_error" />
				</td>
			</tr>
			
			<tr class="sezione_table_label">
				<b><td  class=sezione><fmt:message key='label.mail-template' /></td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.oggetto' /></td>
				<td>
					<spring-form:input path="mailOggetto" cssErrorClass="validation_error_input" size="60" />
					<spring-form:errors path="mailOggetto" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.corpo' /></td>
				<td>
					<spring-form:textarea path="mailCorpo" cssErrorClass="validation_error_input" rows="3" cols="60" />
					<spring-form:errors path="mailCorpo" cssClass="validation_error" />
				</td>
			</tr>
			<tr class="sezione_table_label">
				<b><td  class=sezione><fmt:message key='label.mail-segnaposto' /></td>
			</tr>
			<tr>
		        <table width="100%" border="1">
		        	<tr>
		        		<td><b>Denominazione</b></td>
		        		<td>[DENOMINAZIONE]</td>
		        		<td><b>Data inserimento</b></td>
		        		<td>[DATA_INSERIMENTO]</td>
		        		<td><b>Comune svolgimento</b></td>
		        		<td>[COMUNE_SVOLGIMENTO]</td>
		        	</tr>
		        </table>		
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