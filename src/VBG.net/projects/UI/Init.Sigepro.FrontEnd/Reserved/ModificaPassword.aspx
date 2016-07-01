<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="ModificaPassword.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ModificaPassword" 
Title="Modifica password" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

	<div class="inputForm">
		<fieldset>
			<div>
				<asp:Label ID="Label1" runat="server" Text="Label" AssociatedControlID="txtVecchiaPassword">Vecchia password:</asp:Label>
				<asp:TextBox ID="txtVecchiaPassword" runat="server" TextMode="Password"></asp:TextBox>
			</div>
			
			<div>
				<asp:Label ID="Label2" runat="server" Text="Label" AssociatedControlID="txtNuovaPassword">Nuova password:</asp:Label>
				<asp:TextBox ID="txtNuovaPassword" runat="server" TextMode="Password"></asp:TextBox>
			</div>
			
			<div>
				<asp:Label ID="Label3" runat="server" Text="Label" AssociatedControlID="txtConfermaNuovaPassword">Conferma nuova password:</asp:Label>
				<asp:TextBox ID="txtConfermaNuovaPassword" runat="server" TextMode="Password"></asp:TextBox>
			</div>
		
		<div class="bottoni">
		<asp:Button id="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
		</div>
		</fieldset>
		
	</div>
</asp:Content>
