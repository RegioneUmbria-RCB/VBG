<%@ Page Title="Sostituzioni documentali" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="SostituzioniDocumentali.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.SostituzioniDocumentali" %>
<%@ Register TagPrefix="uc1" Src="~/Reserved/GestioneMovimenti/SostituzioniDocumentaliGrid.ascx" TagName="SostituzioniDocumentaliGrid" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">

        function confermaCanellazione() {
            return confirm("Eliminare il file sostitutivo? L\'operazione non potrà essere annullata.");
        }

        require(['jquery', 'jquery.ui'], function ($) {

            $(function () {
                $('.upload-documento-sostitutivo').hide();


                $('.cmd-sostituisci-documento').on('click', function (e) {

                    var el = $(this),
                        parent = el.closest('.documento-sostituibile'),
                        azioniPanel = parent.find('.azioni'),
                        uploadPanel = el.data('upload-panel');

                    if (!uploadPanel) {
                        uploadPanel = parent.find('.upload-documento-sostitutivo');
                        el.data('upload-panel', uploadPanel);

                        //uploadPanel.dialog({
                        //    title: uploadPanel.data('title'),
                        //    width: 600,
                        //    modal: false,
                        //    autoOpen: false
                        //});
                    }

                    //uploadPanel.dialog('open');
                    uploadPanel.modal('show');
                    //uploadPanel.parent().appendTo($('form:first'))

                    e.preventDefault();
                });

            });
        });
    </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="inputForm">

        <h3>I seguenti allegati della pratica possono essere sostituiti</h3>

        <uc1:SostituzioniDocumentaliGrid runat="server" 
                                         id="sostituzioniDocumentaliGrid" 
                                         OnAnnullaSostituzioneDocumentale="OnAnnullaSostituzioneDocumentale" 
                                         OnSostituisciDocumento="OnSostituisciDocumento" />

        <asp:Repeater runat="server" ID="rptDocumentiEndoSostituibili" OnItemDataBound="rptDocumentiEndoSostituibili_ItemDataBound">
            <ItemTemplate>
                <h4>
                    <asp:Literal runat="server" Text='<%#Eval("Descrizione") %>' />
                </h4>

                <uc1:SostituzioniDocumentaliGrid runat="server" 
                                         id="sostituzioniDocumentaliEndoGrid" 
                                         OnAnnullaSostituzioneDocumentale="OnAnnullaSostituzioneDocumentale" 
                                         OnSostituisciDocumento="OnSostituisciDocumento" />
            </ItemTemplate>

        </asp:Repeater>

        <div class="bottoni">
            <asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
            <asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
        </div>
    </div>
</asp:Content>
