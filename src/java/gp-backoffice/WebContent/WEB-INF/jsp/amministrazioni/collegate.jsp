<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message	key="label.amministrazioni_collegate" /></title>
<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>		
<style>
.amministrazione{

}
.comuni{

}
.disabilitato{

	text-decoration: line-through;
}

.fixed_header{
    width: 800px;
    table-layout: fixed;
    border-collapse: collapse;
}

.fixed_header tbody{
  display:block;
  width: 100%;
  overflow: auto;
  height: 400px;
}

.fixed_header thead tr {
   display: block;
}

.fixed_header thead {


}

.fixed_header th, .fixed_header td {
  padding: 5px;
  text-align: left;
  width: 200px;
}
</style>
</head>
<body>
<span class="titoloPagina"><init:editLabel key="label.amministrazioni_collegate" role="ROLE_EDITLABEL" />: 
	<i><b><c:out value="${amministrazioni.amministrazione}"/></b></i></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="file" />
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../amministrazioni/collegate"/>
	</jsp:include>

<div id="subcontent">

<spring-form:form commandName="amministrazioni" name="inviodati">
	
	<spring-form:hidden path="id.codice" />
<div id="form" class="vbg-form">

	<div class="form-button">
		<a class="btn btn-primary" href="javascript:nuovaAmministrazione();"><fmt:message key="label.nuovo" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('view.htm?codice=${param.codice }','')"><fmt:message key="button.back" /></a>
	</div>	


	<div id="amm-container">
	
	
	
	
	</div> 
				 	
				 	
	<div class="form-button" id="da_nascondere" style="display: none">
		<a class="btn btn-primary" href="javascript:nuovaAmministrazione();"><fmt:message key="label.nuovo" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('view.htm?codice=${param.codice }','')"><fmt:message key="button.back" /></a>
	</div>					 	
				 	
</div>

				 	
</spring-form:form>
	
	
	<vbg-modal id="scelta_amministrazione_id" >
		<div slot='body'>
			<h1>
                Scelta Nuova Amministrazione
             </h1>
             <p>
			<jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
						<jsp:param name="idElemento" value="nuova_amministrazione" />					
						<jsp:param name="pathPropertyDescription" value="amministrazione" />
						<jsp:param name="pathPropertyCode" value="amministrazione.codice" />
						<jsp:param name="autocompleterAjax" value="findAmministrazioniDaCollegare.htm?idAmministrazione=${param.codice}" />
						<jsp:param name="titleKey" value="label.ricerca_amministrazioni" />
						<jsp:param name="id_help" value="help_amministrazioni" />
				</jsp:include>
             </p> 
      
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="okAmministrazioneModal"><fmt:message key="button.ok"/> </div>
			<div class="btn btn-primary" id="closeAmministrazioneModal"><fmt:message key="button.close"/></div>            
		</div>
	</vbg-modal>
	
				

	
	
