<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCModificaNote.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.CCModificaNote" Title="Modifica note riduzioni/incrementi" %>

<%@ Register Assembly="SIGePro.WebControls" Namespace="SIGePro.WebControls.UI" TagPrefix="cc2" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc1" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
<script type="text/javascript">
	function Close()
	{
		window.returnValue = false;
		self.close();
	}
</script>

<b>Note</b><br />
<asp:TextBox runat="server" ID="txtNote" Columns="60" TextMode="MultiLine" Rows="5" ></asp:TextBox>
<br />
<fieldset>
	<cc2:SigeproButton ID="cmdSalva" runat="server" IdRisorsa="SALVA" OnClick="cmdSalva_Click"/>
	<cc2:SigeproButton ID="cmdChiudi" runat="server" OnClientClick="Close();return false;" IdRisorsa="CHIUDI" />
	
</fieldset>
</asp:Content>
