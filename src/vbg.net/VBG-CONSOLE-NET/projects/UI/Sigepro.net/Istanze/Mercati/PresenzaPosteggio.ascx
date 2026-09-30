<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="PresenzaPosteggio.ascx.cs" Inherits="Sigepro.net.Istanze.Mercati.PresenzaPosteggio" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<table width="100%" cellpadding="0" cellspacing="0" height="100%" border="0">
    <tr>
	    <td width="96%">
	        <init:IntTextBox ID="txIdPosteggio" runat="server" Columns="4" Visible="false"></init:IntTextBox>
            <asp:Label ID="lblPosteggio" runat="server" Font-Bold="true"></asp:Label></td>
        <td width="16px" nowrap align="right">
            <asp:Image ID="imNote" runat="server" AlternateText="" ImageUrl="~/images/lettera_n.gif" /></td>
        <td width="16px" nowrap align="right">
            <asp:Image ID="imMerceologie" runat="server" AlternateText="" ImageUrl="~/images/lettera_m.gif" /></td>
    </tr>
	<tr>
		<td colspan="3">
            <asp:Panel ID="pnlOccupante" runat="server">
		        <table border="0" cellpadding="0" cellspacing="0">
		            <colgroup width="20%"></colgroup>
		            <colgroup width="80%"></colgroup>
		            <tr>
		                <td>
                            <init:IntTextBox ID="txtCodiceAnagrafe" runat="server" Columns="4" Visible="false"></init:IntTextBox>
		                    <asp:Label ID="lblDescOccupante" runat="server" Text="Occupante:"></asp:Label>
		                </td>
		                <td><asp:Label ID="lblOccupante" runat="server" Text=""></asp:Label></td>
		            </tr>
		            <tr>
		                <td><asp:Label ID="lblDescPresenze" runat="server" Text="Presenze:"></asp:Label></td>
		                <td><init:IntTextBox ID="txPresenze" runat="server" Columns="4"></init:IntTextBox></td>
		            </tr>
		        </table>
            </asp:Panel>
		</td>
	</tr>
</table>