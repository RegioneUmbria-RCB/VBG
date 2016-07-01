<%@ Page Title="" Language="C#" MasterPageFile="~/Public/PaginaRicercaFoMaster.Master" AutoEventWireup="True" CodeBehind="RicercaInterventiDettaglio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.RicercaInterventiDettaglio" %>
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

			var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx") + "?noprint=true&IdComune=" + IdComune + "&Id=" + Id %>&fromAreaRiservata=false';

			$('#corpoIntervento').load(url, function () {
				$('#accordion').accordion({
					header: "h3",
					heightStyle: 'content',
					beforeActivate: function (event, ui) { $('#corpoIntervento').animate({ scrollTop: 0 }, 700); }
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

			$('#modelloDomanda').dialog({
				width: 850,
				height: 530,
				title: "Modello di domanda",
				modal: true,
				autoOpen: false,
				resizable: false
			});

			$('#lnkVisualizzaModello')
				.click(function (e) {
					e.preventDefault();
					
					var url = '<%= ResolveClientUrl("~/Public/ModelloDomanda/Visualizza.aspx") + "?idComune=" + IdComune + "&Intervento=" + Id + "&Software=" + this.Software %>';

					$('#modelloDomanda iframe')
						.attr('src', url )
						.parent()
						.dialog('open');

					return false;
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

		<div href="#" class="bottoneContenuti azione" id="lnkVisualizzaModello">
			<div class="titolo">Visualizza</div>
			<div class="descrizione">Modello</div>
		</div>
		<div class="clear"></div>

	<div id="corpoIntervento">
		<ul>
			<li>
				<img src='<%=ResolveClientUrl("~/Images/ajax-loader.gif") %>' alt="." />Caricamento
				in corso... </li>
		</ul>
	</div>
	<div id="descrizioneEndo">
	</div>
	<div id="modelloDomanda">
		<iframe style="width:100%;height:100%;border:0px;"></iframe>
	</div>

	<div class="bottoni">
		<asp:Button runat="server" id="cmdClose" OnClick="cmdClose_Click" Text="Torna indietro" />
	</div>

</asp:Content>
