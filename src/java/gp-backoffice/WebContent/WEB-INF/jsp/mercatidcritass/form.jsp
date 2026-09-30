<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatidcritass.id.codice==null}">
			<fmt:message key="label.nuovo_mercatidcritass.title" />
		</c:if> 
		<c:if test="${mercatidcritass.id.codice!=null}">
			<fmt:message key="label.dettaglio_mercatidcritass.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${mercatidcritass.id.codice==null}">
			<fmt:message key="label.nuovo_mercatidcritass.title" />
		</c:if> 
		<c:if test="${mercatidcritass.id.codice!=null}">
			<fmt:message key="label.dettaglio_mercatidcritass.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mercatidcritass" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatidcritass" />
		    </jsp:include>
			<table>
				<c:if test="${mercatidcritass.id.codice==null}">
				    <tr>
					<td>
						<fmt:message key="label.campo" />
					</td>
					<td>
					<script type="text/javascript">
						function selectCampoDinamico(inputField,listItem){
							var a = listItem.id;
							document.getElementById('dyn2Campi_hidden').value = a;
							doSubmit('scegliCampoDinamico.htm','',document.inviodati);				
						}
					</script>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="dyn2Campi" />		
						<jsp:param name="propertyPath" value="dyn2Campi" />				
						<jsp:param name="pathPropertyDescription" value="dyn2Campi.nomecampo" />
						<jsp:param name="pathPropertyCode" value="dyn2Campi.id.codice" />
						<jsp:param name="autocompleterAjax" value="findDyn2CampiByMercato.htm?codiceMercato=${codiceMercato}" />
						<jsp:param name="afterUpdateElement" value="selectCampoDinamico" />
						<jsp:param name="id_help" value="help_campo" />	
					</jsp:include>
					</td>
					</tr>
				</c:if>
				<c:if test="${mercatidcritass.id.codice!=null}">
					<tr>
						<td>
							<fmt:message key="label.campo" />
						</td>
						<td>
						<spring-form:input  path="dyn2Campi.nomecampo" size="70" />
						</td>
					</tr>
				</c:if>
				<%-- 
				</tr>
					<td>
						<fmt:message key="label.valore" />
					</td>
					<td>
						<spring-form:input id="valore_id" path="valore" size="70" />
						<spring-form:errors path="valore" cssClass="error"/>
					</td>
				</tr>
				--%>
				<c:if test="${ mercatidcritass.dyn2Campi.id.codice!=null}">
				<tr>
					<td>
						<fmt:message key="label.consentito" />
					</td>
					<td>
						<spring-form:checkbox id="consentito_id" path="flagConsentito"/>
						<spring-form:errors path="flagConsentito" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<tr>
					${campoHtml}
				</tr>
			</table>
			<script type='text/javascript'>
	
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
		    <%--
			<c:if test="${mercatidcritass.id.codice==null}">
				<li><a href="javascript:doSubmit('scegliCampoDinamico.htm','',document.inviodati)"><fmt:message key="button.select" /></a></li>
			</c:if>
			--%>
			<c:if test="${mercatidcritass.id.codice==null && mercatidcritass.dyn2Campi.id.codice!=null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercatidcritass.id.codice!=null && mercatidcritass.dyn2Campi.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codicePosteggio=${mercatidcritass.mercatiD.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>