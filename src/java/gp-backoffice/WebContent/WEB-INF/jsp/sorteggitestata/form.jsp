<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.AlberoprocSorteggiHelper"%>
<%@page import="java.util.List"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.SorteggitestataCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Sorteggitestata"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
				<fmt:message key="sorteggitestata.label.nuovo_sorteggitestata.title" />
			</c:if> 
			<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">
				<fmt:message key="sorteggitestata.label.dettaglio_sorteggitestata.title" />
			</c:if>
		</title>
		<style>
			.endo-proc-scelti{
				word-break: break-word;
			    white-space: pre-wrap;
			    -moz-white-space: pre-wrap;
				max-width: 600px;
				min-width: 600px;
				float: left;
			}
			
			.sorteggia-group {
				text-align: right;
				padding-right: var(--double-padding);
			}

			#sorteggidettaglio_id th{
				cursor: pointer;
			}
		</style>
		<script src="https://cdn.sheetjs.com/xlsx-0.20.1/package/dist/xlsx.full.min.js"></script>
        <%
        SorteggitestataCommand sorteggitestata = (SorteggitestataCommand)request.getAttribute("sorteggitestata");
        String controlliJs = "";
        if(sorteggitestata.getSorteggitestataFilter()!=null){
            if(sorteggitestata.getSorteggitestataFilter().getAlberoprocSorteggiHelpers()!=null && sorteggitestata.getSorteggitestataFilter().getAlberoprocSorteggiHelpers().size()>0){
                List<AlberoprocSorteggiHelper> helpers = sorteggitestata.getSorteggitestataFilter().getAlberoprocSorteggiHelpers();
                for (AlberoprocSorteggiHelper ah : helpers) {
                    controlliJs +=  "\n assegnaInterventoM('"+ah.getCodiceAlberoproc()+"', '"+ah.getDescrizioneAlberoproc()+"', '"+ah.getPeso()+"', '"+BooleanUtils.toBoolean(ah.getNecessario())+"','"+ah.getScCodice()+"');";
                }               
            }
        }           
        %>
        <script type="text/javascript">
            let messageElimina = '<fmt:message key="label.elimina" />';
        
            vbg.ready(() => {
                document.querySelectorAll('fieldset').forEach((el) => {
                    el.classList.remove('collassato');
                });
                
                <%= controlliJs%>
                viewEscludeEstrazioni();
                viewSalva();
                inizializzaEndoDaForm();
                filtraTabella();
            });
            
            async function cercaProcedimentoAjaxM(codiceAlberoproc, descrizioneAlberoproc){
                
                if(isNaN(codiceAlberoproc)){
                    alert("Ricerca per codice. Inserire un valore numerico");
                    return;
                }
                const data = new URLSearchParams();
                data.append('id',codiceAlberoproc);
                let response = await fetch('../json/getAlberoprocHelper.htm', {
                    method: "POST",
                     cache: "no-cache",
                     body: data
                });
                if(response.status == 200){
                      let resp = await response.text();
                      let json = resp.evalJSON();
                      if(json.id){
                        assegnaInterventoM(codiceAlberoproc, descrizioneAlberoproc,0,'false', json.scCodice);
                      }else{
                        alert("Procedimento non trovato o disattivato.");
                      }
                }else{
                    let resp = await response.text();
                    alert("Errore nella ricerca del procedimento!");
                    console.log("Errore", resp);
                }           
            }           
        
            function assegnaInterventoM(codiceAlberoproc, descrizioneAlberoproc, peso, necessario, scCodice){
                
                let elementId = 'scId_' + codiceAlberoproc +'_'+ new Date().getTime();
                if(!peso){
                    peso = 1;
                }                   
                if(!necessario){
                    necessario="false";
                }
                let idElemento='scId_'+codiceAlberoproc +"_"+ new Date().getTime();             
                let selectedSi = 'selected';
                let selectedNo = 'selected';
                if(necessario=='false'){
                    selectedNo='';
                }else{
                    selectedSi='';
                }
                let scCodiceHTML = '<input type="hidden" id="sccodice_'+idElemento+'" name="helper.scCodice" value="SC_CODICE_VALUE" />';
                let selecthtml = '<select style="${filtroobbligatorio} name="helper.necessario" id="sel_'+idElemento+'" onchange="impostaPeso(this,\''+idElemento+'\')"><option '+selectedNo+' value="false">No</option><option '+selectedNo+' value="true">Si</option></select>';
                let pesohtml = '<input style="${filtropeso}" type="text" onblur="validaPeso(this,\''+idElemento+'\')" id="peso_'+idElemento+'" name="helper.peso" value="PESO_VALUE" />';
                let tmpl = '<tr id="'+idElemento+'"><td><a class="eliminaRiga" href="javascript:eliminaInterventoM(\''+idElemento+'\')" title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a>'+scCodiceHTML+'<input type="hidden" name="helper.codiceAlberoproc" value="CODICE_ALBEROPROC_VALUE" /><input type="hidden" name="helper.descrizioneAlberoproc" value="DESCRIZIONE_ALBEROPROC_VALUE" />DESCRIZIONE_ALBEROPROC_VALUE </td><td>'+pesohtml+'</td><td>'+selecthtml+'</td></tr>';
                tmpl = tmpl.replace(/CODICE_ALBEROPROC_VALUE/g, codiceAlberoproc)
                            .replace(/DESCRIZIONE_ALBEROPROC_VALUE/g,descrizioneAlberoproc )
                            .replace(/PESO_VALUE/g,peso)
                            .replace(/NECESSARIO_VALUE/g, necessario)
                            .replace(/SC_CODICE_VALUE/g, scCodice);
                document.querySelector('#table_lista_interventi').querySelector('tr').insertAdjacentHTML("afterend",tmpl);
            }   
            
            function validaPeso(obj, idElemento){
                let elselect = document.querySelector('#sel_'+idElemento);
                let valore = document.querySelector(obj).val();
                if(!(valore=="1" || valore =="2")){
                    alert("Attenzione!! sono ammessi solamente valori 1 e 2");
                    impostaPeso(elselect, idElemento);
                    return;
                }
                
                let valoreSelect = elselect.val();
                if(valoreSelect=="true" && valore=="2"){
                    alert("Attenzione!! E\' selezionato come intervento obbligatorio ed è ammesso solamente il valore 1");
                    impostaPeso(elselect, idElemento);
                    return;
                }                   
            }
            
            function impostaPeso(obj,idElemento){
                
                if(jQuery(obj).val()=='true'){                      
                    document.querySelector('#peso_'+idElemento).val("1");
                }                   
            }
            
            function eliminaInterventoM(elementId){     
                if(confirm('<fmt:message key="javascript.confirm.delete" />')){
                    document.querySelector('#'+elementId).remove();
                }
            }
            
            function viewEscludeEstrazioni(){
                
                if(document.querySelector('#escludeIstanze_id')){
                    if(document.querySelector('#escludeIstanze_id').checked){
                        if(document.querySelector('#listaCategorieEstrazioni_id')){
                            document.querySelector('#listaCategorieEstrazioni_id').appear();
                        }
                        if(document.querySelector('#listaEstrazioni_id')){
                            document.querySelector('#listaEstrazioni_id').appear();
                        }
                    }else{
                        if(document.querySelector('#listaCategorieEstrazioni_id')){
                            document.querySelector('#listaCategorieEstrazioni_id').fade();
                        }
                        if(document.querySelector('#listaEstrazioni_id')){
                            document.querySelector('#listaEstrazioni_id').fade();
                        }
                    }
                }
            }
            
            function viewSalva(){
                let chkSalva = document.querySelector('#salva_id');
                if(chkSalva) {
	                if(chkSalva.checked){
	                    document.querySelector('#descrizione_id').appear();
	                    document.querySelector('#categoria_id').appear();
	                }else{
	                    document.querySelector('#descrizione_id').fade();
	                    document.querySelector('#categoria_id').fade();
	                }
                }
            }               
            
            function view(){
                if(document.getElementById("algoritmo_id").value!=null && document.getElementById("algoritmo_id").value!=""){
                    codiceAlgoritmo = document.getElementById("algoritmo_id").value;
                    doHref('create.htm?codiceAlgoritmo='+ codiceAlgoritmo +'','');
                }else{
                    vis_errore(document.getElementById("algoritmo_id"),"Algoritmo non Specificato");
                }
            }
            
            function inventarioCallBack (inputField,listItem){
                
                let codiceInventario = listItem.id;
                let inventario = listItem.textContent;      
                impostaEndo(codiceInventario, inventario);
                document.getElementById('ricerca_endo_id').value='';
                document.getElementById('ricerca_endo_hidden').value='';
            }
            
            function eliminaEndo(idEndo){
            	let ulEndoprocedimenti = document.querySelector('#lista_endo_selezionati_id');
            	let li = ulEndoprocedimenti.querySelector('li[data-id="'+idEndo+'"]');
            	ulEndoprocedimenti.removeChild(li);
            	
            	ricalcolaIndiciCampiEndo();
            }
            
            
            function getEndoIdName(indice, nome){
                
                return `sorteggitestataFilter.endoSelezionati[\${indice}].\${nome}`; 
            }
            function impostaEndo(codiceInventario, inventario){
            
            	let ulEndoprocedimenti = document.querySelector('#lista_endo_selezionati_id');
            	
            	// verifico se presente prima di aggiungerlo
            	let liEndo = ulEndoprocedimenti.querySelector('li[data-id="'+ codiceInventario +'"]');
            	if(liEndo){
            		let msg = 'L\'endoprocedimento ' + inventario.replace("'","\'") + ' (' + codiceInventario + ') risulta già presente!';
            	    console.info(msg);
            	    alert(msg);
            	    return;
            	}
                
            	// lo aggiungo
                let li = document.createElement('li');
                li.setAttribute('data-id' , codiceInventario);
                li.style.display='flex';
                li.style.padding='3px';
                
                let divEndo = document.createElement('div');
                divEndo.appendChild(document.createTextNode(inventario));
                li.appendChild(divEndo);
                
                
                let iTrash = document.createElement('i');
                iTrash.classList.add('fa');
                iTrash.classList.add('fa-trash');
                
                let divAzioni = document.createElement('div');
                divAzioni.appendChild(iTrash);
                divAzioni.appendChild(document.createTextNode(messageElimina));
                divAzioni.style.color = "var(--accent-color)";
                divAzioni.style.cursor = "pointer";
                divAzioni.style.paddingLeft = "50px";
                divAzioni.addEventListener('click',(evt) => {
                    evt.preventDefault();
                    eliminaEndo(codiceInventario);
                });
                                
                li.appendChild(divAzioni);
                
                let hiddenEndoId = document.createElement('input');
                hiddenEndoId.classList.add('hidden_endo_id');
                hiddenEndoId.setAttribute("type", "hidden");
                hiddenEndoId.value = codiceInventario;
                
                let hiddenEndoDescrizione = document.createElement('input');
                hiddenEndoDescrizione.classList.add('hidden_endo_descrizione');
                hiddenEndoDescrizione.setAttribute("type", "hidden");
                hiddenEndoDescrizione.value = inventario;
                
                li.appendChild(hiddenEndoId);
                li.appendChild(hiddenEndoDescrizione);
                
                ulEndoprocedimenti.appendChild(li);
                
                ricalcolaIndiciCampiEndo();
            }
            
            const assegnaNome = (element,index) => {
                    let hiddenId = element.querySelector('input[type="hidden"].hidden_endo_id');
                    hiddenId.setAttribute("name", "sorteggitestataFilter.endoSelezionati["+index+"].id");
                    
                    let hiddenDescrizione = element.querySelector('input[type="hidden"].hidden_endo_descrizione');
                    hiddenDescrizione.setAttribute("name", "sorteggitestataFilter.endoSelezionati["+index+"].descrizione");
            };            
            function ricalcolaIndiciCampiEndo(){
            	
            	document.querySelectorAll('#lista_endo_selezionati_id>li').forEach(assegnaNome);
            	
            }
            
            function inizializzaEndoDaForm(){
                
                <c:forEach items="${sorteggitestata.sorteggitestataFilter.endoSelezionati }" var="endo">
                    impostaEndo(${endo.id},"${endo.descrizione}");
                </c:forEach>                
                
            }   
        
            function esportaFile(){
            /* Create worksheet from HTML DOM TABLE */
                const table = document.getElementById('sorteggidettaglio_id');
                const opts = {
                        sheet: 'Dettaglio',
                        dateNF: 'yyyy-mm-dd;@',
                        cellDates: true, 
                        raw: true
                      };
                const wb = XLSX.utils.table_to_book(table, opts);
        
                /* Export to file (start a download) */
                XLSX.writeFile(wb, 'sorteggio'+new Date().getTime()+'.xlsx');
            }
        
            // Ordino le colonne della tabella
        
            const getCellValue = (tr, idx) => tr.children[idx].innerText || tr.children[idx].textContent;
        
            const comparer = (idx, asc) => (a, b) => ((v1, v2) => 
                v1 !== '' && v2 !== '' && !isNaN(v1) && !isNaN(v2) ? v1 - v2 : v1.toString().localeCompare(v2)
                )(getCellValue(asc ? a : b, idx), getCellValue(asc ? b : a, idx));
        
            // do the work...
            document.querySelectorAll('th').forEach(th => th.addEventListener('click', (() => {
                //const table = th.closest('table');
                const table = document.querySelector('#sorteggidettaglio_id');
                const tbody = table.querySelector('tbody');
                Array.from(tbody.querySelectorAll('tr'))
                    .sort(comparer(Array.from(th.parentNode.children).indexOf(th), this.asc = !this.asc))
                    .forEach(tr => tbody.appendChild(tr) );
                    })
            ));
            
            function filtraTabella(){
            	let testo = document.getElementById("input_ricerca").value.toUpperCase();
                let valoreSorteggiata = document.getElementById("selectSorteggia").value; 
                
                let table = document.getElementById("sorteggidettaglio_id");
                let tr = table.getElementsByTagName("tr");
                
                for (let i = 1; i < tr.length; i++) {
                	let matchTesto = false;
                    let matchSorteggiata = true;
                    
                    /* ---- TESTO ---- */
                    if (testo !== "") {
                        let td = tr[i].getElementsByTagName("td");
                        for (let cell of td) {
                            let txtValue = cell.textContent || cell.innerText;
                            if (txtValue.toUpperCase().includes(testo)) {
                                matchTesto = true;
                                break;
                            }
                        }
                    } else {
                        matchTesto = true;
                    }
                    
                    /* ---- FILTRO SORTEGGIATA ---- */
                    if (matchTesto === true && valoreSorteggiata !== "") {
                        let td = tr[i].getElementsByClassName("sorteggiata");
			            matchSorteggiata = false;
			
			            for (let cell of td) {
			                let txtValue = cell.textContent || cell.innerText;
			                if (txtValue === valoreSorteggiata) {
			                    matchSorteggiata = true;
			                    break;
			                }
			            }
			        }
                    
                    tr[i].style.display = (matchTesto && matchSorteggiata) ? "" : "none";
                }
            }
                                
            function visualizzaRigheTabella(){
                table = document.getElementById("sorteggidettaglio_id");
                tr = table.getElementsByTagName("tr");
                for (i = 1; i < tr.length; i++) {
                    tr[i].style.display = "";
                }                       
            }

        </script>
	</head>
    <body>
		<span class="titoloPagina">
			<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
				<fmt:message key="sorteggitestata.label.nuovo_sorteggitestata.title" />
			</c:if> 
			<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">
				<fmt:message key="sorteggitestata.label.dettaglio_sorteggitestata.title" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<%
		    String escludeestrazioni="display:none;";
			String salva="display:none;";		
		%>
		<div id="subcontent">
			<div class="vbg-form">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			       <jsp:param name="commandName" value="sorteggitestata" />
			    </jsp:include>
			    <c:set var="TIPO_ALGORITMO" value="${sorteggitestata.codiceAlgoritmo}" scope="page"></c:set>
				<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
					<spring-form:form commandName="sorteggitestata" name="inviodati">
                        <fieldset class="collassabile">
                            <legend><fmt:message key="label.dati_principali" /></legend>
                            <div class="form-group" id="elementIdBeforeCombo">						
								<jsp:include page="../includes/comboComuni.jsp">					
									<jsp:param name="mostraTutti" value="true" />
									<jsp:param name="readOnly" value="false" />
									<jsp:param name="commandPropertyPath" value="sorteggitestataFilter.comune" />
									<jsp:param name="colspan" value="4" />
									<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />						
								</jsp:include>
							</div>
							<div class="form-group">
								<label><fmt:message key="label.data_sorteggio" /></label>
								<spring-form:input  tabindex="2" id="dataSorteggio_id" path="sorteggitestataFilter.dataSorteggio" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
								<init:calendar idImage="caldata1" idInput="dataSorteggio_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
								<spring-form:errors	path="sorteggitestataFilter.dataSorteggio" cssClass="error" />		
							</div>
							<c:if test="${sorteggilr152013}">
								<div class="form-group">
									<label><fmt:message key="label.algoritmo" /></label>
									<spring-form:select id="algoritmo_id" path="codiceAlgoritmo" onchange="view()"> 
										<spring-form:option value="0" ><fmt:message key='label.standard' /></spring-form:option>		
											<spring-form:option value="1" ><fmt:message key='label.regione_emilia_romagna' /></spring-form:option> 										
									</spring-form:select>
									<spring-form:errors path="codiceAlgoritmo" cssClass="error"/>			
								</div>		
							</c:if>
							<c:if test="${!sorteggilr152013}">
								<spring-form:hidden path="codiceAlgoritmo" />
							</c:if>
                        </fieldset>
						<fieldset id="filtriintervallotemporale_id" style="${filtriintervallotemporale}" class="collassabile">
                            <legend><fmt:message key="label.presentazione_pratica" /></legend>			
                                <div class="form-group">
									<label><fmt:message key="label.data_istanza_dal" /></label>					
									<spring-form:input id="intervalloCampionamentoDal_id" path="sorteggitestataFilter.intervalloCampionamentoDal" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
					   				<init:calendar idImage="caldata4" idInput="intervalloCampionamentoDal_id" imagePath="/images/cal.gif" textKey="label.calendar"/>		   				
					   				<spring-form:errors	path="sorteggitestataFilter.intervalloCampionamentoDal" cssClass="error" />
									<label><fmt:message key="label.data_istanza_al" /></label>					
									<spring-form:input id="intervalloCampionamentoAl_id" path="sorteggitestataFilter.intervalloCampionamentoAl" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
					   				<init:calendar idImage="caldata5" idInput="intervalloCampionamentoAl_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
					   				<spring-form:errors	path="sorteggitestataFilter.intervalloCampionamentoAl" cssClass="error" />					
								</div>			
						</fieldset>			
						<fieldset id="filtriprocedimento1_id" style="${filtriprocedimento}" class="collassabile">
                            <legend><fmt:message key="label.interventi" /></legend>
				            <div class="form-group">
	                            <label style="width: 100% !important;"><fmt:message key="label.seleziona_interventi" /></label>
	                            <div>                           
	                                <div dojoType="dojo.data.ItemFileReadStore" jsId="alberoprocStoreM" url="${pageContext.request.contextPath}/json/getAlberoproc.htm?time=<%=System.currentTimeMillis() %>"></div>
	                                <div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModelM" store="alberoprocStoreM"    query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>" rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
	                                <div dojoType="dijit.Tree" id="treeM" model="alberoprocModelM" />
	                                <script type="dojo/method" event="onClick" args="item">
                                        if(item.id != '0'){
                                            var itemId = alberoprocStoreM.getValue(item, "id");
                                            var descrizioneEstesa = alberoprocStoreM.getValue(item, "descrizioneEstesa");
                                            var padre = alberoprocStoreM.getValue(item, "padre");
                                            cercaProcedimentoAjaxM(itemId, descrizioneEstesa);
                                        }
                                    </script>                       
	                                <script type="dojo/method" event="getIconClass" args="item, opened">
                                        if(item.id != '0'){
                                            var dis = 'false';
                                            if(item){
                                                dis = alberoprocStoreM.getValue(item, "disabilitato");
                                            }
                                            if(dis == 'false'){
                                                return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
                                            }else{
                                                return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
                                            }
                                        }else{
                                            return "dijitFolderOpened"
                                        }
                                    </script>           
	                            </div>
				             </div>
				             <div class="form-group" id="lista_interventi">							
								<table id="table_lista_interventi" class="vbg-table">
								    <thead>
								        <th><fmt:message key="label.interventi_selezionati" /></th>
								        <th style="${filtropeso}"><fmt:message key="label.peso" /></th>
								        <th style="${filtroobbligatorio}"><fmt:message key="label.obbligatorio" /></th>
								    </thead>
								</table>								
				             </div>				             
			            </fieldset>			
						<fieldset id="filtriistanza_id" style="${filtriistanza}" class="collassabile">
                            <legend><fmt:message key="label.filtri_istanza" /></legend>			
							<div class="form-group" style="${filtroarchiviopratiche}">
								<label><fmt:message key="label.archivio_pratiche" /></label>											
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="archiviopratiche_id" />		
									<jsp:param name="propertyPath" value="sorteggitestataFilter.tipiarchivioistanza" />				
									<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.tipiarchivioistanza.archivio" />
									<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.tipiarchivioistanza.id.codice" />
									<jsp:param name="autocompleterAjax" value="findTipiarchivioistanze.htm" />							
									<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
								</jsp:include>
							</div>
							<div class="form-group" style="${filtrotipologiaintervento}">
								<label><fmt:message key="label.tipologia_intervento" /></label>
								<jsp:include page="../includes/searchAlberoProc.jsp">
                                    <jsp:param name="propertyPath" value="sorteggitestataFilter.alberoproc.vwAlberoproc" />								
									<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.alberoproc.vwAlberoproc.scDescrizione" />
									<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.alberoproc.id.codice" />
									<jsp:param name="isSelectLeafDisable" value="true" />
									<jsp:param name="isSelectNodoPadre" value="true" />													
						 		</jsp:include>
							</div>
                            <div class="form-group" style="${filtroprocedura}">
								<label><fmt:message key="label.tipo_procedura" /></label>
								<jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="tipiprocedure_id" />				
									<jsp:param name="propertyPath" value="sorteggitestataFilter.procedura" />			
									<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.procedura.procedura" />
									<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.procedura.id.codice" />
									<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=true" />
									<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
								</jsp:include>
                            </div>
                            <div class="form-group" style="${filtroprocedura}">
                                <label>&nbsp;</label>
							    <b>
							        <fmt:message key="label.sorteggio.procedura_endo_1" /> 
							        <spring-form:select cssStyle="min-width:20px" id="andOrSelezioneEndo_id" path="sorteggitestataFilter.andOrSelezioneEndo" > 
                                           <spring-form:option value="OR" >O</spring-form:option>		
                                           <spring-form:option value="AND" >E</spring-form:option> 										
							        </spring-form:select>
							        <fmt:message key="label.sorteggio.procedura_endo_2" />
							    </b>
                            </div>
							<div class="form-group" style="${filtroprocedura}">
                                <label>&nbsp;</label>
                                <jsp:include page="../includes/autocompletergenerico-no-bind.jsp">
                                    <jsp:param name="idElemento" value="ricerca_endo" />					
										<jsp:param name="pathPropertyDescription" value="ricercaEndoSelezionati" />
										<jsp:param name="pathPropertyCode" value="ricercaEndoSelezionati.codice" />
										<jsp:param name="autocompleterAjax" value="findInventarioprocedimento.htm" />
										<jsp:param name="titleKey" value="label.ricerca_inventarioprocedimento" />
										<jsp:param name="id_help" value="help_ricerca_endo" />
										<jsp:param name="afterUpdateElement" value="inventarioCallBack" />
										<jsp:param name="autocompleterInputSize" value="100" />
                                </jsp:include>
                            </div>
                            <div class="form-group" style="${filtroprocedura}">
                                <label>&nbsp;</label>
                                <ul id="lista_endo_selezionati_id" style="display:inline-block;"></ul>
                            </div>
							<div class="form-group" style="${filtrostato}">
								<label><fmt:message key="label.stato" /></label>
								<spring-form:select id="chiusura_id" path="sorteggitestataFilter.chiusura.id.codicestato" >
									<spring-form:option value=""><fmt:message key='label.tutte' /></spring-form:option>
									<c:forEach items="${sorteggitestata.statiistanzaList}" var="statiistanza">
										<spring-form:option value="${statiistanza.id.codicestato}" label="${statiistanza.stato}"></spring-form:option>
									</c:forEach>
								</spring-form:select>
							</div>
							<div class="form-group" style="${filtrostato}">
								<label><fmt:message key="label.nature_endo"/></label>	
								<spring-form:select id="natureendo_id" path="sorteggitestataFilter.naturaendo" multiple="multiple">
									<c:forEach items="${sorteggitestata.naturaendoList}" var="endo">						
										<spring-form:option value="${endo.id.codice}" label="${endo.natura}"></spring-form:option>
									</c:forEach>
								</spring-form:select>
								<fmt:message key="label.select_multiplo" />					
							</div>			
                        </fieldset>
						<fieldset id="filtrimovimento_id" style="${filtrimovimento}" class="collassabile">
							<legend><fmt:message key="label.filtri_movimento" /></legend>			
							<div class="form-group" style="${filtrodatamovimentodalAl}">
								<label><fmt:message key="label.data_movimento_dal" /></label>					
								<spring-form:input id="dataDal_id" path="sorteggitestataFilter.dataDal" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
				   				<init:calendar idImage="caldata2" idInput="dataDal_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
				   				<init:help idHelp="help2" textKey="sorteggitestata.help.dataDal"/>
				   				<spring-form:errors	path="sorteggitestataFilter.dataDal" cssClass="error" />
												
								<label><fmt:message key="label.data_movimento_al" /></label>					
								<spring-form:input id="dataAl_id" path="sorteggitestataFilter.dataAl" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
				   				<init:calendar idImage="caldata3" idInput="dataAl_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
				   				<init:help idHelp="help3" textKey="sorteggitestata.help.dataAl"/>
				   				<spring-form:errors	path="sorteggitestataFilter.dataAl" cssClass="error" />					
							</div>
							<div class="form-group" style="${filtrotipomovimento}">
								<label><fmt:message key="label.tipomovimento" /></label>												
								<jsp:include page="../includes/tipimovimentosearch.jsp">
									<jsp:param name="idElemento" value="tipoMovimentoInputId" />
									<jsp:param name="pathTipomovimento"	value="sorteggitestataFilter.tipoMovimento" />
								</jsp:include>
								<c:if test="${TIPO_ALGORITMO eq 1}">
								<fmt:message key="sorteggitestata.help.tipo_movimento" />
								</c:if>					
							</div>
							<div class="form-group" style="${filtrotiporicercamovimento}">
								<label><fmt:message key="label.tipo_ricerca_mov" /></label>					
								<spring-form:select path="sorteggitestataFilter.tipoRicercaMovimento" > 
									<spring-form:option value="0" ><fmt:message key='sorteggitestata.label.ricerca_mov_effettuati' /></spring-form:option>
									<spring-form:option value="1"><fmt:message key='sorteggitestata.label.ricerca_mov_da_effettuare' /></spring-form:option>
									<spring-form:option value="2" ><fmt:message key='sorteggitestata.label.ricerca_tutti' /></spring-form:option>
								</spring-form:select>
								<spring-form:errors path="sorteggitestataFilter.tipoRicercaMovimento" cssClass="error"/>				
							</div>
							<div class="form-group" style="${filtrotipologiaesito}">
								<label><fmt:message key="label.tipologia_esito" /></label>					
								<spring-form:select path="sorteggitestataFilter.esito" > 
									<spring-form:option value="0" ><fmt:message key='label.qualsiasi' /></spring-form:option>
									<spring-form:option value="1"><fmt:message key='label.negativo' /></spring-form:option>
									<spring-form:option value="2" ><fmt:message key='label.positivo' /></spring-form:option>
								</spring-form:select>
								<spring-form:errors path="sorteggitestataFilter.esito" cssClass="error"/>					
							</div>			
						</fieldset>
						<fieldset id="filtriestrazione_id" style="${filtriestrazione}" class="collassabile">
							<legend><fmt:message key="label.filtri_estrazione" /></legend>			
							<div class="form-group" style="${filtripercentuale}">
								<label><fmt:message key="label.percentuale" /></label>					
								<spring-form:input cssStyle="text-align:right;" id="percentuale_id" path="sorteggitestataFilter.percentuale" size="3" maxlength="3" onchange="javascript:checkNumberInt(this)" />
								<fmt:message key="label.percento" />
								<spring-form:errors path="sorteggitestataFilter.percentuale" cssClass="error"/>				
							</div>	
							<div class="form-group" style="${filtriestrazionegruppi}">
								<label><fmt:message key="label.estrazione_gruppi" /></label>					
								<spring-form:input cssStyle="text-align:right;" id="gruppiIstanze_id" path="sorteggitestataFilter.gruppiIstanze" size="3" maxlength="3" onchange="javascript:checkNumberInt(this)" />
								<spring-form:errors path="sorteggitestataFilter.gruppiIstanze" cssClass="error"/>
								<fmt:message key="label.istanze" />
							</div>	
							<div class="form-group" style="${filtriarrotondamento}">
								<label><fmt:message key="label.arrotondamento" /></label>					
								<spring-form:select path="sorteggitestataFilter.arrotondamento" > 
									<spring-form:option value="0" ><fmt:message key='label.approssimazione_difetto' /></spring-form:option>
									<spring-form:option value="1"><fmt:message key='label.approssimazione_eccesso' /></spring-form:option>
									<spring-form:option value="2" ><fmt:message key='label.arrotondamento_intero' /></spring-form:option>
								</spring-form:select>
								<spring-form:errors path="sorteggitestataFilter.arrotondamento" cssClass="error"/>				
							</div>			
						</fieldset>			
						<c:if test="${not empty sorteggitestata.sorteggitestataList or not empty sorteggitestata.sorteggiCategorieList}">
							<fieldset id="filtriesclusioneistanze_id" style="${filtriesclusioneistanze}" class="collassabile">
								<legend><fmt:message key="label.filtri_esclusione_istanze" /></legend>			
								<div class="form-group">
									<label><fmt:message key="label.esclude" /></label>
									<spring-form:checkbox id="escludeIstanze_id" path="sorteggitestataFilter.escludeIstanze" onclick="viewEscludeEstrazioni();"/>
									<init:help idHelp="help4" textKey="sorteggitestata.help.escludeIstanze"/>
									<spring-form:errors path="sorteggitestataFilter.escludeIstanze" cssClass="error" />
								</div>
								<c:if test="${not empty sorteggitestata.sorteggitestataList}">
									<div class="form-group" id="listaEstrazioni_id" style="<%=escludeestrazioni %>">
										<label><fmt:message key="label.lista_estrazioni" /></label>						
										<spring-form:select id="listaEstrazioni_id" path="sorteggitestataFilter.listaEstrazioni" multiple="true" size="${fn:length(sorteggitestata.sorteggitestataList)}">
											<spring-form:options items="${sorteggitestata.sorteggitestataList}" itemValue="id.codice" itemLabel="stDescrizione"></spring-form:options>				
										</spring-form:select>						
									</div>				
								</c:if>				
								<c:if test="${not empty sorteggitestata.sorteggiCategorieList}">
									<div class="form-group" id="listaCategorieEstrazioni_id" style="<%=escludeestrazioni %>">
										<label><fmt:message key="label.lista_categorie" /></label>						
										<spring-form:select id="listaCategorieEstrazioni_id" path="sorteggitestataFilter.listaCategorieEstrazioni" multiple="true" size="${fn:length(sorteggitestata.sorteggiCategorieList)}">
											<spring-form:options items="${sorteggitestata.sorteggiCategorieList}" itemValue="id.codice" itemLabel="descrizione"></spring-form:options>				
										</spring-form:select>								
									</div>		 
								</c:if>		
							</fieldset>
						</c:if>
						<fieldset class="collassabile">
							<legend><fmt:message key="label.dati_salvataggio_sorteggio" /></legend>			
							<div class="form-group">
								<label><fmt:message key="label.salva" /></label>
								<spring-form:checkbox id="salva_id" path="sorteggitestataFilter.salva" onclick="viewSalva();"/>
								<init:help idHelp="help5" textKey="sorteggitestata.help.salva"/>
								<spring-form:errors path="sorteggitestataFilter.salva" cssClass="error" />					
							</div>
							<div class="form-group" id="descrizione_id" style="<%=salva %>">
								<label><fmt:message key="label.descrizione" /></label>					
								<spring-form:input id="descrizione_id" path="sorteggitestataFilter.descrizione" size="70" />						
								<spring-form:errors path="sorteggitestataFilter.descrizione" cssClass="error"/>					
							</div>				
							<div class="form-group" id="categoria_id" style="<%=salva %>">
								<label><fmt:message key="label.categoria" /></label>											
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="categoria_id" />		
									<jsp:param name="propertyPath" value="sorteggitestataFilter.categoria" />				
									<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.categoria.descrizione" />
									<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.categoria.id.codice" />
									<jsp:param name="autocompleterAjax" value="findSorteggiCategorie.htm" />							
									<jsp:param name="titleKey" value="label.ricerca_categoria" />
								</jsp:include>					
							</div>		
						</fieldset>			
					<script type='text/javascript'>				
				
						
						
					</script>
					</spring-form:form>
				</c:if>
			</div>
		</div>			
		<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">
			<%-- Testata --%>
			<div class="vbg-form">
				<div class="form-group">		
					<div class="parametriDiv">
						<div class="etichetta">    		 
							<div><fmt:message key="label.descrizione" />:</div>
							<div><fmt:message key="label.data_sorteggio" />:</div>
				        </div>
				        <div class="parametro">
				      		 <div>&nbsp;${sorteggitestata.entity.stDescrizione}</div>
				      		 <div>&nbsp;<fmt:formatDate value="${sorteggitestata.entity.stDatasorteggio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>     		 
			        	</div>
					 </div>
				 </div>
		 		<br class="clear"/>
		 		<spring-form:form commandName="sorteggitestata" name="inviodati">
					<table>
						<tr>
							<td>
								<fmt:message key="label.documento_da_allegare" />
							</td>
							<td>
							<jsp:include page="../includes/oggetti.jsp">
			       				<jsp:param name="idElemento" value="oggettoIdCodice" />
			   					<jsp:param name="codiceOggetto" value="${sorteggitestata.entity.oggetto.id.codice}" />
			   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
			   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
			   				</jsp:include>
		    				<spring-form:hidden path="entity.oggetto.id.codice" id="oggetto_id_codice"/>
		    				<spring-form:hidden path="entity.oggetto.nomefile" id="oggetto_nomefile"/>
		    				<spring-form:errors path="entity.oggetto" cssClass="error"/>
							</td>
						</tr>
						<input type="hidden" value="${sorteggitestata.entity.id.codice}" name="entity.id.codice"></input>
					</table>
				</spring-form:form>
			</div>		
		</c:if>
		<div class="form-button">		
			<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
				<a class="btn btn-primary" href="javascript:doSubmit('sorteggia.htm?sorteggia=true&sorteggidettaglio_id_f_sorteggiata=Si','',document.inviodati)"><fmt:message key="button.sorteggia" /></a>
			</c:if>
			<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">	
				<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>			
				<%-- STAMPA DOCUMENTO TIPO --%>
				<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_DOC_TIPO_SORTEGGIO());%>
				<c:set var="_URL_STAMPA" value="${URL_STAMPA}?ST_ID=${sorteggitestata.entity.id.codice}" /><c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}" />
				<a class="btn btn-primary" href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a>
				<a class="btn btn-primary"	href="javascript:doSubmit('createMovimento.htm?codice=${sorteggitestata.entity.id.codice}','',document.inviodati)"><fmt:message key="button.inserisci_movimento" /></a>		
				<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>		
		</div>
		
		<br class="clear"/>
		<br class="clear"/>
		<%-- Info --%>	
		<c:if test="${sorteggitestata.entity.sorteggitestatainfos!=null && not empty sorteggitestata.entity.sorteggitestatainfos}">	
			<%
			String displayFiltri = "display:none;";
			String styleFiltri = "";		
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_SORT_TEST_VISFILTRI_DIV)).equals("1")) {
			    displayFiltri = "";
			    styleFiltri ="sezioneDatiMeno";
			} else {
			    displayFiltri = "display:none;";
			    styleFiltri ="sezioneDatiPiu";
			}
		%>
		<div class="vbg-form">
			<a class="<%= styleFiltri %>" 
				id="id_link_filtri" 
				href="javascript:showHidePanel('filtriId', 'id_link_filtri', '<%= WebConstants.CONF_UTENTE_SORT_TEST_VISFILTRI_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="sorteggitestata.label.list_info_sorteggio.title" />">
				<label for="id_link_filtri" ><fmt:message key="sorteggitestata.label.list_info_sorteggio.title" /></label>
			</a>			
			<div id="filtriId" style="<%= displayFiltri%>">
				<fieldset>
					<table cellpadding="2" cellspacing="0" class="vbg-table">  
						<thead>
							<tr class="header">
								<th><fmt:message key="label.filtro" /></th>
								<th><fmt:message key="label.valore" /></th>	
							</tr>
						</thead>
						<tbody class="tbody" >					
						<c:forEach var="testatainfo_var" items="${sorteggitestata.entity.sorteggitestatainfos}" varStatus="testatainfoStatus">
							<tr>
								<td>${testatainfo_var.etichetta}</td>
								<td>${testatainfo_var.valore}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div> 	
		</c:if>			
		<%-- End View --%>
	
		<c:if test="${sorteggitestata.sorteggidettaglioDTOList!=null && not empty sorteggitestata.sorteggidettaglioDTOList}">
		<br class="clear"/>	
			<div class="vbg-form">	
				<%
					String displayIstSort = "display:none;";
					String styleIstSort = "";		
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV)).equals("1")) {
					    displayIstSort = "";
					    styleIstSort="sezioneDatiMeno";
					} else {
					    displayIstSort = "display:none;";
					    styleIstSort ="sezioneDatiPiu";
					}
				%>		 
				<a class="<%= styleIstSort %>" 
					id="id_link_istSort" 
					href="javascript:showHidePanel('istSortId', 'id_link_istSort', '<%= WebConstants.CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="sorteggitestata.label.list_istanze_sorteggiate.title" />">
					<label for="id_link_istSort" ><fmt:message key="sorteggitestata.label.list_istanze_sorteggiate.title" /></label>
				</a>
		
				<div id="istSortId" style="<%= displayIstSort%>">		
				<c:set var="action" value="sorteggia.htm?sorteggia=false&"/>		
					<fieldset>	
						<fieldset>
							<legend>
								<fmt:message key="label.ricerca" />
							</legend>
							<div class="form-group">
								<div class="input-icons">
									<i class="fa fa-search icon"></i> <input id="input_ricerca"
										type="text" placeholder="Cerca" onkeyup="filtraTabella()" />
								</div>
								<div class="input-help">
									<fmt:message key="label.messaggio_ricerca_tabella" />
								</div>
							</div>
							
						</fieldset>
						
					<spring-form:form commandName="sorteggitestata" name="jmesa" action="${action}">
					<div class="">
						<div class="exportButton">
							<a href="#" onclick="esportaFile()">
								<i class="fa fa-file-excel"></i>
								Esporta in excel
							</a>
						</div>
						<div class="sorteggia-group">
								<div>
									<label><fmt:message key="label.sorteggiata"/></label>
									<select id="selectSorteggia" onchange="filtraTabella()">
										<option value="">Seleziona...</option>
										<option value="Si" selected>Si</option>
										<option value="No">No</option>
									</select>
								</div>
							</div>
					</div>
					
						<table id="sorteggidettaglio_id" class="vbg-table">
							<thead>
								<tr>
									<th><fmt:message key="label.numeroistanza"/></th>
									<th><fmt:message key="label.data_presentazione"/></th>
									<th><fmt:message key="label.numero_protocollo_abbr"/></th>
									<th><fmt:message key="label.data_protocollo"/></th>
									<th><fmt:message key="label.richiedente"/></th>
									<th><fmt:message key="label.indirizzo"/></th>
									<th><fmt:message key="label.intervento"/></th>
									<th><fmt:message key="label.posizione_archivio"/></th>
									<th><fmt:message key="label.oggetto_pratica_abbr"/></th>
									<th><fmt:message key="label.operatore"/></th>
									<th><fmt:message key="label.responsabile_procedimento_abbr"/></th>
									<th><fmt:message key="label.responsabile_istruttoria"/></th>
									<th><fmt:message key="label.sorteggiata"/></th>
									<c:if test="${TIPO_ALGORITMO eq 1}">
										<th><fmt:message key="label.pratica_obbligatoria_abbr"/></th>
									</c:if>
									<th><fmt:message key="label.comune"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${sorteggitestata.sorteggidettaglioDTOList}" var="sorteggidettaglio_var">
									<tr>
										<td>${sorteggidettaglio_var.numeroIstanza}</td>
										<td><fmt:formatDate value="${sorteggidettaglio_var.dataIstanza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/></td>
										<td>${sorteggidettaglio_var.numeroProtocollo}</td>
										<td><fmt:formatDate value="${sorteggidettaglio_var.dataProtocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/></td>
										<td>${sorteggidettaglio_var.transientRichiedenteQualitaAzienda}</td>
										<td>${sorteggidettaglio_var.transientLocalizzazionePrimario}</td>
										<td>${sorteggidettaglio_var.intervento}</td>
										<td>${sorteggidettaglio_var.archivio}</td>
										<td>${sorteggidettaglio_var.lavori}</td>
										<td>${sorteggidettaglio_var.operatore}</td>
										<td>${sorteggidettaglio_var.responsabileProcedimento}</td>
										<td>${sorteggidettaglio_var.responsabileIstruttoria}</td>
										<td class="sorteggiata">${sorteggidettaglio_var.sorteggiata ? 'Si' : 'No'}</td>
										<c:if test="${TIPO_ALGORITMO eq 1}">
											<td>${sorteggidettaglio_var.flagInterventoObbligatorio == true ? 'Si' : 'No'}</td>
										</c:if>
										<td>${sorteggidettaglio_var.comune}</td>
									</tr>
								</c:forEach>
							</tbody>					
						</table>				
						
						<input type="hidden" value="${sorteggitestata.entity.id.codice}" name="codice" />					
					</spring-form:form>	
					</fieldset>
				</div>
			</div>
		</c:if>	
	</body>
</html>