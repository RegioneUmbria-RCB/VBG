<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Codebehind="OIModificaRiduzioni.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificaRiduzioni"
	Title="Modifica riduzioni/incrementi" %>

<%@ Register Src="OIModificariduzioniCtrl.ascx" TagName="OIModificariduzioniCtrl" TagPrefix="uc1" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>
	<asp:Repeater runat="server" ID="rptTipiCausali" OnItemDataBound="rptTipiCausali_ItemDataBound">
		<HeaderTemplate>
		</HeaderTemplate>
		<ItemTemplate>
			<div class="IntestazioneTabella" style="width: 98%">
				<%#DataBinder.Eval(Container.DataItem , "Descrizione") %>
			</div>
			<uc1:OIModificariduzioniCtrl ID="modificariduzioniCtrl" runat="server"></uc1:OIModificariduzioniCtrl>
		</ItemTemplate>
		<FooterTemplate>
		</FooterTemplate>
	</asp:Repeater>
	<asp:UpdatePanel ID="UpdatePanel1" runat="server">
		<ContentTemplate>
			<init:SigeproButton ID="cmdSalva" runat="server" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
			<init:SigeproButton ID="cmdChiudi" runat="server" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
		</ContentTemplate>
	</asp:UpdatePanel>
</asp:Content>
