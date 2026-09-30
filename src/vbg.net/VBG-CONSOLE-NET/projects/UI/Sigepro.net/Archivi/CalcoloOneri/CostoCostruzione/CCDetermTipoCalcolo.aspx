<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCDetermTipoCalcolo" Title="Tipi di calcolo" Codebehind="CCDetermTipoCalcolo.aspx.cs" %>
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
					<asp:Label runat="server" ID="label7" Text="Intervento di base" AssociatedControlID="ddlSrcInterventoBase" />
					<asp:DropDownList runat="server" ID="ddlSrcInterventoBase" DataValueField="ID" DataTextField="INTERVENTO"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label4" Text="Destinazione di base" AssociatedControlID="ddlSrcDestinazioneBase" />
					<asp:DropDownList runat="server" ID="ddlSrcDestinazioneBase" DataValueField="ID" DataTextField="DESTINAZIONE"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label2" Text="Calcolo di base" AssociatedControlID="ddlSrcCalcoloBase" />
					<asp:DropDownList runat="server" ID="ddlSrcCalcoloBase" DataValueField="ID" DataTextField="TIPOCALCOLO"/>
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
			    <init:GridViewEx AllowSorting="True" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceCCDetermTipoCalcolo"
					DefaultSortExpression="Id" DefaultSortDirection="Ascending">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" SortExpression="Id" />
					    <asp:BoundField DataField="BaseTipoIntervento" HeaderText="Intervento di base"  SortExpression="BaseTipoIntervento" />
					    <asp:BoundField DataField="BaseTipoDestinazione" HeaderText="Destinazione di base"  SortExpression="BaseTipoDestinazione" />
					    <asp:BoundField DataField="BaseTipoCalcolo" HeaderText="Calcolo di base"  SortExpression="BaseTipoCalcolo" />
					    <asp:BoundField DataField="Priorita" SortExpression="Priorita" HeaderText="Priorit&#224;" ItemStyle-HorizontalAlign="Right" />
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </init:GridViewEx>
                <asp:ObjectDataSource ID="ObjectDataSourceCCDetermTipoCalcolo" runat="server" OldValuesParameterFormatString="original_{0}"
                    SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCDetermTipoCalcoloMgr" SortParameterName="sortExpression">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:ControlParameter ControlID="txtSrcCodice" Name="id" PropertyName="ValoreInt"
                            Type="Int32" />
                        <asp:ControlParameter ControlID="ddlSrcInterventoBase" Name="FkOccbtiId" PropertyName="SelectedValue"
                            Type="String" />
                        <asp:ControlParameter ControlID="ddlSrcDestinazioneBase" Name="FkOccbdeId" PropertyName="SelectedValue"
                            Type="String" />
                        <asp:ControlParameter ControlID="ddlSrcCalcoloBase" Name="FkCcbtcId" PropertyName="SelectedValue"
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
				<div>
					<asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblCodice" />
					<asp:Label runat="server" ID="lblCodice" />
				</div>
				<div>
					<asp:Label runat="server" ID="label8" Text="*Intervento di base" AssociatedControlID="ddlInterventoBase" />
					<asp:DropDownList runat="server" ID="ddlInterventoBase" DataValueField="ID" DataTextField="INTERVENTO"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label10" Text="*Destinazione di base" AssociatedControlID="ddlDestinazioneBase" />
					<asp:DropDownList runat="server" ID="ddlDestinazioneBase" DataValueField="ID" DataTextField="DESTINAZIONE"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label11" Text="*Calcolo di base" AssociatedControlID="ddlCalcoloBase" />
					<asp:DropDownList runat="server" ID="ddlCalcoloBase" DataValueField="ID" DataTextField="TIPOCALCOLO"/>
				</div>
				<div>
					<asp:Label runat="server" ID="label12" Text="*Priorità" AssociatedControlID="txtPriorita" />
					<init:IntTextBox runat="server" ID="txtPriorita" Columns="3" />
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