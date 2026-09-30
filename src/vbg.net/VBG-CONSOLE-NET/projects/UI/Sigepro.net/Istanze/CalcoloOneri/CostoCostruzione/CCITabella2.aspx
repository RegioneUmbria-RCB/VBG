<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCITabella2.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.Tabella2" Title="Tabella 2 - Superfici per servizi accessori relativi alla parte residenziale" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>




<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<asp:GridView runat="server" ID="gvDettagli" DataKeyNames="Id" AutoGenerateColumns="false">
	<Columns>
		<asp:BoundField HeaderText="Destinazioni" DataField="DettagliSuperficie" />
				<asp:TemplateField HeaderText="Sup. utile abitabile">
			<ItemTemplate>
				<init:DoubleTextBox runat="server" ID="dtbSuperficie" ValoreDouble='<%# Bind("Superficie") %>'></init:DoubleTextBox>
			</ItemTemplate>
		</asp:TemplateField>
	</Columns>
</asp:GridView>
<fieldset>
	<div class="Bottoni">
		<init:SigeproButton runat="server" ID="cmdProcedi" Text="Procedi" IdRisorsa="PROCEDI" OnClick="cmdProsegui_Click" />
		<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
	</div>
</fieldset>
</asp:Content>
