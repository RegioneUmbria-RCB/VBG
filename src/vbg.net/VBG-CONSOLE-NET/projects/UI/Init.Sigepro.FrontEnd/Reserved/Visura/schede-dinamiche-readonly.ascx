<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="schede-dinamiche-readonly.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.schede_dinamiche_readonly" %>
<%@ Register TagPrefix="dd" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<div class="dati-dinamici-readonly">
    <asp:Repeater runat="server" ID="rptListaSchede" OnItemDataBound="rptListaSchede_ItemDataBound">
        <ItemTemplate>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <asp:Literal runat="server" ID="ltrTitoloScheda" Text='<%#Eval("Descrizione") %>' />
                </div>
                <div class="panel-body">
                    <dd:ModelloDinamicoRenderer ID="renderer" runat="server" />
                </div>
            </div>

        </ItemTemplate>
    </asp:Repeater>
</div>
