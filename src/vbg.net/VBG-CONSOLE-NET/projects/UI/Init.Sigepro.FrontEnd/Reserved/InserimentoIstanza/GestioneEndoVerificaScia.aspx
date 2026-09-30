<%@ Page Title="Untitled page" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="True" CodeBehind="GestioneEndoVerificaScia.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoVerificaScia" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content runat="server" ContentPlaceHolderID="head">
    <style>
        .alert {
            background-image:url('<%=ResolveClientUrl("~/Images/warning-icon.png")%>');
            background-repeat: no-repeat;
            background-position-x: 15px;
            background-position-y: 20px;
            padding-left: 60px;
        }

        .alert ul {
            margin-top: 10px;
        }
    </style>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

	<div class="alert alert-warning">

		<div class="contenutoWarning">
			<asp:Literal runat="server" ID="ltrMessaggioErrore" />
		</div>
	</div>

</asp:Content>
