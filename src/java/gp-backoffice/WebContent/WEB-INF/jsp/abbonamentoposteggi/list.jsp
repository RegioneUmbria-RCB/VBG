<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="abbonamento.label.lista.title" /></title>
	<style >
	.form-group .form-undergroup {
		display: inline-block;
		padding: var(--default-padding);
	}
	
	#dettaglio {
		float: right;
	}
	
	.messaggio {
		padding-right: var(--default-padding);
	}
	
	.pageNum.active {
		background-color: var(--accent-color);
		color:white;
	}
	</style>	

</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="abbonamento.label.lista.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../abbonamento/list" />	
	</jsp:include>
	<div id="subcontent">
		<form name="abbonamento" action="list.htm">	
			<div class="vbg-form">					
				<fieldset>
					<legend>
						<fmt:message key="abbonamento.label.lista"/></legend>					
						
						<fieldset>
							<legend><fmt:message key="abbonamento.label.lista.filtri_ricerca" /></legend>
							<div class="form-group">
								<div>
									<div id="anagrafe-div" class="form-undergroup">
										<label><fmt:message key="label.anagrafe_cf_pi"/></label>
										<input type="text" class="riga-anagr" id="anagrafe_id"  />
									</div>
									<div id="stato-div" class="form-undergroup">
										<label><fmt:message key="label.stato"/></label>
										<select id="stato" name="stato" class="riga-stato" >
											<option value="0">-- Scegli --</option>
											<c:forEach items="${statiborsellino}" var="statoAbb">
												<option value="${statoAbb}" >${statoAbb}</option>
											</c:forEach>
										</select>
									</div>
									<div id="autorizzazioni-div" class="form-undergroup">
										<label for="autorizzazione"><fmt:message key="label.autorizzazione"/></label>
										<input type="text" id="autorizzazioni_id"
											 class="riga-autor" 
											 name="autorizzazione"
											 />
										
										
										
									</div>	
								</div>						
																				
							</div>
						</fieldset>						
						<fieldset>
							<legend><fmt:message key="abbonamento.label.lista"/></legend>

							<table class="vbg-table" id="tabella-abbonamenti">					
								<thead>
									<tr>
										<th><fmt:message key="label.anagrafe"/></th>
										<th><fmt:message key="label.stato"/></th>
										<th><fmt:message key="label.data_creazione"/></th>
										<th><fmt:message key="abbonamento.label.credito_residuo"/></th>
									</tr>
								</thead>
								<tbody id="table_body">
								</tbody>
							</table> 
							
							<template id="riga-bors">
							  <tr>
							    <td></td>
							    <td></td>
							    <td></td>
							    <td>
								    <a href="javascript:void(0)" id="dettaglio">
								 		<i class="fa fa-edit"></i>
										<fmt:message key="label.dettaglio" />
									</a>
								</td>
							  </tr>
							</template>
						</fieldset>
				</fieldset>
			</div>			
		</form>		
	</div>
	
	<div class="form-button">		
		<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>		
	</div>
	<jsp:include page="./funzioniJS.jsp" />
</body>
</html>