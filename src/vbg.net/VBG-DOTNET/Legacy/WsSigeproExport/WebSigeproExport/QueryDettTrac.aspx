<%@ Reference Control="~/Controls/Help.ascx" %>
<%@ Register TagPrefix="uc1" TagName="Help" Src="Controls/Help.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.QueryDettTrac" Codebehind="QueryDettTrac.aspx.cs" %>
<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>QueryDettTrac</title>
		<meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
		<meta name="CODE_LANGUAGE" Content="C#">
		<meta name="vs_defaultClientScript" content="JavaScript">
		<meta name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
		<LINK href="./Styles/SigExpStyle.css" type="text/css" rel="stylesheet">
		<script language="JavaScript" src="./Script/JavaScript.js">
		</script>
	</HEAD>
	<body>
		<form id="Form1" method="post" runat="server">
			<table width="100%">
				<tr><td colspan="3"><uc1:SiteHeader id="SiteHeader1" runat="server"></uc1:SiteHeader></td></tr>
                <tr><td colspan="3"><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle">Configurazione dettagli</asp:label></td></tr>
                <tr><td colspan="3"><asp:Label id="LblEnte" CssClass="SectionTitle" runat="server" Width="96px">Ente:</asp:Label></td></tr>
                <tr><td colspan="3"><asp:label id="LblExp" runat="server" CssClass="SectionTitle">Esportazione:&nbsp;</asp:label></td></tr>
                <tr><td colspan="3"><asp:Label id="LblTrac" runat="server" CssClass="SectionTitle" Width="496px">Tracciato:</asp:Label></td></tr>
				<tr><td colspan="3"><asp:label id="LblInfoCSV" runat="server" CssClass="SectionTitle">L'esportazione attuale è configurata per restituire un file CSV; pertanto i separatori e l'invio finale NON devono essere indicati perchè verranno generati automaticamente in fase di export</asp:label></td></tr>
                <tr><td colspan="3"><br /></td></tr>
                <tr>
					<td><asp:label id="LblID" runat="server" Width="104px" CssClass="FoLabel">ID </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtID" runat="server" Width="48px" CssClass="FoTextBoxNum" ReadOnly="True" MaxLength="6"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="Label1" runat="server" Width="112px" CssClass="FoLabel">IDComune </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtIDCOMUNE" runat="server" Width="80px" CssClass="FoTextBox" MaxLength="6" ReadOnly="true"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="LblDesc" runat="server" Width="112px" CssClass="FoLabel">Descrizione </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtDesc" runat="server" Width="312px" CssClass="FoTextBox" MaxLength="70"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="LblObb" runat="server" Width="112px" CssClass="FoLabel">Obbligatorio </asp:label></td>
					<td colspan="2"><asp:CheckBox id="ChckObbl" runat="server" Width="96px" Height="24px" CssClass="FoText"></asp:CheckBox></td>
				</tr>
				<tr>
					<td><asp:label id="LblOrdine" runat="server" Width="112px" CssClass="FoLabel">Ordine </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtOrdine" runat="server" Width="40px" CssClass="FoTextBoxNum" MaxLength="4"></asp:textbox>
						<asp:CompareValidator id="CompareValidator1" runat="server" ErrorMessage="Questo campo deve essere numerico" Type="Integer" Operator="DataTypeCheck" ControlToValidate="TxtOrdine" CssClass="FoTextBox"></asp:CompareValidator>
						<asp:RequiredFieldValidator id="RequiredFieldValidator1" runat="server" CssClass="FoTextBox" ControlToValidate="TxtOrdine" ErrorMessage="Questo campo non può essere nullo"></asp:RequiredFieldValidator></td>
				</tr>
				<tr>
					<td><asp:label id="LblLungh" runat="server" Width="112px" CssClass="FoLabel" Visible="False">Lunghezza </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtLungh" runat="server" Width="40px" CssClass="FoTextBoxNum" Visible="False"
							MaxLength="4"></asp:textbox>
						<asp:CompareValidator id="CompareValidator2" runat="server" ErrorMessage="Questo campo deve essere numerico"
							Type="Integer" Operator="DataTypeCheck" ControlToValidate="TxtLungh" CssClass="FoTextBox"></asp:CompareValidator></td>
				</tr>
				<tr>
					<td><asp:label id="LblXmlTag" runat="server" Width="112px" CssClass="FoLabel" Visible="False">Xml tag </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtXmlTag" runat="server" Width="144px" CssClass="FoTextBox" Visible="False"
							MaxLength="50"></asp:textbox></td>
				</tr>
				<tr>
					<td><asp:label id="LblNote" runat="server" Width="112px" CssClass="FoLabel">Note </asp:label></td>
					<td colspan="2"><asp:textbox id="TxtNote" runat="server" Width="312px" CssClass="FoTextBox" TextMode="MultiLine" Height="72px" MaxLength="400"></asp:textbox></td>
				</tr>
                <tr>
                    <td width="15%"><asp:label id="LblQuery" runat="server" Width="72px" CssClass="FoLabel">Query </asp:label></td>
    				<td width="75%" valign="top"><asp:TextBox id="TxtQuery" runat="server" Width="100%" TextMode="MultiLine" Height="100%" CssClass="FoTextBox"></asp:TextBox></td>
					<td width="10%" valign="top" align="center"><uc1:Help id="Help1" runat="server"></uc1:Help></td>
				</tr>
				<tr>
					<td vAlign="top"><asp:Label id="LblValore" runat="server" CssClass="FoLabel" Width="56px">Valore</asp:Label></td>
                    <td colspan="2">
                        <table width="100%" cellpadding="0" cellpadding="0" border="0">
                            <tr>
                                <td width="15%" rowspan="2"><asp:TextBox id="TxtValore" runat="server" CssClass="FoTextBox" Width="272px" Height="32px" TextMode="MultiLine"></asp:TextBox></td>
                                <td width="85%"><asp:CheckBox id="ChkBoxFineRiga" runat="server" Text="Carattere fine riga" CssClass="FoTextBox" AutoPostBack="True" oncheckedchanged="ChkBoxFineRiga_CheckedChanged"></asp:CheckBox></td>
                            </tr>
                            <tr>
                                <td><asp:CheckBox id="ChkBoxCampoTestuale" runat="server" Text="Qualifica come testo" CssClass="FoTextBox" AutoPostBack="False"></asp:CheckBox><asp:label id="LabelCampoTestuale" runat="server" CssClass="FoLabel">&nbsp;( in fase di esportazione verrà aggiunto un prefisso <font color="red"><b>"</b></font> e un suffisso <font color="red"><b>"</b></font> per forzare la conversione a campo di testo)</asp:label></td>
                            </tr>
                        </table>                       
                        </td>
				</tr>
    			<tr>
					<td colspan="3">
						<asp:Button id="BtnSalva" runat="server" CssClass="Buttons" Text="Salva" onclick="BtnSalva_Click"></asp:Button>&nbsp;&nbsp;&nbsp;
						<asp:Button id="BtnElimina" runat="server" Text="Elimina" CssClass="Buttons" onclick="BtnElimina_Click" />&nbsp;&nbsp;&nbsp;
						<asp:Button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" CausesValidation="false" onclick="BtnChiudi_Click"></asp:Button>
					</td>
				</tr>
				<tr>
					<td colspan="3"><uc1:SiteFooter id="SiteFooter1" runat="server"></uc1:SiteFooter></td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
