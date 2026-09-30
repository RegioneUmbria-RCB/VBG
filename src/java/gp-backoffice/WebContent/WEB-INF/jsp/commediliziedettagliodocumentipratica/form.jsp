<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page
    import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message
        key="commdettagliodocumentipratica.label.titolo_pagina" /></title>
</head>
<body>

    <span class="titoloPagina"> <fmt:message
            key="commdettagliodocumentipratica.label.titolo_pagina" />
    </span>

    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="form" />
    </jsp:include>

    <form id="mainForm" name="mainForm" method="POST">
        <div id="subcontent">
            <spring-form:form commandName="commissione" name="inviodati">
                <jsp:include page="../includes/displayGlobalMessages.jsp">
                    <jsp:param name="commandName" value="commissione" />
                </jsp:include>
            </spring-form:form>
    
            <style>
            .dati-riepilogo {
                display: flex;
            }
            
            .dati-riepilogo>fieldset {
                flex-grow: 1;
            }
            
            .dettaglio-file {
                margin-bottom: var(--default-padding);
            }
            
            .dettaglio-file > checkbox {
                display: inline-block;
            }
            
            .dettaglio-file > .descrizione {
                font-weight: bold;
                display: inline-block;
            }
            .dettaglio-file > .nome-file {
                margin-left: var(--double-padding);
            }
            
            .selezione-files legend {
                cursor: pointer;
                user-select: none;
            }
            </style>
    
            <div id="form">
                <div class="vbg-form dati-riepilogo">
    
                    <fieldset>
                        <legend>
                            <fmt:message key="commdettagliodocumentipratica.label.dati_pratica"/>
                        </legend>
    
                        <div class="form-group">
                            <label><fmt:message key="label.numero" /></label>
                            <div class="readonly-form-control">
                                ${dati.pratica.numero}</div>
                        </div>
    
                        <div class="form-group">
                            <label><fmt:message key="label.data" /></label>
                            <div class="readonly-form-control">
                                ${dati.pratica.dataPresentazione}</div>
                        </div>
    
    
                        <c:if
                            test="${not empty dati.pratica.numeroProtocollo}">
                            <div class="form-group">
                                <label><fmt:message
                                        key="label.numero_protocollo" /></label>
                                <div class="readonly-form-control">
                                    ${dati.pratica.numeroProtocollo} del
                                    ${dati.pratica.dataProtocollo}</div>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label><fmt:message
                                    key="label.richiedente" /></label>
                            <div class="readonly-form-control">
                                ${dati.pratica.richiedente}</div>
                        </div>
                    </fieldset>
    
                    <fieldset>
                        <legend>
                            <fmt:message key="commdettagliodocumentipratica.label.dati_commissione"/>
                        </legend>
    
                        <div class="form-group">
                            <label><fmt:message
                                    key="label.numero_commissione" /></label>
                            <div class="readonly-form-control">
                                ${dati.commissione.numero}</div>
                        </div>
                        <div class="form-group">
                            <label><fmt:message
                                    key="label.descrizione" /></label>
                            <div class="readonly-form-control">
                                ${dati.commissione.descrizione}</div>
                        </div>
                    </fieldset>
                </div>
                <!-- vbg-form -->
    
                <script type="text/javascript">
                    vbg.ready(() => {
                    	
                    	const aggiornaStatoSezione = (fieldset) => {
                    		const chkSelezionaTutti = fieldset.querySelector('.seleziona-tutti');
                    		const chkSelezionaList = fieldset.querySelectorAll('.chk-seleziona');
                    		const segnapostoSelezionati = fieldset.querySelector('.segnaposto-selezionati');
                    		const totaleCheckbox = chkSelezionaList.length;
                    		                		
                    		let numeroCheckSelezionate = 0;
                    		
                    		chkSelezionaList.forEach((chk) => {
                    			if (chk.checked) {
                    				numeroCheckSelezionate++;
                    			}
                    		});
                    		
                    		chkSelezionaTutti.checked = totaleCheckbox === numeroCheckSelezionate;
                    		segnapostoSelezionati.innerText = `(\${numeroCheckSelezionate}/\${totaleCheckbox})`;
                    	};
                    	
                    	document.querySelectorAll('.selezione-files fieldset').forEach(fieldset => {
                    		aggiornaStatoSezione(fieldset);                		
                    	});
                    	
                    	// Al click su "Seleziona tutti" seleziono tutte le
                    	// checkbox che appartengono allo stesso fieldset
                        document.querySelectorAll('.seleziona-tutti').forEach((item) => {
                        	
                        	const legend = item.closest('legend');
                        	legend.addEventListener('click', (e) => {
                        		var source = e.target || e.srcElement;
                        		
                        		if (legend !== source){
                        			return;
                        		}
                        		item.click();
                        	});
                        	
                        	item.addEventListener('click', (e) => {
                        		
                        		console.log(item.checked);
                        		
                                item.closest('fieldset')
                                    .querySelectorAll('.chk-seleziona')
                                    .forEach((chkSeleziona) => {
                                        chkSeleziona.checked = item.checked;                        
                                    });        
                                
                                aggiornaStatoSezione(item.closest('fieldset'));
                        	});
                        	
                        });
                    	
                    	// Al click su una delle checkbox aggiorno lo stato del 
                    	// "seleziona / deseleziona tutti"
                    	document.querySelectorAll('.chk-seleziona').forEach((item) => {
                    		
                    		item.addEventListener('click', () => {
                    		    aggiornaStatoSezione(item.closest('fieldset'));       			
                    		});
                    	});
                        
                    	// Gestione del click sul bottone di salvataggio
                    	const btnSubmit = document.getElementById("btnSubmit");
                    	if(btnSubmit){
	                    	btnSubmit.addEventListener('click', () => {
	                    		vbg.mostraModalCaricamento();
	                    		doSubmit('update.htm','',document.mainForm);
	                    	});
                    	}
                    	
                    	
                    	const getAllegatiTemplate = async (codiceOggetto, el) => {

                    		el.innerHTML = await getSnippetOggetto(codiceOggetto,'allegato'+codiceOggetto);
                    	}
                    	
              
                    	
                    	const getSnippetOggetto = async (codiceOggetto, idElemento) => {
                    		
                    		let mostralabel = true;
                    		let mostraNomeFile = true;
                    		let readonly = true;
                    		let mostrastorico = false;
                    		let jsFx = 'viewOggetto_' + idElemento + '_fx'; 
                    		let styleHref = '';
                    		let url = `../file/ajaxViewOggettoList.htm?fileId=\${codiceOggetto}&mostralabel=\${mostralabel}&mostraNomeFile=\${mostraNomeFile}&mostrastorico=\${mostrastorico}&readonly=\${readonly}&jsFx=\${jsFx}&styleHref=\${styleHref}`;
                    			
                    		const response = await fetch(url, {
                    			 method: 'GET',
                    			 context: document.body,
                    			 //cache: false,				
                    			 dataType: "html",
                    		});
                    		
                    		if (response.status !== 200) {
                                const errore = await response.text();
                                document.getElementById('id_'+ idElemento).innerHTML = errore.innerText;
                                
                                console.error(errore.innerText);
                                throw errore.innerText;
                            }
                    		
                    		return await response.text();
                    	}                    	
                    	
                    	document.querySelectorAll(".allegati-tpl").forEach( el => {
                    		getAllegatiTemplate(el.getAttribute('data-codiceoggetto'), el);
                		});
                    	
                    });                     
                </script>
    
                <div class="vbg-form selezione-files">
                
                    <input type="hidden" name="idCommissioneR" value="${dati.commissione.idDettaglioCommissione}"/>
                    <input type="hidden" name="codiceIstanza" value="${dati.pratica.id}"/>
    
                    <c:forEach items="${dati.tuttiDocumenti}" var="raggruppamento">
                        <fieldset>
                            <legend>
                                <input type="checkbox" class="seleziona-tutti" title="Seleziona/Deseleziona tutti">
                                ${raggruppamento.titolo}
                                <span class="segnaposto-selezionati">(0/0)</span>
                            </legend>
    
                            <c:forEach items="${raggruppamento.documenti}"
                                var="documento">
    
                                <div class="dettaglio-file">
                                    <input type="checkbox" name="${raggruppamento.categoria}"
                                        class="chk-seleziona"
                                        <c:if test="${documento.selezionato}">checked="checked"</c:if>
                                        value="${documento.fkId}"></input>
                                    <div class="descrizione">
                                        <c:choose>
                                            <c:when test="${documento.descrizione == ''}">
                                                Descrizione non presente
                                            </c:when>
                                            <c:otherwise>
                                                ${documento.descrizione}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="nome-file form-group">
                                        <c:choose>
                                            <c:when test="${documento.nomeFile==''}">
                                                <span class="error" style="min-width: 500px">
                                                    <i class="fa fa-exclamation-triangle"></i> File non presente
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                            	<div class="allegati-tpl" style="min-width: 500px" data-id="${documento.fkId}" data-codiceoggetto="${documento.codiceOggetto}" data-nomefile="${documento.nomeFile}">${documento.nomeFile}&nbsp;<i class="fa fa-spinner fa-spin"></i></div>                                              
                                            </c:otherwise>
                                        </c:choose>                            
                                    </div>
                                </div>
    
                            </c:forEach>
                        </fieldset>
                    </c:forEach>
                </div>
                
                
                <div>
	                <c:if test="${dati.commissione.aperta eq true}">
	                    <div class="btn btn-primary" id="btnSubmit">
	                        <fmt:message key="button.update" />
	                    </div>
	                </c:if>   
                    <a class="btn btn-secondary" href="javascript:doHref('../commissioniediliziet/listCommissioniedilizieR.htm?codiceCommissione=${dati.commissione.id}','')">
                        <fmt:message key="button.back" />
                    </a>
                </div>
            </div> <!-- vbg-form -->
        </div>

    </form>
</body>
</html>