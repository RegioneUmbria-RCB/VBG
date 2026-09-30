<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.dettaglio_iattivitaSnapshot.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.dettaglio_iattivitaSnapshot.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		
		<div class="etichetta">
			<div><fmt:message key="label.denominazione" />:</div>
			<div><fmt:message key="label.ultima_istanza" />:</div>		
			<div><fmt:message key="label.attiva" />:</div>
			<div><fmt:message key="label.operante" />:</div>
			<div><fmt:message key="label.data" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${iAttivitaSnapshot.denominazione}</div>
			<div>${iAttivitaSnapshot.istanza.numeroistanza}</div>
			<div>
			<c:if test="${iAttivitaSnapshot.attiva}">
				<fmt:message key="label.si"></fmt:message>
			</c:if>
			<c:if test="${!iAttivitaSnapshot.attiva}">
				<fmt:message key="label.no"></fmt:message>
			</c:if>
			</div>
			<div>
			<c:if test="${iAttivitaSnapshot.operante}">
				<fmt:message key="label.si"></fmt:message>
			</c:if>
			<c:if test="${!iAttivitaSnapshot.operante}">
				<fmt:message key="label.no"></fmt:message>
			</c:if>	
			</div>
			<div><fmt:formatDate value="${iAttivitaSnapshot.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /> </div>
		</div>
		</div>
	</div>
	<br />
	<div class="titoloSezione">
		<fmt:message key="iattivita.label.dettaglio_schede" ></fmt:message>
	</div>
	
	
	<c:forEach items="${listaModTSnapshotHelpers}" var="modelliTSnapshotHelper_var" varStatus="indexModelliT" >
	
	<fieldset>
	
	<!-- Gestione del fieldset pricipale che descrive la sezione -->
	<legend>
	
	<% 
		String displaySchedeAttivita = "display: none;";
		String styleSchedeAttivita = "sezioneDatiPiu";
		//if(request.getParameter("amministrazioniVisibili")!=null && request.getParameter("amministrazioniVisibili").equals("visibile"))
        //{
        //    displayAmministrazioniinvitate="";
        //    styleAmministrazioniinvitate = "sezioneDatiMeno";
        //}
	%>
	
	<a
		class="<%=styleSchedeAttivita%>"
		id="id_link_schede_attivita${indexModelliT.index}"
		href="javascript:showHidePanelBase('id_schede_attivita_table${indexModelliT.index}','id_link_schede_attivita${indexModelliT.index}','','${pageContext.request.contextPath}/images/','div',false);"
		title="<fmt:message key="label.mostra_nasconde_sezione" /> ${modelliTSnapshotHelper_var.attivitadyn2modTSnapshot.dyn2Modellit.descrizione}">
		<label for="id_link_schede_attivita${indexModelliT.index}"><b>${modelliTSnapshotHelper_var.attivitadyn2modTSnapshot.dyn2Modellit.descrizione}</b></label> 
	</a>
	</legend>					
	</legend>
	<div style="<%=displaySchedeAttivita%>" id="id_schede_attivita_table${indexModelliT.index}" >
	
		<div class="jmesa">
			<table border="1" width="100%" cellpadding="0" cellspacing="0" class="table" >
				<thead>
					<tr class="header">
					    <td width="10%"><fmt:message key="label.campo"/></td>
						<td width="20%"><fmt:message key="label.valore"/></td>
						<td><fmt:message key="label.valore_decodificato"/></td>
					</tr>
				</thead>
				<tbody class="tbody">
				    <c:forEach items="${modelliTSnapshotHelper_var.attivitadyn2datiSnapshots}" var="datiSnapshot_var" varStatus="datiSnapshot_indice">
						<c:set var="trStyle" value="odd"/>							
						<tr class="${trStyle}" >
							<td>${datiSnapshot_var.dyn2Campi.nomecampo}</td>
							<td>${datiSnapshot_var.valore}</td>
							<td>${datiSnapshot_var.valoredecodificato}</td>
						</tr>
					</c:forEach>
				 </tbody>
			</table>	
       	</div>
	</div>
	</fieldset>	
	
	
	
	
	</c:forEach>	
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
</body>
</html>