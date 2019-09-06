<%@ Page Title="Gestione utenze tares" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneUtenzeTares.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneUtenzeTares" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register src="GestioneUtenzeTares_DettagliUtenza.ascx" tagname="GestioneUtenzeTares_DettagliUtenza" tagprefix="uc1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		<asp:MultiView runat="server" ID="multiView">
			<asp:View runat="server" ID="ricercaView">
				<fieldset>
					<div class="descrizioneSezione">
						<asp:Literal runat="server" ID="ltrTestoRicerca" />
					</div>
					<div>
						<asp:Label ID="Label1" runat="server" AssociatedControlID="txtCfUtenza" Text="Identificativo contribuente o codice fiscale"></asp:Label>
						<asp:TextBox runat="server" ID="txtCfUtenza" Columns="20" MaxLength="16" />
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdCerca" Text="Cerca" OnClick="cmdCerca_Click" />
						<asp:Button runat="server" ID="cmdAnnullaRicerca" Text="Annulla" OnClick="cmdAnnullaRicerca_Click" />
					</div>

					<asp:Repeater runat="server" ID="rptUtenze" OnItemDataBound="rptUtenze_ItemDataBound">
						<ItemTemplate>
							<uc1:GestioneUtenzeTares_DettagliUtenza ID="dettagliUtenzeCtrl" runat="server" OnUtenzaSelezionata="OnUtenzaSelezionata" OnErrore="OnErroreSelezione" />							
						</ItemTemplate>					
					</asp:Repeater>					
				</fieldset>
			</asp:View>
			<asp:View runat="server" ID="dettaglioView">
				<fieldset>
					<div class="descrizioneSezione">
						<asp:Literal runat="server" ID="ltrTestoDettaglio" />
					</div>
					<uc1:GestioneUtenzeTares_DettagliUtenza ID="dettagliUtenza" runat="server" MostraBottoneSeleziona="false" />	

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdSelzionaAltraUtenza" Text="Seleziona un'utenza differente" OnClick="cmdSelzionaAltraUtenza_Click" />
					</div>
				</fieldset>
			</asp:View>
		</asp:MultiView>
	</div>
</asp:Content>
