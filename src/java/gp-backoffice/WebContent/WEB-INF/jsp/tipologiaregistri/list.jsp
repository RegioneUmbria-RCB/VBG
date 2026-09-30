<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><fmt:message key="tipologiaregistri.label.lista_tipologiaregistri.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipologiaregistri.label.lista_tipologiaregistri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<div id="subcontent">
	<form name="tipologiaregistriForm" action="list.htm">
	
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
				<legend><fmt:message key="tipologiaregistri.label.lista_tipologiaregistri.title" /></legend>		
				<table class="vbg-table" id="lista_tipiregistro">
					<thead>
						<th><fmt:message key="label.codice" /></th>
						<th><fmt:message key="tipologiaregistri.label.trdescrizione" /></th>
						<th title="<fmt:message key="tipologiaregistri.label.flag_manifestazioni.help" />"><fmt:message key="tipologiaregistri.label.flag_manifestazioni" /></th>
						<th><fmt:message key="label.edit.record" /></th>
					</thead>
					<tbody>
						<c:forEach items="${tipologiaregistriList}" var="tipologiaregistro">
							<tr>
								<td>
									<a href="view.htm?codice=${tipologiaregistro.id.codice}">${tipologiaregistro.id.codice}</a>
								</td>
								<td>${tipologiaregistro.trDescrizione }</td>
								<td>
									<c:choose>
										<c:when test="${tipologiaregistro.flagManifestazioni eq true}"><fmt:message key="label.si" /></c:when>
										<c:otherwise><fmt:message key="label.no" /></c:otherwise>
									</c:choose>
									</td>
								<td>
									<a class="modifica" href="view.htm?codice=${tipologiaregistro.id.codice}" >
									<i class="fa fa-pencil" aria-hidden="true"></i> Modifica</a>
								</td>
							</tr>				
						</c:forEach>
					</tbody>		
				</table>
			</fieldset>	
		</div>
	</form>	
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
		  table = document.getElementById("lista_tipiregistro");
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
	
</body>
</html>