<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="ErroreAccessoPagina.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ErroreAccessoPagina" Title="Errore durante l'apertura dell'istanza" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master"%>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<asp:Literal runat="server" ID="ltrErrore"></asp:Literal>
</asp:Content>
