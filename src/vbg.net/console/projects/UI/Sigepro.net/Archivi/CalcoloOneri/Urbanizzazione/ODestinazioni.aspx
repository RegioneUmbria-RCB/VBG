<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Archivi_CalcoloOneri_Urbanizzazione_ODestinazioni" Title="Destinazioni" Codebehind="ODestinazioni.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI"  %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label1" Text="Codice" AssociatedControlID="txtSrcCodice" />
					<init:IntTextBox runat="server" ID="txtSrcCodice" Columns="6" MaxLength="6" />
				</div>
				<div>
					<asp:Label runat="server" ID="label2" Text="Destinazioni di base" AssociatedControlID="ddlSrcDestinazioneBase" />
					<asp:DropDownList runat="server" ID="ddlSrcDestinazioneBase" DataValueField="ID" DataTextField="DESTINAZIONE" />
				</div>
				<div>
					<asp:Label runat="server" ID="label7" Text="Destinazione" AssociatedControlID="txtSrcDestinazione" />
					<asp:TextBox runat="server" ID="txtSrcDestinazione" MaxLength="200" Columns="80" />
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
			<init:GridViewEx AllowSorting="true" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ODestinazioniDataSource"
			    DefaultSortDirection="Ascending" DefaultSortExpression="Id">
				<AlternatingRowStyle CssClass="RigaAlternata" />
				<RowStyle CssClass="Riga" />
				<HeaderStyle CssClass="IntestazioneTabella" />
				<EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				<Columns>
					<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" SortExpression="Id" />
                    <asp:BoundField DataField="DestinazioneBase" HeaderText="Destinazione di base"  SortExpression="DestinazioneBase" />
					<asp:BoundField DataField="Destinazione" HeaderText="Destinazione" SortExpression="Destinazione"/>
					<asp:BoundField DataField="TipoUnitaMisura" HeaderText="Unit&#224; di misura" SortExpression="TipoUnitaMisura"/>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				</EmptyDataTemplate>
			</init:GridViewEx>
			
			
                <asp:ObjectDataSource ID="ODestinazioniDataSource" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.ODestinazioniMgr" SortParameterName="sortExpression">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:ControlParameter ControlID="txtSrcCodice" Name="codice" PropertyName="ValoreInt"
                            Type="Int32" />
                        <asp:ControlParameter ControlID="txtSrcDestinazione" Name="destinazione" PropertyName="Text"
                            Type="String" />
                        <asp:ControlParameter ControlID="ddlSrcDestinazioneBase" Name="destinazioneBase"
                            PropertyName="SelectedValue" Type="String" />
                        <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                    </SelectParameters>
                </asp:ObjectDataSource>
			</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />
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
					<asp:Label runat="server" ID="label9" Text="*Destinazione di base" AssociatedControlID="ddlDestinazioneBase" />
					<asp:DropDownList runat="server" ID="ddlDestinazioneBase" DataValueField="ID" DataTextField="DESTINAZIONE" />
				</div>
				<div>
					<asp:Label runat="server" ID="label10" Text="*Destinazione" AssociatedControlID="txtDestinazione" />
					<asp:TextBox runat="server" ID="txtDestinazione" TextMode="MultiLine" MaxLength="200" Rows="2" Columns="80" />
				</div>
				<div>
					<asp:Label runat="server" ID="label11" Text="*Unità di misura" AssociatedControlID="ddlUnitaMisura" />
					<asp:DropDownList runat="server" ID="ddlUnitaMisura" DataValueField="UmId" DataTextField="UmDescrbreve" />
				</div>
				<div>
				    <asp:Label runat="server" ID="label4" Text="*Ordinamento" AssociatedControlID="txtOrdinamento" />
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



