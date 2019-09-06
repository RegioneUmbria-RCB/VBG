<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneUtenzeTares_DettagliUtenza.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneUtenzeTares_DettagliUtenza" %>
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
			<div  class="titolo">Residente in</div>
			<div>
				<asp:Literal runat="server" ID="ltrIndirizzoResidenza" />				
			</div>
		</fieldset>
	</div>


	<div class="utenze">
		<asp:Repeater runat="server" ID="rptIndirizziUtenze" OnItemDataBound="OnItemDataBound">
			<HeaderTemplate>
				<table class="riferimentiUtenza">
					<tr>
						<th>Utenza</th>
						<th>Indirizzo</th>
						<th>Superficie</th>
						<th>Tipo utenza</th>
					</tr>
			</HeaderTemplate>
			<ItemTemplate>
				<tr>
					<td><asp:Literal runat="server" ID="Literal1" Text='<%# Eval("IdentificativoUtenza") %>'/></td>
					<td>
						<asp:Label runat="server" ID="ltrIndirizzo" Text='<%# Eval("DatiIndirizzo") %>' cssclass="indirizzo"/><br />
						<asp:Label runat="server" ID="ltrDatiCatastali" Text='<%# Eval("DatiCatastali") %>' cssclass="datiCatastali"/>
						</td>
					<td><asp:Literal runat="server" ID="ltrSuperficie" Text='<%# Eval("Superficie") %>'/></td>
					<%if (MostraBottoneSeleziona){%>
						<td>
							<asp:HiddenField runat="server" ID="hidIdUtenza" Value='<%# Eval("IdentificativoUtenza") %>' />
							<asp:DropDownList runat="server" ID="ddlTipoUtenza" DataTextField="Value" DataValueField="Key" /> 
						</td>
					<%}else{ %>
						<td><asp:Literal runat="server" ID="ltrTipoUtenza" Text='<%# DecodificaEnum(Eval("TipoUtenza")) %>'/> </td>
					<%} %>
				</tr>
			</ItemTemplate>		
			<FooterTemplate>
				</table>
			</FooterTemplate>
		</asp:Repeater>
	</div>

	<asp:Label runat="server" ID="ltrMessagigoErrore" Visible="false" CssClass="errori" />

	<%if (MostraBottoneSeleziona){%>
		<div class="bottoni">
			<asp:Button runat="server" ID="cmdSeleziona" OnClick="cmdSeleziona_Click" Text="Seleziona" />
		</div>
	<%} %>
</div>