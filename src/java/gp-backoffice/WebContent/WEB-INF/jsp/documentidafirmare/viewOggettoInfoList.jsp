<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!-- §§§BEGIN§§§ -->
<a class="vbg-btn btn-mettiallafirma${tipoicona}" title="<fmt:message key="documentidafirmare.label.title_icona" />"
	href="javascript: void 0" onclick="window.open('${pageContext.request.contextPath}/documentidafirmare/popupViewDocumentoDaFirmare.htm?codiceMovAllegato=${codiceMovAllegato}',69,'status=1,menubar=0,scrollbars=1,width=1000, height=500, resizable=1')">
	&nbsp;
</a>
<!-- §§§END§§§ -->