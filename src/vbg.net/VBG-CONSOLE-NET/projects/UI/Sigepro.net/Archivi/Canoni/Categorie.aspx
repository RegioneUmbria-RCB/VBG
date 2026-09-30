<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" CodeBehind="Categorie.aspx.cs" Inherits="Sigepro.net.Archivi.Canoni.Categorie" Title="Categorie" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
			    <init:LabeledTextBox runat="server" ID="txSrcDescrizione" Descrizione="Categoria" Item-Columns="60" Item-MaxLength="50" />
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCerca"  Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click"/>
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click"/>
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click"/>
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="listaView">
			<fieldset>
			    <div>
                    <init:GridViewEx AllowSorting="True" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" DataSourceID="CanoniCategorieDataSource" DefaultSortDirection="Ascending" DefaultSortExpression="Descrizione" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DatabindOnFirstLoad="False">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				        <Columns>
					        <asp:ButtonField CommandName="Select" DataTextField="Descrizione" HeaderText="Descrizione" Text="Button" SortExpression="Descrizione" />
				        </Columns>
				        <EmptyDataTemplate>
					        <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				        </EmptyDataTemplate>
			        </init:GridViewEx>
                    <asp:ObjectDataSource ID="CanoniCategorieDataSource" runat="server" SortParameterName="sortexpression" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CanoniCategorieMgr">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                            <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                            <asp:ControlParameter ControlID="txSrcDescrizione" Name="descrizione" PropertyName="Value"
                                Type="String" />
                            <asp:Parameter Name="sortExpression" Type="String" />
                        </SelectParameters>
                    </asp:ObjectDataSource>
			    </div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdCloseList_Click"/>
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
			    <init:LabeledLabel runat="server" ID="lblCodice" Descrizione="Codice" />
			    <init:LabeledTextBox runat="server" ID="txDescrizione" Descrizione="*Categoria" Item-Columns="60" Item-MaxLength="50" />
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click"/>
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click"/>
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click"/>
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>