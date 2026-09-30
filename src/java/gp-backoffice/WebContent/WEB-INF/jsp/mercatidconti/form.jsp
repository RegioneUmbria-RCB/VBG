<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatiDConti.id.codice==null}">
			<fmt:message key="form.mercatiDConti.title.create" />
		</c:if> 
		<c:if test="${mercatiDConti.id.codice!=null}">
			<fmt:message key="form.mercatiDConti.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${mercatiDConti.id.codice==null}">
	<fmt:message key="form.mercatiDConti.title.create" />
</c:if> 
<c:if test="${mercatiDConti.id.codice!=null}">
	<fmt:message key="form.mercatiDConti.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
 
    <div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.manifestazione" />:</div>
				<div><fmt:message key="label.codiceposteggio" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${mercati.descrizione}" /></div>
				<div><c:out value="${mercatid.codiceposteggio}" /></div>
	</div>
	 </div>
	 <div class="clear"></div>	 
	<spring-form:form commandName="mercatiDConti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatiDConti" />
    </jsp:include>
	<table>
		<tr>
			<td>
				<fmt:message key="form.mercatiDConti.conto" />
			</td>
			<td>
				<spring-form:input id="conto_id" path="conto.descrizioneConto" cssClass="searchbox" onchange="checkValue(this,'conto_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="50"/>
				<init:autocompleter methodAjax="findContiMercato.htm?mercati.id.codice=${mercatiDConti.posteggio.mercati.id.codice}" idHidden="conto_hidden" idInput="conto_id" inputTitleKey="label.ricerca_conto"/>
				<spring-form:errors path="conto" cssClass="error"/> 
				<spring-form:hidden id="conto_hidden" path="conto.id.codice"  />
			</td>
		</tr>
<c:if test="${false}">
		<tr>
			<td><fmt:message key="form.mercatiDConti.flagCanone" /></td>
			<td class="inline-ui-cell">
				<spring-form:checkbox id="flagCanone_id" path="flagCanone" />
				<init:help idHelp="help1" textKey="form.mercatiDConti.flagCanone.help"/>
				<spring-form:errors path="flagCanone" cssClass="error"/>
			</td>
		</tr>
</c:if>
		<tr>
			<td><fmt:message key="form.mercatiDConti.anno" /></td>
			<td>
				<spring-form:input id="anno_id" path="anno" size="4" maxlength="4"/>
				<spring-form:errors path="anno" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.mercatiDConti.contesto" /></td>
			<td class="inline-ui-cell">
				<spring-form:select id="contesto_id" path="contesto">
					<spring-form:options items="<%=WebConstants.MERCATI_CONTESTO %>"/>
				</spring-form:select>
				<init:help idHelp="helpcontesto" textKey="form.mercatiDConti.contesto.help"/>
				<spring-form:errors path="contesto" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.mercatiConti.coefficiente" />
			</td>
			<td class="inline-ui-cell" style="min-width: 500px;">
			<spring-form:input id="coeff_id" path="transientCoefficiente" size="10" onblur="checkNumberValue(this);" onchange="resetValore('imp_id')"/> 
			<label>o <fmt:message key="form.mercatiConti.importo" /></label>
			<spring-form:input id="imp_id" path="transientImporto" size="10" onblur="checkCurrencyValue(this);" onchange="resetValore('coeff_id')" cssStyle="text-align:right;"/>&nbsp;<fmt:message key="label.valuta" />
			<spring-form:errors path="valore" cssClass="error"/>
			
			<spring-form:checkbox id="flagImportomensile_id" path="flagImportomensile" /> 
			<spring-form:errors	path="flagImportomensile" cssClass="error" />
			
			<label for="flagImportomensile_id"><fmt:message key="label.mensile" /></label>
			
			<init:help idHelp="help2" textKey="form.mercatiConti.flagValore.help"/>
			<spring-form:hidden id="hidden_flagValore" path="flagValore"  />
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="label.percentuale_consorzio" /></td>
			<td class="inline-ui-cell">
				<spring-form:input size="10" id="percentualeConsorzio_id" path="percentualeConsorzio" /> 
				<spring-form:errors	path="percentualeConsorzio" cssClass="error" /><label>%</label>
			</td>
		</tr>	
	</table>	
	<script type='text/javascript'>
		$('conto_id').focus();
		
		function resetValore(id){
			$(id).value = "";
			if(id == 'imp_id'){
				$('hidden_flagValore').value = false;
			}else{
				$('hidden_flagValore').value = true;
			}
			
		}
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${mercatiDConti.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${mercatiDConti.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?posteggio.id.codice=${mercatiDConti.posteggio.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
