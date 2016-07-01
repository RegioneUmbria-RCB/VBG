<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="OIModificaNote.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione.OIModificaNote" Title="Modifica note riduzioni/incrementi" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
<script type="text/javascript">
	function Close()
	{
		window.returnValue = false;
		self.close();
	}
</script>

<fieldset>
<div>
	<asp:Label runat="server" AssociatedControlID="txtNote">Note</asp:Label>
<asp:TextBox runat="server" ID="txtNote" Columns="60" TextMode="MultiLine" Rows="5" ></asp:TextBox>
</div>

	<div class="Bottoni">
		<init:SigeproButton ID="cmdSalva" runat="server" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
		<init:SigeproButton ID="cmdChiudi" runat="server" OnClientClick="Close();return false;" IdRisorsa="CHIUDI" />
	</div>
</fieldset>	
</asp:Content>
