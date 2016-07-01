<%@ Page Title="" Language="C#" MasterPageFile="~/Contenuti/ContenutiMaster.Master"
	EnableViewState="false"
	AutoEventWireup="true" CodeBehind="Step3.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Contenuti.Step3" %>

<%@ MasterType VirtualPath="~/Contenuti/ContenutiMaster.Master" %>
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
				height: 530,
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
<!--[if lte IE 8]>
<style type="text/css" media="screen">
	#alberoEndo .tipoEndo > ul
	{
		margin-top: -10px;
	}
</style>
<![endif]-->
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">
	<div id="stepContenuto" class="dettagliIntervento">
		<div id="intestazione">
			<div id="testo">
				<h2>
					Consulta</h2>
				<h1>
					GLI ADEMPIMENTI NECESSARI</h1>
				<h3>
					&nbsp;</h3>
			</div>
			<div id="bottoni">
				<img src='<%= ResolveClientUrl("~/images/contenuti/info.png") %>' alt="maggiori informazioni"
					id="infoImage" />
				<asp:HyperLink runat="server" ID="hlVisualizzaModello">
					<img src='<%=ResolveClientUrl("~/images/contenuti/visualizza-modello.jpg") %>' alt="Visualizza modello di domanda"/>
					<%--<asp:Image runat="server" ID="imgVisualizzaModello" ImageUrl='#' AlternateText="Visualizza modello di domanda" />--%>
				</asp:HyperLink>
				<a href='<%= GetUrlDownloadPagina() %>' target="_blank">
					<img src='<%= ResolveClientUrl("~/images/contenuti/stampa-scheda.jpg") %>' alt="Stampa scheda"
						border="0" />
				</a>
			</div>
			<div id="contenutiTooltip">
				In questa sezione puoi visualizzare gli adempimenti necessari all'attività e all'intervento selezionato.<br />
				La sezione "Procedimenti" offre maggiori informazioni in merito ad ogni singolo procedimento.
			</div>
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
	</div>
</asp:Content>
