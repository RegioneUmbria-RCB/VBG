<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CCValiditaCoefficienti" Title="Configurazione validità coefficienti" Codebehind="CCValiditaCoefficienti.aspx.cs" %>
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
					<init:IntTextBox runat="server" ID="txtSrcCodice" columns="6" />
				</div>
				<div>
					<asp:Label runat="server" ID="label2" Text="Descrizione" AssociatedControlID="txtSrcDescrizione" />
					<asp:TextBox runat="server" ID="txtSrcDescrizione" Columns="80" MaxLength="200" />
				</div>
				<div class="Intervallo">
					<div class="Da">
					<asp:Label runat="server" ID="label7" Text="Dalla data" AssociatedControlID="txtDataDa" />
					<init:DateTextBox runat="server" ID="txtDataDa" />
					</div>
					
					<div class="A">
					<asp:Label CssClass="StessaRiga" runat="server" ID="label8" Text="alla data:" AssociatedControlID="txtDataA" />
					<init:DateTextBox runat="server" ID="txtDataA" />
										</div>
				</div>
				<div class="Intervallo">
					<div class="Da">
					<asp:Label  runat="server" ID="label9" Text="Costo al mq da" AssociatedControlID="txtCostoMqDa" />
					<init:DoubleTextBox runat="server" ID="txtCostoMqDa" Columns="10" />
					</div>
					
					<div class="A">
					<asp:Label runat="server" ID="label10" Text="a" AssociatedControlID="txtCostoMqA" />
					<init:DoubleTextBox runat="server" ID="txtCostoMqA" Columns="10"/>
					</div>
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
			    <init:GridViewEx runat="server" AllowSorting="True" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceCCValiditaCoefficienti" 
			        DefaultSortExpression="Id" DefaultSortDirection="Ascending">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" SortExpression="Id"><HeaderStyle HorizontalAlign="Left"/></asp:ButtonField>
					    <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" SortExpression="Descrizione"><HeaderStyle HorizontalAlign="Left" /></asp:BoundField>
					    <asp:BoundField DataField="Datainiziovalidita" SortExpression="Datainiziovalidita" HeaderText="Data inizio validit&#224;" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="False"><ItemStyle HorizontalAlign="Left" /><HeaderStyle HorizontalAlign="Left" /></asp:BoundField>
				        <asp:BoundField DataField="Costomq" SortExpression="Costomq" HeaderText="Costo al mq" HtmlEncode="False"><ItemStyle HorizontalAlign="Right" /><HeaderStyle HorizontalAlign="Right" /></asp:BoundField>
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </init:GridViewEx>
                <asp:ObjectDataSource ID="ObjectDataSourceCCValiditaCoefficienti" runat="server"
                    OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCValiditaCoefficientiMgr" SortParameterName="sortExpression">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:ControlParameter ControlID="txtSrcCodice" Name="id" PropertyName="ValoreInt"
                            Type="Int32" />
                        <asp:ControlParameter ControlID="txtSrcDescrizione" Name="descrizione" PropertyName="Text"
                            Type="String" />
                        <asp:ControlParameter ControlID="txtDataDa" Name="dataDa" PropertyName="DateValue"
                            Type="DateTime" />
                        <asp:ControlParameter ControlID="txtDataA" Name="dataA" PropertyName="DateValue"
                            Type="DateTime" />
                        <asp:ControlParameter ControlID="txtCostoMqDa" Name="costomq_da" PropertyName="ValoreDouble"
                            Type="Single" />
                        <asp:ControlParameter ControlID="txtCostoMqA" Name="costomq_a" PropertyName="ValoreDouble"
                            Type="Single" />
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
				<div>
					<asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
					<asp:Label runat="server" ID="lblCodice" />
				</div>
				<div>
					<asp:Label runat="server" ID="label4" Text="*Descrizione" AssociatedControlID="txtDescrizione" />
					<asp:TextBox runat="server" ID="txtDescrizione" MaxLength="200" TextMode="MultiLine" Columns="80" Rows="2" />
				</div>
				<div>
					<asp:Label runat="server" ID="label5" Text="*Data inizio validità" AssociatedControlID="txtDataInizioValidita" />
					<init:DateTextBox runat="server" ID="txtDataInizioValidita" />
				</div>
				<div>
					<asp:Label runat="server" ID="label11" Text="*Costo al mq" AssociatedControlID="txtCostoMq" />
					<init:DoubleTextBox runat="server" ID="txtCostoMq" />
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdCoefficientiContributi" Text="Coefficienti per tipo intervento" IdRisorsa="COEFFTIPIINTERVENTO" OnClick="cmdCoefficientiContributi_Click" />
					<init:SigeproButton runat="server" ID="cmdCoeffContribAttivita" Text="Coefficienti per altre tabelle" IdRisorsa="COEFFALTRETABELLE" OnClick="cmdCoeffContribAttivita_Click" />                    
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		
	</asp:MultiView>
</asp:Content>