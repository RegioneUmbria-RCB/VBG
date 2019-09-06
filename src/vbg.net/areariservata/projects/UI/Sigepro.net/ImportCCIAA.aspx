<%@ Register TagPrefix="cc1" Namespace="SIGePro.Net.Controls" Assembly="Sigepro.net" %>
<%@ Page language="c#" MasterPageFile="~/SigeproNetMaster.master" Inherits="SIGePro.Net.ImportCCIAA" Codebehind="ImportCCIAA.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<table id="Table1" width="300" border="0">
				<tr>
					<td colspan="2"><br />
						<asp:label id="Label1" runat="server" CssClass="Label">&nbsp;Seleziona il file da inviare</asp:label>&nbsp;
						<input class="Form" id="FlImport" style="WIDTH: 395px; HEIGHT: 22px" type="file" size="46"
							name="File1" runat="server"></td>
				</tr>
				<tr>
					<td colspan="2"><br />
						<asp:checkbox id="CkbDelete" runat="server" CssClass="Label" Text="Elimina i dati importati precedentemente "></asp:checkbox>&nbsp;&nbsp;&nbsp;
						<asp:checkbox id="CkbSaltaPrimaRiga" runat="server" CssClass="Label" Text="La prima riga del file è d'intestazione"></asp:checkbox></td>
				</tr>
				<tr>
					<td colspan="2"><br />
						<asp:label id="Label3" runat="server" CssClass="Label">&nbsp;Log d'importazione</asp:label></td>
				</tr>
				<tr>
					<td class="Form" colspan="2">
						&nbsp;<asp:listbox id="LstLog" runat="server" Width="624px" Height="224px"></asp:listbox></td>
				</tr>
				<tr>
					<td width="40%">&nbsp;<asp:imagebutton id="BtnAvvia" runat="server" ImageUrl="Images/AVVIA.jpg" OnClick="BtnAvvia_Click"></asp:imagebutton>&nbsp;&nbsp;<asp:imagebutton id="BtnEsci" runat="server" ImageUrl="Images/ESCI.jpg" OnClick="BtnEsci_Click"></asp:imagebutton></td>
					<td align="center"><asp:label id="LblRunTime" runat="server"></asp:label></td>
				</tr>
			</TABLE>
</asp:Content>
