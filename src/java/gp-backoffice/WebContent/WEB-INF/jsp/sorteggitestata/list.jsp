<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="sorteggitestata.label.lista_sorteggitestata.title" /></title>		
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="sorteggitestata.label.lista_sorteggitestata.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
	    </jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
				<jsp:param name="commandName" value="sorteggitestata" />
			</jsp:include>
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
			<form name="sorteggitestataForm" action="list.htm">			
				<fieldset>
					<legend><fmt:message key="sorteggitestata.label.lista_sorteggitestata.title" /></legend>
					<label>Numero righe da visualizzare:</label>
					<select id="numeroRighe">						
						<option value="10">10</option>
						<option value="50">50</option>
						<option value="100" selected>100</option>
					</select>			
					<article class="content">
						<table class="vbg-table" id="sorteggitestata_id">
							<caption></caption>
							<thead>
								<tr>
									<th><fmt:message key="label.codice"/></th>
									<th><fmt:message key="label.data"/></th>
									<th><fmt:message key="label.descrizione"/></th>
									<th><fmt:message key="label.categoria"/></th>
									<th><fmt:message key="label.oggetto"/></th>
									<th><fmt:message key="label.azioni"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${sorteggitestataList}" var="sorteggitestata_var">
									<tr>
										<td><a href="view.htm?codice=${sorteggitestata_var.id.codice}&sorteggidettaglio_id_f_sorteggiata=Si">${sorteggitestata_var.id.codice}</a></td>
										<td><fmt:formatDate value="${sorteggitestata_var.stDatasorteggio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
										<td>${sorteggitestata_var.stDescrizione}</td>
										<td>${sorteggitestata_var.categoria.descrizione}</td>
										<td>
											<c:if test="${sorteggitestata_var.oggetto!=null}">
											<jsp:include page="../includes/visualizzaOggetto.jsp" >
												<jsp:param name="idElemento" value="${sorteggitestata_var.id.codice}" />
												<jsp:param name="fileId" value="${sorteggitestata_var.oggetto.id.codice}" />
											</jsp:include>
											</c:if>	
										</td>
										<td>
											<a class="dettaglioColumn" href="view.htm?codice=${sorteggitestata_var.id.codice}&sorteggidettaglio_id_f_sorteggiata=Si" title="<fmt:message key="label.edit.record" /> ${sorteggitestata_var.id.codice}">
												<label><fmt:message key="label.edit.record.image" /></label>
											</a>													
											<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaSorteggitestata.htm?codice=${sorteggitestata_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${sorteggitestata_var.id.codice}">
												<label><fmt:message key="label.elimina.image" /></label>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</article>
				</fieldset>		
			</form>		
		</div>
	</div>
	<div class="form-button">		
		<a class="btn btn-primary" href="javascript:doHref('create.htm?codiceAlgoritmo=0','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>	
	</div>
	<script type="text/javascript">

		function filtraTestoCelle() {
				
			let input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("input_ricerca");
			filter = input.value.toUpperCase();
			table = document.getElementById("sorteggitestata_id");
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

		const content = document.querySelector('.content'); 
		const items = Array.from(content.getElementsByTagName('tr')).slice(1);
		
		let righePerPagina = document.querySelector('#numeroRighe').value;
		console.log(righePerPagina);

		document.querySelector('#numeroRighe').addEventListener('change', function(e) {
			righePerPagina = e.target.value;
			console.log(righePerPagina);
			createPageButtons(items,righePerPagina); 
			showPage(0,items,righePerPagina);
		});

		document.addEventListener('DOMContentLoaded', function () {
			
			const itemsPerPage = righePerPagina;
			let currentPage = 0;
			//const items = Array.from(content.getElementsByTagName('tr')).slice(1);			

			createPageButtons(items,itemsPerPage); // Call this function to create the page buttons initially
			showPage(currentPage,items,itemsPerPage);
		});	

		function showPage(page,items,itemsPerPage) {
			const startIndex = page * itemsPerPage;
			const endIndex = startIndex + itemsPerPage;
			items.forEach((item, index) => {
				item.classList.toggle('hidden', index < startIndex || index >= endIndex);
			});
			updateActiveButtonStates(page);
		}

		function createPageButtons(items,itemsPerPage) {
			// Remove existing page buttons if existing
			if(document.querySelector('.pagination')!= null){
				document.querySelector('.pagination').remove();
			}

			let totalPages = Math.ceil(items.length / itemsPerPage);
			if(totalPages >1){
				let paginationContainer = document.createElement('div');
				let paginationDiv = document.body.appendChild(paginationContainer);
				paginationContainer.classList.add('pagination');

				// Add page buttons
				for (let i = 0; i < totalPages; i++) {
					const pageButton = document.createElement('a');
					pageButton.setAttribute('href', 'javascript:cambiaPagina('+i+','+itemsPerPage+')');
					pageButton.textContent = i + 1;

					content.appendChild(paginationContainer);
					paginationDiv.appendChild(pageButton);
				}
			}
		}

		function updateActiveButtonStates(currentPage) {
			const pageButtons = document.querySelectorAll('.pagination a');
			pageButtons.forEach((button, index) => {
				if (index === currentPage) {
					button.classList.add('active');
				} else {
					button.classList.remove('active');
				}
			});
		}

		function cambiaPagina(currentPage,itemsPerPage) {
			
			showPage(currentPage,items,itemsPerPage);
			updateActiveButtonStates(currentPage);
		}
		
		
	</script>
	<style>
		.pagination {
			text-align: center;
			margin-top: 20px;
		}

		.pagination a {
			padding: 5px 10px;
			margin: 0 5px;
			cursor: pointer;			
			border-radius: 1px;
			border: none;
		}

		.hidden {
			clip: rect(0 0 0 0);
			clip-path: inset(50%);
			height: 1px;
			overflow: hidden;
			position: absolute;
			white-space: nowrap;
			width: 1px;
		}

		.pagination a.active {
			background-color: #669900;
			color: white;
		}
	</style>
</body>
</html>