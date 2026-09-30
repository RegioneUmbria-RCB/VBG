<%@ Register TagPrefix="uc1" TagName="SiteHeader" Src="Controls/SiteHeader.ascx" %>
<%@ Register TagPrefix="uc1" TagName="SiteFooter" Src="Controls/SiteFooter.ascx" %>
<%@ Page language="c#" Inherits="WebSigeproExport.Exp" ValidateRequest="false" Codebehind="Exp.aspx.cs" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>Exp</title>
		<meta content="Microsoft Visual Studio .NET 7.1" name="GENERATOR">
		<meta content="C#" name="CODE_LANGUAGE">
		<meta content="JavaScript" name="vs_defaultClientScript">
		<meta content="http://schemas.microsoft.com/intellisense/ie5" name="vs_targetSchema">
		<LINK href="./Styles/SigExpStyle.css" type="text/css" rel="stylesheet">
		<script language="JavaScript" src="./Script/JavaScript.js">
		</script>
                <script language="javascript">
                    function MostraPannello(bVisible) {
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
				<tr><td><uc1:siteheader id="SiteHeader1" runat="server"></uc1:siteheader></td></tr>
				<tr><td><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle" Width="144px"> Esportazione</asp:label></td></tr>
                <tr><td><br /></td></tr>
                <tr>
                    <td>
						<table width="100%" border="0">
							<tr>
								<td><asp:label id="LblID" runat="server" CssClass="FoLabel" Width="80px">ID </asp:label></td>
								<td><asp:textbox id="TxtID" runat="server" CssClass="FoTextBoxNum" Width="40px" MaxLength="4" ReadOnly="True"></asp:textbox></td>
							</tr>
							<tr>
								<td><asp:label id="lblIdcomune" runat="server" CssClass="FoLabel" Width="80px">IDComune</asp:label></td>
								<td><asp:textbox id="txtIdcomune" runat="server" CssClass="FoTextBox" Width="80px" MaxLength="6"  ></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator5" runat="server" CssClass="FoTextBox" ControlToValidate="txtIdcomune"
										ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
							</tr>
							<tr>
								<td><asp:label id="LblDesc" runat="server" CssClass="FoLabel" Width="112px">Descrizione </asp:label></td>
								<td><asp:textbox id="TxtDesc" runat="server" CssClass="FoTextBox" Width="360px" MaxLength="50"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator1" runat="server" CssClass="FoTextBox" ControlToValidate="TxtDesc"
										ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
							</tr>
							<tr>
								<td width="15%"><asp:label id="LblInXsd" runat="server" CssClass="FoLabel" Width="112px">File Xsd </asp:label></td>
								<td width="85%"><asp:textbox id="TxtXsd" runat="server" CssClass="FoTextBox" Width="360px" MaxLength="2000" Height="88px"
										TextMode="MultiLine"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator3" runat="server" CssClass="FoTextBox" ControlToValidate="TxtXsd"
										ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
							</tr>
							<tr>
								<td><asp:label id="LblFileName" runat="server" CssClass="FoLabel" Width="112px">File di output </asp:label></td>
								<td><asp:textbox id="TxtFileName" runat="server" CssClass="FoTextBox" Width="248px" MaxLength="40"></asp:textbox><asp:requiredfieldvalidator id="RequiredFieldValidator2" runat="server" CssClass="FoTextBox" ControlToValidate="TxtFileName"
										ErrorMessage="Questo campo non può essere nullo"></asp:requiredfieldvalidator></td>
							</tr>
							<tr>
								<td><asp:label id="LlbType" runat="server" CssClass="FoLabel" Width="120px">Tipo di esportazione </asp:label></td>
								<td><asp:dropdownlist id="DDLstType" runat="server" CssClass="FoTextBox" Width="216px" AutoPostBack="True" onselectedindexchanged="DDLstType_SelectedIndexChanged"></asp:dropdownlist></td>
							</tr>
							<tr>
								<td><asp:label id="LblTypeContext" runat="server" CssClass="FoLabel" Width="120px">Tipo di contesto </asp:label></td>
								<td><asp:dropdownlist id="DDLstTypeContext" runat="server" CssClass="FoTextBox" Width="216px" AutoPostBack="True"></asp:dropdownlist></td>
							</tr>
							<tr>
								<td><asp:label id="LblXmlTag" runat="server" CssClass="FoLabel" Width="112px" Visible="False">Xml tag </asp:label></td>
								<td><asp:textbox id="TxtXmlTag" runat="server" CssClass="FoTextBox" Width="248px" MaxLength="50"
										Visible="False"></asp:textbox>
									<asp:RequiredFieldValidator id="RequiredFieldValidator4" runat="server" ErrorMessage="Questo campo non può essere nullo"
										ControlToValidate="TxtXmlTag" CssClass="FoTextBox"></asp:RequiredFieldValidator></td>
							</tr>
							<tr>
								<td>
									<asp:Label id="LblAnnDati" runat="server" Width="104px" CssClass="FoLabel">Annullare dati?</asp:Label></td>
								<td>
									<asp:CheckBox id="ChckAnnDati" runat="server" ToolTip="Se spuntato tutti i dati (tranne quelli di input) vengono annullati al termine dell'elaborazione di ogni pratica"
										Width="296px"></asp:CheckBox></td>
							</tr>
							<tr>
								<td>
									<asp:Label id="LblInsNull" runat="server" CssClass="FoLabel">Inserire nulli?</asp:Label></td>
								<td>
									<asp:CheckBox id="ChckInsNull" runat="server" ToolTip="Se spuntato inserisce un racord null se la query non ha restituito alcun valore"
										Width="256px"></asp:CheckBox></td>
							</tr>
							<tr>
								<td>
									<asp:Label id="LblFlgAbilitata" runat="server" CssClass="FoLabel">Esportazione abilitata?</asp:Label></td>
								<td>
									<asp:CheckBox id="ChckFlgAbilitata" runat="server" ToolTip="Se spuntato l'esportazione sarà utlizzabile dall'ente" Width="256px"></asp:CheckBox></td>
							</tr>
						</table>
						<br />
						<asp:button id="BtnSalva" runat="server" CssClass="Buttons" Text="Salva" onclick="BtnSalva_Click"></asp:button>&nbsp;&nbsp;&nbsp;
						<asp:button id="BtnPersonalizza" runat="server" CssClass="Buttons" Text="Copia" onclick="BtnPersonalizza_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnParametri" runat="server" CssClass="Buttons" Text="Parametri" CausesValidation="False" onclick="BtnParametri_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnTracciati" runat="server" CssClass="Buttons" Text="Tracciati" CausesValidation="False" onclick="BtnTracciati_Click1"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnElimina" runat="server" CssClass="Buttons" Text="Elimina" CausesValidation="False" onclick="BtnElimina_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnExport" runat="server" CssClass="Buttons" Text="Esporta" CausesValidation="False" onclick="BtnExport_Click"></asp:button>&nbsp;&nbsp;&nbsp;
                        <asp:button id="BtnImport" runat="server" CssClass="Buttons" Text="Importa" CausesValidation="False" onclick="BtnImport_Click"></asp:button>&nbsp;&nbsp;&nbsp;
						<asp:button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" CausesValidation="False" onclick="BtnChiudi_Click"></asp:button></td>
				</tr>
                <tr>
                    <td>
                        <asp:Panel ID="pnlEnte" runat="server">
                            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                <tr align="right">
                                    <td><asp:ImageButton ID="imgChiudi" runat="server" ImageUrl="~/Images/pulsante_chiudi.gif" onclick="imgChiudi_Click" CausesValidation="false"/></td>
                                </tr>
                                <tr>
                                    <td><asp:TextBox ID="txtEnte" runat="server" Width="80px"></asp:TextBox><asp:Label id="lblEnteReplicato" runat="server" CssClass="SectionTitle">&nbsp;&nbsp;Indicare l&#39;idcomune nel quale si intende copiare o importare l&#39;esportazione</asp:Label></td>
                                </tr>
                                <tr>
                                    <td>
                                    <asp:FileUpload runat="server" ID="fuFileupload" /><asp:Label id="Label1" runat="server" CssClass="SectionTitle">Selezionare il file di configurazione XML da utilizzare per creare l'esportazione</asp:Label>
                                    </td>
                                </tr>
                                <tr>
                                    <td><asp:Button ID="btnConfermaReplica" runat="server" Text="Ok" CssClass="Buttons" onclick="btnConfermaReplica_Click" CausesValidation="false"/></td>
                                </tr>
                            </table>
                        </asp:Panel>
                    </td>
                </tr>
				<tr>
					<td><uc1:sitefooter id="SiteFooter1" runat="server"></uc1:sitefooter></td>
				</tr>
			</table>
		</form>
	</body>
</HTML>
