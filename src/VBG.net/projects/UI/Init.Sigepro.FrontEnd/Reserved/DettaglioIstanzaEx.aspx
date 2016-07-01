<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DettaglioIstanzaEx.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaEx" Title="Dati istanza" %>

<%@ Register Src="VisuraExCtrl.ascx" TagName="VisuraExCtrl" TagPrefix="uc1" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<uc1:VisuraExCtrl id="VisuraExCtrl1" runat="server">
	</uc1:VisuraExCtrl>
	
		<div class="inputForm">
	<fieldset>
	<div class="bottoni">
		<asp:Button ID="cmdClose" runat="server" Text="Chiudi" OnClick="cmdClose_Click" />
	</div>
	</fieldset>
	
	</div>
</asp:Content>
