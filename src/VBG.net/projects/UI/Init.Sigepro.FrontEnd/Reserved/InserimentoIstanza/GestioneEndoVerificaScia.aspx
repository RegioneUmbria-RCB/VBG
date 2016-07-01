<%@ Page Title="Untitled page" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="True" CodeBehind="GestioneEndoVerificaScia.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoVerificaScia" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		<fieldset>
			<div class="warningEndo">
				<asp:Image runat="server" ID="imgWarning" ImageUrl="~/Images/warning-icon.png" class="iconaWarning" />
				<div class="contenutoWarning">
					<asp:Literal runat="server" ID="ltrMessaggioErrore" />
				</div>
			</div>
		</fieldset>
	</div>
</asp:Content>
