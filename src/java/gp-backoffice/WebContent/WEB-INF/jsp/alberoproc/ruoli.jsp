<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="alberoproc.label.dettaglio_ruoli.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="alberoproc.label.dettaglio_ruoli.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
    	<div class="parametriDiv">
   			<div class="etichetta">
				<div><fmt:message key="label.intervento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${alberoproc.vwAlberoproc.scDescrizione}" /></div>
	 		</div>
		</div>	       
    <div class="clear"></div>
	<spring-form:form commandName="alberoproc" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="alberoproc" />
    </jsp:include>
    <fieldset>
    <legend><fmt:message key="alberoproc.label.ruoli_padre" /></legend>
	<table>
		<c:forEach items="${ruolisPadre}" var="ruolipadre_var" varStatus="a_padre">
			<tr>
				<td>
					<input  type="checkbox" value="${ruolipadre_var.id.codice}" name="ruoliPadre" checked="checked" disabled="disabled"/>${ruolipadre_var.ruolo}
				</td>
			</tr>
		</c:forEach>
	</table>
	</fieldset>
	<br/>
    <fieldset>
    <legend><fmt:message key="alberoproc.label.ruoli" /></legend>
	<table>
		<c:forEach items="${ruolisFiglio}" var="ruoli_var" varStatus="a">
			<tr>
				<td>
						${ruoli_var.id.codice}
				</td>
				<c:if test="${!ruoli_var.ruoloAlberoprocTransient}">
					
					<td>
						<input  type="checkbox" value="${ruoli_var.id.codice}" name="ruoliFiglio" />${ruoli_var.ruolo}
					</td>
					<td>
						<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">	
							<a class="vbg-btn btn-elimina" href="javascript:assegnaRuolo('remove',${ruoli_var.id.codice});">
							</a>
						</spring-security:authorize>
					</td>	
				</c:if>
				<c:if test="${ruoli_var.ruoloAlberoprocTransient}">
					<td>
						<input  type="checkbox" value="${ruoli_var.id.codice}" name="ruoliFiglio" checked="checked"/>${ruoli_var.ruolo}
					</td>
					<td>
					<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
						<a class="vbg-btn btn-aggiungi" href="javascript:assegnaRuolo('add',${ruoli_var.id.codice});">
						</a>
					</spring-security:authorize>
					</td>
				</c:if>
				
			</tr>
			<tr class="intestazioneTabella">
				<td colspan="3">
				</td>
			</tr>		
		</c:forEach>
	</table>
	</fieldset>
</spring-form:form>
</div>
<script type="text/javascript">

	function assegnaRuolo(azione, idruolo){
		if(azione=='add'){
			doSubmit('updateistanzeruolo.htm?codiceAlberoproc=${alberoproc.id.codice}&idruolo='+idruolo,'<fmt:message key="javascript.confirm.assegna_istanze_ruolo" />',document.inviodati)
		}else{
			doSubmit('deleteistanzeruolo.htm?codiceAlberoproc=${alberoproc.id.codice}&idruolo='+idruolo,'<fmt:message key="javascript.confirm.rimuovi_istanze_ruolo" />',document.inviodati)
		}
	}
</script>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('saveRuoli.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
		<li><a href="javascript:doSubmit('deleteistanzeruoli.htm?codiceAlberoproc=${alberoproc.id.codice}','<fmt:message key="javascript.confirm.rimuovi_istanze_ruoli" />',document.inviodati)"><fmt:message key="button.rimuovi_istanze_ruoli" /></a></li>
	</spring-security:authorize>	
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
