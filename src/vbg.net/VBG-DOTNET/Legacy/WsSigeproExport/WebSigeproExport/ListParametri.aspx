<%@ Page language="c#" Inherits="WebSigeproExport.ListParametri" ValidateRequest="false" Codebehind="ListParametri.aspx.cs" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>ListParametri</title>
		<meta content="Microsoft Visual Studio .NET 7.1" name="GENERATOR">
		<meta content="C#" name="CODE_LANGUAGE">
		<meta content="JavaScript" name="vs_defaultClientScript">
		<meta content="http://schemas.microsoft.com/intellisense/ie5" name="vs_targetSchema">
		<LINK href="./Styles/SigExpStyle.css" type="text/css" rel="stylesheet">
		<script language="JavaScript" src="./Script/JavaScript.js">
		</script>
	</HEAD>
	<body>
		<form id="Form1" method="post" runat="server">
			<table width="100%">
				<tr><td><uc1:siteheader id="SiteHeader1" runat="server"></uc1:siteheader></td></tr>
                <tr><td><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle" Width="144px">Lista Parametri</asp:label></td></tr>
                <tr><td><asp:Label id="LblParametri" runat="server" CssClass="SectionTitle">Esportazione:&nbsp;</asp:Label></td></tr>
                <tr><td><br /></td></tr>
				<tr>
					<td>
						<asp:datagrid id="DataGridParametri" runat="server" Width="100%" BorderWidth="1px" CellPadding="1"
							PageSize="20" AllowPaging="True" AutoGenerateColumns="False" DataKeyField="ID" onselectedindexchanged="DataGridParametri_SelectedIndexChanged">
							<AlternatingItemStyle HorizontalAlign="Center" CssClass="AlternatingItemStyle"></AlternatingItemStyle>
							<ItemStyle HorizontalAlign="Center" CssClass="ItemStyle"></ItemStyle>
							<HeaderStyle HorizontalAlign="Center" CssClass="HeaderStyle"></HeaderStyle>
							<Columns>
								<asp:BoundColumn DataField="ID" HeaderText="ID">
									<ItemStyle HorizontalAlign="Right"></ItemStyle>
								</asp:BoundColumn>
								<asp:BoundColumn DataField="IDCOMUNE" HeaderText="IDCOMUNE">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:BoundColumn DataField="NOME" HeaderText="Nome">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:BoundColumn DataField="DESCRIZIONE" HeaderText="Descrizione">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:ButtonColumn Text="Visualizza" CommandName="Select"></asp:ButtonColumn>
								<asp:ButtonColumn Text="Elimina" CommandName="Delete"></asp:ButtonColumn>
							</Columns>
							<PagerStyle CssClass="FoLabel" Mode="NumericPages"></PagerStyle>
						</asp:datagrid><br>
						<asp:button id="BtnAgg" runat="server" CssClass="Buttons" Text="Aggiungi" onclick="BtnAgg_Click"></asp:button>&nbsp;&nbsp;&nbsp;
						<asp:button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" onclick="BtnChiudi_Click"></asp:button></td>
				</tr>
				<tr>
					<td><uc1:sitefooter id="SiteFooter1" runat="server"></uc1:sitefooter></td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
