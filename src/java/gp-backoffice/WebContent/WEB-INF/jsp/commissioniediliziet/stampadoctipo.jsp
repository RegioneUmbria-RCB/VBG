<%@page import="java.net.URLEncoder"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<vbg-modal id="vbg-modal-stampe-id">
	<div slot='body'>
		<h1><fmt:message key="label.stampa" /></h1>
		<div>
			<fmt:message key="label.tipo_allegato_generare" />
			<jsp:include page="../includes/autocompletergenerico-no-bind-TT.jsp" >
					<jsp:param name="idElemento" value="letteretipo" />					
					<jsp:param name="pathPropertyDescription" value="commissioniediliziet.letteretipo.descrizione" />
					<jsp:param name="pathPropertyCode" value="commissioniediliziet.letteretipo.id.codice" />
					<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />
					<jsp:param name="titleKey" value="label.ricerca_documenti_tipo" />
					<jsp:param name="id_help" value="help_doc_tipo" />
			</jsp:include>
			<fmt:message key="help.ricerca_per_software_TT" />
		
		</div>
	</div>
	<div slot='footer'>
	  	<a class="btn btn-primary btn-stampa-popup">
             <fmt:message key="label.stampa" />
         </a>
         <div class="btn btn-secondary" data-role="close-vbg-modal" id="btnChiudiStampaDocTipo"><fmt:message key="button.close"/></div> 
	</div>
</vbg-modal>
<script type="text/javascript">
	vbg.ready(() => {
		
		let btnStampa = document.querySelector('.btn-stampa-popup');
		btnStampa.addEventListener('click', (e) => {
			e.preventDefault();
			stampaDocCommissione();
		});

		let btnStampaDocTipo = document.querySelectorAll('*[data-role="close-vbg-modal"]').forEach(
			x => x.addEventListener('click', (e) => {
			document.getElementById('vbg-modal-stampe-id').close();
		}));

	 });
	
	function stampaDocCommissione(){
		
		var codiceLettera = document.querySelector("#letteretipo_hidden").value;
		if(codiceLettera!=''){
			document.location.href='../commissioniediliziet/createLetteraTipo.htm?codiceCommissione=${param.codicecommissione}&codiceLettera=' + codiceLettera;			
		}		
	}
</script>