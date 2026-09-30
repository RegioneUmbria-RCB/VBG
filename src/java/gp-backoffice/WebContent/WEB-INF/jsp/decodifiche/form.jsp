<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="decodifiche.label.title" /></title>
</head>
<body>

    <span class="titoloPagina"> <fmt:message
            key="decodifiche.label.title" />
    </span>
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="form" />
    </jsp:include>
    <div id="subcontent" class="vbg-form">
        <spring-form:form commandName="decodifiche" name="inviodati">
            <jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName" value="domain" />
            </jsp:include>
            <!-- inizio -->
            <div class="row form-group">
                <label class="tabella" for="autocompleter_id">
                    <fmt:message key="decodifiche.label.tabella" /> 
                </label> 
                
                <input
                    list="tabelle" id="autocompleter_id"
                    data-filter-input="UPPER_ALPHAS_AND_NUMBERS"
                    size="width: 300px;">
                    
                <datalist id="tabelle"> 
                    <c:forEach
                        items="${listTabelle}" var="tabella">
                        <option>${tabella}</option>
                    </c:forEach> 
                </datalist>

                <a href='#' id='cerca' class="btn btn-primary" onclick="return false;">Cerca</a>
                <a href='#' id='nuova-decodifica' class="btn btn-primary">Nuovo</a>
                
            </div>
            <div id="lista-valori"  class="lista">

                <table id="decodifiche_table" class='vbg-table'>
                    <thead>
                        <tr>
                            <th><fmt:message
                                    key="decodifiche.label.chiave" /></th>
                            <th><fmt:message
                                    key="decodifiche.label.valore" /></th>
                            <th><fmt:message
                                    key="decodifiche.label.raggruppamento" /></th>
                            <th><fmt:message
                                    key="decodifiche.label.ordine" /></th>
                            <th><fmt:message
                                    key="decodifiche.label.disabilitato" /></th>
                            <th><fmt:message
                                    key="decodifiche.label.azioni" /></th>
                        </tr>
                    </thead>
                    <tbody id="table_body"></tbody>

                </table>
            </div>

            <template id="decodifiche_riga">
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><i aria-hidden="true"></i></td>
                    <td>
                        <a href="javascript:void(0)" class="modifica">
                            <i class="fa fa-pencil"aria-hidden="true"></i> Modifica
                        </a> 
                        
                    </td>
                </tr>
            </template>
                
             <div id="popup" class='vbg-modal'>
                <div class='vbg-modal-body'>
                    <h1></h1>
                    <div class="validate-required form-group">
                        <label>
                            <fmt:message key="decodifiche.label.tabella" />
                        </label>
                        <input type="text" id="in_tabella" data-filter-input="UPPER_ALPHAS_AND_NUMBERS" class='control-to-validate' size='width: 300px;' />
                        <div id="errori_contesto"
                            class="error validation-feedback">
                            Il campo è obbligatorio</div>
                    </div>
                    <div class="validate-required form-group">
                        <label>
                            <fmt:message key="decodifiche.label.chiave" />
                        </label> 
                        <input type="text" id="in_chiave" data-filter-input="UPPER_ALPHAS_AND_NUMBERS" class='control-to-validate' />
                        <div id="errori_contesto"
                            class="error validation-feedback">
                            Il campo è obbligatorio</div>
                    </div>
                    <div class="validate-required form-group">
                        <label>
                            <fmt:message key="decodifiche.label.valore" />
                        </label>
                        <textarea id="in_valore" class='control-to-validate'></textarea>
                        <div id="errori_contesto"
                            class="error validation-feedback">
                            Il campo è obbligatorio</div>
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="decodifiche.label.raggruppamento" />
                        </label>
                        <input type="text" id="in_raggruppamento" />
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="decodifiche.label.ordine" />
                        </label>
                        <input type="text" id="in_ordine" />
                    </div>
                    <div class="form-group">
                        <label>
                            <fmt:message key="decodifiche.label.disabilitato" />
                        </label>
                        <input type="checkbox" id="in_flagdisabilitato" />
                    </div>
                    <div class='vbg-modal-footer'>
                        <a href="javascript:void(0)" id="bottone_modifica" class='bottone-salvataggio btn btn-primary'>
                                    <fmt:message key="button.update" />
                        
                        <a href="javascript:void(0)" id="bottone_inserisci" class='bottone-salvataggio btn btn-primary'>
                            <fmt:message key="button.insert" />
                        </a>
                        
                        <a href="#" data-role='toggle-popup' class="btn btn-secondary">
                            <fmt:message key="button.back" />
                        </a>

                    </div>
        
                </div>

            </div>
            <script type='text/javascript'>
                vbg.ready(() => {
                    const inputAutoCompleter = document.getElementById('autocompleter_id');
                    const bottoneNuovo = document.querySelector('#nuova-decodifica');
                    const bottoneCerca = document.querySelector('#cerca');



                    // Registro i validatori della pagina
                    const bottoni = document.querySelectorAll('.bottone-salvataggio');
                    var campiDaValidare = Array.from(document.querySelectorAll('.validate-required')).map((campo) => new vbg.validators.RequiredFieldValidator(campo));
                    const validationGroup = new vbg.validators.ValidationGroup(campiDaValidare, bottoni);

                    function mostraTabella() {
                    	document.querySelector('#lista-valori').style.display = 'block';
                    }
                    
                    function nascondiTabella() {
                        document.querySelector('#lista-valori').style.display = 'none';
                    }
                    
                    nascondiTabella();

                    const caricaElementiTabella = async () => {

                    	nascondiTabella();
                        

                        let tableBody = document.getElementById("table_body");
                        tableBody.innerHTML = "";
                        console.log("selected:", inputAutoCompleter.value);
                        if (inputAutoCompleter.value === "") {
                            return;

                        }
                        const response = await fetch("../decodifiche/findDecodificheByTabella.htm?tabella=" + inputAutoCompleter.value, {
                            method: "GET",
                            cache: "no-cache",
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        });
                        let ris = await response.json();
                        console.log(ris);
                        ris.forEach((item, index) => {
                            const body = document.getElementById("table_body");
                            let template = document.getElementById("decodifiche_riga");
                            let clone = template.content.cloneNode(true);
                            let td = clone.querySelectorAll("td");
                            td[0].textContent = item.chiave;
                            td[1].textContent = item.valore;
                            td[2].textContent = item.raggruppamento;
                            td[3].textContent = item.ordine || '';
                            if (item.flgDisabilitato === true) {
                                td[4].querySelector("i").className = "fa fa-check";
                            }

                            body.appendChild(clone);

                            td[5].querySelector(".modifica").addEventListener("click", () => {
                                popolaPopup(item);
                            });

                        });

                        mostraTabella();
                        // enableFunctions();
                    };


                    inputAutoCompleter.addEventListener('input', (e) => {

                        // Chrome genera un event se l'elemento è stato selezionato dalla lista dei suggeriti e un InputEvent con inputType == "insertText" nel caso in cui si sia scritto un testo
                        // Firfox genera sempre un InputEvent ma con inputType == "insertText" nel caso in cui si sia inserito un testo da tastiera
                        // e inputType == "insertReplacementText" nel caso in cui l'elemento sia stato selezionato dalla lista
                        // In entrambi i casi posso verificare se inputType == "insertText", nel caso la condizione sia vera allora il testo è stato
                        // immesso da tastiera
                        var isInputEvent = e.inputType == "insertText";  // (Object.prototype.toString.call(e).indexOf("InputEvent") > -1);

                        if (!isInputEvent) {
                            caricaElementiTabella();
                        }
                    });


                    bottoneNuovo.addEventListener('click', (e) => {
                        e.preventDefault();

                        popolaPopup({
                            tabella: inputAutoCompleter.value
                        });

                    });


                    const popupDecodifica = document.querySelector("#popup");
                    const bottoneModifica = popupDecodifica.querySelector('#bottone_modifica');
                    const bottoneInserisci = popupDecodifica.querySelector('#bottone_inserisci');

                    popupDecodifica.addEventListener('shown', () => {
                        validationGroup.resetValidators();
                    });


                    function popolaPopup(item) {

                        const isInserting = !item.id;

                        popupDecodifica.querySelector("h1").innerHTML = isInserting ? 'Nuova decodifica' : 'Modifica decodifica';
                        popupDecodifica.querySelector("#in_tabella").value = item.tabella;
                        popupDecodifica.querySelector("#in_chiave").value = item.chiave || '';
                        popupDecodifica.querySelector("#in_valore").value = item.valore || '';
                        popupDecodifica.querySelector("#in_raggruppamento").value = item.raggruppamento || '';
                        popupDecodifica.querySelector("#in_ordine").value = item.ordine || '';

                        popupDecodifica.querySelector("#in_flagdisabilitato").checked = item.flgDisabilitato;
                        popupDecodifica.decodifica = item;

                        if (!isInserting) {
                            bottoneModifica.style.display = 'inline-block';
                            bottoneInserisci.style.display = 'none';
                        } else {
                            bottoneModifica.style.display = 'none';
                            bottoneInserisci.style.display = 'inline-block';
                        }

                        popupDecodifica.show();
                    }


                    bottoneModifica.addEventListener('click', async (e) => {

                        // disableFunctions();
                        e.preventDefault();

                        const tabella = popupDecodifica.querySelector("#in_tabella").value;
                        const chiave = popupDecodifica.querySelector("#in_chiave").value;
                        const valore = popupDecodifica.querySelector("#in_valore").value;
                        const raggruppamento = popupDecodifica.querySelector("#in_raggruppamento").value;
                        const flagDisabilitato = popupDecodifica.querySelector("#in_flagdisabilitato").checked;
                        const ordine = popupDecodifica.querySelector("#in_ordine").value;


                        // Validazione dei dati...

                        // Recupero dei dati dati da inviare
                        const formData = new FormData();
                        const decodifica = popupDecodifica.decodifica;

                        formData.append('tabella', tabella);
                        formData.append('chiave', chiave);
                        formData.append('valore', valore);
                        formData.append('raggruppamento', raggruppamento);
                        formData.append('flagDisabilitato', flagDisabilitato);
                        formData.append('codice', decodifica.id.codice);
                        formData.append('ordine', ordine);


                        // Invio dei dati
                        const url = '../decodifiche/modificaDecodifica.htm';

                        const result = await fetch(url, {
                            method: 'POST',
                            body: formData
                        });

                        const resultText = await result.text();

                        // enableFunctions();

                        console.log('rt: ' + resultText);

                        if (resultText === 'OK') {
                       
                            // Ricaricare la tabella
                            inputAutoCompleter.value = tabella;
                            await caricaElementiTabella();
                            
                            // Nascondere il popup
                            popupDecodifica.hide();
                        }

                        // Risposta all'esito della chiamata


                    });

                    bottoneInserisci.addEventListener('click',async (e)=>{
                        e.preventDefault();

                        const tabella = popupDecodifica.querySelector("#in_tabella").value;
                        const chiave = popupDecodifica.querySelector("#in_chiave").value;
                        const valore = popupDecodifica.querySelector("#in_valore").value;
                        const raggruppamento = popupDecodifica.querySelector("#in_raggruppamento").value;
                        const flagDisabilitato = popupDecodifica.querySelector("#in_flagdisabilitato").checked;
                        const ordine = popupDecodifica.querySelector("#in_ordine").value;

                        const formData = new FormData();
                        

                        formData.append('tabella', tabella);
                        formData.append('chiave', chiave);
                        formData.append('valore', valore);
                        formData.append('raggruppamento', raggruppamento);
                        formData.append('flagDisabilitato', flagDisabilitato);
                        formData.append('ordine', ordine);


                        // Invio dei dati
                        const url = '../decodifiche/inserisci.htm';

                        const result = await fetch(url, {
                            method: 'POST',
                            body: formData
                        });

                        const resultText = await result.text();

                        // enableFunctions();

                        console.log('rt: ' + resultText);

                        if (resultText === 'OK') {
                       
                            // Ricaricare la tabella
                            inputAutoCompleter.value = tabella;
                            await caricaElementiTabella();
                            
                            // Nascondere il popup
                            popupDecodifica.hide();
                        }

                    });

                });

				
			</script>
        </spring-form:form>
    
    <div id="functions">
        <ul>
            <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
                        key="button.back" /></a></li>
        </ul>
    </div>
</body>
</html>