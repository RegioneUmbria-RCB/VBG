<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<!-- §§§BEGIN§§§ -->
<c:if test="${inite:isEnterprise()}">
<%--
	<jsp:param name="entityPath" value="entity.civico" />
	<jsp:param name="idCampo" value="civico_id" />
	<jsp:param name="size" value="5" />
	<jsp:param name="resettaCivico" value="true" />
	<jsp:param name="isMappali" value="true" />
	<jsp:param name="displaySitFunction" value="${displaySitFunction}" />
	<jsp:param name="indiceMappali" value="_${a.index}" />
 --%>
	<c:set var="idCampo" value="${param.idCampo}"/>
	<c:if test="${empty param.idCampo}">
		[campo_sit.jsp]  Attenzione !! non è stato settato il parametro idCampo.	
	</c:if>
	<c:set var="entityPath" value="${param.entityPath}"/>
	<c:if test="${empty param.entityPath}">
		[campo_sit.jsp]  Attenzione !! non è stato settato il parametro entityPath.	
	</c:if>
	<c:set var="size" value=""/>
	<c:if test="${empty param.size}">
		<c:set var="size" value="${param.size}"/>
	</c:if>
	
	<c:if test="${empty param.isDettaglio}">
		<c:set var="isDettaglio" value="${param.isDettaglio}"/>
	</c:if>	
		
	<c:set var="fnResettaCivico" value=""/>
	<c:set var="fnValidaSit" value=""/>
	<c:if test="${inite:contains(campiGestiti, idCampo)}">		
		<c:if test="${param.resettaCivico eq 'true'}">
			<c:set var="fnResettaCivico" value="resettaCodiceCivicoID();"/>
		</c:if>	
		<!--  Se è solo dettaglio non dovrà essere validato -->
		<c:if test="${param.isDettaglio !='0'}">	
			<c:set var="fnValidaSit">validaSIT($('${idCampo}${param.indiceMappali}'));</c:set>
		</c:if>
	</c:if>	
	<%-- END RECUPERO PARAMETRI--%>
	<%-- BEGIN SEZIONE FUNZIONI --%>
			<spring-form:input id="${idCampo}${param.indiceMappali}" path="${entityPath}" size="${size}" onchange="${fnResettaCivico}${fnValidaSit}" />
			<spring-form:errors path="${entityPath}" cssClass="error" />
			<span id="spinner-${idCampo}${param.indiceMappali}" style="display: none;">
			<img alt="<fmt:message key="label.richiesta_in_corso" />" src="<%=request.getContextPath()%>/images/spinner.gif" /><fmt:message key="label.richiesta_in_corso" /></span>
			<c:if test="${inite:contains(campiGestiti, idCampo)}">
				<c:if test="${param.isMappali eq 'true'}">
					<span id="sitfx${param.indiceMappali}" style="${param.displaySitFunction}">		
				</c:if>
					<jsp:include page="../includes/funzioni_sit.jsp" >
				        <jsp:param name="idCampo" value="${idCampo}${param.indiceMappali}" />
				        <jsp:param name="isDettaglioCampo" value="${param.isDettaglio}" />
				    </jsp:include>
				<c:if test="${param.isMappali eq 'true'}">	    
					</span>
				</c:if>	    
		    </c:if>		
	<%-- END SEZIONE FUNZIONI --%>
</c:if>	
<!-- §§§END§§§ -->
