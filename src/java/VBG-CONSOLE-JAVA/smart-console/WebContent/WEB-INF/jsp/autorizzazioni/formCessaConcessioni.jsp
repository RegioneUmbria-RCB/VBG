<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.cessa_concessioni.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.cessa_concessioni.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/createCessaConcessioniManifestazione" />
	</jsp:include>
	<div id="subcontent">
		<span class="parametri">   
	    	<fmt:message key="label.manifestazione" />: <label> <c:out value="${mercato.descrizione}"></c:out></label>
	    </span>
	    <span class="parametri">
	    	<fmt:message key="label.mercati_uso" />: <label> <c:out value="${uso.descrizione}"></c:out></label>	
    	</span>
		<spring-form:form commandName="autorizzazioniCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="autorizzazioniCommand" />
			</jsp:include>
			<c:if test="${not empty concessionis}">
			<table>
				<tr>
					<td><fmt:message key="label.causale_cessazione" /></td>
					<td>
						<spring-form:select id="selectCausaleCessazione" path="causaleCessazioneMassiva.id.codice">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${concessionicausalis}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="causaleCessazioneMassiva.id.codice" cssClass="error" />
					</td>
					
					<td><fmt:message key="label.data_cessazione" /></td>
					<td>
						<spring-form:input id="data_cessazione_id" path="dataCessazioneMassiva" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_data_cessazione_id" idInput="data_cessazione_id" textKey="label.calendar"/> 
						<spring-form:errors path="dataCessazioneMassiva" cssClass="error" />
					</td>			
				</tr>
				<tr>
					<td><fmt:message key="label.mercati_uso" /></td>
					<td>
					    <spring-form:select  path="mercatiUso.id.codice" onchange="changeUso(this);">
					     	<option value="">Tutti</option>
					        <c:forEach items="${mercatiUsos}" var="uso_var">
					            <c:if test="${uso!=null && uso.id.codice ne uso_var.id.codice }">
					       			<option value="${uso_var.id.codice}">${uso_var.descrizione}</option>
					       		</c:if>
					       		<c:if test="${uso!=null && uso.id.codice eq uso_var.id.codice }">
					       			<option value="${uso_var.id.codice}" selected="selected">${uso_var.descrizione}</option>
					       		</c:if>
					       		<c:if test="${uso==null}">
					       			<option value="${uso_var.id.codice}">${uso_var.descrizione}</option>
					       		</c:if>
					        </c:forEach>
					  </spring-form:select>
					</td>
				</tr>
			</table>
			</c:if>
			<c:if test="${empty concessionis}">
				<table width="100%">
				<tr> 
				    
					<td width="5%"><fmt:message key="label.mercati_uso" /></td>
					<td>
					    <spring-form:select  path="mercatiUso.id.codice" onchange="changeUso(this);">
					     	<option value="">Tutti</option>
					        <c:forEach items="${mercatiUsos}" var="uso_var">
					            <c:if test="${uso!=null && uso.id.codice ne uso_var.id.codice }">
					       			<option value="${uso_var.id.codice}">${uso_var.descrizione}</option>
					       		</c:if>
					       		<c:if test="${uso!=null && uso.id.codice eq uso_var.id.codice }">
					       			<option value="${uso_var.id.codice}" selected="selected">${uso_var.descrizione}</option>
					       		</c:if>
					       		<c:if test="${uso==null}">
					       			<option value="${uso_var.id.codice}" >${uso_var.descrizione}</option>
					       		</c:if>
					        </c:forEach>
					    </spring-form:select>
					</td>
				</tr>
				</table>
			
			<span style="padding-top: 10px;" class="parametri">   
	    		  <label><fmt:message key="label.help.concessioni_mercato_uso_non_presenti" /></label>
	    	</span>
	    	</c:if>
			<script type="text/javascript">
					function changeUso(id){
						doHref('../autorizzazioni/createCessaConcessioniManifestazione.htm?codiceMercato=${mercato.id.codice}&codiceUso='+id.value,'');
					}
			</script> 	
		</spring-form:form>
		<c:if test="${not empty concessionis}">
		<br />
		<span class="titoloTabella"><fmt:message key="label.concessioni_attive" /></span>
		<form name="concessioniAttive" action="createCessaConcessioniManifestazione.htm">
				<jmesa:springTableFacade
					id="concessioniAttive_id" 
					items="${concessionis}" 
					var="conc"
					maxRows="10" 
					exportTypes="" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
                            <jmesa:htmlColumn property="mercatiD.codiceposteggio" titleKey="label.posteggio">
                            	${conc.mercatiD.codiceposteggio} <c:if test="${not empty conc.autorizzazioniByFkAutconcAutatt.autorizzazioniSubentris }"><img src="${pageContext.request.contextPath}/images/lettera_s.gif" title="Subentro" style="width: 16px;height: 16px;" /></c:if>	
                            </jmesa:htmlColumn>					
							<jmesa:htmlColumn property="autorizzazioniByFkAutconcAutatt.transientEstremiAut" titleKey="label.estremi_concessione" />
							<jmesa:htmlColumn property="autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" titleKey="label.concessione_titolare" />
							<jmesa:htmlColumn property="autorizzazioniByFkAutconcAutatt.autorizdataregistr" titleKey="label.concessione_data_rilascio_concessione" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.AutorizzazioniDataAutFilter" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../autorizzazioni/viewConcessione.htm?codiceAutorizzazione=${conc.autorizzazioniByFkAutconcAutatt.id.codice }&codiceIstanza=${conc.autorizzazioniByFkAutconcAutatt.istanza.id.codice}', '')" >
								<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${mercato.id.codice}" name="codiceMercato"/>
				<input type="hidden" value="${uso.id.codice}" name="codiceUso"/>
			</form>
			</c:if>		
	</div>
	<div id="functions">
		<ul>
			<c:if test="${not empty concessionis}">
			<li><a href="javascript:doSubmit('cessaConcessioniManifestazione.htm?codiceMercato=${mercato.id.codice }&codiceUso=${uso.id.codice }&escludiNuoveDaSubentro=false','<fmt:message key="javascript.confirm.update" />',document.inviodati)"><fmt:message key="button.cessa_tutte_le_concessioni" /></a></li>
			<li><a href="javascript:doSubmit('cessaConcessioniManifestazione.htm?codiceMercato=${mercato.id.codice }&codiceUso=${uso.id.codice }&escludiNuoveDaSubentro=true','<fmt:message key="javascript.confirm.update" />',document.inviodati)"><fmt:message key="button.cessa_tutte_le_concessioni_tranne_nuove_da_subentro" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>
