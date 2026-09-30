<%@ Reference Control="~/Controls/Help.ascx" %>
<%@ Register TagPrefix="uc1" TagName="Help" Src="Controls/Help.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.Help" Codebehind="Help.aspx.cs" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>Help</title>
		<meta name="vs_snapToGrid" content="False">
		<meta name="vs_showGrid" content="True">
		<meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
		<meta name="CODE_LANGUAGE" Content="C#">
		<meta name="vs_defaultClientScript" content="JavaScript">
		<meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
	</HEAD>
	<body>
		<form id="Form1" method="post" runat="server">
			<TABLE id="Table1" cellSpacing="1" cellPadding="1" width="100%" border="0">
				<TR>
					<TD width="30%">
						<asp:TextBox id="TextBox1" runat="server" Width="401px" Height="149px" TextMode="MultiLine"></asp:TextBox></TD>
					<TD vAlign="top">
						<uc1:Help id="Help1" runat="server"></uc1:Help></TD>
				</TR>
			</TABLE>
		</form>
	</body>
</HTML>
