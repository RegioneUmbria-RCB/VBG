<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipomovimento.label.lista_tipomovimento.title" /></title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>
	<style>
		.form-button {
			padding-top: var(--default-padding);
		}
	</style>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipomovimento.label.lista_tipomovimento.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
		<input type="hidden" value="${tipomovimento}"/>
			<form name="domainForm" action="list.htm">
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
					<table class="vbg-table" id="tabella-rabbit">
						<thead>
							<tr>
								<th><fmt:message key="label.movimento"/></th>
								<th><fmt:message key="label.topic"/></th>
								<th><fmt:message key="label.messaggio"/></th>
								<th><fmt:message key="label.azioni"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listamovimenti}" var="mov">
								<tr id="rigaItem" data-tipomov="${mov.id.fkTipimovimento}" data-topic="${mov.id.topic}" data-mailid="${mov.mailtipo.id.codice}">
									<td>${mov.id.fkTipimovimento}</td>
									<td>${mov.id.topic}</td>
									<td>${mov.mailtipo.corpo}</td>
									<td>
										<a class="azione" style="float: none;" href="javascript:void(0)" title="<fmt:message key="label.elimina" />">
											<i class="fa fa-trash-o"></i> <fmt:message key="label.elimina" />
										</a>
										<a title="<fmt:message key="label.modifica"/>" class="modifica" href="javascript:doHref('view.htm?tipomovimento=${mov.id.fkTipimovimento}&topic=${mov.id.topic}','')" >
											<i class="fa fa-pencil" aria-hidden="true"></i> <fmt:message key="label.modifica"/>
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>					
					</table>		
			</form>		
			</div>
	</div>
	<script type="text/javascript">
		vbg.ready(() => {
			document.querySelectorAll('#rigaItem').forEach(item =>{				
				
				item.querySelector('.azione').addEventListener('click',(e) =>{
					  eliminaRecord(item);
				  });
			});
		});
		
		async function eliminaRecord(item){
			if(confirm('<fmt:message key="javascript.confirm.delete" />')){
				
				window.vbg.mostraModalCaricamento();
				
				let tipomov = item.dataset.tipomov;			 	
			 	let topic = item.dataset.topic;
				let url = '${pageContext.request.contextPath}/tipimovimentorabbit/ajaxDelete.htm?tipomovimento=' + tipomov + '&topic='+topic;
				
				const response = await fetch(url, {
					method: "POST",
	                cache: "no-cache"	                    
				});
			 	
			 	if (response.status !== 200) {
				      const errore = await response.text();
				      console.error(errore);
				      throw errore;
				    }
			 	 else{
			 		window.vbg.nascondiModalCaricamento();
			 	   	window.location.href = window.location.pathname + window.location.search;			 	    
			 	 }
			}					
		}
		
		function filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue;
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				table = document.getElementById("tabella-rabbit");
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
		<a class="btn btn-primary" href="javascript:doHref('create.htm?tipomovimento=${tipomovimento}','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		
	</div>
</body>
</html>