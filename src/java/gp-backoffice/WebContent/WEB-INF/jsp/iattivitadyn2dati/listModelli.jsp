<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
	<c:if test="${not empty param.codiceIstanza}">
		<fmt:message key="label.schede_dell_istanza" />	
	</c:if>
	<c:if test="${empty param.codiceIstanza}">
		<fmt:message key="label.anteprima_modello" />
	</c:if>
	</title>
</head>
<body>

	<ul class="listaSchede">
		<c:forEach items="${listaModelliAttivati}" var="modellot">
			<c:set var="cssClass" value="Scheda"/>
			<c:if test="${modellot.id.fkD2mtId eq codiceModello}">
				<c:set var="cssClass" value="SchedaAttiva"/>
			</c:if>
			<li><a class="${cssClass}" href="javascript:showModelliDinamiciAttivita(${modellot.id.fkIaId},${modellot.id.fkD2mtId});">${modellot.dyn2Modellit.descrizione}</a></li>
		</c:forEach>
		<br />
		${html}
		
		<%-- <li><a class="Scheda" href="javascript:scegliNuovaScheda();"><fmt:message key="label.nuova_scheda" /></a></li>  
		
		<span id="anagrafe_dettaglio${assenze_var.id.codiceanagrafe}_${assenze_var.id.idposteggio}_${assenze_var.id.codicemercato}_${assenze_var.id.codiceuso}" style="display: none; text-align: left;"></span>
		--%>
   </ul>
   
   
   
   


</body>

</html>