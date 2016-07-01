<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
	CodeBehind="Benvenuto.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Benvenuto"
	Title="Benvenuto nel sistema di presentazione on-line delle pratiche" %>

<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<style>
		ul > li
		{
			list-style-type: disc;
		}
		ul
		{
			padding-left: 20px;
			padding-top: 10px;
		}
	</style>
	<div id="contenutoStep">
		<div class="descrizioneStep">
			<asp:Literal runat="server" ID="descrizionePagina" />
			<asp:Repeater runat="server" ID="rptDescrizioneVociMenu">
				<HeaderTemplate>
					<ul>
				</HeaderTemplate>
				<ItemTemplate>
					<li>
						<asp:Literal runat="server" ID="ltrDescrizioneStep" Text='<%# Eval("DescrizioneEstesa") %>' /></li>
				</ItemTemplate>
				<FooterTemplate>
					</ul>
				</FooterTemplate>
			</asp:Repeater>
		</div>
	</div>
</asp:Content>
