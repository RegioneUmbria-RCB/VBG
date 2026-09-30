<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${registrazioniImporti.id.codice==null}">
			<fmt:message key="form.registrazioniimporti.title.create" />
		</c:if> 
		<c:if test="${registrazioniImporti.id.codice!=null}">
			<fmt:message key="form.registrazioniimporti.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${registrazioniImporti.id.codice==null}">
	<fmt:message key="form.registrazioniimporti.title.create" />
</c:if> 
<c:if test="${registrazioniImporti.id.codice!=null}">
	<fmt:message key="form.registrazioniimporti.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="registrazioniImporti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioniImporti" />
    </jsp:include>
    <table>
		<tr>
			<td><fmt:message key="form.registrazioniimporti.nrRata" /></td>
			<td>
				<spring-form:input id="nrRata_id" cssStyle="text-align: right;" path="nrRata" size="10" maxlength="10" /> 
		   		<spring-form:errors	path="nrRata" cssClass="error" />
		   	</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniimporti.scadenza" /></td>
			<td>
				<spring-form:input id="scadenza_id" path="scadenza" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="calscadenza" idInput="scadenza_id" textKey="label.calendar"/>
				<spring-form:errors	path="scadenza" cssClass="error" />
		   	</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniimporti.conti" />
			</td>
			<td>
			<script type='text/javascript'>
			function setHiddenFieldconti(inputField,listItem){
				var a = listItem.id;
				var itemSeparator = '<%=WebConstants.ITEM_SEPARATOR%>';
				var valuesToRetrieve = a.split(itemSeparator, 2);
				document.getElementById('conti_id').value = inputField.value;
				document.getElementById('conti_hidden').value = valuesToRetrieve[0]; //è il codice del conto
				document.getElementById('iva_id').value = valuesToRetrieve[1]; // è il coefficente dell'iva
				$('conti_id_choices').fade();	 
			}
			</script>
			
			
				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="conti" />		
					<jsp:param name="propertyPath" value="conti" />				
					<jsp:param name="pathPropertyDescription" value="conti.descrizioneConto" />
					<jsp:param name="pathPropertyCode" value="conti.id.codice" />
					<jsp:param name="autocompleterAjax" value="findContieIva.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_conto" />
					<jsp:param name="afterUpdateElement" value="setHiddenFieldconti"/>
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td> <fmt:message key="form.registrazioniimporti.importo" /></td>
			<td class="inline-ui-cell">
				<spring-form:input cssStyle="text-align: right;" id="importo_id" path="importo" size="10" maxlength="11" onchange="changeValue(this);" />
				<spring-form:errors path="importo" cssClass="error"/>
				<init:help idHelp="help_importo" textKey="form.registrazioniimporti.importo.help"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniimporti.iva" /></td>
			<td><spring-form:input cssStyle="text-align: right;" id="iva_id" path="iva" size="5" maxlength="5" /></td>
		</tr>
	</table>	
	<script type='text/javascript'>
	//<![CDATA[
		$('conti_id').focus();
		
		function changeValue(obj){
			var importo=obj.value;
			if(isNaN(importo.replace(",","."))){
				alert('<fmt:message key="alert.field.numeric" />');
				obj.value = '';
				return;
			}
			obj.value = importo.replace(".",",");
		}
//]]> 
	</script>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${registrazioniImporti.id.codice == null}">
		<li><a href="javascript:doSubmit('insertImporto.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${registrazioniImporti.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateRegistrazioniImporti.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO %>=%2Fregistrazioni%2Fview.htm?codice=${registrazioniImporti.registrazioni.id.codice }','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
