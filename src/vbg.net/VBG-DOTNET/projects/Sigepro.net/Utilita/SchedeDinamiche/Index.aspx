<%@ Page Title="Utilità schede dinamiche" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="Index.aspx.cs" Inherits="Sigepro.net.Utilita.SchedeDinamiche.Index" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


    <fieldset>
        <legend>Esportazione scheda</legend>
        <init:LabeledTextBox runat="server" ID="txtEsportaAlias" Descrizione="Alias" />
        <init:LabeledIntTextBox runat="server" ID="txtEsportaIdScheda" Descrizione="Id scheda" />

        <div>
            <asp:Button ID="btnVaiAEsporta" runat="server" CssClass="btn btn-primary" Text="Esporta scheda" OnClick="btnVaiAEsporta_Click" />
        </div>
    </fieldset>

        <fieldset>
        <legend>Esportazione Istanza</legend>
        <init:LabeledTextBox runat="server" ID="txtEsportaIstanzaAlias" Descrizione="Alias" />
        <init:LabeledIntTextBox runat="server" ID="txtEsportaCodiceIstanza" Descrizione="Codice istanza" />

        <div>
            <asp:Button ID="btnEsportaIstanza" runat="server" CssClass="btn btn-primary" Text="Esporta istanza" OnClick="btnEsportaIstanza_Click" />
        </div>
    </fieldset>

    <fieldset>
        <legend>ImportazioneScheda</legend>
        <init:LabeledTextBox runat="server" ID="txtImportAlias" Descrizione="Alias" />
        <init:LabeledTextBox runat="server" ID="txtImportSoftware" Descrizione="Software" />

        <div>
            <asp:Button ID="btnVaiAImporta" runat="server" CssClass="btn btn-primary" Text="Vai alla pagina" OnClick="btnVaiAImporta_Click"/>
        </div>
    </fieldset>


    <fieldset>
        <legend>Eliminazione scheda</legend>
        <init:LabeledTextBox runat="server" ID="txtEliminaAlias" Descrizione="Alias" />
        <init:LabeledIntTextBox runat="server" ID="txtEliminaIdScheda" Descrizione="Id scheda" />

        <div>
            <asp:Button ID="btnVaiAElimina" runat="server" CssClass="btn btn-primary" Text="Vai alla pagina" OnClick="btnVaiAElimina_Click" />
        </div>
    </fieldset>
</asp:Content>
