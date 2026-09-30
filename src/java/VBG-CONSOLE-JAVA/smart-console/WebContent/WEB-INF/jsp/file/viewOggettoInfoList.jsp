<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
		<a class="visualizzaDoc${tipoFileAttr}Column" href="${downloadFileLink}" target="_blank"  
			title="<fmt:message key="label.visualizza_info_oggetto">
		      			<fmt:param value="${filename}"/>
		      			<fmt:param value="${size}"/>
					</fmt:message>">
		   	<label><fmt:message key="label.visualizza.image" /></label>
		</a>
 <c:if test="${inite:endsWith(filename, '.p7m') || inite:endsWith(filename, '.pdf') || inite:endsWith(filename, '.xml') || inite:endsWith(filename, '.tsd')
               || inite:endsWith(filename, '.m7m')}">  
	<a title="<fmt:message key="label.verifica_firma" />" href="javascript:void 0" 
		onclick="window.open('${pageContext.request.contextPath}/file/popupViewSignedFileInfo.htm?fileId=${id}&idComuneOggetto=${idComuneOggetto}',69,'status=1,menubar=0,scrollbars=1,width=1000, height=450, resizable=1')"><img 
		align="middle" src="${pageContext.request.contextPath}/images/certificato.gif"/></a>
 </c:if>  
 
 
<!-- §§§BEGIN§§§ -->
<c:if test="${param.readonly ne 'true'}">			
	<c:if test="${inite:isEnterprise()}">
	<%
		 Boolean isBloccato = false;
		if(request.getAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + request.getAttribute("id")) != null){
			isBloccato =(Boolean)request.getAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + request.getAttribute("id"));		
		}

	if(!isBloccato){
%>
	<%if(ORMHelper.getIdcomune().equals(request.getAttribute("idComuneOggetto"))){ %>	
			<a title="<fmt:message key="label.firma_digitale" />" href="javascript:void 0" onclick="historySet(escape(document.URL),'../firmadigitale/start.htm?codiceoggetto=${id}&nowindow=true','')"><img align="middle" src="${pageContext.request.contextPath}/images/sign.gif"/></a>
	<%} %>		
<%	}%>
	</c:if>			
		
</c:if>
<!-- §§§END§§§ -->

<%-- 
	Il parametro "mostralabel" è un parametro opzionale. 
    se viene passato con valore true per mette di visualizzare vicino all'icona
    le informazione dell'oggetto:
    1- Nome file
    2- dimensione
--%>
<c:if test="${param.mostralabel == true}">
	<fmt:message key="label.nome_file">
	<fmt:param value="${filename}"/>
	<fmt:param value="${size}"/>
</fmt:message>
</c:if>