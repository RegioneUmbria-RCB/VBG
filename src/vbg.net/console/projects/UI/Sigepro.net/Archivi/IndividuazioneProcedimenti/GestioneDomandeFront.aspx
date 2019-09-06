<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Codebehind="GestioneDomandeFront.aspx.cs" Inherits="Sigepro.net.Archivi.IndividuazioneProcedimenti.GestioneDomandeFront"
	Title="Aree tematiche" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="cc1" Namespace="SIGePro.WebControls.CustomTreeView" Assembly="SIGePro.WebControls" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<style type="text/css">
		.AlberoAreeTematiche .AreaSelezionata
		{
			font-weight: bold;
		}
			
		.AlberoAreeTematiche A,.AlberoAreeTematiche A:hover,.AlberoAreeTematiche A:active,.AlberoAreeTematiche A:visited
		{
			color: #696969;
		}
	</style>
	<script type="text/javascript">
		function PopupEndo( idDomanda )
		{
			var url = '<%=ResolveClientUrl("~/Archivi/IndividuazioneProcedimenti/GestioneEndoDomanda.aspx?Token=" + Token + "&Software=" + Software) %>&Popup=true&IdDomanda=' + idDomanda;
			var arguments = "";
			var feats = "dialogHeight: 500px;dialogWidth:850px;";
			
			window.showModalDialog( url , arguments , feats );
		
			return false;
		}
	</script>

	<!--Albero dei contesti-->
	<table style="width:100%;">
		<tr style="vertical-align:top;">
			<td style="width: 400px; border-top: 1px solid #666;border-left: 1px solid #666;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;overflow-x:scroll;">
				<cc1:TreeViewDomande CssClass="AlberoAreeTematiche" runat="server" ID="tvDomande" OnItemSelected="tvDomande_ItemSelected" />
			</td>
			<td>
				<fieldset style="left: 400px;">
					<legend>Aggiungi nuova sottoarea</legend>
					<init:LabeledTextBox ID="txtDescrizioneNew" Descrizione="Descrizione" runat="server" Item-Columns="80" Item-MaxLength="80" HelpControl="hdDescrizioneNew" />
					<init:HelpDiv runat="server" ID="hdDescrizioneNew">Immettere la descrizione della nuova area tematica e fare click su "aggiungi" per aggiungerla come sottoarea dell'area tematica corrente</init:HelpDiv>
					<div class="Bottoni">
						<init:SigeproButton ID="cmdNew" IdRisorsa="AGGIUNGI" runat="server" OnClick="cmdNew_Click" />
					</div>
					<asp:MultiView runat="server" ID="multiView">
						<asp:View runat="server" ID="rootView">
							
						</asp:View>
						<asp:View runat="server" ID="detailView">
							<legend>Dettagli area tematica</legend>
							<init:LabeledLabel ID="lblId" Descrizione="Id" runat="server" />
							<init:LabeledTextBox ID="txtDescrizione" Descrizione="Descrizione" runat="server"  Item-Columns="80" Item-MaxLength="80"/>
							<init:LabeledTextBox ID="txtNote" Descrizione="Note" runat="server" Item-TextMode="MultiLine" Item-Columns="80" Item-Rows="5"/>
							<init:LabeledIntTextBox ID="txtOrdine" Descrizione="Ordine" runat="server" Item-Columns="3" Item-MaxLength="2" />
							<init:LabeledCheckBox ID="chkDisattiva" Descrizione="Disabilitata" runat="server" />
							
							<div class="Bottoni">
								<init:SigeproButton ID="cmdSave" IdRisorsa="SALVA" runat="server" OnClick="cmdSave_Click" />
								<init:SigeproButton ID="cmdDelete" IdRisorsa="ELIMINA" runat="server" OnClick="cmdDelete_Click" />
								<init:SigeproButton ID="cmdClose" IdRisorsa="CHIUDI" runat="server" OnClick="cmdClose_Click" />		
							</div>
							
							<legend>Domande dell'area</legend>
							<asp:DataGrid ShowHeader="false" Width="100%" DataKeyField="Codicedomanda" runat="server" ID="dgDomande" AutoGenerateColumns="False" OnDeleteCommand="dgDomande_DeleteCommand">
								<ItemStyle CssClass="Riga" />
								<AlternatingItemStyle CssClass="RigaAlternata" />
								<Columns>
									<asp:BoundColumn DataField="Domanda" ItemStyle-Width="90%" ></asp:BoundColumn>
									<asp:TemplateColumn ItemStyle-Width="10%">
										<ItemTemplate >
											<asp:ImageButton ID="ImageButton1" runat="server" CausesValidation="false" AlternateText="Modifica gli endoprocedimenti associati alla domanda" CommandName="Edit" ImageUrl="~/Images/edit.gif" OnClientClick='<%# DataBinder.Eval(Container.DataItem , "Codicedomanda", "return PopupEndo({0});") %>' />
											<asp:ImageButton runat="server" CausesValidation="false" AlternateText="Elimina la domanda" CommandName="Delete" CommandArgument='<%# DataBinder.Eval(Container.DataItem , "Codicedomanda")%>' ImageUrl="~/Images/cestino.gif" OnClientClick="return confirm('Proseguire con l\'eliminazione');"/>
										</ItemTemplate>
									</asp:TemplateColumn>
								</Columns>
							</asp:DataGrid>
							<legend>Aggiungi nuova domanda</legend>
							<init:LabeledTextBox ID="txtNuovaDomanda" Descrizione="Testo della domanda" runat="server" Item-TextMode="MultiLine" Item-Columns="80" Item-Rows="5" HelpControl="hdNuovaDomanda"/>
							<init:HelpDiv runat="server" ID="hdNuovaDomanda">Immettere il testo della nuova domanda e fare click su "aggiungi" per aggiungerla all'area tematica corrente</init:HelpDiv>
							<div class="Bottoni">
								<init:SigeproButton ID="cmdNuovaDomanda" runat="server" IdRisorsa="AGGIUNGI" OnClick="cmdNuovaDomanda_Click" />
							</div>
						</asp:View>
					</asp:MultiView>
				</fieldset>
			</td>
		</tr>
	</table>
</asp:Content>
