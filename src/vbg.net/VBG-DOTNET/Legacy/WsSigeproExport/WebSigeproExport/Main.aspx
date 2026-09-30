<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.Main" ValidateRequest="false" Codebehind="Main.aspx.cs" %>
<%@ Register TagPrefix="cc1" Namespace="skmMenu" Assembly="skmMenu" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>Main</title>
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
					<td>
						<uc1:SiteFooter id="SiteFooter1" runat="server"></uc1:SiteFooter></td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
