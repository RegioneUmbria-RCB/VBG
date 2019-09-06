<%@ Page Title="asd" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneAllegatiMultipli.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiMultipli" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        require(['jquery', 'app/multi-upload/multi-upload', 'jquery.ui'], function ($) {
            $(function () {
                $('#multi-upload').ArMultiUpload();

                $('#<%=cmdConfirmUpload.ClientID%>').on('click', function () {

                    $('#caricamentoFileIncorso').modal('show');

                    $('.bottoni').hide();
                });

            });
        });
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">

    <div class="multi-upload-step">

        <div>
            Selezionare uno o più files da allegare per il documento 
            <b>"<asp:Literal runat="server" ID="ltrDescrizioneAllegato" />"</b>
        </div>

        <div id="multi-upload" class="multi-upload-form">
            <div class="upload-template" style="display: none">
                <div style="margin-bottom:10px">
                    <asp:FileUpload runat="server" ID="fuTemplate" CssClass="form-control" Style="width: 350px" />
                    <a href="#" class="remove-file">
                        <i class="glyphicon glyphicon-trash"></i> Rimuovi
                    </a>
                </div>
            </div>

            <div>
                <div class="upload-form">
                </div>

                <a href="#" class="add-file" style="margin-top: 12px; margin-bottom:12px"><i class="ion-plus"></i> Aggiungi file</a>
            </div>
        </div>

        <asp:Button runat="server" ID="cmdConfirmUpload" CssClass="btn btn-primary" Text="Conferma" OnClick="cmdConfirmUpload_Click" />
        <asp:Button runat="server" ID="cmdCancelupload" Text="Annulla" CssClass="btn btn-default" OnClick="cmdCancelupload_Click" />

    </div>
</asp:Content>
