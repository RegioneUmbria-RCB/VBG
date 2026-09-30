<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_subentri.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.gestione_subentri.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<div id="subcontent">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${codiceIstanza }</c:param>
	 	</c:import>
		<br class="clear"/>
		<div id="status_msg" class="success_header" >
           <fmt:message key="label.subentro_eseguito" />!
        </div>
		<div class="jmesa">
			<table class="table">
				<thead class="header">
				<tr>
					<td><fmt:message key="label.tipo"/></td>
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.istanza"/></td>
					<td><fmt:message key="label.anagrafe"/></td>
					<td><fmt:message key="label.manifestazione"/></td>
					<td><fmt:message key="label.stato"/></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<c:forEach items="${listAutSub}" var="curr_auth" varStatus="authIdx" >
				<tr class="odd"">
					<td>
						<c:if test="${curr_auth.concessione.id.codice!=null}"><fmt:message key="label.concessione" /></c:if>
						<c:if test="${curr_auth.concessione.id.codice==null}"><fmt:message key="label.autorizzazione" /></c:if>
					</td>
					<td>${curr_auth.autorizzazione.autoriznumero }</td>
					<td><fmt:formatDate value="${curr_auth.autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td><fmt:formatDate value="${curr_auth.autorizzazione.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${curr_auth.autorizzazione.autorizcomune.comune}</td>
					<td>${curr_auth.autorizzazione.tipologiaregistro.trDescrizione}</td>
					<td><c:out value="${curr_auth.autorizzazione.istanza.numeroistanza}" default="-" /></td>
					<td>${curr_auth.autorizzazione.anagrafe.descrizioneRichiedente}</td>
					<td>
						<c:if test="${curr_auth.concessione.id.codice!=null}">
							${curr_auth.concessione.mercati.descrizione } - ${curr_auth.concessione.mercatiUso.descrizione }
						</c:if>
					</td>
					<td>
						<c:if test="${curr_auth.autorizzazione.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.autorizzazione.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.autorizzazione.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="8">
						<div class="jmesa">
							<table class="table">
								<thead class="header">
								<tr>
									<td colspan="9"><fmt:message key="label.subentra_a" />:</td>
								</tr>
								<tr>
									<td><fmt:message key="label.numero"/></td>
									<td><fmt:message key="label.data"/></td>
									<td><fmt:message key="label.data_scadenza"/></td>
									<td><fmt:message key="label.comune"/></td>
									<td><fmt:message key="label.registro"/></td>
									<td><fmt:message key="label.istanza"/></td>
									<td><fmt:message key="label.anagrafe"/></td>
									<td><fmt:message key="label.manifestazione"/></td>
									<td><fmt:message key="label.data_cessazione"/></td>
								</tr>
								</thead>
								<tbody class="tbody">
								<%int y=0; %>
								<c:forEach items="${curr_auth.autorizzazione.autorizzazioniSubentris}" var="auth_sub" >
								<tr class="<%=(y%2)==0?"odd":"even"%>">
									<td>${auth_sub.autoriznumero }</td>
									<td><fmt:formatDate value="${auth_sub.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td><fmt:formatDate value="${auth_sub.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td>${auth_sub.autorizcomune.comune}</td>
									<td>${auth_sub.tipologiaregistro.trDescrizione}</td>
									<td><c:out value="${auth_sub.istanze.numeroistanza}" default="-" /></td>
									<td>${auth_sub.anagrafe.descrizioneRichiedente}</td>
									<td>
										<c:if test="${auth_sub.concessione.id.codice!=null}">
											${auth_sub.concessione.mercati.descrizione } - ${auth_sub.concessione.mercatiUso.descrizione }
										</c:if>
									</td>
									<td>
										<fmt:formatDate value="${auth_sub.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
									</td>
								</tr>
								<%y++; %>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
	</div>
	<br />
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createSearch.htm?codiceIstanza=${codiceIstanza }','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>