<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCDettagliSuperficie" Title="Dettagli di superficie" Codebehind="CCDettagliSuperficie.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<div class="Informazioni">
	    <asp:Label CssClass="Etichetta" ID="lblTitolo1" runat="server" Text="Tipologia di superficie"></asp:Label>
	    <asp:Label CssClass="Valore" ID="lblDescrizione1" runat="server" Text=""></asp:Label>
	</div>
	<div class="clear">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<fieldset>
			<div>
			    <init:GridViewEx runat="server" DatabindOnFirstLoad=true AllowSorting="true" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceCCDettaglioSuperficie"
			        DefaultSortExpression="Id" DefaultSortDirection="Ascending">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" HeaderStyle-HorizontalAlign="Left" sortExpression="Id" />
					    <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" HeaderStyle-HorizontalAlign="Left" sortExpression="Descrizione" />
					    <asp:BoundField DataField="TipoSuperficie" HeaderText="TipoSuperficie" HeaderStyle-HorizontalAlign="Left" sortExpression="TipoSuperficie" />
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </init:GridViewEx>
                <asp:ObjectDataSource ID="ObjectDataSourceCCDettaglioSuperficie" runat="server" OldValuesParameterFormatString="original_{0}"
                    SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCDettagliSuperficieMgr" SortParameterName="sortExpression">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:QueryStringParameter Name="fkcctsid" QueryStringField="fkcctsid" Type="String" />
                        <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                    </SelectParameters>
                </asp:ObjectDataSource>
			</div>
				<div class="Bottoni">
				    <init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                    <init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />					
				</div>
			</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
					<asp:Label runat="server" ID="lblCodice" />
				</div>
				<div>
					<asp:Label runat="server" ID="label8" Text="*Tipo superficie" AssociatedControlID="ddlTipiSuperficie" />
					<asp:DropDownList runat="server" ID="ddlTipiSuperficie" DataValueField="ID" DataTextField="DESCRIZIONE"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label4" Text="*Descrizione" AssociatedControlID="txtDescrizione" />
					<asp:TextBox runat="server" ID="txtDescrizione" MaxLength="200" TextMode="MultiLine" Columns="80" Rows="2" />
				</div>
				<div>
					<asp:Label runat="server" ID="label5" Text="Note" AssociatedControlID="txtNote" />
					<asp:TextBox runat="server" ID="txtNote" TextMode="MultiLine" Rows="4" Columns="80" MaxLength="500" />
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		
	</asp:MultiView>
	</div>
</asp:Content>