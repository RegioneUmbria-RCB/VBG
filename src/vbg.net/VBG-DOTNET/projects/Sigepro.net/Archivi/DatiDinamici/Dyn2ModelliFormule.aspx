<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" ValidateRequest="false" AutoEventWireup="true" CodeBehind="Dyn2ModelliFormule.aspx.cs"
    Inherits="Sigepro.net.Archivi.DatiDinamici.Dyn2ModelliFormule" Title="Formula scheda" %>

<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="bs" Namespace="SIGePro.WebControls.Bootstrap" Assembly="SIGePro.WebControls" %>
<%@ Register Src="~/Archivi/DatiDinamici/CampiCollegatiEditor.ascx" TagPrefix="uc1" TagName="CampiCollegatiEditor" %>


<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<asp:Content runat="server" ContentPlaceHolderID="headPagina">

    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/CodeMirror/lib/codemirror.js") %>"></script>
    <link rel="stylesheet" href="<%=ResolveClientUrl("~/js/CodeMirror/lib/codemirror.css") %>" />
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/CodeMirror/mode/clike/clike.js") %>"></script>
    <link rel="stylesheet" href="<%=ResolveClientUrl("~/js/CodeMirror/theme/neat.css") %>" />
    <link rel="stylesheet" href="<%=ResolveClientUrl("~/stili/dyn2-modelli-formule.css") %>" />

    <style type="text/css">
        /*.activeline 
      {
      	background: #f0fcff !important;
      	z-index: -2;
      }*/
        .descrizione-editor {
            position: absolute;
            padding: 4px 12px 4px 12px;
            background-color: #f7f7f7;
            border: 1px solid #dfdfdf;
            border-bottom: 0px;
            display: none;
            top: -28px;
            z-index: 99;
        }

            .descrizione-editor.focused {
                display: block;
            }

        .activeLine {
            background-color: rgba(200,236,255, 0.3) !important;
            margin-left: -6px !important;
            padding-left: 6px !important;
            margin-top: -1px !important;
            padding-top: 1px !important;
            margin-bottom: -1px !important;
            padding-bottom: 1px !important;
        }
    </style>

    <script type="text/javascript">

        let _formulaCodeMirror = null;
        let _usingCodeMirror = null;
        let _servicesCodeMirror = null;
        let _editorUsingVisibile = true;
        let _editorServiziVisibile = true;

        $(function () {

            function resetActiveLines() {
                _formulaCodeMirror.setLineClass(_formulaCodeMirror.activeLineTracker, null);
                _usingCodeMirror.setLineClass(_usingCodeMirror.activeLineTracker, null);
                _servicesCodeMirror.setLineClass(_servicesCodeMirror.activeLineTracker, null);
            }

            let codeMirrorOptions = {
                mode: "text/x-csharp",
                lineNumbers: true,
                theme: 'neat',
                tabMode: 'default',
                tabSize: 4,
                smartIndent: false,
                indentUnit: 4,
                indentWithTabs: true,
                enterMode: 'keep',
                onKeyEvent: (instance, e) => {

                    if (instance === _formulaCodeMirror && e.type === 'keyup' && e.key === "Enter") {
                        const prevText = _formulaCodeMirror.getLine(_formulaCodeMirror.getCursor().line - 1);
                        if (isDichiarazioneFunzione(prevText)) {
                            aggiornaListaMetodi();
                        }
                    }
                },
                onCursorActivity: function (element) {
                    resetActiveLines();
                    element.activeLineTracker = element.setLineClass(element.getCursor().line, "activeLine");
                },
                extraKeys: {
                    "F11": function (element) {

                        var scroller = element.getScrollerElement();

                        if (scroller.className.search(/\bCodeMirror-fullscreen\b/) === -1) {
                            scroller.className += " CodeMirror-fullscreen";
                            scroller.style.height = "100%";
                            scroller.style.width = "100%";
                        } else {
                            scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
                            scroller.style.height = '';
                            scroller.style.width = '';
                        }
                        element.refresh();
                    },
                    "Esc": function () {
                        var scroller = _formulaCodeMirror.getScrollerElement();
                        if (scroller.className.search(/\bCodeMirror-fullscreen\b/) !== -1) {
                            scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
                            scroller.style.height = '';
                            scroller.style.width = '';
                            element.refresh();
                        }
                    },
                    "Ctrl-S": function () {
                        document.getElementById('<%=cmdSalva.ClientID%>').click();

                        return false;
                    }
                }
            }

            function creaDivDescrizione(testoDescrizione, parentElement) {
                var el = document.createElement('div');
                el.className = 'descrizione-editor';
                el.innerText = testoDescrizione;

                parentElement.appendChild(el);
                parentElement.style.position = 'relative';

                return el;
            }


            let ddl = document.getElementById('ctl00_ContentPlaceHolder1_ddlEvento__DropDownList');
            var text = ddl.options[ddl.options.selectedIndex].text;

            let testo = `Formula per ${text}`;
            let titoloEditorUsing = creaDivDescrizione('Blocchi using condivisi tra tutte le formule', document.getElementById('<%=txtUsing.ClientID%>').parentElement);
            let titoloEditorFormula = creaDivDescrizione(testo, document.getElementById('<%=txtScript.ClientID%>').parentElement);
            let titoloEditorServizi = creaDivDescrizione('Servizi iniettati', document.getElementById('<%=txtServices.ClientID%>').parentElement);

            _formulaCodeMirror = CodeMirror.fromTextArea(document.getElementById('<%= txtScript.ClientID%>'), codeMirrorOptions);
            _usingCodeMirror = CodeMirror.fromTextArea(document.getElementById('<%= txtUsing.ClientID%>'), codeMirrorOptions);
            _servicesCodeMirror = CodeMirror.fromTextArea(document.getElementById('<%= txtServices.ClientID%>'), codeMirrorOptions);

            _usingCodeMirror.setOption('onFocus', (instance, event) => {
                titoloEditorUsing.classList.add('focused');
            });

            _usingCodeMirror.setOption('onBlur', (instance, event) => {
                titoloEditorUsing.classList.remove('focused');
            });

            _formulaCodeMirror.setOption('onFocus', (instance, event) => {
                titoloEditorFormula.classList.add('focused');
            });

            _formulaCodeMirror.setOption('onBlur', (instance, event) => {
                titoloEditorFormula.classList.remove('focused');
            });

            _servicesCodeMirror.setOption('onFocus', (instance, event) => {
                titoloEditorServizi.classList.add('focused');
            });

            _servicesCodeMirror.setOption('onBlur', (instance, event) => {
                titoloEditorServizi.classList.remove('focused');
            });


            _formulaCodeMirror.activeLineTracker = _formulaCodeMirror.setLineClass(0, "activeLine");
            _usingCodeMirror.activeLineTracker = _usingCodeMirror.setLineClass(0, 'activeLine');
            _usingCodeMirror.setSize('100%', '100px');
            _servicesCodeMirror.activeLineTracker = _servicesCodeMirror.setLineClass(0, 'activeLine');
            _servicesCodeMirror.setSize('100%', '100px');
            /*
             * Gestione visualizzazione dell'editor per i blocchi using
             */
            function resizeEditor() {
                let margin = 2;

                let winHeight = $(window).height();
                let elTop = $(_formulaCodeMirror.getWrapperElement()).offset().top;
                let newHeight = winHeight - elTop - margin;

                _formulaCodeMirror.setSize('100%', newHeight.toString() + 'px');
            }


            function aggiornaVisualizzazioneEditor(element, toggle) {
                // toggle = $('#<%=chkNascondiUsing.ClientID%>').is(':checked');

                if (toggle) {
                    element.getWrapperElement().style.display = 'block';
                } else {
                    element.getWrapperElement().style.display = 'none';
                }
                resizeEditor();
            }

            let chkMostraUsing = document.querySelector('#<%=chkNascondiUsing.ClientID%>');
            chkMostraUsing.addEventListener('click', () => aggiornaVisualizzazioneEditor(_usingCodeMirror, chkMostraUsing.checked));
            aggiornaVisualizzazioneEditor(_usingCodeMirror, chkMostraUsing.checked);

            let chkNascondiServizi = document.querySelector('#<%=chkNascondiServizi.ClientID%>');
            chkNascondiServizi.addEventListener('click', () => aggiornaVisualizzazioneEditor(_servicesCodeMirror, chkNascondiServizi.checked));
            aggiornaVisualizzazioneEditor(_servicesCodeMirror, chkNascondiServizi.checked);


            $(window).on('resize', aggiornaVisualizzazioneEditor);

            setTimeout(() => {
                $('.esito-salvataggio').hide('slow');
            }, 2000);

            // Combo delle funzioni
            const comboFunzioni = document.getElementById('funzioni');
            comboFunzioni.addEventListener('change', (e) => {
                if (comboFunzioni.value) {
                    const riga = parseInt(comboFunzioni.value);
                    const y = _formulaCodeMirror.charCoords({ line: riga, ch: 0 }, "local").y;
                    const margin = 12;
                    _formulaCodeMirror.setCursor(riga, 0);
                    _formulaCodeMirror.scrollTo(0, y - margin);
                    setTimeout(() => _formulaCodeMirror.focus(), 50);
                    comboFunzioni.value = '';
                }
            });
            aggiornaListaMetodi();
        });

        const isDichiarazioneFunzione = (val) => {
            return (
                val.startsWith('public ') ||
                val.startsWith('private ') ||
                val.startsWith('protected ')
            ) && (val.indexOf('(') !== -1 && val.indexOf(')') !== -1);
        };

        const estraiNomeFunzione = (val) => {
            const splitted = val.split(' ');

            if (splitted.length < 3) return '';

            let nomeFunzione = splitted[2];

            if (nomeFunzione.indexOf('(') !== -1) {
                nomeFunzione = nomeFunzione.slice(0, nomeFunzione.indexOf('(')).trim();
            }

            var argomenti = val.slice(val.indexOf('(') + 1, val.lastIndexOf(')'))
                .split(',')
                .map(x => x.trim().split(' ')[0])
                .join(', ');

            console.log(argomenti);

            return `${nomeFunzione}(${argomenti}): ${splitted[1]}`;
        }

        function aggiornaListaMetodi() {
            const candidati = _formulaCodeMirror.getValue()
                .split('\n')
                .map((x, i) => ({ index: i, text: x.trim() }))
                .filter(l => isDichiarazioneFunzione(l.text))
                .map((x) => ({ index: x.index, text: estraiNomeFunzione(x.text) }))
                .filter(l => l.text.length > 0);

            candidati.sort((a, b) => a.text.localeCompare(b.text));

            const funzioni = document.getElementById('funzioni');
            funzioni.innerHTML = '';
            const primoElemento = document.createElement('option');
            primoElemento.text = 'Vai alla funzione ->';
            primoElemento.value = '';
            funzioni.append(primoElemento);
            funzioni.style.display = candidati.length ? 'block' : 'none';

            for (let candidato of candidati) {
                var option = document.createElement('option');
                option.innerText = '- ' + candidato.text;
                option.value = candidato.index;
                funzioni.append(option);
            }
        }

        function mostraEsempi() {
            showpopup("PopupFormuleDatiDinamici.aspx");
        }

        function inserisciAssegnazione() {
            showpopup("PopupInserisciAssegnazione.aspx?token=<%=Token%>&idmodello=<%=IdModello%>&software=<%=Software %>");
        }

        function inserisciMostraCampoDyn() {
            showpopup("PopupVisualizzaCampo.aspx?token=<%=Token%>&idmodello=<%=IdModello%>&software=<%=Software %>");
        }

        function inserisciMostraCampoStatico() {
            showpopup("PopupVisualizzaCampo.aspx?token=<%=Token%>&idmodello=<%=IdModello%>&software=<%=Software %>&campiStatici=true");
        }


        function showpopup(url) {
            var winWidth = "600";
            var winHeight = "550";
            var w = window.open(url, "", "centerscreen=yes,width=" + winWidth + ",height=" + winHeight + ",scrollbars=yes,location=no");
        }

        function insertText(value) {
            _formulaCodeMirror.replaceSelection(value);
        }

        function copyToClipboard(sender, ctrlId) {
            var el = document.getElementById(ctrlId);
            var varName = el.value.replace(/ /g, "_");
            varName = varName.replace(/\'/g, "_");
            varName = varName.replace(/`/g, "_");

            var data = "var " + varName + " = ModelloCorrente.TrovaCampo(\"" + el.value + "\");";

            //var data = "\"" + el.value + "\"";
            _formulaCodeMirror.replaceSelection(data);
            _formulaCodeMirror.focus();
        }

        vbg.ready(() => {
            document.querySelectorAll('.tag').forEach((el) => {
                el.addEventListener('click', () => {
                    insertText(el.dataset['tag']);
                });
            });

            document.querySelectorAll('.comandi>h4').forEach(el => {
                el.addEventListener('click', () => {
                    if (el.parentNode.classList.contains('chiuso')) {
                        el.parentNode.classList.remove('chiuso');
                    }
                    else {
                        el.parentNode.classList.add('chiuso');
                    }
                });
            });

            //document.getElementById('aggiungiCampiCollegati').addEventListener('click', (e) => {
            //    e.preventDefault();
            //    mostraModalCampiCollegati();
            //})
        });

<%--        function mostraModalCampiCollegati() {
            const modal = document.getElementById('<%=bmAggiungiCampoCollegato.ClientID%>');

            modal.show();
        }--%>

    </script>


</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

    <%--<fieldset>--%>

    <div class="esito-salvataggio">
        <div runat="server" id="divFormulaSalvata" class="alert alert-success formula-salvata" visible="false">
            <b>Formula salvata correttamente
            </b>
        </div>

        <div runat="server" id="divFormulaCompilata" class="alert alert-success formula-salvata" visible="false">
            <b>Formula compilata senza errori
            </b>
        </div>
    </div>

    <div class="d2mf">

        <div class="lista-funzionalita">
            <init:LabeledDropDownList runat="server" ID="ddlEvento" CssClass="evento" Descrizione="Evento" Item-AutoPostBack="true" OnValueChanged="ddlEvento_ValueChanged" />

            <div class="comandi">
                <div class="check-mostra">
                    <asp:CheckBox runat="server" ID="chkNascondiUsing" Text="Mostra using" TextAlign="Right" />
                </div>
                <div class="check-mostra">
                    <asp:CheckBox runat="server" ID="chkNascondiServizi" Text="Mostra servizi iniettati" TextAlign="Right" />
                </div>
            </div>

            <div class="comandi">
                <select id="funzioni"></select>
            </div>
            <div class="comandi">
                <h4>Utilità</h4>
                <a href="javascript:inserisciAssegnazione()">Inserisci formula di assegnazione</a>
                <a href="javascript:inserisciMostraCampoDyn()">Visualizza/Nascondi campo</a>
                <a href="javascript:inserisciMostraCampoStatico()">Visualizza/Nascondi testo</a>
                <a href="javascript:mostraEsempi();">Esempi di codice</a>
            </div>

            <uc1:CampiCollegatiEditor runat="server" id="campiCollegatiEditor" />


            <%if (this.TagsCount > 0)
                {  %>
            <div class="comandi tags chiuso">
                <h4>Tags</h4>
                <asp:Repeater runat="server" ID="rptTags">
                    <ItemTemplate>
                        <div class="tag" data-tag="<%#DataBinder.Eval(Container,"DataItem") %>"><%#DataBinder.Eval(Container,"DataItem") %></div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>
            <%} %>

            <div class="comandi">
                <h4>Comandi</h4>
                <asp:LinkButton runat="server" ID="cmdSalva" Text="<i class='fa fa-floppy-o' aria-hidden='true'></i> Salva" IdRisorsa="" OnClick="cmdSalva_Click" />
                <asp:LinkButton runat="server" ID="cmdCompila" Text="<i class='fa fa-check' aria-hidden='true'></i> Verifica compilazione" OnClick="cmdCompila_Click" />
                <asp:LinkButton runat="server" ID="cmdVisualizzaClasse" Text="<i class='fa fa-search' aria-hidden='true'></i> Visualizza codice" OnClick="cmdVisualizzaClasse_Click" />
                <asp:LinkButton runat="server" ID="cmdChiudi" Text="<i class='fa fa-times' aria-hidden='true'></i> Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
            </div>
        </div>

        <div class="editor-codice">
            <div>
                <asp:TextBox runat="server" ID="txtUsing" Columns="100" Rows="6" TextMode="MultiLine" CssClass="CampoFormula" />
            </div>
            <div>
                <asp:TextBox runat="server" ID="txtServices" Columns="100" Rows="6" TextMode="MultiLine" CssClass="CampoFormula" />
            </div>
            <div>
                <asp:TextBox runat="server" ID="txtScript" Columns="100" Rows="25" TextMode="MultiLine" CssClass="CampoFormula" />
            </div>

        </div>

    </div>

    
    
</asp:Content>
