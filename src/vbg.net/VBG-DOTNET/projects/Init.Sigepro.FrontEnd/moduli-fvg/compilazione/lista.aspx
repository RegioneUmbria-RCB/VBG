<%@ Page Title="" Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="lista.aspx.cs" Inherits="Init.Sigepro.FrontEnd.moduli_fvg.compilazione.lista" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style media="all">
        .schede-da-compilare {
            list-style-type: none;
            margin-bottom: 20px;
        }

            .schede-da-compilare > li {
                margin-left: -34px;
            }

                .schede-da-compilare > li.compilata > a {
                    color: #3c763d;
                }

                .schede-da-compilare > li > a:before {
                    position: relative;
                    top: 1px;
                    display: inline-block;
                    font-family: 'Glyphicons Halflings';
                    font-style: normal;
                    font-weight: 400;
                    line-height: 1;
                    -webkit-font-smoothing: antialiased;
                    content: "\270f";
                    padding-right: 5px;
                }

                .schede-da-compilare > li.compilata > a:before {
                    position: relative;
                    top: 1px;
                    display: inline-block;
                    font-family: 'Glyphicons Halflings';
                    font-style: normal;
                    font-weight: 400;
                    line-height: 1;
                    -webkit-font-smoothing: antialiased;
                    content: "\e013";
                    padding-right: 5px;
                }

        .intestazione-lista-moduli {
            line-height: 2em;
        }

        #iewarning {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(128, 128, 128, 0.8);
            cursor: not-allowed;
        }

            #iewarning > div {
                margin-top: 150px;
                cursor: default;
            }

        .container {
            width: 100% !important;
        }
    </style>

    <%=LoadScript("~/js/lib/bowser-2.11.0/bowser.js") %>

    <script type="text/javascript">

        function isUsingIE() {
            var ua = window.navigator.userAgent;
            var msie = ua.indexOf("MSIE ") > 0 || !!ua.match(/Trident.*rv\:11\./);

            return msie;
        }

        function mostraErroreBrowser(browserName) {
            const alert = document.getElementById('browserwarning');
            const versioneBrowser = document.getElementById('browserInUso');

            alert.style.display = 'block';
            versioneBrowser.innerText = browserName;
        }


        $(function checkBrowser() {

            var isIe = isUsingIE();

            if (isIe) {
                mostraErroreBrowser('Internet explorer');
                document.getElementById('dettagli').style.display = 'none';
                return;
            }

            const browser = bowser.getParser(window.navigator.userAgent);
            const isValid = browser.satisfies({
                chrome: '>88',
                firefox: '>86',
                edge: '>88'
            });

            if (!isValid) {
                mostraErroreBrowser(browser.getBrowserName() + ' ' + browser.getBrowserVersion());
            }
        });

        <%if (!this.TutteLeSchedeSonoCompilate)
        {%>

        $(function onLoad() {

            var bottoneInvia = $("#<%=cmdInviaDatiAlComune.ClientID%>");

            bottoneInvia.addClass("disabled");

            bottoneInvia.on("click", function onClick(e) {
                e.preventDefault();
                return false;
            })
        });
        <%}%>
    </script>
</asp:Content>


<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <h1>
        <asp:Literal runat="server" ID="lblNomeEndoprocedimento"></asp:Literal>
    </h1>


    <div id="browserwarning" style="display:none">
        <div class="container avviso-ie">
            <div class="panel panel-danger">
                <div class="panel-heading">Browser non supportato</div>
                <div class="panel-body">
                    Il browser in uso (<b id="browserInUso">Internet explorer</b>) non è supportato e la compilazione potrebbe non funzionare correttamente.<br />
                    Si consiglia pertanto di utilizzare uno dei seguenti browser:
                   
                    <ul class="list">
                        <li>Firefox versione 86 o successiva [<a href="https://www.mozilla.org/it/firefox/new/" target="_blank">Scarica</a>]</li>
                        <li>Chrome versione 88 o successiva [<a href="https://www.google.com/intl/it_it/chrome/" target="_blank">Scarica</a>]</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div runat="server" id="divDatiPrecompilatiConSuccesso" visible="false">
            <div class="alert alert-success" role="alert">
                <i class="glyphicon glyphicon-ok"></i>
                Precompilazione effettuata correttamente, si può procedere al salvataggio dei quadri
            </div>
        </div>

    <div id="dettagli">
        <div class="intestazione-lista-moduli" id="intestazione-lista-moduli">
            <ar:RisorsaTestualeLabel runat="server" ID="intestazioneListaModuliFVG" IdRisorsa="FVG_INTESTAZIONE_LISTA_MODULI" />
        </div>

        <div runat="server" id="divTutteLeSchedeSonoCompilate" visible="true">
            <div class="alert alert-success" role="alert">
                <i class="glyphicon glyphicon-ok"></i>
                Tutti i quadri del modulo sono stati compilati correttamente. Fare click su <strong>"Crea PDF modulo"</strong> per proseguire.
            </div>
        </div>

        <asp:Repeater runat="server" ID="rptListaSchedeDinamiche">

            <HeaderTemplate>
                <ul class="schede-da-compilare" id="schede-da-compilare">
            </HeaderTemplate>

            <ItemTemplate>
                <li runat="server" class='<%#(bool)Eval("Compilata")? "compilata" : "" %>'>
                    <asp:LinkButton runat="server" Text='<%#Eval("Descrizione")%>' CommandArgument='<%#Eval("Id") %>' OnClick="OnSchedaSelezionata" />
                </li>
            </ItemTemplate>

            <FooterTemplate>
                </ul>
            </FooterTemplate>
        </asp:Repeater>

        <div>
            <asp:Button runat="server" ID="cmdInviaDatiAlComune" CssClass="btn btn-primary" Text="Crea PDF modulo" OnClick="cmdInviaDatiAlComune_Click" />
            <asp:LinkButton runat="server" ID="cmdGeneraPdf" CssClass="btn btn-default" Text="Anteprima" OnClick="cmdGeneraPdf_Click" />

            <%if (!String.IsNullOrEmpty(ReturnUrl))
                { %>
                <a href="<%=ReturnUrl %>" class="btn btn-default">Chiudi</a>

            <%} %>
        </div>

    </div>


</asp:Content>

