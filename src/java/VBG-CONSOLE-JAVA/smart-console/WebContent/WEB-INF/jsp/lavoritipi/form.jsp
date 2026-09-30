<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${lavoritipi.id.codice==null}">
			<fmt:message key="lavoritipi.label.nuovo_lavoritipi.title" />
		</c:if> 
		<c:if test="${lavoritipi.id.codice!=null}">
			<fmt:message key="lavoritipi.label.dettaglio_lavoritipi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${lavoritipi.id.codice==null}">
			<fmt:message key="lavoritipi.label.nuovo_lavoritipi.title" />
		</c:if> 
		<c:if test="${lavoritipi.id.codice!=null}">
			<fmt:message key="lavoritipi.label.dettaglio_lavoritipi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../lavoritipi/view" />
	</jsp:include>
	<c:if test="${not empty param.codiceCategoria}">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="lavoritipi.label.lavoricategorie" />:</div>
			</div>
			<div class="parametro">
				<div>${lavoritipi.lavoricategorie.categoria}</div>
			</div>
		</div>
	</c:if>
	<br />	
	<div id="subcontent">
		<spring-form:form commandName="lavoritipi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="lavoritipi" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="lavoritipi.label.lavoro" />
					</td>
					<td>
						<spring-form:textarea id="lavoro_id" path="lavoro" rows="3" cols="70" />
						<spring-form:errors path="lavoro" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${empty param.codiceCategoria}">
				<tr>
					<td>
						<fmt:message key="lavoritipi.label.lavoricategorie" />
					</td>
					<td>
						<spring-form:input id="lavoricategorie_id" size="50" path="lavoricategorie.categoria" cssClass="searchbox" onchange="checkValue(this,'lavoricategorie_hidden')"/>
						<init:autocompleter methodAjax="findlavoricategorie.htm" idHidden="lavoricategorie_hidden" idInput="lavoricategorie_id" inputTitleKey="label.ricerca_lavoricategorie"></init:autocompleter>
						<spring-form:errors path="lavoricategorie" cssClass="error"/> 
						<spring-form:hidden id="lavoricategorie_hidden" path="lavoricategorie.id.codice"  />
					</td>
				</tr>
				</c:if>
				<c:if test="${not empty param.codiceCategoria}">
				<tr>
					<td>
						<fmt:message key="lavoritipi.label.lavoricategorie" />
					</td>
					<td>
						<input id="lavoricategorie_id" name="_lavoricategorie.categoria" size="50" class="searchbox" value="${lavoritipi.lavoricategorie.categoria}" readonly="readonly" />				
						<spring-form:hidden	id="lavoricategorie_hidden" path="lavoricategorie.id.codice" />									
					</td>
				</tr>
				</c:if>
			</table>
			<script type='text/javascript'>
				$('lavoro_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${lavoritipi.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm?codiceCategoria=${param.codiceCategoria}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${lavoritipi.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm?codiceCategoria=${param.codiceCategoria}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:historySet('${_urlback}','../lavoritipicausalioneri/list.htm?codiceLavoro=${lavoritipi.id.codice}','');"><fmt:message key="lavoritipi.button.lavoritipicausalioneri" /></a></li>				
				<li><a href="javascript:doSubmit('delete.htm?codiceCategoria=${param.codiceCategoria}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceCategoria=${param.codiceCategoria}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>