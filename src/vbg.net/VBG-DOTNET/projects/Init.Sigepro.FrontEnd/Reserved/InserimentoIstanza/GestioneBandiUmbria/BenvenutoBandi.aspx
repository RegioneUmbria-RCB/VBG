<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="BenvenutoBandi.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.BenvenutoBandi" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		<fieldset>
			<div class="descrizioneStep">
				<asp:Literal runat="server" ID="ltrTestoListaStep"></asp:Literal>
			</div>
		</fieldset>	

		<asp:Panel runat="server" ID="pnlSelezioneComune">
			<fieldset>
				<div style="width:100%;margin:0px auto;text-align:center">
					<asp:Literal runat="server" ID="lblComuniAssociati">Selezionare il comune per cui si vuole presentare l'istanza<br /></asp:Literal>
					<br />
					<asp:DropDownList ID="cmbComuni" runat="server" DataTextField="Value" DataValueField="Key" />
				</div>
			</fieldset>
		</asp:Panel>
	</div>

</asp:Content>
