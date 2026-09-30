<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message
		key="label.anagrafe_tributaria_tracciati.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message
			key="label.anagrafe_tributaria_tracciati.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafetributaria/list" />
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


			<table id="tabella_lista_dati" class="vbg-table">
				<thead>
					<th><fmt:message key="label.codice" /></th>
					<th><fmt:message key="label.descrizione" /></th>
					<th><fmt:message key="label.data" /></th>
					<th><fmt:message key="label.responsabile" /></th>
					<th><fmt:message key="label.edit.record" /></th>
				</thead>
				<tbody>
					<c:forEach items="${esitiSalvati}" var="esitiSalvati_var">
						<tr>
							<td><a
								href="javascript:historySet('${_urlback}','../anagrafetributaria/view.htm?codice=${esitiSalvati_var.id}','')">${esitiSalvati_var.id}</a></td>
							<td>${esitiSalvati_var.descrizione }</td>
							<td><fmt:formatDate value="${esitiSalvati_var.data }" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </td>
							<td>${esitiSalvati_var.responsabile }</td>
							<td><a class="modifica"
								href="javascript:historySet('${_urlback}','../anagrafetributaria/view.htm?codice=${esitiSalvati_var.id}','')">
									<i class="fa fa-pencil" aria-hidden="true"></i> <fmt:message
										key="label.modifica" />
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<script type="text/javascript">
		function filtraTestoCelle() {
			  let input, filter, table, tr, td, i, txtValue;
			  input = document.getElementById("input_ricerca");
			  filter = input.value.toUpperCase();
			  table = document.getElementById("tabella_lista_dati");
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
	<div style="margin-top: 10px;">
		<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a> 
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>