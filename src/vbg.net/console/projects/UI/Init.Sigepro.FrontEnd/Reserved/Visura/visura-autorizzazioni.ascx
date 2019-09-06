<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-autorizzazioni.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_autorizzazioni" %>

<asp:GridView GridLines="None" runat="server" CssClass="table" ID="dgAutorizzazioni" AutoGenerateColumns="false">
    <Columns>
        <asp:BoundField DataField="Data" HeaderText="Data Rilascio" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
        <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
        <asp:BoundField DataField="Note" HeaderText="Note" />
        <asp:BoundField DataField="Numero" HeaderText="Numero" />
    </Columns>
    <EmptyDataTemplate>
        <div class="alert alert-info">La pratica non possiede autorizzazioni</div>
    </EmptyDataTemplate>
</asp:GridView>