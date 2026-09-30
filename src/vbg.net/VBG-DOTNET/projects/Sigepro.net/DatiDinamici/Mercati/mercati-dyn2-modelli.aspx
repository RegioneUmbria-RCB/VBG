<%@ Page Title="Schede del mercato" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="mercati-dyn2-modelli.aspx.cs" Inherits="Sigepro.net.DatiDinamici.Mercati.mercati_dyn2_modelli" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register Src="~/Istanze/DatiDinamici/DatiDinamiciCtrl.ascx" TagName="DatiDinamiciCtrl" TagPrefix="uc1" %>


<asp:Content runat="server" ContentPlaceHolderID="headPagina">
        <script>
        $(function () {
            $(".d2DateTextBox").on("change", function () {
                $(this).trigger("changeDate");
            });
        });

    </script>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <uc1:datidinamicictrl id="DatiDinamiciCtrl1" runat="server" ongetlistamodelli="GetListaModelli"
        ongetmodellodinamicodaid="GetModelloDinamicoDaId" NomeChiaveCodice="CodiceMercato" onaggiungischeda="AggiungiScheda" oneliminascheda="EliminaScheda" ongetlistaindicischeda="GetListaIndiciScheda"
        onclose="Close"
        onverificaesistenzastorico="VerificaEsistenzaStorico">
	</uc1:datidinamicictrl>
</asp:Content>
