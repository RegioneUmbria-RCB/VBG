<%@ Page Title="Ricerca pratiche presentate" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="ricerca-pratiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ricerca_pratiche.ricerca_pratiche" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register Src="~/Reserved/InserimentoIstanza/ricerca-pratiche/ricerca-pratiche-dettaglio.ascx" TagPrefix="uc1" TagName="ricercapratichedettaglio" %>



<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        vbg.ready(() => {
            $('a[data-toggle="tab"]').on('shown.bs.tab', (e) => {
                resetValidators();
            })
        });
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="ricercaView">

            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#protocollo" aria-controls="protocollo" role="tab" data-toggle="tab">Ricerca per numero protocollo</a></li>
                <li role="presentation"><a href="#istanza" aria-controls="istanza" role="tab" data-toggle="tab">Ricerca per numero pratica</a></li>
            </ul>

            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="protocollo">
                    <fieldset style="margin-left: 16px" id="fsProtocollo">
                        <ar:TextBox runat="server" ID="txtNumeroProtocollo" Label="Numero protocollo" Required="true" />
                        <ar:DateTextBox runat="server" ID="txtDataProtocollo" Label="Data protocollo" Required="true" />

                        <asp:Button runat="server" CssClass="btn btn-primary vbg-show-spinner-if-valid" ID="cmdCercaProtocollo" Text="Cerca" OnClick="cmdCercaProtocollo_Click" />
                        <%if (!SelezionePraticaCollegataObbligatoria)
                            { %>
                        <asp:LinkButton runat="server" CssClass="btn btn-default vbg-show-spinner" ID="cmdIgnoraRicerca" Text="Ignora ricerca pratica collegata" OnClick="cmdIgnoraRicerca_Click" />
                        <%} %>
                    </fieldset>

                </div>
                <div role="tabpanel" class="tab-pane" id="istanza">
                    <fieldset style="margin-left: 16px" id="fsPratica">

                        <ar:TextBox runat="server" ID="txtNumeroIstanza" Label="Numero pratica" Required="true" />

                        <asp:Button runat="server" CssClass="btn btn-primary vbg-show-spinner-if-valid" ID="cmdCercaPratica" Text="Cerca" OnClick="cmdCercaPratica_Click" />
                        <%if (!SelezionePraticaCollegataObbligatoria)
                            { %>
                        <asp:LinkButton runat="server" CssClass="btn btn-default vbg-show-spinner" ID="cmdIgnoraRicerca2" Text="Ignora ricerca pratica collegata" OnClick="cmdIgnoraRicerca_Click" />
                        <%} %>
                    </fieldset>
                </div>
            </div>



        </asp:View>

        <asp:View runat="server" ID="dettaglioView">

            <script type="text/javascript">
                vbg.ready(() => {
                    $('#<%=cmdAnnullaCollegamento.ClientID%>').on('click', (e) => {

                        e.preventDefault();

                        $('#<%=bmConfermaEliminazioneCollegamento.ClientID%>').modal('show');
                    });

                });
            </script>

            <uc1:ricercapratichedettaglio runat="server" ID="ricercapratichedettaglio" />

            <asp:Button runat="server" CssClass="btn btn-primary vbg-show-spinner" ID="cmdMantieniCollegamento" Text="Mantieni collegamento e procedi" OnClick="cmdManieniCollegamento_Click" />
            <asp:Button runat="server" CssClass="btn btn-default" ID="cmdAnnullaCollegamento" Text="Rimuovi collegamento e cerca un'altra pratica" />
            <asp:Button runat="server" CssClass="btn btn-primary vbg-show-spinner" ID="cmdCopiaDati" Text="Collega a questa pratica" OnClick="cmdCopiaDati_Click" />
            <asp:Button runat="server" CssClass="btn btn-default" ID="cmdNuovaRicerca2" Text="Nuova ricerca" OnClick="cmdNuovaRicerca_Click" />

            <ar:BootstrapModal runat="server" ID="bmConfermaEliminazioneCollegamento" Title="Rimozione collegamento da pratica" KoText="Annulla" OkText="Prosegui" OnOkClicked="bmConfermaEliminazioneCollegamento_OkClicked">
                <ModalBody>
                    <asp:Literal runat="server" ID="ltrTestoConfermaAnnullamentoCollegamento">Eliminare il collegamento tra le pratiche?<br />L'operazione non potrà essere annullata e sarà necessario effettuare una nuova ricerca per ripristinare il collegamento.</asp:Literal>
                </ModalBody>
            </ar:BootstrapModal>
        </asp:View>

        <asp:View runat="server" ID="nonTrovataView">
            <div class="alert alert-warning">
                <asp:Literal runat="server" ID="ltrErrorePraticaNonTrovata">
                    Non sono state trovate pratiche corrispondenti ai criteri di ricerca impostati.
                </asp:Literal>
            </div>

            <asp:Button runat="server" CssClass="btn btn-primary" ID="cmdNuovaRicerca" Text="Nuova ricerca" OnClick="cmdNuovaRicerca_Click" />
        </asp:View>
    </asp:MultiView>

</asp:Content>
