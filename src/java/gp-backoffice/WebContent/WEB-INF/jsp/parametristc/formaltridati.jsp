<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${tipimovStcAltridati.id.codice==null}">
				<fmt:message key="form.tipimovstcaltridati.title.create" />
			</c:if> 
			<c:if test="${tipimovStcAltridati.id.codice!=null}">
				<fmt:message key="form.tipimovstcaltridati.title.view" />
			</c:if>
		</title>
	</head>
	<body>	
		<span class="titoloPagina">
			<c:if test="${tipimovStcAltridati.id.codice==null}">
				<fmt:message key="form.tipimovstcaltridati.title.create" />
			</c:if> 
			<c:if test="${tipimovStcAltridati.id.codice!=null}">
				<fmt:message key="form.tipimovstcaltridati.title.view" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">		
			<div class="vbg-form">
				<spring-form:form commandName="tipimovStcAltridati" name="inviodati">				
					<jsp:include page="../includes/displayGlobalMessages.jsp" >
				        <jsp:param name="commandName" value="tipimovStcAltridati" />
				    </jsp:include>
                    <fieldset>
                        <legend><fmt:message key="label.tipomovimento" /></legend>
                        <div class="etichetta">
                            <div><fmt:message key="label.codice" />:</div>
                            <div><fmt:message key="label.tipomovimento" />:</div>
                        </div>
                        <div class="parametro">
                          <div><c:out value="${tipimovStcMapping.tipimovimento.id.tipomovimento}" /></div>
                          <div><c:out value="${tipimovStcMapping.tipimovimento.movimento }" /></div>
                        </div>
                    </fieldset>
					<fieldset>
				    	<legend><fmt:message key="label.altri_dati" /></legend>
					    <div class="form-group">
							<label><fmt:message key="form.tipimovstcaltridati.amministrazioni" /></label>
							<spring-form:select id="amministrazioni_id" path="amministrazioni.id.codice" items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
							<spring-form:errors path="amministrazioni" cssClass="error"/>
						</div>						
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcaltridati.nomeCampo" /></label>
							<spring-form:input id="nomeCampo_id" path="nomeCampo" size="100"/>
							<init:help idHelp="help_nomeCampo" textKey="form.tipimovstcaltridati.nomeCampo.help"/>
							<spring-form:errors path="nomeCampo" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcaltridati.valoreDefaultCampo" /></label>
							<spring-form:input id="valoreDefaultCampo_id" path="valoreDefaultCampo" size="100"/>
							<init:help idHelp="help_valoreDefaultCampo" textKey="form.tipimovstcaltridati.valoreDefaultCampo.help"/>
							<spring-form:errors path="valoreDefaultCampo" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcaltridati.etichetta" /></label>
							<spring-form:input id="etichetta_id" path="etichetta" size="50" />
							<init:help idHelp="help_etichetta" textKey="form.tipimovstcaltridati.etichetta.help"/>
							<spring-form:errors path="etichetta" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcaltridati.helpText" /></label>
							<spring-form:textarea id="helpText_id" path="helpText" rows="3" cols="70"/>
							<spring-form:errors path="helpText" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcaltridati.ordine" /></label>
							<spring-form:input id="ordine_id" path="ordine" size="2" />
							<spring-form:errors path="ordine" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.obbligatorio" /></label>
							<spring-form:checkbox id="obbligatorio_id" path="obbligatorio"/>
							<spring-form:errors path="obbligatorio" cssClass="error"/>
						</div>					
						<div class="form-group">
							<label><fmt:message key="form.tipimovstcmapping.flaghelp" /></label>
							<spring-form:checkbox id="flagHelp_id" path="flagHelp"/>			
							<spring-form:errors path="flagHelp" cssClass="error"/>
							<fmt:message key="form.tipimovstcmapping.flaghelp.help" />
						</div>
					</fieldset>
				</spring-form:form>
			</div>
		</div>
		<div class="form-button">
			<c:if test="${tipimovStcAltridati.id.codice==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insertAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${tipimovStcAltridati.id.codice!=null}">
				<a class="btn btn-primary" href="javascript:doSubmit('updateAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('deleteAltridati.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm?tipimovimento.idtipomovimento=${idtipomovimento}&viewTab=TAB_ALTRIDATI');"><fmt:message key="button.back" /></a>
		</div>
	</body>
</html>
