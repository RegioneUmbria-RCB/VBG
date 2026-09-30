<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.istanze.title.search" /></title>
	</head>
	<body>
		<div id="subcontent">
				<c:set var="software" value="<%=it.gruppoinit.pal.gp.core.dao.helper.ORMHelper.getSoftware() %>" scope="page"/>
    			<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPE_MOVIMENTO_CREA_ALLEGATO());%>
				<c:set var="_URL_STAMPA" value="${URL_STAMPA}?modo=LD&codiceIstanza=${param.codice}&codiceMovimento=${movimentoAvvio.id.codice}&TipoMovimento=${movimentoAvvio.tipomovimento.id.tipomovimento}&tipoInserimento=${tipoInserimento}" />
				<c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}" />
				<c:set var="warnings" value="${inite:getWarnings()}" />	
				<c:set var="confirmMessage"><fmt:message key="javascript.confirm.inserimento_rapido" /></c:set>
					<script type="text/javascript">
						var newWin = window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');						
					</script>
					<c:if test="${not empty warnings}">
						<c:set var="confirmMessage"><fmt:message key="javascript.confirm.inserimento_rapido_con_avvertimenti" /></c:set>
					</c:if>
				    <div id="infoInserimento_content" style="display: none;">
				    	${confirmMessage}						    	
						<div id="functions">
							<ul>
								<li><a href="javascript:window.location.replace('create.htm?software=${software}&tipoInserimento=4');"><fmt:message key="button.ok" /></a></li>
								<li><a href="javascript:window.location.replace('view.htm?software=${software}&codice=${param.codice}');"><fmt:message key="button.annulla" /></a></li>
							</ul>
						</div>								
						<c:if test="${not empty warnings}">
							<fmt:message key="label.avvertimenti" />
							<ul>
							<c:forEach items="${warnings}" var="g_err">
								<li>${g_err}</li>
							</c:forEach>
							</ul>
						</c:if>
				    </div>					    
					<script type="text/javascript">
						var showDialogInserimento = function(){
							var myDialogInserimento = new dijit.Dialog({
					            title: "<fmt:message key="label.info" />",
					            style: "overflow:auto; width: 600px; height: 250px;",
					            closable: false
					        });
							myDialogInserimento.attr("content",$('infoInserimento_content').innerHTML);
							myDialogInserimento.hide = function(){
								window.location.replace('view.htm?software=${software}&codice=${param.codice}');
							};
							myDialogInserimento.show();
						};
						jQuery(document).ready(function(){
							setTimeout('showDialogInserimento()',10);
						});
					</script>						
    	</div>
	</body>
</html>