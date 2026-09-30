<%@ Page language="c#" Inherits="WebSigeproExport.Parametri" ValidateRequest="false" Codebehind="Parametro.aspx.cs" %>
<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>Parametro</title>
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
                <tr><td><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle">Configurazione parametri</asp:label></td></tr>
                <tr><td><asp:label id="LlbExp" runat="server" CssClass="SectionTitle">Esportazione:&nbsp;</asp:label></td></tr>
                <tr><td><br /></td></tr>
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td style="WIDTH: 140px"><asp:label id="LblID" runat="server" Width="104px" CssClass="FoLabel">ID </asp:label></td>
								<td><asp:textbox id="TxtID" runat="server" Width="40px" CssClass="FoTextBoxNum" ReadOnly="True"></asp:textbox></td>
							</tr>
							<tr>
								<td style="WIDTH: 140px"><asp:label id="lblIDCOMUNE" runat="server" Width="112px" CssClass="FoLabel">IDComune </asp:label></td>
								<td>
                                    <asp:textbox id="txtIdcomune" runat="server" Width="80px" CssClass="FoTextBox" MaxLength="6" ReadOnly="True"></asp:textbox>
                                    <asp:requiredfieldvalidator id="rfvIdComune" runat="server" CssClass="FoTextBox" ErrorMessage="Questo campo non può essere nullo" ControlToValidate="txtIdcomune"></asp:requiredfieldvalidator></td>
							</tr>
							<tr>
								<td style="WIDTH: 140px"><asp:label id="LblNome" runat="server" Width="112px" CssClass="FoLabel">Nome </asp:label></td>
								<td><asp:textbox id="TxtNome" runat="server" Width="300px" CssClass="FoTextBox" MaxLength="50"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator1" runat="server" CssClass="FoTextBox" ErrorMessage="Questo campo non può essere nullo"
										ControlToValidate="TxtNome"></asp:requiredfieldvalidator></td>
							</tr>
							<tr>
								<td style="WIDTH: 140px"><asp:label id="LblDesc" runat="server" Width="112px" CssClass="FoLabel">Descrizione </asp:label></td>
								<td><asp:textbox id="TxtDesc" runat="server" Width="600px" CssClass="FoTextBox" MaxLength="500"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator2" runat="server" CssClass="FoTextBox" ErrorMessage="Questo campo non può essere nullo"
										ControlToValidate="TxtDesc"></asp:requiredfieldvalidator></td>
							</tr>
						</table>
						<br>
						<asp:button id="BtnSalva" runat="server" CssClass="Buttons" Text="Salva" onclick="BtnSalva_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnNuovo" runat="server" CssClass="Buttons" Text="Nuovo" onclick="BtnNuovo_Click"></asp:button>&nbsp;&nbsp;&nbsp;
						<asp:button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" CausesValidation="False" onclick="BtnChiudi_Click"></asp:button></td>
				</tr>
				<tr>
					<td><uc1:sitefooter id="SiteFooter1" runat="server"></uc1:sitefooter></td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
