<%@ Page Title="Integrazione con SIT" ClientIDMode="Static" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="IntegrazioneSIT.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.IntegrazioneSIT" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div id="contenuto-step">
        <div class="descrizione-step">
            <asp:Literal runat="server" ID="ltrDescrizioneStep" ></asp:Literal>
        </div>
        <div>
            <asp:Literal runat="server" ID="ltrMessaggioRisposta"></asp:Literal>
        </div>
        <div class="bottoni" id="bottoniMovimento">
            <asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
            <asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
        </div>
    </div>
</asp:Content>