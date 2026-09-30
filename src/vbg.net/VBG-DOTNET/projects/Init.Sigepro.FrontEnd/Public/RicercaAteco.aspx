<%@ Page Title="" Language="C#" MasterPageFile="~/Public/PaginaRicercaFoMaster.Master" AutoEventWireup="True" CodeBehind="RicercaAteco.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.RicercaAteco" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Ateco" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

	<script type="text/javascript">
        
			$(function () {
				$('#tblContenutoCentrale').attr('background', '<%= ResolveClientUrl("~/images/contenuti/bg-interno-blu.png")%>');

				$('#infoImage').attr('title', $('#contenutiTooltip').html())
						   .hoverbox({ id: 'tooltipAteco' });
			});
	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


	<a href="#" class="bottoneContenuti azione" id="lnkRicerca">
		<div class="titolo">Ricerca</div>
		<div class="descrizione">Testuale</div>
	</a>
	<div class="clear" />

	<cc1:AlberoAtecoJs runat="server" ID="alberoAteco" ClientIdLinkRicerca="lnkRicerca"  OnFogliaSelezionata="alberoAteco_FogliaSelezionata" />
		
</asp:Content>
