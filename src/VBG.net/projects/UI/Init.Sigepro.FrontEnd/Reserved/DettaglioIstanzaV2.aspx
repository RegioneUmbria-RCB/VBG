<%@ Page Title="Dati istanza" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DettaglioIstanzaV2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaV2" %>

<%@ Register Src="VisuraCtrlV2.ascx" TagName="VisuraCtrlV2" TagPrefix="uc1" %>

<asp:Content ID="ContentHead" ContentPlaceHolderID="headPagina" runat="server">
    <style>
        .note-allegato{ font-style: italic }
    </style>

</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<uc1:VisuraCtrlV2 runat="server" ID="visuraCtrl" OnErroreRendering="VisuraCtrlV2_ErroreRendering" />
	<fieldset>
		<div class="bottoni">
			<asp:Button runat="server" ID="cmdClose" Text="Chiudi" OnClick="cmdClose_Click" />
		</div>
	</fieldset>
</asp:Content>
