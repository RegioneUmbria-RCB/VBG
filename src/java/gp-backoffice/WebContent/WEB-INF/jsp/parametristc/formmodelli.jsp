<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${tipimovStcModelli.id.codice==null}">
				<fmt:message key="form.tipimovstcmodelli.title.create" />
			</c:if> 
			<c:if test="${tipimovStcModelli.id.codice!=null}">
				<fmt:message key="form.tipimovstcmodelli.title.view" />
			</c:if>
		</title>
	</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${tipimovStcModelli.id.codice==null}">
				<fmt:message key="form.tipimovstcmodelli.title.create" />
			</c:if> 
			<c:if test="${tipimovStcModelli.id.codice!=null}">
				<fmt:message key="form.tipimovstcmodelli.title.view" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
				<spring-form:form commandName="tipimovStcModelli" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="tipimovStcModelli" />
			    </jsp:include>
                <fieldset>
                    <legend><fmt:message key="label.tipomovimento" /></legend>
                    <div class="etichetta">
                        <div><fmt:message key="label.codice" />:</div>
                        <div><fmt:message key="label.tipomovimento" />:</div>
                    </div>
                    <div class="parametro">
                      <div><c:out value="${tipimovStcModelli.tipimovimento.id.tipomovimento}" /></div>
                      <div><c:out value="${tipimovStcModelli.tipimovimento.movimento }" /></div>
                    </div>
                </fieldset>
			    <fieldset>
			    	<legend><fmt:message key="label.modelli" /></legend>		    
				    <div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.amministrazioni" /></label>
							<spring-form:select id="amministrazioni_id" path="amministrazioni.id.codice" >
								<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
							</spring-form:select>
							<spring-form:errors path="amministrazioni" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmodelli.modello" /></label>
							<spring-form:input id="dyn2Modellit_id" path="dyn2Modellit.descrizione" size="55" cssClass="searchbox" onchange="checkValue(this,'dyn2Modellit_hidden')" onkeydown="javascript:return searchAll(this,event)" />
							<init:autocompleter methodAjax="findAllDyn2Modelli.htm" idHidden="dyn2Modellit_hidden" idInput="dyn2Modellit_id" inputTitleKey="label.ricerca_modello"/>
							<spring-form:hidden id="dyn2Modellit_hidden" path="dyn2Modellit.id.codice"/>
							<spring-form:errors path="dyn2Modellit" cssClass="error"/>
						</div>
					</fieldset>
				</spring-form:form>
			</div>
		</div>
		
		<div class="form-button">
			<c:if test="${tipimovStcModelli.id.codice==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insertModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${tipimovStcModelli.id.codice!=null}">
				<a class="btn btn-primary" href="javascript:doSubmit('updateModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('deleteModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm?tipimovimento.idtipomovimento=${idtipomovimento}&viewTab=TAB_MODELLI');"><fmt:message key="button.back" /></a>
		</div>
	</body>
</html>
