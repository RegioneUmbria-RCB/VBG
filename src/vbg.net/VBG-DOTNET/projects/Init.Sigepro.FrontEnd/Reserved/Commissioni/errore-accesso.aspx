<%@ Page Title="Errore accesso" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="errore-accesso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Commissioni.errore_accesso" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="alert alert-danger">
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="commissioni.errore-accesso.messaggio">
            <b>Si è verificato un errore durante la lettura del dato richiesto</b><br />
            Contattare l'assistenza se si ritiene di disporre dei permessi richiesti per l'accesso.
        </ar:RisorsaTestualeLabel>
    </div>
    <asp:LinkButton runat="server" ID="cmdChiudi" Text="Chiudi" OnClick="cmdChiudi_Click" CssClass="btn btn-default vbg-show-spinner" />
</asp:Content>
