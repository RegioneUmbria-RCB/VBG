<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="Scadenzario.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Scadenzario" Title="Lista scadenze" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
<div class="inputForm">
	<fieldset>
		<div>
		<asp:GridView id="dgScadenze" runat="server" Width="100%" AutoGenerateColumns="False" DataKeyNames="CodiceScadenza" OnRowCommand="dgScadenze_RowCommand" OnSelectedIndexChanged="dgScadenze_SelectedIndexChanged" GridLines="None">
	<Columns>
		<asp:TemplateField  HeaderText="Numero Istanza">
			<ItemTemplate>
				<asp:LinkButton runat="server" ID="lnkDettaglio" Text='<%# Bind("NumeroIstanza") %>' CommandArgument='<%# Bind("CodiceIstanza") %>' CommandName="DettaglioIstanza"></asp:LinkButton>
			</ItemTemplate>
		</asp:TemplateField>
		
		<asp:BoundField DataField="DatiRichiedente" HeaderText="Richiedente" HtmlEncode="false" />
		<asp:BoundField DataField="DatiAzienda" HeaderText="Azienda" />
		<asp:BoundField DataField="DatiTecnico" HeaderText="Tecnico" />
		<asp:BoundField DataField="DescrStatoIstanza" HeaderText="Stato Istanza" />
		<asp:BoundField DataField="DatiMovimento" HeaderText="Movimento precedente" />
		<asp:BoundField DataField="DescrMovimentoDaFare" ItemStyle-Font-Bold="true" HeaderText="Movimento in scadenza" />
		<asp:BoundField DataField="DataScadenza" HeaderText="Data" />
		<asp:ButtonField Text="Effettua movimento" CommandName="Select" />
	</Columns>
	<EmptyDataTemplate>
		Non è stata trovata nessuna scadenza
	</EmptyDataTemplate>
</asp:GridView>
		</div>
		
		<div class="bottoni">
			<asp:Button id="Button1" runat="server" Text="Chiudi" onclick="Button1_Click" />
		</div>
	</fieldset>
</div>
</asp:Content>
