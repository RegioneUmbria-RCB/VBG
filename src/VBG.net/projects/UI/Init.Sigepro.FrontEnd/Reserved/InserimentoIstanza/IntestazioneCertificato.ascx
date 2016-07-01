<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="IntestazioneCertificato.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.IntestazioneCertificato" %>

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
			<asp:Label ID="Label15" runat="server" AssociatedControlID="lblStatoPratica">Stato</asp:Label>
			<asp:Label runat="server" ID="lblStatoPratica" />
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
			<asp:Label ID="Label1" runat="server" AssociatedControlID="lblIntervento">Localizzazione Intervento</asp:Label>
			<asp:Label runat="server" ID="lblLocalizzazione" />
		</div>
		
		<asp:Panel runat="server" ID="pnlDatiCatastali">
		<legend>Dati catastali</legend>
		<div>
			<asp:GridView BorderWidth="1px" BorderColor="#000" runat="server" ID="dgDatiCatastali" AutoGenerateColumns="false" Width="100%">

				<Columns>
					<asp:BoundField DataField="TipoCatasto" HeaderText="Tipo Catasto" ItemStyle-BorderWidth="1px" ItemStyle-BorderColor="#000" HeaderStyle-BorderWidth="1px" HeaderStyle-BorderColor="#000" />
					<asp:BoundField DataField="Foglio" HeaderText="Foglio" ItemStyle-BorderWidth="1px" ItemStyle-BorderColor="#000" HeaderStyle-BorderWidth="1px" HeaderStyle-BorderColor="#000"  />
					<asp:BoundField DataField="Particella" HeaderText="Particella" ItemStyle-BorderWidth="1px" ItemStyle-BorderColor="#000" HeaderStyle-BorderWidth="1px" HeaderStyle-BorderColor="#000"  />
					<asp:BoundField DataField="Sub" HeaderText="Subalterno" ItemStyle-BorderWidth="1px" ItemStyle-BorderColor="#000"  HeaderStyle-BorderWidth="1px" HeaderStyle-BorderColor="#000" />
				</Columns>

			</asp:GridView>
		</div>
		</asp:Panel>
	</fieldset>
</div>