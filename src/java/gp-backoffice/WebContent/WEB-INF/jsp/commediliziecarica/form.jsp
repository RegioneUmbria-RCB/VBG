<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commediliziecarica.id.codice==null}">
			<fmt:message key="commediliziecarica.label.nuovo_commediliziecarica.title" />
		</c:if> 
		<c:if test="${commediliziecarica.id.codice!=null}">
			<fmt:message key="commediliziecarica.label.dettaglio_commediliziecarica.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commediliziecarica.id.codice==null}">
			<fmt:message key="commediliziecarica.label.nuovo_commediliziecarica.title" />
		</c:if> 
		<c:if test="${commediliziecarica.id.codice!=null}">
			<fmt:message key="commediliziecarica.label.dettaglio_commediliziecarica.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">	
		<spring-form:form commandName="commediliziecarica" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commediliziecarica" />
		    </jsp:include>
		    <div class="vbg-form">
				<fieldset>
					<legend>Cariche</legend>
						    
		    		<div class="form-group">
			    		<label><fmt:message key="label.descrizione"/></label>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
			    	</div>			   
		    		<div class="form-group">
			    		<label><fmt:message key="commediliziecarica.label.dirittovoto"/></label>
						<spring-form:checkbox id="dirittovoto_id" path="dirittovoto" value="1" />
						<fmt:message key="commediliziecarica.label.dirittovoto.help" />
						<spring-form:errors path="dirittovoto" cssClass="error"/>
			    	</div>	
		    		<div class="form-group">
			    		<label><fmt:message key="label.ordinamento" /></label>
						<spring-form:input id="ordinamento_id" path="ordinamento" size="5" />
						<spring-form:errors path="ordinamento" cssClass="error"/>
			    	</div>			    	
				</fieldset>
			</div>
		</spring-form:form>	
		  <div class="vbg-form">
			<div class="form-button">		
					<c:if test="${commediliziecarica.id.codice==null}">
						<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
					</c:if>
					<c:if test="${commediliziecarica.id.codice!=null}">
						<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
						<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
					</c:if>
					<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>	
			</div>
		</div>
	</div>
</body>
</html>