<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
            <html xmlns="http://www.w3.org/1999/xhtml">

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <script type="module"
                    src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>"
                    defer></script>
                <title>
                    <fmt:message key="configurazioni_metadati.label.title" />
                </title>
                <style>
                    @keyframes flash-highlight {
                        0% {
                            background-color: rgb(238, 238, 145);
                           
                        }

                        100% {
                            background-color: transparent;
                           
                        }
                    }

                    .flash {
                        animation: flash-highlight 0.5s ease-out 1;
                  
                    }

                    #row_num {
                        background-position: 10px 10px;
                        background-repeat: no-repeat;
                        padding-left: 40px;
                        margin-top: 12px;
                        margin-bottom: 12px;
                        background-color: #e1ebf4;
                    }
                /*    @supports (-moz-appearance: none) {
	                   input[list] {
						  background: url("data:image/svg+xml;utf8,<svg fill='gray' height='20' viewBox='0 0 24 24' width='20' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>") no-repeat right center;
						  background-size: 2em;
						  padding-right: 2em;
	    				
						}
					}
					 */
					.datalist-wrapper {
					  position: relative;
					  display: inline-block;
					  width: 200px;
					}
					
					.datalist-wrapper input {
					  width: 100%;
					  box-sizing: border-box;
					  padding-right: 30px;
					}
					
					.dropdown-arrow {
					  position: absolute;
					  right: 0;
					  top: 0;
					  height: 100%;
					  width: 30px;
					  border: none;
					  cursor: pointer;
					  background: url("data:image/svg+xml;utf8,<svg fill='gray' height='20' viewBox='0 0 24 24' width='20' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>") no-repeat right center;
					}
					
					.datalist-options {
					  position: absolute;
					  top: 100%;
					  left: 0;
					  right: 0;
					  border: 1px solid #ccc;
					  background: white;
					  max-height: 150px;
					  overflow-y: auto;
					  z-index: 1000;
					  list-style: none;
					  margin: 0;
					  padding: 0;
					}
					
					.datalist-options li {
					  padding: 8px;
					  cursor: pointer;
					}
					
					.datalist-options li:hover {
					  background-color: #eee;
					}
					
					.hidden {
					  display: none;
					}

					.tooltip-container {
					    position: relative;
					    display: inline-block;
					    cursor: pointer;
					}
					
					.tooltip-container::after {
					    content: attr(data-info);
					    visibility: hidden;
					    opacity: 0;
					    position: absolute;
					    bottom: 150%; 
					    left: 50%;
					    transform: translateX(-50%);
					    padding: 8px 12px;
					    background-color: #333;
					    color: #fff;
					    white-space: normal;
					    min-width: 150px;
					    text-align: center;
					    border-radius: 4px;
					    z-index: 99;
					    transition: opacity 0.3s, visibility 0.3s;
					}
					
					.tooltip-container::before {
					    content: '';
					    visibility: hidden;
					    opacity: 0;
					    position: absolute;
					    bottom: 82%;
					    left: 50%;
					    transform: translateX(-50%);
					    border-width: 12px 8px 0 8px;
					    border-style: solid;
					    border-color: #333 transparent transparent transparent;
					    z-index: 100;
					    transition: opacity 0.3s, visibility 0.3s;
					}
					
					.tooltip-container:hover::after,
					.tooltip-container:hover::before {
					    visibility: visible;
					    opacity: 1;
					}
					
					
                </style>
            </head>

            <body>
                <span class="titoloPagina">
                    <fmt:message key="configurazioni_metadati.label.title" />
                </span>
                <jsp:include page="../includes/innerNavigation.jsp">
                    <jsp:param name="navmode" value="form" />
                </jsp:include>
                <jsp:include page="../includes/history.jsp">
                    <jsp:param name="path" value="../configurazione/create" />
                </jsp:include>
                <div id="subcontent" class="vbg-form">
                        <jsp:include page="../includes/displayGlobalMessages.jsp">
                            <jsp:param name="commandName" value="configurazioniMetadati" />
                        </jsp:include>
                        <input id="id" type="hidden" />
                        <fieldset>
                        	<legend>
                                    <fmt:message key="configurazioni_metadati.label.metadato" />
                                </legend>
                            <spring-form:form commandName="configurazioniMetadati" name="inviodati">

                                
                                <div class="form-group">
                                    <label>
                                        <fmt:message key="configurazioni_metadati.label.chiave" />
                                    </label>
                                    <c:choose>
                                        <c:when test="${not empty configurazioniMetadati.id  }">
                                            <input id="input_chiave" type="text" placeholder="" size="70"
                                                value="${configurazioniMetadati.id.chiave}" data-filter-input="UPPER_ALPHAS_AND_NUMBERS"/>

                                        </c:when>
                                        <c:otherwise>
                                            <input id="input_chiave" type="text" placeholder="" size="70" value="" data-filter-input="UPPER_ALPHAS_AND_NUMBERS" />
                                        </c:otherwise>

                                    </c:choose>
                                </div>
                                <div class="form-group">
                                    <label>
                                        <fmt:message key="configurazioni_metadati.label.valore" />
                                    </label>
                                    <c:choose>
                                        <c:when test="${not empty configurazioniMetadati.id  }">

                                            <textarea id="input_valore" placeholder="" rows="5" cols="40"
                                                value="${configurazioniMetadati.valore }"></textarea>
                                        </c:when>
                                        <c:otherwise>
                                            <textarea id="input_valore" rows="5" cols="40" placeholder="valore"" value="" ></textarea>
                                        </c:otherwise>

                                    </c:choose>

                                </div>
                                <div class=" form-group form-button">
                                	<label>
                                        <fmt:message key="configurazioni_metadati.label.categoria" />
                                    </label>
							      <%--   <input
					                    list="categorie_datalist_id" id="input_categoria"
					                    data-filter-input="UPPER_ALPHAS_AND_NUMBERS"
					                    size="width: 300px;">
					                <datalist id="categorie_datalist_id"> 
					                    <c:forEach
					                        items="${configurazioniMetadatiCategorie}" var="categoria">
					                        <option>${categoria}</option>
					                    </c:forEach> 
					                </datalist> --%>
					                <div class="datalist-wrapper">
									  <input type="text" id="input_categoria" autocomplete="on" data-filter-input="UPPER_ALPHAS_AND_NUMBERS">
									  <button id="dropdown_button" type="button" class="dropdown-arrow"></button>
									  <ul id="categorie_datalist_id" class="datalist-options hidden">
									   	<c:forEach items="${configurazioniMetadatiCategorie}" var="categoria">
									       <li>${categoria}</li>
									    </c:forEach> 
									  </ul>
									</div>
														                
								
					                <a href='#' id='modifica_categoria_id' class="btn btn-primary" onclick="return false;"> <fmt:message key="button.modify" /></a>  
                                </div>
                                
                                  <div class="form-group">
                                    <label>
                                        <fmt:message key="configurazioni_metadati.label.ordine" />
                                    </label>
                                    <c:choose>
                                        <c:when test="${not empty configurazioniMetadati.id  }">
                                            <input id="input_ordine" type="number" placeholder="0" size="10"
                                                value="${configurazioniMetadati.ordine}" name="ordine" />

                                        </c:when>
                                        <c:otherwise>
                                            <input id="input_ordine" type="number" placeholder="0" size="10" value="" name="ordine" />
                                        </c:otherwise>

                                    </c:choose>
                                </div>
                                <div class=" form-button">

                                    <!-- <a id="nuovo_metadato" class="btn btn-primary">
                                        <fmt:message key="button.new" /> -->
                                    </a>
                                    <a id="salva_metadato" class="btn btn-primary">
                                        <fmt:message key="button.save" />
                                    </a>

                                    </a>

                                    <a id="elimina_metadato" class="btn btn-primary">
                                        <fmt:message key="button.delete" />

                                        <a href="javascript:doHref('create.htm?software=${configurazione.configurazione.id.software}','')"
                                            class="btn btn-secondary">
                                            <fmt:message key="button.close" />
                                        </a>
                                </div>
                            
                            </spring-form:form>
                        </fieldset>

                    	<fieldset>
                            <legend><fmt:message key="label.form_ricerca" /> </legend>
                                <div class="form-group">
                                    <div class="input-icons">
                                        <i class="fa fa-search icon"></i>
                                        <input id="input_ricerca" type="text" placeholder="Cerca"/>
                                    </div>
                                    <div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella" /></div>		
                                
                                    <div class="input-icons">
                                        <input type="number" id="row_num" value="100" min="1"  size="20"
                                            oninput="changeRowsPerPage(this.value)">
                                            <div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella_righe" /></div> 
                                        
                                    </div>
                                </div>

			                </fieldset>	
			                			                
		                    <fieldset>
								<legend><fmt:message key="configurazioni_metadati.label.elenco_metadti" /></legend>
                               <div class="form-group">
								<table id='elenco_metadati' class="vbg-table">
									<thead>
										<th><fmt:message key="configurazioni_metadati.label.categoria"/></th>
										<th><fmt:message key="configurazioni_metadati.label.ordine"/></th>
										<th><fmt:message key="configurazioni_metadati.label.chiave"/></th>
										<th><fmt:message key="configurazioni_metadati.label.valore"/></th>
										<th><fmt:message key="configurazioni_metadati.label.azioni"/></th>
									</thead>
									<tbody id="elenco_metadati_body"></tbody>
								</table>
								<div id="pagination-controls" class="form-button"></div>
								 </div>
							</fieldset>
			            	<div id="popup" class='vbg-modal'>
				                <div class='vbg-modal-body'>
				                    <h1>MODIFICA CATEGORIA</h1>
				                     <div class="form-group">
				                                    <label>
				                                        <fmt:message key="configurazioni_metadati.label.categoria" />
				                                    </label>
				                                    
				                                    <input id="input_categoria_corrente" type="text" disabled="disabled" />
				                                      
				                          </div>
				                     <div class="form-group">
				                                    <label>
				                                        <fmt:message key="configurazioni_metadati.label.categoria_nuovo" />
				                                    </label>
				                                    
				                                    <input id="input_categoria_nuovo" type="text" value="" data-filter-input="UPPER_ALPHAS_AND_NUMBERS"/>
				                                      
				                                </div>
				                    
				                    <div class='vbg-modal-footer'>
				                        <a href="javascript:void(0)" id="bottone_modifica" class='bottone-salvataggio btn btn-primary'>
				                                    <fmt:message key="button.update" />
				                        
				                        <a href="#" data-role='toggle-popup' class="btn btn-secondary">
				                            <fmt:message key="button.back" />
				                        </a>
				
				                    </div>
				        
				                </div>
			            </div>   
                </div>
                
                    
                <script type="text/javascript">
               
                    vbg.ready(() => {
                    	

                   	document.addEventListener('click', function (e) {
                   		  const wrapper = document.querySelector('.datalist-wrapper');
                   		  if (!wrapper.contains(e.target)) {
                   		    document.getElementById('categorie_datalist_id').classList.add('hidden');
                   		  }
                   		});
                   		
                 	document.querySelector('#dropdown_button').addEventListener('click',(e)=>{
                   			 const list = document.getElementById('categorie_datalist_id');
                      		  list.classList.toggle('hidden');
                   		});
                   		
                   	let list = document.querySelector('#categorie_datalist_id');
                   	 Array.from(list.querySelectorAll('li')).map(li =>{ 
                   			 li.addEventListener('click',(e)=>{
                   				document.getElementById('input_categoria').value = e.target.innerHTML;
                         		 	document.getElementById('categorie_datalist_id').classList.add('hidden');
                   			 });
                   			 
                   		 });
                   	
                 	document.querySelector('#input_categoria').addEventListener('input',(e)=>{
                   		
                 		const input = document.getElementById('input_categoria').value.toLowerCase();
                 		document.getElementById('categorie_datalist_id').classList.remove('hidden');
                 		  const options = document.querySelector('#categorie_datalist_id').querySelectorAll('li');
                 		  options.forEach(option => {
                 		    const match = option.textContent.toLowerCase().includes(input);
                 		    option.style.display = match ? 'block' : 'none';
                 		  });
                      	});

                       
                        document.querySelector('#salva_metadato').addEventListener('click', salvaMetadato);
                        document.querySelector('#elimina_metadato').addEventListener('click', eliminaMetadato);
                        const categoria = document.querySelector('#input_categoria');
                        const bottoneModificaCategoria = document.querySelector('#modifica_categoria_id');
                        bottoneModificaCategoria.addEventListener('click', (e) => {
                            e.preventDefault();

                            const popupModificaCategoria = document.querySelector('#popup');
                            
                            const oldCat = document.querySelector('#input_categoria').value;
                            if (oldCat.trim() === "") {
                              alert("Per favore, scegli una categoria da modificare.");
                            } else {
                            	 document.querySelector('#input_categoria_corrente').value = oldCat;
                            	 document.querySelector('#input_categoria_nuovo').value = '';
                            	 popupModificaCategoria.show();
                            	 
                            }
                            

                        });
                        
                        document.querySelector('#bottone_modifica').addEventListener('click', (e) => {
                        	e.preventDefault();
                        	updateCategoria();
                        	
                        });
                        

                        getListaMetadati().then(
                            paginazione

                        ).catch((error) => {

                            console.error("Error:", error);
                        });



                        //LOGICA PAGINAZIONE

                        function paginazione() {
                            const table = document.getElementById('elenco_metadati');
                            const tbody = table.querySelector('tbody');
                            const rows = Array.from(tbody.querySelectorAll('tr'));
                            let rowsPerPage = 100;
                            let currentPage = 1;
                            let numPages = Math.ceil(rows.length / rowsPerPage);

                            const paginationControls = document.getElementById('pagination-controls');
                            const searchInput = document.querySelector('#input_ricerca');


                            let filteredRows = rows; // Start with all rows

                            // --- Core Functions ---

                            /**
                             * 1. Filters rows based on the current search input.
                             * 2. Resets pagination and recalculates page count.
                             * 3. Triggers the display of the first page of filtered data.
                             */
                            function filterAndPaginate(searchTerm = searchInput.value) {
                                const lowerCaseSearchTerm = searchTerm.toLowerCase();

                                // 2. Filter Rows: Create a new array of rows that match the search term
                                filteredRows = rows.filter(row => {
                                    // Get all text content from the row and check for a match
                                    const rowText = row.textContent.toLowerCase();
                                    return rowText.includes(lowerCaseSearchTerm);
                                });

                                // 3. Reset Pagination
                                currentPage = 1;

                                // 5. Display the first page of the new filtered set
                                displayPage(currentPage);
                            }

                            /**
                             * Displays the correct subset of rows for the current page.
                             * @param {number} page - The page number to display.
                             */
                            function displayPage(page) {
                                currentPage = page;
                                const numPages = Math.ceil(filteredRows.length / rowsPerPage);
                                const start = (page - 1) * rowsPerPage;
                                const end = start + rowsPerPage;

                                // Hide all original rows
                                rows.forEach(row => row.style.display = 'none');

                                // Show only the visible subset of the *filtered* rows
                                filteredRows.slice(start, end).forEach(row => row.style.display = '');

                                // Update pagination buttons based on the *filtered* data's length
                                renderPagination(numPages);
                            }

                            /**
                             * Creates and inserts the pagination buttons.
                             */
                            function renderPagination(numPages) {

                                paginationControls.innerHTML = ''; // Clear previous buttons

                                if (numPages > 1) {
                                    for (let i = 1; i <= numPages; i++) {
                                        const button = document.createElement('button');
                                        button.innerText = i;
                                        button.classList.add('btn');
                                        button.classList.add('btn-primary');
                                        if (i === currentPage) {


                                            button.classList.remove('btn-primary');
                                            button.classList.add('btn-secondary');
                                        }
                                        button.addEventListener('click', () => displayPage(i));
                                        paginationControls.appendChild(button);
                                    }
                                }
                            }
                            // --Function to handle input change ---
                            window.changeRowsPerPage = function (newRowsValue) {
                                // 1. Validate the input
                                const newRows = parseInt(newRowsValue, 10);

                                // Use the new value only if it is a positive integer. Otherwise, ignore or reset.
                                if (newRows > 0 && newRows !== rowsPerPage) {

                                    // 2. Update the global rowsPerPage variable
                                    rowsPerPage = newRows;

                                    // 3. Reset the search filter and re-paginate
                                    // This recalculates the total pages and displays the first page.
                                    const searchInput = document.querySelector('#input_ricerca');
                                    filterAndPaginate(searchInput.value);
                                }
                            };
                            // --- Event Listeners ---

                            // Listen for changes in the search input
                            searchInput.addEventListener('keyup', () => {
                                // Debouncing is recommended here for performance, but keyup is simplest
                                filterAndPaginate();
                            });

                            // Initial setup
                            filterAndPaginate();


                        }



                        //END




                        async function salvaMetadato() {

                            let idComuniAssociatiSoftware = ${ configurazioniMetadati.id.idComuniAssociatiSoftware };
                            let chiave = document.querySelector('#input_chiave').value;
                            let valore = document.querySelector('#input_valore').value;
                            let ordine = document.querySelector('#input_ordine').value;
                            let categoria = document.querySelector('#input_categoria').value;
                         

                            const postParams = { idComuniAssociatiSoftware, chiave, valore, ordine, categoria };
                            const queryParams = new URLSearchParams(postParams);
                            const apiEndpoint = '../configurazione/ajaxInsertOrUpdateConfigurazioniMetadati.htm';
                            window.vbg.mostraModalCaricamento();
                            const response = await fetch(apiEndpoint, {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(postParams)
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        alert("Controlla i dati inseriti");
                                        throw new Error(`HTTP error! status: ${response.status}`);
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                    getListaMetadati().then(
                                        paginazione

                                    ).catch((error) => {
										alert(error);
                                        console.error("Error:", error);
                                    });
                                    let datalist = document.querySelector('#categorie_datalist_id');
                                    const lis = Array.from(datalist.querySelectorAll('li')).map(li => li.innerHTML );
                                    if (!lis.includes(categoria)) {
                                        const newLi = document.createElement("li");
               
                                        newLi.innerHTML = categoria;
                                        newLi.addEventListener('click',(e)=>{
                                        	selectOption(e.target.innerHTML);
                                        	
                                        });
                                        datalist.appendChild(newLi);
                                      }

                                    document.querySelector('#input_chiave').value = "";
                                    document.querySelector('#input_valore').value = "";
                                    document.querySelector('#input_categoria').value = "";
                                    document.querySelector('#input_ordine').value = "";
                                  
                                    window.vbg.nascondiModalCaricamento()
                                })
                                .catch(error => {
                                	alert(error);
                                    console.error('Fetch Error:', error);
                                    window.vbg.nascondiModalCaricamento();
                                });




                        }

                        async function eliminaMetadato() {

                            let idComuniAssociatiSoftware = ${ configurazioniMetadati.id.idComuniAssociatiSoftware };
                            let chiave = document.querySelector('#input_chiave').value;


                            const postParams = { idComuniAssociatiSoftware, chiave };
                            const queryParams = new URLSearchParams(postParams);
                            const apiEndpoint = '../configurazione/ajaxEliminaConfigurazioniMetadati.htm';
                            window.vbg.mostraModalCaricamento();
                            const response = await fetch(apiEndpoint, {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: queryParams
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        alert("Controlla i dati inseriti");
                                        throw new Error(`HTTP error! status: ${response.status}`);
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                    document.querySelector('#input_chiave').value = "";
                                    document.querySelector('#input_valore').value = "";
                                    document.querySelector('#input_categoria').value = "";
                                    document.querySelector('#input_ordine').value = "";
                                    getListaMetadati().then(
                                        paginazione

                                    ).catch((error) => {
                                    	alert(error);
                                        console.error("Error:", error);
                                    });

                                    window.vbg.nascondiModalCaricamento()
                                })
                                .catch(error => {
                                	alert(error);
                                    console.error('Fetch Error:', error);
                                    window.vbg.nascondiModalCaricamento();

                                });




                        }
                        async function setInputs(confiurazioneMetadato) {
                            let chiave = document.querySelector('#input_chiave');
                            chiave.value = confiurazioneMetadato.chiave;
                            chiave.addEventListener('animationend', (event) => {

                                if (event.animationName === 'flash-highlight') {
                                    event.target.classList.remove('flash');
                                }
                            });
                            chiave.classList.add('flash');

                            let valore = document.querySelector('#input_valore');
                            valore.value = confiurazioneMetadato.valore;
                            valore.addEventListener('animationend', (event) => {

                                if (event.animationName === 'flash-highlight') {
                                    event.target.classList.remove('flash');
                                }
                            });
                            valore.classList.add('flash');

                            let categoria = document.querySelector('#input_categoria');
                            categoria.value = confiurazioneMetadato.categoria === undefined ? '': confiurazioneMetadato.categoria;
                            categoria.addEventListener('animationend', (event) => {

                                if (event.animationName === 'flash-highlight') {
                                    event.target.classList.remove('flash');
                                }
                            });
                            categoria.classList.add('flash');

                            let ordine = document.querySelector('#input_ordine');
                            ordine.value = confiurazioneMetadato.ordine;;
                            ordine.addEventListener('animationend', (event) => {

                                if (event.animationName === 'flash-highlight') {
                                    event.target.classList.remove('flash');
                                }
                            });
                            ordine.classList.add('flash');
                        }

                        async function getListaMetadati() {

                            return new Promise(async (resolve, reject) => {

                                let idComuniAssociatiSoftware = ${ configurazione.comuniassociatisoftware.id.codice };//${ configurazioniMetadati.id.idComuniAssociatiSoftware };

                                const apiEndpoint = '../configurazione/listaConfigurazioniMetadati.htm?idComuniAssociatiSoftware=' + idComuniAssociatiSoftware;
                                const response = await fetch(apiEndpoint, {
                                    method: 'GET',
                                    headers: {
                                        'Accept': 'application/json',
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    },
                                })
                                    .then(response => {
                                        if (!response.ok) {
                                            throw new Error(`HTTP error! status: ${response.status}`);
                                        }
                                        return response.json();
                                    })
                                    .then(data => {

                                        let tbody = document.querySelector('#elenco_metadati_body');
                                        tbody.innerHTML = "";
                                        for (const confiurazioneMetadato of data) {
                                            let tr = document.createElement('tr');
                                            let tdNome = document.createElement('td');
                                            let tdValore = document.createElement('td');
                                            let tdAzioni = document.createElement('td');
                                            let tdOrdine = document.createElement('td');
                                            let tdCategoria = document.createElement('td');
                                            tr.appendChild(tdCategoria);
                                            tr.appendChild(tdOrdine);
                                            tr.appendChild(tdNome);
                                            tr.appendChild(tdValore);
                                            tr.appendChild(tdAzioni);
                                            tdOrdine.innerHTML = confiurazioneMetadato.ordine === undefined ? '':confiurazioneMetadato.ordine;
                                            tdNome.innerHTML = confiurazioneMetadato.chiave;
                                            tdValore.innerHTML = confiurazioneMetadato.valore;
                                            tdCategoria.innerHTML = confiurazioneMetadato.categoria === undefined ? '':confiurazioneMetadato.categoria;
                                            tdAzioni.innerHTML = `
                                            	<div class="tooltip-container" data-info="Modifica i dati della configurazione">
                                            	 <a href="javascript:void(0)" id="id_modify" class="modifica">
                                                	<i class="fa fa-pencil" aria-hidden="true"></i>Modifica 
                                           		 </a>
                                                </div>
                                                <div class="tooltip-container" data-info="Elimina la configurazione">
                            						<a  id="id_delete" href="javascript:void(0)">
                            							<i class="fa fa-trash" aria-hidden="true"></i>
                            						</a>
                            					</div>
											`;

                                            tbody.appendChild(tr);
                                            tdAzioni.querySelector('#id_modify').addEventListener('click', async () => {
                                                await setInputs(confiurazioneMetadato).then(()=>{
                                                		window.scrollTo({
                                                			  top: 0,
                                                			  behavior: 'smooth'
                                                			});

                                                		
                                                });


                                            });

                                            tdAzioni.querySelector('#id_delete').addEventListener('click', async () => {
                                                await setInputs(confiurazioneMetadato);
                                                eliminaMetadato();
                                                tr.style.display = 'none';


                                            });

                                        }

                                    })
                                    .catch(error => {
                                    	alert(error);
                                        console.error('Fetch Error:', error);


                                    });

                                resolve("Data successfully loaded!");


                            });

                        }

                        async function updateCategoria() {

                            let categoriaOriginale = document.querySelector('#input_categoria_corrente').value;
                            let nuovaCategoria = document.querySelector('#input_categoria_nuovo').value
                            const postParams = { categoriaOriginale, nuovaCategoria };
                            const queryParams = new URLSearchParams(postParams);
                            const apiEndpoint = '../configurazione/updateCategoriaConfigurazioniMetadati.htm';
                            window.vbg.mostraModalCaricamento();
                            const response = await fetch(apiEndpoint, {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: queryParams
                            })
                                .then(response => {
                                    if (!response.ok) {
                                    	 window.vbg.nascondiModalCaricamento();
                                        alert("il tentativo di modificare la categoria è faallito");
                                        throw new Error(`HTTP error! status: ${response.status}`);
                                    }
                                    return response.json();
                                }).then(data => {
                                    getListaMetadati().then(
                                        paginazione

                                    ).catch((error) => {
                                    	alert(error);
                                        console.error("Error:", error);
                                    });
                                    let datalist = document.querySelector('#categorie_datalist_id');
                                     Array.from(datalist.querySelectorAll('li')).map(li =>{ 
                                    	 if(li.innerHTML === categoriaOriginale){
                                    		 li.innerHTML = nuovaCategoria ;
                                    		 /* li.value = nuovaCategoria; */
                                    		 document.querySelector('#input_categoria').value='';
                                    		 document.querySelector('#popup').hide();
                                    	 }
                                    } );
                                    window.vbg.nascondiModalCaricamento();


                                })


                        }

                    });




                </script>
                
            </body>
             
            </html>