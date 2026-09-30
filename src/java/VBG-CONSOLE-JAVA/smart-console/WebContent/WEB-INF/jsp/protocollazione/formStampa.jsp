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

<title>
		<fmt:message key="label.stampa_etichette" />
</title>
</head>
<body>
<span class="titoloPagina"> 
	<fmt:message key="label.stampa_etichette" />
</span>
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
				<table width="100%">
					<tr>
						<td><fmt:message key="label.stampante" />
						</td>
						<td>
							<spring-form:select id="stampante_id" path="stampante">
								<spring-form:options items="${listaStampanti}"/>
							</spring-form:select>
							<spring-form:errors path="stampante" cssClass="error"/>
						</td>
					</tr>
					<tr>
						<td><fmt:message key="label.numero_etichette" /></td>
						<td>
							<spring-form:input id="numeroCopie_id" path="numeroCopie" size="5" cssStyle="text-align: right;"/>
							<spring-form:errors path="numeroCopie" cssClass="error"/>
						</td>
					</tr>
				</table>			
		</spring-form:form>
	</div>	
	<script type="text/javascript">
	function stampaEtichette(){
		if($('stampante_id').value==''){
			alert('<fmt:message key="label.stampante" /> <fmt:message key="alert.required" />');
			$('stampante_id').focus();
			return;
		}
		if($('numeroCopie_id').value==''){
			alert('<fmt:message key="label.numero_etichette" /> <fmt:message key="alert.required" />');
			$('numeroCopie_id').focus();
			return;
		}else if (isNaN($('numeroCopie_id').value)){
			alert('<fmt:message key="alert.field.numeric" /> [<fmt:message key="label.numero_etichette" />]');
			$('numeroCopie_id').focus();
			return;
		}
		doSubmit('stampa.htm','', document.inviodati);	
	}			
	</script>
	<div id="functions">
		<ul>
			<li><a href="javascript:stampaEtichette();"><fmt:message key="button.stampa" /></a></li>
			<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>