<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.conti.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.conti.title.list" /></span>
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
		
			<form name="contiForm" action="list.htm">
			<fieldset>
				<legend>
					<fmt:message key="form.conti.title.list" />
				</legend>	
				<table id="conti_tab" class="vbg-table">
					<thead>
						<tr>
							<th width="2%"><fmt:message key="label.codice"/></th>
							<th width="40%"><fmt:message key="form.conti.descrizione"/></th>
							<th width="5%"><fmt:message key="form.conti.datascadenza"/></th>
							<th width="10%"><fmt:message key="form.conti.note"/></th>
							<th width="5%"><fmt:message key="tipicausalioneri.label.mappaturanodopag"/></th>
							<th width="2%"><fmt:message key="label.edit.record"/></th>							
						</tr>
					</thead>
					<tbody>
					<c:forEach var="conti_var" items="${contiList}">
						<tr>
							<td><a href="view.htm?codice=${conti_var.id.codice}">${conti_var.id.codice}</a></td>
							<td>${conti_var.descrizione}</td>
							<td><fmt:formatDate value="${conti_var.dataScadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></td>
							<td>${conti_var.note}</td>
							<td>${conti_var.mappaturanodopag}</td>
							<td>
								<a class="dettaglioColumn" href="view.htm?codice=${conti_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${conti_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
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
				var _captionTab='<fmt:message key="form.conti.title.list" />';
				
				function filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue;
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				table = document.getElementById("conti_tab");
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