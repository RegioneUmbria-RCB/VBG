<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><fmt:message key="label.elaborazioni_massive_schede_istanza" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.elaborazioni_massive_schede_istanza" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<div id="subcontent">
	
	
		<div class="vbg-form">
			<fieldset>
				<legend><fmt:message key="label.ricerca" /></legend>
				<div class="form-group">
					<div class="input-icons">
						<i class="fa fa-search icon"></i>
						<input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
					</div>					
				</div>		
			</fieldset>
		
			<fieldset>
				<legend><fmt:message key="label.elaborazioni_massive_schede_istanza.lista" /></legend>
				
				<div class="table-fix-head">		
					<table class="vbg-table" id="lista_elaborazioni">
						<thead>
							<th style="width: 60%"><fmt:message key="label.descrizione" /></th>
							<th><fmt:message key="label.data_inizio" /></th>
							<th><fmt:message key="label.data_fine" /></th>
							<th><fmt:message key="label.azioni" /></th>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="testata">
								<c:if test="${!testata.flgEliminata || testata.flgEliminata == null }">
									<tr>
										<td class="filterable">
											${testata.descrizione}
										</td>
										<td>
											<fmt:formatDate value="${testata.dataInizio}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" />	
										</td>	
										<td>
											<fmt:formatDate value="${testata.dataFine}" pattern="<%=WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN %>" />	
										</td>								
										<td>
											<a class="modifica" href="view.htm?idElaborazione=${testata.id}" >
											<i class="fa fa-pencil" aria-hidden="true"></i> <fmt:message key="label.modifica" /></a>
											<a class="elimina" href="eliminaElaborazioneMassiveRiga.htm?idElaborazione=${testata.id}" >
											<i class="fa fa-times" aria-hidden="true"></i> <fmt:message key="label.elimina" /></a>
										</td>
									</tr>
								</c:if>				
							</c:forEach>
						</tbody>		
					</table>
				</div>
			</fieldset>	
		</div>
	
	</div>
	<div>	
		<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>	
	</div>
	<script type="text/javascript">
	
	function filtraTestoCelle() {
		  let input, filter, table, tr, td, i, txtValue;
		  input = document.getElementById("input_ricerca");
		  filter = input.value.toUpperCase();
		  table = document.getElementById("lista_elaborazioni");
		  tr = table.getElementsByTagName("tr");
		  let trovato = false;
		  for (i = 1; i < tr.length; i++) {
			  td = tr[i].getElementsByClassName("filterable");
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
	
</body>
</html>