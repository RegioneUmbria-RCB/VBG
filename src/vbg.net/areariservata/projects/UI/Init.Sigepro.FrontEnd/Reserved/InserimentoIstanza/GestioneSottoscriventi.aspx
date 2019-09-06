<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneSottoscriventi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneSottoscriventi" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <asp:GridView runat="server" ID="gvSottoscriventi"
        AutoGenerateColumns="false"
        OnRowDataBound="gvSottoscriventi_RowDataBound"
        DataKeyNames="CodiceFiscale"
        CssClass="table"
        GridLines="None">
        <Columns>
            <asp:BoundField HeaderText="Nominativo" DataField="Nominativo" />
            <asp:BoundField HeaderText="In qualità di" DataField="TipoSoggetto" />
            <asp:TemplateField HeaderText="Sottoscrive" ItemStyle-HorizontalAlign="Center">
                <ItemTemplate>
                    <asp:CheckBox runat="server" ID="chkSottoscrive" CssClass="form-control" Checked='<%# Bind("Sottoscrivente") %>' OnCheckedChanged="chkSottoscrive_CheckedChanged" AutoPostBack="true" />
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField ItemStyle-HorizontalAlign="Center" HeaderText="<span><b>Soggetto sottoscrivente</b><br/><i>Indicare il soggetto delegato alla sottoscrizione digitale</i></span>">
                <ItemTemplate>
                    <asp:DropDownList runat="server" ID="ddlAventeProcura" CssClass="form-control">
                    </asp:DropDownList>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
    </asp:GridView>

</asp:Content>
