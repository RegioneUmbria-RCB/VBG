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
		<script type="text/javascript">
			//<![CDATA[
				 const array = new Array();
		   		 function changeStatus(obj){
		   		 	var i=0;
		   		 	while(i<array.length){
						if(obj.checked){
							$('check'+array[i]).checked=true;
						}else{
							$('check'+array[i]).checked=false;
						}
						i++;
					}
		   		 }
		   		function checkRelated(id1,id2){
		   			var c = $(id1);
					var cc = $(id2);
					if(c && cc){
						if(c.checked){
							cc.checked=false;
						}
						if(cc.checked){
							c.checked=false;
						}
					}
		   		 }
		   		function pushToArray(valore){
		   			array.push(valore);
		   		}
		   		
		    //]]> 
		</script>
	 	<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}</c:param>
	 	</c:import>
	 	<br class="clear"/>
	 	
	<div class="vbg-form">
				<fieldset>
					<legend>
						<fmt:message key="label.lista_autorizzazioni_concessioni" />
					</legend>
		 	
			
					<spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati">
						
						<table class="vbg-table">
						<thead>
							<tr>
								<th><fmt:message key="label.tipo"/></th>
								<th><fmt:message key="label.numero"/></th>
								<th><fmt:message key="label.data"/></th>
								<th><fmt:message key="label.data_scadenza"/></th>
								<th><fmt:message key="label.comune"/></th>
								<th><fmt:message key="label.registro"/></th>
								<th><fmt:message key="label.istanza"/></th>
								<th><fmt:message key="label.localizzazioni"/></th>
								<th><fmt:message key="label.concessione_titolare"/></th>
								<th><fmt:message key="mercatid.label.occupante"/></th>
								<th><fmt:message key="label.manifestazione"/></th>
								<th><fmt:message key="label.stato"/></th>
								<th align="center"><input type="checkbox" onclick="changeStatus(this);" title="<fmt:message key='label.checkbox.selDeselAll' />" /></th>
							</tr>
						</thead>
						<tbody>
							
							<c:forEach items="${autorizzazioniSubentriCommand.listAutDaRicerca}" var="curr_auth" varStatus="authIdx" >
							<tr>
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
								<td><c:out value="${curr_auth.autorizzazione.istanza.transientLocalizzazionePrimario}" default="-" /></td>
								<td>${curr_auth.autorizzazione.anagrafe.descrizioneRichiedente}</td>
								<td>${curr_auth.autorizzazione.occupante.descrizioneRichiedente}</td>
								<td>
									<c:if test="${curr_auth.concessione.id.codice!=null}">
										${curr_auth.concessione.mercati.descrizione } - ${curr_auth.concessione.mercatiUso.descrizione } - ${curr_auth.concessione.mercatiD.codiceposteggio}
									</c:if>
								</td>
								<td>
									<c:if test="${curr_auth.autorizzazione.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
									<c:if test="${curr_auth.autorizzazione.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.autorizzazione.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
								</td>
			
								<td align="center"><spring-form:checkbox path="listAutDaRicerca[${authIdx.index}].daSubentrare" id="check${authIdx.index }" title="Segna per subentro" />
								<script type="text/javascript">
											pushToArray('${authIdx.index}');
								</script>
							 </td>
								
							</tr>
							<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.id.codice!=null}">
								<tr>
									<td><i class="fa fa-lg fa-link"><fmt:message key="label.autorizzazione_collegata" /></i></td>
									<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autoriznumero }</td>
									<td><fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td><fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>					
									<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizcomune.comune}</td>
									<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione}</td>
									<td><c:out value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.istanza.numeroistanza}" default="-" /></td>
									<td><c:out value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.istanza.transientLocalizzazionePrimario}" default="-" /></td>
									<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.anagrafe.descrizioneRichiedente}</td>
									<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.occupante.descrizioneRichiedente}</td>
									<td>&nbsp;</td>
									<td>
										<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
										<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
									</td>
									<td>&nbsp;
									</td>
								</tr>
							</c:if>
							
							</c:forEach>
							</tbody>
						</table>
			
					</spring-form:form>
		</fieldset>
		</div>			
	</div>
	<br />
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('addToList.htm','');"><fmt:message key="button.save" /></a></li>
			<c:if test="${return_to!=null}">
			<li><a href="javascript:historySet(escape('..%2Fautorizzazionisubentri%2FcreateSearch.htm%3FcodiceIstanza%3D${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}%26return_to%3D${return_to}'), '../autorizzazioni/createAutorizzazione.htm', '');"><fmt:message key="button.nuova_autorizzazione" /></a></li>
			<li><a href="javascript:doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }&return_to=${return_to}','');"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${return_to==null}">
			<li><a href="javascript:historySet(escape('..%2Fautorizzazionisubentri%2FcreateSearch.htm%3FcodiceIstanza%3D${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}'), '../autorizzazioni/createAutorizzazione.htm', '');"><fmt:message key="button.nuova_autorizzazione" /></a></li>
			<li><a href="javascript:doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }','');"><fmt:message key="button.back" /></a></li>	
			</c:if>
		</ul>
	</div>
</body>
</html>