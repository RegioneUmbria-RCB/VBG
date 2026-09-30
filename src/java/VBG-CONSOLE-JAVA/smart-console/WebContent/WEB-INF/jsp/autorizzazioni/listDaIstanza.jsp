<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_aree_pubbliche.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.gestione_aree_pubbliche.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/create" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzaAutConcHelper.istanza.id.codice}</c:param>
	 </c:import>
	 <br class="clear" />
	 <%--
	 <jsp:include page="../includes/istanze_funzioni.jsp">
    	<jsp:param name="codiceIstanza" value="${istanzaAutConcHelper.istanza.id.codice}" />
    	<jsp:param name="hideFunction" value="btnAutorizzazioniConcessioni" />
	</jsp:include>
	 --%>
	<div id="subcontent">
		 <c:if test="${not empty istanzaAutConcHelper.autorizzazioni or not empty istanzaAutConcHelper.autorizzazioniSubentri or not empty istanzaAutConcHelper.concessioni or not empty istanzaAutConcHelper.concessioniSubentri}">
		 <label for="_mostra_nascondi_id"><fmt:message key="label.visualizza_nascondi_subentri"/><input type="checkbox" id="_mostra_nascondi_id" name="_mostra_nascondi" onclick="visualizzaInfoSubentri();"/></label>
		 </c:if>
		 <c:if test="${not empty istanzaAutConcHelper.autorizzazioni}">
		 <div class="jmesa">
		 	<fieldset><legend><fmt:message key="label.autorizzazioni" /></legend>
			<table class="table">
				<thead class="header">
				<tr>
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.stato"/></td>
					<td><fmt:message key="label.edit.record"/></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<%int x=0; %>
				<c:forEach items="${istanzaAutConcHelper.autorizzazioni}" var="curr_auth" >
				<tr class="<%=(x%2)==0?"odd":"even"%>">
					<td>${curr_auth.autoriznumero }</td>
					<td><fmt:formatDate value="${curr_auth.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td><fmt:formatDate value="${curr_auth.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${curr_auth.autorizcomune.comune}</td>
					<td>${curr_auth.tipologiaregistro.trDescrizione}</td>
					<td>
						<c:if test="${curr_auth.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<td>
						<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?codiceIstanza=${istanzaAutConcHelper.istanza.id.codice}&codice=${curr_auth.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${curr_auth.id.codice }">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</td>
				</tr>
				<c:if test="${not empty curr_auth.autorizzazioniSubentris}">
				<tr id="aut_cron_sub_id" style="display: none;">
					<td>&nbsp;</td>
					<td colspan="6">
					<table class="table">
						<thead class="header">
							<tr class="titoloSezione">
								<td colspan="8"><fmt:message key="label.cronologia_subentri" /></td>
							</tr>
							<tr>
								<td><fmt:message key="label.numero"/></td>
								<td><fmt:message key="label.data"/></td>
								<td><fmt:message key="label.data_scadenza"/></td>
								<td><fmt:message key="label.comune"/></td>
								<td><fmt:message key="label.registro"/></td>
								<td><fmt:message key="label.anagrafe"/></td>
								<td><fmt:message key="label.istanza"/></td>
								<td><fmt:message key="label.data_cessazione"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
							<%int x1=0; %>
							<c:forEach items="${curr_auth.autorizzazioniSubentris}" var="curr_authSub" >
								<tr class="<%=(x1%2)==0?"odd":"even"%>">
									<td>${curr_authSub.autoriznumero }</td>
									<td><fmt:formatDate value="${curr_authSub.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td><fmt:formatDate value="${curr_authSub.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td>${curr_authSub.autorizcomune.comune}</td>
									<td>${curr_authSub.tipologiaregistro.trDescrizione}</td>
									<td>${curr_authSub.anagrafe.descrizioneRichiedente}</td>
									<td>${curr_authSub.istanze.numeroistanza}</td>
									<td>
										<fmt:formatDate value="${curr_authSub.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
									</td>
								</tr>
								<%x1++; %>
							</c:forEach>
						</tbody>
					</table>
					</td>
				</tr>
				</c:if>
				<%x++; %>
				</c:forEach>
				</tbody>
			</table>
			</fieldset>
		</div>
		</c:if>
		<c:if test="${not empty istanzaAutConcHelper.autorizzazioniSubentri}">
		<div class="jmesa">
			<fieldset><legend><fmt:message key="label.autorizzazioni_subentrate" /></legend>
			<table class="table">
				<thead class="header">
				<tr>
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.data_cessazione"/></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<%int y=0; %>
				<c:forEach items="${istanzaAutConcHelper.autorizzazioniSubentri}" var="curr_authSub" >
				<tr class="<%=(y%2)==0?"odd":"even"%>">
					<td>${curr_authSub.autoriznumero }</td>
					<td><fmt:formatDate value="${curr_authSub.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
					<td><fmt:formatDate value="${curr_authSub.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
					<td>${curr_authSub.autorizcomune.comune}</td>
					<td>${curr_authSub.tipologiaregistro.trDescrizione}</td>
					<td><fmt:formatDate value="${curr_authSub.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
				</tr>
				<tr id="autsub_subentrante_id" style="display: none;">
					<td>&nbsp;</td>
					<td colspan="5">
						<table class="table">
							<thead class="header">
							<tr class="titoloSezione">
								<td colspan="7"><fmt:message key="label.subentrante" /></td>
							</tr>
							<tr>
								<td><fmt:message key="label.numero"/></td>
								<td><fmt:message key="label.data"/></td>
								<td><fmt:message key="label.data_scadenza"/></td>
								<td><fmt:message key="label.comune"/></td>
								<td><fmt:message key="label.registro"/></td>
								<td><fmt:message key="label.anagrafe"/></td>
								<td><fmt:message key="label.istanza"/></td>
								<td><fmt:message key="label.edit.record"/></td>
							</tr>
							</thead>
							<tbody class="tbody">
							<tr class="odd">
								<td>${curr_authSub.autorizzazioni.autoriznumero }</td>
								<td><fmt:formatDate value="${curr_authSub.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
								<td><fmt:formatDate value="${curr_authSub.autorizzazioni.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
								<td>${curr_authSub.autorizzazioni.autorizcomune.comune}</td>
								<td>${curr_authSub.autorizzazioni.tipologiaregistro.trDescrizione}</td>
								<td>${curr_authSub.autorizzazioni.anagrafe.descrizioneRichiedente}</td>
								<td>${curr_authSub.autorizzazioni.istanza.numeroistanza}</td>
								<td>
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewAutorizzazione.htm?codiceIstanza=${curr_authSub.autorizzazioni.istanza.id.codice}&codice=${curr_authSub.autorizzazioni.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${curr_authSub.autorizzazioni.id.codice }">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</td>
							</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<%y++; %>
				</c:forEach>
				</tbody>
			</table>
			</fieldset>
		</div>
		</c:if>
		<c:if test="${not empty istanzaAutConcHelper.concessioni}">
		 <div class="jmesa">
		 <fieldset><legend><fmt:message key="label.concessioni" /></legend>
			<table class="table">
				<thead class="header">
				<tr>
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.manifestazione"/></td>
					<td><fmt:message key="label.mercati_uso"/></td>
					<td><fmt:message key="label.posteggio"/></td>
					<td><fmt:message key="label.stato"/></td>
					<td><fmt:message key="label.edit.record"/></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<%int z=0; %>
				<c:forEach items="${istanzaAutConcHelper.concessioni}" var="curr_conc" >
				<tr class="<%=(z%2)==0?"odd":"even"%>">
					<td>
						${curr_conc.autorizzazioniByFkAutconcAutatt.autoriznumero }
						<c:if test="${not empty curr_conc.autorizzazioniByFkAutconcAutcoll}"><a href="#" title="${curr_conc.autorizzazioniByFkAutconcAutcoll.transientEstremiAut}">(<fmt:message key="label.autorizzazione_collegata" />)</a></c:if>
					</td>
					<td><fmt:formatDate value="${curr_conc.autorizzazioniByFkAutconcAutatt.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td><fmt:formatDate value="${curr_conc.autorizzazioniByFkAutconcAutatt.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${curr_conc.autorizzazioniByFkAutconcAutatt.autorizcomune.comune}</td>
					<td>${curr_conc.autorizzazioniByFkAutconcAutatt.tipologiaregistro.trDescrizione}</td>
					<td>${curr_conc.mercati.descrizione}</td>
					<td>${curr_conc.mercatiUso.descrizione}</td>
					<td>${curr_conc.mercatiD.codiceposteggio}</td>
					<td>
						<c:if test="${curr_conc.autorizzazioniByFkAutconcAutatt.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_conc.autorizzazioniByFkAutconcAutatt.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_conc.autorizzazioniByFkAutconcAutatt.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<td>
						<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewConcessione.htm?codiceIstanza=${istanzaAutConcHelper.istanza.id.codice}&codiceAutorizzazione=${curr_conc.autorizzazioniByFkAutconcAutatt.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${curr_conc.autorizzazioniByFkAutconcAutatt.id.codice }">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</td>
				</tr>
				<c:if test="${not empty curr_conc.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris}">
				<tr id="conc_cron_sub_id" style="display: none;">
					<td>&nbsp;</td>
					<td colspan="8">
						<table class="table">
							<thead class="header">
							<tr class="titoloSezione">
								<td colspan="7"><fmt:message key="label.cronologia_subentri" /></td>
							</tr>
							<tr>
								<td><fmt:message key="label.numero"/></td>
								<td><fmt:message key="label.data"/></td>
								<td><fmt:message key="label.data_scadenza"/></td>
								<td><fmt:message key="label.comune"/></td>
								<td><fmt:message key="label.registro"/></td>
								<td><fmt:message key="label.anagrafe"/></td>
								<td><fmt:message key="label.istanza"/></td>
								<td><fmt:message key="label.data_cessazione"/></td>
							</tr>
							</thead>
							<tbody class="tbody">
							<%int z1=0; %>
							<c:forEach items="${curr_conc.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris}" var="curr_concSub" >
							<tr class="<%=(z1%2)==0?"odd":"even"%>">
								<td>${curr_concSub.autoriznumero }</td>
								<td><fmt:formatDate value="${curr_concSub.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
								<td><fmt:formatDate value="${curr_concSub.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
								<td>${curr_concSub.autorizcomune.comune}</td>
								<td>${curr_concSub.tipologiaregistro.trDescrizione}</td>
								<td>${curr_concSub.anagrafe.descrizioneRichiedente}</td>
								<td>${curr_concSub.istanze.numeroistanza}</td>
								<td>
									<fmt:formatDate value="${curr_concSub.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
								</td>
							</tr>
							<%z1++; %>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				</c:if>
				<%z++; %>
				</c:forEach>
				</tbody>
			</table>
			</fieldset>
		</div>
		</c:if>
		<c:if test="${not empty istanzaAutConcHelper.concessioniSubentri}">
		<div class="jmesa">
			<fieldset><legend><fmt:message key="label.concessioni_subentrate" /></legend>
			<table class="table">
				<thead class="header">
				<tr>
					<td><fmt:message key="label.numero"/></td>
					<td><fmt:message key="label.data"/></td>
					<td><fmt:message key="label.data_scadenza"/></td>
					<td><fmt:message key="label.comune"/></td>
					<td><fmt:message key="label.registro"/></td>
					<td><fmt:message key="label.manifestazione"/></td>
					<td><fmt:message key="label.mercati_uso"/></td>
					<td><fmt:message key="label.posteggio"/></td>
					<td><fmt:message key="label.data_cessazione"/></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<%int t=0; %>
				<c:forEach items="${istanzaAutConcHelper.concessioniSubentri}" var="curr_concSub" varStatus="ccs_idx">
				<tr class="<%=(t%2)==0?"odd":"even"%>">
					<td>
						${curr_concSub.autoriznumero }
						<c:if test="${not empty curr_concSub.autorizzazioneCollegataSubentri}"><a href="#" title="${curr_concSub.autorizzazioneCollegataSubentri.id.codice}">(<fmt:message key="label.autorizzazione_collegata" />)</a></c:if>
					</td>
					<td><fmt:formatDate value="${curr_concSub.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
					<td><fmt:formatDate value="${curr_concSub.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
					<td>${curr_concSub.autorizcomune.comune}</td>
					<td>${curr_concSub.tipologiaregistro.trDescrizione}</td>
					<c:forEach items="${curr_concSub.autorizzazioni.autorizzazioniConcessionisForFkAutconcAutatt}" var="aut_conc_var" varStatus="acv_idx">
						<c:if test="${acv_idx.count eq 1}"> 
							<td>${aut_conc_var.mercati.descrizione}</td>
							<td>${aut_conc_var.mercatiUso.descrizione}</td>
							<td>${aut_conc_var.mercatiD.codiceposteggio}</td>
						</c:if>
					</c:forEach>
					<td><fmt:formatDate value="${curr_concSub.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td> 
				</tr>
				<tr id="concsub_subentrante_id" style="display: none;">
					<td>&nbsp;</td>
					<td colspan="8">
						<table class="table">
							<thead class="header">
							<tr class="titoloSezione">
								<td colspan="7"><fmt:message key="label.subentrante" /></td>
							</tr>
							<tr>
								<td><fmt:message key="label.numero"/></td>
								<td><fmt:message key="label.data"/></td>
								<td><fmt:message key="label.data_scadenza"/></td>
								<td><fmt:message key="label.comune"/></td>
								<td><fmt:message key="label.registro"/></td>
								<td><fmt:message key="label.anagrafe"/></td>
								<td><fmt:message key="label.istanza"/></td>
								<td><fmt:message key="label.edit.record"/></td>
							</tr>
							</thead>
							<tbody class="tbody">
							<tr class="odd">
								<td>${curr_concSub.autorizzazioni.autoriznumero }</td>
								<td><fmt:formatDate value="${curr_concSub.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
								<td><fmt:formatDate value="${curr_concSub.autorizzazioni.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
								<td>${curr_concSub.autorizcomune.comune}</td>
								<td>${curr_concSub.autorizzazioni.tipologiaregistro.trDescrizione}</td>
								<td>${curr_concSub.autorizzazioni.anagrafe.descrizioneRichiedente}</td>
								<td>${curr_concSub.autorizzazioni.istanza.numeroistanza}</td>
								<td>
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewConcessione.htm?codiceIstanza=${curr_concSub.autorizzazioni.istanza.id.codice}&codiceAutorizzazione=${curr_concSub.autorizzazioni.id.codice }', '')" title="<fmt:message key="label.edit.record" /> ${curr_concSub.autorizzazioni.id.codice }">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</td>
							</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<%t++; %>
				</c:forEach>
				</tbody>
			</table>
			</fieldset>
		</div>
		</c:if>
		
		<script type="text/javascript">
		function visualizzaInfoSubentri(){
			showHideDiv("aut_cron_sub_id");
			showHideDiv("autsub_subentrante_id");
			showHideDiv("conc_cron_sub_id");
			showHideDiv("concsub_subentrante_id");			
		}
		
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../autorizzazionisubentri/createSearch.htm?resetAttrs=true&codiceIstanza=${istanzaAutConcHelper.istanza.id.codice }','')"><fmt:message key="button.subentro" /></a></li>
			<li><a href="javascript:historySet('${_urlback}','../autorizzazioni/createAutorizzazione.htm?codiceIstanza=${istanzaAutConcHelper.istanza.id.codice }','')"><fmt:message key="button.nuova_autorizzazione" /></a></li>
			<c:if test="${mercatiConfigurazione eq true}">
				<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../autorizzazioni/createConcessione.htm?codiceIstanza=${istanzaAutConcHelper.istanza.id.codice }','')"><fmt:message key="button.nuova_concessione" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>