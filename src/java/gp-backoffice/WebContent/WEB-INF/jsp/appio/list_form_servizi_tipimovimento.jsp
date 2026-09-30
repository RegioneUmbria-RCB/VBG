<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html lang="it">
	<head>
		<meta charset="UTF-8">
		<title><fmt:message key="label.appio.list"/> </title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.appio.list"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../appio/list_servizi_tipomovimento" />
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
				<fieldset>
				<input type="hidden" id="idservizio" value="${idservizio}"/>
					<legend><fmt:message key="label.appio.list" /></legend>					
					<table class="vbg-table" id="tabella-servizi">
						<thead>
							<th><fmt:message key="appio.label.servizio" /></th>
							<th><fmt:message key="label.tipomovimento" /></th>
							<th><fmt:message key="label.azioni"/></th>
						</thead>
						<tbody>
							<c:forEach  items="${tipimovimentoAppIoServizi}" var="tipomovSer" >
								<tr>
									<td>${tipomovSer.appIoServizi.descrizione }&nbsp;(<a href="#" class="servizio" data-id-servizio="${tipomovSer.id.identificativoServizio }">${tipomovSer.id.identificativoServizio }</a>)</td>
									<td>${tipomovSer.tipimovimento.descrizioneEstesa }</td>
									<td>
										<a href="javascript:void(0)" id="dettaglio" 
											data-id-servizio="${tipomovSer.id.identificativoServizio}" 
											data-id-tipomovimento="${tipomovSer.id.tipomovimento}">
										 		<i class="fa fa-edit"></i>
												<fmt:message key="label.dettaglio" />
										</a>
										<a href="#" id="elimina" data-id-servizio="${tipomovSer.id.identificativoServizio}" data-id-tipomovimento="${tipomovSer.id.tipomovimento}"><i class="fa fa-trash" aria-hidden="true"></i>
										<fmt:message key="label.elimina" /></a>										
									</td>
								</tr>							
							</c:forEach>						
						</tbody>					
					</table>
				</fieldset>
			</div>
		</div>
		<script type="text/javascript">
		vbg.ready(() => {

			document.querySelectorAll('#elimina').forEach( (elm) =>{
			    
				elm.addEventListener('click', (e) =>{
					 if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
						elimina(e);
					 }
			    });
			});	
			
		document.querySelectorAll('#dettaglio').forEach( (elm) =>{
			    
				elm.addEventListener('click', (e) =>{
					let idServizio = e.target.dataset.idServizio;
					let idTipoMovimento = e.target.dataset.idTipomovimento;
					javascript:historySet('../appioserviziconfig/listservizitipimovimento.htm?idservizio='+idServizio,'../appioserviziconfig/viewservizitipimovimento.htm?id.identificativoServizio='+idServizio+"&id.tipomovimento="+idTipoMovimento);
			    });
			});	
			
			
		    document.querySelectorAll('.servizio').forEach( (elm) =>{
					    
				elm.addEventListener('click', (e) =>{
					const idServizio = e.target.dataset.idServizio;
					javascript:historySet('../appioserviziconfig/listservizitipimovimento.htm?idservizio='+idServizio,'../appioserviziconfig/serviziente.htm?idservizio='+idServizio);
					
				});
			});	
		    
		    document.querySelector('#nuovo_mov').addEventListener('click', ()=>{
		    	
		    	let idservizio = document.querySelector('#idservizio').value;
		    	javascript:historySet('../appioserviziconfig/listservizitipimovimento.htm?idservizio='+idservizio,'../appioserviziconfig/createservizitipimovimento.htm?id.identificativoServizio='+idservizio);
		    });
			
		});	
		
		
			function filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue;
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				table = document.getElementById("tabella-servizi");
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
			
			async function elimina(e){
				
				let idServizio = e.target.dataset.idServizio;
				let idTipoMovimento = e.target.dataset.idTipomovimento;
				window.vbg.mostraModalCaricamento();
				const response = await fetch('../appioserviziconfig/jsonEliminaTipimovimentoServizio.htm?idservizio='+idServizio+'&tipomovimento='+idTipoMovimento, {
            		method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                    }
                    
            	});
				
				if( await response.status == 200){
					e.target.parentNode.parentNode.remove();
					window.vbg.nascondiModalCaricamento();
            		
            	} else {
            		_gestioneErrori(await response.json());
            	}
			}
			
			function _gestioneErrori(errJson){
	     		console.log(errJson.error);
	     		alert( errJson.error);
				}
		</script>
		<div class="form-button">
			<a id="nuovo_mov" class="btn btn-primary" href="#"><fmt:message key="button.new" /></a> 
			<a class="btn btn-secondary" href="javascript:doHref('../appioserviziconfig/serviziente.htm?idservizio=${idservizio}','')"><fmt:message key="button.back" /></a>
		</div>
	</body>
</html>