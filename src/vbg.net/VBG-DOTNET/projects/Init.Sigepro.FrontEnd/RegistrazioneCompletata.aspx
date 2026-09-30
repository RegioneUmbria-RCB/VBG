<%@ Page Title="Registrazione completata" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="RegistrazioneCompletata.aspx.cs" Inherits="Init.Sigepro.FrontEnd.RegistrazioneCompletata" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server"></asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <%if (EsitoPositivo)
        {%>

    <asp:Label ID="lblTesto" runat="server" Text="Richiesta di accesso al sistema inviata correttamente: a breve le verranno comunicate, all’indirizzo di posta elettronica indicato in fase di registrazione, le credenziali di accesso">
    </asp:Label>

    <%}
        else
        { %>
    <div class="alert alert-danger">
        <%=MessaggioErrore %>
    </div>
    <%} %>
    <div style="margin-top: 24px">
        <asp:Button runat="server" ID="cmdChiudi" CssClass="btn btn-primary" Text="Torna alla pagina di login" OnClick="cmdChiudi_Click" />
    </div>
</asp:Content>
