<%@ Page Title="Benvenuto nel sistema di presentazione on-line delle pratiche" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="BenvenutoTares.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.BenvenutoTares" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

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

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	
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
