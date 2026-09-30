<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_documenti_commissione" /></title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>	
</head>
<body>


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

	<span class="titoloPagina"><fmt:message key="label.lista_documenti_commissione"/></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
			<fieldset>
    			<legend><fmt:message key="label.dettaglio_commissione_edilizia" /></legend>
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
		
		<br class="clear"/>
	
	
		<fieldset>
			<legend>Elenco documenti</legend>
		
		<table class="vbg-table">
		 	<thead>
				<tr>
					<th width="5%"><fmt:message key="label.codice"/></th>
					<th><fmt:message key="label.descrizione"/></th>
					<th width="40%"><fmt:message key="label.note"/></th>
					<th width="5%"><fmt:message key="label.data_registrazione"/></th>
					<th width="5%"><fmt:message key="label.pubblica"/></th>
					<th width="10%"><fmt:message key="label.file"/></th>
					<th width="2%"><fmt:message key="label.azioni"/></th>
				</tr>	 	
		 	</thead>
		 	<tbody>
		 		<c:forEach items="${commedilizieallegatiList}"  var="commedilizieallegati_var">
			 		<tr>
						<td><a href="view.htm?codice=${commedilizieallegati_var.id.codice}">${commedilizieallegati_var.id.codice}</a></td>
						<td>${commedilizieallegati_var.descrizione}</td>
						<td>${commedilizieallegati_var.note}</td>
						<td><fmt:formatDate value="${commedilizieallegati_var.dataregistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td>
		
							<input type="checkbox" class="chk_pubblica"
									data-id-allegato="${commedilizieallegati_var.id.codice}" value="${commedilizieallegati_var.id.codice}"
									name="chk_pubblica" ${commedilizieallegati_var.flagPubblica?'checked':''} />
					       			<span id="result_${commedilizieallegati_var.id.codice}" style="display: none"></span>				
						</td>
						<td>
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
		   						<jsp:param name="idElemento" value="allegato${commedilizieallegati_var.id.codice}" />
		   						<jsp:param name="fileId" value="${commedilizieallegati_var.oggetti.id.codice}" />
		   						<jsp:param name="readonly" value="true" />
							</jsp:include>
							
							<span class="firme_allegati_presenti" data-id-allegato="${commedilizieallegati_var.id.codice}"><i class="fa fa-spinner fa-spin"></i></span>
							
						</td>
						<td>
							<a title="<fmt:message key="label.modifica"/>" class="modifica" href="view.htm?codice=${commedilizieallegati_var.id.codice}" >
							<i class="fa fa-pencil" aria-hidden="true"></i></a>
						</td>			
					</tr>						 		
		 		</c:forEach>
		 	</tbody>
		</table> 
		</fieldset>
	</div>
	
		<jsp:include page="snippetfirmaallegati.jsp" >
			<jsp:param name="idallegato" value="${commedilizieallegati_var.id.codice}" />
		</jsp:include>
	
	
	
	
	
	<script type="text/javascript">

	
	vbg.ready(() => {
		
	
		
			async function abilita(obj){														
					
					vbg.mostraModalCaricamento();
					
					const response = await fetch("../commedilizieallegati/ajaxPubblica.htm?codice="+escape(obj.value)+"&abilita="+obj.checked, {
		                method: "POST",
		                cache: "no-cache",
		                headers: {
		                    'Content-Type': 'application/json'
		                }
					});				
				
					
					let messagioDatoAggiornato = document.getElementById('result_'+ obj.getAttribute('data-id-allegato'));
					let messaggio = await response.text();
					messagioDatoAggiornato.innerHTML = messaggio;
					messagioDatoAggiornato.setAttribute("title", messaggio );
					messagioDatoAggiornato.style.display="inline-block";
					vbg.nascondiModalCaricamento();
					if (response.status === 200) {
						messagioDatoAggiornato.classList.remove("dato_aggiornamento_errore");
						messagioDatoAggiornato.classList.add("dato_aggiornato");
						setTimeout(()=>{ messagioDatoAggiornato.style.display="none"; }, 2000);
					}else{
						messagioDatoAggiornato.classList.remove("dato_aggiornato");
						messagioDatoAggiornato.classList.add("dato_aggiornamento_errore");
					}
			}		
			
			document.querySelectorAll('.chk_pubblica').forEach((item) => {
        		
        		item.addEventListener('click', () => {
        			
        			abilita(item);       			
        		});
        	});
		
	   }); 
		
	</script>

	</div>
	<div class="vbg-form">
		<div class="form-button">		
			<a class="btn btn-primary" href="javascript:doHref('create.htm?codiceCommissione=${commissioniedilizieT.id.codice}','');"><fmt:message key="button.new" /></a>
			<a class="btn btn-secondary" href="javascript:doHref('../commissioniediliziet/view.htm?codice=${commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a>		
		</div>
	</div>
</body>
</html>