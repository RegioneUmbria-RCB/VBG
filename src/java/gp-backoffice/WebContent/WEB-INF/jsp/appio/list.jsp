<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html lang="it">
	<head>
		<meta charset="UTF-8">
		<title><fmt:message key="label.appio.list"/> </title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.appio.list"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../appio/list" />
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
					<legend><fmt:message key="label.appio.list" /></legend>					
					<table class="vbg-table" id="tabella-servizi">
						<caption/>
						<thead>
							<th><fmt:message key="label.appio.identificativo_servizio" /></th>
							<th><fmt:message key="label.descrizione" /></th>
							<th><fmt:message key="label.dettaglio"/></th>
						</thead>
						<tbody>
							<c:forEach var="servizi" items="${ioServizi}" >
								<tr>
									<td>${servizi.id.identificativoServizio }</td>
									<td>${servizi.descrizione }</td>
									<td>
										<a href="#" id="dettaglio" data-id-servizio="${servizi.id.identificativoServizio}">
										<i class="fa fa-search" aria-hidden="true"></i>
										<fmt:message key="label.dettaglio" /></a>										
									</td>
								</tr>							
							</c:forEach>						
						</tbody>					
					</table>
				</fieldset>
			</div>
		</div>
		<script type="text/javascript">
		
			document.querySelectorAll('#dettaglio').forEach( (dett) =>{
			    
			    dett.addEventListener('click', (e) =>{
				
					const idServizio = e.target.dataset.idServizio;
					console.log("Dettaglio:", e.target.dataset.idServizio);					
					javascript:historySet('../appioserviziconfig/list.htm','../appioserviziconfig/serviziente.htm?idservizio='+idServizio,'');			
			    });
			});				
		
		
			function filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue;
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				table = document.getElementById("tabella-servizi");
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
		<div class="form-button">
			<a class="btn btn-primary" href="javascript:historySet('../appioserviziconfig/list.htm','../appioserviziconfig/serviziente.htm','');"><fmt:message key="button.new" /></a> 
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		</div>
	</body>
</html>