<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="codiceMovimento" value="${param.codiceMovimento }"/>
<c:set var="flagCamcom" value="${param.flagCamcom}"/>
<c:set var="inviatoACamcom" value="${param.inviatoACamcom}"/>
<%-- 
		parametri obbligatori:
			codiceMovimento
			flagCamcom 'true' o 'false' preso dal tipomovimento
			inviatoACamcom  'true' o 'false' preso dal movimento
 --%>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE FUNZIONI --%>
<c:if test="${flagCamcom eq true}">
	<c:if test="${inviatoACamcom eq true}">
		<img align="middle" src="${pageContext.request.contextPath}/images/retestc_out.gif" title='<fmt:message key="label.dati_inviati_a_camera_commercio" />'/>
	</c:if>				
	<c:if test="${inviatoACamcom ne true}">
		<img  align="middle" src="${pageContext.request.contextPath}/images/warning.gif" title='<fmt:message key="label.dati_da_inviare_a_camera_commercio"/>' />
	</c:if>				
</c:if>
<%-- END SEZIONE FUNZIONI --%>
