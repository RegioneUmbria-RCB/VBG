<%@ Page Title="Sessione terminata correttamente" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="LogoutCompleted.aspx.cs" Inherits="Init.Sigepro.FrontEnd.LogoutCompleted" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server"></asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


    <asp:Label ID="Label2" runat="server">
	    Sessione terminata correttamente, ora è possibile chiudere la finestra corrente oppure tornare alla pagina di login
    </asp:Label>

    <div style="margin-top: 24px">
        <asp:Button runat="server" ID="cmdChiudi" CssClass="btn btn-primary" Text="Torna alla pagina di login" OnClick="cmdChiudi_Click" />
    </div>
</asp:Content>
