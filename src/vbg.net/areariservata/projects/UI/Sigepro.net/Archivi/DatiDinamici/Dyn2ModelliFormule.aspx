<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" ValidateRequest="false" AutoEventWireup="true" CodeBehind="Dyn2ModelliFormule.aspx.cs"
    Inherits="Sigepro.net.Archivi.DatiDinamici.Dyn2ModelliFormule" Title="Formula scheda" %>

<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
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
    </style>

    <script type="text/javascript">

        var g_codeMIrror = null;

        $(function () {
            g_codeMIrror = CodeMirror.fromTextArea(document.getElementById('<%= txtScript.ClientID%>'), {
                mode: "text/x-csharp",
                lineNumbers: true,
                theme: 'neat',
                tabMode: 'default',
                tabSize: 4,
                smartIndent: false,
                indentUnit: 4,
                indentWithTabs: true,
                enterMode: 'keep',
                onCursorActivity: function () {
                    g_codeMIrror.setLineClass(hlLine, null);
                    hlLine = g_codeMIrror.setLineClass(g_codeMIrror.getCursor().line, "activeline");
                },
                extraKeys: {
                    "F11": function () {
                        var scroller = g_codeMIrror.getScrollerElement();
                        if (scroller.className.search(/\bCodeMirror-fullscreen\b/) === -1) {
                            scroller.className += " CodeMirror-fullscreen";
                            scroller.style.height = "100%";
                            scroller.style.width = "100%";
                            g_codeMIrror.refresh();
                        } else {
                            scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
                            scroller.style.height = '';
                            scroller.style.width = '';
                            g_codeMIrror.refresh();
                        }
                    },
                    "Esc": function () {
                        var scroller = g_codeMIrror.getScrollerElement();
                        if (scroller.className.search(/\bCodeMirror-fullscreen\b/) !== -1) {
                            scroller.className = scroller.className.replace(" CodeMirror-fullscreen", "");
                            scroller.style.height = '';
                            scroller.style.width = '';
                            g_codeMIrror.refresh();
                        }
                    }
                }
            });

            var hlLine = g_codeMIrror.setLineClass(0, "activeline");
        });

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
		    g_codeMIrror.replaceSelection(value);
		}

		function copyToClipboard(sender, ctrlId) {
		    var el = document.getElementById(ctrlId);
		    var varName = el.value.replace(/ /g, "_");
		    varName = varName.replace(/\'/g, "_");
		    varName = varName.replace(/`/g, "_");

		    var data = "var " + varName + " = ModelloCorrente.TrovaCampo(\"" + el.value + "\");";

		    //var data = "\"" + el.value + "\"";
		    g_codeMIrror.replaceSelection(data);
		    g_codeMIrror.focus();
		}


		function resizeHeight() {
		    var winHeight = $(window).height(),
                element = $('.CodeMirror'),
                elementScroll = $('.CodeMirror-scroll'),
                elTop = element.offset().top,
                elHeight = element.height(),
                newHeight = winHeight - elTop - 20;

		    element.height(newHeight);
		    elementScroll.height(newHeight);
		}

		function resizeWidth() {
		    var winWidth = $(window).width(),
                functionsWidth = $('.d2mf>.lista-funzionalita').width(),
                newWidth = winWidth - functionsWidth;

		    $('.editor-codice').width(newWidth);
		}

		function resizeEditor() {
		    resizeHeight();
		    resizeWidth();
		}

		function hideSuccess() {
		    var el = $('.formula-salvata');

		    if (!el.length) {
		        return;
		    }

		    el.hide('slow', resizeEditor);
		}

		$(function () {
		    resizeEditor();

		    $(window).on('resize', resizeEditor);

		    setTimeout(hideSuccess, 2000);
		});


    </script>


</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">


    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="1">
        <asp:View runat="server" ID="loginView">
        </asp:View>
        <asp:View runat="server" ID="detailsView">
            <%--<fieldset>--%>

            <div runat="server" id="divFormulaSalvata" class="alert alert-success formula-salvata" visible="false">
                <b>Formula salvata correttamente
                </b>
            </div>

            <div runat="server" id="divFormulaCompilata" class="alert alert-success formula-salvata" visible="false">
                <b>Formula compilata senza errori
                </b>
            </div>

            <div class="d2mf">

                <div class="lista-funzionalita">
                    <init:LabeledDropDownList runat="server" ID="ddlEvento" Descrizione="Evento" Item-AutoPostBack="true" OnValueChanged="ddlEvento_ValueChanged" />

                    <hr />

                    <span>
                        <a href="javascript:inserisciAssegnazione()">Inserisci formula di assegnazione</a><br />
                        <a href="javascript:inserisciMostraCampoDyn()">Visualizza/Nascondi campo diamico</a><br />
                        <a href="javascript:inserisciMostraCampoStatico()">Visualizza/Nascondi campo testuale</a><br />
                        <a href="javascript:mostraEsempi();">Esempi di codice</a>
                    </span>


                    <hr />

                    <span>
                        <asp:LinkButton runat="server" ID="cmdSalva" Text="<i class='fa fa-floppy-o' aria-hidden='true'></i> Salva" IdRisorsa="" OnClick="cmdSalva_Click" /><br />
                        <asp:LinkButton runat="server" ID="cmdCompila" Text="<i class='fa fa-check' aria-hidden='true'></i> Verifica compilazione" OnClick="cmdCompila_Click" /><br />
                        <asp:LinkButton runat="server" ID="cmdVisualizzaClasse" Text="<i class='fa fa-search' aria-hidden='true'></i> Visualizza codice" OnClick="cmdVisualizzaClasse_Click" /><br />
                        <%--<asp:Button runat="server" ID="cmdCompilaFrontoffice" Text="Verifica compilazione FO" OnClick="cmdCompilaFrontoffice_Click" />--%>
                        <asp:LinkButton runat="server" ID="cmdChiudi" Text="<i class='fa fa-times' aria-hidden='true'></i> Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
                    </span>
                </div>

                <div class="editor-codice">
                    <div>
                        <asp:TextBox runat="server" ID="txtScript" Columns="100" Rows="25" TextMode="MultiLine" CssClass="CampoFormula" />
                    </div>

                </div>

            </div>

            <%--</fieldset>--%>
        </asp:View>
    </asp:MultiView>
</asp:Content>
