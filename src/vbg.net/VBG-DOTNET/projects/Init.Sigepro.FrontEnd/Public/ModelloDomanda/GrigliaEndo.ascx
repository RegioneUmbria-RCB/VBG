<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GrigliaEndo.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Public.ModelloDomanda.GrigliaEndo" %>
<asp:Repeater runat="server" ID="rptEndoprocedimenti" Visible="false">
	<HeaderTemplate>
		<div>
			<h3><asp:Literal runat="server" ID="ltrTitoloGriglia" Text='<%#DescrizioneGriglia %>' /></h3>
			<div id="alberoEndo" class="alberoEndo">
				<ul>
	</HeaderTemplate>
	<ItemTemplate>
		<li class="famigliaEndo">
			<div class="persist-header">
				<asp:Literal runat="server" ID="ltrFamigliaEndo" Text='<%# Eval("Descrizione") %>' />
			</div>
			<asp:Repeater runat="server" ID="rptTipiEndo" DataSource='<%# Eval("TipiEndoprocedimenti") %>'>
				<HeaderTemplate>
					<ul>
				</HeaderTemplate>
				<ItemTemplate>
					<li class="tipoEndo">
						<asp:Literal runat="server" ID="ltrTipoEndo" Text='<%# Eval("Descrizione") %>' />
						<asp:Repeater runat="server" ID="rptEndo" DataSource='<%# GetEndoBindingSource(Eval("Endoprocedimenti")) %>'>
							<HeaderTemplate>
								<ul>
							</HeaderTemplate>
							<ItemTemplate>
								<li class="endo">
									<dl>
										<dd>
											<asp:HiddenField runat="server" ID="hidIdendo" Value='<%# Eval("Id")%>' />
											<asp:CheckBox runat="server" ID="chkSelezionato" Text='<%# Eval("Descrizione")%>' />
										</dd>
										<asp:Literal runat="server" ID="ltrAmministrazione" Visible='<%# Eval("AmministrazionePresente") %>'
											Text='<%# Eval("Amministrazione", "<dt>Amministrazione</dt><dd>{0}</dd>") %>' />
									</dl>
								</li>
							</ItemTemplate>
							<FooterTemplate>
								</ul>
							</FooterTemplate>
						</asp:Repeater>
					</li>
				</ItemTemplate>
				<FooterTemplate>
					</ul>
				</FooterTemplate>
			</asp:Repeater>
		</li>
	</ItemTemplate>
	<FooterTemplate>
		</ul> </div> </div>
	</FooterTemplate>
</asp:Repeater>
