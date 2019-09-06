<%@ Page Title="Dati inviati con successo" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DatiInviatiConSuccesso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.DatiInviatiConSuccesso" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="alert alert-success" role="alert">
	I dati immessi sono stati trasmessi correttamente
	</div>

    <asp:Button Text="Torna alla home page" id="cmdChiudi" runat="server" CssClass="btn btn-default" OnClick="cmdChiudi_Click" />
</asp:Content>
