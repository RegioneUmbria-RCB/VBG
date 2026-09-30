<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
	<c:if test="${tipibandooutput.id.codice==null}">
		<fmt:message key="form.tipibandooutput.title.create" />
	</c:if>
	<c:if test="${tipibandooutput.id.codice!=null}">
		<fmt:message key="form.tipibandooutput.title.view" />
	</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
	<%
	String dyn2campiRifStyle = "";
	String dyn2campiOutStyle = "";
	%>
<c:if test="${tipibandooutput.id.codice==null}">
    <%
    dyn2campiRifStyle = "display:none";
    dyn2campiOutStyle = "display:none";
    %>
	<fmt:message key="form.tipibandooutput.title.create" />
</c:if> 
<c:if test="${tipibandooutput.id.codice!=null}">
	<c:set var="BANDI_TIPOCALCOLO_VALORE" value="<%=WebConstants.BANDI_TIPOCALCOLO_VALORE %>"></c:set>
	<c:set var="BANDI_TIPOCALCOLO_ELEMENTO" value="<%=WebConstants.BANDI_TIPOCALCOLO_ELEMENTO %>"></c:set>
	<c:set var="BANDI_TIPOCALCOLO_MERCATI" value="<%=WebConstants.BANDI_TIPOCALCOLO_MERCATI %>"></c:set>
    <c:if test="${tipibandooutput.tipocalcolo eq BANDI_TIPOCALCOLO_VALORE || tipibandooutput.tipocalcolo eq BANDI_TIPOCALCOLO_MERCATI}">
		<%
		dyn2campiRifStyle = "";
		dyn2campiOutStyle = "";
		%>
	</c:if>
	<c:if test="${tipibandooutput.tipocalcolo eq BANDI_TIPOCALCOLO_ELEMENTO}">
		<%
		dyn2campiRifStyle = "display:none";
		dyn2campiOutStyle = "";
		%>
	</c:if>
	<fmt:message key="form.tipibandooutput.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
<span class="parametri"><fmt:message key="form.tipibando.title.prefix"/><label>${tipibandooutput.tipigraduatoriet.tipibando.descrizione}</label></span>
<span class="parametri"><fmt:message key="form.tipigraduatoriet.title"/><label>${tipibandooutput.tipigraduatoriet.descrizione}</label></span>
	<spring-form:form commandName="tipibandooutput" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="tipibandooutput" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.tipibandooutput.tipocalcolo" /></td>
			<td><spring-form:select id="tipocalcolo_id" path="tipocalcolo"
				onchange="mostra(this);return false;">
				<spring-form:option value=" "><fmt:message key="label.select.default" /></spring-form:option>
				<spring-form:option value="<%=WebConstants.BANDI_TIPOCALCOLO_VALORE %>"><fmt:message key="form.tipibandooutput.tipocalcolo.valore" /></spring-form:option>
				<%--
				<spring-form:option value="<%=WebConstants.BANDI_TIPOCALCOLO_MERCATI %>"><fmt:message key="form.tipibandooutput.tipocalcolo.mercati" /></spring-form:option>
				 --%>
				<spring-form:option value="<%=WebConstants.BANDI_TIPOCALCOLO_ELEMENTO %>"><fmt:message key="form.tipibandooutput.tipocalcolo.elemento" /></spring-form:option>
			</spring-form:select></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipibandooutput.tipoinput" /></td>
			<td><spring-form:select id="tipoinput_id"	items="${listTipibandoinput}" itemLabel="etichetta"		itemValue="id.codice" path="tipibandoinput.id.codice">
			</spring-form:select></td>
		</tr>
		<tr id="dyn2CampiRif" style="<%=dyn2campiRifStyle%>">
			<td><fmt:message key="form.tipibandooutput.dyn2CampiRif" /></td>
			<td><spring-form:textarea rows="1" cols="62" id="dyn2CampiRif_id" path="dyn2CampiRif.nomecampo"	cssClass="searchbox" onchange="checkValue(this,'dyn2CampiRif_hidden')" onkeydown="javascript:return searchAll(this,event)" />
			<init:autocompleter methodAjax="findDyn2Campi.htm?idModello=${tipibandooutput.tipigraduatoriet.tipibando.dyn2Modellit.id.codice}" idHidden="dyn2CampiRif_hidden" idInput="dyn2CampiRif_id" inputTitleKey="label.ricerca_campo"/>
			<spring-form:errors path="dyn2CampiRif" cssClass="error" /> 
			<spring-form:hidden id="dyn2CampiRif_hidden" path="dyn2CampiRif.id.codice" /></td>
		</tr>
 		<tr id="dyn2CampiOut" style="<%=dyn2campiOutStyle%>" >
			<td><fmt:message key="form.tipibandooutput.dyn2CampiOut" /></td>
			<td><spring-form:textarea rows="1" cols="62" id="dyn2CampiOut_id" path="dyn2CampiOut.nomecampo"	cssClass="searchbox" onchange="checkValue(this,'dyn2CampiOut_hidden')" onkeydown="javascript:return searchAll(this,event)" />
			<init:autocompleter methodAjax="findDyn2Campi.htm?idModello=${tipibandooutput.tipigraduatoriet.tipibando.dyn2Modellit.id.codice}" idHidden="dyn2CampiOut_hidden" idInput="dyn2CampiOut_id" inputTitleKey="label.ricerca_campo"/>
			<spring-form:errors path="dyn2CampiOut" cssClass="error" /> 
			<spring-form:hidden id="dyn2CampiOut_hidden" path="dyn2CampiOut.id.codice" /></td>
		</tr>
	</table>
	<script type='text/javascript'>
	$('tipocalcolo_id').focus();
	function mostra(obj) {
		var pos = obj.selectedIndex;
		var valore = '';
		if (pos > -1) {
			valore = obj.options[pos].value;
		}
		if (valore == '<%=WebConstants.BANDI_TIPOCALCOLO_ELEMENTO %>') {
			$('dyn2CampiOut').appear();
			$('dyn2CampiRif').fade();
			
		}
		if (valore == '<%=WebConstants.BANDI_TIPOCALCOLO_VALORE %>' || valore == '<%=WebConstants.BANDI_TIPOCALCOLO_MERCATI %>') {
			$('dyn2CampiOut').appear();
			$('dyn2CampiRif').appear();
		}

		if (valore == ' ' ) {
			$('dyn2CampiOut').fade();
			$('dyn2CampiRif').fade();
		}
		return false;
	}
	mostra($('tipocalcolo_id'));
</script>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if  test="${tipibandooutput.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)" ><fmt:message key="button.insert"  /></a></li>
	</c:if>
	<c:if test="${tipibandooutput.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)" ><fmt:message key="button.update"  /></a></li> 
	    <li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?tipigraduatoriet.id.codice=${tipibandooutput.tipigraduatoriet.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
