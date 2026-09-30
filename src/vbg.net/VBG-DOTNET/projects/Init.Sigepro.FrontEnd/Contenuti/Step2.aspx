<%@ Page Title="" Language="C#" MasterPageFile="~/Contenuti/ContenutiMaster.Master"
    EnableViewState="false" AutoEventWireup="true" CodeBehind="Step2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Contenuti.Step2" %>

<%@ MasterType VirtualPath="~/Contenuti/ContenutiMaster.Master" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <%=LoadScripts(new[] {
        "~/js/app/alberoInterventi.js",
        "~/js/app/wrapDescrizioneNodiPadre.js"
    }) %>


    <script type="text/javascript">
        

        $(function () {
            $('#tblContenutoCentrale').attr('background', '<%= ResolveClientUrl("~/images/contenuti/bg-interno-verde.png")%>');

            $('#infoImage').attr('title', $('#contenutiTooltip').html())
                .hoverbox({ id: 'tooltipIntervento' });

            window.alberoInterventi = new AlberoInterventi();

            window.alberoInterventi.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;
        });

    </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">
    <div id="stepContenuto" class="ricercaIntervento">

        <div id="intestazione">
            <div id="testo">
                <h2>Ricerca</h2>
                <h1>L'INTERVENTO DI TUO INTERESSE</h1>
            </div>
            <div id="bottoni">
                <img src='<%= ResolveClientUrl("~/images/contenuti/info.png") %>' id="infoImage" alt="informazioni" />
                <a href="#" id="lnkRicerca">
                    <img src='<%= ResolveClientUrl("~/images/contenuti/ricerca-testuale.png") %>' alt="ricerca testuale" border="0" /></a>
            </div>
            <div id="contenutiTooltip">
                In questa sezione è possibile selezionare un intervento per reperire informazioni relative a: 
				<ul>
                    <li>Leggi e normative</li>
                    <li>Modulistiche</li>
                    <li>Dichiarazioni</li>
                    <li>Allegati da presentare</li>
                </ul>
            </div>
        </div>
        <div class="clear"></div>
        <%--<asp:Literal runat="server" ID="ltrMessaggioErrore" />--%>
        <cc1:AlberoInterventiJs runat="server" ID="alberoInterventi"
            EvidenziaVociAttivabiliDaAreaRiservata="true"
            Note="<i>Le voci contrassegnate con * sono attivabili tramite i servizi online</i>"
            OnFogliaSelezionata="InterventoSelezionato" />
    </div>
</asp:Content>
