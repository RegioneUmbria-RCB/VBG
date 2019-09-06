<%@ Page Title=" " Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="compila.aspx.cs" Inherits="Init.Sigepro.FrontEnd.moduli_fvg.compilazione.compila" %>

<%@ MasterType VirtualPath="~/AreaRiservataPopupMaster.Master" %>
<%@ Register TagPrefix="dd" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.form.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.tooltip.fix.js")%>'></script>
    <style>
        .con-bordo td {
            border: 1px solid #000;
        }
    </style>

    <script type="text/javascript">

        var g_datiDinamiciExtender = null;

        function fixAggiungiRiga() {
            var button = $('tr.bloccoMultiploAggiungiRiga>td>a');
            var el = $('<i />', { 'class': 'ion-android-add-circle' });

            el.css('padding-right', '5px');
            button.addClass('aggiungiRiga');
            button.before(el);
        }

        function fixAggiungiBlocco() {
            var button = $('#datiDinamici a[id*=btnAggiungi]');

            var el = $('<i />', { 'class': 'ion-android-add-circle' });
            el.css('padding-right', '5px');
            button.addClass('aggiungiRiga');
            button.before(el);
        }

        function fixEliminaRiga() {
            var button = $('.divEliminazioneBlocco>a');
            var el = $('<i />', { 'class': 'ion-android-remove-circle' });
            el.css('padding-right', '5px');
            button.addClass('eliminaRiga');
            button.before(el);

        }

        $(function () {
            g_datiDinamiciExtender = new DatiDinamiciExtender('<%=pnlErrori.ClientID %>', '#<%=cmdSalva.ClientID %>, #<%=cmdSalvaEContinua.ClientID%>', $('.d2WatchButton'));

            fixAggiungiRiga();
            fixAggiungiBlocco();
            fixEliminaRiga();
        });

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
         
    <div id="datiDinamici" class="gestione-dati-dinamici">
        <div class='<%= renderer.DataSource.ModelloMultiplo ? "contenutoScheda" : String.Empty %>'>

            <asp:Panel runat="server" ID="pnlErrori" CssClass="alert alert-danger" Style="display: none"></asp:Panel>
            <dd:ModelloDinamicoRenderer ID="renderer" runat="server" />

            <asp:Button runat="server" ID="cmdSalvaEContinua" Text="Salva e continua" CssClass="btn btn-primary" OnClick="cmdSalvaEContinua_Click" />
            <asp:Button runat="server" ID="cmdSalva" Text="Salva e torna alla lista dei quadri" CssClass="btn btn-primary" OnClick="cmdSalva_Click" />
            <asp:LinkButton runat="server" ID="lnkChiudi" Text="Chiudi senza salvare" CssClass="btn btn-default d2WatchButton" OnClick="lnkChiudi_Click" />

        </div>
    </div>

</asp:Content>
