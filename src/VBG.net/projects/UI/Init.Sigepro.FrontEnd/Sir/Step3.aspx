<%@ Page Title="" Language="C#" MasterPageFile="~/Sir/SirMaster.Master" AutoEventWireup="true" CodeBehind="Step3.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Sir.Step3" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
<script type="text/javascript">

		require(['jquery', 'app/wrapDescrizioneNodiPadre', 'jquery.ui'], function ($, wrapDescrizioneNodiPadre) {

			function collegaClickHandlerADettagliProcedimenti(elementoPadre) {
				var listaLinkDettagli = elementoPadre ? elementoPadre.find('.linkDettagliendo') : $('.linkDettagliendo');

				listaLinkDettagli.click(function (e) {
					e.preventDefault();
					var url = $(this).attr('href');

					$('#descrizioneEndo').load(url, function () { $(this).dialog('open'); });
				});
			};


			$('#tblContenutoCentrale').attr('background', '<%= ResolveClientUrl("~/images/contenuti/bg-interno-arancio.png")%>');

			var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx") + "?noprint=true&IdComune=" + IdComune + "&Id=" + Id %>&fromAreaRiservata=false';

			$('#corpoIntervento').load(url, function () {
				$('#accordion').accordion({
					header: "h3",
					heightStyle: 'content',
					change: function (event, ui) { $('#corpoIntervento').animate({ scrollTop: 0 }, 700); }
				});
				$('#accordion tr:nth-child(even)').addClass('rigaAlternata');

				collegaClickHandlerADettagliProcedimenti(null);

				wrapDescrizioneNodiPadre();

				$('.mostraEndoFacoltativi').click(function (e) {
					var idTipoEndo = $(this).attr('tipoEndo');
					var loadUrl = '<%=GetUrlEndoAttivabili() %>&tipoEndo=' + idTipoEndo;

					$(this).parent().parent().load(loadUrl + " li", function () {
						collegaClickHandlerADettagliProcedimenti($(this));
					});
				});
			});

			$('#descrizioneEndo').dialog({
				width: 800,
				height: 500,
				title: "Dettagli dell\'endoprocedimento",
				modal: true,
				autoOpen: false,
				open: function () {
					$(this).find('#accordion').accordion({ header: "h3", heightStyle: 'content' });
					$(this).find('tr:nth-child(even)').addClass('rigaAlternata');
				}
			});

			$('#infoImage').attr('title', $('#contenutiTooltip').html())
						   .hoverbox({ id: 'tooltipDettagliIntervento' });

			$('#modelloDomanda').dialog({
				width: 850,
				height: 570,
				title: "Modello di domanda",
				modal: true,
				autoOpen: false,
				resizable: false
			});

			$('#<%=hlVisualizzaModello.ClientID %>')
				.click(function (e) {
					e.preventDefault();

					$('#modelloDomanda iframe')
						.attr('src', this.href)
						.parent()
						.dialog('open');

					return false;
				});
		});

	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">

	<div class="sir-sezione">
		<div class="sir-titolo-sezione">
			<h2>Consulta</h2>
			<h1>GLI ADEMPIMENTI NECESSARI</h1>
		</div>	

		<div class="sir-bottoni-sezione">
			<asp:HyperLink runat="server" ID="hlVisualizzaModello"><img src='<%=ResolveClientUrl("~/images/contenuti/visualizza-modello.jpg") %>' alt="Visualizza modello di domanda"/></asp:HyperLink>
			<a href='<%= GetUrlDownloadPagina() %>' target="_blank">
				<img src='<%= ResolveClientUrl("~/images/contenuti/stampa-scheda.jpg") %>' alt="Stampa scheda"
					border="0" />
			</a>
		</div>
		<div class="clear"></div>

		<div id="corpoIntervento">
			<ul>
				<li>
					<img src='<%=ResolveClientUrl("~/Images/ajax-loader.gif") %>' alt="." />Caricamento
					in corso... </li>
			</ul>
		</div>

		<div id="descrizioneEndo"></div>

		<div id="modelloDomanda">
			<iframe style="width:100%;height:100%;border:0px;"></iframe>
		</div>
	</div>

</asp:Content>
