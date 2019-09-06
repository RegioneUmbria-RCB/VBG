<%@ Page Title="Scheda" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="LivornoSchedaPortaleCittadino.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.LivornoSchedaPortaleCittadino" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register Src="~/Reserved/InserimentoIstanza/Allegati/GrigliaAllegati.ascx" TagPrefix="uc1" TagName="GrigliaAllegati" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        require(['jquery', 'app/uploadAllegati'], function ($, UploadAllegati) {

            $(function () {
                var loaderUrl = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>';
                var urlBaseNote = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAllegati_Note.ashx")%>';
                var idComune = '<%=IdComune %>';
                var token = '<%=UserAuthenticationResult.Token %>';
                var software = '<%=Software %>';
                var idDomanda = '<%=IdDomanda %>';
                var provenienza = 'I';

                new UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software, idDomanda, provenienza);

                $('#btnAggiungiAllegato').on('click', function (e) {
                    e.preventDefault();

                    $('.modal-aggiungi-allegato').modal('show');
                });

                $('#btnAnnullaAggiuntaAllegato').on('click', function (e) {

                    e.preventDefault();

                    $('.modal-aggiungi-allegato').modal('hide');
                });

                $('#<%=cmdAggiungiAllegato.ClientID%>').on('click', function (e) {

                    var descrizione = $('#<%=txtDescrizioneAllegato.Inner.ClientID%>').val().trim();

                    if (descrizione === '') {
                        e.preventDefault();
                        $('#errDescrizioneNonValida').show();
                        $('#<%=txtDescrizioneAllegato.Inner.ClientID%>').addClass('alert alert-danger');
                        return;
                    }
                    $('.modal-aggiungi-allegato').modal('hide');
                    $('#caricamentoFileIncorso').modal('show');
                });

                
            });
        });
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">

    <asp:HyperLink runat="server" ID="hlVisualizzaScheda" Target="_blank" Text="Visualizza la scheda dell'intervento" />


    <%if (this.ModelliPresenti)
      {%>
    <h3>Modelli</h3>
    <uc1:GrigliaAllegati runat="server"
        ID="grigliaModelli"
        OnAllegaDocumento="grigliaModelli_AllegaDocumento"
        OnRimuoviDocumento="grigliaModelli_RimuoviDocumento"
        OnErrore="grigliaModelli_Errore" />

    <%}%>

    <% if (this.AllegatiPresenti){%>
    <h3>Allegati</h3>
    <uc1:GrigliaAllegati runat="server" ID="grigliaAllegati"
        OnAllegaDocumento="grigliaModelli_AllegaDocumento"
        OnRimuoviDocumento="grigliaModelli_RimuoviDocumento"
        OnErrore="grigliaModelli_Errore" />
    <%}%>

    <% if (this.AllegatiLiberiPresenti){%>
    <h3>Allegati Liberi</h3>
    <uc1:GrigliaAllegati runat="server" ID="grigliaAllegatiLiberi"
        OnRimuoviDocumento="grigliaModelli_RimuoviDocumento"
        OnErrore="grigliaModelli_Errore" />
    <%}%>


    <button id="btnAggiungiAllegato" class="btn btn-primary" value="Aggiungi allegato">Aggiungi allegato</button>

    <div class="modal fade modal-aggiungi-allegato" id="aggiungiAllegato" data-backdrop="static" data-keyboard="false" role="dialog" aria-hidden="true" style="padding-top: 15%; overflow-y: visible; display: none;">
        <div class="modal-dialog modal-m">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 style="margin: 0;">Selezionare il file da caricare</h3>
                </div>
                <div class="modal-body">

                    <div class="alert alert-danger" role="alert" id="errDescrizioneNonValida" style="display:none">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Attenzione</span>
                        Inserire una descrizione per l'allegato
                    </div>

                    <div>
                        <ar:TextBox runat="server" ID="txtDescrizioneAllegato" Label="Descrizione" />
                        <div>
                            <asp:Label runat="server" ID="Label1" Text="File" AssociatedControlID="fuUploadNuovo" />
                            <asp:FileUpload runat="server" ID="fuUploadNuovo" Style="width: 400px" />
                        </div>
                    </div>
                </div>

                <div class="modal-footer">

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdAggiungiAllegato" Text="Allega" OnClick="cmdAggiungiAllegato_Click" />
                        <button id="btnAnnullaAggiuntaAllegato" class="btn btn-default" value="AnnullaAggiunta">Annulla</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</asp:Content>
