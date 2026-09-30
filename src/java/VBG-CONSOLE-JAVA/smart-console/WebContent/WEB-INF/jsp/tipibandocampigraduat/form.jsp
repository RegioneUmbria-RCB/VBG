
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipibandocampigraduat.id.codice==null}">
			<fmt:message key="form.tipibandocampigraduat.title.create" />
		</c:if> 
		<c:if test="${tipibandocampigraduat.id.codice!=null}">
			<fmt:message key="form.tipibandocampigraduat.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipibandocampigraduat.id.codice==null}">
	<fmt:message key="form.tipibandocampigraduat.title.create" />
</c:if> 
<c:if test="${tipibandocampigraduat.id.codice!=null}">
	<fmt:message key="form.tipibandocampigraduat.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
    <span class="parametri"><fmt:message key="form.tipibando.title.prefix"/> <label>${tipibandocampigraduat.tipigraduatoriet.tipibando.descrizione}</label></span> 
	<span class="parametri"><fmt:message key="form.tipigraduatoriet.title"/> <label>${tipibandocampigraduat.tipigraduatoriet.descrizione}</label></span>
	<spring-form:form commandName="tipibandocampigraduat" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipibandocampigraduat" />
    </jsp:include>
    <c:if test="${tipibandocampigraduat.ordine != null}">
	<table>
        <tr>
			<td><fmt:message key="form.tipibandocampigraduat.dyn2Campi" /></td>
			<td>
				<spring-form:textarea rows="1" cols="62" id="dyn2Campi_id" path="dyn2Campi.nomecampo" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'dyn2Campi_hidden')" />
				<init:autocompleter methodAjax="findDyn2Campi.htm?idModello=${tipibandocampigraduat.tipigraduatoriet.tipibando.dyn2Modellit.id.codice}" idHidden="dyn2Campi_hidden" idInput="dyn2Campi_id" inputTitleKey="label.ricerca_campo"/>
				<spring-form:errors path="dyn2Campi" cssClass="error" />
				<spring-form:hidden id="dyn2Campi_hidden" path="dyn2Campi.id.codice" />
			</td>
		</tr>
        <tr>
           <td>
				<fmt:message key="form.tipibandocampigraduat.ordinamento" />
		   </td>
           <td>
              <spring-form:select path="ordinamentoascdesc">
                   <spring-form:option value="ASC" label="Crescente"/>
                   <spring-form:option value="DESC" label="Decrescente"/>
              </spring-form:select>&nbsp;
              <init:help idHelp="help1" textKey="form.tipibandocampigraduat.ordinamento.help"/>				
             </td> 
       </tr>
      	<tr>
			<td><fmt:message key="form.tipibandocampigraduat.ordine" /></td>
			<td>
				<spring-form:input id="ordine_id" path="ordine" size="1" maxlength="1"/>
				<spring-form:errors path="ordine" cssClass="error"/>
			</td>
		</tr>
	</table>
	<script type='text/javascript'>
		$('dyn2Campi_id').focus();
		function searchAll(inputField,evt){
		 var charCode = (evt.which) ? evt.which : event.keyCode;
		 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
		   inputField.value='%';
		 }
		}
	</script>
	</c:if>
	<c:if test="${tipibandocampigraduat.ordine == null}">
		<p class="error">
			<fmt:message key="form.tipibandocampigraduat.ordine.errormaxorder"/>
		</p>
	</c:if>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipibandocampigraduat.id.codice==null && (tipibandocampigraduat.ordine <= 9 || tipibandocampigraduat.ordine == null)}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipibandocampigraduat.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?tipigraduatoriet.id.codice=${tipibandocampigraduat.tipigraduatoriet.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
