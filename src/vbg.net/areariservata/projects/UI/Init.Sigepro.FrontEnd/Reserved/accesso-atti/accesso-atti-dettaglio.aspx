<%@ Page Title="Dettaglio pratica" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="accesso-atti-dettaglio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.accesso_atti.accesso_atti_dettaglio" %>

<%@ Register Src="~/Reserved/VisuraExCtrl.ascx" TagName="VisuraExCtrl" TagPrefix="uc1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <uc1:VisuraExCtrl id="VisuraExCtrl1" runat="server">
    </uc1:VisuraExCtrl>
    <asp:Button ID="cmdClose" runat="server" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />

</asp:Content>
