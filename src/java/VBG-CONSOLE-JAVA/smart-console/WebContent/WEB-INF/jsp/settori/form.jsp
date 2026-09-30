<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${settori.displayMode==settori.displayConstants.NEW}">
			<fmt:message key="settori.label.nuovo_settori.title" />
		</c:if> 
		<c:if test="${settori.displayMode==settori.displayConstants.VIEW}">
			<fmt:message key="settori.label.dettaglio_settori.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${settori.displayMode==settori.displayConstants.NEW}">
	<fmt:message key="settori.label.nuovo_settori.title" />
</c:if> 
<c:if test="${settori.displayMode==settori.displayConstants.VIEW}">
	<fmt:message key="settori.label.dettaglio_settori.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
 <jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../settori/view" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="settori" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="settori" />
    </jsp:include>
    <br class="clear"/>
	<table width="100%">
        <tr>
            <td><fmt:message key="label.codice" /></td>
            <c:if test="${settori.displayMode==settori.displayConstants.NEW}">
	        <td><spring-form:input id="codicesettore_id" path="entity.id.codicesettore" size="5" />
			<spring-form:errors path="entity.id.codicesettore" cssClass="error"/></td>
			</c:if>
			<c:if test="${settori.displayMode==settori.displayConstants.VIEW}">
			<td><spring-form:input id="codicesettore_id" path="entity.id.codicesettore" size="5" readonly="true"/>
			<init:help idHelp="helpCodice" textKey="help.codice"/></td>
			</c:if>
		</tr>
		<tr>
			<td><fmt:message key="settori.label.settore" /></td>
			<td><spring-form:textarea id="settore_id" path="entity.settore" cols="60" rows="2"/> 
				<spring-form:errors path="entity.settore" cssClass="error" tabindex="0" />
            </td>
		</tr>
        <tr>
			<td>
				<fmt:message key="settori.label.tipiunitamisura" />
			</td>
			<td>
				<spring-form:input id="tipiunitamisura_id" path="entity.tipiunitamisura.umDescrbreve" cssClass="searchbox" onchange="checkValue(this,'tipiunitamisura_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
				<init:autocompleter methodAjax="findTipiunitamisura.htm" idHidden="tipiunitamisura_hidden" idInput="tipiunitamisura_id" inputTitleKey="label.ricerca_unitamisura"></init:autocompleter>
				<spring-form:errors path="entity.tipiunitamisura" cssClass="error"/> 
				<spring-form:hidden id="tipiunitamisura_hidden" path="entity.tipiunitamisura.id.codice"  />
			</td>
		</tr>
        <tr>
			<td><fmt:message key="settori.label.flag_contamq_attivita"/></td>
			<td><spring-form:checkbox path="entity.flagContamqattivita" value="1" />
            <init:help idHelp="helpContaMq" textKey="settori.help.flag_contamq_attivita"/>
			<spring-form:errors path="entity.flagContamqattivita" cssClass="error" />
            </td>
		</tr>
		<tr>
			<td><fmt:message key="settori.label.flag_nserimento_multiplo"/></td>
			<td><spring-form:checkbox path="entity.flagInsmultiplo" value="1" />
            <init:help idHelp="helpInsMult" textKey="settori.help.flag_nserimento_multiplo"/>
			<spring-form:errors path="entity.flagInsmultiplo" cssClass="error" /></td>
		</tr>
		<tr>
        <td>
				<fmt:message key="label.disabilitato" />
		</td>
		
        <td>
				<spring-form:select id="flagDisabilitato_id" path="entity.flagDisabilitato">
				    <spring-form:option value="false" label="no" />
                    <spring-form:option value="true" label="si" />
                </spring-form:select> 
                <spring-form:errors path="entity.flagDisabilitato" cssClass="error"/></td>
			
        </tr>
		<tr class="titoloSezione">
			<td colspan="4"><fmt:message key="settori.label.fo_richiesto.title" /></td>
		</tr>
        <tr>
			<td><fmt:message key="settori.label.fo_richiesto" /></td>
			<td><spring-form:checkbox path="entity.foRichiesto" value="1" />
            <init:help idHelp="helpFoRich" textKey="settori.help.fo_richiesto"/>
			<spring-form:errors path="entity.foRichiesto" cssClass="error" /></td>
		</tr>
	</table>
	
	<script type='text/javascript'>
		$('codicesettore_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${settori.displayMode==settori.displayConstants.NEW}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${settori.displayMode==settori.displayConstants.VIEW}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
        <li><a href="javascript:doHref('listsettoriavvisi.htm?codicesettore=${settori.entity.id.codicesettore}','')"><fmt:message key="settori.button.settori_avvisi" /></a></li>
        <li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../attivita/list.htm?codicesectore=${settori.entity.id.codicesettore}','')"><fmt:message key="settori.button.attivita" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
