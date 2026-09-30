<%@page import="it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<fieldset>
<c:if test="${empty filename}">
	<div style="margin-left: 4px;"><b><fmt:message key="form.oggetti.no.file.present" /></b></div>
	<a class="btn btn-primary" href='javascript:void 0' onclick="showUploadControl${param.idElemento}(); return false;"><fmt:message key="form.oggetti.upload" /></a></li>
		
</c:if>
<c:if test="${not empty filename}">
	<div style="margin-left: 4px;" class="vbg-form">
	<b>${filename} 
		<c:if test="${not empty size}">(${size})</c:if> 
	(id=${id})</b>
	<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
	<c:if test="${not empty linkServiziRest }">
		<a href="#" onclick="jQuery('#link_pubblico_${param.idElemento}').toggle()">+</a>
		<p style="display:none;" id="link_pubblico_${param.idElemento}">Link download pubblico <b>${linkServiziRest}</b><br /></p>				 
	</c:if>
	</spring-security:authorize>
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
	<c:if test="${inite:endsWith(filename, '.p7m') || inite:endsWith(filename, '.pdf') || inite:endsWith(filename, '.xml') || inite:endsWith(filename, '.tsd')
               || inite:endsWith(filename, '.m7m')}">
    <span style="display:inline-flex; align-items:center; gap:4px; padding:0 6px; height:18px; background:#f8f9fa; border:1px solid #dcdcdc; border-radius:5px; vertical-align:bottom;">
     <a title="<fmt:message key='label.verifica_firma' />" href="javascript:void 0" onclick="if(document.getElementById('chkVerificaDataId').checked){window.open('${pageContext.request.contextPath}/file/popupViewSignedFileInfo.htm?fileId=${id}',69,'status=1,menubar=0,scrollbars=1,width=800,height=450,resizable=1');}else{window.open('${pageContext.request.contextPath}/file/popupViewSignedFileInfo.htm?fileId=${id}&verificaalladata=false',69,'status=1,menubar=0,scrollbars=1,width=800,height=450,resizable=1');}"> <img style="display:block;" src="${pageContext.request.contextPath}/images/certificato.gif"/></a>
     <input type="checkbox" id="chkVerificaDataId" checked title="Verifica la validità della firma alla data della firma">
    </span>    
	</c:if>
	</div>
	<span id="functions">
		<ul>
			<c:if test="${param.isLibreria}">
				<li><a href='javascript:void 0' onclick="showUploadControl${param.idElemento}(); return false;"><fmt:message key="form.oggetti.upload" /></a></li>
			</c:if>
<%
		Boolean isBloccato = false;
		if(request.getAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + request.getAttribute("id")) != null){
			isBloccato =(Boolean)request.getAttribute(WebConstants.OGGETTI_FILE_BLOCCATO_MODEL_ATTRIBUTE + request.getAttribute("id"));		
		}
		String showFirma = request.getParameter("showFirma");
		
	if( (!isBloccato) || StringUtils.defaultString(showFirma).equalsIgnoreCase("true")){
%>			
			<c:if test="${isFirmaRemotaAttiva}">
					<li>
							<a href="javascript:void 0" onclick="historySet(relativeURL(document.location, true),'../firmadigitale2/startFirmaRemota.htm?codiceoggetto=${id}','')"><fmt:message key="label.firma_digitale_remota" /></a>
					</li>
			</c:if>					
					<li>
						<a href="javascript:void 0" onclick="historySet(relativeURL(document.location, true),'../firmadigitale2/start.htm?codiceoggetto=${id}&nofinestra=true','')"><fmt:message key="label.firma_digitale" /></a>									
					</li>		
<%} %>			
			<li><a target="_blank" href="${downloadFileLink}"><fmt:message key="form.oggetti.view" /></a></li>
<%if(!isBloccato){
%>							
				<c:choose>					
					<c:when test="${isDocumentoSTC eq false}">			
						<li><a id="${param.idElemento}_btn_modFile" href="javascript:void 0" onclick="editDocs${param.idElemento}('${id}');"><fmt:message key="label.modifica" /></a>
							<div id="${param.idElemento}_tooltip" dojoType="dijit.Tooltip" connectId="${param.idElemento}_btn_modFile" position="below" style="display: none;"><fmt:message key="label.applet_modifica_doc.help" /></div>						
						</li>	
					</c:when>					
				</c:choose>	
					<c:if test="${isStoricizzato}">
						<li><a title="<fmt:message key="label.storico_documenti" />" id="${param.idElemento}_btn_storicoFile" href="javascript:void 0" onclick="tabStoricoOggetto${param.idElemento}('lista_storico_oggettiDiv','${id}')"><fmt:message key="label.storico" /></a></li>
					</c:if>
			<c:if test="${!param.isLibreria}">
				<c:choose>					
					<c:when test="${isDocumentoSTC eq false}">
						<li><a href="javascript: void 0" onclick="resetOggetto${param.idElemento}('<fmt:message key="javascript.confirm.delete" />');return false;"><fmt:message key="form.oggetti.delete" /></a></li>
					</c:when>					
				</c:choose>	
			</c:if>
		</ul>
		
<%}else{ 
	//il file è bloccato
%>
			<img onclick="alert('<fmt:message key="label.messaggio_oggetto_bloccato" />')"
					style="cursor: help;"
					title="<fmt:message key="label.messaggio_oggetto_bloccato" />" 
					src="${pageContext.request.contextPath}/images/info.gif" 
					id="img_info_oggetto_${param.idElemento}_${id}_tooltip"/></a>
			
<%} %>	
				
	</span>
</c:if>
</fieldset>