<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${foarjservizi.id.codice==null}">
			<fmt:message key="foarjservizi.label.nuovo_foarjservizi.title" />
		</c:if> 
		<c:if test="${foarjservizi.id.codice!=null}">
			<fmt:message key="foarjservizi.label.dettaglio_foarjservizi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${foarjservizi.id.codice==null}">
			<fmt:message key="foarjservizi.label.nuovo_foarjservizi.title" />
		</c:if> 
		<c:if test="${foarjservizi.id.codice!=null}">
			<fmt:message key="foarjservizi.label.dettaglio_foarjservizi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
       		<div class="parametro"> 
	        	<div>
	        		${foarjservizi.alberoproc.vwAlberoproc.scDescrizione}
	        	</div>
	        </div>
	    </div>
	    <div class="clear"></div>
		<spring-form:form commandName="foarjservizi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="foarjservizi" />
		    </jsp:include>
			<table>
			<c:choose>
				<c:when test="${centroServiziUrlBrevi eq true }">
					<tr>
						<td>
							<fmt:message key="foarjservizi.label.urlServizio" />
						</td>
						<td>
							<spring-form:input id="urlServizio_id" path="urlServizio" size="70" />
							<spring-form:errors path="urlServizio" cssClass="error"/>
						</td>
					</tr>
				</c:when>	
				<c:otherwise>
					<spring-form:hidden id="urlServizio_id" path="urlServizio" />
					<spring-form:errors path="urlServizio" cssClass="error"/>
				</c:otherwise>
			</c:choose>
				<tr>
					<td>
						<fmt:message key="foarjservizi.label.nlaservizi" />
					</td>
					<td>
						<spring-form:select path="nlaServizi.id.codice">
							<spring-form:options items="${nlaserviziList}" itemLabel="descrizione" itemValue="id.codice"/>
						</spring-form:select>
						<spring-form:errors path="nlaServizi.id.codice" cssClass="error"/> 	
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="foarjservizi.label.anonimo" />
					</td>
					<td>
						<spring-form:checkbox path="anonimo" />
						<spring-form:errors path="anonimo" cssClass="error"/> 	
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('urlServizio_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${foarjservizi.id.codice == null || errore_inserimento eq true}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${empty errore_inserimento}">
				<c:if test="${foarjservizi.id.codice != null}">
					<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				</c:if>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceprocedimento=${foarjservizi.alberoproc.id.codice }','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>