<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_convocati" /></title>
	
	<style type="text/css">
		
		.dato_aggiornato{
			color: var(--color-success);
		}
		.dato_aggiornamento_errore{
			color: var(--error-color);
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
			width: 500px;			
		}
	</style>
	
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_convocati" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">	
	
	
	<div class="vbg-form ">
	<fieldset>
		<legend><fmt:message key="label.dettaglio_commissione_edilizia"/></legend>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.numero_commissione" />:</div>
				<div><fmt:message key="label.descrizione" />:</div> 
			</div>
			<div class="parametro">
				<div>${commissioniedilizieT.numprotocollo}</div>
				<div>${commissioniedilizieT.descrizione}</div>
			</div>
		</div>		
	</fieldset>
		
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
			<legend><fmt:message key="label.commisioni_edilize_elenco_soggetti" /></legend>
			<form name="commedilizieappelloForm" action="list.htm">
					<table class="vbg-table" id ="tabella_lista_appello">
						<thead>
							<th><fmt:message key="label.codice" /></th>
							<th><fmt:message key="label.componente" /></th>
							<th><fmt:message key="commedilizieappello.label.pin_accesso"/></th>
							<th><fmt:message key="label.carica" /></th>
							<th><fmt:message key="label.presente" /></th>
							<th><fmt:message key="label.edit.record" /></th>			
						</thead>
						<tbody>
							<c:forEach items="${commedilizieappelloList}" var="commedilizieappello_var">
								<tr>
									<td>
										<a href="view.htm?codice=${commedilizieappello_var.id.codice}">${commedilizieappello_var.id.codice}</a>
									</td>
									<td>
										${commedilizieappello_var.componente }										
									</td>
									<td>										
										${commedilizieappello_var.pin }										
									</td>
									<td>${commedilizieappello_var.commedilizieCarica.descrizione }</td>
									<td>
										<input  type="checkbox" value="${commedilizieappello_var.id.codice}" name="chk_convocato" ${commedilizieappello_var.presente?'checked':''} onclick="abilita(this, 'result_${commedilizieappello_var.id.codice}')"/>
			                        	<span id="result_${commedilizieappello_var.id.codice}" style="display: none"></span>		
									</td>
									<td>
										<a class="modifica" href="view.htm?codice=${commedilizieappello_var.id.codice}" >
										<i class="fa fa-pencil" aria-hidden="true"></i> Modifica</a>
									
									</td>
								</tr>			
							</c:forEach>
						</tbody>			
					</table>			
					 <input type="hidden" value="${commissioniedilizieT.id.codice}" name="codiceCommissione"/>
				</form>
		</fieldset>
		<script type="text/javascript">
						
		async function abilita(obj, id){														
				
				vbg.mostraModalCaricamento();
				
				const response = await fetch("../commedilizieappello/ajaxAbilitaDisabilita.htm?codice="+escape(obj.value)+"&abilita="+obj.checked, {
	                method: "POST",
	                cache: "no-cache",
	                headers: {
	                    'Content-Type': 'application/json'
	                }
				
				});				
			
				vbg.nascondiModalCaricamento();
				let messagioDatoAggiornato = document.getElementById(id);
				let messaggio = await response.text();
				messagioDatoAggiornato.innerHTML = messaggio;
				messagioDatoAggiornato.setAttribute("title",messaggio );
				messagioDatoAggiornato.style.display="inline-block";
				if (response.status === 200) {
					messagioDatoAggiornato.classList.remove("dato_aggiornamento_errore");
					messagioDatoAggiornato.classList.add("dato_aggiornato");
					setTimeout(()=>{ messagioDatoAggiornato.style.display="none"; }, 2000);
				}else{
					messagioDatoAggiornato.classList.remove("dato_aggiornato");
					messagioDatoAggiornato.classList.add("dato_aggiornamento_errore");
					
				}
				
		}		
			
			
			function filtraTestoCelle() {
				  let input, filter, table, tr, td, i, txtValue;
				  input = document.getElementById("input_ricerca");
				  filter = input.value.toUpperCase();
				  table = document.getElementById("tabella_lista_appello");
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
		<c:if test="${commissioniedilizieT.flagaperta eq true}">
			<a class="btn btn-primary" href="javascript:doHref('create.htm?codiceCommissione=${commissioniedilizieT.id.codice}','');"><fmt:message key="button.new" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('../commissioniediliziet/view.htm?codice=${commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>