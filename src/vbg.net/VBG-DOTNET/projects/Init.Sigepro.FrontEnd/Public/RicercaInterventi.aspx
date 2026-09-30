<%@ Page Title="Ricerca interventi" Language="C#" MasterPageFile="~/Public/PaginaRicercaFoMaster.Master" AutoEventWireup="True" CodeBehind="RicercaInterventi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.RicercaInterventi" %>

<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <%=LoadScripts(new[] {
    "~/js/app/alberointerventi.js",
    "~/js/app/wrapDescrizioneNodiPadre.js",
    }) %>

    <script type="text/javascript">


        $(function () {
            var alberoInterventi = new AlberoInterventi();
            alberoInterventi.instance.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;
        });


    </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


    <div href="#" class="bottoneContenuti azione" id="lnkRicerca">
        <div class="titolo">Ricerca</div>
        <div class="descrizione">Testuale</div>
    </div>
    <div class="clear" />

    <cc1:AlberoInterventiJs runat="server" ID="alberoInterventi"
        EvidenziaVociAttivabiliDaAreaRiservata="true"
        Note="<i>Le voci contrassegnate con * sono attivabili tramite i servizi online</i>"
        OnFogliaSelezionata="InterventoSelezionato" CookiePrefix="Interventi" />

</asp:Content>
