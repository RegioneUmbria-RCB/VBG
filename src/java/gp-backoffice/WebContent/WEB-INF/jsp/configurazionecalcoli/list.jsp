<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%><html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="configurazionecalcoli.label.lista.title" /></title>
    <script type="text/javascript">
	    vbg.ready(() => {
	        let input = document.getElementById("input_ricerca");
	        input.addEventListener('keyup',(e) => {
	            e.preventDefault();
	        	filtraTestoCelle();
	        });
	    });
    
	    async function aggiornaDescrizione(codice,descrizione){
	        
	    	disableFunctions();
	    	
	    	const data = new URLSearchParams();
	        data.append('codice',codice);
	        data.append('descrizione',descrizione);
	        
	        let url = "${pageContext.request.contextPath}/configurazionecalcoli/ajaxAggiornaDescrizione.htm";
            const response = await fetch(url, {
                method: "POST",
                cache: "no-cache",
                body: data       
            });
            
            if (response.status !== 200) {
                let errore = await response.text();
                console.error(errore);
                throw errore;
           } else {
        	    alert("Aggiornamento completato con successo");
           }
            
            enableFunctions();
	    }
	    
	    function filtraTestoCelle() {
	        
	        let input = document.getElementById("input_ricerca");
	        let filter = input.value.toUpperCase();
	        let tr = document.querySelectorAll("table.vbg-table>tbody>tr");
	        let trovato = false;

	        for (i = 0; i<tr.length; i++) {
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
	          } else{
	            tr[i].style.display = "none";                   
	          }
	        }
	    }
	    
    </script>
</head>
<body>
    <span class="titoloPagina"><fmt:message key="configurazionecalcoli.label.lista.title" /></span>
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
        <jsp:param name="path" value="../configurazionecalcoli/list" /> 
    </jsp:include>
    <jsp:include page="../includes/displayGlobalMessages.jsp">
        <jsp:param name="commandName" value="listaCalcoli" />
    </jsp:include>
    <div id="subcontent">
        <form name="configurazioneCalcoliListForm" action="list.htm">
            <div class="vbg-form">
                <fieldset>
                    <legend>
                        <fmt:message key="label.ricerca" />
                    </legend>
                    <div class="form-group">
                        <div class="input-icons">
                            <i class="fa fa-search icon"></i> <input id="input_ricerca"
                                type="text" placeholder="Cerca" />
                        </div>
                        <div class="input-help">
                            <fmt:message key="label.messaggio_ricerca_tabella" />
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend><fmt:message key="configurazionecalcoli.label.lista.title"/></legend>
                    <table class="vbg-table">
                        <thead>
                            <tr>
                                <th><fmt:message key="label.codice"/></th>
                                <th><fmt:message key="label.descrizione"/></th>
                                <th><fmt:message key="label.versione"/></th>
                                <th><fmt:message key="label.azioni"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${calcoliList}" var="calcolo">
                                <tr>
                                    <td>
                                        <a href="${calcolo.url}">${calcolo.id}</a>
                                    </td>
                                    <td>
                                        <input type="text" value ="${calcolo.descrizione}" style="width: 100%" onChange="javascript:aggiornaDescrizione(${calcolo.id},this.value);"></input>
                                    </td>
                                    <td>${calcolo.versione }</td>
                                    <td>
	                                    <a
	                                     href="javascript: void(0)"; onclick="doHref('elimina.htm?codice=${calcolo.id}','<fmt:message key="javascript.confirm.delete" />');"
	                                     title="<fmt:message key="button.elimina"/>">
	                                        <i class="fa fa-trash"></i>
	                                    </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </div>
        </form>
	    <div class="form-button">       
	        <a class="btn btn-primary" href="${urlNuovoCalcolo}"><fmt:message key="button.new" /></a>
	        <a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>        
	    </div>
    </div>
</body>