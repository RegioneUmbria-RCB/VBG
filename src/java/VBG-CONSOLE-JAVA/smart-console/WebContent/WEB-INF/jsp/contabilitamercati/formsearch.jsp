<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.contabilitamercati.title.search" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.contabilitamercati.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">
	<%--
	la chiave di ricerca serve per la funzionalità 'salva ricerche', utilizzare il nome della pagina jsp come valore
	--%>
	<input type="hidden" id="chiave_ricerca" value="contabilitamercati_formsearch" />
	<input type="hidden" id="nome_form_ricerca" value="mercatipresenzeT" />
	<spring-form:form commandName="mercatipresenzeT" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeT" />
    </jsp:include>
    <%-- form delle ricerche salvate --%>
	<jsp:include page="../includes/ricerche.jsp" />
    <table>
		<tr>
            <td><fmt:message key="form.contabilitamercati.anno" /></td>
            <td>
                <spring-form:select id="anno_id"  path="anno">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${anniList}" itemValue="anno" itemLabel="anno" />
				</spring-form:select>
			</td>
     	</tr>
		<tr>
			<td>
				<fmt:message key="form.contabilitamercati.mercato" />
			</td>
			<td>
				<spring-form:input id="mercati_id" path="mercato.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findMercati.htm" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercato" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercato.id.codice" />
			</td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('mercati_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('search.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
