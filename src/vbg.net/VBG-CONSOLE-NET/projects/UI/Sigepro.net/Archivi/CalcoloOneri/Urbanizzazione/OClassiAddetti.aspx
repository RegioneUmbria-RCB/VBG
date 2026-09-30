<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Archivi_CalcoloOneri_Urbanizzazione_OClassiAddetti" Title="Classi Addetti (Industria)" Codebehind="OClassiAddetti.aspx.cs" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">

<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">

	<asp:View runat="server" ID="ricerca">
		<fieldset>
			<div>
				<asp:Label runat="server" ID="lblSrcId" Text="Codice" AssociatedControlID="txtSrcId" ></asp:Label>
				<init:IntTextBox runat="server" ID="txtSrcId" columns="6" MaxLength="6"></init:IntTextBox>
			</div>
			
			<div>
				<asp:Label runat="server" ID="Label1" Text="Classe" AssociatedControlID="txtSrcClasse" ></asp:Label>
				<asp:TextBox runat="server" ID="txtSrcClasse" Columns="80" MaxLength="200"></asp:TextBox>
			</div>
			
			<div class="Bottoni">
				<init:SigeproButton runat="server" ID="cmdCerca"  Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click" />
				<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
				<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
			</div>
			
		</fieldset>
	</asp:View>
	
	<asp:View runat="server" ID="lista">
	
		<init:GridViewEx AllowSorting="true" runat="server" AutoGenerateColumns="False" DataKeyNames="Id" ID="gvLista" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="OClassiAddettiDataSource"
		        DefaultSortExpression="Id" DefaultSortDirection="Ascending">
		
				<AlternatingRowStyle CssClass="RigaAlternata" />
				<RowStyle CssClass="Riga" />
				<HeaderStyle CssClass="IntestazioneTabella" />
				<EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
		
				<Columns>
					<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" HeaderStyle-HorizontalAlign="Left" sortExpression="Id" />
					<asp:BoundField DataField="Classe" HeaderText="Classe" HeaderStyle-HorizontalAlign="Left" sortExpression="Classe" />
				</Columns>
		
				<EmptyDataTemplate>
					<asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				</EmptyDataTemplate>
		
		</init:GridViewEx>
        <asp:ObjectDataSource ID="OClassiAddettiDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
            SelectMethod="Find" TypeName="Init.SIGePro.Manager.OClassiAddettiMgr" SortParameterName="sortExpression">
            <SelectParameters>
                <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                <asp:ControlParameter ControlID="txtSrcId" DefaultValue="" Name="codice" PropertyName="ValoreInt"
                    Type="Int32" />
                <asp:ControlParameter ControlID="txtSrcClasse" Name="classe" PropertyName="Text"
                    Type="String" />
                <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
            </SelectParameters>
        </asp:ObjectDataSource>
		
		<div class="Bottoni">
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
					<asp:Label runat="server" ID="label4" Text="*Classe" AssociatedControlID="txtClasse" />
					<asp:TextBox runat="server" ID="txtClasse" Columns="80" MaxLength="200" />
				</div>
				<div>
					<asp:Label runat="server" ID="label2" Text="*Ordinamento" AssociatedControlID="txtOrdinamento" />
					<init:IntTextBox runat="server" ID="txtOrdinamento" Columns="3" MaxLength="2" />
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

