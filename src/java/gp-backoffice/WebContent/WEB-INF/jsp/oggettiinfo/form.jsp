<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${oggettiinfo.id.codice==null}">
			<fmt:message key="oggettiinfo.label.nuovo_oggettiinfo.title" />
		</c:if> 
		<c:if test="${oggettiinfo.id.codice!=null}">
			<fmt:message key="oggettiinfo.label.dettaglio_oggettiinfo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${oggettiinfo.id.codice==null}">
			<fmt:message key="oggettiinfo.label.nuovo_oggettiinfo.title" />
		</c:if> 
		<c:if test="${oggettiinfo.id.codice!=null}">
			<fmt:message key="oggettiinfo.label.dettaglio_oggettiinfo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="oggettiinfo" name="inviodati" enctype="multipart/form-data">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="oggettiinfo" />
		    </jsp:include>
			<table width="100%">	
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error" />
					</td>
				</tr>
				<tr>
				<td><fmt:message key="label.file" /></td>
				<td>				
						<jsp:include page="oggettiinfoupload.jsp">
							<jsp:param name="isLibreria" value="true" />
			       			<jsp:param name="idElemento" value="oggettoIdCodice" />
			   				<jsp:param name="codiceOggetto" value="${oggettiinfo.oggetto.id.codice}" />
			   				<jsp:param name="codiceOggettoId" value="codiceoggettoinfo_id_codice" />
			   			</jsp:include>
	    				<spring-form:hidden path="oggetto.id.codice" id="codiceoggettoinfo_id_codice" />
	    				<spring-form:errors path="oggetto.id.codice" cssClass="error" />
	    		</td>
				</tr>
				<tr>
					<td><fmt:message key="oggettiinfo.label.tipologieoggetto" /></td>
					<td>
					<spring-form:select id="tipologieoggetto_id" path="tipologieoggetto.id.codice"  >
						<spring-form:option value="" ></spring-form:option>
						<spring-form:options items="${tipologieoggettoList}" itemLabel="descrizionetipologia" itemValue="id.codice"/>
					</spring-form:select>
					<spring-form:errors path="tipologieoggetto" cssClass="error" />
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('descrizione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${oggettiinfo.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${oggettiinfo.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>