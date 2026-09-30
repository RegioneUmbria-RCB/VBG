<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="geoin.aspx.cs" Inherits="Sigepro.net.Istanze.SitFirenze.geoin" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SIT - Componente cartografico</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link rel="stylesheet" type="text/css" href='<%=ResolveClientUrl("~/stili/reset.css")%>' />
	<link rel="stylesheet" type="text/css" href="http://webmap.comune.intranet/css/index.css" />
	<link rel="stylesheet" type="text/css" href="http://webmap.comune.intranet/js_wc/4.0.0/css/web_components.css" />
	<link rel="Stylesheet" type="text/css" href='<%=ResolveClientUrl("~/stili/jqueryui/jquery-ui.custom.min.css")%>' />
	<link rel="stylesheet" type="text/css" href="geoin.css" />
	<script type="text/javascript" src='<%=ResolveClientUrl("~/js/jquery.min.js")%>'></script>
	<script type="text/javascript" src='<%=ResolveClientUrl("~/js/jquery-ui.min.js")%>'></script>
	<!-- CURRENT_PAGE_LIB -->
	<script type="text/javascript" src="http://webmap.comune.intranet/js_wc/4.0.0/web_components.js"></script>
	<!--[if IE]><script type="text/javascript" src="http://webmap.comune.intranet/js_wc/4.0.0/src/excanvas.js"></script><![endif]-->
	<script type="text/javascript" src="http://webmap.comune.intranet/js/index.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/i_signaler.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/com_signaler.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/debug_signaler.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/js_signaler.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/searcher.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/tabula_map.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/measure_viewer.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/db_persister.js"></script>
	<script type="text/javascript" src="http://webmap.comune.intranet/js/signaler_proxy.js"></script>
	<!-- CURRENT_PAGE_LIB -->
	<script type="text/javascript" src="geoin.utils.js"></script>
	<script type="text/javascript" src="geoin.sigeproServiceProxy.js"></script>
	<script type="text/javascript" src="geoin.layer.js"></script>
	<script type="text/javascript" src="geoin.confirmDialog.js"></script>
	<script type="text/javascript" src="geoin.xmlResultsParser.js"></script>
	<script type="text/javascript" src="geoin.GeoInCallbackService.js"></script>
	<script type="text/javascript" src="geoin.popupDettaglioAttivitaService.js"></script>
	<script type="text/javascript" src="geoin.ElementoOpenerService.js"></script>
	<script type="text/javascript" src="geoin.js"></script>

	<script type="text/javascript">
		/// <reference path="~/js/json.js" />
		/// <reference path="~/js/jquery.min.js" />
		/// <reference path="~/js/jquery-ui.min.js" />
		/// <reference path="geoin.layer.js" />
		/// <reference path="geoin.sigeproServiceProxy.js" />
		/// <reference path="geoin.xmlResultsLoader.js" />
		/// <reference path="geoin.confirmDialog.js" />
		/// <reference path="geoin.utils.js" />
		/// <reference path="geoin.js" />
		/// <reference path="geoin.GeoInCallbackService.js" />
		var _plugin;
		var _idPunto = '<%=this.Model.IdPuntoSit %>';
		var _idStradario = <%=this.Model.ID%>;
		var _idElemento = '<%=IdElemento %>';

		$(function () {
			var ashxPath			= new AshxPathService('<%=ResolveClientUrl("~/Istanze/SitFirenze/GeoinService.asmx") %>');
			var pageContext			= new PageContext('<%=IdComune%>', '<%=Software%>', '<%=Token%>');
			var geoinServiceProxy	= new GeoInServiceProxy( ashxPath, pageContext );
			var confirmationDialog	= new ConfirmationDialogElementService('confirmationDialog');
			var divVisualizzazione	= new DivVisualizzazioneService('pannelloPlugin');
			var popupDettagli		= new PopupDettaglioAttivitaService($('#dettagliAttivita'),geoinServiceProxy);
			var elementoOpenerService = new ElementoOpenerService(_idElemento);

			var callbackSvc			= new GeoInCallbackService( {
				puntoAggiunto : function(key){
					_idPunto = key;
					geoinServiceProxy.salvaIdPunto( _idStradario, key );
					$('#idPuntoCorrente').val(key);
					$('#boxCentraDaIdPunto').show();
					elementoOpenerService.impostaIdPunto(key);
				},
				
				layerCorrenteInizializzato : function(plugin){
					if( _idPunto !== '' )
					{
						plugin.impostaPuntoCorrente( _idPunto );
						plugin.focusSuPuntoCorrente();
					}else{
						$('#cmdCentraDaVia').click();
					}					

				},

				puntoEliminato : function(key){
					_idPunto = null;
					geoinServiceProxy.rimuoviIdPunto( _idStradario );
					$('#boxCentraDaIdPunto').hide();
					elementoOpenerService.impostaIdPunto('');
				},

				puntoCliccato : function(key){
					popupDettagli.mostraDettagli(key);
				}
			} );



			_plugin = new GeoInPlugin( geoinServiceProxy, confirmationDialog, divVisualizzazione, callbackSvc );

			$('#cmdCentraDaCatasto').click(function (e) {
				var foglio = $('#foglio').val();
				var particella = $('#particella').val();
				var tipoCatasto = $('#<%= ddlTipoCatasto.ClientID %>').val();

				_plugin.focusDaFoglioParticellaCatasto(foglio, particella, tipoCatasto);

				e.preventDefault();

				return false;
			});

			$('#cmdCentraDaVia').click(function (e) {
				var via = $('#via').val();
				var civico = $('#civico').val();

				_plugin.focusDaViaCivico(via, civico, '');

				e.preventDefault();

				return false;
			});

			$('#cmdCentraPuntoCorrente').click(function(e){
				var idPunto = $('#idPuntoCorrente').val();

				_plugin.impostaPuntoCorrente( idPunto );
				_plugin.focusSuPuntoCorrente();

				e.preventDefault();

				return false;
			});

			$('#chkAttive').click( function(e){
				if($(this).is(':checked'))
					_plugin.mostraAziendeAttive();
				else
					_plugin.nascondiAziendeAttive();
			});

			$('#chkCessate').click( function(e){
				if($(this).is(':checked'))
					_plugin.mostraAziendeCessate();
				else
					_plugin.nascondiAziendeCessate();
			});

			if( _idPunto === '')
				$('#boxCentraDaIdPunto').hide();

			<%= GetScriptIniziale() %>
		});
	</script>
