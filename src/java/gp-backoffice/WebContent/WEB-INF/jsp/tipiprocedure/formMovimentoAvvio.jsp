<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title>
		<c:if test="${tipiprocedureavvio.id.tipomovimento==null}">
			<fmt:message key="tipiprocedure.label.nuovo_tipiprocedureavvio.title" />
		</c:if>
		<c:if test="${tipiprocedureavvio.id.tipomovimento!=null}">
			<fmt:message key="tipiprocedure.label.dettaglio_tipiprocedureavvio.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
			<c:if test="${tipiprocedureavvio.id.tipomovimento==null}">
			<fmt:message key="tipiprocedure.label.nuovo_tipiprocedureavvio.title" />
		</c:if>
		<c:if test="${tipiprocedureavvio.id.tipomovimento!=null}">
			<fmt:message key="tipiprocedure.label.dettaglio_tipiprocedureavvio.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<span class="parametri"><fmt:message key="tipiprocedure.label.codice_procedura"/><label> ${tipiprocedure.id.codice}</label></span>
	<span class="parametri"><fmt:message key="label.procedura"/><label> ${tipiprocedure.procedura}</label></span>		
		<spring-form:form commandName="tipiprocedureavvio" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipiprocedureavvio" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.tipomovimento" />
					</td>
					<td>
						<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento" value="tipoMovimento" />							
						</jsp:include>	
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipiprocedure.label.flag_movimento_default"/>
					</td>
					<td>
					 <c:if test="${isMovimentoDefaultPresent==true}">
						<spring-form:checkbox id="defaultsn_id" path="defaultsn" disabled="true"/>
					</c:if>
					<c:if test="${isMovimentoDefaultPresent==false}">
						<spring-form:checkbox id="defaultsn_id" path="defaultsn" />
					</c:if>
						<spring-form:errors path="defaultsn" cssClass="error"/>
					</td>
		       </tr>
			</table>
			<script type='text/javascript'>
			
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
		    <c:if test="${tipiprocedureavvio.id.tipomovimento==null}">
				<li><a href="javascript:doSubmit('insertMovimentoavvio.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipiprocedureavvio.id.tipomovimento!=null}">
				<li><a href="javascript:doSubmit('updateMovimentoavvio.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
				<li><a href="javascript:doHref('view.htm?codice=${tipiprocedure.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>