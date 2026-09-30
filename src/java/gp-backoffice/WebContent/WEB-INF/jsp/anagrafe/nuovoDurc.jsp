<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.richiesta_nuovo_durc" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.richiesta_nuovo_durc" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
<div class="parametriDiv">
<div class="etichetta">
<div><fmt:message key="label.soggetto" />:</div>
</div>
<div class="parametro">
<div><c:out value="${anagrafe.descrizioneRichiedente}" /></div>
</div>
</div>
<br class="clear" />	
		<spring-form:form commandName="nuovoDurcHelper" name="inviodati">
			<input type="hidden" value="${anagrafe.id.codice}" name="codiceAnagrafe" />
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="nuovoDurcHelper" />
		    </jsp:include>
		    
		    <c:if test="${not empty nuovoDurcHelper.messaggioValidazione}">		    	
		    	<div class="error_header" style="width: 800px; min-height: 50px; border: thin dotted; padding: 5px;">
						<div style="margin-left: 16px;">${nuovoDurcHelper.messaggioValidazione}</div>
				</div>		    	
		    </c:if>
		    
			<table>
				<c:forEach items="${nuovoDurcHelper.durc.company.companyAddress}" var="address" varStatus="idx">
					
					<tr class="titoloSezione">
						<td colspan="6">
						<c:choose>
						<c:when test="${address.type eq 'LEGAL'}">
							<fmt:message key="label.dati_sede_legale" />
						</c:when>
						<c:otherwise><fmt:message key="label.dati_sede_operativa" /></c:otherwise>		
						</c:choose>					
						</td>
					</tr>					
					<tr>
						<td>
							<fmt:message key="label.toponimo" />
						</td>
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].particle">
								<input type="text" name="${status.expression}" value="${status.value}" id="toponimo_${idx.index }" size="10" />							
							</spring:bind>
							
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].particle" cssClass="validation_error" />
							
						</td>						
						<td>
							<fmt:message key="label.indirizzo" />
						</td>
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].description">
								<input type="text" name="${status.expression}" value="${status.value}" id="description_${idx.index }" size="50" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].description" cssClass="validation_error" />
						</td>
						<td>
							<fmt:message key="label.civico" />
						</td>
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].number">
								<input type="text" name="${status.expression}" value="${status.value}" id="civico_${idx.index }" size="10" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].number" cssClass="validation_error" />
						</td>		
					</tr>
					<tr>
						<td>
							<fmt:message key="label.comune" />
						</td>
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].city">
								<input type="text" name="${status.expression}" value="${status.value}" id="comune_${idx.index }" size="10" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].city" cssClass="validation_error" />
						</td>
						<td>
							<fmt:message key="label.codice_istat" />
						</td>
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].cityID">
								<input type="text" name="${status.expression}" value="${status.value}" id="istat_comune_${idx.index }" size="50" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].cityID" cssClass="validation_error" />
						</td>
						<td>
							<fmt:message key="label.codice_catastale_comune" />
						</td>
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].cityFiscalID">
								<input type="text" name="${status.expression}" value="${status.value}" id="cf_comune_${idx.index }" size="10" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].cityFiscalID" cssClass="validation_error" />
						</td>						
					</tr>
					
					<tr>
						<td>
							<fmt:message key="label.cap" />
						</td>						
						<td>
							<spring:bind path="durc.company.companyAddress[${idx.index }].CAP">
								<input type="text" name="${status.expression}" value="${status.value}" id="cap_${idx.index }" size="10" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].CAP" cssClass="validation_error" />
						</td>						
						<td>						
							<fmt:message key="label.provincia" />
						</td>
						<td colspan="3">
							<spring:bind path="durc.company.companyAddress[${idx.index }].province">
								<input type="text" name="${status.expression}" value="${status.value}" id="provincia_${idx.index }" size="50" />
							</spring:bind>
							<spring-form:errors path="durc.company.companyAddress[${idx.index }].province" cssClass="validation_error" />
						</td>
											
					</tr>			
				</c:forEach>
					
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="label.dati_contatti" />						
						</td>
					</tr>
					<tr>
					    <td><fmt:message key="label.email" /></td>
						<td colspan="5"><spring-form:input path="durc.company.companyEmail" size="50"/></td>
					</tr>
					<tr>
					    <td><fmt:message key="label.pec" /></td>
						<td colspan="5"><spring-form:input path="durc.company.companyPecAddress" size="50"/></td>
					</tr>
					
					<!--  SEZIONE ISTITUTI PREVIDENZIALI -->
					<%-- 	
					<tr class="titoloSezione">
						<td colspan="6">
							<fmt:message key="label.dati_enti_previdenziali" />
						</td>
					</tr>
					<c:forEach items="${nuovoDurcHelper.durc.institute}" var="institute" varStatus="idx1">					
					<tr>
						<td>
							<fmt:message key="label.matricola" /> ${institute.instituteName}
						</td>
						<td>
							<spring:bind path="durc.institute[${idx1.index}].companyCode">
								<input type="text" name="${status.expression}" value="${status.value}" id="companyCode_${idx1.index}" size="10" />							
							</spring:bind>
							<spring-form:errors path="durc.institute[${idx1.index}].companyCode" cssClass="validation_error" />
							
						</td>
					
						<td>
							<fmt:message key="label.sede" /> ${institute.instituteName}
						</td>
						<td colspan="2">
							<spring:bind path="durc.institute[${idx1.index}].instituteOffice.officeCode">
								<input type="text" name="${status.expression}" value="${status.value}" id="officeCode_${idx1.index}" size="10" />							
							</spring:bind>
							<spring-form:errors path="durc.institute[${idx1.index}].instituteOffice.officeCode" cssClass="validation_error" />
						</td>
					</tr>
					</c:forEach>
					--%>			
				</table>
		</spring-form:form>
	</div>
	
	<div id="functions">
		<ul>			
			<li><a href="javascript:doSubmit('insertNuovoDurc.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>			
			<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>