<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_Urbanizzazione_OIndiciTerritoriali"
	Title="Indici Territoriali" Codebehind="OIndiciTerritoriali.aspx.cs" %>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="lista">
			<asp:GridView runat="server" AutoGenerateColumns="False" DataKeyNames="Id" ID="gvLista" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceOIndiciTerritoriali"
				OnRowDataBound="gvLista_RowDataBound">
				<AlternatingRowStyle CssClass="RigaAlternata" />
				<RowStyle CssClass="Riga" />
				<HeaderStyle CssClass="IntestazioneTabella" />
				<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
				<Columns>
					<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" HeaderStyle-HorizontalAlign="Left" />
					<asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
					<asp:TemplateField HeaderText="DTZ" ItemStyle-HorizontalAlign="Right" HeaderStyle-HorizontalAlign="Right">
						<ItemTemplate>
							<asp:Label ID="lblDTZ" runat="server" />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField HeaderText="IFT" ItemStyle-HorizontalAlign="Right" HeaderStyle-HorizontalAlign="Right">
						<ItemTemplate>
							<asp:Label ID="lblIFT" runat="server" />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField HeaderText="IFF" ItemStyle-HorizontalAlign="Right" HeaderStyle-HorizontalAlign="Right">
						<ItemTemplate>
							<asp:Label ID="lblIFF" runat="server" />
						</ItemTemplate>
					</asp:TemplateField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="Label6" runat="server"></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
			<asp:ObjectDataSource ID="ObjectDataSourceOIndiciTerritoriali" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.OIndiciTerritorialiMgr">
				<SelectParameters>
					<asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
					<asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
				</SelectParameters>
			</asp:ObjectDataSource>
			<div class="Bottoni">
				<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
				<init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdCloseList_Click" />
			</div>
		</asp:View>
		<asp:View runat="server" ID="scheda">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
					<asp:Label runat="server" ID="lblCodice" />
				</div>
				<div>
					<asp:Label runat="server" ID="lblDTZ" Text="Densità di zona" AssociatedControlID="txtDTZ"></asp:Label>
					<init:DoubleTextBox runat="server" ID="txtDTZ" Columns="6" />
				</div>
				<div>
					<asp:Label runat="server" ID="lblIFT" Text="Indice di fabbricabilità territoriale" AssociatedControlID="txtIFT"></asp:Label>
					<init:DoubleTextBox runat="server" ID="txtIFT" Columns="6" />
				</div>
				<div>
					<asp:Label runat="server" ID="lblIFF" Text="Indice di fabbricabilità fondiaria" AssociatedControlID="txtIFF"></asp:Label>
					<init:DoubleTextBox runat="server" ID="txtIFF" Columns="6" />
				</div>
				<div>
					<asp:Label runat="server" ID="Label1" Text="Descrizione" AssociatedControlID="txtDescrizione"></asp:Label>
					<asp:TextBox runat="server" ID="txtDescrizione" Columns="50" MaxLength="50" />
				</div>
				<div class="DescrizioneCampo">
					Se lasciato vuoto verrà valorizzato con i valori degli indici compilati
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>
