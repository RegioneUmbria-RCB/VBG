<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>
            <fmt:message key="label.sposta_presenze" />
        </title>
        <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
    </head>

    <body>
        <jsp:include page="../includes/history.jsp">
            <jsp:param name="path" value="../autorizzazioni/spostaPresenze" />
        </jsp:include>

        <span class="titoloPagina">
            <fmt:message key="label.sposta_presenze" />
        </span>
        <jsp:include page="../includes/innerNavigation.jsp">
            <jsp:param name="navmode" value="form" />
        </jsp:include>

        <div class="vbg-form">
            <fieldset>
                <legend><fmt:message key="label.dati_istanza"/></legend>
                <c:import url="/ajax/dettaglioIstanza.htm">
                    <c:param name="codIstanza">${param.codiceIstanza}</c:param>
                </c:import>
            </fieldset>
        </div>

        <br class="clear"/>

        <div id="subcontent">
            <%-- "spostaPresenzeCommand" fa riferimento ad un model.addAttribute("spostaPresenzeCommand", new SpostaPresenzeCommand()); --%>
            <%-- "inviodati" è il nome della form che verrà presa nella submit tramite document.inviodati --%>
            <spring-form:form commandName="spostaPresenzeCommand" name="inviodati">
                <jsp:include page="../includes/displayGlobalMessages.jsp">
                    <jsp:param name="commandName" value="spostaPresenzeCommand" />
                </jsp:include>
                <div class="vbg-form">
                    <fieldset>
                        <legend><fmt:message key="label.presenze_da_spostare" /></legend>
                        <spring-form:hidden id="codiceIstanza" path="codiceIstanza" />

                        <init:editLabel key="label.descrizione_sposta_presenze" role="ROLE_EDITLABEL" />
                        <br><br><br>

                        <div class="form-group">
                            <label><fmt:message key="label.sorgente" /></label>
                            <spring-form:select id="selectAutorizzazioneSorgente" path="autorizzazioneSorgente.id" >
                                <spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
                                <spring-form:options items="${autorizzazioniSorgenteList}" itemLabel="autorizNumeroFull" itemValue="id"/>
                            </spring-form:select>
                            <label style="width: auto; margin-left: 20px"><fmt:message key="label.autorizzazione_sorgente" /></label>

                            <spring-form:errors path="autorizzazioneSorgente.id" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label><fmt:message key="label.destinazione" /></label>
                            <spring-form:select id="selectAutorizzazioneDestinataria" path="codiceAutorizzazioneDestinataria" disabled="true">
                                <spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
                                <%-- Inseriti i valori della sorgente per evitare il resizing iniziale della select --%>
                                <spring-form:options items="${autorizzazioniSorgenteList}" itemLabel="autorizNumeroFull" itemValue="id"/>
                            </spring-form:select>
                            <label style="width: auto; margin-left: 20px"><fmt:message key="label.autorizzazione_destinazione" /></label>

                            <spring-form:errors path="codiceAutorizzazioneDestinataria" cssClass="error" />
                        </div>
                    </fieldset>
                </div>
            </spring-form:form>
        </div>

        <script type="text/javascript">
            document.addEventListener('DOMContentLoaded', function () {
                const selectAutorizzazioneSorgente = document.getElementById('selectAutorizzazioneSorgente');
                const selectAutorizzazioneDestinataria = document.getElementById('selectAutorizzazioneDestinataria');

                // Salva le opzioni originali per la seconda select
                const originalOptions = Array.from(selectAutorizzazioneSorgente.options).map(opt => ({
                    value: opt.value,
                    text: opt.text
                }));

                selectAutorizzazioneSorgente.addEventListener('change', function () {
                    const selectedValue = this.value;

                    // Abilita/disabilita la seconda select
                    selectAutorizzazioneDestinataria.disabled = !selectedValue;

                    // Salva temporaneamente l'opzione iniziale ("Seleziona")
                    const firstOption = selectAutorizzazioneDestinataria.querySelector("option[value='']");

                    // Pulisce le opzioni della seconda
                    selectAutorizzazioneDestinataria.innerHTML = '';

                    // Reinserisce l'opzione iniziale
                    if (!selectedValue && firstOption) {
                        selectAutorizzazioneDestinataria.appendChild(firstOption);
                    }

                    // Ricostruisce le opzioni escluse quella selezionata nella prima
                    originalOptions.forEach(opt => {
                        if (opt.value !== selectedValue) {
                            const option = new Option(opt.text, opt.value);
                            selectAutorizzazioneDestinataria.add(option);
                        }
                    });
                });
            });

            function spostaPresenze() {
                if (convalidaTendine()) {
                    doSubmit('confermaSpostaPresenze.htm', '', document.inviodati);
                }
            }

            function convalidaTendine() {
                if (document.getElementById('selectAutorizzazioneSorgente')) {
                    if (document.getElementById('selectAutorizzazioneSorgente').value == '') {
                        alert("<fmt:message key="label.sorgente.obbligatoria" />");
                        document.getElementById('selectAutorizzazioneSorgente').focus();
                        return false;
                    }
                }
                if (document.getElementById('selectAutorizzazioneDestinataria')) {
                    if (document.getElementById('selectAutorizzazioneDestinataria').value == '') {
                        alert("<fmt:message key="label.destinazione.obbligatoria" />");
                        document.getElementById('selectAutorizzazioneDestinataria').focus();
                        return false;
                    }
                }
                return true;
            }

            function apriConfermaSpostaPresenze() {
                if (convalidaTendine()) {
                    aggiornaDatiModale();
                    document.querySelector('#popup-confermaSpostamento').open();
                }
            }

            function aggiornaDatiModale() {
                const selectAutorizzazioneSorgente = document.getElementById("selectAutorizzazioneSorgente");
                const selectAutorizzazioneDestinataria = document.getElementById("selectAutorizzazioneDestinataria");

                const sorgenteText = selectAutorizzazioneSorgente?.options[selectAutorizzazioneSorgente.selectedIndex]?.text || "";
                const destinatariaText = selectAutorizzazioneDestinataria?.options[selectAutorizzazioneDestinataria.selectedIndex]?.text || "";

                document.getElementById('codAutorizzazioneSorgente').textContent = sorgenteText;
                document.getElementById('codAutorizzazioneDestinataria').textContent = destinatariaText;
            }
        </script>

        <vbg-modal id="popup-confermaSpostamento">
            <div slot='body'>
                <h1 style="margin-top: 0"><fmt:message key="label.conferma_operazione" /></h1>
                <p><fmt:message key="label.modale_sposta_presenza.parte1" />
                    <b><spring-security:authentication property="principal.responsabile" /></b>
                    <fmt:message key="label.modale_sposta_presenza.parte2" />
                    "<span id="codAutorizzazioneSorgente"></span>"
                    <fmt:message key="label.modale_sposta_presenza.parte3" />
                    "<span id="codAutorizzazioneDestinataria"></span>"
                    <br>
                    <fmt:message key="label.modale_sposta_presenza.parte4" />
                </p>
            </div>
            <div slot='footer' style="text-align: right">
                <a class="btn btn-primary" href="javascript:spostaPresenze()"><fmt:message key="button.sposta" /></a>
                <a class="btn btn-secondary" id="closeButtonModal"><fmt:message key="button.annulla" /></a>
            </div>
        </vbg-modal>

        <div class="form-button">
            <a class="btn btn-primary" href="javascript:apriConfermaSpostaPresenze();"><fmt:message key="button.sposta" /></a>
            <a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
        </div>
    </body>
</html>