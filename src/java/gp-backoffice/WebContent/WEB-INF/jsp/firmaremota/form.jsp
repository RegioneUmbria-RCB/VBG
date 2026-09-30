<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="firmaremota.label.form.title" />
	</title>
	<script type="text/javascript">
	vbg.ready(() => {
		let firmaId = '${firmaremota.id}';
		let btnInsert      = document.getElementById('btnInsert');
		let btnUpdate      = document.getElementById('btnUpdate');
		let btnRecuperaPar = document.getElementById('btnRecuperaPar');
		let btnDelete      = document.getElementById('btnDelete');
		let btnClose       = document.getElementById('btnClose');
		
		let popup        = document.querySelector("#popup");
		let btnClosePar  = popup.querySelector('#btnClosePar');
		let btnUpdatePar = popup.querySelector('#btnUpdatePar');
		
		let btnOrdineSu = document.querySelectorAll('i.fa-caret-square-up');
		let btnOrdineGiu = document.querySelectorAll('i.fa-caret-square-down');
		
		
		let hrefModifica = document.querySelectorAll("#parametri>table.vbg-table>tbody>tr>td.azioni>ul>li>a.modifica");
		let hrefElimina = document.querySelectorAll("#parametri>table.vbg-table>tbody>tr>td.azioni>ul>li>a.elimina");
		
		btnInsert.addEventListener('click',(e) => {
			e.preventDefault();
			if(_validaForm()){
				doSubmit('insert.htm','Procedere con l\'inserimento?',document.inviodati);	
			} else {
				alert('Dati obbligatori mancanti');
			}
		});
		
		btnUpdate.addEventListener('click',(e) => {
			e.preventDefault();
			window.vbg.mostraModalCaricamento();
			if(_validaForm()){
				ricalcolaIndiciParametri();
				doSubmit('update.htm?codice='+firmaId,'Procedere con l\'aggiornamento?',document.inviodati);	
			} else {
				alert('Dati obbligatori mancanti');
			}
			window.vbg.nascondiModalCaricamento();
		});
		
		btnRecuperaPar.addEventListener('click',async (e) => {
			e.preventDefault();
			window.vbg.mostraModalCaricamento();
			let nomeComponente = document.getElementById('provider_id').value;
			let endpoint = document.getElementById('endpoint_id').value;
			await _recuperaParametri(nomeComponente,endpoint);
			window.vbg.nascondiModalCaricamento();
		});
		
		btnDelete.addEventListener('click', (e) => {
			e.preventDefault();
			doSubmit('delete.htm?codice='+firmaId,'Procedere con la cancellazione dell\'integrazione configurata?',document.inviodati);
		});
		
		btnClose.addEventListener('click',(e) => {
			e.preventDefault();
			let procedi = true;
			if ( modificheNonSalvatePresenti() ){
				procedi = confirm("Sono presenti modifiche non salvate. Uscendo senza salvare verranno perse, continuare?");
			}
			if(procedi === true){
				doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','');	
			}
			
		});
		
		btnClosePar.addEventListener('click',(e) => {
			e.preventDefault();
			popup.hide();
		});
		
		btnUpdatePar.addEventListener('click',(e) => {
			e.preventDefault();
			if( _validaPopup(popup) ){
				_aggiornaParametro(popup);
				modificaNonSalvata();
				popup.hide();
			}
		});
		
		if(firmaId === ''){
			btnInsert.style.display = '';
			btnUpdate.style.display = 'none';
			btnDelete.style.display = 'none';
			btnRecuperaPar.style.display = 'none';
		} else {
			btnInsert.style.display = 'none';
			btnUpdate.style.display = '';
			btnDelete.style.display = '';
			btnRecuperaPar.style.display = '';
		}
		
		document.querySelectorAll('.error').forEach(errMsg => {
			errMsg.parentElement.children[1].addEventListener('change',(e) => {
				e.preventDefault();
				modificaNonSalvata();
				_validaForm();
			});
		});
		
		document.getElementById('attiva_id').addEventListener('change',(e) => {
			e.preventDefault();
			modificaNonSalvata();
		});
		
		hrefModifica.forEach(href => {
			href.addEventListener('click',(e) => {
				e.preventDefault();
				window.vbg.mostraModalCaricamento();
				_modificaParametro(e.target);
				window.vbg.nascondiModalCaricamento();
			});
		});
		
		hrefElimina.forEach(href => {
			href.addEventListener('click',(e) => {
				e.preventDefault();
				window.vbg.mostraModalCaricamento();
				eliminaParametro(e.target);
				modificaNonSalvata();
				window.vbg.nascondiModalCaricamento();	
			});
		});
		
		btnOrdineSu.forEach(btn => {
			btn.addEventListener('click',(e) => {
				e.preventDefault();
				
				let tr = e.target;
				while(tr.nodeName.toLowerCase() != 'tr'){
					tr = tr.parentNode;
				}
				
				let curIdx = parseInt(tr.dataset.idx);
				let newIdx = curIdx - 1;
				
				cambiaOrdineParametro(tr.parentNode,newIdx,curIdx);
			});	
		});
				
		btnOrdineGiu.forEach(btn => {
			btn.addEventListener('click',(e) => {
				e.preventDefault();
				
				let tr = e.target;
				while(tr.nodeName.toLowerCase() != 'tr'){
					tr = tr.parentNode;
				}
				
				let curIdx = parseInt(tr.dataset.idx);
				let newIdx = curIdx + 1;
				
				cambiaOrdineParametro(tr.parentNode,curIdx,newIdx);
				
			});	
		});
		
		_validaForm();
	});
	
	function cambiaOrdineParametro(tbody,curIdx,newIdx){
		var element1 = tbody.querySelector('tr[data-idx="'+curIdx+'"]');
		var element2 = tbody.querySelector('tr[data-idx="'+newIdx+'"]');
		
		if(element1 == null || element2 == null ){
			return;
		}
		
		element1.dataset.idx = newIdx;
		element2.dataset.idx = curIdx;
		
		tbody.insertBefore(element2,element1);
		
		ricalcolaIndiciParametri();
		
		modificaNonSalvata();
	}
	
	function ricalcolaIndiciParametri(){
		let parametri = document.querySelectorAll('#parametri>table>tbody>tr');
		let index = 0;
				
		parametri.forEach(parametro => {
			parametro.querySelectorAll('td>input[type="hidden"]').forEach(input => {
				
				let s = input.name.split(']');
				input.name = 'parametri[' + index + ']' + s[1];
				if(s[1]==='.ordine'){
					input.value = index;
				}
			});
			index++;
		});
	}
	
	function eliminaParametro(targetHref){
		
		let tr = targetHref;
		while(tr.nodeName.toLowerCase() != 'tr'){
			tr = tr.parentNode;
		}
		
		tr.remove();
	}
	
	function _modificaParametro(targetHref){
		let tr = targetHref;
		while(tr.nodeName.toLowerCase() != 'tr'){
			tr = tr.parentNode;
		}
		
		let popup = document.querySelector("#popup");
		
		popup.querySelector('#chiave').value = tr.querySelector('td.chiave>input[type="hidden"]').value;
		popup.querySelector('#chiave').disabled = true;
		popup.querySelector('#descrizione').value = tr.querySelector('td.descrizione>input[type="hidden"]').value;
		popup.querySelector('#obbligatorio').checked = ( tr.querySelector('td.obbligatorio>input[type="hidden"]').value === 'true');
		popup.querySelector('#visibile').checked = ( tr.querySelector('td.visibile>input[type="hidden"]').value === 'true');
		popup.querySelector('#readOnly').checked = ( tr.querySelector('td.readOnly>input[type="hidden"]').value === 'true');
		popup.querySelector('#tipoCampo').value = tr.querySelector('td.tipoCampo>input[type="hidden"]').value;
		popup.querySelector('#valoreDefault').value = tr.querySelector('td.valoreDefault>input[type="hidden"]').value;
		
		console.log(tr.querySelector('td.tipoCampo>input[type="hidden"]').value);
		
		if(tr.querySelector('td.tipoCampo>input[type="hidden"]').value === 'PASSWORD') {
			popup.querySelector('#valoreDefault').type = 'password';
		} else {
			popup.querySelector('#valoreDefault').type = 'text';
		}
		
		btnUpdatePar.style.display = '';
		
		_validaPopup(popup);
		
		popup.show();
	}
	
	async function _recuperaParametri(nomeComponente, endpoint){
		try
		{
			
			const postParams = { request: { nomeComponente, endpoint } };
			
			const response = await fetch('../firmaremota/jsonRecuperaParametri.htm', {
        		method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postParams)
        	});
			
			const jsResponse = await response.json();
			
			let parametri = jsResponse.response.parametri;
			parametri.forEach(par => {
				
				let parametroPresente = document.querySelector('fieldset>table.vbg-table>tbody>tr>td.chiave>input[value="' + par.chiave + '"]');

				if( parametroPresente != null ){
					return;
				}
				
				let tbody = document.querySelector('fieldset>table.vbg-table>tbody');
				let rowIdx = tbody.rows.length;
				
				let tr = document.createElement('tr');
				tr.dataset.idx = rowIdx;
				tbody.appendChild(tr);
				
				tr.innerHTML = '';
				
				tr.appendChild(_creaTdOrdinamento('parametri['+rowIdx+'].ordine',par.ordine,'ordine'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].chiave',par.chiave,par.chiave,'chiave'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].descrizione',par.descrizione,par.descrizione,'descrizione'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].obbligatorio',par.obbligatorio,par.obbligatorio === true ? 'SI' : 'NO','obbligatorio'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].visibile',true,'SI','visibile'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].readOnly',false,'NO','readOnly'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].tipoCampo','TESTO','TESTO','tipoCampo'));
				tr.appendChild(_creaTdParametro('parametri['+rowIdx+'].valoreDefault',par.valore,par.valore,'valoreDefault'));
				tr.appendChild(_creaTdAzioni());
				
				modificaNonSalvata();
			});
			
			ricalcolaIndiciParametri();
		}
		catch(error) {
			console.log(error);
            alert('Si sono verificati errori durante il recupero dei parametri');
        }
		finally{                                  
            
        }
	}
	
	
	
	function _validaForm(){
		let retVal = true;
		document.querySelectorAll('#datigenerali>.form-group>.error').forEach(errMsg => {
			if( errMsg.parentElement.children[1].value != '' ){
				errMsg.style.display = 'none';
			} else {
				retVal = false;
				errMsg.style.display = '';
			}
		});
		
		return retVal;
	}
	
	function _validaPopup(popup){
		let retVal = true;
		popup.querySelectorAll('div.vbg-modal-body>div.validate-required').forEach(div => {
			if( div.children[1].value != '' ){
				div.querySelector('.validation-feedback').style.display = 'none';
			} else {
				retVal = false;
				
				div.querySelector('.validation-feedback').style.display = '';
			}
		});
		return retVal;
	}
	

	function _aggiornaParametro(popup){
		let chiave = popup.querySelector('#chiave').value;
		document.querySelectorAll('fieldset>table.vbg-table>tbody>tr>td.chiave>input[type="hidden"]').forEach(input => {
			if(input.value === chiave ){
				let tr = input.parentNode.parentNode;
				_aggiungiColonneParametri(popup,tr);
			}			
		});
	}
	
	function _aggiungiColonneParametri(popup, tr){
		let idx = tr.dataset.idx;
		
		tr.innerHTML = '';
		let valore;
		let valoreVisualizzato;
		let checked;
		let nomeHidden;
		
		
		
		//1. Ordinamento
		tr.appendChild(_creaTdOrdinamento('parametri['+idx+'].ordine',idx,'ordine'));
		
		//2. Chiave
		valore = popup.querySelector('#chiave').value;
		valoreVisualizzato = valore;
		nomeHidden = 'parametri['+idx+'].chiave';
		tr.appendChild(_creaTdParametro(nomeHidden,valore,valoreVisualizzato,'chiave'));
		
		//3. Descrizione
		valore = popup.querySelector('#descrizione').value;
		valoreVisualizzato = valore;
		nomeHidden = 'parametri['+idx+'].descrizione';
		tr.appendChild(_creaTdParametro(nomeHidden,valore,valoreVisualizzato,'descrizione'));
		
		//4. Obbligatorio
		checked = popup.querySelector('#obbligatorio').checked;
		valoreVisualizzato = checked ? 'SI' : 'NO';
		nomeHidden = 'parametri['+idx+'].obbligatorio';
		tr.appendChild(_creaTdParametro(nomeHidden,checked,valoreVisualizzato,'obbligatorio'));
		
		//5. Visibile
		checked = popup.querySelector('#visibile').checked;
		valoreVisualizzato = checked ? 'SI' : 'NO';
		nomeHidden = 'parametri['+idx+'].visibile';
		tr.appendChild(_creaTdParametro(nomeHidden,checked,valoreVisualizzato,'visibile'));
		
		//6. Readonly
		checked = popup.querySelector('#readOnly').checked;
		valoreVisualizzato = checked ? 'SI' : 'NO';
		nomeHidden = 'parametri['+idx+'].readOnly';
		tr.appendChild(_creaTdParametro(nomeHidden,checked,valoreVisualizzato,'readOnly'));
		
		//7. Tipo campo
		valoreTipoCampo = popup.querySelector('#tipoCampo').value;
		valoreVisualizzato = valoreTipoCampo;
		nomeHidden = 'parametri['+idx+'].tipoCampo';
		tr.appendChild(_creaTdParametro(nomeHidden,valoreTipoCampo,valoreVisualizzato,'tipoCampo'));
		
		//8. Valore default
		valore = popup.querySelector('#valoreDefault').value;
		valoreVisualizzato = (valoreTipoCampo === 'PASSWORD') ? '********' : valore;
		nomeHidden = 'parametri['+idx+'].valoreDefault';
		tr.appendChild(_creaTdParametro(nomeHidden,valore,valoreVisualizzato,'valoreDefault'));
		
		//9. Azioni
		tr.appendChild(_creaTdAzioni());
	}
	
	function _creaTdAzioni(){
		let tdAzioni = document.createElement('td');
		tdAzioni.classList.add('azioni');
		
		let ulAzioni = document.createElement('ul');
		tdAzioni.appendChild(ulAzioni);
		
		let liModifica = document.createElement('li');
		ulAzioni.appendChild(liModifica);
		
		let hrefModifica = document.createElement('a');
		hrefModifica.classList.add('modifica');
		liModifica.appendChild(hrefModifica);
		
		let iModifica = document.createElement('i');
		iModifica.classList.add('fa');
		iModifica.classList.add('fa-pencil');
		hrefModifica.appendChild(iModifica);
		hrefModifica.appendChild(document.createTextNode(' Modifica'));
		hrefModifica.addEventListener('click',(e) => {
			e.preventDefault();
			window.vbg.mostraModalCaricamento();
			_modificaParametro(e.target);
			window.vbg.nascondiModalCaricamento();
		});
		
		
		let liElimina = document.createElement('li');
		ulAzioni.appendChild(liElimina);
		
		let hrefElimina = document.createElement('a');
		hrefElimina.classList.add('elimina');
		liElimina.appendChild(hrefElimina);
		
		let iElimina = document.createElement('i');
		iElimina.classList.add('fa');
		iElimina.classList.add('fa-trash');
		hrefElimina.appendChild(iElimina);
		hrefElimina.appendChild(document.createTextNode(' Elimina'));
		hrefElimina.addEventListener('click', (e) => {
			e.preventDefault();
			window.vbg.mostraModalCaricamento();
			modificaNonSalvata();
			console.log(e);
			window.vbg.nascondiModalCaricamento();
		});
		
		return tdAzioni;
	}
	
	
	function _creaTdParametro(nomeHidden,valore,valoreVisualizzato,classe){
		
		let hidden = document.createElement('input');
		hidden.type = 'hidden';
		hidden.name = nomeHidden;
		hidden.value = valore;
		
		let span = document.createElement('span');
		span.innerText = valoreVisualizzato;
		
		let td = document.createElement('td');
		td.classList.add(classe);
		td.appendChild(hidden);
		td.appendChild(span);
		return td;
	}
	
	function _creaTdOrdinamento(nomeHidden,valore,classe){
		let hidden = document.createElement('input');
		hidden.type = 'hidden';
		hidden.name = nomeHidden;
		hidden.value = valore;
		
		let iSquareUp = document.createElement('i');
		iSquareUp.classList.add('far');
		iSquareUp.classList.add('fa-2x');
		iSquareUp.classList.add('fa-caret-square-up');
		iSquareUp.style.color = 'var(--accent-color)';
		iSquareUp.style.cursor = 'pointer';
		iSquareUp.addEventListener('click',(e) => {
			e.preventDefault();
			
			let tr = e.target;
			while(tr.nodeName.toLowerCase() != 'tr'){
				tr = tr.parentNode;
			}
			
			let curIdx = parseInt(tr.dataset.idx);
			let newIdx = curIdx - 1;
			
			cambiaOrdineParametro(tr.parentNode,newIdx,curIdx);
		});
		
		let iSquareDown = document.createElement('i');
		iSquareDown.classList.add('far');
		iSquareDown.classList.add('fa-2x');
		iSquareDown.classList.add('fa-caret-square-down');
		iSquareDown.style.color = 'var(--accent-color)';
		iSquareDown.style.cursor = 'pointer';
		iSquareDown.addEventListener('click',(e) => {
			e.preventDefault();
			
			let tr = e.target;
			while(tr.nodeName.toLowerCase() != 'tr'){
				tr = tr.parentNode;
			}
			
			let curIdx = parseInt(tr.dataset.idx);
			let newIdx = curIdx + 1;
			
			cambiaOrdineParametro(tr.parentNode,curIdx,newIdx);
		});

		let td = document.createElement('td');
		td.classList.add(classe);
		td.appendChild(hidden);
		td.appendChild(iSquareUp);
		td.appendChild(iSquareDown);
		return td;
	}
	
	function modificaNonSalvata(){
		let txtModifica = document.querySelector('input[name="modifica-non-salvata"]');
		txtModifica.value = 'Sono presenti modifiche non ancora salvate';
		document.querySelector('div.global_messages').style.display = 'none';
	}
	
	function modificheNonSalvatePresenti(){
		return document.querySelector('input[name="modifica-non-salvata"]').value != '';
	}
	
	</script>
	<style>
		.azioni > ul {
		    list-style-type:none;
		    padding:0px;
		    margin:0px;
		    white-space: nowrap;
		}
		.azioni > ul > li > a {
			cursor: pointer;
		}
		.modifiche-non-salvate {
			width: 100%;
    		border-style: none;
    		color: var(--accent-color);
    		font-weight: bold;	
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="firmaremota.label.form.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="firmaremota" name="inviodati">
			<input type="text" name="modifica-non-salvata" value="" class="modifiche-non-salvate"></input>
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="firmaremota" />
		    </jsp:include>
		    <jsp:include page="../includes/history.jsp">
	            <jsp:param name="path" value="../firmaremota/view" />
	        </jsp:include>
	        <div id="form" class="vbg-form">
	        	<fieldset id="datigenerali">
	        		<legend><fmt:message key="firmaremota.label.form.datigenerali" /></legend>
	        		<div class="form-group">
	        			<label><fmt:message key="firmaremota.label.form.descrizione" /></label>
	        			<spring-form:input id="descrizione_id" path="descrizione" size="25" maxlength="20" />
	        			<div id="errore_descrizione_id" class="error validation-feedback" ><fmt:message key="firmaremota.label.errore.descrizione.required" /></div>
	        		</div>
	        		<div class="form-group">
						<label><fmt:message key="firmaremota.label.form.provider" /></label>
				 		<spring-form:select id="provider_id" path="providerName">
							<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
							<spring-form:options items="${providers}" itemValue="chiave" itemLabel="descrizione"></spring-form:options>
						</spring-form:select>	
				 		<div id="errore_provider_id" class="error validation-feedback" ><fmt:message key="firmaremota.label.errore.provider.required" /></div>
	        		</div>
					<div class="form-group">
	        			<label><fmt:message key="firmaremota.label.form.endpoint" /></label>
	        			<spring-form:input id="endpoint_id" path="endpoint" size="100" maxlength="100" />
	        			<div id="errore_endpoint_id" class="error validation-feedback" ><fmt:message key="firmaremota.label.errore.endpoint.required" /></div>
	        		</div>
					<div class="form-group">
	        			<label><fmt:message key="firmaremota.label.form.attiva" /></label>
	        			<spring-form:checkbox id="attiva_id" path="attiva" />
	        		</div>
	        		<div class="form-button">
        				<a id="btnInsert" class="btn btn-primary"><fmt:message key="button.insert" /></a>
	        			<a id="btnUpdate" class="btn btn-primary" ><fmt:message key="button.update" /></a>
	        			<a id="btnRecuperaPar" class="btn btn-primary" ><fmt:message key="button.recuperaparametri" /></a>
	        			<a id="btnDelete" class="btn btn-primary"><fmt:message key="button.delete" /></a>
	        			<a id="btnClose"  class="btn btn-secondary"><fmt:message key="button.back" /></a>
	        		</div>
	        	</fieldset>
	        	<fieldset id="parametri">
	        		<legend><fmt:message key="firmaremota.label.form.parametri" /></legend>
	        		<table class="vbg-table">
	        			<thead>
	        				<tr>
	        					<th width="5%"><fmt:message key="label.ordine"/></th>
	        					<th width="5%"><fmt:message key="firmaremota.label.parametri.chiave"/></th>
	        					<th width="29%"><fmt:message key="firmaremota.label.parametri.descrizione"/></th>
	        					<th width="7%"><fmt:message key="firmaremota.label.parametri.obbligatorio"/></th>
	        					<th width="7%"><fmt:message key="firmaremota.label.parametri.visibile"/></th>
	        					<th width="7%"><fmt:message key="firmaremota.label.parametri.readonly"/></th>
	        					<th width="15%"><fmt:message key="firmaremota.label.parametri.tipocampo"/></th>
	        					<th width="20%"><fmt:message key="firmaremota.label.parametri.valoredefault"/></th>
	        					<th width="5%"><fmt:message key="label.azioni"/></th>
	        				</tr>
	        			</thead>
	        			<tbody>
	        				<c:forEach items="${firmaremota.parametri}" var="parametro" varStatus="a">
		        				<tr data-idx="${a.index}">
		        					<td>
		        						<spring:bind path="firmaremota.parametri[${a.index}].ordine">
		        							<input type="hidden" name="${status.expression}" value="${status.value}"></input>
		        						</spring:bind>
		        						<i class="far fa-2x fa-caret-square-up" style="color: var(--accent-color); cursor: pointer;" ></i>
		        						<i class="far fa-2x fa-caret-square-down" style="color: var(--accent-color); cursor: pointer;" ></i>
		        					</td>
		        					<td class="chiave">
			        					<spring:bind path="firmaremota.parametri[${a.index}].chiave">
			        						<input type="hidden" name="${status.expression}" value="${status.value}"></input>
			        						<span>${status.value}</span>
			        					</spring:bind>
		        					</td>
		        					<td class="descrizione">
										<spring:bind path="firmaremota.parametri[${a.index}].descrizione">
			        						<input type="hidden" name="${status.expression}" value="${status.value}"></input>
			        						<span>${status.value}</span>
			        					</spring:bind>
									</td>
		        					<td class="obbligatorio">
		        						<spring:bind path="firmaremota.parametri[${a.index}].obbligatorio">
		        							<input type="hidden" name="${status.expression}" value="${status.value}"></input>
		        							<span>${status.value ? 'SI' : 'NO'}</span>
		        						</spring:bind>
		        					</td>
		        					<td class="visibile">
		        						<spring:bind path="firmaremota.parametri[${a.index}].visibile">
		        							<input type="hidden" name="${status.expression}" value="${status.value}"></input>
		        							<span>${status.value ? 'SI' : 'NO'}</span>
		        						</spring:bind>
		        					</td>
		        					<td class="readOnly">
		        						<spring:bind path="firmaremota.parametri[${a.index}].readOnly">
		        							<input type="hidden" name="${status.expression}" value="${status.value}"></input>
		        							<span>${status.value ? 'SI' : 'NO'}</span>
		        						</spring:bind>
		        					</td>
		        					<td class="tipoCampo">
										<spring:bind path="firmaremota.parametri[${a.index}].tipoCampo">
			        						<input type="hidden" name="${status.expression}" value="${status.value}"></input>
			        						<span>${status.value}</span>
			        					</spring:bind>
		        					</td>
		        					<td class="valoreDefault">
										<spring:bind path="firmaremota.parametri[${a.index}].valoreDefault">
			        						<input type="hidden" name="${status.expression}" value="${status.value}"></input>
			        						<span>
			        							<c:if test="${parametro.tipoCampo eq 'PASSWORD'}">
			        								********
			        							</c:if>
			        							<c:if test="${parametro.tipoCampo != 'PASSWORD'}">
				        							${status.value}
				        						</c:if>
											</span>
			        					</spring:bind>
		        					</td>
		        					<td class="azioni">
		        						<ul>
		        							<li>
		        								<a class='modifica'><i class="fa fa-pencil"aria-hidden="true"></i> Modifica</a>	
		        							</li>
		        							<li>
		        								<a class='elimina'><i class="fa fa-trash"aria-hidden="true"></i> Elimina</a>
		        							</li>
		        						</ul>
		        					</td>
		        				</tr>
	        				</c:forEach>
	        			</tbody>
	        		</table>
	        	</fieldset>
				<div id="popup" class="vbg-modal" style="display: flex;">
					<div class="vbg-modal-body">
						<h1><fmt:message key="firmaremota.label.popup.title" /></h1>
						<div class="validate-required form-group">
							<label><fmt:message key="firmaremota.label.parametri.chiave" /></label>
		                    <input type="text" id="chiave" class='control-to-validate' style='width: 25px;' />
		                    <div id="errori_contesto" class="error validation-feedback"><fmt:message key="alert.required" /></div>
						</div>
						<div class="validate-required form-group">
							<label><fmt:message key="firmaremota.label.parametri.descrizione" /></label>
		                    <input type="text" id="descrizione" class='control-to-validate' style='width: 300px;' />
		                    <div class="error validation-feedback"><fmt:message key="alert.required" /></div>
						</div>
						<div class="form-group">
							<label><fmt:message key="firmaremota.label.parametri.obbligatorio" /></label>
							<input type="checkbox" id="obbligatorio"></input>
						</div>
						<div class="form-group">
							<label><fmt:message key="firmaremota.label.parametri.visibile" /></label>
							<input type="checkbox" id="visibile"></input>
						</div>
						<div class="form-group">
							<label><fmt:message key="firmaremota.label.parametri.readonly" /></label>
							<input type="checkbox" id="readOnly"></input>
						</div>
						<div class="validate-required form-group">
							<label><fmt:message key="firmaremota.label.parametri.tipocampo" /></label>
							<select id="tipoCampo" class='control-to-validate'>
								<option value=""><fmt:message key="label.select.default"/></option>
								<option value="TESTO">TESTO</option>
								<option value="PASSWORD">PASSWORD</option>
								<option value="LISTA">LISTA</option>
							</select>
							<div name="errori_contesto" class="error validation-feedback"><fmt:message key="alert.required" /></div>
						</div>
						<div class="form-group">
							<label><fmt:message key="firmaremota.label.parametri.valoredefault" /></label>
							<input type="text" id="valoreDefault" class='control-to-validate' style='width: 200px;'></input>
						</div>
						<div class='vbg-modal-footer'>
                       		<a id="btnUpdatePar" class='btn btn-primary'><fmt:message key="button.update" /></a>
                        	<a id="btnClosePar"  class="btn btn-secondary"><fmt:message key="button.back" /></a>
						</div>
					</div>
				</div>
	        </div>
		</spring-form:form>
	</div>
</body>
</html>