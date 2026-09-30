<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="inventarioprocedimenti.label.lista_oneri.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_oneri.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
	    </jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
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
			<legend><fmt:message key="inventarioprocedimenti.label.lista_oneri.title" /></legend>
			 	<div class="parametriDiv">
			    	<div class="etichetta">
						<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
					</div>
					<div class="parametro">
						<div><c:out value="${inventarioprocedimenti.procedimento}" /></div>
					</div>
			    </div>
	    		<div class="clear"></div>	
				<form name="oneriForm" action="listoneri.htm">
					<table class="vbg-table" id="oneri_id">
					<caption></caption>
						<thead>
							<tr>
								<th><fmt:message key="label.codice" /></th>
								<th><fmt:message key="inventarioprocedimenti.label.modulo_software" /></th>
								<th><fmt:message key="inventarioprocedimenti.label.tipo_causale" /></th>
								<th><fmt:message key="inventarioprocedimenti.importo_causale" /></th>
								<th><fmt:message key="inventarioprocedimenti.importo_istruttoria" /></th>
								<th><fmt:message key="inventarioprocedimenti.flag_pagato" /></th>
								<th><fmt:message key="label.note" /></th>								
								<th><fmt:message key="label.edit.record" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${inventarioprocedimentioneriList}" var="oneri_var" >
							<tr>
								<td><a href="viewOneri.htm?codiceoneri=${oneri_var.id.codice}">${oneri_var.id.codice}</a></td>
								<td>${oneri_var.software.descrizione}</td>
								<td>${oneri_var.tipicausalioneri.coDescrizione}</td>
								<td>${oneri_var.importo}</td>
								<td>${oneri_var.importoistruttoria}</td>
								<td>${oneri_var.flagPagato ? 'Si':'No'}</td>
								<td>${oneri_var.note}</td>
								<td><a href="viewOneri.htm?codiceoneri=${oneri_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${oneri_var.tipicausalioneri.coDescrizione}">
										<i class="fa fa-edit"></i>
									</a></td>
							</tr>							
							</c:forEach>						
						</tbody>					
					</table>
					  <input type="hidden" value="${inventarioprocedimenti.id.codice}" name="codiceendo"/>
				</form>
				</fieldset>
				<script type="text/javascript">
					function filtraTestoCelle() {
				
						let input, filter, table, tr, td, i, txtValue;
						input = document.getElementById("input_ricerca");
						filter = input.value.toUpperCase();
						table = document.getElementById("oneri_id");
						tr = table.getElementsByTagName("tr");
						let trovato = false;
						for (i = 1; i < tr.length; i++) {
						  td = tr[i].getElementsByTagName("td");
						  for (let cell of td) {
						    if (cell) {
						      txtValue = cell.textContent || cell.innerText;			       
						      if (txtValue.toUpperCase().indexOf(filter) > -1) {			        
						      	trovato = true;
						      }			        
						    }
						  }
						  if(trovato){
						  	tr[i].style.display = "";
						  	trovato = false;
						  } else{
						  	tr[i].style.display = "none";				  	
						  }
						}
					}				
					
				</script>
			</div>
		</div>
		<div class="form-button">			
			<a class="btn btn-primary" href="javascript:doHref('createOneri.htm?codiceendo=${inventarioprocedimenti.id.codice}','');"><fmt:message key="button.new" /></a>
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>			
		</div>
	</body>
</html>