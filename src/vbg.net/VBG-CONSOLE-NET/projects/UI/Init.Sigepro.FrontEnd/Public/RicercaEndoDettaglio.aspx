<%@ Page Title="" Language="C#" MasterPageFile="~/Public/PaginaRicercaFoMaster.Master" AutoEventWireup="True" CodeBehind="RicercaEndoDettaglio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.RicercaEndoDettaglio" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

<script type="text/javascript">
	require(['jquery', 'app/wrapDescrizioneNodiPadre', 'jquery.ui'], function ($, wrapDescrizioneNodiPadre) {

		var url = '<%= ResolveClientUrl("~/Public/MostraDettagliEndo.aspx") + "?noprint=true&IdComune=" + IdComune + "&Id=" + Id %>&fromAreaRiservata=false&MostraBottoneStampa=false';

		$('#corpoEndo').load(url, function () {
			$('#accordion').accordion({
				header: "h3",
				heightStyle: 'content',
				beforeActivate: function (event, ui) { $('body').animate({ scrollTop: 0 }, 'fast'); }
			});
			$('#accordion tr:nth-child(even)').addClass('rigaAlternata');

			wrapDescrizioneNodiPadre();
		});

		$('#lnkStampa').click(function (e) {
			var url = '<%=GetUrlDownloadPagina() %>';

			window.open(url);

			e.preventDefault();
		});
	});

	</script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

		<div href="#" class="bottoneContenuti azione" id="lnkStampa">
			<div class="titolo">Stampa</div>
			<div class="descrizione">Scheda</div>
		</div>

		<div class="clear"></div>

		<div id="corpoEndo">
			<ul>
				<li>
					<img src='<%=ResolveClientUrl("~/Images/ajax-loader.gif") %>' alt="." />Caricamento
					in corso... </li>
			</ul>
		</div>

		<div class="bottoni">
			<asp:Button runat="server" id="cmdClose" OnClick="cmdClose_Click" Text="Torna indietro" />
		</div>

</asp:Content>
