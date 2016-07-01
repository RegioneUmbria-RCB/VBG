<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master"  ValidateRequest="false" AutoEventWireup="True" Inherits="Archivi_DatiDinamici_Dyn2Campi"
	Title="Campi dinamici" Codebehind="Dyn2Campi.aspx.cs" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
				<init:LabeledTextBox ID="srcCodice" runat="server" Descrizione="Codice" Item-Columns="10"/>
				<init:LabeledTextBox ID="srcNomeCampo" runat="server" Descrizione="Nome campo" Item-Columns="80"/>
				<init:LabeledTextBox ID="srcEtichetta" runat="server" Descrizione="Etichetta" Item-Columns="80" />
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCerca"  Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click" />
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="listaView">
			<fieldset>
				<div>
					<init:GridViewEx AllowSorting="True" runat="server" ID="gvLista" AutoGenerateColumns="False" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSource1"
						DefaultSortDirection="Ascending" DefaultSortExpression="Id" DataKeyNames="Id">
						<EmptyDataRowStyle CssClass="NessunRecordTrovato"></EmptyDataRowStyle>
						<Columns>
							<asp:ButtonField HeaderText="Codice" SortExpression="Id" Text="Button" DataTextField="Id" CommandName="Select"></asp:ButtonField>
							<asp:BoundField DataField="Nomecampo" SortExpression="Nomecampo" HeaderText="Nome campo"></asp:BoundField>
							<asp:BoundField DataField="Etichetta" SortExpression="Etichetta" HeaderText="Etichetta"></asp:BoundField>
							<asp:BoundField DataField="Descrizione" SortExpression="Descrizione" HeaderText="Descrizione"></asp:BoundField>
							<asp:TemplateField HeaderText="Tipo dato" SortExpression="Tipodato">
								<itemtemplate><asp:Label runat="server" Text='<%# TraduciTipoDato(DataBinder.Eval(Container.DataItem,"Tipodato")) %>' id="Label1"></asp:Label></itemtemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="Contesto" SortExpression="FkD2bcId">
								<itemtemplate>
<asp:Label runat="server" Text='<%# TraduciContesto( DataBinder.Eval(Container.DataItem,"FkD2bcId")) %>' id="Label2"></asp:Label>
</itemtemplate>
							</asp:TemplateField>
						</Columns>
						<RowStyle CssClass="Riga"></RowStyle>
						<EmptyDataTemplate>
							<asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
						</EmptyDataTemplate>
						<HeaderStyle CssClass="IntestazioneTabella"></HeaderStyle>
						<AlternatingRowStyle CssClass="RigaAlternata"></AlternatingRowStyle>
					</init:GridViewEx>
					<asp:ObjectDataSource ID="ObjectDataSource1" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" SortParameterName="sortExpression"
						TypeName="Init.SIGePro.Manager.Dyn2CampiMgr">
						<SelectParameters>
							<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
							<asp:QueryStringParameter Name="software" QueryStringField="Software" Type="String" />
							<asp:ControlParameter ControlID="srcCodice" Name="codice" PropertyName="Value" Type="String" />
							<asp:ControlParameter ControlID="srcNomeCampo" Name="nomeCampo" PropertyName="Value" Type="String" />
							<asp:ControlParameter ControlID="srcEtichetta" Name="etichetta" PropertyName="Value" Type="String" />
							<asp:Parameter Name="sortExpression" Type="String" />
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
				<init:LabeledLabel ID="lblId" runat="server" Descrizione="Codice"></init:LabeledLabel>
				<init:LabeledTextBox ID="txtNomeCampo" runat="server" Descrizione="*Nome campo" Item-Columns="80" Item-MaxLength="400" />
				<init:LabeledTextBox ID="txtEtichetta" runat="server" Descrizione="Etichetta" Item-Columns="80" Item-MaxLength="200" />
				<init:LabeledTextBox ID="txtDescrizione" runat="server" Descrizione="Descrizione" Item-TextMode="MultiLine" Item-Rows="4" Item-Columns="80" />
				<init:LabeledDropDownList ID="ddlContesto" runat="server" Descrizione="*Contesto" OnValueChanged="ddlContesto_ValueChanged" />
				<init:LabeledDropDownList ID="ddlTipoDato" runat="server" Descrizione="*Tipo dato" OnValueChanged="ddlTipoDato_ValueChanged" Item-AutoPostBack="true"  HelpControl="helpTipoControllo" />
				<asp:Panel runat="server" ID="pnlProprietaControllo">
					<legend>Proprietà controllo</legend>
					<asp:Repeater runat="server" ID="gvProprietaControllo" OnItemDataBound="gvProprietaControllo_ItemDataBound">
						<ItemTemplate>
							<asp:HiddenField runat="server" ID="hfPropId" Value='<%# Bind( "Name" ) %>'/>
							<init:LabeledTextBox ID="txtProprieta" runat="server" Descrizione='<%# Bind( "Description" ) %>' Item-Columns="40" Item-MaxLength="1000" />
							<init:LabeledDropDownList ID="ddlProprieta" runat="server" Descrizione='<%# Bind( "Description" ) %>'  />
						</ItemTemplate>
					</asp:Repeater>
				</asp:Panel>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Ok" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdEditFormule" Text="Formule" IdRisorsa="FORMULE" OnClick="cmdEditFormule_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>

			<init:HelpDiv runat="server" ID="helpTipoControllo">
			</init:HelpDiv>

            <asp:Panel runat="server" ID="pnlCampoCompareInSchede">
				
				<fieldset>
					<legend>Il campo è contenuto nelle schede:</legend>
				</fieldset>   
				<div>
					<asp:GridView runat="server" ID="gvSchedeDelCampo" AutoGenerateColumns="false">
						<Columns>
							<asp:BoundField DataField="Id" HeaderText="Id" />
							<asp:BoundField DataField="Software" HeaderText="Modulo" />
							<asp:BoundField DataField="CodiceScheda" HeaderText="Codice scheda" />
							<asp:BoundField DataField="Descrizione" HeaderText="Descrizione"/>
							<asp:BoundField DataField="FkD2bcId" HeaderText="Contesto"/>						
						</Columns>
					</asp:GridView>
				</div>
			</asp:Panel>
		</asp:View>
	</asp:MultiView>
</asp:Content>
