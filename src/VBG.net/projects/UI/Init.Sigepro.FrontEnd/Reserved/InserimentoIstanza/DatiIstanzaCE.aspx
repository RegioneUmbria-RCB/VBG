<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="DatiIstanzaCE.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiIstanzaCE" Title="Untitled Page" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		<fieldset>
			<div>
				<asp:Label runat="server" ID="Label1" AssociatedControlID="Oggetto" Text="Oggetto (*)" />
				<asp:TextBox id="Oggetto" runat="server" Columns="60" MaxLength="100" Rows="5" TextMode="MultiLine" />
				<asp:RequiredFieldValidator id="RequiredFieldValidator1" runat="server" ErrorMessage="Campo obbligatorio" ControlToValidate="Oggetto" />
			</div>
			
			<div>
				<asp:Label runat="server" ID="Label2" AssociatedControlID="Note" Text="Note" />
				<asp:TextBox id="Note" runat="server" Columns="60" MaxLength="100" Rows="10" TextMode="MultiLine" />
			</div>
		</fieldset>
	</div>
</asp:Content>
