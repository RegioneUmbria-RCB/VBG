<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="alberoCoefficientiT.label.lista_alberoCoefficientiT.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="alberoCoefficientiT.label.lista_alberoCoefficientiT.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="alberoCoefficientiTForm" action="list.htm">
			<div class="vbg-form">
				<fieldset>
					<legend><fmt:message key="label.form_ricerca" /> </legend>
					<div class="form-group">
						<div class="input-icons">
							<i class="fa fa-search icon"></i>
							<input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
						</div>
						<div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella" /></div>		
					</div>
				</fieldset>	
				<fieldset>
					<legend><fmt:message key="label.bandi_configurati" /> </legend>
					<table id="tabella-coefficienti" class="vbg-table">
						<thead>
							<tr>
								<th><fmt:message key="label.codice"/></th>					
								<th><fmt:message key="label.descrizione"/></th>							
								<th><fmt:message key="label.alberoproc"/></th>
								<th><fmt:message key="label.note"/></th>
								<th><fmt:message key="label.attivo"/></th>
								<th><fmt:message key="label.azioni"/></th>		
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${alberoCoefficientiTList}" var="coeff">
								<tr>
									<td>
										<a href="view.htm?codice=${coeff.id.codice}">${coeff.id.codice}</a>										
									</td>
									<td>${coeff.descrizione}</td>
									<td>${coeff.alberoProc.scDescrizione }</td>
									<td>${coeff.note }</td>
									<td>
										<input type="checkbox" ${coeff.attivo? 'checked':''} onclick="gestAttivo(${coeff.id.codice}, this)" />
									</td>
									<td>
										<a href="javascript:cancellaTestata(${coeff.id.codice})" title="<fmt:message key="label.elimina"/>">
											<i class="fa fa-trash-o"></i> <fmt:message key="label.elimina" />
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</div>
			
		</form>
		<script type="text/javascript">
		
		
		
		async function gestAttivo(codice, obj){
		    
		    
			   	window.vbg.mostraModalCaricamento();
			   	
			   	const data = new URLSearchParams();
			 	data.append('codice', codice);
			 	data.append('attiva', obj.checked)
			   	
			   	let url = "${pageContext.request.contextPath}/alberocoefficientit/ajaxGestAttivoTestata.htm";
			 	const response = await fetch(url, {
					method: "POST",
	                cache: "no-cache",
	                body: data       
				});
			 	
			 	const errore = await response.text();
			 	window.vbg.nascondiModalCaricamento();		 	    
			 	if (response.status !== 200) {
				      console.error(errore);
				      throw errore;
				 }
			 
		}
		
		
			async function cancellaTestata(codice){
			    if(confirm('<fmt:message key="javascript.confirm.delete" />')){
			    
				   	window.vbg.mostraModalCaricamento();
				   	
				   	const data = new URLSearchParams();
				 	data.append('codice',codice);
				   	
				   	let url = "${pageContext.request.contextPath}/alberocoefficientit/ajaxDeleteTestata.htm";
				 	const response = await fetch(url, {
						method: "POST",
		                cache: "no-cache",
		                body: data       
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
				table = document.getElementById("tabella-coefficienti");
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

			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="alberoCoefficientiT.label.lista_alberoCoefficientiT.title" />';
		</script>
		<div class="form-button">		
				<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
				<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>		
			</div>
	</div>
	
</body>
</html>