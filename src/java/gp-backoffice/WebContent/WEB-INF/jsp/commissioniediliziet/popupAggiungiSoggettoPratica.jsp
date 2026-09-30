<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<vbg-modal id="vbg-modal-aggiungi-soggetto" data-id="">
	<div slot="body">
		<script type="text/javascript">
		</script>
		<h1>
			<fmt:message key="label.aggiungi_soggetti_a_commissione" />
		</h1>
		<div id="formAggiungiSoggettoPratica" class="vbg-form">
			<fieldset>
				<legend><fmt:message key="label.istanza" /></legend>
			    <div class="form-group">
			        <label><fmt:message key="label.istanza" /></label>
			        <div id="riferimentiistanza" class="readonly-form-control"></div>
			    </div>
			    <div class="form-group">
			    	<label><fmt:message key="label.protocollo" /></label>
			        <div id="riferimentiprotocollo" class="readonly-form-control"></div>
				</div>
			</fieldset>
			<div class="form-group">
				<table id="lista-soggetti" class="vbg-table">
					<thead>
						<tr>
							<th title='<fmt:message key="label.checkbox.selDeselAll" />'>
								<input type="checkbox" id="check_tutti" />
							</th>
							<th><fmt:message key="label.soggetto_istanza" /></th>
							<th><fmt:message key="label.qualifica_soggetto" /></th>
							<th><fmt:message key="label.carica"/> </th>						
						</tr>
					</thead>
					<tbody>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div slot="footer" class="vbg-modal-footer">
		<a id="aggiungiSoggetti" class="btn btn-primary"><fmt:message key="button.save" /></a>
		<div class="btn btn-secondary" data-role="close-vbg-modal" id="btnChiudiSoggetti_id"><fmt:message key="button.close"/></div> 		
	</div>
</vbg-modal>
<script type='text/javascript'>
	vbg.ready(() => {
		
		let aggiungiSoggetti = document.getElementById('aggiungiSoggetti');
		aggiungiSoggetti.addEventListener('click', async (e) => {
			
			let soggettipratica = [];
			let codici = [];
			let carica = [];			
			
			document.querySelectorAll('.riga-soggetti').forEach(
					x => {
						
						let idSoggetto ="";
						let codiceCarica = "";
						let soggettoPratica = x.querySelector('#vbg-modal-aggiungi-soggetto input[name=chksoggetto]').value;						
						if(x.querySelector('#vbg-modal-aggiungi-soggetto input[name=chksoggetto]:checked')){
							idSoggetto = x.querySelector('#vbg-modal-aggiungi-soggetto input[name=chksoggetto]:checked').value;
							if(x.querySelector('#vbg-modal-aggiungi-soggetto option:checked')){
								codiceCarica = x.querySelector('#vbg-modal-aggiungi-soggetto option:checked').value;
							}	
						}					
						soggettipratica.push(soggettoPratica);
						codici.push(idSoggetto);
						carica.push(codiceCarica);					
				});
			
			const data = new URLSearchParams();
			data.append('idRiga',parseInt(document.getElementById('vbg-modal-aggiungi-soggetto').dataset.id));
			data.append('anagrafiche',codici.toString());
			data.append('cariche', carica.toString());
			data.append('soggettipratica', soggettipratica.toString());
			
			window.vbg.mostraModalCaricamento();
			const response = await fetch("../commissioniediliziet/ajaxGestisciSoggettiIstanza.htm", {
	            method: "POST",
	            cache: "no-cache",
				body: data							
			}).then(response => {
				//In caso di status diverso da 200 mostrare l'errore
				
				window.vbg.nascondiModalCaricamento(); 
				document.querySelector('#vbg-modal-aggiungi-soggetto').close();
				if(response.status !== 200){
					response.text().then(function (text) {
						document.querySelector('#errore-eliminazione-soggetto-messaggio').innerHTML= text;
						document.querySelector('#errore-eliminazione-soggetto').open();
						
						});
					
				}
				
				
				
			 });
			
		});
		
		
		document.querySelector('#btnChiudiSoggetti_id').addEventListener('click', (e) => {
				document.getElementById('vbg-modal-aggiungi-soggetto').close();
		});
		
	});
</script>