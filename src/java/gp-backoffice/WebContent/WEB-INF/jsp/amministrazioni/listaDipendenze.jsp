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
				<fmt:message key="label.amministrazione" />:
			</div>
		</div>
		<div class="parametro">
			<div>
				${amministrazioni.amministrazione} (${amministrazioni.id.codice})
			</div>
		</div>
	</div>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../amministrazioni/listaDipendenze" />
		<jsp:param name="qs" value="codice%3D${amministrazioni.id.codice}%26software%3DTT" />	
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="letteretipo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="letteretipo" />
			</jsp:include>
			<br class="clear"/>
			
			
			<%-- model.addAttribute"inventarioprocedimentis",inventarioprocedimentis); --%>
			<c:if test="${not empty inventarioprocedimentis}">
				<fieldset>
					<legend><fmt:message key="label.endoprocedimenti" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.endoprocedimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${inventarioprocedimentis}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../inventarioprocedimenti/view.htm?codice=${el_var.id.codice}&software=${el_var.software.codice}','')">${el_var.procedimento} </a>
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
			
			<%-- model.addAttribute"tipimovstcmappings",tipimovstcmappings); --%>
			<c:if test="${not empty tipimovstcmappings}">
				<fieldset>
					<legend><fmt:message key="form.tipimovstcmapping.title.view" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovstcmappings}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../parametristc/viewMapping.htm?id=${el_var.id.codice}&tipimovimento.idtipomovimento=${el_var.tipimovimento.id.tipomovimento}&software=${el_var.tipimovimento.software.codice}','')">${el_var.tipimovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipimovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
						
			<%-- model.addAttribute"tipimovstcmappingmitts",tipimovstcmappingmitts); --%>			
			<c:if test="${not empty tipimovstcmappingmitts}">
				<fieldset>
					<legend><fmt:message key="form.tipimovstcmapping.title.view" /> - <fmt:message key="label.amministrazione_mittente" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovstcmappingmitts}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../parametristc/viewMapping.htm?id=${el_var.id.codice}&tipimovimento.idtipomovimento=${el_var.tipimovimento.id.tipomovimento}&software=${el_var.tipimovimento.software.codice}','')">${el_var.tipimovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipimovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			<%-- model.addAttribute"tipimovstcalberoprocs",tipimovstcalberoprocs); --%>
			<%-- SE CANCELLATI I MAPPINGS ALLORA SONO STATI SICURAMENTE CANCELLATI ANCHE QUESTI 
			<c:if test="${not empty tipimovstcalberoprocs}">
				<fieldset>
					<legend><fmt:message key="form.tipimovstcalberoproc.title.view" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovstcalberoprocs}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../parametristc/viewAlberoproc.htm?id=${el_var.id.codice}&tipimovimento.idtipomovimento=${el_var.tipimovimento.id.tipomovimento}&software=${el_var.tipimovimento.software.codice}','')">${el_var.tipimovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipimovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			 --%>
			<%-- model.addAttribute"tipimovstcaltridatis",tipimovstcaltridatis); --%>
			<%-- SE CANCELLATI I MAPPINGS ALLORA SONO STATI SICURAMENTE CANCELLATI ANCHE QUESTI
			<c:if test="${not empty tipimovstcaltridatis}">
				<fieldset>
					<legend><fmt:message key="form.tipimovstcaltridati.title.view" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovstcaltridatis}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../parametristc/viewAltridati.htm?id=${el_var.id.codice}&tipimovimento.idtipomovimento=${el_var.tipimovimento.id.tipomovimento}&software=${el_var.tipimovimento.software.codice}','')">${el_var.tipimovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipimovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			 --%>
			<%-- model.addAttribute"tipimovstcmodellis",tipimovstcmodellis); viewModelli--%>
			<%-- SE CANCELLATI I MAPPINGS ALLORA SONO STATI SICURAMENTE CANCELLATI ANCHE QUESTI
			<c:if test="${not empty tipimovstcmodellis}">
				<fieldset>
					<legend><fmt:message key="form.tipimovstcaltridati.title.view" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.movimento" /></td>
						<td><fmt:message key="label.software" /></td>
					</tr>
					<c:forEach items="${tipimovstcmodellis}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../parametristc/viewAltridati.htm?id=${el_var.id.codice}&tipimovimento.idtipomovimento=${el_var.tipimovimento.id.tipomovimento}&software=${el_var.tipimovimento.software.codice}','')">${el_var.tipimovimento.descrizioneEstesa} </a>
							</td>
							<td>
								${el_var.tipimovimento.software.descrizione}
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			 --%>
			
			<%-- model.addAttribute"protocolloregistrimitts",protocolloregistrimitts); --%>
			<c:if test="${not empty protocolloregistrimitts}">
				<fieldset>
					<legend><fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" /> - <fmt:message key="tipologiaregistri.label.amministrazione_mittente" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.codice" /></td>
					</tr>
					<c:forEach items="${protocolloregistrimitts}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipologiaregistri/createProtocolloRegistri.htm?codice=${el_var.id.codice}','')">${el_var.id.codice} </a>
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			
			
			<%-- model.addAttribute"protocolloregistridests",protocolloregistridests); --%>			
			<c:if test="${not empty protocolloregistridests}">
				<fieldset>
					<legend><fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" /> - <fmt:message key="tipologiaregistri.label.amministrazione_destinataria" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.codice" /></td>
					</tr>
					<c:forEach items="${protocolloregistridests}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../tipologiaregistri/createProtocolloRegistri.htm?codice=${el_var.id.codice}','')">${el_var.id.codice} </a>
							</td>
						</tr>
					</c:forEach>
					</table>
				</fieldset>
				<br class="clear"/>
			</c:if>
			<%-- model.addAttribute"commedilizietipologies",commedilizietipologies); commedilizietipologie/view.htm?codice= --%>
			<c:if test="${not empty commedilizietipologies}">
				<fieldset>
					<legend><fmt:message key="commedilizietipologie.label.dettaglio_commedilizietipologie.title" /></legend>
					<table style="width: 100%;">					
					<tr class="titoloSezione">
						<td width="60%"><fmt:message key="label.descrizione" /></td>
					</tr>
					<c:forEach items="${commedilizietipologies}" var="el_var">
						<tr>
							<td>
								<a href="javascript:historySet('${_urlback}', '../commedilizietipologie/view.htm?codice=${el_var.id.codice}','')">${el_var.descrizione} </a>
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
