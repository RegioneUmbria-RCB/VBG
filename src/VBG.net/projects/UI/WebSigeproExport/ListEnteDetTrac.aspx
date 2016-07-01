<%@ Page Language="c#"  Inherits="WebSigeproExport.ListEnteDetTrac" ValidateRequest="false" Codebehind="ListEnteDetTrac.aspx.cs" %>
<%@ Register Src="Controls/siteheader.ascx" TagName="siteheader" TagPrefix="uc1" %>
<%@ Register Src="Controls/sitefooter.ascx" TagName="sitefooter" TagPrefix="uc2" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<title>ListEnteDetTrac</title>
		<meta content="Microsoft Visual Studio .NET 7.1" name="GENERATOR">
		<meta content="C#" name="CODE_LANGUAGE">
		<meta content="JavaScript" name="vs_defaultClientScript">
		<meta content="http://schemas.microsoft.com/intellisense/ie5" name="vs_targetSchema">
		<LINK href="./Styles/SigExpStyle.css" type="text/css" rel="stylesheet">
		<script language="JavaScript" src="./Script/JavaScript.js">
		</script>
	</HEAD>
	<body>
		<form id="form1" runat="server">
			<table width="100%">
				<tr><td><uc1:siteheader id="Siteheader1" runat="server"></uc1:siteheader></td></tr>
                <tr><td><asp:label id="LlbTitle" runat="server" CssClass="SectionTitle">Lista dettagli</asp:label></td></tr>
                <tr><td><asp:label id="LblEnte" runat="server" CssClass="SectionTitle">Ente:&nbsp;</asp:label></td></tr>
                <tr><td><asp:label id="LblExp" runat="server" CssClass="SectionTitle">Esportazione:&nbsp;</asp:label></td></tr>
                <tr><td><asp:label id="LblTrac" runat="server" CssClass="SectionTitle">Tracciato:&nbsp;</asp:label></td></tr>
                <tr><td><asp:label id="LblInfoCSV" runat="server" CssClass="SectionTitle">L'esportazione attuale è configurata per restituire un file CSV; pertanto i separatori e l'invio finale NON devono essere indicati perchè verranno generati automaticamente in fase di export<br /></asp:label></td></tr>
                <tr><td><br /></td></tr>
				<tr>
					<td>
						<asp:datagrid id="DataGridEnteDetTrac" runat="server" Width="100%" DataKeyField="ID" CellPadding="1"
							BorderWidth="1px" PageSize="20" AllowPaging="True" AutoGenerateColumns="False" onselectedindexchanged="DataGridEnteDetTrac_SelectedIndexChanged">
							<AlternatingItemStyle HorizontalAlign="Center" CssClass="AlternatingItemStyle"></AlternatingItemStyle>
							<ItemStyle HorizontalAlign="Center" CssClass="ItemStyle"></ItemStyle>
							<HeaderStyle HorizontalAlign="Center" CssClass="HeaderStyle"></HeaderStyle>
							<Columns>
								<asp:BoundColumn Visible="True" DataField="ID" HeaderText="ID"></asp:BoundColumn>
								<asp:BoundColumn DataField="IDCOMUNE" HeaderText="Ente">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:BoundColumn DataField="DESCRIZIONE" HeaderText="Descrizione">
									<ItemStyle HorizontalAlign="Left"></ItemStyle>
								</asp:BoundColumn>
								<asp:BoundColumn DataField="OBBLIGATORIO" HeaderText="Obbligatorio">
									<ItemStyle HorizontalAlign="Right"></ItemStyle>
								</asp:BoundColumn>
                                <asp:TemplateColumn>
                                    <ItemStyle HorizontalAlign="Left"></ItemStyle>
                                    <HeaderTemplate>QUERY</HeaderTemplate>
                                    <ItemTemplate>
                                        <asp:TextBox ID="txtQuery" runat="server" TextMode="MultiLine" width="100%"></asp:TextBox>
                                    </ItemTemplate>
                                </asp:TemplateColumn>
                                <asp:TemplateColumn>
                                    <ItemStyle HorizontalAlign="Center"></ItemStyle>
                                    <HeaderTemplate>VALORE</HeaderTemplate>
                                    <ItemTemplate>
                                        <asp:TextBox ID="txtValore" runat="server"></asp:TextBox><br />
                                        <asp:CheckBox id="ChkBoxFineRiga" runat="server" Text="Testo a capo"></asp:CheckBox><br />
                                        <asp:CheckBox id="ChkBoxCampoTestuale" runat="server" Text="Qualifica come testo"></asp:CheckBox>
                                    </ItemTemplate>
                                </asp:TemplateColumn>
                                <asp:ButtonColumn Text="Salva" CommandName="Update"></asp:ButtonColumn>
								<asp:ButtonColumn Text="Visualizza" CommandName="Select"></asp:ButtonColumn>
								<asp:ButtonColumn Text="Elimina" CommandName="Delete"></asp:ButtonColumn>
							</Columns>
							<PagerStyle Mode="NumericPages" CssClass="FoLabel"></PagerStyle>
						</asp:datagrid><BR>
                        <asp:button id="BtnNuovo" runat="server" CssClass="Buttons" Text="Nuovo" 
                            onclick="BtnNuovo_Click"></asp:button>&nbsp;&nbsp;&nbsp;
						<asp:button id="BtnChiudi" runat="server" CssClass="Buttons" Text="Chiudi" onclick="BtnChiudi_Click"></asp:button></td>
				</tr>
				<TR>
					<TD><uc2:sitefooter id="Sitefooter1" runat="server"></uc2:sitefooter></TD>
				</TR>
			</table>
		</form>
	</body>
</HTML>
