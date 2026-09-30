<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message	key="label.anagrafe_tributaria_tracciati.title" /></title>
<style>
span{
    padding-right: 25px;
}
#panel_errori_id{
   	min-height: 50px;
   	
	}
.loader {
    display: flex;
    position: relative;
    width: 80px;
    height: 80px;
    opacity: 0;
    margin: auto;

}


.loader.show {
    opacity: 1;
}

.loader div {
    display: inline-block;
    position: absolute;
    margin: auto;
    left: 80px;
    width: 16px;
    background: #720c0c;
    animation: loader 1.2s cubic-bezier(0, 0.5, 0.5, 1) infinite;
}

.loader div:nth-child(1) {
    left: 8px;
    animation-delay: -0.24s;
}

.loader div:nth-child(2) {
    left: 32px;
    animation-delay: -0.12s;
}

.loader div:nth-child(3) {
    left: 56px;
    animation-delay: 0;
}

@keyframes loader {
    0% {
        top: 8px;
        height: 64px;
    }

    50%,
    100% {
        top: 24px;
        height: 32px;
    }
}
#contaner-nascondi-righe > li{
	display:inline;
}
.nascondi{
	display:none;
}
#snackbar {
  visibility: hidden;
  min-width: 250px;
  margin-left: -125px;
  border-radius: 2px;
  padding: 16px;
  position: fixed;
  z-index: 1;
  left: 50%;
  bottom: 30px;
  font-size: 17px;
  background-color: #155724;
  color: #d4edda;
}


#snackbar.show {
  visibility: visible;
  -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
  animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

@-webkit-keyframes fadein {
  from {bottom: 0; opacity: 0;} 
  to {bottom: 30px; opacity: 1;}
}

@keyframes fadein {
  from {bottom: 0; opacity: 0;}
  to {bottom: 30px; opacity: 1;}
}

@-webkit-keyframes fadeout {
  from {bottom: 30px; opacity: 1;} 
  to {bottom: 0; opacity: 0;}
}

@keyframes fadeout {
  from {bottom: 30px; opacity: 1;}
  to {bottom: 0; opacity: 0;}
}
.tabelle-errori td{
	white-space:normal !important;
}
.tabelle-errori td label{
	width:60px !important;
}
.righe-tracciato-container{
    max-height: 500px;
    overflow-y: auto;
}
.errore-gruppi{
	display: inline;
	color: red;
	font-weight: bold;
}
@-webkit-keyframes flash {
    0% ,
    50%{
        background-color: #d4edda;
    }
    
    100% {
        background-color: var(--main-bg-color);
    }
}
    
.flash {
    -webkit-animation-name: flash;
    -webkit-animation-duration: 2000ms;
    -webkit-animation-iteration-count: 1;
    -webkit-animation-timing-function: ease-in-out;
}  

</style>
</head>
<body>
<span class="titoloPagina"><init:editLabel key="label.anagrafe_tributaria_tracciati.title" role="ROLE_EDITLABEL" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="file" />
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafetributaria/view"/>
	</jsp:include>
<div id="subcontent">

<spring-form:form commandName="testataEsitoTracciatoModel" name="inviodati">
	
<spring-form:hidden path="id" />

<div id="form" class="vbg-form">	
	<fieldset>
			 	<legend><init:editLabel key="label.dati_generali" role="ROLE_EDITLABEL" /> 
			 	<%-- AL MOMENTO NON DISPONIBILE LINK ESTERNO
			 		- <a href="#"><init:editLabel key="label.documentazione_funzionalita" role="ROLE_EDITLABEL" /> <i class="fas fa-external-link-alt"></i></a>
			 	 --%>
			 	
			 	</legend>

			 	<div class="form-group">
			 		<label><fmt:message key="label.descrizione" /></label>
			 			
			 			<spring-form:input id="descrizione_id" path="descrizione" size="100" maxlength="255" cssClass="required" />
			 		</div>
			 		
				<div class="form-group">
			 		<label><init:editLabel key="label.tipologia" role="ROLE_EDITLABEL" /></label>
			 		${ testataEsitoTracciatoModel.tipologia }
			 		</div>
		 					 				 	
	</fieldset>
</div>	

	<div class="form-button">
		<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
	</div>
 
