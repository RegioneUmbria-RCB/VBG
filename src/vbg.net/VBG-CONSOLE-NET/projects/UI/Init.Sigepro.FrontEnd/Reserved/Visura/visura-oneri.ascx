<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-oneri.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_oneri" %>

<asp:GridView GridLines="None" runat="server" ID="dgOneri" CssClass="table" AutoGenerateColumns="false">
    <Columns>
        <asp:BoundField HeaderText="Causale" DataField="Causale" />
        <asp:BoundField DataField="Importo" HeaderText="Importo" ItemStyle-HorizontalAlign="Right" DataFormatString="€ {0:N2}" HtmlEncode="false" />
        <asp:BoundField DataField="DataScadenza" HeaderText="Data Scadenza" ItemStyle-HorizontalAlign="Center"
            DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
        <asp:BoundField DataField="DataPagamento" HeaderText="Data Pagamento" ItemStyle-HorizontalAlign="Center"
            DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
    </Columns>
    <EmptyDataTemplate>
        <div class="alert alert-info">La pratica non contiene oneri</div>
    </EmptyDataTemplate>
</asp:GridView>