</head>
<body>
	<form id="form1" runat="server">
	<div>
		<div id="contenitorePlugin">
			<div id="pannelloPlugin">
			</div>
		</div>
		<div id="controlli">
			<fieldset>
				<legend>Centra da foglio, particella e tipo catasto</legend>
				<div>
					<label>
						Foglio</label>
					<input type="text" class="small" id="foglio" value="<%= this.Model.Foglio %>"/>
				</div>
				<div>
					<label>
						Particella</label>
					<input type="text" class="small" id="particella" value="<%= this.Model.Particella %>"/>
				</div>

				<div>
					<label>
						Catasto</label>
					<asp:DropDownList runat="server" ID="ddlTipoCatasto" CssClass="small" />
				</div>

				<div class="bottoni">
					<a href="#" id="cmdCentraDaCatasto">Centra</a>
				</div>
			</fieldset>
			<fieldset>
				<legend>Centra da via e civico</legend>
				<div>
					<label>
						Via</label>
					<input type="text" id="via" class="large" value="<%= this.Model.NomeVia %>" />
				</div>
				<div>
					<label>
						Civico</label>
					<input type="text" class="small" id="civico" value="<%= this.Model.Civico%>" />
				</div>
				<!--<div>
					<label>
						Colore</label>
					<input type="text" value="" class="small" id="colore" />
				</div>-->
				<div class="bottoni">
					<a href="#" id="cmdCentraDaVia">Centra</a>
				</div>
			</fieldset>
			<fieldset id="boxCentraDaIdPunto">
				<legend>Centra su punto corrente</legend>
				<div>
					<label>id punto</label>
					<input type="text" id="idPuntoCorrente" readonly="readonly" class="large" value="<%=this.Model.IdPuntoSit %>" />
				</div>
				<div class="bottoni">
					<a href="#" id="cmdCentraPuntoCorrente">Centra</a>
				</div>
			</fieldset>
			<fieldset>
				<legend>Layer "Commercio fisso"</legend>
				<div>
					<label for="chkAttive">Mostra attive</label>
					<input type="checkbox" id="chkAttive" />
				</div>

				<div>
					<label for="chkCessate">Mostra cessate</label>
					<input type="checkbox" id="chkCessate" />
				</div>
			</fieldset>
		</div>
		<div id="confirmationDialog"></div>

		<div id="dettagliAttivita">
			<fieldset>
				<div>
					<label>Attiva</label>
					<span id="txtAttiva"></span>
				</div>

				<div>
					<label>Operante</label>
					<span id="txtOperante"></span>
				</div>

				<div>
					<label>Richiedente</label>
					<span id="txtRichiedente"></span>
				</div>

				<div>
					<label>In qualità di</label>
					<span id="txtInQualitaDi"></span>
				</div>

				<div>
					<label>Azienda</label>
					<span id="txtAzienda"></span>
				</div>
			</fieldset>
		</div>
	</div>
	</form>
</body>
</html>
