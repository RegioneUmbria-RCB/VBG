<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.AuthLevel"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Ateco" %>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.sposta_pratica.title"/></title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	<style media="all">
		.field{
			
    		margin:10px 22px;
    		vertical-align:middle;
    		font-size: 2em;
		}
		.isa_errore{
			background-color: #FFD2D2;
		}
		.isa_successo{
			background-color: #DFF2BF;
		}
		.legend_messaggio{
			
    		border: none !important;
		}
		#sezione_messaggio{
			display: none;
		}
		.icon_errore{
			color: #D8000C;
			margin-right:10px
		}
		.icon_successo{
			color: #4F8A10;
			margin-right:10px
		}
		.sezione_ruoli_modelli{
			list-style:none;
		}
		#tabella_pratiche{
			display: block;
		}
		.pagination {
  			display: inline-block;
  			margin-top:10px;
		}

		.pagination a {
		  color: black;
		  float: left;
		  padding: 8px 16px;
		  text-decoration: none;
		   border: 1px solid #ddd;
		}

		.pagination a.active {
		  background-color: #4CAF50;
		  color: white;
		}
		.pagination a:hover:not(.active) {background-color: #ddd;}
		
		.vissualizza_sezione{
			display: block;
		
		}
		.nascondi_sezione{
			display: none;
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.sposta_pratica.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../alberoproc/view" />
	</jsp:include>
	<div class="corpo">
		<div id="pratica_da_spostare" class="vbg-form">
			<fieldset>
				<legend><fmt:message key="label.pratica_da_spostare"/></legend>
				<span>${alberoproc.descrizioneCompleta }</span>	
				<input id="codice_inventario_da_spostare" type="hidden" value="${alberoproc.id.codice }" name="codiceInventarioOld"/>	
			</fieldset>
		
		</div>
		<div id="albero" class="vbg-form">
			<fieldset>
				<legend><fmt:message key="label.pratica_dove_spostare"/></legend>
				<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
	                       
	            <div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" store="alberoprocStore" query="{root:'1'}" rootId="<%=WebConstants.ATECO_CODICE_ROOT%>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
	            
	            <div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
	            
	            <script type="dojo/method" event="onClick" args="item">
		        if(item.id != '0'){
		        	var itemId = alberoprocStore.getValue(item, "id");		 
					var descr = alberoprocStore.getValue(item, "descrizioneEstesa");									
		        	cercaProcedimentoAjax(itemId,descr);
		        }
   		   		 </script>
	           
	            <script type="dojo/method" event="getIconClass" args="item, opened">
                if (item.id != '0') {
                    var dis = 'false';
                    var scPubblica = '';
                    if (item) {
                        dis = alberoprocStore.getValue(item, "disabilitato");
                        scPubblica = alberoprocStore.getValue(item, "scPubblica");
                    }
                    var icona = "";
                    if (dis == 'false') {
                        if (!item || this.model.mayHaveChildren(item)) {
                            if (opened) {
                                icona = "dijitFolderOpened";
                            } else {
                                icona = "dijitFolderClosed";
                            }
                        } else {
                            icona = "dijitLeaf";
                        }

                        if (scPubblica) {
                            if (scPubblica == '0') {
                                icona = icona.replace('dijit', 'nonPubblicare');
                            } else if (scPubblica == '1') {
                                icona = icona.replace('dijit', 'areaRiservataFrontoffice');
                            } else if (scPubblica == '2') {
                                icona = icona.replace('dijit', 'areaRiservata');
                            } else if (scPubblica == '3') {
                                icona = icona.replace('dijit', 'frontoffice');
                            }
                        }

                        return icona;

                    } else {
                        return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
                    }
                } else {
                    return "dijitFolderOpened"
                }
            </script>
           
			
			</fieldset>
		
		</div>
	
	</div>
	 <vbg-modal id="vbgmodal">
		<div slot='body'>
		<h1>Modifica Intervento Pratiche</h1>
		<div id="sezione_messaggio">
			<div class="vbg-form">
				<fieldset id="field_messaggio" class="field">
					<legend class="legend_messaggio"><i id="icon_messaggio"></i></legend>
					<div id="messaggio"></div>
				</fieldset>
				
				
			</div>
		</div>
		<div id="sezione_1">
	             <p>
	              	Sono state individuate <b><span id="num_pratiche"></span></b> pratiche da spostare (<a id="link_lista_pratiche" href="javascript:void(0)">clicca qui per l'elenco completo</a>)
	             </p> 
	             <p>
	             	Selezionare i ruoli e i modelli da aggiungere e cliccare su <b>OK</b> per confermare l'operazione.
	             </p>
	             <div class="vbg-form">
	             	<fieldset>
	             		<legend>RUOLI</legend>
	             		<div id="sezione_ruoli" >
	             			<ul id="sezione_ruoli_list" class="sezione_ruoli_modelli"></ul>
	             		</div>
	             	</fieldset>
	             	
	             
	             </div>
	             <div class="vbg-form">
	             	<fieldset>
	             		<legend>MODELLI</legend>
	             		<div id="sezione_modelli" >
	             			<ul id="sezione_modelli_list" class="sezione_ruoli_modelli"></ul>
	             		</div>
	             	</fieldset>
	             	
	             
	             </div>
	             <input id="codiceInterventiHidden" type="hidden" name="codiceInventarioNew" />
             </div> 
             <div id="sezione_2" class="nascondi_sezione">
				<table class="vbg-table" id="tabella_pratiche" >
					<thead class="header">
						<tr>
							<th>Numero</th>
							<th>Data</t>
							<th>Protocollo</th>
							<th>Richiedente</th>
							<th>Oggetto</th>
						</tr>
					</thead>
					
				</table>
				<div class="pagination" id="area_paginazione">
				
					
				</div>
				<input type="hidden" id ="total"/>
				<input type="hidden" id ="len_lista_pratiche"/>
				<input type="hidden" id="start_id" value="0"/>
		</div>          
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="okModal"><fmt:message key="button.ok"/> </div>
			<div class="btn btn-primary" id="closeModal"><fmt:message key="button.close"/></div>
			<div class="btn btn-primary nascondi_sezione" id="close_sezione_tabella"><fmt:message key="button.close"/></div>            
		</div>
	</vbg-modal>
	
	 <script type="text/javascript">
            const modal = document.getElementById('vbgmodal');
            const codiceInterventoNew = document.getElementById('codiceInterventiHidden');
            const codiceInterventoOld = document.getElementById('codice_inventario_da_spostare');
            
            function callAsync(options) {
				
				disableFunctions();
				
				return jQuery.ajax({	
					url : options.url,
					data : options.data,
					method : 'POST',
					type : 'POST', // For jQuery < 1.9
					context : document.body,
					cache : false,
					dataType : options.dataType || "html",
					success : options.success,
					error : options.error || gestisciErrore
				}).always(enableFunctions);
			}
            function gestisciErrore(){
            	
            }
            function cercaProcedimentoAjax(ItemId,descr){
           		codiceInterventoNew.value = ItemId;
           		callAsync({
                       url : "ajaxSelectNewIntervento.htm",
                       data : {
                       	codiceInterventoOld : codiceInterventoOld.value,
                       	codiceInterventoNew : codiceInterventoNew.value            
                       },
                       dataType : "json",
                       success : function(data) {
                    	let sezione = document.getElementById('sezione_1');
                    	if(data.errore){
                    		sezione.style.display = "none";
                    		document.getElementById('icon_messaggio').classList.remove(...document.getElementById('icon_messaggio').classList);
                    		document.getElementById('icon_messaggio').classList.add('fa');
                    		document.getElementById('icon_messaggio').classList.add('fa-times-circle');
                    		document.getElementById('icon_messaggio').classList.add('icon_errore');
                    		document.getElementById('field_messaggio').classList.remove(...document.getElementById('field_messaggio').classList);
                    		document.getElementById('field_messaggio').classList.add('field');
                    		document.getElementById('field_messaggio').classList.add('isa_errore');
                    		document.getElementById('sezione_messaggio').style.display = "block";
                    		document.getElementById('messaggio').innerHTML = data.errore;
                    		document.getElementById('okModal').classList.add('nascondi_sezione');
                    	}else{
                    		document.getElementById('num_pratiche').innerHTML = data.numeroPraticheDaSpostare;
                    		document.getElementById('sezione_messaggio').style.display = "none";
                    		let sezioneRuoli = document.getElementById('sezione_ruoli_list');
                    		let sezioneModelli = document.getElementById('sezione_modelli_list');
                    		sezioneRuoli.innerHTML = "";
                    		sezioneModelli.innerHTML = "";
                    		if(data.listaRuoli){
                    			data.listaRuoli.forEach((obj,index)=>{
                    				let li = document.createElement('li');
                    				let label = document.createElement('label');
                    				label.setAttribute('for','listaRuoli'+index);
                    				label.innerHTML = obj.descrizione;
                        			let inp = document.createElement('input');
                        			inp.setAttribute('type','checkbox');
                        			inp.setAttribute('name','listaRuoli'+index);
                        			inp.classList.add('ruoli');
                        			inp.checked = true;
                        			inp.value = obj.id;
                        			li.appendChild(inp);
                        			li.appendChild(label);
                        			sezioneRuoli.appendChild(li);
	
                        		});
                    			
                    		}
                    		if(data.listaSchedeDinamiche){
                    			data.listaSchedeDinamiche.forEach((obj,index)=>{
                    				let li = document.createElement('li');
                    				let label = document.createElement('label');
                    				label.setAttribute('for','listaModelli'+index);
                    				label.innerHTML = obj.descrizione;
                        			let inp = document.createElement('input');
                        			inp.setAttribute('type','checkbox');
                        			inp.setAttribute('name','listaModelli'+index);
                        			inp.classList.add('modelli');
                        			inp.checked = true;
                        			inp.value = obj.id;
                        			inp.innerHTML = obj.descrizione;
                        			li.appendChild(inp);
                        			li.appendChild(label);
                        			sezioneModelli.appendChild(li); 			
                        			
                        		});
                    		}
                    		
                    		
                    		
                    	}
                       	modal.open();
                           
                       }
				});
	
            		
           	}
            	
            	document.getElementById('closeModal').addEventListener('click', (e)=>{
        			modal.close();
        		});
            	document.getElementById('okModal').addEventListener('click', (e)=>{
            		let listaRuoli = document.querySelectorAll('.ruoli');
            		let listaModelli = document.querySelectorAll('.modelli');
            		let arrayRuoli = [];
            		let arraymodelli = [];
            		listaRuoli.forEach((obj,index)=>{
            			if(obj.checked === true){
            				arrayRuoli.push(obj.value);
                			
                		}
            		});
            		listaModelli.forEach((obj,index)=>{
            			if(obj.checked === true){
            				arraymodelli.push(obj.value);
                			
                		}
            		});
            		window.vbg.mostraModalCaricamento();
            		
        			callAsync({
        				 url : "ajaxSpostaPratica.htm",
                         contentType : "application/x-www-form-urlencoded",
                         dataType:"json",
                         data: {
                        	 listaRuoli: arrayRuoli,
                        	 listaModelli: arraymodelli,
                        	 codiceInterventoNew: codiceInterventoNew.value,
                        	 codiceInterventoOld: codiceInterventoOld.value
                        	 
                        	 
                         },
                         success : function(data) {
                        	 window.vbg.nascondiModalCaricamento();
                        	 let sezione = document.getElementById('sezione_1');
                        	 sezione.style.display = "none"; 
                        	 if(data.codice === "OK"){
                        		document.getElementById('sezione_messaggio').style.display = "block";
                        		document.getElementById('icon_messaggio').classList.add('fa');
                        		document.getElementById('icon_messaggio').classList.add('fa-check-circle');
                         		document.getElementById('icon_messaggio').classList.add('icon_successo');
                         		document.getElementById('field_messaggio').classList.add('isa_successo');
                        		document.getElementById('messaggio').innerHTML = "Operazione avvenuta con successo";
                        		document.getElementById('okModal').classList.add('nascondi_sezione');
                        		 
                        	 }else{
                         		document.getElementById('sezione_messaggio').style.display = "block";
                         		document.getElementById('icon_messaggio').classList.add('fa');
                         		document.getElementById('icon_messaggio').classList.add('fa-times-circle');
                        		document.getElementById('icon_messaggio').classList.add('icon_errore');
                        		document.getElementById('field_messaggio').classList.add('isa_errore');
                         		document.getElementById('messaggio').innerHTML = data.descrizione;
                         		document.getElementById('okModal').classList.add('nascondi_sezione');
                        		 
                        	 }
                 
                         },
                         error: function(errore){
                        	 window.vbg.nascondiModalCaricamento();
                        	 console.log(errore);
                        	 
                         }
        				
        			});
        		});
            	 document.getElementById('link_lista_pratiche').addEventListener('click',async ()=>{
	            		let codiceInterventoOrigine = document.getElementById('codice_inventario_da_spostare');
	            		document.getElementById('sezione_1').classList.add('nascondi_sezione');
	            		document.getElementById('sezione_2').classList.add('visualizza_sezione');
	            		document.getElementById('sezione_2').classList.remove('nascondi_sezione');
	            		document.getElementById('okModal').classList.add('nascondi_sezione');
	            		document.getElementById('closeModal').classList.add('nascondi_sezione');
	            		let bottoneCloseTabella = document.getElementById('close_sezione_tabella');
	            		bottoneCloseTabella.classList.add('visualizza_sezione');
	            		bottoneCloseTabella.classList.remove('nascondi_sezione');
	            		
	            		bottoneCloseTabella.addEventListener('click',()=>{
	            			document.getElementById('okModal').classList.remove('nascondi_sezione');
		            		document.getElementById('closeModal').classList.remove('nascondi_sezione');
		            		document.getElementById('okModal').classList.add('visualizza_sezione');
		            		document.getElementById('closeModal').classList.add('visualizza_sezione');
		            		bottoneCloseTabella.classList.add('nascondi_sezione');
		            		bottoneCloseTabella.classList.remove('visualizza_sezione');
		            		document.getElementById('sezione_1').classList.remove('nascondi_sezione');
		            		document.getElementById('sezione_1').classList.add('visualizza_sezione');
		            		document.getElementById('sezione_2').classList.remove('visualizza_sezione');
		            		document.getElementById('sezione_2').classList.add('nascondi_sezione');
	            		});
	            		
	            		
	            		const limit = 50;
	            		if(document.getElementById('prev') === null && document.getElementById('next') === null){
		            		caricaListaPratiche(codiceInterventoOrigine,0,limit).then(()=>{
		            			
		            			
		            				let pag = document.getElementById('area_paginazione');
			            			pag.innerHTML = "";
			                    	let a1 = document.createElement('a');
			                    	a1.setAttribute('href','javascript:void(0)');
			                    	a1.id ='prev';
			                		a1.innerHTML = 'Precedente';
			                		pag.appendChild(a1);
			                		let total = document.getElementById('total').value;
			                    	let pagNum = parseInt(total/limit);
			                    	if(total%limit > 0){
			                    		pagNum++;
			                    	}
			                    	
			                    	
			                    	let a2 = document.createElement('a');
			                    	a2.setAttribute('href','javascript:void(0)');
			                    	a2.id = 'next';
			                		a2.innerHTML = 'Prossima';
			                		pag.appendChild(a2);
			                		
			                    	let nextButton = document.getElementById('next');
			                    	let prevButton = document.getElementById('prev');
			                    	let offset = 0;
			                    	let pagina = 1;
			                    	nextButton.addEventListener('click',(e)=>{
			                    
			                    			if (offset + limit < total) {
			                    				offset = offset + limit;
			                    				caricaListaPratiche(codiceInterventoOrigine,offset,limit).then(()=>{
			                    					pagina++;
						                    		a2.innerHTML = 'Pagina('+pagina+'/'+pagNum+') &raquo;';
						                    		a1.innerHTML = 'Precedente';
			                    				});
					                    		
											}
			                    			
				                    		
										
			                    		
			                    		
			                    	});
			                    	prevButton.addEventListener('click',(e)=>{
			                    		
			                    		if (offset -limit >= 0) {
			                    			offset = offset - limit;
			                    			caricaListaPratiche(codiceInterventoOrigine,offset,limit).then(()=>{
			                    				pagina--;
					                    		a1.innerHTML = ' &laquo; Pagina('+pagina+'/'+pagNum+') ';
					                    		a2.innerHTML = 'Prossima';
			                    			});
				                    		
				                    		
										}
				                    		
				                    });
		                        	 
		            			
		            		});
	            		}
                    	
            		 
            	 });
            	 
            	 
            	const caricaListaPratiche = async (codiceInterventoOrigine,offset,limit)=>{
            		window.vbg.mostraModalCaricamento();
            		 await callAsync({
        				 url : "ajaxVisualizzapratiche.htm",
                         contentType : "application/x-www-form-urlencoded",
                         dataType:"json",
                         data: {
                        	 codiceInterventoOrigine: codiceInterventoOrigine.value,
                        	 offset: offset,
                        	 limit: limit            	 
                         },
                         success : function(data) {
         
                        	 let tabellaPratiche = document.getElementById('tabella_pratiche');
                        	 if(tabellaPratiche.tBodies.length > 0){
                        		 tabellaPratiche.tBodies[0].innerHTML = "";
                        	 }
                        	 else{
                        		 tabellaPratiche.createTBody();
                        	 }
                        	 
                        	 data.lista_istanze.forEach((obj,index)=>{
                        		 let riga = document.createElement('tr');
                        		 let numIstanza = document.createElement('td');
                        		 let data = document.createElement('td');
                        		 let protocollo = document.createElement('td');
                        		 let richiedente = document.createElement('td');
                        		 let oggetto = document.createElement('td');
                        		 numIstanza.innerHTML = obj.numero_istanza;
                        		 data.innerHTML = obj.data;
                        		 if(obj.numero_protocollo){
                        			 protocollo.innerHTML = obj.numero_protocollo;
                        		 }
                        		 
                        		 richiedente.innerHTML = obj.richiedente;
                        		 oggetto.innerHTML = obj.oggetto;
                        		 riga.appendChild(numIstanza);
                        		 riga.appendChild(data);
                        		 riga.appendChild(protocollo);
                        		 riga.appendChild(richiedente);
                        		 riga.appendChild(oggetto);
                        			 
                        		 tabellaPratiche.tBodies[0].appendChild(riga);
                        		 
                        	 });
                        	 document.getElementById('len_lista_pratiche').value = data.lista_istanze.length;
                    		 document.getElementById('total').value = data.total;
                    		 window.vbg.nascondiModalCaricamento();
       
                         },
                         error: function(errore){
                        	 window.vbg.nascondiModalCaricamento();
                        	 console.log(errore);
                        	 
                         }
            		 });
            		 
            	 }
            	
            
            </script>
	
	<div id="functions">
		<ul>
			
			<!-- <li><a href="javascript:void(0)"><fmt:message key="button.sposta_pratica" /></a></li>  -->
			
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>