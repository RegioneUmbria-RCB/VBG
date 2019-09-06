<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Istanze_CalcoloOneri_Urbanizzazione_OICalcoloTot" Title="Calcolo degli oneri di urbanizzazione" Codebehind="OICalcoloTot.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>




<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="risultato">
			<asp:GridView runat="server" AutoGenerateColumns="False" DataKeyNames="Id" ID="gvLista" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" OnRowDataBound="gvLista_RowDataBound" OnRowDeleting="gvLista_RowDeleting">
				<AlternatingRowStyle CssClass="RigaAlternata" />
				<RowStyle CssClass="Riga" />
				<HeaderStyle CssClass="IntestazioneTabella" />
				<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
				<Columns>
					<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" />
					<asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
                    <asp:TemplateField>
	                    <ItemTemplate>
		                    <asp:ImageButton ID="cmdElimina" runat="server" CommandName="Delete" ImageUrl="~/images/Delete.gif" />
	                    </ItemTemplate>
                    </asp:TemplateField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="Label6" runat="server"></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
			<div class="Bottoni">
				<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
			</div>
		</asp:View>
		<asp:View runat="server" ID="inserimento">
			<asp:ScriptManager ID="ScriptManager1" runat="server">
			</asp:ScriptManager>
			<fieldset>
				<asp:UpdatePanel ID="UpdatePanel1" runat="server">
					<ContentTemplate>
						<div>
							<asp:Label runat="server" ID="label2" Text="Data" AssociatedControlID="txtData"></asp:Label>
							<init:DateTextBox runat="server" ID="txtData" AutoPostBack="True" OnTextChanged="txtData_TextChanged"></init:DateTextBox>
						</div>
						<div>
							<asp:Label runat="server" ID="lblFkCCVCID" Text="Listino coefficienti" AssociatedControlID="ddlCoefficienti"></asp:Label>
							<asp:DropDownList runat="server" ID="ddlCoefficienti" DataTextField="Descrizione" DataValueField="Id" />
						</div>						
						<init:LabeledTextBox ID="txtDescrizione" Descrizione="Descrizione" runat="server" HelpControl="hdDescrizione" Item-Columns="100" Item-MaxLength="200" />
						<init:HelpDiv ID="hdDescrizione" runat="server" >Se lasciato vuoto verrà valorizzato con <b>"Calcolo degli oneri di urbanizzazione"</b></init:HelpDiv>
					</ContentTemplate>
				</asp:UpdatePanel>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdInserisci"  Text="Inserisci" IdRisorsa="INSERISCI" OnClick="cmdInserisci_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiInserimento" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiInserimento_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View ID="dettaglio" runat="server">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label13" Text="Codice" AssociatedControlID="lblId"></asp:Label>
					<asp:Label runat="server" ID="lblId" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="label7" Text="Data" AssociatedControlID="lblData"></asp:Label>
					<asp:Label runat="server" ID="lblData" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="Label8" Text="Listino coefficienti" AssociatedControlID="lblListino"></asp:Label>
					<asp:Label runat="server" ID="lblListino" Text=""></asp:Label>
				</div>

				<div>
					<asp:Label runat="server" ID="Label12" Text="*Descrizione" AssociatedControlID="txtEditDescrizione"></asp:Label>
					<asp:TextBox runat="server" ID="txtEditDescrizione" Columns="100" MaxLength="200" />
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" id="cmdAggiornaDescrizione" Text="Aggiorna descrizione" IdRisorsa="AGGIORNADESCRIZIONE" OnClick="cmdAggiornaDescrizione_Click" />
					<init:SigeproButton runat="server" ID="cmdEliminaCalcolo" Text="Elimina calcolo" IdRisorsa="ELIMINACALCOLO" OnClick="cmdEliminaCalcolo_Click" />
				</div>
			</fieldset>

			<div class="TabellaOneri">
				<asp:DataGrid ShowFooter="true" runat="server" ID="dgDettagliCalcolo" AutoGenerateColumns="true" CellSpacing="-1" GridLines="None" OnItemCommand="dgDettagliCalcolo_ItemCommand" OnItemDataBound="dgDettagliCalcolo_ItemDataBound">
					<HeaderStyle CssClass="IntestazioneTabella" />
				</asp:DataGrid>
			</div>
			
			<fieldset>
				<!--<div>-->
				<!--</div>-->
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdDettagli" Text="Dettagli" IdRisorsa="DETTAGLI" OnClick="cmdRedirDettagli_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>

</asp:Content>

