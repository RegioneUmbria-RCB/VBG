<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.ListEnteTrac" ValidateRequest="false" Codebehind="ListEnteTrac.aspx.cs" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>ListEnteTrac</title>
		<meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
		<meta name="CODE_LANGUAGE" Content="C#">
		<meta name="vs_defaultClientScript" content="JavaScript">
		<meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
		<LINK href="./Styles/SigExpStyle.css" type="text/css" rel="stylesheet">
		<script language="JavaScript" src="./Script/JavaScript.js">
		</script>
        <script language="javascript">
            function MostraPannello( bVisible ) {
                if (bVisible) {
                    document.getElementById("pnlEnte").style.visibility = 'visible';
                } else {
                    document.getElementById("pnlEnte").style.visibility = 'hidden';
                }
                return false;
            }
        </script>
	</HEAD>
	<body>
		<form id="Form1" method="post" runat="server">
			<table width="100%">
				<tr><td><uc1:SiteHeader id="SiteHeader1" runat="server"></uc1:SiteHeader></td></tr>
                <tr><td><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle" Width="208px">Lista Configurazione Tracciati</asp:label></td></tr>
                <tr><td><asp:label id="lblEsportazione" runat="server" CssClass="SectionTitle" Width="100%">Esportazione:&nbsp;</asp:label></td></tr>
                <tr><td><asp:Label id="LblEnte" runat="server" Width="72px" CssClass="SectionTitle">Ente:&nbsp;</asp:Label></td></tr>
                <tr><td><br /></td></tr>
				<tr>
					<td>
						<asp:datagrid id="DataGridEnte" runat="server" Width="100%" DataKeyField="ID" CellPadding="1"
							BorderWidth="1px" PageSize="20" AllowPaging="True" AutoGenerateColumns="False" onselectedindexchanged="DataGridEnte_SelectedIndexChanged">
							<AlternatingItemStyle HorizontalAlign="Center" CssClass="AlternatingItemStyle"></AlternatingItemStyle>
							<ItemStyle HorizontalAlign="Center" CssClass="ItemStyle"></ItemStyle>
							<HeaderStyle HorizontalAlign="Center" CssClass="HeaderStyle"></HeaderStyle>
							<Columns>
								<asp:BoundColumn Visible="False" DataField="ID" HeaderText="ID"></asp:BoundColumn>
								<asp:BoundColumn DataField="IDCOMUNE" HeaderText="Ente">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:BoundColumn DataField="DESCRIZIONE" HeaderText="Tracciati">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:ButtonColumn Text="Visualizza" CommandName="Select"></asp:ButtonColumn>
								<asp:ButtonColumn Text="Elimina" CommandName="Delete"></asp:ButtonColumn>
							</Columns>
							<PagerStyle CssClass="FoLabel" Mode="NumericPages"></PagerStyle>
						</asp:datagrid><br>
                        <asp:button id="BtnNuovoTrac" runat="server" CssClass="Buttons" Text="Nuovo tracciato" onclick="BtnNuovoTrac_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" onclick="BtnChiudi_Click"></asp:button>
                        </td>
				</tr>
                <tr><td><uc1:SiteFooter id="SiteFooter1" runat="server"></uc1:SiteFooter></td></tr>
			</table>
		</form>
	</body>
</HTML>
