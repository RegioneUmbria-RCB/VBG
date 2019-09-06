<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Codebehind="IstanzeDyn2Storico.aspx.cs" Inherits="Sigepro.net.Istanze.DatiDinamici.Storico.IstanzeDyn2Storico"
	Title="Storico schede" %>

<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="init" Namespace="Init.SIGePro.DatiDinamici.WebControls" Assembly="SIGePro.DatiDinamici" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<div class="Informazioni">
	
		<div>
			<span class="Etichetta">Istanza</span>
			<span class="Valore"><asp:Literal runat="server" ID="ltrCodiceIstanza"/></span>
		</div>
		<div>
			<span class="Etichetta">Modello</span>
			<span class="Valore"><asp:Literal runat="server" ID="ltrNomeModello"/></span>
		</div>
	</div>

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<fieldset>
				<div>
					<asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="IdVersione" OnSelectedIndexChanged="gvLista_SelectedIndexChanged">
						<AlternatingRowStyle CssClass="RigaAlternata" />
						<RowStyle CssClass="Riga" />
						<HeaderStyle CssClass="IntestazioneTabella" />
						<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
						<Columns>
							<asp:TemplateField HeaderText="Versione">
								<ItemTemplate>
									<asp:LinkButton runat="server" ID="lbSelect" CommandName="Select" Text='<%# Bind("IdVersione") %>' />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:BoundField DataField="DataVersione" HeaderText="Data" HtmlEncode="false" DataFormatString="{0:dd/MM/yyyy hh:mm}" />
							<asp:TemplateField>
								<ItemTemplate>
									<asp:CheckBox runat="server" ID="chkSelezionato" />
								</ItemTemplate>
							</asp:TemplateField>
						</Columns>
						<EmptyDataTemplate>
							<asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
						</EmptyDataTemplate>
					</asp:GridView>
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClientClick="self.close();" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClientClick="return confirm('Eliminare le righe selezionate?')" OnClick="cmdElimina_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<asp:Repeater runat="server" ID="rptMolteplicita">
					<HeaderTemplate>
						<table>
							<tr>
								<td>
									<div>
										<asp:Label runat="Server" ID="lblTitolo" AssociatedControlID="lblTitolo"><b>Codice scheda:&nbsp;</b></asp:Label>
					</HeaderTemplate>
					<ItemTemplate>
						<asp:LinkButton runat="server" ID="lnkApriIndice" CommandArgument='<%# DataBinder.Eval( Container , "DataItem" ) %>' Text='<%# TestoIndice( DataBinder.Eval( Container , "DataItem" ) ) %>'
							OnClick="CambiaIndice" Visible='<%# !IndiceCorrente( DataBinder.Eval( Container , "DataItem" ) ) %>' />
						<b>
							<asp:Literal runat="server" ID="ltrIndice" Text='<%# TestoIndice( DataBinder.Eval( Container , "DataItem" )) %>' Visible='<%# IndiceCorrente( DataBinder.Eval( Container , "DataItem" ) ) %>' /></b>
					</ItemTemplate>
					<FooterTemplate>
									</div> 
								</td>
							</tr>
						</table>
					</FooterTemplate>
				</asp:Repeater>
				<init:ModelloDinamicoRenderer ID="ModelloDinamicoRenderer1" runat="server" ReadOnly="true" />
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>
