<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><fmt:message key="label.record_disabilitato_lista_delle_dipendenze" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.record_disabilitato_lista_delle_dipendenze" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<div class="parametriDiv">
		<div class="etichetta">
			<div>
				<fmt:message key="label.procedura"/>:
			</div>
		</div>
		<div class="parametro">
			<div>
				${tipiprocedure.procedura} (${tipiprocedure.id.codice})
			</div>
		</div>
	</div>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipiprocedure/listaDipendenze" />
		<jsp:param name="qs" value="codice%3D${tipiprocedure.id.codice}%26software%3D${tipiprocedure.software.codice}" />	
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipiprocedure" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="tipiprocedure" />
			</jsp:include>
			<br class="clear"/>
			<%-- model.addAttribute("alberoprocs", alberoprocs); --%>
			<c:if test="${not empty alberoprocs}">
				<fieldset>
					<legend><fmt:message key="label.alberoproc" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">						
						<td width="90%"><fmt:message key="label.alberoproc" /></td>
						<td width="10%"><fmt:message key="label.software" /></td>						
					</tr>
					<c:forEach items="${alberoprocs}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../alberoproc/view.htm?codice=${el_var.id.codice}&software=${el_var.software.codice}','')">${el_var.vwAlberoproc.scDescrizione} </a>
							</td>
							<td>
								${el_var.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			<%-- model.addAttribute("tipicontromovimentos", tipicontromovimentos); --%>
			<c:if test="${not empty tipicontromovimentos}">
				<fieldset>
					<legend><fmt:message key="tipimovimento.label.contro_movimenti_associati.tilte" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipicontromovimentos}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipimovimento/view.htm?codice=${el_var.tipomovimento.id.tipomovimento}&software=${el_var.tipomovimento.software.codice}','')">${el_var.tipomovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipomovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
						
		</spring-form:form>
	</div>
	<br class="clear"/>
	<div id="functions">
		<ul>
			<li><a
				href="javascript:doHref('view.htm?codice=${param.codice}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>

</body>
</html>
