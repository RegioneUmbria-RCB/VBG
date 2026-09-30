<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
		<fmt:message key="label.lista_domandestc" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.lista_domandestc" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="domandestc" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="domandestc" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.data" /></td>
					<td><b><fmt:formatDate value="${domandestc.dataricezione }"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.stcIdnodo" /> <fmt:message key="label.mittente" /></td>
					<td><b>${domandestc.idNodo }</b></td>
				</tr>			
				<tr>
					<td><fmt:message key="label.stcIdente" /> <fmt:message key="label.mittente" /></td>
					<td><b>${domandestc.idEntemitt }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.stcIdsportello" /> <fmt:message key="label.mittente" /></td>
					<td><b>${domandestc.idSportellomitt }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.domande_mittente" /></td>
					<td><b>${domandestc.idDomandamitt }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.codice_fiscale" /></td>
					<td><b>${domandestc.codicefiscaleRichiedente }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.numero_istanza" /></td>
					<td><b>${domandestc.numeroistanza }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.nominativo" /></td>
					<td><b>${domandestc.richiedente }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.descrizione_errore" /></td>
					<td><b>${domandestc.ultimoerrore }</b></td>
				</tr>
				<tr>
					<td><fmt:message key="label.data" /></td>
					<td>
						<b><fmt:formatDate value="${domandestc.dataUltimoerrore }"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </b>
					</td>
				</tr>
				<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
					<tr>
						
						<td colspan="2">
							<jsp:include page="../includes/oggetti.jsp" >
			       				<jsp:param name="idElemento" value="oggettoIdCodice" />
			   					<jsp:param name="codiceOggetto" value="${domandestc.oggetti.id.codice}" />
			   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
			   					<jsp:param name="nomefileId" value="oggetto_nomefile" />			   					
			   				</jsp:include>
		    				<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice"/>
		    				<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile"/>
		    				<spring-form:errors path="oggetti" cssClass="error"/>
						</td>
					</tr>
				</spring-security:authorize>
				<spring-security:authorize ifNotGranted="ROLE_ADMINISTRATOR">
					<tr>
						<td colspan="2">
							<a class="dettaglioColumn" href="javascript:doHref('../file/ajaxDownload.htm?fileId=${domandestc.oggetti.id.codice}','')" title="<fmt:message key="label.visualizza" />"> <label><fmt:message key="label.visualizza" /></label></a>
						</td>
					</tr>
				</spring-security:authorize>
			</table>

		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message	key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>
