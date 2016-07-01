<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCDestinazioni" Title="Destinazioni" Codebehind="CCDestinazioni.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>




<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label1" Text="Codice" AssociatedControlID="txtSrcCodice" />
					<init:IntTextBox runat="server" ID="txtSrcCodice" Columns="6" />
				</div>
				<div>
					<asp:Label runat="server" ID="label7" Text="Destinazione di base" AssociatedControlID="ddlSrcDestinazioneBase" />
					<asp:DropDownList runat="server" ID="ddlSrcDestinazioneBase" DataValueField="ID" DataTextField="DESTINAZIONE"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label5" Text="Destinazione" AssociatedControlID="txtSrcDestinazione" />
					<asp:TextBox runat="server" ID="txtSrcDestinazione" Columns="80" MaxLength="200" />
				</div>
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
			    <init:GridViewEx AllowSorting="True" DefaultSortExpression="Id" DefaultSortDirection="Ascending" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceCCDestinazione">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:ButtonField CommandName="Select" SortExpression="Id" DataTextField="Id" HeaderText="Codice" Text="Button" />
					    <asp:BoundField DataField="DestinazioneBase" HeaderText="Destinazione di base"  SortExpression="DestinazioneBase" />
					    <asp:BoundField DataField="Destinazione" HeaderText="Destinazione"  SortExpression="Destinazione" />
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </init:GridViewEx>
                <asp:ObjectDataSource ID="ObjectDataSourceCCDestinazione" runat="server" OldValuesParameterFormatString="original_{0}"
                    SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCDestinazioniMgr" SortParameterName="sortExpression">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:ControlParameter ControlID="txtSrcCodice" Name="id" PropertyName="ValoreInt"
                            Type="Int32" />
                        <asp:ControlParameter ControlID="txtSrcDestinazione" Name="destinazione" PropertyName="Text"
                            Type="String" />
                        <asp:ControlParameter ControlID="ddlSrcDestinazioneBase" Name="destinazioneBase"
                            PropertyName="SelectedValue" Type="String" />
                        <asp:QueryStringParameter DefaultValue="" Name="software" QueryStringField="software"
                            Type="String" />
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
				<div>
					<asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
					<asp:Label runat="server" ID="lblCodice" />
				</div>
				<div>
					<asp:Label runat="server" ID="label10" Text="*Destinazione di base" AssociatedControlID="ddlDestinazioneBase" />
					<asp:DropDownList runat="server" ID="ddlDestinazioneBase" DataValueField="ID" DataTextField="DESTINAZIONE"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label12" Text="*Destinazione" AssociatedControlID="txtDestinazioneBase" />
					<asp:TextBox runat="server" ID="txtDestinazioneBase" TextMode="MultiLine" MaxLength="200" Columns="80" Rows="2" />
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