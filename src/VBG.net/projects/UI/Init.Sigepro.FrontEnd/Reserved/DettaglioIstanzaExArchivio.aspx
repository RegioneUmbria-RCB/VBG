<%@ Page Title="Dati Istanza" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
	CodeBehind="DettaglioIstanzaExArchivio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaExArchivio" %>

<%@ Register Src="VisuraExCtrl.ascx" TagName="VisuraExCtrl" TagPrefix="uc1" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<uc1:VisuraExCtrl ID="VisuraExCtrl1" runat="server" DaArchivio="true" />
	<div class="inputForm">
		<fieldset>
			<div class="bottoni">
				<asp:Button ID="cmdClose" runat="server" Text="Chiudi" OnClick="cmdClose_Click" />
			</div>
		</fieldset>
	</div>
</asp:Content>
