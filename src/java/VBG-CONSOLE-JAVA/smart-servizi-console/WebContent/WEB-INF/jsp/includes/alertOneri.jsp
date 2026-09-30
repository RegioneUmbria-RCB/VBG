<%@page import="it.gruppoinit.upgr.core.FileUtils"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri" %>
<%@ page import="it.gruppoinit.pal.gp.areariservata.web.util.FileUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%--
BEGIN GESTIONE ERRORI ONERI
--%>
<c:if test="${not empty avviso_presenza_oneri_pagati}">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
		<span class="ui-icon ui-icon-alert" style="float: left;margin-top: 2px;margin-right: .3em;"></span> 
		<strong><fmt:message key="alert.avviso-oneri-pagati" /></strong>
		<ul>
		<c:forEach items="${listaOneriPagati}" var="onere">       	
			<li>
				<c:if test="${not empty onere.inventarioprocedimenti}">				
				${onere.inventarioprocedimenti.procedimento} -
				</c:if>				
				${onere.tipicausalioneri.coDescrizione} -
				<fmt:formatNumber minFractionDigits="2">${onere.importo}</fmt:formatNumber> &euro;				
				<c:if test="${not empty onere.oggettoPdf}">
					<%
						FoArjDomandeOneri fado = ((FoArjDomandeOneri)pageContext.getAttribute("onere"));
						String queryString = "id=" + fado.getOggettoPdf().getId().getCodice()+"&ts_="+System.currentTimeMillis();
						String qsDownloadFile = FileUtils.getLinkForFile(queryString);
					%>
					<a href="../ajax/download.htm?<%= qsDownloadFile%>" target="_new"><fmt:message key="label.scarica" /></a>
				</c:if>
			</li>       
		</c:forEach>		
		</ul>
		
		<input type="checkbox" id="procediConCancellazioneOneri_id" name="procediConCancellazioneOneri" />
		<label for="procediConCancellazioneOneri_id"><fmt:message key="label.procedere-con-cancellazione-oneri" /></label>
	</div>
</c:if>
<%--
END GESTIONE ERRORI ONERI
--%>