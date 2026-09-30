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
				 var array=new Array();
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
		    //]]> 
		</script>
	 	<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}</c:param>
	 	</c:import>
	 	<br class="clear"/>
		<fmt:message key="label.lista_autorizzazioni_concessioni" />
		<spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati">
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
					<td><fmt:message key="mercatid.label.occupante"/></td>
					<td><fmt:message key="label.manifestazione"/></td>
					<td><fmt:message key="label.stato"/></td>
					<td align="center"><input type="checkbox" onclick="changeStatus(this);" title="<fmt:message key='label.checkbox.selDeselAll' />" /></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<%int x=0; %>
				<c:forEach items="${autorizzazioniSubentriCommand.listAutDaRicerca}" var="curr_auth" varStatus="authIdx" >
				<tr class="<%=(x%2)==0?"odd":"even"%>">
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
						<c:if test="${not empty curr_auth.autorizzazione.istanza}">						
								${curr_auth.autorizzazione.istanza.titolareLegaleORichiedente.descrizioneRichiedente}
						</c:if>
					</td>
					
					
					<td>
						<c:if test="${curr_auth.concessione.id.codice!=null}">
							${curr_auth.concessione.mercati.descrizione } - ${curr_auth.concessione.mercatiUso.descrizione } - ${curr_auth.concessione.mercatiD.codiceposteggio}
						</c:if>
					</td>
					<td>
						<c:if test="${curr_auth.autorizzazione.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.autorizzazione.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.autorizzazione.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<c:if test="${curr_auth.autorizzazione.flagAttiva eq true}">
					<td align="center"><spring-form:checkbox path="listAutDaRicerca[${authIdx.index}].daSubentrare" id="check${authIdx.index }" title="Segna per subentro" /></td>
					</c:if>
					<c:if test="${curr_auth.autorizzazione.flagAttiva ne true}"><td>&nbsp;</td></c:if>
				</tr>
				<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.id.codice!=null}">
				<tr class="<%=(x%2)==0?"odd":"even"%>" >
					<td>AUT. COLLEGATA</td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autoriznumero }</td>
					<td><fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.autorizcomune.comune}</td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione}</td>
					<td><c:out value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.istanza.numeroistanza}" default="-" /></td>
					<td>${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.anagrafe.descrizioneRichiedente}</td>
					<td>&nbsp;</td>
					<td>
						<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
						<c:if test="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${curr_auth.concessione.autorizzazioniByFkAutconcAutcoll.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
					</td>
					<td>&nbsp;</td>
					<!--  
					<td align="center"><spring-form:checkbox path="listAutDaRicerca[${authIdx.index}].autorizzazioneCollegataHelper.daSubentrare" id="checkColl${authIdx.index }" title="Segna per subentro" /></td>
					-->
				</tr>
				</c:if>
				<script type="text/javascript">
					array.push('${authIdx.index}');
				</script>
				<%x++; %>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</spring-form:form>
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