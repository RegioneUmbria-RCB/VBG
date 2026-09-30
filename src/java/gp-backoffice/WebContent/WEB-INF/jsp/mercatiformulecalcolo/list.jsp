<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_mercatiformulecalcolo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_mercatiformulecalcolo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatiformulecalcolo/list" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="mercatiformulecalcolo" />
	</jsp:include>
	<div id="subcontent">
	
	    <c:if test="${not empty mercatiformulecalcoloList}">
		<c:forEach items="${mercatiformulecalcoloList}" var="mercatiformulecalcolo" varStatus="a">
		    
			<div class="jmesa">
					<div class=titoloTabella><fmt:message key="label.giorno" />: <b>${mercatiformulecalcolo.mercatiUso.descrizione}</b>
					</div>
					<table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="40%"><fmt:message key="label.descrizione" /> </td>
				                <td width="15%" ><fmt:message key="label.data_inizio_validita" /></td>
				                <td width="15%" ><fmt:message key="label.data_fine_validita" /></td>
				                <td width="15%" ><fmt:message key="label.contesto_formule" /></td>
				                <td width="5%" ><fmt:message key="label.azioni" /></td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>
						<c:if test="${not empty mercatiformulecalcolo.mercatiFormuleCalcolos}">
						<c:forEach items="${mercatiformulecalcolo.mercatiFormuleCalcolos}" var="formula_var">
						<tr class="<%=(l%2)==0?"odd":"even"%>">
						      <td>${formula_var.descrizione}</td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${formula_var.dataInizioValidita}"/></td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${formula_var.dataFineValidita}"/></td>
    		                  <td>${formula_var.contesto.descrizione}</td>
    		                  <td>
    		                  	<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatiformulecalcolo/view.htm?codice=${formula_var.id.codice}','')" title="<fmt:message key="label.edit.record" />${formula_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
    		                  </td>
			             </tr>
						<%l++; %>
						</c:forEach>
						</c:if>
						<c:if test="${empty mercatiformulecalcolo.mercatiFormuleCalcolos}">
							<tr class="even">
								<td colspan="6"><fmt:message key="html.statusbar.noResultsFound" /></td>
							</tr>
						</c:if>
						<c:if test="${codice_uso ==null && codice_mercato!=null}">
						<tr>
							<td>
							<a class="addColumn" href="javascript:javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatiformulecalcolo/create.htm?codiceuso=${mercatiformulecalcolo.mercatiUso.id.codice}','');" title="<fmt:message key="label.nuova" />&nbsp;<fmt:message key="label.formula" />">
									<label><fmt:message key="label.add.record.image" /></label>
								</a>
							</td>
						</tr>
						</c:if>
						</tbody>
					</table>
					
					</div>
		</c:forEach>
		
		</c:if>
		<c:if test="${empty mercatiformulecalcoloList}"><fmt:message key="label.impossibile_configurazione_livello_servizio" />
			<div id="functions">
			<ul>
			    <li><a
				href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listgiorni.htm?codicemercato=${codice_mercato}','')"><fmt:message
				key="button.giorni" /></a>
				</li>
			</ul>	
			</div>
		
		</c:if>
		
	</div>
	<div id="functions">
		<ul>
		  
		 	<c:if test="${codice_uso!=null}">
		    	<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatiformulecalcolo/create.htm?codiceuso=${codice_uso}','')";><fmt:message key="button.new" /></a></li>
		    </c:if>
		    <c:if test="${codice_uso==null}">
		    <li><a
			href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/list.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.livelli_servizio" /></a></li>
			<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid/listconfigurazione.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.posteggi" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>