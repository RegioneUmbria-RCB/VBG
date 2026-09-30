<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!-- §§§BEGIN§§§ -->
<a title="<fmt:message key="documentidafirmare.label.title_icona" />"
	href="javascript:void 0"
	onclick="window.open('${pageContext.request.contextPath}/documentidafirmare/popupViewDocumentoDaFirmare.htm?codiceMovAllegato=${codiceMovAllegato}',69,'status=1,menubar=0,scrollbars=1,width=1000, height=500, resizable=1')"><img
	src="${pageContext.request.contextPath}/images/metti_alla_firma${tipoicona }.gif" /></a>
<!-- §§§END§§§ -->