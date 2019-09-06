<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-soggetti.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_soggetti" %>
<div>
    <h3>Soggetti</h3>

    <asp:GridView GridLines="None" runat="server" ID="dgSoggetti" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:BoundField DataField="Nominativo" HeaderText="Nominativo" />
            <asp:BoundField DataField="InQualitaDi" HeaderText="Tipo soggetto" />
            <asp:BoundField DataField="NominativoCollegato" HeaderText="Anagrafica collegata" />
            <asp:BoundField DataField="Procuratore" HeaderText="Procuratore" />
        </Columns>
    </asp:GridView>
</div>