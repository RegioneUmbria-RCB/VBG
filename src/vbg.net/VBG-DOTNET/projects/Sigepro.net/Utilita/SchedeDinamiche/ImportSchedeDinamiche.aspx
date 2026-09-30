<%@ Page Title="Importazione schede dinamiche" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="ImportSchedeDinamiche.aspx.cs" Inherits="Sigepro.net.Utilita.SchedeDinamiche.ImportSchedeDinamiche" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <fieldset>
        <legend>Selezionare il file da importare</legend>
        <asp:FileUpload ID="fileUpload" runat="server" />

    </fieldset>
    <fieldset>
        <legend>Selezionare alias e software in cui importare il file</legend>
        <init:LabeledTextBox runat="server" ID="txtAlias" Descrizione="Alias" />
        <init:LabeledTextBox runat="server" ID="txtSoftware" Descrizione="Software" />

        <div>
            <asp:Button ID="btnVerifica" runat="server" CssClass="btn btn-primary" Text="Verifica" OnClick="btnVerifica_Click" />
            <%if (PermetteImport)
                { %>
            <asp:Button ID="btnImport" runat="server" CssClass="btn btn-primary" Text="Importa" OnClick="btnImporta_Click" />
            <%} %>
        </div>
    </fieldset>

    <asp:Repeater runat="server" ID="rptEsiti">
        <ItemTemplate>
            <div class="alert alert-<%# Eval("Tipo") %>">
                <%# Eval("Messaggio") %>
            </div>
        </ItemTemplate>
    </asp:Repeater>

</asp:Content>
