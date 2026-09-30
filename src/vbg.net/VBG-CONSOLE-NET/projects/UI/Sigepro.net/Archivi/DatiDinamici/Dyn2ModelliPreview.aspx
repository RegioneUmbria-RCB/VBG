<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="Dyn2ModelliPreview.aspx.cs" Inherits="Sigepro.net.Archivi.DatiDinamici.Dyn2ModelliPreview" Title="Anteprima modello" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>
<%@ Register tagPrefix="init" namespace="Init.SIGePro.DatiDinamici.WebControls" assembly="SIGePro.DatiDinamici"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
			<asp:ScriptManager ID="ScriptManager1" runat="server" >
			</asp:ScriptManager>
	<div class="ContenutoScheda">
	<init:ModelloDinamicoRenderer ID="ModelloDinamicoRenderer1" runat="server" ReadOnly="True" Preview="True" />
	</div>
	<div class="Bottoni">
		<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI"  OnClientClick="javascript:self.close();" />
	</div>
</asp:Content>