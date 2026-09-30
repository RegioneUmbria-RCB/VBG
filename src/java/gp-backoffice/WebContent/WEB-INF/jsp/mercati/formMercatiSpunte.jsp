<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
			<fmt:message key="manifestazione.label.mercati_spunte" />
		
	</title>
</head>
<body>
	<span class="titoloPagina"> 
		
			<fmt:message key="manifestazione.label.mercati_spunte" />
		
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
	    <jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercati.descrizione}" />											
		</jsp:include>
	<br class="clear" />
	<div>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="mercatispunte" />
	</jsp:include>
  	</div>
	<spring-form:form commandName="mercatispunte" name="inviodati">
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" />
			</td>
			<td><spring-form:input id="descrizione_id" path="descrizione"
				size="70" /> 
			 <spring-form:errors
				path="descrizione" cssClass="error" /></td>
		</tr>
		<%--
		<tr>
				<td><fmt:message key="manifestazione.label.flag_segna_presenza" /></td>
				
					<td><spring-form:checkbox id="flagSegnaPres_id"
						path="flagSegnaPres" />  <spring-form:errors
						path="flagSegnaPres" cssClass="error" /></td>
				
			</tr>
			 --%>
			 <tr>
				<td><fmt:message key="manifestazione.label.flag_filtro_cat_merceologiche" /></td>
				
					<td><spring-form:checkbox id="flagFiltroCatmerc_id"
						path="flagFiltroCatmerc" />  <spring-form:errors
						path="flagFiltroCatmerc" cssClass="error" /></td>
				
			</tr>
		<tr>
			<td><fmt:message key="label.ordine" />
			</td>
			<td><spring-form:input id="ordine_id" path="ordine"  cssStyle="text-align:right;" size="6" /> 
			
				<spring-form:errors path="ordine" cssClass="error" /></td>
		</tr>
	</table>

	<script type='text/javascript'>
			$('descrizione_id').focus();
	</script>
	</spring-form:form>
	</div>
	<div id="functions">
	<ul>
		<c:if test="${mercatispunte.id.codice==null}">
			<li><a
				href="javascript:doSubmit('insertMercatiSpunte.htm','',document.inviodati)"><fmt:message
				key="button.insert" /></a></li>
		</c:if>
		<c:if test="${mercatispunte.id.codice!=null}">
			<li><a
				href="javascript:doSubmit('updateMercatiSpunte.htm','',document.inviodati)"><fmt:message
				key="button.update" /></a></li>
			<li><a
				href="javascript:doSubmit('deleteMercatiSpunte.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
				key="button.delete" /></a></li>
		</c:if>
		<li><a
			href="javascript:doHref('listmercatispunte.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
			key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>