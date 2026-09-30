<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<fieldset>
<c:if test="${empty filename}">
	<div style="margin-left: 4px;"><b><fmt:message key="form.oggetti.no.file.present" /></b></div>
	<span id="functions">
		<ul>
			<li><a href='javascript:void 0' onclick="showUploadControl${param.idElemento}(); return false;"><fmt:message key="form.oggetti.upload" /></a></li>
		</ul>
	</span>
</c:if>
<c:if test="${not empty filename}">
	<div style="margin-left: 4px;">
	<b>${filename} 
		<c:if test="${not empty size}">(${size})</c:if> 
	(id=${id})</b>
	<c:if test="${not empty linkServiziRest }">

		<a href="#" onclick="jQuery('#link_pubblico_${param.idElemento}').toggle()">+</a>
		<p style="display:none;" id="link_pubblico_${param.idElemento}">Link download pubblico <b>${linkServiziRest}</b><br /></p>		
		

	</c:if>
	<c:if test="${libreria_oggetti eq true}">
		<div class="libreria_oggetti" data-value="${id}">L'oggetto è stato inserito dalla libreria e 
		la sua modifica sarà ereditata dalle altre configurazioni che lo stanno usando</div>
	</c:if>
	
	<c:if test="${inite:endsWith(filename, '.p7m') || inite:endsWith(filename, '.pdf') || inite:endsWith(filename, '.xml') || inite:endsWith(filename, '.tsd')
               || inite:endsWith(filename, '.m7m')}">
		<a title="<fmt:message key="label.verifica_firma" />" href="javascript:void 0" 
			onclick="window.open('${pageContext.request.contextPath}/file/popupViewSignedFileInfo.htm?fileId=${id}&idComuneOggetto=${idComuneOggetto}',69,'status=1,menubar=0,scrollbars=1,width=800, height=450, resizable=1')"><img align="middle" src="${pageContext.request.contextPath}/images/certificato.gif"/></a>
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

<%} %>
			
			<li><a target="_blank" href="${downloadFileLink}"><fmt:message key="form.oggetti.view" /></a></li>
<%if(!isBloccato){
%>						
			<!-- §§§BEGIN§§§ -->			
				<c:if test="${inite:isEnterprise()}">
				<c:choose>					
					<c:when test="${isDocumentoSTC eq false}">
					
					<%if(ORMHelper.getIdcomune().equals(request.getAttribute("idComuneOggetto"))){ %>			
						<li><a id="${param.idElemento}_btn_modFile" href="javascript:void 0" onclick="editDocs${param.idElemento}('${id}');"><fmt:message key="label.modifica" /></a>
							 
							<div id="${param.idElemento}_tooltip" dojoType="dijit.Tooltip" connectId="${param.idElemento}_btn_modFile" position="below" style="display: none;"><fmt:message key="label.applet_modifica_doc.help" /></div>						
							
						</li>
					<%} %>	
						
							
					</c:when>					
				</c:choose>	
				</c:if>			
			<!-- §§§END§§§ -->
			<c:if test="${!param.isLibreria}">
				<c:choose>					
					<c:when test="${isDocumentoSTC eq false}">
					<%if(ORMHelper.getIdcomune().equals(request.getAttribute("idComuneOggetto"))){ %>			
						<li><a href="javascript: void 0" onclick="resetOggetto${param.idElemento}('<fmt:message key="javascript.confirm.delete" />');return false;"><fmt:message key="form.oggetti.delete" /></a></li>
					<%} %>	
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