<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="commedilizietipopareri.label.lista_commedilizietipopareri.title" /></title>
</head>
<body>

	<span class="titoloPagina"><fmt:message key="commedilizietipopareri.label.lista_commedilizietipopareri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
			<fieldset>
			<legend><fmt:message key="label.commisioni_edilize_ricerca" /></legend>
				<div class="form-group">
					<div class="input-icons">
						<i class="fa fa-search icon"></i>
						<input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
					</div>
					<div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella" /></div>			
				</div>
			</fieldset>	
			
			<fieldset>
				<legend>Lista pareri</legend>
				<form name="commedilizietipopareriForm" action="list.htm">
					<table id="commedilizietipopareri_id" class="vbg-table">
						<thead>
							<th><fmt:message key="label.codice"/></th>
							<th><fmt:message key="label.descrizione"/></th>
							<th><fmt:message key="label.edit.record"/></th>
						</thead>
						<tbody>
							<c:forEach items="${commedilizietipopareriList}" var="commedilizietipopareri_var">
								<tr>
									<td><a href="view.htm?codice=${commedilizietipopareri_var.id.codice}">${commedilizietipopareri_var.id.codice}</a></td>
									<td>${commedilizietipopareri_var.descrizione}</td>
									<td>
										<a class="modifica" href="view.htm?codice=${commedilizietipopareri_var.id.codice}" title="<fmt:message key="label.edit.record" />${commedilizietipopareri_var.id.codice}">
											<i class="fa fa-pencil" aria-hidden="true"></i> Modifica
										</a>
									</td>
								</tr>
							</c:forEach>
						
						</tbody>
					
					</table>
				</form>
			</fieldset>
		</div>
		<script type="text/javascript">
			
			function filtraTestoCelle() {
				  let input, filter, table, tr, td, i, txtValue;
				  input = document.getElementById("input_ricerca");
				  filter = input.value.toUpperCase();
				  table = document.getElementById("commedilizietipopareri_id");
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
	<div>	
		<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>	
	</div>
</body>
</html>