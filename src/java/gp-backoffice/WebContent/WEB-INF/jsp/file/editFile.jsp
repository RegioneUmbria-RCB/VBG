<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="label.applet_modifica_doc" /></title>
	<script src="${pageContext.request.contextPath}/scripts/deployJava.js"></script>	
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/layouts/layout.css" />
	<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/styles/standard.css" />
	<style type="text/css"> 
	<!--
	#app {
		width: 100%;
		height: 70px;
	}
	#functions {
		
	}
	-->
	</style>
	<script type="text/javascript">
		function chiudi(){
			
			<c:if test="${not empty param.func}">			
				window.opener.${param.func}();
			</c:if>
			window.close();
		}
	</script>
</head>
<body style="padding: 20px 20px 20px 20px;">
<!-- §§§BEGIN§§§ -->			
	
		<fieldset>
			<legend><fmt:message key="label.applet_modifica_doc" /></legend>
			<div style="padding: 20px 20px 20px 20px;">
				<fmt:message key="label.applet_modifica_doc.help" />
			</div>	
			
			<div id="app">
				<script>
					var attributes = {width:'100%', height:'100%'} ;
					var parameters = {				
						labelBtnInviaModifiche : 'SALVA MODIFICHE',
						debug : 'true',
						urlOggetti : '${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=${oggetto.id.codice}',
						urlUploadOggetti : '${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/file/ajaxUpdate.htm?fileId=${oggetto.id.codice}',
						jnlp_href : '${pageContext.request.contextPath}/init-editdocs-applet.jnlp'
					};
					var version = '1.6';
					deployJava.runApplet(attributes, parameters, version);
				</script>
			</div>
		</fieldset>
		<div id="functions">
			<ul>
				<li>
					<a href="javascript:chiudi();"><fmt:message key="button.back" /></a>
				</li>
			</ul>
		</div>
		
<!-- §§§END§§§ -->
</body>
</html>