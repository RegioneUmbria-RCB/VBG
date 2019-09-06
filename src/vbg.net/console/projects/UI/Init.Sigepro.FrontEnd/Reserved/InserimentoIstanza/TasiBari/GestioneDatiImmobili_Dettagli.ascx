<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneDatiImmobili_Dettagli.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TasiBari.GestioneDatiImmobili_Dettagli" %>
<div class="dettagliUtenzaTares">
	<div class="datiAnagrafici">
		<fieldset>
			<div class="titolo">Dati Anagrafici</div>
			<div>
				<asp:Literal runat="server" ID="ltrIdentificativoUtenza" /><br />
				<asp:Literal runat="server" ID="ltrNominativo" /><br />
				<asp:Literal runat="server" ID="ltrCodiceFiscale" /><br />
				<asp:Literal runat="server" ID="ltrDatiNascita" />
			</div>
			<div  class="titolo">
				<asp:Literal runat="server" ID="ltrLabelIndirizzoResidenza" />
			</div>
			<div>
				<asp:Literal runat="server" ID="ltrIndirizzoResidenza" />				
			</div>
		</fieldset>

		<asp:Repeater runat="server" ID="rptDatiImmobili">
			<HeaderTemplate>
				<table class="riferimentiUtenza">
					<tr>
						<th>Id</th>
						<th>Indirizzo</th>
						<th>Percentuale</th>
						<th>Tipo</th>
						<th>&nbsp;</th>
					</tr>
			</HeaderTemplate>
			<ItemTemplate>
				<tr>
					<td>
						<asp:Literal runat="server" ID="ltrIdImmobile" Text='<%# Eval("IdImmobile") %>'/>
					</td>
					<td>
						<asp:Label runat="server" ID="ltrIndirizzo" Text='<%# Eval("Ubicazione") %>' cssclass="indirizzo"/><br />
						<asp:Label runat="server" ID="ltrDatiCatastali" Text='<%# Eval("RiferimentiCatastali") %>' cssclass="datiCatastali"/>
					</td>
					<td>
						<asp:Literal runat="server" ID="Literal1" Text='<%# Eval("PercentualePossesso") %>'/>
					</td>
					<td>
						<asp:Literal runat="server" ID="Literal2" Text='<%# Eval("TipoImmobile") %>'/>
					</td>
					<td>
						<asp:CheckBox runat="server" ID="chkSeleziona" Visible='<%# Eval("PermettiSelezione") %>' />
					</td>

				</tr>
			</ItemTemplate>		
			<FooterTemplate>
				</table>
			</FooterTemplate>
		</asp:Repeater>

		<div class="bottoni">
			<asp:Button runat="server" ID="cmdConfermaSelezione" Visible='<%# (bool)PermettiSelezione %>' OnClick="cmdConfermaSelezione_Click" Text="Conferma la selezione" />
		</div>
	</div>
</div>