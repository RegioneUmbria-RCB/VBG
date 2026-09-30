<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatidavvisi.id.codice==null}">
			<fmt:message key="label.nuovo_mercatid_avvisi.title" />
		</c:if> 
		<c:if test="${mercatidavvisi.id.codice!=null}">
			<fmt:message key="label.dettaglio_mercatid_avvisi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${mercatidavvisi.id.codice==null}">
			<fmt:message key="label.nuovo_mercatid_avvisi.title"/>
		</c:if> 
	    <c:if test="${mercatidavvisi.id.codice!=null}">
			<fmt:message key="label.dettaglio_mercatid_avvisi.title" />
		</c:if>
	</span>

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div class="parametriDiv">
	  	<div class="etichetta">
			<div><fmt:message key="label.titolare" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${titolare.descrizioneRichiedente}" /></div>
	 	</div>
    </div>
    <br />
	<div id="subcontent">
		<spring-form:form commandName="mercatidavvisi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="mercatidavvisi" />
			</jsp:include>

			<table>
				<tr>
					<td><fmt:message key="label.causale_onere" /></td>
					<td><jsp:include page="../includes/autocompletergenericoTT.jsp">
						<jsp:param name="idElemento" value="tipicausalioneri" />
						<jsp:param name="propertyPath" value="tipicausalioneri" />
						<jsp:param name="pathPropertyDescription" value="tipicausalioneri.coDescrizione" />
						<jsp:param name="pathPropertyCode" value="tipicausalioneri.id.codice" />
						<jsp:param name="autocompleterAjax" value="findTipicausalioneri.htm?codice=" />
						<jsp:param name="titleKey" value="label.causali_oneri" />
						<jsp:param name="id_help" value="help_causali_oneri" />
					</jsp:include></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.verificato"/>
					</td>
					<td>
						<spring-form:checkbox id="flagVerificato_id" path="flagVerificato" />
						<spring-form:errors path="flagVerificato" cssClass="error"/>
					</td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			
	<c:if test="${mercatidavvisi.id.codice==null}">
	<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
						key="button.insert" /></a></li>
	</c:if>
	<c:if test="${mercatidavvisi.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
    <li><a href="javascript:doHref('list.htm?posteggioId=${mercatidavvisi.mercatiD.id.codice}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>




