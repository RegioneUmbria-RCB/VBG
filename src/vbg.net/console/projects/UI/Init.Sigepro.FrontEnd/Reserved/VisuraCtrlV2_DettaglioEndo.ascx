<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="VisuraCtrlV2_DettaglioEndo.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraCtrlV2_DettaglioEndo" %>
<div class="visura-dettagli-endoprocedimento">
	<asp:Literal runat="server" ID="ltrProcedimento" Text='<%# DataBinder.Eval( DataSource, "descrizione")%>' />

    <asp:Panel runat="server" Visible='<%# DataBinder.Eval( DataSource, "ContieneAllegati") %>' CssClass="pannello-allegati">
       </asp:Panel>

	<asp:Panel runat="server" Visible='<%# DataBinder.Eval( DataSource, "ContieneAllegati") %>' CssClass="pannello-allegati">
		<div class="titolo">Allegati:</div>
		<asp:Repeater runat="server" ID="rptAllegati" DataSource='<%# DataSource.documenti %>' OnItemDataBound="rptAllegati_ItemDataBound">
			<HeaderTemplate>
				<ul>
			</HeaderTemplate>
			
			<ItemTemplate>
				<li>
					<asp:HyperLink runat="server" ID="hlDownloadAllegato" Text='<%#DataBinder.Eval( Container.DataItem , "DOCUMENTO" ) %>' Target="_blank" />
					<div class="md5-allegato">
						[<asp:Literal runat="server" ID="ltrNomeFile" Text='<%# DataBinder.Eval( Container.DataItem , "allegati.allegato" )%>' />
						<asp:Literal runat="server" ID="ltrMd5" Text='<%# DataBinder.Eval( Container.DataItem , "allegati.Md5", "- MD5: {0}" )%>'
							Visible='<%# Eval("allegati.HasMd5")%>' />]
					</div>
                    <div class="note-allegato">
                        <asp:Literal runat="server" ID="Literal4" Text='<%# Eval( "annotazioni" , "Note: {0}")%>' Visible='<%# Eval("HasAnnotazioni")%>' />
                    </div>
				</li>
			</ItemTemplate>
			
			<FooterTemplate>
				</ul>
			</FooterTemplate>
		</asp:Repeater>
	</asp:Panel>
</div>
