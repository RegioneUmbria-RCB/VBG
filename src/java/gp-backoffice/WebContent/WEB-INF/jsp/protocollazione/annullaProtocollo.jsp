<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.annulla_protocollo" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message
	key="label.annulla_protocollo" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${protocolloCommand.entity.id.codice}</c:param>
	</c:import>
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="protocolloCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="protocolloCommand" />
			</jsp:include>			
			<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.NEW}">
				<table width="100%">
					<tr>
						<td><label class="required">*</label> <fmt:message key="label.motivo_annullamento" />
						</td>
						<td>
						<c:if test="${not empty motiviAnnullamento}">
							<spring-form:select id="motivoAnnullamento_id" path="motivoAnnullamento" >
								<spring-form:options items="${motiviAnnullamento}" itemLabel="descrizione" itemValue="codice"/>
							</spring-form:select>
						</c:if>	
						<c:if test="${empty motiviAnnullamento}">
							<spring-form:textarea id="motivoAnnullamento_id" path="motivoAnnullamento" cols="100" rows="5" />
						</c:if>	
							<spring-form:errors path="motivoAnnullamento" cssClass="error" />
							
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.note_annullamento" /></td>
						<td>
							<spring-form:textarea id="noteAnnullamento_id" path="noteAnnullamento" cols="100" rows="5" />
							<spring-form:errors path="noteAnnullamento" cssClass="error" />
						</td>
					</tr>		
				</table>
			</c:if>
		</spring-form:form>
	</div>
	<script type="text/javascript">
		function annulla(){
			if($('motivoAnnullamento_id').value==''){
				alert('<fmt:message key="label.motivo_annullamento" /> <fmt:message key="alert.required" />');
				$('motivoAnnullamento_id').focus();
				return;
			}
			doSubmit('annullaProtocollo.htm','<fmt:message key="javascript.confirm.annulla_protocolla" />',document.inviodati);
		}
	</script>
	<div id="functions">
		<ul>
		<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.NEW}">
			<li><a href="javascript:annulla();"><fmt:message key="button.annulla" /></a></li>
		</c:if>
			<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>