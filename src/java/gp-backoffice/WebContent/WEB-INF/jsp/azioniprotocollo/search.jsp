search.jsp
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.azioni_protocollo" /></title>
	<style type="text/css">
		.configurazione {
			color: #669900;
			cursor: copy;
		}
		
		.selezionata {
			background-color: #E5FACA;
		}
		
		.div-amministrazione {
			flex: 1;
			margin: auto;
		}
		
		.label-amministrazione {
			display: inline-block;
			width: var(--label-max-width);
		}
	</style>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="label.azioni_protocollo" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>



	<div id="subcontent">
		<spring-form:form commandName="azioniProtocollazioneCommand"
			name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="azioniProtocollazioneCommand" />
			</jsp:include>
			<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../azioniprotocollo/search" />
			</jsp:include>
			<div id="form" class="vbg-form">
				<fieldset>
					<legend><fmt:message key="azioniprotocollo.dati_generali.label" /></legend>
					<div class="form-group">
						<label><fmt:message key="label.numero_protocollo" /></label>
						<spring-form:input id="numero_id" path="datiProtocollo.numeroProtocollo" size="7" cssStyle="text-align: right"/>
					</div>
					<div class="form-group">
						<label><fmt:message key="label.anno" /></label>
						<spring-form:input id="anno_id" path="datiProtocollo.annoProtocollo" size="7" cssStyle="text-align: right"/>
					</div>
				</fieldset>
				<fieldset>
					<legend><fmt:message key="label.scelta_configurazioni_protocollo" /></legend>
					<div class="form-group">
						<ul>
							<c:forEach items="${configurazioniVerticalizzazionis}" var="cfg">							
								<li 
									data-comune="${cfg.comune.codicecomune}" 
									data-software="${cfg.software.codice}"
									style="display: flex">
									<div name="configurazione" style="flex: 1; margin: auto;">
										<fmt:message key="label.comune" />: ${cfg.comune.comune}, <fmt:message key="label.modulo" />: ${cfg.software.descrizione}
									</div>
								</li>							
							</c:forEach>
						</ul>
						<input type="hidden" id="codicecomune_id" name="comune.codicecomune" />
						<input type="hidden" id="softwarecodice_id" name="protSoftware.codice"  />
						<input type="hidden" id="uo_id" name="uo" />
						<input type="hidden" id="ruolo_id" name="ruolo"  />
					</div>
				</fieldset>
			</div>
		</spring-form:form>
	</div>
	<script type="text/javascript">
	vbg.ready(() => {
		
		const configurazioni = document.querySelectorAll('[name="configurazione"]');
		if(configurazioni)
		{
			configurazioni.forEach(config => {
				config.parentElement.classList.add('configurazione');
				config.addEventListener('click',async (e) =>{
					e.stopPropagation();
					e.preventDefault();
					
					disableFunctions();
					
					let codComune = e.target.parentElement.dataset.comune;
					let codSoftware = e.target.parentElement.dataset.software;
					
					await scegliConfigurazione(codComune,codSoftware);
					
					enableFunctions();

				});
			});
			
			if(configurazioni.length == 1){
				configurazioni[0].click();
			}
		}
		
		const btnLeggi = document.querySelector('[name="leggiprotocollo"]');
		btnLeggi.addEventListener('click', async (e) => {
			if (document.querySelector('#numero_id').value == '') {
				alert('<fmt:message key="label.numero_protocollo" /> <fmt:message key="alert.required" />');
				return;
			}
			if (document.querySelector('#anno_id').value == '') {
				alert('<fmt:message key="label.anno" /> <fmt:message key="alert.required" />');
				return;
			}

			try
			{
	        	await fetch('../history/ajaxSet.htm?ReturnTo=${_urlback}', {
	        		method: 'GET'
	        	});
	        	
	        	doSubmit('view.htm', '', document.inviodati);
			} 
			catch(error) {                                    
	            alert(error);
	        }
			finally{                                  
	            
	        }
		});
		
		const btnChiudi = document.querySelector('[name="chiudi"]');
		btnChiudi.addEventListener('click', (e) => {
			historyBack();
		});		
	});
	
	async function recuperaAmministrazioni(codiceComune,software ){
		try
		{
			const postParams = { request: { codiceComune, software } };
			
        	const response = await fetch('../azioniprotocollo/jsonFindAmministrazioniProtocollo.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
        	
        	const jsResponse = await response.json();

        	return jsResponse.dettaglio.amministrazioni;
		} 
		catch(error) {                                    
            alert(error);
        }
		finally{                                  
            
        }
	}
	
	async function renderAmministrazione(codiceComune,software){
		
		let amministrazioni = await recuperaAmministrazioni(codiceComune,software);
		if( amministrazioni.length == 0){
			return null;
		}
		
		let div = document.createElement('div');
			div.classList.add('div-amministrazione');
			div.setAttribute('name','amministrazione');
			
		let label = document.createElement('label');
			label.classList.add('label-amministrazione');
			label.appendChild(document.createTextNode("<fmt:message key='label.amministrazione' />"));
		
		let select = document.createElement('select');
			select.addEventListener('change', (e) => {
				
				document.querySelector('[name="uo"]').value = e.target.options[e.target.selectedIndex].dataset.uo;
				document.querySelector('[name="ruolo"]').value = e.target.options[e.target.selectedIndex].dataset.ruolo;
			});
		
		let emptyOpt = document.createElement('option');
			emptyOpt.value = '';
			emptyOpt.dataset.uo = '';
			emptyOpt.dataset.ruolo = '';
			emptyOpt.text = '';
		
		select.appendChild(emptyOpt);
			
		for (var amministrazione of amministrazioni) {
			let option = document.createElement('option');
			option.value = amministrazione.codice;
			option.dataset.uo = ( amministrazione.uo ) ? amministrazione.uo : '';
			option.dataset.ruolo = ( amministrazione.ruolo ) ? amministrazione.ruolo : '';
			option.text = amministrazione.descrizione;
			select.appendChild(option);		
		}
				
		div.appendChild(label);
		div.appendChild(select);
				
		return div;
	}
	
	
	async function scegliConfigurazione(codiceComune,software){
		document.querySelector('#codicecomune_id').value = codiceComune;
		document.querySelector('#softwarecodice_id').value  = software;
		
		const precedente = document.querySelector('.configurazione.selezionata');
		if(precedente != null){
			precedente.classList.remove('selezionata');
			let amministrazione = precedente.querySelector('[name="amministrazione"]');
			if(amministrazione != null){
				precedente.removeChild(amministrazione);
			}
		}
		
		
		
		const attuale = document.querySelector("[data-comune='" + codiceComune + "'][data-software='" + software + "']");
		if(attuale != null && attuale != precedente){
			attuale.classList.add('selezionata');
			
			let amm = await renderAmministrazione(codiceComune,software);
			if( amm != null ){
				attuale.appendChild(amm);	
			}
		}
	}
	</script>

	<div class="form-button">
		<a class="btn btn-primary" name="leggiprotocollo"><fmt:message key="button.ok" /></a>
		<a class="btn btn-secondary" name="chiudi"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>