<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.utils.Utilities" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="taskscheduler.label.lista_taskscheduler.title" /></title>
	<script type="text/javascript">
		vbg.ready(() => {
			const ricTestuale = document.getElementById('input_ricerca');
			const ricAttivo = document.getElementById('ricerca_attivo');
			const ricEsecuzione = document.getElementById('ricerca_esecuzione');

			ricTestuale.addEventListener("keyup", applicaFiltri);
			ricAttivo.addEventListener("change", applicaFiltri);
			ricEsecuzione.addEventListener("change", applicaFiltri);
            
            function applicaFiltri(){
                const filtroTesto = ricTestuale.value.toUpperCase();
                const filtroAttivo = ricAttivo.value.toUpperCase();
                const filtroEsecuzione = ricEsecuzione.value.toUpperCase();
                
                const righe = document.querySelectorAll('table.vbg-table>tbody>tr');

                righe.forEach(tr => {
                    let visibile = true;
                    
                 // ---- FILTRO TESTO ----
                    if (filtroTesto !== '') {
                        const celle = tr.querySelectorAll("td");
                        let trovato = false;
                        celle.forEach(td => {
                            const txt = td.textContent.toUpperCase();
                            if (txt.includes(filtroTesto)) {
                            	trovato = true;
                            }
                        });

                        if (!trovato){
                            visibile = false;
                        }
                    }

                    // ---- FILTRO ATTIVO ----
                    if (visibile == true && filtroAttivo !== '') {
                        if (tr.dataset.attivo.toUpperCase() !== filtroAttivo) {
                            visibile = false;
                        }
                    }

                    // ---- FILTRO ESECUZIONE ----
                    if (visibile == true && filtroEsecuzione !== '') {
                        if (tr.dataset.esecuzione.toUpperCase() !== filtroEsecuzione) {
                            visibile = false;
                        }
                    }

                    tr.style.display = visibile ? "" : "none";
                });
            }
		});
	</script>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="taskscheduler.label.lista_taskscheduler.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
			<jsp:param name="commandName" value="sorteggitestata" />
		</jsp:include>
		<form name="taskschedulerForm" action="list.htm">
            <div class="vbg-form">
	            <fieldset class="collassabile">
	                <legend><fmt:message key="label.form_ricerca" /> </legend>
	                <div class="form-group">
	                    <div class="input-icons">
	                        <i class="fa fa-search icon"></i>
	                        <input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
	                        <label><fmt:message key="label.messaggio_ricerca_tabella" /></label>
	                    </div>
	                </div>
	                <div class="form-group">
                        <label><fmt:message key="label.attivo" /></label>
                        <select id="ricerca_attivo">
                            <option value=""></option>
                            <option value="true">SI</option>
                            <option value="false">NO</option>
                        </select>
	                </div>
	                <div class="form-group">
	                   <label><fmt:message key="label.in_esecuzione" /></label>
	                    <select id="ricerca_esecuzione">
	                        <option value=""></option>
	                        <option value="true">SI</option>
	                        <option value="false">NO</option>
	                    </select>
	                </div>
	            </fieldset> 
	            <fieldset>
	                <legend><fmt:message key="taskscheduler.label.elenco" /></legend>
	                <table class="vbg-table">
	                    <thead>
	                        <tr>
	                            <th><fmt:message key="label.descrizione_operazione"/></th>
	                            <th><fmt:message key="label.operazione"/></th>
	                            <th><fmt:message key="label.intervallo"/></th>
	                            <th><fmt:message key="label.prossima_esecuzione"/></th>
	                            <th><fmt:message key="label.attivo"/></th>
	                            <th><fmt:message key="label.in_esecuzione"/></th>
	                            <th><fmt:message key="label.azioni"/></th>
	                           </tr>       
	                    </thead>
	                    <tbody>
	                        <c:forEach items="${taskschedulerList}" var="riga" varStatus="i">
	                            <tr data-attivo="${riga.attivo}" data-esecuzione="${riga.inEsecuzione}">
	                                <td>${riga.id}&nbsp;-&nbsp;${riga.descrizione}</td>
	                                <td>${riga.operazione}</td>
	                                <td>${riga.descrizioneIntervallo}</td>
	                                <td>${Utilities.formatDate(riga.prossimaEsecuzione,true)}</td>
	                                <td>${riga.attivo ? 'SI' : 'NO'}</td>
	                                <td>${riga.inEsecuzione ? 'SI' : 'NO'}</td>
	                                <td>
	                                   <a href="view.htm?codice=${riga.id}">
	                                       <i class="fa fa-edit vbg-link fa-lg"></i>
	                                   </a>
	                                   <a href="javascript:doHref('eliminaOperazione.htm?codiceoperazione=${riga.id}','<fmt:message key="javascript.confirm.delete" />')">
	                                       <i class="fa fa-times vbg-link fa-lg"></i>
	                                   </a>
	                                </td>
	                            </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
	            </fieldset>
            </div>
		</form>
	</div>
	<div>
	   <a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
	   <a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>