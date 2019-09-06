<%@ Page Title="Dati Istanza" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
	CodeBehind="DettaglioIstanzaExArchivio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaExArchivio" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>

<%@ Register Src="VisuraExCtrl.ascx" TagName="VisuraExCtrl" TagPrefix="uc1" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<uc1:VisuraExCtrl ID="VisuraExCtrl1" runat="server" DaArchivio="true" />


	<asp:Button ID="cmdClose" runat="server" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />
</asp:Content>
