<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${alberoprocD2modtatt.id.fkD2mtId==null}">
			<fmt:message key="label.nuovo_modello_attivita" />
		</c:if> 
		<c:if test="${alberoprocD2modtatt.id.fkD2mtId!=null}">			
			<fmt:message key="label.dettaglio_modello_attivita" />
		</c:if>			
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${alberoprocD2modtatt.id.fkD2mtId==null}">
			<fmt:message key="label.nuovo_modello_attivita" />
		</c:if> 
		<c:if test="${alberoprocD2modtatt.id.fkD2mtId!=null}">
			<fmt:message key="label.dettaglio_modello_attivita" />
		</c:if>			
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>

	<div id="subcontent">
	<div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="label.procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${alberoprocD2modtatt.alberoproc.scDescrizione}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
		<spring-form:form commandName="alberoprocD2modtatt" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocDyn2modellit" />
		    </jsp:include>
			<table>
				<c:if test="${!view}">
					<tr>
						<td>
							<fmt:message key="label.modello" />
						</td>
						<td>
							<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="dyn2Modellit_id" />		
								<jsp:param name="propertyPath" value="dyn2Modellit" />				
								<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
								<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
								<jsp:param name="autocompleterAjax" value="findDyn2ModelliAttivitaCurretSoftwareOrTT.htm?codicesoftware=" />	
								<jsp:param name="titleKey" value="label.ricerca_modelli" />
								<jsp:param name="id_help" value="help_modello" />
								<jsp:param name="help" value="help.modelli_archivi_base" />
							</jsp:include>
						</td>
					</tr>
				</c:if>
				<c:if test="${view}">
					<tr>
						<td><fmt:message key="label.modello" /></td>
						<td><spring-form:input id="modello_id" path="dyn2Modellit.descrizione" size="70" disabled="true" />
						<spring-form:errors path="dyn2Modellit" cssClass="error"/></td>
					</tr>
				</c:if>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${!view}">
				<li><a href="javascript:doSubmit('insertModelliAttivita.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${view}">
				<%-- <li><a href="javascript:doSubmit('updateModelliAttivita.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>--%>
			</c:if>
			<c:if test="${alberoprocD2modtatt.id.fkD2mtId!=null}">				
				<li><a href="javascript:doSubmit('deleteModelliAttivita.htm?codicemodellot=${alberoprocD2modtatt.id.fkD2mtId}&codiceprocedimento=${alberoprocD2modtatt.id.fkScId}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listmodelliAttivita.htm?codiceprocedimento=${alberoprocD2modtatt.alberoproc.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>