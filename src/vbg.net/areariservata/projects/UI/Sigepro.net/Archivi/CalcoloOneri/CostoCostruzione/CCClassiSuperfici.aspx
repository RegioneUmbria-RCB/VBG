<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCClassiSuperfici" Title="Tabella 1 - Classi di superficie" Codebehind="CCClassiSuperfici.aspx.cs" %>
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
					<init:IntTextBox runat="server" ID="txtSrcCodice" columns="6" MaxLength="6"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label2" Text="Classe" AssociatedControlID="txtSrcDescrizione" />
					<asp:TextBox runat="server" ID="txtSrcDescrizione" MaxLength="200" Columns="80" />
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
			    <init:GridViewEx AllowSorting="True" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceCCClassiSuperfici" 
    			    DefaultSortExpression="Id" DefaultSortDirection="Ascending">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:ButtonField CommandName="Select" DataTextField="ID" HeaderText="Codice" Text="Button" SortExpression="Id"><HeaderStyle HorizontalAlign="Left" /></asp:ButtonField>
					    <asp:BoundField DataField="CLASSE" HeaderText="Classe" SortExpression="CLASSE"><HeaderStyle HorizontalAlign="Left" /></asp:BoundField>
					    <asp:BoundField DataField="DA" HeaderText="Da mq" SortExpression="DA"><ItemStyle HorizontalAlign="Right" /><HeaderStyle HorizontalAlign="Right" /></asp:BoundField>
					    <asp:BoundField DataField="A" HeaderText="A mq" SortExpression="A"><ItemStyle HorizontalAlign="Right" /><HeaderStyle HorizontalAlign="Right" /></asp:BoundField>
					    <asp:BoundField DataField="INCREMENTO" HeaderText="Incremento (%)" SortExpression="INCREMENTO"><ItemStyle HorizontalAlign="Right" /><HeaderStyle HorizontalAlign="Right" /></asp:BoundField>
						<asp:BoundField DataField="AliquotaCalcoloCostoCostruzione" HeaderText="Aliquota calcolo CC (%)" SortExpression="AliquotaCalcoloCostoCostruzione"><ItemStyle HorizontalAlign="Right" /><HeaderStyle HorizontalAlign="Right" /></asp:BoundField>
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </init:GridViewEx>
                <asp:ObjectDataSource ID="ObjectDataSourceCCClassiSuperfici" runat="server" OldValuesParameterFormatString="original_{0}"
                    SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCClassiSuperficiMgr" SortParameterName="sortExpression">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:ControlParameter ControlID="txtSrcCodice" Name="id" PropertyName="ValoreInt"
                            Type="Int32" />
                        <asp:ControlParameter ControlID="txtSrcDescrizione" Name="classe" PropertyName="Text"
                            Type="String" />
                        <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
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
			
				<init:LabeledLabel runat="server" ID="lblCodice" Descrizione="Codice" />
				<init:LabeledTextBox runat="server" ID="txtDescrizione" Descrizione="*Classe" Item-MaxLength="200" Item-TextMode="MultiLine" Item-Columns="80" Item-Rows="2" />
				<init:LabeledIntTextBox runat="server" ID="txtDa" HelpControl="hdDa" Descrizione="*Maggiore di mq (>)"  Item-Columns="6" Item-MaxLength="6"/>
				<init:HelpDiv runat="server" ID="hdDa">
					Per intervalli che partono da 0 indicare come primo valore -1.
                </init:HelpDiv>
                <init:LabeledIntTextBox runat="server" ID="txtA" Descrizione="*Minore o uguale a mq (<=)"  Item-Columns="6" Item-MaxLength="6"/>
				<init:LabeledDoubleTextBox runat="server" ID="txtIncremento" Item-Columns="6" Item-MaxLength="6" Descrizione="*Incremento (%)" />

				<legend>Calcolo del costo di costruzione</legend>
				<init:LabeledDoubleTextBox runat="server" ID="txtAliquotaCC" Item-Columns="6" Item-MaxLength="6" Descrizione="Aliquota per calcolo CC (%)" />

				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		
	</asp:MultiView>
</asp:Content>