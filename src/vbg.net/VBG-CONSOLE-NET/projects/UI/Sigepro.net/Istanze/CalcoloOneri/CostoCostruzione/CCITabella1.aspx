<%@ Page Language="C#"  MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCITabella1.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.Tabella1" Title="Tabella 1 - Incremento per superficie abitabile" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<asp:GridView runat="server" ID="gvDettagli" AutoGenerateColumns="False" DataKeyNames="Id">
	<Columns>
		<asp:BoundField HeaderText="Classi di sup. mq" DataField="ClassiSuperfici" />
		<asp:TemplateField HeaderText="Alloggi">
			<ItemTemplate>
				<init:IntTextBox runat="server" ID="itbAlloggi" ValoreInt='<%# Bind("Alloggi") %>'></init:IntTextBox>
			</ItemTemplate>
		</asp:TemplateField>
		<asp:TemplateField HeaderText="Sup. utile abitabile">
			<ItemTemplate>
				<init:DoubleTextBox runat="server" ID="dtbSu" ValoreDouble='<%# Bind("Su") %>'></init:DoubleTextBox>
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