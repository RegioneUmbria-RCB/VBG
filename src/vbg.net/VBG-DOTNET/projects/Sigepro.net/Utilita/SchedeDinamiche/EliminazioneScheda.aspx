<%@ Page Title="Eliminazione schede" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="EliminazioneScheda.aspx.cs" Inherits="Sigepro.net.Utilita.SchedeDinamiche.EliminazioneScheda" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <fieldset>
        <legend>Selezionare alias e id scheda da eliminare</legend>
        <init:LabeledTextBox runat="server" ID="txtAlias" Descrizione="Alias" />
        <init:LabeledIntTextBox runat="server" ID="txtIdScheda" Descrizione="Id scheda" />

        <div>
            <asp:Button ID="btnElimina" runat="server" CssClass="btn btn-primary" Text="Elimina (occhio che cancella sul serio!)" OnClick="btnElimina_Click" />
        </div>
    </fieldset>
</asp:Content>
