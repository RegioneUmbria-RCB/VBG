<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="documentmerge.title.stampadocumentotipo" />
	</title>
	

	
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="documentmerge.title.stampadocumentotipo" />
	</span>
	<%-- 
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include>
	--%> 
	<div id="subcontent">	

		<spring-form:form commandName="mergeCommand" name="inviodati" action="stampaDocumento.htm">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mergeCommand" />
		    </jsp:include>

			<%-- 
		    <input type="hidden" id="chiave_ricerca" value="istanze_search" />
			<input type="hidden" id="nome_form_ricerca" value="istanzeCommand" />
		    <jsp:include page="../includes/ricerche.jsp" />
		    --%>
	    	<table width="100%" >
				<tr id="elementIdBeforeCombo">
					<td width="15%"></td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td><fmt:message key="documentmerge.label.documentotipo" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="letteratipo_id" />		
							<jsp:param name="propertyPath" value="letteraTipo" />				
							<jsp:param name="pathPropertyDescription" value="letteraTipo.descrizione" />
							<jsp:param name="pathPropertyCode" value="letteraTipo.id.codice" />
							<jsp:param name="autocompleterAjax" value="findLettereTipo.htm" />
							<jsp:param name="titleKey" value="documentmerge.label.documentotipo.alt" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="documentmerge.label.istanza" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="istanza_id" />		
							<jsp:param name="propertyPath" value="istanza" />				
							<jsp:param name="pathPropertyDescription" value="istanza.numeroistanza" />
							<jsp:param name="pathPropertyCode" value="istanza.id.codice" />
							<jsp:param name="autocompleterAjax" value="findIstanze.htm" />
							<jsp:param name="titleKey" value="documentmerge.label.istanza.alt" />
						</jsp:include>
					</td>
				</tr>
			</table>
	    </spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:mergeDocument();"><fmt:message key="documentmerge.button.stampadocumento" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>

	<script type="text/javascript">
		function mergeDocument(){
			document.location.href = 'stampaDocumento.htm?'+ getQueryStringFromForm(document.inviodati);
		}

	</script>
		
</body>
</html>