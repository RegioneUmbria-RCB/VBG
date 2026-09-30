<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" MaintainScrollPositionOnPostback="true" AutoEventWireup="true" CodeBehind="GestioneAllegatiIntervento.aspx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiIntervento" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" Src="~/Reserved/InserimentoIstanza/Allegati/GrigliaAllegati.ascx" TagName="GrigliaAllegati" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">


    <script type="text/javascript">
        require(['jquery', 'app/uploadAllegati'], function ($, UploadAllegati) {

            $(function () {
                var loaderUrl = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>',
                                urlBaseNote = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAllegati_Note.ashx")%>',
                                idComune = '<%=IdComune %>',
                                token = '<%=UserAuthenticationResult.Token %>',
                                software = '<%=Software %>',
                                idDomanda = '<%=IdDomanda %>',
                                provenienza = 'I',
                                modalAggiungiAllegato = $("#<%=bmAggiungiAllegato.ClientID%>");

                new UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software, idDomanda, provenienza);

                $(".cmdNuovoAllegato").on("click", function (e) {
                    modalAggiungiAllegato.modal("show");

                    e.preventDefault();
                });

                modalAggiungiAllegato.on("hidden.bs.modal shown.bs.modal", function () {
                    updateValidators();
                });
            });
        });
    </script>


    <ar:AttributiAllegato runat="server" ID="ltrLegendaAttributi" Legenda="true" />

    <asp:Repeater runat="server" ID="rptCategorie" OnItemDataBound="OnItemDataBound">
        <ItemTemplate>
            <div style='font-weight: bold; text-transform: uppercase <%= (NumeroCategorie > 1) ? "" : ";display:none"%>'>
                <asp:Literal runat="server" ID="ltrCategoria" Text='<%# Bind("Descrizione") %>' Visible="<%# NumeroCategorie > 0 %>" />
            </div>

            <uc1:GrigliaAllegati runat="server"
                ID="grigliaAllegati"
                OnAllegaDocumento="OnAllegaDocumento"
                OnCompilaDocumento="OnCompilaDocumento"
                OnFirmaDocumento="OnFirmaDocumento"
                OnRimuoviDocumento="OnRimuoviDocumento"
                OnErrore="ErroreGriglia"
                OnAllegaDocumentiMultipli="AllegaDocumentiMultipli" />
        </ItemTemplate>
    </asp:Repeater>

    <div style="text-align: right">
        <a href="#" class="btn btn-default cmdNuovoAllegato" runat="server" id="cmdNuovoAllegato">Aggiungi allegato libero</a>
    </div>

    <ar:BootstrapModal runat="server" ID="bmNoteAllegato" ExtraCssClass="note-allegato" Title="Note dell'allegato" ShowOkButton="false">
    </ar:BootstrapModal>


    <div id="noteAllegato"></div>

    <ar:BootstrapModal runat="server" ID="bmAggiungiAllegato" Title="Nuovo allegato libero" OnOkClicked="cmdAggiungi_Click" OkText="Aggiungi allegato" KoText="Annulla">
        <ModalBody>
            <ar:DropDownList runat="server" ID="ddlTipoAllegato" Label="Categoria" DataTextField="Descrizione" Required="true" DataValueField="Id" />
            <ar:TextBox runat="server" ID="txtDescrizioneAllegato" Label="Descrizione" Required="true" />
            <ar:ArFileUpload runat="server" ID="fuUploadNuovo" Label="File" Required="true" />
        </ModalBody>
    </ar:BootstrapModal>

</asp:Content>
