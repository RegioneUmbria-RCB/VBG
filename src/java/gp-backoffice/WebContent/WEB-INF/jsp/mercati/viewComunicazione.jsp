<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.comunicazione.detail.title" /></title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-comunicazioni-massive/vbg-comunicazioni-massive-detail.js?<%=vJS %>" defer></script>
		<script type="text/javascript">
		vbg.ready(() => {
			
			let schedulato = ${schedulerAttivo};
			
			let dati = ${dettaglio};
			let dettaglio = dati.comunicazione;
			
			let comunicazione = document.createElement('vbg-comunicazioni-massive-detail');
				comunicazione.setAttribute('data-alias',dettaglio.alias);
				comunicazione.setAttribute('data-software',dettaglio.software);
				comunicazione.setAttribute('data-auth','${restPrivateAuthorization}');
				comunicazione.setAttribute('data-return-to','../mercati/listComunicazioni.htm?codicemercato=${codiceManifestazione}');
				comunicazione.setAttribute('data-titolo','${manifestazione}');
				comunicazione.databind(dettaglio);

			let divScheduler = document.createElement('div');
			if(schedulato === false){
				divScheduler.classList.add('warningLine');
				divScheduler.textContent = 'Nessuna operazione pianificata attiva per elaborare massivamente le comunicazioni in automatico';				
			}
			let subcontent = document.querySelector('#subcontent');
				subcontent.appendChild(divScheduler);	
				subcontent.appendChild(comunicazione);


		});
		</script>
	</head>
	<body>
	<form>
		<span class="titoloPagina"><fmt:message key="label.comunicazione.detail.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
    	</jsp:include>
	    <div id="subcontent"></div>
	   </form>
	</body>
</html>