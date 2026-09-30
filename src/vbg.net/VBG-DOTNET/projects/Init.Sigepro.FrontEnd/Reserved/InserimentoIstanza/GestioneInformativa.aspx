<%@ Page Title="titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneInformativa.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneInformativa" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">


	<div class="panel panel-default" >
        <div class="panel-body">
		    <asp:Literal runat="server" ID="ltrTestoInformativa"/>
        </div>
	</div>

	<asp:CheckBox runat="server" ID="chkAccetto" Text="Accettazione" TextAlign="Right" />
	
</asp:Content>
