<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatiCfgAttivita.id.fkCodiceattivita==null}">
			<fmt:message key="form.mercatiCfgAttivita.title.create" />
		</c:if> 
		<c:if test="${mercatiCfgAttivita.id.fkCodiceattivita!=null}">
			<fmt:message key="form.mercatiCfgAttivita.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${mercatiCfgAttivita.id.fkCodiceattivita==null}">
	<fmt:message key="form.mercatiCfgAttivita.title.create" />
</c:if> 
<c:if test="${mercatiCfgAttivita.id.fkCodiceattivita!=null}">
	<fmt:message key="form.mercatiCfgAttivita.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatiCfgAttivita" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatiCfgAttivita" />
    </jsp:include>
	<table>
		<tr>
			<td>
				<fmt:message key="form.mercatiCfgAttivita.attivita" />
			</td>
			<td>
				<spring-form:input id="attivita_id" path="attivita.istat" cssClass="searchbox" onchange="checkValue(this,'attivita_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="55"/>
				<init:autocompleter methodAjax="findAttivitaPerMercato.htm?codicesettore=${codicesettore}" idHidden="attivita_hidden" idInput="attivita_id" inputTitleKey="label.ricerca_attivita"/>
				<spring-form:errors path="attivita" cssClass="error"/> 
				<spring-form:hidden id="attivita_hidden" path="attivita.id.codiceistat"  />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.mercatiCfgAttivita.coefficiente" /></td>
			<td><spring-form:input id="coefficiente_id" path="coefficiente" size="6" maxlength="6"/>
			<spring-form:errors path="coefficiente" cssClass="error" delimiter=", "/></td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('attivita_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${mercatiCfgAttivita.id.fkCodiceattivita==null}">
		<li><a href="javascript:doSubmit('insertMercatiCfgAttivita.htm?codicesettore=${codicesettore}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${mercatiCfgAttivita.id.fkCodiceattivita!=null}">
		<li><a href="javascript:doSubmit('updateMercatiCfgAttivita.htm?codicesettore=${codicesettore}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteMercatiCfgAttivita.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO %>=%2Fmercaticonfigurazione%2Fview.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
