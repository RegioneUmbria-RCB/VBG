<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_mercatilivelloservizio.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_mercatilivelloservizio.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatilivelloservizio/list" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="alberoproc" />
	</jsp:include>
	<div id="subcontent">
	
	    <c:if test="${not empty mercatilivelloservizioList}">
		<c:forEach items="${mercatilivelloservizioList}" var="mercatilivelloservizio" varStatus="a">
		    
			<div class="jmesa">
					<div class=titoloTabella><fmt:message key="label.giorno" />: <b>${mercatilivelloservizio.mercatiUso.descrizione}</b></div>
					<table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="25%"><fmt:message key="label.descrizione" /> </td>
								<td width="25%" ><fmt:message key="label.tipologia_livello_servizio" /></td>
								<td width="25%" ><fmt:message key="label.segnaposto_formula" /></td>
				                <td width="3%" align="center" ><fmt:message key="label.attivo" /></td>
				                <td width="5%" ><fmt:message key="label.tariffa" /></td>
				                <td width="5%" ><fmt:message key="label.data_inizio_validita" /></td>
				                <td width="5%" ><fmt:message key="label.data_fine_validita" /></td>
				                <td width="7%" ><fmt:message key="label.azioni" /></td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>
						<c:if test="${not empty mercatilivelloservizio.mercatiLivelloServizios}">
						<c:forEach items="${mercatilivelloservizio.mercatiLivelloServizios}" var="livello_servizio_var">
						<tr class="<%=(l%2)==0?"odd":"even"%>">
						      <td>${livello_servizio_var.descrizione}</td>
							  <td>${livello_servizio_var.livelloServizio.descrizione}</td>
							  <td>${livello_servizio_var.livelloServizio.segnaposto}</td>
    		                  <td align="center">
    		                   	<c:if test="${livello_servizio_var.attivo eq false}"><fmt:message key="label.no" /></c:if>
								<c:if test="${livello_servizio_var.attivo eq true}"><fmt:message key="label.si" /></c:if>
			              	  </td>
    		                  <td><fmt:formatNumber value="${livello_servizio_var.tariffa}" minFractionDigits="2"></fmt:formatNumber></td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${livello_servizio_var.dataInizioValidita}"/></td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${livello_servizio_var.dataFineValidita}"/></td>
    		                  <td>
    		                  	<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/view.htm?codice=${livello_servizio_var.id.codice}','')" title="<fmt:message key="label.edit.record" />${livello_servizio_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<%--  <c:if test="${livello_servizio_var.attivo eq true && livello_servizio_var.dataFineValidita == null}"> --%>
								<c:if test="${livello_servizio_var.attivo eq true }">
									<a  class="rielabora" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/createUpdateTariffaServizio.htm?codiceservizio=${livello_servizio_var.id.codice}','')" title="<fmt:message key="label.aggiorna_tariffa" />  " >
											<label><fmt:message key="label.aggiorna_tariffa" /></label>
									</a>
									<%-- 
									<a  class="blockColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/view.htm?codice=${livello_servizio_var.id.codice}&impostachiusura=1','')" title="<fmt:message key="label.termina_tariffa" />  " >
											<label><fmt:message key="label.termina_tariffa" /></label>
									</a>
									--%>
								</c:if>
								<c:if test="${codice_uso!=null}">
									<a  class="eliminaRiga" href="javascript:doHref('deleteSingoloLivello.htm?codice=${livello_servizio_var.id.codice}&codiceuso=${codice_uso}&codicemercato=${codice_mercato}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />  " >
										<label><fmt:message key="label.elimina" /></label>
								</c:if>
								<c:if test="${codice_uso==null && codice_mercato!=null}">
									<a  class="eliminaRiga" href="javascript:doHref('deleteSingoloLivello.htm?codice=${livello_servizio_var.id.codice}&codicemercato=${codice_mercato}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />  " >
										<label><fmt:message key="label.elimina" /></label>
								</c:if>
								
    		                  </td>
			             </tr>
						<%l++; %>
						</c:forEach>
						</c:if>
						<c:if test="${empty mercatilivelloservizio.mercatiLivelloServizios}">
							<tr class="even">
								<td colspan="8"><fmt:message key="html.statusbar.noResultsFound" /></td>
							</tr>
						</c:if>
						</tbody>
					</table>
					</div>
		</c:forEach>
		</c:if>
		<c:if test="${empty mercatilivelloservizioList}"><fmt:message key="label.impossibile_configurazione_livello_servizio" />
			<div id="functions">
			<ul>
			    <li><a
				href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercati/listgiorni.htm?codicemercato=${codice_mercato}','')"><fmt:message
				key="button.giorni" /></a>
				</li>
			</ul>	
			</div>
		
		</c:if>
		
		<%-- 
		<form name="mercatilivelloservizioForm" action="list.htm">
			<jmesa:springTableFacade
				id="mercatilivelloservizio_id" 
				items="${mercatilivelloservizioList}" 
				var="mercatilivelloservizio_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${mercatilivelloservizio_var.id.codice}">${mercatilivelloservizio_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="property1" titleKey="mercatilivelloservizio.label.property1.table" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${mercatilivelloservizio_var.id.codice}" title="<fmt:message key="label.edit.record" />${mercatilivelloservizio_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="mercatilivelloservizio.label.lista_mercatilivelloservizio.title" />';
		</script>
		--%>
	</div>
	<div id="functions">
		<ul>
		 	<c:if test="${codice_uso!=null}">
		    	<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/create.htm?codiceuso=${codice_uso}','')";><fmt:message key="button.new" /></a></li>
		    </c:if>
		    <c:if test="${codice_uso==null && codice_mercato!=null}">
		    	<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/create.htm?codicemercato=${codice_mercato}','')";><fmt:message key="button.new" /></a></li>
		    </c:if>
		   
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>