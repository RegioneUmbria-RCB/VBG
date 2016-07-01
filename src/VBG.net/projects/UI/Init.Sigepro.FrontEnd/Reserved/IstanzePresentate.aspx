<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" Codebehind="IstanzePresentate.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.IstanzePresentate"
	Title="Le mie pratiche" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Visura" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="ricercaView">
			<div class="inputForm">
				<cc1:FiltriVisuraControl ID="FiltriVisura" runat="server"></cc1:FiltriVisuraControl>
				<fieldset>
					<div class="bottoni">
						<asp:Button ID="cmdSearch" runat="server" Text="Cerca" OnClick="cmdSearch_Click" /></div>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="listaView">
			<div class="inputForm">
				<fieldset>
					<div>
						<cc1:ListaVisuraDataGrid runat="server" ID="dglistaPratiche" Width="100%" OnIstanzaSelezionata="dglistaPratiche_IstanzaSelezionata">
						</cc1:ListaVisuraDataGrid>
					</div>
					<div class="bottoni">
						<asp:Button ID="cmdNewSearch" runat="server" Text="Nuova Ricerca" OnClick="cmdNewSearch_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
	</asp:MultiView>
</asp:Content>
