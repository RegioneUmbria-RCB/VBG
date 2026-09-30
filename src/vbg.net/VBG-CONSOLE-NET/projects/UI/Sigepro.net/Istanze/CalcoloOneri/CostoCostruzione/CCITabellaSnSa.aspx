<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCITabellaSnSa.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.CCITabellaSnSa" Title="Superfici per attività turistiche commerciali e direzionali e relativi accessori" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
  <table>
        <colgroup width="70%" />
        <colgroup width="30%" />
        <tr>
            <th>
                Denominazione</th>
            <th>
                Superficie (mq)</th>
        </tr>
        
        <tr>
            <td>
                Superficie netta non residenziale</td>
            <td>
    
   <init:DoubleTextBox runat="server" ID="dtbSuArt9" ></init:DoubleTextBox></td>
        </tr>
        <tr>
            <td>
                Superficie accessori</td>
            <td>
				<init:DoubleTextBox runat="server" ID="dtbSa"></init:DoubleTextBox>
            </td>
        </tr>
        
    </table>
	<fieldset>
		<div class="Bottoni">
			<init:SigeproButton runat="server" ID="cmdProcedi" Text="Procedi" IdRisorsa="PROCEDI" OnClick="cmdProsegui_Click" />
			<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
		</div>
	</fieldset>
</asp:Content>
