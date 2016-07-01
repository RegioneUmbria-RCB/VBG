<%@ Page Title="Archivio pratiche" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="ArchivioPratiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ArchivioPratiche" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Visura" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="ricercaView">
			<div class="inputForm">
				<cc1:FiltriArchivioIstanzeControl ID="FiltriVisura" runat="server" />
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
						<cc1:ListaArchivioIstanzeDataGrid runat="server" ID="dglistaPratiche" Width="100%" OnIstanzaSelezionata="dglistaPratiche_IstanzaSelezionata">
						</cc1:ListaArchivioIstanzeDataGrid>
					</div>
					<div class="bottoni">
						<asp:Button ID="cmdNewSearch" runat="server" Text="Nuova Ricerca" OnClick="cmdNewSearch_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
	</asp:MultiView>
</asp:Content>
