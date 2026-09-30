<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.SelectEnte" ValidateRequest="false" Codebehind="SelectEnte.aspx.cs" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>SelectEnte</title>
		<meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
		<meta name="CODE_LANGUAGE" Content="C#">
		<meta name="vs_defaultClientScript" content="JavaScript">
		<meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
		<LINK href="./Styles/SigExpStyle.css" type="text/css" rel="stylesheet">
	</HEAD>
	<body>
		<form id="Form1" method="post" runat="server">
			<table width="100%">
				<tr>
					<td>
						<uc1:SiteHeader id="SiteHeader1" runat="server"></uc1:SiteHeader></td>
				</tr>
				<tr>
					<td style="HEIGHT: 103px"><asp:label id="LlbTitle" runat="server" Width="144px" CssClass="SectionTitle">Ricerca Ente</asp:label><br>
						<br>
						<table width="100%">
							<tr>
								<td style="WIDTH: 63px"><asp:label id="Label1" runat="server" Width="60px" CssClass="FoLabel">Esportazione </asp:label></td>
								<td>
									<asp:DropDownList id="DDLstExp" runat="server"></asp:DropDownList></td>
							</tr>
						</table>
						<br>
						<asp:Button id="BtnNuovo" runat="server" CssClass="Buttons" Text="Nuovo" CausesValidation="False" onclick="BtnNuovo_Click"></asp:Button>&nbsp;&nbsp;&nbsp;
                        <asp:Button id="BtnCerca" runat="server" CssClass="Buttons" Text="Visualizza" onclick="BtnCerca_Click"></asp:Button>&nbsp;&nbsp;&nbsp;
						<asp:Button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" CausesValidation="False" onclick="BtnChiudi_Click"></asp:Button>
                        
                        </td>
				</tr>
				<tr>
					<td>
						<uc1:SiteFooter id="SiteFooter1" runat="server"></uc1:SiteFooter>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
