<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.comunicazione.mercati.list.title" /></title>
		<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-comunicazioni-massive/vbg-comunicazioni-massive-list-gen.js?<%=vJS %>" defer></script>
		<script type="text/javascript">
		vbg.ready(() => {
			
			let schedulato = ${schedulerAttivo};
			
			let dati = ${dettaglio};
			let dettaglio = dati.comunicazioni_massive;
			
			let divScheduler = document.createElement('div');
			if(schedulato === false){
				divScheduler.classList.add('warningLine');
				divScheduler.textContent = 'Nessuna operazione pianificata attiva per elaborare massivamente le comunicazioni in automatico';				
			}
						
			let comunicazioni = document.createElement('vbg-comunicazioni-massive-list-gen');
				comunicazioni.setAttribute('data-alias',dettaglio.alias);
				comunicazioni.setAttribute('data-software',dettaglio.software);
				comunicazioni.setAttribute('data-titolo',dettaglio.manifestazione);
				comunicazioni.setAttribute('data-auth','${restPrivateAuthorization}');
				comunicazioni.setAttribute('data-return-to','../istanze/searchIstanze.htm');
				comunicazioni.databind(dettaglio.comunicazioni);
				comunicazioni.addEventListener('detailclick',function(ev){
					let id = ev.detail.id;
					doHref('viewComunicazioneM.htm?idcomunicazione=' + id);
				});
				comunicazioni.nuovacomunicazione(${not empty urlnuovacomunicazione ? urlnuovacomunicazione : "''"});
			let subcontent = document.querySelector('#subcontent');
				subcontent.appendChild(divScheduler);	
				subcontent.appendChild(comunicazioni);
			
		});
		</script>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.comunicazione.mercati.list.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
    	</jsp:include>
	    <div id="subcontent"></div>
	    <p>Se si vuole creare una nuova comunicazione, è necessario effettuare una nuova ricerca sulle istanze, e in seguito cliccare sull'apposito bottone presente sotto
	    i risultati di ricerca</p>
	</body>
</html>