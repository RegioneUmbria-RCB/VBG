<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:message key="label.applet_modifica_doc.help" />
<fieldset>
<applet 
		name="Edit Docs Applet"
		code="it.gruppoinit.pal.gp.backoffice.applets.EditDocsApplet" 
		codebase="${pageContext.request.contextPath}/applets/" 
		archive="init-editdocs-applet.jar"
		height="100" 
		width="800" 
		align="middle" style="padding: 2px;"><p>Questo browser non supporta le Java Applet.</p>
		<param name="debug" value="true" />
		<param name="callJsCloseEditDocs" value="true" />
		<param name="callJsCloseEditDocsFunc" value="${param.func }" />
		<param name="labelBtnInviaModifiche" value='<fmt:message key="label.applet_modifica_doc.button" />' />
		<param name="labelDownloadCompletato" value='<fmt:message key="label.applet_modifica_doc.download_completed" />' />
		<param name="urlOggetti" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/file/ajaxModificaDocumento.htm?fileId=${oggetto.id.codice}&ts_=<%= System.currentTimeMillis() %>" />
		<param name="urlUploadOggetti" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/file/ajaxUpdate.htm?fileId=${oggetto.id.codice}&ts_=<%= System.currentTimeMillis() %>" />
</applet>
</fieldset>