<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Dyn2Campi"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${dyn2campi.id.codice==null}">
			<fmt:message key="label.nuovo_dyn2campi.title" />
		</c:if> 
		<c:if test="${dyn2campi.id.codice!=null}">
			<fmt:message key="label.dettaglio_dyn2campi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${dyn2campi.id.codice==null}">
			<fmt:message key="label.nuovo_dyn2campi.title" />
		</c:if> 
		<c:if test="${dyn2campi.id.codice!=null}">
			<fmt:message key="label.dettaglio_dyn2campi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="dyn2campi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="dyn2campi" />
		    </jsp:include>
		    
		    <jsp:include page="../includes/history.jsp">
		   	<jsp:param name="path" value="../dyn2campi/view" />
		   	<jsp:param name="qs" value="codice%3D${dyn2campi.id.codice}"/>
		</jsp:include>
		    
			<table width="100%">
				<tr>
					<td>
						<fmt:message key="label.nome_campo" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="nomecampo_id" path="nomecampo" size="50" />
						<spring-form:errors path="nomecampo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.etichetta" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="etichetta_id" path="etichetta" size="50" />
						<spring-form:errors path="etichetta" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:textarea id="descrizione_id" path="descrizione" cols="57" rows="6" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<input type="hidden" name="dyn2Basecontesti.id" value="IS" id="contesti_id"/>				
				<%--
				<tr>
					<td><fmt:message key="label.contesti"/></td>
					<td>
						
						<spring-form:select id="contesti_id" path="dyn2Basecontesti.id"> 
							<spring-form:option value="" ><fmt:message key='label.nessuno_funzioni_non_disponibili'/></spring-form:option>
							<spring-form:options items="${basecontestis}" itemLabel="contesto" itemValue="id" />
						</spring-form:select>
						<spring-form:errors path="dyn2Basecontesti" cssClass="error"/>
					</td>
				</tr>
				 --%>
				<c:if test="${dyn2campi.id.codice==null}">
				<tr>
					<td><fmt:message key="label.tipo_dato"/></td>
					<td class="inline-ui-cell">
						<spring-form:select id="tipo_dato_id" path="tipodato"> 
							<spring-form:options items="${enumTipi}" itemLabel="valore" itemValue="chiave"  />
						</spring-form:select>
						<spring-form:errors path="tipodato" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${dyn2campi.id.codice!=null}">
					<tr>
						<td><fmt:message key="label.tipo_dato"/></td>
						<td class="inline-ui-cell">
							<spring-form:select id="tipo_dato_id" path="tipodato"> 
								<spring-form:options items="${enumTipi}" itemLabel="valore" itemValue="chiave"  disabled="true"/>
							</spring-form:select>
							<spring-form:errors path="tipodato" cssClass="error"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${dyn2campi.id.codice!=null}">
				<tr  class="titoloSezione">
					<td colspan="2"><fmt:message key="label.proprieta_controllo"/></td>
				</tr>
				
				<c:forEach items="${dyn2campi.dyn2Campiproprietas}" var="current" varStatus="a">
				<tr>
					<td width="30%">
						${current.etichettaTransiet}
					</td>
					<td class="inline-ui-cell">
					
						<c:if test="${current.tipologiaCampoTransient eq 'selectTipoRicerca'}">
						<spring:bind path="dyn2Campiproprietas[${a.index}].valore">
							<c:if test="${status.value eq 0}" >
							<select name="${status.expression}" >
							  	<option value="0"><fmt:message key="label.mostra_risultati_con_testo_ricercato"/></option>
							  	<option value="1"><fmt:message key="label.mostra_risultati_che_iniziano_con_testo_ricercato"/></option>
							</select>
							</c:if>
							<c:if test="${status.value eq 1}" >
							<select name="${status.expression}" >
							  	<option value="1"><fmt:message key="label.mostra_risultati_che_iniziano_con_testo_ricercato"/></option>
							  	<option value="0"><fmt:message key="label.mostra_risultati_con_testo_ricercato"/></option>
							</select>
							</c:if>
					        <spring-form:errors path="dyn2Campiproprietas[${a.index}].valore" cssClass="error" />
						</spring:bind> 
						</c:if>
					
						<c:if test="${current.tipologiaCampoTransient eq 'select'}">
						<spring:bind path="dyn2Campiproprietas[${a.index}].valore">
							<c:if test="${status.value eq false}" >
							<select name="${status.expression}" >
							  	<option value="false">No</option>
							  	<option value="true">Si</option>
							</select>
							</c:if>
							<c:if test="${status.value eq true}" >
							<select name="${status.expression}" >
							  	<option value="true">Si</option>
							  	<option value="false">No</option>
							</select>
							</c:if>
					        <spring-form:errors path="dyn2Campiproprietas[${a.index}].valore" cssClass="error" />
						</spring:bind> 
						</c:if>
						<c:if test="${current.tipologiaCampoTransient eq 'input'}">
						<spring:bind path="dyn2Campiproprietas[${a.index}].valore">
							<input type="text" name="${status.expression}" value="${status.value}" size="50"/>
							<spring-form:errors path="dyn2Campiproprietas[${a.index}].valore" cssClass="error" />
						</spring:bind> 
					    </c:if>
				    </td>
				</tr>
			</c:forEach>
		</c:if>
				
				
			</table>
			<script type='text/javascript'>
			if($('nomecampo_id')){
				$('nomecampo_id').focus();
			}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${dyn2campi.id.codice==null}">
				<li><a href="javascript:doSubmit('${dyn2campi.prefixPopup}insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			
			<c:if test="${dyn2campi.id.codice!=null}">
				<li><a href="javascript:doSubmit('${dyn2campi.prefixPopup}update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<c:if test="${dyn2campi.popup eq false or empty dyn2campi.popup}">	
				<li><a href="javascript:historySet('${_urlback}','../dyn2campi/viewFormule.htm?codice=${dyn2campi.id.codice}','',document.inviodati)"><fmt:message key="button.formule" /></a></li>				
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			</c:if>
			<c:if test="${dyn2campi.popup eq false or empty dyn2campi.popup}">
				<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		    </c:if>
		    <c:if test="${dyn2campi.popup eq true}">
				<li><a href="javascript:self.close()"><fmt:message key="button.back" /></a></li>
			</c:if>
			
		</ul>
	</div>
	<c:if test="${dyn2campi.popup eq true}">
		<%if(StringUtils.defaultString(request.getParameter("done"),"false").equalsIgnoreCase("true")){ 		
		String desc = ((Dyn2Campi)request.getAttribute("dyn2campi")).getNomecampo().replace("'","\\'"); 		
		%>			
		<script type="text/javascript">
		jQuery(document).ready(function(){
			
			opener.jQuery('#${dyn2campi.popupCaller}_id1').val('<%= desc%>');
			opener.jQuery('#${dyn2campi.popupCaller}_id2').val('<%= desc%>');
			opener.jQuery('#${dyn2campi.popupCaller}_hidden').val('${dyn2campi.id.codice}');
			opener.jQuery('#${dyn2campi.popupCaller}_id1').change();
			opener.jQuery('#${dyn2campi.popupCaller}_id2').change();
		});
		</script>				
		<%} %>
	</c:if>	
</body>
</html>