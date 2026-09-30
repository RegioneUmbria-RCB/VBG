<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_SIT_ATTIVO)%></c:set>
<c:if test="${VERTICALIZZAZIONE_SIT_ATTIVO_IN_REQUEST eq true}">
<!-- §§§BEGIN§§§ -->

	<c:set var="funzioneRichiesta" value="validaSIT"/>
	<c:set var="idCampo" value="${param.idCampo}"/>
	<%-- 
	funzioni possibili:
		validaSIT: 
			effettua la ricerca dei valori
	 --%>
	<c:if test="${not empty param.funzioneRichiesta}">
		<c:set var="funzioneRichiesta" value="${param.funzioneRichiesta}"/>
	</c:if>
	
	<%-- END RECUPERO PARAMETRI--%>
	<%-- BEGIN SEZIONE FUNZIONI --%>
	<c:choose>
	<c:when test="${param.isDettaglioCampo == 'false' || param.isDettaglioCampo == ''}">
		<span id="spinner-${idCampo}" style="display: none;"></span>
		<a style="float: none;" href="javascript:validaSIT($('${idCampo}'), true)" title="<fmt:message key="label.ricerca_dati_sit" />">	
			<i class="fa fa-globe-europe fa-lg"></i>
		</a>
	</c:when>
	<c:otherwise>
		<c:if test="${param.isDettaglioCampo == '1'}">
			<span id="spinner-${idCampo}" style="display: none;"></span>
			<a class="gisLink" style="float: none;" href="javascript:validaSIT($('${idCampo}'), true)" title="<fmt:message key="label.ricerca_dati_sit" />">	
		    <label><fmt:message key="label.gis.image" /></label></a>
		    <a class="dettaglioGisLink" style="float: none;" href="javascript:dettaglioSIT($('${idCampo}'), true)" title="<fmt:message key="label.dettaglio_dati_sit" />">	
			<label><fmt:message key="label.dettaglio_gis.image" /></label></a>
		</c:if>
		<c:if test="${param.isDettaglioCampo == '0'}">
		    <span id="spinner-${idCampo}" style="display: none;"></span>
			<a class="dettaglioGisLink" style="float: none;" href="javascript:dettaglioSIT($('${idCampo}'), true)" title="<fmt:message key="label.dettaglio_dati_sit" />">	
			<label><fmt:message key="label.dettaglio_gis.image" /></label></a>
		</c:if>
	
	</c:otherwise>
	</c:choose>
	
	<%-- 
	<c:choose>
	<c:when test="${funzioneRichiesta eq 'validaSIT'}">
		<a class="gisLink" style="float: none;" href="javascript:validaSIT($('${idCampo}'), true)" title="<fmt:message key="label.ricerca_dati_sit" />">	
		<label><fmt:message key="label.gis.image" /></label></a>
	</c:when>
	</c:choose>
	<c:if test="${param.isDettaglioCampo == 'true'}">
	<a class="dettaglioGisLink" style="float: none;" href="javascript:dettaglioSIT($('${idCampo}'), true)" title="<fmt:message key="label.dettaglio_dati_sit" />">	
		<label><fmt:message key="label.dettaglio_gis.image" /></label></a>
	</c:if>
	--%>
	<%-- END SEZIONE FUNZIONI --%>

<!-- §§§END§§§ -->
</c:if>