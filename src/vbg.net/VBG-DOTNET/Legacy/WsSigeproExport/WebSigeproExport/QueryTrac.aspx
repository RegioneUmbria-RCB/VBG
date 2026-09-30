<%@ Reference Control="~/Controls/Help.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.QueryTrac"  ValidateRequest="false" Codebehind="QueryTrac.aspx.cs" %>
<%@ Register TagPrefix="uc1" TagName="Help" Src="Controls/Help.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>QueryTrac</title>
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
				<tr>
					<td colspan="3"><uc1:siteheader id="SiteHeader1" runat="server"></uc1:siteheader></td>
				</tr>
                <tr><td colspan="3"><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle">Configurazione tracciato</asp:label></td></tr>
                <tr><td colspan="3"><asp:label id="LblEnte" runat="server" CssClass="SectionTitle">Ente:&nbsp;</asp:label></td></tr>
                <tr><td colspan="3"><asp:label id="LlbExp" runat="server" CssClass="SectionTitle">Esportazione:&nbsp;</asp:label></td></tr>
                <tr><td colspan="3"><br /></td></tr>
                <tr>
					<td><asp:label id="LblID" runat="server" CssClass="FoLabel" Width="104px">ID </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtID" runat="server" CssClass="FoTextBoxNum" Width="40px" ReadOnly="True"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="LblIDCOMUNE" runat="server" CssClass="FoLabel" Width="112px">IDComune</asp:label></td>
					<td colspan="2"><asp:textbox id="TxtIDCOMUNE" runat="server" CssClass="FoTextBox" Width="80px" MaxLength="6" ReadOnly="true"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator4" runat="server" CssClass="FoTextBox" ControlToValidate="TxtIDCOMUNE"
							ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
				</tr>
				<tr>
					<td><asp:label id="LblDescBreve" runat="server" CssClass="FoLabel" Width="112px">Desc. Breve </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtDescBreve" runat="server" CssClass="FoTextBox" Width="152px" MaxLength="20"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator1" runat="server" CssClass="FoTextBox" ControlToValidate="TxtDescBreve"
							ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
				</tr>
				<tr>
					<td><asp:label id="LblDesc" runat="server" CssClass="FoLabel" Width="112px">Descrizione </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtDesc" runat="server" CssClass="FoTextBox" Width="360px" MaxLength="100"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator2" runat="server" CssClass="FoTextBox" ControlToValidate="TxtDesc"
							ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
				</tr>
				<tr>
					<td><asp:label id="LblOrdine" runat="server" CssClass="FoLabel" Width="112px">Ordine </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtOrdine" runat="server" CssClass="FoTextBoxNum" Width="40px" MaxLength="2"></asp:textbox><asp:comparevalidator id="CompareValidator1" runat="server" CssClass="FoTextBox" ControlToValidate="TxtOrdine"
							ErrorMessage="Questo campo deve essere numerico" Type="Integer" Operator="DataTypeCheck"></asp:comparevalidator>
						<asp:RequiredFieldValidator id="RequiredFieldValidator3" runat="server" CssClass="FoTextBox" ControlToValidate="TxtOrdine"
							ErrorMessage="Questo campo non può essere nullo"></asp:RequiredFieldValidator></td>
				</tr>
				<tr>
					<td><asp:label id="LblFileName" runat="server" CssClass="FoLabel" Width="112px">File di output </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtFileName" runat="server" CssClass="FoTextBox" Width="152px" MaxLength="30"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="LblXmlTag" runat="server" CssClass="FoLabel" Width="112px">Xml tag </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtXmlTag" runat="server" CssClass="FoTextBox" Width="152px" MaxLength="50"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="LblTypeTrac" runat="server" CssClass="FoLabel">Tipo</asp:label></td>
					<td colspan="2"><asp:dropdownlist id="DDListTypeTrc" runat="server" CssClass="FoTextBox" Width="184px"></asp:dropdownlist></td>
				</tr>
				<tr>
					<td width="15%"><asp:label id="LblQuery" runat="server" CssClass="FoLabel">Query</asp:label></td>
					<td width="75%"><asp:textbox id="TxtQuery" runat="server" Width="100%" CssClass="FoTextBox" Height="100%" TextMode="MultiLine" MaxLength="3000"></asp:textbox></td>
                    <td width="10%"><uc1:help id="Help1" runat="server"></uc1:help></td>
				</tr>
    			<tr>
					<td colspan="3">
                        <asp:button id="BtnSalva" runat="server" CssClass="Buttons" Text="Salva" onclick="BtnSalva_Click"></asp:button>&nbsp;&nbsp;&nbsp;
						<asp:button id="BtnQryDettTrac" runat="server" CssClass="Buttons" Text="Dettaglio Tracciato" CausesValidation="False" onclick="BtnQryDettTrac_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnElimina" runat="server" CssClass="Buttons" Text="Elimina" CausesValidation="False" onclick="BtnElimina_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" CausesValidation="False" onclick="BtnChiudi_Click"></asp:button>
								    
                    </td>
				</tr>
				<tr>
					<td colspan="3"><uc1:sitefooter id="SiteFooter1" runat="server"></uc1:sitefooter></td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
