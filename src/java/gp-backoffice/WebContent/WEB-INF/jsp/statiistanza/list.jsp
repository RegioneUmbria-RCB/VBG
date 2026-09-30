<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="statiistanze.label.lista_statiistanza.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="statiistanze.label.lista_statiistanza.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
				<form name="statiistanzaForm" action="list.htm">					
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
						<legend><fmt:message key="statiistanze.label.lista_statiistanza.title" /></legend>
						<table class="vbg-table" id="lista_stati" >
						<caption/>
							<thead>
								<tr>
									<th><fmt:message key="label.codice"/></th>
									<th><fmt:message key="statiistanze.label.stato"/></th>
									<th><fmt:message key="statiistanze.label.stato_esterno"/></th>
									<th><fmt:message key="statiistanze.label.modificaistanza"/></th>
									<th><fmt:message key="statiistanze.label.flag_warning"/></th>
									<th><fmt:message key="label.edit.record"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="statiistanza_var" items="${statiistanzaList}">
									<tr>
										<td>
											<a href="view.htm?codice=${statiistanza_var.id.codicestato}">${statiistanza_var.id.codicestato}</a>
										</td>
										<td>${statiistanza_var.stato}</td>
										<td>${statiistanza_var.etichettaSistemaEsterno}</td>
										<td>${statiistanza_var.modificaistanza ? 'Si' : 'No'} </td>
										<td>
											<c:if test="${statiistanza_var.flagWarning ne null}">
												${statiistanza_var.flagWarning ? 'Si' : 'No'}
											</c:if>
										</td>
										<td>
											<a href="view.htm?codice=${statiistanza_var.id.codicestato}" title="<fmt:message key="label.edit.record" /> ${statiistanza_var.id.codicestato}">
												<i class="fa fa-edit"></i>
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</fieldset>
				
				</form>
				
				<script type="text/javascript">
					var _jmesaUrl='list.htm?';
					var _captionTab='<fmt:message key="statiistanze.label.lista_statiistanza.title" />';
					
					function filtraTestoCelle() {
				
						let input, filter, table, tr, td, i, txtValue;
						input = document.getElementById("input_ricerca");
						filter = input.value.toUpperCase();
						table = document.getElementById("lista_stati");
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
			
			<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
			
		</div>
	</body>
</html>