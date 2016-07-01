<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Istanze_CalcoloOneri_CostoCostruzione_CCITabella4" Title="Tabella 4 - Incremento per particolari caratteristiche" Codebehind="CCITabella4.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
	<asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="False" DataSourceID="Tab4DataSource" DataKeyNames="Id" OnRowDataBound="GridView1_RowDataBound">
		<Columns>
			<asp:TemplateField HeaderText="Numero di caratteristiche" SortExpression="FkCcicId">
				<EditItemTemplate>
					<asp:TextBox ID="TextBox1" runat="server" Text='<%# Bind("FkCcicId") %>'></asp:TextBox>
				</EditItemTemplate>
				<ItemTemplate>
					<asp:Label ID="lblDescrizioneCaratteristica" runat="server" Text='<%# Bind("FkCcicId") %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:TemplateField HeaderText="Ipotesi che ricorre" SortExpression="Selezionata" ItemStyle-HorizontalAlign="Center">
				<ItemTemplate>
					<asp:CheckBox ID="chkSelezionata" runat="server" Checked='<%# DataBinder.Eval( Container.DataItem , "Selezionata" ).ToString() == "1"%>'/>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:BoundField DataField="Incremento" DataFormatString="{0:N2} %" HeaderText="% Incremento" HtmlEncode="False" SortExpression="Incremento"  ItemStyle-HorizontalAlign="Right" />
		</Columns>
	</asp:GridView>
	<asp:ObjectDataSource ID="Tab4DataSource" runat="server" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCITabella4Mgr">
		<SelectParameters>
			<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
			<asp:QueryStringParameter Name="idCalcolo" QueryStringField="IdCalcolo" Type="Int32" />
		</SelectParameters>
	</asp:ObjectDataSource>
	<fieldset>
		<div class="Bottoni">
			<init:SigeproButton runat="server" ID="cmdProcedi" Text="Procedi" IdRisorsa="PROCEDI" OnClick="cmdProcedi_Click" />
			<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
		</div>
	</fieldset>


</asp:Content>

