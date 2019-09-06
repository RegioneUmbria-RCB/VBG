<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" CodeBehind="TipiSuperfici.aspx.cs" Inherits="Sigepro.net.Archivi.Canoni.TipiSuperfici" Title="Tipi superfici"%>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
			    <init:LabeledTextBox runat="server" ID="txSrcTipoSuperficie" Descrizione="Tipo superficie" Item-Columns="60" Item-MaxLength="50" />
                <init:LabeledLabel runat="server" ID="lblTipoCalcoloCerca" Descrizione="Tipo di calcolo"  />

			    <asp:DropDownList runat="server" ID="ddlTipoCalcoloCerca">
			        <asp:ListItem Text="" Value="" Selected="True" />
			        <asp:ListItem Text="Annuale" Value="A" />
			        <asp:ListItem Text="Stagionale" Value="S" />
			    </asp:DropDownList>
				
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
                    <init:GridViewEx AllowSorting="True" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" DataSourceID="CanoniTipiSuperficiDataSource" DefaultSortDirection="Ascending" DefaultSortExpression="TipoSuperficie" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DatabindOnFirstLoad="False" OnRowDataBound="gvLista_RowDataBound">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				        <Columns>
					        <asp:ButtonField CommandName="Select" DataTextField="TipoSuperficie" HeaderText="Tipo Superficie" Text="Button" SortExpression="TipoSuperficie" />
					        <asp:BoundField HeaderText="Pertinenza" DataField="Pertinenza" SortExpression="Pertinenza" />
                            <asp:BoundField HeaderText="Tipo di calcolo" DataField="TipocalcoloDescr" SortExpression="TipoCalcolo" />
				        </Columns>
				        <EmptyDataTemplate>
					        <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				        </EmptyDataTemplate>
			        </init:GridViewEx>
                    <asp:ObjectDataSource ID="CanoniTipiSuperficiDataSource" runat="server" SortParameterName="sortexpression" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CanoniTipiSuperficiMgr">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                            <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                            <asp:ControlParameter ControlID="txSrcTipoSuperficie" Name="tipoSuperficie" PropertyName="Value"
                                Type="String" />
                            <asp:ControlParameter ControlID="ddlTipoCalcoloCerca" Name="tipoCalcolo" PropertyName="SelectedValue"
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
			    <init:LabeledTextBox runat="server" ID="txTipoSuperficie" Descrizione="*Tipo superficie" Item-Columns="60" Item-MaxLength="50" />
		        <init:LabeledCheckBox runat="server" ID="chkPertinenza" Descrizione="Pertinenza" HelpControl="hdPertinenza"/>
		        <init:HelpDiv runat="server" ID="hdPertinenza">Se spuntato, identifica il tipo di superficie come pertinenza</init:HelpDiv>
		        <init:LabeledCheckBox runat="server" ID="chkFlagConteggiaMq" Descrizione="Conteggia MQ" HelpControl="hdFlagConteggiaMq" />
		        <init:HelpDiv runat="server" ID="hdFlagConteggiaMq">Se spuntato, questa tipologia di superficie, non va ad incidere il totale dei metri quadri nel calcolo del canone di un'istanza</init:HelpDiv>
                <init:LabeledLabel runat="server" ID="lblTipoCalcolo" Descrizione="Tipo di calcolo"  />
                
				    <asp:DropDownList runat="server" ID="ddlTipoCalcolo">
				        <asp:ListItem Text="" Value="" Selected="True" />
				        <asp:ListItem Text="Annuale" Value="A" />
				        <asp:ListItem Text="Stagionale" Value="S" />
				    </asp:DropDownList>
				    <asp:RequiredFieldValidator runat="server" ID="rfvTipoCalcolo" ControlToValidate="ddlTipoCalcolo" ErrorMessage="*" />
				
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click"/>
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" CausesValidation="false"/>
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" CausesValidation="false"/>
				</div>
		    </fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>