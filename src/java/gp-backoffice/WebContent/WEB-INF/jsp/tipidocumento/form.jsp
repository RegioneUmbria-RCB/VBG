<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipidocumento.id.codice==null}">
			<fmt:message key="tipidocumento.label.nuovo_tipidocumento.title" />
		</c:if> 
		<c:if test="${tipidocumento.id.codice!=null}">
			<fmt:message key="tipidocumento.label.dettaglio_tipidocumento.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipidocumento.id.codice==null}">
			<fmt:message key="tipidocumento.label.nuovo_tipidocumento.title" />
		</c:if> 
		<c:if test="${tipidocumento.id.codice!=null}">
			<fmt:message key="tipidocumento.label.dettaglio_tipidocumento.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipidocumento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipidocumento" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="tipidocumento.label.documento" />
					</td>
					<td>
						<spring-form:input id="documento_id" path="documento" size="70" />
						<spring-form:errors path="documento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipidocumento.label.letteretipo" />
					</td>
					<td>
						<spring-form:input id="letteretipo_id" path="letteretipo.descrizione" cssClass="searchbox" onchange="checkValue(this,'letteretipo_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="70"/>
						<init:autocompleter methodAjax="findLettereTipo.htm" idHidden="letteretipo_hidden" idInput="letteretipo_id" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter>
						<spring-form:errors path="letteretipo" cssClass="error"/> 
						<spring-form:hidden id="letteretipo_hidden" path="letteretipo.id.codice"  />
					</td>
				</tr>
				<td><fmt:message key="tipidocumento.label.oggettoxsl" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp" >
			       			<jsp:param name="idElemento" value="oggettoXslIdCodice" />
			   				<jsp:param name="codiceOggetto" value="${tipidocumento.oggettoXsl.id.codice}" />
			   				<jsp:param name="codiceOggettoId" value="oggettoXsl_id_codice" />
			   				<jsp:param name="nomefileId" value="oggettoXsl_nomefile" />
		    			</jsp:include>
		    			<spring-form:hidden path="oggettoXsl.id.codice" id="oggettoXsl_id_codice"/>
		    			<spring-form:hidden path="oggettoXsl.nomefile" id="oggettoXsl_nomefile"/>
		    			<spring-form:errors path="oggettoXsl" cssClass="error"/>
						&nbsp;<fmt:message key="tipidocumento.label.oggettoxsl.help" />
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="tipidocumento.label.numggvalidita" />
					</td>
					<td>
						<spring-form:input id="numggvalidita_id" path="numggvalidita" size="5" maxlength="5"/>
						<spring-form:errors path="numggvalidita" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('documento_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<%
	String uriStampeDoc = BackofficeNETConstants.getURL_STAMPE_TIPI_DOC() + "?CodiceTipoDocumento="+request.getParameter("codice");
	String urlStampeDoc = BackofficeNETConstants.getUrlTo(request,uriStampeDoc,"",(String)session.getAttribute(WebConstants.SOFTWARE),true);	
	pageContext.setAttribute("url_stampe",urlStampeDoc);
	%>
	<div id="functions">
		<ul>
			<c:if test="${tipidocumento.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipidocumento.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				<li><a href="javascript:void 0"	onclick="window.open('${url_stampe}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message	key="button.print" /></a></li>
			</c:if>
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>