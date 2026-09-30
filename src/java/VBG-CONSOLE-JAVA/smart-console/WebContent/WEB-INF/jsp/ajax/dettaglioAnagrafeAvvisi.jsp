<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<div>
	<fieldset><legend><fmt:message key="label.lista_notifiche_soggetti" />:<br/>
	<b>${anagrafe.descrizioneRichiedente}</b></legend>
	<c:forEach items="${scadenzes}" var="scadenza_var">
		<c:if test="${scadenza_var.categoria eq 'A'}">
			<c:set var="className" value="avvisoBox" scope="page"/>
			<c:set var="divTitle" scope="page"><fmt:message key="label.avviso" /></c:set>
		</c:if>
		<c:if test="${scadenza_var.categoria ne 'A'}">
			<c:set var="className" value="scadenzaBox" scope="page"/>
			<c:set var="divTitle" scope="page"><fmt:message key="label.scadenza"/></c:set>
		</c:if>
		<div class="${className}" title="${divTitle}">
			<div><fmt:formatDate value="${scadenza_var.dataregistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> - 
			<fmt:formatDate value="${scadenza_var.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></div>
			<div>${scadenza_var.scadenza}</div>
		</div>		
	</c:forEach>
	</fieldset>
</div>