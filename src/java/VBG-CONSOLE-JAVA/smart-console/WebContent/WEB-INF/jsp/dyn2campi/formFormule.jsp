<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService.EventoModelli"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.dettaglio_formula" />
	</title>
</head>
<body>

	<script src="${pageContext.request.contextPath}/scripts/codemirror/lib/codemirror-compressed.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/codemirror/lib/codemirror.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/scripts/codemirror/theme/night.css">
	<script type="text/javascript">
	var g_codeMIrror = null;
	jQuery(document).ready(
	function () {
		g_codeMIrror = CodeMirror.fromTextArea(document.getElementById('scriptString_id'),{
								mode: "javascript",
								lineNumbers: true,
								theme: 'night',
								tabMode: 'shift',
								indentUnit: 4,
								enterMode: 'keep',
								onCursorActivity: function() {
									g_codeMIrror.setLineClass(hlLine, null);
									hlLine = g_codeMIrror.setLineClass(g_codeMIrror.getCursor().line, "activeline");
								}
						});
		var hlLine = g_codeMIrror.setLineClass(0, "activeline");
	}
	);

	function saveForm(){
		g_codeMIrror.toTextArea();
		doSubmit('saveFormula.htm','',document.inviodati);
	}
	</script>

	<span class="titoloPagina">
		<fmt:message key="label.dettaglio_formula" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2modellit/view" />
	</jsp:include>	
		<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.dettaglio_dyn2campi.title" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${dyn2campi.nomecampo}			
				</div>
			</div>
		</div>
		<br class="clear" />
		<spring-form:form commandName="dyn2modellit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="dyn2modellit" />
		    </jsp:include>
		    <input type="hidden" name="codice" value="${codice}"/>
	   
		    <c:set var="eventoCaricamento" value="<%= EventoModelli.Caricamento.name() %>"></c:set>
		    
		   	<c:set var="eventoModifica" value="<%= EventoModelli.Modifica.name() %>"></c:set>
		   	
		    <c:set var="eventoSalvataggio" value="<%= EventoModelli.Salvataggio.name() %>"></c:set>
	
			<table width="100%">
				<tr>
					<td width="20%"><fmt:message key="label.eventi_formule" /></td>
					<td>
						<select id="eventoId" name="evento" onchange="formuleView();">
							<option <c:if test="${evento eq eventoCaricamento}"> selected </c:if> value="<%=EventoModelli.Caricamento.name() %>"><%=EventoModelli.Caricamento.name() %></option>
						 	<option <c:if test="${evento eq eventoModifica}"> selected </c:if> value="<%=EventoModelli.Modifica %>"><%=EventoModelli.Modifica%></option>
							<option <c:if test="${evento eq eventoSalvataggio}"> selected </c:if> value="<%=EventoModelli.Salvataggio %>"><%=EventoModelli.Salvataggio %></option>
						<select>						
					</td>
				</tr>
				<tr>
					<td colspan="2">						
						<textarea id="scriptString_id" name="scriptString" cols="200" rows="20">${scriptString}</textarea>
					</td>
				</tr>
				
			</table>
			<script type="text/javascript">
				function formuleView(){
					doSubmit('viewFormule.htm','');					
				}		
			</script>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:saveForm();"><fmt:message key="button.insert" /></a></li>	
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>