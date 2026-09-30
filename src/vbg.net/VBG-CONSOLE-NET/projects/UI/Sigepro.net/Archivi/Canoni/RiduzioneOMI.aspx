<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="RiduzioneOMI.aspx.cs" Inherits="Sigepro.net.Archivi.Canoni.RiduzioneOMI" Title="Classi di riduzione OMI" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<fieldset>
			    <div>
                    <init:GridViewEx AllowSorting="False" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" DataSourceID="CanoniRiduzioniOMIDataSource" DatabindOnFirstLoad="True" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" OnRowDataBound="gvLista_RowDataBound">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				        <Columns>
					        <asp:ButtonField CommandName="Select" DataTextField="Descrizione" HeaderText="Descrizione" Text="Button" SortExpression="Descrizione" />
					        <asp:BoundField HeaderText="Da mq" />
					        <asp:BoundField DataField="MqA" HeaderText="A mq" SortExpression="MqA" />
				        </Columns>
				        <EmptyDataTemplate>
					        <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				        </EmptyDataTemplate>
			        </init:GridViewEx>
                    <asp:ObjectDataSource ID="CanoniRiduzioniOMIDataSource" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CanoniRiduzioniOMIMgr">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                            <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                        </SelectParameters>
                    </asp:ObjectDataSource>
			    </div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click"/>
					<init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdCloseList_Click"/>
				</div>
			</fieldset>
		</asp:View>		
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
			    <init:LabeledLabel runat="server" ID="lblCodice" Descrizione="Codice" />
			    <init:LabeledTextBox runat="server" ID="txDescrizione" Descrizione="*Descrizione" Item-Columns="55" Item-MaxLength="50" />
		        <init:LabeledDoubleTextBox runat="server" ID="txMqA" Descrizione="*Minore o uguale a (<=)" Item-Columns="8" />
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click"/>
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click"/>
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click"/>
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>