<%@ Page Title="" Language="C#" MasterPageFile="~/Contenuti/ContenutiMaster.Master" AutoEventWireup="true" 
CodeBehind="Step1.aspx.cs" EnableViewState="false" Inherits="Init.Sigepro.FrontEnd.Contenuti.Step1" %>
<%@ MasterType VirtualPath="~/Contenuti/ContenutiMaster.Master" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Ateco" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
	<script type="text/javascript">

		require(['jquery','jquery.hoverbox'], function ($) {
			$(function () {
				$('#tblContenutoCentrale').attr('background', '<%= ResolveClientUrl("~/images/contenuti/bg-interno-blu.png")%>');

				$('#infoImage').attr('title', $('#contenutiTooltip').html())
						   .hoverbox({ id: 'tooltipAteco' });
			});
		});


	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">

	

	<div id="stepContenuto" class="ricercaAteco">
	
		<div id="intestazione">
			<div id="testo">
				<h2>Ricerca</h2>
				<h1>L'ATTIVITA' CHE SVOLGE LA TUA IMPRESA</h1>
			</div>
			<div id="bottoni">
				<img src='<%= ResolveClientUrl("~/images/contenuti/info.png") %>' alt="maggiori informazioni" id="infoImage"/>
				<a href="#" id="lnkRicerca"><img src='<%= ResolveClientUrl("~/images/contenuti/ricerca-testuale.png") %>' alt="ricerca testuale" border="0" /></a>
			</div>
			<div id="contenutiTooltip">
				In questa sezione è possibile individuare l'attività della propria azienda secondo la classificazione ATECO.
				In seguito alla selezione sarà possibile individuare le informazioni relative agli adempimenti che è possibile attivare.
			</div>
		</div>
		<div class="clear"></div>
		<cc1:AlberoAtecoJs runat="server" ID="alberoAteco" ClientIdLinkRicerca="lnkRicerca"  OnFogliaSelezionata="alberoAteco_FogliaSelezionata" />
		
	</div>
	

</asp:Content>
