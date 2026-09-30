<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Dyn2MetadatiContesti"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${dyn2metadaticontesti.id.codice==null}">
        <fmt:message
            key="dyn2metadaticontesti.label.nuovo_dyn2metadaticontesti.title" />
    </c:if> <c:if test="${dyn2metadaticontesti.id.codice!=null}">
        <fmt:message
            key="dyn2metadaticontesti.label.dettaglio_dyn2metadaticontesti.title" />
    </c:if></title>

        <style>
        #dettaglioSchede {
            padding: 16px 0 16px 0;
        }
        
        #functions ul {
            padding: 16px 0 16px 0;
        }
        
        .divisore {
            margin-top: 30px;
        }
        </style>
    </head>
<body>


    <script type='text/javascript'>
                
            (function ($) {

                /*/////////////////////////////////////////////////////////
                // Validazione
                //*/
                class Validator {

                    constructor(formGroup) {
                        this.isValid = true;
                        this.campo = formGroup.querySelector('.control-to-validate');
                        this.campoErrore = formGroup.querySelector(".validation-feedback");
                        this.campoErrore.style.display = 'none';
                        this.onPostValidate = null;

                        this.campo.addEventListener('blur', () => {
                            // this.onPreValidation();
                            this.doValidation();
                        });
                    }

                    get valoreCampo() { return this.campo.value || ''; }
                    set valoreCampo(value) { this.campo.value = value; }

                    doValidationInternal() {
                        return true;
                    }

                    doValidation() {
                        this.isValid = this.doValidationInternal();

                        this.campoErrore.style.display = this.isValid ? 'none' : 'block';
                        this.campo.classList.toggle('input-error', !this.isValid);

                        if (this.onPostValidate) {
                            this.onPostValidate();
                        }
                    }
                }


                class CampoObbligatorioValidator extends Validator {

                    constructor(formGroup) {
                        super(formGroup);
                    }

                    doValidationInternal() {
                        return this.valoreCampo !== '';
                    }
                }

                class ContestoValidator extends Validator {

                    constructor(formGroup) {
                        super(formGroup);
                    }

                    doValidationInternal() {

                        this.valoreCampo = this.valoreCampo.toUpperCase()

                        const regex = /^[A-Z0-9_]*$/;

                        return this.valoreCampo !== '' && regex.test(this.valoreCampo);
                    }
                }

                class GruppoValidatori {

                    constructor(validatori, bottoneSalvataggio) {
                        this.validatori = validatori;
                        this.validatori.forEach((validatore) => validatore.onPostValidate = () => this.onPostValidate());
                        this.bottoneSalvataggio = bottoneSalvataggio;
                        this.isValid = true;
                        
                        this.bottoneSalvataggio.addEventListener('click', (e) => {
                            this.triggerValidation();
                            
                            if (!this.isValid) {
                                e.preventDefault();
                                return false;
                            }
                            
                            return true;
                        })
                    }
                    
                    triggerValidation() {
                        this.validatori.forEach((validatore) => validatore.doValidation());
                    }

                    onPostValidate() {

                        this.isValid = this.validatori.reduce((result, validatore) => result && validatore.isValid, true);

                        this.bottoneSalvataggio.toggleAttribute('disabled', !this.isValid);

                        if (this.bottoneSalvataggio.hasAttribute('disabled')) {
                            this.bottoneSalvataggio.setAttribute('disabled', 'disabled');
                        }                       
                    }
                }

                
                /*/////////////////////////////////////////////////////////
                // Editor testata
                //*/
                class EditorTestata {

                    constructor(container) {
                        const bottone = container.querySelector('.bottone-salva');
                        const validatori = [].concat(
                            Array.from(container.querySelectorAll('.validate-contesto')).map((campo) => new ContestoValidator(campo)),
                            Array.from(container.querySelectorAll('.validate-required')).map((campo) => new CampoObbligatorioValidator(campo))
                        );
                        this.gruppoValidatori = new GruppoValidatori(validatori, bottone);
                    }
                }



                /*/////////////////////////////////////////////////////////
                // Form inserimento nuovo metadato
                //*/
                class FormInserimentoDettagli {

                    constructor(idTestata, urlCreazioneMetadato, container) {
                        this.idTestata = idTestata;
                        this.urlCreazioneMetadato = urlCreazioneMetadato;
                        this.bottoneSalvataggio = container.querySelector('.bottone-salva');
                        this.campoContesto = container.querySelector('#contestocampo_id');
                        this.campoIdCampoDinamico = container.querySelector('#metadato_id_hidden');
                        this.campoDescrizioneCampoDinamico = container.querySelector('#metadato_id');
                        this.onError = void (0);

                        this.validatoriContesto = Array.from(container.querySelectorAll('.validate-contesto')).map((campo) => new ContestoValidator(campo));

                        this.validatore = new GruppoValidatori(this.validatoriContesto, this.bottoneSalvataggio);

                        this.bottoneSalvataggio.addEventListener('click', (e) => {
                            e.preventDefault();
                            this.onSalvaClick(e);
                            return false;
                        });
                    }

                    async onSalvaClick(e) {

                        disableFunctions();

                        const data = await this.creaMetadato();

                        console.log('Esito salvataggio: ', data);

                        if (data.toLowerCase() == 'ok') {
                            document.location.reload();
                            return;
                        }

                        enableFunctions();
                        this.onError(data);
                    }

                    creaMetadato() {

                        const codiceCampo = this.campoIdCampoDinamico.value || '';
                        const contestoCampo = this.campoContesto.value || '';
                        const idTestata = this.idTestata;

                        if (codiceCampo === '' || contestoCampo === '' || !idTestata) {
                            return false;
                        }

                        return $.ajax({
                            url: this.urlCreazioneMetadato,
                            method: 'GET',  // TODO: può lavorare in post?
                            data: {
                                codiceMetadatoContesti: idTestata,
                                codiceCampo: codiceCampo,
                                contestoCampo: contestoCampo
                            },
                            context: document.body,
                            cache: false,
                            dataType: "html",
                            error: (jqXHR, textStatus, errorThrown) => {
                                console.error([jqXHR, textStatus, errorThrown]);

                                this.onError("Si è verificato un errore di sistema, riprovare in un secondo momento");
                            }
                        });
                    }
                }


                /*//////////////////////////////
                // Form principale di dettaglio 
                */
                class EditorDettaglio {

                    constructor(idTestata, contenitoreSchede) {

                        this.idTestata = parseInt(idTestata);
                        this.urlCaricamentoDettaglio = '${pageContext.request.contextPath}/dyn2metadaticontesti/ajaxDettaglioMetadati.htm';
                        this.urlSalvataggio = "${pageContext.request.contextPath}/dyn2metadaticontesti/ajaxAssegnaMetadati.htm";
                        this.urlEliminazione = "${pageContext.request.contextPath}/dyn2metadaticontesti/ajaxEliminaMetadato.htm";
                        this.container = contenitoreSchede;

                        if (this.idTestata > 0) {
                            this.inizializza();
                        }
                    }

                    async inizializza() {

                        try {
                            // Mostro lo spinner di caricamento
                            disableFunctions();
                            console.log('spinner');
                            // Attendo l'esito della chiamata asincrona
                            await $.ajax({
                                url: this.urlCaricamentoDettaglio,
                                context: document.body,
                                cache: false,
                                dataType: "html",
                                method: 'GET',
                                data: {
                                    codiceMetadatoContesto: this.idTestata
                                },
                                success: (data) => {
                                    if (!data) {
                                        return;
                                    }
                                    console.log('dati caricati');
                                    
                                    // Uso jquery perché settando l'innerHTML da javascript non vengono eseguiti gli script che 
                                    // permettono di popolare l'autocomplete
                                    $(this.container).html(data);

                                    this.divErrore = document.querySelector('#errore-dettaglio-schede');

                                    this.formInserimentoDettagli = new FormInserimentoDettagli(this.idTestata, this.urlSalvataggio, document.querySelector('#form-inserimento-righe'));
                                    this.formInserimentoDettagli.onError = (messaggio) => this.mostraErrore(messaggio);

                                    this.listaBottoniElimina = new ListaBottoniElimina(this.urlEliminazione, document.querySelectorAll('.elimina-riga'));
                                    this.listaBottoniElimina.onError = (messaggio) => this.mostraErrore(messaggio);
                                },
                                error: (jqXHR, textStatus, errorThrown) => {
                                    console.error([jqXHR, textStatus, errorThrown]);

                                    this.mostraErrore("Si è verificato un errore di sistema, riprovare in un secondo momento");

                                    enableFunctions();
                                }
                            });
                        } finally {
                            // Nascondo lo spinner di caricamento (anche se si è verificata un'eccezione)
                            enableFunctions();
                            console.log('fine spinner');
                        }
                    }

                    mostraErrore(testo) {

                        this.divErrore.innerHTML = "<div class='error_header'>" + testo + "</div>";
                        this.divErrore.style.display = 'block';
                    }
                }


                /*//////////////////////////////////////////////////
                // Lista di bottoni per l'eliminazione dei dettagli
                */
                class ListaBottoniElimina {

                    constructor(urlEliminazione, bottoniElimina) {

                        this.onError = void (0);
                        this.urlEliminazione = urlEliminazione;
                        this.messaggioErrore = 'Eliminare l\'elemento selezionato? L\'operazione non potrà essere annullata';

                        bottoniElimina.forEach((btn) => {
                            btn.addEventListener('click', (e) => {
                                e.preventDefault();
                                this.onEliminaClick(e);
                                return false;
                            });
                        });
                    }

                    async onEliminaClick(e) {

                        if (!confirm(this.messaggioErrore)) {
                            return;
                        }

                        disableFunctions();

                        const data = await this.eliminaMetadati(e.currentTarget.dataset.id);

                        if (data.toLowerCase() === 'ok') {
                            document.location.reload();
                            return;
                        }

                        enableFunctions();
                        this.onError(data);
                    }

                    eliminaMetadati(idDettaglio) {

                        return $.ajax({
                            url: this.urlEliminazione,
                            data: {
                                codiceMetadato: idDettaglio
                            },
                            context: document.body,
                            cache: false,
                            dataType: "html",
                            error: (jqXHR, textStatus, errorThrown) => {
                                console.error([jqXHR, textStatus, errorThrown]);

                                this.onError("Si è verificato un errore di sistema, riprovare in un secondo momento");
                            }
                        });
                    }
                }

                $(() => {
                    const editorTestata = new EditorTestata(document.querySelector('#editor-testata'));
                    const editorDettaglio = new EditorDettaglio(${ dyn2metadaticontesti.id.codice == null } ? '-1' : '${dyn2metadaticontesti.id.codice}', document.querySelector('#dettaglioSchede'));
                });

            })(jQuery); 
                
            </script>



    <span class="titoloPagina"> 
        <c:if test="${dyn2metadaticontesti.id.codice==null}">
            <fmt:message key="dyn2metadaticontesti.label.nuovo_dyn2metadaticontesti.title" />
        </c:if> 
        
        <c:if test="${dyn2metadaticontesti.id.codice!=null}">
            <fmt:message key="dyn2metadaticontesti.label.dettaglio_dyn2metadaticontesti.title" />
        </c:if>
    </span>
    
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="form" />
    </jsp:include>
    
    <div id="subcontent">
        <spring-form:form commandName="dyn2metadaticontesti"
            name="inviodati">
            <jsp:include page="../includes/displayGlobalMessages.jsp">
                <jsp:param name="commandName" value="dyn2metadaticontesti" />
            </jsp:include>
            
            <input type="hidden" name="codiceMetadatoContesto"
                id="codiceMetadatoContesto_id"
                value="${dyn2metadaticontesti.id.codice}" />

            <div id="editor-testata">
                <table>
                    <tr class="validate-required">
                        <td><fmt:message
                                key="dyn2metadaticontesti.label.descrizione" />
                        </td>
                        <td><spring-form:input id="descrizione_id"
                                path="descrizione" size="100"
                                cssStyle=""
                                cssClass="control-to-validate" /> <spring-form:errors
                                path="descrizione" cssClass="error" />
                            <div id="errori_contesto"
                                class="error validation-feedback">
                                Il campo è obbligatorio</div></td>
                    </tr>
                    <tr class="validate-contesto">
                        <td><fmt:message
                                key="dyn2metadaticontesti.label.contesto" />
                        </td>
                        <td><spring-form:input id="contesto_id"
                                path="contesto" size="100"
                                cssClass="control-to-validate" /> <init:help
                                idHelp="contesto_help_id"
                                textKey="dyn2metadaticontesti.help.contesto" />
                            
                            <spring-form:errors path="contesto"
                                cssClass="error" />
                                
                            <div id="errori_contesto"
                                class="error validation-feedback">
                                Il campo contiene valori non validi. Può
                                contenere solo numeri, lettere e _ senza
                                spazi.</div></td>
                    </tr>
                </table>


                <div id="functions">
                    <ul>
                        <c:if
                            test="${dyn2metadaticontesti.id.codice==null}">
                            <li><a
                                href="javascript:doSubmit('insert.htm','',document.inviodati)"
                                id="inserimento" class="bottone-salva"><fmt:message
                                        key="button.insert" /></a></li>
                        </c:if>
                        <c:if
                            test="${dyn2metadaticontesti.id.codice!=null}">
                            <li><a
                                href="javascript:doSubmit('update.htm','',document.inviodati)"
                                id="update" class="bottone-salva"><fmt:message
                                        key="button.update" /></a></li>
                            <li><a
                                href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
                                        key="button.delete" /></a></li>
                        </c:if>
                        <li><a
                            href="javascript:doHref('list.htm','')"><fmt:message
                                    key="button.back" /></a></li>
                    </ul>
                </div>
            </div>


        </spring-form:form>
    </div>


    <br />
    <div class="divisore"></div>

    <!-- SCHEDE PER DYN2METADATI (in seguito modificare qua sotto per aggiungere altre schede -->
    <c:if test="${dyn2metadaticontesti.id.codice!=null}">
        <div class="subcontent">
            <div id="navigation">
                <ul class="listaSchede">
                    <li id="schedaMetadati_id"><a href="#"><fmt:message
                                key="label.metadato" /></a></li>
                </ul>
            </div>

            <div id="dettaglioSchede"></div>

        </div>
    </c:if>

</body>
</html>