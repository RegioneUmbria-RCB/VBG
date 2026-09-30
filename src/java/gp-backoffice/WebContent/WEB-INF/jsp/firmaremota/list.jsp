<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="firmaremota.label.lista.title" /></title>
	<script type="text/javascript">
	vbg.ready(() => {
		let stati = document.querySelectorAll('table>tbody>tr>td>i.fa-circle');
		stati.forEach(async (e) => {
			let tr = e.parentElement.parentElement;
			
			let servizioAttivo = await _verificaServizio(tr.dataset.provider,tr.dataset.endpoint);
			if( servizioAttivo == true ){
				e.style.color = 'green';
				e.parentElement.appendChild(document.createTextNode(' ATTIVO'));
			} else {
				e.style.color = 'red';
				e.parentElement.appendChild(document.createTextNode(' NON ATTIVO'));
			}
						
		})
	});
	
	async function _verificaServizio(nomeComponente, endpoint){
		try
		{
			
			const postParams = { request: { nomeComponente, endpoint } };
			
			const response = await fetch('../firmadigitale2/jsonRecuperaParametri.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
			const jsResponse = await response.json();
			
			if( response.status === 200 ){
				return true;
			}
			return false;
						
		}
		catch(error) {
			return false;
        }
	}
	
	</script>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="firmaremota.label.lista.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../firmaremota/list" />
	</jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
			<form name="elementoListaFirmeRemoteForm" action="list.htm">
				<fieldset>
					<legend>
						<fmt:message key="label.ricerca" />
					</legend>
					<div class="form-group">
						<div class="input-icons">
							<i class="fa fa-search icon"></i> <input id="input_ricerca"
								type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()" />
						</div>
						<div class="input-help">
							<fmt:message key="label.messaggio_ricerca_tabella" />
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend><fmt:message key="firmaremota.label.lista.title"/></legend>
					<table class="vbg-table">
						<thead>
							<tr>
								<th width="2%"><fmt:message key="label.codice"/></th>
								<th><fmt:message key="firmaremota.table.label.descrizione"/></th>
								<th><fmt:message key="firmaremota.table.label.attiva"/></th>
								<th><fmt:message key="firmaremota.table.label.endpoint"/></th>
								<th><fmt:message key="firmaremota.table.label.statoservizio"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${firmaRemotaListModel}" var="firmaRemota">
								<tr data-provider="${firmaRemota.provider.chiave}" data-endpoint="${firmaRemota.endpoint}">
									<td>
										<a href="javascript:historySet('${_urlback}','../firmaremota/view.htm?codice=${firmaRemota.id}','');">${firmaRemota.id}</a>
									</td>
									<td>${firmaRemota.descrizione}</td>
									<td>${firmaRemota.attiva}</td>
									<td>${firmaRemota.endpoint}</td>
									<td><i class="fa fa-solid fa-circle"></i></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
	<div class="form-button">		
		<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>