<%@ Control Language="c#" Inherits="WebSigeproExport.Controls.SiteHeader" Codebehind="SiteHeader.ascx.cs" %>
<table width="100%" border="0" cellspacing="0">
	<tr>
		<td class="hrTop" Height="3" width="60%">
			<asp:Image id="Image2" Height="3px" runat="server" ImageUrl="../Images/spacer.gif"></asp:Image>
		</td>
        <td class="hrTop" Height="3" width="60%" align="right">
            <asp:Label ID="lblVersione" runat="server" Text=""></asp:Label>
        </td>
	</tr>
	<tr>
		<td colspan="2">
            <a href="SelectEnte.aspx">
			    <asp:Image id="Image1" runat="server" ImageUrl="../Images/logo.gif" ToolTip="Vai alla home!"></asp:Image>
            </a>
		</td>
	</tr>
</table>
