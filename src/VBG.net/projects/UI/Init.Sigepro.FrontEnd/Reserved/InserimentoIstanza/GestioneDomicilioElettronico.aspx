<%@ Page Title="Domicilio elettronico" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneDomicilioElettronico.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDomicilioElettronico" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<script type="text/javascript">
		var ddlDomicilioElettronico;
		var txtAltroIndirizzo;
		var divAltroIndirizzo;

		require(['jquery'],function($){
			$(function () {
				ddlDomicilioElettronico = $('#<%=ddlDomicilioElettronico.ClientID %>');
				txtAltroIndirizzo = $('#<%=txtAltroIndirizzo.ClientID %>');
				divAltroIndirizzo = $('#altroIndirizzo');

				ddlDomicilioElettronico.change( onDropDownChanged );

				ddlDomicilioElettronico.change();
			});

			function onDropDownChanged() {
				var usaEmailEsistente = ddlDomicilioElettronico.val() !== '';

				var style = usaEmailEsistente ? 'none' : '';

				if(usaEmailEsistente)
					txtAltroIndirizzo.val('');

				divAltroIndirizzo.css('display', style);
			};
		});
	</script>

	<br />
	<div class="inputForm">
		<fieldset>
			<div>
				<asp:Label ID="lblDomicilioElettronico" runat="server" AssociatedControlID="ddlDomicilioElettronico" Text="Domicilio elettronico"></asp:Label>
				<asp:DropDownList runat="server" ID="ddlDomicilioElettronico" DataValueField="Email" DataTextField="Nominativo" />
		
			</div>
			<div id="altroIndirizzo">
				<asp:Label ID="Label1" runat="server" AssociatedControlID="txtAltroIndirizzo" Text="Specificare l'indirizzo da utilizzare"></asp:Label>
				<asp:TextBox runat="server" ID="txtAltroIndirizzo" Columns="60" />
			</div>
		</fieldset>
	</div>
</asp:Content>
