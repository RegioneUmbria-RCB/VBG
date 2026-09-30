<%@ Page Title="Riepilogo posizioni debitorie" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="pago-dopo-riepilogo.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.pago_dopo.pago_dopo_riepilogo" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>
<%@ Register Src="~/Reserved/InserimentoIstanza/Pagamenti/stato-posizioni-ctrl.ascx" TagPrefix="uc1" TagName="statoposizionictrl" %>


<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">

    <div>
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="riepilogo-posizioni-debitorie.descrizione-estesa">
            Di seguito la lista dei pagamenti da effettuare necessari per la presentazione dell’istanza.<br />
            <div class="alert alert-info">
                <b>
                    Gli avvisi saranno validi per trenta giorni dalla data di elaborazione, come indicato nel testo dell’avviso. 
                    Una volta scaduti senza che sia stato effettuato il pagamento bisognerà rigenerare le posizioni debitorie.
                </b>
            </div>
        </ar:RisorsaTestualeLabel>
    </div>


    <h3>
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="riepilogo-posizioni-debitorie.titolo">
        Sono state aperte le seguenti posizioni debitorie
        </ar:RisorsaTestualeLabel>
    </h3>

    <uc1:statoposizionictrl runat="server" ID="statoPosizioniControl" />


    <div class="piede-pagina">
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="riepilogo-posizioni-debitorie.piede-pagina">
        La compilazione della domanda potrà riprendere al termine del saldo di tutte le posizioni debitorie aperte
        </ar:RisorsaTestualeLabel>
    </div>

    <asp:Button runat="server" ID="cmdTornaAllaHome" Text="Torna alla home page" CssClass="btn btn-primary" OnClick="cmdTornaAllaHome_Click" />
</asp:Content>
