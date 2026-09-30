<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <title><fmt:message key="label.layouttesti.title" /></title>
	    <style>
            .azione {
	           display: block;
               white-space: nowrap;
               padding-bottom: 2px;
               padding-top: 2px;
            }
	    </style>
        <script type="text/javascript">
            vbg.ready(() => {
            });
            
            function filtraTestoCelle() {
              let input = document.getElementById("input_ricerca");
              let filter = input.value.toUpperCase();
              let table = document.querySelector("table.searchable");
              let tr = table.getElementsByTagName("tr");
              let trovato = false;

              for (i = 1; i < tr.length; i++) {
                let td = tr[i].getElementsByTagName("td");
                for (let cell of td) {
                  if (cell) {
                    let txtValue = cell.textContent || cell.innerText;                   
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {                    
                      trovato = true;
                    }                 
                  }
                }
              
                if(trovato){
                  tr[i].style.display = "";
                  trovato = false;
                } else {
                  tr[i].style.display = "none";                   
                }
              }
            }
            
            function nuovoTesto(idx){
                let tr = document.querySelector("tr[data-idx='" + idx + "']");
                tr.querySelector("textarea").style.display = '';
                tr.querySelector("a.nuovo").style.display = 'none';
                tr.querySelector("a.salva").style.display = '';
                tr.querySelector("a.annulla").style.display = '';
            }
            
            function modificaTesto(idx){
                let tr = document.querySelector("tr[data-idx='" + idx + "']");
                let textArea = tr.querySelector("textarea");
                let span = textArea.parentNode.querySelector("span");

                textArea.style.display = '';
                textArea.value = span.textContent;
                span.style.display = 'none';
                
                tr.querySelector("a.modifica").style.display = 'none';
                tr.querySelector("a.ripristina").style.display = 'none';
                tr.querySelector("a.salva").style.display = '';
                tr.querySelector("a.annulla").style.display = '';
            }
            
            function annullaModifica(idx){
                let tr = document.querySelector("tr[data-idx='" + idx + "']");
                let textArea = tr.querySelector("textarea");
                let span = textArea.parentNode.querySelector("span");
                
                textArea.style.display = 'none';
                textArea.value = '';
                span.style.display = '';
                               
                tr.querySelector("a.nuovo").style.display = ( span.textContent === '' ? '' : 'none' ); 
                tr.querySelector("a.modifica").style.display = ( span.textContent === '' ? 'none' : '' );
                tr.querySelector("a.ripristina").style.display = ( span.textContent === '' ? 'none' : '' );
                
                tr.querySelector("a.salva").style.display = 'none';
                tr.querySelector("a.annulla").style.display = 'none';
            }
            
            async function salvaTesto(idx){
                let tr = document.querySelector("tr[data-idx='" + idx + "']");
                let textArea = tr.querySelector("textarea");
                if(textArea.value === ''){
                    alert('Immettere il testo da salvare');
                    return;
                }
                
                if(confirm('Procedere con il salvataggio?')){
                    try {
                        
                    	window.vbg.mostraModalCaricamento();
                    	
                        let codiceTesto = tr.querySelector('span.codiceTesto').textContent;
                        let software = tr.querySelector('span.software').textContent;;
                        let testo = textArea.value;
                        
                        const postParams = { request: { codiceTesto, software, testo } };
                        
                        const response = await fetch('jsonSalvaTesto.htm', {
                            method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(postParams)
                        });
                        
                        await response.json();
                        
                        let span = textArea.parentNode.querySelector("span");
                        textArea.style.display = 'none';
                        span.textContent = textArea.value;
                        span.style.display= '';
                        
                        tr.querySelector("a.salva").style.display = 'none';
                        tr.querySelector("a.annulla").style.display = 'none';
                        tr.querySelector("a.modifica").style.display = '';
                        tr.querySelector("a.ripristina").style.display = '';

                    } 
                    catch(error) {                                    
                        alert(error);
                    }
                    finally{                                  
                    	window.vbg.nascondiModalCaricamento();
                    }
                }
            }
            
            async function ripristinaTesto(idx){
                let tr = document.querySelector("tr[data-idx='" + idx + "']");
                
                if(confirm('Il testo visualizzato verrà eliminato, si desidera procedere?')){
                	try {
                		window.vbg.mostraModalCaricamento();
                		
                        let codiceTesto = tr.querySelector('span.codiceTesto').textContent;
                        let software = tr.querySelector('span.software').textContent;;
                        
                        const postParams = { request: { codiceTesto, software } };
                        
                        const response = await fetch('jsonRipristinaTesto.htm', {
                            method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(postParams)
                        });
                        
                        await response.json();

                        tr.querySelector("a.nuovo").style.display = '';
                        tr.querySelector("a.modifica").style.display = 'none';
                        tr.querySelector("a.ripristina").style.display = 'none';
                        
                        let textArea = tr.querySelector("textarea");
                        let span = textArea.parentNode.querySelector("span");
                        
                        span.textContent = '';
                        textArea.value = '';
                    } 
                    catch(error) {                                    
                        alert(error);
                    }
                    finally{                                  
                    	window.vbg.nascondiModalCaricamento();
                    }
                }
            }
        </script>
    </head>
	<body>
        <span class="titoloPagina"><fmt:message key="label.layouttesti.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
            <jsp:param name="navmode" value="list"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
            <jsp:param name="path" value="../layouttesti/list" />
		</jsp:include>
		<div id="subcontent">
            <div class="vbg-form">
	            <fieldset>
                    <legend><fmt:message key="label.form_ricerca" /> </legend>
	                <div class="form-group">
	                    <div class="input-icons">
	                        <i class="fa fa-search icon"></i>
	                        <input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
	                    </div>
	                    <div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella" /></div>     
	                </div>
	                <div id="functions">
	                    <ul>
	                        <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	                    </ul>
	                </div>
	            </fieldset>
	            <fieldset>
                    <legend><fmt:message key="label.layouttesti.elenco_testi" /></legend>
                    <table class="vbg-table searchable sortable">
						<thead>
                            <tr>                            
                                <th><fmt:message key="label.layouttesti.codice_testo"/></th>
                                <th><fmt:message key="label.layouttesti.testo_base"/></th>
                                <th><fmt:message key="label.software"/></th>
                                <th><fmt:message key="label.layouttesti.testo_visualizzato"/></th>
                                <th><fmt:message key="label.azioni"/></th>
						    </tr>
						</thead>
						<tbody>
                            <c:forEach items="${testi}" var="testo" varStatus="row">
                                <tr data-idx='${row.index}'>
                                    <td><span class='codiceTesto'>${testo.codiceTesto}</span></td>
                                    <td>${testo.testoBase}</td>
                                    <td><span class='software'>${testo.software}</span></td>
                                    <td>
                                        <span>${testo.nuovoTesto}</span>
                                        <textarea rows="2" cols="30" style="display: none;"></textarea>
                                    </td>
                                    <td>
                                        <a href="javascript:nuovoTesto(${row.index});" class="azione nuovo" style='display:${StringUtils.isBlank(testo.nuovoTesto) ? "" : "none"}'>
                                            <i class="fa fa-plus-circle"></i>&nbsp;<fmt:message key="label.layouttesti.testo_nuovo"/>
                                        </a>
                                        <a href="javascript:modificaTesto(${row.index});" class="azione modifica" style='display:${StringUtils.isBlank(testo.nuovoTesto) ? "none" : ""}'>
                                            <i class="fa fa-pencil"></i>&nbsp;<fmt:message key="label.layouttesti.testo_modifica"/>
                                        </a>
                                        <a href="javascript:ripristinaTesto(${row.index});" class="azione ripristina" style='display:${StringUtils.isBlank(testo.nuovoTesto) ? "none" : ""}'>
                                            <i class="fa fa-trash"></i>&nbsp;<fmt:message key="label.layouttesti.testo_ripristina"/>
                                        </a>
                                        <a href="javascript:salvaTesto(${row.index});" class="azione salva" style='display:none'>
                                            <i class="fa fa-save"></i>&nbsp;<fmt:message key="label.layouttesti.salva_modifiche"/>
                                        </a>
                                        <a href="javascript:annullaModifica(${row.index});" class="azione annulla" style='display:none'>
                                            <i class="fa fa-rotate-left"></i>&nbsp;<fmt:message key="label.layouttesti.annulla_modifiche"/>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
						</tbody>
                    </table>
	            </fieldset>
            </div>		
		</div>
	</body>
</html>