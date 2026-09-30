<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipisoggetto.id.codice==null}">
			<fmt:message key="tipisoggetto.label.nuovo_tipisoggetto.title" />
		</c:if> 
		<c:if test="${tipisoggetto.id.codice!=null}">
			<fmt:message key="tipisoggetto.label.dettaglio_tipisoggetto.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipisoggetto.id.codice==null}">
			<fmt:message key="tipisoggetto.label.nuovo_tipisoggetto.title" />
		</c:if> 
		<c:if test="${tipisoggetto.id.codice!=null}">
			<fmt:message key="tipisoggetto.label.dettaglio_tipisoggetto.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include> 
	<div id="subcontent">
		<spring-form:form commandName="tipisoggetto" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipisoggetto" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td colspan="2">
						<spring-form:input id="tiposoggetto_id" path="tiposoggetto" size="70" />						
						<spring-form:errors path="tiposoggetto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2">
						<spring-form:checkbox id="flagqualita_id" path="flagqualita" value="1" />
						<fmt:message key="tipisoggetto.label.flagqualita.help" />						
					</td>
				</tr>			
				<tr>
					<td></td>
					<td colspan="2">
						<spring-form:checkbox id="richiedianagrafecoll_id" path="richiedianagrafecoll" value="1" onclick="visualizzaFlgLegalerap();"/>
						<fmt:message key="tipisoggetto.label.richiedianagrafecoll" />						
					</td>
				</tr>				
				<tr id="flgLegalerap_tr_id" >
					<td></td>					
					<td colspan="2">
						<spring-form:checkbox id="flgLegalerap_id" path="flgLegalerap" value="1" />
						<fmt:message key="tipisoggetto.label.flgLegalerap" />												
					</td>					
				</tr>
				<tr class="titoloSezione">
					<td colspan="3" >
						<fmt:message key="label.dati_registro_imprese.legend"/>					
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.dati_registro_imprese.carica" />
					</td>
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="riCariche_id" />		
							<jsp:param name="propertyPath" value="riCariche" />					
							<jsp:param name="pathPropertyDescription" value="riCariche.descrizione" />
							<jsp:param name="pathPropertyCode" value="riCariche.codice" />
							<jsp:param name="autocompleterAjax" value="findRiCariche.htm" />
							<jsp:param name="titleKey" value="label.ricerca_dati_registro_imprese.carica" />
						</jsp:include>
					</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="3" >
						<fmt:message key="tipisoggetto.label.dati_frontoffice.legend"/>					
					</td>
				</tr>						
				<tr>
					<td>
						<fmt:message key="tipisoggetto.label.utilizzo" />
					</td>
					<td width="5%">
						<spring-form:select id="utilizzo_id" path="utilizzo">
							<spring-form:option value=""><fmt:message key="tipisoggetto.label.utilizzo.item_backoffice_frontoffice" /></spring-form:option>
							<spring-form:option value="B"><fmt:message key="tipisoggetto.label.utilizzo.item_backoffice" /></spring-form:option>
							<spring-form:option value="F"><fmt:message key="tipisoggetto.label.utilizzo.item_frontoffice" /></spring-form:option>
						</spring-form:select>			
					</td>
					<td>										
						<fmt:message key="tipisoggetto.label.utilizzo.help" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipisoggetto.label.tipoanagrafe" />
					</td>
					<td>
						<spring-form:select id="tipoanagrafe_id" path="tipoanagrafe">
							<spring-form:option value=""></spring-form:option>
							<spring-form:option value="F"><fmt:message key="tipisoggetto.label.tipoanagrafe.item_fisica" /></spring-form:option>
							<spring-form:option value="G"><fmt:message key="tipisoggetto.label.tipoanagrafe.item_giuridica" /></spring-form:option>
						</spring-form:select>												
					</td>
					<td>										
						<fmt:message key="tipisoggetto.label.tipoanagrafe.help" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipisoggetto.label.tipodato" />
					</td>
					<td>
						<spring-form:select id="tipodato_id" path="tipodato">
							<spring-form:option value=""></spring-form:option>
							<spring-form:option value="A"><fmt:message key="tipisoggetto.label.tipodato.item_azienda" /></spring-form:option>
							<spring-form:option value="R"><fmt:message key="tipisoggetto.label.tipodato.item_richiedente" /></spring-form:option>
							<spring-form:option value="T"><fmt:message key="tipisoggetto.label.tipodato.item_tecnico" /></spring-form:option>
						</spring-form:select>												
					</td>
					<td>										
						<fmt:message key="tipisoggetto.label.tipodato.help" />
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="tipisoggetto.label.foObbligatorio" />
					</td>
					<td>
						<spring-form:checkbox id="foObbligatorio_id" path="foObbligatorio" value="1" />
					</td>
					<td>										
						<fmt:message key="tipisoggetto.label.foObbligatorio.help" />						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipisoggetto.label.flgDatialbo" />
					</td>
					<td>
						<spring-form:checkbox id="flgDatialbo_id" path="flgDatialbo" value="1" />
					</td>
					<td>										
						<fmt:message key="tipisoggetto.label.flgDatialbo.help" />						
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="tipisoggetto.label.flgSpecificadescrizione" />
					</td>
					<td>
						<spring-form:checkbox id="flgSpecificadescrizione_id" path="flgSpecificadescrizione" value="1" />
					</td>
					<td>										
						<fmt:message key="tipisoggetto.label.flgSpecificadescrizione.help" />						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.ordine" />
					</td>
					<td colspan="2">
						<spring-form:input id="ordine_id" path="ordine" size="6" />						
						<spring-form:errors path="ordine" cssClass="error"/>
					</td>
				</tr>
				<tr class="titoloSezione">
					<td colspan="3"><fmt:message key="label.anagrafe_tributaria"/></td>
				</tr>
				<tr>
					<td><fmt:message key="label.qualifica_soggetto"/></td>
					<td colspan="2">
						<spring-form:input id="atribQualificasoggetto_id" path="atribQualificasoggetto" size="4" />						
						<spring-form:errors path="atribQualificasoggetto" cssClass="error"/>
						<init:help idHelp="atribQualificasoggetto_id_help" textKey="help.tiposoggetto.attributo_qualifica_soggetto"/>
					</td>
				</tr>
				<%--
				<c:if test="${tipisoggetto.id.codice!=null}">
				<c:if test="${mapping_soggetti eq true }">
				<tr class="titoloSezione">
					<td colspan="3"><fmt:message key="tipisoggetto.label.mapping" /></td>
				</tr>
				<c:forEach items="${tipisoggetto.tipisoggettopeoples}" var="tp">
				<tr>
					<td>${tp.id.tiporapprpeople }<a class="eliminaRiga" href="javascript:doSubmit('updateMapping.htm?op=del&tipo=${tp.id.tiporapprpeople }','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina" /></label></a></td>
					<td colspan="2"></td>
				</tr>
				</c:forEach>
				<tr>
					<td><input type="text" name="tipo" size="30"/><a class="addColumn" href="javascript:doSubmit('updateMapping.htm?op=add','',document.inviodati)" title="<fmt:message key="label.aggiungi" />"><label><fmt:message key="label.nuovo" /></label></a></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				</c:if>
				</c:if>
				 --%>
			</table>			
			<script type="text/javascript">    
			   $('tiposoggetto_id').focus();				
		       visualizzaFlgLegalerap();
		       function visualizzaFlgLegalerap(){
			       if($('richiedianagrafecoll_id')!=null && $('richiedianagrafecoll_id').checked){ 
			    	    showDiv('flgLegalerap_tr_id');					    					    
					}else{
						hideDiv('flgLegalerap_tr_id');												
					}
		       }
		    </script>		
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${tipisoggetto.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipisoggetto.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>