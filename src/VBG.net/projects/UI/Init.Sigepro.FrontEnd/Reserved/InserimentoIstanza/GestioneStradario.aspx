<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" Codebehind="GestioneStradario.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneStradario" Title="Untitled Page" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
<%--	<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>--%>

	
	<div class="inputForm">
		<fieldset>
			<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
				<asp:View runat="server" ID="listaView">
						<asp:GridView ID="dgStradario" Width="100%" 
										AutoGenerateColumns="False" 
										DataKeyNames="Id" 
										OnRowDeleting="dgStradario_DeleteCommand" 
										GridLines="None"
										runat="server">
							<Columns>
								<asp:BoundField DataField="Indirizzo" ReadOnly="True" HeaderText="Indirizzo">
									<HeaderStyle Width="40%"></HeaderStyle>
								</asp:BoundField>
								<asp:BoundField DataField="Civico"  ItemStyle-Width="5%" />
								<asp:BoundField DataField="Esponente"  ItemStyle-Width="5%" />
								<asp:BoundField DataField="Colore" ItemStyle-Width="5%"  />
								<asp:BoundField DataField="Scala"  ItemStyle-Width="5%"  />
								<asp:BoundField DataField="Piano"  ItemStyle-Width="5%" />
								<asp:BoundField DataField="Interno" ItemStyle-Width="5%"  />					
								<asp:BoundField DataField="EsponenteInterno"  ItemStyle-Width="5%" />							
								<asp:BoundField DataField="Fabbricato" ItemStyle-Width="5%"  />					
								<asp:BoundField DataField="Km"  ItemStyle-Width="5%"  />	
								<asp:BoundField DataField="Note" ReadOnly="True" HeaderText="Note" ItemStyle-Width="20%"  />

								<asp:TemplateField HeaderStyle-Width="70px" ItemStyle-HorizontalAlign="Right">
									<ItemTemplate>
										<asp:LinkButton runat="server" ID="lnk1" CommandName="Delete" Text="Rimuovi" OnClientClick="return confirm('Proseguire con l\'eliminazione?')"></asp:LinkButton>
									</ItemTemplate>
								</asp:TemplateField>
							</Columns>
						</asp:GridView>

					<div class="bottoni">
						<asp:Button runat="server" ID="cmdAddNew" Text="Aggiungi" OnClick="cmdAddNew_Click" /></div>
				</asp:View>
				<asp:View runat="server" ID="dettaglioView">
					<script type="text/javascript">

						require(['app/autocompleteStradario'], function (AutocompleteStradario) {
							AutocompleteStradario({
								idCampoIndirizzo : '<%= Indirizzo.ClientID %>',
								idCampoCodiceStradario : '<%= hiddenCodiceStradario.ClientID%>',
								serviceUrl: '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario',
								idComune: '<%=IdComune %>',
								codiceComune: '<%=CodiceComune %>'
							});
						});
					</script>
					
					<asp:GridView runat="server" ID="dgIndirizzi" 
								GridLines="None" 
								AutoGenerateColumns="False" 
								DataKeyNames="CODICESTRADARIO" 
								OnSelectedIndexChanged="dgIndirizzi_SelectedIndexChanged" >
						<Columns>
							<asp:TemplateField HeaderText="Via">
								<ItemTemplate>
									<asp:Literal runat="server" ID="ltrDescrizione" Text='<%# Bind("NomeVia") %>' />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:ButtonField CommandName="Select" Text="Seleziona" />
						</Columns>
					</asp:GridView>

					<div>
						<label>&nbsp;</label>
						<span><i>I campi contrassegnati con * sono obbligatori</i></span>
					</div>

					<div>
						<asp:HiddenField runat="server" ID="hiddenCodiceStradario" />
						<asp:Label runat="server" ID="lbl1" AssociatedControlID="Indirizzo" Text="Indirizzo *" />
						<asp:TextBox ID="Indirizzo" runat="server" MaxLength="100" Columns="60" />
						<span id="errMsgOuput"></span>
					</div>

					<cc2:LabeledTextBox runat="server" ID="txtCivico" Descrizione="Civico" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtEsponente" Descrizione="Esponente" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledDropDownList runat="server" ID="ddlColore" Descrizione="Colore" Item-DataTextField="COLORE" Item-DataValueField="CODICECOLORE" />
					<cc2:LabeledTextBox runat="server" ID="txtScala" Descrizione="Scala" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtPiano" Descrizione="Piano" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtInterno" Descrizione="Interno" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtEsponenteInterno" Descrizione="Esponente interno" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtFabbricato" Descrizione="Fabbricato" Item-MaxLength="10" Item-Columns="9" />
					<cc2:LabeledTextBox runat="server" ID="txtKm" Descrizione="Km" Item-MaxLength="10" Item-Columns="9" />
					
					<div>
						<asp:Label runat="server" ID="Label2" AssociatedControlID="Note" Text="Note" />
						<asp:TextBox ID="Note" runat="server" MaxLength="80" Columns="60" Rows="4" TextMode="MultiLine" />
					</div>

					<div class="bottoni">
						<asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
						<asp:Button ID="cmdCancel" runat="server" Text="Annulla" OnClick="cmdCancel_Click" />
					</div>
				</asp:View>
			</asp:MultiView>
		</fieldset>
	</div>
</asp:Content>
