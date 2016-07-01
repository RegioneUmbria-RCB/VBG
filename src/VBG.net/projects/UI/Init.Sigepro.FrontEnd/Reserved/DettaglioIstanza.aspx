<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" Codebehind="DettaglioIstanza.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanza"
	Title="Dati Pratica" %>
<%@ Register TagPrefix="uc1" TagName="VisuraCtrl" Src="~/Reserved/VisuraCtrl.ascx" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	
	<uc1:VisuraCtrl runat="server" ID="visuraCtrl" />
	
	<div class="inputForm">
	<fieldset>
	<div class="bottoni">
		<asp:Button ID="cmdClose" runat="server" Text="Chiudi" OnClick="cmdClose_Click" />
	</div>
	</fieldset>
	
	</div>
</asp:Content>
