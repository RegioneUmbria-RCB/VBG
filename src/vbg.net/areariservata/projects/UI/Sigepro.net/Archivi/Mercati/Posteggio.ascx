<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="Posteggio.ascx.cs" Inherits="Sigepro.net.Archivi.Mercati.Posteggio" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
	    <td width="96%"><asp:Label ID="lblPosteggio" runat="server"></asp:Label></td>
        <td width="16px" nowrap align="right"><asp:Image ID="imNote" runat="server" AlternateText="" ImageUrl="~/images/lettera_n.gif" /></td>
        <td width="16px" nowrap align="right"><asp:Image ID="imMerceologie" runat="server" AlternateText="" ImageUrl="~/images/lettera_m.gif" /></td>
    </tr>
	<tr>
	    <td colspan="3"><asp:Label ID="lblIntestazione" runat="server" Text="Concessionari" CssClass="concessionari"></asp:Label></td>
	</tr>
	<tr>
		<td colspan="2">
            <asp:GridView ID="gvOccupanti" runat="server" AutoGenerateColumns="False" CellPadding="0" >
                <Columns>
                    <asp:BoundField HeaderText="Giorno" DataField="Uso"/>
                    <asp:BoundField HeaderText="Occupante" DataField="Nominativo"/>
                </Columns>
            </asp:GridView>
        </td>
        <td valign="baseline"><asp:Image ID="imgStorico" runat="server" AlternateText="Visualizza la storia del posteggio" ImageUrl="~/images/lettera_s.gif" /></td>
	</tr>
</table>