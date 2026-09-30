<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="ModificaPassword.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ModificaPassword"
    Title="Modifica password" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <ar:TextBox runat="server" ID="txtVecchiaPassword" Label="Vecchia password" TextMode="Password" />
    <ar:TextBox runat="server" ID="txtNuovaPassword" Label="Nuova password" TextMode="Password" />
    <ar:TextBox runat="server" ID="txtConfermaNuovaPassword" Label="Conferma nuova password" TextMode="Password" />

    <div class="bottoni">
        <asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
    </div>

</asp:Content>
