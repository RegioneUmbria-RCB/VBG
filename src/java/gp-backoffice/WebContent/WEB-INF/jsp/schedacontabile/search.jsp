<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.schedacontabile.title.search" />
	</title>
</head>
<body>
<span class="titoloPagina">
<fmt:message key="form.schedacontabile.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>

<div id="subcontent">
	<spring-form:form commandName="schedaContabileCommand" name="inviodati">
    <jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="schedaContabileCommand" />
    </jsp:include>
	<table>
		<tr>
			<td>
				<fmt:message key="form.schedacontabile.anagrafe" />
			</td>
			<td>
				<spring-form:input id="anagrafe_id" path="entity.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="80"/><fmt:message key="ajax.search.minchars"/>
				<init:autocompleter methodAjax="findAnagrafeRegistrazioni.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="entity" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="entity.id.codice"  />
			</td>
		</tr>
	</table>
	
	<script type='text/javascript'>
	//<![CDATA[
		$('anagrafe_id').focus(); 	
	function searchAll(inputField,evt){
		 var charCode = (evt.which) ? evt.which : event.keyCode;
		 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
		   inputField.value='%';
		 }
		}

	function checkSubmit(){
		if ($('anagrafe_hidden').value == ''){
			alert('<fmt:message key="field.required" />');
			$('anagrafe_id').focus();
			return false;
		}
		return true; 	
	}
	//]]> 
	</script>		
</spring-form:form>
</div>
<div id="functions">
<ul>
  <li><a href="javascript:if(checkSubmit()){doHref('view.htm?entity.id.codice='+$('anagrafe_hidden').value,'')}"><fmt:message key="button.search" /></a></li>
  <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
