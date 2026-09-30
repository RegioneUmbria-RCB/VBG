<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.moduli_attivi.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.moduli_attivi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice==null}">
			<fmt:message key="inventarioprocedimenti.label.moduli_attivi.title" />
		</c:if> 
		<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice!=null}">
			<fmt:message key="inventarioprocedimenti.label.moduli_attivi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.entity.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
		    <spring-form:hidden path="inventarioprocedimentisoftware.inventarioprocedimento.id.codice"/>
		    <spring-form:hidden path="inventarioprocedimentisoftware.inventarioprocedimento.procedimento"/>
			<table>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.software" />
					</td>
					<td>
					<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice==null}">		
            			<spring-form:select id="software_id" path="inventarioprocedimentisoftware.software.codice"  >
            				<spring-form:options items="${softareAttiviDaConfiguare}" itemLabel="descrizione" itemValue="codice"/>
						</spring-form:select>
					</c:if>
					<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice!=null}">
						<spring-form:select id="software_id" path="inventarioprocedimentisoftware.software.codice"  >
            				<spring-form:option value="${inventarioprocedimenti.inventarioprocedimentisoftware.software.codice}" label="${inventarioprocedimenti.inventarioprocedimentisoftware.software.descrizione}" />
						</spring-form:select>
					</c:if>
					</td>
				</tr>
				<tr>
					<td>
					    <fmt:message key="inventarioprocedimenti.label.tipimovimento" />
					</td>
					<td class="inline-ui-cell">
							<script type="text/javascript">
								var searchTT = false;
								function tipomovimentoCallBack(inputField,listItem){
									var a = listItem.id;
									document.getElementById('tipimovimento_id').value = inputField.value;
									document.getElementById('tipimovimento_hidden').value = a;
								}
								function filterMovimentiForSoftware(element, entry) {
									if(searchTT){
										return entry + "&codice=TT";
									}
									var selectObj = document.getElementById('software_id');
									var software=getSelectTextAndValue(selectObj);
									//alert(software);
									return entry + "&codice=" + software[0];
								}
								function tuttiSw(){
									searchTT = false;
									if($('tuttiSw_id').checked){
									    searchTT = true;
									}
								}
							</script>
					    <spring-form:input id="tipimovimento_id" path="inventarioprocedimentisoftware.tipimovimento.movimento" cssClass="searchbox" size="67" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
					    <init:autocompleter methodAjax='findTipiMovimentoForSoftware.htm'  idHidden="tipimovimento_hidden"  idInput="tipimovimento_id" callBack="filterMovimentiForSoftware" afterUpdateElement="tipomovimentoCallBack" inputTitleKey="label.ricerca_tipimovimento"></init:autocompleter>
						<spring-form:errors path="inventarioprocedimentisoftware.tipimovimento" cssClass="error"/> 
						<spring-form:hidden id="tipimovimento_hidden" path="inventarioprocedimentisoftware.tipimovimento.id.tipomovimento" />
						<input type="checkbox" id="tuttiSw_id" onclick="tuttiSw();" />
	                	<init:help idHelp="help4" textKey="help.movimenti_archivi_base" />
					</td>
			    </tr>
			    <tr>
			    	<td>
						<fmt:message key="label.amministrazione" />
					</td>
					<td class="inline-ui-cell">
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />		
							<jsp:param name="propertyPath" value="inventarioprocedimentisoftware.amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="inventarioprocedimentisoftware.amministrazioni.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="inventarioprocedimentisoftware.amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />	
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
						</jsp:include>		
					</td>
			    </tr>
			    
				<tr>
			    	<td>
						<fmt:message key="label.opzionale" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox path="inventarioprocedimentisoftware.flagMovOpzionale"/>
						<spring-form:errors path="inventarioprocedimentisoftware.flagMovOpzionale" cssClass="error"/>
						<init:help idHelp="flag_opzionale_help" textKey="inventarioprocedimentisoftware.flagmovopzionale.help"/> 		
					</td>
			    </tr>
			    
			</table>

		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice==null}">
				<li><a href="javascript:doSubmit('insertInvetariosoftware.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${inventarioprocedimenti.inventarioprocedimentisoftware.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateInvetariosoftware.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteInvetariosoftware.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listmodalita.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>