</div>
<script type="text/javascript">
	
	
		const container = document.querySelector('#amm-container');
		
		const idAmministrazioneMadre = ${param.codice};
		
		const modalNuovaAmministrazione = document.getElementById('scelta_amministrazione_id');
		const closeAmministrazioneModal  = document.getElementById('closeAmministrazioneModal');
		closeAmministrazioneModal.onclick = function() { 
			
			modalNuovaAmministrazione.close();
			
		};
		const okAmministrazioneModal  = document.getElementById('okAmministrazioneModal');	
		
		okAmministrazioneModal.onclick = function() { 
			
			let codiceSottoAmministrazione = document.getElementById('nuova_amministrazione_hidden').value;
			
			
			
			document.getElementById('nuova_amministrazione_hidden').value = '';
			document.getElementById('nuova_amministrazione_id').value = '';
			
			
			modalNuovaAmministrazione.close();
			
			assegnaComunePanelPerAmministrazione(codiceSottoAmministrazione);
			
		};
		
		const nuovaAmministrazione = () => {
			
			modalNuovaAmministrazione.open();
		}
		
		
		
		const getConfigurazioniTutteAmministrazioni = async () => {
			const response = await fetch('../amministrazioni/ajaxGetConfigurazioniTutteAmministrazioni.htm?idAmministrazione='+idAmministrazioneMadre);
		    return response.json();
		   	
		}
		
		

		
		const getConfigurazioniAmministrazione = async (codiceSottoAmministrazione) => {
			const response = await fetch('../amministrazioni/ajaxGetConfigurazioniAmministrazione.htm?idAmministrazione='+idAmministrazioneMadre+'&codiceSottoAmministrazione='+codiceSottoAmministrazione);
		   	return response.json();
		}

	    const inizializzaInterfaccia =  async () => {
	    	
	    		spinnerOn();
	    		
				let contenuto = await getConfigurazioniTutteAmministrazioni();				
				container.innerHTML = componiUI(contenuto);		
				
				spinnerOff();

	    };
	    
	    function spinnerOn(){
	    	 vbg.mostraModalCaricamento();
	    }
	    function spinnerOff(){
	    	 vbg.nascondiModalCaricamento();
	    	
	    }
	    
		function componiUI(contenuto){
			
			let uiProcessata = '';
			let visualizzaDaNascondere = false; 
			contenuto.each(amm =>{  
				visualizzaDaNascondere = true;
				uiProcessata += componiUIAmministrazione(amm);
			});
			if(visualizzaDaNascondere){
				document.getElementById('da_nascondere').style.display='';
			}
			return uiProcessata;
		}

	
	
	
	async function eliminaComune(elemento){
		
		if(confirm('Procedere con l\'eliminazione?')){
			spinnerOn();
			
			const formData = new FormData();
			
	        formData.append('idAmministrazione', idAmministrazioneMadre);
	        formData.append('codiceSottoAmministrazione', elemento.dataset.idamministrazionecollegata);
	        formData.append('codicecomune', elemento.dataset.codicecomune);
	        
			let response = await fetch("../amministrazioni/ajaxEliminaComune.htm" , {
	            method: "POST",
	            cache: "no-cache",
	            body: formData
	        });
			
			
			let amm = await getConfigurazioniAmministrazione(elemento.dataset.idamministrazionecollegata);	
			
			let id = 'comuni-content-' + elemento.dataset.idamministrazionecollegata;
			
			if(amm.comuni && amm.comuni.length>0){
				document.getElementById(id).innerHTML = componiUIComuni(amm);
			}else{
				eliminaAmministrazioneCollegata(elemento.dataset.idamministrazionecollegata);
			}
			
			spinnerOff();
		}
	}
	
	
	async function assegnaComune(elemento){
		
		spinnerOn();
		
		const formData = new FormData();
		
        formData.append('idAmministrazione', idAmministrazioneMadre);
        formData.append('codiceSottoAmministrazione', elemento.dataset.idamministrazionecollegata);
        formData.append('codicecomune', elemento.dataset.codicecomune);
        
		let response = await fetch("../amministrazioni/ajaxAssegnaComune.htm" , {
            method: "POST",
            cache: "no-cache",
            body: formData
        });
		
		elemento.parentNode.parentNode.classList.add('disabilitato');
		elemento.remove();
		
		let id = 'comuni-content-'+elemento.dataset.idamministrazionecollegata;
		
		let amm = await getConfigurazioniAmministrazione(elemento.dataset.idamministrazionecollegata);	
		
		let elementoDaAggiornare = document.getElementById(id);
		if(elementoDaAggiornare){
			elementoDaAggiornare.innerHTML = componiUIComuni(amm);
		}else{
			// nuovo inserimento aggiorno tutta la lista
			inizializzaInterfaccia();
		}
		spinnerOff();
	}
	
	
