<%@ Page Title="Dati inviati con successo" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DatiInviatiConSuccesso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.DatiInviatiConSuccesso" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="alert alert-success" role="alert">
		<%=MessaggioDiSuccesso %>
	</div>
    <asp:Button Text="Torna alla home page" id="cmdChiudi" runat="server" CssClass="btn btn-default" OnClick="cmdChiudi_Click" />

	<asp:Button Text="Vai alla pratica" ID="cmdGotoPratica" runat="server" CssClass="btn btn-primary" OnClick="cmdVisualizzaDati_Click" />
</asp:Content>