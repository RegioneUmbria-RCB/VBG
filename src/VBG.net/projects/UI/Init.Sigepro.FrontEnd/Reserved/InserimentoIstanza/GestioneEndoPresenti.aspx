<%@ Page Title="Gestione endo presenti" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneEndoPresenti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoPresenti" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc3" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
	<style media="all">
		.infoEstremiAtto{ font-style:italic; }
		.estremoObbligatorio{ background: url('<%=ResolveClientUrl("~/images/asterisco.png")%>') no-repeat left center; }
		.estremoAtto{ padding-left: 10px;}
	</style>

	<script type="text/javascript">
		require(['jquery', 'app/gestoneEndoPresenti'], function ($, gestioneEndo) {
			$(function () {
				gestioneEndo.bootstrap({
					serviceUrl: '<%= ResolveClientUrl("~/reserved/inserimentoIstanza/GestioneEndoPresenti.Scripts.asmx") %>/GetStringaCampiRichiesti?<%=Request.QueryString %>',
					token: '<%=UserAuthenticationResult.Token %>'
				});
			});
		});
	</script>
	
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		<asp:Repeater runat="server" ID="rptEndo">
			<HeaderTemplate>
			</HeaderTemplate>
			<ItemTemplate>
				<fieldset class="blocco fisso">
					<legend>
						<asp:Literal ID="ltrNomeEndo" runat="server" Text='<%# Eval("Descrizione") %>' />
					</legend>
					<asp:HiddenField runat="server" ID="hidIdEndo" Value='<%# Eval("CodiceInventario") %>' />
					<asp:CheckBox runat="server" ID="chkPresente" Checked='<%# Bind("Presente") %>' CssClass="comboPresente"
						Text="Sono in possesso dell'autorizzazione/titolo abilitativo" TextAlign="Right" />
					<div class="estremiAtto">
						<div class="infoEstremiAtto">
							
						</div>
						<div>
							<asp:Label runat="server" ID="Label2" AssociatedControlID="ddlTipiTitolo" Text="Tipo titolo:" CssClass="estremoAtto estremoObbligatorio" />
							<asp:DropDownList runat="server" ID="ddlTipiTitolo" 
															 CssClass="dropDownTipiTitolo"
															 DataValueField="Codice" 
															 DataTextField="Descrizione" 
															 DataSource='<%# Bind( "TitoliSupportati")%>' 
															 Selectedvalue='<%# Bind("IdTipoTitoloSelezionato") %>'/>
						</div>
						<div>
							<asp:Label runat="server" ID="lblNumeroAtto" AssociatedControlID="txtNumeroAtto"
								Text="Numero:" CssClass="estremoAtto labelNumero" />
							<asp:TextBox runat="server" ID="txtNumeroAtto" Text='<%# Eval("NumeroAtto") %>' Columns="10" MaxLength="15" CssClass="attoTextControl" />
						</div>
						<div>
							<asp:Label runat="server" ID="Label1" AssociatedControlID="txtDataAtto" Text="Del:"  CssClass="estremoAtto labelData"/>
							<cc3:DateTextBox ID="txtDataAtto" runat="server" Text='<%# Eval("DataAtto") %>'
								Columns="10"  CssClass="attoTextControl"/>
						</div>
						<div>
							<asp:Label runat="server" ID="Label3" AssociatedControlID="txtRilasciatoDa" Text="Rilasciato da:"  CssClass="estremoAtto labelRilasciatoDa"/>
							<asp:TextBox runat="server" ID="txtRilasciatoDa" Text='<%# Eval("RilasciatoDa") %>'
								Width="400px" MaxLength="100" CssClass="attoTextControl" />
						</div>
						<div>
							<asp:Label runat="server" ID="Label4" AssociatedControlID="txtNote" Text="Note:" CssClass="estremoAtto" />
							<asp:TextBox runat="server" ID="txtNote" Text='<%# Eval("Note") %>' TextMode="MultiLine"
								Width="400px" Rows="4" />
						</div>
					</div>
				</fieldset>
			</ItemTemplate>
			<FooterTemplate>
			</FooterTemplate>
		</asp:Repeater>
	</div>
</asp:Content>
