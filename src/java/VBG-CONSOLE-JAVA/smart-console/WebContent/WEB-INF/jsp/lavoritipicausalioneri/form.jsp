<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${lavoritipicausalioneri.id.codice==null}">
			<fmt:message key="lavoritipicausalioneri.label.nuovo_lavoritipicausalioneri.title" />
		</c:if> 
		<c:if test="${lavoritipicausalioneri.id.codice!=null}">
			<fmt:message key="lavoritipicausalioneri.label.dettaglio_lavoritipicausalioneri.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${lavoritipicausalioneri.id.codice==null}">
			<fmt:message key="lavoritipicausalioneri.label.nuovo_lavoritipicausalioneri.title" />
		</c:if> 
		<c:if test="${lavoritipicausalioneri.id.codice!=null}">
			<fmt:message key="lavoritipicausalioneri.label.dettaglio_lavoritipicausalioneri.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<c:if test="${not empty param.codiceLavoro}">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="lavoritipicausalioneri.label.lavoritipi" />:</div>
			</div>
			<div class="parametro">
				<div>${lavoritipicausalioneri.lavoritipi.lavoro}</div>
			</div>
		</div>
	</c:if>
	<br />
	<div id="subcontent">		
		<spring-form:form commandName="lavoritipicausalioneri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="lavoritipicausalioneri" />
		    </jsp:include>
			<table>
				<c:if test="${empty param.codiceLavoro}">
				<tr>
					<td>
						<fmt:message key="lavoritipicausalioneri.label.lavoritipi" />
					</td>
					<td>
						<spring-form:input id="lavoritipi_id" size="50" path="lavoritipi.lavoro" cssClass="searchbox" onchange="checkValue(this,'lavoritipi_hidden')"  onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findLavoritipi.htm" idHidden="lavoritipi_hidden" idInput="lavoritipi_id" inputTitleKey="label.ricerca_lavoritipi"></init:autocompleter>
						<spring-form:errors path="lavoritipi" cssClass="error"/> 
						<spring-form:hidden id="lavoritipi_hidden" path="lavoritipi.id.codice"  />
					</td>
				</tr>
				</c:if>
				<c:if test="${not empty param.codiceLavoro}">
				<tr>
					<td>
						<fmt:message key="lavoritipicausalioneri.label.lavoritipi" />
					</td>
					<td>
						<input id="lavoritipi_id" name="_lavoritipi.lavoro" class="searchbox"  size="50" value="${lavoritipicausalioneri.lavoritipi.lavoro}" readonly="readonly" />				
						<spring-form:hidden	id="lavoritipi_hidden" path="lavoritipi.id.codice" />													
					</td>
				</tr>
				</c:if>
				
				<tr>
					<td>
						<fmt:message key="lavoritipicausalioneri.label.tipicausalioneri" />
					</td>
					<td>
						<spring-form:input id="tipicausalioneri_id" size="50" path="tipicausalioneri.coDescrizione" cssClass="searchbox" onchange="checkValue(this,'tipicausalioneri_hidden')" onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findTipicausalioneri.htm" idHidden="tipicausalioneri_hidden" idInput="tipicausalioneri_id" inputTitleKey="label.ricerca_tipicausalioneri"></init:autocompleter>
						<spring-form:errors path="tipicausalioneri" cssClass="error"/> 
						<spring-form:hidden id="tipicausalioneri_hidden" path="tipicausalioneri.id.codice"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="lavoritipicausalioneri.label.tipiunitamisura" />
					</td>
					<td>
						<spring-form:input id="tipiunitamisura_id" size="50" path="tipiunitamisura.umDescrbreve" cssClass="searchbox" onchange="checkValue(this,'tipiunitamisura_hidden')"  onkeydown="javascript:return searchAll(this,event)" />
						<init:autocompleter methodAjax="findTipiunitamisura.htm" idHidden="tipiunitamisura_hidden" idInput="tipiunitamisura_id" inputTitleKey="label.ricerca_unitamisura"></init:autocompleter>
						<spring-form:errors path="tipiunitamisura" cssClass="error"/> 
						<spring-form:hidden id="tipiunitamisura_hidden" path="tipiunitamisura.id.codice"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="lavoritipicausalioneri.label.costounitarioum" />
					</td>
					<td>
						<spring-form:input id="costoUnitarioUm_id" path="costoUnitarioUm" size="10" cssStyle="text-align: right;"  onchange="changeValue(this);"/>
						<spring-form:errors path="costoUnitarioUm" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('costounitarioUm_id').focus();
				function changeValue(obj){
					var importo=obj.value;
					if(isNaN(importo.replace(",","."))){
						alert('<fmt:message key="alert.field.numeric" />');
						obj.value = '';
						return;
					}
					obj.value = importo.replace(".",",");
				}
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${lavoritipicausalioneri.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm?codiceLavoro=${param.codiceLavoro}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${lavoritipicausalioneri.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm?codiceLavoro=${param.codiceLavoro}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm?codiceLavoro=${param.codiceLavoro}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceLavoro=${param.codiceLavoro}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>