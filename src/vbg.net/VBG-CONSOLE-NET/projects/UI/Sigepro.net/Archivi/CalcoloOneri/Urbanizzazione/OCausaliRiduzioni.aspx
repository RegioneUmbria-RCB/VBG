<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Codebehind="OCausaliRiduzioni.aspx.cs" Inherits="Sigepro.net.Archivi.CalcoloOneri.Urbanizzazione.OCausaliRiduzioni"
	Title="Causali di riduzione/incremento" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
				<init:LabeledTextBox ID="txtSrcDescrizione" runat="server" Descrizione="Tipologia di riduzione" Item-Columns="50" Item-MaxLength="50" />
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCerca" Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click" />
					<init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="listaView">
			<fieldset>
				<div>
					<init:GridViewEx  runat="server" ID="gvLista" AutoGenerateColumns="False" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DatabindOnFirstLoad="False"
						DataSourceID="OCausaliRiduzioniDataSource" DefaultSortDirection="Ascending" DefaultSortExpression="Id" DataKeyNames="Id">
						<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
						<Columns>
							<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" />
							<asp:BoundField DataField="Descrizione" HeaderText="Descrizione"></asp:BoundField>
						</Columns>
						<RowStyle CssClass="Riga" />
						<EmptyDataTemplate>
							<asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
						</EmptyDataTemplate>
						<HeaderStyle CssClass="IntestazioneTabella" />
						<AlternatingRowStyle CssClass="RigaAlternata" />
					</init:GridViewEx>
					<asp:ObjectDataSource ID="OCausaliRiduzioniDataSource" SelectMethod="Find" TypeName="Init.SIGePro.Manager.OCausaliRiduzioniTMgr" SortParameterName="sortExpression"
						runat="server">
						<SelectParameters>
							<asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
							<asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
							<asp:ControlParameter ControlID="txtSrcDescrizione" Name="descrizione" PropertyName="Value" Type="String" />
						</SelectParameters>
					</asp:ObjectDataSource>
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<init:LabeledLabel runat="server" ID="lblId" Descrizione="Codice" />
				<init:LabeledTextBox ID="txtDescrizione" runat="server" Descrizione="Tipologia di riduzione" HelpControl="hdDescrizione" Item-Columns="50" Item-MaxLength="50" />
				<init:HelpDiv ID="hdDescrizione" runat="server">Ad una tipologia vengono associate delle causali di riduzione/incremento che potranno essere utilizzate nei calcoli del contributo di urbalizzazione</init:HelpDiv>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Ok" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
			<asp:Panel runat="server" ID="pnlDettagli">
				<br />
				<br />
				<fieldset>
					<legend>Causali di riduzione/incremento</legend>
				</fieldset>
				<asp:DataGrid runat="server" Width="800px" ID="dgDettagli" ShowFooter="true" AutoGenerateColumns="false" DataKeyField="Id" OnItemCommand="dgDettagli_ItemCommand"
					OnDeleteCommand="dgDettagli_DeleteCommand" OnEditCommand="dgDettagli_EditCommand" OnUpdateCommand="dgDettagli_UpdateCommand" OnCancelCommand="dgDettagli_CancelCommand" OnItemDataBound="dgDettagli_ItemDataBound">
					<ItemStyle CssClass="Riga" />
					<FooterStyle CssClass="RigaSelezionata" />
					<EditItemStyle CssClass="RigaSelezionata" />
					<HeaderStyle CssClass="IntestazioneTabella" />
					<Columns>
						<asp:TemplateColumn HeaderText="Causale">
							<ItemTemplate>
								<asp:Label ID="Label1" runat="server" Text='<%# Bind("Descrizione") %>'></asp:Label>
							</ItemTemplate>

							<EditItemTemplate>
								<asp:TextBox runat="server" ID="txtDescrizione" Columns="40" MaxLength="50" Text='<%# Bind("Descrizione") %>'></asp:TextBox>
							</EditItemTemplate>
							
							<FooterTemplate>
								<asp:TextBox runat="server" ID="txtNewDescrizione" Columns="40" MaxLength="50"></asp:TextBox>
							</FooterTemplate>
						</asp:TemplateColumn>
						<asp:TemplateColumn HeaderText="Tipologia" ItemStyle-HorizontalAlign="Center" FooterStyle-HorizontalAlign="center">
							<ItemTemplate>
								<asp:Label ID="Label2" runat="server" Text='<%# Convert.ToSingle( DataBinder.Eval( Container.DataItem , "Riduzioneperc" ) ) >= 0 ? "<span style=\"color:green\">Incremento</span>" : "<span style=\"color:red\">Riduzione</span>" %>'></asp:Label>
							</ItemTemplate>

							<EditItemTemplate>
								<asp:DropDownList runat="server" ID="ddlTipologia" SelectedValue='<%# Convert.ToSingle( DataBinder.Eval( Container.DataItem , "Riduzioneperc" ) ) >= 0 ? "+1" : "-1" %>'>
									<asp:ListItem Text="Incremento" Value="+1" ></asp:ListItem>
									<asp:ListItem Text="Riduzione" Value="-1" ></asp:ListItem>
								</asp:DropDownList>
							</EditItemTemplate>

							<FooterTemplate>
								<asp:DropDownList runat="server" ID="ddlNewTipologia">
									<asp:ListItem Text="Incremento" Value="+1"></asp:ListItem>
									<asp:ListItem Text="Riduzione" Value="-1"></asp:ListItem>
								</asp:DropDownList>
							</FooterTemplate>
						</asp:TemplateColumn>
						<asp:TemplateColumn HeaderText="Importo (%)" ItemStyle-HorizontalAlign="Right" FooterStyle-HorizontalAlign="Right">
							<ItemTemplate>
								<asp:Label ID="Label3" runat="server" Text='<%# DataBinder.Eval(Container.DataItem,"Riduzioneperc" , "{0:N2}") %>'></asp:Label>
							</ItemTemplate>
							
							<EditItemTemplate>
								<init:FloatTextBox Columns="6" MaxLength="6" runat="server" ID="ftxtImporto" ValoreFloat='<%# Math.Abs(Convert.ToSingle(DataBinder.Eval(Container.DataItem,"Riduzioneperc" ))) %>'></init:FloatTextBox>
							</EditItemTemplate>

							
							<FooterTemplate>
								<init:FloatTextBox Columns="6" MaxLength="6" runat="server" ID="ftxtNewImporto" ValoreFloat="0"></init:FloatTextBox>
							</FooterTemplate>
						</asp:TemplateColumn>
						<asp:TemplateColumn>
							<ItemTemplate>
								<asp:ImageButton ID="ImageButton2" runat="server" CausesValidation="False" CommandName="Edit" ImageUrl="~/Images/edit.gif" AlternateText="Modifica" />
							</ItemTemplate>
							<EditItemTemplate>
								<asp:ImageButton ID="ImageButton4" runat="server" CausesValidation="False" CommandName="Update" ImageUrl="~/Images/salva.gif" AlternateText="Aggiorna" />
								<asp:ImageButton ID="ImageButton1" runat="server" CausesValidation="False" CommandName="Cancel" ImageUrl="~/Images/cancel.gif" AlternateText="Annulla" />
								<asp:ImageButton ID="imgElimina" runat="server" CausesValidation="False" CommandName="Delete" ImageUrl="~/Images/cestino.gif" AlternateText="Elimina" />
							</EditItemTemplate>
							<FooterTemplate>
								<asp:ImageButton ID="ImageButton1" runat="server" CausesValidation="False" CommandName="Inserisci" ImageUrl="~/Images/Add.gif" AlternateText="Inserisci" />
							</FooterTemplate>
						</asp:TemplateColumn>
					</Columns>
				</asp:DataGrid>
			</asp:Panel>
		</asp:View>
	</asp:MultiView>
</asp:Content>
