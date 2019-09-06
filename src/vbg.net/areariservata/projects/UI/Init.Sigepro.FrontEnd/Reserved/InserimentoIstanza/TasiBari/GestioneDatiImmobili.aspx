<%@ Page Title="Gestione immobili TASI" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneDatiImmobili.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TasiBari.GestioneDatiImmobili" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register src="~/Reserved/InserimentoIstanza/TasiBari/GestioneDatiImmobili_Dettagli.ascx" tagname="GestioneDatiImmobili_Dettagli" tagprefix="uc1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
<div class="inputForm">
		<asp:MultiView runat="server" ID="multiView">
			<asp:View runat="server" ID="ricercaView">
				<fieldset>
					<div class="descrizioneSezione">
						<asp:Literal runat="server" ID="ltrTestoRicerca" />
					</div>
					<div>
						<label>Codice fiscale o partita iva contribuente</label>
						<asp:TextBox runat="server" ID="txtCfUtenza" Columns="20" MaxLength="16" />
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdCerca" Text="Cerca" OnClick="cmdCerca_Click" />
						<asp:Button runat="server" ID="cmdAnnullaRicerca" Text="Annulla" OnClick="cmdAnnullaRicerca_Click" />
					</div>

					<uc1:GestioneDatiImmobili_Dettagli ID="datiImmobiliCercati" runat="server" OnImmobiliSelezionati="OnImmobiliSelezionati" OnValidazioneDatiSelezionati="OnValidazioneDatiSelezionati" />							
				</fieldset>
			</asp:View>
			<asp:View runat="server" ID="dettaglioView">
				<fieldset>
					<div class="descrizioneSezione">
						<asp:Literal runat="server" ID="ltrTestoDettaglio" />
					</div>
					<uc1:GestioneDatiImmobili_Dettagli ID="datiImmobili" runat="server" PermettiSelezione="false" />	

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdSelzionaAltraUtenza" Text="Seleziona un altro contribuente" OnClick="cmdSelzionaAltraUtenza_Click" />
					</div>
				</fieldset>
			</asp:View>
		</asp:MultiView>
	</div>


</asp:Content>
