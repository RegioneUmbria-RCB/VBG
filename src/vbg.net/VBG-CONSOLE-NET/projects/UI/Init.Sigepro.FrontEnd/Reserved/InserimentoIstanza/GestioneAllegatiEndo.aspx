<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneAllegatiEndo.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiEndo"
    Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" Src="~/Reserved/InserimentoIstanza/Allegati/GrigliaAllegati.ascx" TagName="GrigliaAllegati" %>

<asp:Content runat="server" ID="headerContent" ContentPlaceHolderID="head">
    <script type="text/javascript">

        require(['jquery', 'app/uploadAllegati'], function ($, UploadAllegati) {

            $(function () {
                var loaderUrl = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>',
                    urlBaseNote = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAllegati_Note.ashx")%>',
                    idComune = '<%=IdComune %>',
                    token = '<%=UserAuthenticationResult.Token %>',
                    software = '<%=Software %>',
                    idDomanda = '<%=IdDomanda %>',
                    provenienza = 'E',
                    modalAggiungiAllegato = $("#<%=bmAggiungiAllegato.ClientID%>");

                new UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software, idDomanda, provenienza);


                $(".cmdNuovoAllegato").on("click", function (e) {
                    modalAggiungiAllegato.modal("show");

                    e.preventDefault();
                });
            });
        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <ar:AttributiAllegato runat="server" ID="ltrLegendaAttributi" Legenda="true" />

    <asp:Repeater runat="server" ID="rptEndo" OnItemDataBound="OnItemDataBound">
        <ItemTemplate>
            <span style="font-weight: bold; text-transform: uppercase">
                <asp:Literal runat="server" ID="ltrProcedimento" Text='<%# Bind("Descrizione") %>'></asp:Literal>
            </span>
            <br />

            <uc1:GrigliaAllegati runat="server"
                ID="grigliaAllegati"
                OnAllegaDocumento="OnAllegaDocumento"
                OnCompilaDocumento="OnCompilaDocumento"
                OnFirmaDocumento="OnFirmaDocumento"
                OnRimuoviDocumento="OnRimuoviDocumento"
                OnErrore="ErroreGriglia" />
            <br />
        </ItemTemplate>
    </asp:Repeater>



    <div style="text-align: right">
        <asp:Button runat="server" ID="cmdNuovoAllegato" CssClass="btn btn-default cmdNuovoAllegato" Text="Nuovo allegato" OnClick="cmdNuovoAllegato_Click" />
    </div>

    <ar:BootstrapModal runat="server" ID="bmNoteAllegato" ExtraCssClass="note-allegato" Title="Note dell'allegato" ShowOkButton="false">
    </ar:BootstrapModal>

    <ar:BootstrapModal runat="server" ID="bmAggiungiAllegato" Title="Nuovo allegato libero" OnOkClicked="cmdAggiungi_Click" OkText="Aggiungi allegato" KoText="Annulla">
        <ModalBody>
            <ar:DropDownList runat="server" ID="ddlTipoAllegato" Label="Categoria" DataTextField="Descrizione" Required="true" DataValueField="Codice" />
            <ar:TextBox runat="server" ID="txtDescrizioneAllegato" Label="Descrizione" Required="true" />
            <ar:ArFileUpload runat="server" ID="fuUploadNuovo" Label="File" Required="true" InnerAttributes="class=form-control" />
        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