<div id="form" class="vbg-form"style="margin-top: 10px;">	
	<fieldset>
			 <legend><fmt:message key="label.errori" /> <div class="errore-gruppi">[${ testataEsitoTracciatoModel.numeroGruppi}]</div></legend>
			 <table id="contaner-nascondi-righe" style="width:100%">
		 		<tr>
		 			<td style="width:45%"></td>
		 			<td style="width:45%"></td>
		 			<td style="width:10%">
		 				<input type="checkbox" name="nascondi_righe_validate" id="id_nascondi_righe_validate"/>
		 	   			<label for="id_nascondi_righe_validate" style="float:right clear:both">Nascondi completate</label>
		 			</td>
		 		
		 		</tr> 
			</table>				
			 <div id="panel_errori_id"></div>
			 <div class="loader">
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
	</fieldset>
	<div id="snackbar" class="alert alert-success"></div>
	
	<vbg-modal id="mod" class="vbg-form">
	
        <div slot="body" id="mod-body" class="form-group">
        </div>
        <div slot="footer" id="vbg-modal-footer"></div>
    </vbg-modal>	 	
	
</div>
				 	
</spring-form:form>

<script type="text/javascript">
	
	const numeroGruppi = ${ testataEsitoTracciatoModel.numeroGruppi};	
	const limit = 10;
	
	
	(function () {


	    const errori = document.querySelector('#panel_errori_id');
	    const loader = document.querySelector('.loader');

	    const getErrori = async (offset, limit) => {
	    	const idTestata = ${testataEsitoTracciatoModel.id};
	      
	       const response = await fetch('../anagrafetributaria/ajaxGetRighe.htm?idTestata='+idTestata+'&offset='+offset+'&limit='+limit);
	        return response.json();

	    }

	    const creaLinkCerca = (idGruppo) => {
			const lb = document.createElement('label');
	        const a = document.createElement('a');
	        a.setAttribute('href', 'javascript:void(0)');
	        const iCerca = document.createElement('i');
	        iCerca.setAttribute('aria-hidden', true);
	        iCerca.classList.add('form-group');
	        iCerca.classList.add('fa');
	        iCerca.classList.add('fa-search');
			a.addEventListener('click',()=>{
				if(hasQueryStringField('codiceGruppoAnagrafetributaria')){
					let urlParams = setQueryStringValue('codiceGruppoAnagrafetributaria',idGruppo);
					historySet(encodeURIComponent('../anagrafetributaria/view.htm?'+urlParams.toString()), '../istanze/searchIstanze.htm?tipoRicerca=3&codiceGruppoAnagrafetributaria='+idGruppo, '');
				}else{
					
					historySet('${_urlback}'+encodeURIComponent('&codiceGruppoAnagrafetributaria='+idGruppo), '../istanze/searchIstanze.htm?tipoRicerca=3&codiceGruppoAnagrafetributaria='+idGruppo, '');
				}
				 
			});
	        a.appendChild(iCerca);
	        a.appendChild(document.createTextNode('Cerca'));
	        lb.appendChild(a);
	        return lb;
	    }
	    const creaTabellaErrori =  (listaErrori) => {
	        const tb = document.createElement('table');
	        const thd = document.createElement('thead');
	        const th1 = document.createElement('th');
	        const th2 = document.createElement('th');
	        const th3 = document.createElement('th');
	        const th4 = document.createElement('th');
	        const tr = document.createElement('tr');
	        th1.appendChild(document.createTextNode('Tipologia Errore'));
	        th2.appendChild(document.createTextNode('Errore'));
	        th3.appendChild(document.createTextNode('Intestazione'));
	        th4.appendChild(document.createTextNode('Completato'));
	        tr.appendChild(th1);
	        tr.appendChild(th2);
	        tr.appendChild(th3);
	        tr.appendChild(th4);
	        thd.appendChild(tr);
	        tb.appendChild(thd);
	        var tbdy = document.createElement('tbody');
	        tb.appendChild(thd);
	        tb.appendChild(tbdy);
	        tb.classList.add('vbg-table');
	        tb.classList.add('tabelle-errori');
	        listaErrori.forEach(e => {
	            const tr = tb.insertRow();
	            const td = document.createElement('td');
	            const td1 = document.createElement('td');
	            const td2 = document.createElement('td');
	            td.classList.add('form-group');
	            td1.classList.add('form-group');
	            td2.classList.add('form-group');
	            const sp = document.createElement('label');
	            sp.appendChild(document.createTextNode(e.tipologia_errore));
	            sp.style.float='left';
	            td.appendChild(sp);
	            td1.appendChild(document.createTextNode(e.errore));
	            td2.appendChild(document.createTextNode(e.intestazione));
	            td.style.width='4%';
	            td1.style.width='43%';
	            td2.style.width='43%';
	            
	            tr.appendChild(td);
	            tr.appendChild(td1);
	            tr.appendChild(td2);
	            const td3 = document.createElement('td');
	            td3.style.width='10%';
	            const completatoCheckbox = document.createElement('input');
	            completatoCheckbox.type = "checkbox";
	            completatoCheckbox.name = "completato";
	            completatoCheckbox.value = e.id;
	            completatoCheckbox.checked = e.validato
	            completatoCheckbox.addEventListener('change',async (event)=>{
	            	const idRigaErrore = event.target.value;
	            	window.vbg.mostraModalCaricamento();
            		const response = await fetch('../anagrafetributaria/ajaxAggiornaValidaErrore.htm?idRigaErrore='+idRigaErrore+'&segnaValido='+event.target.checked);
            		const risp = await response.text();
            		window.vbg.nascondiModalCaricamento();
            		const snackBar = document.querySelector('#snackbar');
            		snackBar.innerHTML='';
	          		if( risp === "OK"){	
	          			snackBar.appendChild(document.createTextNode('Aggiornato con successo'));
	          			snackBar.style.backgroundColor = '#d4edda';
	          			snackBar.style.color='#155724';
	          			
	          		}else{
	          			snackBar.appendChild(document.createTextNode('Aggiornamento fallito'));
	          			snackBar.style.backgroundColor = '#f8d7da';
	          			snackBar.style.color='#721c24';
	          		}
	          		snackBar.classList.add('show');
          			setTimeout(()=>{
          				snackBar.classList.remove('show');
          			},3000);
          				
	            	
	            });
	            td3.appendChild(completatoCheckbox);
	            tr.appendChild(td3);
	            tbdy.appendChild(tr);

	        });
	        return tb;

	    }

	    const initModalIstanzeTrovate = (listaIstanze, idEsitoGruppo) => {
	        const { modalBody, modalFooter, modal } = initModal();
	        listaIstanze.forEach(ist => {
	            const lIst = document.createElement('div');
	            lIst.classList.add('form-group');
	            const lb = document.createElement('label');
	            const istT = document.createElement('a');
	            istT.setAttribute('href', "javascript:void(0);");
	            istT.appendChild(document.createTextNode(ist.numero));
	            istT.addEventListener('click',()=>{
	            	//historySet('${_urlback}','../istanze/view.htm?codice='+ist.codice,'');
	            	
	            	if(hasQueryStringField('codiceGruppoAnagrafetributaria')){
						let urlParams = setQueryStringValue('codiceGruppoAnagrafetributaria',idEsitoGruppo);
						historySet(encodeURIComponent('../anagrafetributaria/view.htm?'+urlParams.toString()), '../istanze/view.htm?codice='+ist.codice, '');
					}else{
						
						historySet('${_urlback}'+encodeURIComponent('&codiceGruppoAnagrafetributaria='+idEsitoGruppo), '../istanze/view.htm?codice='+ist.codice, '');
					}
	            });
	            lb.appendChild(istT);
	            lIst.appendChild(lb);
	            const nominativo = document.createElement('label');
	            nominativo.appendChild(document.createTextNode(ist.nominativo));
	            lIst.appendChild(nominativo);
	            modalBody.appendChild(lIst);
	        });

	        modalFooter.appendChild(creaLinkCerca(idEsitoGruppo));
	        return modal;

	    }

	    const creaLinkDettaglioRigheTracciato = (idGruppo) => {

	        const lb = document.createElement('label');
	        const a = document.createElement('a');
	        const i = document.createElement('i');
	        i.setAttribute('aria-hidden', true);
	        a.setAttribute('href', 'javascript:void(0)');
	       
	        a.addEventListener('click', async (e) => {
	        	
	            const { modal, modalBody, modalFooter } = initModal();
	            window.vbg.mostraModalCaricamento();
	            let response = await fetch('../anagrafetributaria/ajaxDettaglioRigheTracciato.htm?idGruppo='+idGruppo);
	            let json = await response.json();
	            
	            
	            let h1 = document.createElement('h1');
	            h1.appendChild(document.createTextNode('Dettaglio righe tracciato'));
	            modalBody.appendChild(h1);
	            
	            let divContainer = document.createElement('div');
	            divContainer.className = "righe-tracciato-container";

	            json.forEach(dettTracciato=>{
	            	
	            	let fieldSet = document.createElement('fieldset');
	            	let legend = document.createElement('legend');
	            	legend.appendChild(document.createTextNode('Riga nel file di tracciato: ' + dettTracciato.posizione));
	            	fieldSet.appendChild(legend);	            	
	            	let textArea = document.createElement('textarea');	
	            	textArea.innerText = dettTracciato.riga;
	            	textArea.style.whiteSpace ="wrap";
	            	textArea.style.width = "100%";
	            	fieldSet.appendChild(textArea);
	            	divContainer.appendChild(fieldSet);
	            	
	            });	            
	            modalBody.appendChild(divContainer);
	            
	            window.vbg.nascondiModalCaricamento();
	            modal.open();
	            
	        });
	        i.classList.add('fa');
	        i.classList.add('fa-search-plus');
	        a.appendChild(i);
	        a.appendChild(document.createTextNode('Dettaglio righe tracciato'));
	        lb.appendChild(a);
	        
	        return lb;

	    }
	    const mostraErrori = (risultati) => {


	        risultati.forEach(element => {
	            let f = document.createElement('fieldset');
	            f.setAttribute('id','id_'+element.id_esito_gruppo);

	            // const s = document.createElement('label');
	            // s.appendChild(document.createTextNode(element.id_esito_gruppo));
	            let d = document.createElement('div');
	            d.classList.add('form-group');
	            if (element.istanza != null) {
	                let lb1 = document.createElement('label');
	                let a1 = document.createElement('a');
	                a1.setAttribute('data-id', element.istanza.codice);
	                a1.setAttribute('href', "javascript:void(0);");
	                a1.appendChild(document.createTextNode(element.istanza.numero));
	                a1.addEventListener('click', () => {
	                    let codiceIstanza = element.istanza.codice;
	                    //historySet('${_urlback}','../istanze/view.htm?codice='+element.istanza.codice,'');
	                    if(hasQueryStringField('codiceGruppoAnagrafetributaria')){
							let urlParams = setQueryStringValue('codiceGruppoAnagrafetributaria',element.id_esito_gruppo);
							historySet(encodeURIComponent('../anagrafetributaria/view.htm?'+urlParams.toString()), '../istanze/view.htm?codice='+codiceIstanza, '');
						}else{
							
							historySet('${_urlback}'+encodeURIComponent('&codiceGruppoAnagrafetributaria='+element.id_esito_gruppo), '../istanze/view.htm?codice='+codiceIstanza, '');
						}
	                });
	                lb1.appendChild(a1);	                	                              
	                d.appendChild(lb1);
	               
	                let lb3 = document.createElement('label');
	                lb3.appendChild(creaLinkCambiaPratica(element.id_esito_gruppo));
	                d.appendChild(lb3);
	                d.appendChild(creaLinkDettaglioRigheTracciato(element.id_esito_gruppo));
	                f.appendChild(d);
	                
	                if(element.istanza.richiedente !== undefined){
	                	let richiedente = document.createElement('div');
	                	richiedente.appendChild(document.createTextNode(element.istanza.richiedente));
	                	f.appendChild(richiedente);
	                }	

	            } else if (element.istanza == null && element.istanze_trovate != null) {
	                let lb1 = document.createElement('label');
	                let a1 = document.createElement('a');
	                a1.setAttribute('href', "javascript:void(0);");
	                a1.setAttribute("istanze-trovate", element.istanze_trovate);
	                let istNum = Object.keys(element.istanze_trovate).length;
	                a1.appendChild(document.createTextNode(`Trovate ${istNum} istanze`));
	                lb1.appendChild(a1);
	                d.appendChild(lb1);
	                d.appendChild(creaLinkDettaglioRigheTracciato(element.id_esito_gruppo));
	                f.appendChild(d);
	                a1.addEventListener('click', (e) => {
	                    const modalIstTrovate = initModalIstanzeTrovate(element.istanze_trovate, element.id_esito_gruppo);
	                    modalIstTrovate.open();
	                });
	            } else if (element.istanza == null && element.istanze_trovate === undefined) {
	                d.appendChild(creaLinkCerca(element.id_esito_gruppo));
	                d.appendChild(creaLinkDettaglioRigheTracciato(element.id_esito_gruppo));
	                f.appendChild(d);
	            }
	            // f.appendChild(s);
	            f.classList.add('form-group');
	            f.appendChild(creaTabellaErrori(element.errori));
	            errori.appendChild(f);
	        });


	    }

	    
	    const creaLinkCambiaPratica = (idGruppo) => {
	    	
	        let a = document.createElement('a');
	        a.setAttribute('href', 'javascript:void(0)');
	        let iCerca = document.createElement('i');
	        iCerca.setAttribute('aria-hidden', true);
	        iCerca.classList.add('form-group');
	        iCerca.classList.add('fa');
	        iCerca.classList.add('fa-search');
			a.addEventListener('click',()=>{

				if(hasQueryStringField('codiceGruppoAnagrafetributaria')){
					let urlParams = setQueryStringValue('codiceGruppoAnagrafetributaria',idGruppo);
					historySet(encodeURIComponent('../anagrafetributaria/view.htm?'+urlParams.toString()), '../istanze/searchIstanze.htm?tipoRicerca=3&codiceGruppoAnagrafetributaria='+idGruppo, '');
				}else{
					
					historySet('${_urlback}'+encodeURIComponent('&codiceGruppoAnagrafetributaria='+idGruppo), '../istanze/searchIstanze.htm?tipoRicerca=3&codiceGruppoAnagrafetributaria='+idGruppo, '');
				} 
			});
	        a.appendChild(iCerca);
	        a.appendChild(document.createTextNode('Cambia pratica'));	        
	        return a;
	    }

	    const hideLoader = () => {
	        loader.classList.remove('show');
	    };

	    const showLoader = () => {
	        loader.classList.add('show');
	    };

	    const hasMoreErrori = (c2, limit, total) => {
	        if (c2 <= c && (limit * c2) <= total) {
	            return true;
	        }
	        return false;
	    };
	    const hasQueryStringField = (field)=>{
	    	var url = window.location.href;
	    	if(url.indexOf('?' + field + '=') != -1)
	    	    return true;
	    	else if(url.indexOf('&' + field + '=') != -1)
	    	    return true;
	    	return false
	    };
	    
	    
	    const getQueryStringValue = (field)=>{
	    	if (hasQueryStringField(field)) {
	    		const queryString = window.location.search;
		    	const urlParams = new URLSearchParams(queryString);
		    	return urlParams.get(field);
			}
	    	
	    	return null;
	    }
	    const setQueryStringValue = (field,value)=>{
	    	
	    		const queryString = window.location.search;
		    	const urlParams = new URLSearchParams(queryString);
		    	urlParams.set(field,value);
		    	return urlParams;
	    }


	    const loadErrori =  async (cnt, limit) => {


	        showLoader();



	        setTimeout(async () => {
	        	const idGruppoAnagrafe = getQueryStringValue('codiceGruppoAnagrafetributaria');
				
	            try {
	            	if(total == 0 && idGruppoAnagrafe == null){
	            		
	                    const response = await getErrori(limit * cnt, limit);
	                    mostraErrori(response.risultati);
	                    total = response.total;
	                    limit = response.limit;
	                    c = Math.ceil(total / limit);
	                    return;
	            	}else if(idGruppoAnagrafe != null && total == 0){
	            		vbg.mostraModalCaricamento();
	            		 const response = await getErrori(limit * cnt, limit);
	            		 let risultati = response.risultati;
	            		 mostraErrori(risultati);
	            		 
		                 let trovato = false;
	            		 risultati.forEach(ris=>{
	            			 if(ris.id_esito_gruppo == idGruppoAnagrafe){
	            				 trovato=true;

	            			 }
	            		 });
	            		 if (!trovato) {
	            			cnt =cnt+1;
	            			counter = cnt;
	            			 loadErrori(cnt,limit);
	            			//window.scrollTo(window.scrollTop,document.body.scrollHeight);
							
						}else{
							total = response.total;
							limit = response.limit;
							c = Math.ceil(total / limit);
						 	 document.querySelector('#id_'+idGruppoAnagrafe).scrollIntoView({ behavior: 'smooth', block: 'end'});
							 setTimeout(()=>{document.querySelector('#id_'+idGruppoAnagrafe).classList.add('flash')},1000);
							setTimeout(()=>{document.querySelector('#id_'+idGruppoAnagrafe).classList.remove('flash')},500);
							vbg.nascondiModalCaricamento();
		            		return;
						}
	            		
		                 
	                     
	            	}	            	
	            	else if (hasMoreErrori(c2, limit, total)  && total > 0) {
	                    const response = await getErrori(limit * counter, limit);
	                    mostraErrori(response.risultati);
	                    nascondiRigheValidate(null);
	                    total = response.total;
	                    limit = response.limit;
	                    return;
	                }
	                
	            } catch (error) {
	                console.log(error.message);
	            } finally {
	                hideLoader();
	            }
	        }, 500);

	    };


	    let offset = 0;	    
	    let total = 0;
	    let counter = 0;
	    let c;
	    let c2 = 1;

	    function isScrollCompleto(e) {
	    	const {scrollHeight, clientHeight,scrollTop} = e.target.documentElement;
	        if (scrollHeight == 0) {
	            return true;
	        }
	        if (Math.abs(scrollHeight - clientHeight - scrollTop) < 1) { return true; }
	        else { return false; }
	    }

	    document.addEventListener('scroll', (e) => {
	        if (isScrollCompleto(e) && hasMoreErrori(c2, limit, total)) {
	            counter++;
	            c2++;
	            loadErrori(counter, limit);
	        }

	    }, {
	        passive: true
	    });
	    
	    function initModal() {
		    const modal = document.querySelector('#mod');
		    const modalBody = document.querySelector('#mod-body');
		    const modalFooter = document.querySelector('#vbg-modal-footer');
		    modalBody.innerHTML = "";
		    modalFooter.innerHTML = "";
		    return { modalBody, modalFooter, modal };
		}
	    
	    
	    const nascondiRigheValidate = (evt)=>{
	    	
	    	let checked;
	    	if(evt != null){
	    		checked = evt.target.checked;
	    	}else{
	    		checked = document.querySelector('#id_nascondi_righe_validate').checked;
	    	}
	    	const tabelle = document.querySelectorAll('.tabelle-errori');
	    	if(checked){    		
	    		tabelle.forEach(tabella =>{
	    			let counter = 0;
	    			const righe = tabella.querySelectorAll('tr');
	    			righe.forEach(riga =>{
	    				const completato = riga.cells[3].querySelector('input');
	    				if( completato != null && completato.checked ){
	    					counter++;
	    					riga.classList.add('nascondi');
	    				}
	    				if(righe.length == counter+1){
	    					tabella.parentElement.classList.add('nascondi');
	    				}
	    			});
	    		});
	    	}else{
	    		const tabelle = document.querySelectorAll('.tabelle-errori');
	    		tabelle.forEach(tabella =>{
	    			let counter = 0;
	    			const righe = tabella.querySelectorAll('tr');
	    			righe.forEach(riga =>{
	    				const completato = riga.cells[3].querySelector('input');	    					
	    					if( completato != null && completato.checked ){
		    					counter++;
		    					riga.classList.remove('nascondi');
		    					
		    				}
	    				
	    			});
	    			if(righe.length == counter+1){
    					tabella.parentElement.classList.remove('nascondi');
    				}
	    		});
	    	}
	    	
	    	
	    }
	    
	    const nascondiRighe = document.querySelector('#id_nascondi_righe_validate');
	    nascondiRighe.addEventListener('change',(evt)=>{
	    	nascondiRigheValidate(evt);
	    });

	    // initialize
	    loadErrori(offset, limit);

	})();

	




</script>

</body>
</html>