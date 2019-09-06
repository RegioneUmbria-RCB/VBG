<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="AlberelloEndoControl.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Public.AlberelloEndoControl" %>
<asp:Repeater runat="server" ID="rptFamiglieEndo">
	<HeaderTemplate>
		<div>
			<h3>
				<a href="#"><%= Titolo %></a></h3>
			<div id="alberoEndo" class="alberoEndo">
				<ul>
	</HeaderTemplate>
	<ItemTemplate>
		<li class="famigliaEndo">
			<asp:Literal runat="server" ID="ltrFamigliaEndo" Text='<%# Eval("Descrizione") %>' />
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
											<%# Eval("LinkDettagli")%>
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