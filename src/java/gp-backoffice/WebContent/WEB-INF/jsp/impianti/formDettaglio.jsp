<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="impiantiprocedure.label.nuova" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="impiantiprocedure.label.nuova" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	<div id="subcontent">
		<spring-form:form commandName="impiantiprocedure" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="impiantiprocedure" />
		    </jsp:include>
			<table>				
				<tr>
					<td>
						<fmt:message key="label.tipiprocedure.procedura" />
					</td>

					<td>
						<spring-form:input id="tipiprocedure_id" path="tipiprocedure.procedura" cssClass="searchbox" size="80" onchange="checkValue(this,'tipimovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
						<init:autocompleter methodAjax='findTipiprocedure.htm?soloConMovimentoAvvio=true'  idHidden="tipiprocedure_hidden"  idInput="tipiprocedure_id" inputTitleKey="label.ricerca_tipiprocedure"></init:autocompleter>
						<spring-form:errors path="tipiprocedure" cssClass="error"/> 
						<spring-form:hidden id="tipiprocedure_hidden" path="tipiprocedure.id.codice"  />					    		                
		            </td>					
				</tr>
			</table>
			<script type='text/javascript'>
				function convalida(){
					if($('tipiprocedure_hidden').value==''){
						alert('<fmt:message key="field.required" />');
						return false;
					}	
					return true;
				}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:if(convalida()){doSubmit('insertDettaglio.htm?codice=${param.codice}','',document.inviodati);}"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${param.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>