async function assegnaComunePanelPerAmministrazione(idamministrazionecollegata){
		
		spinnerOn();
		const formData = new FormData();
        formData.append('idAmministrazione', idAmministrazioneMadre);
       
        
		let response = await fetch("../amministrazioni/ajaxComuniDisponibili.htm" , {
            method: "POST",
            cache: "no-cache",
            body: formData
        });
		
		let listaComuni = await response.json();
		
		const { modalBody, modalFooter, modal } = initModal();
		
		if(listaComuni.comuni && listaComuni.comuni.length>0){			
			
			let codiceSottoAmministrazione = idamministrazionecollegata;
			let comuneContent = '';
			listaComuni.comuni.each(comune =>{ 			
				let linkElimina
				comuneContent +=  `<tr class="comune-ass-content" data-codice-comune="\${comune.codicecomune}">
										<td>\${comune.comune}</td>
										<td>\${comune.siglaprovincia}</td>
										<td>\${comune.regione}</td>
										<td><a href="javascript: void 0" onclick="assegnaComune(this)" 
												data-idamministrazionecollegata="\${codiceSottoAmministrazione}"
												data-codicecomune="\${comune.codicecomune}" 
												aria-hidden="true" 
												class="form-group fa fa-plus"><fmt:message key="label.assegna" /></a></td>
									</tr>`;
			
			});
			
			modalBody.innerHTML = `<table id="comuni-panel-table" class="vbg-table fixed_header" data-id-amministrazione="\${codiceSottoAmministrazione}">
					<thead>
					<tr>
						<th width="50%"><fmt:message key="label.comune" /></th>
						<th width="20%"><fmt:message key="label.provincia" /></th>
						<th width="20%"><fmt:message key="label.regione" /></th>
						<th width="10%"><input type="text" id="comuni-panel-filtro" size="20" 
							placeholder="Cerca" onkeyup="filtraTabella('comuni-panel-table',this)"/></th>
					<tr>
					</thead>
					<tbody>`
					+  comuneContent + 
					`</tbody>
					</table>
					`; 
					
		}else{
			modalBody.innerHTML = `Tutti i comuni sono stati assegnati`;
		}
		
		modal.open();
		spinnerOff();
	}
   
	async function assegnaComunePanel(elemento){
		
		assegnaComunePanelPerAmministrazione(elemento.dataset.idamministrazionecollegata);
		
	}
   
	
	function eliminaAmministrazioneCollegata(codiceSottoAmministrazione){
		
		const element = document.getElementById('amministrazione-content-'+codiceSottoAmministrazione);
		element.remove();
		
	}
	
	function componiUIAmministrazione(amm){
		
		let templateAmministrazione = `<fieldset id="amministrazione-content-\${amm.idAmministrazione}" class="amministrazione" data-codice-amministrazione="\${amm.idAmministrazione}">
										<legend >\${amm.amministrazione}</legend>
										<div id="comuni-content-\${amm.idAmministrazione}">
										@TEMPLATE_COMUNI@
										</div>
										</fieldset>`;
												
		let comuneContent = componiUIComuni(amm);
		
		return templateAmministrazione.replace('@TEMPLATE_COMUNI@',comuneContent);
	}
									
	function  componiUIComuni(amm){
		
		let comuneContent='';
		amm.comuni.each(comune =>{ 			
			let linkElimina
			comuneContent +=  `<tr class="comune-content" data-codice-comune="\${comune.codicecomune}">
									<td>\${comune.comune}</td>
									<td>\${comune.siglaprovincia}</td>
									<td>\${comune.regione}</td>
									<td><a href="javascript: void 0" onclick="eliminaComune(this)" 
											data-idamministrazionecollegata="\${amm.idAmministrazione}"
											data-codicecomune="\${comune.codicecomune}" 
											aria-hidden="true" 
											class="form-group fa fa-trash"><fmt:message key="label.elimina" /></a></td>
								</tr>`;
		
		});
		
		return `<table class="vbg-table" data-id-amministrazione="\${amm.idAmministrazione}">
				<thead>
				<tr>
					<th width="60%"><fmt:message key="label.comune" /></th>
					<th width="5%"><fmt:message key="label.provincia" /></th>
					<th width="20%"><fmt:message key="label.regione" /></th>
					<th width="15%"><fmt:message key="label.azioni" /></th>
				<tr>
				</thead>
				<tbody>` +  comuneContent + 
				`</tbody>
				</table>
				<a href="javascript: void 0" onclick="assegnaComunePanel(this)" 
					data-idamministrazionecollegata="\${amm.idAmministrazione}"
					aria-hidden="true" 
					class="form-group fa fa-plus"><fmt:message key="label.assegna" /></a>
				`; 
	}			
									
	
	function initModal() {
	    const modal = document.querySelector('#mod');
	    const modalBody = document.querySelector('#mod-body');
	    const modalFooter = document.querySelector('#vbg-modal-footer');
	    modalBody.innerHTML = "";
	    modalFooter.innerHTML = "";
	    return { modalBody, modalFooter, modal };
	}
	
	
	function filtraTabella(idTabella, input) {
		
		let filter, table, tr, td, i, txtValue;
		filter = input.value.toUpperCase();
		table = document.getElementById(idTabella);
		tr = table.getElementsByTagName("tr");
		let trovato = false;
		for (i = 1; i < tr.length; i++) {
		  td = tr[i].getElementsByTagName('td');
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
    	
	vbg.ready(() => {

	   
    inizializzaInterfaccia();

	});
									

</script>

	<vbg-modal id="mod" class="vbg-form">
	
        <div slot="body" id="mod-body" class="form-group">
        </div>
        <div slot="footer" id="vbg-modal-footer"></div>
    </vbg-modal>	 	

</body>
</html>