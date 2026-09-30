<%@page import="it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>

            
    <a style="${param.styleHref}" href="${downloadFileLink}" target="_blank"  
        title="<fmt:message key="label.visualizza_info_oggetto">
                    <fmt:param value="${filename}"/>
                    <fmt:param value="${size}"/>
                </fmt:message>">
                <c:choose>
                    <c:when test = "${tipoFileAttr eq 'WORD'}">
                        <i class="fas fa-file-word fa-lg"></i>
                    </c:when>
                    <c:when test="${tipoFileAttr eq 'PDF' }">
                        <i class="fas fa-file-pdf fa-lg"></i>
                    </c:when>
                    <c:otherwise>
                        <i class="fas fa-file-download fa-lg"></i>
                    </c:otherwise>
                </c:choose>
    </a>
        
        
     <c:if test="${documentoFirmato eq true}">  

        <a style="${param.styleHref}"  title="<fmt:message key="label.verifica_firma" />" 
            href="javascript: void 0" 
            onclick="window.open('${pageContext.request.contextPath}/file/popupViewSignedFileInfo.htm?fileId=${id}',69,'status=1,menubar=0,scrollbars=1,width=1000, height=450, resizable=1')">
            <i class="fas fa-file-signature fa-lg"></i>   
        </a>
        
        
     </c:if>  

    <c:if test="${param.readonly ne 'true'}">			
    	
    	<%
            Boolean isBloccato = false;
    		if(request.getAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + request.getAttribute("id")) != null){
    		 	isBloccato =(Boolean)request.getAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + request.getAttribute("id"));		
    		}
    
            if(!isBloccato){%>
         		
         		<c:choose>
	         		<c:when test="${param.showEditDocs eq 'true'}">	
						<a id="${param.idElemento}_btn_modFile" href="${pageContext.request.contextPath}/file/editDocApplication.htm?fileId=${id}"><i class="fas fa-edit"></i></a>
	                </c:when>
	                <c:otherwise>
		                <a style="${param.styleHref}" title="<fmt:message key="label.firma_digitale" />" href="javascript:void 0" onclick="historySet(relativeURL(document.location, true),'../firmadigitale2/start.htm?codiceoggetto=${id}&nofinestra=true','')">
		                    <i class="fas fa-key fa-lg"></i>
		                </a>
		                <c:if test="${isFirmaRemotaAttiva}">
		                    <a style="${param.styleHref}" title="<fmt:message key="label.firma_digitale_remota" />" href="javascript:void 0" onclick="historySet(relativeURL(document.location, true),'../firmadigitale2/startFirmaRemota.htm?codiceoggetto=${id}','')">
		                        <i class="fas fa-sign-in-alt fa-lg"></i>
		                    </a>
		                </c:if>
	                </c:otherwise>
                </c:choose>
                <%
            }%>            
    </c:if>
     <c:set var="etichetta_dato_sensibile" scope="page"><fmt:message key="label.oggetti_metadati.dato_sensibile.non_attivo" /></c:set>
    <%if (request.getAttribute(OggettiMetadatiService.DATO_SENSIBILE).equals(Boolean.TRUE)){ %>
		<c:set var="etichetta_dato_sensibile" scope="page"><fmt:message key="label.oggetti_metadati.dato_sensibile.attivo" /></c:set>
    <%} %>
    <a style="${param.styleHref}" title="${ etichetta_dato_sensibile }" 
            			href="javascript:void 0" 
            			data-id="${id}" 
            			data-metadato="<%= OggettiMetadatiService.DATO_SENSIBILE%>" 
            			data-valore="<%= request.getAttribute(OggettiMetadatiService.DATO_SENSIBILE)%>" 
            			data-class="dato_sensibile_${id}"
            			data-messaggio-conferma="<fmt:message key="label.oggetti_metadati.dato_sensibile.conferma" />"
            			data-class-valore-true="fa-user-slash"
            			data-class-valore-false="fa-user"
            			data-etichetta-dato-sensibile-attivo="<fmt:message key="label.oggetti_metadati.dato_sensibile.attivo" />"
            			data-etichetta-dato-sensibile-non-attivo="<fmt:message key="label.oggetti_metadati.dato_sensibile.non_attivo" />"
            			onclick="aggiornaMetadatoOggettoBoolean(this)">
            	<%if (request.getAttribute(OggettiMetadatiService.DATO_SENSIBILE).equals(Boolean.TRUE)){ %>
            		<i class="dato_sensibile_${id} fas fa-user-slash fa-lg" ></i>
				<%}else{ %>
            		<i class="dato_sensibile_${id} fas fa-user fa-lg" ></i>
				<%} %>            	
            </a>
    <c:if test="${isStoricizzato}">
    	<a style="${param.styleHref}"  title="<fmt:message key="label.storico_documenti" />" href="javascript:void 0" onclick="tabStoricoOggetto('lista_storico_oggettiDiv','${id}');">
            <%-- <img align="middle" src="${pageContext.request.contextPath}/images/history_folder.gif"/> --%>
            <i class="fas fa-history"></i>
        </a>
    </c:if>



    
    <%-- 
    	Il parametro "mostralabel" è un parametro opzionale. 
        se viene passato con valore true per mette di visualizzare vicino all'icona
        le informazione dell'oggetto:
        1- Nome file
        2- dimensione
    --%>
    <c:if test="${param.mostralabel == true}">	
    	<c:if test="${param.mostraNomeFile == true}">${filename}</c:if> [ ${size}]
    </c:if>