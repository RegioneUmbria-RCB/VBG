<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DettaglioIstanzaEx.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaEx" Title="Dati istanza" %>

<%@ Register Src="VisuraExCtrl.ascx" TagName="VisuraExCtrl" TagPrefix="uc1" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<uc1:VisuraExCtrl id="VisuraExCtrl1" runat="server">
	</uc1:VisuraExCtrl>
	
    <asp:Button ID="cmdClose" runat="server" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />
    <asp:Button ID="cmdAccedi" runat="server" CssClass="btn btn-primary" Text="Accedi per visualizzare i dati completi" OnClick="cmdAccedi_Click"/>
    <asp:Button ID="cmdGeneraRiepilogo" runat="server" CssClass="btn btn-primary" Text="Rigenera riepilogo pratica" OnClick="cmdGeneraRiepilogo_Click"/>

</asp:Content>
