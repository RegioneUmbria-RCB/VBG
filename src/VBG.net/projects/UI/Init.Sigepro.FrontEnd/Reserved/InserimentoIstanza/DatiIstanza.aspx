<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="DatiIstanza.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiIstanza" Title="Untitled Page" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
<div class="inputForm">
	<fieldset>
		<div>
			<asp:Label runat="server" ID="label1" AssociatedControlID="Oggetto">Oggetto (*)</asp:Label>
			<asp:TextBox runat="server" ID="Oggetto" Columns="60" MaxLength="100" Rows="5"
                TextMode="MultiLine" />
             <asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ErrorMessage="*"
                ControlToValidate="Oggetto"></asp:RequiredFieldValidator>  
		</div>
		
		<div>
			<asp:Label runat="server" ID="label2" AssociatedControlID="DenominazioneAttivita">Denominazione Attività</asp:Label>
			<asp:TextBox runat="server" id="DenominazioneAttivita" Columns="60" MaxLength="100" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="*"
                ControlToValidate="DenominazioneAttivita" Enabled="false"></asp:RequiredFieldValidator>
		</div>
		
		<div>
			<asp:Label runat="server" ID="label3" AssociatedControlID="Note">Note</asp:Label>
			<asp:TextBox runat="server" ID="Note" TextMode="MultiLine" Columns="60" Rows="10" />
		</div>
		
	</fieldset>
</div>

</asp:Content>
