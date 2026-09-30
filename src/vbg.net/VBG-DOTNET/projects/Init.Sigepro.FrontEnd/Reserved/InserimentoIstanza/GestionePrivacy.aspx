<%@ Page Title="titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestionePrivacy.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestionePrivacy" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">


	<div class="panel panel-default" >
        <div class="panel-body">
		    <asp:Literal runat="server" ID="ltrTestoPrivacy"/>
        </div>
	</div>

	<asp:CheckBox runat="server" ID="chkAccetto" Text="Accettazione" TextAlign="Right" />
	
</asp:Content>
