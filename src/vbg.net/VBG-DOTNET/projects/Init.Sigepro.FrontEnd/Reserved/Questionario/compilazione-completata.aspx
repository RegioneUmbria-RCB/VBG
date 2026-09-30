<%@ Page  Title="Compilazione questionario" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="compilazione-completata.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Questionario.compilazione_completata" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="alert alert-success">
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="questionario.titolo.compilazione-completata">
            Grazie per aver compilato il nostro questionario, la tua opinione ci aiuterà a migliorare la qualità dei nostri servizi.
        </ar:RisorsaTestualeLabel>
    </div>

    <asp:Button runat="server" ID="cmdChiudi" Text="Torna alla pratica" CssClass="btn btn-default vbg-show-spinner" OnClick="cmdChiudi_Click" />
</asp:Content>
