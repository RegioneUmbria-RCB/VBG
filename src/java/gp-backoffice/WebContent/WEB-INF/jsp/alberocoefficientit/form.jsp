<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Ateco" %>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${alberoCoefficientiT.id.codice==null}">
			<fmt:message key="alberoCoefficientiR.label.nuovo_alberoCoefficientiR.title" />
		</c:if> 
		<c:if test="${alberoCoefficientiT.id.codice!=null}">
			<fmt:message key="alberoCoefficientiR.label.dettaglio_alberoCoefficientiR.title" />
		</c:if>
	</title>
	<style media="all">
		.btn-foot {
   			text-align: right !important;
   			padding: var(--default-padding);
		}
		#codCoef {
			min-width: 4px;
		}
		
		.vbg-form .form-group > label {
			width: 400px !important;
		}
		
		.show-hide {
			display: none;
		}
		#label_proc {
			width: 378px !important;
		}
		
		.btn_salva{
			display: none;
		}
		
		.riga-note{
			display: inline-block;
			white-space: break-spaces;
			width: 1273px;
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${alberoCoefficientiT.id.codice==null}">
			<fmt:message key="alberoCoefficientiR.label.nuovo_alberoCoefficientiR.title" />
		</c:if> 
		<c:if test="${alberoCoefficientiT.id.codice!=null}">
			<fmt:message key="alberoCoefficientiR.label.dettaglio_alberoCoefficientiR.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="alberoCoefficientiR" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoCoefficientiT" />
		    </jsp:include>
			<div class="vbg-form">
				<c:if test="${alberoCoefficientiT.id.codice==null}">
					<fieldset>
						<legend><fmt:message key="label.inserimento_intervento"/></legend>
						<div class="form-group">
				            <label>* <fmt:message key="label.descrizione"/></label>
				            <input type="text" name="descrizione" value="${alberoCoefficientiT.descrizione }">
				            <spring-form:errors path="descrizione" cssClass="error validation-feedback"/>		            
				        </div>
				        <div class="form-group">
				            <label id="label_proc">* <fmt:message key="label.alberoproc"/></label> 
				            <a href="javascript:openCloseProc()"><i class="fa fa-binoculars"></i></a>
				           	<input type="text"  id="proc_id" size="80" readonly/>
				           	<input type="hidden" name="alberoProc.id.codice" id="proc_id_hidden"/>
				           	<spring-form:errors path="alberoProc.id.codice" cssClass="error validation-feedback"/>
				            <div class="albero_proc">
				            	<div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStore" url="${pageContext.request.contextPath}/json/getAlberoprocPerRuoli.htm?time=<%=System.currentTimeMillis() %>"></div>
				            
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
				            </div>
				        </div>
				        <div class="form-group">
				            <label><fmt:message key="label.note"/></label>
				            <textarea rows="5" cols="30" name="note"></textarea>		            
				        </div>
				        <div class="form-group">
				            <label><fmt:message key="label.attivo" /></label>
				            <input type="checkbox" id="attivo_id" name="attivo" ${alberoCoefficientiT.attivo?'checked':''} onchange="changeValue()">
				            <spring-form:errors path="attivo" cssClass="error validation-feedback"/>			            
				        </div>
				        <div class="form-group">
				        	<label><fmt:message key="alberoCoefficientiT.label.copiaDa" /></label>
				        	<select name="codiceCopiaTestata">
				        		<option value="0"><fmt:message key="alberoCoefficientiT.label.scegli" /></option>
				        		<c:forEach items="${listaTestata }" var="testata">				        			
				        			<option  value="${testata.id.codice }">${testata.descrizione } - ${testata.alberoProc.descrizioneCompleta }</option>				        			
				        		</c:forEach>
				        	</select>
				        </div>
			        </fieldset>
				</c:if>
				<c:if test="${alberoCoefficientiT.id.codice!=null}">
					<input id="codiceTestata" type="hidden" value="${alberoCoefficientiT.id.codice}" >
			        <fieldset>		        
			            <legend><fmt:message key="label.dettaglio"/></legend>			            
			            <div class="parametriDiv">
							<div class="etichetta">
								<div><fmt:message key="label.intervento"/>:</div>
								<div><fmt:message key="label.tipo_concorso" />:</div>						
							</div>		
							<div class="parametro">      
								<div>${alberoCoefficientiT.alberoProc.scDescrizione }</div> 		 	
								<div>${alberoCoefficientiT.descrizione }</div>						
							</div>
						</div>
					</fieldset>
					<c:if test="${not isAmministratore }">
						 <fieldset>
				            <legend><fmt:message key="label.coefficienti_e_punteggi" /></legend>
				            <c:forEach items="${righeCoeff}" var="riga">
					            <c:if test="${riga.visibile }">				            
					            	<div class="form-group" id="coeff-riga" data-id-codice="${riga.id.codice}" data-valore="${riga.valore}">
							            <label>${riga.codiceCoefficente} - ${riga.descrizione}</label>
							            <input id="valore_id_${riga.id.codice}"  type="text" value="${riga.valore}" />						           
							           	<div class="riga-note"> ${riga.note}</div>
						            </div>					             
					            </c:if>				            
				            </c:forEach>				           
			            </fieldset>
					</c:if>
					
					<c:if test="${isAmministratore }">
				        <fieldset>
				            <legend><fmt:message key="label.coefficienti_e_punteggi" /></legend>		            
				            <table id="tabella-coefficienti" class="vbg-table">
								<thead>
									<tr>
										<th><fmt:message key="label.codice"/></th>					
										<th><fmt:message key="label.descrizione"/></th>							
										<th><fmt:message key="label.valore"/></th>
										<th style="width:900px;"><fmt:message key="label.note"/></th>
										<%-- <th><fmt:message key="label.tipo"/></th> --%>
										<th><fmt:message key="label.visibile"/></th>
										<th style="width: 190px;"><fmt:message key="label.azioni"/></th>				
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${righeCoeff}" var="riga">
										<tr>
											<td>${riga.codiceCoefficente}</td>
											<td>${riga.descrizione}</td>
											<td>${riga.valore}</td>
											<td>${riga.note }</td>
											<%-- <td>${riga.tipo}</td> --%>
											<td>
												<input type="checkbox" name="visibile" value="${riga.visibile }" ${riga.visibile?'checked': '' } readonly disabled>
											</td>
											<td>
												<a class="azione" style="float: none;" href="javascript:doSubmit('deleteRiga.htm?codiceRiga=${riga.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" />">
													<i class="fa fa-trash-o"></i> <fmt:message key="label.elimina" />
												</a>
												<a title="<fmt:message key="label.modifica"/>" class="modifica" href="javascript:doHref('viewRiga.htm?codice=${riga.id.codice}','')" >
													<i class="fa fa-pencil" aria-hidden="true"></i> <fmt:message key="label.modifica"/>
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
				            <div class="btn-foot">
							 	<a id="aggiungi-new-page" class="cmd-aggiungi" href="javascript:void(0)">
		                           <i class="fa fa-plus-circle"></i>
		                          <fmt:message key="label.aggiungi"/></a>
							</div>
		        		</fieldset>
	        		</c:if>  
        		</c:if>    
    		</div>
			<script type='text/javascript'>
			
			let bottoneAggiungi = document.querySelector('#aggiungi-new-page');
			let bottoneCreaRiga = document.querySelector('#aggiungi-riga');
			let proc = document.querySelector('.albero_proc');
			
			if(proc){
				proc.addClassName('show-hide');			
			}
			
			/* function mostraSalvaValore(codice){
			    			   
			   	document.querySelector('#salva_valore_'+codice).removeClassName('btn_salva');

			}
			
			async function salvaValore(codice){
			    
			    console.log("Salva");
			   	let valore = document.querySelector('#valore_id_'+codice).value;
			   	window.vbg.mostraModalCaricamento();
			   	
			   	const data = new URLSearchParams();
			 	data.append('codice',codice);
			 	data.append('valore',valore);
			   	
			   	let url = "${pageContext.request.contextPath}/alberocoefficientit/ajaxSalvaRiga.htm";
			 	const response = await fetch(url, {
					method: "POST",
	                cache: "no-cache",
	                body: data       
				});
			 	
			 	if (response.status !== 200) {
				      const errore = await response.text();
				      console.error(errore);
				      throw errore;
				    }
			 	 else{			 	   
			 		
			 	 }
			 	window.vbg.nascondiModalCaricamento();
			   
			} */
			
			 if (bottoneAggiungi) {
				    bottoneAggiungi.addEventListener("click", async (e) => {
					let testata = document.querySelector('#codiceTestata').value;
				    	console.log("Aggiungi riga",testata);
				    	doHref('createRiga.htm?codiceTestata='+testata,'');
				      
				    });
				  }
			 
			 /* if (bottoneCreaRiga){
			     bottoneCreaRiga.addEventListener('click', () =>{
				 
				 let nuovaRiga = document.querySelector('#nuova-riga');
				 document.querySelector('.btn-foot').hide();
			
				 if (nuovaRiga.getAttribute('class') =='nascosto'){
				     nuovaRiga.setAttribute('class','form-group');
				     nuovaRiga.show();
				     
				 }else{
					 const elemento = `
					        <label>
					 			<div>
					            	<input id="codCoef" type="text" name="codiceCoefficente" size="1">				            	
					            	-					            
					            	<input id="descr" type="text" name="descrizione" size="45">
				            	</div>
					        </label>
					        <input id="valore" type="text" name="valore">
					        <a id="aggiungi-campi" class="cmd-aggiungi" href="javascript:aggiungiCoefficiente()">
					        	<i class="fa fa-save"></i></i> <fmt:message key="label.salva"/>
					        </a>
					        <a id="rimuovi" class="cmd-aggiungi" href="javascript:chiudiDiv()">
					        <i class="fa fa-times"></i><fmt:message key="label.chiudi"/>
					        </a>
					        `;
			        nuovaRiga.setAttribute('class','form-group');
			        nuovaRiga.innerHTML = elemento;
				 }
			     });			    
			 }			  */
			
			 function chiudiDiv(){
			     
			     document.querySelector('#nuova-riga').hide();
			     document.querySelector('#nuova-riga').setAttribute('class', 'nascosto');
			     document.querySelector('.btn-foot').show();
			     
			 }			
			 
			async function cercaProcedimentoAjax(codiceAlberoproc, desc){
				
				console.log(codiceAlberoproc+ " "+ desc);						
				if(isNaN(codiceAlberoproc)){
					alert("Ricerca per codice. Inserire un valore numerico");
					return;
				}
				
				window.vbg.mostraModalCaricamento();
				const data = new URLSearchParams();
			 	data.append('id',codiceAlberoproc);
			 	data.append('hideDisabled', "false");
			   	
			   	let url = "${pageContext.request.contextPath}/json/getAlberoprocHelper.htm";
			 	const response = await fetch(url, {
					method: "POST",
	                cache: "no-cache",
	                body: data       
				});
			 	
			 	if (response.status !== 200) {
				      const errore = await response.text();
				      window.vbg.nascondiModalCaricamento();
				      console.error(errore);
				      throw errore;
				    }
			 	 else{			 	   
			 		let json = await response.json();
			 		window.vbg.nascondiModalCaricamento();
			 		if(json.id){
						if(json.padre == 'true'){
							alert("Procedimento non selezionabile.");
						}else{
							document.querySelector('#proc_id').value = desc;
							document.querySelector('#proc_id_hidden').value = codiceAlberoproc;		
						}						
					}else{
						alert("Procedimento non trovato o disattivato.");
				    }
			 	 }
			}
			 
			 function changeValue(){
				    
				    console.log("cambio valore");
				    let visibile =  document.querySelector('#attivo_id');
				    if(visibile.checked){
						visibile.value = true;
				    }else{
						visibile.value = false;
				    }
				}			 
			     
		     let bottoneSalva = document.querySelector('#aggiungi-campi');
		     
			async function aggiungiCoefficiente(){
			 	console.log("Aggiungi");
			 	
			 	 window.vbg.mostraModalCaricamento();
			 	 let codiceCoeff = document.querySelector('#codCoef').value,
			 		descrizione =  document.querySelector('#descr').value,
					valore = document.querySelector('#valore').value,
					codiceTestata = document.querySelector('#codiceTestata').value;
			 	 if(codiceCoeff =='' || descrizione== '' || valore ==''){
			 	     
			 	 }
			 	const data = new URLSearchParams();
			 	data.append('codiceCoeff',codiceCoeff);
			 	data.append('descrizione',descrizione);
			 	data.append('valore',valore);
			 	data.append('codiceTestata',codiceTestata);
			 	let url = "${pageContext.request.contextPath}/alberocoefficientit/ajaxInsertRiga.htm";
			 	const response = await fetch(url, {
					method: "POST",
	                cache: "no-cache",
	                body: data       
				});
			 	 if (response.status !== 200) {
				      const errore = await response.text();
				      console.error(errore);
				      throw errore;
				    }
			 	 else{
			 	 	window.location.reload(); 
			 	 }
		     }
			
			function openCloseProc(){
			    
			    console.log(proc);
			    if(proc.hasClassName('show-hide')){
					proc.removeClassName('show-hide');
			    }else{
					proc.addClassName('show-hide');
			    }			    	
			}
			
			function salvaListaValori(){
			    window.vbg.mostraModalCaricamento();
			    
			    document.querySelectorAll('#coeff-riga').forEach(async e=>  {
					
					let codice = e.dataset.idCodice;
					let valore = document.querySelector('#valore_id_'+codice).value;
			    	
					console.log("Aggiorna "+codice+ " valore: "+ valore);
					const data = new URLSearchParams();
				 	data.append('codice',codice);
				 	data.append('valore',valore);
				   	
				   	let url = "${pageContext.request.contextPath}/alberocoefficientit/ajaxSalvaRiga.htm";
				 	const response = await fetch(url, {
						method: "POST",
		                cache: "no-cache",
		                body: data       
					});
				 	
				 	if (response.status !== 200) {
					      const errore = await response.text();
					      console.error(errore);
					      throw errore;
					    }
				 	 
				 	window.vbg.nascondiModalCaricamento();
			    
			    });    
			    
			}
		     	 
			</script>	
		</spring-form:form>
	</div>
	<div class="form-button">
		<c:if test="${alberoCoefficientiT.id.codice==null}">
			<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert"/></a>
		</c:if>
		<c:if test="${alberoCoefficientiT.id.codice!=null}">
			<a class="btn btn-primary" href="javascript:salvaListaValori()"><fmt:message key="button.update"/></a>
		</c:if>
	    <a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
	</div>	
</body>
</html>