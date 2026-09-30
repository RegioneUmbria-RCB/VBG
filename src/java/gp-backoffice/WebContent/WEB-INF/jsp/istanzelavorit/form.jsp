<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.nuovo_lavori_istanza" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.nuovo_lavori_istanza" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzelavorit/list" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanza.id.codice}</c:param>
	</c:import>
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="istanzelavorit" name="inviodati${istanzelavorit.id.codice}">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzelavorit" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td>						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="indirizzo_id" />
							<jsp:param name="propertyPath" value="istanzestradario" />										
							<jsp:param name="pathPropertyDescription" value="istanzestradario.descrizioneEstesaTransient" />
							<jsp:param name="pathPropertyCode" value="istanzestradario.id.codice" />
							<jsp:param name="autocompleterAjax" value="findIstanzestradario.htm?codiceIstanza=${istanzelavorit.istanze.id.codice}" />							
							<jsp:param name="titleKey" value="label.ricerca_stradario" />
							<jsp:param name="autocompleterInputSize" value="85" />
						</jsp:include>
					</td>
					</tr>
					<tr>
						<td><fmt:message key="label.categoria_lavoro" /></td>
						<td>
							<spring-form:textarea id="categorialavoro_id"  path="lavoricategorie.categoria" cssClass="searchbox" onchange="checkValue(this,'categorialavoro_hidden')" onkeydown="return searchAll(this,event)" cols="70" rows="4" />
							<init:autocompleter methodAjax="findLavoricategorie.htm" idHidden="categorialavoro_hidden" idInput="categorialavoro_id" inputTitleKey="label.ricerca_lavoricategorie"/>
							<spring-form:hidden	id="categorialavoro_hidden" path="lavoricategorie.categoria"/>
						</td>
					</tr>
				    <tr>
				   		<td><fmt:message key="label.tipologia_lavoro" /></td>
						<td>
							 <script type="text/javascript">
								function filter(element, entry) {
								//document.getElementById("categorialavoro_id").value	='';
								return entry + "&codiceCategoriaLavoro=" + document.getElementById("categorialavoro_hidden").value;								
								}
							</script>
							<spring-form:textarea id="tipologialavoro_id"  path="lavoritipi.lavoro" cssClass="searchbox" onchange="checkValue(this,'tipologialavoro_hidden')" onkeydown="return searchAll(this,event)" cols="70" rows="4" />
							<init:autocompleter methodAjax="findLavoritipiAndCategoria.htm" idHidden="tipologialavoro_hidden" idInput="tipologialavoro_id" callBack="filter" inputTitleKey="label.ricerca_tipi_lavori"/>
							<spring-form:hidden	id="tipologialavoro_hidden" path="lavoritipi.id.codice"/>
							<spring-form:errors path="lavoritipi" cssClass="error"/> 
						</td>
					</tr>
						
			</table>
			
		</spring-form:form>
	</div>
	<script type="text/javascript">
	$('categorialavoro_id').value='';
	</script>
	<div id="functions">
		<ul>
			<c:if test="${istanzelavorit.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${istanzelavorit.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanzelavorit.istanze.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>