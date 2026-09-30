<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_Urbanizzazione_OValiditaCoefficienti" Title="Configurazione validità coefficienti" Codebehind="OValiditaCoefficienti.aspx.cs" %>

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
					<asp:TextBox runat="server" ID="txtSrcDescrizione" columns="80" MaxLength="200" />
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
			<asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="OValiditaCoefficientiDataSource">
				<AlternatingRowStyle CssClass="RigaAlternata" />
				<RowStyle CssClass="Riga" />
				<HeaderStyle CssClass="IntestazioneTabella" />
				<EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				<Columns>
					<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" >
                        <HeaderStyle HorizontalAlign="Left" />
                    </asp:ButtonField>
					<asp:BoundField DataField="Descrizione" HeaderText="Descrizione" >
                        <HeaderStyle HorizontalAlign="Left" />
                    </asp:BoundField>
					<asp:BoundField DataField="Datainiziovalidita" HeaderText="Data inizio validit&#224;" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="False" >
                        <HeaderStyle HorizontalAlign="Left" />
                    </asp:BoundField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
                <asp:ObjectDataSource ID="OValiditaCoefficientiDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
                    SelectMethod="Find" TypeName="Init.SIGePro.Manager.OValiditaCoefficientiMgr">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                        <asp:ControlParameter ControlID="txtSrcCodice" Name="codice" PropertyName="Text"
                            Type="Int32" />
                        <asp:ControlParameter ControlID="txtSrcDescrizione" Name="descrizione" PropertyName="Text"
                            Type="String" />
                    </SelectParameters>
                </asp:ObjectDataSource>
			</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdCloseList_Click" />
				</div>
			</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
					<asp:Label runat="server" ID="lblCodice"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label4" Text="*Descrizione" AssociatedControlID="txtDescrizione" />
					<asp:TextBox runat="server" ID="txtDescrizione" columns="80" MaxLength="200" />
				</div>
				<div>
					<asp:Label runat="server" ID="label5" Text="*Data inizio validità" AssociatedControlID="txtDataInizioValidita" />
					<init:DateTextBox runat="server" ID="txtDataInizioValidita" />
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdTabellaD" Text="Tabella D - D.M. 10/5/77 ( industria )" IdRisorsa="TABELLAD" OnClick="cmdTabellaD_Click" />
					<init:SigeproButton runat="server" ID="cmdTabellaABC" Text="Tabella A, Tabella B, Tabella C ( L. 10/77 Art. 10 )" IdRisorsa="TABELLEABC" OnClick="cmdTabellaABC_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		
	</asp:MultiView>
</asp:Content>


