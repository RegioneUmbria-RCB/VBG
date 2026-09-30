<%@ Page Title=" " Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
    CodeBehind="CompilaSchedeDinamiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.CompilaSchedeDinamiche" %>

<%@ Register TagPrefix="dd" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

<asp:Content ID="Content2" ContentPlaceHolderID="headPagina" runat="server">
    <%=LoadScripts(new[]{
        "~/js/lib/jquery.tooltip.fix.js",
        "~/js/lib/jquery.form.js"
    }) %>

    <script>
        $(() => {
            const procedi = document.getElementById('<%=cmdProcedi.ClientID%>');
            
            if (procedi) {
                procedi.addEventListener('click', () => {
                    mostraModalCaricamento();
                });
            }
        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="contenutoStep">
        <div id="datiDinamici">
            <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
                <asp:View runat="server" ID="listaView">

                    <h3>Schede da compilare</h3>

                    <asp:Repeater runat="server" ID="rptSchedeDaCompilare">
                        <HeaderTemplate>
                            <ul>
                                <ul>
                        </HeaderTemplate>
                        <ItemTemplate>
                            <li class='elementoListaDatiDinamici'>
                                <i class='glyphicon <%# Convert.ToBoolean(DataBinder.Eval(Container.DataItem,"Compilata")) ? "glyphicon-ok" : "glyphicon-pencil"%>'></i>
                                <asp:LinkButton runat="server" ID="lnkSchedaDinamica" Text='<%# Eval("Descrizione") %>'
                                    CommandArgument='<%# Eval("IdScheda") %>' OnClick="lnkSchedaDinamica_Click" />
                            </li>
                        </ItemTemplate>
                        <FooterTemplate>
                            </li>
								</ul>
							</ul>
                        </FooterTemplate>
                    </asp:Repeater>
                    <div id="bottoniMovimento">
                        <asp:Button runat="server" CssClass="btn btn-default" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
                        <asp:Button runat="server" CssClass="btn btn-primary" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
                    </div>

                </asp:View>
                <asp:View runat="server" ID="dettaglioView">
                    <script type="text/javascript">

                        (function () {
                            var g_datiDinamiciExtender = null;

                            function fixAggiungiRiga() {
                                var button = $('tr.bloccoMultiploAggiungiRiga>td>input');
                                var el = $('<i />', { 'class': 'ion-android-add-circle' });

                                button.addClass('aggiungiRiga');
                                button.before(el);
                            }

                            function fixAggiungiBlocco() {
                                var button = $('#datiDinamici input[name*=btnAggiungi][type=submit][value=\'Aggiungi\']');

                                var el = $('<i />', { 'class': 'ion-android-add-circle' });

                                button.addClass('aggiungiRiga');
                                button.before(el);
                            }

                            function fixEliminaRiga() {
                                var button = $('.divEliminazioneBlocco>a');
                                var el = $('<i />', { 'class': 'ion-android-remove-circle' });

                                button.addClass('eliminaRiga');
                                button.before(el);

                            }

                            $(function () {
                                g_datiDinamiciExtender = new DatiDinamiciExtender('<%=pnlErrori.ClientID %>', '<%=cmdSalva.ClientID %>', $('.d2WatchButton'));

                                fixAggiungiRiga();
                                fixAggiungiBlocco();
                                fixEliminaRiga();


                                document.getElementById('<%=cmdSalva.ClientID%>').addEventListener('click', () => {
                                    mostraModalCaricamento();
                                });
                            });
                        }());
                    </script>

                    <h2>
                        <asp:Label runat="server" ID="lblTitoloModello" CssClass="" />
                    </h2>

                    <asp:Panel runat="server" ID="pnlErrori" CssClass="alert alert-danger" Style="display: none"></asp:Panel>

                    <dd:ModelloDinamicoRenderer ID="renderer" runat="server" />


                    <asp:Button runat="server" CssClass="btn btn-default d2WatchButton" ID="cmdChiudi" Text="Torna alla lista delle schede senza salvare" OnClick="cmdChiudi_Click" />
                    <asp:Button runat="server" CssClass="btn btn-primary" ID="cmdSalva" Text="Salva e torna alla lista delle schede" OnClick="cmdSalva_Click" />

                    <ar:bootstrapmodal runat="server" extracssclass="d2-bootstrap-modal" id="d2BootstrapModal" title="Informazioni" showokbutton="false">
                        <modalbody>
                            &nbsp;
                        </modalbody>
                    </ar:bootstrapmodal>
                </asp:View>
            </asp:MultiView>
        </div>
    </div>
</asp:Content>
