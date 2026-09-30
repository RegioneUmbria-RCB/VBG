<%@ Page language="c#" Inherits="SIGePro.Net.SendMail" AutoEventWireup="true"  MasterPageFile="~/SigeproNetMaster.master" Codebehind="SendMail.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<TABLE id="Table1" cellSpacing="1" cellPadding="1" width="100%" border="0">
				<TR>
					<TD width="25%"><br>
						&nbsp;<asp:label id="Label1" runat="server" CssClass="Label">*Da:</asp:label>
						<asp:RequiredFieldValidator id="RequiredFieldValidator3" runat="server" ErrorMessage="*" ControlToValidate="txtFrom">*</asp:RequiredFieldValidator></TD>
					<TD width="75%"><asp:textbox id="txtFrom" runat="server" Width="100%" ReadOnly="True"></asp:textbox></TD>
				</TR>
				<TR>
					<TD width="25%"><br>
						&nbsp;<asp:label id="Label2" runat="server" CssClass="Label">*A:</asp:label>
						<asp:RequiredFieldValidator id="RequiredFieldValidator2" runat="server" ErrorMessage="*" ControlToValidate="txtTo">*</asp:RequiredFieldValidator></TD>
					<TD width="75%"><asp:textbox id="txtTo" runat="server" Width="100%"></asp:textbox></TD>
				</TR>
				<TR>
					<TD width="25%"><br>
						&nbsp;&nbsp;<asp:label id="Label3" runat="server" CssClass="Label">Cc:</asp:label></TD>
					<TD width="75%"><br>
						<asp:textbox id="txtCC" runat="server" Width="100%"></asp:textbox></TD>
				</TR>
				<TR>
					<TD width="25%"><br>
						&nbsp;&nbsp;<asp:label id="Label4" runat="server" CssClass="Label">Bcc:</asp:label></TD>
					<TD width="75%"><br>
						<asp:textbox id="txtBCC" runat="server" Width="100%"></asp:textbox></TD>
				</TR>
				<TR>
					<TD width="25%"><br>
						&nbsp;<asp:label id="Label8" runat="server" CssClass="Label">Mail tipo:</asp:label></TD>
					<TD width="75%"><br>
						<asp:dropdownlist id="ddlMailTipo" runat="server" AutoPostBack="True" onselectedindexchanged="ddlMailTipo_SelectedIndexChanged"></asp:dropdownlist></TD>
				</TR>
				<TR>
					<TD width="25%"><br>
						&nbsp;&nbsp;<asp:label id="Label5" runat="server" CssClass="Label">*Oggetto:</asp:label>
						<asp:RequiredFieldValidator id="RequiredFieldValidator1" runat="server" ErrorMessage="*" ControlToValidate="txtSubject">*</asp:RequiredFieldValidator></TD>
					<TD width="75%"><br>
						<asp:textbox id="txtSubject" runat="server" Width="100%"></asp:textbox></TD>
				</TR>
				<TR>
					<TD vAlign="top" width="25%"><br>
						&nbsp;&nbsp;<asp:label id="Label6" runat="server" CssClass="Label">*Corpo:</asp:label>
						<asp:RequiredFieldValidator id="RequiredFieldValidator4" runat="server" ErrorMessage="*" ControlToValidate="txtBody">*</asp:RequiredFieldValidator></TD>
					<TD width="75%"><br>
						<asp:textbox id="txtBody" runat="server" Width="100%" Rows="10" Columns="5" TextMode="MultiLine"></asp:textbox></TD>
				</TR>
				<TR>
					<TD vAlign="top" width="25%"><br>
						&nbsp;&nbsp;<asp:label id="Label7" runat="server" CssClass="Label">Lista allegati:</asp:label></TD>
					<TD width="75%"><br>
						<asp:checkboxlist id="chkAttachments" runat="server"></asp:checkboxlist></TD>
				</TR>
				<TR>
					<TD width="25%" colSpan="2"><asp:imagebutton id="Send" runat="server" ImageUrl="Images/INVIAEMAIL.gif" OnClick="Send_Click"></asp:imagebutton>&nbsp;
						<asp:imagebutton id="Close" runat="server" ImageUrl="Images/CHIUDI.gif" CausesValidation="False"></asp:imagebutton></TD>
				</TR>
			</TABLE>
</asp:Content>