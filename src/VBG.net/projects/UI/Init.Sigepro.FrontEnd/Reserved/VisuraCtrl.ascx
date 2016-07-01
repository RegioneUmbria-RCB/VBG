<%@ Control Language="C#" AutoEventWireup="true" Codebehind="VisuraCtrl.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraCtrl" %>
<div class="inputForm">
	<fieldset>
		<div>
			<asp:Literal ID="ltrIntestazioneDettaglio" runat="server"></asp:Literal>
		</div>
	</fieldset>	
	<fieldset>
		
		<legend>Dettagli dell'istanza</legend>
		<div>
			<asp:Label ID="Label17" runat="server" AssociatedControlID="lblNumeroPratica">N.Pratica</asp:Label>
			<asp:Label runat="server" ID="lblNumeroPratica" />
		</div>
		<div>
			<asp:Label ID="Label14" runat="server" AssociatedControlID="lblDataPresentazione">Data invio</asp:Label>
			<asp:Label runat="server" ID="lblDataPresentazione" />
		</div>
		<div>
			<asp:Label ID="Label12" runat="server" AssociatedControlID="lblOggetto">Oggetto</asp:Label>
			<asp:Label runat="server" ID="lblOggetto" />
		</div>
		<div>
			<asp:Label ID="Label13" runat="server" AssociatedControlID="lblIntervento">Descrizione Intervento</asp:Label>
			<asp:Label runat="server" ID="lblIntervento" />
		</div>
		<div>
			<asp:Label ID="Label15" runat="server" AssociatedControlID="lblStatoPratica">Stato</asp:Label>
			<asp:Label runat="server" ID="lblStatoPratica" />
		</div>
		<div>
			<asp:Label ID="Label16" runat="server" AssociatedControlID="lblProtocollo">Numero protocollo</asp:Label>
			<asp:Label runat="server" ID="lblProtocollo" />
		</div>
		<div>
			<asp:Label ID="Folabel9" runat="server" AssociatedControlID="lblDataProtocollo">Data protocollo</asp:Label>
			<asp:Label runat="server" ID="lblDataProtocollo" />
		</div>
		<legend>Riferimenti</legend>
		<div>
			<asp:Label ID="Label1" runat="server" AssociatedControlID="lblResponsabileProc">Responsabile procedimento</asp:Label>
			<asp:Label runat="server" ID="lblResponsabileProc" />
		</div>
		
		<div>
			<asp:Label ID="Label2" runat="server" AssociatedControlID="lblIstruttore">Istruttore</asp:Label>
			<asp:Label runat="server" ID="lblIstruttore" />
		</div>
		
		<div>
			<asp:Label ID="Label3" runat="server" AssociatedControlID="lblOperatore">Operatore</asp:Label>
			<asp:Label runat="server" ID="lblOperatore" />
		</div>
		
		<legend>Soggetti</legend>
		<div>
			<asp:GridView runat="server" ID="dgSoggetti" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="Nominativo" HeaderText="Nominativo" />
					<asp:BoundField DataField="Indirizzo" HeaderText="Indirizzo" />
					<asp:BoundField DataField="Localita" HeaderText="Localit&agrave;" />
					<asp:BoundField DataField="Citta" HeaderText="Città" />
					<asp:BoundField DataField="Cap" HeaderText="Cap" />
					<asp:BoundField DataField="Provincia" HeaderText="Provincia" />
					<asp:BoundField DataField="CodiceFiscale" HeaderText="Codice Fiscale" />
					<asp:BoundField DataField="PartitaIva" HeaderText="Partita Iva" />
					<asp:BoundField DataField="TipoRapporto" HeaderText="Tipo Rapporto" />
				</Columns>
			</asp:GridView>
		</div>
		<legend>Localizzazioni</legend>
		<div>
			<asp:GridView runat="server" ID="dgLocalizzazioni" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="Indirizzo" HeaderText="Indirizzo" />
					<asp:BoundField DataField="Civico" HeaderText="Civico" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non sono presenti localizzazioni</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Dati catastali</legend>
		<div>
			<asp:GridView runat="server" ID="dgDatiCatastali" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="TipoCatasto" HeaderText="Tipo Catasto" />
					<asp:BoundField DataField="Foglio" HeaderText="Foglio" />
					<asp:BoundField DataField="Particella" HeaderText="Particella" />
					<asp:BoundField DataField="Sub" HeaderText="Subalterno" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non sono presenti dati catastali</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Procedimenti</legend>
		<div>
			<asp:GridView runat="server" ID="dgProcedimenti" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="Procedimento" HeaderText="Procedimento" />
					<asp:BoundField DataField="Amministrazione" HeaderText="Amministrazione" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non ci sono procedimenti attivati</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Movimenti</legend>
		<div>
			<asp:GridView runat="server" ID="dgMovimenti" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="Movimento" HeaderText="Movimento" />
					<asp:BoundField DataField="Data" HeaderText="Data" ItemStyle-HorizontalAlign="Center" />
					<asp:BoundField DataField="Parere" HeaderText="Parere" />
					<asp:TemplateField>
						<HeaderTemplate>
							<asp:Label ID="asdasd" runat="server">Esito</asp:Label>
						</HeaderTemplate>
						<ItemTemplate>
							<asp:Label ID="Folabel2" runat="server" Text='<%# EsitoMovimento(DataBinder.Eval(Container.DataItem,"Esito") )%>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:BoundField DataField="Note" HeaderText="Note" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non ci sono movimenti attivati</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Autorizzazioni</legend>
		<div>
			<asp:GridView runat="server" ID="dgAutorizzazioni" AutoGenerateColumns="false" Width="100%">

				<Columns>
					<asp:BoundField DataField="DataRilascio" HeaderText="Data Rilascio" />
					<asp:BoundField DataField="Tipologia" HeaderText="Tipologia" />
					<asp:BoundField DataField="Note" HeaderText="Note" />
					<asp:BoundField DataField="Numero" HeaderText="Numero" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non sono presenti autorizzazioni</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Oneri</legend>
		<div>
			<asp:GridView runat="server" ID="dgOneri" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="Onere" HeaderText="Onere" />
					<asp:BoundField DataField="Importo" HeaderText="Importo" ItemStyle-HorizontalAlign="Right" />
					<asp:BoundField DataField="DataScadenza" HeaderText="Data Scadenza" ItemStyle-HorizontalAlign="Center" />
					<asp:BoundField DataField="DataPagamento" HeaderText="Data Pagamento" ItemStyle-HorizontalAlign="Center" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non ci sono oneri per la pratica</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Altri dati</legend>
		<div>
			<asp:GridView runat="server" ID="dgAltridati" AutoGenerateColumns="false" Width="100%">
				<Columns>
					<asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
					<asp:BoundField DataField="Valore" HeaderText="Valore" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non ci sono altri dati disponibili</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<legend>Scadenze</legend>
		<div>
			<asp:GridView runat="server" ID="dgScadenze" DataKeyNames="CodiceScadenza" AutoGenerateColumns="false" Width="100%" OnSelectedIndexChanged="dgScadenze_SelectedIndexChanged"> 
				<Columns>
					<asp:BoundField DataField="DatiMovimento" HeaderText="Movimento precedente"></asp:BoundField>
					<asp:BoundField DataField="DescrMovimentoDaFare" HeaderText="Movimento da fare"></asp:BoundField>
					<asp:BoundField DataField="DataScadenza" HeaderText="Scadenza"></asp:BoundField>
					<asp:BoundField DataField="Procedura" HeaderText="Procedura"></asp:BoundField>
					<asp:ButtonField Text="Effettua movimento" CommandName="Select"></asp:ButtonField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server">Non ci sono scadenze</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
</div>
