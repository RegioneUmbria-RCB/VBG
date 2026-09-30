<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><fmt:message key="label.firma_digitale_remota" /></title>
	<script type="text/javascript">
	vbg.ready(() => {
		
		let fieldSetParametri = document.querySelector("fieldset[name='parametri']");
		let fieldSetDocumenti = document.querySelector("fieldset[name='documenti']");
		let cbProvider = document.getElementById('id_provider');
		let btnChiudi = document.getElementById('chiudi_id');
		let btnFirma = document.getElementById('btnFirma');
		
		cbProvider.addEventListener('change',async (e) => {
			e.preventDefault();
			window.vbg.mostraModalCaricamento();
			try
			{
				let parametri = await recuperaParametri(e.target.value);
				aggiungiParametriAlForm(parametri);				
			}
			catch(error) {
				console.log(error);
			}
			window.vbg.nascondiModalCaricamento();
		});
		
		btnChiudi.addEventListener('click',async(e) => {
			e.preventDefault();
			await chiudi();
		});
		
		btnFirma.addEventListener('click',async (e) => {
			e.preventDefault();
			try {
				window.vbg.mostraModalCaricamento();
				
				let sessionId = fieldSetParametri.dataset.sessionid;

				if(sessionId === ''){
					let sessionid = await avviaProcesso();
					fieldSetParametri.dataset.sessionid = sessionid;
				}
				
				let status = await caricaDocumenti(cbProvider.value);
				if(status != 200){
					throw new Error('Si sono verificati errori durante il caricamento dei file da firmare');
				}
				status = await firmaDocumenti(cbProvider.value);
				if(status != 200){
					throw new Error('Si sono verificati errori durante la firma dei file');
				}
				
				e.target.style.display = 'none';
				if(document.getElementById('btnGeneraOTP')){
					document.getElementById('btnGeneraOTP').style.display = 'none';	
				}
				
				let statoAvanzamento = await verificaAvanzamento(cbProvider.value);
				await recuperaFileFirmati(cbProvider.value);

			} catch (e) {
				console.log(e.message);
				alert(e.message);
				e.target.style.display = '';
				let btnGeneraOTP = document.getElementById('btnGeneraOTP');
				if(btnGeneraOTP){
					btnGeneraOTP.style.display = '';	
				}

			} finally {
				window.vbg.nascondiModalCaricamento();	
			}
		});
		
		fieldSetParametri.classList.remove('collassato');
		fieldSetDocumenti.classList.remove('collassato');
	});
	
	async function recuperaFileFirmati(idConfigurazione){
		let ulDocumenti = document.querySelector("fieldset[name='documenti']>.form-group>ul");
		ulDocumenti.classList.add('fa-ul');
		ulDocumenti.querySelectorAll('li').forEach(async (li) => {
			try{
			
				let iStato = document.createElement('i');
				iStato.classList.add('fas');
				iStato.classList.add('fa-spinner');
				iStato.classList.add('fa-spin');
				li.insertBefore(iStato, li.firstChild);
				
				let request = {};
				request.sessionid = document.querySelector("fieldset[name='parametri']").dataset.sessionid;
				request.idConfigurazione = idConfigurazione;
				request.codiceOggetto = li.dataset.id;
				
				const postParams = { request };
				
				const response = await fetch('../firmadigitale2/jsonRecuperaFileFirmato.htm', {
	        		method: 'POST',
	                headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json',
	                },
	                body: JSON.stringify(postParams)
	        	});
				
				const jsResponse = await response.json();
				
				iStato.setAttribute('class', '');
				iStato.classList.add('fas');
				iStato.classList.add('fa-check');
				iStato.classList.add('fa-lg');

				let nomeFile = li.querySelector('.nome-file>b');
				nomeFile.textContent = '';
				nomeFile.textContent = jsResponse.response.nomefile;
				
				li.querySelectorAll('div.form-button>a.btn').forEach((btn) => {
					btn.style.display = 'none';
				});
				
			} catch(error) {
				console.log(error);
	            alert(error);
	        }
			finally{                                  
	            
	        }
		});
	}
	
	async function verificaAvanzamento(idConfigurazione){
		
		try{
			let request = {};
			request.sessionid = document.querySelector("fieldset[name='parametri']").dataset.sessionid;
			request.idConfigurazione = idConfigurazione;
			
			const postParams = { request };
			
			const response = await fetch('../firmadigitale2/jsonStatoAvanzamento.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
			const jsResponse = await response.json();
			
			return response;
			
		} catch(error) {
			console.log(error);
            alert(error);
        }
		finally{                                  
            
        }
	}
	
	async function firmaDocumenti(idConfigurazione){
		try
		{
			let request = {};
			request.sessionid = document.querySelector("fieldset[name='parametri']").dataset.sessionid;
			request.idConfigurazione = idConfigurazione;
			request.parametri = recuperaParametriDalForm();
			request.idoggetti = recuperaIdOggetti();
			
			const postParams = { request };
						
			const response = await fetch('../firmadigitale2/jsonFirmaDocumenti.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			return await response.status;
		}
		catch(error) {
			console.log(error);
            alert(error);
        }
		finally{                                  
            
        }
	}
	async function caricaDocumenti(idConfigurazione){
		try
		{
			let request = {};
			request.sessionid = document.querySelector("fieldset[name='parametri']").dataset.sessionid;
			request.idConfigurazione = idConfigurazione;
			request.idoggetti = recuperaIdOggetti();
			
			const postParams = { request };
			
			const response = await fetch('../firmadigitale2/jsonAggiungiDocumenti.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
			return await response.status;
			
		}
		catch(error) {
			console.log(error);
            alert(error);
        }
		finally{                                  
            
        }
	}
	
	async function recuperaParametri(idComponente){
		try
		{
			const postParams = { request: { idComponente } };
			
			const response = await fetch('../firmadigitale2/jsonRecuperaParametri.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
			const jsResponse = await response.json();
			
			return jsResponse.response.parametri;
			
		}
		catch(error) {
			console.log(error);
            alert(error);
        }
		finally{                                  
            
        }
	}
	
	async function chiudi(){
		
		try
		{
			const idoggetti = recuperaIdOggetti();
			
			const postParams = { request: { idoggetti } };
			
			const response = await fetch('../firmadigitale2/jsonSbloccaDocumenti.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
			const status = await response.status;
			
			if(status === 200){
				historyBack('');
			} else {
				throw new Error('Si sono verificati errori durante lo sblocco dei file');		
			}
		}
		catch(error) {
			console.log(error);
            alert(error);
        }
	}
	
	async function avviaProcesso(){
		
		let request = {};
		request.idConfigurazione = document.getElementById('id_provider').value;
		request.parametri = recuperaParametriDalForm();
		
		const postParams = { request };
		
		const response = await fetch('../firmadigitale2/jsonAvviaProcesso.htm', {
       		method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postParams)
       	});
		
		const status = await response.status;
		
		if(status === 200){
			const jsResponse = await response.json();
			return jsResponse.response.sessionid;
		} else {
			throw new Error('Si sono verificati errori durante l\'avvio del processo di firma!');
		}
	}
	
	function aggiungiParametriAlForm(parametri){
		let container = document.querySelector("fieldset[name='parametri']>span");
		container.innerHTML = '';
			
		parametri.forEach(par => {
			let div = document.createElement('div');
			div.classList.add("form-group");
			div.dataset.chiave = par.chiave;
			if(par.visibile == true){
				div.style.display = '';
			} else {
				div.style.display = 'none';
			}
			
			let label = document.createElement('label');
			label.innerText = par.etichetta;
			label.title = par.descrizione;
			div.appendChild(label);
			
			let parametro;
			
			switch(par.tipoCampo){
				
				case "LISTA": {
					parametro = document.createElement('select');
					
					let options = par.valore.split(',');

					options.forEach(opt => {
						if(par.chiave==='TIPO_FIRMA'){
							let aggiungiTipoFirma = true;
							if(opt === 'PADES' && 'false' === '${isPadesPossibile}'){
								aggiungiTipoFirma = false;
							}
							if(aggiungiTipoFirma){
								let option = document.createElement('option');					
								option.value = opt;
								option.text = option.value;
								parametro.appendChild(option);
							}
						}else{
							let option = document.createElement('option');					
							option.value = opt;
							option.text = option.value;
							parametro.appendChild(option);
						}
					
					});
			 		break;
				}
				case "PASSWORD": {
			 		parametro = document.createElement('input');
			 		parametro.type = 'password';
					parametro.value = par.valore ?? '';
					break;	
				}
			 	default: {
			 		parametro = document.createElement('input');
			 		parametro.type = 'text';
					parametro.value = par.valore ?? '';
					break;
				}
			}
			
			parametro.classList.add('parametrofirma');
			if(par.obbligatorio == true){
				parametro.classList.add('obbligatorio');
			}
			if(par.readOnly == true){
				parametro.disabled = true;
			}
			div.appendChild(parametro);
			
			if( par.chiave === 'OTP' ) {
							
				let spanPin = document.createElement('span');
				spanPin.classList.add('success_header');
				spanPin.classList.add('alert');
				spanPin.classList.add('alert-success');
				spanPin.style.color = 'var(--color-success)';
				spanPin.style.display = 'none';
				
				let btnPin = document.createElement('a');
				btnPin.id = 'btnGeneraOTP';
				btnPin.classList.add('btn');
				btnPin.classList.add('btn-primary');
				btnPin.appendChild(document.createTextNode("GENERA OTP"));
				btnPin.addEventListener('click', async (e) => {
					e.preventDefault();
					window.vbg.mostraModalCaricamento();
					let sessionid = await avviaProcesso();
					document.querySelector("fieldset[name='parametri']").dataset.sessionid = sessionid;
					spanPin.style.display = '';
					window.vbg.nascondiModalCaricamento();
				
				});
				div.appendChild(btnPin);
				div.appendChild(spanPin);
			}

			
			container.appendChild(div);
		});
	}
	
	function recuperaIdOggetti(){
		let documenti = document.querySelectorAll("fieldset[name='documenti']>div.form-group>ul>li");
		let idOggetti = [];
		documenti.forEach(doc => {
			idOggetti.push(doc.dataset.id);
		});
		
		return idOggetti;
	}
	
	function recuperaParametriDalForm() {
		let divParams = document.querySelectorAll("fieldset[name='parametri']>span>.form-group");
		let params = [];
		divParams.forEach(div => {
			let param = {};
			param.chiave = div.dataset.chiave;
			param.valore = div.querySelector(".parametrofirma").value;
			
			params.push(param);
		});
		return params;
	}
	
	function editDocs(codiceOggetto){
		location.href = '${pageContext.request.contextPath}/file/editDocApplication.htm?fileId=' + codiceOggetto;
	}
	
	function anteprimaPdf(codiceOggetto){
		location.href = '${pageContext.request.contextPath}/file/ajaxAnteprimaPdf.htm?fileId=' + codiceOggetto;
	}
	
	function caricaAnteprima(codiceOggetto, mimeType){
		
		let fieldset = document.querySelector('fieldset[name="anteprima"]');
		let container = fieldset.querySelector('div.form-group');
		let oldAnteprima = container.querySelector('object');
		if(oldAnteprima){
			container.removeChild(oldAnteprima);
		}
		
		const url = '${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=' + codiceOggetto + '&no_dialog=true';
 
		let newAnteprima = document.createElement('object');
		newAnteprima.setAttribute('data',url);
		newAnteprima.setAttribute('type',mimeType);
		newAnteprima.style.width = '100%';
		newAnteprima.style.height = '500px';
		
		container.appendChild(newAnteprima);
		
		fieldset.classList.remove('collassato');
	}
	
	function trasformaInPdf(codiceOggetto){
		
		let msg = '\tAttenzione! \nLa presente funzionalità crea \nun nuovo allegato convertito in PDF \na partire da quello selezionato. \n\tSi vuole procedere?';
		
			if(confirm(msg)){
			    var form = document.createElement("form");
			    
			    var coggetto = document.createElement("input"); 
			    var mAllaFirma = document.createElement("input");  
			    var iFirmaRe = document.createElement("input");
			    
			    form.method = "POST";
			    form.name = "frm-"+ Date.now();
			    form.action = "${pageContext.request.contextPath}/firmadigitale2/insertTrasformaInPdf.htm";   
	
			    coggetto.value=codiceOggetto;
			    coggetto.name="codiceOggetto";
			    form.appendChild(coggetto);  
			    
			    let cDaFirmare;
			    
			    <c:forEach items="${codiceoggettoArray}" var="o">
			    	
			    	cDaFirmare = document.createElement("input");
			    	cDaFirmare.value="${o}";
			    	cDaFirmare.name="codiciDocDaFirmareArray";
			    	form.appendChild(cDaFirmare);  
			    	
				</c:forEach>
			    
    
					
			    mAllaFirma.value="${isMettiAllafirma}";
			    mAllaFirma.name="mettiAllaFirma";
			    form.appendChild(mAllaFirma);
			    
			    iFirmaRe.value="true";
			    iFirmaRe.name="isFirmaRemota";
			    form.appendChild(iFirmaRe);
	
			    document.body.appendChild(form);
	
			    form.submit();
			}

	}

	</script>
</head>
<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.firma_digitale_remota" />
	</span>
	<c:if test="${not empty error }">
		<div class="error">${error }</div>
	</c:if>
	<div id="subcontent">
		<br />
		 <form name="paginaFirmaFrm"  action="${pageContext.request.contextPath}/firmadigitale2/insertTrasformaInPdf.htm&isFirmaRemota=true" method="post">
			
			<c:forEach items="${codiceoggettoArray}" var="o">
				<input type="hidden" name="codiciDocDaFirmareArray" value="${o}"/>
			</c:forEach>
			<div class="vbg-form">
				<fieldset class="collassabile" name="parametri" data-sessionid="">
					<legend><fmt:message key="label.parametri_firma_remota" /></legend>
					<div class="form-group">
						<label><fmt:message key="label.provider_firma" /></label>
						<spring-form:select path="providerFirma" id="id_provider">
	    		     		<spring-form:option value=""></spring-form:option>
	    		     		<c:forEach var="provider" items="${providerFirma}">
	    		     		    <spring-form:option value="${provider.chiave}">${provider.valore}</spring-form:option>
	    		     		</c:forEach>
	   		     		</spring-form:select>
					</div>
					<span class="form-group"></span>
					<div class="form-group">
						<c:set var="displayFirma"></c:set>
						<c:if test="${nascondiFirma eq true}">
							<c:set var="displayFirma"> style="display:none;" </c:set>
						</c:if>
						<a ${displayFirma} class="btn btn-primary" id="btnFirma"><fmt:message key="button.firma" /></a>
						<a class="btn btn-secondary" id="chiudi_id"><fmt:message key="button.back" /></a>
					</div>
				</fieldset>
				<fieldset class="collassabile" name="documenti">
					<legend><fmt:message key="firmadigitale.label.documentidafirmare" /></legend>
					<div class="form-group">
						<ul>
							<c:forEach items="${listaFilesDaFirmare}" var="documento">
								<li data-id="${documento.codice}">
									<span><fmt:message key="documentidafirmare.label.nome_file" /></span>
									<span class="nome-file"><b>${documento.descrizione}</b></span>
									<div class="form-button">
										<a class="btn btn-primary" href="javascript:editDocs('${documento.codice}');"><fmt:message key="label.modifica" /></a>
										<c:set var="display">display: none</c:set>
										<c:if test="${documento.mimeType !='application/pdf' && documento.mimeType != 'application/pkcs7-mime'}">
											<c:set var="display"></c:set>
										</c:if>
										<a class="btn btn-primary" href="javascript:caricaAnteprima(${documento.codice},'${documento.mimeType}');"><fmt:message key="label.visualizza" /></a>	
										<a style="${display}" class="btn btn-primary" href="javascript:anteprimaPdf(${documento.codice});"><fmt:message key="label.anteprima_pdf" /></a>
										<a style="${display}" class="btn btn-primary" href="javascript:trasformaInPdf(${documento.codice})"><fmt:message key="label.converti_in_pdf" /></a>
										
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</fieldset>
				<fieldset class="collassabile" name="anteprima">
					<legend><fmt:message key="label.firma_digitale_anteprima" /></legend>
					<div class="form-group"></div>
				</fieldset>
		 	</div>
		 </form> 
	</div>
</body>
</html>
